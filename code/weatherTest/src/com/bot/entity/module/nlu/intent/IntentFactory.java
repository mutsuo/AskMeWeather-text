/**
 * 
 */
package com.bot.entity.module.nlu.intent;

/**
 *@desc:意图工厂类，单例模式
 *@author 邓旸
 *@date:2020年2月13日下午5:33:49
 */
public class IntentFactory {
	private static IntentFactory instance = new IntentFactory();
	/**
	 * 
	 */
	private IntentFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static IntentFactory getInstance() {
		return instance;
	}
	
	public Intent getIntent(String id) {
		Intent intent = null;
		if(id.equals(Intent.WEATHER)) {
			intent = new IntentWeather();
			intent.setIntentId(Intent.WEATHER);
			intent.setIntentName("天气");
		}
		else if(id.equals(Intent.TEMPERATURE)) {
			intent = new IntentTemperature();
			intent.setIntentId(Intent.TEMPERATURE);
			intent.setIntentName("温度");
		}
		else if(id.equals(Intent.TEMPERATURE_RANGE)) {
			intent = new IntentTempRange();
			intent.setIntentId(Intent.TEMPERATURE_RANGE);
			intent.setIntentName("温差");
		}
		else if(id.equals(Intent.HUMIDITY)) {
			intent = new IntentHumidity();
			intent.setIntentId(Intent.HUMIDITY);
			intent.setIntentName("湿度");
		}
		else if(id.equals(Intent.VISIBILITY)) {
			intent = new IntentVisibility();
			intent.setIntentId(Intent.VISIBILITY);
			intent.setIntentName("能见度");
		}
		
		return intent;
	}

}
