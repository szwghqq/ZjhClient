package com.dozengame;

import java.io.IOException;

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
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.dozengame.util.GameUtil;
/**
 * 消息提示框
 * @author hewengao
 *
 */
public class Message1Dialog extends Dialog {
	
	TextView message;
	ImageView sureImg;
	ImageView cancelImg;
	Bitmap bg;
	Bitmap game_button2;
	Bitmap game_button2s;
	HwgCallBack callback;
	public Message1Dialog(Context context, int dialog,String msg,HwgCallBack callback,String sureText,String cancelText) {
		super(context,dialog);
		init(msg,callback);
		TextView tv=(TextView)findViewById(R.id.sureTv);
		tv.setText(sureText);
		
		TextView canceltv=(TextView)findViewById(R.id.cancelTv);
		canceltv.setText(cancelText);
	}
	
	public Message1Dialog(Context context, int dialog,String msg,HwgCallBack callback,String sureText) {
		super(context,dialog);
		init(msg,callback);
		TextView tv=(TextView)findViewById(R.id.sureTv);
		tv.setText(sureText);
	}
	
	public Message1Dialog(Context context, int dialog,String msg,HwgCallBack callback) {
		super(context,dialog);
		init(msg,callback);
	}
	private void init(String msg,HwgCallBack callback){
		this.callback=callback;
		this.setContentView(R.layout.message1view);
		init();
		message.setText(msg);
		Window w=this.getWindow();
		try {
			bg = BitmapFactory.decodeStream(this.getContext().getAssets().open("msg_bg.png"));
			w.setBackgroundDrawable(new BitmapDrawable(bg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setOnDismissListener(new OnDismissListener(){
			public void onDismiss(DialogInterface dialog) {
			  gc();
			}
			
		});
	}
	boolean isSureClick=false;
	private void init(){
		message=(TextView)findViewById(R.id.message);
		sureImg=(ImageView)findViewById(R.id.sureImg);
		cancelImg=(ImageView)findViewById(R.id.cancelImg);
		try {
			  game_button2 = BitmapFactory.decodeStream(getContext().getAssets().open("game_button2.png"));
			  game_button2s = BitmapFactory.decodeStream(getContext().getAssets().open("game_button2s.png"));
			  sureImg.setImageBitmap(game_button2);
			  cancelImg.setImageBitmap(game_button2);
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		OnTouchListener listener=new OnTouchListener(){

			 
			public boolean onTouch(View v, MotionEvent event) {
				int action =event.getAction();
				ImageView imgV =(ImageView)v;
				if(action ==0){
					//按下
					imgV.setImageBitmap(game_button2s);
				}else if(action ==1){
					//弹起
					imgV.setImageBitmap(game_button2);
					if(imgV == sureImg){
						if(isSureClick ==false){
							isSureClick =true;
						    sureClick();
						}
					}else if(cancelImg == imgV){
						cancelClick();
					}
				}
				return true;
			}
			
		};	
		sureImg.setOnTouchListener(listener);
		cancelImg.setOnTouchListener(listener);
	}
	/**
	 * 执行确定事件
	 */
	private void sureClick(){
		callback.CallBack();
		dismiss();
	}
	/**
	 * 执行取消事件
	 */
    private void cancelClick(){
    	callback.CallBack(-1);
    	dismiss();
	}
    private void gc(){
		 GameUtil.recycle(game_button2);
		 GameUtil.recycle(game_button2s);
		 GameUtil.recycle(bg);
		 message =null;
		 sureImg =null;
		 cancelImg =null;
	}
    /**
	 * 侦听按键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	   //GameUtil.backExit(DzpkGameActivity.this);
	              return false;
	       }
	       if( keyCode == KeyEvent.KEYCODE_HOME){
               return true;
		    }
	       return true;
	  //return super.onKeyDown(keyCode, event);
	} 
	public boolean onKeyUp(int keyCode, KeyEvent event){
//		 if( keyCode == KeyEvent.KEYCODE_HOME){
//            return true;
//		  }
		 return true;
	}
//    public void onAttachedToWindow() {
//    	 
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//        super.onAttachedToWindow();
//    }
//	@Override
//	public void onBackPressed() {
//		dismiss();
//	}
}
