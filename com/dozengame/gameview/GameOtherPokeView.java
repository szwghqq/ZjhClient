package com.dozengame.gameview;

import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;

/**
 * 游戏中其他玩家的牌
 * @author hewengao
 */
public class GameOtherPokeView extends View {

	static final int[][] pokeViewPoint = { {417, 435 },{ 192, 435 },{ 22, 366},{ 22,143},
			   { 255, 48},{604,48},{827,143},{827,366},{653,435}};
	int[] pokes = { 1, 5 };
     
	int [] state={1,1};
    int pos;
	Bitmap cacheBitmap;
	Canvas canvas;
	public GameOtherPokeView(Context context,int pos,Bitmap backPoke) {
		super(context);
		this.pos = pos;
		canvas = new Canvas();
		this.backPoke=backPoke;
		//默认不可见
		this.setVisibility(View.INVISIBLE);
	}
	public void drawCacheBitmap() {
		cacheBitmap = Bitmap.createBitmap(110, 100, Config.ARGB_4444);
		canvas.setBitmap(cacheBitmap);
		drawPoke1();
		drawPoke2();
	}
	public void drawCacheBitmap1() {
		cacheBitmap = Bitmap.createBitmap(110, 100, Config.ARGB_4444);
		canvas.setBitmap(cacheBitmap);
		canvas.drawBitmap(backPoke, 0, 0, null);
		canvas.drawBitmap(backPoke, 42, 0, null);
	}
	/**
	 * 绘制第一张牌
	 */
	private void drawPoke1(){
		Bitmap bitMap = GameBitMap.getPokeBitMap(pokes[0]-1);
		if(state[0] ==0){
    		//bitMap=Bitmap.createBitmap(bitMap);
    		bitMap=GameBitMap.getRgbAlphaBitmap(bitMap);
    	}
		canvas.drawBitmap(bitMap, 0, 0, null);
		GameUtil.recycle(bitMap);
		bitMap =null;
	}
	/**
	 * 绘制第二张牌
	 */
    private void drawPoke2(){
    	Bitmap bitMap = GameBitMap.getPokeBitMap(pokes[1]-1);
    	if(state[1] ==0){
    		//bitMap=Bitmap.createBitmap(bitMap);
    		bitMap=GameBitMap.getRgbAlphaBitmap(bitMap);
    	}
		canvas.drawBitmap(bitMap, 42,0, null);
		GameUtil.recycle(bitMap);
		bitMap =null;
	}
    boolean drawRun =true;
	public void draw() {
		drawRun =true;
		postInvalidate();
	}
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if(View.VISIBLE == this.getVisibility()){
			if(bool){
				if(drawRun){
					drawRun= false;
					GameUtil.recycle(cacheBitmap);
				    drawCacheBitmap();
				    canvas.drawBitmap(cacheBitmap,pokeViewPoint[pos][0],pokeViewPoint[pos][1],null);
				}else{
					canvas.drawBitmap(cacheBitmap,pokeViewPoint[pos][0],pokeViewPoint[pos][1],null);
				}
			}else{
				if(drawRun){
					 drawRun= false;
					 GameUtil.recycle(cacheBitmap);
					 drawCacheBitmap1();
					 canvas.drawBitmap(cacheBitmap,pokeViewPoint[pos][0],pokeViewPoint[pos][1],null);
				}else{
					 canvas.drawBitmap(cacheBitmap,pokeViewPoint[pos][0],pokeViewPoint[pos][1],null);
				}
			}
		}
	}
	public int[] getPokes() {
		return pokes;
	}
	public void setPokes(int[] pokes) {
		this.pokes = pokes;
		//draw();
	}
	public int[] getState() {
		return state;
	}
	public void setState(int[] state) {
		this.state = state;
	}
	public void setState(int index, int st) {
		this.state[index] = st;
	}
    
	public void destory(){
		GameUtil.recycle(cacheBitmap);
		cacheBitmap =null;
    	canvas =null;
		pokes = null;
	    state=null;
	}
	 
	@Override
	public void setVisibility(int visibility) {
		// TODO Auto-generated method stub
		super.setVisibility(visibility);
		if(View.VISIBLE == visibility){
			draw();
		}else{
			GameUtil.recycle(cacheBitmap);
		}
	}
	private boolean bool = false;
	Bitmap backPoke;
	public void startAnim(final GameOtherPokeViewManager pokeViewManager,final int allcount){
		 bool =false;
		 final  int centerX=pokeViewPoint[pos][0]+55;
		 final  int centerY=pokeViewPoint[pos][1]+50;
		 setVisibility(View.VISIBLE);	 
		ScaleAnimation animation = new ScaleAnimation(1.0f,0.0f,1.0f,1.0f,Animation.ABSOLUTE,centerX,Animation.ABSOLUTE,centerY);
		final AccelerateInterpolator air=new AccelerateInterpolator();//加速器
		animation.setInterpolator(air);
		animation.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationEnd(Animation animation) {
					bool = true;
					draw();
					 
					ScaleAnimation animation1 = new ScaleAnimation(0.0f,1.0f,1.0f,1.0f,Animation.ABSOLUTE,centerX,Animation.ABSOLUTE,centerY);
					animation1.setInterpolator(air);//加速器
					animation1.setAnimationListener(new AnimationListener(){

						@Override
						public void onAnimationEnd(Animation animation) {
							pokeViewManager.fanPaiEnd(allcount);
						}

						@Override
						public void onAnimationRepeat(Animation animation) {}

						@Override
						public void onAnimationStart(Animation animation) {}
						
					});
					animation1.setDuration(80);
					startAnimation(animation1);
					 
				}
			});
 	    animation.setDuration(140);
 		startAnimation(animation);
	}
}
