package com.hebeishida.common;

import java.util.Date;
import java.util.Set;

import com.hebeishida.entity.Order_record;
import com.hebeishida.entity.Person;

import net.sf.json.JSONObject;

public class UserInfo {
	private Person userInfo;
	private int VIP;
	public UserInfo() {
	}

	public UserInfo(Person userInfo) {
		this.userInfo=userInfo;
		this.VIP=0;
	}

	public Person getUserInfo() {
		return userInfo;
	}

	public int getVIP() {
		return VIP;
	}

	public void setVIP(int vIP) {
		VIP = vIP;
	}

	public void setUserInfo(Person userInfo) {
		this.userInfo = userInfo;
	}
	
	public JSONObject toJson() {
		//处理是否是VIP
		HandleVip();
	
		JSONObject res=new JSONObject();
		res.put("name", userInfo.getUsername());
		res.put("tel", userInfo.getLogin().getTel());
		res.put("birthday", userInfo.getBirthday().toString());
		res.put("city", userInfo.getCity());
		res.put("VIP",VIP);
		return res;
	}
	
	public void HandleVip() {
		Date now=new Date();
		Set<Order_record> or=userInfo.getLogin().getOrder_record();
		System.out.println("=======Order_record_list   "+or.size());
		for (Order_record order_record : or) {
			if (order_record.getStatus()==1&&order_record.getEnd_time().getTime()>=now.getTime()) {
				VIP=1;
				break;
			}
		}
	}
}
