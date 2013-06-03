package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
/**
 * 游戏下注视图
 * @author hewengao
 *
 */
public class GameXiaZhuView extends View {

	static final int[][] playerChouMaViewPoint = {{458,440},{235,440},{65,385},{65,162},{295,65},{645,65},{867,160},{867,383},{692,440}};
	int pos =0;
	Canvas canvas;
	Bitmap bitchouma;
	 
	int money=90000000;
	public int getMoney() {
		return money;
	}
	/**
	 * 增加筹码
	 * @param gold
	 */
	public void addGold(int gold){
		this.money += gold;
		if(this.money >0){
			 this.setVisibility(View.VISIBLE);
		}
		 
	}
	public void setMoney(int money) {
		this.money = money;
		if(this.money >0){
		 this.setVisibility(View.VISIBLE);
		}
	}
	Paint pt = new Paint();
	public GameXiaZhuView(Context context,int pos) {
		super(context);
		this.pos=pos;
		canvas = new Canvas();
		pt.setColor(Color.WHITE);
		pt.setAntiAlias(true);
		this.setVisibility(View.INVISIBLE);
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
				 destoryBitmap();
				if(money<10){
					 bitchouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA10);
				}else if(money<100){
					bitchouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA100);
				}else if(money<1000){
					bitchouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA1k);
				}else if(money<10000){
					bitchouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA1w);
				}else if(money<100000){
					bitchouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA10w);
				}else if(money<1000000){
					bitchouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA100w);
				}else if(money<10000000){
					bitchouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA1000w);
				}else if(money<100000000){
					bitchouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA1y);
				}else if(money<1000000000){
					bitchouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA10y);
				}else{
					bitchouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA100y);
				}
		    }
			if(bitchouma != null ){
			canvas.drawBitmap(bitchouma, playerChouMaViewPoint[pos][0]-4,playerChouMaViewPoint[pos][1]-4,null);
			}
		}
		
	}

	private void destoryBitmap(){
		GameUtil.recycle(bitchouma);
		bitchouma =null;
	}
	public void destory(){
		//playerChouMaViewPoint =null;
		canvas=null;
		destoryBitmap();
	}
	@Override
	public void setVisibility(int visibility) {
		// TODO Auto-generated method stub
		super.setVisibility(visibility);
		if(View.VISIBLE == visibility){
			draw();
		}else{
			destoryBitmap();
		}
	}

}
