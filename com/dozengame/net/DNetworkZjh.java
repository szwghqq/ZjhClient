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
 * 网络处理超类 主要功能: 发送命令 接收命令
 * 
 * @author hewengao
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

	// 收到用户点开始
	public void onTexReady(ReadData rd) {
		// System.out.println("DNetworkTex onTexReady 收到用户点开始");
		byte siteNo = rd.readByte();
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_TEX_READY",
		// siteNo));
	}

	// 收到发牌
	public void onTexFaPai(ReadData rd) {
		Log.i("test14",
				"1onTexFaPaionTexFaPaionTexFaPaionTexFaPai: "
						+ System.currentTimeMillis());
		// System.out.println("DNetworkTex onTexFaPai 收到发牌");
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
			if (pokeId >= 1 && pokeId <= 52) { // todo::只接收合法的牌id
				pokes.add(pokeId);
			}
		}
		Log.i("test14",
				"2onTexFaPaionTexFaPaionTexFaPaionTexFaPai: "
						+ System.currentTimeMillis());
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_FAPAI, data));
	}

	// 收到自己的最佳牌型，逐步提示提示
	public void onTexBestPoke(ReadData rd) {
		// System.out.println("DNetworkTex onTexBestPoke 收到自己的最佳牌型，逐步提示提示");
		HashMap data = new HashMap();
		// 牌有多大，是个数字。第一位代表牌型。1-9，但不含皇家同花顺。
		data.put("weight", rd.readString());
		ArrayList pokes = new ArrayList();
		data.put("pokes", pokes);
		int len = rd.readInt();
		for (int i = 0; i < len; i++) {
			pokes.add(rd.readByte());
		}
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_BESTPOKES, data));
	}

	// 恢复显示
	public void onTexResetDisplay(ReadData rd) {
		Log.i("test5", "ResetDisplay ResetDisplay");
		// System.out.println("DNetworkTex onTexMyPoke 恢复显示");
		HashMap data = new HashMap();
		data.put("zhuangsite", rd.readByte()); // 庄家位置
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
			// 总的下注金额
			tempMap.put("betgold", rd.readInt());
			tempMap.put("islose", rd.readByte());
			tempMap.put("isallin", rd.readByte());
			// 本轮下注金额，显示到头像前面
			tempMap.put("currbet", rd.readInt());
			playerInfo.add(tempMap);
		}
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_RESETDISPLAY,
				data));
	}

	// 用户下注
	public void onTexXiaZhu(ReadData rd) {
		System.out.println("DNetworkTex onTexXiaZhu 用户下注");
		HashMap data = new HashMap();
		data.put("siteno", rd.readByte());// 座位号
		data.put("betgold", rd.readInt());// 总的下注额
		data.put("currbet", rd.readInt());// 本轮下注额
		data.put("sex", rd.readByte()); // 1=男 0=女
		data.put("type", rd.readByte()); // 1=下注 2=加注 3=底注 4=梭哈 5=跟注
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_XIAZHUSUCC, data));
	}

	// 收到投降
	public void onTexGiveUp(ReadData rd) {
		System.out.println("DNetworkTex onTexGiveUp 收到投降");
		Byte siteNo = rd.readByte();
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_GIVEUP, siteNo));
	}

	// 用户不下注
	public void onTexBuXiaZhu(ReadData rd) {
		System.out.println("DNetworkTex onTexBuXiaZhu 用户不下注");
		byte siteNo = rd.readByte();
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_BUXIAZHU, siteNo));
	}

	// 刷新金币
	public void onTexRefreshGold(ReadData rd) {
		Log.i(tag, "DNetworkTex onTexRefreshGold 刷新金币");
		HashMap data = new HashMap();
		data.put("siteno", rd.readByte());
		data.put("gold", rd.readInt());
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_REFRESHGOLD, data));
	}

	// 收到面板
	public void onTexPanel(ReadData rd) {
		// follow: 1 add: 1 allPut: 0 giveUp: 1 pass: 0 chipIn:0 min: 6000 max:
		// 39800 followGold: 2000

		// follow: 0 add: 1 allPut: 0 giveUp: 1 pass: 1 chipIn:0 min: 4000 max:
		// 39800 followGold: 0

		System.out.println("DNetworkTex onTexPanel 收到面板");
		HashMap data = new HashMap();
		data.put("follow", rd.readByte());// 跟注
		data.put("add", rd.readByte());// 加注
		data.put("allPut", rd.readByte());// 全下
		data.put("giveUp", rd.readByte());// 放弃
		data.put("pass", rd.readByte());// 看牌
		data.put("chipIn", rd.readByte());
		data.put("min", rd.readInt());// 最小
		data.put("max", rd.readInt());// 最大
		data.put("followGold", rd.readInt());// 跟注金额
		dispatchEvent(new Event(TexEventType.ON_TEX_BUTTON_STATUS, data));
	}

	// 自动面板
	public void onTexAutoPanel(ReadData rd) {
		System.out.println("DNetworkTex onTexAutoPanel 自动面板");
		HashMap data = new HashMap();
		data.put("guo", rd.readByte());
		data.put("guoqi", rd.readByte());
		data.put("genrenhe", rd.readByte());// 跟任何
		data.put("gen", rd.readByte());// 跟
		data.put("gengold", rd.readInt());// 跟的金额
		dispatchEvent(new Event(TexEventType.ON_TEX_AUTO_PANEL, data));
	}

	// 游戏结束，重开一局
	public void onTexGameOver(ReadData rd) {
		GameApplication.jieSuanIng = true;
		Log.i("test9", "DNetworkTex onTexGameOver 游戏结束，重开一局");
		DJieSuan.initData();
		HashMap data = new HashMap();
		byte iscomplete = rd.readByte();
		data.put("iscomplete", iscomplete); // 是否在结束时结算(非中途放弃)
		DJieSuan.m_iscomplete = iscomplete;
		ArrayList players = new ArrayList();
		// 赢钱的人数
		int len = rd.readInt();
		int siteNo, wingold;
		String weight;
		for (int i = 0; i < len; i++) {
			HashMap d = new HashMap();
			// 座位号
			siteNo = rd.readByte();
			d.put("siteno", siteNo);
			DJieSuan.m_winsiteList.add(siteNo);
			// 获得多少钱
			wingold = rd.readInt();
			d.put("wingold", wingold);
			DJieSuan.m_windGold.put(siteNo, wingold);
			// 牌有多大，是个数字。第一位代表牌型。1-9，但不含皇家同花顺。
			weight = rd.readString();
			d.put("weight", weight);
			DJieSuan.m_winPokeWeight.put(siteNo, weight);
			ArrayList poollist = new ArrayList();
			d.put("poollist", poollist);
			int len1 = rd.readInt(); // 该玩家获得的彩池数
			for (int j = 0; j < len1; j++) {
				int index = rd.readByte();
				int gold = rd.readInt();

				HashMap pm = new HashMap();
				// 1=主池 2=彩池1 3=彩池2 ...
				pm.put("poolindex", index);
				// 在这个池赢到明细
				pm.put("poolgold", gold);
				poollist.add(pm);
				DJieSuan.m_totalCaiChi[index - 1] += gold; // 算出总的彩池
			}
			DJieSuan.m_pondList.put(siteNo, poollist);
			players.add(d);// 所有赢家
		}
		data.put("players", players);
		// 结算面板桌上下了多少豆
		int chipIn = rd.readInt();
		data.put("chipIn", chipIn);
		DJieSuan.m_mychipIn = chipIn;
		// 结算面板桌上获得多少豆
		int gainGold = rd.readInt();
		data.put("gainGold", gainGold);
		DJieSuan.m_mygainGold = gainGold;
		ArrayList pokes5 = new ArrayList();
		data.put("pokes5", pokes5);
		int len1 = rd.readInt();
		byte temp;
		for (int j = 0; j < len1; j++) {
			// 本局最佳组合牌，应为5张
			temp = rd.readByte();
			pokes5.add(temp);
			DJieSuan.m_finalBeskPoke.add(temp);
		}

		// 附加的信息
		len = rd.readByte(); // 坚持到底的玩家
		ArrayList sites = new ArrayList();
		data.put("sites", sites);
		for (int i = 0; i < len; i++) {
			HashMap pm = new HashMap();
			siteNo = rd.readByte();
			pm.put("siteno", siteNo);
			len1 = rd.readByte(); // 该玩家底牌,2张
			int[] pokes = new int[len1];
			pm.put("pokes", pokes);
			for (int j = 0; j < len1; j++) {
				pokes[j] = rd.readByte();
			}
			// 牌型
			String pokeweight = rd.readString();
			pm.put("pokeweight", pokeweight);

			ArrayList pokes51 = new ArrayList();
			pm.put("pokes5", pokes51);
			len1 = rd.readByte(); // 最佳组合牌，5张
			for (int j = 0; j < len1; j++) {
				pokes51.add(rd.readByte());
			}
			sites.add(pm);
			DJieSuan.m_diPoke.put(siteNo, pokes);// 底牌
			DJieSuan.m_pokeWeight.put(siteNo, pokeweight); // 该玩家家牌型
			DJieSuan.m_bestPoke.put(siteNo, pokes51); // 该玩家的最佳组合牌
		}
		len = rd.readByte(); // 彩池个数
		ArrayList deskpools = new ArrayList();
		data.put("deskpools", deskpools);
		HashMap hmTemp;
		for (int i = 0; i < len; i++) {
			HashMap hm = new HashMap();
			// 本彩池总金额
			hm.put("chouma", rd.readInt());
			ArrayList winlist = new ArrayList();
			hm.put("winlist", winlist);
			// 彩池有几个人分
			len1 = rd.readByte();
			for (int j = 0; j < len1; j++) {
				hmTemp = new HashMap();
				// 应分筹码座位
				hmTemp.put("siteno", rd.readByte());
				// 应分数额
				hmTemp.put("winchouma", rd.readInt());
				winlist.add(hmTemp);
			}
			deskpools.add(hm);
		}
		// DTrace.traceex("收到游戏结束协议:", data);
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_GAMEOVER, data));
	}

	// 收到所有玩家信息
	public void onTexPlayerInfo(ReadData rd) {
		System.out.println("\nDNetworkTex onTexPlayerInfo 收到所有玩家信息\n");
		ArrayList data = new ArrayList();
		int len = rd.readInt(); // 坐下的人数
		System.out.println("坐下的人数： " + len);
		HashMap temp = null;
		for (int i = 0; i < len; i++) {
			temp = new HashMap();
			// 座位号
			temp.put("site", rd.readByte());
			// 状态号
			temp.put("state", rd.readByte());
			// 剩余时间
			temp.put("timeout", rd.readByte());
			// 全部的延时时间总时间
			temp.put("delay", rd.readByte());
			data.add(temp);
		}
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_PLAYERINFO, data));
	}

	// 收到桌面彩池信息
	public void onTexDeskPollInfo(ReadData rd) {
		System.out.println("\nDNetworkTex onTexDeskPollInfo 收到桌面彩池信息\n");
		ArrayList deskPondGold = new ArrayList();
		int len = rd.readInt();
		for (int i = 0; i < len; i++) {
			deskPondGold.add(rd.readInt()); // 桌面金币
		}
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_DESK_POLL_INFO,
				deskPondGold));
	}

	// 收到牌桌信息
	public void onTexDeskInfo(ReadData rd) {
		System.out.println("\nDNetworkText onTexDeskInfo 收到牌桌信息\n");

		DeskInfo deskInfo = new DeskInfo();
		deskInfo.setDeskno(rd.readInt());// 桌号
		deskInfo.setName(rd.readString()); // 名称
		deskInfo.setDesktype(rd.readByte());// 1普通，2比赛，3VIP专用
		deskInfo.setFast(rd.readByte());// 是否快速
		deskInfo.setBetgold(rd.readInt()); // 桌面筹码数
		deskInfo.setUsergold(rd.readInt()); // 桌子玩家筹码数
		deskInfo.setNeedlevel(rd.readInt()); // 桌子解锁等级
		deskInfo.setSmallbet(rd.readInt()); // 小盲
		deskInfo.setLargebet(rd.readInt()); // 大盲
		deskInfo.setAt_least_gold(rd.readInt()); // 金币下限
		deskInfo.setAt_most_gold(rd.readInt()); // 金币上限
		deskInfo.setSpecal_choushui(rd.readInt()); // 抽水
		deskInfo.setMin_playercount(rd.readByte()); // 最少开局人数
		deskInfo.setMax_playercount(rd.readByte()); // 最多开局人数
		deskInfo.setPlayercount(rd.readByte()); // 当前在玩人数
		deskInfo.setShowsitbtn(rd.readByte()); // 是否显示坐下按钮
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_DESK_INFO, deskInfo));
	}

	// BBS验证串
	public void onTexBbsUrl(ReadData rd) {
		System.out.println("\nDNetworkTex onTexBbsUrl BBS验证串\n");
		// String bbsUrl = rd.readString(); // BBS验证串
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_BBS_URL", bbsUrl));
	}

	// 收到桌子正在结算
	public void onTexJsWaitIng(ReadData rd) {
		Log.i("test9", "收到桌子正在结算");
		System.out.println("\nDNetworkTex onTexJsWaitIng 收到桌子正在结算\n");
		int leftTime = rd.readInt(); // 结算剩下的时间
		GameApplication.jieSuanIng = true;// 结算中
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_JS_WAITING",
		// leftTime));
	}

	public void onTexSingleDetail(ReadData rd) {
		System.out.println("\nDNetworkTex onTexSingleDetail \n");
		HashMap data = new HashMap();
		data.put("sys_time", rd.readString()); // 时间
		data.put("smallbet", rd.readInt()); // 小盲
		data.put("largebet", rd.readInt()); // 大盲
		data.put("betgold", rd.readInt()); // 下注额
		data.put("wingold", rd.readInt()); // 赢得筹码
		data.put("betflag", rd.readInt()); // 下注:-1弃牌、0正常、1全下
		data.put("pokeweight", rd.readString()); // 牌型，可能为0
		data.put("pokes5", rd.readString()); // 最佳牌组合:|12|15|16|17 ,可能空,
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_SINGLE_DETAIL",
		// data));
	}

	// 收到自己德洲豆
	public void onTexShowMyGold(ReadData rd) {

		int gold = rd.readInt(); // 收到自己德洲豆
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_SHOW_MY_TOTAL_BEAN,
				gold));
	}

	// 收到桌面扑克
	public void onTexDeskPoke(ReadData rd) {
		System.out.println("\nDNetworkTex onTexDeskPoke 收到桌面扑克 \n");
		ArrayList pokes = new ArrayList();
		int len = rd.readInt();
		for (int i = 0; i < len; i++) {
			pokes.add(rd.readByte());
		}
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_DESKPOKE, pokes));
	}

	// 收到游戏开始(开场动画)
	public void onTexGameStart(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGameStart 收到游戏开始(开场动画) \n");
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_GAMESTART, null));
	}

	// 收到购买筹码窗口
	public void onTexBuyChouMa(ReadData m_netptr) {
		System.out.println("\nDNetworkTex onTexBuyChouMa 收到购买筹码窗口 \n");
		HashMap data = new HashMap();
		data.put("deskno", m_netptr.readInt()); // 玩家要坐的座位号（如果是公会专场的话）
		data.put("min", m_netptr.readInt()); // 赔率*100
		data.put("max", m_netptr.readInt()); // 赔率*1000
		data.put("gold", m_netptr.readInt()); // 玩家实际金币
		data.put("defaultgold", m_netptr.readInt()); // 默认购买的金币
		data.put("timeout", m_netptr.readInt()); // 是否提示超时？在游戏中破产会提示30秒超时
		data.put("halfhour", m_netptr.readByte()); // 是否提示半小时前来过？0不提示，1提示
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_BUYCHOUMA, data));
	}

	// 收到商店出售的所有礼品列表
	public void onTexGiftShop(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiftShop 收到商店出售的所有礼品列表 \n");
		HashMap data = new HashMap();
		int itemcount = rd.readInt();
		data.put("itemcount", itemcount);
		ArrayList gifts = new ArrayList();
		data.put("gifts", gifts);
		for (int i = 0; i < itemcount; i++) {
			HashMap temp = new HashMap();
			temp.put("giftid", rd.readInt());
			temp.put("price", rd.readInt());// 出售价格
			gifts.add(temp);
		}
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_SHOP", data));
	}

	// 收到礼物标识
	public void onTexGiftIcon(ReadData rd) {

		HashMap data = new HashMap();
		data.put("siteno", rd.readByte());
		data.put("giftid", rd.readInt());
		Log.i("test4", "onTexGiftIcon giftid:" + data.get("giftid"));
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_GIFT_ICON, data));
	}

	// 播放送礼物动画
	public void onTexPlayGift(ReadData rd) {

		HashMap data = new HashMap();
		data.put("fromsiteno", rd.readByte());
		data.put("tositeno", rd.readByte());
		data.put("giftid", rd.readInt());
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_PLAY_GIFT, data));
	}

	// 购买礼物或发表情失败
	public void onTexGiftOrEmotFaild(ReadData rd) {

		byte retcode = rd.readByte(); // 1=成功扣钱 2=钱不够 0=其他异常 3=对方礼物满了
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_FAILD",
		// data));
	}

	// 批量购买礼物失败
	public void onTexGiftFaildList(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiftFaildList 批量购买礼物失败 \n");
		int len = rd.readInt(); // 失败的个数
		ArrayList data = new ArrayList();
		HashMap temp = null;
		for (int i = 0; i < len; i++) {
			temp = new HashMap();
			temp.put("siteno", rd.readByte());
			// 1=成功扣钱 2=钱不够 3=今天超过限额了 4=礼物满了 0=其他异常
			temp.put("retcode", rd.readInt());
			data.add(temp);
		}
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_FAILDLIST",
		// data));
	}

	// 出售礼品结果
	public void onTexGiftSale(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiftSale 出售礼品结果 \n");
		HashMap data = new HashMap();
		data.put("retcode", rd.readInt()); // 1成功，-1不存在的礼品，2次礼品不能回收，3系统忙，请稍后重试
		data.put("getgold", rd.readInt()); // 成功卖礼物得到多少钱，失败时无意义
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_SALE", data));
	}

	// 播放发表情动画
	public void onTexPlayEmot(ReadData rd) {
		System.out.println("\nDNetworkTex onTexPlayEmot 播放发表情动画 \n");
		HashMap data = new HashMap();
		data.put("siteno", rd.readByte());
		data.put("emotid", rd.readInt());
		Log.i("test18",
				"siteno: " + data.get("siteno") + " emotid: "
						+ data.get("emotid"));
		this.dispatchEvent(new Event(TexEventType.ON_TEX_RECV_PLAY_EMOT, data));
	}

	// 礼物响应
	public void onTexGiftResponse(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiftResponse 礼物响应 \n");
		HashMap data = new HashMap();
		data.put("fromsite", rd.readInt());
		data.put("tosite", rd.readInt());
		data.put("response_id", rd.readInt());
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_RESPONSE",
		// data));
	}

	// 收到礼物列表详情
	public void onTexGiftList(ReadData rd) {
		System.out.println("\nDNetworkTex onTexGiftList  收到礼物列表详情 \n");
		ArrayList giftlist = new ArrayList();
		int len = rd.readInt();
		HashMap temp = null;
		for (int i = 0; i < len; i++) {
			temp = new HashMap();
			temp.put("index", rd.readInt());
			temp.put("id", rd.readInt());
			temp.put("isusing", rd.readByte());
			temp.put("cansale", rd.readByte());// 是否可以出售
			temp.put("salegold", rd.readInt());// 可以买多少钱
			temp.put("fromuser", rd.readString());
			giftlist.add(temp);
		}
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_GIFT_LIST", data));
	}

	// 收到超时
	public void onTexTimeOut(ReadData rd) {
		System.out.println("\nDNetworkTex onTexTimeOut  \n");
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_TIMEOUT, null));
	}

	// 用户坐下
	public void onTexSitDown(ReadData rd) {
		System.out.println("\nDNetworkTex onTexSitDown  用户坐下 \n");
		HashMap data = new HashMap();
		data.put("recode", rd.readByte());
		data.put("bReloginUser", rd.readByte());
		data.put("userkey", rd.readString());
		data.put("nick", rd.readString());
		data.put("deskno", rd.readInt());
		data.put("siteno", rd.readInt());
		data.put("olddeskno", rd.readInt());
		data.put("oldsiteno", rd.readInt());
		data.put("total_gold", rd.readInt()); // 所有金币
		data.put("cityName", rd.readString());
		data.put("beginTimeOut", rd.readInt());
		data.put("face", rd.readString());
		data.put("sex", rd.readByte());
		data.put("startState", rd.readByte()); // 游戏状态
		data.put("userid", rd.readInt());
		data.put("channel", rd.readString());
		data.put("gameexp", rd.readInt());
		data.put("channelid", rd.readInt());
		data.put("peilv", rd.readInt());
		data.put("tour_point", rd.readInt()); // 竞技场点数
		data.put("gamelevel", rd.readInt()); // 等级
		data.put("handgold", rd.readInt()); // 手中的筹码
		data.put("viplevel", rd.readInt());// vip登级
		data.put("mobilemode", rd.readByte());// 是否手机登录 0:pc 和2 ：手机
		// rd.readInt();//short_channel_id
		// rd.readInt();//home_status
		dispatchEvent(new Event(TexEventType.ON_TEX_RECV_SITDOWN, data));
	}

	// 收到用户进入观战
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
		data.put("retcode", rd.readInt()); // 返回码
		Integer default_chouma = rd.readInt();
		data.put("default_chouma", default_chouma); // 默认买入的筹码数
		Integer obj = (Integer) GameApplication.userInfo.get("user_real_id");

		if (userid == obj) {

			GameApplication.userInfo.put("default_chouma", default_chouma);
			// 设置快速开始按钮可见
			// GameApplication.getDzpkGame().gameBottomView.addQuickStartButton();
		}
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_WATCHING", data));
	}

	// 用户站起
	public void onTexStandUp(ReadData rd) {
		System.out.println("\nDNetworkTex onTexStandUp  用户站起 \n");
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

	// 收到服务器返回错误提示:0xx大厅的，1xx游戏里的
	public void onTexServerError(ReadData rd) {
		// HashMap data = new HashMap();
		int msgType = rd.readByte();

		// data.put("msgtype", msgType);
		String msg = rd.readString();

		// data.put("msg", msg);
		if (msgType == 1) {
			// 游戏里
			dispatchEvent(new Event(TexEventType.ON_TEX_RECV_SERVER_ERROR, msg));
		} else if (msgType == 0) {
			// 游戏大厅
			dispatchEvent(new Event(TexEventType.ON_TEX_RECV_SERVER_ERROR, msg));
		}
	}

	// 收到头像变更
	public void onTexChangFace(ReadData rd) {

		System.out.println("\nDNetworkTex onTexChangFace 收到头像变更\n");
		HashMap data = new HashMap();
		data.put("userid", rd.readInt());
		data.put("siteno", rd.readInt());
		data.put("face", rd.readString());
		// this.dispatchEvent(new DNetWorkEvent("ON_TEX_RECV_CHANGEFACE",
		// data));
	}

	// 收到聊天
	public void onTexDeskChat(ReadData rd) {
		System.out.println("\nDNetworkTex onTexDeskChat 收到聊天\n");
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

	// 退出观战
	public void onTexExitWatch(ReadData rd) {
		System.out.println("\nDNetworkTex onTexExitWatch 退出观战\n");
		HashMap data = new HashMap();
		data.put("deskno", rd.readShort());
		data.put("userid", rd.readInt());
		this.dispatchEvent(new Event(Event.ON_RECV_EXIT_WATCH, data));
	}

	// 收到用户增加经验
	public void onTexAddExp(ReadData rd) {
		System.out.println("\nDNetworkTex onTexAddExp 收到用户增加经验\n");
		HashMap data = new HashMap();
		data.put("userid", rd.readInt());
		data.put("siteno", rd.readByte());
		data.put("addexp", rd.readInt());
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_ADD_EXPERIENCE",
		// data));
	}

	// 收到用户等级升级
	public void onTexLevelUpGrade(ReadData rd) {
		Log.i("test5", "onRecvLevelUpgrade onTexLevelUpGrade");
		// System.out.println("\nDNetworkTex onTexLevelUpGrade 收到用户等级升级\n");
		HashMap data = new HashMap();
		data.put("userid", rd.readInt());
		int siteNo = rd.readByte();
		data.put("siteno", siteNo);
		data.put("level", rd.readInt());
		data.put("givegold", rd.readInt());
		this.dispatchEvent(new Event(TexEventType.ON_RECV_LEVEL_UPGRADE, data));
	}

	// 收到用户获得天天送经验红利
	public void onTexDayAddExp(ReadData rd) {
		// System.out.println("\nDNetworkTex onTexDayAddExp 收到用户获得天天送经验红利\n");
		HashMap data = new HashMap();
		data.put("userid", rd.readInt());
		data.put("siteno", rd.readByte());
		data.put("level", rd.readInt());
		data.put("addexp", rd.readInt());
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_DAY_ADDEXP", data));
	}

	// 收到淘汰或者得奖
	public void onTexPrizeOrLost(ReadData rd) {
		// System.out.println("\nDNetworkTex onTexPrizeOrLost 收到淘汰或者得奖\n");
		HashMap data = new HashMap();
		data.put("userid", rd.readInt()); // userid
		data.put("siteno", rd.readByte()); // siteno
		data.put("mingci", rd.readByte()); // 名次
		data.put("givegold", rd.readInt()); // 获得金币
		data.put("addexp", rd.readInt()); // 获得经验
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_LEVEL_PRIZE_OR_LOST",
		// data));
	}

	// 收到系统派发信息
	public void onTexServerMsg(ReadData rd) {
		System.out.println("\nDNetworkTex onTexServerMsg  收到系统派发信息\n");
		HashMap data = new HashMap();
		data.put("msgtype", rd.readInt());
		data.put("userid", rd.readInt());
		data.put("msg", rd.readString());
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_LEVEL_SERVER_MSG",
		// data));
	}

	// 收到返回大厅结果
	public void onTexBackHallResult(ReadData rd) {
		System.out.println("\nDNetworkTex onTexBackHallResult  收到返回大厅结果\n");
		short success = rd.readShort(); // 结果
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
		data.put("kickType", rd.readByte()); // 是否可以踢人，byte，0：不可以，1：可以
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

	// 算牌东东
	public void onTexCalcEnable(ReadData rd) {
		System.out.println("\nDNetworkTex onTexCalcEnable 算牌东东\n");
		byte isType = rd.readByte(); // 是否可以算牌，byte，0：不可以，1：可以
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
		byte isWin = rd.readByte();// 0输，1赢
		// this.dispatchEvent(new DNetWorkEvent("ON_RECV_GIFT_XINSHOU_ONE",
		// data));
	}

	protected void onProcessCommand(ReadData rd) {

		String strCmd = rd.getStrCmd();
		Log.i(tag, " DNetworkTjh receive command: " + strCmd);
		if (CMD_NOTIRY_ZJH_FOLLOWDEAD.equals(strCmd)) {
			// 跟死
			onTexReady(rd);
		} else if (CMD_NOTIRY_ZJH_CLICKCAICHI.equals(strCmd)) {
			// 收到点击彩池
			onTexFaPai(rd);
		} else if (CMD_NOTIRY_ZJH_GETCAICHIINFO.equals(strCmd)) {
			// 收到彩池信息
			onTexBestPoke(rd);
		} else if (CMD_NOTIRY_ZJH_RELOGIN_ACTION.equals(strCmd)) {

			// 收到重登录动作按钮
			onTexResetDisplay(rd);
		} else if (CMD_NOTIFY_ZJH_RELOGIN.equals(strCmd)) {
			// 收到掉线用户重登录消息
			onTexXiaZhu(rd);
		} else if (CMD_NOTIFY_ZJH_RECVOFFLINESITE.equals(strCmd)) {
			// 收到离线消息
			onTexGiveUp(rd);
		} else if (CMD_RESPONSE_ZJH_GETCAICHI.equals(strCmd)) {
			// 收到自己中了彩池
			onTexBuXiaZhu(rd);
		} else if (CMD_RESPONSE_ZJH_CHOUSHUINUM.equals(strCmd)) {
			// 收到抽水多少
			onTexRefreshGold(rd);
		} else if (CMD_RESPONSE_ZJH_TIMEOUT.equals(strCmd)) {
			// 收到自己超时
			onTexPanel(rd);
		} else if (CMD_NOTIFY_ZJH_RECVBIPAISITE.equals(strCmd)) {
			// 收到可以比牌的座位号
			onTexAutoPanel(rd);
		} else if (CMD_NOTIFY_ZJH_ROOMBASICINFO.equals(strCmd)) {
			// 收到刷新房间基本信息
			onTexGameOver(rd);
		} else if (CMD_NOTIFY_ZJH_ROOMINFO.equals(strCmd)) {
			// 收到刷新房间信息
			onTexPlayerInfo(rd);
		} else if (CMD_NOTIFY_ZJH_GIVEUP.equals(strCmd)) {
			// 收到投降
			onTexDeskPollInfo(rd);
		} else if (CMD_NOTIFY_ZJH_REFRESHGOLD.equals(strCmd)) {
			// 刷新金币
			onTexDeskInfo(rd);
		} else if (CMD_NOTIFY_ZJH_GAMEOVER.equals(strCmd)) {
			// 游戏结束，重开一局
			onTexBbsUrl(rd);
		} else if (CMD_NOTIFY_ZJH_XIAZHU.equals(strCmd)) {
			// 用户下注
//			onTexTodayDetail(rd);
		} else if (CMD_NOTIFY_ZJH_SITDOWN.equals(strCmd)) {
			// 用户坐下
//			onTexTodayDetailEnd(rd);
		} else if (CMD_NOTIFY_ZJH_STANDUP.equals(strCmd)) {
			// 用户站起
			onTexJsWaitIng(rd);
		} else if (CMD_NOTIFY_ZJH_START.equals(strCmd)) {
			// 收到用户点开始
			onTexSingleDetail(rd);
		} else if (CMD_NOTIFY_ZJH_DESK_INFO.equals(strCmd)) {
			// 收到本桌各座位的状态
			onTexShowMyGold(rd);
		} else if (CMD_NOTIFY_ZJH_FAPAI.equals(strCmd)) {
			// 收到发牌
			onTexDeskPoke(rd);
		} else if (CMD_NOTIFY_ZJH_KANPAI.equals(strCmd)) {
			// 收到看牌
			onTexGameStart(rd);
		} else if ("NTTT".equals(strCmd)) {
			// 收到购买筹码窗口
			onTexBuyChouMa(rd);
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

		m_netptr.writeString("TXRQST");
		m_netptr.writeEnd();
	}

	// 发送点放弃按钮
	public void sendClickGiveUp() throws Exception {
		m_netptr.writeString("TXRQFQ");
		m_netptr.writeEnd();
	}

	// 发送点全下按钮
	public void sendClickTex() throws Exception {

		m_netptr.writeString("TXRQAI");
		m_netptr.writeEnd();
	}

	// 发送点跟按钮
	public void sendClickFollow() throws Exception {

		m_netptr.writeString("TXRQGZ");
		m_netptr.writeEnd();
	}

	// 发送点不下按钮
	public void sendClickBuxia() throws Exception {

		m_netptr.writeString("TXRQBX");
		m_netptr.writeEnd();
	}

	// 发送点购买筹码
	public void sendBuyChouma(int gold, int deskno, int siteno)
			throws Exception {
		// DTrace.traceex("-----------siteno:" + siteno);
		m_netptr.writeString("TXRQBC");
		m_netptr.writeInt(gold);
		m_netptr.writeInt(deskno);
		m_netptr.writeByte((byte) siteno);
		m_netptr.writeByte((byte) 0);// 自动顶注
		m_netptr.writeByte((byte) 0);// 自动买入
		m_netptr.writeEnd();
	}

	// 点下注/加注按钮
	public void sendClickXiaZhu(int gold, int type_1xia_2jia) throws Exception {

		m_netptr.writeString("TXRQXZ");
		m_netptr.writeInt(gold);
		m_netptr.writeByte((byte) type_1xia_2jia);
		m_netptr.writeEnd();
	}

	// 发送请求购买筹码，即要坐下
	public void sendRequestTXNTBC() throws Exception {

		m_netptr.writeString("TXNTBC");
		m_netptr.writeEnd();
	}

	// 发送请求排队(买完筹码调)
	public void sendRequestPaiDui() throws Exception {

		m_netptr.writeString("RQDQ");
		m_netptr.writeEnd();
	}

	// 发送请求快速开始（点击桌子下面的快速开始按钮）
	public void sendRequestQuickStart(int chouma) throws Exception {

		m_netptr.writeString("TXAUSI");
		m_netptr.writeInt(chouma);
		m_netptr.writeEnd();
	}

	// 发送请求桌子信息
	public void sendRequestDeskInfo(int deskno) throws Exception {

		m_netptr.writeString("TXNINF");
		m_netptr.writeInt(deskno);
		m_netptr.writeEnd();
	}

	// 发送请求论坛字串
	public void sendRequestURL() throws Exception {

		// m_netptr.writeString(CMD_NOTIFY_TEX_BBS_URL);
		// m_netptr.writeEnd();
	}

	// ////////////////////////表情和礼物 开始/////////////////////////////////////
	// 发表情
	public void sendPlayEmot(int emotid) throws Exception {

		m_netptr.writeString("TXEMOT");
		m_netptr.writeInt(emotid);
		m_netptr.writeEnd();
	}

	// 请求商品列表
	public void sendOpenShop() throws Exception {
		m_netptr.writeString("TXGFSP");
		m_netptr.writeEnd();
	}

	// 给xx,xx,xx买礼物
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

	// 请求查看已有礼物列表
	public void sendRequestGiftList(int user_id) throws Exception {
		m_netptr.writeString("TXGFLT");
		m_netptr.writeInt(user_id);
		m_netptr.writeEnd();
	}

	// 请求穿某个礼物
	public void sendRequestUseGift(int gift_index) throws Exception {

		m_netptr.writeString("TXGFUS");
		m_netptr.writeInt(gift_index);
		m_netptr.writeEnd();
	}

	// 发送礼物响应
	public void sendGiftResponse(int user_id, int response_id) throws Exception {

		m_netptr.writeString("TXGFRS");
		m_netptr.writeInt(user_id);
		m_netptr.writeInt(response_id);
		m_netptr.writeEnd();
	}

	// 请求扔某个礼物
	public void sendRequestDropGift(int gift_index) throws Exception {

		m_netptr.writeString("TXGFDP");
		m_netptr.writeInt(gift_index);
		m_netptr.writeEnd();
	}

	// 请求卖掉某个礼物
	public void sendRequestSaleGift(int gift_index) throws Exception {
		m_netptr.writeString("TXGFSL");
		m_netptr.writeInt(gift_index);
		m_netptr.writeEnd();
	}

	// ////////////////////////表情和礼物 结束/////////////////////////////////////
	// 发送桌内聊天
	public void sendDeskChat(String chatText) throws Exception {

		m_netptr.writeString("RQDC");
		m_netptr.writeByte((byte) 2);
		m_netptr.writeString(chatText);
		m_netptr.writeInt(0);
		m_netptr.writeEnd();
	}

	// 请求公测送礼
	public void sendGetBetaGife() throws Exception {

		m_netptr.writeString("RQGIFT");
		m_netptr.writeEnd();
	}

	private final String CMD_REQUEST_UPDATE_VIPINFO = "RQVIPIF"; // 客户端请求刷新VIP信息

	public void sendUpdateVipInfo() throws Exception {
		m_netptr.writeString(CMD_REQUEST_UPDATE_VIPINFO);
		m_netptr.writeEnd();
	}

}
