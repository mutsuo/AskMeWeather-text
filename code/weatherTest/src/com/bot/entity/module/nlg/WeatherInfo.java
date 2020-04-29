/**
 * 
 */
package com.bot.entity.module.nlg;

import java.util.HashMap;
import java.util.Map;

import com.bot.entity.module.nlu.slot.MyDate;

/**
 *@desc:保存查询到的天气信息
 *@author 邓旸
 *@date:2020年4月25日下午5:12:42
 */
public class WeatherInfo {
	//日期
	private MyDate date; 
	//地点
	private String loc;
	//当前气温
	private String nowTem;
	//最高气温
	private String maxTem;
	//最低气温
	private String minTem;
	//气象文字
	private String weather;
	//湿度
	private String humidity;
	//气压
	private String pressure; 
	//风速
	private String windSpeed;
	//风向
	private String windDir;
	//风级
	private String windPower;
	//能见度
	private String visibility;
	private WeatherInfo night;
	private WeatherInfo day;
	//指数
	private Map<String, String> index;
	//车辆限行
	private Map<String, String> restriction;
	
	/**
	 * 
	 */
	public WeatherInfo() {
		date = new MyDate();
		loc = "";
		nowTem = "";
		maxTem = "";
		minTem = "";
		weather = "";
		humidity = "";
		pressure = "";
		windSpeed = "";
		windDir = "";
		windPower = "";
		visibility = "";
		index = new HashMap<String, String>();
		restriction = new HashMap<String, String>();
//		day = new WeatherInfo();
//		night = new WeatherInfo();
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public WeatherInfo getNight() {
		return night;
	}

	public void setNight(WeatherInfo night) {
		this.night = night;
	}

	public WeatherInfo getDay() {
		return day;
	}

	public void setDay(WeatherInfo day) {
		this.day = day;
	}

	public MyDate getDate() {
		return date;
	}

	public void setDate(MyDate date) {
		this.date = date;
	}
	
	public void setDate(String fm) {
		this.date = new MyDate(fm);
	}

	public Map<String, String> getRestriction() {
		return restriction;
	}

	public void setRestriction(Map<String, String> restriction) {
		this.restriction = restriction;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getNowTem() {
		return nowTem;
	}

	public void setNowTem(String nowTem) {
		this.nowTem = nowTem;
	}

	public String getMaxTem() {
		return maxTem;
	}

	public void setMaxTem(String maxTem) {
		this.maxTem = maxTem;
	}

	public String getMinTem() {
		return minTem;
	}

	public void setMinTem(String minTem) {
		this.minTem = minTem;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getWindDir() {
		return windDir;
	}

	public void setWindDir(String windDir) {
		this.windDir = windDir;
	}

	public String getWindPower() {
		return windPower;
	}

	public void setWindPower(String windPower) {
		this.windPower = windPower;
	}

	public Map<String, String> getIndex() {
		return index;
	}

	public void setIndex(Map<String, String> index) {
		this.index = index;
	}
	
	public void initDay() {
		this.day = new WeatherInfo();
	}
	
	public void initNight() {
		this.night = new WeatherInfo();
	}

}
