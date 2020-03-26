<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="register" method="post">
		<div>
			手机号：<input type="text" id="Tel" name="Tel"
				placeholder="请输入手机号 长度:11个字符" />
		</div>
		<div>
			密码：<input type="password" id="password" name="password"
				placeholder="请输入密码" />
		</div>
		<div>
			昵称：<input type="text" id="username" name="username"
				placeholder="请输入密码" />
		</div>
		<div>
			<input type="submit" value="注册">
		</div>
	</form>
</body>
</html>