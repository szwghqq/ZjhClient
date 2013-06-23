package com.dozengame.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import android.util.Log;

import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.event.EventDispatcher;
import com.dozengame.event.TexEventType;
import com.dozengame.event.ZjhEventType;
import com.dozengame.net.pojo.DConfig;
import com.dozengame.net.pojo.DGroupInfoItem;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.DUtils;
import com.dozengame.util.GameUtil;

public class SocketService extends EventDispatcher implements CallBack {

	final static String tag = "SocketService";
	private static String ip = "192.168.1.3";
	private static int port = 6000;

	public static final String CMD_REQUEST_LOGIN = "RQLG";// 请求登陆
	private static final String CMD_REQUEST_GROUP_INFO = "RQGI";// 请求获取房间列表
	public static String sendGetAreaInfo = "dznew|ddz|zjh|mj|soha|tlj|sdh|pdk|dz2|lzdz|cdd|hldz|tex|zysz";
	SocketBase gcSocket;
	SocketBase gsSocket;
	DNetworkCenter dnc;
	DNetworkGame dnGame;
	DNetworkZjh dntex;
	DNetworkGameFriend dnfriend;
	DNetworkVip dnvip;

	public SocketService() {

	}

	public SocketService(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	/**
	 * 检测网络连接
	 * 
	 * @return
	 */
	public boolean checkNetConnection() {
		try {
			gcSocket = new SocketBase(ip, port, true);
			gcSocket.CreateConnection();
			if (gcSocket.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			GameUtil.openNetErrorMsg();
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 关闭与中心的连接
	 */
	public void shutDownGCenter() {
		removeCenterEventListener();
		if (gcSocket != null) {
			gcSocket.shutDownConnection();
		}
	}

	/**
	 * 关闭与游戏服务端的连接
	 */
	public void shutDownGServer() {

		removeTexEventListener();
		removeGameEventListener();
		removeVipEventListener();
		if (gsSocket != null) {
			gsSocket.shutDownConnection();
		}
	}

	/**
	 * 发送登录
	 * 
	 * @param strUser
	 * @param strKey
	 * @throws Exception
	 */
	public void sendLogin(String strUser, String strKey) throws Exception {

		if (gcSocket.isConnected()) {
			dnc = new DNetworkCenter(gcSocket);
			// 添加中心服务器侦听事件
			addCenterEventListener();
			sendGetAreaInfo(gcSocket, sendGetAreaInfo);
			sendLoginInfo(gcSocket, strUser, strKey);
			new Thread(gcSocket).start();
		}

	}

	/**
	 * 中心服务器连接已关闭
	 */
	public void centerNetClose(Event e) {
		GameUtil.openNetErrorMsg();
	}

	/**
	 * 游戏服务器连接已关闭
	 */
	public void gameNetClose(Event e) {
		GameUtil.openNetErrorMsg();
	}

	/**
	 * 发送网速检测
	 * 
	 * @throws Exception
	 */
	public void sendCheckNet(String currentTime) throws Exception {
		Log.i("test4", "sendCheckNet");
		dnGame.sendCheckNet(currentTime);
	}

	/**
	 * 发送自动加入游戏
	 * 
	 * @throws Exception
	 */
	public void sendRequestAutoJoin() throws Exception {
		dnGame.sendRequestAutoJoin(-1);
	}

	/**
	 * 发送购买筹码命令
	 * 
	 * @throws Exception
	 */
	public void sendRequestTXNTBC() throws Exception {
		// dntex.sendRequestTXNTBC();
	}

	/**
	 * 发送快速开始
	 * 
	 * @param chouma
	 */
	public void sendRequestQuickStart(int chouma) throws Exception {
		// dntex.sendRequestQuickStart(chouma);
	}

	/**
	 * 请求返回大厅
	 * 
	 * @throws Exception
	 */
	public void sendRequestBackHall() throws Exception {
		dntex.sendRequestBackHall();
	}

	/**
	 * 请求站起
	 * 
	 * @throws Exception
	 */
	public void sendRequestStandUp() throws Exception {
		dnGame.sendRequestStandup();
	}

	public void onDataRecv(Event e) {
		try {

			dnc.onDataRecv(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 发送自己GPS坐标
	 * 
	 * @throws Exception
	 * 
	 */
	public void sendGPS(String strMyLatitudeX, String strMyLongitudeY, int type) throws Exception {
		dnGame.sendGPS(strMyLatitudeX, strMyLongitudeY, type);
	}

	/**
	 * 发送取得分区信息
	 * 
	 * @param strGameName
	 */
	public static void sendGetAreaInfo(SocketBase socket, String strGameName) {

		try {
			DUtils.wirteToLogFile(" sendGetAreaInfo: " + strGameName);
			System.out.println("sendGetAreaInfo");
			socket.writeString(CMD_REQUEST_GROUP_INFO);
			socket.writeString(strGameName);
			socket.writeEnd();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 发送登录领取
	 * 
	 * @throws Exception
	 */
	public void sendRequestLoginLingQu() throws Exception {
		dnGame.sendRequestLoginLingQu();
	}

	/**
	 * 发送登录信息－－－－－－－封测代码
	 * 
	 * @param strUsername
	 * @param strPassword
	 */
	public static void sendLoginInfo(SocketBase socket, String strUsername, String strPassword) {
		System.out.println("sendLoginInfo:" + strUsername + " password=" + strPassword);
		try {
			DConfig.strUser = strUsername;

			socket.writeString(CMD_REQUEST_LOGIN);
			socket.writeString(strUsername);
			socket.writeString(strPassword);
			// socket.writeByte((byte) 0);
			// socket.writeString("1");
			socket.writeByte((byte) 1);// YY通行证
			socket.writeString("999");
			socket.writeEnd();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 保存一个分区的所有房间
	ArrayList<DGroupInfoItem> tempList = null;

	/**
	 * 接收到所有房间信息
	 * 
	 * @param e
	 */
	public void recvGroupInfo(Event e) {
		// /DUtils.wirteToLogFile(" recvGroupInfo " + e.getEventName());
		System.out.println(" recvGroupInfo " + e.getEventName());
		// System.out.println(" 接收到所有房间信息 " + e.getEventName());
		HashMap data = (HashMap) e.getData();
		Set entry = data.entrySet();
		Iterator it = entry.iterator();
		Entry temp = null;

		while (it.hasNext()) {
			temp = (Entry) it.next();
			System.out.println("name: " + temp.getKey());
			tempList = (ArrayList<DGroupInfoItem>) temp.getValue();
			if (tempList != null) {
				int size = tempList.size();
				for (int i = 0; i < size; i++) {
					tempList.get(i).toString();
				}
			}
		}
		// callBack.CallBack(tempList);
		e.setData(tempList);
		// 传递至界面
		dispatchEvent(e);
	}

	HashMap userInfo = null;

	/**
	 * 登录结果
	 * 
	 * @param e
	 */
	public void recvCenterLoginResult(Event e) {

		userInfo = (HashMap) e.getData();
		DConfig.strKey = (String) userInfo.get("gamekey");
		DConfig.userId = (Integer) userInfo.get("userid");
		dispatchEvent(e);
		// DConfig.strKey = (String) userInfo.get("md5");
		// dispatchEvent(e);
		// userInfo = (HashMap) e.getData();
		// DConfig.strKey = (String) userInfo.get("gamekey");
		// System.out.println("recvCenterLoginResult  gamekey: "+DConfig.strKey);
		// if (tempList != null && !tempList.isEmpty()) {
		// changRoom(tempList.get(0));
		// }
	}

	/**
	 * 变更房间
	 * 
	 * @param item
	 */
	public void changRoom(DGroupInfoItem item) {
		System.out.println("item.ip=" + item.ip);
		System.out.println("item.port=" + item.port);

		try {
			gsSocket = new SocketBase(item.ip, item.port);
			gsSocket.CreateConnection();
			if (gsSocket.isConnected()) {
				Log.i("test2", "connect success: " + item.ip + ":" + item.port);
				dnGame = new DNetworkGame(gsSocket);
				dntex = new DNetworkZjh(gsSocket);
				dnfriend = new DNetworkGameFriend(gsSocket);
				dnvip = new DNetworkVip(gsSocket);
				addGameEventListener();
				addTexEventListener();
				addVipEventListener();
				Log.i("test2", "user: " + DConfig.userId + "   key: " + DConfig.strKey);
				dnGame.sendRequestLoginGame(DConfig.userId, DConfig.strKey);
				new Thread(gsSocket).start();
			}
		} catch (Exception e) {
			GameUtil.openNetErrorMsg();
			e.printStackTrace();
		}
	}

	/**
	 * 发送购买筹码
	 * 
	 * @param gold
	 * @param deskNo
	 * @param sitNo
	 * @throws Exception
	 */
	public void sendBuyChouma(final int gold, final int deskNo, final int sitNo) throws Exception {
		// dntex.sendBuyChouma(gold, deskNo, sitNo);
	}

	/**
	 * 登录发送手机信息
	 * 
	 * @param model
	 * @param screen
	 */
	public void sendMobLogin(String model, String screen) throws Exception {
		dnGame.sendMobLogin(model, screen);
	}

	/**
	 * 收到钱不够被踢出房间
	 * 
	 * @param e
	 */
	public void onRecvLowGold(Event e) {
		dispatchEvent(e);
	}

	int count = 0;

	/**
	 * 接收到登录游戏结果
	 * 
	 * @param e
	 */
	public void onRecvGameServerLoginResult(Event e) {
		System.out.println("onRecvGameServerLoginResult");
		try {

			HashMap data = (HashMap) e.getData();
			if (data != null) {
				short code = (Short) data.get("code");
				if (code == 1 || code == 2) {
					System.out.println("onRecvGameServerLoginResult 登录成功,更新频道信息");
					dnGame.sendRequestUpdateChannelInfo("");
				} else if (code == -101) {
					System.out.println("onRecvGameServerLoginResult 登录游戏服务器失败,可能有相同帐号正在使用");
				} else if (code == -102) {
					System.out.println("onRecvGameServerLoginResult 您已经在一个房间打牌，请完成牌局");
				} else {
					System.out.println("onRecvGameServerLoginResult 登陆游戏服务器失败 错误码：" + code);
				}
				dispatchEvent(e);
				// if(code ==-1){
				// count++;
				// System.out.println("send login again");
				// if(count % 20 !=0){
				// dnGame.sendRequestLoginGame(DConfig.strUser, DConfig.strKey);
				// }else{
				// dispatchEvent(e);
				// }
				// }
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	boolean m_bSelectedGame = false;

	/**
	 * 接收个人信息
	 * 
	 * @param e
	 */
	public void onRecvMyInfo(Event e) {
		DUtils.wirteToLogFile(" onRecvMyInfo: " + e.getEventName());
		System.out.println("onRecvMyInfo");
		try {
			HashMap data = (HashMap) e.getData();
			if (userInfo == null)
				userInfo = new HashMap();
			userInfo.put("userid", data.get("userid"));
			userInfo.put("usersex", data.get("usersex"));
			userInfo.put("usernick", data.get("usernick"));
			userInfo.put("imgurl", data.get("imgurl"));
			userInfo.put("gold", data.get("gold"));
			userInfo.put("city", data.get("city"));
			userInfo.put("channelid", data.get("channelid"));
			userInfo.put("user_real_id", data.get("user_real_id"));
			userInfo.put("md5_userid", data.get("md5_userid"));
			userInfo.put("gameexp", data.get("gameexp"));
			userInfo.put("cansit", data.get("cansit"));
			userInfo.put("tour_point", data.get("tour_point"));
			userInfo.put("welcometype", data.get("welcometype"));// 看教程:0未看过、1看过未领奖、3看过领奖过
			userInfo.put("canjiuji", data.get("canjiuji"));

			TaskManager.getInstance().execute(new TaskExecutorAdapter() {
				public int executeTask() throws Exception {
					if (GameUtil.initLocationGPS(GameApplication.currentActivity) == 1) {
						Log.i("test2", "接收到自己的信息发送经伟度");
						sendGPS(Double.toString(DConfig.nMyLatitudeX), Double.toString(DConfig.nMyLongitudeY), 0);
					} else {
						Log.i("test2", "接收到自己的信息未获取到经伟度");
					}
					return 0;
				}
			});

			// ------------------ 告诉服务器是第一次进入，不是切房间，选择游戏 ----------------//
			// if (!m_bSelectedGame) {
			// m_bSelectedGame = true;
			// //请求选择游戏
			// System.out.println("onRecvMyInfo send select youxi");
			// dnGame.sendSelectedGame();
			// }
			dispatchEvent(e);

			// int chang=1;
			// int tab=0;
			// int gold=(Integer)userInfo.get("gold");
			// if(gold<1500){
			// tab=1;
			// }else if(gold <10000){
			// tab=2;
			// }else if(gold <500000){
			// tab=3;
			// }else{
			// tab=4;
			// }
			// 请求房间桌子列表
			// System.out.println("onRecvMyInfo send requestDesk");
			// dnGame.sendRequestDeskList(chang, tab, 0, 0, 0, -1);
			// 请求论坛字串
			// dnGame.sendRequestURL();
		} catch (Exception ex) {
			System.out.println("onRecvMyInfo error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * 请求桌子列表
	 * 
	 * @param chang
	 * @param tab
	 * @throws Exception
	 */
	public void sendRequestDeskList(int chang, int tab) throws Exception {
		System.out.println("sendRequestDeskList send requestDesk");
		if (tab == 5) {
			dnGame.sendRequestDeskList(chang, tab, 0, 0, 1, -1);
		} else {
			dnGame.sendRequestDeskList(chang, tab, 0, 0, 0, -1);
		}
	}

	/**
	 * 收到组分区附加信息
	 * 
	 * @param e
	 */
	public void onRecvGroupInfo(Event e) {
		DUtils.wirteToLogFile(" onRecvGroupInfo: " + e.getEventName());
		System.out.println("onRecvGroupInfo");
		HashMap data = (HashMap) e.getData();
		// DGroupInfoItem groupInfo= getGroupInfo();
		// if (groupinfo.groupid == e.data.groupid)
		// {
		// for (var k:String in e.data)
		// {
		// //DTrace.traceex("ns_onRecv " + k)
		// groupinfo[k] = e.data[k];
		// }
		// }
	}

	// 收到显示选择面板
	public void onRecvChooseShow(Event e) {
		DUtils.wirteToLogFile(" onRecvChooseShow: " + e.getEventName());
		System.out.println("onRecvChooseShow");
		HashMap data = (HashMap) e.getData();
		byte isShow = (Byte) data.get("isShow");
		if (isShow == 0) {
			return;
		} else if (isShow == 1) {
		}
	}

	/**
	 * 收到声望详细信息[大厅]
	 * 
	 * @param e
	 */
	public void onRecvUserHallGameInfo(Event e) {
		DUtils.wirteToLogFile(" onRecvUserHallGameInfo: " + e.getEventName());
		System.out.println("onRecvUserHallGameInfo");
		HashMap data = (HashMap) e.getData();
	}

	/**
	 * 收到声望详细信息[游戏]
	 * 
	 * @param e
	 */
	public void onRecvUserGameGameInfo(Event e) {
		DUtils.wirteToLogFile(" onRecvUserHallGameInfo: " + e.getEventName());
		System.out.println("onRecvUserHallGameInfo");
		HashMap data = (HashMap) e.getData();
	}

	/**
	 * 收到服务器ip限制登录
	 * 
	 * @param e
	 */
	public void onRecvRestrictLogin(Event e) {
		DUtils.wirteToLogFile(" onRecvRestrictLogin: " + e.getEventName());
		System.out.println("onRecvRestrictLogin");
		HashMap data = (HashMap) e.getData();
		int retCode = (Integer) data.get("retcode");
		if (data == null)
			return;
		if (retCode < 0) {
			// 拒绝登录
		} else {
			// dispatchEvent(new DDataEvent("GAME_START_MUTEX", null));
		}
	}

	public void onRecvSitDownResult(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 发送离开牌桌大厅
	 * 
	 * @throws Exception
	 */
	public void sendClientLeaveRoom() throws Exception {
		dnGame.sendClientLeaveRoom();
	}

	/**
	 * 发送请求桌子信息
	 * 
	 * @param deskno
	 * @throws Exception
	 */
	public void sendRequestDeskInfo(int deskno) throws Exception {
		// dntex.sendRequestDeskInfo(deskno);
	}

	/**
	 * 加入桌子
	 * 
	 * @param deskNo
	 */
	public void sendRequestDeskUser(int deskNo) throws Exception {

		System.out.println("sendRequestDeskUser: deskNo: " + deskNo);
		dnGame.sendRequestDeskUser(deskNo);
		dnGame.sendRequestWatch(deskNo);
		sendRequestURL();
		sendClientLeaveRoom();
		sendRequestDeskInfo(deskNo);
		;
	}

	/**
	 * 发送请求论坛字串
	 * 
	 * @param deskNo
	 */
	public void sendRequestURL() throws Exception {
		dnGame.sendRequestURL();
	}

	/**
	 * 请求观战
	 * 
	 * @param deskNo
	 */
	public void sendRequestWatch(int deskNo) throws Exception {
		dnGame.sendRequestWatch(deskNo);
	}

	public void sendRefresh() throws Exception {
		Log.i("test2", "sendRefresh");
		dnGame.sendRefresh(DConfig.userId);
	}

	/**
	 * 请求刷新钱及VIP信息
	 * 
	 * @throws Exception
	 */
	public void sendUpdateGold() throws Exception {
		Log.i("test2", "sendUpdateGold");
		dnGame.sendUpdateGold();
		dnGame.sendUpdateVipInfo();
	}

	/**
	 * 下注
	 * 
	 * @param gold
	 * @param type
	 */
	public void sendClickXiaZhu(int gold, int type) throws Exception {

		System.out.println("sendClickXiaZhu: gold: " + gold + " type: " + type);
		// dntex.sendClickXiaZhu(gold, type);

	}

	/**
	 * 全下
	 * 
	 * @throws Exception
	 */
	public void sendClickTex() throws Exception {
		System.out.println("sendClickTex");
		// dntex.sendClickTex();
	}

	/**
	 * 发送不下注
	 * 
	 * @throws Exception
	 */
	public void sendClickBuxia() throws Exception {
		System.out.println("sendClickBuxia");
		// dntex.sendClickBuxia();

	}

	/**
	 * 发送跟注
	 * 
	 * @throws Exception
	 */
	public void sendClickFollow() throws Exception {
		System.out.println("sendClickBuxia");
		// dntex.sendClickFollow();
	}

	/**
	 * 发送放弃
	 * 
	 * @throws Exception
	 */
	public void sendClickGiveUp() throws Exception {
		System.out.println("sendClickGiveUp");
		dntex.sendClickGiveUp();
	}

	/**
	 * 发送踢人次数
	 * 
	 * @throws Exception
	 */
	public void sendRequestKickIsShow() throws Exception {
		// dntex.sendRequestKickIsShow();
	}

	/**
	 * 请求救济
	 * 
	 * @throws Exception
	 */
	public void sendRequestJiuJi() throws Exception {
		// TODO Auto-generated method stub
		// dntex.sendRequestJiuJi();
	}

	/**
	 * 发送表情
	 * 
	 * @param emotid
	 * @throws Exception
	 */
	public void sendPlayEmot(int emotid) throws Exception {
		// dntex.sendPlayEmot(emotid);
	}

	/**
	 * 发送聊天内容
	 * 
	 * @param chatText
	 * @throws Exception
	 */
	public void sendDeskChat(String chatText) throws Exception {
		// dntex.sendDeskChat(chatText);
	}

	/********************* 好友相关请求 *******************************/
	// 请求游戏者附加信息和成就信息
	public void sendRequestUserExtraInfoAchieveInfo(Integer userid) throws Exception {
		dnfriend.sendRequestUserExtraInfoAchieveInfo(userid);
	}

	/**
	 * 取得分页桌子详细信息 大厅列表信息
	 * 
	 * @param e
	 */
	public void onRecvCurPageRoomList(Event e) {
		try {
			DUtils.wirteToLogFile(" onRecvCurPageRoomList: " + e.getEventName());
			System.out.println("onRecvCurPageRoomList");
			dispatchEvent(e);
			// ArrayList data = (ArrayList)e.getData();
			// callBack.CallBack(data);
			// if(data !=null && !data.isEmpty()){
			// int size=data.size();
			// HashMap deskInfo=null;
			// deskInfo=(HashMap)data.get(0);
			// int deskNo=(Integer)deskInfo.get("deskno"); //桌号
			// String deskName=(String)deskInfo.get("name"); //名称
			// System.out.println("deskNo: "+deskNo+" deskName: "+deskName);
			// //请求加入桌子
			// dnGame.sendRequestDeskUser(deskNo);
			// for(int i=0;i<size;i++){
			// deskInfo=(HashMap)data.get(i);
			// deskInfo.get("deskno"); //桌号
			// deskInfo.get("name"); //名称
			// deskInfo.get("desktype"); //类型:1普通，2比赛，3VIP专用
			// deskInfo.get("fast"); //是否快速
			// deskInfo.get("betgold"); //桌面筹码数
			// deskInfo.get("usergold"); //桌子玩家筹码数
			// deskInfo.get("needlevel"); //桌子解锁等级
			// deskInfo.get("smallbet"); //小盲
			// deskInfo.get("largebet"); //大盲
			// deskInfo.get("at_least_gold"); //金币下限
			// deskInfo.get("at_most_gold"); //金币上限
			// deskInfo.get("specal_choushui"); //抽水
			// deskInfo.get("min_playercount"); //最少开局人数
			// deskInfo.get("max_playercount"); //最多开局人数
			// deskInfo.get("playercount"); //当前在玩人数
			// deskInfo.get("watchercount"); //观战人数
			// deskInfo.get("start"); //是否开始 1=是 0=开始
			// }
			// }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 接收BBS验证字串
	 * 
	 * @param e
	 */
	public void onTexRecvBbsUrl(Event e) {
		System.out.println("onTexRecvBbsUrl");
	}

	/**
	 * 登录送钱
	 * 
	 * @param e
	 */
	public void onTexLoginShowDayGold(Event e) {
		System.out.println("onTexLoginShowDayGold");
		dispatchEvent(e);
	}

	/**
	 * 取得桌子里玩家的详细信息事件
	 * 
	 * @param e
	 */
	public void onRecvDeskUserList(Event e) {
		try {
			System.out.println("onRecvDeskUserList");
			HashMap data = (HashMap) e.getData();
			int deskNo = (Integer) data.get("deskno");
			System.out.println("deskNo: " + deskNo);
			dispatchEvent(e);
			// 请求观战
			// dnGame.sendRequestWatch(deskNo);
			// 离开牌桌大厅
			// dnGame.sendClientLeaveRoom();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 收到桌面钱变化的事件
	 * 
	 * @param e
	 */
	public void onRecvChangeGold(Event e) {
		System.out.println("onRecvChangeGold");
		HashMap data = (HashMap) e.getData();
	}

	/**
	 * 收到更新金币的事件
	 * 
	 * @param e
	 */
	public void onRecvUpdateGold(Event e) {
		System.out.println("onRecvUpdateGold");
		HashMap data = (HashMap) e.getData();
	}

	/**
	 * 收到玩家状态信息
	 * 
	 * @param e
	 */
	public void onRecvPlayerInfo(Event e) {
		System.out.println("onRecvPlayerInfo");
		dispatchEvent(e);
	}

	/**
	 * 收到坐下
	 * 
	 * @param e
	 */
	public void onRecvSitDown(Event e) {
		System.out.println("onRecvSitDown");
		dispatchEvent(e);
	}

	/**
	 * 收到自动面板
	 * 
	 * @param e
	 */
	public void onRecvAutoPanel(Event e) {
		System.out.println("onRecvAutoPanel");
		dispatchEvent(e);
	}

	/**
	 * 收到面板
	 * 
	 * @param e
	 */
	public void onTexPanel(Event e) {
		System.out.println("onTexPanel");
		dispatchEvent(e);
	}

	/**
	 * 收到牌桌信息
	 * 
	 * @param e
	 */
	public void onRecvDeskInfo(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到发牌信息
	 * 
	 * @param e
	 */
	public void onRecvFaPai(Event e) {
		Log.i("test14", "3onTexFaPaionTexFaPaionTexFaPaionTexFaPai: " + System.currentTimeMillis());
		dispatchEvent(e);
	}

	/**
	 * 收到被踢含超时
	 * 
	 * @param e
	 */
	public void onRecvKickMe(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到玩家下注成功
	 * 
	 * @param e
	 */
	public void onRecvXiaZhuSucc(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到我的德州豆
	 * 
	 * @param e
	 */
	public void onRecvMyTotalBean(Event e) {
		// dispatchEvent(e);
		if (GameApplication.userInfo != null) {

			Integer gold = (Integer) e.getData();
			Log.i("test8", "onRecvMyTotalBean gold: " + gold);
			// GameApplication.userInfo.put("gold", gold);
			// if(GameApplication.currentActivity instanceof
			// DzpkGameMenuActivity){
			// ((DzpkGameMenuActivity)GameApplication.currentActivity).setPlayerChoumaText(gold);
			// }
		}
	}

	/**
	 * 收到桌面的牌
	 */
	public void onRecvDeskPoke(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到某个座位的金币刷新
	 * 
	 * @param e
	 */
	public void onRecvRefreshGold(Event e) {
		Log.i(tag, "onRecvRefreshGold");
		dispatchEvent(e);
	}

	/**
	 * 收到游戏结束
	 * 
	 * @param e
	 */
	public void onRecvGameOver(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到购买筹码窗
	 * 
	 * @param e
	 */
	public void onRecvBuyChouma(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 玩家站起
	 */
	public void onRecvStaneUp(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到游戏开始
	 * 
	 * @param e
	 */
	public void onRecvGameStart(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到玩家不下注
	 * 
	 * @param e
	 */
	public void onRecvBuXiaZhu(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到自己的最佳牌型
	 * 
	 * @param e
	 */
	public void onRecvBestPokes(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到桌面彩池信息
	 * 
	 * @param e
	 */
	public void onRecvDeskPollInfo(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到退出观战
	 * 
	 * @param e
	 */
	public void onRecvExitWatch(Event e) {
		// dispatchEvent(e);
	}

	/**
	 * 收到用户观战
	 */
	public void onRecvWatching(Event e) {
		// dispatchEvent(e);
	}

	/**
	 * 恢复显示
	 */
	public void onRecvResetDisplay(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到玩家投降
	 * 
	 * @param e
	 */
	public void onRecvGiveUp(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到登录送钱
	 * 
	 * @param e
	 */
	public void onRecvLoginGive(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到玩家位置数据GPS
	 * 
	 * @param e
	 */
	public void onRecvPlayerLocationData(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到能否进入房间
	 */
	public void onRecvCanNotToRoom(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到指定座位礼物
	 * 
	 * @param e
	 */
	public void onRecvGiftIcon(Event e) {

		dispatchEvent(e);
	}

	/**
	 * 播放赠送礼物
	 * 
	 * @param e
	 */
	public void onRecvPlayGift(Event e) {

		dispatchEvent(e);
	}

	/**
	 * 太富有了不能坐下
	 * 
	 * @param e
	 */
	public void onRecvRichCanNotToRoom(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到服务端的提示信息
	 * 
	 * @param e
	 */
	public void onRecvServerError(Event e) {
		dispatchEvent(e);
	}

	public void onRecvKickBeiti(Event e) {
		dispatchEvent(e);
	}

	// 收到游戏相关信息
	public void onRecvWatchError(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到救济金信息
	 * 
	 * @param e
	 */
	public void onRecvGiveJiuJi(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 收到网速检测
	 * 
	 * @param e
	 */
	public void onRecvCheckNet(Event e) {
		Log.i("test4", "recvCheckNet service");
		dispatchEvent(e);
	}

	/**
	 * 收到用户等级上升
	 * 
	 * @param e
	 */
	public void onRecvLevelUpgrade(Event e) {
		Log.i("test5", "onRecvLevelUpgrade ");
		HashMap data = (HashMap) e.getData();
		DzpkGameActivityDialog game = GameApplication.getDzpkGame();
		if (game != null && data != null) {
			if (game.gameDataManager != null && game.gameDataManager.sitDownUsers != null) {
				HashMap player = (HashMap) game.gameDataManager.sitDownUsers.get(data.get("siteno"));
				if (player != null) {
					Log.i("test5", "onRecvLevelUpgrade gamelevel: " + data.get("level"));
					player.put("gamelevel", data.get("level"));
				} else {
					Log.i("test5", "not onRecvLevelUpgrade gamelevel: " + data.get("level"));
				}
			}
		}
	}

	/**
	 * 收到Vip过期信息
	 * 
	 * @param e
	 */
	public void onNotifyVipOver(Event e) {
		HashMap data = (HashMap) e.getData();
		GameUtil.openVipDialog(data);
	}

	public void onRecvChat(Event e) {
		dispatchEvent(e);
	}

	public void onRecvEmot(Event e) {
		dispatchEvent(e);
	}

	/**
	 * 添加中心服务器侦听事件
	 */
	private void addCenterEventListener() {
		if (gcSocket != null) {
			gcSocket.addEventListener(Event.CLOSE, this, "centerNetClose");
		}
		dnc.addEventListener(Event.ON_RECV_GROUP_INFO, this, "recvGroupInfo");
		dnc.addEventListener(Event.ON_RECV_LOGIN_RESULT, this, "recvCenterLoginResult");

	}

	/**
	 * 移除中心服务器侦听事件
	 */
	private void removeCenterEventListener() {
		if (gcSocket != null) {
			gcSocket.removeEventListener(Event.CLOSE, this, "centerNetClose");
		}
		dnc.removeEventListener(Event.ON_RECV_GROUP_INFO, this, "recvGroupInfo");
		dnc.removeEventListener(Event.ON_RECV_LOGIN_RESULT, this, "recvCenterLoginResult");
		Log.i("test15", "removeCenterEventListenerremoveCenterEventListener");
	}

	private void addTexEventListener() {

		dntex.addEventListener(ZjhEventType.ON_ZJH_RECV_SITDOWN, this, "onRecvSitDown"); // 坐下
		dntex.addEventListener(ZjhEventType.ON_ZJH_RECV_STANDUP, this, "onRecvStaneUp"); // 站起

		// -----------------------------------------------------------------------------------------------------
		dntex.addEventListener(TexEventType.ON_TEX_RECV_RESETDISPLAY, this, "onRecvResetDisplay");// 恢复显示
		dntex.addEventListener(TexEventType.ON_TEX_RECV_TEX_READY, this, "onRecvReady"); // 收到准备
		dntex.addEventListener(TexEventType.ON_TEX_RECV_FAPAI, this, "onRecvFaPai"); // 收到发牌
		dntex.addEventListener(TexEventType.ON_TEX_RECV_BESTPOKES, this, "onRecvBestPokes"); // 收到自己的最佳牌型
		dntex.addEventListener(TexEventType.ON_TEX_RECV_MYPOKE, this, "onRecvMyPoke"); // 收到我的扑克
		dntex.addEventListener(TexEventType.ON_TEX_AUTO_PANEL, this, "onRecvAutoPanel"); // 自动面板
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIVEUP, this, "onRecvGiveUp"); // 收到投降的消息
		dntex.addEventListener(TexEventType.ON_TEX_RECV_XIAZHUSUCC, this, "onRecvXiaZhuSucc"); // 收到下注成功
		dntex.addEventListener(TexEventType.ON_TEX_RECV_BUXIAZHU, this, "onRecvBuXiaZhu"); // 收到不下注
		dntex.addEventListener(TexEventType.ON_TEX_BUTTON_STATUS, this, "onTexPanel"); // 设置按钮对应的动作
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GAMEOVER, this, "onRecvGameOver"); // 收到游戏结束，重新开始
		dntex.addEventListener(TexEventType.ON_TEX_RECV_REFRESHGOLD, this, "onRecvRefreshGold"); // 更新桌内座位上的金币
		dntex.addEventListener(TexEventType.ON_TEX_RECV_DESK_POLL_INFO, this, "onRecvDeskPollInfo"); // 收到桌面彩池信息
		dntex.addEventListener(TexEventType.ON_TEX_RECV_DESK_INFO, this, "onRecvDeskInfo"); // 收到桌信息
		dntex.addEventListener(TexEventType.ON_TEX_RECV_SHOW_MY_TOTAL_BEAN, this, "onRecvMyTotalBean"); // 收到我的德州豆
		dntex.addEventListener(TexEventType.ON_TEX_RECV_PLAYERINFO, this, "onRecvPlayerInfo"); // 更新所有人状态
		dntex.addEventListener(TexEventType.ON_TEX_RECV_POKEINFO, this, "onRecvPokeinfo"); // 发送某个座位上的牌(重登陆用)
		dntex.addEventListener(TexEventType.ON_TEX_RECV_RELOGIN_DESKGOLD, this, "onRecvReloginDeskGold"); // 收到桌面金币改变(重登陆用)
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GAMESTART, this, "onRecvGameStart"); // 收到游戏开始
		dntex.addEventListener(TexEventType.ON_TEX_RECV_BUYCHOUMA, this, "onRecvBuyChouma"); // 收到购买筹码
		dntex.addEventListener(TexEventType.ON_TEX_RECV_JS_WAITING, this, "onRecvJSWaiting"); // 收到桌子正在结算

		dntex.addEventListener(TexEventType.ON_TEX_RECV_TIMEOUT, this, "onRecvKickMe"); // 收到被踢
		dntex.addEventListener(TexEventType.ON_TEX_RECV_SITDOWN, this, "onRecvSitDown"); // 坐下
		dntex.addEventListener(TexEventType.ON_TEX_RECV_STANDUP, this, "onRecvStaneUp"); // 站起
		dntex.addEventListener(TexEventType.ON_TEX_RECV_SERVER_ERROR, this, "onRecvServerError"); // 收到服务端的提示信息
		dntex.addEventListener(TexEventType.ON_TEX_RECV_CHANGEFACE, this, "onRecvChangeFace"); // 收到玩家修改头像

		dntex.addEventListener(TexEventType.ON_TEX_RECV_SPECAL_ICON, this, "onRecvSpecalIcon"); // 收到特殊标识

		dntex.addEventListener(TexEventType.ON_TEX_RECV_DESKPOKE, this, "onRecvDeskPoke"); // 收到桌面扑克
		dntex.addEventListener(TexEventType.ON_TEX_RECV_WATCHING, this, "onRecvWatching"); // 收到用户观战
		dntex.addEventListener(TexEventType.ON_RECV_EXIT_WATCH, this, "onRecvExitWatch"); // 收到退出观战
		dntex.addEventListener(TexEventType.ON_RECV_ADD_EXPERIENCE, this, "onRecvAddExp"); // 收到增加经验
		dntex.addEventListener(TexEventType.ON_RECV_LEVEL_UPGRADE, this, "onRecvLevelUpgrade"); // 收到用户等级升级
		dntex.addEventListener(TexEventType.ON_RECV_DAY_ADDEXP, this, "onRecvExpFenhong"); // 收到用户获得天天送经验红利
		dntex.addEventListener(TexEventType.ON_RECV_LEVEL_PRIZE_OR_LOST, this, "onRecvPrizeOrLost"); // 收到自己得奖或被淘汰
		dntex.addEventListener(TexEventType.ON_RECV_LEVEL_SERVER_MSG, this, "onRecvLevelUpgradeInfo"); // 收到用户升级后显示的信息

		dntex.addEventListener(TexEventType.ON_BACK_HALL_RESULT, this, "onRecvStandupResult"); // 收到服务器通知返回大厅

		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_SHOP, this, "onRecvShopList"); // 收到商城礼物列表
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_LIST, this, "onRecvGiftList"); // 收到礼物列表
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_ICON, this, "onRecvGiftIcon"); // 收到礼物标识
		dntex.addEventListener(TexEventType.ON_TEX_RECV_PLAY_GIFT, this, "onRecvPlayGift"); // 播放送礼物动画
		// dntex.addEventListener(TexEventType.ON_TEX_RECV_PLAY_EMOT,this,"onRecvPlayEmot");
		// //播放表情
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_FAILD, this, "onRecvBuyGiftFailed"); // 购买礼物或发送表情失败
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_FAILDLIST, this, "onRecvGiftFailedList"); // 批量购买礼物失败
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_SALE, this, "onRecvGiftSaleResult"); // 收到出售礼物后的状态
		dntex.addEventListener(TexEventType.ON_RECV_GIFT_XINSHOU_ONE, this, "onRecvGiftXinshouOne"); // 给第一次玩牌的新手送礼物
		// 收到礼物回复
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_RESPONSE, this, "onRecvGiftResponse");

		dntex.addEventListener(TexEventType.ON_RECV_LOGIN_SHOW_BETAGIFE, this, "onRecvShowBetaGift");
		dntex.addEventListener(TexEventType.ON_RECV_LOGIN_GET_BETAGIFE, this, "onRecvGetBetaGift");

		// m_gsProcessor.addEventListener("ON_TEX_RECV_BBS_URL",this,"");

		dntex.addEventListener(TexEventType.ON_TEX_RECV_SINGLE_DETAIL, this, "onRecvSingleDetail");

		dntex.addEventListener(TexEventType.ON_RECV_GIVE_JIUJI, this, "onRecvGiveJiuJi");
		// ------踢人start-----------------------
		dntex.addEventListener(TexEventType.ON_RECV_KICK_ENABLE, this, "onRecvKickEnable");
		dntex.addEventListener(TexEventType.ON_RECV_KICK, this, "onRecvKick");
		dntex.addEventListener(TexEventType.ON_RECV_KICK_RESULT, this, "onRecvKickResult");
		dntex.addEventListener(TexEventType.ON_RECV_KICK_CISHU, this, "onRecvKickCishu");
		dntex.addEventListener(TexEventType.ON_RECV_KICK_OVER, this, "onRecvKickOver");
		dntex.addEventListener(TexEventType.ON_RECV_KICK_OVER_BEITI, this, "onRecvKickBeiti");
		dntex.addEventListener(TexEventType.ON_RECV_KICK_SHOW, this, "onRecvKickShow");
		// ------踢人end-----------------------

		// ----算牌start-----------------------
		dntex.addEventListener(TexEventType.ON_RECV_CALC_ENABLE, this, "onRecvCalcEnable");
		// ------算牌end-----------------------

		// 聊天
		dntex.addEventListener(TexEventType.ON_TEX_RECV_CHAT, this, "onRecvChat");
		dntex.addEventListener(TexEventType.ON_TEX_RECV_PLAY_EMOT, this, "onRecvEmot");

	}

	private void addGameEventListener() {
		// 添加游戏服务关闭
		gsSocket.addEventListener(Event.CLOSE, this, "gameNetClose");

		// 网速检测
		dnGame.addEventListener(Event.ON_RECV_CHECK_NET, this, "onRecvCheckNet");
		dnGame.addEventListener(Event.ON_RECV_WATCH_ERROR, this, "onRecvWatchError");
		// 收到坐下结果
		dnGame.addEventListener(Event.ON_RECV_SITDOWN_RESULT, this, "onRecvSitDownResult");
		dnGame.addEventListener(Event.ON_RECV_RICH_CANNOT_TOROOM, this, "onRecvRichCanNotToRoom");
		dnGame.addEventListener(Event.ON_RECV_CAN_NOT_TOROOM, this, "onRecvCanNotToRoom");
		dnGame.addEventListener(Event.ON_RECV_LOW_GOLD, this, "onRecvLowGold");
		dnGame.addEventListener(Event.ON_RECV_GAME_LOGIN_RESULT, this, "onRecvGameServerLoginResult");
		dnGame.addEventListener(Event.ON_RECV_MY_INFO, this, "onRecvMyInfo");
		dnGame.addEventListener(Event.ON_RECV_GROUP_INFO, this, "onRecvGroupInfo");
		dnGame.addEventListener(Event.ON_RECV_CHOOSE_SHOW, this, "onRecvChooseShow");
		dnGame.addEventListener(Event.ON_RECV_USER_HALL_GAMEINFO, this, "onRecvUserHallGameInfo");
		dnGame.addEventListener(Event.ON_RECV_RESTRICT_LOGIN, this, "onRecvRestrictLogin");
		dnGame.addEventListener(Event.ON_RECV_CURPAGEROOM_LIST, this, "onRecvCurPageRoomList");
		dnGame.addEventListener(Event.ON_TEX_RECV_BBS_URL, this, "onTexRecvBbsUrl");
		dnGame.addEventListener(Event.ON_TEX_LOGIN_SHOW_DAY_GOLD, this, "onTexLoginShowDayGold");
		dnGame.addEventListener(Event.ON_RECV_DESK_USERLIST, this, "onRecvDeskUserList");
		dnGame.addEventListener(Event.ON_RECV_USER_GAME_GAMEINFO, this, "onRecvUserGameGameInfo");
		dnGame.addEventListener(Event.ON_RECV_CHANGE_GOLD, this, "onRecvChangeGold");
		dnGame.addEventListener(Event.ON_RECV_UPDATE_GOLD, this, "onRecvUpdateGold");
		dnGame.addEventListener(Event.ON_TEX_RECV_LOGIN_GIVE, this, "onRecvLoginGive");

		// 监听GPS事件
		dnGame.addEventListener(Event.ON_RECV_GPS, this, "onRecvPlayerLocationData");

	}

	private void removeTexEventListener() {
		if (dntex == null)
			return;

		dntex.removeEventListener(ZjhEventType.ON_ZJH_RECV_SITDOWN, this, "onRecvSitDown"); // 坐下
		dntex.removeEventListener(ZjhEventType.ON_ZJH_RECV_STANDUP, this, "onRecvStaneUp"); // 站起

		// -----------------------------------------------------------------------------------------------------

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_RESETDISPLAY, this, "onRecvResetDisplay");// 恢复显示
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_TEX_READY, this, "onRecvReady"); // 收到准备
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_FAPAI, this, "onRecvFaPai"); // 收到发牌
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_BESTPOKES, this, "onRecvBestPokes"); // 收到自己的最佳牌型
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_MYPOKE, this, "onRecvMyPoke"); // 收到我的扑克
		dntex.removeEventListener(TexEventType.ON_TEX_AUTO_PANEL, this, "onRecvAutoPanel"); // 自动面板
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIVEUP, this, "onRecvGiveUp"); // 收到投降的消息
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_XIAZHUSUCC, this, "onRecvXiaZhuSucc"); // 收到下注成功
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_BUXIAZHU, this, "onRecvBuXiaZhu"); // 收到不下注
		dntex.removeEventListener(TexEventType.ON_TEX_BUTTON_STATUS, this, "onTexPanel"); // 设置按钮对应的动作
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GAMEOVER, this, "onRecvGameOver"); // 收到游戏结束，重新开始
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_REFRESHGOLD, this, "onRecvRefreshGold"); // 更新桌内座位上的金币
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_DESK_POLL_INFO, this, "onRecvDeskPollInfo"); // 收到桌面彩池信息
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_DESK_INFO, this, "onRecvDeskInfo"); // 收到桌信息
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_SHOW_MY_TOTAL_BEAN, this, "onRecvMyTotalBean"); // 收到我的德州豆
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_PLAYERINFO, this, "onRecvPlayerInfo"); // 更新所有人状态
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_POKEINFO, this, "onRecvPokeinfo"); // 发送某个座位上的牌(重登陆用)
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_RELOGIN_DESKGOLD, this, "onRecvReloginDeskGold"); // 收到桌面金币改变(重登陆用)
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GAMESTART, this, "onRecvGameStart"); // 收到游戏开始
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_BUYCHOUMA, this, "onRecvBuyChouma"); // 收到购买筹码
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_JS_WAITING, this, "onRecvJSWaiting"); // 收到桌子正在结算

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_TIMEOUT, this, "onRecvKickMe"); // 收到被踢
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_SITDOWN, this, "onRecvSitDown"); // 坐下
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_STANDUP, this, "onRecvStaneUp"); // 站起
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_SERVER_ERROR, this, "onRecvServerError"); // 收到服务端的提示信息
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_CHANGEFACE, this, "onRecvChangeFace"); // 收到玩家修改头像

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_SPECAL_ICON, this, "onRecvSpecalIcon"); // 收到特殊标识

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_DESKPOKE, this, "onRecvDeskPoke"); // 收到桌面扑克
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_WATCHING, this, "onRecvWatching"); // 收到用户观战
		dntex.removeEventListener(TexEventType.ON_RECV_EXIT_WATCH, this, "onRecvExitWatch"); // 收到退出观战
		dntex.removeEventListener(TexEventType.ON_RECV_ADD_EXPERIENCE, this, "onRecvAddExp"); // 收到增加经验
		dntex.removeEventListener(TexEventType.ON_RECV_LEVEL_UPGRADE, this, "onRecvLevelUpgrade"); // 收到用户等级升级
		dntex.removeEventListener(TexEventType.ON_RECV_DAY_ADDEXP, this, "onRecvExpFenhong"); // 收到用户获得天天送经验红利
		dntex.removeEventListener(TexEventType.ON_RECV_LEVEL_PRIZE_OR_LOST, this, "onRecvPrizeOrLost"); // 收到自己得奖或被淘汰
		dntex.removeEventListener(TexEventType.ON_RECV_LEVEL_SERVER_MSG, this, "onRecvLevelUpgradeInfo"); // 收到用户升级后显示的信息

		dntex.removeEventListener(TexEventType.ON_BACK_HALL_RESULT, this, "onRecvStandupResult"); // 收到服务器通知返回大厅

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_SHOP, this, "onRecvShopList"); // 收到商城礼物列表
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_LIST, this, "onRecvGiftList"); // 收到礼物列表
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_ICON, this, "onRecvGiftIcon"); // 收到礼物标识
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_PLAY_GIFT, this, "onRecvPlayGift"); // 播放送礼物动画
		// dntex.removeEventListener(TexEventType.ON_TEX_RECV_PLAY_EMOT,this,"onRecvPlayEmot");
		// //播放表情
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_FAILD, this, "onRecvBuyGiftFailed"); // 购买礼物或发送表情失败
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_FAILDLIST, this, "onRecvGiftFailedList"); // 批量购买礼物失败
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_SALE, this, "onRecvGiftSaleResult"); // 收到出售礼物后的状态
		dntex.removeEventListener(TexEventType.ON_RECV_GIFT_XINSHOU_ONE, this, "onRecvGiftXinshouOne"); // 给第一次玩牌的新手送礼物
		// 收到礼物回复
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_RESPONSE, this, "onRecvGiftResponse");

		dntex.removeEventListener(TexEventType.ON_RECV_LOGIN_SHOW_BETAGIFE, this, "onRecvShowBetaGift");
		dntex.removeEventListener(TexEventType.ON_RECV_LOGIN_GET_BETAGIFE, this, "onRecvGetBetaGift");

		// m_gsProcessor.addEventListener("ON_TEX_RECV_BBS_URL",this,"");

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_SINGLE_DETAIL, this, "onRecvSingleDetail");

		dntex.removeEventListener(TexEventType.ON_RECV_GIVE_JIUJI, this, "onRecvGiveJiuJi");
		// ------踢人start-----------------------
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_ENABLE, this, "onRecvKickEnable");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK, this, "onRecvKick");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_RESULT, this, "onRecvKickResult");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_CISHU, this, "onRecvKickCishu");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_OVER, this, "onRecvKickOver");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_OVER_BEITI, this, "onRecvKickBeiti");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_SHOW, this, "onRecvKickShow");
		// ------踢人end-----------------------

		// ----算牌start-----------------------
		dntex.removeEventListener(TexEventType.ON_RECV_CALC_ENABLE, this, "onRecvCalcEnable");
		// ------算牌end-----------------------

		// 聊天
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_CHAT, this, "onRecvChat");
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_PLAY_EMOT, this, "onRecvEmot");
		Log.i("test15", "removeTexEventListenerremoveTexEventListener");
	}

	private void removeGameEventListener() {
		if (gsSocket != null) {
			gsSocket.removeEventListener(Event.CLOSE, this, "gameNetClose");
		}

		if (dnGame == null)
			return;
		dnGame.removeEventListener(Event.ON_RECV_CHECK_NET, this, "onRecvCheckNet");
		dnGame.removeEventListener(Event.ON_RECV_WATCH_ERROR, this, "onRecvWatchError");
		dnGame.removeEventListener(Event.ON_RECV_SITDOWN_RESULT, this, "onRecvSitDownResult");
		dnGame.removeEventListener(Event.ON_RECV_RICH_CANNOT_TOROOM, this, "onRecvRichCanNotToRoom");
		dnGame.removeEventListener(Event.ON_RECV_CAN_NOT_TOROOM, this, "onRecvCanNotToRoom");
		dnGame.removeEventListener(Event.ON_TEX_RECV_LOGIN_GIVE, this, "onRecvLoginGive");
		dnGame.removeEventListener(Event.ON_RECV_LOW_GOLD, this, "onRecvLowGold");
		dnGame.removeEventListener(Event.ON_RECV_GAME_LOGIN_RESULT, this, "onRecvGameServerLoginResult");
		dnGame.removeEventListener(Event.ON_RECV_MY_INFO, this, "onRecvMyInfo");
		dnGame.removeEventListener(Event.ON_RECV_GROUP_INFO, this, "onRecvGroupInfo");
		dnGame.removeEventListener(Event.ON_RECV_CHOOSE_SHOW, this, "onRecvChooseShow");
		dnGame.removeEventListener(Event.ON_RECV_USER_HALL_GAMEINFO, this, "onRecvUserHallGameInfo");
		dnGame.removeEventListener(Event.ON_RECV_RESTRICT_LOGIN, this, "onRecvRestrictLogin");
		dnGame.removeEventListener(Event.ON_RECV_CURPAGEROOM_LIST, this, "onRecvCurPageRoomList");
		dnGame.removeEventListener(Event.ON_TEX_RECV_BBS_URL, this, "onTexRecvBbsUrl");
		dnGame.removeEventListener(Event.ON_TEX_LOGIN_SHOW_DAY_GOLD, this, "onTexLoginShowDayGold");
		dnGame.removeEventListener(Event.ON_RECV_DESK_USERLIST, this, "onRecvDeskUserList");
		dnGame.removeEventListener(Event.ON_RECV_USER_GAME_GAMEINFO, this, "onRecvUserGameGameInfo");
		dnGame.removeEventListener(Event.ON_RECV_CHANGE_GOLD, this, "onRecvChangeGold");
		dnGame.removeEventListener(Event.ON_RECV_UPDATE_GOLD, this, "onRecvUpdateGold");

		// 监听GPS事件
		dnGame.removeEventListener(Event.ON_RECV_GPS, this, "onRecvPlayerLocationData");

		Log.i("test15", "removeGameEventListenerremoveGameEventListener");

	}

	public void addVipEventListener() {
		if (dnvip == null)
			return;
		dnvip.addEventListener(DNetworkVip.CMD_NOTIFY_VIP_OVER, this, "onNotifyVipOver");
	}

	public void removeVipEventListener() {
		if (dnvip == null)
			return;
		dnvip.removeEventListener(DNetworkVip.CMD_NOTIFY_VIP_OVER, this, "onNotifyVipOver");
	}

	// ////////////////后续需要改进如下////////////////////////
	public void addTexEventListener(String eventType, CallBack callback, String funcName) {
		dntex.addEventListener(eventType, callback, funcName);
	}

	public void removeTexEventListener(String eventType, CallBack callback, String funcName) {
		if (dntex == null)
			return;
		dntex.removeEventListener(eventType, callback, funcName);
	}

	public void addGameEventListener(String eventType, CallBack callback, String funcName) {
		dnGame.addEventListener(eventType, callback, funcName);
	}

	public void removeGameEventListener(String eventType, CallBack callback, String funcName) {
		if (dnGame == null)
			return;
		dnGame.removeEventListener(eventType, callback, funcName);
	}

	public void addFriendEventListener(String eventType, CallBack callback, String funcName) {
		dnfriend.addEventListener(eventType, callback, funcName);
	}

	public void removeFriendEventListener(String eventType, CallBack callback, String funcName) {
		if (dnfriend == null)
			return;
		dnfriend.removeEventListener(eventType, callback, funcName);
	}

}
