
package cn.vcity.wbase.common.helper;
/**
 * 
 * 公用String处理方法。<br/>
 * @author 罗留庭 EN:Aly.Luo，开发部-珠海市网佳科技有限公司. Create-Time: 2007-9-8.
 * @version 1.0 
 */
public class StringHelper {
	/**
	 * 处理空,传入对象是Object,如果是空返回""
	 * @param o
	 * @return
	 */
	public static String doWithNull(Object o) {
		if(o==null) return "";
		else return o.toString();
		
	}
	/**
	 * 除去字符串两头的空格
	 * @param s
	 * @author zmw zhmu11@126.com
	 * @return
	 */
	public static String trimString(String s){
		StringHelper.doWithNull(s);
		return s.trim();
	}
	/**
	 * 判断一个字符串是否为null或""
	 * @param str
	 * @author zmw zhmu11@126.com
	 * @return
	 */
	public static boolean isNULLOrEmpty(String str){
		if (str == null) return true;
		if (str.trim().length()==0) return true;
		return false;
	}
 	/**
	 * 用于把字符串劈成数组<br>
	 * @param strSource
	 * @param strDiv
	 * @return
	 */
	 public static String[] split(String strSource, String strDiv) {
         int arynum = 0, intIdx = 0, intIdex = 0;
         int div_length = strDiv.length();
         if (strSource.compareTo("") != 0) {
                 if (strSource.indexOf(strDiv) != -1) {
                         intIdx = strSource.indexOf(strDiv);
                         for (int intCount = 1; ; intCount++) {
                                 if (strSource.indexOf(strDiv, intIdx + div_length) != -1) {
                                         intIdx = strSource.indexOf(strDiv, intIdx + div_length);
                                         arynum = intCount;
                                 }
                                 else {
                                         arynum += 2;
                                         break;
                                 }
                         }
                 }
                 else {
                         arynum = 1;
                 }
         }
         else {
                 arynum = 0;

         }
         intIdx = 0;
         intIdex = 0;
         String[] returnStr = new String[arynum];

         if (strSource.compareTo("") != 0) {
                 if (strSource.indexOf(strDiv) != -1) {
                         intIdx = (int) strSource.indexOf(strDiv);
                         returnStr[0] = (String) strSource.substring(0, intIdx);
                         for (int intCount = 1; ; intCount++) {
                                 if (strSource.indexOf(strDiv, intIdx + div_length) != -1) {
                                         intIdex = (int) strSource.indexOf(strDiv, intIdx + div_length);
                                         returnStr[intCount] = (String) strSource.substring(intIdx + div_length,
                                                 intIdex);
                                         intIdx = (int) strSource.indexOf(strDiv, intIdx + div_length);
                                 }
                                 else {
                                         returnStr[intCount] = (String) strSource.substring(intIdx + div_length,
                                                 strSource.length());
                                         break;
                                 }
                         }
                 }
                 else {
                         returnStr[0] = (String) strSource.substring(0, strSource.length());
                         return returnStr;
                 }
         }
         else {
                 return returnStr;
         }
         return returnStr;
 }
	 
	   /**
	    * 用于把String数组转换成以,相隔的字符串<br>
	    * @param arr
	    * @return
	    */
		public  static String arrToString(String [] arr) 
		{
			if(arr==null) return "";
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<arr.length;i++)
			{
				if(arr[i]!=null&&arr[i].length()>0)
					sb.append(arr[i]+",");
			}
			String returnValue=sb.toString();
			if(returnValue.endsWith(",")) returnValue=returnValue.substring(0,returnValue.length()-1);
			return returnValue;
		}
		
		
		/**
		 * 用于把String数组转换成以splitChar相隔的字符串<br>
		 * @param arr
		 * @param splitChar
		 * @return
		 */
		public  static String arrToString(String [] arr,String splitChar) 
		{
			if(splitChar==null) return arrToString(arr);
			if(arr==null) return "";
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<arr.length;i++)
			{
				if(arr[i]!=null&&arr[i].length()>0)
					sb.append(arr[i]+splitChar);
			}
			String returnValue=sb.toString();
			if(returnValue.endsWith(splitChar)) returnValue=returnValue.substring(0,returnValue.length()-splitChar.length());
			return returnValue;
		}
		
		/**
		 * 对象(字符数组和字符串)转换成字符串。<br>
		 * 如果对象是字符数组,数组转换成以,相隔的字符串<br>
		 * @param object
		 * @return
		 */
		public  static String objectToString(Object object) 
		{
			if(object==null) return "";
			String returnValue="";
			if(object instanceof String)
			{
				returnValue=(String)object;
			}
			else if(object instanceof String[])
			{
				returnValue=StringHelper.arrToString((String []) object);
			}
			else
			{
				returnValue=object.toString();
			}
			return returnValue;
		}
		
		/**
		 * 对象(字符数组和字符串)转换成字符串。<br>
		 * 如果对象是字符数组,数组转换成以splitChar相隔的字符串<br>
		 * @param object
		 * @return
		 */
		public  static String objectToString(Object object,String splitChar) 
		{
			if(object==null) return "";
			String returnValue="";
			if(object instanceof String)
			{
				returnValue=(String)object;
			}
			else if(object instanceof String[])
			{
				returnValue=StringHelper.arrToString((String []) object,splitChar);
			}
			return returnValue;
		}
		
//		Replace the specified string "strSub" with string "strRpl".
		//eg:replace(txt,"\n","<BR>");
		public static String replace(String str, String strSub, String strRpl) {
			String[] tmp = split(str, strSub);
			StringBuffer returnstr = new StringBuffer();
			if (tmp.length != 0) {
				returnstr.append(tmp[0]);
				for (int i = 0; i < tmp.length - 1; i++) {
					returnstr.append(strRpl);
					returnstr.append(tmp[i + 1]);
				}
			}
			return doWithNull(returnstr);
		}
		
		public static String htmlEncode(String txt) {
			if (txt != null) {
				txt = replace(txt, "&", "&amp;");
				txt = replace(txt, "&amp;amp;", "&amp;");
				txt = replace(txt, "&amp;quot;", "&quot;");
				txt = replace(txt, "\"", "&quot;");
				txt = replace(txt, "&amp;lt;", "&lt;");
				txt = replace(txt, "<", "&lt;");
				txt = replace(txt, "&amp;gt;", "&gt;");
				txt = replace(txt, ">", "&gt;");
				txt = replace(txt, "&amp;nbsp;", "&nbsp;");
				//txt = replace(txt," ","&nbsp;");
			}
			return txt;
		}
		
		//Restore string include HTML tags from storage.
		public static String unHtmlEncode(String txt) {
			if (txt != null) {
				txt = replace(txt, "&amp;", "&");
				txt = replace(txt, "&quot;", "\"");
				txt = replace(txt, "&lt;", "<");
				txt = replace(txt, "&gt;", ">");
				txt = replace(txt, "&nbsp;", " ");
			}
			return txt;
		}
		/**
		 * 把字符串中可以引起SQL注入的字符去掉
		 * @param str
		 * @return
		 */
		public static String replaceIllegalChar(String str){
			str = replace(str, "\'", "");
			return str;
		}
		
		//Encode the string by GBK
		public static String toGBK(String str) {
			if (str != null) {
				byte[] tmpbyte = null;
				try {
					tmpbyte = str.getBytes("ISO8859_1");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toGBK :" + e.getMessage());
				}
				try {
					str = new String(tmpbyte, "GBK");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toGBK :" + e.getMessage());
				}
			}
			return str;
		}
		
		//Encode the string by ISO8859_1
		public static String toISO(String str) {
			if (str != null) {
				byte[] tmpbyte = null;
				try {
					tmpbyte = str.getBytes("GBK");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toISO :" + e.getMessage());
				}
				try {
					str = new String(tmpbyte, "ISO8859_1");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toISO :" + e.getMessage());
				}
			}
			return str;
		}
		
		//Encode the string by UTF-8
		public static String toUTF8(String str) {
			if (str != null) {
				byte[] tmpbyte = null;
				try {
					tmpbyte = str.getBytes("ISO-8859-1");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toUTF8 :" + e.getMessage());
				}
				try {
					str = new String(tmpbyte, "UTF-8");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toUTF8 :" + e.getMessage());
				}
			}
			return str;
		}
		
		
//		Encode the string by UTF-8
		public static String utf8ToGBK(String str) {
			if (str != null) {
				byte[] tmpbyte = null;
				try {
					tmpbyte = str.getBytes("UTF-8");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toUTF8 :" + e.getMessage());
				}
				try {
					str = new String(tmpbyte, "ISO-8859-1");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toUTF8 :" + e.getMessage());
				}
			}
			return str;
		}
		
//		Encode the string by UTF-8
		public static String utf8ToISO(String str) {
			if (str != null) {
				byte[] tmpbyte = null;
				try {
					tmpbyte = str.getBytes("UTF-8");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toUTF8 :" + e.getMessage());
				}
				try {
					str = new String(tmpbyte, "GBK");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toUTF8 :" + e.getMessage());
				}
			}
			return str;
		}
		
//		Encode the string by UTF-8
		public static String gbkToUTF8(String str) {
			if (str != null) {
				byte[] tmpbyte = null;
				try {
					tmpbyte = str.getBytes("GBK");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toUTF8 :" + e.getMessage());
				}
				try {
					str = new String(tmpbyte, "UTF-8");
				}
				catch (Exception e) {
					System.out.println("Error: Method: StringUtil.toUTF8 :" + e.getMessage());
				}
			}
			return str;
		}
		
		
}
