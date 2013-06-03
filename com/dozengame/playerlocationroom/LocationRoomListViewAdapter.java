package com.dozengame.playerlocationroom;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alipay.net.JavaHttp;
import com.dozengame.DzpkGameMenuActivity;
import com.dozengame.GameApplication;
import com.dozengame.HwgCallBack;
import com.dozengame.PlayerInfoDialog;
import com.dozengame.R;
import com.dozengame.event.Event;
import com.dozengame.event.FriendEventType;
import com.dozengame.gameview.PlayerInfoView;
import com.dozengame.net.pojo.DConfig;
import com.dozengame.net.pojo.PlayerNetPhoto;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.net.JavaHttp;
import com.dozengame.HwgCallBack;
import com.dozengame.R;
import com.dozengame.net.pojo.PlayerNetPhoto;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
import com.dozengame.event.CallBack;

public class LocationRoomListViewAdapter extends BaseAdapter implements HwgCallBack, CallBack{

	private List<PlayerLocationData> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    
    int nD;
    
    int nX;

    Bitmap palyerBit;
    Bitmap piece_bg;
    Bitmap info_button;
    Bitmap info_buttons;
    
    ImageView avatarImg;
    ImageView infoImage;
    
//    PlayerInfoView pv;
//    FrameLayout frameLayout;
    
    private List<Map<String, Object>> mAvatarImgData;
    
	public LocationRoomListViewAdapter(Context context,List<PlayerLocationData> listViewData) {
		this.mContext = context;
		this.mList = listViewData;
		
		init(context);
	}
	
	public void init(Context context)
	{
		try {
			piece_bg = BitmapFactory.decodeStream(mContext.getAssets().open("locationroom/gps_bg.png"));
			info_button = BitmapFactory.decodeStream(mContext.getAssets().open("hall_button.png"));
			info_buttons = BitmapFactory.decodeStream(mContext.getAssets().open("hall_buttons.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity)mContext;
		final ListView listview = (ListView)parent;
        convertView = mInflater.inflate(R.layout.locationroomlist_piece, null);  
        convertView.setBackgroundDrawable(new BitmapDrawable(piece_bg));
     /*   convertView.setSelected(false);
        convertView.setPressed(false);
        convertView.onTouchEvent(null);*/
        
        avatarImg = (ImageView)convertView.findViewById(R.id.avatarimage);
        TextView nick = (TextView)convertView.findViewById(R.id.friendnick);
        TextView gold = (TextView)convertView.findViewById(R.id.gold);
        TextView distance = (TextView)convertView.findViewById(R.id.distance);
     //   TextView vip = (TextView)convertView.findViewById(R.id.vip);
   //     TextView distancelogo = (TextView)convertView.findViewById(R.id.distancelogo);
        
        ImageView friendNickImg = (ImageView)convertView.findViewById(R.id.friendnickimage);  //
   //     ImageView goldImg = (ImageView)convertView.findViewById(R.id.goldimage);    //
    //    ImageView distanceImg = (ImageView)convertView.findViewById(R.id.distanceimage);    //距离
    //    ImageView distanceLogImg = (ImageView)convertView.findViewById(R.id.distancelogoimage);    //
        
        PlayerLocationData tempData = (PlayerLocationData)getItem(position);
        
        ////详细信息
   //     infoImage=(ImageView)convertView.findViewById(R.id.distancelogoimage);
   //     infoImage.setImageBitmap(info_button);
        ///
        
        //显示头像
        final String face=tempData.getFaceurl();
		if(face.startsWith("http:") || face.startsWith("https:")){
			PlayerNetPhoto	photo = GameUtil.getPlayerPhotoById(mContext,tempData.nUserid);
			if(photo != null && photo.getHttpUrl().equals(face)){
				//存在
				Log.i("test17", "存在此基础上 ");
				createBitmap(photo.getPhotoBytes(),false,avatarImg);
			}else{
				 
				//不存在需要下载图片
				TaskManager.getInstance().execute(new TaskExecutorAdapter(){
 
					public int executeTask() throws Exception {
						JavaHttp http= new JavaHttp(face, LocationRoomListViewAdapter.this, "download");
						http.execute();
						return 0;
					}
				});
				
				while(nX < 1)
				{
					//等待HTTP加载结束
				}
				nX = 0;
			}
		  }else{
		    	Bitmap result=	GameBitMap.getBitmapByName(this.mContext, face);
		    	if(result != null){
		    		palyerBit=GameBitMap.resizeBitmap(result, GameUtil.imgWidth, GameUtil.imgHeight);
					if(palyerBit != null && !palyerBit.isRecycled()){
						avatarImg.setImageBitmap(palyerBit);
					 
					}
					
					GameUtil.recycle(result);
		    	}
		  }
	
  //      goldImg.setBackgroundResource(R.drawable.gamechouma);
  //      distanceImg.setBackgroundResource(R.drawable.gps_space);
  //      distanceLogImg.setBackgroundResource(R.drawable.gps_class);
        
        if(tempData.nSex == 0)
        {
        	//女
        	Bitmap result=	GameBitMap.getBitmapByName(this.mContext, "locationroom/gps_woman.png");
        	friendNickImg.setImageBitmap(result);
        	//friendNickImg.setBackgroundResource(R.drawable.gps_woman);
        }
        else
        {
        	//friendNickImg.setBackgroundResource(R.drawable.gps_man);
        	Bitmap result=	GameBitMap.getBitmapByName(this.mContext, "locationroom/gps_man.png");
        	friendNickImg.setImageBitmap(result);
        }
        
        nick.setText("" + tempData.strNick);
        gold.setText("￥ " + tempData.nGold);
      
        if(tempData.strLatitude == null && tempData.strLongitude == null )
		{
        	
        	distance.setText("无");
		}
		else
		{
			//final Calendar currentDate = Calendar.getInstance();
			Date currentDate = new Date();
			//currentDate.setTime(date);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");   
	        String str1 = sdf1.format(currentDate);   

			
			String s=  tempData.strDate.substring(0,10); //截取出年月日
			
			//int nD;
			try {
				nD = (int) getCompareDate(s, str1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (tempData.nDistance < 1)
			{
				tempData.nDistance = tempData.nDistance * 100;
				
				if(tempData.nStat == 0)
				{
					if(nD == 0)
					{
						distance.setText(""+ tempData.nDistance +"米/今日");
					}
					else
					{
						distance.setText(""+ tempData.nDistance +"米"+"/"+ nD +"日前");
					}
					
				}
				else
				{
					distance.setText(""+ tempData.nDistance +"米");
				}
	
	//			distancelogo.setText("1 公 里 内");
			}
			else
			{	
				//nDistance.setMaximumFractionDigits(3);
				tempData.nDistance = Math.round(tempData.nDistance * 10000)/ 10000;
				if(tempData.nStat == 0)
				{
					if(nD == 0)
					{
						distance.setText(""+ tempData.nDistance +"公里/今日");
					}
					else
					{
						distance.setText(""+ tempData.nDistance +"公里"+"/"+ nD +"日前");
					}
					
				}
				else
				{
					distance.setText(""+ tempData.nDistance +"公里");
				}
				//distance.setText(""+ nDistance +"公里");
				
			/*	if(nDistance < 10)
				{
					distancelogo.setText("10公里内");
				}
				else if(nDistance < 100)
				{
					distancelogo.setText("100公里内");
				}
				else if(nDistance < 200)
				{
					distancelogo.setText("200公里内");
				}
				else if(nDistance < 1000)
				{
					distancelogo.setText("1000公里内");
				}
				else if(nDistance < 5000)
				{
					distancelogo.setText("5000公里内");
				}
				else
				{
					distancelogo.setText("遥远的国度");
				}*/
			}
			
			int nSize = mList.size();
			if(nSize-1 == position)
			{
				loadingStop();
			}
		}
        
        //监听按钮
     //   addListener();
    
        return  convertView;
	}
	private void createBitmap(byte [] bytes,boolean bl,ImageView playerImg){
		 Bitmap result=null;
		 try{	 
	   	   result= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	   	   Bitmap  palyerBit=GameBitMap.resizeBitmap(result, GameUtil.imgWidth, GameUtil.imgHeight);
	   	
	   	   if(bl){
	   		 
	   		   handler.sendEmptyMessage(1);
	   	   }else{
	   		   if(palyerBit != null){
				 playerImg.setImageBitmap(palyerBit); 
				 
				 nX = 1;
				}  
	   	   }
			   Log.i("test17","createBitmap在附近玩家中");
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 GameUtil.recycle(result); 
		 }
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		};
	};
	 
	

	@Override
	public void CallBack(Object... obj) {
		if(obj == null || obj.length!=1 || !(obj[0] instanceof byte[])) {
			return;
		}
		Log.i("test17","CallBackCallBackCallBack");
		byte[] response = (byte[])obj[0];
			try {
			//	createBitmap(response,true, avatarImg);
				createBitmap(response,false, avatarImg);
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				 
			}
	}
	
	protected void onDestroy() {
	
		GameUtil.recycle(palyerBit);
		GameUtil.recycle(piece_bg);
		
		GameUtil.recycle(info_button);
		GameUtil.recycle(info_buttons);
	}
	
	
	/**
	 * 获取日期差，返回相差天数。
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static long getCompareDate(String startDate,String endDate) throws ParseException {   
	     SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");   
	     Date date1=formatter.parse(startDate);       
	     Date date2=formatter.parse(endDate);   
	     long l = date2.getTime() - date1.getTime();   
	     long d = l/(24*60*60*1000);   
	     return d;   
	 } 
/*
	 *//**
     * 添加按钮事件
     *//*
    private void addListener(){

    	//返回按钮事件
    	infoImage.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	infoImage.setImageBitmap(info_button);
			    	return true;
			    }else if(action ==1){
			    	infoImage.setImageBitmap(info_buttons);
			    	infoEvent();   //返回
			    	return true;
			    }
				return false;
			}
    		
    	});
    }*/
 /*   
    *//**
	 * 详细信息
	 *//*
	private void infoEvent(){
		
		//添加事件侦听
	//	GameApplication.getSocketService().addEventListener(FriendEventType.ON_RECV_EXTRAINFO_ACHIEVEINFO, this, "onResponseExtrainfoAchieveInfo");				
		
		//请求游戏者附加信息和成就信息
		try {
			GameApplication.getSocketService().sendRequestUserExtraInfoAchieveInfo(DConfig.userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	*//**
	 * 收到成就附加信息
	 * @param data
	 *//*
	public void onResponseExtrainfoAchieveInfo(HashMap data) {
		Message msg=handler1.obtainMessage();
		msg.what =0;
		msg.obj =data;
		handler1.sendMessage(msg);
	}
	Handler handler1 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			executeResponseExtrainfoAchieveInfo((HashMap)msg.obj);
		}
	};
	*//**
	 * 处理收到成就信息
	 * 在新的弹出窗口上
	 * @param data
	 *//*
	private void executeResponseExtrainfoAchieveInfo(HashMap data){
	
		// 弹起
				pv =new PlayerInfoView(mContext,3,data);
			    frameLayout.addView(pv);
	}
	*/
    
	/**
	 * loading等待条
	 */
	public  void loadingStop(){
		 
		GameApplication.dismissLoading();
	}
	
}
