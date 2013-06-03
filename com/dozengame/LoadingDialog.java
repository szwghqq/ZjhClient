package com.dozengame;
import java.io.IOException;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;

import com.dozengame.util.GameUtil;

/**
 * 加载进度窗口
 * @author hewengao
 * 
 */
public class LoadingDialog extends Dialog {
	ImageView loadingImg;
	boolean run = true;
	Bitmap wait_bg;
	Bitmap wait_w0;
	Bitmap wait_w1;
	Bitmap wait_w2;
	Bitmap wait_w3;
	Bitmap wait_w4;
	Bitmap wait_w5;
	Bitmap wait_w6;
	Bitmap wait_w7;
	HwgCallBack callback=null;
	long timeOut=-1;
	public LoadingDialog(Context context,int dialog) {
		super(context,dialog);
		callback =null;
		init();
	}
	public LoadingDialog(Context context,int dialog,HwgCallBack callback,int timeOut) {
		super(context,dialog);
		this.callback =callback;
		this.timeOut=timeOut;
		init();
	}
	public void init(){
		setContentView(R.layout.loadingview);
		initBitmap();
		loadingImg = (ImageView) findViewById(R.id.loadingImg);
		if(this.timeOut !=-1){
		 loadingImg.setImageBitmap(wait_w0);
		}
		Window w=this.getWindow();
		w.setBackgroundDrawable(new BitmapDrawable(wait_bg));
		this.setOnDismissListener(new OnDismissListener(){
			@Override
			public void onDismiss(DialogInterface dialog) {
				 destroy();
			}
			
		});
	}
	/**
	 * 初始化图片
	 */
	private void initBitmap(){
		try {
			wait_w0 = BitmapFactory.decodeStream(this.getContext().getAssets().open(
			"wait_w0.png"));
			wait_w1 = BitmapFactory.decodeStream(this.getContext().getAssets().open(
			"wait_w1.png"));
			wait_w2 = BitmapFactory.decodeStream(this.getContext().getAssets().open(
			"wait_w2.png"));
			wait_w3= BitmapFactory.decodeStream(this.getContext().getAssets().open(
			"wait_w3.png"));
			wait_w4= BitmapFactory.decodeStream(this.getContext().getAssets().open(
			"wait_w4.png"));
			wait_w5 = BitmapFactory.decodeStream(this.getContext().getAssets().open(
			"wait_w5.png"));
			wait_w6 = BitmapFactory.decodeStream(this.getContext().getAssets().open(
			"wait_w6.png"));
			wait_w7 = BitmapFactory.decodeStream(this.getContext().getAssets().open(
			"wait_w7.png"));
			wait_bg = BitmapFactory.decodeStream(this.getContext().getAssets().open(
			"wait_bg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
		run = false;
	}
	long startTime=0;
	public void start() {
		try {
			 int count = 0;
			if(timeOut ==-1){
				while (run) {
					count++;
					if (count % 8 == 0) {
						count = 0;
					}
				    handler.sendEmptyMessage(count);
					Thread.sleep(100);
				}
			 
			}else{
				startTime =System.currentTimeMillis();
				long endTime =0;
				while (run) {
					count++;
					if (count % 8 == 0) {
						count = 0;
					}
				    handler.sendEmptyMessage(count);
					Thread.sleep(100);
					endTime=System.currentTimeMillis();
					if((endTime-startTime) >= timeOut){
						handler.sendEmptyMessage(-1);
						if(callback != null){
							callback.CallBack();
						}
						return;
					}
				}
			}
			handler.sendEmptyMessage(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
 
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			 
			super.handleMessage(msg);
			changLoadImg(msg.what);
		}
	};
	
	private void changLoadImg(int what){
		switch (what) {
		case 0:
			loadingImg.setImageBitmap(wait_w0);
			break;
		case 1:
			loadingImg.setImageBitmap(wait_w1);
			break;
		case 2:
			loadingImg.setImageBitmap(wait_w2);
			break;
		case 3:
			loadingImg.setImageBitmap(wait_w3);
			break;
		case 4:
			loadingImg.setImageBitmap(wait_w4);
			break;
		case 5:
			loadingImg.setImageBitmap(wait_w5);
			break;
		case 6:
			loadingImg.setImageBitmap(wait_w6);
			break;
		case 7:
			loadingImg.setImageBitmap(wait_w7);
			
			break;
		default:
			dismiss();
            break;
		}
		 
	}
	/**
	 * 释放资源
	 */
	private void destroy(){
		
		 GameUtil.recycle(wait_bg);
		 GameUtil.recycle(wait_w0);
		 GameUtil.recycle(wait_w1);
		 GameUtil.recycle(wait_w2);
		 GameUtil.recycle(wait_w3);
		 
		 GameUtil.recycle(wait_w4);
		 GameUtil.recycle(wait_w5);
		 GameUtil.recycle(wait_w6);
		 GameUtil.recycle(wait_w7);
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
