package com.dozengame.net.pojo;

import java.io.Serializable;
/**
 * ÀñÎï
 * @author hewengao
 *
 */
public class Gift implements Serializable {
	public static final String ID="gift_id";
	public static final String TAB="gift_tab";
	public static final String NAME="gift_name";
	public static final String IMGPATH="gift_imgpath";
	 
	public static final int ID_COLUMN=0;
	public static final int TAB_COLUMN=1;
	public static final int NAME_COLUMN=2;
	public static final int IMGPATH_COLUMN=3;
 
	
	public static final String TABLE_NAME="tb_gift";
	public static final String DROP_TABLE=" DROP TABLE IF EXISTS "+TABLE_NAME;
	public static final String CREATE_TABLE="create table "+TABLE_NAME+" ("+
 	                                      ID+ " INTEGER PRIMARY KEY,"+
 	                                      TAB+ " INTEGER,"+
 	                                      NAME+ " TEXT,"+
 	                                      IMGPATH+ " TEXT"+
 	                                  ")";
	int tab;
	int id;
	String name;
	String imgPath;
	public int getTab() {
		return tab;
	}
	public void setTab(int tab) {
		this.tab = tab;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
}
