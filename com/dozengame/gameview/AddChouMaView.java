package com.dozengame.gameview;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;

import com.dozengame.GameApplication;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
import com.dozengame.util.Measure;

/**
 * 加注筹码
 * 
 * @author hewengao
 */
public class AddChouMaView extends View {

	Bitmap hall_fill_bar;
	Bitmap hall_fill_bar_s;
	Bitmap hall_fill_bg;
	Bitmap hall_fill_bg1;
	Bitmap hall_fill_bg2;
	Bitmap hall_fill_butallin;
	Bitmap hall_fill_butallins;
	Bitmap cacheBitmap = null;
	Paint paint = new Paint();
	Canvas canvas = null;
	//final int width = 322;
	//final int height = 500;
	static final int[] barPointbak = { 5, 425 };
	static final int[] barPoint = { 0, 385 };
	static final int[] barPointX = { 170+25, 295 };
	static final int[] barPointX2 = {5+25, 150 };
	static final int barHeight=108;
	//static final int barHeight=68;
	static final int barMinH = 75;
	static   int barMaxH = 385;
	//static final int barMaxH = 425;
	//static final int[] xx = { 600, 50 };
	int state = -1;
	int maxGold;//最大金额
	int minGold;//最小金额
	float dwGold;//单位金额
	int defaultGold;//默认金额
	 
	AddChouMaViewDialog dialog;
	public AddChouMaView(Context context,AddChouMaViewDialog dialog) {
		super(context);
		this.dialog=dialog;
//		cacheBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
//		canvas = new Canvas();
//		canvas.setBitmap(cacheBitmap);
		initBitmap();
		initData();
 
	}
	public void initData(){
		maxGold=GameApplication.getDzpkGame().gameBottomView.max;
		defaultGold =minGold=GameApplication.getDzpkGame().gameBottomView.min;
		 
		 
		if(minGold == maxGold){
			barPoint[1]=barMinH;
			return;
		}
		dwGold=(float)((maxGold-minGold)/310.0);
		//计算当前滑块坐标
		float temp=((defaultGold-minGold)/dwGold);
		temp =barMaxH-temp;
		
		try {
			barPoint[1]=DecimalFormat.getInstance().parse(temp+"").intValue();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(barPoint[1] <barMinH){
			barPoint[1]=barMinH;
		}
		else if(barPoint[1] <barMaxH){ 
			barPoint[1]=barMaxH;
		}else{
			barMaxH=barPoint[1];
		}
	} 

	/**
	 * 初始化需要用到的图片
	 */
	private void initBitmap() {
		try {
			hall_fill_bar = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_fill_bar.png"));
			hall_fill_bar_s = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_fill_bar_s.png"));
			hall_fill_bg = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_fill_bg.png"));

			hall_fill_butallin = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_fill_butallin.png"));
			hall_fill_butallins = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_fill_butallins.png"));
			hall_fill_bg1 = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_fill_bg1.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw() {
	 
		if(minGold != maxGold){
		   
			if(barPoint[1]>= barMaxH){
			    defaultGold =minGold;
			     
			    
			}else if(barPoint[1]<= barMinH){
				defaultGold =maxGold;
	 
			}else{

				float temp=((barPoint[1]-barMinH)*dwGold);
				temp = maxGold-temp;
				
				try {
					defaultGold=DecimalFormat.getInstance().parse(temp+"").intValue();
					 
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
			//buyChouMa.setBuyChouMa(gold);
		}
		
 		postInvalidate();

	}
    String s="$";
    String AllIn="All In";
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(hall_fill_bg, 148+25, 16, null);
		 
		
		int h=385-barPoint[1]-15;
		if(h > 0){
			hall_fill_bg2=GameBitMap.resizeBitmap(hall_fill_bg1, hall_fill_bg1.getWidth(), h);
			canvas.drawBitmap(hall_fill_bg2, 221+25, barPoint[1]+100, null);
		}
		if(state ==2){
			canvas.drawBitmap(hall_fill_bar_s, barPoint[0], barPoint[1], null);
		}else{
			canvas.drawBitmap(hall_fill_bar, barPoint[0], barPoint[1], null);
		}
		// 绘制AllIn按钮
		if(state ==3){
			canvas.drawBitmap(hall_fill_butallins, 148+25, 16, null);
		}else{
			canvas.drawBitmap(hall_fill_butallin, 148+25, 16, null);
		}
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		 
		paint.setTextSize(30.0f);

		canvas.drawText(AllIn,205+25, 55, paint);
		String temp = s+defaultGold;
		//temp=s+2000000000;
		paint.setColor(Color.BLACK);
		paint.setTextSize(32.0f);
		int ah=28;
		if(temp.length()>9){
			paint.setTextSize(30.0f);
			//ah =10;
		}
		float w= paint.measureText(temp);
		 w=(148)/2-w/2+10;
		 if(w <28){
			 w =28;
		 }
		 FontMetrics fm =paint.getFontMetrics();
		 h = (int)Math.ceil(fm.descent - fm.ascent);
		 
		 h=barHeight/2-h/2+ah;
		canvas.drawText(temp, w,barPoint[1]+h, paint);
        
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		float x = event.getX();
		float y = event.getY();
		int action = event.getAction();
       
		if (action == 0) {
            // 按下
			//是否点击的是滑块
			if (Measure.isInnerBorder(x, y, barPointX[0], barPoint[1], barPointX[1],  barPoint[1] + barHeight)) {
         					System.out.println("is down");
         		state =1;
			}else if(Measure.isInnerBorder(x, y, barPointX2[0], barPoint[1], barPointX2[1],  barPoint[1] + barHeight)){
				//点击的左边的鑫额块
				state =2;
				draw();
			}else if(Measure.isInnerBorder(x, y, 148+25,16, 148+182+25,  16+61)){
				//点击的是AllIn
				state =3;
				draw();
			}
		} else if (action == 1) {
			  
			// 弹起
			if(state ==1){
				 //下注当前鑫额
				GameApplication.getDzpkGame().gameBottomView.consoleButtonClick(3, defaultGold);
				dialog.dismiss();
			}else if(state ==2){
			    //下注当前鑫额
				GameApplication.getDzpkGame().gameBottomView.consoleButtonClick(3, defaultGold);
				dialog.dismiss();
			}else if(state ==3){
				//激发全下注
				GameApplication.getDzpkGame().gameBottomView.consoleButtonClick(4, maxGold);
				dialog.dismiss();
			}
			if (state != 0) {
				state =-1;
				draw();
				return true;
			}
		} else if (action == MotionEvent.ACTION_MOVE) {
			// 移动
			if (state == 1) {
				setHkPoint((int)y);
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
	private void setHkPoint(int y){
		if (y < barMinH) {
			y = barMinH;
		} else if (y > barMaxH) {
			y = barMaxH;
		}
		barPoint[1] = y;
	}
	/**
	 * 释放资源
	 */
	public void destroy(){
	 
		 GameUtil.recycle(hall_fill_bg1);
		 GameUtil.recycle(hall_fill_bg2);
		 GameUtil.recycle(hall_fill_bar);
		 GameUtil.recycle(hall_fill_bar_s);
		 GameUtil.recycle(hall_fill_bg);
		 GameUtil.recycle(hall_fill_butallin);
		 GameUtil.recycle(hall_fill_butallins);
		 GameUtil.recycle(cacheBitmap);
		 this.destroyDrawingCache();
		 paint=null;
	}
}
