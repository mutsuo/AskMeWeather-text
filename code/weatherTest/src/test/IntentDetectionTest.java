/**
 * 
 */
package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.bot.entity.Bot;
import com.bot.entity.module.nlu.NLUModule;
import com.bot.entity.module.nlu.intent.Intent;

/**
 *@desc:一句话被描述
 *@author 邓旸
 *@date:2020年3月9日下午3:10:36
 */
public class IntentDetectionTest {
	public static void main(String[] args) throws Exception {
		String path = "F:/file_temp/";
		String fileName = "携程问答数据.csv";
		Map<String,String> intentTest = new HashMap<String, String>();
		InputStreamReader isr = new InputStreamReader(new FileInputStream(path+fileName), "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		while((s = br.readLine())!=null) {
			intentTest.put(s.split(",")[0], s.split(",")[1]);
		}
		
		NLUModule nlu = NLUModule.getInstance();
		int[] res_t = {0,0,0,0,0};
		int[] res_f = {0,0,0,0,0};
		int t = 0;
		double n = 0.0;
		for(Map.Entry<String, String> entry: intentTest.entrySet()) {
			Intent testRes = nlu.intentDtection(entry.getKey());
			if(testRes==null) {
				System.out.println(entry.getKey());
				continue;
			}
//			System.out.println(entry.getKey());
//			System.out.println("test:"+testRes.getIntentId());
//			System.out.println("intent:"+entry.getValue());
//			System.out.println();
			if(testRes.getIntentId().equals(entry.getValue())) {
//				System.out.println(entry.getValue().charAt(entry.getValue().length()-1)-'0'-1);
				res_t[entry.getValue().charAt(entry.getValue().length()-1)-'0'-1]++;
				t++;
			}else {
				res_f[testRes.getIntentId().charAt(testRes.getIntentId().length()-1)-'0'-1]++;
			}
			n++;
		}
		for(int i = 0;i<5;i++) System.out.print(res_t[i]+" ");
		System.out.println();
		for(int i = 0;i<5;i++) System.out.print(res_f[i]+" ");
		System.out.println();
		
//		double n = (double)intentTest.size();
		double precision = 0, recall = 0,f1 = 0;
		for(int i = 0;i<5;i++) {
			double tn = 1.0 * res_t[i];
			double fn = 1.0 * res_f[i];
			double tp = 0.0;
			double fp = 0.0;
			for(int j = 0;j<5;j++) {
				if(j==i) continue;
				else {
					tp += 1.0 * res_t[j];
					fp += 1.0 * res_f[j];
				}
			}
			precision += tp / (tp+fp);
			recall += tp / (tp+fn);
		}		        
        precision = 1.0*precision/5;
        recall = 1.0*recall/5;
        f1 = 2*(precision*recall / (precision+recall));
		
        System.out.println("accuracy:"+1.0*t/n);
        System.out.println("precision:"+precision);
        System.out.println("recall:"+recall);
        System.out.println("f1-score:"+f1);
	}
}
