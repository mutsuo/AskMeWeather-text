/**
 * 
 */
package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bot.entity.module.nlu.NLUModule;
import com.bot.entity.module.nlu.slot.LOCNode;
import com.bot.entity.module.nlu.slot.Slot;
import com.bot.entity.module.nlu.slot.SlotDATE;

/**
 *@desc:一句话被描述
 *@author 邓旸
 *@date:2020年3月9日下午5:38:35
 */
public class SlotFillingTest {

	/**
	 *@desc:一句话描述
	 *@param args
	 *@return:void
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 *@trhows
	 */
	public static void main(String[] args) throws UnsupportedEncodingException, Exception {
		String path = "F:/file_temp/";
		String fileName1 = "携程问答数据.csv";
		String fileName2 = "slotFillingRes1.csv";
		List<String> utterances = new ArrayList<String>();
		InputStreamReader isr = new InputStreamReader(new FileInputStream(path+fileName1), "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		while((s = br.readLine())!=null) {
			utterances.add(s.split(",")[0]);
		}
		br.close();
		isr.close();
		
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path+fileName2), "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		NLUModule nlu = NLUModule.getInstance();
		StringBuffer buffer = new StringBuffer();
		for(String utterance: utterances) {
			Slot slot = nlu.slotFilling(utterance);
			List<Map<String, SlotDATE>> date = slot.getDate();
			LOCNode loc = slot.getLoc();
			
			buffer.append(utterance);
			buffer.append(",");
			
			int flag = 1;
			for(Map<String, SlotDATE> map: date) {
				if(flag==1) {
					flag = 0;
				}else {
					buffer.append(" ");
				}
				buffer.append(map.get("start").getFormatedDate());
				if(map.get("end")!=null) {
					buffer.append("~");
					buffer.append(map.get("end").getFormatedDate());
				}
			}
			if(flag==1) buffer.append("缺失");
			buffer.append(",");
			if(!loc.getName().equals("ROOT"))buffer.append(loc.getName());
			else buffer.append("缺失");
			buffer.append("\n");
		}
		bw.write(buffer.toString());
	}

}
