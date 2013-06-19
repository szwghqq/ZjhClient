package com.dozengame;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.event.TexEventType;
import com.dozengame.gameview.BuyChouMaDialog;
import com.dozengame.gameview.CheckNetThread;
import com.dozengame.gameview.CheckNetView;
import com.dozengame.gameview.GameAllInViewManager;
import com.dozengame.gameview.GameAllInViewManager1;
import com.dozengame.gameview.GameBottomViewManager;
import com.dozengame.gameview.GameButtonView;
import com.dozengame.gameview.GameFaPaiManager;
import com.dozengame.gameview.GameGolbalPokeTypeView;
import com.dozengame.gameview.GameJsqViewManager;
import com.dozengame.gameview.GameMyPokeView;
import com.dozengame.gameview.GameMyWinView;
import com.dozengame.gameview.GameOtherPokeViewManager;
import com.dozengame.gameview.GamePlayerChouMaViewManager;
import com.dozengame.gameview.GamePlayerViewManager;
import com.dozengame.gameview.GamePlayerWinMoneyViewManager;
import com.dozengame.gameview.GameView;
import com.dozengame.gameview.GameXiaZhuViewManager;
import com.dozengame.gameview.GameZjViewManager;
import com.dozengame.gameview.GlobalPokeViewManager;
import com.dozengame.gameview.PokeBackViewManager;
import com.dozengame.gameview.PondViewManager;
import com.dozengame.gameview.SitNoDisplayViewManager;
import com.dozengame.gameview.SitView;
import com.dozengame.gameview.ToastPaiXingView;
import com.dozengame.music.MediaManager;
import com.dozengame.net.SocketService;
import com.dozengame.net.pojo.DJieSuan;
import com.dozengame.net.pojo.DeskInfo;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.net.task.TaskSingleManager;
import com.dozengame.talk.GameTalkBiaoQingViewManager;
import com.dozengame.talk.GameTalkChatViewManager;
import com.dozengame.util.GameUtil;
 


 
 
/**
 * 游戏活动视图
 * @author hewengao
 *
 */
public class DzpkGameActivityDialog extends Dialog implements CallBack {
	public boolean restart =false;
	//接收到桌子上用户列表
	private static final  int recvDeskUserList=0;
	//更新所有玩家状态
	public static final int recvPlayerInfo=1;
	//用户坐下信息
	private static final int  recvSitDown=2;
	//桌子信息
	private static final int  recvDeskInfo=3;
	//收到发牌
	private static final int  recvFaPai=4;
	//收到自动面板
	private static final int  recvAutoPanel=5;
	//收到面板
	private static final int  recvPanel=6;
	//被踢
	private static final int  recvKickMe=7;
	//下注成功
	private static final int  recvXiaZhuSucc=8;
	//显示自己的德州豆
	private static final int  recvShowMyTotalBean=9;
	//显示桌面的牌
	private static final int  recvDeskPoke=10;
	//收到某个座位的金币刷新
	private static final int recvRefreshGold =11;
	//游戏结束
	private static final int recvGameOver =12;
	//收到购买筹码窗
	private static final int recvBuyChouma=13;
	//用户站起
	private static final int recvStandup=14;
	//收到游戏开始
	private static final int recvGameStart=15;
	//收到玩家不下注
	private static final int recvBuXiaZhu=16;
	//收到自己的最佳牌型
	private static final int recvBestPokes=17;
	//收到桌面彩池信息
	private static final int recvDeskPollInfo=18;
	//收到恢复显示
	private static final int recvResetDisplay=19;
	//收到钱不够被踢出
    private static final int recvLowGold = 20;
    //收到玩家投降
    private static final int recvGiveUp =21;
    //不能进入房间
    private static final int recvNotInToRoom=22;
    //收到指定座位礼物
    private static final int recvGiftIcon =23;
    //太富有了不能坐下
    private static final int recvRichNotInToRoom=24;
    //销毁
    private static final int finish=25;
   
    //播放赠送礼物 
    private static final int recvPlayGiftIcon= 26;
    //收到坐下结果
    private static final int recvSitDownResult=27;
    //收到服务端的提示信息
    private static final int recvServerError=28;
    //收到服务端的提示信息
    private static final int recvBeiTi=29;
    //收到游戏相关信息
    private static final int recvWatchError =30;
    //收到救济
    private static final int recvJiuJi =31;
    //收到桌内聊天
    private static final int recvChat =32;//ON_TEX_RECV_CHAT;
    private static final int recvEmot =33;//ON_TEX_RECV_CHAT;
    //去充值
    private static final int chongzhi=34;
	//桌子信息
	public DeskInfo deskInfo = null;
    final String tag="test12";
	SocketService service=null;
	public  FrameLayout frameLayout;
 
	 //游戏底部按钮管理
	public GameBottomViewManager gameBottomView=null;
	public GameDataManager gameDataManager ;
	private RelativeLayout	mainLayout;
	public SitView sitView;//座位
	public GameView gameView;
	//是否快速开始
	public boolean isquick =false;
	TestNeCunThread testNeCun;
	private Bitmap currBgBitmap;
	private int deskType=-1;
	public boolean isBackClick =false;
	//是否已销毁
	public static boolean isDestroy =true;
	 CheckNetThread cnetThread;
	 CheckNetView checkNetView;
	 DzpkGameActivity activity =null;
	public GameJsqViewManager  jsqViewManager;
	private Vibrator vibrator = null;          
//	public MyDialog(Test2Activity context,int theme) {
//		 super(context,theme);
//		 this.context = context;
//	     setContentView(R.layout.dialog);
//	     frameLayout=(FrameLayout)findViewById(R.id.layout);
//		 DisplayMetrics dm = new DisplayMetrics();   
//		 this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm); 
//		 frameLayout.getLayoutParams().height =dm.heightPixels;
//		 frameLayout.getLayoutParams().width =dm.widthPixels; 
//		 jsqViews = new GameJsqViewManager(this);
//		 final ColorDrawable drawable =new ColorDrawable(0);
//		 this.getWindow().setBackgroundDrawable(drawable);
//	}
	 PlayerDialog playerDialog;
	 public  PlayerInfoDialog pInfoDialog=null;//玩家信息窗
	 public GameZjViewManager zjViewManager;
	// public FaiPaiDialog faiPaiDialog =null;
	 public DzpkGameActivityDialog(DzpkGameActivity context,int theme,final PlayerDialog playerDialog,final FaiPaiDialog faiPaiDialog) {
			super(context,theme);
		    activity = context;
			this.playerDialog=playerDialog;
			//this.faiPaiDialog =faiPaiDialog;
			jsqViewManager = context.jsqViewManager;
			 {
			   //playerDialog中
			    zjViewManager =playerDialog.zjViewManager;
				this.myPoke =playerDialog.myPoke;
				this.pokeBackViewManager=playerDialog.pokeBackViewManager;
				this.playerViewManager = playerDialog.playerViewManager;
			 }
			 
			 {
				  //FaiPaiDialog中
				 this.faPaiManager=faiPaiDialog.faPaiManager;
				 this.pokeTypeView=faiPaiDialog.pokeTypeView;
				 this.globalPokeManager=faiPaiDialog.globalPokeManager;
				 this.otherPokeViewManager =faiPaiDialog.otherPokeViewManager;
				 this.sitView = faiPaiDialog.sitView;
			 }
			setContentView(R.layout.gamemain);
			 final ColorDrawable drawable =new ColorDrawable(0);
			 this.getWindow().setBackgroundDrawable(drawable);
			init(context);
			this.setOnDismissListener(new OnDismissListener(){
				@Override
				public void onDismiss(DialogInterface dialog) {
				   destroy();
				   if(GameApplication.getDzpkGame().pInfoDialog !=null && GameApplication.getDzpkGame().pInfoDialog.isShowing()){
						GameApplication.getDzpkGame().pInfoDialog.dismiss();
					}
				   if(faiPaiDialog != null){
					   faiPaiDialog.dismiss();
				   }
				   //activity.finish();
				}
				
			});
			 
			this.setOnShowListener(new OnShowListener(){
 
				public void onShow(DialogInterface dialog) {
                    Log.i("test12", "onShowonShowonShow");
				    init();
				    load();
                   
				}
			});
	 }

	public void init(Context context) {
	 
		isBackClick =false;
		isDestroy =false;
 
		//防止手机休眠
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  
		setContentView(R.layout.gamemain);
		activityState=4;
		GameApplication.setDzpkGame(this);
		gameDataManager=new GameDataManager(this);
		mainLayout=(RelativeLayout)findViewById(R.id.mainLayout);
 
		frameLayout=(FrameLayout)findViewById(R.id.FrameLayout);
        checkNetView = new CheckNetView(context);
        frameLayout.addView(checkNetView);
		gameView =new GameView(this);
		frameLayout.addView(gameView);
	 
		//添加庄家
		//addZjViewManager();
		//添加返回和站起按钮
		addBackAndStandButton();
		//添加玩家下注筹码
		addPlayerChouMaViewManager();
		//添加彩池信息
		addPondViewManager();
		addPondViewManager1();
		//游戏底部按钮管理
		gameBottomView=new GameBottomViewManager(this);
		//添加我赢了视图
		addMyWinView();
		//添加玩家赢钱
		addPlayerWinMoneyManager();
		//添加玩家AllIn管理
		addAllInViewManager();
		//添加下注筹码管理
		addGameXiaZhuViewManager();
        //添加牌型提示信息
		addToastPaiXing();
		//添加座位显示
		addSitNumberViewManager();
		//添加玩家聊天提示管理
		 addTalkViewManager();
		
		GameApplication.testNetCun();
		    //testNeCun=new TestNeCunThread(this);
		 final ColorDrawable drawable =new ColorDrawable(0);
		this.getWindow().setBackgroundDrawable(drawable); 
	}
	//座位显示号
	public SitNoDisplayViewManager sitnoDisplayViewManager =null;
	public void  addSitNumberViewManager(){
		sitnoDisplayViewManager = new SitNoDisplayViewManager(this);
	}
	public GameTalkChatViewManager talkChatViewManager =null;
	public GameTalkBiaoQingViewManager talkQingViewManager =null;
	public void  addTalkViewManager(){
		talkChatViewManager= new GameTalkChatViewManager(this);
		talkQingViewManager = new GameTalkBiaoQingViewManager(this);
	}
 
	 
	public ToastPaiXingView toastPaiXingView;
	private void addToastPaiXing(){
		toastPaiXingView = new ToastPaiXingView(this.getContext());
		frameLayout.addView(toastPaiXingView);
	}
	public GameXiaZhuViewManager xiaZhuViewManager;
	/**
	 * 添加下注筹码管理
	 */
	private void addGameXiaZhuViewManager(){
		xiaZhuViewManager =  new GameXiaZhuViewManager(this);
	}
	public GameAllInViewManager allInViewManager;
	public GameAllInViewManager1 allInViewManager1;
	/**
	 * 添加玩家AllIn管理
	 */
	private void addAllInViewManager(){
		//allInViewManager=new GameAllInViewManager(this);
		allInViewManager1=new GameAllInViewManager1(this);
	}
	
	public GamePlayerWinMoneyViewManager playerWinMoneyManager;
	/**
	 *添加玩家赢钱视图管理
	 */
	private void addPlayerWinMoneyManager(){
		playerWinMoneyManager=new GamePlayerWinMoneyViewManager(this);
	}
	public GameMyWinView myWinView;
	/**
	 * 添加我赢了视图
	 */
	private void addMyWinView(){
		myWinView=new GameMyWinView(this.getContext());
		this.addView(myWinView);
	}
	public GamePlayerChouMaViewManager playerChouMaViewManager = null;
	/**
	 * 添加玩家下注筹码信息
	 */
	private void addPlayerChouMaViewManager(){
		playerChouMaViewManager =  new GamePlayerChouMaViewManager(this);
	}
	public PondViewManager pondViewManager1 = null;
	/**
	 * 添加彩池信息1用于动画
	 */
	private void addPondViewManager1(){
		  pondViewManager1 = new PondViewManager(this);
	}
	public PondViewManager pondViewManager = null;
	/**
	 * 添加彩池信息
	 */
	private void addPondViewManager(){
		  pondViewManager = new PondViewManager(this);
	}
	public GameGolbalPokeTypeView pokeTypeView;
//	/**
//	 * 添加全局牌形
//	 */
//	private void addPokeTypeView(){
//		pokeTypeView = new GameGolbalPokeTypeView(this.getContext());
//		frameLayout.addView(pokeTypeView);
//	}
	public GlobalPokeViewManager globalPokeManager;
//	/**
//	 * 添加公共牌
//	 */
//	private void addGlobalManager(){
//		globalPokeManager =new GlobalPokeViewManager(this);
//	}
	
	
	
	public PokeBackViewManager pokeBackViewManager;
//	/**
//	 * 其他玩家的小牌
//	 */
//	private void addPokeBackViewManager(){
//		pokeBackViewManager = new PokeBackViewManager(this);
//	}
 	public GameMyPokeView myPoke;
//	/**
//	 * 添加自己的牌
//	 */
//	private void addMyPoke(){
//		myPoke =new GameMyPokeView(this.getContext());
//		frameLayout.addView(myPoke);
//	}
	
 	public GameFaPaiManager faPaiManager;
//	/**
//	 * 添加发牌
//	 */
//	private void addFaiPaiManager(){
//		faPaiManager =new GameFaPaiManager(this);
//	}
	public GameOtherPokeViewManager otherPokeViewManager;
//	/**
//	 * 添加其他玩家的底牌
//	 */
//	private void addOtherPokeView(){
//		otherPokeViewManager =new GameOtherPokeViewManager(this);
//	}

	 
	public GamePlayerViewManager playerViewManager=null;
	/**
	 * 添加游戏玩家视图
	 */
	private void addPlayerViewManager(){
	    playerViewManager=new GamePlayerViewManager(this.getContext());
		frameLayout.addView(playerViewManager);
	}
	
	public GameButtonView standButton;
	GameButtonView backButton;
	/**
	 * 添加返回和站起按钮
	 */
	private void addBackAndStandButton(){
		//站起按钮
	    standButton =new GameButtonView(this.getContext(),1);
	    
	    frameLayout.addView(standButton);
		View.OnClickListener listener2 =new View.OnClickListener(){
			public void onClick(View v) {	
				//请求站起
				sendStandUp();
			}
		};
		standButton.setOnClickListener(listener2);
		//站起按钮不可见
		standButton.setVisibility(View.INVISIBLE);
		//返回大厅按钮
	     backButton =new GameButtonView(this.getContext(),0);
	     frameLayout.addView(backButton);
		View.OnClickListener listener =new View.OnClickListener(){
			 
			public void onClick(View v) {
				if(isBackClick ==false){
					isBackClick =true;
				    leaveRooms();
				}
				 
			}
		};
		backButton.setOnClickListener(listener);
	}
	
	/**
	 * 请求返回大厅
	 */
	private void sendRequestBackHall(final int state){
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			@Override
			public int executeTask() throws Exception {
				
			   // if(state ==1){
			   //   backToRoom();
			   // }
				try{
					while(true){
				     if(jsqViewManager.isStop()){
				    	 break;
				     }
				     Thread.sleep(10); 
				}
				}catch(Exception e){}
				sendMsg(finish, null);
				try{
				      GameApplication.getSocketService().sendRequestBackHall();
					}catch(Exception e){
						
					}
				return 0;
			}
		});
	}
	/**
	 * 请求返回去充值界面
	 */
	private void sendRequestBackToChongZhi(){
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			@Override
			public int executeTask() throws Exception {
				try{
					while(true){
				     if(jsqViewManager.isStop()){
				    	 break;
				     }
				     Thread.sleep(10); 
				}
				}catch(Exception e){}
				sendMsg(chongzhi, null);
				 try{
				      GameApplication.getSocketService().sendRequestBackHall();
					}catch(Exception e){
						
					}
				return 0;
			}
		});
	}
	/**
	 * 返回大厅
	 */
	private void backToRoom(){
		Log.i("test12", "backToRoom");
		     dismiss();
		     clearFinishDaga();
			 Intent it =new Intent();
			 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 it.setClass(this.getContext(), DzpkGameRoomActivity.class);
			 this.getContext().startActivity(it);
			 //右左滑入
			 activity.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
		 
	}
	 /**
	  * 返回去充值
	  */
	private void backToChongZhi(){
		Log.i("test12", "backToChongZhi");
		     dismiss();
		     clearFinishDaga();
			 Intent it =new Intent();
			 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 it.putExtra("chongzhi", true);
			 it.setClass(this.getContext(), DzpkGameMenuActivity.class);
			 this.getContext().startActivity(it);		 
	}
	/**
	 * 清理
	 */
    private void clearFinishDaga(){
    	TaskSingleManager.getInstance().cancel();
		TaskSingleManager.setTaskSingleManagerNull();
		removeListener();
		
	 
    }
	//是否第一次进入
	public boolean isFirstIn =true;
	/**
	 * 请求站起
	 */
	public  void sendStandUp(){
	 
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			@Override
			public int executeTask() throws Exception {
				//发送站起
			    GameApplication.getSocketService().sendRequestStandUp();
			    //请求观战
			    GameApplication.getSocketService().sendRequestWatch(deskInfo.getDeskno());
			    //发送踢人次数
			    GameApplication.getSocketService().sendRequestKickIsShow();
			    //发送离开大厅
			    GameApplication.getSocketService().sendClientLeaveRoom();
			    //发送请求桌子信息
			    GameApplication.getSocketService().sendRequestDeskInfo(deskInfo.getDeskno());
				return 0;
			}
		});
		
	}
	/**
	 * 添加视图
	 * @param view
	 */
	public void addView(View view){
		frameLayout.addView(view);
	}
	/**
	 * 移除视图
	 * @param view
	 */
    public void removeView(View view) {
    	frameLayout.removeView(view);
	}
 
    /**
     * 初始化
     */
	private void init() {
		 service = GameApplication.getSocketService();
		 addListener();
		//网速检测
         cnetThread= new CheckNetThread(checkNetView);
	}
	int type=-1;
	 
	
	public void load(){
		if(restart){
			  
			restart =false;
			//发送返回大厅
			sendRequestBackHall(-1);
		 
			//恢复显示
			TaskManager.getInstance().execute(new TaskExecutorAdapter(){
 
				@Override
				public int executeTask() throws Exception {
					//发送刷新
		             GameApplication.getSocketService().sendRefresh();
					return 0;
				}
			});
		}else{
		
		 Intent intent = activity.getIntent();
		 type= intent.getIntExtra("type", -1);
		  //快速开始 1:从大厅界面 2:从功能菜单界面
		 if(type == 1 || type == 2){
			 sitView.setVisibility(View.INVISIBLE);
			 sendQuickGame();
		 }else{
			  
			 deskInfo = (DeskInfo) intent.getSerializableExtra("deskInfo");
			 TaskManager.getInstance().execute(new TaskExecutorAdapter() {
					public int executeTask() throws Exception {
						service.sendRequestDeskUser(deskInfo.getDeskno());
						return 0;
					}

				});
		 }
	 	} 
    	
	}
	//启用Loading
	public void loadIng(){
		activity.loadIng();
		//GameApplication.showLoading(this.getContext(),callback,40000);
	}
    /**
     * 发送快速进入游戏
     */
	private void sendQuickGame(){
		 
		isquick =true;
	 	TaskManager.getInstance().execute(new TaskExecutorAdapter(){
	 		@Override
	 		public int executeTask() throws Exception {
	 			service.sendRequestAutoJoin();
	 			return 0;
	 		}
	 	});
	}
 
	/**
	 * 添加侦听事件.
	 */
	private void addListener(){
		service.addEventListener(TexEventType.ON_RECV_GIVE_JIUJI, this, "onRecvGiveJiuJi");
		service.addEventListener(Event.ON_RECV_WATCH_ERROR, this, "onRecvWatchError");
		//收到被踢
		service.addEventListener(TexEventType.ON_RECV_KICK_OVER_BEITI, this, "onRecvKickBeiti");
		//收到服务端的提示信息
		service.addEventListener(TexEventType.ON_TEX_RECV_SERVER_ERROR, this, "onRecvServerError");

		//播入赠送礼物 
		service.addEventListener(TexEventType.ON_TEX_RECV_PLAY_GIFT, this, "onRecvPlayGift");
		//太富有不能坐下
		service.addEventListener(Event.ON_RECV_RICH_CANNOT_TOROOM, this, "onRecvRichCanNotToRoom");
		//收到指定座位礼物 
		service.addEventListener(TexEventType.ON_TEX_RECV_GIFT_ICON, this, "onRecvGiftIcon");
		//不能进入房间
		service.addEventListener(Event.ON_RECV_CAN_NOT_TOROOM, this, "onRecvCanNotToRoom");
		//收到钱不够被踢出
		service.addEventListener(Event.ON_RECV_LOW_GOLD, this,"onRecvLowGold");
		//收到恢复显示
		service.addEventListener(TexEventType.ON_TEX_RECV_RESETDISPLAY, this,"onRecvResetDisplay");
		//收到桌面彩池信息
		service.addEventListener(TexEventType.ON_TEX_RECV_DESK_POLL_INFO, this,"onRecvDeskPollInfo");
		//收到自己的最佳牌型
		service.addEventListener(TexEventType.ON_TEX_RECV_BESTPOKES, this,
		"onRecvBestPokes");
		//收到玩家投降
		service.addEventListener(TexEventType.ON_TEX_RECV_GIVEUP, this, "onRecvGiveUp");
		//收到玩家不下注
		service.addEventListener(TexEventType.ON_TEX_RECV_BUXIAZHU, this,
		"onRecvBuXiaZhu");
		//收到游戏开始
		service.addEventListener(TexEventType.ON_TEX_RECV_GAMESTART, this,
		"onRecvGameStart");
		//用户站起
		service.addEventListener(TexEventType.ON_TEX_RECV_STANDUP, this,
		"onRecvStandup");
		
		//收到购买筹码
		service.addEventListener(TexEventType.ON_TEX_RECV_BUYCHOUMA, this,
		"onRecvBuyChouma");
		//收到游戏结束
		service.addEventListener(TexEventType.ON_TEX_RECV_GAMEOVER, this,
			"onRecvGameOver");
		
		//收到某个座位的金币刷新
	    service.addEventListener(TexEventType.ON_TEX_RECV_REFRESHGOLD, this,
			"onRecvRefreshGold");
		//显示桌面的牌
        service.addEventListener(TexEventType.ON_TEX_RECV_DESKPOKE, this,
		"onRecvDeskPoke");
		//显示自己的德州豆
		service.addEventListener(TexEventType.ON_TEX_RECV_SHOW_MY_TOTAL_BEAN, this,
		"onRecvShowMyTotalBean");
		//收到玩家下注成功
		service.addEventListener(TexEventType.ON_TEX_RECV_XIAZHUSUCC, this,
		"onRecvXiaZhuSucc");
		//侦听被踢
		service.addEventListener(TexEventType.ON_TEX_RECV_TIMEOUT, this,
		"onRecvKickMe");
		//侦听自动面板
		service.addEventListener(TexEventType.ON_TEX_AUTO_PANEL, this,
		"onTexAutoPanel");
		//侦听面板
		service.addEventListener(TexEventType.ON_TEX_BUTTON_STATUS, this,
		"onTexPanel");
		//侦听发牌
		service.addEventListener(TexEventType.ON_TEX_RECV_FAPAI, this,
		"onTexRecvFaPai");
		//侦听玩家坐下信息
		service.addEventListener(TexEventType.ON_TEX_RECV_SITDOWN, this,
		"onTexRecvSitDown");
		// 侦听桌子信息
		service.addEventListener(TexEventType.ON_TEX_RECV_DESK_INFO, this,
				"onTexRecvDeskInfo");
		// 侦听桌子上用户列表
		service.addEventListener(Event.ON_RECV_DESK_USERLIST, this,
				"onRecvDeskUserList");
		// 侦听更新所有玩家状态
		service.addEventListener(TexEventType.ON_TEX_RECV_PLAYERINFO, this,
		"onTexRecvPlayerInfo");
		//侦听桌内聊天
		service.addEventListener(TexEventType.ON_TEX_RECV_CHAT, this,
						"onTexRecvChat");
		service.addEventListener(TexEventType.ON_TEX_RECV_PLAY_EMOT, this,
				"onTexRecvEmot");
	}
	/**
	 *移除侦听事件.
	 */
	private void removeListener(){
		if(service ==null){
			return;
		}
		service.removeEventListener(TexEventType.ON_RECV_GIVE_JIUJI, this, "onRecvGiveJiuJi");
		service.removeEventListener(Event.ON_RECV_WATCH_ERROR, this, "onRecvWatchError");
		//收到被踢
		service.removeEventListener(TexEventType.ON_RECV_KICK_OVER_BEITI, this, "onRecvKickBeiti");
		//收到服务端的提示信息
		service.removeEventListener(TexEventType.ON_TEX_RECV_SERVER_ERROR, this, "onRecvServerError");
		//播入赠送礼物 
		service.removeEventListener(TexEventType.ON_TEX_RECV_PLAY_GIFT, this, "onRecvPlayGift");
		//太富有不能坐下
		service.removeEventListener(Event.ON_RECV_RICH_CANNOT_TOROOM, this, "onRecvRichCanNotToRoom");
		//收到指定座位礼物 
		service.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_ICON, this, "onRecvGiftIcon");
		//不能进入房间
		service.removeEventListener(Event.ON_RECV_CAN_NOT_TOROOM, this, "onRecvCanNotToRoom");
		//收到钱不够被踢出
		service.removeEventListener(Event.ON_RECV_LOW_GOLD, this,"onRecvLowGold");
		//恢复显示
		service.removeEventListener(TexEventType.ON_TEX_RECV_RESETDISPLAY, this,"onRecvResetDisplay");
		//收到桌面彩池信息
		service.removeEventListener(TexEventType.ON_TEX_RECV_DESK_POLL_INFO, this,"onRecvDeskPollInfo");
		//收到自己的最佳牌型
		service.removeEventListener(TexEventType.ON_TEX_RECV_BESTPOKES, this,
		"onRecvBestPokes");
		//收到玩家投降
		service.removeEventListener(TexEventType.ON_TEX_RECV_GIVEUP, this, "onRecvGiveUp");
		//收到玩家不下注
		service.removeEventListener(TexEventType.ON_TEX_RECV_BUXIAZHU, this,
		"onRecvBuXiaZhu");
		//收到游戏开始
		service.removeEventListener(TexEventType.ON_TEX_RECV_GAMESTART, this,
		"onRecvGameStart");
		//用户站起
		service.removeEventListener(TexEventType.ON_TEX_RECV_STANDUP, this,
		"onRecvStandup");
		
		//收到购买筹码
		service.removeEventListener(TexEventType.ON_TEX_RECV_BUYCHOUMA, this,
		"onRecvBuyChouma");
		//收到游戏结束
		service.removeEventListener(TexEventType.ON_TEX_RECV_GAMEOVER, this,
			"onRecvGameOver");
		
		//收到某个座位的金币刷新
	    service.removeEventListener(TexEventType.ON_TEX_RECV_REFRESHGOLD, this,
			"onRecvRefreshGold");
		//显示桌面的牌
        service.removeEventListener(TexEventType.ON_TEX_RECV_DESKPOKE, this,
		"onRecvDeskPoke");
		//显示自己的德州豆
		service.removeEventListener(TexEventType.ON_TEX_RECV_SHOW_MY_TOTAL_BEAN, this,
		"onRecvShowMyTotalBean");
		//收到玩家下注成功
		service.removeEventListener(TexEventType.ON_TEX_RECV_XIAZHUSUCC, this,
		"onRecvXiaZhuSucc");
		//侦听被踢
		service.addEventListener(TexEventType.ON_TEX_RECV_TIMEOUT, this,
		"onRecvKickMe");
		//侦听自动面板
		service.removeEventListener(TexEventType.ON_TEX_AUTO_PANEL, this,
		"onTexAutoPanel");
		//侦听面板
		service.removeEventListener(TexEventType.ON_TEX_BUTTON_STATUS, this,
		"onTexPanel");
		//侦听发牌
		service.removeEventListener(TexEventType.ON_TEX_RECV_FAPAI, this,
		"onTexRecvFaPai");
		//侦听玩家坐下信息
		service.removeEventListener(TexEventType.ON_TEX_RECV_SITDOWN, this,
		"onTexRecvSitDown");
		// 侦听桌子信息
		service.removeEventListener(TexEventType.ON_TEX_RECV_DESK_INFO, this,
				"onTexRecvDeskInfo");
		// 侦听桌子上用户列表
		service.removeEventListener(Event.ON_RECV_DESK_USERLIST, this,
				"onRecvDeskUserList");
		// 侦听更新所有玩家状态
		service.removeEventListener(TexEventType.ON_TEX_RECV_PLAYERINFO, this,
		"onTexRecvPlayerInfo");
		//侦听桌内聊天
		service.removeEventListener(TexEventType.ON_TEX_RECV_CHAT, this,
				"onTexRecvChat");
		
		service.removeEventListener(TexEventType.ON_TEX_RECV_PLAY_EMOT, this,
				"onTexRecvEmot");
		
		
	}
	/**
	 * 收到钱不够被踢出
	 * @param e
	 */
	public void onRecvLowGold(Event e){
		Log.i(tag, "onRecvLowGold");
		sendMsg(recvLowGold, e.getData());
	}
	/**
	 * 收到桌面彩池信息
	 * @param e
	 */
	public void onRecvDeskPollInfo(Event e){
		Log.i(tag, "onRecvDeskPollInfo");
		sendMsg(recvDeskPollInfo, e.getData());
	}
	/**
	 * 收到最佳牌型
	 * @param e
	 */
	public void onRecvBestPokes(Event e){
		Log.i(tag, "onRecvBuXiaZhu");
		sendMsg(recvBestPokes, e.getData());
	}
	/**
	 * 收到玩家不下注
	 * @param e
	 */
	public void onRecvBuXiaZhu(Event e){
		Log.i(tag, "onRecvBuXiaZhu");
		sendMsg(recvBuXiaZhu, e.getData());
	}
	/**
	 * 收到游戏开始
	 * @param e
	 */
	public void onRecvGameStart(Event e){
		Log.i(tag, "onRecvGameStart");
		sendMsg(recvGameStart, e.getData());
	}
	
	/**
	 * 收到用户站起
	 * @param e
	 */
	public void onRecvStandup(Event e){
		Log.i(tag, "onRecvStandup");
		sendMsg(recvStandup, e.getData());
	}
	/**
	 * 收到购买筹码窗
	 * @param e
	 */
	public void onRecvBuyChouma(Event e){
		Log.i(tag, "onRecvBuyChouma");
		//showBuyChouMaDialog((HashMap)e.getData());
		sendMsg(recvBuyChouma, e.getData());
		
	}
	/**
	 * 收到游戏结束
	 * @param e
	 */
	public void  onRecvGameOver(Event e){
		Log.i(tag, "onRecvGameOver");
		sendMsg(recvGameOver, e.getData());
	}
	/**
	 * 收到某个座位的金币刷新
	 * @param e
	 */
	public void onRecvRefreshGold(Event e){
		Log.i(tag, "onRecvRefreshGold");
		sendMsg(recvRefreshGold, e.getData());
	}
	/**
	 * 显示桌面的牌
	 * @param e
	 */
	public void onRecvDeskPoke(Event e){
		Log.i(tag, "onRecvDeskPoke");
		sendMsg(recvDeskPoke, e.getData());
	}
	/**
	 * 显示自己的德州豆
	 * @param e
	 */
	public void  onRecvShowMyTotalBean(Event e){
		Log.i(tag, "onRecvShowMyTotalBean");
		sendMsg(recvShowMyTotalBean, e.getData());
	}
	/**
	 * 收到用户下注成功
	 * @param e
	 */
	public void onRecvXiaZhuSucc(Event e){
		Log.i(tag, "onRecvXiaZhuSucc");
		sendMsg(recvXiaZhuSucc, e.getData());
	}
	/**
	 * 侦听被踢
	 * @param e
	 */
	public void onRecvKickMe(Event e){
		Log.i(tag, "onTexAutoPanel");
		sendMsg(recvKickMe, e.getData());
	}
	/**
	 * 侦听到自动面板
	 * @param e
	 */
	public void onTexAutoPanel(Event e){
		Log.i(tag, "onTexAutoPanel");
		sendMsg(recvAutoPanel, e.getData());
	}
	/**
	 * 侦听到面板
	 * @param e
	 */
	public void onTexPanel(Event e){
		Log.i(tag, "onTexPanel");
		sendMsg(recvPanel, e.getData());
	}
	/**
	 * 侦听到更新所有玩家状态
	 * @param e
	 */
	public void  onTexRecvPlayerInfo(Event e){
		Log.i(tag, "onTexRecvPlayerInfo");
		sendMsg(recvPlayerInfo, e.getData());
	}
	/**
	 * 侦听接收到桌子上用户列表
	 * 
	 * @param e
	 */
	public void onRecvDeskUserList(Event e) {
		Log.i(tag, "onRecvDeskUserList");
		sendMsg(recvDeskUserList, e.getData());
	}
	/**
	 * 侦听到桌子信息
	 * @param e
	 */
	public void onTexRecvDeskInfo(Event e){
		Log.i(tag, "onTexDeskInfo");
       
		sendMsg(recvDeskInfo,e.getData());
	}
	/**
	 * 玩家坐下
	 * @param e
	 */
	public void onTexRecvSitDown(Event e){
		Log.i(tag, "onTexRecvSitDown");
		sendMsg(recvSitDown, e.getData());
	}
	/**
	 * 收到发牌
	 * @param e
	 */
	public void  onTexRecvFaPai(Event e){
		Log.i(tag, "onTexRecvFaPai");
		Log.i("test14", "4onTexFaPaionTexFaPaionTexFaPaionTexFaPai: "+System.currentTimeMillis());
		sendMsg(recvFaPai, e.getData());
	}
	/**
	 * 收到恢复显示
	 * @param e
	 */
	public void  onRecvResetDisplay(Event e){
		Log.i(tag, "onRecvResetDisplay");
		sendMsg(recvResetDisplay, e.getData());
	}
	/**
	 * 收到玩家投降
	 * @param e
	 */
	public void onRecvGiveUp(Event e){
		sendMsg(recvGiveUp, e.getData());
	}
	/**
	 * 收到座位礼物
	 * @param e
	 */
	public void onRecvGiftIcon(Event e){
		Log.i(tag, "Game onRecvGiftIcon");
		sendMsg(recvGiftIcon, e.getData());
	}
	/**
	 * 收到播入赠送礼物
	 * @param e
	 */
	public void onRecvPlayGift(Event e){
		Log.i(tag, "Game onRecvPlayGift");
		sendMsg(recvPlayGiftIcon, e.getData());
	}
	/**
	 * 收到坐下结果
	 * @param e
	 */
	public void onRecvSitDownResult(Event e){
		sendMsg(recvSitDownResult, e.getData());
	}
	/**
	 * 收到服务端的提示信息
	 * @param e
	 */
	public void onRecvServerError(Event e){
		sendMsg(recvServerError, e.getData());
	}
	/**
	 * 太富有不能坐下
	 * @param e
	 */
	public void  onRecvRichCanNotToRoom(Event e){
		sendMsg(recvRichNotInToRoom, null);
	}
	/**
	 * 收到被踢
	 * @param e
	 */
	public void onRecvKickBeiti(Event e){
		sendMsg(recvBeiTi, null);
	}
	 
	public void onRecvWatchError(Event e){
		sendMsg(recvWatchError, e.getData());
	}
	/**
	 * 收到救济
	 * @param e
	 */
	public void onRecvGiveJiuJi(Event e){
		sendMsg(recvJiuJi, e.getData());
	}
	public void onTexRecvChat(Event e){
		sendMsg(recvChat, e.getData());
	}
	public void onTexRecvEmot(Event e){
		sendMsg(recvEmot, e.getData());
	}
	private void sendMsg(final int flag,final Object obj) {
//		Message msg = new Message();
//        msg.what = flag;
//        msg.obj = obj;
//        handler.sendMessage(msg);
		if(flag == recvPlayerInfo){
			 Log.i("test14","recvPlayerInfo");
			 Message msg = new Message();
	         msg.what = flag;
	         msg.obj = obj;
	         //handler2.sendMessage(msg);
			 playerDialog.sendMsg(msg, gameDataManager);
			 //gameDataManager.setPlayerInfoState((ArrayList)msg.obj); 
			 return;
		};
        if(flag ==recvFaPai)
        Log.i("test14", "44onTexFaPaionTexFaPaionTexFaPaionTexFaPai: "+System.currentTimeMillis());
		//单线程处理
		TaskSingleManager.getInstance().execute(new TaskExecutorAdapter(){
			@Override
			public int executeTask() throws Exception {
				Log.i(tag, "flag: "+flag);
				Message msg = new Message();
		        msg.what = flag;
		        msg.obj = obj;
		        handler.sendMessage(msg);
				return 0;
			}
			
		});
		
	}
	
	
	Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
			if (!Thread.currentThread().isInterrupted()) {
				 
				 if(gameDataManager==null){
					 return;
				 }
				 switch(msg.what){
					case recvPlayerInfo:
						gameDataManager.setPlayerInfoState((ArrayList)msg.obj); 
				 }
			}
		}};
	
	Handler handler = new Handler() {
		@SuppressWarnings("rawtypes")
		public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
			if (!Thread.currentThread().isInterrupted()) {
				 
				 if(gameDataManager==null){
					 return;
				 }
				switch(msg.what){
				case finish:
					backToRoom();
					break;
				case chongzhi:
					backToChongZhi();
					break;
				case recvDeskUserList:
					gameDataManager.setDeskUsers((HashMap)msg.obj); 
					break;
				case recvPlayerInfo:
					gameDataManager.setPlayerInfoState((ArrayList)msg.obj); 
				   break;
				case recvSitDown:
					gameDataManager.addOrReplaceSitDownUser((HashMap)msg.obj); 
				   break;
				case recvDeskInfo:
					deskInfo=(DeskInfo)msg.obj;
					byte deskType=deskInfo.getDesktype();
				    gameView.setDeskInfo(deskInfo);
					//byte isNeedDrawSit = deskInfo.getShowsitbtn();
					Log.i(tag,"gjghggg isNeedDrawSit: "+deskInfo.getShowsitbtn());
					if(!gameDataManager.hasPalerUp2){
						Log.i(tag,"execute sitdown");
						setSitViewState();
					}
					 activity.setGameDeskBackImg(deskType);
					//gameDataManager.setDeskInfo(deskInfo); 
				   break;
				case recvFaPai:
					Log.i("test14", "5onTexFaPaionTexFaPaionTexFaPaionTexFaPai: "+System.currentTimeMillis());
					gameDataManager.setRecvFaPai((HashMap)msg.obj); 
				   break;
				case recvAutoPanel:
					gameDataManager.setRecvPanel(2,(HashMap)msg.obj); 
				   break;
				case recvPanel:
					gameDataManager.setRecvPanel(1,(HashMap)msg.obj); 
				   break;
				case recvKickMe:
					gameDataManager.setRecvKickMe(); 
				   break;
				case recvXiaZhuSucc:
					gameDataManager.setRecvXiaZhuSucc((HashMap)msg.obj); 
				   break;
				case recvShowMyTotalBean:
					gameDataManager.setRecvShowMyTotalBean((Integer)msg.obj); 
					break;
				case recvDeskPoke:
					gameDataManager.addDeskPoke((ArrayList)msg.obj); 
					break;
				case recvRefreshGold:
					gameDataManager.setRecvRefreshGold((HashMap)msg.obj); 
					break;
				case recvGameOver:
					gameDataManager.setRecvGameOver((HashMap)msg.obj); 
					break;
				case recvBuyChouma:
					buyChouMadata =(HashMap)msg.obj;
					if(!gameDataManager.GameOver){
					  showBuyChouMaDialog();
					}else{
						isCanNotRoom =false;
						isNeedShowBuyChouMa =true;
					}
					//gameDataManager.setRecvBuyChouma((HashMap)msg.obj); 
					break;
				case recvStandup:
					 
					gameDataManager.setRecvStandUp((HashMap)msg.obj); 
					break;
				case recvGameStart:
					gameDataManager.setRecvGameStart(); 
					break;
				case recvBuXiaZhu:
					gameDataManager.setRecvBuXiaZhu((Byte)msg.obj);
					break;
				case recvBestPokes:
					//activity.setRecvBestPokes((HashMap)msg.obj);
					toastPaiXingView.setRecvBestPokes((HashMap)msg.obj);
					//gameDataManager.setRecvBestPokess((HashMap)msg.obj);
					break;
				case recvDeskPollInfo:
					gameDataManager.setRecvDeskPollInfo((ArrayList)msg.obj);
					break;
				case recvResetDisplay:
					gameDataManager.setRecvResetDisplay((HashMap)msg.obj);
					break;
				case recvLowGold:
					//收到钱不够被踢出
					 dismiss();
					break;
				case recvGiveUp:
					gameDataManager.setRecvGiveUp((Byte)msg.obj);
					//playerViewManager.setPlayerStateBySiteNo((Byte)msg.obj, 6);
					break;
				case recvNotInToRoom:
					
					canNotRoomData =(HashMap)msg.obj;
				    if(!gameDataManager.GameOver){
					    executeCanNotIntoRoom();
					}else{
						isNeedShowBuyChouMa =false;
						isCanNotRoom = true;
					}
					break;
				case recvGiftIcon:
					//收到礼物
					gameDataManager.setGiftIcon((HashMap)msg.obj);
					//playerViewManager.setGiftIcon((HashMap)msg.obj);
					break;
				case recvPlayGiftIcon:
					//播放赠送礼物
					gameDataManager.setPlayGiftIcon((HashMap)msg.obj);
					break;
				case recvRichNotInToRoom:
					HwgCallBack callback1 = new HwgCallBack(){
						public  void CallBack(Object[] obj) {
							sendRequestBackHall(0);
						};
					};
					GameUtil.openMessageDialog(getContext(),GameUtil.CHOUMADUO,callback1);
					break;
				case recvSitDownResult:
					int code=(Integer)msg.obj;
					break;
				case recvServerError:
					GameUtil.openMessageDialog(getContext(),(String)msg.obj);
					break;
				case recvBeiTi:
					HwgCallBack callback2 = new HwgCallBack(){
						public  void CallBack(Object[] obj) {
							sendRequestBackHall(0);
						};
					};
					GameUtil.openMessageDialog(getContext(),GameUtil.BEITI,callback2);
					break;
				case recvWatchError:
					 GameUtil.onRecvWatchError(DzpkGameActivityDialog.this,(HashMap)msg.obj);
					break;
				case recvJiuJi:
					showJiuJiInfo((HashMap)msg.obj);
					break;
				case recvChat:
					//处理聊天
					talkChatViewManager.executeChat((HashMap)msg.obj);
					break;
				case recvEmot:
					 //处理表情
					talkQingViewManager.executeBiaoQing((HashMap)msg.obj);
					break;
				}
			}
			super.handleMessage(msg);
		}

	
	};
	//筹码不足不能进入房间
	HashMap canNotRoomData;
	boolean isCanNotRoom=false;
	private void executeCanNotIntoRoom() {
		isCanNotRoom =false;
		HwgCallBack callback = new HwgCallBack(){
			public  void CallBack(Object[] obj) {
				sendRequestBackHall(0);
			};
		};
		//不能进入房间
		GameUtil.executeCanNotIntoRoom(this.getContext(),canNotRoomData,callback);

	}
	
	 //购买筹码的数据
	 HashMap buyChouMadata;
	 boolean isNeedShowBuyChouMa=false;
	   /**
	    * 显示购买筹码窗
	    * @param data
	    */
	public void showBuyChouMaDialog(){
		sitView.clickSit =false;
		isNeedShowBuyChouMa =false;
		Integer min=(Integer)buyChouMadata.get("min");
		Integer gold=(Integer) buyChouMadata.get("gold");
		if(gold < min){
			//请求救济金
			TaskManager.getInstance().execute(new TaskExecutorAdapter(){

				@Override
				public int executeTask() throws Exception {
					service.sendRequestJiuJi();
					return 0;
				}
				
			});
//			HwgCallBack callback = new HwgCallBack(){
//				public void CallBack(Object... obj) {
//					//请求返回大厅
//					sendRequestBackHall(1);
//				}
//			};
//			GameUtil.openMessageDialog(DzpkGameActivity.this,GameUtil.msg4,callback);
		}else{
		  if(playerViewManager.mySite && playerViewManager.getPlayerState() != 11){
			  //当前玩家已坐下，且不为购买筹码状态，则不需要弹出购买窗
			  return;
		  }
		  BuyChouMaDialog bcmd =new BuyChouMaDialog(this.getContext(),R.style.dialog,buyChouMadata);
		  bcmd.show();
		}
	}
	/**
	 * 显示救济信息
	 * @param data
	 */
	private void showJiuJiInfo(HashMap data){
		if(isquick == false){
			
			if (data != null) {
				Byte cangive = (Byte) data.get("cangive");
				Byte lqcishu = (Byte) data.get("lqcishu");
				HwgCallBack callback = new HwgCallBack() {
					public void CallBack(Object... obj) {
						if(obj == null || obj.length ==0){
						 //去到充值界面
						sendRequestBackToChongZhi();
						}
					 
					}
				};
				if (cangive == -1) {
					
					GameUtil.openMessage1Dialog(this.getContext(),
							GameUtil.msg4, callback,GameUtil.chongZhi);
				} else {
					GameUtil.openMessage1Dialog(this.getContext(),
							GameUtil.msgJiuJi + "\r\n(本日" + lqcishu + "次,共3次)",
							callback,GameUtil.chongZhi,GameUtil.sure);
				}

			}
		}else{
			//立即开始
			if(type ==1 || type ==2){
				Byte cangive=(Byte)data.get("cangive");
				Byte lqcishu=(Byte)data.get("lqcishu");
				backToLjKs(type,cangive,lqcishu);
			}
			 
		}
	}
	/**
	 *返回至点击立即开始的界面
	 */
	private void backToLjKs(int type,int cangive,int lqcishu){
		Intent it =new Intent();
		it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		it.putExtra("jiuji", 1);
		it.putExtra("cangive", cangive);
		it.putExtra("lqcishu", lqcishu);
		if(type == 2){
			it.setClass(activity, DzpkGameMenuActivity.class);
		}else if(type == 1){
			it.setClass(activity, DzpkGameRoomActivity.class);
		}
		activity.startActivity(it);
		activity.finish(); 
	}
	
	/**
     * 下注
     * @param gold 下注金额
     * @param type 1:下注 2:加注
     */
	public void sendClickXiaZhu(final int gold,final int type){
		TaskManager.getInstance().execute(new TaskExecutorAdapter() {
			public int executeTask() throws Exception {
				service.sendClickXiaZhu(gold,type);
				return 0;
			}

		});
	}
	/**
     * 不下注
     * 
     */
	public void sendClickBuxia(){
		TaskManager.getInstance().execute(new TaskExecutorAdapter() {
			public int executeTask() throws Exception {
				service.sendClickBuxia();
				return 0;
			}

		});
	}
 
	/**
	 * 发送跟注
	 * @throws Exception
	 */
	public void sendClickFollow(){
		TaskManager.getInstance().execute(new TaskExecutorAdapter() {
			public int executeTask() throws Exception {
				service.sendClickFollow();
				return 0;
			}

		});
	}
	 /**
	  * 发送放弃
	  * @throws Exception
	  */
	public void sendClickGiveUp() {
		TaskManager.getInstance().execute(new TaskExecutorAdapter() {
			public int executeTask() throws Exception {
				service.sendClickGiveUp();
				return 0;
			}
		});
	}
	boolean isfirst =true;
	//设置座位状态
	private void setSitViewState(){
		if(deskInfo == null){
			return;
		}
		if((type ==1 || type ==2) && isfirst){
	    		isfirst = false;
	    		return;
	    }
 	    if(deskInfo.getShowsitbtn() ==1){
 	    	
 	    	Log.i(tag,"execute display");
 	    	Integer	default_chouma =(Integer)GameApplication.userInfo.get("default_chouma");
 	    	if(default_chouma != null && default_chouma >= deskInfo.getAt_least_gold()){
 	    		//设置快速开始按钮
 		    	gameBottomView.addQuickStartButton();
 	    	}else{
 	    		gameBottomView.removeQuickView();
 	    	}
	    	//座位显示
  			sitView.setNeedDraw(true);
  			
  		}else{
  			//座位不显示
  			sitView.setNeedDraw(false);
  			gameBottomView.removeQuickView();
  			Log.i(tag,"execute not display");
  		}
  		 
	}
	
	/**
	 * 重置信息
	 */
	public void reset(){
		 
		//TaskSingleManager2.getInstance().cancel();
		//TaskSingleManager2.setTaskSingleManagerNull();
		 toastPaiXingView.reset();
         //设置座位状态
		 setSitViewState();
		 //gameDataManager.executeStandUpAction2();
		//清空结算数据信息
		 DJieSuan.initData();
        //重置公共牌
		 globalPokeManager.reset();
		//重置玩家小背牌牌
		 pokeBackViewManager.reset();
		//重置玩家牌
		 otherPokeViewManager.reset();
		 //重置筹码
		 playerChouMaViewManager.reset();
		//重置自己的牌
		 myPoke.reset();
		//重置公共牌型
		 pokeTypeView.reset();
		//重置庄家
		 zjViewManager.reset();
		//重置彩池
		 pondViewManager.reset();
		 //重置玩家
		 playerViewManager.reset();
		//重置玩家状态
		 gameDataManager.updatePlayerState(true);
		//是否需要显示购买筹码窗
		 if(isNeedShowBuyChouMa){
			 showBuyChouMaDialog();
		 }
		 if(isCanNotRoom){
			 executeCanNotIntoRoom();
		 }
		 System.gc();
		 //结算结束
		 GameApplication.jieSuanIng=false;
		 gameDataManager.executeSitDownAction();
	}
	/**
	 * 保存当前状态
	 */
	public void saveCurrentState(){
		pokeBackViewManager.saveCurrentState();
		otherPokeViewManager.saveCurrentState();
		zjViewManager.saveCurrentState();
	    playerChouMaViewManager.saveCurrentState();
	    jsqViewManager.setStop();
	}
	/**
	 *
	 * 恢复当前状
	 */
	public void backCurrentState(){
		pokeBackViewManager.backCurrentState();
		otherPokeViewManager.backCurrentState();
		zjViewManager.backCurrentState();
		playerChouMaViewManager.backCurrentState();
	}
	//离开房间
	private void leaveRooms(){
		//停止我赢
		myWinView.stopThread();
		//停止计时器
	    jsqViewManager.setStop();
	    //请求返回大厅
	    sendRequestBackHall(1);
		 //sendRequestBackHall(-1);
	}
	 //离开房间
	private void leaveRoom(){
//		 Intent it =new Intent();
//		 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		 it.setClass(this.getContext(), DzpkGameRoomActivity.class);
//		 this.getContext().startActivity(it);
		  HwgCallBack callback = new HwgCallBack() {
				
				@Override
				public void CallBack(Object... obj) {
					isBackClick =false;
					backButton.setIsclick(false);
					if(obj == null || obj.length ==0){
						leaveRooms();
					}
				}
			  };
	    	  GameUtil.openMessage1Dialog(this.getContext(), "确定离开房间吗？", callback);
	    	  
	}

//    public void onAttachedToWindow() {
//    	 this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
//        //this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//        super.onAttachedToWindow();
//    }
	/**
	 * 收到不能进入房间
	 * @param e
	 */
	public void onRecvCanNotToRoom(Event e){
	     sendMsg(recvNotInToRoom, e.getData()); 
     }

	@Override
	protected void onStop() {

		super.onStop();
		try {
			if (vibrator != null) {
				vibrator.cancel();
			}
		} catch (Exception e) {

		}
	}

	public void startVibrate(long mils) {
		try {
			if (vibrator == null) {
				vibrator = (Vibrator) getContext().getSystemService(
						Context.VIBRATOR_SERVICE);
			}
			if (vibrator != null) {
				//long[] pattern = { 100, 400, 100, 400 }; // 停止 开启 停止 开启
				//vibrator.vibrate(pattern, 1);// 按pattern节奏震动一次
				vibrator.vibrate(mils);
			}
		} catch (Exception e) {

		}
	}
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		boolean result= super.onTouchEvent(event);
//		if(result ==false){
//			result = faiPaiDialog.onTouchEvent(event);
//		}
//		return result;
//	}
    public void destroy() {
    	Log.i("test19", "DzpkGameActivityDialog destroy");
		 removeListener();
		 MediaManager.getInstance().pauseSoundAll();
		 if(cnetThread !=null && !cnetThread.isInterrupted()){
			 cnetThread.interrupt();
			 cnetThread=null;
		 }
    	if(frameLayout != null){
    		frameLayout.removeAllViews();
    	}
        frameLayout =null;
//		if (sitView != null) {
//			sitView.destroy();
//		}
//		sitView = null;
//        if(zjViewManager != null){
//        	zjViewManager.destroy();
//        }
//        zjViewManager =null;
		if (gameView != null) {
			gameView.destroy();
		}
		gameView = null;
		

		if (jsqViewManager != null) {
			jsqViewManager.setStop();
			jsqViewManager.destroy();
		}
		jsqViewManager = null;
		

	

		if (standButton != null) {
			standButton.destroy();
		}
		standButton = null;
		if (backButton != null) {
			backButton.destroy();
		}
		backButton = null;
		

		if (playerChouMaViewManager != null) {
			playerChouMaViewManager.destory();
		}
		playerChouMaViewManager = null;

		if (pondViewManager != null) {
			pondViewManager.destory();
		}
		pondViewManager = null;

		if (pondViewManager1 != null) {
			pondViewManager1.destory();
		}
		pondViewManager1 = null;
		if (gameBottomView != null) {
			gameBottomView.destroy();
		}
		gameBottomView = null;

		

		if (playerWinMoneyManager != null) {
			playerWinMoneyManager.destroy();
		}
		playerWinMoneyManager = null;

		if (allInViewManager != null) {
			allInViewManager.destroy();
		}
		allInViewManager = null;
		
		if (allInViewManager1 != null) {
			allInViewManager1.destroy();
		}
		allInViewManager1 = null;
		
		

		if (xiaZhuViewManager != null) {
			xiaZhuViewManager.destory();
		}
		xiaZhuViewManager = null;
		
		if(checkNetView!= null){
			checkNetView.destory();
		}
		checkNetView=null;
		
		if (myWinView != null) {
			myWinView.destroy();
		}
		myWinView = null;

		if (toastPaiXingView != null) {
			toastPaiXingView.destroy();
		}
		toastPaiXingView = null;
	    if(sitnoDisplayViewManager != null){
	    	sitnoDisplayViewManager.destroy();
	    }
	    sitnoDisplayViewManager=null;
	    
	    if(talkChatViewManager != null){
	    	talkChatViewManager.destroy();
	    }
	    talkChatViewManager=null;
	    
	    if(talkQingViewManager != null){
	    	talkQingViewManager.destroy();
	    }
	    talkQingViewManager=null;
	     
		//GameApplication.setDzpkGame(null);
		/*******************PlayerDialog中************************/
//		if (playerViewManager != null) {
//			playerViewManager.destroy();
//		}
//		playerViewManager = null;
//		if (pokeBackViewManager != null) {
//			pokeBackViewManager.destroy();
//		}
//		pokeBackViewManager = null;

//		if (myPoke != null) {
//			myPoke.destroy();
//		}
//		myPoke = null;
 
		/*******************FaiPaiDialog中************************/
//		if (faPaiManager != null) {
//			faPaiManager.destroy();
//		}
//		faPaiManager = null;
//		
//		if (globalPokeManager != null) {
//			globalPokeManager.destroy();
//		}
//		globalPokeManager = null;
//		if (pokeTypeView != null) {
//			pokeTypeView.destroy();
//		}
//		pokeTypeView = null;
//		if(otherPokeViewManager != null){
//			otherPokeViewManager.destory();
//		}
//		 
//		otherPokeViewManager =null;
	}
    short activityState =0;
	/**
	 * 侦听按键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if (keyCode == KeyEvent.KEYCODE_BACK  ) {
	    	     //GameUtil.backExit(DzpkGameActivity.this);
	    	   leaveRoom();
	          return false;
	       }
	       if(keyCode == KeyEvent.KEYCODE_MENU){
	    	   return false;
	       }
//	       if( keyCode == KeyEvent.KEYCODE_HOME){
//	    	  // Toast.makeText(this,"keyCode: "+keyCode+"  KEYCODE_HOME:  "+KeyEvent.KEYCODE_HOME,Toast.LENGTH_LONG).show(); 
//	    	   GameUtil.exitGame(DzpkGameActivity.this);
//	    	   return true;
//	       }
	  return super.onKeyDown(keyCode, event);
	} 
	 
	public boolean onKeyUp(final int keyCode, final KeyEvent event){
		 if( keyCode == KeyEvent.KEYCODE_HOME){
			 HwgCallBack callback = new HwgCallBack() {
				public void CallBack(Object... obj) {
					  dismiss();
				}
			 };
			 GameUtil.openMessage1Dialog(this.getContext(), "此操作将结束当前游戏", callback);
             return true;
		  }
		 return false;
	}
    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
    	// TODO Auto-generated method stub
    	 Log.i("test18", "onKeyMultiple : "+keyCode+" repeatCount: "+repeatCount);
    	 if(keyCode == KeyEvent.KEYCODE_MENU){
    		 Log.i("test18", "onKeyMultiple : "+keyCode);
	    	   return false;
	     }
    	return super.onKeyMultiple(keyCode, repeatCount, event);
    }
    
  
//	 /**
//	  * 销毁
//	  */
//	protected void onDestroy() {
//		 
//			Log.i(tag, "onDestroy");
//			Log.i("test1", "prev destroy "+this.hashCode());
//			GameApplication.testNetCun();
//			Log.i(tag,"DzpkGameActivity onDestroy: "+this.hashCode());
//			GameUtil.recycle(currBgBitmap);
//			currBgBitmap =null;
//			activityState=-1; 
//			destroy();
//			
//			System.gc();
//			Log.i("test1", "next destroy ");
//			GameApplication.testNetCun();
//			isDestroy =true;
//	}
	
	
//	/**
//	 * 用户可交互
//	 */
//	protected void onResume() {
//		super.onResume();
//		activityState =0;
//		Log.i(tag,"onResume");
//		GameApplication.currentActivity =this;
//		init();
//		load();
//		
//	}
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//		 activityState=-2;
//		Log.i(tag,"onStop");
//		
//		 
//	}
//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		 activityState=1;
//		Log.i(tag,"onStart");
//	}
//	public boolean restart = false;
//	@Override
//	protected void onRestart() {
//		// TODO Auto-generated method stub
//		//super.onRestart();
//		restart =true;
//		 activityState=2;
//		Log.i(tag,"onRestart");
//	}
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		//super.onPause();
//		 activityState=3;
//		 Log.i(tag,"onPause");
//		 removeListener();
//		 if(cnetThread !=null && !cnetThread.isInterrupted()){
//			 cnetThread.interrupt();
//			 cnetThread=null;
//		 }
//		 //reset();
//		 jsqViewManager.setStop();
//	}
//	/**
//	 * 保存锁屏信息
//	 */
//	private void saveLocked() {
//		//需要保存用户信息
//		SharedPreferences uiState  = this.getSharedPreferences("locked",MODE_WORLD_WRITEABLE);
//		SharedPreferences.Editor editor = uiState.edit();
//		editor.putBoolean("locked", true);
//		editor.commit(); 
//		System.out.println("save success");
//	}
//	
//	private boolean getIsLocked(){
//		SharedPreferences uiState  = this.getSharedPreferences("locked",MODE_WORLD_WRITEABLE);
//		boolean result= uiState.getBoolean("locked", false);
//		if(result){
//			SharedPreferences.Editor editor = uiState.edit();
//			editor.remove("locked");
//			editor.commit(); 
//		}
//		return result;
//	}
}