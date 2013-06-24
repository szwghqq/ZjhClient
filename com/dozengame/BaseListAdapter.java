package com.dozengame;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseListAdapter<T> extends BaseAdapter {

	private List<T> list = null;
	private Context context;
	private int layoutid;
	private LayoutInflater mInflater;
	private String[] fieldName;
	private int[] fieldId;
	private int[] eventFieldId;
	private HwgCallBack[] callBack = null;
	private HwgCallBack call = null;

	public BaseListAdapter(Context context, int layoutid, List<T> list,
			String[] fieldName, int[] fieldId) {
		init(context, layoutid, list, fieldName, fieldId, null, null,null);
	}
	public BaseListAdapter(Context context, int layoutid, List<T> list,
			String[] fieldName, int[] fieldId,HwgCallBack callback) {
		init(context, layoutid, list, fieldName, fieldId, null,null,callback);
	}
	public void init(Context context, int layoutid, List<T> list,
			String[] fieldName, int[] fieldId, int[] eventFieldId,
			HwgCallBack[] callBack,HwgCallBack call) {
		this.context = context;
		this.layoutid = layoutid;
		this.list = list;
		this.fieldName = fieldName;
		this.fieldId = fieldId;
		this.eventFieldId = eventFieldId;
		this.callBack = callBack;
		this.call =call;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public BaseListAdapter(Context context, int layoutid, List<T> list,
			String[] fieldName, int[] fieldId, int[] eventFieldId,
			HwgCallBack[] callBack) {
		init(context, layoutid, list, fieldName, fieldId, eventFieldId,
				callBack,null);
  
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
	ViewGroup currentView;
	float startx;
	float starty;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view= createViewFromResource(position, convertView, parent, layoutid);
//		ImageView iv = new ImageView(context);//针对外面传递过来的Context变量，
//        iv.setImageResource(R.drawable.logo2);      
//        iv.setScaleType(ImageView.ScaleType.FIT_XY);		
        
		view.setOnTouchListener(new OnTouchListener(){
 
			public boolean onTouch(View v, MotionEvent event) {
				 if(currentView != null && currentView != v){
					 setViewTextColor(currentView,Color.WHITE);
					 //currentView.setBackgroundResource(0);
				 }
				 currentView =(ViewGroup)v;
				 if(event.getAction() ==0){
					 startx = event.getX();
					 starty = event.getY();
					 setViewTextColor(currentView,Color.WHITE);
					// currentView.setBackgroundResource(R.drawable.hall_list);
					 
				 }else  if(event.getAction() == 1){
					 setViewTextColor(currentView,Color.WHITE);
					// currentView.setBackgroundResource(0);
					 if(call != null){
						 call.CallBack(position);
					 }
				 }else{
					 float temp = event.getX()-startx;
					 if(temp > 10  || temp < -10){
						 setViewTextColor(currentView,Color.WHITE);
						// currentView.setBackgroundResource(0);
					 }else{
						 temp = event.getY() -starty;
						 if(temp > 10  || temp < -10){
							 setViewTextColor(currentView,Color.WHITE);
							 //currentView.setBackgroundResource(0);
						 }
					 }
				 }
				return true;
			}
			
		});
		return view;
	}
	
	private void setViewTextColor(ViewGroup parentView,int color){
		int count =parentView.getChildCount();
		View tempView=null;
		for(int i =0 ; i<count;i++){
			tempView=parentView.getChildAt(i);
			if(tempView instanceof TextView){
				((TextView)tempView).setTextColor(color);
			} 
		}
		
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

			View temp = null;
			for (int i = 0; i < len; i++) {
				temp = view.findViewById(fieldId[i]);
				try {
					 
					Object obj = item.getClass()
							.getMethod("get" + fieldName[i]).invoke(item);
					if (temp instanceof TextView) {
						if (obj != null) {
							((TextView) temp).setText(obj.toString());
						}
						 
					} else if (temp instanceof ImageView) {
						((ImageView) temp).setImageDrawable((Drawable) obj);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (eventFieldId != null) {
			int len = eventFieldId.length;
			View temp = null;
			for (int i = 0; i < len; i++) {
				temp = view.findViewById(eventFieldId[i]);
				call = callBack[i];

				if (temp instanceof Button) {

					Button tempBtn = (Button) temp;
					tempBtn.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							call.CallBack(position, item);
						}
					});
				}
			}
		}
		 
		return view;
	}

}
