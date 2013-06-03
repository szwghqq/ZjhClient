package com.dozengame.event;

/**
 * 事件常量
 * 
 * @author hewengao
 * 
 */
public class TexEventType {
	 
	public static final String ON_TEX_RECV_RESETDISPLAY="ON_TEX_RECV_RESETDISPLAY";	//重新登录显示或桌内玩家已在玩
	public static final String ON_TEX_RECV_TEX_READY = "ON_TEX_RECV_TEX_READY"; // 收到准备
	public static final String ON_TEX_RECV_FAPAI = "ON_TEX_RECV_FAPAI"; // 收到发牌
	public static final String ON_TEX_RECV_BESTPOKES = "ON_TEX_RECV_BESTPOKES"; // 收到最佳牌型
	public static final String ON_TEX_RECV_MYPOKE = "ON_TEX_RECV_MYPOKE"; // 收到我的扑克
	public static final String ON_TEX_AUTO_PANEL = "ON_TEX_AUTO_PANEL"; // 自动面板
	public static final String ON_TEX_RECV_GIVEUP = "ON_TEX_RECV_GIVEUP"; // 收到投降的消息
	public static final String ON_TEX_RECV_XIAZHUSUCC = "ON_TEX_RECV_XIAZHUSUCC"; // 收到下注成功
	public static final String ON_TEX_RECV_BUXIAZHU = "ON_TEX_RECV_BUXIAZHU"; // 收到不下注
	public static final String ON_TEX_BUTTON_STATUS = "ON_TEX_BUTTON_STATUS"; // 设置按钮对应的动作
	public static final String ON_TEX_RECV_GAMEOVER = "ON_TEX_RECV_GAMEOVER"; // 收到游戏结束，重新开始
	public static final String ON_TEX_RECV_REFRESHGOLD = "ON_TEX_RECV_REFRESHGOLD"; // 更新桌内座位上的金币
	public static final String ON_TEX_RECV_DESK_POLL_INFO = "ON_TEX_RECV_DESK_POLL_INFO"; // 收到桌面彩池信息
	public static final String ON_TEX_RECV_DESK_INFO = "ON_TEX_RECV_DESK_INFO"; // 收到桌信息
	public static final String ON_TEX_RECV_SHOW_MY_TOTAL_BEAN = "ON_TEX_RECV_SHOW_MY_TOTAL_BEAN"; // 收到我的德州豆
	public static final String ON_TEX_RECV_PLAYERINFO = "ON_TEX_RECV_PLAYERINFO"; // 更新所有人状态
	public static final String ON_TEX_RECV_POKEINFO = "ON_TEX_RECV_POKEINFO"; // 发送某个座位上的牌(重登陆用)
	public static final String ON_TEX_RECV_RELOGIN_DESKGOLD = "ON_TEX_RECV_RELOGIN_DESKGOLD"; // 收到桌面金币改变(重登陆用)
	public static final String ON_TEX_RECV_GAMESTART = "ON_TEX_RECV_GAMESTART"; // 收到游戏开始
	public static final String ON_TEX_RECV_BUYCHOUMA = "ON_TEX_RECV_BUYCHOUMA"; // 收到购买筹码
	public static final String ON_TEX_RECV_JS_WAITING = "ON_TEX_RECV_JS_WAITING"; // 收到桌子正在结算

	public static final String ON_TEX_RECV_TIMEOUT = "ON_TEX_RECV_TIMEOUT"; // 收到被踢
	public static final String ON_TEX_RECV_SITDOWN = "ON_TEX_RECV_SITDOWN"; // 坐下
	public static final String ON_TEX_RECV_STANDUP = "ON_TEX_RECV_STANDUP"; // 站起
	public static final String ON_TEX_RECV_SERVER_ERROR = "ON_TEX_RECV_SERVER_ERROR"; // 收到服务端的提示信息
	public static final String ON_TEX_RECV_CHANGEFACE = "ON_TEX_RECV_CHANGEFACE"; // 收到玩家修改头像

	public static final String ON_TEX_RECV_SPECAL_ICON = "ON_TEX_RECV_SPECAL_ICON"; // 收到特殊标识

	public static final String ON_TEX_RECV_DESKPOKE = "ON_TEX_RECV_DESKPOKE"; // 收到桌面扑克
	public static final String ON_TEX_RECV_WATCHING = "ON_TEX_RECV_WATCHING"; // 收到用户观战
	public static final String ON_RECV_EXIT_WATCH = "ON_RECV_EXIT_WATCH"; // 收到退出观战
	public static final String ON_RECV_ADD_EXPERIENCE = "ON_RECV_ADD_EXPERIENCE"; // 收到增加经验
	public static final String ON_RECV_LEVEL_UPGRADE = "ON_RECV_LEVEL_UPGRADE"; // 收到用户等级升级
	public static final String ON_RECV_DAY_ADDEXP = "ON_RECV_DAY_ADDEXP"; // 收到用户获得天天送经验红利
	public static final String ON_RECV_LEVEL_PRIZE_OR_LOST = "ON_RECV_LEVEL_PRIZE_OR_LOST"; // 收到自己得奖或被淘汰
	public static final String ON_RECV_LEVEL_SERVER_MSG = "ON_RECV_LEVEL_SERVER_MSG"; // 收到用户升级后显示的信息

	public static final String ON_BACK_HALL_RESULT = "ON_BACK_HALL_RESULT"; // 收到服务器通知返回大厅

	public static final String ON_TEX_RECV_GIFT_SHOP = "ON_TEX_RECV_GIFT_SHOP"; // 收到商城礼物列表
	public static final String ON_TEX_RECV_GIFT_LIST = "ON_TEX_RECV_GIFT_LIST"; // 收到礼物列表
	public static final String ON_TEX_RECV_GIFT_ICON = "ON_TEX_RECV_GIFT_ICON"; // 收到礼物标识
	public static final String ON_TEX_RECV_PLAY_GIFT = "ON_TEX_RECV_PLAY_GIFT"; // 播放送礼物动画
	public static final String ON_TEX_RECV_PLAY_EMOT = "ON_TEX_RECV_PLAY_EMOT"; // 播放表情
	public static final String ON_TEX_RECV_GIFT_FAILD = "ON_TEX_RECV_GIFT_FAILD"; // 购买礼物或发送表情失败
	public static final String ON_TEX_RECV_GIFT_FAILDLIST = "ON_TEX_RECV_GIFT_FAILDLIST"; // 批量购买礼物失败
	public static final String ON_TEX_RECV_GIFT_SALE = "ON_TEX_RECV_GIFT_SALE"; // 收到出售礼物后的状态
	public static final String ON_RECV_GIFT_XINSHOU_ONE = "ON_RECV_GIFT_XINSHOU_ONE"; // 给第一次玩牌的新手送礼物
	// 收到礼物回复
 
	public static final String ON_TEX_RECV_GIFT_RESPONSE = "ON_TEX_RECV_GIFT_RESPONSE";

	public static final String ON_RECV_LOGIN_SHOW_BETAGIFE = "ON_RECV_LOGIN_SHOW_BETAGIFE";
	public static final String ON_RECV_LOGIN_GET_BETAGIFE = "ON_RECV_LOGIN_GET_BETAGIFE";

	// m_gsProcessor.addEventListener("ON_TEX_RECV_BBS_URL",this,"");

	public static final String ON_TEX_RECV_SINGLE_DETAIL = "ON_TEX_RECV_SINGLE_DETAIL";

	public static final String ON_RECV_GIVE_JIUJI = "ON_RECV_GIVE_JIUJI";
	// ------踢人start-----------------------
	public static final String ON_RECV_KICK_ENABLE = "ON_RECV_KICK_ENABLE";
	public static final String ON_RECV_KICK = "ON_RECV_KICK";
	public static final String ON_RECV_KICK_RESULT = "ON_RECV_KICK_RESULT";
	public static final String ON_RECV_KICK_CISHU = "ON_RECV_KICK_CISHU";
	public static final String ON_RECV_KICK_OVER = "ON_RECV_KICK_OVER";
	public static final String ON_RECV_KICK_OVER_BEITI = "ON_RECV_KICK_OVER_BEITI";
	public static final String ON_RECV_KICK_SHOW = "ON_RECV_KICK_SHOW";
	// ------踢人end-----------------------

	// ----算牌start-----------------------
	public static final String ON_RECV_CALC_ENABLE = "ON_RECV_CALC_ENABLE";
	// ------算牌end-----------------------
	//聊天
	public static final String ON_TEX_RECV_CHAT =  "ON_TEX_RECV_CHAT";
	
	 
}
