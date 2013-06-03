package com.dozengame.gameview;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
import com.dozengame.util.Measure;

/**
 * 购买筹码视图
 * 
 * @author admin
 * 
 */
public class BuyChouMaView extends View {

	static final Paint paint = new Paint();
	static final int[] points = { 127, 0 };
	
	Bitmap game_gamer_max;
	Bitmap game_gamer_maxs;
	Bitmap game_gamer_reduce;
	Bitmap game_gamer_reduces;
	Bitmap game_gamer_add;
	Bitmap game_gamer_adds;
	Bitmap game_gamer_bar;
	Bitmap game_gamer_fkbg;
	Bitmap game_chg_bg3;
	Bitmap game_chg_bg31;
	Bitmap cacheBitmap;
	 
	
	static final int reduceX=24;
	static final int addX=618;
	static final int maxX=726;
	static final int width = 125;
	//int width = 52;
	static final int width1=102;
	static final int width2=122;
	static final int height = 90;
	static final int min = 127;
	//int max = 569;
	static final int max = 490;
	//static final int max = 507;
	static final int step =10;
	
	static int state = 0;
	
	BuyChouMaDialog buyChouMa;
	public BuyChouMaView(BuyChouMaDialog buyChouMa) {
		super(buyChouMa.getContext());
		this.buyChouMa=buyChouMa;
		initBitmap();
		initData();
	}
	int minGold;//最小
	int maxGold;//最大
	int defaultGold;//默认购买的鑫币值
	float dwGold =0;//每一个单位的金币值
	/**
	 * 初始化数据
	 */
	private void initData(){
		HashMap data =buyChouMa.data;
		minGold=(Integer)data.get("min");
		maxGold=(Integer) data.get("max");
		defaultGold=(Integer) data.get("defaultgold");
		if(minGold == maxGold){
			points[0]=max;
			return;
		}
		//dwGold=(float)((maxGold-minGold)/442.0);
		dwGold=(float)((maxGold-minGold)/363.0);
		//计算当前滑块坐标
		float temp=((defaultGold-minGold)/dwGold);
	    temp = temp+min;
	    try {
			points[0]=DecimalFormat.getInstance().parse(temp+"").intValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(points[0] >max){
			points[0]=max;
		}
	}
    /**
     * 初始化需要用到的图片
     */
	private void initBitmap() {
		try {
			game_gamer_max = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("game_gamer_max.png"));
			game_gamer_maxs = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("game_gamer_maxs.png"));

			game_gamer_reduce = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("game_gamer_reduce.png"));
			game_gamer_reduces = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("game_gamer_reduces.png"));

			game_gamer_add = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("game_gamer_add.png"));
			game_gamer_adds = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("game_gamer_adds.png"));

			game_gamer_bar = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("game_gamer_bar.png"));
			game_gamer_fkbg = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("game_gamer_fkbg.png"));
			game_chg_bg3= BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("game_chg_bg3.png"));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw() {
		if(minGold != maxGold){
		    int gold =0;
			if(points[0]== max){
			   gold =maxGold;
			}else{
				float ff=((points[0]-min)*dwGold);
				ff = ff+minGold;
				try {
					gold=DecimalFormat.getInstance().parse(ff+"").intValue();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			buyChouMa.setBuyChouMa(gold);
		}
		
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawImg(canvas); 
	}

	private void drawImg(Canvas canvas) {
		if (state == 1) {
			canvas.drawBitmap(game_gamer_reduces, reduceX, 0, paint);
		} else {
			canvas.drawBitmap(game_gamer_reduce, reduceX, 0, paint);
		}
		canvas.drawBitmap(game_gamer_fkbg, 127, -1, paint);
		 
		int w = points[0]-127;
		if(w > 0 ){
//			if(game_chg_bg31 != null && !game_chg_bg31.isRecycled()){
//				game_chg_bg31.recycle();
//			}
			game_chg_bg31=GameBitMap.resizeBitmap(game_chg_bg3, w, game_chg_bg3.getHeight());
		    canvas.drawBitmap(game_chg_bg31,127, 17, paint);
		}
	
		if (state == 2) {
			canvas.drawBitmap(game_gamer_adds, addX, 0, paint);
		} else {
			canvas.drawBitmap(game_gamer_add, addX, 0, paint);
		}
		canvas.drawBitmap(game_gamer_bar, points[0],-1, paint);

		if (state == 3) {
			canvas.drawBitmap(game_gamer_maxs, maxX, 0, paint);
		} else {
			canvas.drawBitmap(game_gamer_max, maxX, 0, paint);
		}
		 
	}
    boolean ispress=false;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		float x = event.getX();
		float y = event.getY();

		int action = event.getAction();
         
		if (action != MotionEvent.ACTION_MOVE) {
			if (action == 0) {
			 
				// 按下的是滑块
				if (Measure.isInnerBorder(x, y, points[0], points[1], points[0]
						+ width, points[1] + height)) {
					state = 0;
					ispress=true;
				} else if (Measure.isInnerBorder(x, y, reduceX, 0,
						reduceX + width1, height)) {
					// 按下的是减
					state = 1;
					draw();
				} else if (Measure.isInnerBorder(x, y,addX, 0,
						addX + width1,height)) {
					// 按下的是加
					state = 2;
					draw();
				} else if (Measure.isInnerBorder(x, y, maxX, 0,
						maxX+ width2,height)) {
					// 按下的是最大
					state = 3;
					draw();
				}
			} else if (action == 1) {
				// 弹起
				if(state ==1){
					int xx=points[0]-step;
					setHkPoint(xx);
				}else if(state ==2){
					int xx=points[0]+step;
					setHkPoint(xx);
				}else if(state ==3){
					points[0]=max;
				}
				if (state != 0) {
					state =-1;
					draw();
					return true;
				}
			}
		} else {
			// 移动
			if (state == 0) {
			 
				setHkPoint((int)x);
				draw();
				return true;
			}
		}
		return true;
	}
	/**
	 * 设置滑块的x坐标
	 * @param x
	 */
	private void setHkPoint(int x){
		if (x < min) {
			x = min;
		} else if (x > max) {
			x = max;
		}
		points[0] =  x;
	}
	/**
	 * 释放资源
	 */
	public void destroy() {
	     this.destroyDrawingCache();
		 GameUtil.recycle(game_gamer_max);
		 game_gamer_max =null;
		 GameUtil.recycle(game_gamer_maxs);
		 game_gamer_maxs =null;
		 GameUtil.recycle(game_gamer_reduce);
		 game_gamer_reduce=null;
		 GameUtil.recycle(game_gamer_reduces);
		 game_gamer_reduces=null;
		 GameUtil.recycle(game_gamer_add);
		 game_gamer_add =null;
		 GameUtil.recycle(game_gamer_adds);
		 game_gamer_adds =null;
		 GameUtil.recycle(game_gamer_bar);
		 game_gamer_bar=null;
		 GameUtil.recycle(game_gamer_fkbg);
		 game_gamer_fkbg=null;
		 GameUtil.recycle(cacheBitmap);
		 cacheBitmap=null;
		 GameUtil.recycle(game_chg_bg3);
		 game_chg_bg3=null;
		 
		 buyChouMa =null;
		 //paint =null;
		 //points=null;
		  
	}
}
