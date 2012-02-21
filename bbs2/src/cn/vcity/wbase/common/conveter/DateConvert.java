package cn.vcity.wbase.common.conveter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.Converter;

/**
 * 
 * 日期转换对象，使用该转换器，在BaseForm当中做一下注册，系统自动地帮助字符的日期表示转换为java.util.Date对象.<br/>
 * 
 * @author 罗留庭 EN:Aly.Luo，开发部-珠海市网佳科技有限公司. Create-Time: 2007-9-8.
 * @version 1.0  
 */
public class DateConvert implements Converter {
	/**
	 * 日期格式化对象.
	 */
    private static SimpleDateFormat df = new SimpleDateFormat();
    
    /**
     * 模式集合.
     * 由原来的HashSet改为ArrayList,add的次序和迭代的次序一样
     */
    private static List<String> patterns = new ArrayList<String>();
    //注册一下日期的转换格式
    static{
    	
    	patterns.add("yyyy-MM-dd");
        patterns.add("yyyy-MM-dd HH:mm");
        patterns.add("yyyy-MM-dd HH:mm:ss");
        patterns.add("yyyy/MM/dd HH:mm:ss");
        
    }
    
    /**
     * 构造器
     *
     */
    public DateConvert() {
    	super();
    }
    
    /**
     * 日期转换器.
     * @param type Class
     * @param value Object
     * return Date Object.
     */
    public Object convert(Class type,Object value){
        if(value == null){
            return null;
        }else if(value instanceof String){
            String dateStr = (String)value;
            if (dateStr.length()==0){
            	return null;
            }
            Object dateObj = null;
            Iterator it = patterns.iterator();

            while(it.hasNext()){
                try{
                    String pattern = (String)it.next();
                    df.applyPattern(pattern);
                    dateObj = df.parse(dateStr);
                    break;
                }catch(ParseException ex){
                	ex.printStackTrace();
                    //do iterator continue
                }
            }
            return dateObj;
        }else{
            return null;
        }
    }
    
    
    public static String DateToStringYMD(Date date){
    	df.applyPattern(patterns.get(0));
    	return df.format(date);
    }
    public static String DateToStringYMDHM(Date date){
    	df.applyPattern(patterns.get(1));
    	return df.format(date);
    }
    public static String DateToStringYMDHMS(Date date){
    	df.applyPattern(patterns.get(2));
    	return df.format(date);
    }
    public static String DateToString(Date date,String dateFormate){
    	df.applyPattern(dateFormate);
    	return df.format(date);
    }
}
