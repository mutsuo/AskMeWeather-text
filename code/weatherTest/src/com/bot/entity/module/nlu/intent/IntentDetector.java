/**
 * 
 */
package com.bot.entity.module.nlu.intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bot.entity.util.KeywordsMap;

/**
 *@desc:一句话被描述
 *@author 邓旸
 *@date:2020年2月13日下午2:46:11
 */
public class IntentDetector {
	private Map<String, ArrayList<String>> keywordMap = new HashMap<String, ArrayList<String>>();
	private static IntentDetector instance = new IntentDetector();
	
	private static final String FILE_NAME = "CNNClassification.py";
	private static final String FILE_DIR = "F:/file_temp/";
	private static final String FILE_PATH = FILE_DIR + FILE_NAME;
	private static final String COMPILER_PATH = "H:/Anaconda3/python.exe";
	
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
		String intentID = "";
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
		
		IntentFactory intentFactory = IntentFactory.getInstance();
		Intent intent = intentFactory.getIntent(intentID);		
		
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
