package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.view.View;

import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
/**
 * 玩家背面手牌
 * @author hewengao
 *
 */
public class GamePokeBackView extends View {
	static final int[][] pokeBackViewPoint = {{546,395},{320,395},{150,323},{150,235},{383,145},{555,145},{780,235},{780,323},{605,395}};
	int pos =0;
	Bitmap cacheBitmap;
	Bitmap cacheBitmapPrev;
	Bitmap pokeBack;
	Canvas canvas;
	//boolean bitmapIsInit =false;
	public GamePokeBackView(Context context,int pos) {
		super(context);
		this.pos=pos;
		canvas = new Canvas();
		this.setVisibility(View.INVISIBLE);
		
	}
	public void drawCacheBitmap(){
		    if(canvas ==null)return;
		    pokeBack=GameBitMap.getGameBitMap(GameBitMap.GAME_POKE_BACK);
			cacheBitmapPrev=cacheBitmap;//缓存用于释放
			cacheBitmap = Bitmap.createBitmap(36, 46, Config.ARGB_8888);
			canvas.setBitmap(cacheBitmap);
			canvas.drawBitmap(pokeBack, 0,5,null);
			canvas.drawBitmap(pokeBack, 5,0,null);
		 
	}
	boolean drawRun =true;  
	public void draw(){
		drawRun =true;  
		postInvalidate();
	}
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(this.getVisibility() == View.VISIBLE){
			if(drawRun){
				drawRun =false;
				GameUtil.recycle(cacheBitmap);
				drawCacheBitmap();
				canvas.drawBitmap(cacheBitmap, pokeBackViewPoint[pos][0],pokeBackViewPoint[pos][1],null);
				destroyBitmap();
			}else{
				if(cacheBitmap !=null && !cacheBitmap.isRecycled()){
				 canvas.drawBitmap(cacheBitmap, pokeBackViewPoint[pos][0],pokeBackViewPoint[pos][1],null);
				}
				
			}
		}
	}
	/**
	 * 销毁
	 */
	public void destory(){
		destroyBitmap();
		GameUtil.recycle(cacheBitmap);
		cacheBitmap =null;
		canvas =null;
		destroyDrawingCache();
	}
	private void destroyBitmap(){
		 GameUtil.recycle(pokeBack);
		 pokeBack =null;
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
