/**
 * 
 */
package com.hebeishida.bot.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *@desc:一句话被描述
 *@author 邓旸
 *@date:2020年2月6日下午5:06:07
 */
public class KeywordsMap extends HashMap<String, ArrayList<String>> {
	private static KeywordsMap instance = new KeywordsMap();
	/**
	 *@desc:一句话描述
	 *@Fields:{field}
	 */
	private static final long serialVersionUID = 1L;
	
	public KeywordsMap() {
	}
	
	public void init() {
		ArrayList<String> w01 = new ArrayList<>();
		w01.add("天气");
		instance.put("W01", w01);
		
		ArrayList<String> w02 = new ArrayList<>();
		w02.add("气温");
		w02.add("温度");
		instance.put("W02", w02);
		
		ArrayList<String> w03 = new ArrayList<>();
		w03.add("温差");
		instance.put("W03", w03);
		
		ArrayList<String> w04 = new ArrayList<>();
		w04.add("湿度");
		w04.add("闷热");
		instance.put("W04", w04);
		
		ArrayList<String> w05 = new ArrayList<>();
		w05.add("能见度");
		w05.add("霾");
		instance.put("W05", w05);
	}
	
	public static KeywordsMap getInstance() {
		return instance;
	}
}
