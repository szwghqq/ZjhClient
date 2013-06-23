package com.dozengame;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dozengame.gameview.GameJsqViewManager;
import com.dozengame.music.MediaManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
 
 
/**
 * ��Ϸ���ͼ
 * @author hewengao
 *
 */
public class DzpkGameActivity extends Activity{
	final static  String tag ="test2";
	public static boolean isDestroy =true;
	public FrameLayout frameLayout;
	int deskType =-1;
	Bitmap currBgBitmap=null;
	PlayerDialog dialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//��ֹ�ֻ�����
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  
		//��ʼ������
		GameUtil.initGift(this);
		setContentView(R.layout.gamemain1);
		activityState=4;
		frameLayout=(FrameLayout)findViewById(R.id.mainLayout);
		setGameDeskBackImg(1);
		//��Ӽ�ʱ��
//		addJsqView();
//		dialog = new PlayerDialog(this,R.style.dialog);
//		dialog.show();
		loadIng();
		//todo
		//1.�����˷�����������Ϣ
	}
	
	/**
	 * ������Ϸ���ӱ���
	 * @param deskType
	 */
	public void setGameDeskBackImg(int type){
		if(this.deskType == type)return;
		this.deskType =type;
		GameUtil.recycle(currBgBitmap);
		switch(deskType){
		case 1:
			currBgBitmap = GameBitMap.getGameBitMapByName(DzpkGameActivity.this, GameBitMap.MAIN_DESK1);
			//mainLayout.setBackgroundResource(R.drawable.main_desk1);
			break;
		case 2:
			currBgBitmap = GameBitMap.getGameBitMapByName(DzpkGameActivity.this, GameBitMap.MAIN_DESK2);
			//mainLayout.setBackgroundResource(R.drawable.main_desk2);
			break;
		case 3:
			currBgBitmap = GameBitMap.getGameBitMapByName(DzpkGameActivity.this, GameBitMap.MAIN_DESK3);
			//mainLayout.setBackgroundResource(R.drawable.main_desk3);
			break;
		default:
			currBgBitmap = GameBitMap.getGameBitMapByName(DzpkGameActivity.this, GameBitMap.MAIN_DESK1);
			//mainLayout.setBackgroundResource(R.drawable.main_desk1);
			break;
		}
		frameLayout.setBackgroundDrawable(new BitmapDrawable(currBgBitmap));
	}
 
	 
	public GameJsqViewManager  jsqViewManager;
	/**
	 * ��Ӽ�ʱ����ͼ
	 */
	private void addJsqView(){
		jsqViewManager = new GameJsqViewManager(this);
	}
    public void destroy() {
    	Log.i("test19", "DzpkGameActivity destroy");
    	if(frameLayout != null){
    		frameLayout.removeAllViews();
    	}
        frameLayout =null;
        if(	jsqViewManager != null){
        	jsqViewManager.destroy();
        }
		jsqViewManager = null;
	
		
	}
    short activityState =0;
	 /**
	  * ����
	  */
	protected void onDestroy() {
			super.onDestroy();
			Log.i(tag, "onDestroy");
			Log.i("test17", "prev destroy "+this.hashCode());
			GameApplication.testNetCun();
			GameUtil.recycle(currBgBitmap);
			currBgBitmap =null;
			activityState=-1; 
			destroy();
			System.gc();
			Log.i("test17", "next destroy ");
			GameApplication.testNetCun();
			isDestroy =true;
	}
	//����Loading
		public void loadIng(){
			//��������
			HwgCallBack callback = new HwgCallBack() {
	 
				public void CallBack(Object... obj) {
					HwgCallBack callback = new HwgCallBack(){

						@Override
						public void CallBack(Object... obj) {
						}
					};
					 GameUtil.openMessageDialog(DzpkGameActivity.this, GameUtil.EXITMSG, callback);
				}
			};
			GameApplication.showLoading(DzpkGameActivity.this,callback,40000);
		}
	
	/**
	 * �û��ɽ���
	 */
	protected void onResume() {
		super.onResume();
		activityState =0;
		Log.i(tag,"onResume");
		GameApplication.currentActivity =this;
		MediaManager.getInstance().playMedia(MediaManager.mame2);
		//init();
		//load();
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		 activityState=-2;
		Log.i(tag,"onStop");
		
		 
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		 activityState=1;
		Log.i(tag,"onStart");
	}
	public boolean restart = false;
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		restart =true;
		 activityState=2;
		Log.i(tag,"onRestart");
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 activityState=3;
		 Log.i(tag,"onPause");
//		 removeListener();
//		 if(cnetThread !=null && !cnetThread.isInterrupted()){
//			 cnetThread.interrupt();
//			 cnetThread=null;
//		 }
		 //reset();
		 //jsqViewManager.setStop();
	}
	Toast toast=null;
	 
	/**
	 * �յ��Լ����������
	 * 
	 * @param data
	 */
	public void setRecvBestPokes(HashMap data) {
		String weight=(String)data.get("weight");//����
		ArrayList pokes =  (ArrayList)data.get("pokes");//��ϵ���
		if(toast !=null)toast.cancel();
		weight = GameUtil.getPokeWeight(Integer.valueOf(weight.substring(0, 1)));
		toast = Toast.makeText(this, weight, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER,0,35);
   		toast.show();
	}
 
}