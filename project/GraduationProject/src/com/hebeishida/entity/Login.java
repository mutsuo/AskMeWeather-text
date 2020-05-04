package com.hebeishida.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "login")
public class Login {
	private int id;
	private String Tel;
	private String password;
	
	private Person person;
	private Set<Order_record> order_record=new HashSet<Order_record>();

	public Login() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToOne(mappedBy="login")
	public Person getPerson() {
		return person;
	}

	@OneToMany(mappedBy="login",targetEntity=Order_record.class)
	public Set<Order_record> getOrder_record() {
		return order_record;
	}

	public void setOrder_record(Set<Order_record> order_record) {
		this.order_record = order_record;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
