/**
 * 
 */
package com.hebeishida.bot.module.dm;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hebeishida.bot.module.dm.state.State;
import com.hebeishida.bot.module.nlu.intent.Intent;
import com.hebeishida.bot.module.nlu.slot.SlotDATE;
import com.hebeishida.bot.util.StateStack;

/**
 *@desc:对话跟踪模块，单例模式
 *@author 邓旸
 *@date:2020年2月15日下午12:50:01
 */
public class DialogueTracer {
	private static DialogueTracer instance = new DialogueTracer();
	private StateStack stateStack = StateStack.getInstance();
	static Logger logger = Logger.getLogger(DialogueTracer.class);
	/**
	 * 
	 */
	private DialogueTracer() {
		// TODO Auto-generated constructor stub
	}
	
	public static DialogueTracer getInstance() {
		return instance;
	}
	
	/**
	 * 
	 *@desc:判断话题是否改变
	 *<br>判断依据：1.调用机器人是否改变；2.意图是否改变；
	 *<br>true：主题改变；false：主题不变
	 *@param intent
	 *@param slot
	 *@return:boolean
	 *@trhows
	 */
	public boolean isThemeChanged(int botType, Intent intent, int turnId) {
		boolean flag = false;
		if(stateStack.isEmpty() || turnId==0) {
			flag = false;
		}else {
			//找到第一个非系统状态
			int n = stateStack.size()-1;
			while(n>=0) {
				State state = stateStack.get(n);
				if(state.getStateType()!=State.STATE_START
						&& state.getStateType()!=State.STATE_END
						&& state.getStateType()!=State.STATE_INTURRUPT
						&& state.getRole()==State.ROLE_USER) {
					break;
				}
				n--;
			}
			if(n>=0) {
				State lastState = stateStack.get(n);
				stateStack.pushStateStack(lastState);
				
				if(botType==State.STATE_CHAT_BOT) {
					//若当前调用的是闲聊机器人，但上一对话调用的是任务型机器人——主题改变
					if(lastState.getStateType()!=State.STATE_CHAT_BOT) flag = true;
				}else if(botType==State.STATE_TASK_BOT && lastState.getStateType()==State.STATE_CHAT_BOT) {
					//若当前是任务型机器人，但上衣对话调用的是闲聊机器人——主题改变
					flag = true;	
				}else {
					//若当前和上一状态调用的都是任务型机器人，但意图不同——主题改变
					Intent lastIntent = lastState.getStateContent().getIntent();
					if(intent!=null && lastIntent!=null && !lastIntent.getIntentId().equals(intent.getIntentId())) {
						flag = true;
					}
				}				
			}
		}
		
		return flag;
	}
	
	/**
	 * 
	 *@desc:返回满足要求的状态
	 *<br>1. task-based； 2. 单日查询
	 *@return
	 *@return:State
	 *@trhows
	 */
	public State getLastStateContainComplicatedDATE() {
		State lastState = null;
		if(!stateStack.isEmpty()) {
			//找到第一个非系统状态
			int n = stateStack.size()-1;
			while(n>=0) {
				State state = stateStack.get(n);
				if(state.getRole()==State.ROLE_USER	&& state.getStateType()==State.STATE_TASK_BOT
						&& state.getStateContent().getSlot().getDate().size() == 1
						&& state.getStateContent().getSlot().getDate().get(0).get("start") != null
						&& state.getStateContent().getSlot().getDate().get(0).get("end") == null
						&& state.getStateContent().getSlot().getDate().get(0).get("start").isFilled()) {
					break;
				}
				n--;
			}
			if(n>=0) {
				logger.debug("last user state has been found");
				lastState = new State(stateStack.get(n));
//				List<Map<String, SlotDATE>> dateList = stateStack.get(n).getStateContent().getSlot().getDate();
//				if(dateList.size()==1
//						&& dateList.get(0).get("end")==null
//						&& dateList.get(0).get("start")!=null
//						&& dateList.get(0).get("start").isFilled()) {
//				}
			}
		}
		
		return lastState;
	}
	
	/**
	 * 
	 *@desc:返回上一个含有符合要求LOC语义槽的状态
	 *@return
	 *@return:State
	 *@trhows
	 */
	public State getLastStateContainComplicatedLOC() {
		State lastState = null;
		
		if(!stateStack.isEmpty()) {
			//找到第一个非系统状态
			int n = stateStack.size()-1;
			while(n>=0) {
				logger.debug("last user state has been found");
				State state = stateStack.get(n);
				if(state.getRole()==State.ROLE_USER	&& state.getStateType()==State.STATE_TASK_BOT
						&& !state.getStateContent().getSlot().getLoc().getName().equals("ROOT")) {
					break;
				}
				n--;
			}
			if(n>=0) {
				if(stateStack.get(n).getStateContent().getSlot().getLoc().isFilled()) {
					lastState = new State(stateStack.get(n));
				}
			}
		}
		
		return lastState;
	}
	
	public void addState(State state) {
		State newState = new State(state);
		this.stateStack.pushStateStack(newState);
	}

	/**
	 *@desc:一句话描述
	 *@return
	 *@return:Object
	 *@trhows
	 */
	public Intent getLastIntent() {
		Intent lastIntent = null;
		if(!stateStack.isEmpty()) {
			//找到第一个非系统状态
			int n = stateStack.size()-1;
			while(n>=0) {
				State state = stateStack.get(n);
				if(state.getRole()==State.ROLE_USER	&& state.getStateType()==State.STATE_TASK_BOT) {
					break;
				}
				n--;
			}
			if(n>=0) {
				if(stateStack.get(n).getStateContent().getIntent()!=null) {
					lastIntent = stateStack.get(n).getStateContent().getIntent();
				}
			}
		}
		return lastIntent;
	}

	
	public State getLastUserState() {
		State lastState = null;
		
		if(!stateStack.isEmpty()) {
			//找到第一个非系统状态
			int n = stateStack.size()-1;
			while(n>=0) {
				State state = stateStack.get(n);
				if(state.getRole()==State.ROLE_USER) {
					break;
				}
				n--;
			}
			if(n>=0) lastState = new State(stateStack.get(n));
		}
		return lastState;
	}
}
