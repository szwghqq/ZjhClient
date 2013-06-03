package com.dozengame.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import com.dozengame.GameApplication;
import com.dozengame.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * 游戏图片资源
 * 
 * @author hewengao
 * 
 */
public class GameBitMap {

	public final static String GAME_VIP1 = "game_vip1.png";
	public final static String GAME_VIP2 = "game_vip2.png";
	public final static String GAME_VIP3 = "game_vip3.png";
	public final static String GAME_VIP4 = "game_vip4.png";
	public final static String GAME_VIP5 = "game_vip5.png";
	public final static String GAME_PHOTO_FRAME = "game_photo_frame.png";
	public final static String GAME_PHOTO_FRAME_SELF = "game_photo_frame_self.png";
	public final static String GAME_LIWU_BG = "game_liwu_bg.png";
	public final static String GAME_POND_BG = "game_pondbg.png";
	public final static String GAME_POKE_BACK = "game_poke_back.png";
//	public final static String GAME_JSQ_GREEN = "GAME_JSQ_GREEN";
//	public final static String GAME_JSQ_YELLOW = "GAME_JSQ_YELLOW";
//	public final static String GAME_JSQ_RED = "GAME_JSQ_RED";
	public final static String GAME_ZJD = "game_zjd.png";
	
	public final static String GAME_POKETYPE_BG0 = "game_poketype_bg0.png";
	public final static String GAME_POKETYPE_BG1 = "game_poketype_bg1.png";
	public final static String GAME_POKETYPE_BG2 = "game_poketype_bg2.png";
	public final static String GAME_POKETYPE_BG3 = "game_poketype_bg3.png";
	public final static String GAME_POKETYPE_BG4 = "game_poketype_bg4.png";
	public final static String GAME_POKETYPE_BG5 = "game_poketype_bg5.png";
	public final static String GAME_POKETYPE_BG6 = "game_poketype_bg6.png";
	public final static String GAME_POKETYPE_BG7 = "game_poketype_bg7.png";
	public final static String GAME_POKETYPE_BG8 = "game_poketype_bg8.png";
	public final static String GAME_POKETYPE_BG9 = "game_poketype_bg9.png";
	
	
	public final static String GAME_BGNUMBER = "game_bgnumber.png";
	
	public final static String GAME_ALLIN_LEFT = "game_allin_left.png";
	public final static String GAME_ALLIN_RIGHT = "game_allin_right.png";
	
//	public final static String GAME_TALK_LEFT1 = "talk/game_pop_l01.png";
//	public final static String GAME_TALK_LEFT2 = "talk/game_pop_l02.png";
 	public final static String GAME_TALK_LEFT3 = "talk/game_pop_l03.png";
//	public final static String GAME_TALK_LEFT4 = "talk/game_pop_l04.png";
	
//	public final static String GAME_TALK_RIGHT1 = "talk/game_pop_r01.png";
//	public final static String GAME_TALK_RIGHT2 = "talk/game_pop_r02.png";
	public final static String GAME_TALK_RIGHT3 = "talk/game_pop_r03.png";
//	public final static String GAME_TALK_RIGHT4 = "talk/game_pop_r04.png";
	
	public final static String GAME_TALK_PANGGUANG = "talk/game_pop_l05.png";
	
	
	
	
	public final static String GAME_CHOUMA_BG = "game_chouma_bg01.png";
	
	public final static String GAME_CHOUMA10 = "game_chouma_10.png";
	public final static String GAME_CHOUMA100 = "game_chouma_100.png";
	public final static String GAME_CHOUMA1k = "game_chouma_1k.png";
	public final static String GAME_CHOUMA1w = "game_chouma_1w.png";
	public final static String GAME_CHOUMA10w = "game_chouma_10w.png";
	public final static String GAME_CHOUMA100w = "game_chouma_100w.png";
	public final static String GAME_CHOUMA1000w = "game_chouma_1000w.png";
	public final static String GAME_CHOUMA1y = "game_chouma_1y.png";
	public final static String GAME_CHOUMA10y = "game_chouma_10y.png";
	public final static String GAME_CHOUMA100y = "game_chouma_100y.png";
	 
	public final static String GAME_SIGNAL3 = "game_signal3.png";
	public final static String GAME_SIGNAL2 = "game_signal2.png";
	public final static String GAME_SIGNAL1 = "game_signal1.png";
	
	public final static String HALL_STAND = "hall_stand.png";
	public final static String HALL_BACK = "hall_back.png";
	public final static String HALL_BUTTON = "hall_button.png";
	public final static String HALL_BUTTONS = "hall_buttons.png";
	
	public final static String MYWINBG = "mywin_bg.png";
	public final static String MYWINTEXT = "mywin_text.png";
	public final static String ADDMONEYBG = "add_money_bg.png";
	public final static String ALLIN = "all_in.png";
	public final static String HALL_SITDOWN = "hall_sitdown.png";
	public final static String HALL_SITDOWNS = "hall_sitdowns.png";
	public final static String MAIN_DESK1 = "main_desk1.jpg";
	public final static String MAIN_DESK2 = "main_desk2.jpg";
	public final static String MAIN_DESK3 = "main_desk3.jpg";
	public final static String DIALOG_ALPHA = "dialog_alpha.png";
	 
//	public static Bitmap gamePokeBg = null;
//	public static Bitmap myWinBg = null;
//	public static Bitmap myWinText = null;
//	public static Bitmap addMoneyBg = null;
//	public static Bitmap HALL_SITDOWN = null;
//	public static Bitmap HALL_SITDOWNS = null;
//	public static Bitmap allIn = null;
//	private static HashMap<Integer,Bitmap> pokeMap = new HashMap<Integer,Bitmap>();
	/**
	 * 获取游戏图片资源
	 * 
	 * @param name
	 * @return
	 */
	public static Bitmap getGameBitMap(String name) {
		 
		return getGameBitMapByName(GameApplication.currentActivity, name);
	}

	public static Bitmap getGameBitMapByName(Context context, String name) {

		try {
			InputStream is=context.getAssets().open(name);
			Bitmap temp =BitmapFactory.decodeStream(is);
			 try{
			     is.close();
			 }catch(Exception e){
				 e.printStackTrace();
			 }finally{
				 is =null;
			 }
//			if (name.equals(GAME_VIP1)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_vip1.png"));
//			} else if (name.equals(GAME_VIP2)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_vip2.png"));
//			} else if (name.equals(GAME_VIP3)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_vip3.png"));
//			} else if (name.equals(GAME_VIP4)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_vip4.png"));
//			} else if (name.equals(GAME_VIP5)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_vip5.png"));
//			} else if (name.equals(GAME_PHOTO_FRAME)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_photo_frame.png"));
//			} else if (name.equals(GAME_PHOTO_FRAME_SELF)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_photo_frame_self.png"));
//			} else if (name.equals(GAME_LIWU_BG)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_liwu_bg.png"));
//			} else if (name.equals(GAME_POND_BG)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_pondbg.png"));
//			} else if (name.equals(GAME_POKE_BACK)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_poke_back.png"));
//			} else if (name.equals(GAME_JSQ_GREEN)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_jsq_green.png"));
//			} else if (name.equals(GAME_JSQ_RED)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_jsq_red.png"));
//			} else if (name.equals(GAME_JSQ_YELLOW)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_jsq_yellow.png"));
//			} else if (name.equals(GAME_ZJD)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_zjd.png"));
//			} else if (name.equals(GAME_POKETYPE_BG)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_poketype_bg.png"));
//			} else if (name.equals(GAME_CHOUMA_BG)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_chouma_bg.png"));
//			} else if (name.equals(GAME_CHOUMA1)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_chouma1.png"));
//			} else if (name.equals(GAME_CHOUMA10)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_chouma10.png"));
//			} else if (name.equals(GAME_CHOUMA100)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_chouma100.png"));
//			} else if (name.equals(GAME_CHOUMAK)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_choumak.png"));
//			} else if (name.equals(GAME_CHOUMAW)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_choumaw.png"));
//			} else if (name.equals(GAME_CHOUMAWW)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"game_choumaww.png"));
//			} else if (name.equals(HALL_BACK)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"hall_back.png"));
//			} else if (name.equals(HALL_STAND)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"hall_stand.png"));
//			} else if (name.equals(HALL_BUTTON)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"hall_button.png"));
//			} else if (name.equals(HALL_BUTTONS)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//						"hall_buttons.png"));
//			}else if (name.equals(MYWINBG)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//				"mywin_bg.png"));
//	     }else if (name.equals(MYWINTEXT)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//				"mywin_text.png"));
//	     }else if (name.equals(ADDMONEYBG)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//				"add_money_bg.png"));
//	     }else if (name.equals(ALLIN)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//				"all_in.png"));
//	     }else if (name.equals(HALL_SITDOWN)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//				"hall_sitdown.png"));
//	     }else if (name.equals(HALL_SITDOWNS)) {
//				temp = BitmapFactory.decodeStream(context.getAssets().open(
//				"hall_sitdowns.png"));
//	     }
 
		 return temp;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据路径名称获取图片
	 * @param context
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapByName(Context context, String path){
		Bitmap result=null;
		try {
			result = BitmapFactory.decodeStream(context.getAssets().open(path));
		} catch (IOException e) {
			Log.i("test17","face path:"+path);
			e.printStackTrace();
		}
		return result;
	}
	 
	/**
	 * 初始化所有的牌
	 * 
	 * @param imgPath
	 *            源图
	 * @param wcnt
	 *            每一小图片宽
	 * @param hcnt
	 *            每一小图片高
	 * @param len
	 *            图片数量
	 * @return
	 * @throws Exception
	 */
	public static void initPokes(Context context, String imgPath, int wcnt,
			int hcnt) {

		try {
			Bitmap source = BitmapFactory.decodeStream(context.getAssets()
					.open(imgPath));
			int w1 = source.getWidth();
			int h1 = source.getHeight();
			w1 = w1 / wcnt;// 宽个数
			h1 = h1 / hcnt;// 高的个数
			int x = 0;
			int y = 0;
			int k = -1;
			Bitmap temp = null;
			for (int i = 0; i < h1; i++) {
				y = i * hcnt;
				for (int j = 0; j < w1; j++) {
					k = i * w1 + j;
					if (k == 53) {
						return;
					}
					x = j * wcnt;
					temp = source.createBitmap(source, x, y, wcnt, hcnt);
					//pokeMap.put(k, temp);
				}
			}
			GameUtil.recycle(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据牌的序号获取牌
	 * @param context
	 * @param imgPath
	 * @param wcnt
	 * @param hcnt
	 * @param pokeId
	 * @return
	 */
	private static Bitmap getBitmapByPokeNum(Context context, String imgPath, int wcnt,
			int hcnt,int pokeId) {

		try {
			if(pokeId<0 || pokeId>52)return null;
			Bitmap source = BitmapFactory.decodeStream(context.getAssets()
					.open(imgPath));
			//int w1 = source.getWidth();
			//int h1 = source.getHeight();
			//w1 = w1 / wcnt;// 宽个数
			//h1 = h1 / hcnt;// 高的个数
			final int w1 =13;
			//int h1 =5;
			int x = (pokeId%w1)*wcnt;
			int y = (pokeId/w1)*hcnt;
			Bitmap temp =source.createBitmap(source, x, y, wcnt, hcnt);
			 
			GameUtil.recycle(source);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void initAllMap(Context context) {

//		initPokes(context, "game_poke.png", 70, 100);
//		try {
//
////			myWinBg = BitmapFactory.decodeStream(context.getAssets().open(
////					"mywin_bg.png"));
////			myWinText = BitmapFactory.decodeStream(context.getAssets().open(
////					"mywin_text.png"));
////			addMoneyBg = BitmapFactory.decodeStream(context.getAssets().open(
////					"add_money_bg.png"));
////			allIn = BitmapFactory.decodeStream(context.getAssets().open(
////					"all_in.png"));
////			HALL_SITDOWN = BitmapFactory.decodeStream(context.getAssets().open(
////					"hall_sitdown.png"));
////			HALL_SITDOWNS = BitmapFactory.decodeStream(context.getAssets()
////					.open("hall_sitdowns.png"));
//		} catch (Exception e) {
//
//		}
//		// initGameBitMap(context);
	}

	/**
	 * 获取牌的图片
	 * 
	 * @param id
	 * @return
	 */
	public static Bitmap getPokeBitMap(Integer pokeid) {
		//return pokeMap.get(pokeid);
		return getBitmapByPokeNum(GameApplication.currentActivity,"game_poke.png", 70, 100,pokeid);
	}
    /**
     * 清空内容
     */
	public static void clear(){
//		Set<Entry<Integer, Bitmap>>  entry=pokeMap.entrySet();
//		Iterator<Entry<Integer,Bitmap>> it =entry.iterator();
//		while(it.hasNext()){
//			 GameUtil.recycle(it.next().getValue());
//		}
//		pokeMap.clear();
	}
	public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {

		// load the origial Bitmap
		Bitmap BitmapOrg = bitmap;

		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;

		// calculate the scale
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the Bitmap
		matrix.postScale(scaleWidth, scaleHeight);
		// if you want to rotate the Bitmap
		// matrix.postRotate(45);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);
		// make a Drawable from Bitmap to allow to set the Bitmap
		// to the ImageView, ImageButton or what ever
		return resizedBitmap;

	}

	public static BitmapDrawable resizeBitmapToDrawable(Bitmap bitmap, int w, int h) {

		// load the origial Bitmap
		Bitmap BitmapOrg = bitmap;

		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;

		// calculate the scale
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the Bitmap
		matrix.postScale(scaleWidth, scaleHeight);
		// if you want to rotate the Bitmap
		// matrix.postRotate(45);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);

		// make a Drawable from Bitmap to allow to set the Bitmap
		// to the ImageView, ImageButton or what ever
		return new BitmapDrawable(resizedBitmap);

	}
    /**
     * 调整图片的Alpha值
     * @param bitmap
     * @return
     */
	public static Bitmap getRgbAlphaBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int r, g, b, color,a;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// 获得Bitmap 图片中每一个点的color颜色值
				color = bitmap.getPixel(j, i);
				
				a =Color.alpha(color);
				if(a >128){
					r = Color.red(color);
					g = Color.green(color);
					b = Color.blue(color);
				    color = Color.argb(128, r, g, b);
				    bitmap.setPixel(j, i, color);
				}
			}
		}
		return bitmap;
		
	}
//	 /**
//     * 调整图片的Alpha值
//     * @param bitmap
//     * @return
//     */
//	public static Bitmap getRgbAlphaBitmaps(Bitmap bitmap) {
//		int width = bitmap.getWidth();
//		int height = bitmap.getHeight();
//		int r, g, b, color,a;
//		for (int i = 0; i < height; i++) {
//			for (int j = 0; j < width; j++) {
//				// 获得Bitmap 图片中每一个点的color颜色值
//				color = bitmap.getPixel(j, i);
//				
//				a =Color.alpha(color);
//				if(a >128){
//					r = Color.red(color);
//					g = Color.green(color);
//					b = Color.blue(color);
//				    color = Color.argb(128, r, g, b);
//				    bitmap.setPixel(j, i, color);
//				}
//			}
//		}
//		return bitmap;
//		
//	}
	 /**
     * 调整图片的Alpha值
     * @param bitmap
     * @return
     */
	public static Bitmap getAlphaBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int r, g, b, color;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// 获得Bitmap 图片中每一个点的color颜色值
				color = bitmap.getPixel(j, i);
				r = Color.red(color);
				g = Color.green(color);
				b = Color.blue(color);
				color = Color.argb(0, r, g, b);
				bitmap.setPixel(j, i, color);
			}
		}
		return bitmap;
		
	}
	 /**
     * 调整图片的Rgb值
     * @param bitmap
     * @return
     */
	public static Bitmap getRgbBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int a, r, g, b, color;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// 获得Bitmap 图片中每一个点的color颜色值
				color = bitmap.getPixel(j, i);
				r = Color.red(color);
				g = Color.green(color);
				b = Color.blue(color);
				a = Color.alpha(color);
				r -= 60;
				r = getLarge0Value(r);
				g -= 60;
				g = getLarge0Value(g);
				b -= 60;
				b = getLarge0Value(b);
				color = Color.argb(a, r, g, b);
				bitmap.setPixel(j, i, color);
			}
		}
		return bitmap;
	}

	private static int getLarge0Value(int v) {
		if (v < 0)
			v = 0;
		return v;
	}

	/**
	 * 图片倒影
	 * 
	 * @param originalImage
	 * @return
	 */
	public static Bitmap createReflectedImage(Bitmap originalImage) {
		// The gap we want between the reflection and the original image
		final int reflectionGap = 4;

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		// This will not scale but will flip on the Y axis
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		// Create a Bitmap with the flip matrix applied to it.
		// We only want the bottom half of the image
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);

		// Create a new bitmap with same width but taller to fit reflection
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		// Create a new Canvas with the bitmap that's big enough for
		// the image plus gap plus reflection
		Canvas canvas = new Canvas(bitmapWithReflection);
		// Draw in the original image
		canvas.drawBitmap(originalImage, 0, 0, null);
		// Draw in the gap
		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
		// Draw in the reflection
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		// Create a shader that is a linear gradient that covers the reflection
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		// Set the paint to use this shader (linear gradient)
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

}
