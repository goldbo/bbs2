package cn.vcity.wbase.common.base;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OnlineCounter {
	private static List list = new ArrayList();

	private static List list1 = new ArrayList();

	private OnlineCounter() {
	}

	// 统计当前在线会员人数
	@SuppressWarnings("unchecked")
	public static void doUser(Object object, boolean bl) {
		// 得到用户当前访问时间
		String nowtime = getTime("mm:ss");
		// Str数组用来存放用户的id和访问时间
		String[] str = new String[2];
		str[0] = object.toString();
		str[1] = nowtime;

		// temp数组用来临时存放从list里面取出的用户的id和访问时间
		String[] temp = new String[2];

		// 循环list
		for (int i = 0; i < list.size(); i++) {
			temp = (String[]) list.get(i);
			// 如果从list里面取出的用户ip和str里面存放的id相同，则更新访问时间
			if (bl == false && temp[0].equals(str[0])) {
				list.set(i, str);
				return;
			}
			// 如果监听类中穿过来bl==true，则认为不在线

			if (bl == true && temp[0].equals(str[0])) {
				list.remove(i);
			}
		}
		// 添加一个新的在线用户
		if (bl == false)
			list.add(str);
	}

	// 统计网站所有在线人数
	@SuppressWarnings("unchecked")
	public static void allUser(Object object) {
		// 得到用户当前访问时间
		String nowtime = getTime("mm:ss");
		// Str数组用来存放用户得ip和访问时间
		String[] str = new String[2];
		str[0] = object.toString();
		str[1] = nowtime;

		// temp数组用来临时存放从list里面取出的用户ip和访问时间
		String[] temp = new String[2];

		// 循环list
		for (int i = 0; i < list1.size(); i++) {
			temp = (String[]) list1.get(i);
			// 如果从list里面取出的用户ip和str里面存放的ip相同，则更新访问时间
			if (temp[0].equals(str[0])) {
				list1.set(i, str);
				return;
			}
			// 如果用户超过10分钟没有访问，则认为不在线
			if (subTime(nowtime, temp[1]) > 600) {
				// System.out.print("清空list过期的值"+i);
				list1.remove(i);
			}

		}
		// 添加一个新的在线用户
		list1.add(str);
	}

	public static int getOnlineAllCount() {
		// 返回当前在线人数
		return list1.size();
	}

	public static int getOnlineCount() {
		// 返回当前在线人数
		return list.size();
	}

	/*
	 * public static void romovelist() {
	 *  }
	 */

	public static List getOnline() {
		return list;
	}

	/** 计算两个时间差，返回相差秒数 */
	public static int subTime(String src, String des) {
		int n = 0;
		java.util.Calendar ca = java.util.Calendar.getInstance();
		long time1 = compareStringTime(src, des, "mm:ss");
		ca.setTimeInMillis(time1);
		n = (ca.get(java.util.Calendar.MINUTE)) * 60;
		n = n + ca.get(java.util.Calendar.SECOND);
		return n;
	}

	/**
	 * 获得当前时间
	 * 
	 * @param parrten
	 *            输出的时间格式
	 * @return 返回时间
	 */
	public static String getTime(String parrten) {
		String timestr;
		if (parrten.length()==0) {
			parrten = "yyyyMMddHHmmss";
		}
		java.text.SimpleDateFormat sdf = new SimpleDateFormat(parrten);
		java.util.Date cday = new Date();
		timestr = sdf.format(cday);
		return timestr;
	}

	/**
	 * 比较两个字符串时间的大小
	 * 
	 * @param t1
	 *            时间1
	 * @param t2
	 *            时间2
	 * @param parrten
	 *            时间格式 :yyyy-MM-dd
	 * @return 返回long =0相等，>0 t1>t2，<0 t1<t2
	 */
	public static long compareStringTime(String t1, String t2, String parrten) {
		SimpleDateFormat formatter = new SimpleDateFormat(parrten);
		ParsePosition pos = new ParsePosition(0);
		ParsePosition pos1 = new ParsePosition(0);
		Date dt1 = formatter.parse(t1, pos);
		Date dt2 = formatter.parse(t2, pos1);
		long l = dt1.getTime() - dt2.getTime();
		return l;
	}

}
