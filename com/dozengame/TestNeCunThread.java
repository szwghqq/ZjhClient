package com.dozengame;

import com.dozengame.util.GameUtil;

import android.content.Context;
import android.util.Log;

public class TestNeCunThread extends Thread {
	boolean run =true;
	Context context;
	public TestNeCunThread(Context context){
		this.context =context;
		this.start();
	}
	 @Override
	public void run() {
		 String temp;
		 try {
			 while(run){
				 temp=GameUtil.getNeCun(context);
				 Log.i("test8", "start: "+DzpkGameStartActivity.startNeCun+" currnet:"+temp);
				 Thread.sleep(1000*30);
			 }
		 } catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	 public void stops(){
		 run =false;
	 }
}
