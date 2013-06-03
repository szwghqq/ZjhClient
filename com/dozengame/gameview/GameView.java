package com.dozengame.gameview;
import java.util.HashMap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.PlayerInfoDialog;
import com.dozengame.R;
import com.dozengame.net.pojo.DeskInfo;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.Measure;

public class GameView extends View {
    //玩家坐标
	static final int[][] playerPoint ={{405,391},{180,391},{17,343},{17,160},{243,15},{592,15},{825,109},{825,327},{641,391}};
	static final int[][] roomPoint=new int[][]{{820,533},{820,560}};
	static final int [] sitNumberClickArea={295,222};
	 
	static final int playerW=120;
	static final int playerH=165;
	int playerClickIndex;
	DzpkGameActivityDialog context;
	PlayerInfoView currentgpv;
	public int state=0;
	boolean isPress=false;
	Paint pt = new Paint();
	String roomId=null;
	String roomBet=null;
	boolean isSetDeskInfo=false;
	 
	public GameView(DzpkGameActivityDialog context) {
		super(context.getContext());
		this.context=context;
		int c= Color.argb(255, 99, 99, 99);
		pt.setColor(c);
		pt.setTextSize(18);
		pt.setAntiAlias(true);
	}
	//只需要设置一次
	public void setDeskInfo(DeskInfo deskInfo){
		if(!isSetDeskInfo){
			isSetDeskInfo =true;
			roomId ="房间号:"+context.deskInfo.getDeskno();
			roomBet ="大小盲:"+context.deskInfo.getBet();
			postInvalidate();
		}
	}
	@Override
	protected void onDraw(Canvas canvas) {
	 
		//super.onDraw(canvas);
		if(roomId !=null){
			 
		
			canvas.drawText(roomId, roomPoint[0][0],  roomPoint[0][1],pt);
			canvas.drawText(roomBet, roomPoint[1][0],  roomPoint[1][1],pt);
			
		}
		  
 
	}
	public boolean onTouchEvent(MotionEvent event) {
		float xx = event.getX();
		float yy = event.getY();
	    int action=event.getAction();
	    boolean result=false;
	    result= GameApplication.getDzpkGame().sitView.clickSitDown(action, xx, yy);
	   // Log.i("test15","result: "+result);
	    if(result){
	    	
	    	return result;
	    }
	    //点击座位或图像区域
	    if(state ==0){
	     result= clickPlayer(action,xx,yy);
	    }
	    if(result){
	    	return result;
	    }
	    if (Measure.isInnerBorder(xx, yy, 
	    		sitNumberClickArea[0],sitNumberClickArea[1],
	    		sitNumberClickArea[0]+370,sitNumberClickArea[1]+100)) {
	    	if(action == 0){
	    		GameApplication.getDzpkGame().sitnoDisplayViewManager.setViewVisibility(View.VISIBLE);
	    	    result = true;
	    	}else if(action == 1){
	    		GameApplication.getDzpkGame().sitnoDisplayViewManager.setViewVisibility(View.INVISIBLE);
	    		result = true;
	    	}
	    }else{
	    	GameApplication.getDzpkGame().sitnoDisplayViewManager.setViewVisibility(View.INVISIBLE);
	    	result =false;
	    }
	   // Log.i("test15","onTouchEventonTouchEventonTouchEvent result: "+result);
//	    if(state ==0){
//	    	//点击座位或图像区域
//		    result= clickPlayer(action,xx,yy);
//	    }else if(state ==1){
//	    	//点击到弹出用户信息
//	        result=currentgpv.click(action, xx, yy);
//	    }
		return result;
	}
	
	 /**
	  * 点击座位或图像区域
	  * @param action
	  * @param xx
	  * @param yy
	  * @return
	  */
	public boolean clickPlayer(int action, float xx, float yy) {
       
		if (action == 0) {
			isPress = true;
		} else {
			isPress = false;
		}
		//检验玩家图像的点击
		for (int i = 0; i < 9; i++) {
			int x = playerPoint[i][0];
			int y = playerPoint[i][1];
			if (Measure.isInnerBorder(xx, yy, x,
					y, x + playerW,
					y + playerH)) {
				//先判断当前位置是否已经有人坐下了
				if(context.playerViewManager.isSited(i)){
					//有人坐下了
					if (action == 0) {
						// 按下
						playerClickIndex = i;
						return true;
					} else if (action == 1) {
						if(isShowPlayInfo == false){
						  isShowPlayInfo =true;
						  //请求游戏者附加信息和成就信息
						  sendRequestUserExtraInfoAchieveInfo(i);
						}
						
						playerClickIndex = -1;
						return true;
					}
				}
			}
		}
   
		return false;
	}
	private int playerIndex =-1;
	public boolean isShowPlayInfo =false;
	//请求游戏者附加信息和成就信息
	private void sendRequestUserExtraInfoAchieveInfo(int index){
		playerIndex =index;
		int siteNo=GameApplication.getDzpkGame().playerViewManager.getSiteNo(index);
		HashMap player =(HashMap)GameApplication.getDzpkGame().gameDataManager.sitDownUsers.get(siteNo);
		if(player != null){
			final Integer userid=(Integer)player.get("userid");
			if(userid != null){
				TaskManager.getInstance().execute(new TaskExecutorAdapter(){
 
					public int executeTask() throws Exception {
						GameApplication.getSocketService().sendRequestUserExtraInfoAchieveInfo(userid);
						return 0;
					}
					
				});
				
			}
		}
	}
	/**
	 * 移除
	 * @param gpv
	 */
	public void removePlayerView(PlayerInfoView gpv){
		isShowPlayInfo =false;
		context.removeView(gpv);
		//释放资源
		gpv.destroy();
		gpv = null;
		state =0;
	}
	/**
	 * 位移动画
	 * @param gpv
	 */
	private   void TranslateAnimation(PlayerInfoView gpv){
		
		 currentgpv=gpv;
		 context.addView(currentgpv); 
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
				state=1;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				 
			}

			@Override
			public void onAnimationStart(Animation animation) {
			     state=2;
			}
			
		});
		mScaleAnimation.setDuration(500);
		gpv.startAnimation(mScaleAnimation);	
	}
	/**
	 *销毁
	 */
	public void destroy() {
		Log.i("test19", "GameView destroy");
		 if(currentgpv != null){
			 currentgpv.destroy();
		 }
		 currentgpv= null;
		 context= null;
		 handler=null;
		 this.destroyDrawingCache();
	}
	/**
	 * 收到成就附加信息
	 * @param data
	 */
	public void onResponseExtrainfoAchieveInfo(HashMap data) {
		Message msg=handler.obtainMessage();
		msg.what =0;
		msg.obj =data;
		handler.sendMessage(msg);
	}
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			executeResponseExtrainfoAchieveInfo((HashMap)msg.obj);
		}
	};
	/**
	 * 处理收到成就信息
	 * 在当前界面上
	 * @param data
	 */
	private void executeResponseExtrainfoAchieveInfos(HashMap data){
		// 弹起
		PlayerInfoView pv =new PlayerInfoView(this.getContext(),playerIndex,data);
		pv.setGv(this);
		TranslateAnimation(pv);
	}
	 
	/**
	 * 处理收到成就信息
	 * 在新的弹出窗口上
	 * @param data
	 */
	private void executeResponseExtrainfoAchieveInfo(HashMap data){
		if(GameApplication.getDzpkGame() != null){
			if(GameApplication.getDzpkGame().pInfoDialog !=null && GameApplication.getDzpkGame().pInfoDialog.isShowing()){
				GameApplication.getDzpkGame().pInfoDialog.dismiss();
			}
			GameApplication.getDzpkGame().pInfoDialog = new PlayerInfoDialog(this.getContext(), R.style.dialog, playerIndex, data, this);
			GameApplication.getDzpkGame().pInfoDialog.show();	
		}
//		// 弹起
//		PlayerInfoView pv =new PlayerInfoView(this.getContext(),playerIndex,data);
//		pv.setGv(this);
//		TranslateAnimation(pv);
	}
}
