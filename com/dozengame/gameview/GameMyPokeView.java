package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;

/**
 * 游戏中自己的牌
 * 
 * @author hewengao
 * 
 */
public class GameMyPokeView extends View {

	static final int[] pokeViewPoint = { 502, 452 };
	static final int[] pokeY = { 0, 0 };// 牌的Y坐标
	int pos = 0;
	int step = 5;
	Bitmap cacheBitmap;
	Bitmap cacheBackBitmap;
	 
 
	Bitmap poke1;
	Bitmap poke2;
	Canvas canvas;
	int[] pokes = { 1, 5 };
	int[] state = { 1, 1 };
	static final Matrix mMatrix = new Matrix();
	boolean isrun = true;
	public int[] getState() {
		return state;
	}

	public void setState(int[] state) {
		this.state = state;
	}

	public void setState(int index, int st) {
		this.state[index] = st;
	}



	public GameMyPokeView(Context context) {
		super(context);
		canvas = new Canvas();
		this.setVisibility(View.INVISIBLE);
	}

	// /**
	// * 设置牌
	// * @param pokes
	// */
	public void setPokes(int[] pokes) {
		this.pokes = pokes;
		draw();
	}

	private void drawCacheBitmap() {
		GameUtil.recycle(cacheBitmap);
		cacheBitmap = Bitmap.createBitmap(140, 115, Config.ARGB_8888);
		canvas.setBitmap(cacheBitmap);

		Bitmap bitMap = GameBitMap.getPokeBitMap(pokes[0] - 1);
		if (bitMap != null) {
			mMatrix.reset();
			mMatrix.setRotate(-20.0f);
		 
			poke1 = Bitmap.createBitmap(bitMap, 0, 0, 70, 100, mMatrix, true);
			if (state[0] == 0) {
				poke1= GameBitMap.getRgbAlphaBitmap(poke1);
			}
			canvas.drawBitmap(poke1, 0, pokeY[0], null);
			GameUtil.recycle(bitMap);
			bitMap =null;
		}

		bitMap = GameBitMap.getPokeBitMap(pokes[1] - 1);
		if (bitMap != null) {
			mMatrix.reset();
			mMatrix.setRotate(10.0f);
		 
			poke2 = Bitmap.createBitmap(bitMap, 0, 0, 70, 100, mMatrix, true);
			if (state[1] == 0) {
				poke2= GameBitMap.getRgbAlphaBitmap(poke2);
			}
			canvas.drawBitmap(poke2, 42, pokeY[1], null);
			GameUtil.recycle(bitMap);
			bitMap =null;
		}
		GameUtil.recycle(poke1);
		GameUtil.recycle(poke2);
		 
	}
	//绘制背牌
	private void drawCacheBackBitmap() {
		GameUtil.recycle(cacheBackBitmap);
		cacheBackBitmap = Bitmap.createBitmap(140, 115, Config.ARGB_8888);
		canvas.setBitmap(cacheBackBitmap);
		Bitmap bitMap = GameBitMap.getPokeBitMap(52);
		Bitmap temp1=null,temp2=null;
		if (bitMap != null) {
			mMatrix.reset();
			mMatrix.setRotate(-20.0f);
		 
			temp1 = Bitmap.createBitmap(bitMap, 0, 0, 70, 100, mMatrix, true);
			canvas.drawBitmap(temp1, 0, pokeY[0], null);
		
			mMatrix.reset();
			mMatrix.setRotate(10.0f);
			temp2 = Bitmap.createBitmap(bitMap, 0, 0, 70, 100, mMatrix, true);
			canvas.drawBitmap(temp2, 42, pokeY[1], null);
		}
		GameUtil.recycle(bitMap);
		GameUtil.recycle(temp1);
		GameUtil.recycle(temp2);
		temp1 =null;
		temp2 =null;
		bitMap =null;
	}
	//控制重绘
	 boolean drawRun =true;
	public void draw() {
		drawRun =true;
		postInvalidate();
	}
   private boolean bool = false;
	//启用翻牌动画
	public void startFanPaiAnim(){
		 bool =false;
		 setVisibility(View.VISIBLE);	
		 startAnimPrevPart();
	}
	float centerX=0;
	float centerY=0;
	/**
	 * 启用翻牌动画
	 */
	private void startAnimPrevPart(){
	      centerX=pokeViewPoint[0]+140/2.0f;
	      centerY=pokeViewPoint[1]+115/2.0f;
		ScaleAnimation animation = new ScaleAnimation(1.0f,0.0f,1.0f,1.0f,Animation.ABSOLUTE,centerX,Animation.ABSOLUTE,centerY);
 	    animation.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}
				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				@Override
				public void onAnimationEnd(Animation animation) {
					bool = true;
					draw();
					ScaleAnimation animation1 = new ScaleAnimation(0.0f,1.0f,1.0f,1.0f,Animation.ABSOLUTE,centerX,Animation.ABSOLUTE,centerY);
					animation1.setDuration(100);
					startAnimation(animation1);
	 
					 
				}
			});
 	    animation.setDuration(150);
 		startAnimation(animation);
	}
	/**
	 * 弃牌状态
	 */
	public void giveUpState(){
		 setState(0,0);
		 setState(1,0);
		 draw();
	}
	boolean isfrist = true;

	protected void onDraw(Canvas canvas) {
 
		if(this.getVisibility() == View.VISIBLE){
			if(bool){
				if(drawRun){
					drawRun =false;
					drawCacheBitmap();
					canvas.drawBitmap(cacheBitmap, pokeViewPoint[0], pokeViewPoint[1],null);
					destroyBitmap();
				}else{
					if(cacheBitmap != null){
					 canvas.drawBitmap(cacheBitmap, pokeViewPoint[0], pokeViewPoint[1],null);
					}
				}
			}else{
				if(cacheBackBitmap == null){
					drawCacheBackBitmap();
				}
				canvas.drawBitmap(cacheBackBitmap, pokeViewPoint[0], pokeViewPoint[1],null);
			}
		}
	}

	 public void startAnim() {
		new MyThread();
	}

	private class MyThread extends Thread {
		public MyThread() {
			this.start();
		}

		@Override
		public void run() {
			boolean bl = false;
			int count = 0;
			while (isrun) {
				if (bl) {
					bl = false;
					pokeY[0] = step;
					pokeY[1] = 0;
				} else {
					bl = true;
					pokeY[1] = step;
					pokeY[0] = 0;
				}
				draw();
				count++;
				if (count == 1000)
					break;
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			pokeY[0] = 0;
			pokeY[0] = 0;
			draw();
		}
	}
	public void reset() {
		this.setVisibility(View.INVISIBLE);
		state[0] = 1;
		state[1] = 1;
	}

	public void destroy() {
		Log.i("test19", "GameMyPokeView destroy");
		 destroyBitmap();
		 GameUtil.recycle(cacheBitmap);
		 GameUtil.recycle(cacheBackBitmap);
		 this.destroyDrawingCache();
		 cacheBitmap =null;
		 cacheBackBitmap =null;
		 pokes = null;
		 state=null;
		 canvas =null;
	}
	 
	private void destroyBitmap(){
 
		GameUtil.recycle(poke1);
		poke1 =null; 
		GameUtil.recycle(poke2);
		poke2 =null; 
	}
	/**
	 * 重写View可见性
	 */
	public void setVisibility(int visibility){
		super.setVisibility(visibility);
		if(View.INVISIBLE ==visibility){
			destroyBitmap();
			GameUtil.recycle(cacheBitmap);
		}else{
			draw();
		}
	}
}
