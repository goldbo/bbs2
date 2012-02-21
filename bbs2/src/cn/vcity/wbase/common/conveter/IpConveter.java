package cn.vcity.wbase.common.conveter;

/**
 * IP地址转换
 * @author xms
 *
 */
public class IpConveter {
	/*
	 * 前提是IP地址不能为: 0.0.0.0
	 */
	public long ipAddressToLong(String strIpAddress){
		long ip = 0;
		long ip1 = 0;
		long ip2 = 0;
		long ip3 = 0;
		long ip4 = 0;
		final long IP_ADDRESS = 256;
		if(strIpAddress == null){
			return ip;
		}
		else{
			String[] ipArray = strIpAddress.split("\\.");
			if(ipArray != null && ipArray.length == 4){
				ip1 = Long.parseLong(ipArray[0]) * IP_ADDRESS * IP_ADDRESS * IP_ADDRESS;
				ip2 = Long.parseLong(ipArray[1])* IP_ADDRESS * IP_ADDRESS;
				ip3 = Long.parseLong(ipArray[2])* IP_ADDRESS;
				ip4 = Long.parseLong(ipArray[3]);
				
				ip = ip1 + ip2 + ip3 + ip4;
			}
		}
		return ip;
	}
	
	/*
	 * 前提是IP地址不能为: 0.0.0.0
	 */
	public String longToiIpAddress(long longIpAddress){
		String strIpAddress=""; 
		long ip1 = 0;
		long ip2 = 0;
		long ip3 = 0;
		long ip4 = 0;
		final long IP_ADDRESS = 256;
		//System.out.println(longIpAddress);
		try{
			ip4 = longIpAddress % IP_ADDRESS;
			//System.out.println("ip4:"+ip4);
			ip3 = ((longIpAddress-ip4)/IP_ADDRESS)%IP_ADDRESS;
			//System.out.println("ip3:"+ip3);
			ip2 = ((longIpAddress - ip3*IP_ADDRESS - ip4)/(IP_ADDRESS*IP_ADDRESS))%IP_ADDRESS;
			//System.out.println("ip2:"+ip2);
			ip1 = (longIpAddress - ip2*IP_ADDRESS*IP_ADDRESS - ip3*IP_ADDRESS - ip4)/(IP_ADDRESS*IP_ADDRESS*IP_ADDRESS);
			//System.out.println("ip1:"+ip1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		if(ip4 > -1 && ip3 > -1 && ip2 > -1 && ip1 > -1)
		strIpAddress = String.valueOf(ip1) + "." + String.valueOf(ip2) + "." + String.valueOf(ip3) + "." + String.valueOf(ip4);
		
		return strIpAddress;
	}
	
	public String  cIP16To10(String ip16){
		String ipaddress = "";
		try{
		String[] ipArray = ip16.split("=");
		if(ipArray.length>1){
			String num2 = Integer.valueOf(ipArray[1],16).toString();
			String num3 = Integer.valueOf(ipArray[2],16).toString();
			String num4 = Integer.valueOf(ipArray[3],16).toString();
			String num5 = Integer.valueOf(ipArray[4],16).toString();
			ipaddress =num2+"."+num3+"."+num4+"."+num5;
		}else{
			ipaddress = ip16;
		}
		
		}catch(Exception e){
			ipaddress = "_";
		}
		return ipaddress;
	}

}