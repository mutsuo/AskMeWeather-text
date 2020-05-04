/**
 * 
 */
package com.hebeishida.bot.module.nlg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.log4j.Logger;

import com.hebeishida.bot.module.dm.Policy;
import com.hebeishida.bot.module.nlu.intent.Intent;
import com.hebeishida.bot.module.nlu.slot.Slot;
import com.hebeishida.bot.util.HttpUtil;

import net.sf.json.JSONObject;

/**
 *@desc:对话生成模块，单例模式
 *@author 邓旸
 *@date:2020年2月15日下午8:00:13
 */
public class NLGModule {
	private static NLGModule instance = new NLGModule();
	public static Logger logger = Logger.getLogger(NLGModule.class);
	/**
	 * 
	 */
	private NLGModule() {
	}
	
	public static NLGModule getInstance() {
		return instance;
	}

	public List<String> solve(int instr, String utterance, Intent intent, Slot slot) throws Exception {
		List<String> reply = new ArrayList<String>();
		switch(instr) {
		case Policy.STATE_OK:
			reply = taskBasedCommonReply(intent, slot);
			break;
		case Policy.STATE_ILLEGAL:
			reply.add(this.safeReply());
			break;
		case Policy.INSTRUCTION_CHAT_API:
			reply.add(replyByBotApi(utterance));
			break;
		case Policy.INSTRUCTION_ERROR_INTENT_LOST:
			reply.add(intentLostReply());
			break;
		case Policy.INSTRUCTION_ERROR_DATE_ILLEGAL:
			//不认识
			reply.add(dateIllegalReply());
			break;
		case Policy.INSTRUCTION_ERROR_LOC_UNSUPPORT:
			//不支持
			reply.add(locUnsupportReply());
			break;
		case Policy.INSTRUCTION_ERROR_DATE_DAY_LOST:
			reply.add(dayLostReply(slot));
			break;
		case Policy.INSTRUCTION_ERROR_DATE_MONTH_LOST:
			reply.add("INSTRUCTION_ERROR_DATE_MONTH_LOST");
			break;
		case Policy.INSTRUCTION_ERROR_DATE_DAY_AND_MONTH_LOST:
			reply.add("INSTRUCTION_ERROR_DATE_DAY_AND_MONTH_LOST");
			break;
		case Policy.INSTRUCTION_ERROR_DATE_UNSUPPORT:
			reply.add(this.dateUnsupportedReply());
			break;
		case Policy.INSTRUCTION_ERROR_DATE_LOST:
			reply.add(this.dateLostReply(intent));
			break;
		case Policy.INSTRUCTION_ERROR_DATE_AND_LOC_LOST:
			reply.add(dateAndLocLostReply(intent));
			break;
		case Policy.INSTRUCTION_ERROR_LOC_LOST:
			reply.add(locLostReply(intent));
			break;
		}
		
		return reply;
	}
	
	/**
	 *@desc:一句话描述
	 *@param intent
	 *@return
	 *@return:String
	 *@trhows
	 */
	private String dateLostReply(Intent intent) {
		StringBuffer reply = new StringBuffer();
		reply.append("查哪天的");
		reply.append(intent.getIntentName());
		reply.append("呀");
		return reply.toString();
	}

	/**
	 * 
	 *@desc:安全回复
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String safeReply() {
		String[] replys = {"原来如此","噢","这样啊"};
		Random rand = new Random();
		return replys[rand.nextInt(replys.length-1)];
	}

	public String unsupportedReply(String date, String intentName) {
		StringBuffer sb = new StringBuffer();
		sb.append("抱歉，我这里没有");
		String recentDate = isRecent(date); 
		if(recentDate.equals("")) sb.append(date);
		else sb.append(recentDate);
		sb.append("的");
		sb.append(intentName);
		sb.append("数据");
		return sb.toString();
	}
	/***
	 * 
	 *@desc:问候
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String greeting() {
		String[] replys = {"你好呀\\(@^0^@)/","哈喽~"};
		Random rand = new Random();
		return replys[rand.nextInt(replys.length-1)];
	}

	/**
	 *@desc:一句话描述
	 *@return
	 *@return:String
	 *@trhows
	 */
	private String intentLostReply() {
		String[] replys = {"请问是要询问什么吗？","我不太明白你的意思……","你是在问我问题吗？","那个，我不太懂你的意思"};
		Random rand = new Random();
		return replys[rand.nextInt(replys.length-1)];
	}

	/**
	 *@desc:Date语义槽缺失day时的回复
	 *@param slot
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String dayLostReply(Slot slot) {
		StringBuffer reply = new StringBuffer(); 
		if(slot.getDate().size()==1 && slot.getDate().get(0).get("end")==null) {
			reply.append(slot.getDate().get(0).get("start").getMonth());
			reply.append("月的哪一天呢");
		}else {
			Random rand = new Random();
			switch(rand.nextInt(1)) {
			case 0:
				reply.append("几号呢？");
				break;
			case 1:
				reply.append("请告诉我具体的日期……");
				break;
			}
		}
		return reply.toString();
	}

	/**
	 *@desc:给出DATE不合法时的回复
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String dateIllegalReply() {
		String[] replys = {"你在逗我吧，日历上根本没有这一天！","几月几号？麻烦你再说一遍？","你说的这个日期……好像不太符合常理"};
		Random rand = new Random();
		
		return replys[rand.nextInt(replys.length-1)];
	}

	/**
	 *@desc:过去日期的回答
	 *@return
	 *@return:String
	 *@trhows
	 */
	private String dateUnsupportedReply() {
		String[] replys = {"抱歉，我不记得了……","我查不到过去的天气……"};
		Random rand = new Random();
		return replys[rand.nextInt(replys.length-1)];
	}

	/**
	 *@desc:LOC语义槽丢失时的回复
	 *@param intent
	 *@return
	 *@return:String
	 *@trhows
	 */
	private String locLostReply(Intent intent) {
		StringBuffer reply = new StringBuffer();
		Random rand = new Random();
		switch(rand.nextInt(1)) {
		case 0:
			reply.append("想要查询哪里的");
			reply.append(intent.getIntentName());
			reply.append("？");
			break;
		case 1:
			reply.append("请告诉我要查询的地点XD");
			break;
		}
		return reply.toString();
	}

	/**
	 *@desc:DATE和LOC语义槽都缺失的回复
	 *@param intent
	 *@return
	 *@return:String
	 *@trhows
	 */
	private String dateAndLocLostReply(Intent intent) {
		StringBuffer reply = new StringBuffer();
		Random rand = new Random();
		switch(rand.nextInt(1)) {
		case 0:
			reply.append("嗯？你要查什么地方的");
			reply.append(intent.getIntentName());
			break;
		case 1:
			reply.append("你想查几号的");
			reply.append(intent.getIntentName());
			reply.append("呀");
			break;
		}
		return reply.toString();
	}

	/**
	 *@desc:给出LOC不支持时的回复
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String locUnsupportReply() {
		String[] replys = {"抱歉……我不认识你说的这个地方","抱歉，你说的这个地方是哪？","什么，你说哪？"};
		Random rand = new Random();
		
		return replys[rand.nextInt(replys.length-1)];
	}

	public String isRecent(String date) {
		String recen = "";
		String[] dayInfo = date.split("-");
		Date dNow = new Date();
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String[] todayDate = fm.format(dNow).split("-");
		if(dayInfo[0].equals(todayDate[0]) && dayInfo[1].equals(todayDate[1]) && dayInfo[2].equals(todayDate[2])) {
			recen = "今天";
		}else if(dayInfo[0].equals(todayDate[0]) && dayInfo[1].equals(todayDate[1]) 
				&& Integer.parseInt(dayInfo[2])==Integer.parseInt(todayDate[2])+1) {
			recen = "明天";
		}else if(dayInfo[0].equals(todayDate[0]) && dayInfo[1].equals(todayDate[1]) 
				&& Integer.parseInt(dayInfo[2])==Integer.parseInt(todayDate[2])+2) {
			recen = "后天";
		}
		
		return recen;
	}

	/***
	 * 
	 *@desc:网络故障回复
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String netErrorReply() {
		return "抱歉，由于网络故障，无法接收到响应";
	}
	
	public List<String> taskBasedCommonReply(Intent intent, Slot slot) throws Exception {
			WeatherService service = WeatherService.getInstance();
			List<WeatherInfo> list = service.apiQuery(WeatherService.XIN_ZHI_TIAN_QI, intent, slot);
			List<String> replys = null;
			
			if(list == null) {
				replys.add(netErrorReply());
			}else {
				if(intent.getIntentId().equals(Intent.WEATHER)) replys = weatherReply(slot, list);
				else if(intent.getIntentId().equals(Intent.TEMPERATURE)) replys = tempReply(slot, list);
				else if(intent.getIntentId().equals(Intent.TEMPERATURE_RANGE)) replys = tempRangeReply(slot, list);
				else if(intent.getIntentId().charAt(0)=='L') replys = lifeIndexReply(slot, list);
				else if(intent.getIntentId().charAt(0)=='X') replys = restrictionReply(slot, list);
				else replys = otherReply(slot, list, intent);
			}
			
			
			return replys;
		}
		
		/**
	 *@desc:一车辆限行
	 *@param slot
	 *@param list
	 *@return
	 *@return:List<String>
	 *@trhows
	 */
	private List<String> restrictionReply(Slot slot, List<WeatherInfo> list) {
		List<String> replys = new ArrayList<String>();
		if(list.size()==0) replys.add("没有相关的限行信息，可以自由出行");
		for(WeatherInfo wi: list) {
			String date = isRecent(wi.getDate().getFormatedDate());
			if(date.equals("")) date = wi.getDate().getMonth()+"月"+wi.getDate().getDay()+"日";
			
			String loc = slot.getLoc().getName();
			
			StringBuffer sb = new StringBuffer();
			if(wi.getRestriction().size()==0) {
				sb.append("没有相关的限行信息，可以自由出行");
			}else {
				sb.append(loc);
				sb.append(date);
				sb.append("的车辆限行信息如下：\n");
				sb.append(wi.getRestriction().get("penalty"));
				sb.append("\n限行范围为");
				sb.append(wi.getRestriction().get("region"));
				sb.append("\n");
				if(wi.getRestriction().get("remark")!=null) {
					sb.append(wi.getRestriction().get("remark"));
					sb.append("\n");
				}
				sb.append(wi.getRestriction().get("memo"));
				if(wi.getRestriction().get("plates")!=null) {
					sb.append("：");
					sb.append(wi.getRestriction().get("plates"));
				}
			}
			replys.add(sb.toString());
		}
		
		return replys;
	}

		/**
	 *@desc:生活指数
	 *@param slot
	 *@param list
	 *@return
	 *@return:List<String>
	 *@trhows
	 */
	private List<String> lifeIndexReply(Slot slot, List<WeatherInfo> list) {
		List<String> replys = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		
		if(list.get(0).getIndex().get("detail")!=null) {
			sb.append(slot.getLoc().getName());
			sb.append("今天");
			sb.append(list.get(0).getIndex().get("detail"));
			replys.add(sb.toString());
		}
		
		return replys;
	}

		/**
	 *@desc: 温差
	 *@param slot
	 *@param list
	 *@return
	 *@return:List<String>
	 *@trhows
	 */
	public List<String> tempRangeReply(Slot slot, List<WeatherInfo> list) {
		List<String> replys = new ArrayList<String>();
		
		for(WeatherInfo wi: list) {
			String date = isRecent(wi.getDate().getFormatedDate());
			if(date.equals("")) date = wi.getDate().getMonth()+"月"+wi.getDate().getDay()+"日";
			
			String loc = slot.getLoc().getName();
			
			int tempRange = -1;
			
			String tempHigh = wi.getMaxTem();
			String tempLow = wi.getMinTem();
			
			if(!tempHigh.equals("") && !tempLow.equals("")) {
				tempRange = Integer.parseInt(tempHigh) - Integer.parseInt(tempLow);
				if(tempRange > 0) {
					StringBuffer sb = new StringBuffer();
					sb.append(loc);
					sb.append(date);
					sb.append("的温差为");
					sb.append(tempRange);
					sb.append("摄氏度");
					
					replys.add(sb.toString());
				}else {
					replys.add(unsupportedReply(date, "温差"));
				}
			}else {
				replys.add(unsupportedReply(date, "温差"));
			}
			
		}
		return replys;
	}

		/**
				 *@desc: 任务型，语义槽完整时的回复
				 *@param intent
				 *@param slot
				 *@return
				 *@return:String
				 * @throws Exception 
				 *@trhows
				 */
		//		public List<String> taskBasedCommonReply1(Intent intent, Slot slot) throws Exception {
		//			WeatherService service = WeatherService.getInstance();
		//			List<WeatherInfo> list = service.apiQuery(WeatherService.JISU, intent, slot);
		//			
		//			List<String> replys = new ArrayList<String>();
		//			Random rand = new Random();
		//			for(Entry<String, Map<String, String>> entry: map.entrySet()) {
		//				String date = isRecent(entry.getKey());
		//				if(date.equals("") && entry.getKey()!=null) {
		//					String[] d = entry.getKey().split("-"); 
		//					date = d[1]+"月"+d[2]+"日";
		//				}
		//				
		//				String loc = slot.getLoc().getName();
		//				
		//				String weather = "";
		//				if(entry.getValue().get("weather")!=null) weather = entry.getValue().get("weather");
		//				else{
		//					if(entry.getValue().get("weather_day")!=null && entry.getValue().get("weather_night")!=null) {
		//						weather = entry.getValue().get("weather_day") + "转" +entry.getValue().get("weather_night");					
		//					}else {
		//						if(entry.getValue().get("weather_day")!=null) weather = entry.getValue().get("weather_day");
		//						if(entry.getValue().get("weather_night")!=null) weather = entry.getValue().get("weather_night");
		//					}
		//				}
		//				
		//				String tempLow = "";
		//				if(entry.getValue().get("templow")!=null) tempLow = entry.getValue().get("templow");
		//							
		//				String tempHigh = "";
		//				if(entry.getValue().get("temphigh")!=null) tempHigh = entry.getValue().get("templow");
		//				
		//				String tempDay = "";
		//				if(entry.getValue().get("templow_day")!=null) tempDay = entry.getValue().get("templow_day");
		//				
		//				String tempNight = "";
		//				if(entry.getValue().get("templow_night")!=null) tempNight = entry.getValue().get("templow_night");
		//				
		//				String index = "";
		//				if(entry.getValue().get("index")!=null) index = entry.getValue().get("index");
		//				
		//				StringBuffer buffer = new StringBuffer();
		//				buffer.append(loc);
		//				buffer.append(date);
		//				buffer.append(weather);
		//				buffer.append(",");
		//				if(!tempHigh.equals("") && !tempLow.equals("")) {
		//					buffer.append(tempLow);
		//					buffer.append("℃到");
		//					buffer.append(tempHigh);
		//					buffer.append("℃");
		//				}else {
		//					if(!tempHigh.equals("")) {
		//						buffer.append("最高温为");
		//						buffer.append(tempHigh);
		//						buffer.append("℃");
		//					}
		//					else if(!tempLow.equals("")) {
		//						buffer.append("最低温为");
		//						buffer.append(tempLow);
		//						buffer.append("℃");
		//					}
		//					else if(!tempDay.equals("") && !tempNight.equals("")) {
		//						buffer.append("白天最低");
		//						buffer.append(tempDay);
		//						buffer.append("摄氏度，");
		//						buffer.append("夜间最低");
		//						buffer.append(tempNight);
		//						buffer.append("摄氏度");
		//					}else {
		//						int highTemp = -1, lowTemp = -1;
		//						if(!tempDay.equals("")) {
		//							highTemp = Integer.parseInt(tempDay);
		//	//						buffer.append("白天最低");
		//	//						buffer.append(tempDay);
		//	//						buffer.append("摄氏度");
		//						}else if(!tempNight.equals("")){
		//							lowTemp = Integer.parseInt(tempNight);
		//	//						buffer.append("夜间最低");
		//	//						buffer.append(tempNight);
		//	//						buffer.append("摄氏度");
		//						}
		//						if(highTemp!=-1 && lowTemp!=-1) {
		//							if(highTemp < lowTemp) {
		//								int t = highTemp;
		//								highTemp = lowTemp;
		//								lowTemp = t;
		//							}
		//							buffer.append(lowTemp);
		//							buffer.append("至");
		//							buffer.append(highTemp);
		//							buffer.append("摄氏度");
		//						}
		//					}
		//				}
		//				buffer.append("\n");
		//				if(!index.equals("")) buffer.append(index);
		//				
		//				replys.add(buffer.toString());
		//			}
		//			
		//			
		//			return replys;
		//		}
				
	public List<String> weatherReply(Slot slot, List<WeatherInfo> list){
		List<String> replys = new ArrayList<String>();
		for(WeatherInfo wi: list) {
			String date = isRecent(wi.getDate().getFormatedDate());
			if(date.equals("")) date = wi.getDate().getMonth()+"月"+wi.getDate().getDay()+"日";
			
			String loc = slot.getLoc().getName();
			
			String weather = "";
			if(!wi.getWeather().equals("")) weather = wi.getWeather();
			else{
				if(!wi.getDay().getWeather().equals("") && !wi.getNight().getWeather().equals("")) {
					if(!wi.getDay().getWeather().equals(wi.getNight().getWeather()))
						weather = wi.getDay().getWeather() + "转" + wi.getNight().getWeather();
					else weather = wi.getDay().getWeather();
				}else {
					if(!wi.getDay().getWeather().equals("")) weather = wi.getDay().getWeather();
					if(!wi.getNight().getWeather().equals("")) weather = wi.getNight().getWeather();
				}
			}
			
			String tempLow = wi.getMinTem();
			String tempHigh = wi.getMaxTem();
			
			String tempDay = "";
			if(wi.getDay()!=null) wi.getDay().getMaxTem();
			
			String tempNight = "";
			if(wi.getNight()!=null) wi.getNight().getMinTem();
			
//				String index = "";
//				if(entry.getValue().get("index")!=null) index = entry.getValue().get("index");
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(loc);
			buffer.append(date);
			buffer.append(weather);
			buffer.append(",");
			if(!wi.getNowTem().equals("")) {
				buffer.append(wi.getNowTem());
				buffer.append("摄氏度");
			}
			else if(!tempHigh.equals("") && !tempLow.equals("")) {
				if(!tempHigh.equals(tempLow)) {
					buffer.append(tempLow);
					buffer.append("℃到");
					buffer.append(tempHigh);
					buffer.append("℃");
				}else {
					buffer.append(tempLow);
					buffer.append("℃");
				}
				
			}else {
				if(!tempHigh.equals("")) {
					buffer.append("最高温为");
					buffer.append(tempHigh);
					buffer.append("℃");
				}
				else if(!tempLow.equals("")) {
					buffer.append("最低温为");
					buffer.append(tempLow);
					buffer.append("℃");
				}
				else if(!tempDay.equals("") && !tempNight.equals("")) {
					buffer.append("白天最低");
					buffer.append(tempDay);
					buffer.append("摄氏度，");
					buffer.append("夜间最低");
					buffer.append(tempNight);
					buffer.append("摄氏度");
				}else {
					int highTemp = -1, lowTemp = -1;
					if(!tempDay.equals("")) {
						highTemp = Integer.parseInt(tempDay);
//						buffer.append("白天最低");
//						buffer.append(tempDay);
//						buffer.append("摄氏度");
					}else if(!tempNight.equals("")){
						lowTemp = Integer.parseInt(tempNight);
//						buffer.append("夜间最低");
//						buffer.append(tempNight);
//						buffer.append("摄氏度");
					}
					if(highTemp!=-1 && lowTemp!=-1) {
						if(highTemp < lowTemp) {
							int t = highTemp;
							highTemp = lowTemp;
							lowTemp = t;
						}
						buffer.append(lowTemp);
						buffer.append("至");
						buffer.append(highTemp);
						buffer.append("摄氏度");
					}else {
						if(highTemp!=-1) {
							buffer.append(highTemp);
							buffer.append("摄氏度");
						}else if(lowTemp!=-1) {
							buffer.append(lowTemp);
							buffer.append("摄氏度");
						}
					}
				}
			}
//				buffer.append("\n");
//				if(!index.equals("")) buffer.append(index);
			
			replys.add(buffer.toString());
		}
		
		return replys;
	}

	/***
	 * 
	 *@desc:其它气象数据
	 *@param slot
	 *@param list
	 *@return
	 *@return:List<String>
	 *@trhows
	 */
	public List<String> otherReply(Slot slot, List<WeatherInfo> list, Intent intent){
		List<String> replys = new ArrayList<String>();
		for(WeatherInfo wi: list) {
			String date = isRecent(wi.getDate().getFormatedDate());
			if(date.equals("")) date = wi.getDate().getMonth()+"月"+wi.getDate().getDay()+"日";
			
			String data = "";
			if(intent.getIntentName().equals(Intent.VISIBILITY)) data = wi.getVisibility();
			else if(intent.getIntentName().equals(Intent.HUMIDITY)) data = wi.getHumidity();
			else if(intent.getIntentName().equals(Intent.WIND_DIR)) data = wi.getWindDir();
			else if(intent.getIntentName().equals(Intent.WIND_SPEED)) data = wi.getWindSpeed();
			else if(intent.getIntentName().equals(Intent.WIND_POWER)) data = wi.getWindPower();
			
			if(data.equals("")) {
				replys.add(this.unsupportedReply(wi.getDate().getFormatedDate(), intent.getIntentName()));
			}else {
				StringBuffer sb = new StringBuffer();
				String loc = "";
				loc = slot.getLoc().getName();
				
				sb.append(loc);
				sb.append(date);
				sb.append("的");
				sb.append(intent.getIntentName());
				sb.append("为");
				sb.append(data);
				
				replys.add(sb.toString());
			}
		}
		
		return replys;
	}
	
	/***
	 * 
	 *@desc:温度
	 *@param slot
	 *@param list
	 *@return
	 *@return:List<String>
	 *@trhows
	 */
	public List<String> tempReply(Slot slot, List<WeatherInfo> list){
		List<String> replys = new ArrayList<String>();
		
		for(WeatherInfo wi: list) {
			String date = isRecent(wi.getDate().getFormatedDate());
			if(date.equals("")) date = wi.getDate().getMonth()+"月"+wi.getDate().getDay()+"日";
			
			String loc = "";
			loc = slot.getLoc().getName();
			
			String tempHigh = "";
			if(!wi.getMaxTem().equals("")) tempHigh = wi.getMaxTem();
			
			String tempLow = "";
			if(!wi.getMinTem().equals("")) tempLow = wi.getMinTem();
			
			String tempDay = "";
			if(wi.getDay()!=null && !wi.getDay().getMaxTem().equals("")) tempDay = wi.getDay().getMaxTem();
			
			String tempNight = "";
			if(wi.getNight()!=null && !wi.getNight().getMinTem().equals("")) tempNight = wi.getNight().getMinTem();
			
			String tempRange = "";
			if(!tempHigh.equals("") && !tempLow.equals("")) tempRange = tempLow + "至" + tempHigh + "摄氏度";
			else if(!tempDay.equals("") && !tempNight.equals("")) tempRange = tempNight + "至" + tempDay + "摄氏度";
			
			StringBuffer sb = new StringBuffer();
			sb.append(loc);
			sb.append(date);
			sb.append("的温度为");
			sb.append(tempRange);
			sb.append("。");
			if(!wi.getNowTem().equals("")) {
				sb.append("\n当前温度为");
				sb.append(wi.getNowTem());
				sb.append("摄氏度");
			}
			replys.add(sb.toString());
		}
		
		return replys;
	}
		
	/**
	 * 
	 *@desc:调用api创建闲聊应答
	 *@param utterance
	 *@return
	 *@throws Exception
	 *@return:String
	 *@trhows
	 */
	public String replyByBotApi(String utterance) throws Exception {
		String reply = "";
		String url = "http://api.qingyunke.com/api.php?key=free&appid=0&msg="+URLEncoder.encode(utterance,"UTF-8");
		try {
			String result = HttpUtil.sendGet(url, "utf-8");
			JSONObject json = JSONObject.fromObject(result);
			if(json.getInt("result")!=0) reply = safeReply();
			else reply = json.getString("content");
		}catch (Exception e) {
		}
		
		return reply;
	}
	
}
