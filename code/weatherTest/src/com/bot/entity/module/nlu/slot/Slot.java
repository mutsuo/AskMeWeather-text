/**
 * 
 */
package com.bot.entity.module.nlu.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@desc:语义槽
 *@author 邓旸
 *@date:2020年2月6日下午6:06:18
 */
public class Slot {
	//一个list中存储若干个时间短语，每个短语用map存储，key为时间类型标记（start/end，时间点的标记为start），value为DATE语义槽实例
	private List<Map<String, SlotDATE>> dateList = new ArrayList<Map<String, SlotDATE>>();
	private LOCNode loc = new LOCNode();
	
	public Slot() {
		
	}
	public Slot(List<Map<String, SlotDATE>> dateList, LOCNode loc) {
		this.dateList = dateList;
		this.loc = loc;
	}
	
	public List<Map<String, SlotDATE>> getDate() {
		return dateList;
	}
	public void setDate(List<Map<String, SlotDATE>> date) {
		this.dateList = date;
	}
	public LOCNode getLoc() {
		return loc;
	}
	public void setLoc(LOCNode loc) {
		this.loc = loc;
	}
	
	public boolean isLocComplited() {
		return loc.isFilled();
	}
	public boolean isDateComplited() {
		boolean flag = false;
		if(dateList.size()>0) {
			for(Map<String, SlotDATE> map:dateList) {
				if(map.get("start")!=null && map.get("start").isFilled()) {
					if(map.get("end")==null) flag = true;
					else if(map.get("end")!=null && map.get("end").isFilled()) flag = true;
					else{
						flag = false;
						break;
					}
				}else {
					flag = false;
					break;
				}
			}
		}else {
			flag = false;
		}
		
		return flag;
	}
}
