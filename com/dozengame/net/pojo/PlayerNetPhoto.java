package com.dozengame.net.pojo;

import java.io.Serializable;
/**
 * 玩家形象图片
 * @author Administrator
 *
 */
public class PlayerNetPhoto implements Serializable {
	
	public static final String ID="player_id";
	public static final String HTTP_URL="player_http_url";
	public static final String STATE="state";
	public static final String PHOTO_BYTES="photo_bytes";
	 
	public static final int ID_COLUMN=0;
	public static final int HTTP_URL_COLUMN=1;
	public static final int STATE_COLUMN=2;
	public static final int PHOTO_BYTES_COLUMN=3;
 
	
	public static final String TABLE_NAME="tb_player_photo";
	public static final String DROP_TABLE=" DROP TABLE IF EXISTS "+TABLE_NAME;
	public static final String CREATE_TABLE="create table if not exists "+TABLE_NAME+" ("+
 	                                      ID+ " INTEGER PRIMARY KEY,"+
 	                                      HTTP_URL+ " TEXT,"+
 	                                      STATE+ " INTEGER,"+
 	                                      PHOTO_BYTES+ " byte[]"+
 	                                  ")";
	
	int id;             //标识 玩家userid
	String httpUrl;     //http下载地址
	int state;          //状态
	byte[] photoBytes;  //形象字节数组
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHttpUrl() {
		return httpUrl;
	}
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public byte[] getPhotoBytes() {
		return photoBytes;
	}
	public void setPhotoBytes(byte[] photoBytes) {
		this.photoBytes = photoBytes;
	}
}
