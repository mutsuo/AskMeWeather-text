package com.hebeishida.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_record")
public class Order_record {
	private int id;
	//private int M_id;
	//private int T_id;
	private Date create_time;
	private Date end_time;
	private int status;
	private String pay_way;
	private String order_no;
	
	private Order_menu order_menu;
	private Login login;
	
	public Order_record() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPay_way() {
		return pay_way;
	}

	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}
	
	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	@OneToOne
	@JoinColumn(name="M_id")
	public Order_menu getOrder_menu() {
		return order_menu;
	}

	public void setOrder_menu(Order_menu order_menu) {
		this.order_menu = order_menu;
	}

	@ManyToOne
	@JoinColumn(name="T_id")
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

}
