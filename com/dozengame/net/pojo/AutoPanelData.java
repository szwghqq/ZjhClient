package com.dozengame.net.pojo;
/**
 * 自动面板数据
 * @author hewengao
 *
 */
public class AutoPanelData {

	int pass;//：自动看牌、看牌或弃牌、跟任何、跟数字都可以过
	int giveUp;//可以放弃：看牌或弃牌
	int follow;//可以跟：跟任何、跟指定数字
	int followGold;//跟注金额
	public int getPass() {
		return pass;
	}
	public void setPass(int pass) {
		this.pass = pass;
	}
	public int getGiveUp() {
		return giveUp;
	}
	public void setGiveUp(int giveUp) {
		this.giveUp = giveUp;
	}
	public int getFollow() {
		return follow;
	}
	public void setFollow(int follow) {
		this.follow = follow;
	}
	public int getFollowGold() {
		return followGold;
	}
	public void setFollowGold(int followGold) {
		this.followGold = followGold;
	}
}
