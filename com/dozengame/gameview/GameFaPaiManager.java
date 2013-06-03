package com.dozengame.gameview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.FaiPaiDialog;
import com.dozengame.GameApplication;
import com.dozengame.PlayerDialog;
import com.dozengame.music.MediaManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;

/**
 * 发牌动画管理
 * @author hewengao
 *
 */
public class GameFaPaiManager {
 
	static final int [] faPaiStartPoint={465,100};//发牌起点坐标
	//static final int [][] faPainEndPoint1={{546,385},{320,385},{150,323},{150,235},{383,145},{555,145},{780,235},{780,323},{605,385}};//发牌结束坐标
	static final int [][] faPainEndPoint={{81,295},{-145,295},{-315,223},{-315,135},{-82,45},{80,45},{315,135},{315,223},{140,295}};//发牌结束坐标
	static final GameFaPaiView [] fpViews =new GameFaPaiView[9];
	FaiPaiDialog context;
	Bitmap pokeBack;
	
	public GameFaPaiManager(FaiPaiDialog context){
		this.context=context;
		pokeBack = GameBitMap.getGameBitMap(GameBitMap.GAME_POKE_BACK);
		initAnim();
	}
	public void initAnim(){
		for(int i=0;i < 9;i++){
			fpViews[i] =new GameFaPaiView(context.getContext());
			context.frameLayout.addView(fpViews[i]);
		}
	}
	
	/**
	 * 启用发牌动画
	 */
	public void startFaPaiAnim(){
		try {
			int count =0;
			//得到庄家坐位号
			List list =GameApplication.getDzpkGame().gameDataManager.siteList;
			if(list != null && !list.isEmpty()){
				faiPaiIng = true;
				int size  =list.size();
				int siteNo;
				int index;
				 MediaManager.getInstance().playSound(MediaManager.deal2,size-1);
				 for(int i =0 ; i< size;i++){
					siteNo =(Byte)list.get(i);
					//根据座位号获取玩家在桌子上的索引号
				    index=GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
					if(i ==0){
					   //显示庄家
					  GameApplication.getDzpkGame().zjViewManager.setOtherViewVisibility(index, View.VISIBLE);
					}
					//发牌
				    translateAnimStart(fpViews[index],index,++count,size);
				} 
	 
				if(size ==2){
				    //小盲
					GameApplication.getDzpkGame().playerViewManager.setPlayerStateBySiteNo((Byte)list.get(0), 9);
					//大盲
					GameApplication.getDzpkGame().playerViewManager.setPlayerStateBySiteNo((Byte)list.get(1), 8);
					 
				}else if(size > 2){
					 //小盲
					GameApplication.getDzpkGame().playerViewManager.setPlayerStateBySiteNo((Byte)list.get(1), 9);
					//大盲
					GameApplication.getDzpkGame().playerViewManager.setPlayerStateBySiteNo((Byte)list.get(2), 8);
				}
			//list.clear();
		 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		//GameApplication.getDzpkGame().gameDataManager.siteList=null;
	}
	
	public boolean faiPaiIng = false;
	private  AnimationSet animSet =null;
	/**
     * 发牌动画
     * @param cmvStart
     * @param index
     * @param currGold
     */
    private void translateAnimStart(final GameFaPaiView faPaiView,final int index,final int count,final int size) throws Exception{
    	
    	//得到当前座位的下注筹码视图
		int x=faPainEndPoint[index][0];
		int y=faPainEndPoint[index][1];
		faPaiView.setVisibility(View.VISIBLE);
	    animSet = new AnimationSet(true);
	    final RotateAnimation rotate = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,
 				(float) 0.5, Animation.RELATIVE_TO_SELF, (float) 0.5);
 		rotate.setRepeatCount(-1);
 		
 		animSet.addAnimation(rotate);
	   // faPaiStartPoint
		TranslateAnimation am1 =   new TranslateAnimation(0, x, 0, y);
		animSet.addAnimation(am1);
		int time =0;
		//设置时间持续时间
		 if(index == 5 || index == 4){
			 time =300;
		 }else if(index == 3 || index ==6){
			 time =350;
		 }else if(index ==2 || index ==7){
			 time =400;
		 }else if(index ==1 || index ==8){
			 time =370;
		 }else{
		     time =350;
		 }
		 rotate.setDuration(time);
		 am1.setDuration(time);
		 am1.setInterpolator(new AccelerateInterpolator());//加速器
		 am1.setAnimationListener(new AnimationListener(){

				@Override
				public void onAnimationEnd(Animation animation) {
				
					rotate.cancel();
					faPaiView.setVisibility(View.INVISIBLE);
					if(GameApplication.getDzpkGame().playerViewManager.mySite && index ==0 && GameApplication.getDzpkGame().playerViewManager.zhunDongValue ==2){
						 //设置我的牌可见
					  //  GameApplication.getDzpkGame().myPoke.setVisibility(View.VISIBLE);
						 GameApplication.getDzpkGame().myPoke.startFanPaiAnim();
						 Log.i("test16", "接收牌");
						 GameApplication.getDzpkGame().toastPaiXingView.show();
					}else{
						//如果正在转桌不需要显示背牌
						if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){
						    //判断当前位置的玩家是否显示状态
							if(GameApplication.getDzpkGame().playerViewManager.getPlayerDisplayByIndex(index)){
							  GameApplication.getDzpkGame().pokeBackViewManager.setOtherViewVisibility(index, View.VISIBLE);
							}
						}
					}
					if(count == size){
						//表示发牌完毕
						faiPaiIng = false;
						//执行弃牌动作
						GameApplication.getDzpkGame().gameDataManager.executeGiveUpAction();
						//执行下注动作
						GameApplication.getDzpkGame().gameDataManager.executeXiaZhuAction();
						//执行坐下动作
						GameApplication.getDzpkGame().gameDataManager.executeSitDownAction();
					}
				}
				public void onAnimationRepeat(Animation animation) {

				}
				public void onAnimationStart(Animation animation) {
					 
					
				}
				
				
			});
		  //am1.setFillAfter(true);
		  //am1.setStartOffset(200*count);
          //faPaiView.getImgView().startAnimation(am1);
		  animSet.setStartOffset(300*count);
		  
		  faPaiView.getImgView().startAnimation(animSet);
		  
		 
    }
    private class GameFaPaiViews extends View {
		
    	public GameFaPaiViews(Context context) {
			super(context);
			this.setVisibility(View.INVISIBLE);
		}
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawBitmap(pokeBack,faPaiStartPoint[0], faPaiStartPoint[1], null);
		}
	 
		 public void destroy(){
			  
		  }
	  
 
	}
	private class GameFaPaiView extends LinearLayout {
 
		 
		ImageView imgView;
		 
		public GameFaPaiView(Context context) {
			super(context);
			//this.setBackgroundDrawable(new ColorDrawable(0));
			this.setLayoutParams(new LinearLayout.LayoutParams(960,640));
			imgView = new ImageView(context);
			imgView.setImageBitmap(pokeBack);
			LinearLayout.LayoutParams  params=new LinearLayout.LayoutParams(  
					  FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);  
		    params.leftMargin=faPaiStartPoint[0];
		    params.topMargin=faPaiStartPoint[1];
		   
			this.addView(imgView,params);
			this.setVisibility(View.INVISIBLE);
		     
		}
		public ImageView getImgView(){
			return imgView;
		}
		 
	   public void destroy(){
		   imgView =null; 
	   }
	  
 
	}
	/**
	 * 释放
	 */
	public void destroy(){
		Log.i("test19", "GameFaPaiManager destroy");
		   if(animSet != null){
			   animSet.cancel();
			   animSet =null;
		   }
		  GameUtil.recycle(pokeBack);
		  pokeBack=null;
		  for(int i=0;i<9;i++){
			  if( fpViews[i] != null){
			  fpViews[i].destroy();
			  }
			  fpViews[i]=null;
		  }
	}
}
