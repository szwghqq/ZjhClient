package com.dozengame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.net.pojo.DGroupInfoItem;
import com.dozengame.net.pojo.DeskInfo;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameUtil;

public class RoomView extends LinearLayout implements CallBack{

	private List<DeskInfo> listItem= new ArrayList<DeskInfo>();
	private List<DeskInfo> listItemClone= new ArrayList<DeskInfo>();
	BaseListAdapter adapter;
	ListView listView;
	DzpkGameRoomActivity context;
	int roomTypeId;
	DGroupInfoItem groupItem;
	//��ǰѡ����ͼ
	ViewGroup currentViewGroup;
	public RoomView(DzpkGameRoomActivity context,int roomTypeId) {
		super(context);
		this.context=context;
		this.roomTypeId=roomTypeId;
	    View view=this.inflate(context, R.layout.gameroomflip, this);
	    listView=(ListView)findViewById(R.id.list);
        initData();
	}
	public void setScrollEnable(boolean enable){
		this.listView.setScrollbarFadingEnabled(enable);
	}
	/**
//	 * ��ʼ������
	 */
	public void initData(){
		
//		listView.setOnItemClickListener(new OnItemClickListener(){
//
//			@Override
//			public void onItemClick(AdapterView<?> adView, View view, int arg2,
//					long arg3) {
//				DeskInfo deskInfo= (DeskInfo)adView.getItemAtPosition(arg2);
//				addDesk(deskInfo);
//			}
//			
//		});
 
        HwgCallBack callback = new HwgCallBack(){
 
			public void CallBack(Object... obj) {
				if(DzpkGameActivity.isDestroy){
					 if(obj != null && obj.length ==1){
						 Integer pos = (Integer)obj[0];
						 DeskInfo deskInfo= (DeskInfo)listView.getItemAtPosition(pos);
						  addDesk(deskInfo);
					 }
				}else{
					loadStart();
				}
			}
        	
        };
		adapter = new BaseListAdapter<DeskInfo>(context,
				R.layout.gridviewitem, listItem, new String[] { "img", 
				   "Deskno","Name","RenNum","Bet","XieDai","Watchercount" }, 
				   new int[] { R.id.img, R.id.roomId, R.id.roomName,R.id.renNum,R.id.mZhu ,R.id.xieDai ,R.id.pGuan},callback);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
 
	}
	
	/**
	 * ��������
	 * @param deskInfo
	 */
	private void addDesk(DeskInfo deskInfo){
		 setScrollEnable(true);
		 Intent intent =new Intent(context,DzpkGameActivity.class);
		 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 intent.putExtra("deskInfo", deskInfo);
		 context.startActivity(intent);
		 context.finish();
		 
	}
	/**
	 * ��������
	 */
	public void loadData(){
		setScrollEnable(true);
		loadingStart();
		//����¼�����
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_CURPAGEROOM_LIST, this, "onRecvCurPageRoomList");
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			@Override
			public int executeTask() throws Exception {
				//��������������
				GameApplication.getSocketService().sendRequestDeskList(1, roomTypeId);
				return 0;
			}
		});
		
	}
	/**
	 * 
	 */
	public void loadStart(){
	    loadingStop();
		GameApplication.loading =  new LoadingDialog(context,R.style.dialog,null,-1);
		GameApplication.loading.show();
 
	}
	/**
	 * ��ʼ����
	 */
	private void loadingStart(){
		loadingStop();
		HwgCallBack callback = new HwgCallBack() {
			
			@Override
			public void CallBack(Object... obj) {
                //���س�ʱ
				 GameUtil.openNetErrorMsg();
			}
		};
		GameApplication.loading =  new LoadingDialog(context,R.style.dialog,callback,30000);
		GameApplication.loading.show();
        TaskManager.getInstance().execute(new TaskExecutorAdapter(){
		@Override
		public int executeTask() throws Exception {
			GameApplication.loading.start();
			return 0;
		} 
       });
      
	}
	public  void loadingStop(){
		 
		GameApplication.dismissLoading();
	}
	/**
	 * ���յ������б���Ϣ
	 * @param e
	 */
	public void onRecvCurPageRoomList(Event e){
		 
		 loadingStop();
	 
		//�Ƴ��¼�����
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_CURPAGEROOM_LIST, this, "onRecvCurPageRoomList");
		ArrayList data = (ArrayList)e.getData();
		//���½�������
	    sendMsg(data);
	}
	
	View currentSelectView;//��ǰѡ�е��б�����ͼ
	public int getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {// ����һ��Handler�����ڴ��������߳���UI��ͨѶ
			if (!Thread.currentThread().isInterrupted()) {
			 
				    listItemClone.clear();
				    listItem.clear();
					if (msg.obj != null) {
						ArrayList<DeskInfo> list = (ArrayList<DeskInfo>) msg.obj;
						listItemClone.addAll(list);
						executeDisplayDeskInfo();
						list.clear();
						list = null;
					}else{
					   adapter.notifyDataSetChanged();
					}
			        if(listItemClone != null && listItemClone.size() > 0){
			        	 setScrollEnable(false);
			        }else{
			        	 setScrollEnable(true);
			        }
					
			}
			super.handleMessage(msg);
		}
	};

	private void sendMsg(List<DeskInfo> list) {
		Message msg = new Message();
		msg.obj = list;
		handler.sendMessage(msg);
	}
	 
	public void dismiss(){
		if(GameApplication.loading != null){
			//if(loading.isShowing()){
			GameApplication.loading.dismiss();
			//}
		}
		listItem.clear();
		listItemClone.clear();
		GameApplication.loading =null;
	}

	/**
	 * ������ʾ�����б�
	 */
	public void executeDisplayDeskInfo(){
		 
		SharedPreferences uiState  = this.getContext().getApplicationContext().getSharedPreferences("displayeSet",Context.MODE_WORLD_WRITEABLE);
		listItem.clear();
		int empty = uiState.getInt("empty", -1);
		int full = uiState.getInt("full", -1);
		int state =3;
		if(empty ==0  && full ==0){
			state =0;
		}else if(empty ==0){
			state =1;
		}else if(full ==0){
			state =2;
		} 
		DeskInfo temp=null;
		int size;
		switch(state){
		case 0://���պ���
			  size =listItemClone.size();
				for(int i=0;i < size;i++){
					temp=listItemClone.get(i);
					if(temp.isFull() || temp.isEmpty()){
						continue;
					}
					listItem.add(temp);
				}
			break;
		case 1://ֻ������
			  size =listItemClone.size();
				for(int i=0;i < size;i++){
					temp=listItemClone.get(i);
					if(temp.isEmpty()){
						continue;
					}
					listItem.add(temp);
				}
			break;
		case 2://ֻ������
			size =listItemClone.size();
			for(int i=0;i < size;i++){
				temp=listItemClone.get(i);
				if(temp.isFull()){
					continue;
				}
				listItem.add(temp);
			}
			break;
		case 3://������
			listItem.addAll(listItemClone);
			break;
		}
		sort(-1,(short)1);
		//adapter.notifyDataSetChanged();
	}
	/**
	 * �����������
	 * @param titleIndex 0:����id 1������ 2:���� 3:��Сä 4:��СЯ�� 5���Թ�
	 * @param s 0:���� 1:����
	 */
	public void sort(final int titleIndex,final short s) {
		java.util.Collections.sort(listItem,new Comparator<DeskInfo>(){
			@Override
			public int compare(DeskInfo obj1, DeskInfo obj2) {
				int result =0;
				switch(titleIndex){
				case -1:
					 if(obj1.getPlayercount() > obj2.getPlayercount()){
						 result =1;
					 }else if(obj1.getPlayercount()< obj2.getPlayercount()){
						 result = -1;
					 }else{
						 //������ͬ�����ʸߵ���ǰ
						 if(obj1.getSmallbet() > obj2.getSmallbet()){
							 result =1;
						 }else{
							 //�Թ�����
							 if(obj1.getWatchercount() > obj2.getWatchercount()){
								 result =1;
							 }
						 }
					 } 
					break;
				case 0:
					 if(obj1.getDeskno() > obj2.getDeskno()){
						 result =1;
					 }else if(obj1.getDeskno()< obj2.getDeskno()){
						 result = -1;
					 } 
					break;
				case 1:
					result=obj1.getName().compareTo(obj2.getName());
					break;
				case 2:
					 if(obj1.getPlayercount() > obj2.getPlayercount()){
						 result =1;
					 }else if(obj1.getPlayercount()< obj2.getPlayercount()){
						 result = -1;
					 } 
					break;
				case 3:
					if(obj1.getSmallbet() > obj2.getSmallbet()){
						 result =1;
					 }else if(obj1.getSmallbet()< obj2.getSmallbet()){
						 result = -1;
					 } 
					break;
				case 4:
					if(obj1.getAt_least_gold() > obj2.getAt_least_gold()){
						 result =1;
					 }else if(obj1.getAt_least_gold()< obj2.getAt_least_gold()){
						 result = -1;
					 } 
					break;
				case 5:
					if(obj1.getWatchercount() > obj2.getWatchercount()){
						 result =1;
					 }else if(obj1.getWatchercount()< obj2.getWatchercount()){
						 result = -1;
					 } 
					break;
				}
				if(s == 1){
					result = -result;
				}
				return result;
			}
		});
//		if(s ==1){
//		   java.util.Collections.reverse(listItem);
//		}
		adapter.notifyDataSetChanged();
	}
	
}
