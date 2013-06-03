package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dozengame.GameApplication;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
import com.dozengame.util.Measure;
/**
 * 坐位视图
 * 主要功能坐下按钮的显示
 * 房间人数未满
 * 只有当游戏在未开始及自己未坐下的状态下才需要绘制
 * 且已有人坐座位不需要绘制
 * @author hewengao
 */
public class SitView extends View {
	
	static final int[][] sitPoint ={{427,440},{213,440},{32,365},{32,120},{260,45},{600,45},{825,125},{825,360},{654,440}};
 
    int clickIndex=-1;//点击的索引
	boolean ispress=false;
	public static int  siteIndex=-1;//点击的索引
    int sitWidth,sitHeight;//坐下图宽高
    Bitmap HALL_SITDOWN, HALL_SITDOWNS;
    Bitmap cacheBitmap=null;
    boolean bitmapIsInit =false;
    Canvas c = null;
    boolean isNeedDraw =true;//是否需要绘制坐位
	public SitView(Context context) {
		super(context);
	    c = new Canvas();
	    setVisibility(View.INVISIBLE);
	}
	/**
	 * 初始化数据
	 */
    private void init(){
    	if(bitmapIsInit ==false){
	    	HALL_SITDOWN=GameBitMap.getGameBitMap(GameBitMap.HALL_SITDOWN);
	    	HALL_SITDOWNS=GameBitMap.getGameBitMap(GameBitMap.HALL_SITDOWNS);
	    	
			sitWidth=HALL_SITDOWN.getWidth();
			sitHeight=HALL_SITDOWN.getHeight();
			bitmapIsInit =true;
    	}
    }
    boolean drawRun =true;
	/**
	 * 重绘
	 */
	private void draw(){
		drawRun =true;
		this.postInvalidate();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		try{
		  if(isNeedDraw && View.VISIBLE ==this.getVisibility()){
			    if(drawRun){
				    drawRun =false;
				    init();
				    GameUtil.recycle(cacheBitmap);
				    cacheBitmap= Bitmap.createBitmap(960,640, Config.ARGB_4444);
				    c.setBitmap(cacheBitmap);
					//游戏未开始需要绘制座位
			    	for (int i = 0; i < 9; i++) {
			    		 if(clickIndex==i){
			               drawSit(c, true, i);
			    		 }
			    		 else{
			    		   drawSit(c, false, i);
			    		 }
					}
			     
			    }
			  canvas.drawBitmap(cacheBitmap, 0, 0, null);
		  }
		}catch(Exception e){
			Log.e(GameUtil.ERRORTAG, "msg: "+e.getMessage());
			e.printStackTrace();
		}catch(java.lang.OutOfMemoryError oom){
			Log.e(GameUtil.ERRORTAG, "OutOfMemoryError msg: "+oom.getMessage());
			oom.printStackTrace();
		}
    	 
	}
	
	/**
	 * 绘制座位
	 * @param canvas
	 * @param isClick 是否点击了
	 * @param n 第几个座位
	 */
	private void drawSit(Canvas canvas,boolean isClick, int n) throws Exception{
		//当前坐位无人坐时需要绘制
	    if(!GameApplication.getDzpkGame().playerViewManager.isSited(n)){
			int siteX=sitPoint[n][0];//座位坐标x
			int siteY=sitPoint[n][1];//座位坐标y
			Bitmap bitmap=null;
			if(isClick){
				//点击了
				bitmap=HALL_SITDOWNS;
			}else{
				bitmap=HALL_SITDOWN;
			}
			canvas.drawBitmap(bitmap, siteX, siteY, null);
	   }
	}
	
	 @Override
	public boolean onTouchEvent(MotionEvent event) {
		 if(isNeedDraw){
			 
			 return clickSitDown(event.getAction(),event.getX(),event.getY());
		 }else{
			 return false;
		 }
	}

	
	/**
	 * 点击坐下
	 * @param xx
	 * @param yy
	 */
	public boolean clickSitDown(int action,float xx ,float yy){
		for(int i = 0; i < 9; i++) {
			 //当前位置没人坐才可以点
			if(GameApplication.getDzpkGame().playerViewManager.isSited(i)){
			        continue;
			}
			if(Measure.isInnerBorder(xx, yy, sitPoint[i][0], sitPoint[i][1], sitPoint[i][0]+sitWidth, sitPoint[i][1]+sitHeight)){
					if(action==0){
						ispress =true;
						//按下
						if(clickIndex !=i){
					    	clickIndex=i;
							draw();
						}
					}else if(action ==1){
						//弹起
						ispress =false;
						clickIndex=-1;
						draw();
						if(clickSit==false && !GameApplication.getDzpkGame().playerViewManager.mySite){
							//表示自己未坐下,发送购买筹码命令
							siteIndex =i;
							
							sendRequestTXNTBC();
						}
						
					}
					return true;
			}else{
				
				if(action ==1 && ispress ==true){
					ispress=false;
					clickIndex =-1;
					draw();
				}
			}
		}
		return false;
	}
	public boolean clickSit =false;
	/**
	 * 发送购买筹码命令
	 */
	private  void sendRequestTXNTBC(){
		clickSit =true;
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			public int executeTask() throws Exception {
				GameApplication.getDzpkGame().gameDataManager.GameOver =false;
				GameApplication.getSocketService().sendRequestTXNTBC();
				return 0;
			}
			
		}); 
	}
	/**
	 * 释放资源
	 */
	public void destroy(){
		Log.i("test19", "SitView destroy");
		 destroyBitmap();
		 destroyDrawingCache();
		 GameUtil.recycle(cacheBitmap);
		 cacheBitmap =null;
	}
	private void destroyBitmap(){
		 GameUtil.recycle(HALL_SITDOWN);
		 HALL_SITDOWN =null;
		 GameUtil.recycle(HALL_SITDOWNS);
		 HALL_SITDOWNS =null;
		 bitmapIsInit =false;
	}
	/**
	 * 重写View可见性
	 */
	public void setVisibility(int visibility){
		super.setVisibility(visibility);
		if(View.INVISIBLE ==visibility){
			destroyBitmap();
			GameUtil.recycle(cacheBitmap);
			cacheBitmap =null;
			System.gc();
		 
		}else{
			draw();
		}
	}
	public void setNeedDraw(boolean isNeedDraw) {
		this.isNeedDraw = isNeedDraw;
		if(isNeedDraw){
			setVisibility(View.VISIBLE);
		}else{
			setVisibility(View.INVISIBLE);
		}
	}
}
