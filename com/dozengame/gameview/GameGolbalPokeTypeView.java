package com.dozengame.gameview;

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
/**
 * 全局牌形视图
 * @author hewengao
 *
 */
public class GameGolbalPokeTypeView extends View {
	
	static final int[] typeViewPoint = {398, 288 };
	static final  Paint pt = new Paint();
	int pos =0;
	Bitmap cacheBitmap;
	Bitmap gamePokeTypeBg;
	
	Canvas canvas;
	int pokeType=1;
	
	
	public GameGolbalPokeTypeView(Context context) {
		super(context);
		canvas = new Canvas();
		pt.setColor(Color.WHITE);
		pt.setTextSize(25);
		this.setVisibility(View.INVISIBLE);
		
	}
	 
	public void setPokeType(int pokeType) {
		this.pokeType = pokeType;
		setVisibility(View.VISIBLE);
		//draw();
	}
	public void drawCacheBitmap(){
		switch(pokeType){
		case 1:
			gamePokeTypeBg=GameBitMap.getGameBitMap(GameBitMap.GAME_POKETYPE_BG0);
			 break;
		case 2:
			gamePokeTypeBg=GameBitMap.getGameBitMap(GameBitMap.GAME_POKETYPE_BG1);
			 break;
		case 3:
			gamePokeTypeBg=GameBitMap.getGameBitMap(GameBitMap.GAME_POKETYPE_BG2);
			 break;
		case 4:
			gamePokeTypeBg=GameBitMap.getGameBitMap(GameBitMap.GAME_POKETYPE_BG3);
			 break;
		case 5:
			gamePokeTypeBg=GameBitMap.getGameBitMap(GameBitMap.GAME_POKETYPE_BG4);
			 break;
		case 6:
			gamePokeTypeBg=GameBitMap.getGameBitMap(GameBitMap.GAME_POKETYPE_BG5);
			 break;
		case 7:
			gamePokeTypeBg=GameBitMap.getGameBitMap(GameBitMap.GAME_POKETYPE_BG6);
			 break;
		case 8:
			gamePokeTypeBg=GameBitMap.getGameBitMap(GameBitMap.GAME_POKETYPE_BG7);
			 break;
		case 9:
			gamePokeTypeBg=GameBitMap.getGameBitMap(GameBitMap.GAME_POKETYPE_BG8);
			 break;
		case 10:
			gamePokeTypeBg=GameBitMap.getGameBitMap(GameBitMap.GAME_POKETYPE_BG9);
			 break;
		}
		
		cacheBitmap = Bitmap.createBitmap(165, 34, Config.ARGB_8888);
		canvas.setBitmap(cacheBitmap);
		canvas.drawBitmap(gamePokeTypeBg, 0,0,null);
		String pokeTypeName=GameUtil.getPokeWeight(pokeType);
		float w=pt.measureText(pokeTypeName);
		canvas.drawText(GameUtil.getPokeWeight(pokeType), (165-w)/2, (34-pt.ascent())/2-2, pt);
	}
	boolean drawRun =true;
	public void draw(){
		 
		drawRun=true;
		postInvalidate();
	}
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(this.getVisibility() == View.VISIBLE){
			if(drawRun){
			 
				drawRun=false;
				GameUtil.recycle(cacheBitmap);
				drawCacheBitmap();
				destroyBitmap();
			} 
			canvas.drawBitmap(cacheBitmap, typeViewPoint[0],typeViewPoint[1],null);
			 
		}
	}

	public void reset() {
	    this.setVisibility(View.INVISIBLE);
	}
	@Override
	public void setVisibility(int visibility) {
		// TODO Auto-generated method stub
		super.setVisibility(visibility);
		if(View.VISIBLE ==visibility){
		 
			draw();
		}
	}
	public void destroy(){
		Log.i("test19", "GameGolbalPokeType destroy");
		 GameUtil.recycle(cacheBitmap);
		 cacheBitmap =null;
		 destroyBitmap();
 	     canvas =null;
 
	}
	private void destroyBitmap(){
		 GameUtil.recycle(gamePokeTypeBg);
		 gamePokeTypeBg =null;
	}
}
