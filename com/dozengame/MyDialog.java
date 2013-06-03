package com.dozengame;

import com.dozengame.gameview.GameJsqViewManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class MyDialog extends Dialog {
	
	public FrameLayout frameLayout;
	GameJsqViewManager jsqViews;
	DzpkGameActivity context;
	public MyDialog(final DzpkGameActivity context,int theme) {
		 super(context,theme);
		 this.context =context;
	     setContentView(R.layout.dialog);
	     frameLayout=(FrameLayout)findViewById(R.id.layout);
		 DisplayMetrics dm = new DisplayMetrics();   
		 this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm); 
		 frameLayout.getLayoutParams().height =dm.heightPixels;
		 frameLayout.getLayoutParams().width =dm.widthPixels; 
		 jsqViews = new GameJsqViewManager(context);
		 final ColorDrawable drawable =new ColorDrawable(0);
		 this.getWindow().setBackgroundDrawable(drawable);
		 this.setOnDismissListener(new OnDismissListener(){
				@Override
				public void onDismiss(DialogInterface dialog) {
				  // destroy();
					context.finish();
				}
				
			});
	}
	int count =-1;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 Log.i("test11", "onKeyDown onKeyDown keyCode: "+keyCode);
		 if(keyCode == KeyEvent.KEYCODE_MENU){
			 count ++;
			 Log.i("test11", "onKeyDown 1111111111111111");
			 if(count>2){
			  context.onKeyDown(keyCode, event);
			 }
			 if(count ==9)count=0;
			  jsqViews.setJsqSiteNo(count, 21, 21);
			  return false;
		 }else if(keyCode == KeyEvent.KEYCODE_BACK){
			 dismiss();
			 context.finish();
		 }
		return super.onKeyDown(keyCode, event);
	}

}
