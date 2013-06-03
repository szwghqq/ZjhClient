package com.dozengame.net.task;

import java.util.Date;

 
public final class Task implements Runnable{
	
	private TaskExecutor taskExecutor = null;
	
	private Date startTime = null;
	
	private int result = 0;
	
	private int taskId = 0;
 
	public Task(TaskExecutor taskExecutor){
		this.taskExecutor = taskExecutor;
	 
	}
	public final void run() {
	   
		startTime = new Date();
		try{
			result = taskExecutor.executeTask();
			if(result==0){
				taskExecutor.onSuccess();
			}else{
				taskExecutor.onFailure(result);
			}
		}catch(Exception e){
			result = -1;
			try{
				taskExecutor.onException(e);
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}finally{
			try{
				taskExecutor.onFinal(this);
			}catch(Exception e){
				e.printStackTrace();
			}
			startTime = null;
		}
	}
 
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public int getResult() {
		return result;
	}
	
}
