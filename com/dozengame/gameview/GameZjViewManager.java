package com.dozengame.gameview;

 
import android.util.Log;
import android.view.View;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.PlayerDialog;
/**
 * 庄家视图管理
 * @author hewengao
 *
 */
public class GameZjViewManager{

 
	PlayerDialog context;
	//int[][] pondViewPoint = {{285,188},{432,188},{579,188},{285,188},{168,270},{695,270},{280,321},{425,321},{574,321}};
	static final GameZJView [] zjViews= new GameZJView[9];
	int index =-1;
	public GameZjViewManager(PlayerDialog context) {
		 
		this.context=context;
		initZJViews();
	}
	
	private void initZJViews(){
		for(int i=0; i<9;i++){
			zjViews[i]=new GameZJView(context.getContext(),i);
			context.frameLayout.addView(zjViews[i]);
		}
	}
	/**
	 * 设置可见性
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
 
		if(index >=0){
			reset();
			this.index = index;
		    zjViews[index].setVisibility(visibility);
		}
	}

	public void reset() {
	    if(index >=0){
	    	zjViews[index].setVisibility(View.INVISIBLE);
	    }
	}

	int currentVisible=-1;
	/**
	 * 保存当前状态
	 */
	public void saveCurrentState() {
		currentVisible =-1;
		for(int i =0; i< 9;i++){
			if(zjViews[i].getVisibility() == View.VISIBLE){
				 zjViews[index].setVisibility(View.INVISIBLE);
				 currentVisible =i;
				 break;
			}
		}
	}
	/**
	 *
	 * 恢复当前状态
	 *  
	 */
	public void backCurrentState() {
		 
		 if(currentVisible >-1){
			index=GameApplication.getDzpkGame().playerViewManager.getPlayerIndexBak(currentVisible);
			zjViews[index].setVisibility(View.VISIBLE);
		 }
		 currentVisible=-1;
	}
	
	public void destroy(){
		Log.i("test19", "GameZjViewManager destroy");
		for(int i=0; i<9;i++){
			if(zjViews[i] != null){
			 zjViews[i].destroy();
			 zjViews[i] =null;
			}
		}
 
		context =null;
	}
}
