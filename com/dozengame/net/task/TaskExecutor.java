package com.dozengame.net.task;

public interface TaskExecutor {
	 
	int executeTask()throws Exception;

	void onSuccess()throws Exception;
	 
	void onFailure(int result)throws Exception;
	 
	void onException(Exception e)throws Exception;
 
	void onFinal(Task task) throws Exception;
}
