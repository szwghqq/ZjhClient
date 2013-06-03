package com.dozengame.talk;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;

/**
 * 游戏聊天提示界面
 * 
 * @author hewengao
 * 
 */
public class GameTalkChatView extends View {
	//绘制动画起点坐标
	static final int[][] point = { { 412, 370 }, {175, 415 }, { 18,260 }, { 18, 125 },
			{ 245, 30 }, { 475, 70 }, { 698, 130 }, {698, 260 },
			{ 523, 415 },{ 5,502} };
 
	Bitmap cacheBitmap;
	int pos;
	String chatMsg="";
 
	Paint pt = new Paint();
	public GameTalkChatView(Context context, int pos) {
		super(context);
		this.pos = pos;
		pt.setColor(Color.BLACK);
		pt.setAntiAlias(true);
		if(pos < 5){
			  cacheBitmap = GameBitMap.getGameBitMap(GameBitMap.GAME_TALK_LEFT3);
	    }else if(pos ==9){	    	
	    	 cacheBitmap = GameBitMap.getGameBitMap(GameBitMap.GAME_TALK_PANGGUANG); 
	    }else{
	    	 cacheBitmap = GameBitMap.getGameBitMap(GameBitMap.GAME_TALK_RIGHT3);
	    }
		this.setVisibility(View.INVISIBLE);
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(this.getVisibility() == View.VISIBLE){
		   
		    int len=0;
		    try {
			  len = chatMsg.getBytes("GBK").length;
			
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		  
		  
		    canvas.drawBitmap(cacheBitmap, point[pos][0], point[pos][1], null);
		    float x =0,y=0;
		    if(pos ==9){
		    	if(len <= 20){
					pt.setTextSize(30);
				}else{
					pt.setTextSize(20);
				}
		    	  float w= pt.measureText(chatMsg);
		    	  float h = pt.descent() - pt.ascent();
		    	x = point[pos][0]+(386-w)/2;
		    	y= point[pos][1]+(45-h)/2+h;
		    }else{
		    	if(len <= 12){
					pt.setTextSize(30);
				}else{
					pt.setTextSize(20);
				}
		    	  float w= pt.measureText(chatMsg);
		    	  float h = pt.descent() - pt.ascent();
		    	x = point[pos][0]+(245-w)/2;
		    	y= point[pos][1]+(45-h)/2+h;
		    }
		    canvas.drawText(chatMsg, x, y, pt);
		}
	}
	Thread currentThread=null;
	/**
	 * 启用动画
	 */
	public void startAnim(final String chatText) {
		
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
					chatMsg =chatText;
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
			}else{
				setVisibility(View.INVISIBLE);
			}
		};
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
