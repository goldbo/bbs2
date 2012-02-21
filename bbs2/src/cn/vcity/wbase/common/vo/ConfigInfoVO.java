package cn.vcity.wbase.common.vo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigInfoVO implements java.io.Serializable{
	
	private static final long serialVersionUID = -291996993288028782L;
	private static Properties configInfoProps;
	public static Properties getConfigInfoProps() {
		return configInfoProps;
	}
	public static  String getProperty(String name) throws IOException
	{
		String retrunValue="";
		if(configInfoProps!=null)
		{
			retrunValue=(String)configInfoProps.getProperty(name);
		}
		else
		{
			InputStream web_confInput = null;
			try {
				ClassLoader loader = ConfigInfoVO.class.getClassLoader();
		        web_confInput = loader.getResourceAsStream("web_conf.properties"); 
				ConfigInfoVO.configInfoProps=new Properties();
				ConfigInfoVO.configInfoProps.load(web_confInput);
				retrunValue=(String)configInfoProps.getProperty(name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				web_confInput.close();
			}
		}
		return retrunValue;
	}
	

}
