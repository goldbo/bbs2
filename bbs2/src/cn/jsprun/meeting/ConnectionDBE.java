package cn.jsprun.meeting;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cn.jsprun.utils.Common;
import cn.jsprun.utils.JspRunConfig;

public class ConnectionDBE {
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
			String dbhost = properties.getProperty("dbhost3");
			String dbport = properties.getProperty("dbport3");
			String dbname = properties.getProperty("dbname3");
			String dbuser = properties.getProperty("dbuser3");
			String dbpw = properties.getProperty("dbpw3");

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + dbhost + ":"
					+ dbport + "/" + dbname, dbuser, dbpw);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	// 根据用户的用户名，进行查询用户对象
	public static UmsUser getUmsUserByAccount(String username) {
		UmsUser uu = null;
		Connection con = ConnectionDBE.getConnect();
		if (con != null) {
			try {
				String sql = "SELECT * from Ums_User where account='"
						+ username + "'";
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					uu = new UmsUser();
					uu.setId(rs.getInt("id"));
					uu.setGroupID(rs.getInt("groupID"));
					uu.setAccount(rs.getString("account"));
					uu.setName(rs.getString("name"));
					uu.setPassword(rs.getString("password"));
					uu.setEmail(rs.getString("email"));
					uu.setHeadImg(rs.getString("headImg"));
					uu.setIp(rs.getString("ip"));
					uu.setLoginIp(rs.getString("loginIp"));
					uu.setLoginTime(rs.getDate("loginTime"));
					uu.setCreateTime(rs.getDate("createTime"));
					uu.setStatus(rs.getInt("status"));
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
		return uu;
	}

	// 根据用户的用户编号，查询tchost对象(主持人)
	public static TcHost getTcHostByUserId(String userId) {
		TcHost th = null;
		Connection con = ConnectionDBE.getConnect();
		if (con != null) {
			try {
				String sql = "SELECT * from Tc_Host where userID=" + userId;
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					th = new TcHost();
					th.setId(rs.getInt("id"));
					th.setUserID(rs.getInt("userID"));
					th.setSex(rs.getInt("sex"));
					th.setRemark(rs.getString("remark"));
					th.setCreator(rs.getInt("creator"));
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
		return th;
	}

	// 根据用户的用户编号，查询tchost对象(参会者)
	public static TcParticipants getTcParticipantsByUserId(String userId) {
		TcParticipants tp = null;
		Connection con = ConnectionDBE.getConnect();
		if (con != null) {
			try {
				String sql = "SELECT * from Tc_Participants where userID="
						+ userId;
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					tp = new TcParticipants();
					tp.setId(rs.getInt("id"));
					tp.setUserID(rs.getInt("userID"));
					tp.setSex(rs.getInt("sex"));
					tp.setCreator(rs.getInt("creator"));
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
		return tp;
	}

	/***
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public static boolean addUmsUser(UmsUser user) {
		boolean flg = false;
		try {
			Connection con = ConnectionDBE.getConnect();
			if (con != null) {
				String sql = "INSERT INTO ums_user" + " VALUES ( null,'"
						+ user.getGroupID() + "','" + user.getAccount()
						+ "', '" + user.getName() + "', '" + user.getPassword()
						+ "'," + " '" + user.getEmail() + "', '', '"
						+ user.getIp() + "', '', '"
						+ sf.format(user.getLoginTime()) + "', '"
						+ sf.format(user.getCreateTime()) + "', '"
						+ user.getStatus() + "')";
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

	/***
	 * 添加用户关联表（参会者）
	 * 
	 * @param user
	 * @return
	 */
	public static boolean addTcParticipants(TcParticipants tp) {
		boolean flg = false;
		try {
			Connection con = ConnectionDBE.getConnect();
			if (con != null) {
				String sql = "INSERT INTO Tc_Participants" + " VALUES ( null,'"
						+ tp.getUserID() + "','" + tp.getSex() + "','"
						+ tp.getCreator() + "')";
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

	/***
	 * 添加关联表(主持人)
	 * 
	 * @param user
	 * @return
	 */
	public static boolean addTcHost(TcHost host) {
		boolean flg = false;
		try {
			Connection con = ConnectionDBE.getConnect();
			if (con != null) {
				String sql = "INSERT INTO Tc_Host" + " VALUES ( null,'"
						+ host.getUserID() + "','" + host.getSex() + "','"
						+ host.getRemark() + "','" + host.getCreator() + "')";
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

	/***
	 * 添加房间
	 * 
	 * @param room
	 * @return
	 */
	public static boolean addTcRoom(TcRoom room) {
		boolean flg = false;
		try {
			Connection con = ConnectionDBE.getConnect();
			if (con != null) {
				String sql = "insert into "
						+ "Tc_Room values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, room.getRoomName());
				pstmt.setInt(2, room.getRoomMaxUsers());
				pstmt.setInt(3, room.getAutoMic());
				pstmt.setInt(4, room.getVisitor());
				pstmt.setString(5, room.getRtmptURL());
				pstmt.setString(6, room.getNote());
				pstmt.setString(7, sf.format(room.getCreateTime()));
				pstmt.setInt(8, room.getHostID());
				pstmt.setString(9, room.getRoomImageURL());
				pstmt.setInt(10, room.getStatus());
				pstmt.setString(11, room.getPeriod());
				pstmt.setInt(12, room.getRoomTest());
				pstmt.setString(13, room.getParticipantsIDs());
				pstmt.setInt(14, room.getCreator());
				pstmt.setInt(15, room.getRoomMaxVideos());
				pstmt.executeUpdate();
				flg = true;
				
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

	// 根据用户的用户编号，查询tchost对象(主持人)
	public static TcRoom getTcRoomById(String roomId) {
		TcRoom room = null;
		Connection con = ConnectionDBE.getConnect();
		if (con != null) {
			try {
				String sql = "SELECT * from Tc_Room where id=" + roomId;
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					room = new TcRoom();
					room.setId(rs.getInt("id"));
					room.setAutoMic(rs.getInt("autoMic"));
					room.setCreateTime(rs.getDate("createTime"));
					room.setCreator(rs.getInt("creator"));
					room.setHostID(rs.getInt("hostID"));
					room.setNote(rs.getString("note"));
					room.setParticipantsIDs(rs.getString("participantsIDs"));
					room.setPeriod(rs.getString("period"));
					room.setRoomImageURL(rs.getString("roomImageURL"));
					room.setRoomMaxUsers(rs.getInt("roomMaxUsers"));
					room.setRoomName(rs.getString("roomName"));
					room.setRoomTest(rs.getInt("roomTest"));
					room.setRtmptURL(rs.getString("rtmptURL"));
					room.setStatus(rs.getInt("status"));
					room.setVisitor(rs.getInt("visitor"));
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
		return room;
	}

	/***
	 * 修改房间
	 * 
	 * @param room
	 * @return
	 */
	public static boolean updateTcRoom(TcRoom room) {
		boolean flg = false;
		try {
			Connection con = ConnectionDBE.getConnect();
			if (con != null) {
				String sql = "update Tc_Room set roomName='"
						+ room.getRoomName() + "',note='" + room.getNote()
						+ "',period='" + room.getPeriod() + "'," + " hostID='"
						+ room.getHostID() + "',participantsIDs='"
						+ room.getParticipantsIDs() + "' where id="
						+ room.getId();
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				flg = true;
				
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

	/***
	 * 修改用户属性
	 * 
	 * @param user
	 * @return
	 */
	public static boolean updateUmsUser(UmsUser user) {
		boolean flg = false;
		try {
			Connection con = ConnectionDBE.getConnect();
			if (con != null) {
				String sql = "update ums_user set groupID='"
						+ user.getGroupID() + "' where id=" + user.getId();
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				flg = true;
				
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

	/***
	 * 删除主持人
	 * 
	 * @param id
	 * @return
	 */
	public static boolean delTcHost(String id) {
		boolean flg = false;
		try {
			Connection con = ConnectionDBE.getConnect();
			if (con != null) {
				String sql = "delete Tc_Host where id=" + id;
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				flg = true;
				
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

	/***
	 * 删除会议室
	 * 
	 * @param id
	 * @return
	 */
	public static boolean delTcRoom(String id) {
		boolean flg = false;
		try {
			Connection con = ConnectionDBE.getConnect();
			if (con != null) {
				String sql = "delete from  Tc_Room where id=" + id;
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				flg = true;
				
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
	
	/***
	 * 根据房间编号，获取会议视频url集合
	 * @param roomId
	 * @return
	 */
	public static List<String> getTcRecordingByRoomId(String roomId) {
		List<String> videoUrlList = null;
		try {
			Connection con = ConnectionDBE.getConnect();
			if (con != null) {
				String sql = "select * from tc_recording where roomID=" + roomId;
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()){
					videoUrlList = new ArrayList<String>();
					videoUrlList.add(rs.getString("Path"));
				}
				
				while (rs.next()) {
					videoUrlList.add(rs.getString("Path"));
				}
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return videoUrlList;
	}
	
	/***
	 * 根据房间编号，获取会议聊天内容集合
	 * @param roomId
	 * @return
	 */
	public static List<String> getTcChatByRoomId(String roomId) {
		List<String> chatContentList = null;
		try {
			Connection con = ConnectionDBE.getConnect();
			String str = "";
			if (con != null) {
				String sql = "select * from tc_chat where roomID=" + roomId;
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()){
					chatContentList = new ArrayList<String>();
					str = " \r\n时间："+rs.getString("createTime")+"\r\n";
					str += rs.getString("sendUserName")+"对"+rs.getString("acceptUserName")+"说："+rs.getString("content");
					chatContentList.add(str);
				}
				while (rs.next()) {
					str = " \r\n时间："+rs.getString("createTime")+"\r\n";
					str += rs.getString("sendUserName")+"对"+rs.getString("acceptUserName")+"说："+rs.getString("content");
					chatContentList.add(str);
				}
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return chatContentList;
	}
	
	// 查找所有的E声网用户
	public static List<UmsUser> getUmsUserList() {
		List<UmsUser> uuList = new ArrayList<UmsUser>();
		Connection con = Common.getConnect();
		if (con != null) {
			try {
				String sql = "SELECT * from customer limit 0,1000";
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				UmsUser uu = null;
				while (rs.next()) {
					uu = new UmsUser();
					uu.setId(rs.getInt("customer_id"));
					uu.setName(rs.getString("customer_account"));
					uu.setEmail(rs.getString("email"));
					uuList.add(uu);
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
		return uuList;
	}
}
