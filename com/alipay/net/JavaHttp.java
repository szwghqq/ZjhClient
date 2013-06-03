package com.alipay.net;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import android.util.Log;
import com.dozengame.HwgCallBack;
 

/**
 * java.net.* 处理联网相关的
 * 
 * @author hwg
 */
public class JavaHttp {

	 
	String url="http://127.0.0.1:8085/zfb/servlet/RSATrade";
	String content;
	private static int timeoutConnection = 3000;// 设置超时时间
	public byte[] response = null;
	private int status = 0;// 默认是0表示正在处理, -1:表示连接超时 ,-2:表示响应失败// -3：表示其它异常,1：表示处理完毕
 
	private HwgCallBack callback;
	public JavaHttp(String url,HwgCallBack callback,String content) {
		this.url=url;
		this.callback = callback;
		this.content =content;
		 
	}
	public void execute() {
		Log.i("test17", "executeexecuteexecuteexecute");
		HttpURLConnection http = null;
		try {
			//url="http://bbs.dozengame.com/uc_server/data/avatar/000/04/41/65_avatar_middle.jpg";
			URL urls = new URL(url);
			http = (HttpURLConnection) urls.openConnection();
			http.setConnectTimeout(timeoutConnection);// 设置连接超时时间
			http.setDoInput(true);
			http.setDoOutput(true);

			http.setRequestMethod("POST");
			http.setUseCaches(false);// 请求不使用缓存
			http.setInstanceFollowRedirects(true);
			http.connect();
			OutputStream out = http.getOutputStream();
			out.write(content.getBytes());
			out.flush();
			out.close();
			int code = http.getResponseCode();
			Log.i("test17", "code: "+code);
			if (code == 200) {
				status =1;
				InputStream is = http.getInputStream();
				response=receiveData1(is);
			} else if (code ==301 || code ==302){
			    Map<String,List<String>> map =	http.getHeaderFields();
			    List<String> list= map.get("location");
			    if(list != null && !list.isEmpty()){
			    	status =8;
			        url=list.get(0);
			        Log.i("test17", "location url: "+url);
			    }else{
			    	status=-2;
			    }
//			    Set<String> set=  map.keySet();
//			    Iterator<String> it=set.iterator();
//			    while(it.hasNext()){
//			    	String temp=it.next();
//			    	List<String> list=map.get(temp);
//			    	Log.i("test17",  "   temp: "+temp);
//			    	int size = list.size();
//			    	for(int i =0;i<size;i++){
//			    		Log.i("test17","i: "+i+" "+list.get(i));
//			    	}
//			    	Log.i("test17",  "   ");
//			    }
//				InputStream is = http.getInputStream();
//				response=receiveData1(is);
//				Log.i("test17", "response: "+new String(response));
			}else {
				// 处理异常
				status = -2;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(this.getClass().getName(), e.getMessage());
			if (e.getClass() == java.net.SocketTimeoutException.class) {
				status = -1; // 连接超时
			} else {
				status = -3;
			}
		} finally {
			http.disconnect();// 关闭连接
			http = null;
		}
		Log.i("test17", "status: "+status);
		if(status==8){
			execute();
		}else{
		 callBack();
		}
	}
	/**
	 * 普通接收
	 * @param is
	 * @return
	 * @throws Exception
	 */
    private byte[] receiveData1(InputStream is ) throws  Exception{
     
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte bt[] = new byte[512];
		int len = 0;
		while ((len = is.read(bt)) != -1) {
			baos.write(bt, 0, len);
		}
		baos.flush();
		baos.close();
		bt = baos.toByteArray();
		return bt;
    }
 
   
    private void callBack() {
	        if(status ==1){//表示处理完成功调用
	        	Log.i("test17","nnd is success");
	        	callback.CallBack(response);
			}else{//表示处理失败后调用
				Log.i("test17","nnd is failed");
				callback.CallBack(status);
			}
	}
	public int getStatus() {
		return status;
	}

}
