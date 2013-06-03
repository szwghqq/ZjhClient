package com.dozengame;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dozengame.util.GameUtil;
 

/**
 * 游戏数据
 * 
 * @author hewengao
 * 
 */
public class GameDataManager {
	
	final String tag = "com.dozengame.GameView";
	// 状态
	public int state = 0;
	// 桌面上所有玩家信息
	public HashMap deskUsers = null;
	// 玩家的状态
	public ArrayList playerInfoState = null;
	// 坐下的玩家
	public HashMap sitDownUsers = new HashMap();
	// 收到发牌
	public HashMap recvFaPai = null;
	// 我的座位
	public int mySiteNo = -1;
	// 我的牌
	public int[] myPoke = new int[2];
	public ArrayList pokes;// 牌面牌

	public DzpkGameActivityDialog dzpkGame = null;

	/**
	 * 构造函数
	 * 
	 * @param dzpkGame
	 */
	public GameDataManager(DzpkGameActivityDialog dzpkGame) {
		this.dzpkGame = dzpkGame;
	}

	/**
	 * 设置桌子的玩家信息
	 * 
	 * @param deskUsers
	 */
	public void setDeskUsers(HashMap deskUsers) {
		
		this.deskUsers = deskUsers;
//		int deskChouMa = (Integer) deskUsers.get("betgold");// 桌面筹码
//		int usergold = (Integer) deskUsers.get("usergold");// 桌子玩家筹码数
//		int playercount = (Byte) deskUsers.get("playercount"); // 在玩人数
//		int watchercount = (Integer) deskUsers.get("watchercount"); // 观战人数
//		HashMap data=new HashMap();
//		data.put("deskno", rd.readInt());
//		data.put("betgold",rd.readInt());	//桌面筹码数
//		data.put("usergold",rd.readInt());	//桌子玩家筹码数
//		data.put("playercount",rd.readByte());  //在玩人数
//		data.put("watchercount",rd.readInt());   //观战人数
//		List list =new ArrayList();
//		data.put("userlist",list);
//		int n = rd.readByte();		//桌内人数
//		HashMap temp=null;
//		for (int i = 0; i < n; i++){
//			temp=new HashMap();
//			temp.put("state_value",rd.readByte());		//每个座位的状态 SITE_UI_VALUE = _S{NULL = 0, NOTREADY = 1, READY = 2, PLAYING = 3}
//			temp.put("userid",rd.readInt());		//ID
//			temp.put("nick",rd.readString());	//昵称
//			temp.put("isvip",rd.readByte());		//是否VIP玩家:0不是，1是
//			temp.put("faceurl",rd.readString());	//头像
//			temp.put("gold",rd.readInt());		//金币
//			list.add(temp);
//		}

	}
   public int siteNo=-1;
   private boolean hasPlayerState =false;
	/**
	 * 更新所有玩家状态
	 * @param sitDownUsers
	 */
	public void setPlayerInfoState(ArrayList playerInfoState) {
		try{
			this.playerInfoState = playerInfoState;
			hasPlayerState = true;
			// 判断是否已坐到自己对应的位置,如果未则需要等到所有玩家坐下后才能绘制
			if (!(GameApplication.getDzpkGame().playerViewManager.mySite && GameApplication
					.getDzpkGame().playerViewManager.zhunDongValue != 2)) {
				   executePlayerState();
			}
		}catch(Exception e){
			
		}
		 
	}
	/**
	 * 更新玩家状态
	 */
	public void updatePlayerState(boolean isGameOver){
		 dzpkGame.playerViewManager.setPlayerState(playerInfoState,isGameOver);
	}
	/**
	 * 执行玩家状态
	 */
	private void executePlayerState(){
		if(hasPlayerState){
			if (playerInfoState != null) {
					int size = playerInfoState.size();
					HashMap temp = null;
					int state;
			  for (int i = 0; i < size; i++) {
					temp = (HashMap) playerInfoState.get(i);
					//Log.i(tag, "site : " + (Byte) temp.get("site"));// 座位号
					//Log.i(tag, "state: " + (Byte) temp.get("state"));// 状态号
					// 0:默认状态 1:等待 2:未准备 4:离开5:收到面板6:购买筹码
					state = (Byte) temp.get("state");
					if (state == 5) {
						// 剩余时间
						byte timeOut= (Byte) temp.get("timeout");
						 // 总时间
						byte delay =(Byte)temp.get("delay");
						 
						siteNo = (Byte) temp.get("site");
						// 绘制计时器
						dzpkGame.jsqViewManager.setJsqSiteNo(siteNo,timeOut,delay);
						//Log.i("test13","timeOut: "+timeOut+" delay: "+delay+" siteNo: "+siteNo);
						break;
					}
				}
			   updatePlayerState(false);
			}
		}
		hasPlayerState =false;
		siteNo=-1;
		
	}
 
	//是否有人坐下
	private boolean hasPlayerSit =false;
	//当前坐下的玩家
	ArrayList<HashMap> currentSitPlayer = new ArrayList<HashMap>();
	private ArrayList<Boolean> listHashPalerSit = new ArrayList<Boolean>();
	private boolean isSelfSit=false;
	/**
	 * 添加或替换坐下用户
	 * 此处每次有一个人坐下将会依次加入坐下的所有人
	 * @param sitDownUser
	 */
	public void addOrReplaceSitDownUser(HashMap sitDownUser) {
		//坐下前先执行需要站起的处理
		executeStandUpAction2();
		executeStandUpAction();
		if (sitDownUser != null) {
			Integer siteNo = (Integer) sitDownUser.get("siteno");
			//Log.i("test11", "sitDown siteNo: "+siteNo);
			if (siteNo != null) {
				HashMap  user = (HashMap)sitDownUsers.get(siteNo);
				if(user != null){
					int userid1=(Integer)user.get("userid");
					int userid2=(Integer)sitDownUser.get("userid");
					if(userid1 == userid2){
						Log.i("test15", "坐下是同一个人: userid1: "+userid1+" userid2: "+userid2);
						sitDownUsers.put(siteNo, sitDownUser);
						return;
					}else{
						Log.i("test15", "坐下不是同一个人: userid1: "+userid1+" userid2: "+userid2);
					}
				}
				 {
					currentSitPlayer.add(sitDownUser);
					sitDownUsers.put(siteNo, sitDownUser);
					listHashPalerSit.add(true);
					Object obj = GameApplication.userInfo.get("user_real_id");
					if(sitDownUser.get("userid").equals(obj)){
						isSelfSit=true;//自己坐下
					}
					//hasPlayerSit=true;
					 
					if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){
						
						  executeSitDownAction();
						
					}
				} 
			}
		}
	}
	 
	 
	/**
	 * 执行坐下动作
	 */
	public void executeSitDownAction(){
		 //自己坐下时未在结算中可以执行坐下,在结算中则需要等待结算结束
		 if(isSelfSit && (GameApplication.jieSuanIng || GameApplication.getDzpkGame().faPaiManager.faiPaiIng))return;
		  isSelfSit =false;
		 if(!listHashPalerSit.isEmpty()){
			   if(listHashPalerSit.remove(0)){
			     // hasPlayerSit =false;
				   if(dzpkGame != null && dzpkGame.playerViewManager !=null){
					  Log.i("test15", "currentSitPlayer: "+currentSitPlayer.size());
			          dzpkGame.playerViewManager.sitDown(currentSitPlayer);
				   }
			   }
			}else{
			   
			}
		 
		 
	 
	}
	

   boolean hasPalerUp =false;
    //当前移除玩家
	ArrayList<Integer> currentRemovePlayer = new ArrayList<Integer>();
	public boolean hasPalerUp2 =false;
	ArrayList<Integer> currentRemovePlayer2 = new ArrayList<Integer>();
    
	/**
	 * 收到用户站起
	 * 
	 * @param obj
	 */
	public void setRecvStandUp(HashMap data) {

		String nick = (String) data.get("nick");
		String currentuser=  (String) data.get("currentuser");
		//Log.i(tag, "setRecvStandUp " + nick);
		int removeSiteNo = (Integer) data.get("siteno");
		///Log.i("test11", "setRecvStandUp siteNo: "+removeSiteNo);
		int recode = (Integer)data.get("recode");
		//Log.i("test","recode: "+recode);
		HashMap users=(HashMap)sitDownUsers.get(removeSiteNo);
		if(users == null || nick== null || !nick.equals(users.get("nick"))){
			Log.i("test15", "standUp nick: "+nick+" siteNo: "+removeSiteNo+"不存在");
			return;
		}
		if(recode != 0){
			//立即站起
			hasPalerUp =true;
			currentRemovePlayer.add(removeSiteNo);
		}else{
			hasPalerUp2 =true;
			currentRemovePlayer2.add(removeSiteNo);
		}
		Log.i("test15", "standUp nick: "+nick+" siteNo: "+removeSiteNo+"  currentuser: "+currentuser);
		sitDownUsers.remove(removeSiteNo);
		
		//hasRecvStandUp=true;
		if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
			executeStandUpAction2();
			executeStandUpAction();
		}
	 
	}
	/**
	 * 结算后站起
	 * 执行用户站起动作
	 */
	private void executeStandUpAction2(){
			if(hasPalerUp2){
				//Log.i("test", "executeStandUpAction2executeStandUpAction2executeStandUpAction2 size : "+currentRemovePlayer2.size());
				hasPalerUp2 =false;
			    GameApplication.getDzpkGame().playerViewManager.removeSiteNoPlayer(currentRemovePlayer2);
			}
		 
	}
	/**
	 * 立即站起
	 * 执行用户站起动作
	 */
	private void executeStandUpAction(){
			if(hasPalerUp){
				hasPalerUp =false;
			    GameApplication.getDzpkGame().playerViewManager.removeSiteNoPlayer(currentRemovePlayer);
			}
		 
	}
    //桌子的座位
	public ArrayList siteList = null;
	private boolean hasFaiPai =false;
	/**
	 * 设置发牌
	 * 
	 * @param recvFaPai
	 */
	public void setRecvFaPai(HashMap recvFaPai) {
		this.recvFaPai = recvFaPai;
		 
		if (recvFaPai != null) {
			if(siteList != null)siteList.clear();
		    siteList = (ArrayList) recvFaPai.get("siteList");
			ArrayList pokes = (ArrayList) recvFaPai.get("pokes");			 
			if (pokes != null) {
				int size  = pokes.size();
				for (int i = 0; i < size; i++) {
					//Log.i(tag, "poke: " + pokes.get(i));
					if (i == 2) {
						break;
					}
					myPoke[i] = (Byte) pokes.get(i);
				}
				if(size != 0){
					dzpkGame.myPoke.setPokes(myPoke);
				}
			}
			hasFaiPai =true;
			//判断是否已坐到自己对应的位置,如果未则需要等到所有玩家坐下后才能绘制,将在所有玩家坐下后通知发牌 
			if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){
				executeFaiPaiAction();
			}
		}
	}
	public boolean faiPai =false;//是否发牌了
	/**
	 * 执行发牌动作
	 */
	private void executeFaiPaiAction(){
		
		if(hasFaiPai){
		    
		   hasFaiPai =false;
		   faiPai =true;
		   dzpkGame.faPaiManager.startFaPaiAnim();
		}
		
	}
	/**
	 * 得到庄家座位号 
	 * @param index 将传入 0
	 * @return
	 */
	public int getSiteNo(int index){
		if(siteList != null ){
			int size =siteList.size();
			if(size>index){
				return (Byte)siteList.get(index);
			}
		}
		return -1;
	}

	/**
	 * 设置面板
	 * 
	 * @param type
	 *            1:普通面板 2：自动面板
	 * @param recvPanel
	 */
	public void setRecvPanel(int type, HashMap data) {
		if (type == 2) {
			byte guo = (Byte) data.get("guo");
			byte guoqi = (Byte) data.get("guoqi");
			byte genrenhe = (Byte) data.get("genrenhe");// 跟任何
			byte gen = (Byte) data.get("gen");// 跟
			int gengold = (Integer) data.get("gengold");// 跟的金额
			GameApplication.getDzpkGame().gameBottomView.addAutoButtonLayout(data);
		} else {
			GameApplication.getDzpkGame().gameBottomView.addButtonLayout(data);
			//state = 1;
			// 设置普通面板的值
			//GameApplication.getDzpkGame().consoleView.setPanelData(data);
		}
	}

	/**
	 * 被踢了
	 */
	public void setRecvKickMe() {
		if (mySiteNo != -1) {
			sitDownUsers.remove(mySiteNo);
		}
		mySiteNo = -1;
		cleareMyPoke();

	}
	
    //是否有人下注
	private boolean hasXiaZhu=false;
	private ArrayList<HashMap> xiaZhuData = new ArrayList<HashMap>();
	/**
	 * 某座位下注成功
	 * 
	 * @param obj
	 */
	public void setRecvXiaZhuSucc(HashMap data) {
		if (data != null) {
			hasXiaZhu =true;
		    xiaZhuData.add(data);
		    dzpkGame.jsqViewManager.setIsOperation(true);//已操作
//			byte siteNo = (Byte) data.get("siteno");// 座位号
//			int betGold = (Integer) data.get("betgold");// 总的下注额
//			int currbet = (Integer) data.get("currbet");// 本轮下注额
//			byte sex = (Byte) data.get("sex"); // 1=男 0=女
//			byte type = (Byte) data.get("type");// 1=下注 2=加注 3=底注 4=梭哈 5=跟注
			if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
				executeXiaZhuAction();
			}
		}
	}
	/**
	 * 执行下注动作
	 */
	public void executeXiaZhuAction(){
		if(GameApplication.getDzpkGame().faPaiManager.faiPaiIng == false){
		 if(hasXiaZhu && !xiaZhuData.isEmpty()){
			 GameApplication.getDzpkGame().xiaZhuViewManager.setPlayerXiaZhuChouMa(xiaZhuData);
			
		 }
		 hasXiaZhu =false;
		}
	 
	}
	/**
	 * 自己的德州豆
	 * 
	 * @param myTotalGold
	 */
	public void setRecvShowMyTotalBean(Integer myTotalGold) {
		//Log.i(tag, "setRecvShowMyTotalBean myTotalGold : " + myTotalGold);
		if (mySiteNo != -1) {
			HashMap user = (HashMap) sitDownUsers.get(mySiteNo);
			if (user != null) {
				// 更新手上金币
				user.put("total_gold", myTotalGold);
			}
		}
	}
	private boolean hasRecvDeskPoke=false;
	/**
	 * 添加显示桌面的牌
	 * 
	 * @param obj
	 */
	public void addDeskPoke(ArrayList pokes) {
		this.pokes = pokes;
		hasRecvDeskPoke =true;
		if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
			  //当前没有在转动过程中
			if(!GameApplication.getDzpkGame().xiaZhuViewManager.currentXiaZhu){
			   //当前没有正在下注
				executeDeskPokeAction();
		   }
			
		}
	}
	/**
	 * 执行收到桌面牌的动作
	 */
	public void executeDeskPokeAction(){
		if(hasRecvDeskPoke){
		  hasRecvDeskPoke =false;
		  //执行下注动画
		  GameApplication.getDzpkGame().playerChouMaViewManager.execXiaZhuChouMaAnim();
		  //翻牌动画
		  GameApplication.getDzpkGame().globalPokeManager.setDeskPokes(pokes);
		}
	}

	 
	private boolean hasRecvSiteGold=false;
	int refreshSiteGold;//刷新的金币
	int refreshSite;//刷新的座位
	HashMap<Integer,Integer> refreshGold = new HashMap<Integer,Integer>();
	/**
	 * 收到某个座位的金币刷新
	 * 
	 * @param obj
	 */
	public void setRecvRefreshGold(HashMap data) {
         if(data == null)return;
		 refreshSite= (Byte) data.get("siteno");//刷新的座位
		 refreshSiteGold = (Integer) data.get("gold");//刷新的金币
		data.clear();
		HashMap user = (HashMap) sitDownUsers.get(refreshSite);
		if (user != null) {
			// 更新手上金币
			 
			user.put("handgold", refreshSiteGold);
			if(GameOver){
			    refreshGold.put(refreshSite, refreshSiteGold);
			}else{
				hasRecvSiteGold =true;
				if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
					 
					executeRefreshSiteGoldAction();
				}else{
					 
				}
			}
		}else{
			 
		} 
	}
	/**
	 * 结算赢钱动画调用
	 * 执行刷薪座位金币动作
	 */
	public void executeRefreshSiteGoldAction(int siteNo){
		 Integer gold =refreshGold.remove(siteNo);
		 if(gold != null){
		  GameApplication.getDzpkGame().playerViewManager.setRecvRefreshGold(siteNo,gold);
		 }else{
			 
		 }
	}
	/**
	 * 执行刷薪座位金币动作
	 */
	private void executeRefreshSiteGoldAction(){
		  if(hasRecvSiteGold){
		    hasRecvSiteGold =false;
		    GameApplication.getDzpkGame().playerViewManager.setRecvRefreshGold(refreshSite,refreshSiteGold);
	      }
	}
    //控制游戏是否结束
	public boolean GameOver =false;
	private boolean hasGameOver=false;
	 
	/**
	 * 收到游戏结束
	 * 
	 * @param obj
	 */
	public void setRecvGameOver(HashMap data) {
		
	    faiPai =false;
		GameOver =true;
		hasGameOver =true;
	   
		if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
		       //当前没有在转动过程中
			if(!GameApplication.getDzpkGame().xiaZhuViewManager.currentXiaZhu){
			   //当前没有正在下注
			 executeGameOverAction();
		   }
		} 
		
	 
	 
	
	}
	/**
	 * 执行游戏结束动作
	 */
	public void executeGameOverAction(){
	
		if(hasGameOver){
			hasGameOver =false;
			if(GameApplication.getDzpkGame().playerViewManager.mySite){
			  GameApplication.getDzpkGame().gameBottomView.addWait();
			}else{
			  GameApplication.getDzpkGame().gameBottomView.removeAllViews();
			}
			//停止计时器
			dzpkGame.jsqViewManager.setStop();
			//绘制下注筹码动画
			dzpkGame.playerChouMaViewManager.execXiaZhuChouMaAnim();
		}
	}
	boolean hasGiftIcon =false;
	 ArrayList<HashMap> giftIconDataList=new ArrayList<HashMap>();
    public void setGiftIcon(HashMap data) {
		//this.giftIconData =data;
		giftIconDataList.add(data);
    	hasGiftIcon =true;
		DzpkGameActivityDialog dzpkGame = GameApplication.getDzpkGame();
		if(!(dzpkGame.playerViewManager.mySite  && dzpkGame.playerViewManager.zhunDongValue !=2)){	
		       //当前没有在转动过程中
			executeGiftIconAction();
		} 
	}
    /**
	 * 执行礼物动作
	 */
	private void executeGiftIconAction(){
		if(hasGiftIcon){
			hasGiftIcon =false;
			 
			GameApplication.getDzpkGame().playerViewManager.setGiftIcon(giftIconDataList);
		}
	}
    boolean hasPlayGiftIcon =false;
    ArrayList<HashMap> playGiftIconDataList = new ArrayList<HashMap>();
	//HashMap playGiftIconData=null;
    public void setPlayGiftIcon(HashMap data) {
		// TODO Auto-generated method stub
    	 
    	//this.playGiftIconData =data;
    	playGiftIconDataList.add(data);
    	hasPlayGiftIcon =true;
		DzpkGameActivityDialog dzpkGame = GameApplication.getDzpkGame();
		if(!(dzpkGame.playerViewManager.mySite  && dzpkGame.playerViewManager.zhunDongValue !=2)){	
		       //当前没有在转动过程中
			executePlayGiftIconAction();
		} 
	}
    /**
	 * 执行播放礼物动作
	 */
	private void executePlayGiftIconAction(){
		if(hasPlayGiftIcon){
			hasPlayGiftIcon =false;
			GameApplication.getDzpkGame().playerViewManager.setPlayGiftIcon(playGiftIconDataList);
		}
	}
	/**
	 * 收到游戏开始
	 */
	public void setRecvGameStart() {
		GameOver =false;
		GameApplication.getDzpkGame().gameBottomView.removeAllViews();
		GameApplication.getDzpkGame().reset();
		GameApplication.getDzpkGame().playerChouMaViewManager.currentMainIndex=0;
		//GameApplication.getDzpkGame().playerViewManager.resetPlayerState();
		cleareMyPoke();
	}
	/**
	 * 清空自己的牌
	 */
	private void cleareMyPoke(){
		myPoke[0] = 0;
		myPoke[1] = 0;
	}
	 
	

	/**
	 *座位玩家不下注
	 * 
	 * @param siteNo
	 */
	public void setRecvBuXiaZhu(byte siteNo) {
		dzpkGame.jsqViewManager.setIsOperation(true);//已操作
		GameApplication.getDzpkGame().playerViewManager.setPlayerStateBySiteNo(siteNo, 7);
		 
	}
    /**
     * 收到桌面彩池信息
     * @param obj
     */
	public void setRecvDeskPollInfo(ArrayList data) {
		 
		GameApplication.getDzpkGame().playerChouMaViewManager.recvDeskPollInfo(data);
	}
	 
	/**
	 * 收到自己的最佳牌型
	 * 
	 * @param data
	 */
	public void setRecvBestPokess(HashMap data) {
		String weight=(String)data.get("weight");//牌型
		ArrayList pokes =  (ArrayList)data.get("pokes");//组合的牌
	 
	}
	private boolean hasRecvGiveUp =false;
	private int giveUpSiteNo;
	ArrayList<Integer> giveUpList = new ArrayList<Integer>();
	 /**
     * 弃牌
     * @param siteNo 座位号
     */
	public void setRecvGiveUp(Byte siteNo) {
		hasRecvGiveUp =true;
		giveUpSiteNo=siteNo;
		giveUpList.add(giveUpSiteNo);
		dzpkGame.jsqViewManager.setIsOperation(true);//已操作
		if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
			
			executeGiveUpAction();
			 
		}
	}
	/**
	 * 执行玩家弃牌动作
	 */
	public void executeGiveUpAction(){
		//是否在发牌中
		if(GameApplication.getDzpkGame().faPaiManager.faiPaiIng ==false){
			if(hasRecvGiveUp){
				while(giveUpList.size()>0){
					dzpkGame.playerViewManager.setPlayerStateBySiteNo(giveUpList.remove(0), 6);
					
				}
				
			}
			hasRecvGiveUp =false;
			giveUpSiteNo =-1;
		}
		
	}
	/**
	 * 发送购买筹码
	 */
	public void sendBuyChouma() {
		// 发送购买筹码指令
		//dzpkGame.sendBuyChouma((Integer) deskInfo.get("at_least_gold"),(Integer) deskInfo.get("deskno"), mySiteNo);
		state = 3;
	}
	//是否需要恢复显示
	public boolean isNeedResetDisplay =false;
	/**
	 * 收到恢复显示
	 * @param obj
	 */
	public void setRecvResetDisplay(HashMap data) {
	
		if(!GameApplication.getDzpkGame().restart){
			if(!GameApplication.getDzpkGame().isFirstIn ){
				//非第一次进入游戏界面不需要做恢复显示
				return;
			}
		}else{
			//Log.i("test2", "setRecvResetDisplaysetRecvResetDisplay restart true");
			GameApplication.getDzpkGame().restart =false;
			//GameApplication.getDzpkGame().reset();
		    
		}
	
		GameApplication.getDzpkGame().isFirstIn=false;
	 
		isNeedResetDisplay =true;
		 
		//庄家位置
		int zjIndex=(Byte)data.get("zhuangsite");
		zjIndex=GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(zjIndex);
		GameApplication.getDzpkGame().zjViewManager.setOtherViewVisibility(zjIndex, View.VISIBLE);
		//桌面牌
		ArrayList deskPokes=(ArrayList)data.get("deskPokes");
		if(deskPokes != null){
			int size = deskPokes.size();
			int poke;
			for(int i =0 ;i < size; i++){
				poke=(Byte)deskPokes.get(i);
				//设置桌面的牌值
				GameApplication.getDzpkGame().globalPokeManager.setPokeByIndex(i, poke);
			}
			//设置已有牌个数
			GameApplication.getDzpkGame().globalPokeManager.setPokeSize(size);
		}
		//玩家信息
		ArrayList playerInfo=(ArrayList)data.get("playerInfo");
		if(playerInfo != null){
			int size =playerInfo.size();
			HashMap tempMap;
			int siteNo;
			int index;
			int currbet;
			for (int i = 0; i < size; i++) {
				tempMap = (HashMap)playerInfo.get(i);
//				//总的下注金额
//              int betGold=(Integer)tempMap.get("betgold");
// 				tempMap.get("islose");
// 				tempMap.get("isallin");
				//玩家座位
				siteNo=(Byte)tempMap.get("siteno");
				//得到座位对应的玩家索引
				index=GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
				 //本轮下注金额
				currbet=(Integer)tempMap.get("currbet");
				GameApplication.getDzpkGame().playerChouMaViewManager.setChouMaByIndex(index, currbet);
				HashMap player=(HashMap)sitDownUsers.get(siteNo);
            	if(player == null)continue;
                if(index ==0){
                	Object obj1=player.get("userid");
                	Object obj2 = GameApplication.userInfo.get("user_real_id");
                	if(obj1 !=null && obj1.equals(obj2)){
                	    //表示当前位置是自己
                		//此处还需要知道自己的牌值
                		GameApplication.getDzpkGame().myPoke.setVisibility(View.VISIBLE);
                	}else{
                		GameApplication.getDzpkGame().pokeBackViewManager.setOtherViewVisibility(index, View.VISIBLE);
                	}
                }else{
                	GameApplication.getDzpkGame().pokeBackViewManager.setOtherViewVisibility(index, View.VISIBLE);
                }
 			   
			}
		}
		isNeedResetDisplay =false;
		Log.i("test15","1111 ResetDisplay ResetDisplay ResetDisplay: "+currentSitPlayer.size());
	 
		//执行坐下动作
	    executeSitDownAction();
		if(GameApplication.getDzpkGame().isquick == false){
			Log.i("test15", "ResetDisplay ResetDisplay ResetDisplay");
		    GameApplication.dismissLoading();
		}
	}

	public void executeAction(){
		 
		 //执行发牌动作
		executeFaiPaiAction();
		//执行收桌面牌动作
		executeDeskPokeAction();
		//执行玩家状态动作
		executePlayerState();
		//执行金币刷新动作
		executeRefreshSiteGoldAction();
		
		//执行站起动作
		executeStandUpAction2();
		executeStandUpAction();
		//执行坐下动作
	    executeSitDownAction();
		//执行玩家弃牌动作
		executeGiveUpAction();
		//执行下注动作
		executeXiaZhuAction();
		//执行游戏结束动作
		executeGameOverAction();
		//执行礼物动作
		executeGiftIconAction();
		//执行播入礼物动作
		executePlayGiftIconAction();
	}

	

	
}
