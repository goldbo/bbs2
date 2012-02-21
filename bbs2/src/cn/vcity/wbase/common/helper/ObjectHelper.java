package cn.vcity.wbase.common.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
/**
 * 
 * 公用Object对象类，提供对object对象的静态帮助方法。<br/>
 * @author 罗留庭 EN:Aly.Luo，开发部-珠海市网佳科技有限公司. Create-Time: 2007-9-8.
 * @version 1.0 
 */
public class ObjectHelper {

	/**
	 * 通过反射机械得到object中property属性的值
	 * @param property 对象的属性名
	 * @param object   对象
	 * @return Object
	 */
	public  static Object getMethodValue(String property,Object object) 
	{
		 Field[] fields = object.getClass().getDeclaredFields(); 
		 Object valueObject = new Object();
		 for(int j=0;j<fields.length;j++)
		 {
			 if (fields[j].getName().equals(property))
             {
				 String firstChar = fields[j].getName().substring(0, 1);
		         String leaveChar = fields[j].getName().substring(1);
		         String methodName = firstChar.toUpperCase() + leaveChar; 
		         
		         try{
		         Method meth = object.getClass().getMethod("get" +
		                 methodName, null);
		          valueObject=meth.invoke(object, null);
		         }catch(Exception ex)
		         {
		        	 ex.printStackTrace();
		         }
		         break;
             } 
		 }
		 return valueObject;
	}
	
    /**
     * 对象的copy 
     * @param srcObject 源对象,srcObject可以是Collection类型对象
     * @param targetClassName  target copy对象的全类名。
     * @return Object 
     */
	@SuppressWarnings("unchecked")
	public static Object objectCopy(Object srcObject,String targetClassName)
	{
		Object targetObject = new Object();
        if ((srcObject == null) || (targetClassName == null)) {
            return null;
        }
        if (srcObject instanceof Collection) {
            Iterator iter = ((Collection) srcObject).iterator();
            Collection coll = new ArrayList();
            Object item = new Object();
            Object targetItem = new Object();
            while (iter.hasNext()) {
                item = iter.next();
                targetItem = _objectCopy(item, targetClassName);
                coll.add(targetItem);
            }
            targetObject = coll;
        } else {
            targetObject = _objectCopy(srcObject, targetClassName);
        }
        return targetObject;
	}

	  /**
     * 对象的copy 
     * @param srcObject 源对象,srcObject只能是单体类型对象
     * @param targetClassName  target copy对象的全类名。
     * @return Object 
     */
	private  static  Object _objectCopy(Object srcObject,String targetClassName)
	{
	        Class targetClass;
	        Object targetObject = new Object();
			try {
				targetClass = Class.forName(targetClassName);
				targetObject = targetClass.newInstance();
				BeanUtils.copyProperties(targetObject,srcObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return  targetObject;
	}

	
	 /**
     * 把字符数组转行成适应sql in参数和字符串.
     * @param strArr 源对象,srcObject只能是单体类型对象
     * @return String
     * 如:<br>
     *   conn.setAutoCommit(false);<br>
         st = conn.createStatement();<br>
         <font color='red'>String strIds=ObjectHelper.strArroStringSQL(dictId);</font><br>
         String update="UPDATE SYS_DICTIONARY_TYPE SET DEL_FLAG='Y' WHERE DICTIONARY_TYPE_ID IN("+strIds+")";<br>
		 st.executeUpdate(update);<br>
         conn.commit();<br>
     */
	public static String strArroStringSQL(String [] strArr)
	{
	    if(strArr==null||strArr.length==0) return "";
	    StringBuffer sb=new StringBuffer();
	    for(int i=0;i<strArr.length;i++)
	    {
	    	sb.append("'"+strArr[i]+"',");
	    }
	    String returnValue=sb.toString();
	    returnValue=returnValue.substring(0,returnValue.length()-1);
	    return returnValue;
	}

	
}
