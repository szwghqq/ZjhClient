package com.dozengame;

 
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dozengame.db.DBManager;
import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.net.pojo.DGroupInfoItem;
import com.dozengame.net.pojo.PlayerNetPhoto;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameUtil;
 
/**
 * ��Ϸ��������
 * @author hewengao
 *
 */
public class DzpkGameStartActivity extends BaseActivity implements Runnable,CallBack{
	
	TextView checkNet;
	TextView checkNet1;
	static final String checkText0="�����������";
	static final String checkText1="����������,������������";
	static final String checkText2="��Ϸ������,���Ժ�";
	static String checkSlh="";
	boolean loadGame=false;
	Bitmap startBit =null;
   public  static String startNeCun;//��ʼ�ڴ�
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  
//        DBManager.init(this);
//        try {
//			DBManager.executeSql(PlayerNetPhoto.DROP_TABLE);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        //��ȡ��ʼ�ڴ��=
        startNeCun= GameUtil.getNeCun(this);
        setContentView(R.layout.main1);
        init();
        Log.i("test18","model: "+android.os.Build.MODEL+"  ");

    }
    float h =0,w=0;
    /**
     * �����Ļ�ֱ���
     * @return
     */
    private boolean checkScreen(){
     
    	 DisplayMetrics dm = new DisplayMetrics();   
		 this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm); 
    	   h = dm.heightPixels;//*dm.density;
    	   w = dm.widthPixels;//*dm.density;
    	   
//    	 if(w==960 && h == 640){
//    		 return true;
//    	 }else{
//    		 GameUtil.openMessageDialog(this,"���ֻ��ֱ���Ϊ:"+(int)h+"*"+(int)w+"��ǰ�汾ֻ֧��640*960");
//    	     return false;
//    	 }
    	   return true;
    }
    public void init(){
    	try {
			startBit=BitmapFactory.decodeStream(getAssets().open("start.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		RelativeLayout startLayout = (RelativeLayout)findViewById(R.id.start_layout1);
		if(startLayout == null){
			Log.i("test11", "startLayout is null");
			return;
		}else{
			Log.i("test11", "startLayout is not null");
		}
		startLayout.setBackgroundDrawable(new BitmapDrawable(startBit));
    	checkNet = (TextView)findViewById(R.id.checkNet);
        checkNet1 = (TextView)findViewById(R.id.checkNet1);
        if(!checkScreen()){
        	return ;
        }
        checkNet.setText(checkText0);
      
        new Thread(this).start();
        TaskManager.getInstance().execute(new TaskExecutorAdapter(){
 
			public int executeTask() throws Exception {
			   Thread.sleep(3000);
			   boolean bl = true;
			   if(GameApplication.getSocketService().checkNetConnection()){
				   //����������,������Ϸ
				   loadGame=true;
				   sendMsg();
			   }else{
				   //������ʧ��
				   runing=false;
				   sendMsg();
			   }
				return 0;
			}
        	
        });
    }
    
    /**
     * �����û���¼��Ϣ
     */
    public void saveUserLoginInfo(String userName,String pwd){
    		//��Ҫ�����û���Ϣ
    		SharedPreferences uiState=getPreferences(0);
    		SharedPreferences.Editor editor=uiState.edit();
    		editor.putString("userName",userName);
    		editor.putString("pwd", pwd);
    		editor.commit();
    }

    int count=0;
    boolean runing =true;
	@Override
	public void run() {
       while(runing){
    	   switch(count){
    	   case 0:
    		   checkSlh="";
    		   break;
    	   case 1:
    		   checkSlh=".";
    		   break;
    	   case 2:
    		   checkSlh="..";
    		   break;
    	   case 3:
    		   checkSlh="...";
    		   break;
    	   }
    	   count++;
    	   if(count==4){
    		   count=0;
    	   }
    	   sendMsg();
    	   try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
       }
	}
	private void sendMsg() {
		handler.sendEmptyMessage(3);
	}
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {// ����һ��Handler�����ڴ��������߳���UI��ͨѶ
			 if(msg.what ==3){
				if (!Thread.currentThread().isInterrupted()) {
					if(runing){
					    checkNet1.setText(checkSlh);
					}else{
						checkNet1.setText("");
						checkNet.setText(checkText1);
					}
					if(loadGame){
						checkNet.setText(checkText2);
						 forward();
						
					}
				}
			}else if(msg.what ==0){
				//��Ϸ
		        executGameLoginResult((HashMap) msg.obj);
			}else if(msg.what ==1){
				//����
				executCenterLoginResult((HashMap) msg.obj);
			}else if(msg.what ==2){
				GameUtil.openMessageDialog(DzpkGameStartActivity.this,GameUtil.LOGINTIMEOUT);
			}else if(msg.what ==4){
				forward(null);
			}else if(msg.what ==5){
				forward(msg.obj);
			}
		}
	};
	boolean isAddListener=false;
	/**
	 * ��ת����Ϸ�������½����
	 */
	private void forward(){
		//���ж��Ƿ��б��浱ǰ�û���Ϣ
       
		SharedPreferences uiState= this.getApplicationContext().getSharedPreferences("login",MODE_WORLD_READABLE);
		final String userName=uiState.getString("userName", "").trim();
		final String pwd=uiState.getString("pwd", "").trim(); 
		 runing=false;
		if(userName.equals("") && pwd.equals("")){
			//��ʾû�б�����ʷ,��ת����½����
			System.out.println("is not found");
			forwardToLogin();
		}else{
			addServiceListener();
			isAddListener=true;
			//ֱ�ӵ�¼�������¼���ɹ�������ת����½����
			System.out.println("userName: "+userName);
			System.out.println("pwd: "+pwd);
			login(userName,pwd);
		}
	}
	private void clearUserInfo(){
		SharedPreferences uiState= this.getApplicationContext().getSharedPreferences("login",MODE_WORLD_READABLE);
		Editor editor=uiState.edit();
		editor.putString("userName", "");
		editor.putString("pwd", "");
		editor.commit();
	}
	/**
	 * ��ת����¼
	 */
	private void forwardToLogin(){
		loadingStop();
		Intent it=new Intent();
		it.setClass(DzpkGameStartActivity.this, DzpkGameLoginActivity.class);
		//��Intent��ֱ�Ӽ����־ Intent.FLAG_ACTIVITY_CLEAR_TOP����������Bʱ��������ý��̿ռ������Activity
		it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(it);
		this.finish();
		 
	}
	 /**
     * ��½
     */
	private void login(final String userName,final String pwd){
		loadingStart();
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){

			public int executeTask() throws Exception {
				GameApplication.getSocketService().sendLogin(userName, pwd);
				return 0;
			}
			
		});
	}
	
	ArrayList<DGroupInfoItem> list;
	/**
	 * ���������շ�������
	 * @param e
	 */
	public void recvGroupInfo(Event e){
    	list =(ArrayList<DGroupInfoItem>) e.getData();
	}
	
	/**
	 * ���������ĵ�¼���
	 * @param e
	 */
	public void recvCenterLoginResult(Event e){
	 
		Message msg =handler.obtainMessage();
		msg.what=1;
		msg.obj=e.getData();;
		handler.sendMessage(msg);
		 
	}
 
	//���͵�¼��Ϸ������
	private void sendLoginGameServer(){
	 
		if(list == null || list.isEmpty()){
			loadingStop();
			return;
		}
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			@Override
			public int executeTask() throws Exception {
				if(list != null && !list.isEmpty()){
					int size = list.size();
					DGroupInfoItem groupItem;
					int i=0;
					for( i=0;i<size;i++){
						groupItem = list.get(i);
						if(groupItem.groupid==19001){
							//ѡ���������¼
					       GameApplication.getSocketService().changRoom(groupItem);
					      break;
						}
					}
					if(i == size){
						loadingStop();
						GameUtil.openMessageDialog(DzpkGameStartActivity.this,GameUtil.gamenotfound);
					}
				}
				return 0;
			}		
	     });
	}
	/**
	 * �����¼���
	 * @param data
	 */
	private void executCenterLoginResult(HashMap data){
		if (data != null) {
			short code = (Short) data.get("code");
			if (code == 1) {
				sendLoginGameServer();
			} else {
				loadingStop();
				HwgCallBack callback = new HwgCallBack(){
					@Override
					public void CallBack(Object... obj) {
						 clearUserInfo();
						 forwardToLogin();
					}
				};
				GameUtil.openMessageDialog(this,GameUtil.msg31,callback);
 
//				System.out
//						.println("onRecvGameServerLoginResult ��½���ķ�����ʧ�� �����룺"
//								+ code);
			}
		}
	}
	
	/**
	 * �����¼���
	 * @param data
	 */
	private void executGameLoginResult(HashMap data){
		 
		if (data != null) {
			short code = (Short) data.get("code");
			if (code == 1 || code == 2) {
				System.out
						.println("onRecvGameServerLoginResult ��¼�ɹ�,����Ƶ����Ϣ");
				//dnGame.sendRequestUpdateChannelInfo("");
			} else if (code == -101) {
				GameUtil.openMessageDialog(this,GameUtil.msg1);
				GameApplication.joinAagin();
				System.out
						.println("onRecvGameServerLoginResult ��¼��Ϸ������ʧ��,�ʺ�����ʹ��");
			} else if (code == -102) {
				GameUtil.openMessageDialog(this,GameUtil.msg2);
				GameApplication.joinAagin();
				System.out
						.println("onRecvGameServerLoginResult ���Ѿ���һ��������ƣ�������ƾ�");
			} else {
				HwgCallBack callback = new HwgCallBack(){
					@Override
					public void CallBack(Object... obj) {
						 clearUserInfo();
						 forwardToLogin();
					}
				};
				GameUtil.openMessageDialog(this,GameUtil.msg3,callback);
				//GameApplication.joinAagin();
				System.out
						.println("onRecvGameServerLoginResult ��½��Ϸ������ʧ�� �����룺"
								+ code);
			}
		}
	}
	public void recvGameLoginResult(Event e){ 
		loadingStop();
		Message msg =handler.obtainMessage();
		msg.what=0;
		msg.obj=e.getData();
		handler.sendMessage(msg);
		
	}
	public void recvMyInfo(Event e){
		 loadingStop();
		 System.out.println("DzpkGameStartActivity recvMyInfo");
		 GameApplication.userInfo=(HashMap)e.getData();
//		 Intent it=new Intent();
//		 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
// 		 it.setClass(DzpkGameStartActivity.this, DzpkGameMenuActivity.class);
// 		 startActivity(it);
// 		 finish();
	}
	
	/**
	 * ��ת����
	 */
	private void forward(Object data){
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			@Override
			public int executeTask() throws Exception {
				 GameApplication.getSocketService().sendMobLogin(android.os.Build.MODEL, w+"*"+h);
				return 0;
			}
			
		});
		 int allGold=0;
		 int vipGold=0;
		 int gold =0;
		 int taskGold =0;
		 if(data !=null){
		 
			 allGold=(Integer)((HashMap)data).get("allGold");
			 vipGold=(Integer)((HashMap)data).get("vipadd");
			 taskGold=(Integer)((HashMap)data).get("vtask_add");
			 gold=(Integer)((HashMap)data).get("gold");
		 }
		 Intent it=new Intent();
		 it.putExtra("vipadd", vipGold);
	     it.putExtra("vtask_add", taskGold);
	     it.putExtra("gold", gold);
		 it.putExtra("allGold", allGold);
 		// it.setClass(DzpkGameStartActivity.this, DzpkGameMenuActivity.class);
 		it.setClass(DzpkGameStartActivity.this, DzpkGameRoomActivity.class);
 		 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 		 startActivity(it);
 		 finish();
 		 
	}
	/**
	 * �����¼��Ǯ
	 * @param event
	 */
	public void onTexLoginShowDayGold(Event event){
		int type =(Integer)event.getData();
		if(type ==1){
		    sendRequestLoginLingQu();
        }else{
			Message msg = handler.obtainMessage();
			msg.obj =event.getData();
			msg.what=4;
			handler.sendMessage(msg);
        }
	}
	  /**
	   * ���͵�¼��ȡ
	   */
	  private void sendRequestLoginLingQu(){
		  TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			public int executeTask() throws Exception {
				GameApplication.getSocketService().sendRequestLoginLingQu();
				return 0;
			}
		  });
	  }
	  /**
	   * �콱���
	   * @param e
	   */
	  public void onRecvLoginGive(Event e){
			 
		  Message msg= handler.obtainMessage();
		  msg.what=5;
		  msg.obj=e.getData();
		  handler.sendMessage(msg);
	}

	/**
	 * ��������¼�
	 */
	private void addServiceListener(){
		GameApplication.getSocketService().addEventListener(Event.ON_TEX_RECV_LOGIN_GIVE, this, "onRecvLoginGive"); 
		GameApplication.getSocketService().addEventListener(Event.ON_TEX_LOGIN_SHOW_DAY_GOLD, this, "onTexLoginShowDayGold");
	
		
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_GROUP_INFO, this, "recvGroupInfo");
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_LOGIN_RESULT, this,"recvCenterLoginResult");
      
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_GAME_LOGIN_RESULT, this, "recvGameLoginResult");
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_MY_INFO, this, "recvMyInfo");

	}
	/**
	 * �Ƴ������¼�
	 */
	private void removeServiceListener(){
		GameApplication.getSocketService().removeEventListener(Event.ON_TEX_RECV_LOGIN_GIVE, this, "onRecvLoginGive"); 
		GameApplication.getSocketService().removeEventListener(Event.ON_TEX_LOGIN_SHOW_DAY_GOLD, this, "onTexLoginShowDayGold");
	
		
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_GROUP_INFO, this, "recvGroupInfo");
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_LOGIN_RESULT, this,"recvCenterLoginResult");
      
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_GAME_LOGIN_RESULT, this, "recvGameLoginResult");
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_MY_INFO, this, "recvMyInfo");

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(isAddListener){
		   removeServiceListener();
		}
		GameUtil.recycle(startBit);
		 System.gc();
	}
	/**
	 * ���������¼�
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	     GameUtil.backExit(DzpkGameStartActivity.this);
	              return true;
	       }
	  return super.onKeyDown(keyCode, event);
	} 
	 
    int timeOut =30000;//��¼��ʱ����
	
	/**
	 * ��ʼ����
	 */
	private void loadingStart(){
	 
			HwgCallBack callback = new HwgCallBack(){
				public void CallBack(Object... obj) {
					handler.sendEmptyMessage(2);
					
				}
			};
			  loadingStop();
			GameApplication.loading =  new LoadingDialog(this,R.style.dialog,callback,timeOut);
			GameApplication.loading.show();
	       TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			@Override
			public int executeTask() throws Exception {
				GameApplication.loading.start();
				return 0;
			} 
	       });
		 
      
	}
	private void loadingStop(){
		GameApplication.dismissLoading();
	}
	
}