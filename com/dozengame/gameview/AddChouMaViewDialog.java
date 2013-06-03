package com.dozengame.gameview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.dozengame.R;
/**
 * 添加筹码对话框
 * @author hewengao
 *
 */
public class AddChouMaViewDialog extends Dialog {
	AddChouMaView addChouMaView;
	public AddChouMaViewDialog(Context context,int theme) {
		super(context,theme);
		this.setContentView(R.layout.addchoumaview);
		LinearLayout layout=(LinearLayout)findViewById(R.id.layout);
		addChouMaView = new AddChouMaView(context,this);
		layout.addView(addChouMaView);
		Window w=this.getWindow();
		 
		w.setBackgroundDrawable(new ColorDrawable(0));
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x =260;
		this.setOnDismissListener(new OnDismissListener(){

			@Override
			public void onDismiss(DialogInterface dialog) {
				destroy();
			}
		});
	}
	 public boolean onTouchEvent(MotionEvent event) {
	        float x= event.getX();
	        float y= event.getY();
 
	        if(x>=0 && x<=322){
	        	if(y >=0 && y<=500){
	        		return false;
	        	}
	        }
	        dismiss();
	    	return true;
	    }
	 
	 /**
		 * 释放资源
		 */
		public void destroy(){
			
			if(addChouMaView != null){
				addChouMaView.destroy();
			}
			addChouMaView=null;
			System.gc();
		}
}
