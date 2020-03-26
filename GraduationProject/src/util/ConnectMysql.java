package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectMysql {
	public static Connection con = null;

	public ConnectMysql() {

	}
	
	public static void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/weather?useUnicode=true&characterEncoding=utf8";
			// MySQL配置时的用户名
			String user = "root";
			// MySQL配置时的密码
			String password = "Fyz060709";
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			System.out.println("数据库连接异常");
		}
	}

	public static Connection getCon() {
		if (con == null) {
			init();
		}
		return con;
	}
}
