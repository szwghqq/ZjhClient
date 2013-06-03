package com.dozengame.talk;

import java.util.List;

import com.dozengame.HwgCallBack;

 

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BiaoQingListAdapter<T> extends BaseAdapter {

	private List<T> list = null;
	private Context context;
	private int layoutid;
	private LayoutInflater mInflater;
	private String[] fieldName;
	private int[] fieldId;
	private int[] eventFieldId;
	private HwgCallBack[] callBack=null;
	private HwgCallBack call=null;
	public BiaoQingListAdapter(Context context, int layoutid, List<T> list,
			String[] fieldName, int[] fieldId) {
		init(context,layoutid,list,fieldName,fieldId,null,null);
	}
	public void init(Context context, int layoutid, List<T> list,
			String[] fieldName, int[] fieldId,int[] eventFieldId,HwgCallBack[] callBack){
		this.context = context;
		this.layoutid = layoutid;
		this.list = list;
		this.fieldName = fieldName;
		this.fieldId = fieldId;
		this.eventFieldId=eventFieldId;
		this.callBack=callBack;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public BiaoQingListAdapter(Context context, int layoutid, List<T> list,
			String[] fieldName, int[] fieldId,int[] eventFieldId,HwgCallBack[] callBack) {
		init(context,layoutid,list,fieldName,fieldId,eventFieldId,callBack);
		
	}
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	public Context getContext() {
		return context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public int getPosition(T item) {
		return list.indexOf(item);
	}

	@Override
	public T getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return createViewFromResource(position, convertView, parent,
				layoutid);
	}

	private View createViewFromResource(final int position, View convertView,
			ViewGroup parent, int resource) {
		View view;

		if (convertView == null) {
			view = mInflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		final T item = getItem(position);
		if (fieldId != null) {
			 int len = fieldId.length;
			// T item = getItem(position);
			 View temp = null;
			for (int i = 0; i < len; i++) {
				temp =   view.findViewById(fieldId[i]);
				try {
					System.out.println("classname: "+temp.getClass().getName());
					Object obj = item.getClass().getMethod(
							"get" + fieldName[i]).invoke(item);
					if(temp instanceof TextView){
						if (obj != null) {
						((TextView)temp).setText(obj.toString());
						}
						System.out.println("nnd  kwg kwg: "+obj.toString());
					}else if(temp instanceof ImageView){
						((ImageView)temp).setImageDrawable((Drawable)obj);
					} 
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(eventFieldId !=null){
			int len = eventFieldId.length;
			View temp = null;
			for(int i = 0; i < len; i++) {
				temp =   view.findViewById(eventFieldId[i]);
			    call= callBack[i];
				 
				if(temp instanceof Button){
				
					Button tempBtn=(Button)temp;
					tempBtn.setOnClickListener(new OnClickListener(){
						public void onClick(View v) {
						    call.CallBack(position,item);
						}
					});
				} 
			}
		}
		return view;
	}

}
