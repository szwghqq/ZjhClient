package com.dozengame;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dozengame.gameview.GameMyPokeView;
import com.dozengame.gameview.GamePlayerViewManager;
import com.dozengame.gameview.GameZjViewManager;
import com.dozengame.gameview.PokeBackViewManager;
import com.dozengame.util.GameUtil;
/**
 * 玩家信息窗口
 * @author Administrator
 *
 */
public class PlayerDialog extends Dialog {

	DzpkGameActivity context;
	public FrameLayout frameLayout;
	 public PlayerDialog(final DzpkGameActivity context,int theme) {
			super(context,theme);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  
			this.context =context;
			setContentView(R.layout.gamemain1);
			 final ColorDrawable drawable =new ColorDrawable(0);
			 this.getWindow().setBackgroundDrawable(drawable);
			frameLayout=(FrameLayout)findViewById(R.id.mainLayout);
			//添加庄家
			addZjViewManager();
			//其他玩家小背牌
			addPokeBackViewManager();
			//添加玩家视图
			addPlayerViewManager();
			//添加我的牌
			addMyPoke();
			this.setOnDismissListener(new OnDismissListener(){
				@Override
				public void onDismiss(DialogInterface dialog) {
				    destroy();
				    if(GameUtil.isNetError == false){
				        context.finish();
				    }else{
				    	GameUtil.isNetError=false;
				    }
				}
			});
			 
			this.setOnShowListener(new OnShowListener(){
 
				public void onShow(DialogInterface dialog) {
                    Log.i("test12", "onShowonShowonShow");
                    FaiPaiDialog   gameDialog = new FaiPaiDialog(context,R.style.dialog,PlayerDialog.this);
            	    gameDialog.show();
 
				}
			});
	 }
	 public GameZjViewManager zjViewManager;
	/**
	 * 添加庄家
	 */
	private void addZjViewManager(){
		  zjViewManager = new GameZjViewManager(this);
	}
	 public GamePlayerViewManager playerViewManager=null;
	/**
	 * 添加游戏玩家视图
	 */
	private void addPlayerViewManager(){
	    playerViewManager=new GamePlayerViewManager(this.getContext());
		frameLayout.addView(playerViewManager);
	}
	 public PokeBackViewManager pokeBackViewManager;
		/**
		 * 其他玩家的小牌
		 */
		private void addPokeBackViewManager(){
			pokeBackViewManager = new PokeBackViewManager(this);
		}
		public GameMyPokeView myPoke;
		/**
		 * 添加自己的牌
		 */
		private void addMyPoke(){
			myPoke =new GameMyPokeView(this.getContext());
			frameLayout.addView(myPoke);
		}
	
		  public void destroy() {
				Log.i("test19", "PlayerDialog destroy");
			  if(frameLayout != null){
		    		frameLayout.removeAllViews();
		    	}
		        frameLayout =null;
				 
				if (pokeBackViewManager != null) {
					pokeBackViewManager.destroy();
				}
				pokeBackViewManager = null;
 
				if (myPoke != null) {
					myPoke.destroy();
				}
				myPoke = null;
		        if(zjViewManager != null){
	        	 zjViewManager.destroy();
		        }
		        zjViewManager =null;
				if (playerViewManager != null) {
					playerViewManager.destroy();
				}
				playerViewManager =null;
				
				pokeBackViewManager = null;
				playerViewManager =null;
				myPoke = null;
				 
		 }
		  GameDataManager gameDataManager;
		  public void sendMsg(Message msg,GameDataManager gameDataManager){
			  this.gameDataManager =gameDataManager;
			  handler2.sendMessage(msg);
		  }
		
		  Handler handler2 = new Handler() {
				public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
					if (!Thread.currentThread().isInterrupted()) {
						 
						 if(gameDataManager==null){
							 return;
						 }
						 switch(msg.what){
							case DzpkGameActivityDialog.recvPlayerInfo:
								gameDataManager.setPlayerInfoState((ArrayList)msg.obj); 
								break;
						 }
					}
				}};  
				
//				/**
//				 * 侦听按键事件
//				 */
//				public boolean onKeyDown(int keyCode, KeyEvent event) {
//				       if (keyCode == KeyEvent.KEYCODE_BACK  ) {
//		                     dismiss();
//		                  
//				          return false;
//				       }
// 
//				  return super.onKeyDown(keyCode, event);
//				} 
				 
}
