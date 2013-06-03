package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.View;

import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;

/**
 * 游戏全下动画
 * 
 * @author hewengao
 * 
 */
public class GameAllInView1 extends View {
	//绘制动画起点坐标
	static final int[][] point = { { 425, 390 }, {220, 390 }, { 30,300 }, { 65, 100 },
			{ 295, 40 }, { 530, 40 }, { 755, 130 }, { 793, 300 },
			{ 600, 390 } };
 
	Bitmap cacheBitmap;
	int pos;
	 
	public GameAllInView1(Context context, int pos) {
		super(context);
		this.pos = pos;
	 
		if(pos <5){
	    	cacheBitmap = GameBitMap.getGameBitMap(GameBitMap.GAME_ALLIN_RIGHT);
	     
	    }else{
	    	cacheBitmap = GameBitMap.getGameBitMap(GameBitMap.GAME_ALLIN_LEFT);
	    }
		 this.setVisibility(View.INVISIBLE);
	}
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(this.getVisibility() == View.VISIBLE){
		    canvas.drawBitmap(cacheBitmap, point[pos][0], point[pos][1], null);
		}
	}

	/**
	 * 启用动画
	 */
	public void startAnim() {
	    
	    TaskManager.getInstance().execute(new TaskExecutorAdapter(){

			@Override
			public int executeTask() throws Exception {
				try{
					handler.sendEmptyMessage(0);
					Thread.sleep(2000);
				}catch(Exception e){
					
				}finally{
					handler.sendEmptyMessage(1);
				}
				
				return 0;
			}
	    	
	    });
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what ==0){
				 setVisibility(View.VISIBLE);
			}else{
				setVisibility(View.INVISIBLE);
			}
		};
	};
 
	
	   /**
	 * 释放资源
	 */
	public void destroy(){
		destroyBitmap();
		this.destroyDrawingCache();
		 
 
	}
	
	private void destroyBitmap(){
 
		 GameUtil.recycle(cacheBitmap);
		 cacheBitmap =null;
	}
	 
}
