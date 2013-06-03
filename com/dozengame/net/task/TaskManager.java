package com.dozengame.net.task;

 

public class TaskManager {
	
	private int maxTaskSize = 5;

	private ThreadPool pool = null;
	
	private static int taskSequnce = 1;
	private static  TaskManager manager;
	private TaskManager(){
		pool = new ThreadPool(this.maxTaskSize);
	}
	public static  TaskManager getInstance(){
		if(manager ==null)
		   manager=  new TaskManager();
		return manager;
	}
	
	public synchronized int execute(TaskExecutor taskExecutor){
		int ret = -1;
		if(taskExecutor!=null){
			Task task = new Task(taskExecutor);
			task.setTaskId(taskSequnce++);
			this.pool.execute(task);
			ret = task.getTaskId();
		}
		return ret;
	}
}
