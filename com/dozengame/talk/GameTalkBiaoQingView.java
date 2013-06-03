package com.dozengame.talk;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.View;

import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskSingleManager;
import com.dozengame.util.GameUtil;

/**
 * 表情
 * 
 * @author hewengao
 * 
 */
public class GameTalkBiaoQingView extends View {
	 
	static final int[][] point = { { 400, 425 }, {175, 425 }, { 7,355 }, { 7, 135 },
			{ 240, 40 }, { 585, 40 }, { 810, 130 }, { 810, 355 },
			{ 635, 425 } };
 
	Bitmap cacheBitmap;
	int pos;
	 
	public GameTalkBiaoQingView(Context context, int pos) {
		super(context);
		this.pos = pos;
 
	  // this.setVisibility(View.INVISIBLE);
	}
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(this.getVisibility() == View.VISIBLE){
			 if(cacheBitmap != null && !cacheBitmap.isRecycled()){
		         canvas.drawBitmap(cacheBitmap, point[pos][0],point[pos][1],null);
			 }
		}
	}
	Thread currentThread=null;
	/**
	 * 启用动画
	 */
	public void startAnim(int biaoQingId) {
		  GameUtil.recycle(cacheBitmap);
	      biaoQingId = biaoQingId-1000;
	      if(biaoQingId > 0){
		    	  String s="";
	    	    if(biaoQingId < 10){
			    	  s+="0"+biaoQingId;
			    }else{
			    	s+=""+biaoQingId;
			    }
		    	InputStream is =null;
				try {
					is = this.getContext().getAssets().open("biaoqing/emot"+s+".png");
					cacheBitmap=BitmapFactory.decodeStream(is);
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						if(is != null){
						 is.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			 
	      }
	      try{
				if(currentThread != null &&!currentThread.isInterrupted()){
					currentThread.interrupt();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			currentThread=new Thread(){
				public void run() {
					try{
						handler.sendEmptyMessage(0);
						Thread.sleep(2000);
						handler.sendEmptyMessage(1);
					}catch(Exception e){
						
					}
				}
			};
			currentThread.start();
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what ==0){
				 setVisibility(View.VISIBLE);
				 postInvalidate();
			}else{
				setVisibility(View.INVISIBLE);
			}
		};
	};
 
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if(View.INVISIBLE == visibility){
			 GameUtil.recycle(cacheBitmap);
		}
	};
	   /**
	 * 释放资源
	 */
	public void destroy(){
		destroyBitmap();
		this.destroyDrawingCache();
		 
 
	}
	
	private void destroyBitmap(){
 
		 GameUtil.recycle(cacheBitmap);
		 cacheBitmap =null;
	}
	 
}
