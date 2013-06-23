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
	private final String CMD_NOTIFY_ZJH_START = "ZYSZNTST"; // 用户开始
	private final String CMD_NOTIFY_ZJH_PLAYER_STATUS = "ZYSZNTZT"; // 收到本桌各座位的状态
	private final String CMD_NOTIFY_ZJH_FAPAI = "ZYSZNTFP"; // 收到发牌
	private final String CMD_NOTIFY_ZJH_KANPAI = "ZYSZNTKP"; // 收到看牌的内容
	private final String CMD_NOTIFY_ZJH_XIAZHU = "ZYSZSRXZ"; // 收到下注成功信息
	private final String CMD_NOTIRY_ZJH_BIPAI_SUCCESS = "ZYSZSRKP"; // 收到（开牌）比牌完
	private final String CMD_NOTIFY_ZJH_AUTO_BIPAI = "ZYSZNTGO"; // 收到自动比牌（开牌）游戏结算
	private final String CMD_NOTIFY_ZJH_GIVEUP = "ZYSZSRFQ"; // 收到投降消息
	private final String CMD_NOTIRY_ZJH_CLICKCAICHI = "ZYSZCC"; // 收到有人点击彩池
	private final String CMD_NOTIFY_ZJH_CHANGE_CAICHI_SUMGOLD = "ZYSZSNCC"; // 收到改变彩池总金额
	private final String CMD_NOTIFY_ZJH_GET_CAICHI_SUMGOLD = "ZYSZCTIF"; // 收到彩池总金额
	private final String CMD_RESPONSE_ZJH_GETCAICHI = "ZYSZCCOK"; // 收到自己中了彩池
	private final String CMD_NOTIFY_ZJH_BUTTON_STATUS = "ZYSZPAIF"; // 收到设置按钮对应的动作(面板)
	private final String CMD_NOTIFY_ZJH_KICK_USER = "ZYSZREKU"; // 收到自己超时
	private final String CMD_NOTIFY_ZJH_GUANZHAN_STATE = "GZZT"; // 收到观战状态
	private final String CMD_NOTIFY_ZJH_GUANZHAN_FAPAI = "GZFP"; // 观战发牌
	private final String CMD_NOTIFY_ZJH_WATCH = "REWT"; // 收到观战
	private final String CMD_NOTIFY_ZJH_EXIT_WATCH = "EXWT"; // 收到清除牌桌

	public DNetworkZjh(SocketBase net_ptr) {
		super(net_ptr);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap getPokeList(ReadData rd) {
		HashMap data = new HashMap();
		int pokeNum = rd.readByte(); // 牌数
		data.put("pokeNum", pokeNum);
		if (pokeNum > 0) {
			ArrayList list = new ArrayList();
			for (int i = 0; i < pokeNum; i++) {
				list.add(rd.readByte());
			}
			data.put("pokeList", list);
		}
		data.put("pokeType", rd.readByte());
		return data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList getGameOverWinnerLoserData(ReadData rd, int num, String str, int gold) {
		ArrayList list = new ArrayList();
		HashMap temp = null;
		for (int i = 0; i < num; i++) {
			temp = new HashMap();
			temp.put("site", rd.readByte()); // 座位号
			if (str.equals("win")) {
				temp.put("gold", rd.readInt());// 玩家得到多少钱//结算收回多少
				temp.put("wingold", rd.readInt());// 赢了多少
			} else if (str.equals("lose")) {
				temp.put("wingold", rd.readInt());// 输了多少 或 单个人赢了多少
			}
			temp.put("poke", getPokeList(rd));

			if (i == 0 && gold != -999) {
				temp.put("gold", gold);
			}
			list.add(temp);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void onProcessCommand(ReadData rd) {

		String strCmd = rd.getStrCmd();
		Log.i(tag, " DNetworkTjh receive command: " + strCmd);
		if (CMD_NOTIFY_ZJH_SITDOWN.equals(strCmd)) {

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
			data.put("siteno", rd.readByte());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_ZJH_START, data));

		} else if (CMD_NOTIFY_ZJH_PLAYER_STATUS.equals(strCmd)) {

			// 收到本桌各座位的状态
			HashMap data = new HashMap();

			int len = rd.readInt();
			HashMap temp = null;
			for (int i = 0; i < len; i++) {
				temp = new HashMap();
				temp.put("siteno", rd.readByte());
				temp.put("state", rd.readByte());
				temp.put("timecount", rd.readByte());
				data.put(i, temp);
			}
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_PLAYER_STATUS, data));

		} else if (CMD_NOTIFY_ZJH_FAPAI.equals(strCmd)) {
			// 收到发牌
			HashMap data = new HashMap();
			HashMap playerData = new HashMap();

			data.put("zhuangsite", rd.readByte());
			data.put("dizhu", rd.readInt());
			data.put("deskmoney", rd.readInt());
			data.put("caichiAddgold", rd.readInt());
			data.put("players", rd.readByte());
			data.put("playerData", playerData);

			int len = (Integer) data.get("players");
			HashMap temp = null;
			for (int i = 0; i < len; i++) {
				temp = new HashMap();
				temp.put("l_site", rd.readByte());// 座位号
				playerData.put(i, temp);
			}
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_FAPAI, data));

		} else if (CMD_NOTIFY_ZJH_KANPAI.equals(strCmd)) {

			// 收到看牌
			HashMap data = new HashMap();
			data.put("siteno", rd.readByte());
			data.put("poke", getPokeList(rd));
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_KANPAI, data));

		} else if (CMD_NOTIFY_ZJH_XIAZHU.equals(strCmd)) {

			// 用户下注
			HashMap data = new HashMap();
			data.put("siteno", rd.readByte());
			data.put("betmoney", rd.readInt());
			data.put("currbet", rd.readInt());
			data.put("deskmoney", rd.readInt());
			data.put("xztype", rd.readByte());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_XIAZHUSUCC, data));

		} else if (CMD_NOTIRY_ZJH_BIPAI_SUCCESS.equals(strCmd)) {

			// 收到（开牌）比牌完
			HashMap data = new HashMap();
			data.put("kaisiteno", rd.readByte());
			data.put("deskmoney", rd.readInt());
			data.put("betmoney", rd.readInt());
			data.put("currbet", rd.readInt());
			// 赢家信息
			data.put("winner", getGameOverWinnerLoserData(rd, 1, "lose", (Integer) data.get("deskmoney")));
			// 输家信息
			data.put("loser", getGameOverWinnerLoserData(rd, 1, "lose", -999));
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_BIPAISUCC, data));

		} else if (CMD_NOTIFY_ZJH_AUTO_BIPAI.equals(strCmd)) {

			// 收到自动比牌（开牌）游戏结算
			HashMap data = new HashMap();
			data.put("doType", rd.readByte());// 1为下注触发，2为放弃触发
			data.put("deskmoney", rd.readInt()); // 桌面总金额
			int num = 0;
			num = rd.readByte();// 多少人赢了
			data.put("winners", getGameOverWinnerLoserData(rd, num, "win", -999));
			num = rd.readByte();// 多少人输了
			data.put("losers", getGameOverWinnerLoserData(rd, num, "lose", -999));

			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_AUTO_BIPAI, data));

		} else if (CMD_NOTIFY_ZJH_GIVEUP.equals(strCmd)) {

			// 收到投降
			HashMap data = new HashMap();
			data.put("siteno", rd.readByte());
			data.put("losemoney", rd.readInt());
			int isover = rd.readByte();
			data.put("isover", isover);
			if (isover == 1) {
				data.put("winsite", rd.readByte());
				data.put("deskmoney", rd.readInt());
				data.put("wingold", rd.readInt());
				data.put("winpoke", getPokeList(rd));
			}
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_GIVEUP, data));

		} else if (CMD_NOTIRY_ZJH_CLICKCAICHI.equals(strCmd)) {

			// 收到点击彩池
			HashMap data = new HashMap();
			data.put("siteno", rd.readByte());
			data.put("touzhu", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_CAICHICLICK, data));

		} else if (CMD_NOTIFY_ZJH_CHANGE_CAICHI_SUMGOLD.equals(strCmd)) {

			// 收到改变彩池总金额
			HashMap data = new HashMap();
			data.put("sumgold", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_CHANGE_CAICHI_SUMGOLD, data));

		} else if (CMD_NOTIFY_ZJH_GET_CAICHI_SUMGOLD.equals(strCmd)) {

			// 收到彩池总金额
			HashMap data = new HashMap();
			data.put("sumgold", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_CAICHI_SUMGOLD, data));

		} else if (CMD_RESPONSE_ZJH_GETCAICHI.equals(strCmd)) {

			// 收到自己中了彩池
			HashMap data = new HashMap();
			data.put("winnerid", rd.readInt());
			data.put("borcastall", rd.readByte());
			data.put("winuser", rd.readString());
			data.put("wintime", rd.readString());
			data.put("winmoney", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_GET_CAICHI_PRIZE, data));

		} else if (CMD_NOTIFY_ZJH_BUTTON_STATUS.equals(strCmd)) {

			// 收到自己中了彩池
			HashMap data = new HashMap();
			data.put("actor", rd.readByte());
			data.put("btnlook", rd.readByte());
			data.put("btnvs", rd.readByte());
			data.put("btnjiazhu", rd.readByte());
			data.put("btnfollow", rd.readByte());
			data.put("followgold", rd.readInt());
			data.put("btngiveup", rd.readByte());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_BUTTON_STATUS, data));

		} else if (CMD_NOTIFY_ZJH_KICK_USER.equals(strCmd)) {

			// 收到自己超时
			HashMap data = new HashMap();
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_KICK_USER, data));

		} else if (CMD_NOTIFY_ZJH_GUANZHAN_STATE.equals(strCmd)) {

			// 收到观战状态
			HashMap data = new HashMap();
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_GUANZHAN_STATE, data));

		} else if (CMD_NOTIFY_ZJH_GUANZHAN_FAPAI.equals(strCmd)) {

			// 观战发牌
			HashMap data = new HashMap();
			int len = rd.readByte();
			for (int i = 0; i < len; i++) {
				data.put(i, rd.readByte());
			}
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_GUANZHAN_FAPAI, data));

		} else if (CMD_NOTIFY_ZJH_WATCH.equals(strCmd)) {

			// 收到观战
			HashMap data = new HashMap();
			data.put("nick", rd.readString());
			data.put("deskno", rd.readShort());
			data.put("siteno", rd.readByte());

			data.put("gold", rd.readInt());
			data.put("dzcash", rd.readInt());
			data.put("bean", rd.readInt());
			data.put("homepeas", rd.readInt());

			data.put("imgurl", rd.readString());
			data.put("sex", rd.readByte());
			data.put("startState", rd.readByte());
			data.put("userid", rd.readInt());
			data.put("channel", rd.readString());
			data.put("exp", rd.readInt());
			data.put("channelid", rd.readInt());
			data.put("tour_point", rd.readInt());
			data.put("recode", rd.readByte());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_WATCH, data));

		} else if (CMD_NOTIFY_ZJH_EXIT_WATCH.equals(strCmd)) {

			// 收到退出观战
			HashMap data = new HashMap();
			this.dispatchEvent(new Event(ZjhEventType.CMD_ZJH_EXIT_WATCH, data));

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
	public void sendClickStart() throws Exception {

		m_netptr.writeString("ZYSZST");
		m_netptr.writeEnd();
	}

	// 发送点彩池按钮
	public void sendClickCaichi() throws Exception {
		m_netptr.writeString("ZYSZCC");
		m_netptr.writeEnd();
	}

	// 发送点放弃按钮
	public void sendClickGiveUp() throws Exception {

		m_netptr.writeString("ZYSZFQ");
		m_netptr.writeEnd();
	}

	// 发送点开牌按钮
	public void sendClickVS() throws Exception {

		m_netptr.writeString("ZYSZBP");
		m_netptr.writeEnd();
	}

	// 发送点看牌按钮
	public void sendClickLook() throws Exception {

		m_netptr.writeString("ZYSZKP");
		m_netptr.writeEnd();
	}

	// 发送下注结果
	public void sendClickJiazhu(int beishu) throws Exception {
		m_netptr.writeString("ZYSZJZ");
		m_netptr.writeInt(beishu);
		m_netptr.writeEnd();
	}

	// 发送跟注结果
	public void sendClickGenzhu() throws Exception {

		m_netptr.writeString("ZYSZGZ");
		m_netptr.writeEnd();
	}

	// 发送发牌结束
	public void sendFapaiOver() throws Exception {

		m_netptr.writeString("ZYSZFO");
		m_netptr.writeEnd();
	}

	// 强制退出游戏
	public void sendForceOutGame() throws Exception {

		m_netptr.writeString("ZYSZOG");
		m_netptr.writeEnd();
	}

	//请求观战游戏
	public void sendClickWatch(int deskno) throws Exception {

		m_netptr.writeString("REWTEX");
		m_netptr.writeInt(deskno);
		m_netptr.writeEnd();
	}

	// 请求坐下
	public void sendClickSitdown(int deskno) throws Exception {

		m_netptr.writeString("REAUSD");
		m_netptr.writeInt(deskno);
		m_netptr.writeByte((byte) 1);
		m_netptr.writeEnd();
	}

}
