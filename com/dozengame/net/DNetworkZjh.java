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
	private final String CMD_NOTIFY_ZJH_START = "NTST"; // �û���ʼ
	private final String CMD_NOTIFY_ZJH_DESK_INFO = "NTDT"; // �յ���������λ��״̬
	private final String CMD_NOTIFY_ZJH_FAPAI = "NTFP"; // �յ�����
	private final String CMD_NOTIFY_ZJH_KANPAI = "NTKP"; // �յ����Ƶ�����
	private final String CMD_NOTIFY_ZJH_XIAZHU = "SRXZ"; // �յ���ע�ɹ���Ϣ
	private final String CMD_NOTIFY_ZJH_GAMEOVER = "NTGO"; // �յ���Ϸ��������Ϣ
	private final String CMD_NOTIFY_ZJH_REFRESHGOLD = "RESC"; // �յ����ˢ����Ϣ
	private final String CMD_NOTIFY_ZJH_GIVEUP = "SRFQ"; // �յ�Ͷ����Ϣ
	private final String CMD_NOTIFY_ZJH_ROOMINFO = "REIF"; // �յ���÷�����Ϣ
	private final String CMD_NOTIFY_ZJH_ROOMBASICINFO = "RMIF"; // �յ���÷��������Ϣ
	private final String CMD_NOTIFY_ZJH_RECVBIPAISITE = "SRBP"; // �յ����Ա��Ƶ���λ��
	private final String CMD_NOTIFY_ZJH_RECVOFFLINESITE = "NTTO"; // �յ����ߵ���λ��
	private final String CMD_NOTIFY_ZJH_RELOGIN = "NTGR"; // �յ������û��ص�¼
	private final String CMD_NOTIRY_ZJH_RELOGIN_ACTION = "REAI"; // �յ�������Ϣ
	private final String CMD_NOTIRY_ZJH_CLICKCAICHI = "SNCC"; // �յ����˵���ʳ�
	private final String CMD_NOTIRY_ZJH_GETCAICHIINFO = "CTIF"; // �յ��ʳ���Ϣ
	private final String CMD_NOTIRY_ZJH_FOLLOWDEAD = "SRGS"; // �յ�������Ϣ
	private final String CMD_RESPONSE_ZJH_TIMEOUT = "REKU"; // �յ��Լ���ʱ
	private final String CMD_RESPONSE_ZJH_GETCAICHI = "CCOK"; // �յ��Լ����˲ʳ�
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

			// ����
			HashMap data = new HashMap();
			data.put("siteno", rd.readInt()); // ��������λ��
			data.put("leaderpeople", rd.readByte()); // ������
			data.put("betmoney", rd.readInt()); // �������˵���ע�ܽ��
			data.put("alonebetmoney", rd.readInt()); // ��һע����ע���۳����Ʒ����Լ����Ʒ������õĽ���������ע����ע��ʹ��
			data.put("showbetmoney", rd.readInt()); // ��ʾ����ע���
			data.put("totalbetmoney", rd.readInt()); // ̨���ܽ��
			getNextStep(rd);
			data.put("timeout", rd.readInt()); // ̨���ܽ��
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_FOLLOWDEAD,
					data));

		} else if (CMD_NOTIRY_ZJH_CLICKCAICHI.equals(strCmd)) {

			// �յ�����ʳ�
			HashMap data = new HashMap();
			data.put("money", rd.readInt()); // ��ǰ�ʳ��ܽ��;
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_CAICHICLICK,
					data));

		} else if (CMD_NOTIRY_ZJH_GETCAICHIINFO.equals(strCmd)) {

			// �յ��ʳ���Ϣ
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

			// �յ��ص�¼������ť
			HashMap data = new HashMap();
			data.put("relogin", 1);
			data.put("timeout", rd.readInt());
			this.dispatchEvent(new Event(
					ZjhEventType.ON_ZJH_RECV_RELOGIN_ACTION, data));

		} else if (CMD_NOTIFY_ZJH_RELOGIN.equals(strCmd)) {

			// �յ������û��ص�¼��Ϣ
			HashMap data = new HashMap();
			data.put("deskno", rd.readShort());
			data.put("siteno", rd.readByte());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_RELOGIN, data));

		} else if (CMD_NOTIFY_ZJH_RECVOFFLINESITE.equals(strCmd)) {

			// �յ�������Ϣ
			HashMap data = new HashMap();
			data.put("deskno", rd.readShort());
			data.put("siteno", rd.readByte());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_OFFLINESITE,
					data));

		} else if (CMD_RESPONSE_ZJH_GETCAICHI.equals(strCmd)) {

			// �յ��Լ����˲ʳ�
			HashMap data = new HashMap();
			data.put("money", rd.readInt());
			this.dispatchEvent(new Event(
					ZjhEventType.ON_ZJH_RECV_GET_CAICHI_PRIZE, data));

		} else if (CMD_RESPONSE_ZJH_CHOUSHUINUM.equals(strCmd)) {

			// �յ���ˮ����
			HashMap data = new HashMap();
			data.put("choushui", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_CHOUSHUINUM,
					data));

		} else if (CMD_RESPONSE_ZJH_TIMEOUT.equals(strCmd)) {

			// �յ��Լ���ʱ
			HashMap data = new HashMap();
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_TIMEOUT, data));

		} else if (CMD_NOTIFY_ZJH_RECVBIPAISITE.equals(strCmd)) {

			// �յ����Ա��Ƶ���λ��
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

			// �յ�ˢ�·��������Ϣ
			HashMap data = new HashMap();
			data.put("groupname", rd.readString());// ��������
			data.put("deskno", rd.readInt());// ����
			data.put("peilv", rd.readInt());// ����
			data.put("miniGold", rd.readInt());
			data.put("g_minalonebetmoney", rd.readInt());
			data.put("g_maxalonemoney", rd.readInt());
			data.put("g_maxbetmoney", rd.readInt());
			data.put("caichiaddmoney", rd.readInt());
			this.dispatchEvent(new Event(
					ZjhEventType.ON_ZJH_RECV_ROOMBASICINFO, data));

		} else if (CMD_NOTIFY_ZJH_ROOMINFO.equals(strCmd)) {

			// �յ�ˢ�·�����Ϣ
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

			// �յ�Ͷ��
			HashMap data = new HashMap();
			data.put("desksite", rd.readByte());
			data.put("sex", rd.readByte());
			data.put("imgidx", rd.readByte());
			data.put("g_leader", rd.readByte());
			getNextStep(rd);
			data.put("timeout", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_GIVEUP, data));

		} else if (CMD_NOTIFY_ZJH_REFRESHGOLD.equals(strCmd)) {

			// ˢ�½��
			HashMap data = new HashMap();
			data.put("nScore", rd.readInt());
			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_REFRESHGOLD,
					data));

		} else if (CMD_NOTIFY_ZJH_GAMEOVER.equals(strCmd)) {

			// ��Ϸ�������ؿ�һ��
			HashMap data = new HashMap();
			data.put("deskno", rd.readInt());// ��һ��
			data.put("siteno", rd.readByte());// ��һ��λ��
			data.put("winsite", rd.readByte());// ����λ��Ӯ�ˣ�������˭�˳�
			data.put("gameoverreason", rd.readByte());// ����ԭ�� 1 ��������, 2, 3 ��������
			data.put("begintimeout", rd.readByte());// ��ʼ����ʱ��
			data.put("betcountmoney", rd.readInt());// ������ע�ܽ��
			data.put("choushui", rd.readInt());// /��ˮ
			data.put("winnick", rd.readString());// Ӯ������
			data.put("gold", rd.readInt());// Ӯ���ܽ��
			data.put("players", rd.readByte());// ���µ�����

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

			// �û���ע
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

			// �յ���������λ��״̬
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
			// �յ�����
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
				temp.put("l_site", rd.readByte());// ��λ��
				temp.put("imgidx", rd.readByte());// ͷ��ID
				data.put(i, temp);
			}

			this.dispatchEvent(new Event(ZjhEventType.ON_ZJH_RECV_FAPAI, data));

		} else if (CMD_NOTIFY_ZJH_KANPAI.equals(strCmd)) {

			// �յ�����
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
		// DTrace.traceex("���ͷ��ش���������,sendRequestBackHall()");
		m_netptr.writeString("RQBH");
		m_netptr.writeEnd();
	}

	// ���͵㿪ʼ��ť
	public void sendStart() throws Exception {

		m_netptr.writeString("GCST");
		m_netptr.writeEnd();
	}

	// ���͵�ʳذ�ť
	public void sendClickCaichi() throws Exception {
		m_netptr.writeString("CKCC");
		m_netptr.writeEnd();
	}

	// ���͵������ť
	public void sendClickGiveUp() throws Exception {

		m_netptr.writeString("YHFQ");
		m_netptr.writeEnd();
	}

	// ��ȡ������λ��
	public void getBiPaiSite() throws Exception {

		m_netptr.writeString("YHBP");
		m_netptr.writeEnd();
	}

	// ���͵㿴�ư�ť
	public void sendClickLook() throws Exception {

		m_netptr.writeString("YHKP");
		m_netptr.writeEnd();
	}

	// ������ע���
	public void sendXiaZhuData(int score, int xzType, int bpSite)
			throws Exception {
		m_netptr.writeString("YHXZ");
		m_netptr.writeByte((byte) xzType);
		m_netptr.writeInt(score);
		m_netptr.writeByte((byte) bpSite);// --g_xztype = 4��ʾ������ע������Ҫ���Ƶ���λ��
		m_netptr.writeEnd();
	}

	// ����ע��ť
	public void sendClickXiaZhu() throws Exception {

		m_netptr.writeString("YHKP");
		m_netptr.writeEnd();
	}

	// ���ͽ�����������
	public void sendAcceptGuide() throws Exception {

		m_netptr.writeString("RRAG");
		m_netptr.writeEnd();
	}

	// �����ؿ�һ������
	public void sendRestartGame() throws Exception {

		m_netptr.writeString("YHCK");
		m_netptr.writeEnd();
	}

	// ����ˢ�½�����󼰷�����Ϣ
	public void sendRefreshGold() throws Exception {

		m_netptr.writeString("RQSC");
		m_netptr.writeEnd();
	}

	// ����ˢ�·�����Ϣ
	public void sendReqRoomInfo() throws Exception {

		m_netptr.writeString("RQIF");
		m_netptr.writeEnd();
	}

}
