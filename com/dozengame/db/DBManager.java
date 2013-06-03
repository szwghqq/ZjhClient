package com.dozengame.db;

import java.util.ArrayList;

import com.dozengame.net.pojo.Gift;
import com.dozengame.net.pojo.PlayerNetPhoto;

 


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
 

/**
 * 数据库管理
 * 
 * @author admin
 * 
 */
public class DBManager {

	//static SQLiteDatabase mySQLiteDatabase;
	static MyDbHelper helper=null;
	public static final String dataBaseName="dozengame.db";
	static final int dataBaseVersion=1;
	 
	public static void init(Context context) {
		helper = new MyDbHelper(context, dataBaseName, null, dataBaseVersion);
	}
	private static SQLiteDatabase getSQLiteDatabase(){
		 return  helper.getWritableDatabase();
	}
	
	public static int executeSql(String sql) throws Exception {
		int res = -1;
		try {
			SQLiteDatabase	mySQLiteDatabase=getSQLiteDatabase();
			mySQLiteDatabase.execSQL(sql);
			close(mySQLiteDatabase);
			res = 0;
		} catch (Exception e) {
			res = -1;
			throw e;
		}
		return res;
	}

	public static int update(String tableName, ContentValues values,
			String whereClause, String[] whereArgs) throws Exception {
		int res = -1;
		try {
			SQLiteDatabase	mySQLiteDatabase=getSQLiteDatabase();
			res = mySQLiteDatabase.update(tableName, values, whereClause,whereArgs);
			close(mySQLiteDatabase);
			//System.out.println("update res:"+res);
		} catch (Exception e) {
			res = -1;
			throw e;
		}
		return res;
	}

	public static long insert(String tableName, String nullColumnHack,
			ContentValues values) throws Exception {
		long res = -1;
		try {
			SQLiteDatabase	mySQLiteDatabase=getSQLiteDatabase();
			res = mySQLiteDatabase.insert(tableName, nullColumnHack, values);
			close(mySQLiteDatabase);
			///System.out.println("insert res:"+res);
			//Log.d(DBManager.class,"");
		} catch (Exception e) {
			res = -1;
			throw e;
		}
		return res;
	}

	public static int delete(String tableName, String whereClause,
			String[] whereArgs) throws Exception {
		int res = -1;
		try {
			SQLiteDatabase	mySQLiteDatabase=getSQLiteDatabase();
			res = mySQLiteDatabase.delete(tableName, whereClause, whereArgs);
			close(mySQLiteDatabase);
			///System.out.println("delete res:"+res);
		} catch (Exception e) {
			res = -1;
			throw e;
		}
		return res;
	}
 
	private static void close(SQLiteDatabase db){
		 if(db !=null)
			 db.close();
		 db=null;
	}
	/**
	 * 寻找礼物对象
	 * @param table
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	public static Gift findGift(String table,String selection, String[] selectionArgs){
		 Gift gift=null;
		 SQLiteDatabase	mySQLiteDatabase=getSQLiteDatabase();
		 
		 Cursor cur= mySQLiteDatabase.query(table, null,selection, selectionArgs,null,null,null);
		 if(cur !=null){
	       	  if(cur.moveToNext()){
	       		   gift =new Gift();
	       		   gift.setId(cur.getInt(Gift.ID_COLUMN));
	       		   gift.setName(cur.getString(Gift.NAME_COLUMN));
	       		   gift.setImgPath(cur.getString(Gift.IMGPATH_COLUMN));
	       		   gift.setTab(cur.getInt(Gift.TAB_COLUMN));
	   	      }
	       	  cur.close();   
		 }
		 close(mySQLiteDatabase);
		 return gift;
	}
	/**
	 * 玩家形象对象
	 * @param table
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	public static PlayerNetPhoto findPlayerNetPhoto(String table,String selection, String[] selectionArgs){
		PlayerNetPhoto photo=null;
		 SQLiteDatabase	mySQLiteDatabase=getSQLiteDatabase();
		 
		 Cursor cur= mySQLiteDatabase.query(table, null,selection, selectionArgs,null,null,null);
		 if(cur !=null){
	       	  if(cur.moveToNext()){
	       		photo =new PlayerNetPhoto();
	       		photo.setId(cur.getInt(PlayerNetPhoto.ID_COLUMN));
	       		photo.setHttpUrl(cur.getString(PlayerNetPhoto.HTTP_URL_COLUMN));
	       		photo.setState(cur.getInt(PlayerNetPhoto.STATE_COLUMN));
	       		photo.setPhotoBytes(cur.getBlob(PlayerNetPhoto.PHOTO_BYTES_COLUMN));
	   	      }
	       	  cur.close();   
		 }
		 close(mySQLiteDatabase);
		 return photo;
	}
	public static void clear(){
		helper =null;
	}
	private static class MyDbHelper extends SQLiteOpenHelper {
		public MyDbHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				Log.i(DBManager.class.getName()," data base is create");
				//DBManager.executeSql(ContactModel.createTable);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

}
