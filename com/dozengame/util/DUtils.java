package com.dozengame.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Properties;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

 

public class DUtils {
	private final static String tag = "com.hwg.util.GreenUtils";
	private static int cachesize = 1024;
	private static String filedir = "data/data/com.dozengame/";
	private static String tempdir = "tempFile";
	private static String downdir = "downFile";
	private static Deflater compresser = new Deflater();
	private static Inflater decompresser = new Inflater();

	/**
	 * 根据文件名得到文件长度
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(String filename) throws Exception {
		filename = filename.substring(filename.lastIndexOf("/") + 1);
		filename = filedir + tempdir + File.separator + filename;
		File f = new File(filename);
		if (f.exists()) {
			return f.length();
		} else {
			return 0;
		}

	}

	/**
	 * 根据文件名得到文件字节数组
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static byte[] getFileBytes(String filename) throws Exception {
		filename = filename.substring(filename.lastIndexOf("/") + 1);
		filename = filedir + tempdir + File.separator + filename;
		byte[] bt = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
			bt = new byte[fis.available()];
			fis.read(bt);
			return bt;
		} catch (Exception e) {
			throw e;
		} finally {
			if (fis != null)
				fis.close();
			fis = null;
		}

	}

	/**
	 * 反序列化字节数组
	 * 
	 * @param b
	 * @return
	 * @throws IOException
	 */
	public static Object parseByteToObject(byte b[]) throws Exception {
		if (b == null)
			return null;
		ByteArrayInputStream is = null;
		ObjectInputStream ois = null;
		try {
			is = new ByteArrayInputStream(b);
			ois = new ObjectInputStream(is);
			return ois.readObject();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (is != null)
					is.close();
				is = null;
			} catch (Exception e) {
				throw e;
			}
			try {
				if (ois != null)
					ois.close();
				ois = null;
			} catch (Exception e) {
				throw e;
			}

		}
	}

	/**
	 * 反序列化文件
	 * 
	 * @param b
	 * @return
	 * @throws IOException
	 */
	public static Object parseByteToObject(String fileName) throws Exception {

		FileInputStream fis = null;
		ByteArrayInputStream is = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(fileName);
			byte[] by = new byte[fis.available()];
			fis.read(by);
			is = new ByteArrayInputStream(by);
			ois = new ObjectInputStream(is);
			return ois.readObject();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (fis != null)
					fis.close();
				fis = null;
			} catch (Exception e) {
				throw e;
			}
			try {
				if (is != null)
					is.close();
				is = null;
			} catch (Exception e) {
				throw e;
			}
			try {
				if (ois != null)
					ois.close();
				ois = null;
			} catch (Exception e) {
				throw e;
			}

		}
	}

	/**
	 * 序列化对象到文件并获取字节数组
	 * 
	 * @param filename
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static byte[] SerializeBytesToFile(String filename, Object obj)
			throws Exception {

		FileOutputStream fos = null;
		ByteArrayOutputStream os = null;
		ObjectOutputStream oos = null;
		byte b[] = null;
		try {
			os = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(os);
			oos.writeObject(obj);
			oos.close();
			b = os.toByteArray();
			fos = new FileOutputStream(filename);
			fos.write(os.toByteArray());
		} catch (IOException e) {

			throw e;
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			os = null;
			if (oos != null)
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			oos = null;
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			fos = null;
		}
		return b;
	}

	/**
	 * 序列化对象的字节数组
	 * 
	 * @param filename
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static byte[] getSerializeBytesFromObj(Object obj) {

		ByteArrayOutputStream os = null;
		ObjectOutputStream oos = null;
		byte b[] = null;
		try {
			os = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(os);
			oos.writeObject(obj);
			oos.close();
			b = os.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			os = null;
			if (oos != null)
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			oos = null;
		}
		return b;
	}

	/**
	 * 文件反序列化
	 * 
	 * @param filename
	 * @return
	 */
	public static Properties FileToProperties(File file) throws Exception {

		ByteArrayInputStream is = null;
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(file);
			byte[] by = new byte[fis.available()];

			fis.read(by);

			is = new ByteArrayInputStream(by);
			ois = new ObjectInputStream(is);
			prop = (Properties) ois.readObject();
			return prop;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (ois != null) {
					ois.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * 解析流，转换成文档对象模型
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(InputStream is) throws Exception {

		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbfactory.newDocumentBuilder();
		return db.parse(is);

	}

	/**
	 * 压缩字节数组
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] compressBytes(byte input[]) {
		compresser.reset();
		compresser.setInput(input);
		compresser.finish();
		byte output[] = new byte[0];
		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
		try {
			byte[] buf = new byte[cachesize];
			int got;
			while (!compresser.finished()) {
				got = compresser.deflate(buf);
				o.write(buf, 0, got);
			}
			output = o.toByteArray();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	/**
	 * 解压缩字节数组
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] decompressBytes(byte input[]) {
		byte output[] = new byte[0];
		decompresser.reset();
		decompresser.setInput(input);
		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
		try {
			byte[] buf = new byte[cachesize];

			int got;
			while (!decompresser.finished()) {
				got = decompresser.inflate(buf);
				o.write(buf, 0, got);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	/**
	 * 初始化文件保存路径
	 */
	public static void initDataDir() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			filedir = sdDir.getPath() + "/";
		} else {
			filedir = "data/data/com.dozengame/";
		}
		// filedir = "data/data/com.hwg/";
		Log.d(tag, "fileDir: " + filedir);
		File fs = createDir(filedir + tempdir);
		Log.d("hwg", "fileDir: " + fs.getPath());
		String filename ="dzgamelog.txt";
		File file=new File(fs.getPath() + File.separator + filename);
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void wirteToLogFile(String msg){
		String fileName="dzgamelog.txt";
//		try {
//			if(msg !=null){
//			 msg+="\r\n";
//			 long len=getFileSize(fileName);
//			 saveTempFile(fileName,len,msg.getBytes());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	/**
	 * 将字节数组保存至文件
	 * 
	 * @param path
	 * @param bt
	 * @throws Exception
	 */
	public static void saveTempFile(String path, long skip, byte[] bt)
			throws Exception {
		path = path.substring(path.lastIndexOf("/") + 1);
		// File fs = Environment.getExternalStorageDirectory();
		// fs = new File(fs.getPath() + File.separator + "files");
		File fs = createDir(filedir + tempdir);
		fs = new File(fs.getPath() + File.separator + path);
		if (!fs.exists())
			fs.createNewFile();
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(fs, "rw");
			raf.seek(skip);
			raf.write(bt, 0, bt.length);
			Log.i("file", fs.getPath() + File.separator + path);
		} catch (Exception ex) {
			Log.e("tag", "error: " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (raf != null)
				try {
					raf.close();
				} catch (IOException e) {
					throw e;
				}
			raf = null;
		}
	}

	/**
	 * 更改文件名
	 * 
	 * @param fromFileName
	 * @param toFileName
	 * @throws Exception
	 */
	public static void renameFile(String fromFileName, String toFileName)
			throws Exception {
		fromFileName = fromFileName
				.substring(fromFileName.lastIndexOf("/") + 1);
		File fs = createDir(filedir + tempdir);
		fs = new File(fs.getPath() + File.separator + fromFileName);
		File fsdir = createDir(filedir + downdir);
		toFileName = toFileName.substring(toFileName.lastIndexOf("/") + 1);
		fsdir = new File(fsdir.getPath() + File.separator + toFileName);
		fs.renameTo(fsdir);
		Log.d("file", "rename " + fsdir.getPath());
	}

	private static File createDir(String fileName) {
		File fs = new File(fileName);
		if (fs.exists() && fs.isFile()) {
			fs.delete();
			fs.mkdir();
		} else {
			fs.mkdir();
		}
		return fs;
	}

	/**
	 * 将字节数组保存至文件
	 * 
	 * @param path
	 * @param bt
	 * @throws Exception
	 */
	public static void saveFile(String path, byte[] bt) throws Exception {
		path = path.substring(path.lastIndexOf("/") + 1);
		// File fs = Environment.getExternalStorageDirectory();
		// fs = new File(fs.getPath() + File.separator + "files");
		File fs = createDir(filedir + tempdir);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fs.getPath() + File.separator + path);
			fos.write(bt, 0, bt.length);
			fos.flush();
			Log.i("file", fs.getPath() + File.separator + path);
		} catch (Exception ex) {
			Log.e("tag", "error: " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					throw e;
				}
			fos = null;
		}

	}

	public static void installApk(Context context, String path)
			throws Exception {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// intent.setAction(android.content.Intent.ACTION_VIEW);

		path = path.substring(path.lastIndexOf("/") + 1);
		// Uri uri=Uri.parse("file://"+filedir+downdir+File.separator+path);
		File f = new File(filedir + downdir + File.separator + path);
		Uri uri = Uri.fromFile(f);
		if (f.isFile()) {
			System.out.println(path + " is f file!");
		} else {
			System.out.println(path + " is f not file!");
		}
		System.out.println("uri: " + uri.getPath());

		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	public static void deleteApk(Context context, String appPackage)
			throws Exception {
		Intent intent = new Intent(Intent.ACTION_DELETE);
		intent.setData(Uri.parse("package:" + appPackage));
		// intent.setDataAndType(
		// Uri.parse("package:"+appPackage),
		// "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 提示窗口
	 * 
	 * @param str
	 * @param context
	 */
	public static void prompt(String str, Context context) {

		Dialog alert = new AlertDialog.Builder(context).setTitle("提示")
				.setMessage(str).setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								dialog.cancel();
							}
						}).create();
		alert.show();

	}

	/**
	 * 提示选择窗口
	 * 
	 * @param str
	 * @param context
	 */
	public static void Confirm(String str, Context context) {

	}

	/**
	 * Android每帧的数据流的格式是YUV420 将 YUV420转成RGB的函数
	 * @param rgbBuf
	 * @param yuv420sp
	 * @param width
	 * @param height
	 */
	public static  void decodeYUV420SP(byte[] rgbBuf, byte[] yuv420sp,
			int width, int height) {
		final int frameSize = width * height;
		if (rgbBuf == null)
			throw new NullPointerException("buffer 'rgbBuf' is null");
		if (rgbBuf.length < frameSize * 3)
			throw new IllegalArgumentException("buffer 'rgbBuf' size "
					+ rgbBuf.length + " < minimum " + frameSize * 3);

		if (yuv420sp == null)
			throw new NullPointerException("buffer 'yuv420sp' is null");

		if (yuv420sp.length < frameSize * 3 / 2)
			throw new IllegalArgumentException("buffer 'yuv420sp' size "
					+ yuv420sp.length + " < minimum " + frameSize * 3 / 2);

		int i = 0, y = 0;
		int uvp = 0, u = 0, v = 0;
		int y1192 = 0, r = 0, g = 0, b = 0;

		for (int j = 0, yp = 0; j < height; j++) {
			uvp = frameSize + (j >> 1) * width;
			u = 0;
			v = 0;
			for (i = 0; i < width; i++, yp++) {
				y = (0xff & ((int) yuv420sp[yp])) - 16;
				if (y < 0)
					y = 0;
				if ((i & 1) == 0) {
					v = (0xff & yuv420sp[uvp++]) - 128;
					u = (0xff & yuv420sp[uvp++]) - 128;
				}

				y1192 = 1192 * y;
				r = (y1192 + 1634 * v);
				g = (y1192 - 833 * v - 400 * u);
				b = (y1192 + 2066 * u);

				if (r < 0)
					r = 0;
				else if (r > 262143)
					r = 262143;
				if (g < 0)
					g = 0;
				else if (g > 262143)
					g = 262143;
				if (b < 0)
					b = 0;
				else if (b > 262143)
					b = 262143;

				rgbBuf[yp * 3] = (byte) (r >> 10);
				rgbBuf[yp * 3 + 1] = (byte) (g >> 10);
				rgbBuf[yp * 3 + 2] = (byte) (b >> 10);
			}
		}
	}
 
}
