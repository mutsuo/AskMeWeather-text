/**
 * 
 */
package com.bot.entity.module.nlg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.bot.entity.module.dm.Policy;
import com.bot.entity.module.nlu.intent.Intent;
import com.bot.entity.module.nlu.slot.Slot;
import com.bot.entity.util.HttpUtil;

import net.sf.json.JSONObject;

/**
 *@desc:对话生成模块，单例模式
 *@author 邓旸
 *@date:2020年2月15日下午8:00:13
 */
public class NLGModule {
	private static NLGModule instance = new NLGModule();
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
		}
		
		return recen;
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
		public List<String> taskBasedCommonReply(Intent intent, Slot slot) throws Exception {
			WeatherService service = WeatherService.getInstance();
			Map<String, Map<String, String>> map = service.apiQuery(intent, slot);
			
			List<String> replys = new ArrayList<String>();
			Random rand = new Random();
			for(Entry<String, Map<String, String>> entry: map.entrySet()) {
				String date = isRecent(entry.getKey());
				if(date.equals("") && entry.getKey()!=null) {
					String[] d = entry.getKey().split("-"); 
					date = d[1]+"月"+d[2]+"日";
				}
				
				String loc = slot.getLoc().getName();
				
				String weather = "";
				if(entry.getValue().get("weather")!=null) weather = entry.getValue().get("weather");
				else{
					if(entry.getValue().get("weather_day")!=null && entry.getValue().get("weather_night")!=null) {
						weather = entry.getValue().get("weather_day") + "转" +entry.getValue().get("weather_night");					
					}else {
						if(entry.getValue().get("weather_day")!=null) weather = entry.getValue().get("weather_day");
						if(entry.getValue().get("weather_night")!=null) weather = entry.getValue().get("weather_night");
					}
				}
				
				String tempLow = "";
				if(entry.getValue().get("templow")!=null) tempLow = entry.getValue().get("templow");
							
				String tempHigh = "";
				if(entry.getValue().get("temphigh")!=null) tempHigh = entry.getValue().get("templow");
				
				String tempDay = "";
				if(entry.getValue().get("templow_day")!=null) tempDay = entry.getValue().get("templow_day");
				
				String tempNight = "";
				if(entry.getValue().get("templow_night")!=null) tempNight = entry.getValue().get("templow_night");
				
				String index = "";
				if(entry.getValue().get("index")!=null) index = entry.getValue().get("index");
				
				StringBuffer buffer = new StringBuffer();
				buffer.append(loc);
				buffer.append(date);
				buffer.append(weather);
				buffer.append(",");
				if(!tempHigh.equals("") && !tempLow.equals("")) {
					buffer.append(tempLow);
					buffer.append("℃到");
					buffer.append(tempHigh);
					buffer.append("℃");
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
						}
					}
				}
				buffer.append("\n");
				if(!index.equals("")) buffer.append(index);
				
				replys.add(buffer.toString());
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
