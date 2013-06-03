package com.dozengame.gameview;

import java.io.IOException;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.dozengame.R;
import com.dozengame.util.GameUtil;

public class GamePkShowDialog extends Dialog {
 
	public GamePkShowDialog(Context context, int theme) {
		super(context, theme);
 	    this.setContentView(R.layout.gamepkshowview);
 	    Window w=this.getWindow();
		try {
			final Bitmap bit = BitmapFactory.decodeStream(context.getAssets().open("hall_pkpage.png"));
		    w.setBackgroundDrawable(new BitmapDrawable(bit));
		   this.setOnDismissListener(new OnDismissListener(){
			public void onDismiss(DialogInterface dialog) {
				GameUtil.recycle(bit);
			}
			
		});
		} catch (IOException e) {
		}
		 WindowManager.LayoutParams lp = w.getAttributes();
		 lp.x = 305;
		 lp.y = -32;
  
	}
	 public boolean onTouchEvent(MotionEvent event) {
	        float x= event.getX();
	        float y= event.getY();
	        if(x>=0 && x<=338){
	        	if(y >=0 && y<=570){
	        		return false;
	        	}
	        }
	        dismiss();
	    	return true;
	    }
}
