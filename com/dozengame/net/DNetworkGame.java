package com.dozengame.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.util.Log;
import com.dozengame.DzpkGameMenuActivity;
import com.dozengame.GameApplication;
import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.event.EventDispatcher;
import com.dozengame.net.pojo.DConfig;
import com.dozengame.net.pojo.DeskInfo;
import com.dozengame.net.pojo.ReadData;
import com.dozengame.playerlocationroom.PlayerLocationData;
import com.dozengame.util.GameUtil;

/**
 *游戏网络服务 主要功能: 向游戏服务发送请求, 接收服务响应
 * 
 * @author hewengao
 * 
 */
public class DNetworkGame extends EventDispatcher implements CallBack {

	final String tag = "DNetworkGame";
	private String CMD_MOBLOGIN="MOBLOGIN";//手机登录
	private String CMD_MOBPAYOFF="MO_PAYOFF";//手机支付开关
	// 发送的命令
	private String CMD_CHECK_NET="CHECK_NET";//网速检测
	private String CMD_MO_REFRESH="MO_REFRESH";//刷新
	private String CMD_REQUEST_LOGIN = "RQLG"; // 请求登陆

	private String CMD_REQUEST_AUTO_JOIN = "RQAJ"; // 请求自动加入游戏
	private String CMD_REQUEST_PAIDUI = "RQDQ"; // 请求排队
	private String CMD_REQUEST_UNQUEUE = "RQCQ"; // 取消排队
	private String CMD_REQUEST_ROOM_DESKLIST = "RQDS"; // 请求桌子列表

	private String CMD_REQUEST_ROOMLIST_PAGE = "RQDP"; // 请求分页桌子详细信息
	private String CMD_REQUEST_DESKLIST = "RQFD"; // 请求分页桌子详细信息
	private String CMD_REQUEST_STANDUP = "RQSU"; // 用户站起
	private String CMD_REQUEST_CHANGE_FACE = "RQCF"; // 用户申请改变头像
	private String CMD_REQUEST_ACTIVE_FACE = "RAAF"; // 用户申请激活头像
	private String CMD_REQUEST_TIME_TIME = "RETT"; // 心跳
	private String CMD_REQUEST_ONLONE_SERVER = "RQOS"; // 查询所有服务器的在线人数
	private String CMD_REQUEST_GIVE_GOLD = "RQGG"; // 要求送钱
	private String CMD_REQUEST_CLIENT_LEAVE_ROOM = "RQCLR"; // 离开牌桌大厅

	private String CMD_REQUEST_UPDATE_GOLD = "RQGB"; // 要求服务器刷新钱
	private String CMD_REQUEST_WATCH = "REWT"; // 要求观战
	private String CMD_REQUEST_EXIT_WATCH = "REET"; // 要求退出观战
	private String CMD_REQUEST_ROOM_SORT_LIST = "RQRSL"; // 客户端请求房间玩家排名
	private String CMD_REQUEST_UPDATE_VIPINFO = "RQVIPIF"; // 客户端请求刷新VIP信息
	private String CMD_NOTIFY_TEX_BBS_URL = "TXNBBS"; // 收到论坛验证码
	// -------------------------特权码str--------------------
	private String CMD_NOTIFY_TEX_PRIVILEGE_YANZHENG = "JHMGIFTSN";// 特权码验证
	// -------------------------特权码end--------------------
	private String CMD_TEX_UPDATE_CHANNEL_INFO = "TEXUSERCHANNEL"; // 更新用户昵称
	private String CMD_TEX_CHOOSE_ROLE = "TEXAUTHORBAR"; // 收到显示选择角色面板
	private String CMD_TEX_UPDATE_NICK_INFO = "TEXUSERNICK"; // 更新用户昵称

	// 接受的命令(响应)
	private String CMD_RESPONSE_LOGIN_RESULT = "RELG"; // 登陆结果

	// /////////////////////////////////////////////////////////////////////////////////////////////////
	// private String CMD_RESPONSE_ROOMLIST_PAGE_START = "REDPS"; //取得分页桌子详细信息开始
	// private String CMD_RESPONSE_ROOMLIST_PAGE = "REDP"; //取得分页桌子详细信息
	// private String CMD_RESPONSE_ROOMLIST_PAGE_END = "REDPE"; //取得分页桌子详细信息结束
	// /////////////////////////////////////////////////////////////////////////////////////////////////

	private String CMD_RESPONSE_ROOMLIST_START = "REDSS"; // 取得分页桌子详细信息开始
	private String CMD_RESPONSE_ROOMLIST_PAGE = "REDS"; // 取得分页桌子详细信息
	private String CMD_RESPONSE_ROOMLIST_END = "REDSE"; // 取得分页桌子详细信息结束
	private String CMD_RESPONSE_DESK_USERLIST = "SDDU"; // 取得桌子里玩家的详细信息

	private String CMD_RESPONSE_ROOMLIST_DESK_CHANGED = "SDDSS"; // 取得分页桌子变化信息
	private String CMD_RESPONSE_ONLONE_SERVER = "REOS"; // 取得所有服务器的在线人数
	private String CMD_RESPONSE_REDBAG = "RERB"; // 收到红包
	private String CMD_RESPONSE_UPDATE_GOLD = "REGB"; // 收到金币更新

	private String CMD_RESPONSE_HALL_INFO = "REDU"; // 收到声望详细信息[大厅]
	private String CMD_RESPONSE_GAME_INFO = "NTDU"; // 收到声望详细信息[游戏]

	private String CMD_RESPONSE_WATCH_ERROR = "RESE"; // 收到游戏相关信息
	private String CMD_RESPONSE_FRIEND_CHANGE = "NTAF"; // 好友信息改变
	private String CMD_RESPONSE_ACTION_FACE = "RAAF"; // 激活头像结果
	private String CMD_RESPONSE_CHANGE_FACE = "CHFCOK"; // 更换头像OK
	private String CMD_RESPONSE_SELECT_FACE = "RAHD"; // 选择头像结果
	private String CMD_RESPONSE_SORT_LIST = "RERSL"; // 牌桌玩家排名通知
	private String CMD_RESPONSE_STANDUP_RESULT = "REOT"; // 站起结果
	// 接受的命令(通知)
	private String CMD_NOTIFY_MY_INFO = "NTMI"; // 收到自己信息
	private String CMD_NOTIFY_GROUP_INFO = "NTGP"; // 组分区信息
	private String CMD_NOTIFY_CAN_NOT_TOROOM = "RQNI"; // 无法进入房间
	private String CMD_NOTIFY_RICH_CANNOT_TOROOM = "TEXXST";// 太富有了，不能进入
	private String CMD_NOTIFY_SERVER_ERROR = "REMG"; // 服务器返回提示信息
	private String CMD_NOTIFY_RESTRICT_LOGIN = "NTIP"; // 收到服务器拒绝登陆
	private String CMD_NOTIFY_UPDATAQUEUE = "NTQC"; // 更新队列信息
	private String CMD_NOTIFY_TIME_TIME = "NTTT"; // 心跳
	private String CMD_NOTIFY_GM_KICK = "GMSK"; // GM踢人
	private String CMD_NOTIFY_SITDOWN_RESULT = "REFS"; // 收到坐下结果
	private String CMD_NOTIFY_GOLD_CHANGE = "NTGC"; // 收到桌面钱改变
	private String CMD_NOTIFY_LOW_GOLD = "NTPC"; // 收到钱不够踢出房间

	private String CMD_NOTIFY_STUDY_PRIZE = "STOV"; // 收到学习教程奖励

	private String CMD_NOTIFY_USER_DEL = "ULDL"; // 收到玩家退出
	private String CMD_NOTIFY_USER_ADD = "ULAD"; // 收到玩家进入

	private String CMD_NOTIFY_INTEGRAL_JIESUAN = "REINTE"; // 收到积分结算

	private String TOURNAMENT_RECV_ADDPOINT_RESULT = "TRBURS"; // 收到竞技场买点数结果

	// 登陆送钱
	private String CMD_NOTIFY_TEX_LOGIN_SHOW_DAY_GOLD = "SHOWDAYGOLD"; // 打开登陆送钱
	private String CMD_NOTIFY_TEX_RECV_LOGIN_GIVE = "REDAYGOLD"; // 收到登陆送钱的数目

	private String CMD_NOTIFY_TEX_RECV_SHOW_FEEDBACK = "SHOWFEEDBK"; // 收到显示反馈界面

	private String CMD_NOTIFY_TEX_SHOW_CHONGZHI = "REHISMPAY"; // 收到历史最高充值金额

	// -------------
	private List m_guildMemberList;
	private ArrayList m_deskList; // 缓存桌子列表

	private SocketBase m_netptr;// 底层网络接口
	
	//玩家位置GPS
	private String CMD_REQUEST_GPS = "GPSPOSI"; // GPS坐标

	/**
	 * 构造函数
	 * 
	 * @param net_ptr
	 */
	public DNetworkGame(SocketBase net_ptr) {
		this.m_netptr = net_ptr;
		this.m_netptr.addEventListener(Event.SOCKET_DATA, this, "onDataRecv");
	}

	/**
	 * 接收数据
	 * 
	 * @param event
	 */
	public void onDataRecv(Event event) {
		// trace(e.toString());
		ReadData rd = (ReadData) event.getData();
		String strCmd = rd.getStrCmd();
		Log.i(tag, "DNetworkGame receive command: " + strCmd);
        if(CMD_MOBPAYOFF.equals(strCmd)){
        	receiveMobPayOff(rd);
        }
        else if (CMD_NOTIFY_MY_INFO.equals(strCmd)) {
			// 收到自己信息
			receiveMyInfo(rd);
		} else if (CMD_NOTIFY_GROUP_INFO.equals(strCmd)) {
			// 组分区信息
			receiveGroupInfo(rd);
		} else if (CMD_NOTIFY_SITDOWN_RESULT.equals(strCmd)) {
			// /收到坐下结果
			receiveSitDownResult(rd);
		} else if (CMD_RESPONSE_LOGIN_RESULT.equals(strCmd)) {
			// 登陆结果
			receiveLoginResult(rd);
		} else if (CMD_RESPONSE_ROOMLIST_START.equals(strCmd)) {
			// 取得分页桌子详细信息开始
			receiveRoomListStartResult(rd);
		} else if (CMD_RESPONSE_ROOMLIST_PAGE.equals(strCmd)) {
			// 取得分页桌子详细信息
			receiveRoomListPageResult(rd);
		} else if (CMD_RESPONSE_ROOMLIST_END.equals(strCmd)) {
			// 取得分页桌子详细信息结束
			receiveRoomListEndResult(rd);
		} else if (CMD_RESPONSE_DESK_USERLIST.equals(strCmd)) {
			// 收到桌子上所有人的信息
			receiveDeskUserList(rd);
		} else if (CMD_RESPONSE_ROOMLIST_DESK_CHANGED.equals(strCmd)) {
			// 取得分页桌子变化信息
			receiveDeskChanged(rd);
		} else if (CMD_NOTIFY_UPDATAQUEUE.equals(strCmd)) {
			// 更新队列信息
			receiveUpdateQueue(rd);
     	}
//      else if (CMD_NOTIFY_SERVER_ERROR.equals(strCmd)) {
//			// 服务器返回提示信息
//			receiveServerError(rd);
//		} 
		else if (CMD_NOTIFY_RESTRICT_LOGIN.equals(strCmd)) {
			// 服务器IP限制登陆
			receiveRestrictLogin(rd);
		} else if (CMD_NOTIFY_STUDY_PRIZE.equals(strCmd)) {
			// 收到学习教程奖励
			receiveStudyPrize(rd);
		} else if (CMD_NOTIFY_CAN_NOT_TOROOM.equals(strCmd)) {
			// 无法进入房间
			receiveCanNotToRoom(rd);
		} else if (CMD_NOTIFY_GM_KICK.equals(strCmd)) {
			// GM踢人
			receiveGmKick(rd);
		} else if (CMD_NOTIFY_TIME_TIME.equals(strCmd)) {
			// 心跳
			try {
				sendReplyTimeTime();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (CMD_REQUEST_TIME_TIME.equals(strCmd)) {
			// 心跳
			try {
				sendReplyTimeTime();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (CMD_RESPONSE_ONLONE_SERVER.equals(strCmd)) {
			// 在线人数
			receiveOnlineServer(rd);
		} else if (CMD_RESPONSE_WATCH_ERROR.equals(strCmd)) {
			// 收到观战失败
			receiveWatchError(rd);
		} else if (CMD_RESPONSE_UPDATE_GOLD.equals(strCmd)) {
			// 收到刷新金币
			receiveUpdateGold(rd);
		} else if (CMD_NOTIFY_GOLD_CHANGE.equals(strCmd)) {
			// 收到桌面钱改变
			receiveGoldChange(rd);
		} else if (CMD_NOTIFY_LOW_GOLD.equals(strCmd)) {
			// 收到钱不够踢出房间
			receiveLowGold(rd);
		} else if (CMD_NOTIFY_USER_ADD.equals(strCmd)) {
			// 收到玩家进入
			receiveUserAdd(rd);
		} else if (CMD_NOTIFY_USER_DEL.equals(strCmd)) {
			// 收到玩家退出
			receiveUserDel(rd);
		} else if (CMD_NOTIFY_INTEGRAL_JIESUAN.equals(strCmd)) {
			// 收到积分结算
			receiveInteGralJieSuan(rd);
		} else if (TOURNAMENT_RECV_ADDPOINT_RESULT.equals(strCmd)) {
			// 竞技场买点数结果
			receiveAddPointResult(rd);
		} else if (CMD_RESPONSE_HALL_INFO.equals(strCmd)) {
			// 收到声望详细信息[大厅]
			receiveHallInfo(rd);
		} else if (CMD_RESPONSE_GAME_INFO.equals(strCmd)) {
			// 收到声望详细信息[游戏]
			receiveGameInfo(rd);
		} else if (CMD_RESPONSE_ACTION_FACE.equals(strCmd)) {
			// 激活头像结果
			receiveActionFace(rd);
		} else if (CMD_RESPONSE_CHANGE_FACE.equals(strCmd)) {
			// 更换头像
			receiveChangeFace(rd);
		} else if (CMD_RESPONSE_SELECT_FACE.equals(strCmd)) {
			// 选择头像结果
			receiveSelectFace(rd);
		} else if (CMD_RESPONSE_SORT_LIST.equals(strCmd)) {
			// 用户排名列表
			receiveSortList(rd);
		} else if (CMD_RESPONSE_STANDUP_RESULT.equals(strCmd)) {
			// 收到站起结果
			receiveStandUpResult(rd);
		} else if (CMD_NOTIFY_TEX_BBS_URL.equals(strCmd)) {
			// 收到论坛验证码
			receiveTexBbsUrl(rd);
		} else if (CMD_NOTIFY_TEX_LOGIN_SHOW_DAY_GOLD.equals(strCmd)) {
			// 打开登陆送钱
			receiveLoginShowDayGold(rd);
		} else if (CMD_NOTIFY_TEX_RECV_LOGIN_GIVE.equals(strCmd)) {
			// 收到登陆送钱的数目
			receiveLoginGive(rd);
		} else if (CMD_NOTIFY_TEX_RECV_SHOW_FEEDBACK.equals(strCmd)) {
			// 收到显示反馈
			receiveShowFeedBack(rd);
		} else if (CMD_NOTIFY_TEX_SHOW_CHONGZHI.equals(strCmd)) {
			// 收到历史最高充值金额
			receiveShowChongZhi(rd);
		} else if (CMD_NOTIFY_TEX_PRIVILEGE_YANZHENG.equals(strCmd)) {
			// 特权码验证
			receivePrivilegeYanZheng(rd);
		} else if (CMD_TEX_CHOOSE_ROLE.equals(strCmd)) {
			// 收到显示选择角色面板
			receiveChooseRole(rd);
		} else if (CMD_NOTIFY_RICH_CANNOT_TOROOM.equals(strCmd)) {
			// 太富有了不能坐下
			receiveRichCanNotToRoom(rd);
		}else if (CMD_CHECK_NET.equals(strCmd)) {
			//接收到网速检测
			receiveCheckNet(rd);
		}else if(CMD_MO_REFRESH.equals(strCmd)){
			//接收刷新
			receiveRefresh(rd);
		} 
		else if (CMD_REQUEST_GPS.equals(strCmd)) {
			//接收玩家位置数据（GPS）
			receiveUserGPS(rd);
     	}
		else {
			Log.i(tag, "DNetworkGame not execute command: " + strCmd);
		}

	}
	
	/**接收用户GPS坐标
	 * 
	 */
	private void receiveUserGPS(ReadData rd) 
	{
		System.out.println("receiveUserGPS");

		ArrayList data = new ArrayList();
		
		int n = rd.readInt();
		PlayerLocationData locationData = null;
		
		System.out.println("LEN: "+n);
		for (int i = 0; i < n; i++) 
		{
			int nUserID = rd.readInt();
		/*	//判断是自己跳出
			if (nUserID == DConfig.userId)
			{
				System.out.println("GPS,userID是自己");
				continue;
			}*/
			locationData = new PlayerLocationData();
			locationData.setUserID(nUserID);
		//	locationData.setUserID(rd.readInt());
			locationData.setLatitudeX(rd.readString());
			locationData.setLongitudeY(rd.readString());
			locationData.setFaceurl(rd.readString());
			locationData.setNick(rd.readString());
			//locationData.setIsvip(rd.readInt());
			locationData.setGold(rd.readInt());
			locationData.setStat(rd.readInt());
			locationData.setSex(rd.readInt());
			locationData.setDate(rd.readString());
			
			data.add(locationData);
		}	
		dispatchEvent(new Event(Event.ON_RECV_GPS, data));
	}
	
	//接收到刷新
	private void receiveRefresh(ReadData rd){
		if(GameApplication.getDzpkGame() != null){
			GameApplication.getDzpkGame().restart=false;
		}
		Byte state = rd.readByte();
		Log.i("test2", "receiveRefresh state: "+state);
		if(state ==1){
			//表示需要重新登录
			GameUtil.reLogin();
			
		}else if(state ==2){
			//表示在线不做任何动作
		}
	}
	//接收到网速检测
	private void receiveCheckNet(ReadData rd){
		String time =rd.readString();
		Log.i("test4", "receiveCheckNet: time "+time);
		dispatchEvent(new Event(Event.ON_RECV_CHECK_NET,time));
	}
	private void receiveRichCanNotToRoom(ReadData rd) {
		dispatchEvent(new Event(Event.ON_RECV_RICH_CANNOT_TOROOM, null));
	}
	
	private void receiveMobPayOff(ReadData rd){
		Log.i("test18", "收到手机支付开关");
		GameApplication.mobPayOff=rd.readByte(); //0:关 1:开
		Log.i("test18", "收到手机支付开关: "+GameApplication.mobPayOff);
	}
	/**
	 * 接收到自己的消息
	 * 
	 * @param rd
	 */
	private void receiveMyInfo(ReadData rd) {
		System.out.println("start receiveMyInfo 收到自己信息");
		HashMap data = new HashMap();
		data.put("userid", rd.readString());
		data.put("usersex", rd.readByte());
		data.put("usernick", rd.readString());
		data.put("imgurl", rd.readString());
		data.put("gold", rd.readInt());
		data.put("city", rd.readString());
		data.put("channelid", rd.readInt());
		data.put("user_real_id", rd.readInt());
		data.put("md5_userid", rd.readString());
		data.put("gameexp", rd.readInt());
		data.put("cansit", rd.readInt());
		data.put("tour_point", rd.readInt());
		data.put("welcometype", rd.readInt()); // 看教程:0未看过、1看过未领奖、3看过领奖过
		data.put("canjiuji", rd.readByte()); // canjiuji
		// System.out.println("userid: "+data.get("userid"));
		// System.out.println("usersex: "+data.get("usersex"));
		// System.out.println("usernick: "+data.get("usernick"));
		// System.out.println("imgurl: "+data.get("imgurl"));
		// System.out.println("gold: "+data.get("gold"));
		// System.out.println("city: "+data.get("city"));
		// System.out.println("channelid: "+data.get("channelid"));
		// System.out.println("user_real_id: "+data.get("user_real_id"));
		// System.out.println("md5_userid: "+data.get("md5_userid"));
		// System.out.println("gameexp: "+data.get("gameexp"));
		// System.out.println("cansit: "+data.get("cansit"));
		System.out.println("end receiveMyInfo 收到自己信息");
		dispatchEvent(new Event(Event.ON_RECV_MY_INFO, data));
	}

	/**
	 * 组分区信息
	 * 
	 * @param rd
	 */
	private void receiveGroupInfo(ReadData rd) {
		System.out.println("start receiveGroupInfo 组分区信息");
		HashMap data = new HashMap();
		data.put("groupid", rd.readInt());
		data.put("isguildroom", rd.readInt());
		int len = rd.readInt();
		List list = new ArrayList();
		for (int k = 0; k < len; k++) {
			list.add(rd.readInt());
		}
		java.util.Collections.sort(list);
		data.put("guild_pelv_arr", list);
		data.put("at_least_gold", rd.readInt());
		data.put("at_most_gold", rd.readInt());
		data.put("pay_limit", rd.readInt());
		data.put("nocheat", rd.readByte());
		System.out.println("groupid: " + data.get("groupid"));
		System.out.println("isguildroom: " + data.get("isguildroom"));
		System.out.println("at_least_gold: " + data.get("at_least_gold"));
		System.out.println("at_most_gold: " + data.get("at_most_gold"));
		System.out.println("nocheat: " + data.get("nocheat"));

		System.out.println("end receiveGroupInfo 组分区信息");
		dispatchEvent(new Event(Event.ON_RECV_GROUP_INFO, data));
	}

	/**
	 * 收到坐下结果
	 * 
	 * @param rd
	 */
	private void receiveSitDownResult(ReadData rd) {
		int code = rd.readInt(); // 返回码
	    dispatchEvent(new Event(Event.ON_RECV_SITDOWN_RESULT, code));
	}

	/**
	 * 收到登录结果
	 * 
	 * @param rd
	 */
	private void receiveLoginResult(ReadData rd) {
		System.out.println("start receiveLoginResult 收到登录结果");
		HashMap data = new HashMap();
		data.put("code", rd.readShort());
		data.put("ostime", rd.readString());
		System.out.println("code: " + data.get("code"));
		System.out.println("ostime: " + data.get("ostime"));
		System.out.println("end receiveLoginResult 收到登录结果");
		dispatchEvent(new Event(Event.ON_RECV_GAME_LOGIN_RESULT, data));

	}

	/**
	 * 
	 * 取得分页桌子详细信息开始
	 * 
	 * @param rd
	 */
	private void receiveRoomListStartResult(ReadData rd) {
		m_deskList = new ArrayList();
	}

	/**
	 * 
	 * 取得分页桌子详细信息
	 * 
	 * @param rd
	 */
	private void receiveRoomListPageResult(ReadData rd) {

		int n = rd.readInt();
		DeskInfo deskinfo = null;
		for (int i = 0; i < n; i++) {

			deskinfo = new DeskInfo();
			deskinfo.setDeskno(rd.readInt());// 桌号
			deskinfo.setName(rd.readString());// 名称
			deskinfo.setDesktype(rd.readByte());// 类型:1普通，2比赛，3VIP专用
			deskinfo.setFast(rd.readByte());// 是否快速
			deskinfo.setBetgold(rd.readInt());// 桌面筹码数
			deskinfo.setUsergold(rd.readInt());// 桌子玩家筹码数
			deskinfo.setNeedlevel(rd.readInt());// 桌子解锁等级
			deskinfo.setSmallbet(rd.readInt());// 小盲
			deskinfo.setLargebet(rd.readInt());// 大盲
			deskinfo.setAt_least_gold(rd.readInt());// 金币下限
			deskinfo.setAt_most_gold(rd.readInt());// 金币上限
			deskinfo.setSpecal_choushui(rd.readInt());// 抽水
			deskinfo.setMin_playercount(rd.readByte());// 最少开局人数
			deskinfo.setMax_playercount(rd.readByte());// 最多开局人数
			deskinfo.setPlayercount(rd.readByte());// 当前在玩人数
			deskinfo.setWatchercount(rd.readInt());// 观战人数
			deskinfo.setStart(rd.readByte());// 是否开始 1=是 0=开始
			m_deskList.add(deskinfo);

		}
	}

	/**
	 * 取得分页桌子详细信息结束
	 * 
	 * @param rd
	 */
	private void receiveRoomListEndResult(ReadData rd) {
		ArrayList data = (ArrayList) m_deskList.clone();
		m_deskList.clear();
		dispatchEvent(new Event(Event.ON_RECV_CURPAGEROOM_LIST, data));

	}

	/**
	 *收到桌子上所有人的信息
	 * 
	 * @param rd
	 */
	private void receiveDeskUserList(ReadData rd) {

		HashMap data = new HashMap();
		data.put("deskno", rd.readInt());
		data.put("betgold", rd.readInt()); // 桌面筹码数
		data.put("usergold", rd.readInt()); // 桌子玩家筹码数
		data.put("playercount", rd.readByte()); // 在玩人数
		data.put("watchercount", rd.readInt()); // 观战人数
		List list = new ArrayList();
		data.put("userlist", list);
		int n = rd.readByte(); // 桌内人数
		HashMap temp = null;
		for (int i = 0; i < n; i++) {
			temp = new HashMap();
			temp.put("state_value", rd.readByte()); // 每个座位的状态 SITE_UI_VALUE =
													// _S{NULL = 0, NOTREADY =
													// 1, READY = 2, PLAYING =
													// 3}
			temp.put("userid", rd.readInt()); // ID
			temp.put("nick", rd.readString()); // 昵称
			temp.put("isvip", rd.readByte()); // 是否VIP玩家:0不是，1是
			temp.put("faceurl", rd.readString()); // 头像
			temp.put("gold", rd.readInt()); // 金币
			list.add(temp);
		}
		dispatchEvent(new Event(Event.ON_RECV_DESK_USERLIST, data));

	}

	/**
	 * 取得分页桌子变化信息
	 * 
	 * @param rd
	 */
	private void receiveDeskChanged(ReadData rd) {

		int n = rd.readInt();
		HashMap temp = null;
		HashMap siteitem = null;
		List tempList = null;
		List list = new ArrayList();
		int sitecount = 0;
		for (int i = 0; i < n; i++) {

			temp = new HashMap();
			temp.put("deskno", rd.readInt()); // 桌号
			temp.put("name", rd.readString()); // 名称
			temp.put("desktype", rd.readByte()); // 类型:1普通，2比赛，3VIP专用
			temp.put("fast", rd.readByte()); // 是否快速
			temp.put("betgold", rd.readInt()); // 桌面筹码数
			temp.put("usergold", rd.readInt()); // 桌子玩家筹码数
			temp.put("needlevel", rd.readInt()); // 桌子解锁等级
			temp.put("smallbet", rd.readInt()); // 小盲
			temp.put("largebet", rd.readInt()); // 大盲
			temp.put("at_least_gold", rd.readInt()); // 金币下限 报名费
			temp.put("at_most_gold", rd.readInt()); // 金币上限
			temp.put("specal_choushui", rd.readInt()); // 抽水
			temp.put("min_playercount", rd.readByte()); // 最少开局人数
			temp.put("max_playercount", rd.readByte()); // 最多开局人数
			temp.put("playercount", rd.readByte()); // 当前在玩人数
			temp.put("watchercount", rd.readInt()); // 观战人数
			temp.put("start", rd.readByte()); // 是否开始 1=是 0=开始
			sitecount = rd.readByte();
			temp.put("sitecount", sitecount); // 凳子数
			tempList = new ArrayList();
			temp.put("siteinfo", tempList);
			for (int j = 0; j < sitecount; j++) {
				siteitem = new HashMap();
				siteitem.put("state_value", rd.readByte()); // 每个座位的状态
															// SITE_UI_VALUE =
															// _S{NULL = 0,
															// NOTREADY = 1,
															// READY = 2,
															// PLAYING = 3}
				siteitem.put("userid", rd.readInt()); //
				siteitem.put("nick", rd.readString()); //
				siteitem.put("faceurl", rd.readString()); //
				tempList.add(siteitem);
			}
			list.add(temp);
		}
		HashMap data = new HashMap();
		data.put("list", list);
		data.put("page", rd.readShort()); // 当前页
		data.put("totalpage", rd.readShort());// 共多少页
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_CURPAGEROOM_CHANGED,
		// data));

	}

	/**
	 * 更新队列信息
	 * 
	 * @param rd
	 */
	private void receiveUpdateQueue(ReadData rd) {

		int queueplayer = rd.readInt();
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_QUEUE_INFO,queueplayer));

	}

	/**
	 * 
	 * 收到服务器返回错误提示:0xx大厅的，1xx游戏里的
	 * 
	 * @param rd
	 */
	private void receiveServerError(ReadData rd) {
//		HashMap data = new HashMap();
//		int msgtype = rd.readByte();
//		data.put("msgtype", msgtype);
//		data.put("msg", rd.readString());
//		if (msgtype == 0) {
//			// this.dispatchEvent(new
//			// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_SERVER_ERROR, data));
//		}
	}

	/**
	 * 服务器IP限制登陆
	 * 
	 * @param rd
	 */
	private void receiveRestrictLogin(ReadData rd) {
		System.out.println("start receiveRestrictLogin 收到服务器限制登陆");
		HashMap data = new HashMap();
		data.put("retcode", rd.readInt()); // 是否可以登录
		data.put("msg", rd.readString()); // 返回消息
		System.out.println("retcode: " + data.get("retcode"));
		System.out.println("msg: " + data.get("msg"));
		System.out.println("end receiveRestrictLogin 收到服务器限制登陆");
		dispatchEvent(new Event(Event.ON_RECV_RESTRICT_LOGIN, data));

	}

	/**
	 * 收到学习教程奖励
	 * 
	 * @param rd
	 */
	private void receiveStudyPrize(ReadData rd) {

		HashMap data = new HashMap();
		data.put("userid", rd.readInt());
		data.put("addgold", rd.readInt());
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_STUDY_PRIZE, data));

	}

	/**
	 * 无法进入房间
	 * 
	 * @param rd
	 */
	private void receiveCanNotToRoom(ReadData rd) {
		//Log.i("test4", "receiveCanNotToRoom");
		HashMap data = new HashMap();
		data.put("IntoRoomStats", rd.readByte());
		data.put("gold", rd.readInt());
		this.dispatchEvent(new Event(Event.ON_RECV_CAN_NOT_TOROOM, data));

	}

	/**
	 * GM踢人
	 * 
	 * @param rd
	 */
	private void receiveGmKick(ReadData rd) {

		int code = rd.readInt();
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_GM_KICK, code));

	}

	/**
	 * 在线人数
	 * 
	 * @param rd
	 */
	private void receiveOnlineServer(ReadData rd) {

		HashMap data = new HashMap();
		String groupid = "";
		while (!(groupid = rd.readString()).equals("")) {
			data.put(groupid, rd.readInt()); // 在线人数
		}
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_ONLINE_SERVER, data));

	}

	/**
	 * 收到观战失败
	 * 
	 * @param rd
	 */
	private void receiveWatchError(ReadData rd) {

		 
		HashMap data = new HashMap();
		int errorcode = rd.readByte();
		data.put("errorcode", errorcode);

		if (errorcode == -2) {
			data.put("userid", rd.readInt());
			data.put("roomid", rd.readInt());
		}
	    this.dispatchEvent(new Event(Event.ON_RECV_WATCH_ERROR, data));

	}

	/**
	 * 收到刷新金币
	 * 
	 * @param rd
	 */
	private void receiveUpdateGold(ReadData rd) {
		//HashMap data = new HashMap();
		//data.put("gold", rd.readInt());
		//data.put("canjiuji", rd.readByte()); // 是否可以领取救济:0不行，1可以
		int gold =rd.readInt();
		byte canjiuji = rd.readByte();
		if(GameApplication.userInfo != null){
			GameApplication.userInfo.put("gold", gold);
		   Log.i("test8", "canjiuji gold: "+gold);
			if(GameApplication.currentActivity instanceof DzpkGameMenuActivity){
				((DzpkGameMenuActivity)GameApplication.currentActivity).setPlayerChoumaText(gold,true);
			}
		}
		//dispatchEvent(new Event(Event.ON_RECV_UPDATE_GOLD, data));
	}

	/**
	 * 收到桌面钱改变
	 * 
	 * @param rd
	 */
	private void receiveGoldChange(ReadData rd) {
		HashMap data = new HashMap();
		data.put("site", rd.readInt()); // 座位号
		data.put("gold", rd.readInt()); // 新钱
		dispatchEvent(new Event(Event.ON_RECV_CHANGE_GOLD, data));
	}

	/**
	 *收到钱不够踢出房间
	 * 
	 * @param rd
	 */
	private void receiveLowGold(ReadData rd) {

		HashMap data = new HashMap();
		data.put("retcode", rd.readInt()); // 返回码
		data.put("gold", rd.readInt()); // 钱限制
		this.dispatchEvent(new Event(Event.ON_RECV_LOW_GOLD, data));
	}

	/**
	 *收到玩家进入
	 * 
	 * @param rd
	 */
	private void receiveUserAdd(ReadData rd) {
		HashMap data = new HashMap();
		data.put("userid", rd.readInt()); // 用户的数据库ID
		data.put("nick", rd.readString()); // 昵称
		data.put("faceurl", rd.readString()); // 头像url
		data.put("gold", rd.readInt()); // 金币数目
		data.put("exp", rd.readInt()); // 经验值
		data.put("prestige", rd.readInt()); // 声望
		data.put("integral", rd.readInt()); // 荣誉
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_USER_ADD, data));

	}

	/**
	 * 收到玩家退出
	 * 
	 * @param rd
	 */
	private void receiveUserDel(ReadData rd) {
		int userid = rd.readInt();
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_USER_DEL, userid));

	}

	/**
	 * 收到积分结算
	 * 
	 * @param rd
	 */
	private void receiveInteGralJieSuan(ReadData rd) {
		HashMap data = new HashMap();
		data.put("all", rd.readInt());
		data.put("normal", rd.readInt());
		data.put("extra", rd.readInt());
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_INTEGRAL_JIESUAN, data));

	}

	/**
	 * 竞技场买点数结果
	 * 
	 * @param rd
	 */
	private void receiveAddPointResult(ReadData rd) {
		HashMap data = new HashMap();
		data.put("result", rd.readInt());
		data.put("newpoint", rd.readInt());
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_TOUR_ADDPOINT_RESULT,
		// data));
	}

	// 收到声望详细信息[大厅]
	private void receiveHallInfo(ReadData rd) {
		System.out.println("start receiveHallInfo 收到声望详细信息[大厅]");
		HashMap data = new HashMap();
		data.put("prestige", rd.readInt());
		data.put("ccPoint", rd.readInt());
		data.put("exp", rd.readInt());
		data.put("level", rd.readInt());
		System.out.println("prestige" + data.get("prestige"));
		System.out.println("ccPoint" + data.get("ccPoint"));
		System.out.println("exp" + data.get("exp"));
		System.out.println("level" + data.get("level"));
		System.out.println("end receiveHallInfo 收到声望详细信息[大厅]");
		dispatchEvent(new Event(Event.ON_RECV_USER_HALL_GAMEINFO, data));

	}

	/**
	 * 收到声望详细信息[游戏]
	 * 
	 * @param rd
	 */
	private void receiveGameInfo(ReadData rd) {
		HashMap data = new HashMap();
		data.put("siteno", rd.readInt());
		data.put("userid", rd.readInt());
		data.put("prestige", rd.readInt());
		data.put("ccPoint", rd.readInt());
		data.put("experience", rd.readInt());
		data.put("level", rd.readInt());
		data.put("isrelogin", rd.readByte());
		dispatchEvent(new Event(Event.ON_RECV_USER_GAME_GAMEINFO, data));

	}

	// 激活头像结果
	private void receiveActionFace(ReadData rd) {
		HashMap data = new HashMap();
		data.put("success", rd.readInt());
		data.put("faceid", rd.readInt());
		data.put("price", rd.readInt());
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_ACTIVE_FACE, data));

	}

	/**
	 * 更换头像
	 * 
	 * @param rd
	 */
	private void receiveChangeFace(ReadData rd) {

		String face = rd.readString();
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_CHANGE_FACE_OK, face));

	}

	/**
	 *选择头像结果
	 * 
	 * @param rd
	 */
	private void receiveSelectFace(ReadData rd) {

		HashMap data = new HashMap();
		data.put("notshowextra", rd.readInt());
		int extrafacenum = rd.readInt();
		data.put("extrafacenum", extrafacenum);
		List list = new ArrayList();
		data.put("extrafaces", list);
		for (int i = 0; i < extrafacenum; i++) {
			list.add(rd.readInt());
		}
		int vipfacenum = rd.readInt();
		data.put("vipfacenum", vipfacenum);
		List vipList = new ArrayList();
		data.put("vipfaces", vipList);
		for (int i = 0; i < vipfacenum; i++) {
			vipList.add(rd.readInt());
		}
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_SELECT_FACE_OK, data));

	}

	/**
	 * 用户排名列表 收到来自服务器的玩家排名数据
	 * 
	 * @param rd
	 */
	private void receiveSortList(ReadData rd) {

		HashMap data = new HashMap();
		data.put("type", rd.readString());
		List listdata = new ArrayList();
		int len = rd.readShort();
		HashMap item = null;
		for (int i = 0; i < len; i++) {

			item = new HashMap();
			item.put("userid", rd.readInt()); // 用户的数据库ID
			item.put("nick", rd.readString()); // 昵称
			item.put("faceurl", rd.readString()); // 头像url
			item.put("gold", rd.readInt()); // 金币数目
			item.put("exp", rd.readInt()); // 经验值
			item.put("prestige", rd.readInt()); // 声望
			item.put("integral", rd.readInt()); // 荣誉
			listdata.add(item);
		}
		data.put("listdata", listdata);
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_SORT_LIST, data));

	}

	/**
	 * 收到站起结果
	 * 
	 * @param rd
	 */
	private void receiveStandUpResult(ReadData rd) {
		int result = rd.readShort(); // 结果
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_STAND_UP_RESULT, result));

	}

	/**
	 * 收到论坛验证码
	 * 
	 * @param rd
	 */
	private void receiveTexBbsUrl(ReadData rd) {
		String bbsUrl = rd.readString(); // BBS验证串
		dispatchEvent(new Event(Event.ON_TEX_RECV_BBS_URL, bbsUrl));

	}

	/**
	 *打开登陆送钱
	 * 
	 * @param rd
	 */
	private void receiveLoginShowDayGold(ReadData rd) {
		 
		// System.out.println("start receiveLoginShowDayGold 打开登陆送钱");
		// HashMap data=new HashMap();
         int temp =rd.readInt();
		//GameApplication.showDayGoldResult = temp;
		//Log.i("test3", "receiveLoginShowDayGold: showDayGoldResult: "
		//		+ GameApplication.showDayGoldResult +"  temp: "+temp);
		// data.put("result",result); //-1，IP超限，1成功，其他未知错误
		// data.put("maxviplevel",rd.readInt()); //最高VIP等级
		// data.put("vipadd",rd.readInt()); //vip金币加成
		// System.out.println("result: "+data.get("result"));
		// System.out.println("maxviplevel: "+data.get("maxviplevel"));
		// System.out.println("vipadd: "+data.get("vipadd"));
		// System.out.println("end receiveLoginShowDayGold 打开登陆送钱");
		// 
		rd.clear();
		dispatchEvent(new Event(Event.ON_TEX_LOGIN_SHOW_DAY_GOLD, temp));

	}

	/**
	 * 收到登陆送钱的数目
	 * 
	 * @param rd
	 */
	private void receiveLoginGive(ReadData rd) {
		HashMap data = new HashMap();
	
		data.put("success", rd.readByte());
		int gold =rd.readInt();
		data.put("gold", gold); // 本身领到的筹码500
		int vipGold = rd.readInt();
		data.put("vipadd", vipGold); // vip加成888，1888，3888
		data.put("charmlevel", rd.readInt()); // 农场魅力等级
		data.put("charmadd", rd.readInt()); // 农场魅力加成
		int taskGold = rd.readInt();
		data.put("vtask_add",taskGold); // V任务赠送筹码
		Log.i("test3", "gold: "+gold+" vipGold: "+vipGold+" taskGold: "+taskGold);
		gold = gold+vipGold+taskGold;
		Log.i("test3", "gold: "+gold);
		data.put("allGold", gold);
		dispatchEvent(new Event(Event.ON_TEX_RECV_LOGIN_GIVE, data));

	}

	/**
	 * 收到显示反馈
	 * 
	 * @param rd
	 */
	private void receiveShowFeedBack(ReadData rd) {

		int nFlag = rd.readByte(); // 是否反馈：0, 不能用反馈；1：可以用反馈
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_TEX_RECV_FEEDBACK_CAN_USE,
		// data));

	}

	/**
	 * 收到历史最高充值金额
	 * 
	 * @param rd
	 */
	private void receiveShowChongZhi(ReadData rd) {

		int historyMaxpay = rd.readInt(); // 历史最高的单次充值金额
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_TEX_RECV_SHOW_CHONGZHI,
		// data));

	}

	/**
	 * 特权码验证
	 * 
	 * @param rd
	 */
	private void receivePrivilegeYanZheng(ReadData rd) {
		HashMap data = new HashMap();
		data.put("checkRs", rd.readByte());
		data.put("checkMsg", rd.readString());
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_TEX_RECV_PRIVILEGE_CHECK,
		// data));

	}

	/**
	 * 收到选择角色
	 * 
	 * @param rd
	 */
	private void receiveChooseRole(ReadData rd) {
		System.out.println("start receiveChooseRole 收到显示选择角色面板");
		HashMap data = new HashMap();
		data.put("isShow", rd.readByte());
		data.put("sex", rd.readByte());
		data.put("nick", rd.readString());
		System.out.println("isShow: " + data.get("isShow"));
		System.out.println("sex: " + data.get("sex"));
		System.out.println("nick: " + data.get("nick"));
		System.out.println("end receiveChooseRole 收到显示选择角色面板");
		dispatchEvent(new Event(Event.ON_RECV_CHOOSE_SHOW, data));
	}

	/**
	 * 发送请求游戏服务器登录
	 * 
	 * @param strUsername
	 * @param strPassword
	 */
	public void sendRequestLoginGame(int userId, String strPassword)
			throws Exception {
		Log.i("DNetworkGame","DNetworkGame sendRequestLoginGame Command: "+ CMD_REQUEST_LOGIN);
		m_netptr.writeString(CMD_REQUEST_LOGIN);
		m_netptr.writeInt(userId);
		m_netptr.writeString(strPassword);
		m_netptr.writeByte((byte) DConfig.nRegSit); // from YY 1
		m_netptr.writeString(DConfig.strLoginType);
		m_netptr.writeEnd();
	}

	/**
	 * 发送自动加入游戏请求
	 * 
	 * @param deskno
	 */
	public void sendRequestAutoJoin(int deskno) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestAutoJoin Command: "
				+ CMD_REQUEST_AUTO_JOIN);
		m_netptr.writeString(CMD_REQUEST_AUTO_JOIN);
		m_netptr.writeInt(deskno);
		m_netptr.writeInt(1);
		m_netptr.writeEnd();
	}

	/**
	 * 发送请求排队
	 */
	public void sendRequestPaiDui() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestPaiDui Command: "
				+ CMD_REQUEST_PAIDUI);
		m_netptr.writeString(CMD_REQUEST_PAIDUI);
		m_netptr.writeEnd();
	}

	/**
	 * 发送请求退队
	 */
	public void sendRequestUnqueue() throws Exception {
		// DTrace.traceex("sendRequestUnqueue");
		Log.i(tag, "DNetworkGame sendRequestUnqueue Command: "
				+ CMD_REQUEST_UNQUEUE);

		m_netptr.writeString(CMD_REQUEST_UNQUEUE);
		m_netptr.writeEnd();
	}

	/**
	 * 请求房间的桌子列表
	 * 
	 * @param chang
	 * @param tab
	 * @param hidenull
	 * @param hidefull
	 * @param isfast
	 * @param isStart
	 */
	public void sendRequestDeskList(int chang, int tab, int hidenull,
			int hidefull, int isfast, int isStart) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestDeskList Command: "
				+ CMD_REQUEST_ROOM_DESKLIST);

		m_netptr.writeString(CMD_REQUEST_ROOM_DESKLIST);
		m_netptr.writeShort((short) chang);
		m_netptr.writeShort((short) tab);
		m_netptr.writeByte((byte) hidenull);
		m_netptr.writeByte((byte) hidefull);
		m_netptr.writeByte((byte) isfast);
		m_netptr.writeInt(isStart);
		m_netptr.writeEnd();
	}

	/**
	 * 发送请求房间下一页
	 * 
	 * @param page
	 * @param pagesize
	 * @param hidenull
	 * @param hidefull
	 */
	public void sendRequestPage(int page, int pagesize, int hidenull,
			int hidefull) throws Exception {

		// 这个函数已经废弃不用
		m_netptr.writeString(CMD_REQUEST_ROOMLIST_PAGE);
		m_netptr.writeShort((short) page);
		m_netptr.writeShort((short) pagesize);
		m_netptr.writeByte((byte) hidenull);
		m_netptr.writeByte((byte) hidefull);
		m_netptr.writeEnd();
	}

	/**
	 * 请求桌子的玩家列表
	 */
	public void sendRequestDeskUser(int deskno) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestDeskUser Command: SDDU");
		m_netptr.writeString("SDDU");
		m_netptr.writeInt(deskno);
		m_netptr.writeEnd();
	}

	/**
	 * 发送自己站起来
	 */
	public void sendRequestStandup() throws Exception {
		// DTrace.traceex("发送自己站起来,sendRequestStandup()");
		Log.i(tag, "DNetworkGame sendRequestStandup Command: RQSU");

		m_netptr.writeString(CMD_REQUEST_STANDUP);
		m_netptr.writeEnd();
	}

	/**
	 * 发送激活头像
	 * 
	 * @param faceurl
	 * @param sure
	 */
	public void sendRequestActiveExtraFace(String faceurl, int sure)
			throws Exception {
		// DTrace.traceex("发送激活头像");
		Log.i(tag, "DNetworkGame sendRequestActiveExtraFace Command: "
				+ CMD_REQUEST_ACTIVE_FACE);
		m_netptr.writeString(CMD_REQUEST_ACTIVE_FACE);
		m_netptr.writeString(faceurl);
		m_netptr.writeInt(sure);
		m_netptr.writeEnd();
	}

	/**
	 * 发送离开牌桌大厅
	 */
	public void sendClientLeaveRoom() throws Exception {
		Log.i(tag, "DNetworkGame sendClientLeaveRoom Command: "
				+ CMD_REQUEST_CLIENT_LEAVE_ROOM);

		m_netptr.writeString(CMD_REQUEST_CLIENT_LEAVE_ROOM);
		m_netptr.writeEnd();
	}

	/**
	 * 发送改变头像
	 * 
	 * @param faceUrl
	 */
	public void sendRequestChangeFace(String faceUrl) throws Exception {
		// DTrace.traceex("发送改变头像");
		Log.i(tag, "DNetworkGame sendRequestChangeFace Command: "
				+ CMD_REQUEST_CHANGE_FACE);

		m_netptr.writeString(CMD_REQUEST_CHANGE_FACE);
		m_netptr.writeString(faceUrl);
		m_netptr.writeEnd();
	}

	/**
	 * 心跳
	 */
	private void sendReplyTimeTime() throws Exception {
		Log.i("GameTime", "DNetworkGame sendReplyTimeTime Command: "+CMD_REQUEST_TIME_TIME);
		m_netptr.writeString(CMD_REQUEST_TIME_TIME);
		m_netptr.writeEnd();
	}

	/**
	 * 查选所有服务器的在线人数
	 */
	public void sendRequestOnlinePlayerCountAllServer() throws Exception {
		Log.i(tag,
				"DNetworkGame sendRequestOnlinePlayerCountAllServer Command: "
						+ CMD_REQUEST_ONLONE_SERVER);

		m_netptr.writeString(CMD_REQUEST_ONLONE_SERVER);
		m_netptr.writeEnd();
	}

	/**
	 * 请求服务器送钱
	 */
	public void sendRequestGiveGold() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestGiveGold Command: "
				+ CMD_REQUEST_GIVE_GOLD);
		m_netptr.writeString(CMD_REQUEST_GIVE_GOLD);
		m_netptr.writeEnd();
	}

	/**
	 * 请求服务器刷新钱
	 * 
	 */
	public void sendUpdateGold() throws Exception {
		Log.i(tag, "DNetworkGame sendUpdateGold Command: "
				+ CMD_REQUEST_UPDATE_GOLD);

		m_netptr.writeString(CMD_REQUEST_UPDATE_GOLD);
		m_netptr.writeEnd();
	}

	public void sendUpdateVipInfo() throws Exception {
		Log.i(tag, "DNetworkGame sendUpdateVipInfo Command: "
				+ CMD_REQUEST_UPDATE_VIPINFO);

		m_netptr.writeString(CMD_REQUEST_UPDATE_VIPINFO);
		m_netptr.writeEnd();
	}

	/**
	 * 发送请求论坛字串
	 */
	public void sendRequestURL() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestURL Command: "
				+ CMD_NOTIFY_TEX_BBS_URL);
		m_netptr.writeString(CMD_NOTIFY_TEX_BBS_URL);
		m_netptr.writeEnd();
	}

	/**
	 * 用户请求观战
	 * 
	 * @param deskno
	 */
	public void sendRequestWatch(int deskno) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestWatch Command: "
				+ CMD_REQUEST_WATCH);
		m_netptr.writeString(CMD_REQUEST_WATCH);
		m_netptr.writeInt(deskno);
		m_netptr.writeEnd();
	}

	/**
	 * 用户请求退出观战
	 */
	public void sendRequestExitWatch() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestExitWatch Command: "
				+ CMD_REQUEST_EXIT_WATCH);
		m_netptr.writeString(CMD_REQUEST_EXIT_WATCH);
		m_netptr.writeEnd();
	}

	/**
	 * 客户端请求房间玩家排名
	 * 
	 * @param strType
	 */
	public void sendRequestRoomSortList(String strType) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestRoomSortList Command: "
				+ CMD_REQUEST_ROOM_SORT_LIST);
		m_netptr.writeString(CMD_REQUEST_ROOM_SORT_LIST);
		m_netptr.writeString(strType);
		m_netptr.writeEnd();
	}

	/**
	 * 客户端请求房间全部玩家列表
	 */
	public void sendRequestRoomUserList() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestRoomUserList Command: RQASL");
		m_netptr.writeString("RQASL");
		m_netptr.writeEnd();
	}

	/**
	 * 发送离开游戏
	 */
	public void sendExitGame() throws Exception {
		Log.i(tag, "DNetworkGame sendExitGame Command: RQUL");

		m_netptr.writeString("RQUL");
		m_netptr.writeEnd();
	}

	/**
	 * 发送选择游戏
	 */
	public void sendSelectedGame() throws Exception {
		Log.i(tag, "DNetworkGame sendSelectedGame Command: RQUE");
		m_netptr.writeString("RQUE");
		m_netptr.writeEnd();
	}

	// 发送选头像
	public void sendRequestSelectFace() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestSelectFace Command: RAHD");
		m_netptr.writeString("RAHD");
		m_netptr.writeEnd();
	}

	// 发送看完教程
	public void sendStudyOver() throws Exception {
		Log.i(tag, "DNetworkGame sendStudyOver Command: STOV");
		m_netptr.writeString("STOV");
		m_netptr.writeEnd();
	}

	// 发送领取救济
	public void sendRequestGiveChouMa() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestGiveChouMa Command: RQGIVE");
		m_netptr.writeString("RQGIVE");
		m_netptr.writeEnd();
	}

	/**
	 * 发送坐下
	 * 
	 * @param deskno
	 * @param siteno
	 * @param peilv
	 */
	public void sendSitDown(int deskno, int siteno, int peilv) throws Exception {
		Log.i(tag, "DNetworkGame sendSitDown Command: RQSD");
		m_netptr.writeString("RQSD");
		m_netptr.writeInt(deskno);
		m_netptr.writeInt(siteno);
		m_netptr.writeInt(peilv);
		m_netptr.writeEnd();
	}

	public void tour_sendBuyPoint() throws Exception {
		Log.i(tag, "DNetworkGame tour_sendBuyPoint Command: TRBUGD");
		m_netptr.writeString("TRBUGD");
		m_netptr.writeEnd();
	}

	public void sendRequestLoginLingQu() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestLoginLingQu Command: RQDAYGOLD");
		m_netptr.writeString("RQDAYGOLD");
		m_netptr.writeEnd();
	}

	public void sendRequestFeedBack() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestFeedBack Command: SHOWFEEDBK");
		m_netptr.writeString("SHOWFEEDBK");
		m_netptr.writeEnd();
	}

	public void sendRequestShowChongzhi() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestShowChongzhi Command: "
				+ CMD_NOTIFY_TEX_SHOW_CHONGZHI);
		m_netptr.writeString(CMD_NOTIFY_TEX_SHOW_CHONGZHI);
		m_netptr.writeEnd();
	}

	// -------------------特权码str------------------------
	public void sendRequestPrivilegeCheck(String code) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestPrivilegeCheck Command: "
				+ CMD_NOTIFY_TEX_PRIVILEGE_YANZHENG);

		m_netptr.writeString(CMD_NOTIFY_TEX_PRIVILEGE_YANZHENG);
		m_netptr.writeString(code);
		m_netptr.writeEnd();
	}

	// -------------------特权码end------------------------

	/**
	 * 更新频道信息
	 * 
	 * @throws Exception
	 */
	public void sendRequestUpdateChannelInfo(String strChannelId)
			throws Exception {
		Log.i(tag, "DNetworkGame sendRequestUpdateChannelInfo Command: "
				+ CMD_TEX_UPDATE_CHANNEL_INFO);
		m_netptr.writeString(CMD_TEX_UPDATE_CHANNEL_INFO);
		m_netptr.writeString(strChannelId);
		m_netptr.writeEnd();
	}

	public void sendRequestUpdateUserChannelInfo(HashMap userInfo)
			throws Exception {

		Log.i(tag, "DNetworkGame sendRequestUpdateUserChannelInfo Command: "
				+ CMD_TEX_UPDATE_NICK_INFO);
		m_netptr.writeString(CMD_TEX_UPDATE_NICK_INFO);
		m_netptr.writeString((String) userInfo.get("nick"));
		m_netptr.writeInt((Integer) userInfo.get("sex"));
		m_netptr.writeEnd();
	}
	/**
	 * 发送网速检测
	 * @throws Exception
	 */
	public void sendCheckNet(String currentTime) throws Exception{
		
		m_netptr.writeString(CMD_CHECK_NET);
		m_netptr.writeString(currentTime);
		m_netptr.writeEnd();
	}
	/**
	 * 登录发送手机信息
	 * model:手机型号
	 * screen:屏幕分辩率如640*960
	 * @throws Exception
	 */
	public void sendMobLogin(String model,String screen) throws Exception{
		
		m_netptr.writeString(CMD_MOBLOGIN);
		m_netptr.writeInt(2);
		m_netptr.writeString(model);
		m_netptr.writeString(screen);
		m_netptr.writeEnd();
		Log.i("test18", "model: "+model+"  screen: "+screen);
	}
    /**
     * 请求刷新
     * @param userId
     * @throws Exception
     */
	public void sendRefresh(int userId)throws Exception {
		Log.i("test5", "sendRefresh userId: "+userId);
		m_netptr.writeString(CMD_MO_REFRESH);
		m_netptr.writeInt(userId);
		m_netptr.writeEnd();
	}
	
	/**
	 * 用户发送自己GPS坐标
	 * 
	 */
	public void sendGPS(String strMyLatitudeX, String strMyLongitudeY,int type) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestWatch Command: "
				+ CMD_REQUEST_GPS);
		m_netptr.writeString(CMD_REQUEST_GPS);
		m_netptr.writeString(strMyLatitudeX);
		m_netptr.writeString(strMyLongitudeY);
		m_netptr.writeByte((byte)type);
		m_netptr.writeEnd();
	}
}
