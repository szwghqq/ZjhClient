package com.dozengame.net;

import java.util.ArrayList;
import java.util.HashMap;
import android.util.Log;

import com.dozengame.GameApplication;
import com.dozengame.event.Event;
import com.dozengame.event.TexEventType;
import com.dozengame.net.pojo.DJieSuan;
import com.dozengame.net.pojo.DeskInfo;
import com.dozengame.net.pojo.ReadData;

/**
 * ���紦���� ��Ҫ����: �������� ��������
 * 
 * @author hewengao
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

	// �յ��û��㿪ʼ
	public void onTexReady(ReadData rd) {
		// System.out.println("DNetworkTex onTexReady �յ��û��㿪ʼ");
		byte siteNo = rd.readByte();
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_TEX_READY",
		// siteNo));
	}

	// �յ�����
	public void onTexFaPai(ReadData rd) {
		Log.i("test14",
				"1onTexFaPaionTexFaPaionTexFaPaionTexFaPai: "
						+ System.currentTimeMillis());
		// System.out.println("DNetworkTex onTexFaPai �յ�����");
		HashMap data = new HashMap();
		ArrayList siteList = new ArrayList();
		data.put("siteList", siteList);
		int len = rd.readInt();
		for (int i = 0; i < len; i++) {
			siteList.add(rd.readByte());
		}
		ArrayList pokes = new ArrayList();
		data.put("pokes", pokes);
		len = rd.readInt();
		byte pokeId;
		for (int i = 0; i < len; i++) {
			pokeId = rd.readByte();
			if (pokeId >= 1 && pokeId <= 52) { // todo::ֻ���պϷ�����id
				pokes.add(pokeId);
			}
		}
		Log.i("test14",
				"2onTexFaPaionTexFaPaionTexFaPaionTexFaPai: "
						+ System.currentTimeMillis());
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_FAPAI, data));
	}

	// �յ��Լ���������ͣ�����ʾ��ʾ
	public void onTexBestPoke(ReadData rd) {
		// System.out.println("DNetworkTex onTexBestPoke �յ��Լ���������ͣ�����ʾ��ʾ");
		HashMap data = new HashMap();
		// ���ж���Ǹ����֡���һλ�������͡�1-9���������ʼ�ͬ��˳��
		data.put("weight", rd.readString());
		ArrayList pokes = new ArrayList();
		data.put("pokes", pokes);
		int len = rd.readInt();
		for (int i = 0; i < len; i++) {
			pokes.add(rd.readByte());
		}
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_BESTPOKES, data));
	}

	// �ָ���ʾ
	public void onTexResetDisplay(ReadData rd) {
		Log.i("test5", "ResetDisplay ResetDisplay");
		// System.out.println("DNetworkTex onTexMyPoke �ָ���ʾ");
		HashMap data = new HashMap();
		data.put("zhuangsite", rd.readByte()); // ׯ��λ��
		System.out.println("zhuangsite: " + data.get("zhuangsite"));
		ArrayList deskPokes = new ArrayList();
		data.put("deskPokes", deskPokes);
		int len = rd.readInt();
		for (int i = 0; i < len; i++) {
			deskPokes.add(rd.readByte());
		}
		ArrayList pokes = new ArrayList();
		len = rd.readInt();
		for (int i = 0; i < len; i++) {
			pokes.add(rd.readByte());
		}
		data.put("gold", rd.readInt());
		data.put("betgold", rd.readInt());
		data.put("mybean", rd.readInt());

		len = rd.readInt();
		ArrayList playerInfo = new ArrayList();
		data.put("playerInfo", playerInfo);
		HashMap tempMap = null;
		for (int i = 0; i < len; i++) {
			tempMap = new HashMap();
			tempMap.put("siteno", rd.readByte());
			// �ܵ���ע���
			tempMap.put("betgold", rd.readInt());
			tempMap.put("islose", rd.readByte());
			tempMap.put("isallin", rd.readByte());
			// ������ע����ʾ��ͷ��ǰ��
			tempMap.put("currbet", rd.readInt());
			playerInfo.add(tempMap);
		}
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_RESETDISPLAY,
				data));
	}

	// �û���ע
	public void onTexXiaZhu(ReadData rd) {
		System.out.println("DNetworkTex onTexXiaZhu �û���ע");
		HashMap data = new HashMap();
		data.put("siteno", rd.readByte());// ��λ��
		data.put("betgold", rd.readInt());// �ܵ���ע��
		data.put("currbet", rd.readInt());// ������ע��
		data.put("sex", rd.readByte()); // 1=�� 0=Ů
		data.put("type", rd.readByte()); // 1=��ע 2=��ע 3=��ע 4=��� 5=��ע
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_XIAZHUSUCC, data));
	}

	// �յ�Ͷ��
	public void onTexGiveUp(ReadData rd) {
		System.out.println("DNetworkTex onTexGiveUp �յ�Ͷ��");
		Byte siteNo = rd.readByte();
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_GIVEUP, siteNo));
	}

	// �û�����ע
	public void onTexBuXiaZhu(ReadData rd) {
		System.out.println("DNetworkTex onTexBuXiaZhu �û�����ע");
		byte siteNo = rd.readByte();
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_BUXIAZHU, siteNo));
	}

	// ˢ�½��
	public void onTexRefreshGold(ReadData rd) {
		Log.i(tag, "DNetworkTex onTexRefreshGold ˢ�½��");
		HashMap data = new HashMap();
		data.put("siteno", rd.readByte());
		data.put("gold", rd.readInt());
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_REFRESHGOLD, data));
	}

	// �յ����
	public void onTexPanel(ReadData rd) {
		// follow: 1 add: 1 allPut: 0 giveUp: 1 pass: 0 chipIn:0 min: 6000 max:
		// 39800 followGold: 2000

		// follow: 0 add: 1 allPut: 0 giveUp: 1 pass: 1 chipIn:0 min: 4000 max:
		// 39800 followGold: 0

		System.out.println("DNetworkTex onTexPanel �յ����");
		HashMap data = new HashMap();
		data.put("follow", rd.readByte());// ��ע
		data.put("add", rd.readByte());// ��ע
		data.put("allPut", rd.readByte());// ȫ��
		data.put("giveUp", rd.readByte());// ����
		data.put("pass", rd.readByte());// ����
		data.put("chipIn", rd.readByte());
		data.put("min", rd.readInt());// ��С
		data.put("max", rd.readInt());// ���
		data.put("followGold", rd.readInt());// ��ע���
		dispatchEvent(new Event(TexEventType.ON_TEX_BUTTON_STATUS, data));
	}

	// �Զ����
	public void onTexAutoPanel(ReadData rd) {
		System.out.println("DNetworkTex onTexAutoPanel �Զ����");
		HashMap data = new HashMap();
		data.put("guo", rd.readByte());
		data.put("guoqi", rd.readByte());
		data.put("genrenhe", rd.readByte());// ���κ�
		data.put("gen", rd.readByte());// ��
		data.put("gengold", rd.readInt());// ���Ľ��
		dispatchEvent(new Event(TexEventType.ON_TEX_AUTO_PANEL, data));
	}

	// ��Ϸ�������ؿ�һ��
	public void onTexGameOver(ReadData rd) {
		GameApplication.jieSuanIng = true;
		Log.i("test9", "DNetworkTex onTexGameOver ��Ϸ�������ؿ�һ��");
		DJieSuan.initData();
		HashMap data = new HashMap();
		byte iscomplete = rd.readByte();
		data.put("iscomplete", iscomplete); // �Ƿ��ڽ���ʱ����(����;����)
		DJieSuan.m_iscomplete = iscomplete;
		ArrayList players = new ArrayList();
		// ӮǮ������
		int len = rd.readInt();
		int siteNo, wingold;
		String weight;
		for (int i = 0; i < len; i++) {
			HashMap d = new HashMap();
			// ��λ��
			siteNo = rd.readByte();
			d.put("siteno", siteNo);
			DJieSuan.m_winsiteList.add(siteNo);
			// ��ö���Ǯ
			wingold = rd.readInt();
			d.put("wingold", wingold);
			DJieSuan.m_windGold.put(siteNo, wingold);
			// ���ж���Ǹ����֡���һλ�������͡�1-9���������ʼ�ͬ��˳��
			weight = rd.readString();
			d.put("weight", weight);
			DJieSuan.m_winPokeWeight.put(siteNo, weight);
			ArrayList poollist = new ArrayList();
			d.put("poollist", poollist);
			int len1 = rd.readInt(); // ����һ�õĲʳ���
			for (int j = 0; j < len1; j++) {
				int index = rd.readByte();
				int gold = rd.readInt();

				HashMap pm = new HashMap();
				// 1=���� 2=�ʳ�1 3=�ʳ�2 ...
				pm.put("poolindex", index);
				// �������Ӯ����ϸ
				pm.put("poolgold", gold);
				poollist.add(pm);
				DJieSuan.m_totalCaiChi[index - 1] += gold; // ����ܵĲʳ�
			}
			DJieSuan.m_pondList.put(siteNo, poollist);
			players.add(d);// ����Ӯ��
		}
		data.put("players", players);
		// ��������������˶��ٶ�
		int chipIn = rd.readInt();
		data.put("chipIn", chipIn);
		DJieSuan.m_mychipIn = chipIn;
		// ����������ϻ�ö��ٶ�
		int gainGold = rd.readInt();
		data.put("gainGold", gainGold);
		DJieSuan.m_mygainGold = gainGold;
		ArrayList pokes5 = new ArrayList();
		data.put("pokes5", pokes5);
		int len1 = rd.readInt();
		byte temp;
		for (int j = 0; j < len1; j++) {
			// �����������ƣ�ӦΪ5��
			temp = rd.readByte();
			pokes5.add(temp);
			DJieSuan.m_finalBeskPoke.add(temp);
		}

		// ���ӵ���Ϣ
		len = rd.readByte(); // ��ֵ��׵����
		ArrayList sites = new ArrayList();
		data.put("sites", sites);
		for (int i = 0; i < len; i++) {
			HashMap pm = new HashMap();
			siteNo = rd.readByte();
			pm.put("siteno", siteNo);
			len1 = rd.readByte(); // ����ҵ���,2��
			int[] pokes = new int[len1];
			pm.put("pokes", pokes);
			for (int j = 0; j < len1; j++) {
				pokes[j] = rd.readByte();
			}
			// ����
			String pokeweight = rd.readString();
			pm.put("pokeweight", pokeweight);

			ArrayList pokes51 = new ArrayList();
			pm.put("pokes5", pokes51);
			len1 = rd.readByte(); // �������ƣ�5��
			for (int j = 0; j < len1; j++) {
				pokes51.add(rd.readByte());
			}
			sites.add(pm);
			DJieSuan.m_diPoke.put(siteNo, pokes);// ����
			DJieSuan.m_pokeWeight.put(siteNo, pokeweight); // ����Ҽ�����
			DJieSuan.m_bestPoke.put(siteNo, pokes51); // ����ҵ���������
		}
		len = rd.readByte(); // �ʳظ���
		ArrayList deskpools = new ArrayList();
		data.put("deskpools", deskpools);
		HashMap hmTemp;
		for (int i = 0; i < len; i++) {
			HashMap hm = new HashMap();
			// ���ʳ��ܽ��
			hm.put("chouma", rd.readInt());
			ArrayList winlist = new ArrayList();
			hm.put("winlist", winlist);
			// �ʳ��м����˷�
			len1 = rd.readByte();
			for (int j = 0; j < len1; j++) {
				hmTemp = new HashMap();
				// Ӧ�ֳ�����λ
				hmTemp.put("siteno", rd.readByte());
				// Ӧ������
				hmTemp.put("winchouma", rd.readInt());
				winlist.add(hmTemp);
			}
			deskpools.add(hm);
		}
		// DTrace.traceex("�յ���Ϸ����Э��:", data);
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_GAMEOVER, data));
	}

	// �յ����������Ϣ
	public void onTexPlayerInfo(ReadData rd) {
		System.out.println("\nDNetworkTex onTexPlayerInfo �յ����������Ϣ\n");
		ArrayList data = new ArrayList();
		int len = rd.readInt(); // ���µ�����
		System.out.println("���µ������� " + len);
		HashMap temp = null;
		for (int i = 0; i < len; i++) {
			temp = new HashMap();
			// ��λ��
			temp.put("site", rd.readByte());
			// ״̬��
			temp.put("state", rd.readByte());
			// ʣ��ʱ��
			temp.put("timeout", rd.readByte());
			// ȫ������ʱʱ����ʱ��
			temp.put("delay", rd.readByte());
			data.add(temp);
		}
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_PLAYERINFO, data));
	}

	// �յ�����ʳ���Ϣ
	public void onTexDeskPollInfo(ReadData rd) {
		System.out.println("\nDNetworkTex onTexDeskPollInfo �յ�����ʳ���Ϣ\n");
		ArrayList deskPondGold = new ArrayList();
		int len = rd.readInt();
		for (int i = 0; i < len; i++) {
			deskPondGold.add(rd.readInt()); // ������
		}
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_DESK_POLL_INFO,
				deskPondGold));
	}

	// �յ�������Ϣ
	public void onTexDeskInfo(ReadData rd) {
		System.out.println("\nDNetworkText onTexDeskInfo �յ�������Ϣ\n");

		DeskInfo deskInfo = new DeskInfo();
		deskInfo.setDeskno(rd.readInt());// ����
		deskInfo.setName(rd.readString()); // ����
		deskInfo.setDesktype(rd.readByte());// 1��ͨ��2������3VIPר��
		deskInfo.setFast(rd.readByte());// �Ƿ����
		deskInfo.setBetgold(rd.readInt()); // ���������
		deskInfo.setUsergold(rd.readInt()); // ������ҳ�����
		deskInfo.setNeedlevel(rd.readInt()); // ���ӽ����ȼ�
		deskInfo.setSmallbet(rd.readInt()); // Сä
		deskInfo.setLargebet(rd.readInt()); // ��ä
		deskInfo.setAt_least_gold(rd.readInt()); // �������
		deskInfo.setAt_most_gold(rd.readInt()); // �������
		deskInfo.setSpecal_choushui(rd.readInt()); // ��ˮ
		deskInfo.setMin_playercount(rd.readByte()); // ���ٿ�������
		deskInfo.setMax_playercount(rd.readByte()); // ��࿪������
		deskInfo.setPlayercount(rd.readByte()); // ��ǰ��������
		deskInfo.setShowsitbtn(rd.readByte()); // �Ƿ���ʾ���°�ť
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_DESK_INFO, deskInfo));
	}

	// BBS��֤��
	public void onTexBbsUrl(ReadData rd) {
		System.out.println("\nDNetworkTex onTexBbsUrl BBS��֤��\n");
		// String bbsUrl = rd.readString(); // BBS��֤��
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_BBS_URL", bbsUrl));
	}

	// �յ��������ڽ���
	public void onTexJsWaitIng(ReadData rd) {
		Log.i("test9", "�յ��������ڽ���");
		System.out.println("\nDNetworkTex onTexJsWaitIng �յ��������ڽ���\n");
		int leftTime = rd.readInt(); // ����ʣ�µ�ʱ��
		GameApplication.jieSuanIng = true;// ������
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_JS_WAITING",
		// leftTime));
	}

	public void onTexSingleDetail(ReadData rd) {
		System.out.println("\nDNetworkTex onTexSingleDetail \n");
		HashMap data = new HashMap();
		data.put("sys_time", rd.readString()); // ʱ��
		data.put("smallbet", rd.readInt()); // Сä
		data.put("largebet", rd.readInt()); // ��ä
		data.put("betgold", rd.readInt()); // ��ע��
		data.put("wingold", rd.readInt()); // Ӯ�ó���
		data.put("betflag", rd.readInt()); // ��ע:-1���ơ�0������1ȫ��
		data.put("pokeweight", rd.readString()); // ���ͣ�����Ϊ0
		data.put("pokes5", rd.readString()); // ��������:|12|15|16|17 ,���ܿ�,
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_SINGLE_DETAIL",
		// data));
	}

	// �յ��Լ����޶�
	public void onTexShowMyGold(ReadData rd) {

		int gold = rd.readInt(); // �յ��Լ����޶�
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_SHOW_MY_TOTAL_BEAN,
				gold));
	}

	// �յ������˿�
	public void onTexDeskPoke(ReadData rd) {
		System.out.println("\nDNetworkTex onTexDeskPoke �յ������˿� \n");
		ArrayList pokes = new ArrayList();
		int len = rd.readInt();
		for (int i = 0; i < len; i++) {
			pokes.add(rd.readByte());
		}
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_DESKPOKE, pokes));
	}

	// �յ���Ϸ��ʼ(��������)
	public void onTexGameStart(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGameStart �յ���Ϸ��ʼ(��������) \n");
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_GAMESTART, null));
	}

	// �յ�������봰��
	public void onTexBuyChouMa(ReadData m_netptr) {
		System.out.println("\nDNetworkTex onTexBuyChouMa �յ�������봰�� \n");
		HashMap data = new HashMap();
		data.put("deskno", m_netptr.readInt()); // ���Ҫ������λ�ţ�����ǹ���ר���Ļ���
		data.put("min", m_netptr.readInt()); // ����*100
		data.put("max", m_netptr.readInt()); // ����*1000
		data.put("gold", m_netptr.readInt()); // ���ʵ�ʽ��
		data.put("defaultgold", m_netptr.readInt()); // Ĭ�Ϲ���Ľ��
		data.put("timeout", m_netptr.readInt()); // �Ƿ���ʾ��ʱ������Ϸ���Ʋ�����ʾ30�볬ʱ
		data.put("halfhour", m_netptr.readByte()); // �Ƿ���ʾ��Сʱǰ������0����ʾ��1��ʾ
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_BUYCHOUMA, data));
	}

	// �յ��̵���۵�������Ʒ�б�
	public void onTexGiftShop(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiftShop �յ��̵���۵�������Ʒ�б� \n");
		HashMap data = new HashMap();
		int itemcount = rd.readInt();
		data.put("itemcount", itemcount);
		ArrayList gifts = new ArrayList();
		data.put("gifts", gifts);
		for (int i = 0; i < itemcount; i++) {
			HashMap temp = new HashMap();
			temp.put("giftid", rd.readInt());
			temp.put("price", rd.readInt());// ���ۼ۸�
			gifts.add(temp);
		}
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_SHOP", data));
	}

	// �յ������ʶ
	public void onTexGiftIcon(ReadData rd) {

		HashMap data = new HashMap();
		data.put("siteno", rd.readByte());
		data.put("giftid", rd.readInt());
		Log.i("test4", "onTexGiftIcon giftid:" + data.get("giftid"));
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_GIFT_ICON, data));
	}

	// ���������ﶯ��
	public void onTexPlayGift(ReadData rd) {

		HashMap data = new HashMap();
		data.put("fromsiteno", rd.readByte());
		data.put("tositeno", rd.readByte());
		data.put("giftid", rd.readInt());
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_PLAY_GIFT, data));
	}

	// ��������򷢱���ʧ��
	public void onTexGiftOrEmotFaild(ReadData rd) {

		byte retcode = rd.readByte(); // 1=�ɹ���Ǯ 2=Ǯ���� 0=�����쳣 3=�Է���������
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_FAILD",
		// data));
	}

	// ������������ʧ��
	public void onTexGiftFaildList(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiftFaildList ������������ʧ�� \n");
		int len = rd.readInt(); // ʧ�ܵĸ���
		ArrayList data = new ArrayList();
		HashMap temp = null;
		for (int i = 0; i < len; i++) {
			temp = new HashMap();
			temp.put("siteno", rd.readByte());
			// 1=�ɹ���Ǯ 2=Ǯ���� 3=���쳬���޶��� 4=�������� 0=�����쳣
			temp.put("retcode", rd.readInt());
			data.add(temp);
		}
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_FAILDLIST",
		// data));
	}

	// ������Ʒ���
	public void onTexGiftSale(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiftSale ������Ʒ��� \n");
		HashMap data = new HashMap();
		data.put("retcode", rd.readInt()); // 1�ɹ���-1�����ڵ���Ʒ��2����Ʒ���ܻ��գ�3ϵͳæ�����Ժ�����
		data.put("getgold", rd.readInt()); // �ɹ�������õ�����Ǯ��ʧ��ʱ������
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_SALE", data));
	}

	// ���ŷ����鶯��
	public void onTexPlayEmot(ReadData rd) {
		System.out.println("\nDNetworkTex onTexPlayEmot ���ŷ����鶯�� \n");
		HashMap data = new HashMap();
		data.put("siteno", rd.readByte());
		data.put("emotid", rd.readInt());
		Log.i("test18",
				"siteno: " + data.get("siteno") + " emotid: "
						+ data.get("emotid"));
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_PLAY_EMOT, data));
	}

	// ������Ӧ
	public void onTexGiftResponse(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiftResponse ������Ӧ \n");
		HashMap data = new HashMap();
		data.put("fromsite", rd.readInt());
		data.put("tosite", rd.readInt());
		data.put("response_id", rd.readInt());
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_RESPONSE",
		// data));
	}

	// �յ������б�����
	public void onTexGiftList(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiftList  �յ������б����� \n");
		ArrayList giftlist = new ArrayList();
		int len = rd.readInt();
		HashMap temp = null;
		for (int i = 0; i < len; i++) {
			temp = new HashMap();
			temp.put("index", rd.readInt());
			temp.put("id", rd.readInt());
			temp.put("isusing", rd.readByte());
			temp.put("cansale", rd.readByte());// �Ƿ���Գ���
			temp.put("salegold", rd.readInt());// ���������Ǯ
			temp.put("fromuser", rd.readString());
			giftlist.add(temp);
		}
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_LIST", data));
	}

	// �յ���ʱ
	public void onTexTimeOut(ReadData rd) {
		System.out.println("\nDNetworkTex onTexTimeOut  \n");
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_TIMEOUT, null));
	}

	// �û�����
	public void onTexSitDown(ReadData rd) {
		System.out.println("\nDNetworkTex onTexSitDown  �û����� \n");
		HashMap data = new HashMap();
		data.put("recode", rd.readByte());
		data.put("bReloginUser", rd.readByte());
		data.put("userkey", rd.readString());
		data.put("nick", rd.readString());
		data.put("deskno", rd.readInt());
		data.put("siteno", rd.readInt());
		data.put("olddeskno", rd.readInt());
		data.put("oldsiteno", rd.readInt());
		data.put("total_gold", rd.readInt()); // ���н��
		data.put("cityName", rd.readString());
		data.put("beginTimeOut", rd.readInt());
		data.put("face", rd.readString());
		data.put("sex", rd.readByte());
		data.put("startState", rd.readByte()); // ��Ϸ״̬
		data.put("userid", rd.readInt());
		data.put("channel", rd.readString());
		data.put("gameexp", rd.readInt());
		data.put("channelid", rd.readInt());
		data.put("peilv", rd.readInt());
		data.put("tour_point", rd.readInt()); // ����������
		data.put("gamelevel", rd.readInt()); // �ȼ�
		data.put("handgold", rd.readInt()); // ���еĳ���
		data.put("viplevel", rd.readInt());// vip�Ǽ�
		data.put("mobilemode", rd.readByte());// �Ƿ��ֻ���¼ 0:pc ��2 ���ֻ�
		// rd.readInt();//short_channel_id
		// rd.readInt();//home_status
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_SITDOWN, data));
	}

	// �յ��û������ս
	public void onTexWatchIng(ReadData rd) {

		HashMap data = new HashMap();
		data.put("nick", "");
		data.put("siteno", 0);
		data.put("startState", 0);
		data.put("deskno", rd.readShort());
		int userid = rd.readInt();
		data.put("userid", userid);
		data.put("gold", rd.readInt());
		data.put("sex", rd.readByte());
		data.put("face", rd.readString());
		data.put("channelid", rd.readInt());
		data.put("channel", rd.readString());
		data.put("ddzexp", rd.readInt());
		data.put("tour_point", rd.readInt());
		data.put("isme_watching", 1);
		data.put("retcode", rd.readInt()); // ������
		Integer default_chouma = rd.readInt();
		data.put("default_chouma", default_chouma); // Ĭ������ĳ�����
		Integer obj = (Integer) GameApplication.userInfo.get("user_real_id");

		if (userid == obj) {

			GameApplication.userInfo.put("default_chouma", default_chouma);
			// ���ÿ��ٿ�ʼ��ť�ɼ�
			// GameApplication.getDzpkGame().gameBottomView.addQuickStartButton();
		}
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_WATCHING", data));
	}

	// �û�վ��
	public void onTexStandUp(ReadData rd) {
		System.out.println("\nDNetworkTex onTexStandUp  �û�վ�� \n");
		HashMap data = new HashMap();
		data.put("recode", rd.readInt());
		data.put("currentuser", rd.readString());
		data.put("nick", rd.readString());
		data.put("deskno", rd.readInt());
		data.put("siteno", rd.readInt());
		// Log.i("test10", "recode: "+data.get("recode")+
		// "   currentuser: "+data.get("currentuser")+"  nick: "+data.get("nick"));
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_STANDUP, data));
	}

	// �յ����������ش�����ʾ:0xx�����ģ�1xx��Ϸ���
	public void onTexServerError(ReadData rd) {
		// HashMap data = new HashMap();
		int msgType = rd.readByte();

		// data.put("msgtype", msgType);
		String msg = rd.readString();

		// data.put("msg", msg);
		if (msgType == 1) {
			// ��Ϸ��
			dispatchEvent(new Event(TexEventType.ON_TEX_RECV_SERVER_ERROR, msg));
		} else if (msgType == 0) {
			// ��Ϸ����
			dispatchEvent(new Event(TexEventType.ON_TEX_RECV_SERVER_ERROR, msg));
		}
	}

	// �յ�ͷ����
	public void onTexChangFace(ReadData rd) {

		System.out.println("\nDNetworkTex onTexChangFace �յ�ͷ����\n");
		HashMap data = new HashMap();
		data.put("userid", rd.readInt());
		data.put("siteno", rd.readInt());
		data.put("face", rd.readString());
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_CHANGEFACE",
		// data));
	}

	// �յ�����
	public void onTexDeskChat(ReadData rd) {
		System.out.println("\nDNetworkTex onTexDeskChat �յ�����\n");
		HashMap data = new HashMap();
		data.put("type", rd.readByte());
		data.put("msg", rd.readString());
		data.put("id_from", rd.readInt());
		data.put("name_from", rd.readString());
		data.put("site_from", rd.readByte());
		Log.i("test18",
				"type: " + data.get("type") + " msg: " + data.get("msg")
						+ " idform: " + data.get("id_from") + " name_from: "
						+ data.get("name_from") + " site_from: "
						+ data.get("site_from"));
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_CHAT, data));
	}

	// �˳���ս
	public void onTexExitWatch(ReadData rd) {
		System.out.println("\nDNetworkTex onTexExitWatch �˳���ս\n");
		HashMap data = new HashMap();
		data.put("deskno", rd.readShort());
		data.put("userid", rd.readInt());
		this.dispatchEvent(new Event(Event.ON_RECV_EXIT_WATCH, data));
	}

	// �յ��û����Ӿ���
	public void onTexAddExp(ReadData rd) {
		System.out.println("\nDNetworkTex onTexAddExp �յ��û����Ӿ���\n");
		HashMap data = new HashMap();
		data.put("userid", rd.readInt());
		data.put("siteno", rd.readByte());
		data.put("addexp", rd.readInt());
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_ADD_EXPERIENCE",
		// data));
	}

	// �յ��û��ȼ�����
	public void onTexLevelUpGrade(ReadData rd) {
		Log.i("test5", "onRecvLevelUpgrade onTexLevelUpGrade");
		// System.out.println("\nDNetworkTex onTexLevelUpGrade �յ��û��ȼ�����\n");
		HashMap data = new HashMap();
		data.put("userid", rd.readInt());
		int siteNo = rd.readByte();
		data.put("siteno", siteNo);
		data.put("level", rd.readInt());
		data.put("givegold", rd.readInt());
		this.dispatchEvent(new Event(TexEventType.ON_RECV_LEVEL_UPGRADE, data));
	}

	// �յ��û���������;������
	public void onTexDayAddExp(ReadData rd) {
		// System.out.println("\nDNetworkTex onTexDayAddExp �յ��û���������;������\n");
		HashMap data = new HashMap();
		data.put("userid", rd.readInt());
		data.put("siteno", rd.readByte());
		data.put("level", rd.readInt());
		data.put("addexp", rd.readInt());
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_DAY_ADDEXP", data));
	}

	// �յ���̭���ߵý�
	public void onTexPrizeOrLost(ReadData rd) {
		// System.out.println("\nDNetworkTex onTexPrizeOrLost �յ���̭���ߵý�\n");
		HashMap data = new HashMap();
		data.put("userid", rd.readInt()); // userid
		data.put("siteno", rd.readByte()); // siteno
		data.put("mingci", rd.readByte()); // ����
		data.put("givegold", rd.readInt()); // ��ý��
		data.put("addexp", rd.readInt()); // ��þ���
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_LEVEL_PRIZE_OR_LOST",
		// data));
	}

	// �յ�ϵͳ�ɷ���Ϣ
	public void onTexServerMsg(ReadData rd) {
		System.out.println("\nDNetworkTex onTexServerMsg  �յ�ϵͳ�ɷ���Ϣ\n");
		HashMap data = new HashMap();
		data.put("msgtype", rd.readInt());
		data.put("userid", rd.readInt());
		data.put("msg", rd.readString());
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_LEVEL_SERVER_MSG",
		// data));
	}

	// �յ����ش������
	public void onTexBackHallResult(ReadData rd) {
		System.out.println("\nDNetworkTex onTexBackHallResult  �յ����ش������\n");
		short success = rd.readShort(); // ���
		// this.dispatchEvent(new DNetWorkEvent("ON_BACK_HALL_RESULT", data));
	}

	public void onTexLoginShowBetaGife(ReadData rd) {
		System.out.println("\nDNetworkTex onTexLoginShowBetaGife \n");
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_LOGIN_SHOW_BETAGIFE",
		// data));
	}

	public void onTexLoginGetBetaGife(ReadData m_netptr) {
		System.out.println("\nDNetworkTex onTexLoginGetBetaGife \n");
		HashMap data = new HashMap();
		int len = m_netptr.readByte();
		data.put("len", len);
		ArrayList gifelist = new ArrayList();
		data.put("gifelist", gifelist);
		for (int i = 0; i < len; i++) {
			gifelist.add(m_netptr.readInt());
		}
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_LOGIN_GET_BETAGIFE",
		// data));
	}

	public void onTexKickEnable(ReadData rd) {
		System.out.println("\nDNetworkTex onTexKickEnable \n");
		HashMap data = new HashMap();
		data.put("kickType", rd.readByte()); // �Ƿ�������ˣ�byte��0�������ԣ�1������
		data.put("cishu", rd.readInt());
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_KICK_ENABLE", data));
	}

	public void onTexKick(ReadData rd) {
		System.out.println("\nDNetworkTex onTexKick \n");
		HashMap data = new HashMap();
		data.put("sum", rd.readByte());
		data.put("userName", rd.readString());
		data.put("userFace", rd.readString());
		data.put("faqirenName", rd.readString());
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_KICK", data));
	}

	public void onTexKickResult(ReadData rd) {
		System.out.println("\nDNetworkTex onTexKickResult \n");
		HashMap data = new HashMap();
		data.put("waiverSum", rd.readByte());
		data.put("naySum", rd.readByte());
		data.put("agreeSum", rd.readByte());
		data.put("totalSum", rd.readByte());
		data.put("resultType", rd.readByte());
		data.put("userName", rd.readString());
		data.put("userFace", rd.readString());
		data.put("faqirenName", rd.readString());
		data.put("isKicker", rd.readByte());
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_KICK_RESULT", data));
	}

	public void onTexKickCiShu(ReadData rd) {
		System.out.println("\nDNetworkTex onTexKickCiShu \n");
		HashMap data = new HashMap();
		data.put("isvip", rd.readByte());
		data.put("cishu", rd.readInt());
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_KICK_CISHU", data));
	}

	public void onTexKickOver(ReadData rd) {
		System.out.println("\nDNetworkTex onTexKickOver \n");
		HashMap data = new HashMap();
		data.put("username", rd.readString());
		data.put("userface", rd.readString());
		data.put("waiverSum", rd.readByte());
		data.put("naySum", rd.readByte());
		data.put("agreeSum", rd.readByte());
		data.put("totalSum", rd.readByte());
		data.put("faqirenName", rd.readString());
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_KICK_OVER", data));
	}

	public void onTexKickOverBeiTi(ReadData rd) {
		System.out.println("\nDNetworkTex onTexKickOverBeiTi \n");
		this.dispatchEvent(new Event(TexEventType.ON_RECV_KICK_OVER_BEITI, null));
	}

	public void onTexKickShow(ReadData rd) {
		System.out.println("\nDNetworkTex onTexKickShow \n");
		int cs = rd.readInt();
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_KICK_SHOW", cs));
	}

	public void onTexKickIsShow(ReadData rd) {
		System.out.println("\nDNetworkTex onTexKickIsShow \n");
		int cs = rd.readInt();
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_KICK_SHOW", cs));
	}

	// ���ƶ���
	public void onTexCalcEnable(ReadData rd) {
		System.out.println("\nDNetworkTex onTexCalcEnable ���ƶ���\n");
		byte isType = rd.readByte(); // �Ƿ�������ƣ�byte��0�������ԣ�1������
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_CALC_ENABLE", isType));
	}

	public void onTexGiveJiuJi(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiveJiuJi \n");
		HashMap data = new HashMap();
		data.put("cangive", rd.readByte());
		data.put("lqcishu", rd.readByte());
		this.dispatchEvent(new Event(TexEventType.ON_RECV_GIVE_JIUJI, data));
	}

	public void onTexGiftXinShou(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiftXinShou \n");
		byte isWin = rd.readByte();// 0�䣬1Ӯ
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_GIFT_XINSHOU_ONE",
		// data));
	}

	protected void onProcessCommand(ReadData rd) {

		String strCmd = rd.getStrCmd();
		Log.i(tag, " DNetworkTjh receive command: " + strCmd);
		if (CMD_NOTIRY_ZJH_FOLLOWDEAD.equals(strCmd)) {
			// ����
			onTexReady(rd);
		} else if (CMD_NOTIRY_ZJH_CLICKCAICHI.equals(strCmd)) {
			// �յ�����ʳ�
			onTexFaPai(rd);
		} else if (CMD_NOTIRY_ZJH_GETCAICHIINFO.equals(strCmd)) {
			// �յ��ʳ���Ϣ
			onTexBestPoke(rd);
		} else if (CMD_NOTIRY_ZJH_RELOGIN_ACTION.equals(strCmd)) {

			// �յ��ص�¼������ť
			onTexResetDisplay(rd);
		} else if (CMD_NOTIFY_ZJH_RELOGIN.equals(strCmd)) {
			// �յ������û��ص�¼��Ϣ
			onTexXiaZhu(rd);
		} else if (CMD_NOTIFY_ZJH_RECVOFFLINESITE.equals(strCmd)) {
			// �յ�������Ϣ
			onTexGiveUp(rd);
		} else if (CMD_RESPONSE_ZJH_GETCAICHI.equals(strCmd)) {
			// �յ��Լ����˲ʳ�
			onTexBuXiaZhu(rd);
		} else if (CMD_RESPONSE_ZJH_CHOUSHUINUM.equals(strCmd)) {
			// �յ���ˮ����
			onTexRefreshGold(rd);
		} else if (CMD_RESPONSE_ZJH_TIMEOUT.equals(strCmd)) {
			// �յ��Լ���ʱ
			onTexPanel(rd);
		} else if (CMD_NOTIFY_ZJH_RECVBIPAISITE.equals(strCmd)) {
			// �յ����Ա��Ƶ���λ��
			onTexAutoPanel(rd);
		} else if (CMD_NOTIFY_ZJH_ROOMBASICINFO.equals(strCmd)) {
			// �յ�ˢ�·��������Ϣ
			onTexGameOver(rd);
		} else if (CMD_NOTIFY_ZJH_ROOMINFO.equals(strCmd)) {
			// �յ�ˢ�·�����Ϣ
			onTexPlayerInfo(rd);
		} else if (CMD_NOTIFY_ZJH_GIVEUP.equals(strCmd)) {
			// �յ�Ͷ��
			onTexDeskPollInfo(rd);
		} else if (CMD_NOTIFY_ZJH_REFRESHGOLD.equals(strCmd)) {
			// ˢ�½��
			onTexDeskInfo(rd);
		} else if (CMD_NOTIFY_ZJH_GAMEOVER.equals(strCmd)) {
			// ��Ϸ�������ؿ�һ��
			onTexBbsUrl(rd);
		} else if (CMD_NOTIFY_ZJH_XIAZHU.equals(strCmd)) {
			// �û���ע
//			onTexTodayDetail(rd);
		} else if (CMD_NOTIFY_ZJH_SITDOWN.equals(strCmd)) {
			// �û�����
//			onTexTodayDetailEnd(rd);
		} else if (CMD_NOTIFY_ZJH_STANDUP.equals(strCmd)) {
			// �û�վ��
			onTexJsWaitIng(rd);
		} else if (CMD_NOTIFY_ZJH_START.equals(strCmd)) {
			// �յ��û��㿪ʼ
			onTexSingleDetail(rd);
		} else if (CMD_NOTIFY_ZJH_DESK_INFO.equals(strCmd)) {
			// �յ���������λ��״̬
			onTexShowMyGold(rd);
		} else if (CMD_NOTIFY_ZJH_FAPAI.equals(strCmd)) {
			// �յ�����
			onTexDeskPoke(rd);
		} else if (CMD_NOTIFY_ZJH_KANPAI.equals(strCmd)) {
			// �յ�����
			onTexGameStart(rd);
		} else if ("NTTT".equals(strCmd)) {
			// �յ�������봰��
			onTexBuyChouMa(rd);
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

		m_netptr.writeString("TXRQST");
		m_netptr.writeEnd();
	}

	// ���͵������ť
	public void sendClickGiveUp() throws Exception {
		m_netptr.writeString("TXRQFQ");
		m_netptr.writeEnd();
	}

	// ���͵�ȫ�°�ť
	public void sendClickTex() throws Exception {

		m_netptr.writeString("TXRQAI");
		m_netptr.writeEnd();
	}

	// ���͵����ť
	public void sendClickFollow() throws Exception {

		m_netptr.writeString("TXRQGZ");
		m_netptr.writeEnd();
	}

	// ���͵㲻�°�ť
	public void sendClickBuxia() throws Exception {

		m_netptr.writeString("TXRQBX");
		m_netptr.writeEnd();
	}

	// ���͵㹺�����
	public void sendBuyChouma(int gold, int deskno, int siteno)
			throws Exception {
		// DTrace.traceex("-----------siteno:" + siteno);
		m_netptr.writeString("TXRQBC");
		m_netptr.writeInt(gold);
		m_netptr.writeInt(deskno);
		m_netptr.writeByte((byte) siteno);
		m_netptr.writeByte((byte) 0);// �Զ���ע
		m_netptr.writeByte((byte) 0);// �Զ�����
		m_netptr.writeEnd();
	}

	// ����ע/��ע��ť
	public void sendClickXiaZhu(int gold, int type_1xia_2jia) throws Exception {

		m_netptr.writeString("TXRQXZ");
		m_netptr.writeInt(gold);
		m_netptr.writeByte((byte) type_1xia_2jia);
		m_netptr.writeEnd();
	}

	// ������������룬��Ҫ����
	public void sendRequestTXNTBC() throws Exception {

		m_netptr.writeString("TXNTBC");
		m_netptr.writeEnd();
	}

	// ���������Ŷ�(��������)
	public void sendRequestPaiDui() throws Exception {

		m_netptr.writeString("RQDQ");
		m_netptr.writeEnd();
	}

	// ����������ٿ�ʼ�������������Ŀ��ٿ�ʼ��ť��
	public void sendRequestQuickStart(int chouma) throws Exception {

		m_netptr.writeString("TXAUSI");
		m_netptr.writeInt(chouma);
		m_netptr.writeEnd();
	}

	// ��������������Ϣ
	public void sendRequestDeskInfo(int deskno) throws Exception {

		m_netptr.writeString("TXNINF");
		m_netptr.writeInt(deskno);
		m_netptr.writeEnd();
	}

	// ����������̳�ִ�
	public void sendRequestURL() throws Exception {

		// m_netptr.writeString(CMD_NOTIFY_TEX_BBS_URL);
		// m_netptr.writeEnd();
	}

	// ////////////////////////��������� ��ʼ/////////////////////////////////////
	// ������
	public void sendPlayEmot(int emotid) throws Exception {

		m_netptr.writeString("TXEMOT");
		m_netptr.writeInt(emotid);
		m_netptr.writeEnd();
	}

	// ������Ʒ�б�
	public void sendOpenShop() throws Exception {
		m_netptr.writeString("TXGFSP");
		m_netptr.writeEnd();
	}

	// ��xx,xx,xx������
	public void sendBuyGift(int giftid, ArrayList tosites) throws Exception {

		m_netptr.writeString("TXGIFT");
		m_netptr.writeInt(giftid);
		int len = tosites.size();
		m_netptr.writeInt(len);
		for (int i = 0; i < len; i++) {
			m_netptr.writeByte((Byte) tosites.get(i));
		}
		m_netptr.writeEnd();
	}

	// ����鿴���������б�
	public void sendRequestGiftList(int user_id) throws Exception {
		m_netptr.writeString("TXGFLT");
		m_netptr.writeInt(user_id);
		m_netptr.writeEnd();
	}

	// ����ĳ������
	public void sendRequestUseGift(int gift_index) throws Exception {

		m_netptr.writeString("TXGFUS");
		m_netptr.writeInt(gift_index);
		m_netptr.writeEnd();
	}

	// ����������Ӧ
	public void sendGiftResponse(int user_id, int response_id) throws Exception {

		m_netptr.writeString("TXGFRS");
		m_netptr.writeInt(user_id);
		m_netptr.writeInt(response_id);
		m_netptr.writeEnd();
	}

	// ������ĳ������
	public void sendRequestDropGift(int gift_index) throws Exception {

		m_netptr.writeString("TXGFDP");
		m_netptr.writeInt(gift_index);
		m_netptr.writeEnd();
	}

	// ��������ĳ������
	public void sendRequestSaleGift(int gift_index) throws Exception {
		m_netptr.writeString("TXGFSL");
		m_netptr.writeInt(gift_index);
		m_netptr.writeEnd();
	}

	// ////////////////////////��������� ����/////////////////////////////////////
	// ������������
	public void sendDeskChat(String chatText) throws Exception {

		m_netptr.writeString("RQDC");
		m_netptr.writeByte((byte) 2);
		m_netptr.writeString(chatText);
		m_netptr.writeInt(0);
		m_netptr.writeEnd();
	}

	// ���󹫲�����
	public void sendGetBetaGife() throws Exception {

		m_netptr.writeString("RQGIFT");
		m_netptr.writeEnd();
	}

	private final String CMD_REQUEST_UPDATE_VIPINFO = "RQVIPIF"; // �ͻ�������ˢ��VIP��Ϣ

	public void sendUpdateVipInfo() throws Exception {
		m_netptr.writeString(CMD_REQUEST_UPDATE_VIPINFO);
		m_netptr.writeEnd();
	}

}
