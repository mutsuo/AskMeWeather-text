package com.hebeishida.pay.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.hebeishida.entity.Order_menu;
import com.hebeishida.entity.Order_record;

@Repository
public class PayDao {

	@Resource
	private SessionFactory sessionFactory;
	
	//获得有效的套餐
	public List<Order_menu> getOderMenu() {
		String sql="from Order_menu where status=1";
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return query.list();
	}
	
	//根据套餐类型，获取套餐ID
	public Order_menu getMenuByType(String Type) {
		String sql="from Order_menu where order_type=?";
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		query.setString(0, Type);
		return (Order_menu)query.uniqueResult();
	}
	
	//插入一条购买订单
	public void insertOrder(Order_record or) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(or);
		session.flush();
	}
	
	//通过订单ID查找订单详情
	public Order_record getOrderById(int id) {
		String sql="from Order_record where id=?";
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		query.setInteger(0, id);
		return (Order_record)query.uniqueResult();
	}
}
