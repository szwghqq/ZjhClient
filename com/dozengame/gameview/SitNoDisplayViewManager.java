package com.dozengame.gameview;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;

public class SitNoDisplayViewManager{
	
	DzpkGameActivityDialog context;
	static final SitNoDisplayView [] sitNumberViews= new SitNoDisplayView[9];
	Bitmap bitbg =null;
	public SitNoDisplayViewManager(DzpkGameActivityDialog context) {
		 
		this.context=context;
		bitbg=GameBitMap.getGameBitMap(GameBitMap.GAME_BGNUMBER);
		initViews();
	}
	
	private void initViews(){
		for(int i=0; i<9;i++){
			sitNumberViews[i]=new SitNoDisplayView(context.getContext(),i,bitbg);
			context.frameLayout.addView(sitNumberViews[i]);
		}
	}
	/**
	 * 视图的可见性
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		if(sitNumberViews[index] !=null){
			sitNumberViews[index].setVisibility(visibility);
		}
	}
 
   /**
	 * 释放资源
	 */
	public void destroy(){
		Log.i("test19", "SitNoDisplayViewManager destroy");
		for(int i=0; i<9;i++){
			if(sitNumberViews[i] != null){
				sitNumberViews[i].destroy();
				sitNumberViews[i]=null;
			}
		}
		GameUtil.recycle(bitbg);
		 
	}
		
   public void setViewVisibility(int visibility){
	   for(int i = 0 ;i < 9;i++){
		   if(GameApplication.getDzpkGame().playerViewManager.isSited(i)){
	          setOtherViewVisibility(i,visibility);
		   }else{
			 setOtherViewVisibility(i,View.INVISIBLE);
		   }
	   }
   }
	 
}
