package com.dozengame;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.GameCzDialog;
import com.alipay.net.JavaHttp;
import com.dozengame.event.CallBack;
import com.dozengame.gameview.AddChouMaViewDialog;
import com.dozengame.gameview.GamePlayerView;
import com.dozengame.music.MediaManager;
import com.dozengame.net.pojo.DConfig;
import com.dozengame.net.pojo.PlayerNetPhoto;
import com.dozengame.net.task.Task;
import com.dozengame.net.task.TaskExecutor;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.playerlocationroom.DzpkPlayerLocationRoomActivity;
import com.dozengame.talk.TalkDialog;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
 
/**
 * 游戏首页,功能菜单界面
 * @author hewengao
 *
 */
public class DzpkGameMenuActivity extends BaseActivity implements CallBack,HwgCallBack{
	 
	ImageView gameRoom;
	ImageView quickStart;
	ImageView shangCheng;
	ImageView locationRoom;
	//ImageView jiaoCheng;
	ImageView gameSet;
	ImageView changUser;
	ImageView playerImg;
	ImageView refreshImg;
	Bitmap main_butrefur_s;
	Bitmap main_butrefur;
	
	Bitmap mainBtnBegin;
	Bitmap mainBtnBegins;
	
	Bitmap mainButHall;
	Bitmap mainButHalls;
	
	Bitmap mainButHelp;
	Bitmap mainButHelps;
	
	Bitmap mainButSet;
	Bitmap mainButSets;
	
	Bitmap mainButShop;
	Bitmap mainButShops;
	
	//附近玩家
	Bitmap mainBtnPlayerLocation;
	Bitmap mainBtnPlayerLocations;
	
	Bitmap mainButUser;
	Bitmap mainButUsers;
	
	Bitmap mainChip;
	Bitmap mainDog;
	Bitmap mainBg;
	Bitmap palyerBit;
	TextView userId;
	TextView userName;
	TextView playerChoumaText;
	public void onCreate(Bundle savedInstanceState) {
       
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.menu);
        initBitmap();
        initData();
        //登录领取的奖励
        int allGold= this.getIntent().getIntExtra("allGold", 0);
        if(allGold >0){
        	HashMap data =new HashMap();
        	  
        	int vipadd= this.getIntent().getIntExtra("vipadd", 0);
        	int vtask_add= this.getIntent().getIntExtra("vtask_add", 0);
        	int gold= this.getIntent().getIntExtra("gold", 0);
        	data.put("vipadd", vipadd);
        	data.put("vtask_add", vtask_add);
        	data.put("gold", gold);
        	showLinJiangDialog(data);
        }else{
        	showVipInfo();
        }
       //addEventListener();
            
    }

	private void showVipInfo(){
		if(GameApplication.showVipInfo ==1){
    		GameApplication.showVipInfo =-2;
    	    GameUtil.openVipDialog(GameApplication.vipInfoData);
    	}
	}
//	@Override
//	protected void onResume() {
//		super.onResume();
// 	 GameApplication.currentActivity =this;
////			//发送登录领取
////	        if(GameApplication.showDayGoldResult ==1){
////	        	GameApplication.showDayGoldResult=-1;
////			    sendRequestLoginLingQu();
////	        }
//	}
	 
	@Override
	protected void onStop() {
		super.onStop();
		
	}
	boolean restart =false;
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		restart =true;
		Log.i("test5", "onRestart 1");
	}
	 
	@Override
	protected void onResume() {
		super.onResume();
		MediaManager.getInstance().pauseMedia(MediaManager.mame2);
		if(restart){
			restart =false;
			//发送刷新
			TaskManager.getInstance().execute(new TaskExecutorAdapter(){
 
				public int executeTask() throws Exception {
					GameApplication.getSocketService().sendRefresh();
					return 0;
				}
			});
			return;
		}
		Intent it =this.getIntent();
		int jiuji =it.getIntExtra("jiuji", -1);
		if(jiuji ==1){
			it.putExtra("jiuji",-1);
			int cangive=it.getIntExtra("cangive",-1);
			int lqcishu=it.getIntExtra("lqcishu",-1);
			 HwgCallBack callback = new HwgCallBack() {
					
					@Override
					public void CallBack(Object... obj) {
						if(obj == null || obj.length ==0){
							//打开充值界面
							openChongZhi();
						}
					}
				};
			if(cangive == -1){
				
				 GameUtil.openMessage1Dialog(DzpkGameMenuActivity.this,GameUtil.msg4,callback,GameUtil.chongZhi);
			}else{
				GameUtil.openMessage1Dialog(DzpkGameMenuActivity.this,GameUtil.msgJiuJi+"\r\n(本日"+lqcishu+"次,共3次)",callback,GameUtil.chongZhi,GameUtil.sure);
			}
		}
		 
		boolean chongzhi = it.getBooleanExtra("chongzhi", false);
		if(chongzhi){
			openChongZhi();
		}
	}
//	  private void addEventListener(){
//		  //GameApplication.getSocketService().addEventListener(Event.ON_TEX_RECV_LOGIN_GIVE, this, "onRecvLoginGive"); 
//		
//	  }
//	  private void removeEventListener(){
//		 // GameApplication.getSocketService().removeEventListener(Event.ON_TEX_RECV_LOGIN_GIVE, this, "onRecvLoginGive");
//		 // GameApplication.getSocketService().removeEventListener(Event.ON_RECV_CAN_NOT_TOROOM, this, "onRecvCanNotToRoom");
//	  }
	  /**
	   * 发送登录领取
	   */
	  private void sendRequestLoginLingQu(){
		  TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			public int executeTask() throws Exception {
				GameApplication.getSocketService().sendRequestLoginLingQu();
				return 0;
			}
		  });
	  }
	  private void initBitmap(){
	    	try {
	    		main_butrefur_s = BitmapFactory.decodeStream(getAssets().open("main_butrefur_s.png"));
	    		main_butrefur = BitmapFactory.decodeStream(getAssets().open("main_butrefur.png"));
	    		
	    		mainBtnBegin = BitmapFactory.decodeStream(getAssets().open("main_btnbegin.png"));
	    		mainBtnBegins = BitmapFactory.decodeStream(getAssets().open("main_btnbegins.png"));
	    		
	    		mainButHall = BitmapFactory.decodeStream(getAssets().open("main_buthall.png"));
	    		mainButHalls = BitmapFactory.decodeStream(getAssets().open("main_buthalls.png"));
	    		
	    		mainButHelp = BitmapFactory.decodeStream(getAssets().open("main_buthelp.png"));
	    		mainButHelps = BitmapFactory.decodeStream(getAssets().open("main_buthelps.png"));
	    		
	    		mainButSet = BitmapFactory.decodeStream(getAssets().open("main_butset.png"));
	    		mainButSets = BitmapFactory.decodeStream(getAssets().open("main_butsets.png"));
	    		
	    		mainButShop = BitmapFactory.decodeStream(getAssets().open("main_butshop.png"));
	    		mainButShops = BitmapFactory.decodeStream(getAssets().open("main_butshops.png"));
	    		
	    		//附近玩家
	    		mainBtnPlayerLocation = BitmapFactory.decodeStream(getAssets().open("main_butgps.png"));
	    		mainBtnPlayerLocations = BitmapFactory.decodeStream(getAssets().open("main_butgps_d.png"));
	    		
	    		mainButUser = BitmapFactory.decodeStream(getAssets().open("main_butuser.png"));
	    		mainButUsers = BitmapFactory.decodeStream(getAssets().open("main_butusers.png"));
	    		
	    		mainChip = BitmapFactory.decodeStream(getAssets().open("main_chip.png"));
	    		mainDog = BitmapFactory.decodeStream(getAssets().open("main_dog.jpg"));
	    		mainDog=GameBitMap.resizeBitmap(mainDog, GameUtil.imgWidth, GameUtil.imgHeight);
	    		mainBg = BitmapFactory.decodeStream(getAssets().open("main_bg.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	 /**
     * 初始化控件
     */
    private void initData(){
    	 findViewById(R.id.main_bg).setBackgroundDrawable(new BitmapDrawable(mainBg));
    	 quickStart =  (ImageView)findViewById(R.id.quickStart);
    	 quickStart.setImageBitmap(mainBtnBegin);
    	 
    	 gameRoom =  (ImageView)findViewById(R.id.gameRoom);
    	 gameRoom.setImageBitmap(mainButHall);
    	 
    	 shangCheng =  (ImageView)findViewById(R.id.shangCheng);
    	 shangCheng.setImageBitmap(mainButShop);
    	 
    	//附近玩家
    	 locationRoom =  (ImageView)findViewById(R.id.btnLocationRoom);
    	 locationRoom.setImageBitmap(mainBtnPlayerLocation);
    //	 jiaoCheng =  (ImageView)findViewById(R.id.btnLocationRoom);
    //	 jiaoCheng.setImageBitmap(mainButHelp);
    	 
    	 gameSet =  (ImageView)findViewById(R.id.gameSet);
    	 gameSet.setImageBitmap(mainButSet);
    	 
    	 changUser =  (ImageView)findViewById(R.id.changUser);
    	 changUser.setImageBitmap(mainButUser);
    	 
    	 playerImg=  (ImageView)findViewById(R.id.playerImg);
    	 playerImg.setImageBitmap(mainDog);
    	 
    	 refreshImg=  (ImageView)findViewById(R.id.refresh);
    	 refreshImg.setImageBitmap(main_butrefur);
    	 
    	 ImageView playerChoumaImg=  (ImageView)findViewById(R.id.playerChoumaImg);
    	 playerChoumaImg.setImageBitmap(mainChip);
    	 userId=  (TextView)findViewById(R.id.userId);
    	 userName=  (TextView)findViewById(R.id.userName);
    	 playerChoumaText=  (TextView)findViewById(R.id.playerChoumaText);
    	 //设置用户信息
    	 setUserInfoData(GameApplication.userInfo);
         //添加侦听器
         addListener();
    }
    int user_real_id;
    String face;
    boolean exists = false;
    /**
     * 设置用户信息
     * @param userInfo
     */
    private void setUserInfoData(HashMap userInfo){
    	if(userInfo !=null){
    		
    		try {
    			user_real_id = (Integer)userInfo.get("user_real_id");
    			userId.setText("ID: "+user_real_id);
    			 
    			userName.setText((String)userInfo.get("usernick"));
    			
				//userName.setText(GameUtil.splitIt((String)userInfo.get("usernick"), 17));
			    face=(String)userInfo.get("imgurl");
			    if(face.startsWith("http:") || face.startsWith("https:")){
			    	//在本地查询是否已存在
					PlayerNetPhoto	photo = GameUtil.getPlayerPhotoById(this,user_real_id);
					if(photo != null && photo.getHttpUrl().equals(face)){
						//存在
						Log.i("test17", "存在此基础上 ");
						createBitmap(photo.getPhotoBytes(),false);
					}else{
						if(photo != null){
							exists= true;
						}
						//不存在需要下载图片
						TaskManager.getInstance().execute(new TaskExecutorAdapter(){
		 
							public int executeTask() throws Exception {
								JavaHttp http= new JavaHttp(face, DzpkGameMenuActivity.this, "download");
								http.execute();
								return 0;
							}
						});
					}
			    }else{
					Bitmap result=	GameBitMap.getBitmapByName(this, face);
					if(result != null){
						palyerBit=GameBitMap.resizeBitmap(result, GameUtil.imgWidth, GameUtil.imgHeight);
						if(palyerBit != null){
							playerImg.setImageBitmap(palyerBit);
						}
						GameUtil.recycle(result);
					} 
				}
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
    		playerChoumaText.setText(" $"+userInfo.get("gold"));
    	}
    }
    /**
     * 设置玩家筹码
     * @param gold
     */
    public void setPlayerChoumaText(int gold,boolean bl){
    	 if(bl){
    		Message msg= handler.obtainMessage();
    		msg.what =0;
    		msg.obj =gold;
    		handler.sendMessage(msg);
    	 }
    }
    /**
     * 设置玩家筹码
     * @param gold
     */
    public void setPlayerChoumaText(int gold){
    	Log.i("test8", "setPlayerChoumaText : "+gold);
    	playerChoumaText.setText(" $"+gold);
    }
	private void addListener(){
		
		quickStart.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	quickStart.setImageBitmap(mainBtnBegins);
			    	return true;
			    }else if(action ==1){
			    	quickStart.setImageBitmap(mainBtnBegin);
			    	forward(2);
			    	return true;
			    }
				return false;
			}
    		
    	});
		gameRoom.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	gameRoom.setImageBitmap(mainButHalls);
			    	return true;
			    }else if(action ==1){
			    	gameRoom.setImageBitmap(mainButHall);
			         forward(0);
			    	return true;
			    }
				return false;
			}
    		
    	});
		
		shangCheng.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	 shangCheng.setImageBitmap(mainButShops);
			    	return true;
			    }else if(action ==1){
			    	 shangCheng.setImageBitmap(mainButShop);
			    	 forward(4);
			    	return true;
			    }
				return false;
			}
    		
    	}); 
		
		/*jiaoCheng.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	jiaoCheng.setImageBitmap(mainButHelps);
			    	return true;
			    }else if(action ==1){
			    	jiaoCheng.setImageBitmap(mainButHelp);
			        forward(3);
			    	return true;
			    }
				return false;
			}
    		
    	});*/
		locationRoom.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	locationRoom.setImageBitmap(mainBtnPlayerLocations);
			    	return true;
			    }else if(action ==1){
			    	locationRoom.setImageBitmap(mainBtnPlayerLocation);
			        forward(3);
			    	return true;
			    }
				return false;
			}
    		
    	});
		
		gameSet.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	gameSet.setImageBitmap(mainButSets);
			    	return true;
			    }else if(action ==1){
			    	gameSet.setImageBitmap(mainButSet);
			    	forward(5);
			    	return true;
			    }
				return false;
			}
    		
    	});
		changUser.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	 changUser.setImageBitmap(mainButUsers);
			    	return true;
			    }else if(action ==1){
			    	 changUser.setImageBitmap(mainButUser);
			    	 forward(1);
			    	return true;
			    }
				return false;
			}
    		
    	});
		refreshImg.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	refreshImg.setImageBitmap(main_butrefur_s);
			    	return true;
			    }else if(action ==1){
			    	refreshImg.setImageBitmap(main_butrefur);
			    	//发送刷薪金币信息
			    	sendRefreshGold();
			    	return true;
			    }
				return false;
			}
    		
    	});
		
	}
	private void sendRefreshGold(){
		TaskManager.getInstance().execute(new TaskExecutorAdapter() {
			public int executeTask() throws Exception {
				GameApplication.getSocketService().sendUpdateGold();
				return 0;
			}
		});
	}
	private void forward(int s){
		Intent it =new Intent();
		switch(s){
		case 0:
			GameApplication.tab =-1;
			//游戏大厅
			it.setClass(DzpkGameMenuActivity.this, DzpkGameRoomActivity.class);
			break;
		case 1:
			//更换用户
			if(GameApplication.joinAagin()){
				//重连成功
			  it.setClass(DzpkGameMenuActivity.this, DzpkGameLoginActivity.class);
			
			}else{
				//重连失败
			   it.setClass(DzpkGameMenuActivity.this, DzpkGameStartActivity.class);
			}
			this.finish();
			break;
		case 2://快速开始
			it.putExtra("type", 2);
			it.setClass(DzpkGameMenuActivity.this, DzpkGameActivity.class);
		
			break;
		case 3://新手教程
			//GameUtil.openMessageDialog(DzpkGameMenuActivity.this, GameUtil.msg5);
			//TalkMsgDialog msg = new TalkMsgDialog(this, R.layout.dialog);
			//msg.show();
			//return;
			
			//附近玩家
			it.setClass(DzpkGameMenuActivity.this, DzpkPlayerLocationRoomActivity.class);
			break;
		case 4://道具商城
			openChongZhi();
			//GameUtil.openMessageDialog(DzpkGameMenuActivity.this, GameUtil.msg5);
			return;
		case 5://游戏设置
			//AddChouMaViewDialog addDialog = new AddChouMaViewDialog(this,R.style.dialog);
			//addDialog.show();
			GameUtil.openMessageDialog(DzpkGameMenuActivity.this, GameUtil.msg5);
			return;
		 default:
			 //BuyChouMaDialog bcmd =new BuyChouMaDialog(this,R.style.dialog);
		     //bcmd.show();
			return;
		}
		startActivity(it);
		finish();
	}
	
	/**
	 * 充值
	 */
	private void openChongZhi(){
		try{
			 GameCzDialog gameCzDialog = new GameCzDialog(DzpkGameMenuActivity.this,R.style.dialog);
			 gameCzDialog.show();
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	 /**
	    * 显示领奖窗口
	    * @param data
	    */
	private void showLinJiangDialog(HashMap data){
		HwgCallBack callback = new HwgCallBack() {
			@Override
			public void CallBack(Object... obj) {
				 showVipInfo();
			}
		};
		LinJiangDialog linJiang =new LinJiangDialog(DzpkGameMenuActivity.this,R.style.dialog, data,callback);
		linJiang.show();
	}
	/**
	 * 侦听按键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	   //GameUtil.backExit(DzpkGameMenuActivity.this);
	    	   HwgCallBack callback = new HwgCallBack(){
	    			 
					public void CallBack(Object... obj) {
						if(obj == null || obj.length ==0)
						android.os.Process.killProcess(android.os.Process.myPid());//获取PID 
					}
				};
				GameUtil.openMessage1Dialog(this,GameUtil.EXITMSG,callback);
		         
				//GameUtil.openMessage1Dialog(this,GameUtil.EXITMSG+"\n接收流量: "+GameApplication.getReceBytes()+"\n发送流量: "+GameApplication.getSendBytes(),callback);
	            return true;
	       }
	  return super.onKeyDown(keyCode, event);
	} 

	@Override
	protected void onDestroy() {
		super.onDestroy();
		GameUtil.recycle(mainBtnBegin);
		GameUtil.recycle(mainBtnBegins);
		GameUtil.recycle(mainButHall);
		GameUtil.recycle(mainButHalls);
		GameUtil.recycle(mainButHelp);
		GameUtil.recycle(mainButHelps);
		GameUtil.recycle(mainButSet);
		GameUtil.recycle(mainButSets);
		GameUtil.recycle(mainButShop);
		GameUtil.recycle(mainButShops);
		GameUtil.recycle(mainButUser);
		GameUtil.recycle(mainButUsers);
		GameUtil.recycle(mainChip);
		GameUtil.recycle(mainDog);
		GameUtil.recycle(palyerBit);
		GameUtil.recycle(mainBg);
		GameUtil.recycle(main_butrefur);
		GameUtil.recycle(main_butrefur_s);
		//removeEventListener();
		 System.gc();
	}
	 private void createBitmap(byte [] bytes,boolean bl){
		 Bitmap result=null;
		 try{	Log.i("test17","createBitmapcreateBitmap");
	    	   result= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	    	   palyerBit=GameBitMap.resizeBitmap(result, GameUtil.imgWidth, GameUtil.imgHeight);
	    	   if(bl){
	    		   handler.sendEmptyMessage(1);
	    	   }else{
	    		   if(palyerBit != null){
						playerImg.setImageBitmap(palyerBit); 
					}  
	    	   }
			   Log.i("test17","createBitmapcreateBitmap456");
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 GameUtil.recycle(result); 
		 }
    }
	@Override
	public void CallBack(Object... obj) {
		if(obj == null || obj.length!=1 || !(obj[0] instanceof byte[])) {
			return;
		}
		Log.i("test17","CallBackCallBackCallBack");
		byte[] response = (byte[])obj[0];
			try {
				createBitmap(response,true);
				GameUtil.insertPlayerNetPhoto(GameApplication.currentActivity, user_real_id, face, 0, response, exists);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				 
			}
	}
	Handler handler= new Handler(){
		@Override
		public void handleMessage(Message msg) {
			 if(msg.what ==1){
				 if(palyerBit != null){
					playerImg.setImageBitmap(palyerBit); 
				}
			 }else if(msg.what ==0){
				 setPlayerChoumaText((Integer)msg.obj);
			 }
		}
	};

//	public void onRecvLoginGive(Event e){
//		 
//		  Message msg= handler.obtainMessage();
//		  msg.what=0;
//		  msg.obj=e.getData();
//		  handler.sendMessage(msg);
//	}
//	Handler handler  =new Handler(){
//		@Override
//		public void handleMessage(Message msg) {
//			 
//			super.handleMessage(msg);
//			if(msg.what==0){
//			 showLinJiangDialog(msg.obj);
//			}
//		}
//	};

	
 
//	public void onRecvCanNotToRoom(Event e){
//		 Log.i("test4", "111111receiveCanNotToRoom");
//		HashMap data=(HashMap)e.getData();
//		if(data != null){
//			Byte intoRoomStats =(Byte)data.get("IntoRoomStats");
//			 Log.i("test4", "111111receiveCanNotToRoom:  intoRoomStats: "+intoRoomStats);
//			 if(intoRoomStats == 1){
//				 handler.sendEmptyMessage(1);
//			 }else{
//				 Message msg=handler.obtainMessage();
//				 msg.what=2;
//				 msg.obj=intoRoomStats;
//				 handler.sendMessage(msg);
//				
//			 }
//		}
//	}
//	 /**
//     * 发送快速进入游戏
//     */
//	private void sendQuickGame(){
//		//isquick =true;
//		//Log.i("site", "isquick isquick isquick isquick: "+isquick);
//	 	TaskManager.getInstance().execute(new TaskExecutorAdapter(){
//	 		@Override
//	 		public int executeTask() throws Exception {
//	 			GameApplication.getSocketService().sendRequestAutoJoin();
//	 			return 0;
//	 		}
//	 	});
//	}
}
