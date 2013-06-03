package com.dozengame.gameview;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.alipay.net.JavaHttp;
import com.dozengame.GameApplication;
import com.dozengame.HwgCallBack;
import com.dozengame.net.pojo.PlayerNetPhoto;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
import com.dozengame.util.Measure;

/**
 * 玩家信息视图
 * @author hewengao
 * 
 */
public class PlayerInfoView extends View implements HwgCallBack {

	static final int[][] playerPoint = { { 465, 471 }, { 240, 471 }, { 81, 423 },
			{ 77, 240 }, { 303, 95 }, { 652, 95 }, { 885, 189 }, { 885, 407 },
			{ 701, 471 } };
	static final int[][] playerPoint2 = { { 85, -421 }, { 310, -421 }, { 469, -373 },
			{ 473, -190 }, { 247, -45 }, { -602, -45 }, { -835, -149 },
			{ -835, -357 }, { -651, -421 } };
	static final int[][] xx = { { 8, 5 }, { 510, 5 } };
	static final int[] closeArea={370,10};//关闭按钮的起始区域
	Paint paint = null;
	RelativeLayout rly;
	Bitmap cacheBitmap = null;
	Canvas canvas = null;
	int pos = -1;
	boolean isPress = false;
	
	Bitmap playerImg;
	Bitmap playerImg1;
	Bitmap game_gamer_but;
	Bitmap game_gamer_buts;
	Bitmap game_gamer_close;
	Bitmap game_gamer_bg;
	final int width=442;
	final int height=570;
	int butWidth = 0; //加为好友按钮的宽度
	int butHeight = 0;//加为好友按钮的高度
	int closeWidth=0; //关闭按钮的宽度
	int closeHeight=0;//关闭按钮的高度
	private String playerName = "";//玩家姓名
    boolean isFirst=true;//是否第一次绘制
	final static String temp = "加为好友";
	int userid=-1;
    HashMap data;
	int gameLevel=1;
    boolean isload=false;
	GameView gv = null;
	String face=null;
	boolean exists =false;
	public GameView getGv() {
		return gv;
	}

	public void setGv(GameView gv) {
		this.gv = gv;
	}

	public PlayerInfoView(Context context, int pos,HashMap data) {
		super(context);
		this.pos = pos;
		this.data = data;
		int siteNo=GameApplication.getDzpkGame().playerViewManager.getSiteNo(pos);
		try{
			HashMap player =(HashMap)GameApplication.getDzpkGame().gameDataManager.sitDownUsers.get(siteNo);
			gameLevel =(Integer)player.get("gamelevel");
			userid=(Integer)player.get("userid");
		}catch(Exception e){
			gameLevel =1;
		}
		paint = new Paint();
		paint.setAntiAlias(true);
		//paint.setan
		cacheBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvas = new Canvas();
		canvas.setBitmap(cacheBitmap);
		initImage();
	}

	private void initImage() {
		try {
			Bitmap playerImgTemp = BitmapFactory.decodeStream(getContext().getAssets()
					.open("main_dog.jpg"));
			playerImg = GameBitMap.resizeBitmap(playerImgTemp, 150, 150);
			game_gamer_but = BitmapFactory.decodeStream(getContext()
					.getAssets().open("game_gamer_but.png"));
			game_gamer_buts = BitmapFactory.decodeStream(getContext()
					.getAssets().open("game_gamer_buts.png"));
			game_gamer_close = BitmapFactory.decodeStream(getContext()
					.getAssets().open("game_gamer_close.png"));
			game_gamer_bg = BitmapFactory.decodeStream(getContext()
					.getAssets().open("game_gamer_bg.png"));
			butHeight = game_gamer_buts.getHeight();
			butWidth = game_gamer_buts.getWidth();
			closeHeight = game_gamer_close.getHeight();
			closeWidth = game_gamer_close.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void draw() {
		drawRun =true;
		postInvalidate();
	}

	private void drawCacheBitmap(){
		if(isFirst){
			 //第一次需要绘制背景
			 isFirst =false;
			 canvas.drawBitmap(game_gamer_bg, 0, 0, null);
		}
 
		// 玩家姓名
		if(data ==null)return;
		playerName = (String)data.get("nick");
		int  totalGold =(Integer)data.get("gold");
	
		String cityName =(String)data.get("from");
		int play_count =(Integer)data.get("play_count");
		int win_count =(Integer)data.get("win_count");
		String peilv="0%";
		if(play_count > 0){
		     float peil = (win_count*1.0f)/play_count;
		     NumberFormat format=	NumberFormat.getPercentInstance();
			 format.setMaximumFractionDigits(3);
			 peilv=	format.format(peil); 
		}
		  face =(String)data.get("face");
		if(face !=null){
			if(face.startsWith("http:") || face.startsWith("https:")){
				if(isload == false){
					isload = true;
					//在本地查询是否已存在
					PlayerNetPhoto	photo = GameUtil.getPlayerPhotoById(GameApplication.currentActivity,userid);
					if(photo != null && photo.getHttpUrl().equals(face)){
						Log.i("test17", "存在");
							//存在
						createBitmap(photo.getPhotoBytes(),false);
					}else{
						if(photo != null){
							exists= true;
						}
						//不存在需要下载图片
						TaskManager.getInstance().execute(new TaskExecutorAdapter(){
		 
							public int executeTask() throws Exception {
								JavaHttp http= new JavaHttp(face, PlayerInfoView.this, "download");
								http.execute();
								return 0;
							}
						});
					}
				}
			}else{
				Bitmap result = GameBitMap.getBitmapByName(this.getContext(), face);
				if(result != null){
				  playerImg1= GameBitMap.resizeBitmap(result, GameUtil.imgWidth, GameUtil.imgHeight);
				  GameUtil.recycle(result);
				} 
			}
		}
		canvas.drawBitmap(game_gamer_close, closeArea[0], closeArea[1], null);
		paint.setColor(Color.WHITE);
		paint.setTextSize(30.0f);
		try {
			playerName=GameUtil.splitIt(playerName, 20);
			cityName=GameUtil.splitIt(cityName, 18);
		} catch (Exception e) {
			e.printStackTrace();
		}
		float w = paint.measureText(playerName);
		int h = 50;
		int margin = 40;
		// 绘制玩家姓名
		canvas.drawText(playerName, 190 - w / 2, h, paint);
		// 绘制玩家形象
		h += 30;
		if(playerImg1 != null){
		    canvas.drawBitmap(playerImg1, 221 - playerImg1.getWidth() / 2, h, null);
		}else{
			canvas.drawBitmap(playerImg, 221 - playerImg.getWidth() / 2, h, null);
		}

		// 绘制等级
		h += playerImg.getHeight() + 62;
		canvas.drawText("等级: "+gameLevel, margin, h, paint);
		// 绘制筹码
		h += 45;
		//canvas.drawText("筹码: $"+totalGold, margin, h, paint);
		canvas.drawText("筹码: $"+GameUtil.getMoneySplit(totalGold), margin, h, paint);
		
		// 绘制胜率
		h += 45;
		canvas.drawText("胜率: "+peilv, margin, h, paint);
		// 绘制来自
		h += 45;
		canvas.drawText("来自: "+cityName, margin, h, paint);
		// 绘制加为好友按钮

		if(pos ==0 && GameApplication.getDzpkGame().playerViewManager.mySite){
			//是自己不需加为好友按钮
			return;
		}
		if (isPress) {
			canvas.drawBitmap(game_gamer_buts, 221 - butWidth / 2, 490, paint);
		} else {
			canvas.drawBitmap(game_gamer_but, 221 - butWidth / 2, 490, paint);
		}
		w = paint.measureText(temp);
		paint.setColor(Color.BLACK);
		canvas.drawText(temp, 221 - w / 2, 530, paint);
	}
	
    boolean drawRun= true;
	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		if(View.VISIBLE == this.getVisibility()){
			
			if(drawRun){
				drawRun =false;
				drawCacheBitmap();
				destroy1();
			}
			int x = 0, y = 0;
			if (pos == -1)
				return;
			if (pos < 5) {
				x = xx[1][0];
				y = xx[1][1];
			} else {
				x = xx[0][0];
				y = xx[0][1];
			}
			canvas.drawBitmap(cacheBitmap, x, y, null);
		}

	}

	public boolean click(int action, float x, float y) {

	
		int xs = 0, ys = 0;
		if (pos < 5) {
			xs = xx[1][0];
			ys = xx[1][1];
		} else {
			xs = xx[0][0];
			ys = xx[0][1];
		}
		 
		// 判断是否点了关闭按钮或者点击了非图像块区域
		if (!Measure.isInnerBorder(x, y, xs, ys, xs + width, ys + height)
				|| Measure.isInnerBorder(x, y, closeArea[0]+xs,closeArea[1]+ys, xs +closeArea[0]+ closeWidth, ys+closeArea[1] + closeHeight)) {
			if (action == 1) {
				//gv.removePlayerView(this);
				GameApplication.getDzpkGame().pInfoDialog.dismiss();
			}
			return true;
		}
		//判断是否点了加为好友按钮
		int startX=(width - butWidth) / 2;
		if (Measure.isInnerBorder(x, y, startX+xs, 500+ys, startX +xs+ butWidth, 500+ys + butHeight)){
//			if (action == 0) {
//				isPress = true;
//			} else {
//				isPress = false;
//			}
			if(action ==1){
				isPress = false;
				draw();	
				GameUtil.openMessageDialog(this.getContext(), GameUtil.msg5);
			}else if(action ==0){
				isPress = true;
				draw();	
			} 
			return true;
		}
		return false;
	}

	public float[] getPoint() {
		if (pos == -1)
			return new float[2];
		int[] temp = playerPoint[pos];
		int w = 960;
		int h = 640;
		 
		float[] res = new float[2];
		res[0] = (float) ((temp[0] / 1.0) / w);
		res[1] = (float) ((temp[1] / 1.0) / h);

		 
		return res;
	}
	public void destroy1() {
		 GameUtil.recycle(playerImg1);playerImg1=null;
	}
    /**
     * 销毁
     */
	public void destroy() {
		  destroy1();
		  GameUtil.recycle(playerImg);playerImg=null;
			
			 GameUtil.recycle(game_gamer_but);game_gamer_but =null;
			 GameUtil.recycle(game_gamer_buts);game_gamer_buts =null;
			 GameUtil.recycle(game_gamer_close);game_gamer_close =null;
			 GameUtil.recycle(game_gamer_bg);game_gamer_bg =null;
		 GameUtil.recycle(cacheBitmap);cacheBitmap =null;
		 data =null;
	     this.destroyDrawingCache();
	}

	  private void createBitmap(byte [] bytes,boolean bl){
	    	 Bitmap result= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	    	 playerImg1 =GameBitMap.resizeBitmap(result, GameUtil.imgWidth, GameUtil.imgHeight);
			 GameUtil.recycle(result);
			 if(bl){
			  draw();
			 }
	    }
		@Override
		public void CallBack(Object... obj) {
			if(obj == null || obj.length!=1 || !(obj[0] instanceof byte[])) {
				return;
			}
			byte[] response = (byte[])obj[0];
				try {
					createBitmap(response,true);
					GameUtil.insertPlayerNetPhoto(GameApplication.currentActivity, userid, face, 0, response, exists);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					 
				}
		}

}
