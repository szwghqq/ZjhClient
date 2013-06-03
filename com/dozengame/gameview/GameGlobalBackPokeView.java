package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
/**
 * 游戏公共背牌
 * @author hewengao
 *
 */
public class GameGlobalBackPokeView extends View {
	
	static final int[][] pokePoint = {{295,222},{370,222},{445,222},{520,222},{595,222}};
	Bitmap bitmap=null;
	 
	int centerX=0;
	int centerY=0;
	int pos =0;
	
	public GameGlobalBackPokeView(Context context,int pos,Bitmap bitmapPokeBack) {
		super(context);
	    this.bitmap= bitmapPokeBack;
	    this.pos =pos;
	    centerX=pokePoint[pos][0]+bitmap.getWidth()/2;
	    centerY=pokePoint[pos][1]+bitmap.getHeight()/2;
	   this.setVisibility(View.INVISIBLE);
	}
	
	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}
	boolean drawRun =true;
    private void draw(){
    	drawRun =true;
    	this.postInvalidate();
    }
	protected void onDraw(Canvas canvas) {
		if(View.VISIBLE == this.getVisibility()){
			//if(drawRun){
			//  drawRun =false;
	          canvas.drawBitmap(bitmap,pokePoint[pos][0],pokePoint[pos][1],null);
			//}else{
			//  canvas.drawBitmap(bitmap,pokePoint[pos][0],pokePoint[pos][1],null);
			//}
		}
	}
	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if(View.VISIBLE == visibility){
			draw();
		}else{
			
		}
	}
   /**
    * 释放
    */
	public void destroy(){
		
		bitmap= null;
	}
}
