package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.dozengame.GameApplication;
import com.dozengame.music.MediaManager;
import com.dozengame.util.GameUtil;


/**
 * 计时器视图
 * 
 * @author hewengao
 * 
 */
public class GameJsqView extends View implements Runnable{

	static final int[][] jsqViewPoint = { { 394, 382 }, { 169, 382 }, { 0, 313 }, { 0, 90 },
			{ 231, -5 }, { 581, -5 }, { 804, 90 }, { 804, 313 }, { 630, 382 } };
	int pos = 0;
	Bitmap cacheBitmap;
	Bitmap cacheBitmapPrev;
	Canvas canvas;
	final static Paint paint = new Paint();
	final static int width = 158;
	final static int height = 196;
    int jsqType=0;//0：绿 1:黄 2:红
  
	public GameJsqView(Context context, int pos) {
		super(context);
		this.pos = pos;
	
		this.setVisibility(View.INVISIBLE);
		canvas = new Canvas();
		
	}

	public void drawCacheBitmap() {

		cacheBitmapPrev =cacheBitmap;
		cacheBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvas.setBitmap(cacheBitmap);
		switch(jsqType){
		case 0:
			paint.setColor(Color.GREEN);
			break;
		case 1:
			paint.setColor(Color.YELLOW);
			break;
		case 2:
			paint.setColor(Color.RED);
			break;
		}
	
		paint.setStrokeWidth(10);
		
		paint.setAntiAlias(true);
		// canvas.drawLine(78, 11, 136, 11, paint);//上 右
		if(state <=0){
		   canvas.drawLine(startCurrent,11,start[1], 11, paint);// 上 右
		}
		
		// canvas.drawLine(134, 10, 148, 20, paint);//右上角
		if(state <= 1){
		   canvas.drawLine(start1Current,start1yCurrent,start1[1],start1y[1], paint);//右上角
		}
		
		// canvas.drawLine(146, 18, 146, 188, paint);//右
		if(state <= 2){
		 canvas.drawLine(146,start2Current,146,start2[1], paint);//右
		}
	
		//canvas.drawLine(146, 186, 138, 197, paint);//右下角
		if(state <= 3){
		 canvas.drawLine(start3Current,  start3yCurrent, start3[1],start3y[1], paint);//右下角
		}
		
		// canvas.drawLine(20, 195, 140, 195, paint);//下
		if(state <=4){
		 canvas.drawLine(start4Current , 185, start4[1], 185, paint);//下
		}
 
		//canvas.drawLine(22, 197, 12, 186, paint);//左下角
		if(state <=5){
		 canvas.drawLine(start5Current , start5yCurrent,start5[1] , start5y[1], paint);//左下角
		}
		         
	    // canvas.drawLine(12, 188, 12, 18, paint);//左
		if(state <=6){
		 canvas.drawLine(12,start6Current,12,start6[1], paint);//左
		}
		
		// canvas.drawLine(12, 20, 22, 11, paint);//左上角
		if(state <= 7){
		 canvas.drawLine(start7Current,start7yCurrent ,  start7[1],  start7y[1], paint);//左上角
		}
		
		// canvas.drawLine(20, 11, 78, 11, paint);//上左
		if(state <= 8){
		canvas.drawLine(start8Current, 11, start8[1], 11, paint);//上左
		}
	}
    
	int state = 0;
	//上右
	float[] start = { 78, 138 };
	float startCurrent = start[0];
	
	//上右角
	float[] start1 = { 136, 148 };
	float start1Current = start1[0];
	float[] start1y = { 10, 22 };
	float start1yCurrent = start1y[0];
	
	//右
	float []start2 ={17,179};
	float start2Current = start2[0];
	
	//右下角
	float[] start3 = { 148, 136 };
	float start3Current = start3[0];
	float[] start3y = { 176, 186 };
	float start3yCurrent = start3y[0];
	
	//下
	float []start4 ={138,20};
	float start4Current = start4[0];
	 
	//左下角
	float[] start5 = { 22, 10 };
	float start5Current = start5[0];
	float[] start5y = { 187, 174 };
	float start5yCurrent = start5y[0];
	
	//左
	float []start6 ={178,18};
	float start6Current = start6[0];
	
	//左上角12, 20, 22, 11
	float[] start7 = { 10, 22 };
	float start7Current = start7[0];
	float[] start7y = { 20, 10 };
	float start7yCurrent = start7y[0];
	 
	//上左
	float[] start8 = { 20, 78 };
	float start8Current = start8[0];
	float step = 1.76f;
     
    int timeOut=20000;//还剩多长时间
   
    private void init(){
    	startCurrent = start[0];
    	start1Current = start1[0];
    	start1yCurrent = start1y[0];
    	start2Current = start2[0];
    	start3Current = start3[0];
    	start3yCurrent = start3y[0];
    	start4Current = start4[0];
    	start5Current = start5[0];
    	start5yCurrent = start5y[0];
    	start6Current = start6[0];
    	start7Current = start7[0];
    	start7yCurrent = start7y[0];
    	start8Current = start8[0];
    	state=0;
    	jsqType=0;
    	sleepTime = (delay*1000)/360;
		count=(timeOut*1000)/sleepTime;
		 
		int temp = count;
		if (temp > 325) {
			state = 0;
			startCurrent += (360-temp) * step;
		} else if (temp > 318) {
			state = 1;
			start1Current += (325-temp) * step;
			start1yCurrent += (325-temp) * step;
		} else if (temp > 220) {
			state = 2;
			start2Current += (318-temp) * step;
		} else if (temp > 213) {
			state = 3;
			start3Current -= (220 - temp) * step;
			start3yCurrent += (220 - temp) * step;
		} else if (temp > 145) {
			state = 4;
			start4Current -= (213 - temp) * step;
			if(start4Current < 78){
				jsqType =1;
				
			}
		} else if (temp > 138) {
			jsqType =1;
			state = 5;
			start5Current -= (145 - temp) * step;
			start5yCurrent -= (145 - temp) * step;
		} else if (temp > 41) {
			jsqType =1;
			state = 6;
			start6Current -= (138 - temp) * step;
			if(start6Current <103){
				jsqType =2;
			}
		} else if (temp > 34) {
			
			state = 7;
			jsqType =2;
			start7Current += (41 - temp) * step;
			start7yCurrent -= (41 - temp) * step;
		} else if (temp > 0) {
			state = 8;
			jsqType =2;
			start8Current += (34 - temp) * step;
		} else {
			state = 9;
		}
		if(jsqType > 0){
			playMusic(true);
		}
        if(jsqType == 2){
        	//超时预警
			timeOutYuJinMsg();
        }
		 
    }
  //控制重绘
	boolean drawRun =true;
	public void draw() {
		drawRun =true;
		postInvalidate();
	}
 
	protected void onDraw(Canvas canvas) {
		 //super.onDraw(canvas);
		   paint(canvas);
	}

	boolean isRun = false;
    int sleepTime;
    int count =0;
   // int counts [] ={35,7,98,7,68,7,97,7,34};
    /**
     * 开始计时器
     * @param timeOut:剩余时间
     * @param delay:总时间
     */
    int delay;
   
	public void start(int timeOut,int delay) {
		 
		this.timeOut =timeOut;
		this.delay =delay;
		//if(isSame == false){
		  starts();
		//}
	}
	Thread currentThread;
	/**
	 * 开始
	 */
	private void starts(){
        if(GameApplication.getDzpkGame() != null){
        	//判断点了返回游戏大厅
        	if(!GameApplication.getDzpkGame().isBackClick){
        		currentThread =new Thread(this);
        		currentThread.start();
        	}
        }
	}
  
	boolean stop =false;
	public   void stop(){
		//this.setVisibility(View.INVISIBLE);
		isRun = false;
		//stop =true;
		interrupt();
		this.setVisibility(View.INVISIBLE);
	}
	private void interrupt(){
		if(currentThread != null){
			if(!currentThread.isInterrupted()){
			 currentThread.interrupt();
			}
		}
	}
	public   void stops(int timeOut, int delay) {
		this.timeOut =timeOut;
		this.delay =delay;
		isRun = false;
		//stop =true;
		interrupt();
		starts();
	}
	@Override
	public void run() {
		try {
			sendMsg(2);
			init();
			isRun = true;
			while (isRun) {
				switch (state) {
				case 0:
					startCurrent += step;
					if (startCurrent > start[1]) {
						startCurrent = start[1];
						state =1;
					}
				
					break;
				case 1:
					start1Current += step;
					start1yCurrent+= step;
					if (start1yCurrent > start1y[1]){
						start1yCurrent = start1y[1];
					}
					if (start1Current > start1[1]){
						start1Current = start1[1];
						state =2;
					}
				
					break;
				case 2:
					start2Current += step;
					if (start2Current > start2[1]) {
						start2Current = start2[1];
						state =3;
					}
				
					break;
				case 3:
					start3Current -= step;
					start3yCurrent+= step;
					if (start3yCurrent > start3y[1]){
						start3yCurrent = start3y[1];
					}
					if (start3Current < start3[1]){
						start3Current = start3[1];
						state =4;
					}
				
					break;
				case 4:
					start4Current -= step;
					 
					if (start4Current < start4[1]){
						start4Current = start4[1];
						state =5;
					}
					if(start4Current < 78){
						playMusic(false);
						jsqType =1;
						
					}
					
					break;
				case 5:
					start5Current -= step;
					start5yCurrent-= step;
					if (start5yCurrent < start5y[1]){
						start5yCurrent = start5y[1];
					}
					if (start5Current < start5[1]){
						start5Current = start5[1];
						state =6;
					}
					
					break;
				case 6:
					start6Current -= step;
					if (start6Current < start6[1]){
						start6Current = start6[1];
						state =7;
					}
					if(start6Current <103){
						jsqType =2;
						//超时预警
						timeOutYuJinMsg();
					}
					
					break;
				case 7:
					start7Current += step;
					start7yCurrent-= step;
					if (start7yCurrent < start7y[1]){
						start7yCurrent = start7y[1];
					}
					if (start7Current > start7[1]){
						start7Current = start7[1];
						state =8;
					}
					
					break;
				case 8:
					start8Current += step;
					if (start8Current > start8[1]){
						start8Current = start8[1];
						state =9;
					}
					
					break;
				}
			    if(isRun ==true){
					sendMsg(0);
					Thread.sleep(sleepTime);
					if(state==9){
						isRun=false;
						break;
					}
			    }
			}
			sendMsg(1);
		 
		} catch (Exception e) {
			isRun=false;
			sendMsg(1);
			//e.printStackTrace();
		}
	}

	private void sendMsg(int s) {
		Message msg=handler.obtainMessage();
		msg.what=s;
		handler.sendMessage(msg);
	}
    int what =0;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				what =0;
			    draw();
			}else if(msg.what ==2){
			   setVisibility(View.VISIBLE); 
			}else{
			   setVisibility(View.INVISIBLE); 
			}
		}
	};

	/**
	 * 超时
	 */
	public void timeOut(){
		
	}
	/**
	 * 超时预警
	 */
	public void timeOutYuJinMsg(){
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		GameUtil.recycle(cacheBitmapPrev);
		cacheBitmapPrev =null;
		GameUtil.recycle(cacheBitmap);
		cacheBitmap =null;
 		canvas =null;
		destroyDrawingCache();
		 
	}

	protected void paint(Canvas canvas){
		if(View.VISIBLE == this.getVisibility()){
			if(drawRun){
				drawRun =false;
				drawCacheBitmap();
				canvas.drawBitmap(cacheBitmap, jsqViewPoint[pos][0],
						jsqViewPoint[pos][1], null);
				GameUtil.recycle(cacheBitmapPrev);
			}else{
			  canvas.drawBitmap(cacheBitmap, jsqViewPoint[pos][0],
						jsqViewPoint[pos][1], null);
			}
	   }
	}
	/**
	 * 播放自己超时过半声音
	 */
	private void playMusic(boolean bl){
		if(bl == false && jsqType !=0 )return;
		if(pos ==0 && GameApplication.getDzpkGame().playerViewManager.mySite){
			 //振动一下
			 GameApplication.getDzpkGame().startVibrate(100);
			 //播放自己超时过半声音
			 MediaManager.getInstance().playSound(MediaManager.turnreminder);
		}
	}
}
