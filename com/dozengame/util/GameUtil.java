package com.dozengame.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.util.Log;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.DzpkGameLoginActivity;
import com.dozengame.DzpkGameRoomActivity;
import com.dozengame.DzpkGameStartActivity;
import com.dozengame.GameApplication;
import com.dozengame.HwgCallBack;
import com.dozengame.Message1Dialog;
import com.dozengame.MessageDialog;
import com.dozengame.R;
import com.dozengame.db.DBManager;
import com.dozengame.net.pojo.DConfig;
import com.dozengame.net.pojo.Gift;
import com.dozengame.net.pojo.PlayerNetPhoto;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.xml.SAXParseXmlService;

/**
 * 游戏工具类
 * @author hewengao
 *
 */
public class GameUtil {
	public static final String ERRORTAG="Error";
	//玩家形象宽
	public static final int imgWidth=150;
	//玩家形象高
	public static final int imgHeight=150;
	
	//玩家形象宽
	public static final int imgWidth1=106;
	//玩家形象高
	public static final int imgHeight1=106;
	public static final String sure="确  定";
	public static final String chongZhi="充  值";
	public static final String EXITGAME="需要退出游戏吗？";
	public static final String gamenotfound="当前没有合适的游戏场 ";
    public static final String msg1="登录游戏服务器失败,帐号正在使用";
    public static final String msg2="您已经在一个房间打牌，请完成牌局";
    public static final String msg31="登陆失败,请检查账户密码是否正确";
    public static final String msg3="登陆失败 ,请检查账户密码是否正确";
    public static final String EXITMSG="确定退出程序吗？";
    public static final String msg4="很抱歉，您的筹码不足。\r\n立即充值，获取更多特权。";
    public static final String msgJiuJi="筹码不足,系统送你100救济金.";
    public static final String msg5="内测版暂未开放此功能";
    public static final String  LOGINLINGJIANG="恭喜您，获得登录奖励: $";
    public static final String LOGINLINGJIANG1="VIP登录额外奖励: $";
    public static final String LOGINLINGJIANG2="昨日任务奖励: $";
    public static final String CHOUMABUZU="很抱歉，您的筹码不足";
    public static final String ROOMFULL="对不起，该房间已满，请选择其它房间";
    public static final String CHOUMADUO=" 对不起，您的筹码超过本房间的最高限制";
    public static final String SITHASUSE=" 对不起，该座位上已经有人!";
	public static final String S = "+$";
	public static final  String msg6="请输入用户名"; 
	public static final  String msg7="请输入登录密码"; 
	public static final  String NETERRORMSG="网络已断开,重新启动"; 
	public static final String LOGINTIMEOUT="登录超时请重试";
	public static final String BEITI=" 对不起,您已被踢出房间.";
	public static final String LOADINGFAILED=" 坐下失败请重试.";
	public static final String NOTALK="对不起，旁观状态只允许VIP发言。";
	public static final String NOBIAOQING="该功能需要坐下才能使用！";
	public static final String BUYVIPSUCCESS="恭喜您，购买VIP成功\r\nVIP时间将持续到：";
	public static final String VIPOVERTIME="您的VIP已到期！";
	public static final String MOBPAYOFF="充值功能正在维护，请稍后再试!";
	/**
	 * 根据牌型值得到牌型字符串
	 * @param pokeWeight
	 * @return
	 */
	public static String getPokeWeight(int pokeWeight){
		String pw="";
		switch(pokeWeight){
    	case 1:
    		pw="高牌";
    		break;
    	case 2:
    		pw="一对";
    		break;
    	case 3:
    		pw="两对";
    		break;
    	case 4:
    		pw="三条";
    		break;
    	case 5:
    		pw="顺子";
    		break;
    	case 6:
    		pw="同花";
    		break;
    	case 7:
    		pw="葫芦";
    		break;
    	case 8:
    		pw="四条";
    		break;
    	case 9:
    		pw="同花顺子";
    		break;
    	case 10:
    		pw="皇家同花顺";
    		break;
    	}
		return pw;
	}
	/**
	 * 获取系统可用内存大小
	 * @param context
	 * @return
	 */
	public static String getNeCun(Context context){
		 ActivityManager  _ActivityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	     ActivityManager.MemoryInfo minfo = new ActivityManager.MemoryInfo();     
	     _ActivityManager.getMemoryInfo(minfo);          
	     String result =String.valueOf(minfo.availMem/(1024))+"KB";
	     
	     return result;
	}
	
	/**
	 *打开消息提示框不带取消按钮
	 * @param context
	 * @return
	 */
	public static void openMessageDialog(Context context,String msg){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new MessageDialog(context,R.style.dialog,msg,null);
		GameApplication.msgDialog.show();
	}
	/**
	 *打开消息提示框不带取消按钮
	 * @param context
	 * @return
	 */
	public static void openMessageDialog(Context context,String msg,HwgCallBack callback){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new MessageDialog(context,R.style.dialog,msg,callback);
		GameApplication.msgDialog.show();
	}
	/**
	 *打开消息提示框不带取消按钮
	 * @param context
	 * @return
	 */
	public static void openMessageDialog(Context context,String msg,HwgCallBack callback,String sureText){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new MessageDialog(context,R.style.dialog,msg,callback,sureText);
		GameApplication.msgDialog.show();
	}
	
	/**
	 *打开消息提示框带取消按钮
	 * @param context
	 * @return
	 */
	public static void openMessage1Dialog(Context context,String msg,HwgCallBack callback){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new Message1Dialog(context,R.style.dialog,msg,callback);
		GameApplication.msgDialog.show();
	}
	
	/**
	 *打开消息提示框带取消按钮
	 * @param context
	 * @return
	 */
	public static void openMessage1Dialog(Context context,String msg,HwgCallBack callback,String sureText){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new Message1Dialog(context,R.style.dialog,msg,callback,sureText);
		GameApplication.msgDialog.show();
	}
	/**
	 *打开消息提示框带取消按钮
	 * @param context
	 * @return
	 */
	public static void openMessage1Dialog(Context context,String msg,HwgCallBack callback,String sureText,String cancelText){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new Message1Dialog(context,R.style.dialog,msg,callback,sureText,cancelText);
		GameApplication.msgDialog.show();
	}
	/**
	 * 返回退出
	 */
	public static void backExit(final Activity context){
	 
	 
		if(context.isTaskRoot()){
			HwgCallBack callback = new HwgCallBack(){
	 
				public void CallBack(Object... obj) {
					if(obj == null || obj.length ==0)
					android.os.Process.killProcess(android.os.Process.myPid());//获取PID 
				}
			};
			openMessage1Dialog(context,EXITMSG,callback);
		}else{
			context.finish();
		}
	}
	public static void exitGame(final Activity context){
		HwgCallBack callback = new HwgCallBack(){
			 
			public void CallBack(Object... obj) {
				if(obj == null || obj.length ==0)
				android.os.Process.killProcess(android.os.Process.myPid());//获取PID 
			}
		};
		openMessage1Dialog(context,EXITGAME,callback);
	}
	public static boolean isNetError =false;
	/**
	 * 打开网络错误提示信息
	 */
	public static void openNetErrorMsg(){
		if(isNetError==false && GameApplication.currentActivity.getClass() != DzpkGameStartActivity.class){
		    isNetError =true;
		    GameApplication.getSocketService().shutDownGCenter();
		    GameApplication.getSocketService().shutDownGServer();
			handler.sendEmptyMessage(1);
		}
		
	}
	private static void openNetErrorMessage() {
		GameApplication.dismissLoading();
		HwgCallBack callback =new HwgCallBack(){
			 
			public void CallBack(Object... obj) {
				     GameApplication.dismissLoading();
				     if(GameApplication.currentActivity.getClass() ==DzpkGameActivity.class){
				    	 GameApplication.getDzpkGame().dismiss();
				     }else{
				    	 isNetError =false;
				     }
			 	     Intent it = new Intent();
					 it.setClass(GameApplication.currentActivity, DzpkGameStartActivity.class);
					 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 GameApplication.currentActivity.startActivity(it);
					 GameApplication.currentActivity.finish();
					 
				 
			}
			
		};
		openMessageDialog(GameApplication.currentActivity,NETERRORMSG,callback);
	}
	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what ==1){
			 openNetErrorMessage();
			}else if(msg.what ==0){
				//VIP信息提示
				openVipDialog(msg.obj);
			}
		}
	};
	//打开VIP提示信息
	public static void openVipDialog(HashMap data){
		Message msg =handler.obtainMessage();
		msg.what = 0;
		msg.obj =data;
		handler.sendMessage(msg);
	}
	//打开VIP提示信息
	private static void openVipDialog(Object  obj){
		HashMap data = (HashMap)obj;
		if(data != null){
			Byte success = (Byte)data.get("success");
			//0=vip过期 1=购买vip成功
			if(GameApplication.currentActivity instanceof DzpkGameStartActivity || GameApplication.currentActivity instanceof DzpkGameLoginActivity){
				GameApplication.showVipInfo =1;
				GameApplication.vipInfoData=data;
				return ;
			}
			if(success ==0){
				openMessageDialog(GameApplication.currentActivity,VIPOVERTIME,null);
			}else if(success ==1){
				String overtime =(String)data.get("overtime");
				openMessageDialog(GameApplication.currentActivity,BUYVIPSUCCESS+overtime,null);
			}
		}
	}
	/**
	 * 释放图片资源
	 * @param bitmap
	 */
	public static void recycle(Bitmap bitmap){
		if(bitmap != null){
			if(!bitmap.isRecycled()){
				bitmap.recycle();
			}
		}
		bitmap =null;
	}
	public static String splitIt(String str,int byteNum) throws Exception {
		if(str ==null)return " ";  
		String temp="";
	      int len = str.length();
	      int tempLen =0;
	      int count =0;
	      int i=0;
	      for(i=0; i< len;i++){
	    	  temp = str.charAt(i)+"";
	    	  tempLen=temp.getBytes().length;
	    	  if(tempLen==1){
	    		  count++;
	    	  }else{
	    		  count+=2;
	    	  }
	    	  if(count > byteNum){
	    		  i--;
	    		  break;
	    	  }else if(count ==byteNum){
	    		  break;
	    	  }
	      }
	      if(i < len-1){
	        temp=  str.substring(0, i+1)+"..";
	      }else{
	    	temp =str;
	      }
	     // Log.i("test8", "result: "+temp);
	      return temp;
	}
	public static String splitItss(String str,int byteNum) throws Exception {
		 
		  if(str ==null)return " ";
	      byte bt[] = str.getBytes("UTF-8");
	      int len =bt.length;
	     
	      if(len <= byteNum){
	    	  return str;
	      }
		if (byteNum >= 1 && len > byteNum) {
			String result;
			int count =0;
			int zfCount=0;
			int i=0;
			for(i=0;i < len;i++){
				if(((count/3)*2 + zfCount)>= byteNum){
					break;
				}
				if(bt[i] < 0){
					count++;
				}else{
					zfCount++;
				}
			}
			Log.i("test8","count: "+count+" i: "+i+"  zfCount: "+zfCount);
			i= i-count%3;
			result = new String(bt, 0,i,"UTF-8"); 
             if(i != len){
			  result =result+"..";
             }
			Log.i("test8", "str: "+str+" byteNum: "+byteNum+" result: "+result);
			 
      return result;
		} else {
			throw new  Exception("需要截取的字节数不合格");
		}
	}
	public static String splitIt2(String str,int byteNum) throws Exception {
		 
		  if(str ==null)return " ";
	      byte bt[] = str.getBytes("GBK");
	      int len =bt.length;
	      //Log.i("test4","len: "+len+"  byteNum: "+byteNum);
	      //for(int i=0; i<len;i++){
	    //	  Log.i("test4","i: "+i+"  v: "+bt[i]);
	     // }
	      if(len <= byteNum){
	    	  return str;
	      }
		if (byteNum >= 1 && len > byteNum) {
			String result;
			int count =0;
			for(int i=0;i < byteNum;i++){
				if(bt[i] < 0){
					count++;
				}
			}
			if (count % 2 != 0) {
				result = new String(bt, 0, --byteNum,"GBK"); 
			} else {
				result = new String(bt, 0, byteNum,"GBK");
			}
			result =result+"..";
			Log.i("test8", "str: "+str+" byteNum: "+byteNum+" result: "+result);
			//Log.i("test4",result);
          return result;
		} else {
			throw new  Exception("需要截取的字节数不合格");
		}
	}
	public static  void  executeCanNotIntoRoom(Context context,HashMap data ,HwgCallBack callback) {
		GameApplication.dismissLoading();
		if(data ==null)return;
		Byte intoRoomStats =(Byte)data.get("IntoRoomStats");
		switch(intoRoomStats){
		case 1:
			//正常进入
			break;
		case 0:
			//openMessageDialog(context,CHOUMABUZU,callback);
			TaskManager.getInstance().execute(new TaskExecutorAdapter(){

				@Override
				public int executeTask() throws Exception {
					GameApplication.getSocketService().sendRequestJiuJi();
					return 0;
				}
				
			});
			break;
		case -1:
			openMessageDialog(context,ROOMFULL,callback);
			break;
		case 2:
			openMessageDialog(context,CHOUMADUO,callback);
			break;
		case 3:
			openMessageDialog(context,CHOUMADUO,callback);
			//openMessageDialog(context,CHOUMADUO);
			break;
		case -10:
			//openMessageDialog(context,CHOUMADUO);
			break;
		case -4:
			//openMessageDialog(context,CHOUMADUO);
			break;
		case -8:
			//openMessageDialog(context,CHOUMADUO);
			break;
			
		}
		data.clear();
		data =null;
//		else if (e.data["IntoRoomStats"] == 2)
//		{
//			this.m_ui.resetLastState(true);
//			DTrace.traceex("钱多了");
//			m_ui.showMessageBox("    对不起，您的筹码"+ myinfo.gold +"已经超过本房间的最高限制"+m_nCurrentGroupInfo.at_most_gold+"，点确定进入房间更高倍的房间进行游戏", 1, false, null, false, true, gotoHigherRoom);
//		}
//		else if (e.data["IntoRoomStats"] == 3)
//		{
//			this.m_ui.resetLastState(true);
//			m_ui.showMessageBox("    您筹码已经超过超过100万了，您已经不再适合在新手挑战场进行游戏，我们推荐您去2000倍和10000倍房间，祝您玩的开心。", 1, false, null, true);
//		}
//		else if (e.data["IntoRoomStats"] == -10)
//		{
//			this.m_ui.resetLastState(true);
//			showMessageBox("    竞技场的开放时间为每日 早9:00 到 次日凌晨1:00，您可以先去普通场娱乐一下。", 1);
//		}
//		else if (e.data["IntoRoomStats"] == -4)
//		{
//			this.m_ui.resetLastState(true);
//			showMessageBox("    您的积分不足，进入该房间至少需要积分在 "+e.data.gold+" 以上。", 1);
//		}
//		else if(e.data["IntoRoomStats"] == -8)
//		{
//			this.m_ui.resetLastState(true);
//			showMessageBox("    抱歉，您已被踢离座，10分钟后方可再次进入.", 1);
//		}
	}
    static HashMap<Integer, Gift> giftHm;
	/**
	 * 初始化礼物信息
	 * @param context
	 */
	public static void initGift(Context context){
	 
		try {
			 
			 SharedPreferences uiState  =  context.getApplicationContext().getSharedPreferences("gift",Context.MODE_WORLD_WRITEABLE);
			if(uiState.getInt("hasgift",-1) ==-1){
				
				 DBManager.init(context);
				 DBManager.executeSql(Gift.DROP_TABLE);
				 DBManager.executeSql(Gift.CREATE_TABLE);
				 InputStream is = context.getAssets().open("shop.xml");
				 HashMap<Integer, Gift>  giftTemp=SAXParseXmlService.getGiftService(is);
				 if(giftTemp != null){
					Iterator<Entry<Integer, Gift>> it = giftTemp.entrySet().iterator();
					Gift temp =null;
					ContentValues values=null;
					while(it.hasNext()){
					 
					    temp = it.next().getValue();
				        values=new ContentValues();
						values.put(Gift.ID,temp.getId());
						values.put(Gift.NAME,temp.getName());
						values.put(Gift.TAB,temp.getTab());
						values.put(Gift.IMGPATH,temp.getImgPath());
					    DBManager.insert(Gift.TABLE_NAME, null, values);
						 
					}
					it =null;
					giftTemp.clear();
					giftTemp =null;
				 }
				 DBManager.clear();
				 SharedPreferences.Editor editor = uiState.edit();
				 editor.putInt("hasgift", 0);
				 editor.commit(); 
				 uiState =null;
				 editor =null;
			}
			 
		 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 根据礼物ID获取礼物
	 * @param id
	 */
	public static Gift getGiftById(Context context,Integer giftId){
		//if(giftHm !=null){
		//	return giftHm.get(giftId);
		//}
		 DBManager.init(context);
		 Gift gift= DBManager.findGift(Gift.TABLE_NAME, Gift.ID+"="+giftId, null);
		 DBManager.clear();
		 return gift;
	}
	/**
	 * 根据userID获取PlayerNetPhoto
	 * @param id
	 */
	public static PlayerNetPhoto getPlayerPhotoById(Context context,Integer userID){
		 try{
			 DBManager.init(context);
			 DBManager.executeSql(PlayerNetPhoto.CREATE_TABLE);
			 PlayerNetPhoto photo= DBManager.findPlayerNetPhoto(PlayerNetPhoto.TABLE_NAME, PlayerNetPhoto.ID+"="+userID, null);
			 DBManager.clear();
			 return photo;
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return null;
	}
	/**
	 *添加或替换PlayerNetPhoto
	 * @param id
	 */
 
	public static void insertPlayerNetPhoto(Context context,int userid,String httpUrl,int state,byte [] photoBytes,boolean exists){
		try{
		    DBManager.init(context);
			if(exists){
				//删除
				DBManager.delete(PlayerNetPhoto.TABLE_NAME, PlayerNetPhoto.ID+"="+userid,null);
			}
			//添加
		    ContentValues values=new ContentValues();
			values.put(PlayerNetPhoto.ID,userid);
			values.put(PlayerNetPhoto.HTTP_URL,httpUrl);
			values.put(PlayerNetPhoto.PHOTO_BYTES,photoBytes);
			values.put(PlayerNetPhoto.STATE,state);
		    DBManager.insert(PlayerNetPhoto.TABLE_NAME, null, values);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 判断是否已锁屏
	 * @param c
	 * @return
	 */
	public final static boolean isScreenLocked(Context c) {
        android.app.KeyguardManager mKeyguardManager = (KeyguardManager) c.getSystemService(c.KEYGUARD_SERVICE);
        return mKeyguardManager.inKeyguardRestrictedInputMode();
    }
	
	//观战失败提示
	public final static void onRecvWatchError(final DzpkGameActivityDialog context,HashMap data){
		GameApplication.dismissLoading();
		int errorcode = (Integer) data.get("errorcode");
		String strMsg = "";
		if (errorcode == -1) {
			strMsg = "进入桌子失败，此房间号异常。";
		} else if (errorcode == -2) {
			strMsg = "进入桌子失败，该房间不在本服务器。";
		} else if (errorcode == -3) {
			strMsg = " 进入桌子失败，该房间游戏已开始且不允许观战。";
		} else if (errorcode == -4) {
			strMsg = "进入桌子失败，旁观人数太多，请稍候进入。";
		} else if (errorcode == -5) {
			strMsg = "进入桌子失败，您等级还不够哦。";
		} else if (errorcode == -6) {
			strMsg = "进入桌子失败，该房间仅允许VIP玩家进入。";
		} else if (errorcode == -7) {
			// m_ui.showCmNotEnoughWindow();
			return;
		} else if (errorcode == -8) {
			strMsg = "抱歉，您已被踢离座，10分钟后方可再次进入.";
		} else if (errorcode == -10) {
			strMsg = "抱歉,你已被请出牌桌";
		} else {
			strMsg = "进入桌子失败，该房间仅允许VIP玩家进入。错误码[" + errorcode + "]";
		}
		HwgCallBack callback = new HwgCallBack(){
			public  void CallBack(Object[] obj) {
		 
				 Intent it =new Intent();
				 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 it.setClass(context.getContext(), DzpkGameRoomActivity.class);
				 context.getContext().startActivity(it);
				 context.dismiss();
			};
		};
		openMessageDialog(context.getContext(),strMsg,callback);
		//showMessageBox(strMsg, 1, false)
	}
	/**
	 * 重新登录
	 */
	public static void reLogin(){
		try{
			if(GameApplication.currentActivity == null)return;
			Intent it = new Intent(GameApplication.currentActivity,DzpkGameLoginActivity.class);
			GameApplication.currentActivity.startActivity(it);
			GameApplication.currentActivity.finish();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 解析流，转换成文档对象模型
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(InputStream is) throws Exception {
		 
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbfactory.newDocumentBuilder();
		return db.parse(is);

	}
	/**
	 * 解析流，转换成文档对象模型
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(byte[] bt) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(bt);
		return getDocument(bis);
	}
	
	/**
	 * 获取当前我的位置
	 * @param context
	 * @return 0:GPS已开启 -1:GPS未开启 -2:未获到取经伟度 1:已获取到经伟度
	 */
	public static int initLocationGPS(Context context){
		Log.i("test2", "initLocationGPS");
	 	LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		//判断GPS状态
		boolean GPS_status = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
	   //  boolean NETWORK_status = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	   int state =-1;
	    if(GPS_status){
	    	Log.i("test2", "GPS GPS_status开启");
	    	state =0;
	    }
//	    else if(NETWORK_status){
//	    	Log.i("test2", "GPS NETWORK_status开启");
//	    }
	    else{

	    	Log.i("test2", "GPS GPS_status NETWORK_status未开启");
           state =-1;
           return state;
        }	
		Criteria criteria = new Criteria();  
		criteria.setAccuracy(Criteria.ACCURACY_FINE);  
		criteria.setAltitudeRequired(false);  
		criteria.setBearingRequired(false);  
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); 
		String provider = lm.getBestProvider(criteria, false);
		Log.i("test2", "GPS provider: "+provider);
		
		//获取GPS坐标
		Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if(myLocation == null){
			state = -2;
			 return state;
			// myLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		} 
		 
		DConfig.nMyLatitudeX = myLocation.getLatitude();
		DConfig.nMyLongitudeY = myLocation.getLongitude(); 
		//Log.i("test2", " DConfig.nMyLatitudeX: "+DConfig.nMyLatitudeX+"  DConfig.nMyLongitudeY: "+DConfig.nMyLongitudeY);
         state =1;
		return state;
	}
	/**
	 * 分隔游戏币
	 * @param gold
	 * @return
	 */
	public static String getMoneySplit(int gold){
		String result=""+gold;
		int len = result.length()-1;
		StringBuffer sb = new StringBuffer();
		int count =0;
		for(int i= len; i >=0 ; i--){
			count++;
			sb.append(result.charAt(i));
			if(count % 3 == 0 && count != len+1){
				sb.append(",");
			}
		}
		sb=sb.reverse();
		return sb.toString();
	}
}
