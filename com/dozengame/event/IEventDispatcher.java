package com.dozengame.event;

public interface IEventDispatcher {
	
	//使用 EventDispatcher 对象注册事件侦听器对象，以使侦听器能够接收事件通知。 IEventDispatcher 
	void addEventListener(String type, CallBack callback,String methodName, boolean useCapture,int priority,boolean useWeakReference);
	  
	void addEventListener(String type, CallBack callback,String methodName);
	
	// 将事件调度到事件流中。 IEventDispatcher    
	boolean dispatchEvent(Event event);
	
	//检查 EventDispatcher 对象是否为特定事件类型注册了任何侦听器。 IEventDispatcher 
	boolean hasEventListener(String type);
	
	 //从 EventDispatcher 对象中删除侦听器。 IEventDispatcher 
	void  removeEventListener(String type, CallBack callback,String methodName, boolean useCapture);
	
	void  removeEventListener(String type, CallBack callback,String methodName);
	
	//检查是否用此 EventDispatcher 对象或其任何始祖为指定事件类型注册了事件侦听器。
    boolean  willTrigger(String type);
	  

}
