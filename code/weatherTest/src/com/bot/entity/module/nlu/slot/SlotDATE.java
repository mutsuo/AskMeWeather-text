/**
 * 
 */
package com.bot.entity.module.nlu.slot;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *@desc:语义槽信息
 *@author 邓旸
 *@date:2020年2月6日下午5:56:19
 */
public class SlotDATE {
//	private int date.getDay();
//	private int month;
//	private int year;
	private MyDate date;
	private int startIndex;	//语义槽在原始语料中的起始位置
	private int endIndex;	//语义槽在原始语料中的结束位置
	private String rawString; //当且仅当语义槽为相对时间短语时，启用该属性
	
	public SlotDATE() {
		this.date = new MyDate();
		this.startIndex = -1;
		this.endIndex = -1;
		this.rawString = "";
	}
	
	public SlotDATE(int day,int month,int year) {
		this.date = new MyDate();
		this.date.setDay(day);
		this.date.setMonth(month);
		this.date.setYear(year);
		this.startIndex = -1;
		this.endIndex = -1;
		this.rawString = "";
	}
	
	public boolean isFilled() {
		boolean flag = false;
		if(this.date.getDay()!=-1 && this.date.getMonth()!=-1 && this.date.getYear()!=-1) {
			flag = true;
		}
			
		return flag;
	}
	
	/***
	 * 
	 *@desc:将表示日期的字符串转化为数字型
	 *@param s
	 *@return
	 *@return:int
	 *@trhows
	 */
	public int toNum(String s) {
		int num = 0;
		for(int i = 0;i<s.length();i++) {
			if(s.charAt(i)>='0' && s.charAt(i)<='9') {
				num*=10;
				num+=s.charAt(i)-'0';
			}else {
				if(i>0 && s.charAt(i-1)!='十') num*=10;
				switch(s.charAt(i)) {
				case '一':num+=1;break;
				case '二':num+=2;break;
				case '三':num+=3;break;
				case '四':num+=4;break;
				case '五':num+=5;break;
				case '六':num+=6;break;
				case '七':num+=7;break;
				case '八':num+=8;break;
				case '九':num+=9;break;
				case '十':if(i==0)num = 10;break;
				}				
			}
		}
		return num;
	}
	
	/***
	 * 用含有日期的字符串初始化
	 * @param rawDate
	 */
	public SlotDATE(String rawDate) {
		this.date = new MyDate(); 
		
		String regex = "[年月日号]|[\\.-/\\\\]";
		String[] dateElems = rawDate.split(regex);
		
		if(rawDate.indexOf("年")!=-1 || rawDate.indexOf("月")!=-1 || rawDate.indexOf("日")!=-1 || rawDate.indexOf("号")!=-1) {
			int index = 0;
			if(rawDate.indexOf("年")!=-1) {
				while(index<dateElems.length && dateElems[index]=="") index++;
				this.date.setYear(toNum(dateElems[index]));
				index++;
			}
			if(rawDate.indexOf("月")!=-1) {
				if(index>=dateElems.length) index = 0;
				while(index<dateElems.length && dateElems[index]=="") index++;
				this.date.setMonth(toNum(dateElems[index]));
				index++;
			}
			if(rawDate.indexOf("日")!=-1 || rawDate.indexOf("号")!=-1) {
				if(index>=dateElems.length) index = 0;
				while(index<dateElems.length && dateElems[index]=="") index++;
				this.date.setDay(toNum(dateElems[index]));
				index++;
			}
			if(index<dateElems.length) {
				while(index<dateElems.length && dateElems[index]=="") index++;
				this.date.setDay(toNum(dateElems[index]));
			}
		}else {
			for(int i = dateElems.length-1 ; i >= 0; i--) {
				if(!dateElems[i].equals("")) {
					if(dateElems[i].length() < 4) {
						if(this.date.getDay()==-1) {
							this.date.setDay(toNum(dateElems[i]));						
						}else if(this.date.getMonth()==-1) {
							this.date.setMonth(toNum(dateElems[i]));
						}else if(this.date.getYear()==-1){
							if(dateElems[i].length()==1) {
								this.date.setYear(toNum("200"+dateElems[i]));							
							}
							if(dateElems[i].length()==2) {
								this.date.setYear(toNum("20"+dateElems[i]));							
							}
							if(dateElems[i].length()==3) {
								this.date.setYear(toNum("2"+dateElems[i].substring(1, 3)));
							}
						}
					}else{
						this.date.setYear(toNum(dateElems[i].substring(dateElems[i].length()-4,dateElems[i].length())));
					}
				}
			}
			
		}
		
		this.startIndex = -1;
		this.endIndex = -1;
		this.rawString = rawDate;
	}
	
	public int getDay() {
		return date.getDay();
	}
	public void setDay(int day) {
		this.date.setDay(day);
	}
	public int getMonth() {
		return this.date.getMonth();
	}
	public void setMonth(int month) {
		this.date.setMonth(month);
	}
	public int getYear() {
		return this.date.getYear();
	}
	public void setYear(int year) {
		this.date.setYear(year);
	}
	
	
	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	

	public String getRawString() {
		return rawString;
	}

	public void setRawString(String rawString) {
		this.rawString = rawString;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + date.getDay();
		result = prime * result + endIndex;
		result = prime * result + date.getMonth();
		result = prime * result + ((rawString == null) ? 0 : rawString.hashCode());
		result = prime * result + startIndex;
		result = prime * result + date.getYear();
		return result;
	}

	/**
	 * 只比较日期
	 */
	public boolean equals(Object obj) {
		SlotDATE other = (SlotDATE) obj;
		if (date.getDay() != other.date.getDay())
			return false;
//		if (endIndex != other.endIndex)
//			return false;
		if (date.getMonth() != other.date.getMonth())
			return false;
//		if (rawString == null) {
//			if (other.rawString != null)
//				return false;
//		} else if (!rawString.equals(other.rawString))
//			return false;
//		if (startIndex != other.startIndex)
//			return false;
		if (date.getYear() != other.date.getYear())
			return false;
		return true;
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
		
		if(this.date.getDay()==-1) {
			d = "01";
		}else if(this.date.getDay() < 10) {
			d = "0" + this.date.getDay();
		}else {
			d = "" + this.date.getDay();
		}
		
		if(this.date.getMonth()==-1) {
			m = "01";
		}else if(this.date.getMonth() < 10) {
			m = "0" + this.date.getMonth();
		}else {
			m = "" + this.date.getMonth();
		}
		
		return this.date.getYear() + "-" + m + "-" + d;
	}
	
	/**
	 * 
	 *@desc:检查日期是否合乎常识
	 *@return
	 *@return:boolean
	 *@trhows
	 */
	public boolean isLegal() {
		boolean flag = false;
		
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String[] nowDate = ft.format(dNow).split("-");
		
		//检查时间是否合乎常识
		int[] daySum = {-1,31,-1,31,30,31,30,31,31,30,31,30,31};
		if(this.date.getYear()%4==0 && this.date.getYear()%100!=0 || this.date.getYear()%400==0) {
			daySum[2] = 29;
		}else {
			daySum[2] = 28;
		}
		if(this.date.getDay()>0 && this.date.getMonth()>=1 && this.date.getMonth()<=12 && this.date.getYear()>0  
				&& daySum[this.date.getMonth()] >= this.date.getDay()) {
			flag = true;
		}
		
		return flag;
	}

	/**
	 *@desc:判断当前日期是否早于目标日期
	 *@param endDate
	 *@return
	 *@return:boolean
	 *@trhows
	 */
	public boolean isBefore(SlotDATE date) {
		boolean flag = false;
		if(this.date.getDay()<date.date.getDay() && this.date.getMonth()<date.date.getMonth() && this.date.getYear()<date.date.getYear()) flag = true;
		else if(this.date.getYear()==date.date.getYear() && this.date.getDay()<date.date.getDay() && this.date.getMonth()<date.date.getMonth()) flag = true;
		else if(this.date.getYear()==date.date.getYear() && this.date.getMonth()==date.date.getMonth() && this.date.getDay()<date.date.getDay()) flag = true;
		
		return flag;
	}
	
}
