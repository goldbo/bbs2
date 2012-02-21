package cn.vcity.wbase.common.helper;

/**
 * 
 * 用于生成32位UUID<br/>
 * 实现背景：<br>
 * (1):系统中32位主键大部份是由Hibernate.
 * (2):如果系统主键要由jdbc生成，可以调用此类的genRandomGUID()方法来生成
 * @author 罗留庭 EN:Aly.Luo，开发部-珠海市网佳科技有限公司. Create-Time: 2007-9-18.
 * @version 1.0 
 */
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class GUIDHelper {
	private static Random myRand;

	private static SecureRandom mySecureRand;

	private static String s_id;

	static {
		mySecureRand = new SecureRandom();

		long secureInitializer = mySecureRand.nextLong();
		myRand = new Random(secureInitializer);

		try {

			s_id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public GUIDHelper() {
	}

	public static String genRandomGUID() {
		return genRandomGUID(false);
	}

	public static String genRandomGUID(boolean secure) {
		StringBuffer valueBeforeMD5 = new StringBuffer();
		StringBuffer valueAfterMD5 = new StringBuffer();

		MessageDigest md5;
		StringBuffer sbValueBeforeMD5 = new StringBuffer();

		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error: " + e);
			return valueBeforeMD5.toString();
		}

		long time = System.currentTimeMillis();
		long rand = 0;

		if (secure) {
			rand = mySecureRand.nextLong();
		} else {
			rand = myRand.nextLong();
		}

		sbValueBeforeMD5.append(s_id);
		sbValueBeforeMD5.append(":");
		sbValueBeforeMD5.append(Long.toString(time));
		sbValueBeforeMD5.append(":");
		sbValueBeforeMD5.append(Long.toString(rand));
		valueBeforeMD5.append(sbValueBeforeMD5.toString());
		md5.update(valueBeforeMD5.toString().getBytes());
		byte[] array = md5.digest();
		String strTemp = "";
		for (int i = 0; i < array.length; i++) {
			strTemp = (Integer.toHexString(array[i] & 0XFF));
			if (strTemp.length() == 1) {
				valueAfterMD5.append("0");
				valueAfterMD5.append(strTemp);

			} else {
				valueAfterMD5.append(strTemp);
			}
		}

		return valueAfterMD5.toString().toUpperCase();
	}

	public static void main(String args[]) {
		for (int i = 1; i < 10; i++) {
			System.out.println(Integer.toString(i) + " : "
					+ genRandomGUID(true));
		}
	}

}
