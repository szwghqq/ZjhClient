package com.dozengame.net;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import com.dozengame.GameApplication;
import com.dozengame.event.Event;
import com.dozengame.net.pojo.ReadData;
import com.dozengame.net.pojo.VipInfo;

public class DNetworkVip extends DNetwork{

	public static final String CMD_NOTIFY_VIP_OVER  		 = "VIPOVR";		//收到VIP过期
	public static  final String CMD_NOTIFY_MY_VIP_INFO 	 = "VIPMYI";		//收到我的VIP信息
	public static final String CMD_NOTIFY_SITE_VIP_INFO   = "VIPINF";		//收到某个座位的VIP信息

	public DNetworkVip(SocketBase net_ptr) {
		super(net_ptr);
	}
	protected void onProcessCommand(ReadData rd){
		 
 
	  String strCmd = rd.getStrCmd();
     
		if (CMD_NOTIFY_VIP_OVER.equals(strCmd)) {
			//收到VIP过期 
			byte success=rd.readByte();// 0=vip过期 1=购买vip成功
			byte vip_level=rd.readByte();	// 等级：1铜牌VIP，2银牌VIP，3金牌VIP
			String overtime="";
			if(success ==1 ){
				  overtime= rd.readString();//购买成功读取过期时间
			}
			HashMap data = new HashMap();
			data.put("success", success);
			data.put("viplevel", vip_level);
			data.put("overtime", overtime);
			Log.i("test18","收到VIP过期: "+vip_level+" success: "+success+" overtime: "+overtime);
			dispatchEvent(new Event(CMD_NOTIFY_VIP_OVER, data));
		} else if (CMD_NOTIFY_MY_VIP_INFO.equals(strCmd)) {
			//收到我的VIP信息
			int vipcount = rd.readInt();
			if(vipcount >0){
				ArrayList vips = new ArrayList();
				for(int i =0; i< vipcount;i++){
					VipInfo vip = new VipInfo();
					vip.setVipLevel(rd.readInt());	//VIP等级：1铜牌VIP，2银牌VIP，3金牌VIP
					vip.setOverTime(rd.readString());//结束时间 字符串
					vips.add(vip);
				}
				if(GameApplication.userInfo != null){
				  GameApplication.userInfo.put("vip", vips);
				}
			}
			Log.i("test18","收到我的VIP信息过期vipcount: "+vipcount);
			// dispatchEvent(new Event(CMD_NOTIFY_MY_VIP_INFO, null));
		} else if (CMD_NOTIFY_SITE_VIP_INFO.equals(strCmd)) {
			//收到某个座位的VIP信息
			
			int userid = rd.readInt();
			int siteno= rd.readByte();
			Log.i("test18","收到某个座位的VIP信息 userid: "+userid+" siteno: "+siteno);
			int vipcount= rd.readInt();
			for (int i = 0; i < vipcount; i++){
			     int viplevel= rd.readInt();				//VIP等级：1铜牌VIP，2银牌VIP，3金牌VIP
				 String overtime= rd.readString();			//结束时间 字符串
				 Log.i("test18","overtime: "+overtime+" viplevel: "+viplevel);
			}
			//data["isrelogin"]	= m_netptr.readByte();				//重登陆
			//dispatchEvent(new Event(CMD_NOTIFY_SITE_VIP_INFO, null));
		}  
	}
}
