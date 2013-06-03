package com.dozengame.gameview;

import java.util.ArrayList;
import java.util.HashMap;

 
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.music.MediaManager;

/**
 * 玩家起始下注筹码管理
 * 
 * @author hewengao
 * 
 */
public class GameXiaZhuViewManager {

	static final int[][] translateAnimPoint = { { -38, -90 }, { -55, -90 }, { 115, -77 },
			{ 115, 73 }, { -115, 135 }, { 35, 135 }, { -187, 75 },
			{ -187, -75 }, { -12, -90 } };
	static final GameXiaZhuView[] xiaZhuChouMaViews = new GameXiaZhuView[9];

	public DzpkGameActivityDialog context;
	// 当前是否正在下注
	public boolean currentXiaZhu = false;
	ArrayList<HashMap> list;
	
	public GameXiaZhuViewManager(DzpkGameActivityDialog context) {

		this.context = context;
		initXiaZhuChouMaViews();
	}

	private void initXiaZhuChouMaViews() {
		for (int i = 0; i < 9; i++) {
			xiaZhuChouMaViews[i] = new GameXiaZhuView(context.getContext(), i);
			context.frameLayout.addView(xiaZhuChouMaViews[i]);
		}
	}

	/**
	 * 设置可见性
	 * 
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index, int visibility) {
		xiaZhuChouMaViews[index].setVisibility(visibility);
	}



	/**
	 * 设置下注筹码
	 * 
	 * @param index
	 * @param money
	 */
	public void setPlayerXiaZhuChouMa(ArrayList<HashMap> list) {
		currentXiaZhu = true;
		this.list = list;
		xiaZhuChouMa();
	}

	/**
	 * 下注筹码
	 */
	private void xiaZhuChouMa() {
		int size = list.size();
		HashMap data;
		if (list != null && list.size() > 0) {
			data = list.remove(0);
			Byte siteNo = (Byte) data.get("siteno");// 座位号
			Integer betGold = (Integer) data.get("betgold");// 总的下注额
			Integer currbet = (Integer) data.get("currbet");// 本轮下注额
			Byte sex = (Byte) data.get("sex"); // 1=男 0=女
			Byte type = (Byte) data.get("type");// 1=下注 2=加注 3=底注 4=梭哈 5=跟注
			int index = GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
			GameApplication.getDzpkGame().playerViewManager.setPlayerState(index, type);
			xiaZhuChouMaViews[index].setMoney(currbet);
			//xiaZhuChouMaViews[index].setVisibility(View.VISIBLE);
			//执行金币刷新动作
			//GameApplication.getDzpkGame().gameDataManager.executeRefreshSiteGoldAction(siteNo);
			translateAnimXiaZhu(xiaZhuChouMaViews[index], index,type);
			 
			if (type == 4) {
				// ALLIn动画
				//GameApplication.getDzpkGame().allInViewManager.startAnim(index);
				GameApplication.getDzpkGame().allInViewManager1.startAnim(index);
			}
		} else {
			// 下注完毕
			currentXiaZhu = false;
			// 可能需要执行翻牌流程动画
			GameApplication.getDzpkGame().gameDataManager
					.executeDeskPokeAction();
			// 可能需要执行游戏结束流程动画
			GameApplication.getDzpkGame().gameDataManager
					.executeGameOverAction();
		}
	}

	/**
	 * 玩家筹码到座位下注筹码
	 * 
	 * @param cmvStart
	 * @param index
	 * @param currGold
	 */
	private void translateAnimXiaZhu(final GameXiaZhuView xiaZhuView,
			final int index,final int type) {

		int x = translateAnimPoint[index][0];
		int y = translateAnimPoint[index][1];
		TranslateAnimation mTranslateAnimation = new TranslateAnimation(0, x,
				0, y);
		// 设置时间持续时间为300 毫秒=0.3秒
		mTranslateAnimation.setDuration(300);
		mTranslateAnimation.setInterpolator(new LinearInterpolator());// 均匀
		mTranslateAnimation.setStartOffset(50);
		mTranslateAnimation.setAnimationListener(new MyAnimationListener(xiaZhuView, index,type));
		xiaZhuView.startAnimation(mTranslateAnimation);
	}

	/**
	 * 动画侦听事件
	 * 
	 * @author hewengao
	 * 
	 */
	private class MyAnimationListener implements AnimationListener {
		GameXiaZhuView view1;// 起始视图
		int index = -1;// 某个座位的索引
        int type =-1;
		public MyAnimationListener(GameXiaZhuView view1, int index,int type) {
			this.view1 = view1;
			this.index = index;
			this.type =type;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			view1.setVisibility(View.INVISIBLE);
			GameApplication.getDzpkGame().playerChouMaViewManager.setChouMaByIndex(index, view1.getMoney());
			// 执行下注
			xiaZhuChouMa();
		}

		public void onAnimationRepeat(Animation animation) {
		}

		public void onAnimationStart(Animation animation) {
			if(type ==5){
				 //播放跟注声音
				 MediaManager.getInstance().playSound(MediaManager.call2);
			}else if(type ==2 || type ==4){
				//播放加注声音
				 MediaManager.getInstance().playSound(MediaManager.raise);
			}
		}

	}
	
	public void destory(){
		Log.i("test19", "GameXizZhuViewManager destroy");
	   for(int i=0; i< 9;i++){
		   if(xiaZhuChouMaViews[i] !=null){
		    xiaZhuChouMaViews[i].destory();
		    xiaZhuChouMaViews[i]=null;
		   }
	   }
	  
		context =null;
		// 当前是否正在下注
        if(list != null){
        	list.clear();
        	list =null;
        }
		
	}

}
