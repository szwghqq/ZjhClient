package com.dozengame.net;

import java.util.ArrayList;
import java.util.HashMap;
import android.util.Log;

import com.dozengame.event.Event;
import com.dozengame.event.ZjhEventType;
import com.dozengame.net.pojo.ReadData;

/**
 * ���紦���� ��Ҫ����: �������� ��������
 * 
 * @author Sing.G
 * 
 */
public class DNetworkZjh extends DNetwork {

	final String tag = "DNetworkZjh";

	private final String CMD_NOTIFY_ZJH_SITDOWN = "RESD"; // �û�����
	private final String CMD_NOTIFY_ZJH_STANDUP = "NTSU"; // �û�վ��
	private final String CMD_NOTIFY_ZJH_START = "ZYSZNTST"; // �û���ʼ
	private final String CMD_NOTIFY_ZJH_PLAYER_STATUS = "ZYSZNTZT"; // �յ���������λ��״̬
	private final String CMD_NOTIFY_ZJH_FAPAI = "ZYSZNTFP"; // �յ�����
	private final String CMD_NOTIFY_ZJH_KANPAI = "ZYSZNTKP"; // �յ����Ƶ�����
	private final String CMD_NOTIFY_ZJH_XIAZHU = "ZYSZSRXZ"; // �յ���ע�ɹ���Ϣ
	private final String CMD_NOTIRY_ZJH_BIPAI_SUCCESS = "ZYSZSRKP"; // �յ������ƣ�������
	private final String CMD_NOTIFY_ZJH_AUTO_BIPAI = "ZYSZNTGO"; // �յ��Զ����ƣ����ƣ���Ϸ����
	private final String CMD_NOTIFY_ZJH_GIVEUP = "ZYSZSRFQ"; // �յ�Ͷ����Ϣ
	private final String CMD_NOTIRY_ZJH_CLICKCAICHI = "ZYSZCC"; // �յ����˵���ʳ�
	private final String CMD_NOTIFY_ZJH_CHANGE_CAICHI_SUMGOLD = "ZYSZSNCC"; // �յ��ı�ʳ��ܽ��
	private final String CMD_NOTIFY_ZJH_GET_CAICHI_SUMGOLD = "ZYSZCTIF"; // �յ��ʳ��ܽ��
	private final String CMD_RESPONSE_ZJH_GETCAICHI = "ZYSZCCOK"; // �յ��Լ����˲ʳ�
	private final String CMD_NOTIFY_ZJH_BUTTON_STATUS = "ZYSZPAIF"; // �յ����ð�ť��Ӧ�Ķ���(���)
	private final String CMD_NOTIFY_ZJH_KICK_USER = "ZYSZREKU"; // �յ��Լ���ʱ
	private final String CMD_NOTIFY_ZJH_GUANZHAN_STATE = "GZZT"; // �յ���ս״̬
	private final String CMD_NOTIFY_ZJH_GUANZHAN_FAPAI = "GZFP"; // ��ս����
	private final String CMD_NOTIFY_ZJH_WATCH = "REWT"; // �յ���ս
	private final String CMD_NOTIFY_ZJH_EXIT_WATCH = "EXWT"; // �յ��������

	public DNetworkZjh(SocketBase net_ptr) {
		super(net_ptr);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap getPokeList(ReadData rd) {
		HashMap data = new HashMap();
		int pokeNum = rd.readByte(); // ����
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
			temp.put("site", rd.readByte()); // ��λ��
			if (str.equals("win")) {
				temp.put("gold", rd.readInt());// ��ҵõ�����Ǯ//�����ջض���
				temp.put("wingold", rd.readInt());// Ӯ�˶���
			} else if (str.equals("lose")) {
				temp.put("wingold", rd.readInt());// ���˶��� �� ������Ӯ�˶���
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

			// �û�����
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

			// �û�վ��
			HashMap data = new HashMap();
			data.put("recode", rd.readInt());
			data.put("currentuser", rd.readString());
			data.put("nick", rd.readString());
			data.put("deskno", rd.readInt());
			data.put("siteno", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_STANDUP, data));

		} else if (CMD_NOTIFY_ZJH_START.equals(strCmd)) {

			// �յ��û��㿪ʼ
			HashMap data = new HashMap();
			data.put("siteno", rd.readByte());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_ZJH_START, data));

		} else if (CMD_NOTIFY_ZJH_PLAYER_STATUS.equals(strCmd)) {

			// �յ���������λ��״̬
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
			// �յ�����
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
				temp.put("l_site", rd.readByte());// ��λ��
				playerData.put(i, temp);
			}
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_FAPAI, data));

		} else if (CMD_NOTIFY_ZJH_KANPAI.equals(strCmd)) {

			// �յ�����
			HashMap data = new HashMap();
			data.put("siteno", rd.readByte());
			data.put("poke", getPokeList(rd));
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_KANPAI, data));

		} else if (CMD_NOTIFY_ZJH_XIAZHU.equals(strCmd)) {

			// �û���ע
			HashMap data = new HashMap();
			data.put("siteno", rd.readByte());
			data.put("betmoney", rd.readInt());
			data.put("currbet", rd.readInt());
			data.put("deskmoney", rd.readInt());
			data.put("xztype", rd.readByte());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_XIAZHUSUCC, data));

		} else if (CMD_NOTIRY_ZJH_BIPAI_SUCCESS.equals(strCmd)) {

			// �յ������ƣ�������
			HashMap data = new HashMap();
			data.put("kaisiteno", rd.readByte());
			data.put("deskmoney", rd.readInt());
			data.put("betmoney", rd.readInt());
			data.put("currbet", rd.readInt());
			// Ӯ����Ϣ
			data.put("winner", getGameOverWinnerLoserData(rd, 1, "lose", (Integer) data.get("deskmoney")));
			// �����Ϣ
			data.put("loser", getGameOverWinnerLoserData(rd, 1, "lose", -999));
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_BIPAISUCC, data));

		} else if (CMD_NOTIFY_ZJH_AUTO_BIPAI.equals(strCmd)) {

			// �յ��Զ����ƣ����ƣ���Ϸ����
			HashMap data = new HashMap();
			data.put("doType", rd.readByte());// 1Ϊ��ע������2Ϊ��������
			data.put("deskmoney", rd.readInt()); // �����ܽ��
			int num = 0;
			num = rd.readByte();// ������Ӯ��
			data.put("winners", getGameOverWinnerLoserData(rd, num, "win", -999));
			num = rd.readByte();// ����������
			data.put("losers", getGameOverWinnerLoserData(rd, num, "lose", -999));

			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_AUTO_BIPAI, data));

		} else if (CMD_NOTIFY_ZJH_GIVEUP.equals(strCmd)) {

			// �յ�Ͷ��
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

			// �յ�����ʳ�
			HashMap data = new HashMap();
			data.put("siteno", rd.readByte());
			data.put("touzhu", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_CAICHICLICK, data));

		} else if (CMD_NOTIFY_ZJH_CHANGE_CAICHI_SUMGOLD.equals(strCmd)) {

			// �յ��ı�ʳ��ܽ��
			HashMap data = new HashMap();
			data.put("sumgold", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_CHANGE_CAICHI_SUMGOLD, data));

		} else if (CMD_NOTIFY_ZJH_GET_CAICHI_SUMGOLD.equals(strCmd)) {

			// �յ��ʳ��ܽ��
			HashMap data = new HashMap();
			data.put("sumgold", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_CAICHI_SUMGOLD, data));

		} else if (CMD_RESPONSE_ZJH_GETCAICHI.equals(strCmd)) {

			// �յ��Լ����˲ʳ�
			HashMap data = new HashMap();
			data.put("winnerid", rd.readInt());
			data.put("borcastall", rd.readByte());
			data.put("winuser", rd.readString());
			data.put("wintime", rd.readString());
			data.put("winmoney", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_GET_CAICHI_PRIZE, data));

		} else if (CMD_NOTIFY_ZJH_BUTTON_STATUS.equals(strCmd)) {

			// �յ��Լ����˲ʳ�
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

			// �յ��Լ���ʱ
			HashMap data = new HashMap();
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_KICK_USER, data));

		} else if (CMD_NOTIFY_ZJH_GUANZHAN_STATE.equals(strCmd)) {

			// �յ���ս״̬
			HashMap data = new HashMap();
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_GUANZHAN_STATE, data));

		} else if (CMD_NOTIFY_ZJH_GUANZHAN_FAPAI.equals(strCmd)) {

			// ��ս����
			HashMap data = new HashMap();
			int len = rd.readByte();
			for (int i = 0; i < len; i++) {
				data.put(i, rd.readByte());
			}
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_GUANZHAN_FAPAI, data));

		} else if (CMD_NOTIFY_ZJH_WATCH.equals(strCmd)) {

			// �յ���ս
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

			// �յ��˳���ս
			HashMap data = new HashMap();
			this.dispatchEvent(new Event(ZjhEventType.CMD_ZJH_EXIT_WATCH, data));

		} else if ("NTTT".equals(strCmd)) {

		} else {
			Log.i(tag, "DNetworkZjh not execute command: " + strCmd);
		}

	}

	public void sendRequestBackHall() throws Exception {
		// DTrace.traceex("���ͷ��ش���������,sendRequestBackHall()");
		m_netptr.writeString("RQBH");
		m_netptr.writeEnd();
	}

	// ���͵㿪ʼ��ť
	public void sendClickStart() throws Exception {

		m_netptr.writeString("ZYSZST");
		m_netptr.writeEnd();
	}

	// ���͵�ʳذ�ť
	public void sendClickCaichi() throws Exception {
		m_netptr.writeString("ZYSZCC");
		m_netptr.writeEnd();
	}

	// ���͵������ť
	public void sendClickGiveUp() throws Exception {

		m_netptr.writeString("ZYSZFQ");
		m_netptr.writeEnd();
	}

	// ���͵㿪�ư�ť
	public void sendClickVS() throws Exception {

		m_netptr.writeString("ZYSZBP");
		m_netptr.writeEnd();
	}

	// ���͵㿴�ư�ť
	public void sendClickLook() throws Exception {

		m_netptr.writeString("ZYSZKP");
		m_netptr.writeEnd();
	}

	// ������ע���
	public void sendClickJiazhu(int beishu) throws Exception {
		m_netptr.writeString("ZYSZJZ");
		m_netptr.writeInt(beishu);
		m_netptr.writeEnd();
	}

	// ���͸�ע���
	public void sendClickGenzhu() throws Exception {

		m_netptr.writeString("ZYSZGZ");
		m_netptr.writeEnd();
	}

	// ���ͷ��ƽ���
	public void sendFapaiOver() throws Exception {

		m_netptr.writeString("ZYSZFO");
		m_netptr.writeEnd();
	}

	// ǿ���˳���Ϸ
	public void sendForceOutGame() throws Exception {

		m_netptr.writeString("ZYSZOG");
		m_netptr.writeEnd();
	}

	//�����ս��Ϸ
	public void sendClickWatch(int deskno) throws Exception {

		m_netptr.writeString("REWTEX");
		m_netptr.writeInt(deskno);
		m_netptr.writeEnd();
	}

	// ��������
	public void sendClickSitdown(int deskno) throws Exception {

		m_netptr.writeString("REAUSD");
		m_netptr.writeInt(deskno);
		m_netptr.writeByte((byte) 1);
		m_netptr.writeEnd();
	}

}
