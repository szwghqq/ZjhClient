package com.dozengame.net.task;
import java.util.LinkedList;

import android.util.Log;

public class ThreadPool extends ThreadGroup {  
    private boolean isClosed = false;  
    private LinkedList<Runnable> workQueue;       
    private static int threadPoolID = 1;  
    public ThreadPool(int poolSize) {   
  
        super(threadPoolID + "");      
        setDaemon(true);               
        workQueue = new LinkedList<Runnable>();  
        for(int i = 0; i < poolSize; i++) {  
            new WorkThread(i).start();   
        }  
    }  
      
   
    /** 向工作队列中加入一个新任务,由工作线程去执行该任务*/  
    public synchronized void execute(Runnable task) {  
        if(isClosed) {  
            throw new IllegalStateException();  
        }  
        if(task != null) {  
            workQueue.add(task);//向队列中加入一个任务  
            notify();           //唤醒一个正在getTask()方法中待任务的工作线程  
        }  
    }  
    private synchronized Runnable getTask(int threadid) throws InterruptedException {  
        while(workQueue.size() == 0) {  
            if(isClosed) return null;  
           
            wait();             
        }  
       
        return (Runnable) workQueue.removeFirst();  
    }  
    public synchronized void clearQueue(){
    	workQueue.clear();
    }
    public synchronized void cancelPool(){
    	isClosed = true;  
    	 
    	workQueue.clear();
    }
    public synchronized void closePool() {  
        if(! isClosed) {  
            waitFinish();        
            isClosed = true;  
            workQueue.clear();   
            interrupt();        
        }  
    }  
    /** 等待工作线程把所有任务执行完毕*/  
    public void waitFinish() {  
        synchronized (this) {  
            isClosed = true;  
            notifyAll();            //唤醒所有还在getTask()方法中等待任务的工作线程  
        }  
        Thread[] threads = new Thread[activeCount()]; //activeCount() 返回该线程组中活动线程的估计值。  
        int count = enumerate(threads); //enumerate()方法继承自ThreadGroup类，根据活动线程的估计值获得线程组中当前所有活动的工作线程  
        for(int i =0; i < count; i++) { //等待所有工作线程结束  
            try {  
                threads[i].join();  //等待工作线程结束  
            }catch(InterruptedException ex) {  
                ex.printStackTrace();  
            }  
        }  
    }  
  
    private class WorkThread extends Thread {  
        private int id;  
        public WorkThread(int id) {  
         
            super(ThreadPool.this,id+"");  
            this.id =id;  
        }  
        public void run() {  
            while(! isInterrupted()) {   
                Runnable task = null;  
                try {  
                    task = getTask(id);      
                }catch(InterruptedException ex) {  
                    ex.printStackTrace();  
                }  
                
                if(task == null) return;  
                  
                try {  
                    task.run();   
                }catch(Throwable t) {  
                    t.printStackTrace();  
                }  
            }//  end while  
        }//  end run  
    }// end workThread  
} 
