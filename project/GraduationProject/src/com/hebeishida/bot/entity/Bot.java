/**
 * 
 */
package com.hebeishida.bot.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hebeishida.bot.module.dm.DMModule;
import com.hebeishida.bot.module.dm.Policy;
import com.hebeishida.bot.module.dm.state.State;
import com.hebeishida.bot.module.nlg.NLGModule;
import com.hebeishida.bot.module.nlu.NLUModule;
import com.hebeishida.bot.module.nlu.intent.BotClassification;
import com.hebeishida.bot.module.nlu.intent.Intent;
import com.hebeishida.bot.module.nlu.slot.Slot;
import com.hebeishida.bot.module.nlu.slot.SlotDATE;

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
	
	static Logger logger = Logger.getLogger(Bot.class);
	
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
		logger.debug("user utterance:"+utterance);
		
		//1. NLU Module
		//1-1. Recognize the field
//		int type = BotClassification.getInstance().classify(utterance);
		int type = State.STATE_TASK_BOT;
		logger.debug("bot classification finished");
		if(type == State.STATE_TASK_BOT) {
			logger.debug("--> "+"TASK_BOT");
			
			
			//1-3. Slot Filling
			Slot slot = nlu.slotFilling(utterance);
			logger.debug("Slot Filling finished");
			logger.debug("--> loc:"+slot.getLoc().getName());
			for(Map<String, SlotDATE> map: slot.getDate()) {
				if(map!=null) {
					if(map.get("start")!=null && map.get("end")!=null) {
						logger.debug("--> date: from "+map.get("start").getFormatedDate()+" to "+map.get("end").getFormatedDate());
					}else if(map.get("start")!=null) {
						logger.debug("--> date: "+map.get("start").getFormatedDate());
					}
				}
			}
			
			//1-2. Intent Detection
			Intent intent = null;
			intent = dm.intentAmending(slot, intent, utterance);
			if(intent == null)	intent = nlu.intentDtection(utterance);
			logger.debug("Intent Detection finished");
			logger.debug("--> "+intent.getIntentName());
			
			//2. DM Module
			//2-1. get DM policy
			int instr = dm.solve(State.ROLE_USER, State.STATE_TASK_BOT, utterance, -1, intent, slot);
			logger.debug("DM has solved the input --> instrusion="+instr);
			if(instr == Policy.STATE_OK) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("loc: ");
				buffer.append(slot.getLoc().getName());
				buffer.append("\ndate: ");
				for(Map<String, SlotDATE> map: slot.getDate()) {
					if(map!=null) {
						if(map.get("start")!=null && map.get("end")!=null) {
							buffer.append("from "+map.get("start").getFormatedDate()+" to "+map.get("end").getFormatedDate());
						}else if(map.get("start")!=null) {
							buffer.append("date: "+map.get("start").getFormatedDate());
						}
					}
				}
				logger.debug(buffer.toString());
			}
			
			//3. NLG Module
			//3-1. get reply
			State lastUserState = dm.getLastUserState();
			if(lastUserState!=null)	replyList = nlg.solve(
					instr, 
					utterance, 
					lastUserState.getStateContent().getIntent(),
					lastUserState.getStateContent().getSlot());
			logger.debug("replys have been generated");
			for(String reply: replyList) {
				logger.debug("reply: "+reply);
			}
			
			//3-2. create new(bot) state
			for(String rawUtterance: replyList) {
				dm.solve(State.ROLE_BOT, State.STATE_TASK_BOT, rawUtterance, instr, null, null);				
			}
			logger.debug("new state (for robot) has been created");
			
		}else {
			logger.debug("--> "+"CHAT_BOT");
			//2. DM Module
			//2-1. get DM policy
			int instr = dm.solve(State.ROLE_USER, State.STATE_CHAT_BOT, utterance, -1, null, null);
			logger.debug("DM have solved the input --> instrusion="+instr);
			
			//3. NLG Module
			//3-1. get reply
			replyList = nlg.solve(instr, utterance, null, null);
			logger.debug("replys have been generated");
			for(String reply: replyList) {
				logger.debug("reply: "+reply);
			}
			
			//3-2. create new(bot) state
			for(String rawUtterance: replyList) {
				dm.solve(State.ROLE_BOT, State.STATE_TASK_BOT, rawUtterance, instr, null, null);				
			}
			logger.debug("new state (for robot) has been created");
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
