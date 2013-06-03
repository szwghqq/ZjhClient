package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dozengame.GameApplication;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
import com.dozengame.util.Measure;

/**
 * 游戏按钮
 * 
 * @author hewengao
 * 
 */
public class GameButtonView extends View {

	static final int[][] buttonViewPoint = { { 10, 5 }, { 774, 5 } };
	int pos = 0;
	Bitmap cacheBitmap;
	Bitmap cacheBitmapPrev;
	Bitmap hall_back;
	Bitmap hall_stand;
	Bitmap hall_buttons;
	Bitmap hall_button;
	Canvas canvas;
	static final String roomText = "大厅";
	static final String standText = "站起";
	static final Paint pt = new Paint();
	boolean ispress = false;
	OnClickListener listener;

	public GameButtonView(Context context, int pos) {
		super(context);
		this.pos = pos;
		canvas = new Canvas();
		pt.setColor(Color.WHITE);
		pt.setAntiAlias(true);
		pt.setTextSize(30);
		
	}

	private void initBitmap(){
		hall_buttons=GameBitMap.getGameBitMap(GameBitMap.HALL_BUTTONS);
		hall_button=GameBitMap.getGameBitMap(GameBitMap.HALL_BUTTON);
		if(pos == 0){
		  hall_back=GameBitMap.getGameBitMap(GameBitMap.HALL_BACK);
		}else{
		  hall_stand=GameBitMap.getGameBitMap(GameBitMap.HALL_STAND);
		}
	}
	public void drawCacheBitmap() {
		initBitmap();
		cacheBitmapPrev =cacheBitmap;
		cacheBitmap = Bitmap.createBitmap(176, 70, Config.ARGB_8888);
		canvas.setBitmap(cacheBitmap);
		if (ispress){
			canvas.drawBitmap(hall_buttons, 0, 0,null);
		} else {
			canvas.drawBitmap(hall_button,0, 0, null);
		}
		if (pos == 0) {
			drawBackRoomBitmap();
		} else {
			drawStandBitmap();
		}
	}

	/**
	 * 绘制返回大厅
	 */
	public void drawBackRoomBitmap() {

		canvas.drawBitmap(hall_back, 0, 0,null);
		float w = pt.measureText(roomText);
		float h = pt.ascent();
		canvas.drawText(roomText, (176 - w) / 2 + 20, (70 - h) / 2 - 4, pt);
	}

	/**
	 * 绘制站起
	 */
	public void drawStandBitmap() {
		canvas.drawBitmap(hall_stand, 0,0, null);
		float w = pt.measureText(standText);
		float h = pt.ascent();
		canvas.drawText(standText, (176 - w) / 2 + 20, (70 - h) / 2 - 4, pt);
	}
    boolean drawRun =true;
	public void draw() {
		drawRun = true;
		postInvalidate();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(View.VISIBLE == this.getVisibility()){
			if(drawRun){
				drawRun =false;
				drawCacheBitmap();
				canvas.drawBitmap(cacheBitmap, buttonViewPoint[pos][0],buttonViewPoint[pos][1], null);
				destoryBitmap();
			}else{	
			 canvas.drawBitmap(cacheBitmap, buttonViewPoint[pos][0],buttonViewPoint[pos][1], null);
			}
		} 
	}
    boolean isclick= false;
	public boolean isIsclick() {
		return isclick;
	}

	public void setIsclick(boolean isclick) {
		this.isclick = isclick;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(GameApplication.getDzpkGame() ==null){
			Log.i("test1", "dzpkgame is null");
		}
		if(GameApplication.getDzpkGame().gameView ==null){
			Log.i("test1", "dzpkgame.gameView is null");
		}
		if (GameApplication.getDzpkGame().gameView.state == 1)
			return false;
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();

		if (isclick==false && Measure.isInnerBorder(x, y, buttonViewPoint[pos][0],
				buttonViewPoint[pos][1], buttonViewPoint[pos][0] + 176,
				buttonViewPoint[pos][1] + 70)) {

			if (action == 0) {
				ispress = true;
				draw();
				return true;
			} else if (action == 1) {
				isclick= true;
				draw();
				if (listener != null) {
					listener.onClick(this);
				}
				ispress = false;
				return true;
			}
		} else {
			if (ispress == true) {
				ispress = false;
				draw();
				return true;
			}
		}

		return false;
	}

	public void setOnClickListener(OnClickListener listener) {
		this.listener = listener;
	}
	public void destroy(){
		Log.i("test19", "GameButtonView destroy");
		GameUtil.recycle(cacheBitmap);
		cacheBitmap =null;
		destoryBitmap();
		canvas =null;
		listener =null;
	}
	private void destoryBitmap(){
		GameUtil.recycle(cacheBitmapPrev);
		cacheBitmapPrev =null;
		GameUtil.recycle(hall_button);
		hall_button =null;
		GameUtil.recycle(hall_buttons);
		hall_buttons =null;
		GameUtil.recycle(hall_back);
		hall_back =null;
		GameUtil.recycle(hall_stand);
		hall_stand =null;
	}
	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if(View.VISIBLE == visibility){
			isclick=false;
			draw();
		}
	}
}
