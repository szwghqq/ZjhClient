package com.dozengame.talk;

import java.util.HashMap;

import android.util.Log;

import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.util.GameUtil;

/**
 * 游戏聊天表情管理
 * @author hewengao
 *
 */
public class GameTalkBiaoQingViewManager {
	DzpkGameActivityDialog context;
	static final GameTalkBiaoQingView [] talkBiaoQingViews= new GameTalkBiaoQingView[9];
	public GameTalkBiaoQingViewManager(DzpkGameActivityDialog context) {
		 
		this.context=context;
		initViews();
	}
	
	private void initViews(){
		for(int i=0; i<9;i++){
			talkBiaoQingViews[i]=new GameTalkBiaoQingView(context.getContext(),i);
			context.frameLayout.addView(talkBiaoQingViews[i]);
		}
	}
	/**
	 * 视图的可见性
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		if(talkBiaoQingViews[index] !=null){
			talkBiaoQingViews[index].setVisibility(visibility);
		}
	}
	 
	/**
	 * 启用动画
	 * @param index
	 */
	 public void startAnim(int index,int biaoQingId){
		 if(talkBiaoQingViews[index] !=null){
			 talkBiaoQingViews[index].startAnim(biaoQingId);
		 }
	 }
	   /**
		 * 释放资源
		 */
		public void destroy(){
			Log.i("test19", "GameTalkBiaoQingView destroy");
			for(int i=0; i<9;i++){
				if(talkBiaoQingViews[i] != null){
					talkBiaoQingViews[i].destroy();
					talkBiaoQingViews[i]=null;
				}
			}
			 
		}
		
		 /**
	     * 执行表情
	     * @param obj
	     */
		public void executeBiaoQing(HashMap data) {
		
			Byte siteNo= (Byte)data.get("siteno");
			Integer emotid=(Integer)data.get("emotid");
			Log.i("test18","处理表情: "+emotid);
			if(siteNo>=1){
			  int index=	GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
			  if(index >=0 && index < 9){
			   startAnim(index,emotid);
			  }
			}
		}

}
