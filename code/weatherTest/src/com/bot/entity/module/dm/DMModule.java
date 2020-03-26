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

import com.bot.entity.module.dm.state.State;
import com.bot.entity.module.dm.state.StateContent;
import com.bot.entity.module.nlu.intent.Intent;
import com.bot.entity.module.nlu.slot.LOCNode;
import com.bot.entity.module.nlu.slot.Slot;
import com.bot.entity.module.nlu.slot.SlotDATE;
import com.bot.entity.util.StateStack;

/**
 *@desc:对话管理模块，单例模式<br>
 *【回合（组）与轮次】<br>
 *对于task-based bot，给出查询结果或中断的轮次为一组对话；<br>
 *对于chat bot，每一utterance都为一个轮次
 *@author 邓旸
 *@date:2020年2月13日下午3:37:41
 */
public class DMModule {
	private static DMModule instance = new DMModule();
	private DialogueTracer dialogueTracer = DialogueTracer.getInstance();
	private Policy policy = Policy.getInstance();
	
	private int turnId;
	private int dialogId;

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
	 *@desc:DM模块入口
	 *@param roleType 角色类型
	 *@param botType 机器人类型
	 *@param rawUtterance
	 *@param intent
	 *@param slot
	 *@return:int 指令编号
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
			//1. 判断话题一致性
			if(!dialogueTracer.isThemeChanged(botType, intent,this.turnId)) {{}
				//话题一致：切换轮次
				this.nextTurn();			
			}else {
				//话题不一致：上一话题中断，开启新话题
				this.dialogueInterrupted();
				this.dialogueStarted();
				this.nextTurn();
			}
			
			
			//2. 创建状态
			State state = new State();
			state.setRole(State.ROLE_USER);
			state.setTurnId(this.turnId);
			state.setDialogId(this.dialogId);
			state.setStateType(botType);
			state.getStateContent().setRawUtterance(rawUtterance);
			
			if(botType==State.STATE_TASK_BOT) {
				//0. 意图修正
				intent = this.intentAmending(slot, intent, rawUtterance);
				//调用的是任务型bot				
				//3(2-1). 判断意图和语义槽是否完整
				Intent lastIntent = this.dialogueTracer.getLastIntent();
				policyInstr = this.policy.isIntentFilled(intent, lastIntent);
				if(policyInstr==Policy.STATE_OK) {
					//若上一轮次意图不为空，则直接继承
					if(intent==null) intent = lastIntent;
					policyInstr = this.policy.isSlotComplited(slot);
					if(policyInstr==Policy.STATE_OK) {
						//3-1(2-1-1). 完整
						//创建查询、关闭对话（nlg）
						//3-2(2-1-1). 判断日期是否合法
						policyInstr = this.policy.isSlotLegal(slot); 
					}else {
						//3-1(2-1-2). 不完整：若上文中有，直接填充槽位；否则，创建追问响应（nlg）
						/*
						 * 【注】仅在以下情况中，利用上文进行填充：
						 * 1. 日期：上一次对话(Task based)查询为单日（只有单日查询才可能存在日期不完整的情况）
						 * 2. 地点：继承上一次对话(Task based)
						 */
						if(policyInstr==20 || policyInstr==10) {
							
						}
						else if(policyInstr >10 && policyInstr<=15) {
							//3-2(2-1). 日期不完整
							//返回上一次对话的语义槽(Task based)
							State lastState = dialogueTracer.getLastStateContainComplicatedDATE();
							if(lastState!=null) {
								switch(policyInstr) {
								case Policy.INSTRUCTION_ERROR_DATE_LOST:
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
								policyInstr = Policy.STATE_OK;
							}
							
						}
						else if(policyInstr>=16 && policyInstr<=19) {
							//3-2(2-1). 判断日期是否合法
							policyInstr = this.policy.isSlotLegal(slot); 
							if(policyInstr == Policy.STATE_OK) {
								//支持日期范围：今天、未来6天，不支持过去时间
								//3-4(2-1). 地点不完整
								State lastState = dialogueTracer.getLastStateContainComplicatedLOC();
								if(lastState!=null) {
									slot.setLoc(lastState.getStateContent().getSlot().getLoc());
									policyInstr = Policy.STATE_OK;
								}else {
									policyInstr = Policy.INSTRUCTION_ERROR_LOC_LOST;
								}
							}else {
								//不合法：1.日期不符合常识-->创建澄清响应（nlg）
							}
						}
					}
				}else {
					//意图为空，寻问意图
				}
			}else {
				//调用的是闲聊bot
				policyInstr = Policy.INSTRUCTION_CHAT_API;
				//3.(2-2) 调用api并创建响应
				//3-1.(2-2) 调用api
				//3-2.(2-2) 创建响应
				//3-3.(1-1) 关闭对话
			}
			
			state.getStateContent().setIntent(intent);
			state.getStateContent().setSlot(slot);
			this.dialogueTracer.addState(state);
		}
		
		
		return policyInstr;
		
	}
	
	/**
	 *@desc:意图修正<br>若语义槽占比≥50%，则承袭上一意图
	 *@param slot
	 *@param rawUtterance
	 *@return
	 *@return:Intent
	 *@trhows
	 */
	private Intent intentAmending(Slot slot, Intent intent, String rawUtterance) {
		Intent newIntent = null;
		
		//去掉语句中的停用词
		String root = "F:/file_temp/";
		String fileName = "中文停用词表.txt";
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
	 *@desc:开启新一组对话
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
