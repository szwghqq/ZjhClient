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
 * ��Ϸ���ͼ
 * @author hewengao
 *
 */
public class DzpkGameActivityDialog extends Dialog implements CallBack {
	public boolean restart =false;
	//���յ��������û��б�
	private static final  int recvDeskUserList=0;
	//�����������״̬
	public static final int recvPlayerInfo=1;
	//�û�������Ϣ
	private static final int  recvSitDown=2;
	//������Ϣ
	private static final int  recvDeskInfo=3;
	//�յ�����
	private static final int  recvFaPai=4;
	//�յ��Զ����
	private static final int  recvAutoPanel=5;
	//�յ����
	private static final int  recvPanel=6;
	//����
	private static final int  recvKickMe=7;
	//��ע�ɹ�
	private static final int  recvXiaZhuSucc=8;
	//��ʾ�Լ��ĵ��ݶ�
	private static final int  recvShowMyTotalBean=9;
	//��ʾ�������
	private static final int  recvDeskPoke=10;
	//�յ�ĳ����λ�Ľ��ˢ��
	private static final int recvRefreshGold =11;
	//��Ϸ����
	private static final int recvGameOver =12;
	//�յ�������봰
	private static final int recvBuyChouma=13;
	//�û�վ��
	private static final int recvStandup=14;
	//�յ���Ϸ��ʼ
	private static final int recvGameStart=15;
	//�յ���Ҳ���ע
	private static final int recvBuXiaZhu=16;
	//�յ��Լ����������
	private static final int recvBestPokes=17;
	//�յ�����ʳ���Ϣ
	private static final int recvDeskPollInfo=18;
	//�յ��ָ���ʾ
	private static final int recvResetDisplay=19;
	//�յ�Ǯ�������߳�
    private static final int recvLowGold = 20;
    //�յ����Ͷ��
    private static final int recvGiveUp =21;
    //���ܽ��뷿��
    private static final int recvNotInToRoom=22;
    //�յ�ָ����λ����
    private static final int recvGiftIcon =23;
    //̫�����˲�������
    private static final int recvRichNotInToRoom=24;
    //����
    private static final int finish=25;
   
    //������������ 
    private static final int recvPlayGiftIcon= 26;
    //�յ����½��
    private static final int recvSitDownResult=27;
    //�յ�����˵���ʾ��Ϣ
    private static final int recvServerError=28;
    //�յ�����˵���ʾ��Ϣ
    private static final int recvBeiTi=29;
    //�յ���Ϸ�����Ϣ
    private static final int recvWatchError =30;
    //�յ��ȼ�
    private static final int recvJiuJi =31;
    //�յ���������
    private static final int recvChat =32;//ON_TEX_RECV_CHAT;
    private static final int recvEmot =33;//ON_TEX_RECV_CHAT;
    //ȥ��ֵ
    private static final int chongzhi=34;
	//������Ϣ
	public DeskInfo deskInfo = null;
    final String tag="test12";
	SocketService service=null;
	public  FrameLayout frameLayout;
 
	 //��Ϸ�ײ���ť����
	public GameBottomViewManager gameBottomView=null;
	public GameDataManager gameDataManager ;
	private RelativeLayout	mainLayout;
	public SitView sitView;//��λ
	public GameView gameView;
	//�Ƿ���ٿ�ʼ
	public boolean isquick =false;
	TestNeCunThread testNeCun;
	private Bitmap currBgBitmap;
	private int deskType=-1;
	public boolean isBackClick =false;
	//�Ƿ�������
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
	 public  PlayerInfoDialog pInfoDialog=null;//�����Ϣ��
	 public GameZjViewManager zjViewManager;
	// public FaiPaiDialog faiPaiDialog =null;
	 public DzpkGameActivityDialog(DzpkGameActivity context,int theme,final PlayerDialog playerDialog,final FaiPaiDialog faiPaiDialog) {
			super(context,theme);
		    activity = context;
			this.playerDialog=playerDialog;
			//this.faiPaiDialog =faiPaiDialog;
			jsqViewManager = context.jsqViewManager;
			 {
			   //playerDialog��
			    zjViewManager =playerDialog.zjViewManager;
				this.myPoke =playerDialog.myPoke;
				this.pokeBackViewManager=playerDialog.pokeBackViewManager;
				this.playerViewManager = playerDialog.playerViewManager;
			 }
			 
			 {
				  //FaiPaiDialog��
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
 
		//��ֹ�ֻ�����
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
	 
		//���ׯ��
		//addZjViewManager();
		//��ӷ��غ�վ��ť
		addBackAndStandButton();
		//��������ע����
		addPlayerChouMaViewManager();
		//��Ӳʳ���Ϣ
		addPondViewManager();
		addPondViewManager1();
		//��Ϸ�ײ���ť����
		gameBottomView=new GameBottomViewManager(this);
		//�����Ӯ����ͼ
		addMyWinView();
		//������ӮǮ
		addPlayerWinMoneyManager();
		//������AllIn����
		addAllInViewManager();
		//�����ע�������
		addGameXiaZhuViewManager();
        //���������ʾ��Ϣ
		addToastPaiXing();
		//�����λ��ʾ
		addSitNumberViewManager();
		//������������ʾ����
		 addTalkViewManager();
		
		GameApplication.testNetCun();
		    //testNeCun=new TestNeCunThread(this);
		 final ColorDrawable drawable =new ColorDrawable(0);
		this.getWindow().setBackgroundDrawable(drawable); 
	}
	//��λ��ʾ��
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
	 * �����ע�������
	 */
	private void addGameXiaZhuViewManager(){
		xiaZhuViewManager =  new GameXiaZhuViewManager(this);
	}
	public GameAllInViewManager allInViewManager;
	public GameAllInViewManager1 allInViewManager1;
	/**
	 * ������AllIn����
	 */
	private void addAllInViewManager(){
		//allInViewManager=new GameAllInViewManager(this);
		allInViewManager1=new GameAllInViewManager1(this);
	}
	
	public GamePlayerWinMoneyViewManager playerWinMoneyManager;
	/**
	 *������ӮǮ��ͼ����
	 */
	private void addPlayerWinMoneyManager(){
		playerWinMoneyManager=new GamePlayerWinMoneyViewManager(this);
	}
	public GameMyWinView myWinView;
	/**
	 * �����Ӯ����ͼ
	 */
	private void addMyWinView(){
		myWinView=new GameMyWinView(this.getContext());
		this.addView(myWinView);
	}
	public GamePlayerChouMaViewManager playerChouMaViewManager = null;
	/**
	 * ��������ע������Ϣ
	 */
	private void addPlayerChouMaViewManager(){
		playerChouMaViewManager =  new GamePlayerChouMaViewManager(this);
	}
	public PondViewManager pondViewManager1 = null;
	/**
	 * ��Ӳʳ���Ϣ1���ڶ���
	 */
	private void addPondViewManager1(){
		  pondViewManager1 = new PondViewManager(this);
	}
	public PondViewManager pondViewManager = null;
	/**
	 * ��Ӳʳ���Ϣ
	 */
	private void addPondViewManager(){
		  pondViewManager = new PondViewManager(this);
	}
	public GameGolbalPokeTypeView pokeTypeView;
//	/**
//	 * ���ȫ������
//	 */
//	private void addPokeTypeView(){
//		pokeTypeView = new GameGolbalPokeTypeView(this.getContext());
//		frameLayout.addView(pokeTypeView);
//	}
	public GlobalPokeViewManager globalPokeManager;
//	/**
//	 * ��ӹ�����
//	 */
//	private void addGlobalManager(){
//		globalPokeManager =new GlobalPokeViewManager(this);
//	}
	
	
	
	public PokeBackViewManager pokeBackViewManager;
//	/**
//	 * ������ҵ�С��
//	 */
//	private void addPokeBackViewManager(){
//		pokeBackViewManager = new PokeBackViewManager(this);
//	}
 	public GameMyPokeView myPoke;
//	/**
//	 * ����Լ�����
//	 */
//	private void addMyPoke(){
//		myPoke =new GameMyPokeView(this.getContext());
//		frameLayout.addView(myPoke);
//	}
	
 	public GameFaPaiManager faPaiManager;
//	/**
//	 * ��ӷ���
//	 */
//	private void addFaiPaiManager(){
//		faPaiManager =new GameFaPaiManager(this);
//	}
	public GameOtherPokeViewManager otherPokeViewManager;
//	/**
//	 * ���������ҵĵ���
//	 */
//	private void addOtherPokeView(){
//		otherPokeViewManager =new GameOtherPokeViewManager(this);
//	}

	 
	public GamePlayerViewManager playerViewManager=null;
	/**
	 * �����Ϸ�����ͼ
	 */
	private void addPlayerViewManager(){
	    playerViewManager=new GamePlayerViewManager(this.getContext());
		frameLayout.addView(playerViewManager);
	}
	
	public GameButtonView standButton;
	GameButtonView backButton;
	/**
	 * ��ӷ��غ�վ��ť
	 */
	private void addBackAndStandButton(){
		//վ��ť
	    standButton =new GameButtonView(this.getContext(),1);
	    
	    frameLayout.addView(standButton);
		View.OnClickListener listener2 =new View.OnClickListener(){
			public void onClick(View v) {	
				//����վ��
				sendStandUp();
			}
		};
		standButton.setOnClickListener(listener2);
		//վ��ť���ɼ�
		standButton.setVisibility(View.INVISIBLE);
		//���ش�����ť
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
	 * ���󷵻ش���
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
	 * ���󷵻�ȥ��ֵ����
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
	 * ���ش���
	 */
	private void backToRoom(){
		Log.i("test12", "backToRoom");
		     dismiss();
		     clearFinishDaga();
			 Intent it =new Intent();
			 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 it.setClass(this.getContext(), DzpkGameRoomActivity.class);
			 this.getContext().startActivity(it);
			 //������
			 activity.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
		 
	}
	 /**
	  * ����ȥ��ֵ
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
	 * ����
	 */
    private void clearFinishDaga(){
    	TaskSingleManager.getInstance().cancel();
		TaskSingleManager.setTaskSingleManagerNull();
		removeListener();
		
	 
    }
	//�Ƿ��һ�ν���
	public boolean isFirstIn =true;
	/**
	 * ����վ��
	 */
	public  void sendStandUp(){
	 
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			@Override
			public int executeTask() throws Exception {
				//����վ��
			    GameApplication.getSocketService().sendRequestStandUp();
			    //�����ս
			    GameApplication.getSocketService().sendRequestWatch(deskInfo.getDeskno());
			    //�������˴���
			    GameApplication.getSocketService().sendRequestKickIsShow();
			    //�����뿪����
			    GameApplication.getSocketService().sendClientLeaveRoom();
			    //��������������Ϣ
			    GameApplication.getSocketService().sendRequestDeskInfo(deskInfo.getDeskno());
				return 0;
			}
		});
		
	}
	/**
	 * �����ͼ
	 * @param view
	 */
	public void addView(View view){
		frameLayout.addView(view);
	}
	/**
	 * �Ƴ���ͼ
	 * @param view
	 */
    public void removeView(View view) {
    	frameLayout.removeView(view);
	}
 
    /**
     * ��ʼ��
     */
	private void init() {
		 service = GameApplication.getSocketService();
		 addListener();
		//���ټ��
         cnetThread= new CheckNetThread(checkNetView);
	}
	int type=-1;
	 
	
	public void load(){
		if(restart){
			  
			restart =false;
			//���ͷ��ش���
			sendRequestBackHall(-1);
		 
			//�ָ���ʾ
			TaskManager.getInstance().execute(new TaskExecutorAdapter(){
 
				@Override
				public int executeTask() throws Exception {
					//����ˢ��
		             GameApplication.getSocketService().sendRefresh();
					return 0;
				}
			});
		}else{
		
		 Intent intent = activity.getIntent();
		 type= intent.getIntExtra("type", -1);
		  //���ٿ�ʼ 1:�Ӵ������� 2:�ӹ��ܲ˵�����
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
	//����Loading
	public void loadIng(){
		activity.loadIng();
		//GameApplication.showLoading(this.getContext(),callback,40000);
	}
    /**
     * ���Ϳ��ٽ�����Ϸ
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
	 * ��������¼�.
	 */
	private void addListener(){
		service.addEventListener(TexEventType.ON_RECV_GIVE_JIUJI, this, "onRecvGiveJiuJi");
		service.addEventListener(Event.ON_RECV_WATCH_ERROR, this, "onRecvWatchError");
		//�յ�����
		service.addEventListener(TexEventType.ON_RECV_KICK_OVER_BEITI, this, "onRecvKickBeiti");
		//�յ�����˵���ʾ��Ϣ
		service.addEventListener(TexEventType.ON_TEX_RECV_SERVER_ERROR, this, "onRecvServerError");

		//������������ 
		service.addEventListener(TexEventType.ON_TEX_RECV_PLAY_GIFT, this, "onRecvPlayGift");
		//̫���в�������
		service.addEventListener(Event.ON_RECV_RICH_CANNOT_TOROOM, this, "onRecvRichCanNotToRoom");
		//�յ�ָ����λ���� 
		service.addEventListener(TexEventType.ON_TEX_RECV_GIFT_ICON, this, "onRecvGiftIcon");
		//���ܽ��뷿��
		service.addEventListener(Event.ON_RECV_CAN_NOT_TOROOM, this, "onRecvCanNotToRoom");
		//�յ�Ǯ�������߳�
		service.addEventListener(Event.ON_RECV_LOW_GOLD, this,"onRecvLowGold");
		//�յ��ָ���ʾ
		service.addEventListener(TexEventType.ON_TEX_RECV_RESETDISPLAY, this,"onRecvResetDisplay");
		//�յ�����ʳ���Ϣ
		service.addEventListener(TexEventType.ON_TEX_RECV_DESK_POLL_INFO, this,"onRecvDeskPollInfo");
		//�յ��Լ����������
		service.addEventListener(TexEventType.ON_TEX_RECV_BESTPOKES, this,
		"onRecvBestPokes");
		//�յ����Ͷ��
		service.addEventListener(TexEventType.ON_TEX_RECV_GIVEUP, this, "onRecvGiveUp");
		//�յ���Ҳ���ע
		service.addEventListener(TexEventType.ON_TEX_RECV_BUXIAZHU, this,
		"onRecvBuXiaZhu");
		//�յ���Ϸ��ʼ
		service.addEventListener(TexEventType.ON_TEX_RECV_GAMESTART, this,
		"onRecvGameStart");
		//�û�վ��
		service.addEventListener(TexEventType.ON_TEX_RECV_STANDUP, this,
		"onRecvStandup");
		
		//�յ��������
		service.addEventListener(TexEventType.ON_TEX_RECV_BUYCHOUMA, this,
		"onRecvBuyChouma");
		//�յ���Ϸ����
		service.addEventListener(TexEventType.ON_TEX_RECV_GAMEOVER, this,
			"onRecvGameOver");
		
		//�յ�ĳ����λ�Ľ��ˢ��
	    service.addEventListener(TexEventType.ON_TEX_RECV_REFRESHGOLD, this,
			"onRecvRefreshGold");
		//��ʾ�������
        service.addEventListener(TexEventType.ON_TEX_RECV_DESKPOKE, this,
		"onRecvDeskPoke");
		//��ʾ�Լ��ĵ��ݶ�
		service.addEventListener(TexEventType.ON_TEX_RECV_SHOW_MY_TOTAL_BEAN, this,
		"onRecvShowMyTotalBean");
		//�յ������ע�ɹ�
		service.addEventListener(TexEventType.ON_TEX_RECV_XIAZHUSUCC, this,
		"onRecvXiaZhuSucc");
		//��������
		service.addEventListener(TexEventType.ON_TEX_RECV_TIMEOUT, this,
		"onRecvKickMe");
		//�����Զ����
		service.addEventListener(TexEventType.ON_TEX_AUTO_PANEL, this,
		"onTexAutoPanel");
		//�������
		service.addEventListener(TexEventType.ON_TEX_BUTTON_STATUS, this,
		"onTexPanel");
		//��������
		service.addEventListener(TexEventType.ON_TEX_RECV_FAPAI, this,
		"onTexRecvFaPai");
		//�������������Ϣ
		service.addEventListener(TexEventType.ON_TEX_RECV_SITDOWN, this,
		"onTexRecvSitDown");
		// ����������Ϣ
		service.addEventListener(TexEventType.ON_TEX_RECV_DESK_INFO, this,
				"onTexRecvDeskInfo");
		// �����������û��б�
		service.addEventListener(Event.ON_RECV_DESK_USERLIST, this,
				"onRecvDeskUserList");
		// ���������������״̬
		service.addEventListener(TexEventType.ON_TEX_RECV_PLAYERINFO, this,
		"onTexRecvPlayerInfo");
		//������������
		service.addEventListener(TexEventType.ON_TEX_RECV_CHAT, this,
						"onTexRecvChat");
		service.addEventListener(TexEventType.ON_TEX_RECV_PLAY_EMOT, this,
				"onTexRecvEmot");
	}
	/**
	 *�Ƴ������¼�.
	 */
	private void removeListener(){
		if(service ==null){
			return;
		}
		service.removeEventListener(TexEventType.ON_RECV_GIVE_JIUJI, this, "onRecvGiveJiuJi");
		service.removeEventListener(Event.ON_RECV_WATCH_ERROR, this, "onRecvWatchError");
		//�յ�����
		service.removeEventListener(TexEventType.ON_RECV_KICK_OVER_BEITI, this, "onRecvKickBeiti");
		//�յ�����˵���ʾ��Ϣ
		service.removeEventListener(TexEventType.ON_TEX_RECV_SERVER_ERROR, this, "onRecvServerError");
		//������������ 
		service.removeEventListener(TexEventType.ON_TEX_RECV_PLAY_GIFT, this, "onRecvPlayGift");
		//̫���в�������
		service.removeEventListener(Event.ON_RECV_RICH_CANNOT_TOROOM, this, "onRecvRichCanNotToRoom");
		//�յ�ָ����λ���� 
		service.removeEventListener(TexEventType.ON_TEX_RECV_GIFT_ICON, this, "onRecvGiftIcon");
		//���ܽ��뷿��
		service.removeEventListener(Event.ON_RECV_CAN_NOT_TOROOM, this, "onRecvCanNotToRoom");
		//�յ�Ǯ�������߳�
		service.removeEventListener(Event.ON_RECV_LOW_GOLD, this,"onRecvLowGold");
		//�ָ���ʾ
		service.removeEventListener(TexEventType.ON_TEX_RECV_RESETDISPLAY, this,"onRecvResetDisplay");
		//�յ�����ʳ���Ϣ
		service.removeEventListener(TexEventType.ON_TEX_RECV_DESK_POLL_INFO, this,"onRecvDeskPollInfo");
		//�յ��Լ����������
		service.removeEventListener(TexEventType.ON_TEX_RECV_BESTPOKES, this,
		"onRecvBestPokes");
		//�յ����Ͷ��
		service.removeEventListener(TexEventType.ON_TEX_RECV_GIVEUP, this, "onRecvGiveUp");
		//�յ���Ҳ���ע
		service.removeEventListener(TexEventType.ON_TEX_RECV_BUXIAZHU, this,
		"onRecvBuXiaZhu");
		//�յ���Ϸ��ʼ
		service.removeEventListener(TexEventType.ON_TEX_RECV_GAMESTART, this,
		"onRecvGameStart");
		//�û�վ��
		service.removeEventListener(TexEventType.ON_TEX_RECV_STANDUP, this,
		"onRecvStandup");
		
		//�յ��������
		service.removeEventListener(TexEventType.ON_TEX_RECV_BUYCHOUMA, this,
		"onRecvBuyChouma");
		//�յ���Ϸ����
		service.removeEventListener(TexEventType.ON_TEX_RECV_GAMEOVER, this,
			"onRecvGameOver");
		
		//�յ�ĳ����λ�Ľ��ˢ��
	    service.removeEventListener(TexEventType.ON_TEX_RECV_REFRESHGOLD, this,
			"onRecvRefreshGold");
		//��ʾ�������
        service.removeEventListener(TexEventType.ON_TEX_RECV_DESKPOKE, this,
		"onRecvDeskPoke");
		//��ʾ�Լ��ĵ��ݶ�
		service.removeEventListener(TexEventType.ON_TEX_RECV_SHOW_MY_TOTAL_BEAN, this,
		"onRecvShowMyTotalBean");
		//�յ������ע�ɹ�
		service.removeEventListener(TexEventType.ON_TEX_RECV_XIAZHUSUCC, this,
		"onRecvXiaZhuSucc");
		//��������
		service.addEventListener(TexEventType.ON_TEX_RECV_TIMEOUT, this,
		"onRecvKickMe");
		//�����Զ����
		service.removeEventListener(TexEventType.ON_TEX_AUTO_PANEL, this,
		"onTexAutoPanel");
		//�������
		service.removeEventListener(TexEventType.ON_TEX_BUTTON_STATUS, this,
		"onTexPanel");
		//��������
		service.removeEventListener(TexEventType.ON_TEX_RECV_FAPAI, this,
		"onTexRecvFaPai");
		//�������������Ϣ
		service.removeEventListener(TexEventType.ON_TEX_RECV_SITDOWN, this,
		"onTexRecvSitDown");
		// ����������Ϣ
		service.removeEventListener(TexEventType.ON_TEX_RECV_DESK_INFO, this,
				"onTexRecvDeskInfo");
		// �����������û��б�
		service.removeEventListener(Event.ON_RECV_DESK_USERLIST, this,
				"onRecvDeskUserList");
		// ���������������״̬
		service.removeEventListener(TexEventType.ON_TEX_RECV_PLAYERINFO, this,
		"onTexRecvPlayerInfo");
		//������������
		service.removeEventListener(TexEventType.ON_TEX_RECV_CHAT, this,
				"onTexRecvChat");
		
		service.removeEventListener(TexEventType.ON_TEX_RECV_PLAY_EMOT, this,
				"onTexRecvEmot");
		
		
	}
	/**
	 * �յ�Ǯ�������߳�
	 * @param e
	 */
	public void onRecvLowGold(Event e){
		Log.i(tag, "onRecvLowGold");
		sendMsg(recvLowGold, e.getData());
	}
	/**
	 * �յ�����ʳ���Ϣ
	 * @param e
	 */
	public void onRecvDeskPollInfo(Event e){
		Log.i(tag, "onRecvDeskPollInfo");
		sendMsg(recvDeskPollInfo, e.getData());
	}
	/**
	 * �յ��������
	 * @param e
	 */
	public void onRecvBestPokes(Event e){
		Log.i(tag, "onRecvBuXiaZhu");
		sendMsg(recvBestPokes, e.getData());
	}
	/**
	 * �յ���Ҳ���ע
	 * @param e
	 */
	public void onRecvBuXiaZhu(Event e){
		Log.i(tag, "onRecvBuXiaZhu");
		sendMsg(recvBuXiaZhu, e.getData());
	}
	/**
	 * �յ���Ϸ��ʼ
	 * @param e
	 */
	public void onRecvGameStart(Event e){
		Log.i(tag, "onRecvGameStart");
		sendMsg(recvGameStart, e.getData());
	}
	
	/**
	 * �յ��û�վ��
	 * @param e
	 */
	public void onRecvStandup(Event e){
		Log.i(tag, "onRecvStandup");
		sendMsg(recvStandup, e.getData());
	}
	/**
	 * �յ�������봰
	 * @param e
	 */
	public void onRecvBuyChouma(Event e){
		Log.i(tag, "onRecvBuyChouma");
		//showBuyChouMaDialog((HashMap)e.getData());
		sendMsg(recvBuyChouma, e.getData());
		
	}
	/**
	 * �յ���Ϸ����
	 * @param e
	 */
	public void  onRecvGameOver(Event e){
		Log.i(tag, "onRecvGameOver");
		sendMsg(recvGameOver, e.getData());
	}
	/**
	 * �յ�ĳ����λ�Ľ��ˢ��
	 * @param e
	 */
	public void onRecvRefreshGold(Event e){
		Log.i(tag, "onRecvRefreshGold");
		sendMsg(recvRefreshGold, e.getData());
	}
	/**
	 * ��ʾ�������
	 * @param e
	 */
	public void onRecvDeskPoke(Event e){
		Log.i(tag, "onRecvDeskPoke");
		sendMsg(recvDeskPoke, e.getData());
	}
	/**
	 * ��ʾ�Լ��ĵ��ݶ�
	 * @param e
	 */
	public void  onRecvShowMyTotalBean(Event e){
		Log.i(tag, "onRecvShowMyTotalBean");
		sendMsg(recvShowMyTotalBean, e.getData());
	}
	/**
	 * �յ��û���ע�ɹ�
	 * @param e
	 */
	public void onRecvXiaZhuSucc(Event e){
		Log.i(tag, "onRecvXiaZhuSucc");
		sendMsg(recvXiaZhuSucc, e.getData());
	}
	/**
	 * ��������
	 * @param e
	 */
	public void onRecvKickMe(Event e){
		Log.i(tag, "onTexAutoPanel");
		sendMsg(recvKickMe, e.getData());
	}
	/**
	 * �������Զ����
	 * @param e
	 */
	public void onTexAutoPanel(Event e){
		Log.i(tag, "onTexAutoPanel");
		sendMsg(recvAutoPanel, e.getData());
	}
	/**
	 * ���������
	 * @param e
	 */
	public void onTexPanel(Event e){
		Log.i(tag, "onTexPanel");
		sendMsg(recvPanel, e.getData());
	}
	/**
	 * �����������������״̬
	 * @param e
	 */
	public void  onTexRecvPlayerInfo(Event e){
		Log.i(tag, "onTexRecvPlayerInfo");
		sendMsg(recvPlayerInfo, e.getData());
	}
	/**
	 * �������յ��������û��б�
	 * 
	 * @param e
	 */
	public void onRecvDeskUserList(Event e) {
		Log.i(tag, "onRecvDeskUserList");
		sendMsg(recvDeskUserList, e.getData());
	}
	/**
	 * ������������Ϣ
	 * @param e
	 */
	public void onTexRecvDeskInfo(Event e){
		Log.i(tag, "onTexDeskInfo");
       
		sendMsg(recvDeskInfo,e.getData());
	}
	/**
	 * �������
	 * @param e
	 */
	public void onTexRecvSitDown(Event e){
		Log.i(tag, "onTexRecvSitDown");
		sendMsg(recvSitDown, e.getData());
	}
	/**
	 * �յ�����
	 * @param e
	 */
	public void  onTexRecvFaPai(Event e){
		Log.i(tag, "onTexRecvFaPai");
		Log.i("test14", "4onTexFaPaionTexFaPaionTexFaPaionTexFaPai: "+System.currentTimeMillis());
		sendMsg(recvFaPai, e.getData());
	}
	/**
	 * �յ��ָ���ʾ
	 * @param e
	 */
	public void  onRecvResetDisplay(Event e){
		Log.i(tag, "onRecvResetDisplay");
		sendMsg(recvResetDisplay, e.getData());
	}
	/**
	 * �յ����Ͷ��
	 * @param e
	 */
	public void onRecvGiveUp(Event e){
		sendMsg(recvGiveUp, e.getData());
	}
	/**
	 * �յ���λ����
	 * @param e
	 */
	public void onRecvGiftIcon(Event e){
		Log.i(tag, "Game onRecvGiftIcon");
		sendMsg(recvGiftIcon, e.getData());
	}
	/**
	 * �յ�������������
	 * @param e
	 */
	public void onRecvPlayGift(Event e){
		Log.i(tag, "Game onRecvPlayGift");
		sendMsg(recvPlayGiftIcon, e.getData());
	}
	/**
	 * �յ����½��
	 * @param e
	 */
	public void onRecvSitDownResult(Event e){
		sendMsg(recvSitDownResult, e.getData());
	}
	/**
	 * �յ�����˵���ʾ��Ϣ
	 * @param e
	 */
	public void onRecvServerError(Event e){
		sendMsg(recvServerError, e.getData());
	}
	/**
	 * ̫���в�������
	 * @param e
	 */
	public void  onRecvRichCanNotToRoom(Event e){
		sendMsg(recvRichNotInToRoom, null);
	}
	/**
	 * �յ�����
	 * @param e
	 */
	public void onRecvKickBeiti(Event e){
		sendMsg(recvBeiTi, null);
	}
	 
	public void onRecvWatchError(Event e){
		sendMsg(recvWatchError, e.getData());
	}
	/**
	 * �յ��ȼ�
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
		//���̴߳���
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
		public void handleMessage(Message msg) {// ����һ��Handler�����ڴ��������߳���UI��ͨѶ
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
		public void handleMessage(Message msg) {// ����һ��Handler�����ڴ��������߳���UI��ͨѶ
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
					//�յ�Ǯ�������߳�
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
					//�յ�����
					gameDataManager.setGiftIcon((HashMap)msg.obj);
					//playerViewManager.setGiftIcon((HashMap)msg.obj);
					break;
				case recvPlayGiftIcon:
					//������������
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
					//��������
					talkChatViewManager.executeChat((HashMap)msg.obj);
					break;
				case recvEmot:
					 //�������
					talkQingViewManager.executeBiaoQing((HashMap)msg.obj);
					break;
				}
			}
			super.handleMessage(msg);
		}

	
	};
	//���벻�㲻�ܽ��뷿��
	HashMap canNotRoomData;
	boolean isCanNotRoom=false;
	private void executeCanNotIntoRoom() {
		isCanNotRoom =false;
		HwgCallBack callback = new HwgCallBack(){
			public  void CallBack(Object[] obj) {
				sendRequestBackHall(0);
			};
		};
		//���ܽ��뷿��
		GameUtil.executeCanNotIntoRoom(this.getContext(),canNotRoomData,callback);

	}
	
	 //������������
	 HashMap buyChouMadata;
	 boolean isNeedShowBuyChouMa=false;
	   /**
	    * ��ʾ������봰
	    * @param data
	    */
	public void showBuyChouMaDialog(){
		sitView.clickSit =false;
		isNeedShowBuyChouMa =false;
		Integer min=(Integer)buyChouMadata.get("min");
		Integer gold=(Integer) buyChouMadata.get("gold");
		if(gold < min){
			//����ȼý�
			TaskManager.getInstance().execute(new TaskExecutorAdapter(){

				@Override
				public int executeTask() throws Exception {
					service.sendRequestJiuJi();
					return 0;
				}
				
			});
//			HwgCallBack callback = new HwgCallBack(){
//				public void CallBack(Object... obj) {
//					//���󷵻ش���
//					sendRequestBackHall(1);
//				}
//			};
//			GameUtil.openMessageDialog(DzpkGameActivity.this,GameUtil.msg4,callback);
		}else{
		  if(playerViewManager.mySite && playerViewManager.getPlayerState() != 11){
			  //��ǰ��������£��Ҳ�Ϊ�������״̬������Ҫ��������
			  return;
		  }
		  BuyChouMaDialog bcmd =new BuyChouMaDialog(this.getContext(),R.style.dialog,buyChouMadata);
		  bcmd.show();
		}
	}
	/**
	 * ��ʾ�ȼ���Ϣ
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
						 //ȥ����ֵ����
						sendRequestBackToChongZhi();
						}
					 
					}
				};
				if (cangive == -1) {
					
					GameUtil.openMessage1Dialog(this.getContext(),
							GameUtil.msg4, callback,GameUtil.chongZhi);
				} else {
					GameUtil.openMessage1Dialog(this.getContext(),
							GameUtil.msgJiuJi + "\r\n(����" + lqcishu + "��,��3��)",
							callback,GameUtil.chongZhi,GameUtil.sure);
				}

			}
		}else{
			//������ʼ
			if(type ==1 || type ==2){
				Byte cangive=(Byte)data.get("cangive");
				Byte lqcishu=(Byte)data.get("lqcishu");
				backToLjKs(type,cangive,lqcishu);
			}
			 
		}
	}
	/**
	 *���������������ʼ�Ľ���
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
     * ��ע
     * @param gold ��ע���
     * @param type 1:��ע 2:��ע
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
     * ����ע
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
	 * ���͸�ע
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
	  * ���ͷ���
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
	//������λ״̬
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
 	    		//���ÿ��ٿ�ʼ��ť
 		    	gameBottomView.addQuickStartButton();
 	    	}else{
 	    		gameBottomView.removeQuickView();
 	    	}
	    	//��λ��ʾ
  			sitView.setNeedDraw(true);
  			
  		}else{
  			//��λ����ʾ
  			sitView.setNeedDraw(false);
  			gameBottomView.removeQuickView();
  			Log.i(tag,"execute not display");
  		}
  		 
	}
	
	/**
	 * ������Ϣ
	 */
	public void reset(){
		 
		//TaskSingleManager2.getInstance().cancel();
		//TaskSingleManager2.setTaskSingleManagerNull();
		 toastPaiXingView.reset();
         //������λ״̬
		 setSitViewState();
		 //gameDataManager.executeStandUpAction2();
		//��ս���������Ϣ
		 DJieSuan.initData();
        //���ù�����
		 globalPokeManager.reset();
		//�������С������
		 pokeBackViewManager.reset();
		//���������
		 otherPokeViewManager.reset();
		 //���ó���
		 playerChouMaViewManager.reset();
		//�����Լ�����
		 myPoke.reset();
		//���ù�������
		 pokeTypeView.reset();
		//����ׯ��
		 zjViewManager.reset();
		//���òʳ�
		 pondViewManager.reset();
		 //�������
		 playerViewManager.reset();
		//�������״̬
		 gameDataManager.updatePlayerState(true);
		//�Ƿ���Ҫ��ʾ������봰
		 if(isNeedShowBuyChouMa){
			 showBuyChouMaDialog();
		 }
		 if(isCanNotRoom){
			 executeCanNotIntoRoom();
		 }
		 System.gc();
		 //�������
		 GameApplication.jieSuanIng=false;
		 gameDataManager.executeSitDownAction();
	}
	/**
	 * ���浱ǰ״̬
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
	 * �ָ���ǰ״
	 */
	public void backCurrentState(){
		pokeBackViewManager.backCurrentState();
		otherPokeViewManager.backCurrentState();
		zjViewManager.backCurrentState();
		playerChouMaViewManager.backCurrentState();
	}
	//�뿪����
	private void leaveRooms(){
		//ֹͣ��Ӯ
		myWinView.stopThread();
		//ֹͣ��ʱ��
	    jsqViewManager.setStop();
	    //���󷵻ش���
	    sendRequestBackHall(1);
		 //sendRequestBackHall(-1);
	}
	 //�뿪����
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
	    	  GameUtil.openMessage1Dialog(this.getContext(), "ȷ���뿪������", callback);
	    	  
	}

//    public void onAttachedToWindow() {
//    	 this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
//        //this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//        super.onAttachedToWindow();
//    }
	/**
	 * �յ����ܽ��뷿��
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
				//long[] pattern = { 100, 400, 100, 400 }; // ֹͣ ���� ֹͣ ����
				//vibrator.vibrate(pattern, 1);// ��pattern������һ��
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
		/*******************PlayerDialog��************************/
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
 
		/*******************FaiPaiDialog��************************/
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
	 * ���������¼�
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
			 GameUtil.openMessage1Dialog(this.getContext(), "�˲�����������ǰ��Ϸ", callback);
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
//	  * ����
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
//	 * �û��ɽ���
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
//	 * ����������Ϣ
//	 */
//	private void saveLocked() {
//		//��Ҫ�����û���Ϣ
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