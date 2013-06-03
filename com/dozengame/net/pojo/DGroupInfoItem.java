package com.dozengame.net.pojo;

import java.io.Serializable;
import java.util.List;

public class DGroupInfoItem implements Serializable{

	public int sourceindex;
	public int groupid;
	public String groupname;
	public int gamepeilv;
	public String ip;
	public int port;
	public int curronline;
	public int maxonline;
	public int istourroom;
	public int ishighroom;
	public int isguildroom;
	public List guild_pelv_arr;
	public int at_least_gold;
	public int at_most_gold;
	public int pay_limit;
	public int nocheat;
	public int ishuanle;

	public boolean isTestGroup = false;
	
	public int getSourceindex() {
		return sourceindex;
	}

	public void setSourceindex(int sourceindex) {
		this.sourceindex = sourceindex;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public int getGamepeilv() {
		return gamepeilv;
	}

	public void setGamepeilv(int gamepeilv) {
		this.gamepeilv = gamepeilv;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getCurronline() {
		return curronline;
	}

	public void setCurronline(int curronline) {
		this.curronline = curronline;
	}

	public int getMaxonline() {
		return maxonline;
	}

	public void setMaxonline(int maxonline) {
		this.maxonline = maxonline;
	}

	public int getIstourroom() {
		return istourroom;
	}

	public void setIstourroom(int istourroom) {
		this.istourroom = istourroom;
	}

	public int getIshighroom() {
		return ishighroom;
	}

	public void setIshighroom(int ishighroom) {
		this.ishighroom = ishighroom;
	}

	public int getIsguildroom() {
		return isguildroom;
	}

	public void setIsguildroom(int isguildroom) {
		this.isguildroom = isguildroom;
	}

	public List getGuild_pelv_arr() {
		return guild_pelv_arr;
	}

	public void setGuild_pelv_arr(List guild_pelv_arr) {
		this.guild_pelv_arr = guild_pelv_arr;
	}

	public int getAt_least_gold() {
		return at_least_gold;
	}

	public void setAt_least_gold(int at_least_gold) {
		this.at_least_gold = at_least_gold;
	}

	public int getAt_most_gold() {
		return at_most_gold;
	}

	public void setAt_most_gold(int at_most_gold) {
		this.at_most_gold = at_most_gold;
	}

	public int getPay_limit() {
		return pay_limit;
	}

	public void setPay_limit(int pay_limit) {
		this.pay_limit = pay_limit;
	}

	public int getNocheat() {
		return nocheat;
	}

	public void setNocheat(int nocheat) {
		this.nocheat = nocheat;
	}

	public int getIshuanle() {
		return ishuanle;
	}

	public void setIshuanle(int ishuanle) {
		this.ishuanle = ishuanle;
	}

	public boolean isTestGroup() {
		return isTestGroup;
	}

	public void setTestGroup(boolean isTestGroup) {
		this.isTestGroup = isTestGroup;
	}

	public String toString(){
		
		System.out.println("sourceindex: "+sourceindex);
		System.out.println("groupid: "+groupid);
		System.out.println("groupname: "+groupname);
		System.out.println("gamepeilv: "+gamepeilv);
		System.out.println("ip: "+ip);
		System.out.println("port: "+port);
	 
		System.out.println("curronline: "+curronline);
		System.out.println("maxonline: "+maxonline);
		
		System.out.println("istourroom: "+istourroom);
		System.out.println("ishighroom: "+ishighroom);
		System.out.println("isguildroom: "+isguildroom);
		
		
		
		System.out.println("guild_pelv_arr: "+guild_pelv_arr);
		System.out.println("at_least_gold: "+at_least_gold);
		System.out.println("at_most_gold: "+at_most_gold);
		
		System.out.println("pay_limit: "+pay_limit);
		System.out.println("nocheat: "+nocheat);
		System.out.println("ishuanle: "+ishuanle);
		return "";
	}
}
