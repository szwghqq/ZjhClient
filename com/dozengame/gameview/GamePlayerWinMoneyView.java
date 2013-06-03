package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import com.dozengame.GameApplication;
import com.dozengame.music.MediaManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
/**
 * 玩家赢钱视图
 * @author hewengao
 *
 */
public class GamePlayerWinMoneyView extends View {
	static final int[][]  point = { { 415, 382 }, { 190, 382 }, { 20, 323 },
			{ 20, 100 }, { 253, 5 }, { 602, 5 }, { 805, 100 }, { 805, 323 },
			{ 651, 382 } };
	static final int[][]  pointAnim = { { 0, 140 }, { 0, 140 }, { 0, 140 },
			{ 0, 140 }, { 0, 140 }, { 0, 140 }, { 0, 140 }, { 0, 140 },
			{ 0, 140 } };
	static final Paint pt = new Paint();
	Bitmap cacheBitmap;
	Bitmap addMoneyBg;
	Canvas canvas;
	int money;
	int pos;
	
	public GamePlayerWinMoneyView(Context context,int pos) {
		super(context);
		this.pos=pos;
		canvas = new Canvas();
		pt.setColor(Color.BLACK);
		pt.setAntiAlias(true);
		pt.setTextSize(18);
		this.setVisibility(View.INVISIBLE);
	}

	public void drawCacheBitmap(){
		cacheBitmap = Bitmap.createBitmap(128, 25, Config.ARGB_8888);
		canvas.setBitmap(cacheBitmap);
		addMoneyBg=GameBitMap.getGameBitMap(GameBitMap.ADDMONEYBG);
		canvas.drawBitmap(addMoneyBg, 0,0,null);
	    String my=GameUtil.S+money;
	    float w =pt.measureText(my);
	    float h =pt.ascent();
	    canvas.drawText(my, (128-w)/2, (25-h)/2, pt);
	}
	boolean drawRun =true; 
	public void draw(){
		drawRun =true; 
		postInvalidate();
	}
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(View.VISIBLE == this.getVisibility()){
			
			if(drawRun){
			   drawRun=false;
			   GameUtil.recycle(cacheBitmap);
			   drawCacheBitmap();
			   canvas.drawBitmap(cacheBitmap, point[pos][0],point[pos][1],null);
			   translateAnim();
			   destroyBitmap();
			}else{
			  canvas.drawBitmap(cacheBitmap, point[pos][0],point[pos][1],null);
			}
		}
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
		draw();
	}
	 
	int pondCount=-1;
	/**
     * 启用加钱动画
     */
    public void translateAnimStart(int pondCount){
    	this.pondCount=pondCount;
		setVisibility(View.VISIBLE);
    }
    public void translateAnim(){
    	int x=pointAnim[pos][0];
		int y=pointAnim[pos][1];
    	TranslateAnimation am1 =   new TranslateAnimation(0, x, 0, y);
    	int time=0;
    	if(pondCount <=1){
    		time=1000;
    	}else if(pondCount <=4){
    		time =2000;
    	}else{
    		time =3000;
    	}
		//设置时间持续时间
		 am1.setDuration(time);
//		 AccelerateDecelerateInterpolator 在动画开始与介绍的地方速率改变比较慢，在中间的时候加速 
//		 AccelerateInterpolator 在动画开始的地方速率改变比较慢，然后开始加速 
//		 CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线 
//		 DecelerateInterpolator 在动画开始的地方速率改变比较慢，然后开始减速 
//		 LinearInterpolator 在动画的以均匀的速率改变 
		 am1.setInterpolator(new AccelerateInterpolator());//均匀
		 am1.setAnimationListener(new AnimationListener(){

				@Override
				public void onAnimationEnd(Animation animation) {
					 setVisibility(View.INVISIBLE);
					 //new MyThread();
					 //继续其他赢家彩池动画信息
					GameApplication.getDzpkGame().otherPokeViewManager.drawAllWinPlayerBestPoke();
				}
				public void onAnimationRepeat(Animation animation) {
				}
				public void onAnimationStart(Animation animation) {
					//播放分筹码音效
					MediaManager.getInstance().playSound(MediaManager.winchips);
				}
				
			});   
		  startAnimation(am1);
    }
    
    Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			 super.handleMessage(msg);
			 //继续其他赢家彩池动画信息
			GameApplication.getDzpkGame().otherPokeViewManager.drawAllWinPlayerBestPoke();
		}
	};
	private class MyThread extends Thread{
		public MyThread(){
			this.start();
		}
		@Override
		public void run() {
			 try {
				Thread.sleep(100);
				handler.sendEmptyMessage(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void destroyBitmap(){
		GameUtil.recycle(addMoneyBg);
		addMoneyBg =null;
	}
	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if(View.VISIBLE == visibility){
			draw();
		}else{
			GameUtil.recycle(cacheBitmap);
			cacheBitmap =null;
			destroyBitmap();
		}
	}

	public void destroy() {
		 
		  GameUtil.recycle(cacheBitmap);
		  cacheBitmap =null;
		  destroyBitmap();
		  canvas=null;
		  handler =null;
		 
	}
}
