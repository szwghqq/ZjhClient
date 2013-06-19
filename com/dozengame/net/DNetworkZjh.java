package com.dozengame.net;

import java.util.ArrayList;
import java.util.HashMap;
import android.util.Log;

import com.dozengame.event.Event;
import com.dozengame.event.ZjhEventType;
import com.dozengame.net.pojo.ReadData;

/**
 * 网络处理超类 主要功能: 发送命令 接收命令
 * 
 * @author Sing.G
 * 
 */
public class DNetworkZjh extends DNetwork {

	final String tag = "DNetworkZjh";

	private final String CMD_NOTIFY_ZJH_SITDOWN = "RESD"; // 用户坐下
	private final String CMD_NOTIFY_ZJH_STANDUP = "NTSU"; // 用户站起
	private final String CMD_NOTIFY_ZJH_START = "NTST"; // 用户开始
	private final String CMD_NOTIFY_ZJH_DESK_INFO = "NTDT"; // 收到本桌各座位的状态
	private final String CMD_NOTIFY_ZJH_FAPAI = "NTFP"; // 收到发牌
	private final String CMD_NOTIFY_ZJH_KANPAI = "NTKP"; // 收到看牌的内容
	private final String CMD_NOTIFY_ZJH_XIAZHU = "SRXZ"; // 收到下注成功信息
	private final String CMD_NOTIFY_ZJH_GAMEOVER = "NTGO"; // 收到游戏结束的消息
	private final String CMD_NOTIFY_ZJH_REFRESHGOLD = "RESC"; // 收到金币刷新消息
	private final String CMD_NOTIFY_ZJH_GIVEUP = "SRFQ"; // 收到投降消息
	private final String CMD_NOTIFY_ZJH_ROOMINFO = "REIF"; // 收到获得房间信息
	private final String CMD_NOTIFY_ZJH_ROOMBASICINFO = "RMIF"; // 收到获得房间基本信息
	private final String CMD_NOTIFY_ZJH_RECVBIPAISITE = "SRBP"; // 收到可以比牌的座位号
	private final String CMD_NOTIFY_ZJH_RECVOFFLINESITE = "NTTO"; // 收到离线的座位号
	private final String CMD_NOTIFY_ZJH_RELOGIN = "NTGR"; // 收到掉线用户重登录
	private final String CMD_NOTIRY_ZJH_RELOGIN_ACTION = "REAI"; // 收到动作信息
	private final String CMD_NOTIRY_ZJH_CLICKCAICHI = "SNCC"; // 收到有人点击彩池
	private final String CMD_NOTIRY_ZJH_GETCAICHIINFO = "CTIF"; // 收到彩池信息
	private final String CMD_NOTIRY_ZJH_FOLLOWDEAD = "SRGS"; // 收到跟死信息
	private final String CMD_RESPONSE_ZJH_TIMEOUT = "REKU"; // 收到自己超时
	private final String CMD_RESPONSE_ZJH_GETCAICHI = "CCOK"; // 收到自己中了彩池
	private final String CMD_RESPONSE_ZJH_CHOUSHUINUM = "CSNUM";

	public DNetworkZjh(SocketBase net_ptr) {
		super(net_ptr);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getNextStep(ReadData rd) {
		HashMap data = new HashMap();
		data.put("actor", rd.readByte());
		data.put("btnlook", rd.readByte());
		data.put("btnvs", rd.readByte());
		data.put("btnxiazhu", rd.readByte());
		data.put("btnjiazhu", rd.readByte());
		data.put("btnfollow", rd.readByte());
		data.put("btngiveup", rd.readByte());
		data.put("actor", rd.readByte());
		data.put("actor", rd.readByte());
		data.put("actor", rd.readByte());
		data.put("actor", rd.readByte());
		this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_BUTTON_STATUS, data));
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	protected void onProcessCommand(ReadData rd) {

		String strCmd = rd.getStrCmd();
		Log.i(tag, " DNetworkTjh receive command: " + strCmd);
		if (CMD_NOTIRY_ZJH_FOLLOWDEAD.equals(strCmd)) {

			// 跟死
			HashMap data = new HashMap();
			data.put("siteno", rd.readInt()); // 跟死的座位号
			data.put("leaderpeople", rd.readByte()); // 领牌人
			data.put("betmoney", rd.readInt()); // 跟死的人的下注总金额
			data.put("alonebetmoney", rd.readInt()); // 上一注的下注金额（扣除明牌翻倍以及比牌翻倍作用的金额），用作跟注及加注等使用
			data.put("showbetmoney", rd.readInt()); // 显示的下注金额
			data.put("totalbetmoney", rd.readInt()); // 台面总金额
			getNextStep(rd);
			data.put("timeout", rd.readInt()); // 台面总金额
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_FOLLOWDEAD,
					data));

		} else if (CMD_NOTIRY_ZJH_CLICKCAICHI.equals(strCmd)) {

			// 收到点击彩池
			HashMap data = new HashMap();
			data.put("money", rd.readInt()); // 当前彩池总金额;
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_CAICHICLICK,
					data));

		} else if (CMD_NOTIRY_ZJH_GETCAICHIINFO.equals(strCmd)) {

			// 收到彩池信息
			HashMap data = new HashMap();
			data.put("borcastall", rd.readByte());
			data.put("addmoney", rd.readInt());
			data.put("sumgold", rd.readInt());
			data.put("winuser", rd.readString());
			data.put("wintime", rd.readString());
			data.put("winmoney", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_CAICHIINFO,
					data));

		} else if (CMD_NOTIRY_ZJH_RELOGIN_ACTION.equals(strCmd)) {

			// 收到重登录动作按钮
			HashMap data = new HashMap();
			data.put("relogin", 1);
			data.put("timeout", rd.readInt());
			this.dispatchEvent(new Event(
					ZjhEventType.ON_ZJH_RECV_RELOGIN_ACTION, data));

		} else if (CMD_NOTIFY_ZJH_RELOGIN.equals(strCmd)) {

			// 收到掉线用户重登录消息
			HashMap data = new HashMap();
			data.put("deskno", rd.readShort());
			data.put("siteno", rd.readByte());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_RELOGIN, data));

		} else if (CMD_NOTIFY_ZJH_RECVOFFLINESITE.equals(strCmd)) {

			// 收到离线消息
			HashMap data = new HashMap();
			data.put("deskno", rd.readShort());
			data.put("siteno", rd.readByte());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_OFFLINESITE,
					data));

		} else if (CMD_RESPONSE_ZJH_GETCAICHI.equals(strCmd)) {

			// 收到自己中了彩池
			HashMap data = new HashMap();
			data.put("money", rd.readInt());
			this.dispatchEvent(new Event(
					ZjhEventType.ON_ZJH_RECV_GET_CAICHI_PRIZE, data));

		} else if (CMD_RESPONSE_ZJH_CHOUSHUINUM.equals(strCmd)) {

			// 收到抽水多少
			HashMap data = new HashMap();
			data.put("choushui", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_CHOUSHUINUM,
					data));

		} else if (CMD_RESPONSE_ZJH_TIMEOUT.equals(strCmd)) {

			// 收到自己超时
			HashMap data = new HashMap();
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_TIMEOUT, data));

		} else if (CMD_NOTIFY_ZJH_RECVBIPAISITE.equals(strCmd)) {

			// 收到可以比牌的座位号
			HashMap data = new HashMap();
			data.put("desksite", rd.readByte());
			int len = rd.readByte();
			data.put("count", len);

			ArrayList list = new ArrayList();
			HashMap temp = null;
			for (int i = 0; i < len; i++) {
				temp = new HashMap();
				temp.put("site", rd.readByte());
				data.put(i, temp);
			}
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_BIPAISITE,
					data));

		} else if (CMD_NOTIFY_ZJH_ROOMBASICINFO.equals(strCmd)) {

			// 收到刷新房间基本信息
			HashMap data = new HashMap();
			data.put("groupname", rd.readString());// 房间名称
			data.put("deskno", rd.readInt());// 桌号
			data.put("peilv", rd.readInt());// 赔率
			data.put("miniGold", rd.readInt());
			data.put("g_minalonebetmoney", rd.readInt());
			data.put("g_maxalonemoney", rd.readInt());
			data.put("g_maxbetmoney", rd.readInt());
			data.put("caichiaddmoney", rd.readInt());
			this.dispatchEvent(new Event(
					ZjhEventType.ON_ZJH_RECV_ROOMBASICINFO, data));

		} else if (CMD_NOTIFY_ZJH_ROOMINFO.equals(strCmd)) {

			// 收到刷新房间信息
			HashMap data = new HashMap();
			data.put("mysiteno", rd.readByte());
			data.put("mydeskno", rd.readInt());
			data.put("myislook", rd.readInt());

			if ((Integer) data.get("myislook") == 1) {
				data.put("num1", rd.readByte());
				data.put("num2", rd.readByte());
				data.put("num3", rd.readByte());
			}

			data.put("g_nextActor", rd.readByte());
			data.put("leader", rd.readByte());
			data.put("l_zhuangjia", rd.readByte());
			data.put("l_money", rd.readInt());
			data.put("alone_money", rd.readInt());
			data.put("players", rd.readByte());
			data.put("timeout", rd.readByte());
			data.put("iscaichi", rd.readByte());

			int len = (Integer) data.get("players");
			HashMap temp = null;
			for (int i = 0; i < len; i++) {
				temp = new HashMap();
				temp.put("l_site", rd.readByte());
				temp.put("status", rd.readByte());
				temp.put("imgidx", rd.readByte());
				temp.put("l_look", rd.readByte());
				data.put(i, temp);
			}

			data.put("nScore", rd.readInt());
			data.put("l_zhuangjia", rd.readInt());
			data.put("l_zhuangjia", rd.readInt());
			data.put("l_zhuangjia", rd.readInt());

			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_ROOMINFO,
					data));

		} else if (CMD_NOTIFY_ZJH_GIVEUP.equals(strCmd)) {

			// 收到投降
			HashMap data = new HashMap();
			data.put("desksite", rd.readByte());
			data.put("sex", rd.readByte());
			data.put("imgidx", rd.readByte());
			data.put("g_leader", rd.readByte());
			getNextStep(rd);
			data.put("timeout", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_GIVEUP, data));

		} else if (CMD_NOTIFY_ZJH_REFRESHGOLD.equals(strCmd)) {

			// 刷新金币
			HashMap data = new HashMap();
			data.put("nScore", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_REFRESHGOLD,
					data));

		} else if (CMD_NOTIFY_ZJH_GAMEOVER.equals(strCmd)) {

			// 游戏结束，重开一局
			HashMap data = new HashMap();
			data.put("deskno", rd.readInt());// 哪一桌
			data.put("siteno", rd.readByte());// 哪一座位号
			data.put("winsite", rd.readByte());// 那座位号赢了，或者是谁退出
			data.put("gameoverreason", rd.readByte());// 结束原因 1 正常结束, 2, 3 有人逃跑
			data.put("begintimeout", rd.readByte());// 开始限制时间
			data.put("betcountmoney", rd.readInt());// 桌面下注总金额
			data.put("choushui", rd.readInt());// /抽水
			data.put("winnick", rd.readString());// 赢家姓名
			data.put("gold", rd.readInt());// 赢家总金币
			data.put("players", rd.readByte());// 坐下的人数

			int len = (Integer) data.get("players");
			HashMap temp = null;
			for (int i = 0; i < len; i++) {
				temp = new HashMap();
				temp.put("l_site", rd.readByte());
				temp.put("l_status", rd.readByte());
				temp.put("betmoney", rd.readInt());
				temp.put("num1", rd.readByte());
				temp.put("num2", rd.readByte());
				temp.put("num3", rd.readByte());
				data.put(i, temp);
			}

			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_GAMEOVER,
					data));

		} else if (CMD_NOTIFY_ZJH_XIAZHU.equals(strCmd)) {

			// 用户下注
			HashMap data = new HashMap();
			data.put("siteno", rd.readByte());
			data.put("sex", rd.readByte());
			data.put("g_leader", rd.readByte());
			data.put("money", rd.readInt());
			data.put("alonemoney", rd.readInt());
			data.put("aloneshowmoney", rd.readInt());
			data.put("betmoney", rd.readInt());
			data.put("xztype", rd.readByte());
			data.put("bpSite", rd.readByte());
			data.put("bpWin", rd.readByte());
			data.put("loserimgidx", rd.readByte());
			getNextStep(rd);
			data.put("timeout", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_XIAZHUSUCC,
					data));

		} else if (CMD_NOTIFY_ZJH_SITDOWN.equals(strCmd)) {

			// 用户坐下
			HashMap data = new HashMap();
			data.put("recode", rd.readByte());
			data.put("bReloginUser", rd.readByte());
			data.put("userkey", rd.readString());
			data.put("nick", rd.readString());
			data.put("deskno", rd.readInt());
			data.put("siteno", rd.readInt());

			data.put("olddeskno", rd.readInt());
			data.put("oldsiteno", rd.readInt());
			data.put("gold", rd.readInt());
			data.put("dzcash", rd.readInt());
			data.put("bean", rd.readInt());
			data.put("homepeas", rd.readInt());

			data.put("cityName", rd.readString());
			data.put("beginTimeOut", rd.readInt());
			data.put("face", rd.readString());
			data.put("sex", rd.readByte());
			data.put("startState", rd.readByte());
			data.put("userid", rd.readInt());

			data.put("channel", rd.readString());
			data.put("gameexp", rd.readInt());
			data.put("channelid", rd.readInt());
			data.put("peilv", rd.readInt());

			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_SITDOWN, data));

		} else if (CMD_NOTIFY_ZJH_STANDUP.equals(strCmd)) {

			// 用户站起
			HashMap data = new HashMap();
			data.put("recode", rd.readInt());
			data.put("currentuser", rd.readString());
			data.put("nick", rd.readString());
			data.put("deskno", rd.readInt());
			data.put("siteno", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_STANDUP, data));

		} else if (CMD_NOTIFY_ZJH_START.equals(strCmd)) {

			// 收到用户点开始
			HashMap data = new HashMap();
			data.put("recode", rd.readInt());
			data.put("currentuser", rd.readString());
			data.put("deskno", rd.readInt());
			data.put("siteno", rd.readInt());
			data.put("notReadyNum", rd.readByte());

			int len = (Integer) data.get("notReadyNum");
			HashMap temp = null;
			ArrayList list1 = new ArrayList();
			ArrayList list2 = new ArrayList();
			for (int i = 0; i < len; i++) {
				list1.add(i, rd.readByte());
				list2.add(i, rd.readByte());
			}
			data.put("notReadySite", list1);
			data.put("notReadyClock", list2);

			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_ZJH_START,
					data));

		} else if (CMD_NOTIFY_ZJH_DESK_INFO.equals(strCmd)) {

			// 收到本桌各座位的状态
			HashMap data = new HashMap();

			int len = rd.readInt();
			HashMap temp = null;
			for (int i = 0; i < len; i++) {
				temp = new HashMap();
				temp.put("siteno", rd.readInt());
				temp.put("userid", rd.readString());
				temp.put("startState", rd.readInt());
				temp.put("face", rd.readInt());
				temp.put("nick", rd.readString());
				temp.put("gold", rd.readInt());
				temp.put("provinceid", rd.readByte());
				temp.put("cityname", rd.readString());
				data.put(i, temp);
			}
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_DESK_INFO,
					data));

		} else if (CMD_NOTIFY_ZJH_FAPAI.equals(strCmd)) {
			// 收到发牌
			HashMap data = new HashMap();
			data.put("siteno", rd.readByte());
			data.put("minigold", rd.readInt());
			data.put("deskmoney", rd.readInt());
			getNextStep(rd);
			data.put("timeout", rd.readInt());
			data.put("players", rd.readByte());

			int len = (Integer) data.get("players");
			HashMap temp = null;
			for (int i = 0; i < len; i++) {
				temp = new HashMap();
				temp.put("l_site", rd.readByte());// 座位号
				temp.put("imgidx", rd.readByte());// 头像ID
				data.put(i, temp);
			}

			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_FAPAI, data));

		} else if (CMD_NOTIFY_ZJH_KANPAI.equals(strCmd)) {

			// 收到看牌
			HashMap data = new HashMap();
			data.put("siteno", rd.readByte());
			data.put("sex", rd.readByte());
			data.put("num1", rd.readByte());
			data.put("num2", rd.readByte());
			data.put("num3", rd.readByte());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_KANPAI, data));

		} else if ("NTTT".equals(strCmd)) {

		} else {
			Log.i(tag, "DNetworkZjh not execute command: " + strCmd);
		}

	}

	public void sendRequestBackHall() throws Exception {
		// DTrace.traceex("发送返回大厅的请求,sendRequestBackHall()");
		m_netptr.writeString("RQBH");
		m_netptr.writeEnd();
	}

	// 发送点开始按钮
	public void sendStart() throws Exception {

		m_netptr.writeString("GCST");
		m_netptr.writeEnd();
	}

	// 发送点彩池按钮
	public void sendClickCaichi() throws Exception {
		m_netptr.writeString("CKCC");
		m_netptr.writeEnd();
	}

	// 发送点放弃按钮
	public void sendClickGiveUp() throws Exception {

		m_netptr.writeString("YHFQ");
		m_netptr.writeEnd();
	}

	// 获取比牌座位号
	public void getBiPaiSite() throws Exception {

		m_netptr.writeString("YHBP");
		m_netptr.writeEnd();
	}

	// 发送点看牌按钮
	public void sendClickLook() throws Exception {

		m_netptr.writeString("YHKP");
		m_netptr.writeEnd();
	}

	// 发送下注结果
	public void sendXiaZhuData(int score, int xzType, int bpSite)
			throws Exception {
		m_netptr.writeString("YHXZ");
		m_netptr.writeByte((byte) xzType);
		m_netptr.writeInt(score);
		m_netptr.writeByte((byte) bpSite);// --g_xztype = 4表示比牌下注，传入要比牌的座位号
		m_netptr.writeEnd();
	}

	// 点下注按钮
	public void sendClickXiaZhu() throws Exception {

		m_netptr.writeString("YHKP");
		m_netptr.writeEnd();
	}

	// 发送接受新手任务
	public void sendAcceptGuide() throws Exception {

		m_netptr.writeString("RRAG");
		m_netptr.writeEnd();
	}

	// 发送重开一局请求
	public void sendRestartGame() throws Exception {

		m_netptr.writeString("YHCK");
		m_netptr.writeEnd();
	}

	// 发送刷新金币请求及房间信息
	public void sendRefreshGold() throws Exception {

		m_netptr.writeString("RQSC");
		m_netptr.writeEnd();
	}

	// 发送刷新房间信息
	public void sendReqRoomInfo() throws Exception {

		m_netptr.writeString("RQIF");
		m_netptr.writeEnd();
	}

}
