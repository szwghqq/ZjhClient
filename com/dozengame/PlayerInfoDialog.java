package com.dozengame;

import java.util.HashMap;

import com.dozengame.gameview.GameView;
import com.dozengame.gameview.PlayerInfoView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;

public class PlayerInfoDialog extends Dialog {
	FrameLayout frameLayout;
	GameView gv;
	  PlayerInfoView pv;
	public PlayerInfoDialog(Context context,int theme,int playerIndex,HashMap data,final GameView gv) {
		super(context,theme);
		setContentView(R.layout.playerinfomain1);
		final ColorDrawable drawable =new ColorDrawable(0);
		this.getWindow().setBackgroundDrawable(drawable);
		frameLayout=(FrameLayout)findViewById(R.id.mainLayout);
		this.gv= gv;
		// 弹起
		pv =new PlayerInfoView(context,playerIndex,data);
	    frameLayout.addView(pv);
		
		this.setOnShowListener(new OnShowListener(){
			public void onShow(DialogInterface dialog) {
				TranslateAnimation(pv);
			}
		});
		this.setOnDismissListener(new OnDismissListener(){

			@Override
			public void onDismiss(DialogInterface dialog) {
				gv.isShowPlayInfo =false;
				gv.state =0;
			    pv.destroy();
			}
			
		});
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		 
		// super.onTouchEvent(event);
		 return pv.click(event.getAction(), event.getX(), event.getY());
	 
	}
	
	/**
	 * 位移动画
	 * @param gpv
	 */
	private   void TranslateAnimation(PlayerInfoView gpv){
		
		// currentgpv=gpv;
		// context.addView(currentgpv); 
		ScaleAnimation   mScaleAnimation = null;
		float x=0,y=0;
		float [] point=gpv.getPoint();
		x=point[0];
		y=point[1];
	 
		mScaleAnimation =   new ScaleAnimation(0.0f, 1.0f, 0.0f,1.0f,Animation.RELATIVE_TO_SELF,x,Animation.RELATIVE_TO_SELF,y);
		//mScaleAnimation =   new ScaleAnimation(0.0f, 1.0f, 0.0f,1.0f,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,1.0f);
		//设置时间持续时间为3000 毫秒=3秒
		mScaleAnimation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation animation) {
				gv.state=1;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				 
			}

			@Override
			public void onAnimationStart(Animation animation) {
				gv.state=2;
			}
			
		});
		mScaleAnimation.setDuration(500);
		gpv.startAnimation(mScaleAnimation);	
	}

}
