package com.dozengame.net.pojo;

public class DConfig {

	//public static String strUser = "hwg19860119";					//参数,来自网页  用户名			
	//public static String strKey = "hwg19860119hwg";						//参数,来自网页  密码（以后改成密文）
	public static String strUser = "222";
	
	public static String strKey = "11";
	public static String strKey2 = "11";						//参数,来自网页  密码（以后改成密文）		
    public static int userId=-1;
	public static String strChannelId = "";
	
	public static String strVersion = "";					//GameCenter IP
	
	public static int nRegSit  = 1 	;						//注册站点1表示多玩，0表示官网
	public static String strHost = "duowan.dozengame.com" ;	//yy版
	public static String strHost2 = "duowan.dozengame.com" ;	//yy版
	
	public static int nPort  = 6000;					    	//GameCenter 端口
	public static int nPort2  = 6000;					    	//GameCenter 端口
	public static String strChongzhiUrl = "";			 	//充值url
	public static String strDefaultGame = "";			 	//默认游戏名,来自swf参数
	public static String strLoginType = "1";                 //区分是否频道登录标志, 默认为1
	
	public static final String FROM_CHANNEL = "2";         //来自频道的用户
	
	//本机互斥配置
	public static final int LOCAL_MUTEX_TYPE_DESKLIST  = 1;	//从房间列表点击进来的
	public static final int LOCAL_MUTEX_TYPE_SITDOWN  = 2;	//玩家收到坐下事件后调用(onRecvSitDown)
	public static final int LOCAL_MUTEX_TYPE_TEXT_ENTER  = 3;//大厅，输入房间ID进入
	public static final int LOCAL_MUTEX_TYPE_TEXT_CLICK_DESK_JOINFRIEND  = 4;//大厅，输入房间ID进入

	public static double nMyLatitudeX = 0;					    	//自己GPS纬度坐标X
	public static double nMyLongitudeY = 0;					    	//自己GPS经度坐标Y
}
