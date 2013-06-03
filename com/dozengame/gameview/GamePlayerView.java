package com.dozengame.gameview;

import java.io.IOException;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.alipay.net.JavaHttp;
import com.dozengame.GameApplication;
import com.dozengame.HwgCallBack;
import com.dozengame.net.pojo.PlayerNetPhoto;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;

/**
 * 游戏玩家视图
 * 
 * @author hewengao
 * 
 */
public class GamePlayerView implements HwgCallBack{

	Bitmap cacheBitmap;
	Bitmap cacheBitmapPrev;
	Bitmap cacheBitmapAlpha;
	Canvas canvas;
	boolean isSelf = false; // true:表示自己 false:其它
	int pos;// 座位号
	Bitmap liWuBitMapBg;// 礼物背景
	Bitmap liWuBitMap;// 礼物
	Bitmap vipBitMap;// Vip
	Bitmap playerBitMap;//玩家形象
	Bitmap gamephotoframe;//形象框架
	int vipLevel = 5; // vip级别 0：无 1：1级 2:2级 以此类推
	String bottomInfo = "";// 底部信息
	String topInfo = "";// 头部信息
	static final Paint paint = new Paint();// 画笔
	static final int[][] liWuPoint = { { 0, 70 }, { 106, 70 } };// 礼物坐标
	static final int[][] vipPoint = { { 0, 10 }, { 108, 10 } };// Vip坐标
	String playerName;
	int handGold = 0;
	boolean isDisplay =false;// 是否显示。显示表示有人坐，否则表示无人
	int oldPos = -1;// 原始座位位置
	HashMap player;
	boolean isGiveUp = false;//是否弃牌
	int userid=1;
	String face;
	boolean exists = false;//原来是否有图片
	GamePlayerViewManager playerManager =null;
	public int getOldPos() {
		return oldPos;
	}

	public void setOldPos(int oldPos) {
		this.oldPos = oldPos;
	}

	public GamePlayerView(int pos,GamePlayerViewManager playerManager) {
		this.pos = pos;
		paint.setColor(Color.WHITE);
		this.playerManager =playerManager;
		canvas = new Canvas();
		 
	}

	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
		this.isInit =false;
		isLoad = false;
        draw(true);
	}
    boolean isLoad = false;
	boolean isInit  =false;
	private void init() {
		if(isInit)return;
		isInit =true;
		GameUtil.recycle(vipBitMap);
		vipBitMap =null;
		GameUtil.recycle(playerBitMap);
		playerBitMap =null;
		HashMap sitDownUsers = GameApplication.getDzpkGame().gameDataManager.sitDownUsers;
		if (sitDownUsers != null) {
			player = (HashMap) sitDownUsers.get(oldPos);
			if (player != null) {
				userid = (Integer)player.get("userid");
				playerName = (String) player.get("nick");
				handGold = (Integer) player.get("handgold");// 手中的筹码
				vipLevel = (Integer) player.get("viplevel");// vip登级
			    face=(String)player.get("face");
				if(face != null){
					if(face.startsWith("http:") || face.startsWith("https:")){
						if(isLoad == false){
							isLoad = true;
							//在本地查询是否已存在
							PlayerNetPhoto	photo = GameUtil.getPlayerPhotoById(GameApplication.currentActivity,userid);
							if(photo != null && photo.getHttpUrl().equals(face)){
									//存在
								createBitmap(photo.getPhotoBytes());
							}else{
								if(photo != null){
									exists= true;
								}
								//不存在需要下载图片
								TaskManager.getInstance().execute(new TaskExecutorAdapter(){
				 
									public int executeTask() throws Exception {
										JavaHttp http= new JavaHttp(face, GamePlayerView.this, "download");
										http.execute();
										return 0;
									}
								});
							}
						}
					}else{
						Bitmap result=GameBitMap.getBitmapByName(GameApplication.getDzpkGame().getContext(), face);
						if(result != null){
						   playerBitMap =GameBitMap.resizeBitmap(result, GameUtil.imgWidth1, GameUtil.imgHeight1);
						   GameUtil.recycle(result);
						} 
					}
				}
				if(playerBitMap ==null || playerBitMap.isRecycled()){
					//使用默认形象
					Bitmap result=GameBitMap.getBitmapByName(GameApplication.getDzpkGame().getContext(), "main_dog.jpg");
					if(result != null){
					 playerBitMap =GameBitMap.resizeBitmap(result, GameUtil.imgWidth1, GameUtil.imgHeight1);
					 GameUtil.recycle(result);
					}
				}
				setBottomInfo(handGold + "");
				setTopInfo(playerName);
				switch (vipLevel) {
				case 1:
					vipBitMap = GameBitMap.getGameBitMap(GameBitMap.GAME_VIP1);
					break;
				case 2:
					vipBitMap = GameBitMap.getGameBitMap(GameBitMap.GAME_VIP2);
					break;
				case 3:
					vipBitMap = GameBitMap.getGameBitMap(GameBitMap.GAME_VIP3);
					break;
				case 4:
					vipBitMap = GameBitMap.getGameBitMap(GameBitMap.GAME_VIP4);
					break;
				case 5:
					vipBitMap = GameBitMap.getGameBitMap(GameBitMap.GAME_VIP5);
					break;
				default:
					vipBitMap =null;
					break;
				}

			}

		}
		//draw();
	}
	 
    public void draw(GamePlayerViewManager playerViewManager){
    	 draw(false);
    	 if(playerViewManager != null){
    		 playerViewManager.drawPlayerView(this,pos);
    	 }
    }
	public void draw(boolean bl) {
		// cacheBitmap=null;
		if (isDisplay == false){
			Log.i("test10", "isDisplay false pos: "+pos);
			return;
		}
        init();
        cacheBitmapPrev =cacheBitmap;
		cacheBitmap = Bitmap.createBitmap(136 + 56, 186, Config.ARGB_8888);
		canvas.setBitmap(cacheBitmap);

		if (isSelf) {
			gamephotoframe =GameBitMap.getGameBitMap(GameBitMap.GAME_PHOTO_FRAME_SELF);
		} else {
			gamephotoframe=GameBitMap.getGameBitMap(GameBitMap.GAME_PHOTO_FRAME);
		}
		canvas.drawBitmap(gamephotoframe, 20, 0,null);
		paint.setTextSize(21.0f);
		paint.setAntiAlias(true);
		// 头部信息的高度
		float topH = paint.descent() - paint.ascent();
		if(isGiveUp){
			topInfo=giveUp;
		}
		// 头部信息的宽度
		float topW = paint.measureText(topInfo);
         
		// 绘制头部信息
		canvas.drawText(topInfo, (136 - topW) / 2 + 20, topH+4, paint);
		 float bottomH =166;
		if(bottomInfo.length()>7){
			 paint.setTextSize(18);
			 bottomH =164;
		}
		
		if(playerBitMap != null &&  !playerBitMap.isRecycled()){
		  canvas.drawBitmap(playerBitMap,35,37,null);
		}
		// 底部信息的高度
		// float bottomH = (paint.descent() + paint.ascent());
		//  Log.i("test4", "descent: "+paint.descent()+"  ascent: "+paint.ascent());
		// float bottomH = -paint.ascent();
		// 底部信息的宽度
		float bottomW = paint.measureText(bottomInfo);
		 
		topW = (136 - bottomW) / 2+28;
//		if(topW < 46){
//			topW =46;
//		}
		// 绘制底部信息
		canvas.drawText(bottomInfo,topW,bottomH, paint);

		// 绘制礼物及VIP级别
		if (pos == 0 || pos == 1 || pos == 4 || pos == 5) {
			// 绘制礼物左边
			drawLiWu(0);
			// 绘制Vip右边
			drawVip(1);
		} else if (pos == 2 || pos == 3) {
			// 绘制礼物右边
			drawLiWu(1);
			// 绘制Vip右边
			drawVip(1);
		} else if (pos == 6 || pos == 7) {
			// 绘制礼物左边
			drawLiWu(0);
			// 绘制Vip左边
			drawVip(0);
		} else if (pos == 8) {
			// 绘制礼物右边
			drawLiWu(1);
			// 绘制Vip左边
			drawVip(0);
		}else{
		
		}
 
		 if(playerManager != null && bl){
			 playerManager.draw();
		 }
		  
	}

	/**
	 * 绘制礼物
	 * 
	 * @param state
	 *            1:右边 0:左边
	 */
	private void drawLiWu(int state) {
		initLiwuImg();
		if(liWuBitMap != null && !liWuBitMap.isRecycled()){
			
			 canvas.drawBitmap(liWuBitMap, liWuPoint[state][0],liWuPoint[state][1], null);
		}else{
			liWuBitMapBg = GameBitMap.getGameBitMap(GameBitMap.GAME_LIWU_BG);
			if (liWuBitMapBg != null) {
			  canvas.drawBitmap(liWuBitMapBg, liWuPoint[state][0],liWuPoint[state][1], null);
			}
		}
	}

	/**
	 * 绘制VIP 1:右边 0:左边
	 * 
	 * @param state
	 */
	private void drawVip(int state) {
		if (vipBitMap != null && !vipBitMap.isRecycled()) {
			canvas.drawBitmap(vipBitMap, vipPoint[state][0],vipPoint[state][1], null);
		}
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
		//draw();
	}

	public Bitmap getLiWuBitMap() {
		return liWuBitMap;
	}

	public void setLiWuBitMap(Bitmap liWuBitMap) {
		this.liWuBitMap = liWuBitMap;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getBottomInfo() {
		return bottomInfo;
	}

	public void setBottomInfo(String bottomInfo) {
		this.bottomInfo = bottomInfo;
	}

	public String getTopInfo() {
		return topInfo;
	}

	public void setTopInfo(String topInfo) {
		//this.topInfo = topInfo;
		try {
		this.topInfo = GameUtil.splitIt(topInfo,8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Bitmap getCacheBitmap() {
		 
		if(isGiveUp){
			if(cacheBitmapAlpha ==null){
			    cacheBitmapAlpha = GameBitMap.getRgbAlphaBitmap(cacheBitmap);
			} 
			return cacheBitmapAlpha;
		}else{
		  return cacheBitmap;
		}
	}
	public void setCacheBitmap(Bitmap bit) {
		  cacheBitmap=bit;
	}
	public void clearCacheBitmap() {
		 GameUtil.recycle(cacheBitmap);
		 GameUtil.recycle(cacheBitmapAlpha);
		 destroyBitmap();
		 player =null;
		 cacheBitmap=null;
		 cacheBitmapAlpha=null;
	}
	final static String xiaZhu ="下注"; 
	final static String jiaZhu ="加注"; 
	final static String diZhu ="底注"; 
	final static String allIn ="全下"; 
	final static String genZhu ="跟注";
	final static String giveUp ="弃牌";
	final static String kanPai="看牌";
	final static String bigBet="大盲注";
	final static String smallBet="小盲注";
	final static String waitNext ="等待下局";
	final static String buyChouMa ="购买筹码";
	final static String operator ="操作中";
	final static String leave ="离开";
	final static String noready ="未准备";
	final static String winJia ="赢家";
	int state = -1;
    public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	/**
     * 设置玩家状态
     * @param state
     * 1=下注 2=加注 3=底注 4=全下 5=跟注 6:弃牌 7:看牌 
     * 8:大盲注 9:小盲注 10:等待下局 11:购买筹码 12:未准备
     * 13:离开 14：操作中 15：赢家
     */
	public void setPlayerState(int state,GamePlayerViewManager playerManager) {
 
		 this.state =state;
		  
		 switch(state){
		 case 1:
			 setTopInfo(xiaZhu);
			 break;
		 case 2:
			 setTopInfo(jiaZhu);
			 break;
		 case 3:
			  setTopInfo(diZhu);
			 break;
		 case 4:
			 setTopInfo(allIn);
			 break;
		 case 5:
			 setTopInfo(genZhu);
			 break;
		 case 6:
			 setTopInfo(giveUp);
			 break;
		 case 7:
			 setTopInfo(kanPai);
			 break;
		 case 8:
			 setTopInfo(bigBet);
			 break;
		 case 9:
			 setTopInfo(smallBet);
			 break;
		 case 10:
			 setTopInfo(waitNext);
			 break;
		 case 11:
			 setTopInfo(buyChouMa);
			 break;
		 case 12:
			 setTopInfo(noready);
			 break;
		 case 13:
			 setTopInfo(leave);
			 break;
		 case 14:
			 setTopInfo(playerName);
			 break;
		 case 15:
			 setTopInfo(winJia);
			 break;
		 }
		 if(state !=6){
		  draw(true);
		 }
		
		 
	}
	/**
	 * 恢复头部信息
	 */
	public void reSetTopInfo(){
		//if(!(state ==6  || state ==11)){
		if(state != 11){
			setTopInfo(playerName);
			draw(true);
		}
	}
	public void destroy(){
		  GameUtil.recycle(cacheBitmap);
		  cacheBitmap =null;
		  destroyBitmap();
		  bottomInfo =null;
		  topInfo = null;
		  playerName =null;
	}
	public void destroyCacheBitmapPrev(){
		if(cacheBitmapPrev != cacheBitmap){
		  GameUtil.recycle(cacheBitmapPrev);
		  //GameUtil.recycle(cacheBitmapAlpha);
		  cacheBitmapPrev =null;
		  cacheBitmapAlpha=null;
		}else{
			Log.i("test17", "不能销毁");
		}
	}
	private void destroyBitmap(){
		//destroyCacheBitmapPrev();
		GameUtil.recycle(gamephotoframe);
		GameUtil.recycle(liWuBitMapBg);
		GameUtil.recycle(liWuBitMap);
		gamephotoframe =null;
		liWuBitMap =null;
		liWuBitMapBg =null;
//		GameUtil.recycle(vipBitMap);
//		GameUtil.recycle(playerBitMap);
	}
   /**
    * 设置礼物
    * @param imgPath
    */
	public void setLiWuImgPath(String imgPath) {
		if(player != null){
			
			player.put("liwuimg", imgPath);
			isDisplay =true;
			draw(true);
			
		}
	}
	private void initLiwuImg(){
		if(player != null){
			String imgPath = (String)player.get("liwuimg");
			if(imgPath != null && imgPath.trim().length() > 0){
				Bitmap temp=null;
				try {
					temp = BitmapFactory.decodeStream(GameApplication.getDzpkGame().getContext().getAssets().open(imgPath));
					liWuBitMap= GameBitMap.resizeBitmap(temp, 59,59);
				    GameUtil.recycle(temp);
				    temp =null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
	
			liWuBitMap =null;
		}
	}
	
	public void giveUpState(boolean give){
		 
		isGiveUp =give;
		if(isGiveUp == false){
			GameUtil.recycle(cacheBitmapAlpha);
			cacheBitmapAlpha=null;
		}
		draw(true);
	}
    private void createBitmap(byte [] bytes){
    	 Bitmap result= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    	 playerBitMap =GameBitMap.resizeBitmap(result, GameUtil.imgWidth1, GameUtil.imgHeight1);
		 GameUtil.recycle(result);
		 draw(true);
    }
	@Override
	public void CallBack(Object... obj) {
		if(obj == null || obj.length!=1 || !(obj[0] instanceof byte[])) {
			return;
		}
		byte[] response = (byte[])obj[0];
			try {
				createBitmap(response);
				GameUtil.insertPlayerNetPhoto(GameApplication.currentActivity, userid, face, 0, response, exists);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				 
			}
	}
	 
}

