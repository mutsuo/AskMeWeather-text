/**
 * 
 */
package com.hebeishida.bot.module.nlu.intent;

/**
 *@desc:意图接口类
 *@author 邓旸
 *@date:2020年2月6日下午4:32:38
 */
public class Intent {
	//1. 气象类
	public static final String WEATHER = "W01";
	public static final String TEMPERATURE = "W02";
	public static final String TEMPERATURE_RANGE = "W03";
	public static final String HUMIDITY = "W04";
	public static final String VISIBILITY = "W05";
	public static final String WIND_DIR = "W06";
	public static final String WIND_SPEED = "W07";
	public static final String WIND_POWER = "W08";
	
	//2. 生活类
	//2-1. 生活指数
	//空调指数
	public static final String LIFE_AC = "L01";
	//空气扩散条件
	public static final String LIFE_AIR_CONDITION = "L02";
	//晾晒
	public static final String LIFE_AIRING = "L03";
	//过敏
	public static final String LIFE_ALLERGY = "L04";
	//啤酒
	public static final String LIFE_BEER = "L05";
	//划船
	public static final String LIFE_BOATING = "L06";
	//洗车
	public static final String LIFE_CAR_WASHING = "L07";
	//风寒
	public static final String LIFE_CHILL = "L08";
	//舒适度
	public static final String LIFE_COMFORT = "L09";
	//约会指数
	public static final String LIFE_DATING = "L10";
	//穿衣指数
	public static final String LIFE_DRESSING = "L11";
	//钓鱼指数
	public static final String LIFE_FISHING = "L12";
	//感冒指数
	public static final String LIFE_FLU = "L13";
	//美发
	public static final String LIFE_HAIR_DRESSING = "L14";
	//放风筝
	public static final String LIFE_KITE_FLYING = "L15";
	//化妆
	public static final String LIFE_MAKEUP = "L16";
	//心情
	public static final String LIFE_MOOD = "L17";
	//晨练
	public static final String LIFE_MORNING_SPORT = "L18";
	//旅游
	public static final String LIFE_TRAVEL = "L19";
	//雨伞
	public static final String LIFE_UMBRALA = "L20";
	//防晒
	public static final String LIFE_SUNSCREEN = "L21";
	//运动
	public static final String LIFE_SPORT = "L22";
	//购物
	public static final String LIFE_SHOPPING = "L23";
	
	//限行
	public static final String LIFE_RESTRICTION = "X0";
	
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
