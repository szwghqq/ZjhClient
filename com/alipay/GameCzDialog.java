package com.alipay;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.alipay.net.JavaHttp;
import com.dozengame.GameApplication;
import com.dozengame.HwgCallBack;
import com.dozengame.R;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameUtil;
/**
 *  
 * @author hewengao
 *
 */
public class GameCzDialog extends Dialog implements HwgCallBack{
	Bitmap hall_back;
	Bitmap hall_button1;
	Bitmap hall_button2;
	Bitmap hall_top;
	Bitmap hall_listbg;
	static Bitmap sc_cz_sel;
	static Bitmap sc_cz_bg;
	static Drawable drawableSel;
	static Drawable drawableBg;
    ListView listView;
    private List<CzProductInfo> listItem= new ArrayList<CzProductInfo>();
	public GameCzDialog(Context context,int theme) {
		super(context,theme);
		this.setContentView(R.layout.gamecz);
		
		Log.i("test12", "GameCzDialogGameCzDialogGameCzDialog");
		this.setOnDismissListener(new OnDismissListener(){

			@Override
			public void onDismiss(DialogInterface dialog) {
				 Log.i("test12", "onDismissonDismiss");
				 destory();
			}
			
		});
		this.setOnShowListener(new OnShowListener(){
			@Override
			public void onShow(DialogInterface dialog) {
				 Log.i("test12", "onShowonShowonShow");
				initBitmap();
				initData();
			}
		});
	}
	
	public static Drawable getScCzSel(){
		if(drawableSel ==null){
			if(sc_cz_sel != null){
				drawableSel = new BitmapDrawable(sc_cz_sel);
			}
		}
		return drawableSel;
	}
	public static Drawable getScCzBg(){
		if(drawableBg == null){
			if(sc_cz_bg != null){
				drawableBg= new BitmapDrawable(sc_cz_bg);
			}
		}
		return drawableBg;
	}
	private void destory(){
		 GameUtil.recycle(hall_button1);
		 hall_button1 =null;
		 GameUtil.recycle(hall_button2);
		 hall_button2 =null;
		 GameUtil.recycle(hall_back);
		 hall_back =null;
		 GameUtil.recycle(hall_top);
		 hall_top =null;
		 GameUtil.recycle(hall_listbg);
		 hall_listbg =null;
		 GameUtil.recycle(sc_cz_sel);
		 sc_cz_sel =null;
		 GameUtil.recycle(sc_cz_bg);
		 sc_cz_bg =null;
		 if(listItem != null){
			 int size = listItem.size();
			 for(int i=0; i<size;i++){
				 GameUtil.recycle(listItem.get(i).getProductBitmap());
			 }
			 listItem.clear();
		 }
		     drawableSel =null;
			 drawableBg =null;
	}
	
	 private void initBitmap(){
	    	try {
	    		  hall_button1 = BitmapFactory.decodeStream(this.getContext().getAssets().open("hall_button.png"));
	    		  hall_button2 = BitmapFactory.decodeStream(this.getContext().getAssets().open("hall_buttons.png"));
	    		  hall_back = BitmapFactory.decodeStream(this.getContext().getAssets().open("hall_back.png"));
	    	 
	    		  hall_top = BitmapFactory.decodeStream(this.getContext().getAssets().open("chongzhi/sc_cz_top.png"));
	    		  hall_listbg = BitmapFactory.decodeStream(this.getContext().getAssets().open("hall_listbg.jpg"));
	    		  sc_cz_sel = BitmapFactory.decodeStream(this.getContext().getAssets().open("chongzhi/sc_cz_sel.png"));
	    		  sc_cz_bg = BitmapFactory.decodeStream(this.getContext().getAssets().open("chongzhi/sc_cz_bg.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		  }
		 
		private void initData(){
			
			findViewById(R.id.game_cz).setBackgroundDrawable(new BitmapDrawable(hall_listbg));
			findViewById(R.id.top).setBackgroundDrawable(new BitmapDrawable(hall_top));
		    ImageView backImage=(ImageView)findViewById(R.id.back);
		    backImage.setImageBitmap(hall_back);
			
		   final ImageView backImageBg=(ImageView)findViewById(R.id.backbg);
			backImageBg.setImageBitmap(hall_button1);
			backImageBg.setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					final int action =event.getAction();
				    if(action == 0){
				    	backImageBg.setImageBitmap(hall_button2);
				    	return true;
				    }else if(action ==1){
				    	backImageBg.setImageBitmap(hall_button1);
				    	back();
				    	return true;
				    }
					return false;
				}
	    		
	    	});
			
			listView =(ListView)findViewById(R.id.list);
			loadProductData();
			HwgCallBack callback = new HwgCallBack(){
				 
				public void CallBack(Object... obj) {
					 
				 if(obj != null && obj.length ==1){
					 Integer pos = (Integer)obj[0];
					 CzProductInfo product= (CzProductInfo)listView.getItemAtPosition(pos);
					 useZfb(product);
					 
					 //Toast.makeText(getContext(),product.getProductName(),Toast.LENGTH_LONG).show();
				 }
					 
				}
	        	
	        };
			CzListAdapter<CzProductInfo>	adapter = new CzListAdapter<CzProductInfo>(this.getContext(),
					R.layout.czitem, listItem, new String[] {"Drawable"}, 
					   new int[] {R.id.cz_img},callback);
			listView.setAdapter(adapter);
			 
			adapter.notifyDataSetChanged();
		}
		/**
		 *加载充值产品信息
		 */
		private void loadProductData(){
			try {
				listItem=SAXParseXmlProductService.getProductsService(getContext().getAssets().open("chongzhi/cz.xml"));
				if(listItem !=null){
					int size  = listItem.size();
					CzProductInfo temp =null;
					for(int i=0;i<size;i++){
						temp = listItem.get(i);
						temp.setProductBitmap(BitmapFactory.decodeStream(getContext().getAssets().open(temp.getProductImgPath())));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	private void back(){
		dismiss();
	}
	private ProgressDialog mProgress = null;
	
	/**
	 * 使用支付宝
	 * @param product
	 */
	private void useZfb2(final CzProductInfo product){
		//
		// check to see if the MobileSecurePay is already installed.
		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(getContext());
		boolean isMobile_spExist = mspHelper.detectMobile_sp();
		if (!isMobile_spExist)
			return;

//		// check some info.
//		if (!checkInfo()) {
//			BaseHelper.showDialog(getContext(), "提示",
//					"缺少partner或者seller，请在src/com/alipay/android/appDemo4/PartnerConfig.java中增加。", R.drawable.infoicon);
//					return;
//		}

		// start pay for this order.
		try {
			// prepare the order info.
			
			String signType = getSignType();
			String orderInfo = getOrderInfoBak(product);
			String strsign = sign(signType, orderInfo);
			Log.i("test16", "sign: "+strsign);
			strsign = URLEncoder.encode(strsign);
			Log.i("test16", "sign2: "+strsign);
			String info = orderInfo + "&sign=" + "\"" + strsign + "\"" + "&"+ getSignType();
			Log.i("test16", "info: "+info);
		  //  info ="partner=\"2088701250874534\"&seller=\"2088701250874534\"&out_trade_no=\"1319700381250\"&subject=\"福袋\"&body=\"福袋\"&total_fee=\"0.01\"&notify_url=\"http://172.17.0.139:8085/zfb/servlet/RSANotifyReceiver\"&sign=\"NAtDzsHXLbBmyevXjRZcj7GFWvFWu3USRE8QcBDOv5wjVyG0ULyN812AqlNW4gP+5SzlxtbBCdTCNSese+wngDFEDoAJkicwpHbulvgZnnwF/b+V0CEi1ewDXOBVhggsnbaq53m7BoNm9C31desytD59J7fZS8yMAE9NFtp2620=\"&sign_type=\"RSA\""
;

			// start the pay.
			MobileSecurePayer msp = new MobileSecurePayer();
			boolean bRet = msp.pay(info, mHandler, AlixId.RQF_PAY, this.getContext());

			if (bRet) {
				// show the progress bar to indicate that we have started
				// paying.
				closeProgress();
				mProgress = BaseHelper.showProgress(this, null, "正在支付", false,true);
			} else
				;

			 
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(getContext(), R.string.remote_call_failed,
					Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * 使用支付宝
	 * @param product
	 */
	private void useZfb(final CzProductInfo product){
		 if(GameApplication.mobPayOff == 0){
			 //手机支付暂未开启
			 GameUtil.openMessageDialog(this.getContext(), GameUtil.MOBPAYOFF);
			 return ;
		 }
		// check to see if the MobileSecurePay is already installed.
		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(getContext());
		boolean isMobile_spExist = mspHelper.detectMobile_sp();
		if (!isMobile_spExist)
			return;

//		// check some info.
//		if (!checkInfo()) {
//			BaseHelper.showDialog(getContext(), "提示",
//					"缺少partner或者seller，请在src/com/alipay/android/appDemo4/PartnerConfig.java中增加。", R.drawable.infoicon);
//					return;
//		}

		// start pay for this order.
		try {
			// prepare the order info.
			
		//	String signType = getSignType();
		//	String strsign = sign(signType, orderInfo);
		//	Log.i("test12", "sign: "+strsign);
		//	strsign = URLEncoder.encode(strsign);
		//	Log.i("test12", "sign2: "+strsign);
		//	String info = orderInfo + "&sign=" + "\"" + strsign + "\"" + "&"+ getSignType();
		//	Log.i("test12", "info: "+info);
			mProgress = BaseHelper.showProgress(GameCzDialog.this, null, "正在处理", false,true);
			TaskManager.getInstance().execute(new TaskExecutorAdapter() {
 
				public int executeTask() throws Exception {
					 String orderInfo = getOrderInfo(product);
					 Log.i("test16", "orderInfo: "+orderInfo);
					 JavaHttp http = new JavaHttp(PartnerConfig.url,GameCzDialog.this, orderInfo);
					 http.execute();
					 return 0;
				}
			});
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(getContext(), R.string.remote_call_failed,
					Toast.LENGTH_SHORT).show();
		}
	}
 
	@Override
	public void CallBack(Object... obj) {
		try {
			closeProgress();
			if(obj == null || obj.length!=1 || !(obj[0] instanceof byte[])) {
				return;
			}
			 
			byte[] response = (byte[])obj[0];
			String str = new String(response);
			//ResultChecker checker = new ResultChecker(str);
			 
	       // int singResult = checker.checkSign();
	        //Log.i("test16","singResult: "+singResult);
			Log.i("test16","str1: "+str);
		   // str=URLDecoder.decode(str, "utf-8");
		   // Log.i("test16","str2: "+str);
		    str=str.replace("&", ";.");
		    Document doc= GameUtil.getDocument(str.getBytes());
		    Element root =doc.getDocumentElement();
		    Log.i("test16","nodeName: "+root.getNodeName());
		    NodeList list= root.getElementsByTagName("is_success");
		    Node nd=list.item(0);
		    Log.i("test16","is_success nodeVale: "+nd.getTextContent());
		    if(nd.getTextContent().equals("T")){
		    	
			    list= root.getElementsByTagName("content");
			      nd=list.item(0);
			      String signData = nd.getTextContent();
			      signData= signData.replace(";.", "&");
			      Log.i("test16","content nodeVale: "+signData);
//			      {
//			      list= root.getElementsByTagName("sign");
//			      nd=list.item(0);
//			      String sign = nd.getTextContent();
//			      
//			      Log.i("test16","sign nodeVale: "+sign);
//			      sign=URLEncoder.encode(sign);
//			      String info = signData + "&sign=" + "\"" + sign + "\"" + "&"+ getSignType();
//			      Log.i("test16","info: "+info);
//			      }
			      String signs="";
			      {
			    	  list= root.getElementsByTagName("privatekey");
			    	  nd=list.item(0);
 				      PartnerConfig.RSA_PRIVATE =nd.getTextContent();
 				      Log.i("test16", "privetkey : "+PartnerConfig.RSA_PRIVATE);
 				     Log.i("test16", "signData : "+signData);
 				      signs = sign(getSignType(), signData);
 				      signs = URLEncoder.encode(signs);
			      }
			      String info = signData + "&sign=" + "\"" + signs + "\"" + "&"+ getSignType();
				// start the pay.
				MobileSecurePayer msp = new MobileSecurePayer();
				boolean bRet = msp.pay(info, mHandler, AlixId.RQF_PAY, getContext());
		
				if (bRet) {
					// show the progress bar to indicate that we have started
					// paying.
					closeProgress();
					mProgress = BaseHelper.showProgress(GameCzDialog.this, null, "正在支付", false,true);
				}
		    }else{
		    	Toast.makeText(getContext(), R.string.singFailed,Toast.LENGTH_SHORT).show();
		    }
		
		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(getContext(), R.string.remote_call_failed,
					Toast.LENGTH_SHORT).show();
		}
	}
	String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String strKey = format.format(date);

		java.util.Random r = new java.util.Random();
		strKey = strKey + r.nextInt();
		strKey = strKey.substring(0, 15);
		return strKey;
	}

	//
	// sign the order info.
	String sign(String signType, String content) {
		 
		return Rsa.sign(content, PartnerConfig.RSA_PRIVATE);
	}

	//
	// get the sign type we use.
	String getSignType() {
		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
		return getSignType;
	}

	//
	// get the char set we use.
	String getCharset() {
		String charset = "charset=" + "\"" + "utf-8" + "\"";
		return charset;
	}
	String getOrderInfoBak(CzProductInfo product) {
		String trade_no =System.currentTimeMillis()+"";
		String strOrderInfo = "partner=" + "\"" + PartnerConfig.PARTNER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "seller=" + "\"" + PartnerConfig.SELLER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "out_trade_no=" + "\"" + trade_no + "\"";
		strOrderInfo += "&";
		strOrderInfo += "subject=" + "\"" + product.getProductName()
				+ "\"";
		strOrderInfo += "&";
		strOrderInfo += "body=" + "\"" + product.getProductName() + "\"";
		strOrderInfo += "&";
		strOrderInfo += "total_fee=" + "\""
				+product.getProductPrice() + "\"";
 		strOrderInfo += "&";
 		strOrderInfo += "notify_url=" + "\""
 				+ "http://121.9.240.37:9999" + "\"";

		return strOrderInfo;
	}
	// check some info.the partner,seller etc.
	private boolean checkInfo() {
		String partner = PartnerConfig.PARTNER;
		String seller = PartnerConfig.SELLER;
		if (partner == null || partner.length() <= 0 || seller == null
				|| seller.length() <= 0)
			return false;

		return true;
	}
	
	String getOrderInfo(CzProductInfo product) throws Exception {
		 
		String strOrderInfo = "userid="+GameApplication.userInfo.get("user_real_id")+"&pay_from=9999&subject=" + product.getProductName();
		strOrderInfo += "&";
		strOrderInfo += "body="+ product.getProductName();
		strOrderInfo += "&";
		strOrderInfo += "total_fee=1";//+product.getProductPrice();
	//	strOrderInfo += "&";
	//	strOrderInfo += "notify_url=" + "\""+ "http://121.9.240.37:9999" + "\"";
		return strOrderInfo;
		 
	}
	
	

	//
	// the handler use to receive the pay result.
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				String strRet = (String) msg.obj;

				switch (msg.what) {
				case AlixId.RQF_PAY: {
					//
					closeProgress();
					Log.i("test8","strRet: "+strRet);
					//BaseHelper.log(TAG, strRet);
					try {
						String memo = "memo=";
						int imemoStart = strRet.indexOf("memo=");
						imemoStart += memo.length();
						int imemoEnd = strRet.indexOf(";result=");
						memo = strRet.substring(imemoStart, imemoEnd);

						ResultChecker resultChecker = new ResultChecker(strRet);

						int retVal = resultChecker.checkSign();
						if (retVal == ResultChecker.RESULT_CHECK_SIGN_FAILED) {
							BaseHelper.showDialog(
									getContext(),
									"提示",
									getContext().getResources().getString(
											R.string.check_sign_failed),
									android.R.drawable.ic_dialog_alert);
						} else {
							BaseHelper.showDialog(getContext(), "提示", memo,
									R.drawable.infoicon);
						}
						
					} catch (Exception e) {
						e.printStackTrace();

						BaseHelper.showDialog(getContext(), "提示", strRet,
								R.drawable.infoicon);
					}
				}
					break;
				}

				super.handleMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	//
	// the OnCancelListener for lephone platform.
	static class AlixOnCancelListener implements
			DialogInterface.OnCancelListener {
		GameCzDialog mcontext;

		AlixOnCancelListener(GameCzDialog context) {
			mcontext = context;
		}

		public void onCancel(DialogInterface dialog) {
			mcontext.onKeyDown(KeyEvent.KEYCODE_BACK, null);
		}
	}

	//
	// close the progress bar
	void closeProgress() {
		try {
			if (mProgress != null) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//BaseHelper.log(TAG, "onKeyDown back")
			closeProgress();
			dismiss();
			return true;
		}

		return false;
	}

	
 

//	//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		Log.v(TAG, "onDestroy");
//
//		try {
//			mProgress.dismiss();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
