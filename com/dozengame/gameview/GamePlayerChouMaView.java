package com.dozengame.gameview;

import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.view.View;
/**
 * 玩家下注筹码视图
 * @author hewengao
 *
 */
public class GamePlayerChouMaView extends View {
 
	static final int[][] playerChouMaViewPoint = {{420,355},{190,355},{170,308},{170,235},{190,190},{665,190},{685,235},{685,308},{665,355}};
	int pos =0;
	Bitmap cacheBitmap;
	Bitmap choumaBg;
	Bitmap chouma;
	Canvas canvas;
	int money=900000000;
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
		///draw();
	}
	public void setMoney(int money) {
		this.money = money;
		if(this.money >0){
			this.setVisibility(View.VISIBLE);
		}
		//draw();
	}
	Paint pt = new Paint();
	public GamePlayerChouMaView(Context context,int pos) {
		super(context);
		this.pos=pos;
		canvas = new Canvas();
		pt.setColor(Color.WHITE);
		pt.setAntiAlias(true);
		this.setVisibility(View.INVISIBLE);
	}
	public void drawCacheBitmap(){
		GameUtil.recycle(cacheBitmap);
		cacheBitmap = Bitmap.createBitmap(118,40, Config.ARGB_8888);
		canvas.setBitmap(cacheBitmap);
		
		//Bitmap t_choumaBg =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA_BG);
	
		//choumaBg=GameBitMap.resizeBitmap(t_choumaBg, choumaBg.getWidth()+15, choumaBg.getHeight());
		//GameUtil.recycle(t_choumaBg);
		choumaBg=GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA_BG);
		canvas.drawBitmap(choumaBg,4,7,null);
		if(money<10){
			 chouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA10);
		}else if(money<100){
			chouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA100);
		}else if(money<1000){
			chouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA1k);
		}else if(money<10000){
			chouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA1w);
		}else if(money<100000){
			chouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA10w);
		}else if(money<1000000){
			chouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA100w);
		}else if(money<10000000){
			chouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA1000w);
		}else if(money<100000000){
			chouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA1y);
		}else if(money<1000000000){
			chouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA10y);
		}else{
			chouma =GameBitMap.getGameBitMap(GameBitMap.GAME_CHOUMA100y);
		}
		canvas.drawBitmap(chouma, 0,0,null);
		
		
		String m =money+"";
		 
		if(m.length() < 8){
			pt.setTextSize(18);
		}else{
			pt.setTextSize(14);
		}
		canvas.drawText(m, 40, (40-pt.ascent())/2, pt);
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
			    drawRun =false;
		        drawCacheBitmap();
		        canvas.drawBitmap(cacheBitmap, playerChouMaViewPoint[pos][0],playerChouMaViewPoint[pos][1],null);
		        destoryBitmap();
			}else{
			   canvas.drawBitmap(cacheBitmap, playerChouMaViewPoint[pos][0],playerChouMaViewPoint[pos][1],null);
			}
		}
	}
 
	private void destoryBitmap(){
		 GameUtil.recycle(chouma);
		 GameUtil.recycle(choumaBg);
		 chouma =null;
		 choumaBg=null;
		
	}
	public void destory(){
		GameUtil.recycle(cacheBitmap);
		cacheBitmap =null;
		destoryBitmap();
//	    playerChouMaViewPoint =  null;
//		pos =0;
//	    canvas =null;
	    destroyDrawingCache();
	}
	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if(View.VISIBLE == visibility){
			draw();
		}else{
			GameUtil.recycle(cacheBitmap);
			cacheBitmap =null;
		}
	}
}
