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

	public static final String CMD_REQUEST_LOGIN = "RQLG";// �����½
	private static final String CMD_REQUEST_GROUP_INFO = "RQGI";// �����ȡ�����б�
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
	 * �����������
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
	 * �ر������ĵ�����
	 */
	public void shutDownGCenter() {
		removeCenterEventListener();
		if (gcSocket != null) {
			gcSocket.shutDownConnection();
		}
	}

	/**
	 * �ر�����Ϸ����˵�����
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
	 * ���͵�¼
	 * 
	 * @param strUser
	 * @param strKey
	 * @throws Exception
	 */
	public void sendLogin(String strUser, String strKey) throws Exception {

		if (gcSocket.isConnected()) {
			dnc = new DNetworkCenter(gcSocket);
			// ������ķ����������¼�
			addCenterEventListener();
			sendGetAreaInfo(gcSocket, sendGetAreaInfo);
			sendLoginInfo(gcSocket, strUser, strKey);
			new Thread(gcSocket).start();
		}

	}

	/**
	 * ���ķ����������ѹر�
	 */
	public void centerNetClose(Event e) {
		GameUtil.openNetErrorMsg();
	}

	/**
	 * ��Ϸ�����������ѹر�
	 */
	public void gameNetClose(Event e) {
		GameUtil.openNetErrorMsg();
	}

	/**
	 * �������ټ��
	 * 
	 * @throws Exception
	 */
	public void sendCheckNet(String currentTime) throws Exception {
		Log.i("test4", "sendCheckNet");
		dnGame.sendCheckNet(currentTime);
	}

	/**
	 * �����Զ�������Ϸ
	 * 
	 * @throws Exception
	 */
	public void sendRequestAutoJoin() throws Exception {
		dnGame.sendRequestAutoJoin(-1);
	}

	/**
	 * ���͹����������
	 * 
	 * @throws Exception
	 */
	public void sendRequestTXNTBC() throws Exception {
		// dntex.sendRequestTXNTBC();
	}

	/**
	 * ���Ϳ��ٿ�ʼ
	 * 
	 * @param chouma
	 */
	public void sendRequestQuickStart(int chouma) throws Exception {
		// dntex.sendRequestQuickStart(chouma);
	}

	/**
	 * ���󷵻ش���
	 * 
	 * @throws Exception
	 */
	public void sendRequestBackHall() throws Exception {
		dntex.sendRequestBackHall();
	}

	/**
	 * ����վ��
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
	 * �����Լ�GPS����
	 * 
	 * @throws Exception
	 * 
	 */
	public void sendGPS(String strMyLatitudeX, String strMyLongitudeY, int type) throws Exception {
		dnGame.sendGPS(strMyLatitudeX, strMyLongitudeY, type);
	}

	/**
	 * ����ȡ�÷�����Ϣ
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
	 * ���͵�¼��ȡ
	 * 
	 * @throws Exception
	 */
	public void sendRequestLoginLingQu() throws Exception {
		dnGame.sendRequestLoginLingQu();
	}

	/**
	 * ���͵�¼��Ϣ��������������������
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
			socket.writeByte((byte) 1);// YYͨ��֤
			socket.writeString("999");
			socket.writeEnd();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ����һ�����������з���
	ArrayList<DGroupInfoItem> tempList = null;

	/**
	 * ���յ����з�����Ϣ
	 * 
	 * @param e
	 */
	public void recvGroupInfo(Event e) {
		// /DUtils.wirteToLogFile(" recvGroupInfo " + e.getEventName());
		System.out.println(" recvGroupInfo " + e.getEventName());
		// System.out.println(" ���յ����з�����Ϣ " + e.getEventName());
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
		// ����������
		dispatchEvent(e);
	}

	HashMap userInfo = null;

	/**
	 * ��¼���
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
	 * �������
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
	 * ���͹������
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
	 * ��¼�����ֻ���Ϣ
	 * 
	 * @param model
	 * @param screen
	 */
	public void sendMobLogin(String model, String screen) throws Exception {
		dnGame.sendMobLogin(model, screen);
	}

	/**
	 * �յ�Ǯ�������߳�����
	 * 
	 * @param e
	 */
	public void onRecvLowGold(Event e) {
		dispatchEvent(e);
	}

	int count = 0;

	/**
	 * ���յ���¼��Ϸ���
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
					System.out.println("onRecvGameServerLoginResult ��¼�ɹ�,����Ƶ����Ϣ");
					dnGame.sendRequestUpdateChannelInfo("");
				} else if (code == -101) {
					System.out.println("onRecvGameServerLoginResult ��¼��Ϸ������ʧ��,��������ͬ�ʺ�����ʹ��");
				} else if (code == -102) {
					System.out.println("onRecvGameServerLoginResult ���Ѿ���һ��������ƣ�������ƾ�");
				} else {
					System.out.println("onRecvGameServerLoginResult ��½��Ϸ������ʧ�� �����룺" + code);
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
	 * ���ո�����Ϣ
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
			userInfo.put("welcometype", data.get("welcometype"));// ���̳�:0δ������1����δ�콱��3�����콱��
			userInfo.put("canjiuji", data.get("canjiuji"));

			TaskManager.getInstance().execute(new TaskExecutorAdapter() {
				public int executeTask() throws Exception {
					if (GameUtil.initLocationGPS(GameApplication.currentActivity) == 1) {
						Log.i("test2", "���յ��Լ�����Ϣ���;�ΰ��");
						sendGPS(Double.toString(DConfig.nMyLatitudeX), Double.toString(DConfig.nMyLongitudeY), 0);
					} else {
						Log.i("test2", "���յ��Լ�����Ϣδ��ȡ����ΰ��");
					}
					return 0;
				}
			});

			// ------------------ ���߷������ǵ�һ�ν��룬�����з��䣬ѡ����Ϸ ----------------//
			// if (!m_bSelectedGame) {
			// m_bSelectedGame = true;
			// //����ѡ����Ϸ
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
			// ���󷿼������б�
			// System.out.println("onRecvMyInfo send requestDesk");
			// dnGame.sendRequestDeskList(chang, tab, 0, 0, 0, -1);
			// ������̳�ִ�
			// dnGame.sendRequestURL();
		} catch (Exception ex) {
			System.out.println("onRecvMyInfo error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * ���������б�
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
	 * �յ������������Ϣ
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

	// �յ���ʾѡ�����
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
	 * �յ�������ϸ��Ϣ[����]
	 * 
	 * @param e
	 */
	public void onRecvUserHallGameInfo(Event e) {
		DUtils.wirteToLogFile(" onRecvUserHallGameInfo: " + e.getEventName());
		System.out.println("onRecvUserHallGameInfo");
		HashMap data = (HashMap) e.getData();
	}

	/**
	 * �յ�������ϸ��Ϣ[��Ϸ]
	 * 
	 * @param e
	 */
	public void onRecvUserGameGameInfo(Event e) {
		DUtils.wirteToLogFile(" onRecvUserHallGameInfo: " + e.getEventName());
		System.out.println("onRecvUserHallGameInfo");
		HashMap data = (HashMap) e.getData();
	}

	/**
	 * �յ�������ip���Ƶ�¼
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
			// �ܾ���¼
		} else {
			// dispatchEvent(new DDataEvent("GAME_START_MUTEX", null));
		}
	}

	public void onRecvSitDownResult(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �����뿪��������
	 * 
	 * @throws Exception
	 */
	public void sendClientLeaveRoom() throws Exception {
		dnGame.sendClientLeaveRoom();
	}

	/**
	 * ��������������Ϣ
	 * 
	 * @param deskno
	 * @throws Exception
	 */
	public void sendRequestDeskInfo(int deskno) throws Exception {
		// dntex.sendRequestDeskInfo(deskno);
	}

	/**
	 * ��������
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
	 * ����������̳�ִ�
	 * 
	 * @param deskNo
	 */
	public void sendRequestURL() throws Exception {
		dnGame.sendRequestURL();
	}

	/**
	 * �����ս
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
	 * ����ˢ��Ǯ��VIP��Ϣ
	 * 
	 * @throws Exception
	 */
	public void sendUpdateGold() throws Exception {
		Log.i("test2", "sendUpdateGold");
		dnGame.sendUpdateGold();
		dnGame.sendUpdateVipInfo();
	}

	/**
	 * ��ע
	 * 
	 * @param gold
	 * @param type
	 */
	public void sendClickXiaZhu(int gold, int type) throws Exception {

		System.out.println("sendClickXiaZhu: gold: " + gold + " type: " + type);
		// dntex.sendClickXiaZhu(gold, type);

	}

	/**
	 * ȫ��
	 * 
	 * @throws Exception
	 */
	public void sendClickTex() throws Exception {
		System.out.println("sendClickTex");
		// dntex.sendClickTex();
	}

	/**
	 * ���Ͳ���ע
	 * 
	 * @throws Exception
	 */
	public void sendClickBuxia() throws Exception {
		System.out.println("sendClickBuxia");
		// dntex.sendClickBuxia();

	}

	/**
	 * ���͸�ע
	 * 
	 * @throws Exception
	 */
	public void sendClickFollow() throws Exception {
		System.out.println("sendClickBuxia");
		// dntex.sendClickFollow();
	}

	/**
	 * ���ͷ���
	 * 
	 * @throws Exception
	 */
	public void sendClickGiveUp() throws Exception {
		System.out.println("sendClickGiveUp");
		dntex.sendClickGiveUp();
	}

	/**
	 * �������˴���
	 * 
	 * @throws Exception
	 */
	public void sendRequestKickIsShow() throws Exception {
		// dntex.sendRequestKickIsShow();
	}

	/**
	 * ����ȼ�
	 * 
	 * @throws Exception
	 */
	public void sendRequestJiuJi() throws Exception {
		// TODO Auto-generated method stub
		// dntex.sendRequestJiuJi();
	}

	/**
	 * ���ͱ���
	 * 
	 * @param emotid
	 * @throws Exception
	 */
	public void sendPlayEmot(int emotid) throws Exception {
		// dntex.sendPlayEmot(emotid);
	}

	/**
	 * ������������
	 * 
	 * @param chatText
	 * @throws Exception
	 */
	public void sendDeskChat(String chatText) throws Exception {
		// dntex.sendDeskChat(chatText);
	}

	/********************* ����������� *******************************/
	// ������Ϸ�߸�����Ϣ�ͳɾ���Ϣ
	public void sendRequestUserExtraInfoAchieveInfo(Integer userid) throws Exception {
		dnfriend.sendRequestUserExtraInfoAchieveInfo(userid);
	}

	/**
	 * ȡ�÷�ҳ������ϸ��Ϣ �����б���Ϣ
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
			// int deskNo=(Integer)deskInfo.get("deskno"); //����
			// String deskName=(String)deskInfo.get("name"); //����
			// System.out.println("deskNo: "+deskNo+" deskName: "+deskName);
			// //�����������
			// dnGame.sendRequestDeskUser(deskNo);
			// for(int i=0;i<size;i++){
			// deskInfo=(HashMap)data.get(i);
			// deskInfo.get("deskno"); //����
			// deskInfo.get("name"); //����
			// deskInfo.get("desktype"); //����:1��ͨ��2������3VIPר��
			// deskInfo.get("fast"); //�Ƿ����
			// deskInfo.get("betgold"); //���������
			// deskInfo.get("usergold"); //������ҳ�����
			// deskInfo.get("needlevel"); //���ӽ����ȼ�
			// deskInfo.get("smallbet"); //Сä
			// deskInfo.get("largebet"); //��ä
			// deskInfo.get("at_least_gold"); //�������
			// deskInfo.get("at_most_gold"); //�������
			// deskInfo.get("specal_choushui"); //��ˮ
			// deskInfo.get("min_playercount"); //���ٿ�������
			// deskInfo.get("max_playercount"); //��࿪������
			// deskInfo.get("playercount"); //��ǰ��������
			// deskInfo.get("watchercount"); //��ս����
			// deskInfo.get("start"); //�Ƿ�ʼ 1=�� 0=��ʼ
			// }
			// }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ����BBS��֤�ִ�
	 * 
	 * @param e
	 */
	public void onTexRecvBbsUrl(Event e) {
		System.out.println("onTexRecvBbsUrl");
	}

	/**
	 * ��¼��Ǯ
	 * 
	 * @param e
	 */
	public void onTexLoginShowDayGold(Event e) {
		System.out.println("onTexLoginShowDayGold");
		dispatchEvent(e);
	}

	/**
	 * ȡ����������ҵ���ϸ��Ϣ�¼�
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
			// �����ս
			// dnGame.sendRequestWatch(deskNo);
			// �뿪��������
			// dnGame.sendClientLeaveRoom();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * �յ�����Ǯ�仯���¼�
	 * 
	 * @param e
	 */
	public void onRecvChangeGold(Event e) {
		System.out.println("onRecvChangeGold");
		HashMap data = (HashMap) e.getData();
	}

	/**
	 * �յ����½�ҵ��¼�
	 * 
	 * @param e
	 */
	public void onRecvUpdateGold(Event e) {
		System.out.println("onRecvUpdateGold");
		HashMap data = (HashMap) e.getData();
	}

	/**
	 * �յ����״̬��Ϣ
	 * 
	 * @param e
	 */
	public void onRecvPlayerInfo(Event e) {
		System.out.println("onRecvPlayerInfo");
		dispatchEvent(e);
	}

	/**
	 * �յ�����
	 * 
	 * @param e
	 */
	public void onRecvSitDown(Event e) {
		System.out.println("onRecvSitDown");
		dispatchEvent(e);
	}

	/**
	 * �յ��Զ����
	 * 
	 * @param e
	 */
	public void onRecvAutoPanel(Event e) {
		System.out.println("onRecvAutoPanel");
		dispatchEvent(e);
	}

	/**
	 * �յ����
	 * 
	 * @param e
	 */
	public void onTexPanel(Event e) {
		System.out.println("onTexPanel");
		dispatchEvent(e);
	}

	/**
	 * �յ�������Ϣ
	 * 
	 * @param e
	 */
	public void onRecvDeskInfo(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ�������Ϣ
	 * 
	 * @param e
	 */
	public void onRecvFaPai(Event e) {
		Log.i("test14", "3onTexFaPaionTexFaPaionTexFaPaionTexFaPai: " + System.currentTimeMillis());
		dispatchEvent(e);
	}

	/**
	 * �յ����ߺ���ʱ
	 * 
	 * @param e
	 */
	public void onRecvKickMe(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ������ע�ɹ�
	 * 
	 * @param e
	 */
	public void onRecvXiaZhuSucc(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ��ҵĵ��ݶ�
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
	 * �յ��������
	 */
	public void onRecvDeskPoke(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ�ĳ����λ�Ľ��ˢ��
	 * 
	 * @param e
	 */
	public void onRecvRefreshGold(Event e) {
		Log.i(tag, "onRecvRefreshGold");
		dispatchEvent(e);
	}

	/**
	 * �յ���Ϸ����
	 * 
	 * @param e
	 */
	public void onRecvGameOver(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ�������봰
	 * 
	 * @param e
	 */
	public void onRecvBuyChouma(Event e) {
		dispatchEvent(e);
	}

	/**
	 * ���վ��
	 */
	public void onRecvStaneUp(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ���Ϸ��ʼ
	 * 
	 * @param e
	 */
	public void onRecvGameStart(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ���Ҳ���ע
	 * 
	 * @param e
	 */
	public void onRecvBuXiaZhu(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ��Լ����������
	 * 
	 * @param e
	 */
	public void onRecvBestPokes(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ�����ʳ���Ϣ
	 * 
	 * @param e
	 */
	public void onRecvDeskPollInfo(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ��˳���ս
	 * 
	 * @param e
	 */
	public void onRecvExitWatch(Event e) {
		// dispatchEvent(e);
	}

	/**
	 * �յ��û���ս
	 */
	public void onRecvWatching(Event e) {
		// dispatchEvent(e);
	}

	/**
	 * �ָ���ʾ
	 */
	public void onRecvResetDisplay(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ����Ͷ��
	 * 
	 * @param e
	 */
	public void onRecvGiveUp(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ���¼��Ǯ
	 * 
	 * @param e
	 */
	public void onRecvLoginGive(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ����λ������GPS
	 * 
	 * @param e
	 */
	public void onRecvPlayerLocationData(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ��ܷ���뷿��
	 */
	public void onRecvCanNotToRoom(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ�ָ����λ����
	 * 
	 * @param e
	 */
	public void onRecvGiftIcon(Event e) {

		dispatchEvent(e);
	}

	/**
	 * ������������
	 * 
	 * @param e
	 */
	public void onRecvPlayGift(Event e) {

		dispatchEvent(e);
	}

	/**
	 * ̫�����˲�������
	 * 
	 * @param e
	 */
	public void onRecvRichCanNotToRoom(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ�����˵���ʾ��Ϣ
	 * 
	 * @param e
	 */
	public void onRecvServerError(Event e) {
		dispatchEvent(e);
	}

	public void onRecvKickBeiti(Event e) {
		dispatchEvent(e);
	}

	// �յ���Ϸ�����Ϣ
	public void onRecvWatchError(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ��ȼý���Ϣ
	 * 
	 * @param e
	 */
	public void onRecvGiveJiuJi(Event e) {
		dispatchEvent(e);
	}

	/**
	 * �յ����ټ��
	 * 
	 * @param e
	 */
	public void onRecvCheckNet(Event e) {
		Log.i("test4", "recvCheckNet service");
		dispatchEvent(e);
	}

	/**
	 * �յ��û��ȼ�����
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
	 * �յ�Vip������Ϣ
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
	 * ������ķ����������¼�
	 */
	private void addCenterEventListener() {
		if (gcSocket != null) {
			gcSocket.addEventListener(Event.CLOSE, this, "centerNetClose");
		}
		dnc.addEventListener(Event.ON_RECV_GROUP_INFO, this, "recvGroupInfo");
		dnc.addEventListener(Event.ON_RECV_LOGIN_RESULT, this, "recvCenterLoginResult");

	}

	/**
	 * �Ƴ����ķ����������¼�
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

		dntex.addEventListener(ZjhEventType.ON_ZJH_RECV_SITDOWN, this, "onRecvSitDown"); // ����
		dntex.addEventListener(ZjhEventType.ON_ZJH_RECV_STANDUP, this, "onRecvStaneUp"); // վ��

		// -----------------------------------------------------------------------------------------------------
		dntex.addEventListener(TexEventType.ON_TEX_RECV_RESETDISPLAY, this, "onRecvResetDisplay");// �ָ���ʾ
		dntex.addEventListener(TexEventType.ON_TEX_RECV_TEX_READY, this, "onRecvReady"); // �յ�׼��
		dntex.addEventListener(TexEventType.ON_TEX_RECV_FAPAI, this, "onRecvFaPai"); // �յ�����
		dntex.addEventListener(TexEventType.ON_TEX_RECV_BESTPOKES, this, "onRecvBestPokes"); // �յ��Լ����������
		dntex.addEventListener(TexEventType.ON_TEX_RECV_MYPOKE, this, "onRecvMyPoke"); // �յ��ҵ��˿�
		dntex.addEventListener(TexEventType.ON_TEX_AUTO_PANEL, this, "onRecvAutoPanel"); // �Զ����
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIVEUP, this, "onRecvGiveUp"); // �յ�Ͷ������Ϣ
		dntex.addEventListener(TexEventType.ON_TEX_RECV_XIAZHUSUCC, this, "onRecvXiaZhuSucc"); // �յ���ע�ɹ�
		dntex.addEventListener(TexEventType.ON_TEX_RECV_BUXIAZHU, this, "onRecvBuXiaZhu"); // �յ�����ע
		dntex.addEventListener(TexEventType.ON_TEX_BUTTON_STATUS, this, "onTexPanel"); // ���ð�ť��Ӧ�Ķ���
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GAMEOVER, this, "onRecvGameOver"); // �յ���Ϸ���������¿�ʼ
		dntex.addEventListener(TexEventType.ON_TEX_RECV_REFRESHGOLD, this, "onRecvRefreshGold"); // ����������λ�ϵĽ��
		dntex.addEventListener(TexEventType.ON_TEX_RECV_DESK_POLL_INFO, this, "onRecvDeskPollInfo"); // �յ�����ʳ���Ϣ
		dntex.addEventListener(TexEventType.ON_TEX_RECV_DESK_INFO, this, "onRecvDeskInfo"); // �յ�����Ϣ
		dntex.addEventListener(TexEventType.ON_TEX_RECV_SHOW_MY_TOTAL_BEAN, this, "onRecvMyTotalBean"); // �յ��ҵĵ��ݶ�
		dntex.addEventListener(TexEventType.ON_TEX_RECV_PLAYERINFO, this, "onRecvPlayerInfo"); // ����������״̬
		dntex.addEventListener(TexEventType.ON_TEX_RECV_POKEINFO, this, "onRecvPokeinfo"); // ����ĳ����λ�ϵ���(�ص�½��)
		dntex.addEventListener(TexEventType.ON_TEX_RECV_RELOGIN_DESKGOLD, this, "onRecvReloginDeskGold"); // �յ������Ҹı�(�ص�½��)
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GAMESTART, this, "onRecvGameStart"); // �յ���Ϸ��ʼ
		dntex.addEventListener(TexEventType.ON_TEX_RECV_BUYCHOUMA, this, "onRecvBuyChouma"); // �յ��������
		dntex.addEventListener(TexEventType.ON_TEX_RECV_JS_WAITING, this, "onRecvJSWaiting"); // �յ��������ڽ���

		dntex.addEventListener(TexEventType.ON_TEX_RECV_TIMEOUT, this, "onRecvKickMe"); // �յ�����
		dntex.addEventListener(TexEventType.ON_TEX_RECV_SITDOWN, this, "onRecvSitDown"); // ����
		dntex.addEventListener(TexEventType.ON_TEX_RECV_STANDUP, this, "onRecvStaneUp"); // վ��
		dntex.addEventListener(TexEventType.ON_TEX_RECV_SERVER_ERROR, this, "onRecvServerError"); // �յ�����˵���ʾ��Ϣ
		dntex.addEventListener(TexEventType.ON_TEX_RECV_CHANGEFACE, this, "onRecvChangeFace"); // �յ�����޸�ͷ��

		dntex.addEventListener(TexEventType.ON_TEX_RECV_SPECAL_ICON, this, "onRecvSpecalIcon"); // �յ������ʶ

		dntex.addEventListener(TexEventType.ON_TEX_RECV_DESKPOKE, this, "onRecvDeskPoke"); // �յ������˿�
		dntex.addEventListener(TexEventType.ON_TEX_RECV_WATCHING, this, "onRecvWatching"); // �յ��û���ս
		dntex.addEventListener(TexEventType.ON_RECV_EXIT_WATCH, this, "onRecvExitWatch"); // �յ��˳���ս
		dntex.addEventListener(TexEventType.ON_RECV_ADD_EXPERIENCE, this, "onRecvAddExp"); // �յ����Ӿ���
		dntex.addEventListener(TexEventType.ON_RECV_LEVEL_UPGRADE, this, "onRecvLevelUpgrade"); // �յ��û��ȼ�����
		dntex.addEventListener(TexEventType.ON_RECV_DAY_ADDEXP, this, "onRecvExpFenhong"); // �յ��û���������;������
		dntex.addEventListener(TexEventType.ON_RECV_LEVEL_PRIZE_OR_LOST, this, "onRecvPrizeOrLost"); // �յ��Լ��ý�����̭
		dntex.addEventListener(TexEventType.ON_RECV_LEVEL_SERVER_MSG, this, "onRecvLevelUpgradeInfo"); // �յ��û���������ʾ����Ϣ

		dntex.addEventListener(TexEventType.ON_BACK_HALL_RESULT, this, "onRecvStandupResult"); // �յ�������֪ͨ���ش���

		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_SHOP, this, "onRecvShopList"); // �յ��̳������б�
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_LIST, this, "onRecvGiftList"); // �յ������б�
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_ICON, this, "onRecvGiftIcon"); // �յ������ʶ
		dntex.addEventListener(TexEventType.ON_TEX_RECV_PLAY_GIFT, this, "onRecvPlayGift"); // ���������ﶯ��
		// dntex.addEventListener(TexEventType.ON_TEX_RECV_PLAY_EMOT,this,"onRecvPlayEmot");
		// //���ű���
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_FAILD, this, "onRecvBuyGiftFailed"); // ����������ͱ���ʧ��
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_FAILDLIST, this, "onRecvGiftFailedList"); // ������������ʧ��
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_SALE, this, "onRecvGiftSaleResult"); // �յ�����������״̬
		dntex.addEventListener(TexEventType.ON_RECV_GIFT_XINSHOU_ONE, this, "onRecvGiftXinshouOne"); // ����һ�����Ƶ�����������
		// �յ�����ظ�
		dntex.addEventListener(TexEventType.ON_TEX_RECV_GIFT_RESPONSE, this, "onRecvGiftResponse");

		dntex.addEventListener(TexEventType.ON_RECV_LOGIN_SHOW_BETAGIFE, this, "onRecvShowBetaGift");
		dntex.addEventListener(TexEventType.ON_RECV_LOGIN_GET_BETAGIFE, this, "onRecvGetBetaGift");

		// m_gsProcessor.addEventListener("ON_TEX_RECV_BBS_URL",this,"");

		dntex.addEventListener(TexEventType.ON_TEX_RECV_SINGLE_DETAIL, this, "onRecvSingleDetail");

		dntex.addEventListener(TexEventType.ON_RECV_GIVE_JIUJI, this, "onRecvGiveJiuJi");
		// ------����start-----------------------
		dntex.addEventListener(TexEventType.ON_RECV_KICK_ENABLE, this, "onRecvKickEnable");
		dntex.addEventListener(TexEventType.ON_RECV_KICK, this, "onRecvKick");
		dntex.addEventListener(TexEventType.ON_RECV_KICK_RESULT, this, "onRecvKickResult");
		dntex.addEventListener(TexEventType.ON_RECV_KICK_CISHU, this, "onRecvKickCishu");
		dntex.addEventListener(TexEventType.ON_RECV_KICK_OVER, this, "onRecvKickOver");
		dntex.addEventListener(TexEventType.ON_RECV_KICK_OVER_BEITI, this, "onRecvKickBeiti");
		dntex.addEventListener(TexEventType.ON_RECV_KICK_SHOW, this, "onRecvKickShow");
		// ------����end-----------------------

		// ----����start-----------------------
		dntex.addEventListener(TexEventType.ON_RECV_CALC_ENABLE, this, "onRecvCalcEnable");
		// ------����end-----------------------

		// ����
		dntex.addEventListener(TexEventType.ON_TEX_RECV_CHAT, this, "onRecvChat");
		dntex.addEventListener(TexEventType.ON_TEX_RECV_PLAY_EMOT, this, "onRecvEmot");

	}

	private void addGameEventListener() {
		// �����Ϸ����ر�
		gsSocket.addEventListener(Event.CLOSE, this, "gameNetClose");

		// ���ټ��
		dnGame.addEventListener(Event.ON_RECV_CHECK_NET, this, "onRecvCheckNet");
		dnGame.addEventListener(Event.ON_RECV_WATCH_ERROR, this, "onRecvWatchError");
		// �յ����½��
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

		// ����GPS�¼�
		dnGame.addEventListener(Event.ON_RECV_GPS, this, "onRecvPlayerLocationData");

	}

	private void removeTexEventListener() {
		if (dntex == null)
			return;

		dntex.removeEventListener(ZjhEventType.ON_ZJH_RECV_SITDOWN, this, "onRecvSitDown"); // ����
		dntex.removeEventListener(ZjhEventType.ON_ZJH_RECV_STANDUP, this, "onRecvStaneUp"); // վ��

		// -----------------------------------------------------------------------------------------------------

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_RESETDISPLAY, this, "onRecvResetDisplay");// �ָ���ʾ
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_TEX_READY, this, "onRecvReady"); // �յ�׼��
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_FAPAI, this, "onRecvFaPai"); // �յ�����
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_BESTPOKES, this, "onRecvBestPokes"); // �յ��Լ����������
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_MYPOKE, this, "onRecvMyPoke"); // �յ��ҵ��˿�
		dntex.removeEventListener(TexEventType.ON_TEX_AUTO_PANEL, this, "onRecvAutoPanel"); // �Զ����
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIVEUP, this, "onRecvGiveUp"); // �յ�Ͷ������Ϣ
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_XIAZHUSUCC, this, "onRecvXiaZhuSucc"); // �յ���ע�ɹ�
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_BUXIAZHU, this, "onRecvBuXiaZhu"); // �յ�����ע
		dntex.removeEventListener(TexEventType.ON_TEX_BUTTON_STATUS, this, "onTexPanel"); // ���ð�ť��Ӧ�Ķ���
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GAMEOVER, this, "onRecvGameOver"); // �յ���Ϸ���������¿�ʼ
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_REFRESHGOLD, this, "onRecvRefreshGold"); // ����������λ�ϵĽ��
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_DESK_POLL_INFO, this, "onRecvDeskPollInfo"); // �յ�����ʳ���Ϣ
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_DESK_INFO, this, "onRecvDeskInfo"); // �յ�����Ϣ
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_SHOW_MY_TOTAL_BEAN, this, "onRecvMyTotalBean"); // �յ��ҵĵ��ݶ�
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_PLAYERINFO, this, "onRecvPlayerInfo"); // ����������״̬
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_POKEINFO, this, "onRecvPokeinfo"); // ����ĳ����λ�ϵ���(�ص�½��)
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_RELOGIN_DESKGOLD, this, "onRecvReloginDeskGold"); // �յ������Ҹı�(�ص�½��)
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GAMESTART, this, "onRecvGameStart"); // �յ���Ϸ��ʼ
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_BUYCHOUMA, this, "onRecvBuyChouma"); // �յ��������
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_JS_WAITING, this, "onRecvJSWaiting"); // �յ��������ڽ���

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_TIMEOUT, this, "onRecvKickMe"); // �յ�����
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_SITDOWN, this, "onRecvSitDown"); // ����
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_STANDUP, this, "onRecvStaneUp"); // վ��
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_SERVER_ERROR, this, "onRecvServerError"); // �յ�����˵���ʾ��Ϣ
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_CHANGEFACE, this, "onRecvChangeFace"); // �յ�����޸�ͷ��

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_SPECAL_ICON, this, "onRecvSpecalIcon"); // �յ������ʶ

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_DESKPOKE, this, "onRecvDeskPoke"); // �յ������˿�
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_WATCHING, this, "onRecvWatching"); // �յ��û���ս
		dntex.removeEventListener(TexEventType.ON_RECV_EXIT_WATCH, this, "onRecvExitWatch"); // �յ��˳���ս
		dntex.removeEventListener(TexEventType.ON_RECV_ADD_EXPERIENCE, this, "onRecvAddExp"); // �յ����Ӿ���
		dntex.removeEventListener(TexEventType.ON_RECV_LEVEL_UPGRADE, this, "onRecvLevelUpgrade"); // �յ��û��ȼ�����
		dntex.removeEventListener(TexEventType.ON_RECV_DAY_ADDEXP, this, "onRecvExpFenhong"); // �յ��û���������;������
		dntex.removeEventListener(TexEventType.ON_RECV_LEVEL_PRIZE_OR_LOST, this, "onRecvPrizeOrLost"); // �յ��Լ��ý�����̭
		dntex.removeEventListener(TexEventType.ON_RECV_LEVEL_SERVER_MSG, this, "onRecvLevelUpgradeInfo"); // �յ��û���������ʾ����Ϣ

		dntex.removeEventListener(TexEventType.ON_BACK_HALL_RESULT, this, "onRecvStandupResult"); // �յ�������֪ͨ���ش���

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_SHOP, this, "onRecvShopList"); // �յ��̳������б�
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_LIST, this, "onRecvGiftList"); // �յ������б�
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_ICON, this, "onRecvGiftIcon"); // �յ������ʶ
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_PLAY_GIFT, this, "onRecvPlayGift"); // ���������ﶯ��
		// dntex.removeEventListener(TexEventType.ON_TEX_RECV_PLAY_EMOT,this,"onRecvPlayEmot");
		// //���ű���
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_FAILD, this, "onRecvBuyGiftFailed"); // ����������ͱ���ʧ��
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_FAILDLIST, this, "onRecvGiftFailedList"); // ������������ʧ��
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_SALE, this, "onRecvGiftSaleResult"); // �յ�����������״̬
		dntex.removeEventListener(TexEventType.ON_RECV_GIFT_XINSHOU_ONE, this, "onRecvGiftXinshouOne"); // ����һ�����Ƶ�����������
		// �յ�����ظ�
		dntex.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_RESPONSE, this, "onRecvGiftResponse");

		dntex.removeEventListener(TexEventType.ON_RECV_LOGIN_SHOW_BETAGIFE, this, "onRecvShowBetaGift");
		dntex.removeEventListener(TexEventType.ON_RECV_LOGIN_GET_BETAGIFE, this, "onRecvGetBetaGift");

		// m_gsProcessor.addEventListener("ON_TEX_RECV_BBS_URL",this,"");

		dntex.removeEventListener(TexEventType.ON_TEX_RECV_SINGLE_DETAIL, this, "onRecvSingleDetail");

		dntex.removeEventListener(TexEventType.ON_RECV_GIVE_JIUJI, this, "onRecvGiveJiuJi");
		// ------����start-----------------------
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_ENABLE, this, "onRecvKickEnable");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK, this, "onRecvKick");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_RESULT, this, "onRecvKickResult");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_CISHU, this, "onRecvKickCishu");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_OVER, this, "onRecvKickOver");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_OVER_BEITI, this, "onRecvKickBeiti");
		dntex.removeEventListener(TexEventType.ON_RECV_KICK_SHOW, this, "onRecvKickShow");
		// ------����end-----------------------

		// ----����start-----------------------
		dntex.removeEventListener(TexEventType.ON_RECV_CALC_ENABLE, this, "onRecvCalcEnable");
		// ------����end-----------------------

		// ����
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

		// ����GPS�¼�
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

	// ////////////////������Ҫ�Ľ�����////////////////////////
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
