/**
 * 
 */
package com.bot.entity.module.nlu.intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bot.entity.module.dm.state.State;

/**
 *@desc:一句话被描述
 *@author 邓旸
 *@date:2020年3月23日下午9:15:43
 */
public class BotClassification {
	public static BotClassification instance = new BotClassification();
	private static final String FILE_NAME = "CNNClassification4bot.py";
	private static final String FILE_DIR = "F:/file_temp/";
	private static final String FILE_PATH = FILE_DIR + FILE_NAME;
	private static final String COMPILER_PATH = "H:/Anaconda3/python.exe";
	/**
	 * 
	 */
	private BotClassification() {
		// TODO Auto-generated constructor stub
	}
	
	public static BotClassification getInstance() {
		return instance;
	}
	
	public int classify(String utterance) {
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
		
//		System.out.println(utterance);
		
		if(res.equals("1")) return State.STATE_TASK_BOT;
		else return State.STATE_CHAT_BOT;
	}

}
