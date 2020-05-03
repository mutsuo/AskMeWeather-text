package com.hebeishida.user.control;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hebeishida.common.UserInfo;
import com.hebeishida.entity.Login;
import com.hebeishida.entity.Person;
import com.hebeishida.user.service.UserService;

import net.sf.json.JSONObject;


@Controller
public class UserControl {
	@Resource
	private UserService userService;

	// 注册
	@RequestMapping(value = "register")
	public String register(HttpServletRequest request, ServletContext application) {
		String tel = request.getParameter("Tel");
		String pass = request.getParameter("password");
		String username = request.getParameter("username");

		Login login = new Login();
		Person person = new Person();
		login.setTel(tel);
		login.setPassword(pass);
		person.setUsername(username);

		userService.registerInser(login, person);

		// 将用户信息保存到application
		application.setAttribute("person", person);

		return "demo";
	}

	// 登录功能
	@RequestMapping(value = "login")
	public String login(HttpServletRequest request) {
		String tel = request.getParameter("Tel");
		String pass = request.getParameter("password");
		Login login = userService.loginByTel(tel);
		if (!pass.equals(login.getPassword())) {
			return "index";
		}
		Person person = login.getPerson();
		// 将用户信息保存到application
		//ServletContext application=request.getServletContext();
		request.setAttribute("login", login);
		JSONObject res=new JSONObject();
		res.put("code", 1);
		UserInfo userInfo=new UserInfo(person);
		res.put("userInfo", userInfo.toJson());
		System.out.println("=========="+res.toString());
//		return "forward:/getMenu";
		return "forward:/getOrderList";
	}


}
