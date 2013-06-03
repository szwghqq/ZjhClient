package com.dozengame.net;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.util.Log;

import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.event.EventDispatcher;
import com.dozengame.net.pojo.DGroupInfoItem;
import com.dozengame.net.pojo.ReadData;
 
/**
 * 达人网络服务中心
 * @author hewengao
 *
 */
public class DNetworkCenter extends EventDispatcher implements CallBack{
	// 发送的命令
	private final String CMD_REQUEST_LOGIN = "RQLG"; // 请求登陆
	private final String CMD_REQUEST_GROUP_INFO = "RQGI"; // 请求获取房间列表

	// 接受的命令(响应)
	private final String CMD_RESPONSE_LOGIN_RESULT = "RELG"; // 登陆结果
	private final String CMD_RESPONSE_GROUP_INFO = "REGI"; // 房间列表
	private final String CMD_RESPONSE_GROUP_INFO_END = "REGIEND"; // 房间列表结束
	private final String ON_RECV_DISABLE_ACCOUNT = "DSAC"; // 收到封号信息

	// 接受的命令(通知)
	private final String CMD_NOTIFY_MESSAGE = "NTMS"; // 通知信息
	private final String CMD_NOTIFT_LINEFAILED = "NTOF"; // 掉线通知
	private final String CMD_NOTIFT_GIVE_GOLD = "NTGL"; // 登录送钱
	private SocketBase dRSocket = null;
	 
	public DNetworkCenter(SocketBase dRSocket) {
		this.dRSocket = dRSocket;
		this.dRSocket.addEventListener(Event.SOCKET_DATA, this, "onDataRecv");
	}

	/**
	 * 接收数据
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	public void onDataRecv(Event e) throws IOException, Exception {
		ReadData rd =(ReadData)e.getData();
		if(rd ==null)return ;
		  String strCmd = rd.getStrCmd();
		System.out.println("DNetworkCenter: 接收处理指令: " + strCmd);
		if (strCmd.equals(CMD_RESPONSE_LOGIN_RESULT)) {
			// 接收登录结果
			receiveLoginResult(rd);
		} else if (strCmd.equals(ON_RECV_DISABLE_ACCOUNT)) {
			// 接收收封号信息
			receiveDisableAccount(rd);
		} else if (strCmd.equals(CMD_RESPONSE_GROUP_INFO)) {
			// 接收房间信息
			receiveGroupInfo(rd);
		} else if (strCmd.equals(CMD_RESPONSE_GROUP_INFO_END)) {
			// 房间列表结束
			System.out.println("CMD_RESPONSE_GROUP_INFO_END");
			dispatchEvent(new Event(Event.ON_RECV_GROUP_INFO, roomsInfo));
		} else if (strCmd.equals(CMD_NOTIFY_MESSAGE)) {
			// 接收通知信息
			receiveNotifyMessage(rd);
		} else if (strCmd.equals(CMD_NOTIFT_LINEFAILED)) {
			// 掉线通知
			// this.dispatchEvent(new
			// DNetworkCenterEvent(DNetworkCenterEvent.ON_RECONNECT, null));
		} else if (strCmd.equals(CMD_NOTIFT_GIVE_GOLD)) {
			// 登录送钱
			receiveGiveGlod(rd);
		} else {
			System.out.println("!!!DNetworkCenter: 发现未处理指令: " + strCmd);
		}

	}

	// 房间信息类
	private HashMap roomsInfo = new HashMap();

	// 好友类
	private List frienddata = null;
	private int friendIdx = 0;
	private boolean friendLoadedFlag = false;

	/**
	 * 发送登录信息－－－－－－－封测代码
	 * 
	 * @param strUsername
	 * @param strPassword
	 */
//	public void sendLoginInfo(String strUsername, String strPassword)
//			throws Exception {
//		dRSocket.writeString(CMD_REQUEST_LOGIN);
//		dRSocket.writeString(strUsername);
//		dRSocket.writeString(strPassword);
//		dRSocket.writeByte((byte) 0);
//		dRSocket.writeString("1");
//		dRSocket.writeEnd();
//	}

	/**
	 * 发送取得分区信息
	 * 
	 * @param strGameName
	 */
	public void sendGetAreaInfo(String strGameName) throws Exception {
		System.out.println("##发送取得分区信息");
		dRSocket.writeString(CMD_REQUEST_GROUP_INFO);
		dRSocket.writeString(strGameName);
		dRSocket.writeEnd();
	}

	/**
	 * 发送防踢信息
	 * 
	 * @param nToken
	 * @throws Exception
	 */
	public void sendToken(int nToken) throws Exception {
		dRSocket.writeString("ANYC");
		dRSocket.writeInt(nToken);
		dRSocket.writeEnd();
	}

	/**
	 * 接收登录结果
	 */
	private void receiveLoginResult(ReadData rd) {
		
		HashMap data = new HashMap();
		data.put("code", rd.readShort());
		data.put("lastroomname", rd.readString());
		data.put("lastroomid", rd.readInt());
		data.put("userid", rd.readInt());
		data.put("gamekey", rd.readString());
		data.put("md5", rd.readString());
		data.put("isnewuser", rd.readByte());
		
		 
	    dispatchEvent(new Event(Event.ON_RECV_LOGIN_RESULT, data));

	}

	/**
	 * 收到封号信息
	 */
	private void receiveDisableAccount(ReadData rd) {
		// 收到封号信息
		Hashtable data = new Hashtable();
		data.put("userid", rd.readInt());
		byte status = rd.readByte();
		data.put("status", status);

		if (status == 1) {
			data.put("endtime", rd.readString());
		}
		// this.dispatchEvent(new
		// DNetworkCenterEvent(DNetworkCenterEvent.ON_RECV_DISABLE_ACCOUNT,
		// data));
	}

	/**
	 * 接收房间信息
	 */
	private void receiveGroupInfo(ReadData rd) {
		// 房间列表
		ArrayList<DGroupInfoItem> roomList = new ArrayList<DGroupInfoItem>();
		String groupid = "";
		String name = rd.readString();
		System.out.println("name: "+name);
		while (!(groupid = rd.readString()).equals("")) {
			 
			DGroupInfoItem item = new DGroupInfoItem();
			item.groupid = Integer.valueOf(groupid);
			item.groupname = rd.readString();
			item.gamepeilv = rd.readInt();
			item.ip = rd.readString();
			item.port = rd.readInt();
			
		
			System.out.println("item.groupname: "+item.groupname );
			System.out.println("item.ip: "+item.ip);
			System.out.println("item.port: "+item.port);
			
			item.curronline = rd.readInt();
			item.maxonline = rd.readInt();
			item.isguildroom = rd.readInt();
			item.istourroom = rd.readInt();
			item.ishighroom = rd.readInt(); // 是否为高手场 todo cw
			item.ishuanle = rd.readInt(); // 是否为欢乐场
			item.nocheat = rd.readInt(); // 是否为防作弊
			item.at_least_gold = rd.readInt();
			item.at_most_gold = rd.readInt();
			roomList.add(item);
			item.toString();
		}
		roomsInfo.put(name, roomList);
	}

	/**
	 * 接收通知信息
	 */
	private void receiveNotifyMessage(ReadData rd) {
		// 通知信息
		Hashtable data = new Hashtable();
		data.put("message", rd.readString());
		// this.dispatchEvent(new
		// DNetworkCenterEvent(DNetworkCenterEvent.ON_RECV_MESSAGE, data));

	}

	/**
	 * 登录送钱
	 */
	private void receiveGiveGlod(ReadData rd) {
		Hashtable data = new Hashtable();
		data.put("gold", rd.readInt());
		// this.dispatchEvent(new
		// DNetworkCenterEvent(DNetworkCenterEvent.ON_RECV_GIVE_GOLD, data));
	}
  
}
