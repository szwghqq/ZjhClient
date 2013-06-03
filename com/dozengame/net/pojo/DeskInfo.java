package com.dozengame.net.pojo;

import java.io.Serializable;

/**
 * 桌子信息
 * @author hewengao
 *
 */
public class DeskInfo implements Serializable{

	int deskno; // 桌号
	String name; // 名称
	byte desktype; // 类型:1普通，2比赛，3VIP专用
	byte fast; // 是否快速
	int betgold; // 桌面筹码数
	int usergold; // 桌子玩家筹码数
	int needlevel; // 桌子解锁等级
	int smallbet; // 小盲
	int largebet; // 大盲
	int at_least_gold; // 金币下限
	int at_most_gold; // 金币上限
	int specal_choushui; // 抽水
	byte min_playercount; // 最少开局人数
	byte max_playercount; // 最多开局人数
	byte playercount; // 当前在玩人数
	int watchercount; // 观战人数
	byte start; // 是否开始 1=是 0=开始
	byte showsitbtn;//是否显示坐下按钮
	public byte getShowsitbtn() {
		return showsitbtn;
	}
	public void setShowsitbtn(byte showsitbtn) {
		this.showsitbtn = showsitbtn;
	}
	/**
	 * 是否满
	 * @return
	 */
	public boolean isFull(){
		if(playercount == max_playercount){
			return true;
		}
		return false;
	}
	/**
	 * 是否空
	 * @return
	 */
	public boolean isEmpty(){
		if(playercount == 0){
			return true;
		}
		return false;
	}
	/**
	 * 获取当前人数
	 * @return
	 */
	public String getRenNum(){
		return playercount+"/"+max_playercount;
	}
	/**
	 * 获取大小盲
	 * @return
	 */
	public String getBet(){
		 
		return getDwGold(smallbet)+"/"+getDwGold(largebet);
	}
	/**
	 * 获取单位金币
	 * @param value
	 * @return
	 */
	private String getDwGold(int value){
		String result;
		if(value >= 1000000){
			result =value/1000000+"M";
		}else if(value >=1000){
			result =value/1000+"K";
		}else{
			result =String.valueOf(value);
		}
		return result;
		
		
	}
	/**
	 * 获取携带
	 * @return
	 */
	public String getXieDai(){
		return getDwGold(at_least_gold)+"/"+getDwGold(at_most_gold);
	}
	
	public int getDeskno() {
		return deskno;
	}
	public void setDeskno(int deskno) {
		this.deskno = deskno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte getDesktype() {
		return desktype;
	}
	public void setDesktype(byte desktype) {
		this.desktype = desktype;
	}
	public byte getFast() {
		return fast;
	}
	public void setFast(byte fast) {
		this.fast = fast;
	}
	public int getBetgold() {
		return betgold;
	}
	public void setBetgold(int betgold) {
		this.betgold = betgold;
	}
	public int getUsergold() {
		return usergold;
	}
	public void setUsergold(int usergold) {
		this.usergold = usergold;
	}
	public int getNeedlevel() {
		return needlevel;
	}
	public void setNeedlevel(int needlevel) {
		this.needlevel = needlevel;
	}
	public int getSmallbet() {
		return smallbet;
	}
	public void setSmallbet(int smallbet) {
		this.smallbet = smallbet;
	}
	public int getLargebet() {
		return largebet;
	}
	public void setLargebet(int largebet) {
		this.largebet = largebet;
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
	public int getSpecal_choushui() {
		return specal_choushui;
	}
	public void setSpecal_choushui(int specal_choushui) {
		this.specal_choushui = specal_choushui;
	}
	public byte getMin_playercount() {
		return min_playercount;
	}
	public void setMin_playercount(byte min_playercount) {
		this.min_playercount = min_playercount;
	}
	public byte getMax_playercount() {
		return max_playercount;
	}
	public void setMax_playercount(byte max_playercount) {
		this.max_playercount = max_playercount;
	}
	public byte getPlayercount() {
		return playercount;
	}
	public void setPlayercount(byte playercount) {
		this.playercount = playercount;
	}
	public int getWatchercount() {
		return watchercount;
	}
	public void setWatchercount(int watchercount) {
		this.watchercount = watchercount;
	}
	public byte getStart() {
		return start;
	}
	public void setStart(byte start) {
		this.start = start;
	}
}
