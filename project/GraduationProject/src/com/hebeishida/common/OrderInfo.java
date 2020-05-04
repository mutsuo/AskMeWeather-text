package com.hebeishida.common;

import java.text.SimpleDateFormat;

import com.hebeishida.entity.Order_menu;
import com.hebeishida.entity.Order_record;

import net.sf.json.JSONObject;

public class OrderInfo {

	private Order_menu orderMenu;
	private Order_record orderRecord;

	public OrderInfo() {

	}

	public Order_menu getOrderMenu() {
		return orderMenu;
	}

	public void setOrderMenu(Order_menu orderMenu) {
		this.orderMenu = orderMenu;
	}

	public Order_record getOrderRecord() {
		return orderRecord;
	}

	public void setOrderRecord(Order_record orderRecord) {
		this.orderRecord = orderRecord;
		this.orderMenu = orderRecord.getOrder_menu();
	}

	// 封装 套餐详情 成json格式
	public JSONObject menuToJson() {
		JSONObject res = new JSONObject();
		res.put("order_number", orderMenu.getOrder_number());
		res.put("order_type", orderMenu.getOrder_type());
		res.put("price", orderMenu.getPrice());
		res.put("month", orderMenu.getMonth());
		return res;
	}

	// 封装 订单列表 成json格式
	public JSONObject orderToJson() {
		String[] type = orderMenu.getOrder_type().split(" ");
		String name = "会员" + type[1];
		double p=orderMenu.getPrice()*orderMenu.getMonth();
		String price = "￥"+removeZero(String.valueOf(p));
		SimpleDateFormat temp = new SimpleDateFormat("yyyy.MM.dd");
		JSONObject res = new JSONObject();
		res.put("id", orderRecord.getId());
		res.put("name", name);
		res.put("price", price);
		res.put("createTime", temp.format(orderRecord.getCreate_time()));
		return res;
	}

	// 封装 订单详情 成json格式
	public JSONObject orderDetailToJson() {
		String[] type = orderMenu.getOrder_type().split(" ");
		String name = "会员" + type[1];
		double p=orderMenu.getPrice()*orderMenu.getMonth();
		String price = "￥"+removeZero(String.valueOf(p));
		SimpleDateFormat temp = new SimpleDateFormat("yyyy.MM.dd");
		JSONObject res = new JSONObject();
		res.put("name", name);//套餐名称
		res.put("price", price);//支付总金额
		res.put("orderNo", orderRecord.getOrder_no());//交易单号
		res.put("createTime", temp.format(orderRecord.getCreate_time()));//交易时间
		res.put("type", orderMenu.getOrder_type().replace(' ', 'x'));//套餐类型
		//支付状态判断
		if (orderRecord.getStatus()==1) {
			res.put("status", "交易成功");
			res.put("payway", orderRecord.getPay_way());//支付方式
			res.put("createTime", temp.format(orderRecord.getEnd_time()));//到期时间
		}else if(orderRecord.getStatus()==2){
			res.put("status", "交易取消");
		}else if(orderRecord.getStatus()==3){
			res.put("status", "交易关闭");
		}
		return res;
	}
	
	//把double值的小数点后的0去掉
	public static String removeZero(String s){  
        if(null != s && s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
    }

}
