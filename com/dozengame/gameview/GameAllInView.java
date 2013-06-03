package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;

/**
 * 游戏全下动画
 * 
 * @author hewengao
 * 
 */
public class GameAllInView extends View {
	//绘制动画起点坐标
	static final int[][] pointStart = { { 405, 382 }, { 180, 382 }, { 71, 436 }, { 71, 193 },
			{ 243, 141 }, { 592, 141 }, { 855, 193 }, { 885, 446 },
			{ 641, 382 } };
//	//绘制坐标
	static final int[][] point = { { 405, 322 }, { 180, 302 }, { 141, 243 }, { 121, 170 },
			{ 243, 201 }, { 592, 201 }, { 690, 170 }, { 690, 243 },
			{ 641, 322 } };
	static final float [][] res=new float[9][2];
	Bitmap cacheBitmap;
	Bitmap bitMapCache;
	Canvas canvas;
	int pos;
	static final Matrix mMatrix = new Matrix();
	boolean bitmapIsInit =false;
	public GameAllInView(Context context, int pos) {
		super(context);
		this.pos = pos;
		canvas = new Canvas();
		initRes();
		this.setVisibility(View.INVISIBLE);
	}

	private void initRes(){
		 
		for(int i = 0; i < 9 ;i++){
			res[i][0] = (float) ((pointStart[i][0] / 1.0) / 960);
			res[i][1] = (float) ((pointStart[i][1] / 1.0) / 640);
		}
	}
	public void drawCacheBitmap() {
		if(bitmapIsInit == false){
			bitMapCache = GameBitMap.getGameBitMap(GameBitMap.ALLIN);
			bitmapIsInit =true;
		}
		 
		 Bitmap bitMap =bitMapCache;
		if (pos == 2 || pos ==6) {
			cacheBitmap = Bitmap.createBitmap(178, 178, Config.ARGB_8888);
			mMatrix.reset();
			mMatrix.setRotate(60.0f);
			Bitmap temp = Bitmap.createBitmap(bitMap, 0, 0, 178, 61, mMatrix, true);
			GameUtil.recycle(bitMap);
			bitMap =temp;
		}else if(pos == 3 || pos ==7){
			cacheBitmap = Bitmap.createBitmap(178, 178, Config.ARGB_8888);
			mMatrix.reset();
			mMatrix.setRotate(-60.0f);
			Bitmap temp  = Bitmap.createBitmap(bitMap, 0, 0, 178, 61, mMatrix, true);
			GameUtil.recycle(bitMap);
			bitMap =temp;
		} else{
			cacheBitmap = Bitmap.createBitmap(178, 61, Config.ARGB_8888);
		}
		canvas.setBitmap(cacheBitmap);
		canvas.drawBitmap(bitMap, 0, 0, null);
	}

	public void draw() {
		postInvalidate();
	}
    boolean drawRun =false;
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(this.getVisibility() == View.VISIBLE && drawRun){
			drawRun =false;
			drawCacheBitmap();
			canvas.drawBitmap(cacheBitmap, point[pos][0], point[pos][1], null);
			startAnimed();
			
		}
	}

	/**
	 * 启用动画
	 */
	public void startAnim() {
		drawRun =true;
	    this.setVisibility(View.VISIBLE);
	   
	}
	private void startAnimed(){
		 ScaleAnimation  am1 =   new ScaleAnimation(0.0f, 1.0f, 0.0f,1.0f,Animation.RELATIVE_TO_SELF,res[pos][0],Animation.RELATIVE_TO_SELF,res[pos][1]);
			//设置时间持续时间
			 am1.setDuration(500);
			 am1.setInterpolator(new AccelerateInterpolator());//加速器
			 am1.setAnimationListener(new AnimationListener(){

					@Override
					public void onAnimationEnd(Animation animation) {
						setVisibility(View.INVISIBLE);
					}
					public void onAnimationRepeat(Animation animation) {
					}
					public void onAnimationStart(Animation animation) {
							
					}
				});
			 am1.setStartOffset(20);
			 this.startAnimation(am1);
	}
	
	   /**
	 * 释放资源
	 */
	public void destroy(){
		destroyBitmap();
		this.destroyDrawingCache();
		canvas =null;
//		Bitmap cacheBitmap;
//		Bitmap cacheBitmapPrev;
//		Bitmap bitMapCache;
//		Canvas canvas;
	}
	
	private void destroyBitmap(){
		
		 GameUtil.recycle(bitMapCache);
		 bitMapCache =null;
		 
		 GameUtil.recycle(cacheBitmap);
		 cacheBitmap =null;
		 bitmapIsInit =false;
	}
	/**
	 * 重写View可见性
	 */
	public void setVisibility(int visibility){
		super.setVisibility(visibility);
		if(View.INVISIBLE ==visibility){
			destroyBitmap();
		}else{
			draw();
		}
	}
}
