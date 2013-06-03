package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
 
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

import com.dozengame.GameApplication;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
/**
 * 彩池视图
 * @author hewengao
 *
 */
public class PondView extends View {
	static final int[][] playerPoints = { { 405, 382 }, { 180, 382 }, { 10, 323 },
			{ 10, 100 }, { 243, 5 }, { 592, 5 }, { 815, 100 }, { 815, 323 },
			{ 641, 382 } };
	static final int[][] pondViewPointBak = {{422,195},{285,195},{559,195},{170,270},{670,270},{285,319},{422,319},{559,319}};
	static final int[][] pondViewPoint = {{422,193},{299,193},{545,193},{170,270},{670,270},{285,321},{422,321},{559,321}};
	
	//桌面筹码坐标到玩家坐标
    private static final int[][][] pointAnim = {
    	 {{-17,187},{120,187},{-154,187},{235,112},{-265,112},{120,63},{-17,63},{-154,63}},
    	 {{-242,187},{-105,187},{-379,187},{10,112},{-490,112},{-105,63},{-242,63},{-379,63}},
    	 {{-412,128},{-275,128},{-549,128},{-160,53},{-660,53},{-275,4},{-412,4},{-549,4}},
    	 {{-412,-95},{-275,-95},{-549,-95},{-160,-170},{-660,-170},{-275,-219},{-412,-219},{-549,-219}},
    	 {{-179,-190},{-42,-190},{-316,-190},{73,-265},{-417,-265},{-42,-314},{-179,-314},{-316,-314}},
    	 {{170,-190},{307,-190},{33,-190},{422,-265},{-78,-265},{307,-314},{170,-314},{33,-314}},
    	 {{393,-95},{530,-95},{256,-95},{645,-170},{145,-170},{530,-219},{393,-219},{256,-219}},
    	 {{393,128},{530,128},{256,128},{645,53},{145,53},{530,4},{393,4},{256,4}},
    	 
    	 {{219,187},{356,187},{82,187},{471,112},{-39,112},{356,63},{219,63},{82,63}}
    };
	int pos =0;
	Bitmap cacheBitmap;
	Bitmap pondBg;
	Canvas canvas;
	int money=0;
	static final Paint pt = new Paint();
	static final String m="$";
	boolean animEnd =true;//动画是否已执行完毕
	
	public PondView(Context context,int pos) {
		super(context);
		this.pos=pos;
		canvas = new Canvas();
		 pt.setColor(Color.WHITE);
		 pt.setAntiAlias(true);
		this.setVisibility(View.INVISIBLE);
	}
	
	public int getMoney() {
		return money;
	}
	public void addGolds(int gold){
		this.money +=gold;
		draw();
	}
	private void redGold(int gold){
		this.money -=gold;
		if(money <=1){
		  setVisibility(View.INVISIBLE);	
		}else{
		 draw();
		}
	}
	public void setMoney(int money) {
		this.money = money;
		this.setVisibility(View.VISIBLE);
		//draw();
	}
	 
	//某个赢家赢的钱
	public void setPondMoney(int pondMoney){
		this.money =pondMoney;
	}
	public void drawCacheBitmap(){
		cacheBitmap = Bitmap.createBitmap(117, 31, Config.ARGB_8888);
		canvas.setBitmap(cacheBitmap);
		pondBg = GameBitMap.getGameBitMap(GameBitMap.GAME_POND_BG);
		canvas.drawBitmap(pondBg, 0,0,null);
	   
	    pt.setTextSize(18);
	   
	    String my=m+money;
	    if(my.length()>11){
	    	pt.setTextSize(15);
       } 
	    float w =pt.measureText(my);
	    float h =pt.ascent();
	    canvas.drawText(my, (117-w)/2, (31-h)/2, pt);
	}
	boolean drawRun =true;
	public void draw(){
		drawRun =true;
		postInvalidate();
	}
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(View.VISIBLE ==this.getVisibility()){
			if(drawRun){
		      drawRun =false;
			  drawCacheBitmap();
			  canvas.drawBitmap(cacheBitmap, pondViewPoint[pos][0],pondViewPoint[pos][1],null);
			  translateAnim();
			  destoryBitmap();
			}else{
			  canvas.drawBitmap(cacheBitmap, pondViewPoint[pos][0],pondViewPoint[pos][1],null);
			}
		}

	}
	int index =-1;
	int pondCount=0;
	public void translateAnimStart(final int index,final int pondCount){
		this.index =index;
		this.pondCount =pondCount;
		setVisibility(View.VISIBLE);
	}
	/**
	 * 开启赢的动画
	 */
	  private void translateAnim(){
			if(index ==-1)return;
		    animEnd =false;
			int x=pointAnim[index][pos][0];
			int y=pointAnim[index][pos][1];
			final int indexs = index;
			TranslateAnimation am1 =   new TranslateAnimation(0, x, 0, y);
			int time =2000;
			if(pondCount <=1){
				time =2000;
			}else if(pondCount <= 4){
				time =2500;
			}else if(pondCount <= 8){
				time =3500;
			}
			//设置时间持续时间
			 am1.setDuration(time);
			 am1.setInterpolator(new AccelerateInterpolator());//加速器
			 am1.setAnimationListener(new AnimationListener(){

					@Override
					public void onAnimationEnd(Animation animation) {
						index =-1;
						setVisibility(View.INVISIBLE); 
						 // int indexs = index;
						animEnd =true;
						   //判断所有动画都已执行完毕
						  if(GameApplication.getDzpkGame().pondViewManager1.validAllAnimEnd()){
							  //执行赢家赢钱动画
							  GameApplication.getDzpkGame().playerWinMoneyManager.translateAnimStart(indexs,pondCount);
						  }
					}
					public void onAnimationRepeat(Animation animation) {
					}
					public void onAnimationStart(Animation animation) {
						GameApplication.getDzpkGame().pondViewManager.pondViews[pos].redGold(money);
					}
					
				});  
			  startAnimation(am1);
			 
	    }
	  
	  public void destory(){
 
 	      canvas =null;
		  GameUtil.recycle(cacheBitmap);
		  cacheBitmap =null;
		  destoryBitmap();
	  }
      private void destoryBitmap(){
		  GameUtil.recycle(pondBg);
		  pondBg =null;
	  }
	  @Override
	public void setVisibility(int visibility){
		super.setVisibility(visibility);
		if(View.VISIBLE ==visibility){
			draw();
		}else{
			GameUtil.recycle(cacheBitmap);
			cacheBitmap =null;
			index =-1;
		}
	}
}
