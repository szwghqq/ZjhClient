package com.dozengame.gameview;

import java.util.Date;

import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.View;

public class CheckNetView extends View {

	static int point[] ={388,3};
	Paint pt = new Paint();
	Bitmap game_signal=null;
	Bitmap cacheBitmap =null;
	Canvas canvas;
	int signal =3;
	boolean drawRun =true;
	String time="12:11";
	boolean timeUpdate=true;
	
	public CheckNetView(Context context) {
		 super(context);
		 canvas = new Canvas();
		 int c= Color.argb(255, 204, 204, 204);
		 pt.setColor(c);
		 pt.setTextSize(25);
		 pt.setAntiAlias(true);
		// this.setVisibility(View.INVISIBLE);
	}
	public void setSignalState(int state,boolean timeUpdate){
		signal=state;
		this.timeUpdate =timeUpdate;
		draw();
	}
	public void drawCacheBitmap(){
		 
		if(signal == 3){
			game_signal =GameBitMap.getGameBitMap(GameBitMap.GAME_SIGNAL3);
		}else if(signal == 2){
			game_signal =GameBitMap.getGameBitMap(GameBitMap.GAME_SIGNAL2);
		}else if(signal == 1){
			game_signal =GameBitMap.getGameBitMap(GameBitMap.GAME_SIGNAL1);
		}
		GameUtil.recycle(cacheBitmap);
		cacheBitmap = Bitmap.createBitmap(170, 34, Config.ARGB_4444);
		canvas.setBitmap(cacheBitmap);
		if(timeUpdate){
		  
			timeUpdate =false;
			Date dt = new Date();
			int h =dt.getHours();
			int m = dt.getMinutes();
			if (h < 10) {
				time = "0" + h + ":";
			} else {
				time = h + ":";
			}
			if (m < 10) {
				time += "0" + m;
			} else {
				time += m;
			}
		 
		} 
		canvas.drawText(time, 0, 28, pt);
		canvas.drawBitmap(game_signal, 120, 0, null);
	}
	private void destroyBitmap(){
	   GameUtil.recycle(game_signal);
	   game_signal=null;
	}
	public void draw(){
		drawRun = true;
		postInvalidate();
	}
	protected void onDraw(Canvas canvas) {
//		RectF rect = new RectF();
//		rect.left=417;
//		rect.right=545;
//		rect.top=138;
//		rect.bottom=188;
//		pt.setColor(Color.WHITE);
		//canvas.drawRoundRect(rect, 12, 12, pt);
		//pt.setColor(Color.RED);
		//canvas.drawText("12:11", 390, 40, pt);
		//canvas.drawText("ÍøËÙÂý", 510, 40, pt);
		//canvas.drawBitmap(bitmap, 510, 40, null);
		//canvas.drawText("ÍøËÙÂý", 430, 172, pt);
		//canvas.drawBitmap(cacheBitmap, 390, 40, pt);
		if(drawRun){
		  drawRun =false;
		  drawCacheBitmap();
		  destroyBitmap();
		 } 
		 canvas.drawBitmap(cacheBitmap, point[0],point[1],null); 
	}
	public void destory(){
	     destroyBitmap();
		 GameUtil.recycle(cacheBitmap);
		 cacheBitmap =null;
		 this.destroyDrawingCache();
	}
}
