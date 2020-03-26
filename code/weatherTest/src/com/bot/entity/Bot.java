/**
 * 
 */
package com.bot.entity;

import java.util.ArrayList;
import java.util.List;

import com.bot.entity.module.dm.DMModule;
import com.bot.entity.module.dm.state.State;
import com.bot.entity.module.nlg.NLGModule;
import com.bot.entity.module.nlu.NLUModule;
import com.bot.entity.module.nlu.intent.BotClassification;
import com.bot.entity.module.nlu.intent.Intent;
import com.bot.entity.module.nlu.slot.Slot;

/**
 *@desc:机器人外壳
 *@author 邓旸
 *@date:2020年2月6日下午4:20:58
 */
public class Bot {
	private static Bot instance = new Bot();
	private NLUModule nlu = NLUModule.getInstance();
	private DMModule dm = DMModule.getInstance();
	private NLGModule nlg = NLGModule.getInstance();
	
	private String name = "小天";
	private Integer age = 18;
	private String gender = "female";
	
	/**
	 * 
	 */
	private Bot() {
	}
	
	public static Bot getInstance() {
		return instance;
	}

	public String greeting() {
		return nlg.greeting();
	}

	public List<String> start(String utterance) throws Exception {
		List<String> replyList = new ArrayList<String>();
		//0. init
		
		//1. NLU Module
		//1-1. Recognize the field
		int type = BotClassification.getInstance().classify(utterance);
		if(type == State.STATE_TASK_BOT) {
			//1-2. Intent Detection
			Intent intent = nlu.intentDtection(utterance);
			//1-3. Slot Filling
			Slot slot = nlu.slotFilling(utterance);
			
			//2. DM Module
			//2-1. get DM policy
			int instr = dm.solve(State.ROLE_USER, State.STATE_TASK_BOT, utterance, -1, intent, slot);
			
			//3. NLG Module
			//3-1. get reply
			State lastUserState = dm.getLastUserState();
			if(lastUserState!=null)	replyList = nlg.solve(
					instr, 
					utterance, 
					lastUserState.getStateContent().getIntent(),
					lastUserState.getStateContent().getSlot());
			//3-2. create new(bot) state
			for(String rawUtterance: replyList) {
				dm.solve(State.ROLE_BOT, State.STATE_TASK_BOT, rawUtterance, instr, null, null);				
			}
		}else {
			//2. DM Module
			//2-1. get DM policy
			int instr = dm.solve(State.ROLE_USER, State.STATE_CHAT_BOT, utterance, -1, null, null);
			
			//3. NLG Module
			//3-1. get reply
			replyList = nlg.solve(instr, utterance, null, null);
			//3-2. create new(bot) state
			for(String rawUtterance: replyList) {
				dm.solve(State.ROLE_BOT, State.STATE_TASK_BOT, rawUtterance, instr, null, null);				
			}
		}
		
		return replyList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public static void setInstance(Bot instance) {
		Bot.instance = instance;
	}
}
