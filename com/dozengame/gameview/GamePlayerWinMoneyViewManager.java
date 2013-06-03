package com.dozengame.gameview;

 
import android.util.Log;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.net.pojo.DJieSuan;
/**
 * 玩家赢钱视图管理
 * @author hewengao
 *
 */
public class GamePlayerWinMoneyViewManager {

    static final String tag ="GamePlayerWinMoneyViewManager";
    DzpkGameActivityDialog context;
	static final GamePlayerWinMoneyView [] winMoneyViews= new GamePlayerWinMoneyView[9];
	public GamePlayerWinMoneyViewManager(DzpkGameActivityDialog context) {
		 
		this.context=context;
		initWinMoneyViews();
	}
	
	private void initWinMoneyViews(){
		for(int i=0; i<9;i++){
			winMoneyViews[i]=new GamePlayerWinMoneyView(context.getContext(),i);
			context.frameLayout.addView(winMoneyViews[i]);
		}
	}
	/**
	 * 赢钱视图的可见性
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		if(winMoneyViews[index] !=null){
			winMoneyViews[index].setVisibility(visibility);
		}
	}
	/**
	 * 指定位置赢的钱
	 * @param index
	 * @param visibility
	 */
	public void addMoney(int index,int money){
		if(winMoneyViews[index] !=null){
			winMoneyViews[index].setMoney(money);
		}
	}
	 
	/**
	 * 启用动画
	 * @param index
	 */
	 public void translateAnimStart(int index,int pondCount){
		 
		// Log.i("test1", "winMoneyViews translateAnimStart: "+index);
		 if(index >=0 && index < 9 && winMoneyViews[index] !=null){
			 Integer siteNo= GameApplication.getDzpkGame().playerViewManager.getSiteNo(index);
			if(DJieSuan.m_windGold !=null){
				Integer winGold=(Integer)DJieSuan.m_windGold.get(siteNo);
				if(winGold != null){
				  winMoneyViews[index].setMoney(winGold);
				  GameApplication.getDzpkGame().gameDataManager.executeRefreshSiteGoldAction(siteNo);
				  winMoneyViews[index].translateAnimStart(pondCount);
				}else{
					 
				}
			 
			}
		 }
	 }

	public void destroy() {
		Log.i("test19", "GamePlayerWinMoneny destroy");
		 for(int i =0 ;i <9;i++){
			 if( winMoneyViews[i] != null){
			  winMoneyViews[i].destroy();
			 }
			 winMoneyViews[i]=null;
		 }
		 context =null;
	}
}
