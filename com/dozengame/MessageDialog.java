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
public class MessageDialog extends Dialog {
	
	TextView message;
	ImageView sureImg;
	Bitmap bg;
	Bitmap game_button2;
	Bitmap game_button2s;
	HwgCallBack callback;
	public MessageDialog(Context context, int dialog,String msg,HwgCallBack callback,String sureText) {
		super(context,dialog);
		init(msg,callback);
		TextView tv=(TextView)findViewById(R.id.sureTv);
		tv.setText(sureText);
	}

	public MessageDialog(Context context, int dialog,String msg,HwgCallBack callback) {
		super(context,dialog);
		init(msg,callback);
	}
	private void init(String msg,HwgCallBack callback){
		this.setContentView(R.layout.messageview);
		this.callback=callback;
		init();
		if(msg ==null)msg="";
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
	private void init(){
		message=(TextView)findViewById(R.id.message);
		sureImg=(ImageView)findViewById(R.id.sureImg);
		
		try {
			  game_button2 = BitmapFactory.decodeStream(getContext().getAssets().open("game_button2.png"));
			  game_button2s = BitmapFactory.decodeStream(getContext().getAssets().open("game_button2s.png"));
			  sureImg.setImageBitmap(game_button2);
			 
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
						sureClick();
					}
				}
				return true;
			}
			
		};
 
		sureImg.setOnTouchListener(listener);
	}
	/**
	 * 执行确定事件
	 */
	private void sureClick(){
		if(callback != null){
		 callback.CallBack();
		}
		dismiss();
	}
	private void gc(){
		 GameUtil.recycle(game_button2);
		 GameUtil.recycle(game_button2s);
		 GameUtil.recycle(bg);
		 message =null;
		 sureImg =null;
	}
//	@Override
//	public void onBackPressed() {
//		dismiss();
//	}
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
	 // return super.onKeyDown(keyCode, event);
	      
	} 
	public boolean onKeyUp(int keyCode, KeyEvent event){
//		 if( keyCode == KeyEvent.KEYCODE_HOME){
//             return true;
//		  }
		 return true;
	}
 
//    public void onAttachedToWindow() {
//    	 
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//        super.onAttachedToWindow();
//    }
}
