package cn.jsprun.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import cn.jsprun.meeting.ConnectionDBE;
import cn.jsprun.meeting.UmsUser;

public class ConnectionDBBlog {
	static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 连接数据库
	public static Connection getConnect() {
		Connection conn = null;
		Properties properties = new Properties();
		InputStream fis;
		try {
			fis = new FileInputStream(JspRunConfig.realPath
					+ "config.properties");
			properties.load(fis);
			fis.close();
			String dbhost = properties.getProperty("dbhost4");
			String dbport = properties.getProperty("dbport4");
			String dbname = properties.getProperty("dbname4");
			String dbuser = properties.getProperty("dbuser4");
			String dbpw = properties.getProperty("dbpw4");

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + dbhost + ":"
					+ dbport + "/" + dbname, dbuser, dbpw);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	
	/***
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public static boolean addBlogUser(String sql) {
		boolean flg = false;
		try {
			Connection con = ConnectionDBBlog.getConnect();
			if (con != null) {
				PreparedStatement pstmt = con.prepareStatement(sql);
				flg = pstmt.execute();
				
				if (pstmt != null) {
					pstmt.close();
				}
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			flg = false;
		}
		return flg;
	}

//	 根据用户的用户名，进行查询用户对象
	public static Integer getBlogUserByAccount(String username) {
		Integer  uid = null;
		Connection con = ConnectionDBBlog.getConnect();
		if (con != null) {
			try {
				String sql = "SELECT * from wp_users where user_login='"
						+ username + "'";
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					uid = rs.getInt("id");
				}
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return uid;
	}


	public static void addBlogUserMeta(Integer uid,String[] metaKeyValue,String[] metaValue) {
		try {
			Connection con = ConnectionDBBlog.getConnect();
			PreparedStatement pstmt =null;
			con.setAutoCommit(false);	
			String sql = "INSERT INTO wp_usermeta (user_id,meta_key,meta_value)";
			for (int i = 0; i < metaValue.length; i++) {
				String insertSql = sql+ " VALUES ("+uid+",'"+metaKeyValue[i]+"','"+metaValue[i]+"');";
				pstmt = con.prepareStatement(insertSql);
				pstmt.executeUpdate();
			}
			 con.commit();
			 if (pstmt != null) {
					pstmt.close();
				}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
