package com.dozengame;


import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

import com.dozengame.net.SocketService;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.talk.TalkDialog;

import dalvik.system.VMRuntime;

public class GameApplication {
	//是否结算中
	public static boolean jieSuanIng =false;
	public static long receBytes=0;//接收的字节数
	public static long sendBytes=0;//发送的字节数
	public static int tab = -1;//选择的房间场
	private final static int CWJ_HEAP_SIZE = 12* 1024* 1024 ; 
	private final static float TARGET_HEAP_UTILIZATION = 0.85f; 
	//控制是否需要发送当日登录领奖
	public static int showDayGoldResults=-2;
	public static int showVipInfo=-2;
	public static HashMap vipInfoData =null;
	public static byte mobPayOff =0;//0：关 1:开
	static{
//		android应用中启动的所有Activity都位于一个栈中，
//		当点击返回键时，可以通过Activity的isTaskRoot()方法来判断，
//		当前activity是否位于当前栈中最后一个，如果不是则可以在返回时调用finish()方法，
//		将当前的acitivity杀死。 当用户跳转到应用的主页面时，应对转向Intent 
//		设置flag为FLAG_ACTIVITY_CLEAR_TOP的属性，这个属性会清除所有的之前的Activity  
		//android.os.Process.killProcess(android.os.Process.myPid());//获取PID    
		//设置最小堆内存
		//VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE); 
		//可以增强程序堆内存的处理效率
		//VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);
		VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE);
		VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);
		long heapSize= VMRuntime.getRuntime().getMinimumHeapSize();
		long byteAllocated=VMRuntime.getRuntime().getExternalBytesAllocated();
		float heapuitl=VMRuntime.getRuntime().getTargetHeapUtilization();
		Log.i("test1", "heapSize: "+heapSize+" byteAllocated: "+byteAllocated+"  heapuitl: "+heapuitl);
	}
	//public static ArrayList<DGroupInfoItem> list;
	public static HashMap userInfo;
	private static LinkedList<Activity> listViews= new LinkedList<Activity>();
	public static Activity currentActivity;//当前运行的activity
	public static LoadingDialog loading;
	public static  Dialog msgDialog;
 
	public static void dismissMsgDialog(){
		try{
			if(msgDialog != null){
				if(msgDialog.isShowing()){
					msgDialog.dismiss();
				}
			}
		}catch(Exception e){
			
		}
	}
	public static void dismissLoading(){
		try{
			if(loading != null){
				loading.stop();
				if(loading.isShowing()){
				loading.dismiss();
				}
			}
		}catch(Exception e){
			
		}
	}
	public static void showLoading(Context context,HwgCallBack callback,int timeOut){
		 dismissLoading();
		 GameApplication.loading =  new LoadingDialog(context,R.style.dialog,callback,timeOut);
		 loading.show();
		 TaskManager.getInstance().execute(new TaskExecutorAdapter(){
				@Override
				public int executeTask() throws Exception {
					GameApplication.loading.start();
					return 0;
				} 
		 });
	}
	public static void showLoading(Context context){
		showLoading(context,null,0);
	}
	
	
	//网络服务
	static SocketService service=null;
	public static SocketService getSocketService(){
		  if(service ==null){
			  service = new SocketService(); 
		  }
		  return service;
	}
//	//游戏布局控制
//    static DzpkGameActivity dzpkGame=null;
//    public static void setDzpkGame(DzpkGameActivity dzpkGames){
//    	 dzpkGame =dzpkGames;
//	}
  //游戏布局控制
    static DzpkGameActivityDialog dzpkGameDialg=null;
    public static void setDzpkGame(DzpkGameActivityDialog dzpkGames){
    	dzpkGameDialg =dzpkGames;
	}
	public static DzpkGameActivityDialog getDzpkGame(){
		  return dzpkGameDialg;
	}
	
	public static void push(Activity activity){
		listViews.addFirst(activity);
	}
	public static Activity poll(){
		if(!listViews.isEmpty()){
		 return listViews.removeFirst();
		}
		return null;
	}
	
	public static boolean joinAagin(){
		getSocketService().shutDownGCenter();
		getSocketService().shutDownGServer();
		return getSocketService().checkNetConnection();
	}
	 
	public int checkNetworkInfo(Context context){    
		ConnectivityManager conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);  
		//mobile 3G Data Network       
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();  
		//txt3G.setText(mobile.toString()); //显示3G网络连接状态       
		//wifi        
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();     
		//txtWifi.setText(wifi.toString()); //显示wifi连接状态    } 
		return 0;
	}
	/**
	 * 检测网络连接状态
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context){
		
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivity == null){
			return false;
		}else{
			NetworkInfo [] info = connectivity.getAllNetworkInfo();
			if(info != null){
				int len = info.length;
				for(int i=0;i<len;i++){
					if(info[i].getState() == NetworkInfo.State.CONNECTED){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void testNetCun(){
		long heapSize= VMRuntime.getRuntime().getMinimumHeapSize();
		long byteAllocated=VMRuntime.getRuntime().getExternalBytesAllocated();
		float heapuitl=VMRuntime.getRuntime().getTargetHeapUtilization();
		Log.i("test17", "heapSize: "+heapSize+" byteAllocated: "+byteAllocated+"  heapuitl: "+heapuitl);
	
	}
	static Object lock = new Object();
	public static TalkDialog talkDialog;
	public static void addReceBytes(int len){
		receBytes += len;
	}
	public static void addSendBytes(int len){
		synchronized (lock) {
			sendBytes += len;
		}
	}
	
	public static String getSendBytes(){
		synchronized (lock) {
			
			if(sendBytes < 1024){
				return sendBytes+"B";
			}else{
				double x = sendBytes/1024.0;
				if(x < 1024){
					return x+"KB";
				}else{
					x = x/1024;
					return x+"M";
				}
			}
		}
	}
	public static String getReceBytes(){
		synchronized (lock) {
			
			if(receBytes < 1024){
				return receBytes+"B";
			}else{
				double x = receBytes/1024.0;
				if(x < 1024){
					return x+"KB";
				}else{
					x = x/1024;
					return x+"M";
				}
			}
		}
	}
}
