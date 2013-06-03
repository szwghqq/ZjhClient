package com.dozengame.event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
/**
 * 事件流发送器
 * @author hewengao
 *
 */
public class EventDispatcher implements IEventDispatcher {

	HashMap<String,ArrayList<EventCallBack>> map=new HashMap<String,ArrayList<EventCallBack>>();
	@Override
	public void addEventListener(String type, CallBack callback,String methodName,
			boolean useCapture, int priority, boolean useWeakReference) {
		EventCallBack eventCallBack=new EventCallBack(callback,methodName,useCapture,priority,useWeakReference);
		add(type,eventCallBack);
	}
	@Override
	public void addEventListener(String type, CallBack callback,String methodName) {
		addEventListener(type,callback,methodName,false,0,false);
	}
	private void add(String type,EventCallBack eventCallBack){
		    ArrayList<EventCallBack> list= map.get(type);
			if(list==null){
				list =new ArrayList<EventCallBack>();
				map.put(type, list);
			}
			synchronized(list){
			 list.add(eventCallBack);
		    }
	}

	@Override
	public boolean dispatchEvent(Event event) {
	   if(event !=null && event.getEventName()!=null){
		   ArrayList<EventCallBack> list= map.get(event.getEventName());
		   if(list ==null)return false;
		   synchronized(list){
			   if(!list.isEmpty()){
				   int size=list.size();
//				   if(size >1){
//				      //排序
//					 java.util.Collections.sort(list,
//		                     new Comparator<EventCallBack>(){
//		
//								@Override
//								public int compare(EventCallBack b1, EventCallBack b2) {
//								    if(b1.getPriority()>b2.priority){
//								    	return 1;
//								    }else if(b1.getPriority()==b2.priority){
//								    	return 0;
//								    }else{
//								    	return -1;
//								    }
//								}
//						 
//					 });
//				   }
				 EventCallBack callback=null;
				// size =list.size();
				 for(int i=0;i<size;i++){
					 callback=list.get(i);
					 callback.execute(event);
				 }
				 return true;
			   }
		   }
	   }
		return false;
	}
   
	@Override
	public boolean hasEventListener(String type) {
		 ArrayList<EventCallBack> list=map.get(type);
		 if(list !=null && !list.isEmpty()){
			 return true;
		 }
		return false;
	}

	@Override
	public void removeEventListener(String type, CallBack callback,String methodName,
			boolean useCapture) {
		ArrayList<EventCallBack> list=map.get(type);
		if(list ==null)return;
		synchronized(list){
			 if(!list.isEmpty()){
				 int size=list.size();
				 for(int i=0;i<size;i++){
					 EventCallBack ecb= list.get(i);
					 if(ecb.equalCallback(callback,methodName)){
						list.remove(i);
						break;
					}
				 }
			 }
		}
	}
	
	@Override
	public void removeEventListener(String type, CallBack callback,String methodName) {
		removeEventListener(type,callback,methodName,false);
	}
	@Override
	public boolean willTrigger(String type) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private class EventCallBack{
		
		private CallBack callBack;
		private boolean useCapture;
		private int priority;
		private boolean useWeakReference;
		private String methodName;//调用的方法名
		public EventCallBack(CallBack callback,String methodName){
			 init(callback,methodName,false,0,false);
		}
		
		public EventCallBack(CallBack callback,String methodName,boolean useCapture, int priority, boolean useWeakReference){
			 init(callback,methodName,useCapture,priority,useWeakReference);
		}
		/**
		 * 初始化
		 * @param callback
		 * @param useCapture
		 * @param priority
		 * @param useWeakReference
		 */
		private void init(CallBack callback,String methodName,boolean useCapture, int priority, boolean useWeakReference){
			this.callBack=callback;
			this.useCapture=useCapture;
			this.priority=priority;
			this.useWeakReference=useWeakReference;
			this.methodName=methodName;
		}
		 
		public CallBack getCallBack() {
			return callBack;
		}
		public void setCallBack(CallBack callBack) {
			this.callBack = callBack;
		}
		public boolean isUseCapture() {
			return useCapture;
		}
		public void setUseCapture(boolean useCapture) {
			this.useCapture = useCapture;
		}
		public int getPriority() {
			return priority;
		}
		public void setPriority(int priority) {
			this.priority = priority;
		}
		public boolean isUseWeakReference() {
			return useWeakReference;
		}
		public void setUseWeakReference(boolean useWeakReference) {
			this.useWeakReference = useWeakReference;
		}
		public void execute(Event event){
			if(callBack !=null){
				try {
					callBack.getClass().getMethod(methodName,event.getClass()).invoke(callBack, event);
				} catch (Exception e) {
					e.printStackTrace();
				}  
			}
		}
		public boolean equalCallback(CallBack callback,String mehtodName){
  
			if(mehtodName !=null && mehtodName.equals(this.methodName)){
			   return this.callBack.getClass().getName().equals(callback.getClass().getName());
			}
			return false;
		}
	}

	

}
