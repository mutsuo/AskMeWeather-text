/**
 * 
 */
package com.bot.entity.module.nlg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bot.entity.module.nlu.intent.Intent;
import com.bot.entity.module.nlu.slot.Slot;
import com.bot.entity.module.nlu.slot.SlotDATE;
import com.bot.entity.util.HttpUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *@desc:天气信息查询，单例模式
 *@author 邓旸
 *@date:2020年2月14日下午1:12:11
 */
public class WeatherService {
	private static final String APPKEY = "377115eba4920e4c";// 你的appkey
    private static final String URL = "https://api.jisuapi.com/weather/query";
    
    private static WeatherService instance = new WeatherService();

	/**
	 * 
	 */
	private WeatherService() {
		// TODO Auto-generated constructor stub
	}
	
	public static WeatherService getInstance() {
		return instance;
	}
	
	/**
	 * 
	 *@desc:一句话描述
	 *@param exType
	 *@param resultarr
	 *@param slot
	 *@return
	 *@return:Map<String,Map<String,String>> 日期，(key,value)
	 *@trhows
	 */
	public Map<String, Map<String, String>> queryExtraction(String exType, JSONObject resultarr, Slot slot){
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		List<Map<String, SlotDATE>> dateList = slot.getDate();
		//查询今日天气
		if(dateList.size()==1 && dateList.get(0).get("end")==null
				&& dateList.get(0).get("start").getFormatedDate().equals(resultarr.getString("date"))) {
			Map<String, String> tMap = new HashMap<String, String>();
			if(exType.equals(Intent.WEATHER)) {
				tMap.put("weather", resultarr.getString("weather"));
				tMap.put("temphigh", resultarr.getString("temphigh"));
				tMap.put("templow", resultarr.getString("templow"));
				tMap.put("temp", resultarr.getString("temp"));
				tMap.put("index", ((JSONObject)resultarr.optJSONArray("index").get(0)).getString("detail"));
				map.put(dateList.get(0).get("start").getFormatedDate(), tMap);				
			}
			else if(exType.equals(Intent.TEMPERATURE)) {
				tMap.put("temphigh", resultarr.getString("temphigh"));
				tMap.put("templow", resultarr.getString("templow"));
				tMap.put("index", ((JSONObject)resultarr.optJSONArray("index").get(0)).getString("detail"));
				map.put(dateList.get(0).get("start").getFormatedDate(), tMap);
			}
			else if(exType.equals(Intent.TEMPERATURE_RANGE)){
				tMap.put("tempRange", ""+(Integer.parseInt(resultarr.getString("temphigh"))-Integer.parseInt(resultarr.getString("templow"))));
				tMap.put("index", ((JSONObject)resultarr.optJSONArray("index").get(0)).getString("detail"));
				map.put(dateList.get(0).get("start").getFormatedDate(), tMap);
			}else {
				
			}
		}
		//查询多日日期 或 非今日单日日期
		else if (resultarr.opt("daily") != null) {
			JSONArray daily = resultarr.optJSONArray("daily");
			String[] todayDate = ((JSONObject)daily.opt(0)).getString("date").split("-");
			for(Map<String, SlotDATE> dateMap: dateList) {
				//时间点
				if(dateMap.get("start")!=null && dateMap.get("end")==null) {
					//计算index
					String[] tDate = dateMap.get("start").getFormatedDate().split("-");
					int delt = Integer.parseInt(tDate[2]) - Integer.parseInt(todayDate[2]);
					JSONObject obj = (JSONObject) daily.opt(delt);
					
					Map<String, String> tMap = new HashMap<String, String>();
					if (obj.opt("day") != null) {
						if(exType.equals(Intent.WEATHER)) {
							tMap.put("weather_day", ((JSONObject)obj.opt("day")).getString("weather"));
							tMap.put("templow_day", ((JSONObject)obj.opt("day")).getString("temphigh"));							
						}
						else if(exType.equals(Intent.TEMPERATURE)) {
							tMap.put("templow_day", ((JSONObject)obj.opt("day")).getString("temphigh"));							
						}else if(exType.equals(Intent.TEMPERATURE_RANGE)){
							tMap.put("tempRange", ((JSONObject)obj.opt("day")).getString("temphigh"));
						}else {
							
						}
					}
					if (obj.opt("night") != null) {
						if(exType.equals(Intent.WEATHER)) {
							tMap.put("weather_night", ((JSONObject)obj.opt("night")).getString("weather"));
							tMap.put("templow_night", ((JSONObject)obj.opt("night")).getString("templow"));
						}
						else if(exType.equals(Intent.TEMPERATURE)) {
							tMap.put("templow_night", ((JSONObject)obj.opt("night")).getString("templow"));							
						}else if(exType.equals(Intent.TEMPERATURE_RANGE)){
							if(tMap.get("tempRange")!=null) {
								int dayTemp = Integer.parseInt(tMap.get("tempRange"));
								int nightTemp = Integer.parseInt(((JSONObject)obj.opt("night")).getString("templow"));
								tMap.put("tempRange", ""+(dayTemp>nightTemp?dayTemp-nightTemp:nightTemp-dayTemp));								
							}else {
								tMap.put("tempRange", ((JSONObject)obj.opt("night")).getString("templow"));
							}
						}else {
							
						}
					}
					map.put(dateMap.get("start").getFormatedDate(), tMap);
				}
				//时间段
				else if(dateMap.get("start")!=null && dateMap.get("end")!=null) {
					int startIndex = Integer.parseInt(dateMap.get("start").getFormatedDate().split("-")[2])
										- Integer.parseInt(todayDate[2]);
					int endIndex = Integer.parseInt(dateMap.get("end").getFormatedDate().split("-")[2])
							- Integer.parseInt(todayDate[2]);
					for(int i = startIndex;i<=endIndex;i++) {
						JSONObject obj = (JSONObject) daily.opt(i);
						Map<String, String> tMap = new HashMap<String, String>();
						if (obj.opt("day") != null) {
							if(exType.equals(Intent.WEATHER)) {
								tMap.put("weather_day", ((JSONObject)obj.opt("day")).getString("weather"));
								tMap.put("templow_day", ((JSONObject)obj.opt("day")).getString("temphigh"));							
							}
							else if(exType.equals(Intent.TEMPERATURE)) {
								tMap.put("templow_day", ((JSONObject)obj.opt("day")).getString("temphigh"));							
							}else if(exType.equals(Intent.TEMPERATURE_RANGE)){
								tMap.put("tempRange", ((JSONObject)obj.opt("day")).getString("temphigh"));
							}else {
								
							}
						}
						if (obj.opt("night") != null) {
							if(exType.equals(Intent.WEATHER)) {
								tMap.put("weather_night", ((JSONObject)obj.opt("night")).getString("weather"));
								tMap.put("templow_night", ((JSONObject)obj.opt("night")).getString("templow"));
							}
							else if(exType.equals(Intent.TEMPERATURE)) {
								tMap.put("templow_night", ((JSONObject)obj.opt("night")).getString("templow"));							
							}else if(exType.equals(Intent.TEMPERATURE_RANGE)){
								if(tMap.get("tempRange")!=null) {
									int dayTemp = Integer.parseInt(tMap.get("tempRange"));
									int nightTemp = Integer.parseInt(((JSONObject)obj.opt("night")).getString("templow"));
									tMap.put("tempRange", ""+(dayTemp>nightTemp?dayTemp-nightTemp:nightTemp-dayTemp));								
								}else {
									tMap.put("tempRange", ((JSONObject)obj.opt("night")).getString("templow"));
								}
							}else {
								
							}
						}
    					map.put(obj.getString("date"), tMap);
					}
				}
			}
			
		}
		return map;
	}
	/**
	 *@desc:一句话描述
	 *@param resultarr
	 *@param slot
	 *@return
	 *@return:Map<String,Map<String,String>>
	 *@trhows
	 */
	public Map<String, Map<String, String>> queryTemp(JSONObject resultarr, Slot slot) {
		
		return null;
	}

	public Map<String, Map<String, String>> apiQuery(Intent intent, Slot slot) throws Exception{
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		
		String city = slot.getLoc().getName();
		String result = null;
		String url = URL + "?appkey=" + APPKEY + "&city=" + URLEncoder.encode(city, "utf-8");
		
		result = HttpUtil.sendGet(url, "utf-8");
        JSONObject json = JSONObject.fromObject(result);
        if (json.getInt("status") != 0) {
        	map = new HashMap<String, Map<String, String>>();
        }else {
        	JSONObject resultarr = json.optJSONObject("result");
//        	System.out.println("intent.getIntentId():"+intent.getIntentId());
        	if(intent.getIntentId().equals(Intent.WEATHER)) {
        		map = queryExtraction(Intent.WEATHER, resultarr, slot);        		
        	}
        	else if(intent.getIntentId().equals(Intent.TEMPERATURE)) map = queryExtraction(Intent.TEMPERATURE, resultarr, slot);
        	else if(intent.getIntentId().equals(Intent.TEMPERATURE_RANGE)) map = queryExtraction(Intent.TEMPERATURE_RANGE, resultarr, slot);
        	else if(intent.getIntentId().equals(Intent.VISIBILITY)) map = queryExtraction(Intent.VISIBILITY, resultarr, slot);
        	else if(intent.getIntentId().equals(Intent.HUMIDITY))map = queryExtraction(Intent.HUMIDITY, resultarr, slot);
        }
        
		return map;
	}

}
