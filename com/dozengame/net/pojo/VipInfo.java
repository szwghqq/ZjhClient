package com.dozengame.net.pojo;

public class VipInfo {
	
	int vipLevel;//VIP等级：1铜牌VIP，2银牌VIP，3金牌VIP
	String overTime;	//结束时间 字符串
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	 
}
