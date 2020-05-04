/**
 * 
 */
package com.hebeishida.bot.module.nlu.slot;

/**
 *@desc:一句话被描述
 *@author 邓旸
 *@date:2020年4月25日下午5:18:33
 */
public class MyDate {
	private int day;
	private int month;
	private int year;
	/**
	 * 
	 */
	public MyDate() {
		day = -1;
		month = -1;
		year = -1;
	}
	public MyDate(String fm) {
		String[] dateS = fm.split("-");
		this.year = Integer.parseInt(dateS[0]);
		this.month = Integer.parseInt(dateS[1]);
		this.day = Integer.parseInt(dateS[2]);
	}
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * 
	 *@desc:输出“yyyy-MM-dd”格式的日期字符串
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String getFormatedDate() {
		String d = "";
		String m = "";
		
		if(this.day==-1) {
			d = "01";
		}else if(this.day < 10) {
			d = "0" + this.day;
		}else {
			d = "" + this.day;
		}
		
		if(this.month==-1) {
			m = "01";
		}else if(this.month < 10) {
			m = "0" + this.month;
		}else {
			m = "" + this.month;
		}
		
		return this.year + "-" + m + "-" + d;
	}

}
