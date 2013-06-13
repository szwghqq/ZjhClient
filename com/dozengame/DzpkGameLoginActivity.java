package com.dozengame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.net.pojo.DGroupInfoItem;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameUtil;
/**
 * 用户登录界面
 * @author hewengao
 *
 */
public class DzpkGameLoginActivity extends BaseActivity implements CallBack{
	
	final static CharSequence descValue0="0";
	final static CharSequence descValue1="1";
	EditText userName;//用户名
	EditText pwd;//密码
	ImageView imgCheck;//保存用户密码勾选框
	ImageView imgLogin;//登录
	TextView  textLogin;
	Bitmap bitmap_Btn1=null; 
	Bitmap bitmap_Btn2=null;
	Bitmap bitmap_Check1=null; 
	Bitmap bitmap_Check2=null;
	Bitmap acc_bg=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("test4", "onCreate");
        setContentView(R.layout.login);
        initBitmap();
        findViewById(R.id.loginLayout).setBackgroundDrawable(new BitmapDrawable(acc_bg));
        initData();
        //添加侦听事件
        addServiceListener();
        //这个方法可以实现输入法在窗口上切换显示，如果输入法在窗口上已经显示，则隐藏，如果隐藏，则显示输入法到窗口上
//        InputMethodManager m=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
//        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        
       // ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).
       // hideSoftInputFromWindow(DzpkGameLoginActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); 
      //  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 

      //  imm.showSoftInput(pwd,InputMethodManager.SHOW_IMPLICIT); 
        //imm.


    }
	 
   
    private void initBitmap(){
    	try {
    		bitmap_Btn1 = BitmapFactory.decodeStream(getAssets().open("acc_btn.png"));
    		bitmap_Btn2 = BitmapFactory.decodeStream(getAssets().open("acc_btns.png"));
    		bitmap_Check1 = BitmapFactory.decodeStream(getAssets().open("acc_check.png"));
    		bitmap_Check2 = BitmapFactory.decodeStream(getAssets().open("acc_checks.png"));
    		acc_bg = BitmapFactory.decodeStream(getAssets().open("acc_bg.jpg"));	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * 初始化控件
     */
    private void initData(){
    	userName=(EditText)findViewById(R.id.UserName);
    	pwd=(EditText)findViewById(R.id.pwd);
    	//userName.setText(DConfig.strUser);
    	
    	//pwd.setText(DConfig.strKey);
    	
//    	   userName.setText("109");
//    	   pwd.setText("11");
//
//    	  userName.setText("hwg19860119");
//    	  pwd.setText("hwg19860119hwg");
//    	 userName.setText("109");
//    	 pwd.setText("11");

//     	    userName.setText("hwg19860119");
//     	    pwd.setText("hwg19860119hwg");

    	 //userName.setText("lylmsdn@163.com");
    	 //pwd.setText("windows");
    	 //userName.setText("daren29");
    	 //pwd.setText("zhenyan");
    	
    	imgCheck=(ImageView)findViewById(R.id.acc_check);
    	imgLogin=(ImageView)findViewById(R.id.login);
    	
    	textLogin=(TextView)findViewById(R.id.tvLogin);
    	textLogin.setTextColor(Color.BLACK);
    	
    	imgLogin.setImageBitmap(bitmap_Btn1);
    	imgCheck.setImageBitmap(bitmap_Check1);
    	addListener();
    }
    /**
     * 添加按钮事件
     */
    private void addListener(){
    	imgCheck.setOnClickListener(new OnClickListener(){
 
			public void onClick(View v) {
				 
				CharSequence ss= imgCheck.getContentDescription();
				if(ss.equals(descValue0)){
					//imgCheck.setImageResource(R.drawable.acc_checks);
				 
			    	imgCheck.setImageBitmap(bitmap_Check2);
					imgCheck.setContentDescription(descValue1);
				}else{
					//imgCheck.setImageResource(R.drawable.acc_check);
					imgCheck.setImageBitmap(bitmap_Check1);
					imgCheck.setContentDescription(descValue0);
				}
			}
    		
    	});
    	
    	imgLogin.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	 imgLogin.setImageBitmap(bitmap_Btn2);
			    	//imgLogin.setImageResource(R.drawable.acc_btns);
			    	 
			    	return true;
			    }else if(action ==1){
			         imgLogin.setImageBitmap(bitmap_Btn1);
			    	//imgLogin.setImageResource(R.drawable.acc_btn);
			    	 
			    	login();
			    	return true;
			    }
				return false;
			}
    		
    	});
    	
    }
    boolean bl =true;
    /**
     * 登陆
     */
    private void login(){
    	String name=  userName.getText().toString();//用户名
    	String password= pwd.getText().toString();//密码
   
    	
    	try {
    		Log.i("test4", "name: "+name+" password: "+password);
    		if(name.trim().length() ==0){
    			GameUtil.openMessageDialog(this, GameUtil.msg6);
    		}else if(password.trim().length() ==0){
    			GameUtil.openMessageDialog(this, GameUtil.msg7);
    		}else{
	    		//发送登录命令
	    		login(name, password);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
 
    }
	/**
	 * 保存用户登录信息
	 */
	private void saveUserLoginInfo(boolean bl) {
		String name=  userName.getText().toString();//用户名
    	String password= pwd.getText().toString();//密码
		//需要保存用户信息
		SharedPreferences uiState  = this.getApplicationContext().getSharedPreferences("login",MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = uiState.edit();
		if(bl){
			editor.putString("userName", name);
			editor.putString("pwd", password);
		}else{
			editor.putString("userName", "");
			editor.putString("pwd", "");
		}
		editor.commit(); 
		System.out.println("save success");
	}
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
	 * 侦听到接收分组数据
	 * @param e
	 */
	public void recvGroupInfo(Event e){
//		Message msg =handler.obtainMessage();
//		msg.what=2;
//		msg.obj=e.getData();
//		handler.sendMessage(msg);
 
    	list =(ArrayList<DGroupInfoItem>) e.getData();
 
	}
	
	/**
	 * 侦听到中心登录结果
	 * @param e
	 */
	public void recvCenterLoginResult(Event e){
	 
		HashMap data = (HashMap)e.getData();
//		data.put("code", rd.readShort());
//		data.put("lastroomname", rd.readString());
//		data.put("lastroomid", rd.readInt());
//		data.put("userid", rd.readInt());
//		data.put("gamekey", rd.readString());
//		data.put("md5", rd.readString());
//		data.put("isnewuser", rd.readByte());
		//short code= (Short)data.get("code");
		
		Message msg =handler.obtainMessage();
		msg.what=1;
		msg.obj=data;
		handler.sendMessage(msg);
		//if(code == 1){
			
		//}else{
		
		//}
	}
	public void recvGameLoginResult(Event e){
		 loadingStop();
		 System.out.println("DzpkGameStartActivity recvGameLoginResult");
			HashMap data = (HashMap) e.getData();
			Message msg =handler.obtainMessage();
			msg.what=0;
			msg.obj=data;
			handler.sendMessage(msg);
		
	}
	public void recvMyInfo(Event e){
		loadingStop();
		 System.out.println("DzpkGameStartActivity recvMyInfo");
		 GameApplication.userInfo=(HashMap)e.getData();
		 //forward(null);
	}
	/**
	 * 跳转界面
	 */
	private void forward(Object data){
		DisplayMetrics dm = new DisplayMetrics();   
		this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm); 
    	final float  h = dm.heightPixels;//*dm.density;
    	final float  w = dm.widthPixels;//*dm.density;
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
		 if(imgCheck.getContentDescription().equals(descValue1)){
		    saveUserLoginInfo(true);
		 }else{
			 //清空
			saveUserLoginInfo(false);
		 }
		 Intent it=new Intent();
		 
	     it.putExtra("vipadd", vipGold);
	     it.putExtra("vtask_add", taskGold);
	     it.putExtra("gold", gold);
		 it.putExtra("allGold", allGold);
 		 it.setClass(DzpkGameLoginActivity.this, DzpkGameMenuActivity.class);
 		 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 		 startActivity(it);
 		 finish();
 		 //左右滑入
 		 //overridePendingTransition(android.R.anim.slide_out_right,android.R.anim.slide_in_left);
 		 //淡入淡出
 		 //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	 
	 
	/**
	 * 处理登录送钱
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
	  /**
	   * 领奖结果
	   * @param e
	   */
	  public void onRecvLoginGive(Event e){
			 
		  Message msg= handler.obtainMessage();
		  msg.what=5;
		  msg.obj=e.getData();
		  handler.sendMessage(msg);
	}

	
	/**
	 * 添加侦听事件
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
	 * 移除侦听事件
	 */
	private void removeServiceListener(){
		GameApplication.getSocketService().removeEventListener(Event.ON_TEX_RECV_LOGIN_GIVE, this, "onRecvLoginGive"); 
		GameApplication.getSocketService().removeEventListener(Event.ON_TEX_LOGIN_SHOW_DAY_GOLD, this, "onTexLoginShowDayGold");
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_GROUP_INFO, this, "recvGroupInfo");
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_LOGIN_RESULT, this,"recvCenterLoginResult");
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_GAME_LOGIN_RESULT, this, "recvGameLoginResult");
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_MY_INFO, this, "recvMyInfo");

	}
	/**
	 * 侦听按键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if (keyCode == KeyEvent.KEYCODE_BACK ) {
	    	   GameUtil.backExit(DzpkGameLoginActivity.this);
	              return true;
	       }
//	       if(KeyEvent.KEYCODE_HOME==keyCode){
//	    	   Toast.makeText(this,"keyCode: "+keyCode+"  KEYCODE_HOME:  "+KeyEvent.KEYCODE_HOME,Toast.LENGTH_LONG).show(); 
//             return false;
//	  	   }
	    
	   return super.onKeyDown(keyCode, event);
	} 
	 

//	public void onAttachedToWindow() {
//    	//Toast.makeText(this,"keyCode:   KEYCODE_HOME:  ",Toast.LENGTH_LONG).show(); 
//       // this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//        super.onAttachedToWindow();
//    }
	Handler handler = new Handler(){
	 
		public void handleMessage(Message msg) {
			if(msg.what ==0){
				//游戏
		        executGameLoginResult((HashMap) msg.obj);
			}else if(msg.what ==1){
				//中心
				executCenterLoginResult((HashMap) msg.obj);
			}else if(msg.what ==2){
				//登录超时
				GameUtil.openMessageDialog(DzpkGameLoginActivity.this,GameUtil.LOGINTIMEOUT);
			}else if(msg.what ==4){
				forward(null);
			}else if(msg.what ==5){
				forward(msg.obj);
			}
	}};
	//发送登录游戏服务器
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
							//选择这个场登录
					       GameApplication.getSocketService().changRoom(groupItem);
					      break;
						}
					}
					if(i == size){
						loadingStop();
						GameUtil.openMessageDialog(DzpkGameLoginActivity.this,GameUtil.gamenotfound);
					}
				}
				return 0;
			}		
	     });
	}
	/**
	 * 处理登录结果
	 * @param data
	 */
	private void executCenterLoginResult(HashMap data){
		if (data != null) {
			short code = (Short) data.get("code");
			if (code == 1) {
				sendLoginGameServer();
			} else {
				loadingStop();
				GameUtil.openMessageDialog(this,GameUtil.msg31);
				GameApplication.joinAagin();
				System.out
						.println("onRecvGameServerLoginResult 登陆中心服务器失败 错误码："
								+ code);
			}
		}
	}
	
	/**
	 * 处理登录结果
	 * @param data
	 */
	private void executGameLoginResult(HashMap data){
		if (data != null) {
			short code = (Short) data.get("code");
			if (code == 1 || code == 2) {
				System.out
						.println("onRecvGameServerLoginResult 登录成功,更新频道信息");
				//dnGame.sendRequestUpdateChannelInfo("");
			} else if (code == -101) {
				GameUtil.openMessageDialog(this,GameUtil.msg1);
				GameApplication.joinAagin();
				System.out
						.println("onRecvGameServerLoginResult 登录游戏服务器失败,帐号正在使用");
			} else if (code == -102) {
				GameUtil.openMessageDialog(this,GameUtil.msg2);
				GameApplication.joinAagin();
				System.out
						.println("onRecvGameServerLoginResult 您已经在一个房间打牌，请完成牌局");
			} else {
				GameUtil.openMessageDialog(this,GameUtil.msg3);
				GameApplication.joinAagin();
				System.out
						.println("onRecvGameServerLoginResult 登陆游戏服务器失败 错误码："
								+ code);
			}
		}
	}

	//activity被暂停或收回cpu和其他资源时调用，该方法用于保存活动状态的，也是保护现场，压栈吧！
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("test4","onPause");
	}
	//重新启动activity时调用。该活动仍在栈中，而不是启动新的活动。
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i("test4","onRestart");
	}
	 
	//activity变为在屏幕上对用户可见时调用。
	protected void onStart(){
		super.onStart();
		Log.i("test4","onStart");
	}
 
	//activity被停止并转为不可见阶段及后续的生命周期事件时调用。
	protected void  onStop(){
		super.onStop();
		loadingStop();
		Log.i("test4","onStop");
	}
	@Override
	protected void onDestroy() {
		 
		super.onDestroy();
		Log.i("test4","onDestroy");
	
		GameUtil.recycle(acc_bg);
		GameUtil.recycle(bitmap_Btn1);
		GameUtil.recycle(bitmap_Btn2);
		GameUtil.recycle(bitmap_Check1);
		GameUtil.recycle(bitmap_Check2);
		removeServiceListener();
		 System.gc();
	}
	//static LoadingDialog loading;
	int timeOut =30000;//登录超时控制
	
	/**
	 * 开始加载
	 */
	private void loadingStart(){
		HwgCallBack callback = new HwgCallBack(){
			public void CallBack(Object... obj) {
				handler.sendEmptyMessage(2);
				
			}
		};
	   loadingStop();
	   GameApplication.showLoading(this,callback,timeOut);
//	   GameApplication.loading =  new LoadingDialog(this,R.style.dialog,callback,timeOut);
//	   GameApplication.loading.show();
//       TaskManager.getInstance().execute(new TaskExecutorAdapter(){
//		@Override
//		public int executeTask() throws Exception {
//			GameApplication.loading.start();
//			return 0;
//		} 
//       });
      
	}
	private void loadingStop(){
	  GameApplication.dismissLoading(); 
	}
	 
}