/**
 * 
 */
package com.bot.entity.module.dm;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bot.entity.module.dm.state.State;
import com.bot.entity.module.dm.state.StateContent;
import com.bot.entity.module.nlu.intent.Intent;
import com.bot.entity.module.nlu.slot.LOCNode;
import com.bot.entity.module.nlu.slot.Slot;
import com.bot.entity.module.nlu.slot.SlotDATE;
import com.bot.entity.util.StateStack;

/**
 *@desc:�Ի�����ģ�飬����ģʽ<br>
 *���غϣ��飩���ִΡ�<br>
 *����task-based bot��������ѯ������жϵ��ִ�Ϊһ��Ի���<br>
 *����chat bot��ÿһutterance��Ϊһ���ִ�
 *@author �˕D
 *@date:2020��2��13������3:37:41
 */
public class DMModule {
	private static DMModule instance = new DMModule();
	private DialogueTracer dialogueTracer = DialogueTracer.getInstance();
	private Policy policy = Policy.getInstance();
	
	private int turnId;
	private int dialogId;
	
	static Logger logger = Logger.getLogger(DMModule.class);

	/**
	 * 
	 */
	private DMModule() {
		this.turnId = 0;
		this.dialogId = 0;
	}
	
	public static DMModule getInstance() {
		return instance;
	}
	
	
	/**
	 * 
	 *@desc:DMģ�����
	 *@param roleType ��ɫ����
	 *@param botType ����������
	 *@param rawUtterance
	 *@param intent
	 *@param slot
	 *@return:int ָ����
	 *@trhows
	 */
	public int solve(int roleType, int botType, String rawUtterance, int instr, Intent intent, Slot slot) {
		int policyInstr = -1;
		if(this.dialogId==0) this.dialogueStarted();
		if(roleType==State.ROLE_BOT) {
			this.nextTurn();
			State state = new State();
			state.setRole(State.ROLE_BOT);
			state.setTurnId(this.turnId);
			state.setDialogId(this.dialogId);
			state.setStateType(botType);
			state.getStateContent().setRawUtterance(rawUtterance);
			this.dialogueTracer.addState(state);
			if(instr==Policy.INSTRUCTION_CHAT_API || instr==Policy.STATE_OK) {
				this.dialogueFinished();
			}
		}else {
			//0. ��ͼ����
//			intent = this.intentAmending(slot, intent, rawUtterance);
//			logger.debug("amend the intent --> "+intent.getIntentName());
			//1. �жϻ���һ����
			if(!dialogueTracer.isThemeChanged(botType, intent,this.turnId)) {
				//����һ�£��л��ִ�
				logger.debug("the same theme --> next turn");
				this.nextTurn();			
			}else {
				//���ⲻһ�£���һ�����жϣ������»���
				logger.debug("theme changed");
				this.dialogueInterrupted();
				logger.debug("dialogue interrupted");
				this.dialogueStarted();
				logger.debug("new dialogue started");
				this.nextTurn();
				logger.debug("new turn");
			}
			
			
			//2. ����״̬
			State state = new State();
			state.setRole(State.ROLE_USER);
			state.setTurnId(this.turnId);
			state.setDialogId(this.dialogId);
			state.setStateType(botType);
			state.getStateContent().setRawUtterance(rawUtterance);
			logger.debug("new state created");
			
			if(botType==State.STATE_TASK_BOT) {
				//���õ���������bot				
				//3(2-1). �ж���ͼ��������Ƿ�����
				logger.debug("start to judge wheather the slot and intent are complete...");
				Intent lastIntent = this.dialogueTracer.getLastIntent();
				policyInstr = this.policy.isIntentFilled(intent, lastIntent);
				if(policyInstr==Policy.STATE_OK) {
					//����һ�ִ���ͼ��Ϊ�գ���ֱ�Ӽ̳�
					if(intent==null) intent = lastIntent;
					policyInstr = this.policy.isSlotComplited(slot);
					if(policyInstr==Policy.STATE_OK) {
						//3-1(2-1-1). ����
						logger.debug("--> complete");
						//������ѯ���رնԻ���nlg��
						//3-2(2-1-1). �ж������Ƿ�Ϸ�
						policyInstr = this.policy.isSlotLegal(slot); 
					}else {
						//3-1(2-1-2). �����������������У�ֱ������λ�����򣬴���׷����Ӧ��nlg��
						logger.debug("--> isn't complete");
						/*
						 * ��ע��������������У��������Ľ�����䣺
						 * 1. ���ڣ���һ�ζԻ�(Task based)��ѯΪ���գ�ֻ�е��ղ�ѯ�ſ��ܴ������ڲ������������
						 * 2. �ص㣺�̳���һ�ζԻ�(Task based)
						 */
						if(policyInstr==20) {
							logger.debug("date and loc are lost");
							State lastStateDATE = dialogueTracer.getLastStateContainComplicatedDATE();
							State lastStateLOC = dialogueTracer.getLastStateContainComplicatedLOC();
							slot = fillDATE(lastStateDATE, slot, policyInstr);
							if(lastStateLOC!=null) {
								slot.setLoc(lastStateLOC.getStateContent().getSlot().getLoc());
								policyInstr = Policy.STATE_OK;
								logger.debug("loc has been filled");
							}else {
								policyInstr = Policy.INSTRUCTION_ERROR_LOC_LOST;
								logger.debug("loc is lost");
							}
						}
						else if(policyInstr==10) {
							logger.debug("date is illigeal");
						}
						else if(policyInstr >10 && policyInstr<=15) {
							//3-2(2-1). ���ڲ�����
							logger.debug("date isn't complete");
							//������һ�ζԻ��������(Task based)
							logger.debug("to get the last state from state stack...");
							State lastState = dialogueTracer.getLastStateContainComplicatedDATE();
							if(lastState!=null) {
								slot = fillDATE(lastState, slot, policyInstr); 
								policyInstr = Policy.STATE_OK;
								logger.debug("date has been filled");
							}else {
								policyInstr = Policy.INSTRUCTION_ERROR_DATE_LOST;
								logger.debug("date is lost");
							}
							
						}
						else if(policyInstr>=16 && policyInstr<=19) {
							//3-2(2-1). �ж������Ƿ�Ϸ�
							policyInstr = this.policy.isSlotLegal(slot); 
							if(policyInstr == Policy.STATE_OK) {
								//֧�����ڷ�Χ�����졢δ��6�죬��֧�ֹ�ȥʱ��
								logger.debug("date is legal but loc is lost");
								//3-4(2-1). �ص㲻����
								State lastState = dialogueTracer.getLastStateContainComplicatedLOC();
								if(lastState!=null) {
									slot.setLoc(lastState.getStateContent().getSlot().getLoc());
									policyInstr = Policy.STATE_OK;
									logger.debug("loc has been filled");
								}else {
									policyInstr = Policy.INSTRUCTION_ERROR_LOC_LOST;
									logger.debug("loc is lost");
								}
							}else {
								//���Ϸ���1.���ڲ����ϳ�ʶ-->����������Ӧ��nlg��
								logger.debug("date is illegal");
							}
						}
					}
				}else {
					//��ͼΪ�գ�Ѱ����ͼ
				}
			}else {
				//���õ�������bot
				policyInstr = Policy.INSTRUCTION_CHAT_API;
				//3.(2-2) ����api��������Ӧ
				//3-1.(2-2) ����api
				//3-2.(2-2) ������Ӧ
				//3-3.(1-1) �رնԻ�
			}
			
			state.getStateContent().setIntent(intent);
			state.getStateContent().setSlot(slot);
			this.dialogueTracer.addState(state);
		}
		
		
		return policyInstr;
		
	}
	
	public Slot fillDATE(State lastState, Slot slot, int policyInstr) {
		switch(policyInstr) {
		case Policy.INSTRUCTION_ERROR_DATE_LOST:
			slot.setDate(lastState.getStateContent().getSlot().getDate());
			break;
		case Policy.INSTRUCTION_ERROR_DATE_AND_LOC_LOST:
			slot.setDate(lastState.getStateContent().getSlot().getDate());
			break;
		case Policy.INSTRUCTION_ERROR_DATE_DAY_LOST:
			slot.getDate().get(0).get("start").setDay(lastState.getStateContent().getSlot().getDate().get(0).get("start").getDay());
			break;
		case Policy.INSTRUCTION_ERROR_DATE_MONTH_LOST:
			slot.getDate().get(0).get("start").setMonth(lastState.getStateContent().getSlot().getDate().get(0).get("start").getMonth());
			break;
		case Policy.INSTRUCTION_ERROR_DATE_DAY_AND_MONTH_LOST:
			slot.getDate().get(0).get("start").setDay(lastState.getStateContent().getSlot().getDate().get(0).get("start").getDay());
			slot.getDate().get(0).get("start").setMonth(lastState.getStateContent().getSlot().getDate().get(0).get("start").getMonth());
			break;
		}
		return slot;
	}
	
	/**
	 *@desc:��ͼ����<br>�������ռ�ȡ�50%�����Ϯ��һ��ͼ
	 *@param slot
	 *@param rawUtterance
	 *@return
	 *@return:Intent
	 *@trhows
	 */
	public Intent intentAmending(Slot slot, Intent intent, String rawUtterance) {
		Intent newIntent = null;
		
		//ȥ������е�ͣ�ô�
		String root = "F:/file_temp/";
		String fileName = "����ͣ�ôʱ�.txt";
		List<String> stopWordsList = new ArrayList<String>();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(root+fileName), "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while((s = br.readLine())!=null) {
				stopWordsList.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String sw: stopWordsList) {
			while(rawUtterance.indexOf(sw)!=-1) {
				rawUtterance = rawUtterance.replaceFirst(sw, "");
			}
		}
		
		List<Map<String, SlotDATE>> dList = slot.getDate();
		int uttLen = 0;
		int locLen = 0;
		if(rawUtterance.length()!=0) {
			uttLen = rawUtterance.length();
			if(!slot.getLoc().getName().equals("ROOT")) locLen = slot.getLoc().getName().length();
			int dateLen = 0;
			for(Map<String, SlotDATE> map: dList) {
				if(map.get("start")!=null) dateLen += map.get("start").getEndIndex()-map.get("start").getStartIndex()+1;
				if(map.get("end")!=null) dateLen += map.get("end").getEndIndex()-map.get("end").getStartIndex()+1;
			}
			System.out.println(1.0*(dateLen+locLen)/uttLen);
			if(1.0*(dateLen+locLen)/uttLen>=0.5) {
				State lastState = dialogueTracer.getLastUserState();
				if(lastState!=null && lastState.getStateContent().getIntent()!=null) newIntent = lastState.getStateContent().getIntent();
				else newIntent = intent;
			}else {
				newIntent = intent;
			}
		}else {
			newIntent = intent;
		}
		
		return newIntent;
	}

	public void nextTurn() {
		this.turnId++;
	}
	
	public void restartDM() {
		this.dialogId = 0;
		this.turnId = 0;
	}
	
	/**
	 * 
	 *@desc:������һ��Ի�
	 *@return:void
	 *@trhows
	 */
	public void dialogueStarted() {
		this.dialogId++;
		this.turnId = 0;
		
		State newState = new State();
		newState.setStateType(State.STATE_START);
		newState.setDialogId(this.dialogId);
		newState.setTurnId(this.turnId);
		newState.setRole(State.ROLE_SYS);
		this.dialogueTracer.addState(newState);
	}
	
	public void dialogueFinished() {
		State newState = new State();
		newState.setStateType(State.STATE_END);
		newState.setDialogId(this.dialogId);
		newState.setTurnId(this.turnId);
		newState.setRole(State.ROLE_SYS);
		this.dialogueTracer.addState(newState);
	}
	
	public void dialogueInterrupted() {
		State newState = new State();
		newState.setStateType(State.STATE_INTURRUPT);
		newState.setDialogId(this.dialogId);
		newState.setTurnId(this.turnId);
		newState.setRole(State.ROLE_SYS);
		this.dialogueTracer.addState(newState);
	}
	
	public State getLastUserState() {
		return this.dialogueTracer.getLastUserState();
	}
}