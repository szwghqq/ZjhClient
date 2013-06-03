package com.dozengame;

import java.util.ArrayList;
import java.util.HashMap;
import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.net.pojo.DGroupInfoItem;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;

public class LoginedReceiver implements CallBack {

	public LoginedReceiver(){
		
	}
//	/**
//	 * 侦听到接收分组数据
//	 * @param e
//	 */
//	public void recvGroupInfo(Event e){
//		GameApplication.list =(ArrayList<DGroupInfoItem>) e.getData();
//		 
//		//发送登录游戏服务器
//			TaskManager.getInstance().execute(new TaskExecutorAdapter(){
//			@Override
//			public int executeTask() throws Exception {
//				if(GameApplication.list != null && !GameApplication.list.isEmpty()){
//					int size = GameApplication.list.size();
//					DGroupInfoItem groupItem;
//					for(int i=0;i<size;i++){
//						groupItem = GameApplication.list.get(i);
//						if(groupItem.groupid==18001){
//							//选择这个场登录
//					       GameApplication.getSocketService().changRoom(groupItem);
//					      break;
//						}
//					}
//				}
//				
//				return 0;
//			}
//			
//		});
//	}
	/**
	 * 侦听到中心登录结果
	 * @param e
	 */
	public void recvCenterLoginResult(Event e){
		HashMap data = (HashMap)e.getData();
	}
	public void recvGameLoginResult(Event e){
		 System.out.println("DzpkGameStartActivity recvGameLoginResult");
			HashMap data = (HashMap) e.getData();
			if (data != null) {
				short code = (Short) data.get("code");
				if (code == 1 || code == 2) {
					System.out
							.println("onRecvGameServerLoginResult 登录成功,更新频道信息");
					//dnGame.sendRequestUpdateChannelInfo("");
				} else if (code == -101) {
					System.out
							.println("onRecvGameServerLoginResult 登录游戏服务器失败,可能有相同帐号正在使用");
				} else if (code == -102) {
					System.out
							.println("onRecvGameServerLoginResult 您已经在一个房间打牌，请完成牌局");
				} else {
					System.out
							.println("onRecvGameServerLoginResult 登陆游戏服务器失败 错误码："
									+ code);
				}
			}
		
	}
	public void recvMyInfo(Event e){
		 System.out.println("DzpkGameStartActivity recvMyInfo");
		 GameApplication.userInfo=(HashMap)e.getData();
//		 Intent it=new Intent();
// 		 it.setClass(DzpkGameLoginActivity.this, DzpkGameMenuActivity.class);
// 		 startActivity(it);
// 		 finish();
	}
	/**
	 * 添加侦听事件
	 */
	private void addServiceListener(){
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_GROUP_INFO, this, "recvGroupInfo");
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_LOGIN_RESULT, this,"recvCenterLoginResult");
      
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_GAME_LOGIN_RESULT, this, "recvGameLoginResult");
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_MY_INFO, this, "recvMyInfo");
		//天天登录送钱
		GameApplication.getSocketService().addEventListener(Event.ON_TEX_LOGIN_SHOW_DAY_GOLD, this, "recvLoginShowDayGold");

	}
	/**
	 * 移除侦听事件
	 */
	private void removeServiceListener(){
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_GROUP_INFO, this, "recvGroupInfo");
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_LOGIN_RESULT, this,"recvCenterLoginResult");
      
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_GAME_LOGIN_RESULT, this, "recvGameLoginResult");
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_MY_INFO, this, "recvMyInfo");
		//天天登录送钱
		GameApplication.getSocketService().removeEventListener(Event.ON_TEX_LOGIN_SHOW_DAY_GOLD, this, "recvLoginShowDayGold");

	}
}
