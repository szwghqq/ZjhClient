package com.dozengame.gameview;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.dozengame.util.GameUtil;
/**
 * 牌型提示
 * @author Administrator
 */
public class ToastPaiXingView extends View {
	Paint pt = new Paint();
	 
	String paiXing=null;//牌型
	RectF rectf;
	public String getPaiXing() {
		return paiXing;
	}
	public void setPaiXing(String paiXing) {
		this.paiXing = paiXing;
	}
	public ToastPaiXingView(Context context) {
		super(context);
		//165, 34
		rectf = new RectF(410,325,530,359);
		this.setVisibility(View.INVISIBLE);
	}
	 
	@Override
	protected void onDraw(Canvas canvas) {
	 
		 
		if(View.VISIBLE == this.getVisibility() && paiXing != null && paiXing.length() >0){
		    pt.setStyle(Style.FILL);
		    pt.setColor(Color.BLACK);
			canvas.drawRoundRect(rectf, 8, 8, pt);
			pt.setColor(Color.WHITE);
			pt.setTextSize(23);
			float w =pt.measureText(paiXing);
			canvas.drawText(paiXing, 410+(120-w)/2, 359-8, pt);
		}
 
	}
 
  
	/**
	 *销毁
	 */
	public void destroy() {
		Log.i("test19", "ToastPaiXingView destroy");
		 pt =null;
		 rectf =null;
		 handler=null;
		 this.destroyDrawingCache();
	}
 
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			 if(msg.what ==1){
				 setVisibility(View.VISIBLE);
			 }else if(msg.what ==0){
				 setVisibility(View.INVISIBLE);
			 }
		}
	};
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if(View.VISIBLE ==visibility){
			postInvalidate();
		}else{
			if(isnew == false)paiXing =null;
		}
	};
	public void reset(){
		isshow = true;
		isnew =false;
		paiXing=null;
	}
	Thread thread=null;
	public void show(){
		 isnew =false;
		
		 Log.i("test16", "接收牌1");
		 try{
			if(thread != null && !thread.isInterrupted()){
				thread.interrupt();
			}
			if(paiXing == null){
				 Log.i("test16", "牌型为空");
				 isshow =false;
				return;
			}
		 }catch(Exception e){
			 
		 }
		 isshow =true;
		Log.i("test16", "接收牌2");
		thread = new Thread(){
			@Override
			public void run() {
				try{
				 Log.i("test16", "接收牌3");
				 Thread.sleep(280);
				 handler.sendEmptyMessage(1);
				 Thread.sleep(2000);
				 handler.sendEmptyMessage(0);
				 if(isnew == false) paiXing =null;
				}catch(Exception e){
					
				}
			}
		};
		thread.start();
	}
	boolean isnew= false;
	boolean isshow= true;
	public void setRecvBestPokes(HashMap data) {
		  isnew =true;
		 String weight=(String)data.get("weight");//牌型
		 paiXing = GameUtil.getPokeWeight(Integer.valueOf(weight.substring(0, 1)));
		 Log.i("test16", "paiXing: "+paiXing);
		 if(isshow == false){
			 show();
		 }
	}
	 
}
