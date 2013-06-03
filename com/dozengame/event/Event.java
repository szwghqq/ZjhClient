package com.dozengame.event;

public class Event {

	//连接成功
	public static final String CONNECT = "CONNECT";
	//连接失败
	public static final String CONNECTFAIL = "CONNECTFAIL";
	//关闭
	public static final String CLOSE = "CLOSE";
	//套节字数据
	public static final String SOCKET_DATA = "SOCKET_DATA";
	 
	public static final String SYNC = "SYNC";
	//接收到房间事件
	public static final String ON_RECV_GROUP_INFO = "ON_RECV_GROUP_INFO";
	//接收到登录结果事件
	public static final String ON_RECV_LOGIN_RESULT = "ON_RECV_LOGIN_RESULT";
	//接收到登录结果事件
	public static final String ON_RECV_GAME_LOGIN_RESULT = "ON_RECV_GAME_LOGIN_RESULT";
	//收到钱不够被踢出房间事件
	public static final String ON_RECV_LOW_GOLD = "ON_RECV_LOW_GOLD";
	//能否进入房间事件
	public static final String ON_RECV_CAN_NOT_TOROOM = "ON_RECV_CAN_NOT_TOROOM";
	public static final String ON_RECV_RICH_CANNOT_TOROOM = "CMD_NOTIFY_RICH_CANNOT_TOROOM";
	//接收到坐下结果
	public static final String ON_RECV_SITDOWN_RESULT = "ON_RECV_SITDOWN_RESULT";
	//收到游戏相关信息
	public static final String ON_RECV_WATCH_ERROR = "ON_RECV_WATCH_ERROR";
	//接收到网速检测
	public static final String ON_RECV_CHECK_NET = "ON_RECV_CHECK_NET";
	//接收到自己的信息事件
	public static String ON_RECV_MY_INFO = "ON_RECV_MY_INFO";
	//接收BBS字串事件
	public static String ON_TEX_RECV_BBS_URL="ON_TEX_RECV_BBS_URL";
	//登录送钱事件
	public static String ON_TEX_LOGIN_SHOW_DAY_GOLD="ON_TEX_LOGIN_SHOW_DAY_GOLD";
	//登录送钱数目事件
	public static String ON_TEX_RECV_LOGIN_GIVE="ON_TEX_RECV_LOGIN_GIVE";
	//接收选择角色事件
	public static String ON_RECV_CHOOSE_SHOW="ON_RECV_CHOOSE_SHOW";
	//收到声望详细信息[大厅]事件
	public static String ON_RECV_USER_HALL_GAMEINFO="ON_RECV_USER_HALL_GAMEINFO";
	//收到声望详细信息[游戏]事件
	public static String  ON_RECV_USER_GAME_GAMEINFO="ON_RECV_USER_GAME_GAMEINFO";
	//收到服务器限制登陆事件
	public static String ON_RECV_RESTRICT_LOGIN="ON_RECV_RESTRICT_LOGIN";
	//取得分页桌子详细信息结束事件
	public static String ON_RECV_CURPAGEROOM_LIST="ON_RECV_CURPAGEROOM_LIST";
	//取得桌子里玩家的详细信息事件
	public static String ON_RECV_DESK_USERLIST="ON_RECV_DESK_USERLIST";
	//收到桌面金币变化的事件
	public static String ON_RECV_CHANGE_GOLD="ON_RECV_CHANGE_GOLD";
	//收到更新金币的事件
	public static String ON_RECV_UPDATE_GOLD="ON_RECV_UPDATE_GOLD";
	//收到用户离开观战
	public static String ON_RECV_EXIT_WATCH="ON_RECV_EXIT_WATCH";
	
	//收到用户GPS坐标
	public static String ON_RECV_GPS="ON_RECV_GPS";

	Object data;// 数据
	String eventName;// 事件名称

	public Event(String eventName) {
		this.eventName = eventName;
	}

	public Event(String eventName, Object data) {
		this.eventName = eventName;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
}
