package com.dozengame.gameview;

import android.util.Log;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;

/**
 * 游戏全下动画管理
 * @author hewengao
 *
 */
public class GameAllInViewManager {
	DzpkGameActivityDialog context;
	static final GameAllInView [] allInViews= new GameAllInView[9];
	public GameAllInViewManager(DzpkGameActivityDialog context) {
		 
		this.context=context;
		initViews();
	}
	
	private void initViews(){
		for(int i=0; i<9;i++){
			allInViews[i]=new GameAllInView(context.getContext(),i);
			context.frameLayout.addView(allInViews[i]);
		}
	}
	/**
	 * 视图的可见性
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		if(allInViews[index] !=null){
			allInViews[index].setVisibility(visibility);
		}
	}
	 
	/**
	 * 启用动画
	 * @param index
	 */
	 public void startAnim(int index){
		 if(allInViews[index] !=null){
			 allInViews[index].startAnim();
		 }
	 }
	   /**
		 * 释放资源
		 */
		public void destroy(){
			Log.i("test19", "GameAllInViewManager destroy");
			for(int i=0; i<9;i++){
				if(allInViews[i] != null){
				   allInViews[i].destroy();
				   allInViews[i]=null;
				}
			}
			// allInViews =null;
		}
}
