package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Demo {

	public Demo() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/weather?useUnicode=true&characterEncoding=utf8";
			// MySQL配置时的用户名
			String user = "root";
			// MySQL配置时的密码
			String password = "Fyz060709";
			System.out.println("1111111");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库连接异常");
		}
	}

}
