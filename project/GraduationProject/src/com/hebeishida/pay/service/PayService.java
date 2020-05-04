package com.hebeishida.pay.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hebeishida.entity.Order_menu;
import com.hebeishida.entity.Order_record;
import com.hebeishida.pay.dao.PayDao;

@Service
@Transactional(readOnly = false)
public class PayService {
	@Resource
	private PayDao payDao;

	// 获得套餐类型
	public List<Order_menu> getOderMenu() {
		return payDao.getOderMenu();
	}

	// 根据套餐类型，获取套餐ID
	public Order_menu getMenuByType(String Type) {
		return payDao.getMenuByType(Type);
	}

	// 插入一条购买订单
	public void insertOrder(Order_record or) {
		payDao.insertOrder(or);
	}

	// 通过订单ID查找订单详情
	public Order_record getOrderById(int id) {
		return payDao.getOrderById(id);
	}

}
