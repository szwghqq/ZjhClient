package com.dozengame.gameview;

 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

 
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
 
/**
 * 玩家下注信息
 * @author hewengao
 *
 */
public class GamePlayerChouMaViewManager{

	DzpkGameActivityDialog context;
	//下注筹码到主池筹码动画
	private static final int[][][] translateAnimPoint = {
		 {{2,-155},{242,-155},{242,-113},{242,-40},{242,-5},{-258,-5},{-258,-40},{-258,-113},{-258,-155}},
		 {{-135,-155},{105,-155},{105,-113},{105,-40},{105,-5},{-395,-5},{-395,-40},{-395,-113},{-395,-155}},
		 {{139,-155},{379,-155},{379,-113},{379,-40},{379,-5},{-121,-5},{-121,-40},{-121,-113},{-121,-155}},
		 
		 {{-250,-80},{-10,-80},{-10,-38},{-10,35},{-10,70},{-510,70},{-510,35},{-510,-38},{-510,-80}},
		
		 {{250,-80},{490,-80},{490,-38},{490,35},{490,70},{-10,70},{-10,35},{-10,-38},{-10,-80}},
		 {{-135,-31},{105,-31},{105,11},{105,84},{105,119},{-395,119},{-395,84},{-395,11},{-395,-31}},
		 {{2,-31},{242,-31},{242,11},{242,84},{242,119},{-258,119},{-258,84},{-258,11},{-258,-31}},
		 {{139,-31},{379,-31},{379,11},{379,84},{379,119},{-121,119},{-121,84},{-121,11},{-121,-31}}
		};
	
	static final GamePlayerChouMaView [] playerChouMaViews= new GamePlayerChouMaView[9];
 
    
  //用于控制下注筹码是否都已执行完毕
   public final static HashMap<Integer,Boolean> animListIndex = new  HashMap<Integer,Boolean>();
    //当前主池索引
   public static int currentMainIndex =0;
    
	public GamePlayerChouMaViewManager(DzpkGameActivityDialog context) {
		 
		this.context=context;
		initPlayerChouMaViews();
	}
	
	private void initPlayerChouMaViews(){
		for(int i=0; i<9;i++){
			playerChouMaViews[i]=new GamePlayerChouMaView(context.getContext(),i);
			context.frameLayout.addView(playerChouMaViews[i]);
		}
	}
	
	/**
	 * 设置可见性
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		playerChouMaViews[index].setVisibility(visibility);
	}
	/**
	 * 设置下注筹码
	 * @param index
	 * @param money
	 */
	public void setPlayerXiaZhuChouMa(HashMap data){
		 
		int siteNo = (Byte) data.get("siteno");// 座位号
		int betGold = (Integer) data.get("betgold");// 总的下注额
		int currbet = (Integer) data.get("currbet");// 本轮下注额
		byte sex = (Byte) data.get("sex"); // 1=男 0=女
		byte type = (Byte) data.get("type");// 1=下注 2=加注 3=底注 4=梭哈 5=跟注
		int index=GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
		playerChouMaViews[index].setMoney(currbet);
		GameApplication.getDzpkGame().playerChouMaViewManager.setOtherViewVisibility(index, View.VISIBLE);		
		//HashMap player =(HashMap)GameApplication.getDzpkGame().gameDataManager.sitDownUsers.get(siteNo);
		//int handgold =(Integer)player.get("handgold");
		//Log.i("test4", "handgold: "+handgold+" currbet:  "+currbet+"  type: "+type);
		if(type ==4){
	    	//ALLIn动画
	    	GameApplication.getDzpkGame().allInViewManager.startAnim(index);
	    }
	}
  
   /**
    * 执行下注筹码动画
    */
    public   void execXiaZhuChouMaAnim(){
    	animListIndex.clear();
    	for(int i=0; i<9;i++){
    		if(playerChouMaViews[i].getVisibility() == View.VISIBLE){
    			animListIndex.put(i, false);
    		    translateAnimXiaZhu(playerChouMaViews[i],i);
    		} 
    	}
    	//表示没有动画需要执行
    	if(animListIndex.size()==0){
    		//判断游戏是否已结束
    		if(GameApplication.getDzpkGame().gameDataManager.GameOver){
    			GameApplication.getDzpkGame().gameDataManager.GameOver =false;
				GameApplication.getDzpkGame().otherPokeViewManager.draw();
			}
    	}
    }
    /**
     * 座位下注筹码到桌面筹码视图动画
     * @param cmvStart
     * @param index
     * @param currGold
     */
    private   void translateAnimXiaZhu(final GamePlayerChouMaView xiaZhuView,final int index){
		PondView pondView =GameApplication.getDzpkGame().pondViewManager.getPondView(0);
    	int x=translateAnimPoint[currentMainIndex][index][0];
		int y=translateAnimPoint[currentMainIndex][index][1];
		TranslateAnimation mTranslateAnimation =   new TranslateAnimation(0, x, 0, y);
		//设置时间持续时间为500 毫秒=0.5秒
		mTranslateAnimation.setDuration(500);
		mTranslateAnimation.setInterpolator(new LinearInterpolator());//均匀
		mTranslateAnimation.setAnimationListener(new MyAnimationListener(xiaZhuView,pondView,true,index));
		mTranslateAnimation.setStartOffset(100);
		xiaZhuView.startAnimation(mTranslateAnimation);	
    }
    
    /**
     * 动画侦听事件
     * @author hewengao
     *
     */
    private class MyAnimationListener implements AnimationListener{
    	 GamePlayerChouMaView view1;//起始视图
    	 PondView view2;//到达视图
    	boolean isXiaZhu=false;//true:表示下注动画 false:起始动画
    	int index =-1;//某个座位的索引
    	public MyAnimationListener(GamePlayerChouMaView view1,PondView view2,boolean isXiaZhu,int index){
    		this.view1=view1;
    		this.view2=view2;
    		this.isXiaZhu=isXiaZhu;
    		this.index=index;
    	}
    	 
		@Override
		public void onAnimationEnd(Animation animation) {
			view1.setVisibility(View.INVISIBLE);
			animListIndex.put(index, true);
			if(validAllAnimIsEnd()){
				//接收桌面彩池信息
				executeDeskPollInfo();
				//判断游戏是否已结束
				if(GameApplication.getDzpkGame().gameDataManager.GameOver){
					GameApplication.getDzpkGame().otherPokeViewManager.draw();
				}
			}
		 
		}
 
		public void onAnimationRepeat(Animation animation) {
			 
		}
 
		public void onAnimationStart(Animation animation) {
			 
		}
    	
    }
    /**
     * 所有动画是否已执行完毕
     * @return
     */
    public boolean validAllAnimIsEnd(){
    	Set<Entry<Integer, Boolean>>  set=animListIndex.entrySet();
    	Iterator<Entry<Integer, Boolean>>  it=set.iterator();
    	while(it.hasNext()){
    		if(it.next().getValue() == false){
    		     return false;
    		}
    	}
    	animListIndex.clear();
    	return true;
    }
    //桌面彩池信息
    private   ArrayList  deskPolls;
    /**
     * 收到彩池信息
     * @param data
     */
    public  void recvDeskPollInfo(ArrayList  data){
    	Log.i("test18","收到彩池信息  recvDeskPollInfo");
    	deskPolls=data;
    	//所有下注动画都已执行完毕
    	//if(validAllAnimIsEnd()){
    	//	executeDeskPollInfo();
    	//}
    }
    /**
     * 处理彩池信息
     */
   private  void   executeDeskPollInfo(){
		if(deskPolls !=null){
			ArrayList polls = new ArrayList();
			polls.addAll(deskPolls);
			deskPolls.clear();
			int size = polls.size();
			PondView pondView=null;
		   
			for (int i = 0; i < size; i++) {
			 
			  pondView=GameApplication.getDzpkGame().pondViewManager.getPondView(i);
			 // pondView.setVisibility(View.VISIBLE);
			  pondView.setMoney((Integer)polls.get(i));
			 
			}
			polls.clear();
            currentMainIndex = size-1;
		}
   }

	public void setChouMaByIndex(int index, int currbet) {
		
		 playerChouMaViews[index].setMoney(currbet);
		// if(currbet >0){
			// playerChouMaViews[index].setVisibility(View.VISIBLE);
		// }
	}


	ArrayList<Integer> currnetVisible =new ArrayList<Integer>();
	/**
	 * 保存当前状态
	 */
	public void saveCurrentState() {
		currnetVisible.clear();
		for(int i =0; i< 9;i++){
			if(playerChouMaViews[i].getVisibility() == View.VISIBLE){
				currnetVisible.add(i);
				playerChouMaViews[i].setVisibility(View.INVISIBLE);
			}
		}
	}
	/**
	 *
	 * 恢复当前状态
	 *
	 */
	public void backCurrentState() {
		int size =currnetVisible.size();
		int index =0;
		int money =0;
		for(int i=0; i< size;i++){
			index=currnetVisible.get(i);
			money=playerChouMaViews[index].getMoney();
			index=GameApplication.getDzpkGame().playerViewManager.getPlayerIndexBak(currnetVisible.get(i));
		    playerChouMaViews[index].setMoney(money);
		    playerChouMaViews[index].setVisibility(View.VISIBLE);
		}
		currnetVisible.clear();
	}
	public void destory(){
		Log.i("test19", "GamePlayerChouMaView destroy");
		for(int i=0; i< 9;i++){
			if(playerChouMaViews[i] != null){
			playerChouMaViews[i].destory();
			}
			playerChouMaViews[i]=null;
		}
		if(deskPolls != null){
			deskPolls.clear();
		}
		deskPolls =null;
		context =null; 
	}
	public void reset(){
		for(int i=0; i< 9;i++){
			playerChouMaViews[i].setVisibility(View.INVISIBLE);
			//playerChouMaViews[i]=null;
		}
	}
}
