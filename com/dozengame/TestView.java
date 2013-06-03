package com.dozengame;

import com.dozengame.util.GameBitMap;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class TestView extends View {

	public TestView(Context context) {
		super(context);
		GameBitMap.initAllMap(context);
	}
	int x=0;
	int y=0;
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for(int i=0; i<52;i++){
			canvas.drawBitmap(GameBitMap.getPokeBitMap(i), x, y, null);
			if(i%13==12){
				y =y+100;
				x=0;
			}else{
				x +=70;
			}
		}
	}

}
