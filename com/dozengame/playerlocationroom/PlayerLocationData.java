package com.dozengame.playerlocationroom;

import java.io.Serializable;

import android.graphics.Bitmap;

public class PlayerLocationData implements Serializable{
	
	int nUserid;    //用户ID
	String strLatitude;      //纬度坐标x
	String strLongitude;	   //经度坐标y
	String strFaceurl;     // 头像
	String strNick;         // 昵称
	//int	nIsvip;        // 是否VIP玩家:0不是，1是
	int	nGold;          // 金币
	int	nStat;           //在线状态，0：离线，1：在线
	int	nSex;            //性别，0：女，1：男
	String strDate;    	 // 登录日期
	
	double nDistance;	//距我：
 

	/**
	 * 登录日期
	 * @return
	 */
	public String getDate() 
	{
		return strDate;
	}
	
	public void setDate(String value) 
	{
		this.strDate = value;
	}
	
	
	/**
	 * 距我：
	 * @return
	 */
	public double getDistance() 
	{
		return nDistance;
	}
	
	public void setDistance(double value) 
	{
		this.nDistance = value;
	}
	
	/**
	 * 用户ID
	 * @return
	 */
	public int getUserID() 
	{
		return nUserid;
	}
	
	public void setUserID(int value) 
	{
		this.nUserid = value;
	}
	
	/**
	 * Latitude纬度
	 * @return
	 */
	public String getLatitudeX() 
	{
		return strLatitude;
	}
	
	public void setLatitudeX(String x) 
	{
		this.strLatitude = x;
	}
	
	/**
	 * Longitude经度
	 * @return
	 */
	public String getLongitudeY() 
	{
		return strLongitude;
	}
	
	public void setLongitudeY(String y) 
	{
		this.strLongitude = y;
	}
	
	/**
	 * 头像
	 * @return
	 */
	public String getFaceurl() 
	{
		return strFaceurl;
	}
	
	public void setFaceurl(String url) 
	{
		this.strFaceurl = url;
	}
	
	/**
	 * 昵称
	 * @return
	 */
	public String getNick() 
	{
		return strNick;
	}
	
	public void setNick(String nick) 
	{
		this.strNick = nick;
	}

/*	*//**
	 * 是否VIP玩家:0不是，1是
	 * @return
	 *//*
	public int getIsvip() 
	{
		return nIsvip;
	}
	
	public void setIsvip(int value) 
	{
		this.nIsvip = value;
	}
	*/
	/**
	 * 金币
	 * @return
	 */
	public int getGold() 
	{
		return nGold;
	}
	
	public void setGold(int value) 
	{
		this.nGold = value;
	}
	
	/**
	 * 在线状态，0：离线，1：在线
	 * @return
	 */
	public int getStat() 
	{
		return nStat;
	}
	
	public void setStat(int value) 
	{
		this.nStat = value;
	}
	
	/**
	 * 性别，0：女，1：男
	 * @return
	 */
	public int getSex() 
	{
		return nSex;
	}
	
	public void setSex(int value) 
	{
		this.nSex = value;
	}
	
}
