package com.qzp.model.extend;

public class BusSearch {
	public static String getBusSearchHelper(){
		StringBuffer buffer = new StringBuffer();  
        buffer.append("[礼物]公交查询").append("\n");  
        buffer.append("请输入   公交查询城市名,出发地,目的地").append("\n\n"); 
        buffer.append("城市名和出发地后面有一个逗号 [愉快]").append("\n");
        buffer.append("如： 公交查询南京,河海大学,南京南站");  
        
          
        return buffer.toString();  
	}
}
