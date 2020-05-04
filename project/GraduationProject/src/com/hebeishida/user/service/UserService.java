package com.hebeishida.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hebeishida.entity.Login;
import com.hebeishida.entity.Person;
import com.hebeishida.user.dao.UserDao;

@Service
@Transactional(readOnly=false)
public class UserService {
	@Resource
	private UserDao userDao;
	
	public UserService() {
	}

	//注册
	public void registerInser(Login login,Person person) {
		userDao.registerInsert(login, person);
	}
	
	//登录查询
	public Login loginByTel(String tel) {
		return userDao.getLoginByTel(tel);
	}
	
	//获取用户的详细信息
	public Person personByTel(String tel) {
		return userDao.getPersonByTel(tel);
	}
}
