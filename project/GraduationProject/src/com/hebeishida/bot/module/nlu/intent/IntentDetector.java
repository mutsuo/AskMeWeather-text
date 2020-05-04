/**
 * 
 */
package com.hebeishida.bot.module.nlu.intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hebeishida.bot.util.KeywordsMap;

/**
 *@desc:一句话被描述
 *@author 邓旸
 *@date:2020年2月13日下午2:46:11
 */
public class IntentDetector {
	private Map<String, ArrayList<String>> keywordMap = new HashMap<String, ArrayList<String>>();
	private static IntentDetector instance = new IntentDetector();
	
	private static final String FILE_NAME = "CNNClassification.py";
	private static final String FILE_ROOT = "F:";
	private static final String FILE_DIR = FILE_ROOT +"/file_temp/";
	private static final String FILE_PATH = FILE_DIR + FILE_NAME;
	private static final String COMPILER_PATH = "H:/Anaconda3/python.exe";
	
	private static Logger logger = Logger.getLogger(IntentDetector.class);
	
	private IntentDetector() {
		initKeywordMap();
	}
	public void initKeywordMap() {
		ArrayList<String> w01 = new ArrayList<>();
		w01.add("天气");
		keywordMap.put("W01", w01);
		
		ArrayList<String> w02 = new ArrayList<>();
		w02.add("气温");
		w02.add("温度");
		keywordMap.put("W02", w02);
		
		ArrayList<String> w03 = new ArrayList<>();
		w03.add("温差");
		keywordMap.put("W03", w03);
		
		ArrayList<String> w04 = new ArrayList<>();
		w04.add("湿度");
		w04.add("闷热");
		keywordMap.put("W04", w04);
		
		ArrayList<String> w05 = new ArrayList<>();
		w05.add("能见度");
		w05.add("霾");
		keywordMap.put("W05", w05);
		
		ArrayList<String> w06 = new ArrayList<>();
		w06.add("风向");
		keywordMap.put("W06", w06);
		
		ArrayList<String> w07 = new ArrayList<>();
		w07.add("风速");
		keywordMap.put("W07", w07);
		
		ArrayList<String> w08 = new ArrayList<>();
		w08.add("风力");
		w08.add("风");
		keywordMap.put("W08", w08);
		
		ArrayList<String> l01 = new ArrayList<>();
		l01.add("空调");
		keywordMap.put("L01", l01);
		
		ArrayList<String> l02 = new ArrayList<>();
		l02.add("空气");
		keywordMap.put("L02", l02);
		
		ArrayList<String> l03 = new ArrayList<>();
		l03.add("晾晒");
		keywordMap.put("L03", l03);
		
		ArrayList<String> l04 = new ArrayList<>();
		l04.add("过敏");
		keywordMap.put("L04", l04);
		
		ArrayList<String> l05 = new ArrayList<>();
		l05.add("酒");
		keywordMap.put("L05", l05);
		
		ArrayList<String> l06 = new ArrayList<>();
		l06.add("划船");
		l06.add("艇");
		l06.add("船");
		keywordMap.put("L06", l06);
		
		ArrayList<String> l07 = new ArrayList<>();
		l07.add("洗车");
		keywordMap.put("L07", l07);
		
		ArrayList<String> l08 = new ArrayList<>();
		l08.add("风寒");
		l08.add("冷");
		l08.add("寒");
		keywordMap.put("L08", l08);
		
		ArrayList<String> l09 = new ArrayList<>();
		l09.add("舒适");
		l09.add("舒服");
		l09.add("感觉");
		keywordMap.put("L09", l09);
		
		ArrayList<String> l10 = new ArrayList<>();
		l10.add("约会");
		l10.add("玩");
		keywordMap.put("L10", l10);
		
		ArrayList<String> l11 = new ArrayList<>();
		l11.add("穿");
		l11.add("衣服");
		keywordMap.put("L11", l11);
		
		ArrayList<String> l12 = new ArrayList<>();
		l12.add("钓鱼");
		l12.add("垂钓");
		keywordMap.put("L12", l12);
		
		ArrayList<String> l13 = new ArrayList<>();
		l13.add("感冒");
		l13.add("着凉");
		l13.add("船");
		keywordMap.put("L13", l13);
		
		ArrayList<String> l14 = new ArrayList<>();
		l14.add("美发");
		l14.add("发廊");
		l14.add("剪头发");
		l14.add("Tonny");
		l14.add("Tonny老师");
		l14.add("托尼老师");
		keywordMap.put("L14", l14);
		
		ArrayList<String> l15 = new ArrayList<>();
		l15.add("放风筝");
		keywordMap.put("L15", l15);
		
		ArrayList<String> l16 = new ArrayList<>();
		l16.add("化妆");
		keywordMap.put("L16", l16);
		
		ArrayList<String> l17 = new ArrayList<>();
		l17.add("心情");
		keywordMap.put("L17", l17);
		
		ArrayList<String> l18 = new ArrayList<>();
		l18.add("晨练");
		keywordMap.put("L18", l18);
		
		ArrayList<String> l19 = new ArrayList<>();
		l19.add("旅游");
		l19.add("游玩");
		keywordMap.put("L19", l19);
		
		ArrayList<String> l20 = new ArrayList<>();
		l20.add("伞");
		l20.add("下雨");
		l20.add("有雨");
		keywordMap.put("L20", l20);
		
		ArrayList<String> l21 = new ArrayList<>();
		l21.add("紫外线");
		l21.add("防晒");
		keywordMap.put("L21", l21);
		
		ArrayList<String> l22 = new ArrayList<>();
		l22.add("运动");
		l22.add("锻炼");
		keywordMap.put("L22", l22);
		
		ArrayList<String> l23 = new ArrayList<>();
		l23.add("购物");
		l23.add("买东西");
		l23.add("逛街");
		l23.add("买买买");
		l23.add("囤货");
		keywordMap.put("L23", l23);
		
		ArrayList<String> x0 = new ArrayList<String>();
		x0.add("限行");
		x0.add("开车");
		keywordMap.put("X0", x0);
	}
	
	public static IntentDetector getInstance() {
		return instance;
	}
	
	/***
	 * 
	 *@desc:基于关键词的意图检测
	 *@param utterance
	 *@return
	 *@return:Intent
	 *@trhows
	 */
	public Intent intentDetectionKeywords(String utterance) {
		logger.debug("detect by keywords...");
		String intentID = "";
		Intent intent = null;
		for(Map.Entry<String, ArrayList<String>> entry : keywordMap.entrySet()) {
			for(int i = 0;i<entry.getValue().size();i++) {
				if(utterance.indexOf(entry.getValue().get(i))!=-1) {
					intentID = entry.getKey();
					break;
				}
			}
			if(!intentID.equals("")) {
				break;
			}
		}
		if(!intentID.equals("")) {
			IntentFactory intentFactory = IntentFactory.getInstance();
			intent = intentFactory.getIntent(intentID);		
		}
		
		return intent;
	}
	
	/***
	 * 
	 *@desc:基于CNN的意图分类
	 *@param utterance
	 *@return
	 *@return:Intent
	 *@trhows
	 */
	public Intent intentDetectionCNN(String utterance) {
		String intentId = classificationCNN(utterance);		
		Intent intent = IntentFactory.getInstance().getIntent(intentId);
		
		return intent;
	}
	
	/***
	 * 
	 *@desc:用CNN网络进行分类：隐式意图
	 *@param utterance
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String classificationCNN(String utterance) {
		logger.debug("detect by CNN...");
		String res = "";
		try {
		    String[] args = new String[] { COMPILER_PATH, FILE_PATH, utterance};
		    Process proc = Runtime.getRuntime().exec(args);// 执行py文件
		 
		    BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		    String line = null;
		    while ((line = in.readLine()) != null) {
		        res = line;
//		        System.out.println("line:"+line);
		    }
		    in.close();
		    proc.waitFor();
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		return res;
	}
}
