package com.dozengame.gameview;

import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.view.View;
/**
 * 庄家视图
 * @author hewengao
 */
public class GameZJView extends View {
 
	static final int[][] zjViewPoint = {{370,430},{320,430},{60,290},{160,115},{383,105},{555,105},{768,115},{855,290},{605,430}};
	int pos =0;
	Bitmap cacheBitmap;
	Bitmap cacheBitmapPrev;
	Bitmap zjbitmap;
	Canvas canvas;
	boolean bitmapIsInit =false;
	public GameZJView(Context context,int pos) {
		super(context);
		this.pos=pos;
		canvas = new Canvas();
		this.setVisibility(View.INVISIBLE);
	}
	public void drawCacheBitmap(){
		 
		zjbitmap=GameBitMap.getGameBitMap(GameBitMap.GAME_ZJD); 
		cacheBitmapPrev =cacheBitmap;
		cacheBitmap = Bitmap.createBitmap(34, 34, Config.ARGB_8888);
		canvas.setBitmap(cacheBitmap);
		canvas.drawBitmap(zjbitmap,0,0,null);
	}
	boolean drawRun=true;
	public void draw(){
		drawRun =true;
		postInvalidate();
	}
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(View.VISIBLE == this.getVisibility()){
			  if(drawRun){
				  drawRun=false;
				  drawCacheBitmap();
				  canvas.drawBitmap(cacheBitmap, zjViewPoint[pos][0],zjViewPoint[pos][1],null);
				  destroyBitmap();
			  }else{
				  canvas.drawBitmap(cacheBitmap, zjViewPoint[pos][0],zjViewPoint[pos][1],null);
			  }
		}

	}
	/**
	 * 释放资源
	 */
	public void destroy(){
		 destroyBitmap();
		 GameUtil.recycle(cacheBitmap);
		 cacheBitmap =null;
		 destroyDrawingCache();
 
		 canvas =null;
	}
	private void destroyBitmap(){
		 GameUtil.recycle(zjbitmap);
		 zjbitmap =null;
		 GameUtil.recycle(cacheBitmapPrev);
		 cacheBitmapPrev =null;
	}
	/**
	 * 重写View可见性
	 */
	public void setVisibility(int visibility){
		super.setVisibility(visibility);
		if(View.INVISIBLE ==visibility){
			GameUtil.recycle(cacheBitmap);
			cacheBitmap =null;
			destroyBitmap();
		}else{
			draw();
		}
	}
	
}
