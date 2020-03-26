/**
 * 
 */
package com.bot.entity.module.nlu.intent;

/**
 *@desc:意图接口类
 *@author 邓旸
 *@date:2020年2月6日下午4:32:38
 */
public class Intent {
	public static final String WEATHER = "W01";
	public static final String TEMPERATURE = "W02";
	public static final String TEMPERATURE_RANGE = "W03";
	public static final String HUMIDITY = "W04";
	public static final String VISIBILITY = "W05";
	
	private String intentName;
	private String intentId;
	
	public Intent() {
		
	}
	
	public String generateReply() {
		return null;
	}

	public String getIntentName() {
		return intentName;
	}

	public String getIntentId() {
		return intentId;
	}

	public void setIntentName(String intentName) {
		this.intentName = intentName;
	}

	public void setIntentId(String intentId) {
		this.intentId = intentId;
	}
	
	
}
