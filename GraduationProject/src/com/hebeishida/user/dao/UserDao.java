package com.hebeishida.user.dao;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.hebeishida.entity.Login;
import com.hebeishida.entity.Person;

@Repository
public class UserDao {

	@Resource
	private SessionFactory sessionFactory;

	public UserDao() {
	}

	// 注册操作后，需要更新的数据库
	public void registerInsert(Login login, Person person) {
		insertLogin(login);
		person.setLogin(getLoginByTel(login.getTel()));
		insertRegister(person);
	}

	// 用户注册后，插入login表
	public void insertLogin(Login login) {
		Session session = this.sessionFactory.getCurrentSession();
		//Transaction tran = session.beginTransaction();
		session.save(login);
		session.flush();
		//tran.commit();
	}

	// 用户注册后，插入register表
	public void insertRegister(Person person) {
		Session session = this.sessionFactory.getCurrentSession();
		//Transaction tran = session.beginTransaction();
		session.save(person);
		session.flush();
		//tran.commit();
	}

	// 通过电话tel，获取用户登录信息
	public Login getLoginByTel(String tel) {
		String sql = "from Login where Tel = ?";
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		query.setString(0, tel);
		Login login=(Login) query.uniqueResult();
		return login;
	}
	
	//通过电话Tel，获取用户的详细信息
	public Person getPersonByTel(String tel) {
		return getLoginByTel(tel).getPerson();
	}
}
