package com.dozengame.gameview;

 
import java.util.List;

import android.util.Log;
import android.view.View;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.PlayerDialog;
/**
 * 背小牌
 * @author hewengao
 *
 */
public class PokeBackViewManager{

	String money="";
	PlayerDialog context;
	//int[][] pondViewPoint = {{285,188},{432,188},{579,188},{285,188},{168,270},{695,270},{280,321},{425,321},{574,321}};
	static final GamePokeBackView [] pokeBackViews= new GamePokeBackView[9];
	public PokeBackViewManager(PlayerDialog context) {
		 
		this.context=context;
		initPokeBackViews();
	}
	
	private void initPokeBackViews(){
		for(int i=0; i<9;i++){
			pokeBackViews[i]=new GamePokeBackView(context.getContext(),i);
			context.frameLayout.addView(pokeBackViews[i]);
		}
	}
	/**
	 * 设置牌的可见性
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		if(pokeBackViews[index] !=null){
		 pokeBackViews[index].setVisibility(visibility);
		}
	}
    /**
     * 重置小背牌
     */
	public void reset() {
		for(int i =0; i< 9;i++){
			pokeBackViews[i].setVisibility(View.INVISIBLE);
		}
	}
	
	//ArrayList<Integer> currnetVisible =new ArrayList<Integer>();
	/**
	 * 保存当前状态
	 */
	public void saveCurrentState() {
		//currnetVisible.clear();
		for(int i =0; i< 9;i++){
			if(pokeBackViews[i].getVisibility() == View.VISIBLE){
				//currnetVisible.add(i);
				pokeBackViews[i].setVisibility(View.INVISIBLE);
			}
		}
	}
	/**
	 *
	 * 恢复当前状态
	 *
	 */
	public void backCurrentState() {
//		int size =currnetVisible.size();
//		int index =0;
//		for(int i=0; i< size;i++){
//			index=GameApplication.getDzpkGame().playerViewManager.getPlayerIndexBak(currnetVisible.get(i));
//		    pokeBackViews[index].setVisibility(View.VISIBLE);
//		}
//		currnetVisible.clear();
		if(GameApplication.getDzpkGame().gameDataManager.faiPai){
			List list =GameApplication.getDzpkGame().gameDataManager.siteList;
			if(list != null && !list.isEmpty()){
				int size  =list.size();
				for(int i =0; i< size;i++){
					 setOtherViewVisibility(GameApplication.getDzpkGame().playerViewManager.getPlayerIndex((Byte)list.get(i)), View.VISIBLE);
				}
			}
		}
		
	}
    /**
     * 释放
     */
	public void destroy() {
		Log.i("test19", "PokeBackViewManager destroy");
		for(int i=0; i< 9;i++){
			if(pokeBackViews[i] !=null){
			pokeBackViews[i].destory();
			}
			pokeBackViews[i]=null;
		}
		context = null;
	}
}
