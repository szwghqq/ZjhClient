package com.dozengame.net.pojo;

import java.io.Serializable;

import android.R;
import android.widget.ImageView;

/**
 * ������Ϣ
 * @author hewengao
 *
 */
public class DeskInfo implements Serializable{

	int deskno; // ����
	String name; // ����
	byte desktype; // ����:1��ͨ��2������3VIPר��
	byte fast; // �Ƿ����
	int betgold; // ���������
	int usergold; // ������ҳ�����
	int needlevel; // ���ӽ����ȼ�
	int smallbet; // Сä
	int largebet; // ��ä
	int at_least_gold; // �������
	int at_most_gold; // �������
	int specal_choushui; // ��ˮ
	byte min_playercount; // ���ٿ�������
	byte max_playercount; // ��࿪������
	byte playercount; // ��ǰ��������
	int watchercount; // ��ս����
	byte start; // �Ƿ�ʼ 1=�� 0=��ʼ
	byte showsitbtn;//�Ƿ���ʾ���°�ť
	ImageView img; //��һ����ʾ��ͼƬ
	
	public ImageView getImg() {
		return img;
	}
	public void setImg(ImageView img) {
		this.img = img;
	}
	public byte getShowsitbtn() {
		return showsitbtn;
	}
	public void setShowsitbtn(byte showsitbtn) {
		this.showsitbtn = showsitbtn;
	}
	/**
	 * �Ƿ���
	 * @return
	 */
	public boolean isFull(){
		if(playercount == max_playercount){
			return true;
		}
		return false;
	}
	/**
	 * �Ƿ��
	 * @return
	 */
	public boolean isEmpty(){
		if(playercount == 0){
			return true;
		}
		return false;
	}
	/**
	 * ��ȡ��ǰ����
	 * @return
	 */
	public String getRenNum(){
		return playercount+"/"+max_playercount;
	}
	/**
	 * ��ȡ��Сä
	 * @return
	 */
	public String getBet(){
		 
		return getDwGold(smallbet)+"/"+getDwGold(largebet);
	}
	/**
	 * ��ȡ��λ���
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
	 * ��ȡЯ��
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
