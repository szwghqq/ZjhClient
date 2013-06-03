package com.dozengame.gameview;

 
import android.util.Log;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.music.MediaManager;
import com.dozengame.util.GameBitMap;
/**
 * 计时器信息
 * @author hewengao
 *
 */
public class GameJsqViewManager{

	 
	 DzpkGameActivity context;
	 
	final static GameJsqView [] jsqViews= new GameJsqView[9];
	private int currnetIndex=-1;
	public GameJsqViewManager(DzpkGameActivity context) {
		 
		 this.context=context;
		initPokeBackViews();
	}
	 
	
	private void initPokeBackViews(){
		for(int i=0; i<9;i++){
			jsqViews[i]=new GameJsqView(context,i);
			context.frameLayout.addView(jsqViews[i]);
		}
	}
	 /**
	  * 开启计时器
	  */
	private void start(int timeOut,int delay){
		if(currnetIndex>=0){
		  jsqViews[currnetIndex].start(timeOut,delay);
		}
	}
	/**
	 * 停止计时器
	 */
    public void setStop(){
    	if(currnetIndex>=0){
    	 jsqViews[currnetIndex].stop();
    	}
    	 
    }
    /**
     * 判断计时器是否还在运行
     * @return
     */
    public boolean isStop(){
    	if(currnetIndex>=0){
       	  if(jsqViews[currnetIndex].isRun){
       		  jsqViews[currnetIndex].stop();
       		  return false;
       	  }else{
       		  return true;
       	  }
       	}
    	return true;
    }
    /**
	 * 停止计时器
	 */
    public void setStop(int timeOut,int delay){
    	if(currnetIndex>=0){
    	  jsqViews[currnetIndex].stops(timeOut,delay);
    	}
    	 
    }
    /**
	 * 停止计时器
	 */
    public void setStop(int index){
    	 if(index >=0){
	    	 jsqViews[index].stop();
    	 }
    	 
    }
    //是否已操作
    boolean oper = true;
    public void setIsOperation(boolean oper){
    	this.oper =oper;
    }
	 /**
	  * 控制计时器显示位置
	  * @param siteNo
	  * @param timeOut 剩于时间
	  * @param delay  总时间
	  */
	public void setJsqSiteNo(int siteNo,int timeOut,int delay) {
		
		 int tempIndex = GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
		 if(GameApplication.getDzpkGame().playerViewManager.getPlayerDisplayByIndex(tempIndex) ==false){
			
			 Log.i("test15", "没有显示 siteNo: "+siteNo+" timeOut: "+timeOut+" delay: "+delay+" tempIndex: "+tempIndex);
//			 setStop(currnetIndex);
//			 return;
		 }
		// Log.i("test13", "siteNo: "+siteNo+" timeOut: "+timeOut+" delay: "+delay+" tempIndex: "+tempIndex);
		 if(tempIndex == currnetIndex){
			 if(oper == false){
				// Log.i("test13", "jsqsiteno 重复.");
				 return;
			 }
//			 else{
//				 Log.i("test13", "jsqsiteno 不重复.");
//			 }
			 setStop(timeOut,delay);
		 }else{
			 
			 setStop(currnetIndex);
			 currnetIndex =tempIndex;
			 start(timeOut,delay);
		 }
		 if(tempIndex ==0 && GameApplication.getDzpkGame().playerViewManager.mySite){
			 //播放轮到自己的声音
			 MediaManager.getInstance().playSound(MediaManager.turnstart);
			
		 }
		 //当前未操作
		 oper = false;
	}

	public void destroy() {
		Log.i("test19", "GameJsqView destroy");
		for(int i=0; i<9;i++){
			if(jsqViews[i] != null){
			jsqViews[i].destroy();
			jsqViews[i]=null;
			}
		}
	 
		context =null;
	 
	}
	
//  /**
//  * 按制计时器显示位置
//  * @param siteNo
//  */
//	public void setJsqSiteNo(int siteNo) {
//		 setStop();
//		 currnetIndex= siteNo-1;
//
//		if(context.playerViewManager.myPos >0){
//			  currnetIndex = currnetIndex-context.playerViewManager.myPos;
//			if(currnetIndex < 0){
//				currnetIndex =currnetIndex+9;
//			}
//		}
//		start(20000,0);
//	}
	
	 /**
	  * 按制计时器显示位置
	  * @param siteNo
	  * @param timeOut 剩于时间
	  * @param delay  总时间
	  */
//	public void setJsqSiteNoBak(int siteNo,int timeOut,int delay) {
//		 boolean isSame =false;
//		 int tempIndex =siteNo-1;
//		 if(tempIndex == currnetIndex){
//			
//			 isSame = true;
//			 start(timeOut,delay);
//			 setStop();
//		 }else{
//			 setStop();
//			 currnetIndex= tempIndex;
//				if(context.playerViewManager.myPos >0){
//					  currnetIndex = currnetIndex-context.playerViewManager.myPos;
//					if(currnetIndex < 0){
//						currnetIndex =currnetIndex+9;
//					}
//				}
//			 start(timeOut,delay);
//		 }
//	}
}
