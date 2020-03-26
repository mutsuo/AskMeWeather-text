/**
 * 
 */
package com.bot.entity.module.nlu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bot.entity.module.nlu.intent.Intent;
import com.bot.entity.module.nlu.intent.IntentDetector;
import com.bot.entity.module.nlu.intent.IntentHumidity;
import com.bot.entity.module.nlu.intent.IntentTempRange;
import com.bot.entity.module.nlu.intent.IntentTemperature;
import com.bot.entity.module.nlu.intent.IntentVisibility;
import com.bot.entity.module.nlu.intent.IntentWeather;
import com.bot.entity.module.nlu.slot.LOCNode;
import com.bot.entity.module.nlu.slot.Slot;
import com.bot.entity.module.nlu.slot.SlotDATE;
import com.bot.entity.module.nlu.slot.SlotDateDetector;
import com.bot.entity.module.nlu.slot.SlotLocDetector;
import com.bot.entity.util.KeywordsMap;

/**
 *@desc:对话理解模块，为单例模式
 *@author 邓旸
 *@date:2020年2月6日下午4:18:48
 */
public class NLUModule {
	private static NLUModule instance = new NLUModule();
	private IntentDetector intentDetector = IntentDetector.getInstance();
	private SlotDateDetector dateDet = SlotDateDetector.getInstance();
	private SlotLocDetector locDet = SlotLocDetector.getInstance();
	

	/**
	 * 
	 */
	private NLUModule() {
		
	}
	
	/***
	 * 
	 *@desc:单例模式，返回类实例
	 *@return:NLUModule
	 *@trhows
	 */
	public static NLUModule getInstance() {
		return instance;
	}
	
	
	
	/***
	 * 
	 *@desc:意图检测
	 *@param utterance
	 *@return
	 *@return:Intent
	 *@trhows
	 */
	public Intent intentDtection(String utterance) {
		//1. 关键词探测
		Intent intent = intentDetector.intentDetectionKeywords(utterance);
		//2. CNN网络分类
		if(intent==null) {
			intent = intentDetector.intentDetectionCNN(utterance);
		}
		
		return intent;
	}
	
	
	/***
	 * 
	 *@desc:槽填充
	 *@param utterance
	 *@return
	 *@return:Slot
	 *@trhows
	 */
	public Slot slotFilling(String utterance) {
		List<Map<String, SlotDATE>> dateMap = dateDet.start(utterance);

		locDet.init(SlotLocDetector.INIT_AS_XIN_ZHI_TIAN_QI);
		LOCNode loc = locDet.start(utterance);
		
		//去除对含数字地点的误判
		dateMap = amendSlot(dateMap,loc);
		
		Slot slot = new Slot(dateMap, loc);
		
		return slot;
	}

	/**
	 *@desc:一句话描述
	 *@param dateMap
	 *@param locDet2
	 *@return
	 *@return:List<Map<String,SlotDATE>>
	 *@trhows
	 */
	private List<Map<String, SlotDATE>> amendSlot(List<Map<String, SlotDATE>> dateMap, LOCNode loc) {
		List<Map<String, SlotDATE>> recognitionDate = new ArrayList<Map<String, SlotDATE>>();
		for(Map<String, SlotDATE> map: dateMap) {
			Map<String, SlotDATE> newMap = new HashMap<String, SlotDATE>();
			newMap.put("start", map.get("start"));
			if(map.get("end")!=null) newMap.put("end", map.get("end"));
			recognitionDate.add(newMap);
		}
		//去除对含数字地点的误判结果
		if(!loc.getName().equals("ROOT")) {
			String regex = "[一二三四五六七八九零]+";   
			Pattern p = Pattern.compile(regex); 
			Matcher m = p.matcher(loc.getName()); // 获取 matcher 对象
			int day = -1;
			if(m.find()) {
				if(loc.getName().substring(m.start(), m.start()+1).equals("一")) day = 1;
				else if(loc.getName().substring(m.start(), m.start()+1).equals("二")) day = 2;
				else if(loc.getName().substring(m.start(), m.start()+1).equals("三")) day = 3;
				else if(loc.getName().substring(m.start(), m.start()+1).equals("四")) day = 4;
				else if(loc.getName().substring(m.start(), m.start()+1).equals("五")) day = 5;
				else if(loc.getName().substring(m.start(), m.start()+1).equals("六")) day = 6;
				else if(loc.getName().substring(m.start(), m.start()+1).equals("七")) day = 7;
				else if(loc.getName().substring(m.start(), m.start()+1).equals("八")) day = 8;
				else if(loc.getName().substring(m.start(), m.start()+1).equals("九")) day = 9;
				else if(loc.getName().substring(m.start(), m.start()+1).equals("零")) day = 0;
			}
			if(day!=-1) {
				int index = 0;
				for(Map<String, SlotDATE> map: dateMap) {
					if(map.get("start")!=null && map.get("end")==null
							&& map.get("start").getDay()==day) {
						recognitionDate.remove(index);
						break;
					}
					index++;
				}
			}
		}
		
		return recognitionDate;
	}
	
}
