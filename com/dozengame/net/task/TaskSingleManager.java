package com.dozengame.net.task;

public class TaskSingleManager {
	private int maxTaskSize = 1;

	private ThreadPool pool = null;
	
	private static int taskSequnce = 1;
	private static  TaskSingleManager manager;
	private TaskSingleManager(){
		pool = new ThreadPool(this.maxTaskSize);
	}
	public static  TaskSingleManager getInstance(){
		if(manager ==null)
		   manager=  new TaskSingleManager();
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
	public static void setTaskSingleManagerNull(){
		manager =null;
	}
	public void cancel(){
		pool.cancelPool();
	}
}
