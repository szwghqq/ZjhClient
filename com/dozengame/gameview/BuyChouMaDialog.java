package com.dozengame.gameview;

import java.io.IOException;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dozengame.GameApplication;
import com.dozengame.HwgCallBack;
import com.dozengame.R;
import com.dozengame.net.pojo.DeskInfo;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameUtil;

public class BuyChouMaDialog extends Dialog {

	Bitmap game_button2;
	Bitmap game_button2s;
	ImageView buyImg;
	ImageView cancelImg;
	LinearLayout layout1;
	LinearLayout layout2;
	BuyChouMaView  buyChouMaView;
	TextView minText;//最小值
	TextView maxText;//最大值
	TextView currentChouMaText;//当前购买值
	TextView xianJin;//您的现款
	static final String s ="$";
	static final String xianKan="您的现款: ";
	int buyChouMa=0;
	public HashMap data;
	public BuyChouMaDialog(Context context, int theme,HashMap data) {
		super(context, theme);
 	    this.setContentView(R.layout.buychoumaview);
 	    this.data=data;
 	    initBitmap();
 		initData();
		//this.setContentView(R.layout.displayset);
		//LinearLayout  layout=(LinearLayout)findViewById(R.id.layout);
		//layout.addView(new  DisplaySetView(this.getContext()));
  
	}
    
    public boolean onTouchEvent(MotionEvent event) {
        float x= event.getX();
        float y= event.getY();
        int actionIndex= event.getActionIndex();
        int masked= event.getActionMasked();
		int action = event.getAction();
		System.out.println("action: "+action+"  actionIndex: "+actionIndex+" masked: "+masked);
        if(x>=0 && x<=510){
        	if(y >=0 && y<=230){
        		return false;
        	}
        }
       // dismiss();
    	return false;
    }
	private void initBitmap() {
		try {
			game_button2 = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("game_button2.png"));
			game_button2s = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("game_button2s.png"));
			final Bitmap   bg = BitmapFactory.decodeStream(this.getContext().getAssets().open("game_chg_bg.png"));
			Window w = this.getWindow();
			final BitmapDrawable drawable=new BitmapDrawable(bg);
			w.setBackgroundDrawable(new BitmapDrawable(bg));
			this.setOnDismissListener(new OnDismissListener(){
				public void onDismiss(DialogInterface dialog) {
					destroy();
					GameUtil.recycle(bg);
					GameUtil.recycle(drawable.getBitmap());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化控件
	 */
	private void initData() {
		buyImg = (ImageView) findViewById(R.id.buyImg);
		buyImg.setImageBitmap(game_button2);
		cancelImg = (ImageView) findViewById(R.id.cancalImg);
		cancelImg.setImageBitmap(game_button2);
		
	     layout1 = (LinearLayout) findViewById(R.id.layout1);
	      buyChouMaView= new BuyChouMaView(this);
	     layout1.addView(new BuyChouMaView(this));
	 	 minText =(TextView)findViewById(R.id.min);//最小值
	 	 maxText =(TextView)findViewById(R.id.max);//最大值
	 	 currentChouMaText =(TextView)findViewById(R.id.currentBuyChouMa);//当前购买值
	 	 xianJin =(TextView)findViewById(R.id.xianjin);//您的现款
	 	minText.setText(s+data.get("min").toString());
	 	maxText.setText(s+data.get("max").toString());
	 	xianJin.setText(xianKan+s+data.get("gold").toString());
	 	buyChouMa=(Integer)data.get("defaultgold");
	 	setBuyChouMa(buyChouMa); 
		addListener();
	}
	
	
	private void addListener(){
		cancelImg.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	cancelImg.setImageBitmap(game_button2s);
			    	return true;
			    }else if(action ==1){
			    	cancelImg.setImageBitmap(game_button2);
			    	if(GameApplication.getDzpkGame() != null){
			    		GameApplication.getDzpkGame().sendStandUp();
			    	}
			    	dismiss();
			    	return true;
			    }
				return false;
			}
    		
    	});
		
		buyImg.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
				 
			    if(action == 0){
			    	buyImg.setImageBitmap(game_button2s);
			    	return true;
			    }
			    else if(action ==1){
			    	buyImg.setImageBitmap(game_button2);
			    	buyChouMa();
			    	return true;
			    }
				return true;
			}
    		
    	});
		 
	}
	/**
	 * 购买筹码
	 */
	private void buyChouMa(){	
		if(!GameApplication.getDzpkGame().playerViewManager.mySite){
			
			GameApplication.getDzpkGame().loadIng();
		}
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){

			@Override
			public int executeTask() throws Exception {
				try{
				  //根据索引得到座位号
				 int siteNo=GameApplication.getDzpkGame().playerViewManager.getSiteNo(SitView.siteIndex);
				 
				 DeskInfo deskInfo =GameApplication.getDzpkGame().deskInfo;
				 GameApplication.getSocketService().sendBuyChouma(buyChouMa,deskInfo.getDeskno(),siteNo);
				}catch(Exception e){
					//System.out.println("send buy ChouMa failed: "+e.getMessage());
					e.printStackTrace();
				}finally{
					 if(GameApplication.getDzpkGame().playerViewManager.mySite ){
						 //显示名称
						 GameApplication.getDzpkGame().playerViewManager.setPlayerState(0, 14);
					 }
					dismiss(); 
				}
					
				return 0;
			}
			
			
		});

	}
   
	public void setBuyChouMa(int gold) {
		buyChouMa=gold;
		currentChouMaText.setText(s+buyChouMa);
	}
	
	 /**
	 * 释放资源
	 */
	public void destroy() {
		GameUtil.recycle(game_button2);
		GameUtil.recycle(game_button2s);
		buyChouMaView.destroy();
		buyChouMaView = null;
		data = null;

		game_button2 = null;
		game_button2s = null;
		buyImg = null;
		cancelImg = null;
		layout1 = null;
		layout2 = null;
		buyChouMaView = null;
		minText = null;
		maxText = null;
		currentChouMaText = null;
		xianJin = null;
	}
	/**
	 * 侦听按键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	   //GameUtil.backExit(DzpkGameActivity.this);
	              return false;
	       }
	  return super.onKeyDown(keyCode, event);
	} 
}
