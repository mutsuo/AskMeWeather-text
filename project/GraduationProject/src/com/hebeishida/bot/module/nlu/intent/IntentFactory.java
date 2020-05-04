/**
 * 
 */
package com.hebeishida.bot.module.nlu.intent;

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
		if(id.equals("")) return null;
		Intent intent = new Intent();
		intent.setIntentId(id);
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
		}else if(id.equals(Intent.WIND_DIR)) {
//			intent = new Intent();
//			intent.setIntentId(id);
			intent.setIntentName("风向");
		}else if(id.equals(Intent.WIND_POWER)) {
			intent.setIntentName("风速");
		}else if(id.equals(Intent.LIFE_AC)) {
			intent.setIntentName("空调指数");
		}else if(id.equals(Intent.LIFE_AIR_CONDITION)) {
			intent.setIntentName("空气扩散条件");
		}else if(id.equals(Intent.LIFE_AIRING)) {
			intent.setIntentName("晾晒指数");
		}else if(id.equals(Intent.LIFE_ALLERGY)) {
			intent.setIntentName("过敏指数");
		}else if(id.equals(Intent.LIFE_BEER)) {
			intent.setIntentName("啤酒指数");
		}else if(id.equals(Intent.LIFE_BOATING)) {
			intent.setIntentName("划船指数");
		}else if(id.equals(Intent.LIFE_CAR_WASHING)) {
			intent.setIntentName("洗车指数");
		}else if(id.equals(Intent.LIFE_CHILL)) {
			intent.setIntentName("风寒指数");
		}else if(id.equals(Intent.LIFE_COMFORT)) {
			intent.setIntentName("舒适度");
		}else if(id.equals(Intent.LIFE_DATING)) {
			intent.setIntentName("约会指数");
		}else if(id.equals(Intent.LIFE_DRESSING)) {
			intent.setIntentName("穿衣指数");
		}else if(id.equals(Intent.LIFE_FISHING)) {
			intent.setIntentName("钓鱼指数");
		}else if(id.equals(Intent.LIFE_FLU)) {
			intent.setIntentName("感冒指数");
		}else if(id.equals(Intent.LIFE_HAIR_DRESSING)) {
			intent.setIntentName("美发指数");
		}else if(id.equals(Intent.LIFE_KITE_FLYING)) {
			intent.setIntentName("放风筝");
		}else if(id.equals(Intent.LIFE_MAKEUP)) {
			intent.setIntentName("化妆指数");
		}else if(id.equals(Intent.LIFE_MOOD)) {
			intent.setIntentName("心情指数");
		}else if(id.equals(Intent.LIFE_MORNING_SPORT)) {
			intent.setIntentName("晨练指数");
		}else if(id.equals(Intent.LIFE_TRAVEL)) {
			intent.setIntentName("旅游指数");
		}else if(id.equals(Intent.LIFE_UMBRALA)) {
			intent.setIntentName("雨伞指数");
		}else if(id.equals(Intent.LIFE_SUNSCREEN)) {
			intent.setIntentName("防晒指数");
		}else if(id.equals(Intent.LIFE_SPORT)) {
			intent.setIntentName("运动指数");
		}else if(id.equals(Intent.LIFE_SHOPPING)) {
			intent.setIntentName("购物指数");
		}else if(id.equals(Intent.LIFE_RESTRICTION)) {
			intent.setIntentName("车辆限行");
		}
		
		return intent;
	}

}
