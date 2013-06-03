package com.dozengame.talk;

import java.util.List;

import com.dozengame.R;

 
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TalkMsgAdapter extends BaseAdapter {

	protected static final String TAG = "TalkMsgAdapter";
	 
    int lan =Color.rgb(102, 204, 255);
    int huang =Color.rgb(255,153,0);
	private List<ChatMessage> chatMessages;
	LayoutInflater mInflater;
	int resource;
	public TalkMsgAdapter(Context context, List<ChatMessage> messages,int resource) {
		super();
		 
		this.chatMessages = messages;
		this.resource = resource;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		if (convertView == null) {
			view = mInflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		TextView tv=(TextView)view.findViewById(R.id.finalmsg);
		//TextView player=(TextView)view.findViewById(R.id.player);
		ChatMessage cm =chatMessages.get(position);
		tv.setText(cm.toString());
		if(cm.isSelf){
			setViewTextColor(tv,Color.WHITE);
			//setViewTextColor(player,huangse);
			//player.setText("Œ“£∫");
		}else{
			//player.setText(cm.getTalkNick()+"£∫");
			//setViewTextColor(player,huangse1);
			if(position % 2 ==0){
				setViewTextColor(tv,lan);
			}else{
				setViewTextColor(tv,huang);
			}
		}
		return view;
	}
 
	private void setViewTextColor(TextView tempView,int color){
		tempView.setTextColor(color);
 
	}
}
