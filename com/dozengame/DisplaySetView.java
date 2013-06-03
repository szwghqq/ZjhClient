package com.dozengame;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dozengame.util.Measure;

/**
 * 房间隐藏显示设置
 * 
 * @author hewengao
 * 
 */
public class DisplaySetView extends View {

	Paint paint = new Paint();
	Bitmap hall_menu_but;
	Bitmap hall_menu_buts;
	Bitmap cacheBitmap;
	Canvas cacheCanvas;
	int state = 0;

	public DisplaySetView(Context context) {
		super(context);
		initBitmap();
	}
	 public DisplaySetView(Context context,AttributeSet paramAttributeSet) {
	  super(context,paramAttributeSet);
	 }

	private void initBitmap() {
		try {
			hall_menu_but = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_menu_but.png"));
			hall_menu_buts = BitmapFactory.decodeStream(this.getContext()
					.getAssets().open("hall_menu_buts.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw() {
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (points[0] < 87) {
			state = 0;
		} else {
			state = 1;
		}
		switch (state) {
		case 0:
			drawGuan(canvas);
			break;
		case 1:
			drawKai(canvas);
			break;
		default:
			break;
		}
	}

	private void drawKai(Canvas canvas) {
		paint.setTextSize(30.0f);
		paint.setColor(Color.WHITE);
		canvas.drawText("开", 60, 40, paint);
		canvas.drawBitmap(hall_menu_but, points[0], 0, paint);
	}

	private void drawGuan(Canvas canvas) {
		paint.setTextSize(30.0f);
		paint.setColor(Color.WHITE);
		canvas.drawText("关", 180, 40, paint);
		canvas.drawBitmap(hall_menu_buts, points[0], 0, paint);

	}

	int[] points = { 25, 0 };
	int width = 116;
	int height = 56;
	int min = 25;
	int max = 150;
	boolean ispress = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		float x = event.getX();
		float y = event.getY();
	 
 		int action = event.getAction();
 	 
		if (action != MotionEvent.ACTION_MOVE) {
			if (action == 0) {
				// 按下
				if (Measure.isInnerBorder(x, y, points[0], points[1], points[0]
						+ width, points[1] + height)) {
					System.out.println("is down");
					ispress = true;
				}
			} else if (action == 1) {
				// 弹起
				if (ispress) {
					System.out.println("is up");
					ispress = false;
					if (points[0] < 87) {
						points[0] = 25;
					} else {
						points[0] = 150;
					}
					draw();
					return true;
				}

			}
		} else {
			
			// 移动
			if (ispress) {
				System.out.println("is move");
				if (x < min) {
					x = min;
				} else if (x > max) {
					x = max;
				}
				points[0] = (int) x;
				draw();
				return true;
			}
		}
		return true;
	}

}
