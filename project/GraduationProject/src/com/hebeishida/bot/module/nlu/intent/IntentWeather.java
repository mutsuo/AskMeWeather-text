/**
 * 
 */
package com.hebeishida.bot.module.nlu.intent;

/**
 *@desc:天气意图
 *@author 邓旸
 *@date:2020年2月6日下午4:38:55
 */
public class IntentWeather extends Intent {
	private String intentId = WEATHER;
	private String intentName = "天气";
	/**
	 * 
	 */
	public IntentWeather() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.bot.entity.intent.Intent#generateReply()
	 */
	@Override
	public String generateReply() {
		
		return null;
	}
}
