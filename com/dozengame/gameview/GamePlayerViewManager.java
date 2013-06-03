package com.dozengame.gameview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.dozengame.GameApplication;
import com.dozengame.music.MediaManager;
import com.dozengame.net.pojo.DJieSuan;
import com.dozengame.net.pojo.Gift;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskSingleManager;
import com.dozengame.util.GameUtil;

/**
 * 游戏玩家相关信息视图 包含 等级,形象图,带入金额,牌型等信息
 * 
 * @author hewengao
 * 
 */
public class GamePlayerViewManager extends View {

	// static HashMap users = new HashMap();
	// 用户昵称坐标
	// private int[][] nickPoint = { { 230, 200 }, { 140, 200 }, { 30, 160 },
	// { 30, 70 }, { 140, 20 }, { 330, 20 }, { 430, 70 }, { 430, 160 },
	// { 330, 200 } };
	// // 玩家筹码坐标
	// private int[][] userChoumaPoint = { { 220, 270 }, { 130, 270 },
	// { 20, 230 }, { 20, 140 }, { 130, 90 }, { 320, 90 }, { 420, 140 },
	// { 420, 230 }, { 320, 270 } };
	// // 玩家牌型坐标
	// private int[][] userPokeWeightPoint = { { 220, 280 }, { 130, 280 },
	// { 20, 170 }, { 20, 150 }, { 130, 100 }, { 320, 100 }, { 420, 150 },
	// { 420, 240 }, { 320, 280 } };
	// // 玩家图像坐标
	// int[][] playerPoint9 = { { 405, 331 }, { 180, 331 }, { 17, 323 },
	// { 17, 85 }, { 243, 15 }, { 592, 15 }, { 825, 85 }, { 825, 323 },
	// { 641, 331 } };
	// // 顺时钟玩家图像坐标
	// int[][] playerPointAnim0 = { { -225, 0 }, { -163, -8 }, { 0, -238 },
	// { 226, -70 }, { 349, 0 }, { 233, 70 }, { 0, 238 }, { -174, 8 },
	// { -236, 0 } };
	// // 逆时钟玩家图像坐标
	// int[][] playerPointAnim1 = { { 236, 0 }, { 225, 0 }, { 163, 8 },
	// { 0, 238 }, { -226, 70 }, { -349, 0 }, { -233, -70 }, { 0, -238 },
	// { 184, -8 } };
	static final int playerW = 136;
	static final int playerH = 184;
	public int index = -1;
	// 玩家图像坐标
	static final int[][] playerPoint14 = { { 425, 380 }, { 200, 380 },
			{ 37, 333 }, { 17, 120 }, { 223, 25 }, { 572, 15 }, { 805, 90 },
			{ 825, 303 }, { 661, 370 } };

	// 逆时钟方向转
	// 玩家图像坐标
	static final int[][] playerPoint1 = { { 405 + 34, 385 }, { 180 + 34, 385 },
			{ 17 + 17, 323 + 10 }, { 17, 100 + 23 }, { 243 - 17, 0 + 10 },
			{ 592 - 34, 0 }, { 825 - 17, 100 - 10 }, { 825, 323 - 23 },
			{ 641 + 17, 385 - 10 } };
	// 玩家图像坐标
	static final int[][] playerPoint2 = { { 405 + 68, 385 }, { 180 + 68, 385 },
			{ 17 + 34, 323 + 20 }, { 17, 100 + 46 }, { 243 - 34, 0 + 20 },
			{ 592 - 68, 0 }, { 825 - 34, 100 - 20 }, { 825, 323 - 46 },
			{ 641 + 34, 385 - 20 } };
	// 玩家图像坐标
	static final int[][] playerPoint3 = { { 405 + 102, 385 },
			{ 180 + 102, 385 }, { 17 + 51, 323 + 30 }, { 17, 100 + 69 },
			{ 243 - 51, 0 + 30 }, { 592 - 102, 0 }, { 825 - 51, 100 - 30 },
			{ 825, 323 - 69 }, { 641 + 51, 385 - 30 } };
	// 玩家图像坐标
	static final int[][] playerPoint4 = { { 405 + 136, 385 },
			{ 180 + 136, 385 }, { 17 + 68, 323 + 40 }, { 17, 100 + 92 },
			{ 243 - 68, 0 + 30 }, { 592 - 136, 0 }, { 825 - 68, 100 - 40 },
			{ 825, 323 - 92 }, { 641 + 68, 385 - 40 } };

	// 坐下后坐标
	// 玩家图像坐标
	static final int[][] playerPointBak = { { 405, 382 }, { 180, 382 },
			{ 10, 323 }, { 10, 100 }, { 243, 5 }, { 592, 5 }, { 815, 100 },
			{ 815, 323 }, { 641, 382 } };
	static final int[][] playerPoint = { { 405, 392 }, { 180, 392 },
		{ 10, 323 }, { 10, 100 }, { 243, 5 }, { 592, 5 }, { 815, 100 },
		{ 815, 323 }, { 641, 392 } };

	// 顺时钟方向转
	static final int[][] playerPoint5 = { { 405 - 34, 382 },
			{ 180 - 5, 382 - 10 }, { 0, 323 - 30 }, { 0 + 17, 100 - 20 },
			{ 243 + 34, 0 }, { 592 + 60, 0 + 5 }, { 825, 100 + 23 },
			{ 825 - 17, 323 + 10 }, { 641 - 34, 385 } };
	// 玩家图像坐标
	static final int[][] playerPoint6 = { { 405 - 68, 382 },
			{ 180 - 10, 382 - 20 }, { 0, 323 - 60 }, { 0 + 34, 100 - 40 },
			{ 243 + 68, 0 }, { 592 + 80, 0 + 15 }, { 825, 100 + 46 },
			{ 825 - 34, 323 + 20 }, { 641 - 68, 385 } };
	// 玩家图像坐标
	static final int[][] playerPoint7 = { { 405 - 102, 382 },
			{ 180 - 25, 382 - 30 }, { 0, 323 - 90 }, { 0 + 51, 100 - 60 },
			{ 243 + 102, 0 }, { 592 + 110, 0 + 25 }, { 825, 100 + 69 },
			{ 825 - 51, 323 + 30 }, { 641 - 102, 385 } };
	// 玩家图像坐标
	static final int[][] playerPoint8 = { { 405 - 136, 382 },
			{ 180 - 45, 382 - 40 }, { 0, 323 - 130 }, { 0 + 68, 100 - 80 },
			{ 243 + 138, 0 }, { 592 + 150, 0 + 40 }, { 825, 100 + 92 },
			{ 825 - 68, 323 + 40 }, { 641 - 136, 385 } };

	private int zdCount = 0;// 转动次数
	public int myPos = -1;// 我的索引位置 其他原家的对应的真实索引x位置为 z=x-myPos z<0则z =z+9
	public int zhunDongValue = 0;// 0:未转动 1:正在转 2:已转
	public boolean mySite = false;// 自己是否坐下
	static final GamePlayerView players[] = new GamePlayerView[9];
	static final Paint pt = new Paint();

	/**
	 * 初始化玩家视图
	 */
	public void initPlayerView() {
		for (int i = 0; i < 9; i++) {
			players[i] = new GamePlayerView(i,this);
		}
	}

	/**
	 * 指定位置的玩家是否显示
	 * 
	 * @param index
	 * @return
	 */
	public boolean getPlayerDisplayByIndex(int index) {
		return players[index].isDisplay;
	}

	public GamePlayerViewManager(Context context) {
		super(context);
		initPlayerView();
		pt.setColor(Color.WHITE);

	}

	/**
	 * 玩家站起，移除玩家
	 * 
	 * @param siteNo
	 */
	public void removeSiteNoPlayer(ArrayList<Integer> listSiteNo) {

		while (listSiteNo.size() > 0) {
			Integer siteNo = listSiteNo.remove(0);
			
			if (GameApplication.getDzpkGame().gameDataManager.siteList != null) {
				Byte obj = siteNo.byteValue();
				GameApplication.getDzpkGame().gameDataManager.siteList.remove(obj);
			}
			int index = getPlayerIndex(siteNo);
			GameApplication.getDzpkGame().jsqViewManager.setStop(index);
			Log.i("test10","remove siteNo: "+siteNo +" index: "+index);
			players[index].setDisplay(false);
			players[index].setOldPos(-1);
			players[index].setPos(index);
			players[index].clearCacheBitmap();
			players[index].giveUpState(false);
			// 当前玩家的小牌隐藏
			GameApplication.getDzpkGame().pokeBackViewManager
					.setOtherViewVisibility(index, View.INVISIBLE);
			//设置当前玩家的牌不可见
			GameApplication.getDzpkGame().otherPokeViewManager.setOtherViewVisibility(index, View.INVISIBLE);
			GameApplication.getDzpkGame().jsqViewManager.setStop(index);
			if (index == 0 && mySite) {
				// 将自已置为未坐下
				mySite = false;
				zhunDongValue = 0;
				players[index].setSelf(false);
				GameApplication.getDzpkGame().myPoke
						.setVisibility(View.INVISIBLE);
				// 表示自己站起,座位可见
				// GameApplication.getDzpkGame().sitView.setVisibility(View.VISIBLE);
				// 设置站起按钮不可见
				GameApplication.getDzpkGame().standButton.setVisibility(View.INVISIBLE);
				// 设置快速开始按钮
				GameApplication.getDzpkGame().gameBottomView
						.addQuickStartButton();
			}
		}

		draw();
	}

	/**
	 * 收到座位金币刷新
	 * 
	 * @param data
	 */
	public void setRecvRefreshGold(int siteNo, int holdGold) {

		int index = getPlayerIndex(siteNo);
		players[index].setBottomInfo(holdGold + "");
		if(players[index].isGiveUp){
		Log.i("test15", "setRecvRefreshGold index:"+index +" gold: "+holdGold);
		}
		players[index].draw(true);
		//draw();
	}

	/**
	 * 用户坐下
	 */
	public void sitDown(ArrayList<HashMap> currentSitPlayer) {

		// 需要恢复显示
		if (GameApplication.getDzpkGame().gameDataManager.isNeedResetDisplay){
			Log.i("test15", "需要恢复显示");
			int size = currentSitPlayer.size();
			HashMap tempUser   =null;
			for(int i =0; i<size;i++){
				tempUser = currentSitPlayer.get(0);
				Integer siteNo = (Integer) tempUser.get("siteno");
				Object userId =  tempUser.get("userId");
				System.out.println("siteNo: "+siteNo +" userid: "+userId.toString());
			}
			return;
		}
		HashMap tempUser;
		GamePlayerView tempGpv = null;
		Object obj = GameApplication.userInfo.get("user_real_id");

		while (currentSitPlayer.size() > 0) {
			tempUser = currentSitPlayer.remove(0);
			Integer siteNo = (Integer) tempUser.get("siteno");
			if (mySite && zhunDongValue == 2) {
				// 已经转动过了
				int x = getPlayerIndex(siteNo);

				tempGpv = players[x];
			} else {

				if (tempUser.get("userid").equals(obj)) {
					// 是否快速开始进来
					if (GameApplication.getDzpkGame().isquick) {
						SitView.siteIndex = getPlayerIndex(siteNo);
						tempGpv = players[SitView.siteIndex];
					} else if (GameApplication.getDzpkGame().gameBottomView.quickClick) {
						GameApplication.getDzpkGame().gameBottomView.quickClick = false;
						SitView.siteIndex = getPlayerIndex(siteNo);
						tempGpv = players[SitView.siteIndex];
						 
					} else {
						// int x=getPlayerIndex(siteNo);
						int site = getSiteNo(SitView.siteIndex);
						if (site != siteNo) {

							SitView.siteIndex = getPlayerIndex(siteNo);
						}
						tempGpv = players[SitView.siteIndex];
					}
					 

					GameApplication.getDzpkGame().gameBottomView
							.removeQuickView();
					// 座位隐藏
					GameApplication.getDzpkGame().sitView
							.setVisibility(View.INVISIBLE);
					// 表示自己未坐下
					if (!mySite) {
						mySite = true;
						zhunDongValue = 0;
					}
					// 是自己
					myPos = siteNo - 1;

					tempGpv.setSelf(true);

				} else {
					// 没有转动过且非自己
					int x = getPlayerIndex(siteNo);

					tempGpv = players[x];
					tempGpv.setSelf(false);
				}

			}
			tempGpv.setOldPos(siteNo);
			tempGpv.setDisplay(true);

		}
		// 是否快速开始
		if (mySite && GameApplication.getDzpkGame().isquick) {
			
			GameApplication.getDzpkGame().isquick = false;
			// 保存状态
			GameApplication.getDzpkGame().saveCurrentState();
			if (SitView.siteIndex > 0) {
				// 需要将自己放置到正对发牌员
				if (SitView.siteIndex > 0) {
					if (SitView.siteIndex < 5) {
						zdCount = SitView.siteIndex;
						// 用户自己坐下后转动用户直到自己所处位置为正对发牌员,逆时钟方向
						for (int j = 0; j < zdCount; j++) {
							GamePlayerView temp = players[0];
							for (int i = 1; i < 9; i++) {
								players[i - 1] = players[i];
								if (players[i - 1] != null) {
									players[i - 1].setPos(i - 1);
								}
							}
							if (temp != null) {
								temp.setPos(8);
							}
							players[8] = temp;
						}

					} else {
						// 用户自己坐下后转动用户直到自己所处位置为正对发牌员, 顺时钟方向
						zdCount = 9 - SitView.siteIndex;
						for (int j = 0; j < zdCount; j++) {
							GamePlayerView temp = players[8];
							for (int i = 8; i > 0; i--) {
								players[i] = players[i - 1];
								if (players[i] != null) {
									players[i].setPos(i);
								}
							}
							isrun = 0;
							if (temp != null) {
								temp.setPos(0);
							}
							players[0] = temp;
						}

					}

				}

			}
			draw();
			// 坐下后 恢复状态
			GameApplication.getDzpkGame().backCurrentState();
			// 自己坐下,设置站起按钮可见
			GameApplication.getDzpkGame().standButton.setVisibility(View.VISIBLE);
			// 设置转动玩毕
			zhunDongValue = 2;
			GameApplication.getDzpkGame().gameDataManager.executeAction();
			GameApplication.dismissLoading();
		} else if (zhunDongValue == 0 && mySite) {
			GameApplication.dismissLoading();
			// 表示自己坐下时不是正对发牌员的位置，需要转动到正对发牌员位置
			// 转桌前需要保存玩家当前的状态,转后恢复
			// 保存状态
			// GameApplication.getDzpkGame().saveCurrentState();
			TaskSingleManager.getInstance().execute(new TaskExecutorAdapter() {
				public int executeTask() throws Exception {
					new MyThread().run();
					return 0;
				}

			});

		} else {
			draw();
		}
	}

	/**
	 * 判断指定位置是否有人坐下了
	 * 
	 * @param index
	 * @return
	 */
	public boolean isSited(int index) {
		boolean result = false;
		if (players[index].isDisplay) {
			result = true;
		}
		return result;
	}

	// 控制重绘
	boolean drawRun = true;

	/**
	 * 重绘
	 */
	public void draw() {
		drawRun = false;
		handler.sendEmptyMessage(-1);
		// this.postInvalidate();
	}

	Canvas canvas = null;

	/**
	 * 重写绘制
	 */
	protected void onDraw(Canvas canvas) {

		if (isNeedDrawPlayerView) {
			// 需要重新绘制玩家形象
			isNeedDrawPlayerView = false;
			this.canvas = canvas;
			for (int i = 0; i < 9; i++) {
				if (players[i] != null) {
					players[i].draw(this);
				}
			}
		} else {
			for (int i = 0; i < 9; i++) {
				if (players[i] != null) {
					drawPlayerView(canvas, players[i], i);
				}
			}
		}
	}

	int isrun = 0;
	boolean isNeedDrawPlayerView = false;

	public void drawPlayerView(GamePlayerView playerView, int n) {
		drawPlayerView(canvas, playerView, n);
	}

	/**
	 * 绘制用户图像
	 * 
	 * @param canvas
	 * @param nick
	 * @param n
	 */
	private void drawPlayerView(Canvas canvas, GamePlayerView playerView, int n) {
		int siteX = 0;
		int siteY = 0;
		switch (isrun) {
		case 0:
			siteX = playerPoint[n][0];// 座位坐标x
			siteY = playerPoint[n][1];// 座位坐标y
			break;
		case 1:
			siteX = playerPoint1[n][0];// 座位坐标x
			siteY = playerPoint1[n][1];// 座位坐标y
			break;
		case 2:
			siteX = playerPoint2[n][0];// 座位坐标x
			siteY = playerPoint2[n][1];// 座位坐标y
			break;
		case 3:
			siteX = playerPoint3[n][0];// 座位坐标x
			siteY = playerPoint3[n][1];// 座位坐标y
			break;
		case 4:
			siteX = playerPoint4[n][0];// 座位坐标x
			siteY = playerPoint4[n][1];// 座位坐标y
			break;

		case 5:
			siteX = playerPoint5[n][0];// 座位坐标x
			siteY = playerPoint5[n][1];// 座位坐标y
			break;
		case 6:
			siteX = playerPoint6[n][0];// 座位坐标x
			siteY = playerPoint6[n][1];// 座位坐标y
			break;
		case 7:
			siteX = playerPoint7[n][0];// 座位坐标x
			siteY = playerPoint7[n][1];// 座位坐标y
			break;
		case 8:
			siteX = playerPoint8[n][0];// 座位坐标x
			siteY = playerPoint8[n][1];// 座位坐标y
			break;

		}
		if (playerView.isDisplay() && playerView.getCacheBitmap() != null) {
		
		
			  canvas.drawBitmap(playerView.getCacheBitmap(), siteX - 20, siteY,pt);
			  playerView.destroyCacheBitmapPrev();
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == -1) {
				postInvalidate();
			} else if (msg.what == 2) {
				GameApplication.getDzpkGame().saveCurrentState();
			} else {
				if (msg.what == 1) {
					// 坐下后 恢复状态
					GameApplication.getDzpkGame().backCurrentState();
					// 自己坐下,设置站起按钮可见
					GameApplication.getDzpkGame().standButton
							.setVisibility(View.VISIBLE);
				}
				// 设置转动玩毕
				zhunDongValue = 2;
				GameApplication.getDzpkGame().gameDataManager.executeAction();
			}

		}
	};

	public void sendMsg(int flag) {
		handler.sendEmptyMessage(flag);
	}

	public class MyThread implements Runnable {

		// public MyThread() {
		// // this.start();
		// }

		@Override
		public void run() {
			Log.i("test", "start zhuang dong");
			draw();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sendMsg(2);
			zhunDongValue = 1;
			if (SitView.siteIndex > 0) {

				if (SitView.siteIndex < 5) {
					zdCount = SitView.siteIndex;
					// 用户自己坐下后转动用户直到自己所处位置为正对发牌员,逆时钟方向
					yszZhuanDong();
				} else {
					// 用户自己坐下后转动用户直到自己所处位置为正对发牌员, 顺时钟方向
					zdCount = 9 - SitView.siteIndex;
					xszZhuanDong();
				}

			}
			sendMsg(1);
		}
	}

	/**
	 * 逆时钟方向转动
	 */
	public void yszZhuanDong() {
		if (zdCount == 0) {
			return;
		}
		int sleepTime = 20;
		for (int j = 0; j < zdCount; j++) {

			isrun = 1;
			draw();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// isrun=2;
			// draw();
			// try {
			// Thread.sleep(sleepTime);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			isrun = 3;
			draw();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// isrun = 4;
			// draw();
			// try {
			// Thread.sleep(sleepTime);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			GamePlayerView temp = players[0];
			for (int i = 1; i < 9; i++) {
				players[i - 1] = players[i];
				if (players[i - 1] != null) {
					players[i - 1].setPos(i - 1);
				}
			}
			isrun = 0;
			if (temp != null) {
				temp.setPos(8);
			}
			players[8] = temp;
			if (j == zdCount - 1) {
				// 表示需要重新初始化玩家信息
				isNeedDrawPlayerView = true;
			}
			draw();
			try {
				System.gc();
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 顺时钟方向转动就坐
	 */
	public void xszZhuanDong() {
		if (zdCount == 0) {
			return;
		}
		int sleepTime = 30;
		for (int j = 0; j < zdCount; j++) {

			isrun = 5;
			draw();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// isrun=6;
			// draw();
			// try {
			// Thread.sleep(sleepTime);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			isrun = 7;
			draw();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// isrun = 8;
			// draw();
			// try {
			// Thread.sleep(sleepTime);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			GamePlayerView temp = players[8];

			for (int i = 8; i > 0; i--) {
				players[i] = players[i - 1];
				if (players[i] != null) {
					players[i].setPos(i);
				}
			}
			isrun = 0;
			if (temp != null) {
				temp.setPos(0);
			}
			players[0] = temp;
			if (j == zdCount - 1) {
				// 表示需要重新初始化玩家信息
				isNeedDrawPlayerView = true;
			}
			draw();
			try {
				System.gc();
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 获取指定位置玩家的座位
	 * 
	 * @param x
	 *            玩家的索引,得到座位号
	 */
	public int getSiteNo(int x) {
		int old = x;
		if (myPos > 0) {
			x = x + myPos;
			x = x % 9;
		}

		return x + 1;
	}

	/**
	 * 获取指定位置玩家的索引
	 * 
	 * @param siteNo
	 *            座位号
	 */
	public int getPlayerIndex(int siteNo) {

		int x = siteNo - 1;
		if (myPos > 0) {
			x = x - myPos;
			if (x < 0) {
				x = x + 9;
			}
		}

		return x;
	}

	/**
	 * 获取指定位置玩家的索引
	 * 
	 * @param siteNo
	 *            座位号
	 */
	public int getPlayerIndexBak(int oldIndex) {
		Log.i("test4", "getPlayerIndexBak  oldIndex: " + oldIndex + " myPos: "
				+ myPos);
		int x = 0;
		if (SitView.siteIndex > 0) {
			if (SitView.siteIndex < 5) {
				// 逆时钟方向
				// zdCount
				x = oldIndex - zdCount;
				if (x < 0)
					x = x + 9;
			} else {
				// 顺时钟方向
				// zdCount
				x = oldIndex + zdCount;
				if (x > 8) {
					x = x - 9;
				}
			}
		}
		Log.i("test4", "getPlayerIndexBak  return x: " + x);
		return x;
	}

	/**
	 * 用于自己站起时，恢复其他玩家索引位置
	 * 
	 * @param siteNo
	 *            座位号
	 */
	public int getBackPlayerIndex(int siteNo) {

		int x = siteNo - 1;
		if (myPos > 0) {
			if (myPos < 5) {
				x = x + myPos;
			} else {
				x = x - myPos;
				if (x < 0) {
					x += 9;
				}
			}
		}

		return x;
	}

	/**
	 * 绘制玩家牌型
	 */
	public void drawPlayerPokeType() {
		HashMap m_pokeWeight = DJieSuan.m_pokeWeight;
		if (m_pokeWeight != null) {
			Iterator it = m_pokeWeight.entrySet().iterator();
			Entry temp;
			int siteNo;
			int index;
			int pokeType = 1;
			String pokeTypeName;
			while (it.hasNext()) {
				temp = (Entry) it.next();
				siteNo = (Integer) temp.getKey();
				index = getPlayerIndex(siteNo);
				pokeTypeName = (String) temp.getValue();
				if (pokeTypeName.length() > 0) {
					pokeType = Integer.valueOf(pokeTypeName.substring(0, 1));
				}
				players[index].setTopInfo(GameUtil.getPokeWeight(pokeType));
				players[index].draw(true);
			}
		}
	}

	int prevIndex = -1;

	/**
	 * 设置玩家状态
	 * 
	 * @param index
	 * @param type
	 */
	public void setPlayerState(int index, int state) {
		if (state == 3)
			return;
		//大小盲，弃牌
		if (prevIndex != -1 && state != 8 && state != 9) {
			//Log.i("test11", "prevIndex state: "+players[prevIndex].getState()+" current state: "+state);
			if (players[prevIndex].getState() != 8
					&& players[prevIndex].getState() != 9 && players[prevIndex].getState() != 6) {
			//	Log.i("test13", "prevIndex state: "+players[prevIndex].getState());
				players[prevIndex].reSetTopInfo();
			}

		}
		prevIndex = index;
		//if(players[index].getState() != 6){
		if(players[index].getState() != state){
		  players[index].setPlayerState(state,this);
		}
		//}
	}

	/**
	 * 设置玩家状态
	 * 
	 * @param index
	 * @param type
	 */
	public void setPlayerStateBySiteNo(int siteNo, int state) {
		int index = getPlayerIndex(siteNo);
		setPlayerState(index, state);
		if (state == 6) {
			//播放弃牌音效
			MediaManager.getInstance().playSound(MediaManager.fold4);
			if (index == 0 && mySite) {
				// 自己的牌需要半透明状
				GameApplication.getDzpkGame().myPoke.giveUpState();
				
				//GameApplication.getDzpkGame().myPoke.setVisibility(View.INVISIBLE);
			} else {
				// 玩家的小背牌消失
				GameApplication.getDzpkGame().pokeBackViewManager.setOtherViewVisibility(index, View.INVISIBLE);
				
			}
			//设置玩家图象,礼物,VIP半透明,
			players[index].giveUpState(true);
			//draw();
			//GameApplication.getDzpkGame().pokeBackViewManager.setOtherViewVisibility
		}else if(state ==7){
			//播放不下注过牌音效
			MediaManager.getInstance().playSound(MediaManager.check2);
		}
	}

	/**
	 * 设置玩家状态
	 * 
	 * @param index
	 * @param type
	 */
	public void setPlayerState(ArrayList playerInfoState, boolean isGameOver) {
		if (playerInfoState != null) {
			HashMap temp = null;
			int size = playerInfoState.size();
			Byte siteNo;
			Byte state;
			int index;
			for (int i = 0; i < size; i++) {
				temp = (HashMap) playerInfoState.get(i);
				siteNo = (Byte) temp.get("site");// 座位号
				state = (Byte) temp.get("state");// 状态号

				index = getPlayerIndex(siteNo);

				if (isGameOver == false
						&& state == 0
						&& (prevIndex == index
								|| players[index].getState() == 8 || players[index]
								.getState() == 9))
					continue;

				// 0:默认状态 1:等待 2:未准备 4:离开5:收到面板6:购买筹码
				switch (state) {
				case 0:
					if (isGameOver) {
						state = 10;
					} else {
						state = 14;
					}
					break;
				case 1:
					state = 10;
					break;
				case 2:
					state = 10;
					break;
				case 4:
					state = 13;
					break;
				case 5:
					state = 14;
					break;
				case 6:
					state = 11;
					break;
				}
				//setPlayerState(index,state);
				if(players[index].getState() != state){
				  players[index].setPlayerState(state,this);
				}
                
			}

		}
	}

	/**
	 * 只有当自己坐下时才会调用自己的状态，在购买筹码之前判断
	 * @return
	 */
	public int getPlayerState(){
		return players[0].getState();
	}
	

	// 自带礼物
	public void setGiftIcon(ArrayList<HashMap> list) {
		if (list == null)
			return;
		//int size = list.size();

		HashMap data = null;
		while (list.size() > 0) {
			data = list.remove(0);
			if(data == null)continue;
			Byte siteNo = (Byte) data.get("siteno");
			//if(siteNo ==null)continue;
			Integer giftId = (Integer) data.get("giftid");
			Log.i("test4", "siteNo: "+siteNo+"  giftId: "+giftId);
			int index = getPlayerIndex(siteNo);

			Gift gift = GameUtil.getGiftById(this.getContext(), giftId);

			if (gift != null) {
				Log.i("test4", "gift is not null");
				players[index].setLiWuImgPath(gift.getImgPath());
			}else{
				Log.i("test4", "gift is null");
			}
			gift = null;
			giftId = null;
			siteNo = null;
			data.clear();
			data = null;
		}
		GameApplication.getDzpkGame().playerViewManager.draw();
	}

	// 赠送礼物
	public void setPlayGiftIcon(ArrayList<HashMap> list) {
		if (list == null)
			return;

		HashMap data = null;
		while (list.size() > 0) {
			data = list.remove(0);
			Byte siteNo = (Byte) data.get("tositeno");
			Integer giftId = (Integer) data.get("giftid");
			int index = getPlayerIndex(siteNo);

			Gift gift = GameUtil.getGiftById(this.getContext(), giftId);

			if (gift != null) {
				players[index].setLiWuImgPath(gift.getImgPath());
			}
			gift = null;
			giftId = null;
			siteNo = null;
			data.clear();
			data = null;
		}
		GameApplication.getDzpkGame().playerViewManager.draw();
	}

	public void destroy() {
		Log.i("test19", "GamePlayerViewManager destroy");
		for (int i = 0; i < 9; i++) {
			if (players[i] != null) {
				players[i].destroy();
			}
		}
		this.destroyDrawingCache();
	}
//	/**
//	 * 重置玩家操作状态
//	 */
//	public void resetPlayerState() {
//		for (int i = 1; i < 9; i++) {
//			players[i].reSetTopInfo();
//		}
//	}
	public void reset() {
		for (int i = 0; i < 9; i++) {
			if (players[i] != null) {
				//players[i].reSetTopInfo();
				players[i].setState(14);
				players[i].giveUpState(false);
			}
		}
		draw();
	}

}
