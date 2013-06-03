package com.dozengame.net.task;

public class TaskSingleManager2 {
	private int maxTaskSize = 1;

	private ThreadPool pool = null;
	
	private static int taskSequnce = 1;
	 
	public TaskSingleManager2(){
		pool = new ThreadPool(this.maxTaskSize);
	}
	public synchronized int execute(TaskExecutor taskExecutor){
		int ret = -1;
		if(taskExecutor!=null){
			Task task = new Task(taskExecutor);
			task.setTaskId(taskSequnce++);
			pool.clearQueue();//保持队列中只有一个待处理
			this.pool.execute(task);
			ret = task.getTaskId();
		}
		return ret;
	}
	 
	public void cancel(){
		pool.cancelPool();
	}
}
