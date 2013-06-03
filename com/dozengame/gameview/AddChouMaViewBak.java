package com.dozengame.gameview;

import java.io.IOException;

import com.dozengame.util.GameUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.view.MotionEvent;
import android.view.View;

/**
 * 加注筹码
 * @author hewengao
 */
public class AddChouMaViewBak extends View {

	Bitmap hall_fill_bar;
	Bitmap hall_fill_bg;
	Bitmap hall_fill_butallin;
	Bitmap hall_fill_butallins;
	Paint paint = new Paint();
	Bitmap cacheBitmap = null;
	Canvas canvas = null;
	final int width=322;
	final int height=500;
	int [] barPoint ={5,431};
	int [] barPoint1 ={5,431};
	int barMinH =83;
	int barMaxH=431;
	int [] xx = {600, 50};
	public AddChouMaViewBak(Context context) {
		super(context);
		cacheBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvas = new Canvas();
		canvas.setBitmap(cacheBitmap);
		initBitmap();
		draw();
	}

	/**
	 * 初始化需要用到的图片
	 */
	private void initBitmap() {
		try {
			hall_fill_bar = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_fill_bar.png"));
			hall_fill_bg = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_fill_bg.png"));

			hall_fill_butallin = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_fill_butallin.png"));
			hall_fill_butallins = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_fill_butallins.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    public void draw(){
    	paint.setAlpha(1);
 		canvas.drawRect(new Rect(0, 0, width, height), paint);
 		//绘制背景
 		canvas.drawBitmap(hall_fill_bg, 148, 24, null);
 		canvas.drawBitmap(hall_fill_bar, barPoint[0],  barPoint[1], null);
 		//绘制AllIn按钮
 		canvas.drawBitmap(hall_fill_butallin, 148, 24, null);
 		//canvas.drawBitmap(hall_fill_butallin, 0, 0, paint);
 		paint.setColor(Color.WHITE);
 		paint.setTextSize(30.0f);
        canvas.drawText("$1000", barPoint[0]+30, barPoint[1], paint);
        postInvalidate();
    	
    }
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(cacheBitmap, xx[0], xx[1], null);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		float x = event.getX();
		float y = event.getY();
		int action = event.getAction();
        System.out.println("x: "+x+" y: "+y);
		if (action != MotionEvent.ACTION_MOVE) {
			if (action == 0) {
			  
			} else if (action == 1) {
				 
			}
		}else{
			 
		}
		return true;
	}
	/**
	 * 释放资源
	 */
	public void destroy(){
	 
		 GameUtil.recycle(hall_fill_bar);
		 GameUtil.recycle(hall_fill_bg);
		 GameUtil.recycle(hall_fill_butallin);
		 GameUtil.recycle(hall_fill_butallins);
		 GameUtil.recycle(cacheBitmap);
	}
}
