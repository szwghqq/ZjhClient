package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
 
/**
 * 公共牌视图
 * @author hewengao
 *
 */
public class GlobalPokeView extends View {
    static final int[][] pokePoint = {{295,222},{370,222},{445,222},{520,222},{595,222}};
    
    Bitmap pokess =null;
    Bitmap tempBit =null;
    Context context;
    Bitmap  backPoke;
    int pos;
    int state =1;//控制添加牌的黑色透明背景 0:添加 1：不添加
    int poke;
    public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public GlobalPokeView(Context context,int pos, Bitmap  backPoke) {
		super(context);
		this.pos = pos;
		this.context=context;
		this.backPoke=backPoke;
		this.setVisibility(View.INVISIBLE);
		
		 
	}
	public void setPoke(int poke){
		this.poke=poke;
		if(poke >0){
			pokess= GameBitMap.getPokeBitMap(poke-1);
			bool =true;
			draw();
		}
	}
	boolean drawRun =true;
	public void draw(){
		drawRun =true;
		this.postInvalidate();
	}
//	@Override
//	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
//		if(View.VISIBLE == this.getVisibility()){
//			if(drawRun){
//				drawRun =false;
//			    if(pokess != null){
//			        tempBit=pokess;
//			    	if(state == 0){
//			    		tempBit=Bitmap.createBitmap(tempBit);
//			    		tempBit =GameBitMap.getRgbAlphaBitmap(tempBit);
//			    	}
//				  canvas.drawBitmap(tempBit, pokePoint[pos][0], pokePoint[pos][1],null);
//				  
//			    }
//			}else{
//				canvas.drawBitmap(tempBit, pokePoint[pos][0], pokePoint[pos][1],null);
//			}
//		}
//	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//try{
		if(View.VISIBLE == this.getVisibility()){
			if(bool){
				if(drawRun){
					drawRun =false;
				    if(pokess != null){
//				    	if(tempBit != pokess){
//				    	 GameUtil.recycle(tempBit);
//				    	}
				        tempBit=pokess;
				    	if(state == 0){
				    		tempBit=Bitmap.createBitmap(tempBit);
				    		tempBit =GameBitMap.getRgbAlphaBitmap(tempBit);
				    	}
					  canvas.drawBitmap(tempBit, pokePoint[pos][0], pokePoint[pos][1],null);
				    }
				}else{
					if(tempBit !=null && !tempBit.isRecycled()){
					 canvas.drawBitmap(tempBit, pokePoint[pos][0], pokePoint[pos][1],null);
					}
				}
			}else{
				//绘制背牌
				canvas.drawBitmap(backPoke, pokePoint[pos][0], pokePoint[pos][1],null);
			}
		}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	}
    /**
     * 与当前牌值是否相等。并设置状态
     * @param poke
     */
	public boolean  equalsPoke(int poke){
		if(this.poke ==poke){
			//不盲
			state =1;
			return true;
		}else{
			return false;
		}
	}
	public void destroy() {
		 
		context =null;
		GameUtil.recycle(pokess);
		pokess =null;
		GameUtil.recycle(tempBit);
		tempBit =null;
		this.destroyDrawingCache();
	}
	@Override
	public void setVisibility(int visibility) {
		// TODO Auto-generated method stub
		super.setVisibility(visibility);
		if(View.VISIBLE ==visibility){
			draw();
		}else{
			GameUtil.recycle(pokess);pokess=null;
			GameUtil.recycle(tempBit);tempBit=null;
		}
	}
	private boolean bool = false;
	
	public void startAnim(){
		 bool =false;
		 setVisibility(View.VISIBLE);	
		 startAnimPrevPart();
	}
	int centerX=0;
	int centerY=0;
	/**
	 * 启用翻牌动画
	 */
	private void startAnimPrevPart(){
	      centerX=pokePoint[pos][0]+backPoke.getWidth()/2;
	      centerY=pokePoint[pos][1]+backPoke.getHeight()/2;
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
	 
}
