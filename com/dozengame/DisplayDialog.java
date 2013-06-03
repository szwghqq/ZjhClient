package com.dozengame;
import java.io.IOException;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.dozengame.util.GameUtil;

public class DisplayDialog extends Dialog {

	Bitmap hall_menu_but;
	Bitmap hall_menu_buts;
	ImageView imgcontrol1;
	ImageView imgcontrol2;
	LinearLayout layout1;
	LinearLayout layout2;
	DzpkGameRoomActivity context;
	public DisplayDialog(DzpkGameRoomActivity context, int theme) {
		super(context, theme);
		this.context=context;
 	    this.setContentView(R.layout.displaysetview);
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
		 
        if(x>=0 && x<=510){
        	if(y >=0 && y<=230){
        		return false;
        	}
        }
        dismiss();
    	return true;
    }
	private void initBitmap() {
		try {
			hall_menu_but = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_menu_but.png"));
			hall_menu_buts = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_menu_buts.png"));
			final Bitmap   bg = BitmapFactory.decodeStream(this.getContext().getAssets().open("hall_menu_bg.png"));
			Window w = this.getWindow();
			w.setBackgroundDrawable(new BitmapDrawable(bg));
			WindowManager.LayoutParams lp = w.getAttributes();
			lp.x = 230;
			lp.y = -100;
			 this.setOnDismissListener(new OnDismissListener(){
		    	   @Override
		    	public void onDismiss(DialogInterface dialog) {
		    		 gc();
		    		 GameUtil.recycle(bg);
		    	}
		       });
			//w.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化控件
	 */
	private void initData() {
		
		imgcontrol1 = (ImageView) findViewById(R.id.imgcontrol1);
		
		SharedPreferences uiState  = this.getContext().getApplicationContext().getSharedPreferences("displayeSet",Context.MODE_WORLD_WRITEABLE);
		if(uiState.getInt("empty", -1) ==-1){
			imgcontrol1.setImageBitmap(hall_menu_but);
		}else{
			imgcontrol1.setImageBitmap(hall_menu_buts);
		}
		
		imgcontrol2 = (ImageView) findViewById(R.id.imgcontrol2);
		if(uiState.getInt("full", -1) ==-1){
			imgcontrol2.setImageBitmap(hall_menu_but);
		}else{
			imgcontrol2.setImageBitmap(hall_menu_buts);
		}
		 
		
//		//layout1 = (LinearLayout) findViewById(R.id.layout1);
//		//layout2 = (LinearLayout) findViewById(R.id.layout2);
//		int h=layout1.getMeasuredHeight();
//		int w=layout1.getMeasuredWidth();
//		LayoutParams  lp=(LayoutParams)layout2.getLayoutParams();
//	
//		System.out.println("h : "+h+"  w: "+w);
//		h=lp.height;
//		w=lp.width;
//		System.out.println("h : "+h+"  w: "+w);
		//layout1.removeAllViews();
		//layout1.addView(new DisplaySetView(this.getContext()));
		
		//layout2.removeAllViews();
		//layout2.addView(new DisplaySetView(this.getContext()));
		addListener();
	}
	
	private void addListener(){
		imgcontrol1.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				saveDisplaySetValue(0);
			}
			
		});
		imgcontrol2.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				saveDisplaySetValue(1);
			}
		});
//		imgcontrol1.setOnTouchListener(new OnTouchListener(){
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				final int action =event.getAction();
//			    if(action == 0){
//			    	imgcontrol1.setImageBitmap(hall_menu_buts);
//			    	return true;
//			    }else if(action ==MotionEvent.ACTION_MOVE){
//			    	//移动
//			    	
//			    	return true;
//			    }else if(action ==1){
//			    	imgcontrol1.setImageBitmap(hall_menu_but);
//			    	return true;
//			    }
//				return false;
//			}
//    		
//    	});
//		
//		imgcontrol2.setOnTouchListener(new OnTouchListener(){
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				final int action =event.getAction();
//				 
//			    if(action == 0){
//			    	imgcontrol1.setImageBitmap(hall_menu_buts);
//			    	return true;
//			    }else if(action ==MotionEvent.ACTION_MOVE){
//			    	//移动
//			    	float x= event.getX();
//			    	float y= event.getY();
//			    
//			    	return true;
//			    }
//			    else if(action ==1){
//			    	imgcontrol1.setImageBitmap(hall_menu_but);
//			    	return true;
//			    }
//				return false;
//			}
//    		
//    	});
	}
	/**
	 * 保存显示设置信息
	 * @param type
	 */
	private void saveDisplaySetValue(int type){
		 
		//需要保存用户信息
		SharedPreferences uiState  = this.getContext().getApplicationContext().getSharedPreferences("displayeSet",Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = uiState.edit();
		int value=-1;
		switch(type){
		case 0:
			//隐藏空房间否
			  value =uiState.getInt("empty", -1);
			if(value ==-1){
				value =0;
				imgcontrol1.setImageBitmap(hall_menu_buts);
			}else{
				imgcontrol1.setImageBitmap(hall_menu_but);
				value=-1;
			}
			editor.putInt("empty", value);
			break;
		case 1:
			//隐藏人满房间否
			value =uiState.getInt("full", -1);
			if(value ==-1){
				value =0;
				imgcontrol2.setImageBitmap(hall_menu_buts);
			}else{
				value=-1;
				imgcontrol2.setImageBitmap(hall_menu_but);
			}
			editor.putInt("full", value);
			break;
		}
		editor.commit(); 
		context.executeDispalySetEvent();
	}
	
	private void gc(){
		GameUtil.recycle(hall_menu_but);
		GameUtil.recycle(hall_menu_buts);
	}
 

}
