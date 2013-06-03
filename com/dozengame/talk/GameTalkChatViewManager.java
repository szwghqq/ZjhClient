package com.dozengame.talk;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.util.GameUtil;

/**
 * 游戏聊天管理
 * @author hewengao
 *
 */
public class GameTalkChatViewManager {
	
	DzpkGameActivityDialog context;
	static final GameTalkChatView [] talkChatViews= new GameTalkChatView[10];
	public GameTalkChatViewManager(DzpkGameActivityDialog context) {
		 
		this.context=context;
		initViews();
	}
	
	private void initViews(){
		for(int i=0; i<10;i++){
			talkChatViews[i]=new GameTalkChatView(context.getContext(),i);
			context.frameLayout.addView(talkChatViews[i]);
		}
	}
	/**
	 * 视图的可见性
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		if(talkChatViews[index] !=null){
			talkChatViews[index].setVisibility(visibility);
		}
	}
	 
	/**
	 * 启用动画
	 * @param index
	 */
	 public void startAnim(int index,String chatText){
		 if(talkChatViews[index] !=null){
			 talkChatViews[index].startAnim(chatText);
		 }
	 }
	   /**
		 * 释放资源
		 */
		public void destroy(){
			Log.i("test19", "GameTalkChatView destroy");
			for(int i=0; i<9;i++){
				if(talkChatViews[i] != null){
					talkChatViews[i].destroy();
					talkChatViews[i]=null;
				}
			}
			chatMessages.clear();
		}
		
	public List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
    /**
     * 执行聊天
     * @param obj
     */
	public void executeChat(HashMap data) {
		ChatMessage cm = new ChatMessage();
		String msg =(String)data.get("msg");
		String nick =(String)data.get("name_from");
		cm.setMsg(msg);
		cm.setSelf(false);
		if(GameApplication.userInfo != null){
			Object obj1=GameApplication.userInfo.get("user_real_id");
			Object obj2=data.get("id_from");
			if(obj1.equals(obj2)){
				cm.setSelf(true);
			}
		}
		cm.setTalkNick(nick+"("+data.get("id_from")+")");
		chatMessages.add(cm);
		if(GameApplication.talkDialog != null && GameApplication.talkDialog.isShowing()){
			GameApplication.talkDialog.setChatMessages(chatMessages);
		}
		Byte siteNo = (Byte)data.get("site_from");
	 
		if(siteNo == 0){
                try {
					nick=GameUtil.splitIt(nick, 8);
					nick+="：";
					nick += msg;
					msg=GameUtil.splitIt(nick, 34);
					//旁观聊天
					startAnim(9,msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else{
			try {
				msg=GameUtil.splitIt(msg, 20);
				int index = GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
				//玩家聊天
				startAnim(index,msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		data.get("type");
//		data.get("msg");
//		data.get("id_from");
//		data.get("name_from");
//		data.get("site_from");
	}

	 
}
