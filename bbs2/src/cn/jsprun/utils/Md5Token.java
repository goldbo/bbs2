package cn.jsprun.utils;

import java.security.MessageDigest;

import org.apache.poi.util.StringUtil;

public final class Md5Token {
	private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	private static Md5Token instance = null;

	private Md5Token() {
	}

	public synchronized static Md5Token getInstance() {
		if(instance==null){
			instance=new Md5Token();
		}
		return instance;
	}
	
	public String getShortToken(String arg0) {
		return encoder(arg0).substring(8,24);
	}
	public String getLongToken(String arg0) {
		return encoder(arg0).toString();
	}
	
	private StringBuffer encoder(String arg){
		if(arg==null){
			arg="";
		}
		MessageDigest md5 = null;
		try {
			md5=MessageDigest.getInstance("MD5");
			md5.update(arg.getBytes(JspRunConfig.CHARSET));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toHex(md5.digest());
	}
	private StringBuffer toHex(byte[] bytes) {
		StringBuffer str = new StringBuffer(32);
		int length=bytes.length;
		for (int i = 0; i < length; i++) {
			str.append(hexDigits[(bytes[i] & 0xf0) >> 4]);
			str.append(hexDigits[bytes[i] & 0x0f]);
		}
		bytes=null;
		return str;
	}
	public static void main(String a[]){
		//80225c692a745cd34df8fea09bde22ab
		String [] aa=new String[]{"aa","bb","cc"};
		System.out.println(aa);
	}
}
