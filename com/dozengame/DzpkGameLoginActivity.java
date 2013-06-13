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
 * �û���¼����
 * @author hewengao
 *
 */
public class DzpkGameLoginActivity extends BaseActivity implements CallBack{
	
	final static CharSequence descValue0="0";
	final static CharSequence descValue1="1";
	EditText userName;//�û���
	EditText pwd;//����
	ImageView imgCheck;//�����û����빴ѡ��
	ImageView imgLogin;//��¼
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
        //��������¼�
        addServiceListener();
        //�����������ʵ�����뷨�ڴ������л���ʾ��������뷨�ڴ������Ѿ���ʾ�������أ�������أ�����ʾ���뷨��������
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
     * ��ʼ���ؼ�
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
     * ��Ӱ�ť�¼�
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
     * ��½
     */
    private void login(){
    	String name=  userName.getText().toString();//�û���
    	String password= pwd.getText().toString();//����
   
    	
    	try {
    		Log.i("test4", "name: "+name+" password: "+password);
    		if(name.trim().length() ==0){
    			GameUtil.openMessageDialog(this, GameUtil.msg6);
    		}else if(password.trim().length() ==0){
    			GameUtil.openMessageDialog(this, GameUtil.msg7);
    		}else{
	    		//���͵�¼����
	    		login(name, password);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
 
    }
	/**
	 * �����û���¼��Ϣ
	 */
	private void saveUserLoginInfo(boolean bl) {
		String name=  userName.getText().toString();//�û���
    	String password= pwd.getText().toString();//����
		//��Ҫ�����û���Ϣ
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
	 * ���������շ�������
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
	 * ���������ĵ�¼���
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
	 * ��ת����
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
			 //���
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
 		 //���һ���
 		 //overridePendingTransition(android.R.anim.slide_out_right,android.R.anim.slide_in_left);
 		 //���뵭��
 		 //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
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
	/**
	 * ���������¼�
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
				//��Ϸ
		        executGameLoginResult((HashMap) msg.obj);
			}else if(msg.what ==1){
				//����
				executCenterLoginResult((HashMap) msg.obj);
			}else if(msg.what ==2){
				//��¼��ʱ
				GameUtil.openMessageDialog(DzpkGameLoginActivity.this,GameUtil.LOGINTIMEOUT);
			}else if(msg.what ==4){
				forward(null);
			}else if(msg.what ==5){
				forward(msg.obj);
			}
	}};
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
						GameUtil.openMessageDialog(DzpkGameLoginActivity.this,GameUtil.gamenotfound);
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
				GameUtil.openMessageDialog(this,GameUtil.msg31);
				GameApplication.joinAagin();
				System.out
						.println("onRecvGameServerLoginResult ��½���ķ�����ʧ�� �����룺"
								+ code);
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
				GameUtil.openMessageDialog(this,GameUtil.msg3);
				GameApplication.joinAagin();
				System.out
						.println("onRecvGameServerLoginResult ��½��Ϸ������ʧ�� �����룺"
								+ code);
			}
		}
	}

	//activity����ͣ���ջ�cpu��������Դʱ���ã��÷������ڱ���״̬�ģ�Ҳ�Ǳ����ֳ���ѹջ�ɣ�
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("test4","onPause");
	}
	//��������activityʱ���á��û����ջ�У������������µĻ��
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i("test4","onRestart");
	}
	 
	//activity��Ϊ����Ļ�϶��û��ɼ�ʱ���á�
	protected void onStart(){
		super.onStart();
		Log.i("test4","onStart");
	}
 
	//activity��ֹͣ��תΪ���ɼ��׶μ����������������¼�ʱ���á�
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