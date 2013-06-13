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
//	 * ���������շ�������
//	 * @param e
//	 */
//	public void recvGroupInfo(Event e){
//		GameApplication.list =(ArrayList<DGroupInfoItem>) e.getData();
//		 
//		//���͵�¼��Ϸ������
//			TaskManager.getInstance().execute(new TaskExecutorAdapter(){
//			@Override
//			public int executeTask() throws Exception {
//				if(GameApplication.list != null && !GameApplication.list.isEmpty()){
//					int size = GameApplication.list.size();
//					DGroupInfoItem groupItem;
//					for(int i=0;i<size;i++){
//						groupItem = GameApplication.list.get(i);
//						if(groupItem.groupid==19001){
//							//ѡ���������¼
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
	 * ���������ĵ�¼���
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
							.println("onRecvGameServerLoginResult ��¼�ɹ�,����Ƶ����Ϣ");
					//dnGame.sendRequestUpdateChannelInfo("");
				} else if (code == -101) {
					System.out
							.println("onRecvGameServerLoginResult ��¼��Ϸ������ʧ��,��������ͬ�ʺ�����ʹ��");
				} else if (code == -102) {
					System.out
							.println("onRecvGameServerLoginResult ���Ѿ���һ��������ƣ�������ƾ�");
				} else {
					System.out
							.println("onRecvGameServerLoginResult ��½��Ϸ������ʧ�� �����룺"
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
	 * ��������¼�
	 */
	private void addServiceListener(){
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_GROUP_INFO, this, "recvGroupInfo");
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_LOGIN_RESULT, this,"recvCenterLoginResult");
      
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_GAME_LOGIN_RESULT, this, "recvGameLoginResult");
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_MY_INFO, this, "recvMyInfo");
		//�����¼��Ǯ
		GameApplication.getSocketService().addEventListener(Event.ON_TEX_LOGIN_SHOW_DAY_GOLD, this, "recvLoginShowDayGold");

	}
	/**
	 * �Ƴ������¼�
	 */
	private void removeServiceListener(){
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_GROUP_INFO, this, "recvGroupInfo");
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_LOGIN_RESULT, this,"recvCenterLoginResult");
      
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_GAME_LOGIN_RESULT, this, "recvGameLoginResult");
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_MY_INFO, this, "recvMyInfo");
		//�����¼��Ǯ
		GameApplication.getSocketService().removeEventListener(Event.ON_TEX_LOGIN_SHOW_DAY_GOLD, this, "recvLoginShowDayGold");

	}
}
