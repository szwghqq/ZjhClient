package com.dozengame.gameview;

import android.util.Log;

import com.dozengame.DzpkGameActivityDialog;

/**
 * 游戏全下动画管理
 * @author hewengao
 *
 */
public class GameAllInViewManager1 {
	DzpkGameActivityDialog context;
	static final GameAllInView1 [] allInViews= new GameAllInView1[9];
	public GameAllInViewManager1(DzpkGameActivityDialog context) {
		 
		this.context=context;
		initViews();
	}
	
	private void initViews(){
		for(int i=0; i<9;i++){
			allInViews[i]=new GameAllInView1(context.getContext(),i);
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
