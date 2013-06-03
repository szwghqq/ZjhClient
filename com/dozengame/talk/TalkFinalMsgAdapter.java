package com.dozengame.talk;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dozengame.HwgCallBack;
import com.dozengame.R;

public class TalkFinalMsgAdapter extends BaseAdapter {

	protected static final String TAG = "ChattingAdapter";
	 
	private List<ChatMessage> chatMessages;
	LayoutInflater mInflater;
	int resource;
	HwgCallBack callback;
	public TalkFinalMsgAdapter(Context context, List<ChatMessage> messages,int resource,HwgCallBack callback) {
		super();
		 
		this.chatMessages = messages;
		this.resource = resource;
		this.callback= callback;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return chatMessages.size();
	}

	@Override
	public Object getItem(int position) {
		return chatMessages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	View currentView;
	int pos =-1;
	float startx;
	float starty;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view;

		if (convertView == null) {
			view = mInflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		final TextView tv=(TextView)view.findViewById(R.id.finalmsg);
		ChatMessage cm =chatMessages.get(position);
		setViewBackGround(view,position);
		 view.setOnTouchListener(new OnTouchListener(){
			 
				public boolean onTouch(View v, MotionEvent event) {
					 if(currentView != null && currentView != v){
						 setViewBackGround(currentView,pos);
					 }
					 currentView =(ViewGroup)v;
					 pos= position;
					 if(event.getAction() ==0){
						 startx = event.getX();
						 starty = event.getY();
						 
						 currentView.setBackgroundResource(R.drawable.chat_preinstall_s);
						 
					 }else  if(event.getAction() == 1){
						 setViewBackGround(currentView,position);
						 if(callback != null){
							 callback.CallBack(position);
						 }
					 }else{
						 float temp = event.getX()-startx;
						 if(temp > 10  || temp < -10){
							 setViewBackGround(currentView,position);
						 }else{
							 temp = event.getY() -starty;
							 if(temp > 10  || temp < -10){
								 setViewBackGround(currentView,position);
							 }
						 }
					 }
					return true;
				}
				
			});
		tv.setText(cm.getMsg());
		return view;
	}
 
	private void setViewBackGround(View view,int position){
		  if(position <0)return;
		  if(position %2 ==0){
			 view.setBackgroundResource(R.drawable.chat_preinstall);
			}else{
			  view.setBackgroundResource(R.drawable.chat_preinstall2);
			}
	}

}
