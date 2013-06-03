package com.dozengame.net.pojo;

import java.util.ArrayList;
import java.util.HashMap;

public class DJieSuan {

	public static ArrayList m_siteList = new ArrayList(); // 所有玩家列表
	public static ArrayList m_siteGold = new ArrayList(); // 各个玩家手里的筹码
	public static ArrayList m_winsiteList = new ArrayList(); // 赢家列表
	public static HashMap m_pondList = new HashMap(); // 各个赢家获得彩池的信息
	public static HashMap m_bestPoke = new HashMap(); // 各个玩家的最佳组合牌
	public static HashMap m_diPoke = new HashMap(); // 坚持到底各个玩家的底牌
	public static HashMap m_windGold = new HashMap(); // 各个赢家赢的钱
	public static HashMap m_pokeWeight = new HashMap(); // 各个玩家的牌型
	public static HashMap m_winPokeWeight = new HashMap(); // 各个赢家的牌型
	public static ArrayList m_finalBeskPoke = new ArrayList(); // 本局最佳组合牌

	public static int m_mychipIn; // 本人本轮总下注
	public static int m_mygainGold; // 本人本轮总获得
	public static int m_mywinGold; // 本人本轮共计赢得（总获得 - 总下注 = 总赢)
	public static int m_mycurTotalGold; // 本人当前的总德州豆
	public static int m_choushui; // 系统抽水，即本局消耗
	public static int[] m_totalCaiChi = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // 彩池信息
	// public static IDHall m_hall ;
	public static int m_iscomplete = 0; // 结算标志，是否正常结束：区别于中途所有人放弃
	// 处理结算数据
	public static ArrayList m_itemList = new ArrayList();

	public DJieSuan() {

	}

	// 在游戏开始的时候，清空结算数据
	public static void initData() {
		m_siteList.clear(); // 所有玩家列表
		m_siteGold.clear(); // 各个玩家手里的筹码
		m_winsiteList.clear(); // 赢家列表
		m_pondList.clear(); // 各个赢家获得彩池的信息
		m_bestPoke.clear(); // 各个赢家的最佳组合牌
		m_diPoke.clear(); // 各个赢家的底牌
		m_windGold.clear(); // 各个赢家赢的钱
		m_pokeWeight.clear(); // 各个赢家牌大小
		m_finalBeskPoke.clear(); // 本局最佳组合牌
		m_itemList.clear();
		int len = m_totalCaiChi.length;
		for (int i = 0; i < len; i++) {
			m_totalCaiChi[i] = 0;
		}
		m_mychipIn = 0;
		m_mygainGold = 0;
		m_mywinGold = 0;
		m_choushui = 5;
		m_mycurTotalGold = 0;

	}

	// public static void dealJieSuanData(ArrayList playerList, IDHall hall, int
	// mysite){
	//		
	// 
	// m_winsiteList.sort(sortbypokeweit);
	// int len=m_winsiteList.size();
	// for (int i = 0; i < len; i++){
	// HashMap obj = new HashMap();
	// int siteno = (Integer)m_winsiteList.get(i);
	// obj.put("userName", "");
	//		 
	// if(playerList[siteno])
	// obj["userName"] = playerList[siteno].nick;
	// obj["totalGold"] = m_windGold[siteno];
	// obj["poollist"] = m_pondList[siteno];
	// if (mysite == siteno) obj["isme"] = 1;
	// m_itemList[i] = obj;
	// //DTrace.traceex("dealJieSuanData::::obj::::", obj);
	// }
	// //DTrace.traceex("dealJieSuanData::itemlist::::", m_itemList);
	// m_mycurTotalGold = int(hall.getUserInfo().gold); //总的德州豆
	// }

	// //根据牌重，从大到小排序赢家列表
	// public static int sortbypokeweit(site1:int, site2:int){
	// Number pokew1 = m_pokeWeight[site1];
	// Number pokew2 = m_pokeWeight[site2];
	// if (pokew1 < pokew2) return 1;
	// else if (pokew1 > pokew2) return -1;
	// else return 0;
	// }
	// 游戏标志，是否发完牌结束的
	public void setIscomplete(int setValue) {
		m_iscomplete = setValue;
	}

	public int getIscomplete() {
		return m_iscomplete;
	}
}
