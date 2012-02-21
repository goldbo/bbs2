package cn.vcity.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ChatUtil {
	
	public static HashMap<String , List<Object>> MSGMAP = new HashMap<String, List<Object>>();	//保存每对聊天者的聊天记录
	public static List<Object> MSGLIST = new ArrayList<Object>();		//保存所有的聊天记录，按一定的时间间隔持久化到数据库
	public static HashMap<String,Integer> CHATFLAG = new HashMap<String,Integer>();    //保存聊天下标
	public static HashMap<String,Integer> TEMP = new HashMap<String,Integer>();    //临时变量,用于判断是否更新下标
	public static HashMap<String,String> ISSAMEUSER = new HashMap<String,String>();     //临时变量,用于判断是否同一用户读取了两次数据
	public static int INTERVAL = 1000*60;	//时间间隔  默认为1分钟

	//聊天室模块
	public static List<Object> CHATROOMMSGLIST = new ArrayList<Object>();
	public static HashMap<String , List<Object>> CHATROOMMSGMAP = new HashMap<String, List<Object>>();
}
