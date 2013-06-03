package com.dozengame.talk;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dozengame.GameApplication;
import com.dozengame.HwgCallBack;
import com.dozengame.R;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameUtil;

public class TalkDialog extends Dialog implements HwgCallBack{

	ListView msgList;
	ListView msgFinalList;
	ImageView btnBack;
	ImageView btnSend;
	 Bitmap hall_button1;
	 Bitmap hall_button2;
	 Bitmap hall_buttons;
	 Bitmap talk_bg;
	 int biaoQingW = 120;
	 int biaoQingH = 117;
	 Bitmap biaoQing[] = new Bitmap[20];
	 GridView biaoQingView=null;
	 EditText etMsg;
	 /**
	 * @param context
	 * @param theme
	 */
	public TalkDialog(Context context, int theme) {
		super(context, theme);
		setContentView(R.layout.talkmsg);
		
		initBitmap();
		initView();
		initData();
		 final BitmapDrawable drawable =new BitmapDrawable(talk_bg);
		 this.getWindow().setBackgroundDrawable(drawable);
		this.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				GameApplication.getDzpkGame().gameBottomView.msgDialogIsOpen=false;
				 destory();
			}
		});
	}
	private void destory(){
		GameUtil.recycle(talk_bg);
		GameUtil.recycle(hall_button1);
		GameUtil.recycle(hall_button2);
		GameUtil.recycle(hall_buttons);
		for(int i = 0;i <  20;i++){
			GameUtil.recycle(biaoQing[i]);
		}
	}
	 private void initBitmap(){
    	try {
    		talk_bg = BitmapFactory.decodeStream(this.getContext().getAssets().open("talk_bg.png"));
    		hall_button1 = BitmapFactory.decodeStream(this.getContext().getAssets().open("chat_but01.png"));
    		hall_button2 = BitmapFactory.decodeStream(this.getContext().getAssets().open("chat_but02.png"));
    		hall_buttons = BitmapFactory.decodeStream(this.getContext().getAssets().open("chat_but_s.png"));
    		Bitmap temp = null;
    		for(int i = 1;i <= 20;i++){
    			if(i<10){
    			    temp=BitmapFactory.decodeStream(this.getContext().getAssets().open("biaoqing/emot0"+i+".png"));
    			}else{
    				temp=BitmapFactory.decodeStream(this.getContext().getAssets().open("biaoqing/emot"+i+".png"));
    			}
//    			if(i == 13){
//    				 biaoQing[19]=temp;
//    			}
//    			else if(i ==20){
//    				 biaoQing[12]=temp;
//    			}else{
    			 biaoQing[i-1]=temp;
    			//}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
     }
	private void initView(){
		etMsg =(EditText)findViewById(R.id.msg);
		msgList=(ListView)findViewById(R.id.msglist);
		msgFinalList=(ListView)findViewById(R.id.msg_final_list);
		
		btnBack= (ImageView)findViewById(R.id.btn_back);
		btnSend =(ImageView)findViewById(R.id.btn_send);
		btnBack.setBackgroundDrawable(new BitmapDrawable(hall_button2));
		btnSend.setBackgroundDrawable(new BitmapDrawable(hall_button1));
		OnTouchListener listener = new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
				ImageView iv = (ImageView)v;
			    if(action == 0){
			    	iv.setImageBitmap(hall_buttons);
			    	return true;
			    }else if(action ==1){
			    	if(iv == btnSend){
 			    	 iv.setImageBitmap(hall_button1);
 			    	 sendDeskChat(etMsg.getText().toString(),1);
 			    	 etMsg.setText("");
 			    	  
			    	}else if(iv == btnBack){
			    	 iv.setImageBitmap(hall_button2);
			    	 dismiss();
			    	}
			    	return true;
			    }
				return false;
			}
    		
    	};
    	btnSend.setOnTouchListener(listener);
    	btnBack.setOnTouchListener(listener);
    	TextView tv= (TextView)findViewById(R.id.msg_tishi);
    	tv.setText("每个表情将消耗"+GameApplication.getDzpkGame().deskInfo.getSmallbet()+"个筹码");
	}
	List<ChatMessage> list=null;
	private void initData(){
		 
	     list = new ArrayList<ChatMessage>();
		 ChatMessage cm = new ChatMessage();
		 cm.setMsg("大家好,我是来打酱油的!");
		 list.add(cm);
		 
		 cm = new ChatMessage();
		 cm.setMsg("我在手机里玩，说话没那么快呐！");
		 list.add(cm);
		 
		 cm = new ChatMessage();
		 cm.setMsg("敢跟我单挑吗？");
		 list.add(cm);
		 
		 cm = new ChatMessage();
		 cm.setMsg("那谁，你能不能快点儿？ ");
		 list.add(cm);
		 
		 cm = new ChatMessage();
		 cm.setMsg("见过慢的，没见过这么慢的。  ");
		 list.add(cm);
		 
		 cm = new ChatMessage();
		 cm.setMsg("冷静，一定要冷静！  ");
		 list.add(cm);
		 cm = new ChatMessage();
		 cm.setMsg("冲动是魔鬼。");
		 list.add(cm);
		 
		 cm = new ChatMessage();
		 cm.setMsg("拜托，你就跟了吧。 ");
		 list.add(cm);
		 
		 cm = new ChatMessage();
		 cm.setMsg("你敢ALL IN吗？  ");
		 list.add(cm);
		 
		 cm = new ChatMessage();
		 cm.setMsg("此时不SO，更待何时？  ");
		 list.add(cm);
		 
		 cm = new ChatMessage();
		 cm.setMsg("让看看牌再加钱好不？  ");
		 list.add(cm);
		 
		 cm = new ChatMessage();
		 cm.setMsg("辛辛苦苦几十年，一把回到解放前。 ");
		 list.add(cm);
			
		 cm = new ChatMessage();
		 cm.setMsg("我不是党员，别T我~  ");
		 list.add(cm);
			
		 cm = new ChatMessage();
		 cm.setMsg("一万几千，过眼云烟~ ");
		 list.add(cm);
		 
		 cm = new ChatMessage();
		 cm.setMsg("凭你那技术，打败我是个不可能任务。");
		 list.add(cm);
			
			
			
		 TalkFinalMsgAdapter tfmAdapter = new TalkFinalMsgAdapter(this.getContext(), list, R.layout.finalmsg,this);
		 msgFinalList.setAdapter(tfmAdapter);
		 
		 tfmAdapter.notifyDataSetChanged();
		 
		 initGrid();
		 initTalkData();
	}
	 TalkMsgAdapter tfmAdapter=null;
	 List<ChatMessage> lists=new ArrayList<ChatMessage>();
	private void initTalkData(){
		try{
		
		
		 tfmAdapter = new TalkMsgAdapter(this.getContext(), lists, R.layout.talkmessage);
		 msgList.setAdapter(tfmAdapter);
		// lists.clear();
		// lists.addAll(GameApplication.getDzpkGame().talkChatViewManager.chatMessages); 
		// tfmAdapter.notifyDataSetChanged();
		 setChatMessages(GameApplication.getDzpkGame().talkChatViewManager.chatMessages);
		// msgList.setSelectionFromTop(lists.size()-1, 0);
		}catch(Exception e){
			
		}
 
	}
	public void setChatMessages(List<ChatMessage> messages){
		 lists.clear();
		 lists.addAll(messages);
		 tfmAdapter.notifyDataSetChanged();
	}
	private void initGrid() {
		List<BiaoQingDto> list = new ArrayList<BiaoQingDto>();
		for(int i =1;i <= 20;i++){
			BiaoQingDto temp= new BiaoQingDto();
			temp.setId(1000+i);
			temp.setIconDraw(new BitmapDrawable(biaoQing[i-1]));
			list.add(temp);
			
		}
		biaoQingView=(GridView)findViewById(R.id.msg_biaoqin_view);
		final BiaoQingListAdapter<BiaoQingDto> adapter = new BiaoQingListAdapter<BiaoQingDto>(this.getContext(),
				R.layout.grid_item, list, // 列表内容
				new String[] {"IconDraw" }, new int[] {R.id.item_image });
		biaoQingView.setAdapter(adapter);
		biaoQingView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				BiaoQingDto biaoQing = adapter.getItem(position);
				sendBiaoQing(biaoQing.getId());
			}
			
		});
		 

	}
	/**
	 * 发送表情
	 * @param biaoQingId
	 */
	private void sendBiaoQing(final int biaoQingId){
		//只有当自己坐下时才能发表情
		if(GameApplication.getDzpkGame().playerViewManager.mySite){
			TaskManager.getInstance().execute(new TaskExecutorAdapter() {
				public int executeTask() throws Exception {
					GameApplication.getSocketService().sendPlayEmot(biaoQingId);
					return 0;
				}
			});
		}else{
			GameApplication.getDzpkGame().talkChatViewManager.startAnim(9, GameUtil.NOBIAOQING);
		}
		dismiss();
	}
	/**
	 * 发送文字
	 * @param biaoQingId
	 */
	private void sendDeskChat(final String chat,int state){
		if(chat ==null || chat.length()==0)return;
		//只有当自己坐下或自己是VIP时可以发送文字
	  ArrayList list =(ArrayList)GameApplication.userInfo.get("vip");
	  if((list != null && !list.isEmpty()) || GameApplication.getDzpkGame().playerViewManager.mySite){
		TaskManager.getInstance().execute(new TaskExecutorAdapter() {
			public int executeTask() throws Exception {
				GameApplication.getSocketService().sendDeskChat(chat);
				return 0;
			}
		});
	  }else{
		  GameApplication.getDzpkGame().talkChatViewManager.startAnim(9, GameUtil.NOTALK);
		  if(state ==1){
		    dismiss();
		  }
	  }
		
	}
	@Override
	public void CallBack(Object... obj) {
	  if(obj != null && obj.length ==1){
		  Integer pos=(Integer)obj[0];
		  if(list != null){
			  ChatMessage cm=(ChatMessage)list.get(pos);
			  sendDeskChat(cm.getMsg(),0);
			  dismiss();
		  }
	  }
	}
}
