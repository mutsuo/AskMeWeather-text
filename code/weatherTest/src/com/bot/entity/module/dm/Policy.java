/**
 * 
 */
package com.bot.entity.module.dm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bot.entity.module.nlu.intent.Intent;
import com.bot.entity.module.nlu.slot.LOCNode;
import com.bot.entity.module.nlu.slot.Slot;
import com.bot.entity.module.nlu.slot.SlotDATE;

/**
 *@desc:对话策略模块，单例模式
 *@author 邓旸
 *@date:2020年2月15日下午12:49:18
 */
public class Policy {
	//-1~5为系统状态
	public final static int STATE_OK = 1;
	public final static int STATE_ILLEGAL = -1;
	
	//6~9为意图相关指令
	public final static int INSTRUCTION_ERROR_INTENT_LOST = 6;
	
	//10~20为语义槽相关指令
	//10~15为日期语义槽相关指令
	public final static int INSTRUCTION_ERROR_DATE_ILLEGAL = 10;
	public final static int INSTRUCTION_ERROR_DATE_DAY_LOST = 11;
	public final static int INSTRUCTION_ERROR_DATE_MONTH_LOST = 12;
	public final static int INSTRUCTION_ERROR_DATE_DAY_AND_MONTH_LOST = 13;
	public final static int INSTRUCTION_ERROR_DATE_LOST = 14;	//日期语义槽不存在
	public final static int INSTRUCTION_ERROR_DATE_UNSUPPORT = 15; //日期不支持（是过去日期）
	
	//16~19为地点语义槽相关指令
	public final static int INSTRUCTION_ERROR_LOC_LOST = 16;
	public final static int INSTRUCTION_ERROR_LOC_UNSUPPORT = 17;
	
	public final static int INSTRUCTION_ERROR_DATE_AND_LOC_LOST = 20;
	
	//其它指令
	public final static int INSTRUCTION_CHAT_API = 21;
	
	private static Policy instance = new Policy();
	

	/**
	 * 
	 */
	private Policy() {
	}
	
	public static Policy getInstance() {
		return instance;
	}
	
	/**
	 * 
	 *@desc:判断
	 *@param slot
	 *@return
	 *@return:int
	 *@trhows
	 */
	public int isSlotLegal(Slot slot) {
		int instr = -1;
		List<Map<String, SlotDATE>> dateList = slot.getDate();
		
		for(Map<String, SlotDATE> map: dateList) {
			if(map.get("start")!=null && 
					map.get("start").isLegal()
					) {
				if(this.isSupportedDate(map.get("start"))) {
					instr = STATE_OK;					
				}else {
					instr = INSTRUCTION_ERROR_DATE_UNSUPPORT;
					break;
				}
			}else if(map.get("start")!=null){
				instr = INSTRUCTION_ERROR_DATE_ILLEGAL;
				break;
			}
			if(map.get("end")!=null && map.get("end").isLegal()) {
				if(this.isSupportedDate(map.get("end"))) {
					instr = STATE_OK;					
				}else {
					instr = INSTRUCTION_ERROR_DATE_UNSUPPORT;
					break;
				}
			}else if(map.get("end")!=null){
				instr = INSTRUCTION_ERROR_DATE_ILLEGAL;
				break;
			}
		}
		
		return instr;
	}
	
	public boolean isPastDate(SlotDATE date) {
		boolean flag = false;
		
		int[] daySum = {-1,31,-1,31,30,31,30,31,31,30,31,30,31};
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String[] nowDate = ft.format(dNow).split("-");
		if(date.getYear() == Integer.parseInt(nowDate[0])
				&& date.getMonth() >= Integer.parseInt(nowDate[1])
				&& date.getDay() >= Integer.parseInt(nowDate[2])) {
		}else {
			flag = true;
		}
		
		return flag;
	}

	/**
	 * 
	 *@desc:判断日期是否支持：1.是否为过去日期；2.是否未来三天内
	 *@param date
	 *@return
	 *@return:boolean
	 *@trhows
	 */
	public boolean isSupportedDate(SlotDATE date) {
		boolean flag = false;
		//1. 判断是否为过去日期
		if(!this.isPastDate(date)) {
			//2. 检查是否在支持范围内
			int[] daySum = {-1,31,-1,31,30,31,30,31,31,30,31,30,31};
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String[] nowDate = ft.format(dNow).split("-");
			int delt = -1;
			if(date.getMonth()>Integer.parseInt(nowDate[1])) {
				delt = daySum[Integer.parseInt(nowDate[1])] - Integer.parseInt(nowDate[2]) + date.getDay();
			}else {
				delt = date.getDay() - Integer.parseInt(nowDate[2]);
			}
			if(delt>=0 && delt<=6) {
				flag = true;
			}
		}
		
		return flag;
	}
	
	public int isDateFilled(SlotDATE date) {
		int instr = -1;
		if(date.getDay()==-1 && date.getMonth()==-1) instr = INSTRUCTION_ERROR_DATE_DAY_AND_MONTH_LOST;
		else {
			if(date.getDay()==-1) instr = INSTRUCTION_ERROR_DATE_DAY_LOST;
			else if(date.getMonth()==-1) instr = INSTRUCTION_ERROR_DATE_MONTH_LOST;
			else instr = STATE_OK;
		}
		
		return instr;
	}
	
	/**
	 * 
	 *@desc:判断DATE语义槽是否完整，并返回指令
	 *@param dateList
	 *@return
	 *@return:int
	 *@trhows
	 */
	public int isDateComplited(List<Map<String, SlotDATE>> dateList) {
		/*
		 * date.year和date.month使用当前值填充，理论上不会出现缺失情况 
		 */
		int instr = -1;
		if(dateList.size()>0) {
			int index = 0;
			for(Map<String, SlotDATE> map:dateList) {
				int startInstr = -1;
				int endInstr = -1;
				if(map.get("start")!=null) {
					startInstr = isDateFilled(map.get("start"));
					if(map.get("end")==null) endInstr = STATE_OK;
					else if(map.get("end")!=null) {
						endInstr = isDateFilled(map.get("end")); 
					}
				}else {
				}
				if(startInstr!=STATE_OK || endInstr!=STATE_OK) {
					//若终止时间不完整，直接按照查找单日处理
					if(startInstr!=STATE_OK && endInstr==STATE_OK) {
						instr = startInstr;
						break;
					}else {
						dateList.get(index).put("end", null);
					}
				}
				index++;
			}
			if(instr==-1) instr = STATE_OK;
		}else {
			instr = INSTRUCTION_ERROR_DATE_LOST;
		}
		
		return instr;
	}
	
	/**
	 * 
	 *@desc:判断LOC语义槽是否完整，并返回指令
	 *@param loc
	 *@return
	 *@return:int
	 *@trhows
	 */
	public int isLocComplited(LOCNode loc) {
		int instr = -1;
		if(loc.isFilled()) {
			instr = STATE_OK;
		}else {
//			if(loc.getIndex()==0) instr = INSTRUCTION_ERROR_LOC_UNSUPPORT;
//			else instr = INSTRUCTION_ERROR_LOC_LOST;
			instr = INSTRUCTION_ERROR_LOC_LOST;
		}
		
		return instr;
	}
	
	public int isSlotComplited(Slot slot) {
		int instr = -1;
		int dateInstr = this.isDateComplited(slot.getDate());
		int locInstr = this.isLocComplited(slot.getLoc()); 
		if(dateInstr==STATE_OK && locInstr==STATE_OK) {
			instr = STATE_OK;
		}else {
			if(dateInstr!=STATE_OK && locInstr!=STATE_OK) instr = INSTRUCTION_ERROR_DATE_AND_LOC_LOST;
			else if(dateInstr!=STATE_OK) instr = dateInstr;
			else if(locInstr!=STATE_OK) instr = locInstr;
		}
		
		return instr;
	}

	/**
	 *@desc:一句话描述
	 *@param intent
	 *@param lastIntent
	 *@return
	 *@return:int
	 *@trhows
	 */
	public int isIntentFilled(Intent intent, Intent lastIntent) {
		int instr = -1;
		if(intent!=null || intent==null && lastIntent!=null) instr = STATE_OK;
		else instr = INSTRUCTION_ERROR_INTENT_LOST;
		return instr;
	}
	
}
