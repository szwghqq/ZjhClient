package com.alipay;

public class PartnerConfig {
	//public static final String  url="http://172.17.0.105:80/servlet/RSATrade";
	
	//public static final String  url="http://172.17.0.139:8085/zfb/servlet/RSATrade";
	//public static final String  url="http://172.17.0.105/aliPay/index.do?command=sign";
	 public static final String  url="http://www.dozenking.com/tex/alipay/pay.do?command=sign";
	//个人
	//合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	public static final String PARTNER1 = "2088702244672581";//2088701250874534
	//账户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	public static final String SELLER1 = "2088702244672581";//2088701250874534
	//商户（RSA）私钥  
	public static final String RSA_PRIVATE1 = "MIICXQIBAAKBgQDSz/DUJdruVa+kY+6oK2YsbIKDMLOMqBiMbQytHHpuKRPgMpSm"+
												"xB0WNmKNnN4uD6eqnV4wWof4V3O8FNPI6/5RXT5mZ/edM2W3vTHVZ2khleV9BGwH"+
												"Fg7QBatsW+WQ6EU8oZXK9w4CMnbSBXtPck6kyx1OV639CxB35I9JHs9jVwIDAQAB"+
												"AoGBAK0c1r3PjDW1JXPQIDX21YNbY91JS9gQXYBLOGKbhSobD1hHEOzVFLGd3VY+"+
												"3/BhKKUhbywz234iikaDr9pJeN9oGqTmt3hTGUYx81/xCDRVXGxunXMMRvMwQz/1"+
												"y4wIMaVlyTaqU9GyRIMzZ4ibPok47Gamb292OajGQzgj6FzxAkEA7W8E0w0e+AWc"+
												"r7I3NGLrEnw6VJTJcptTcYmfKWgeaIkRaKB/rylu+6XALVYrvdj44zsIsABakKvD"+
												"unWV59oCnwJBAONMAst2DfBS1SeC1YiwnIphkdObsixh07MVNYZFLKaDtisZOanh"+
												"xLV3lNbS7TTk7DnTzcJTZvsb1CK8nuIc3EkCQQCB0qQ2qgP25UqpHq7kq/mQn/Bc"+
												"8wW67ocPm4o1X8LQsbGdHbTzF5qhah3MLEO6iHp99HdplKfwVpqsx0JyBVhNAkAR"+
												"qQI+lLmYhpcRLtUwfahsCBCwhFRfpmowa+FwwUcq2Oj6iEc2r9f80YG6/0B276J7"+
												"C73c72X89LiSylz97IghAkAU1nLtvwZxm0NYROWj4xvpw9qzwdAf4TjTidhpQboa"+
												"r0UTtegJ4x3rtVAOgDJl7NcqfMtpVrPWddll9RaVpWVJ";
	//支付宝（RSA）公钥  用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
	public static final String RSA_ALIPAY_PUBLIC1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdA08eDNkL6jeF3XlfVQStZwlLkPHAgWBcRIwP p2h0WXA7X3hZzP9Ag5AKcUah+K6ry9/ntCbuvgCZftuSiDSi8cJPjnbOWH85DqoRmbV3KOarO3UE IgpUCfSw2GBiBPnimSF5n2lglHksZOSDGhCmLd4i6kcNKzAXnyJCv8RyJQIDAQAB";
	
	//公司
	//合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	public static final String PARTNER = "2088701250874534";//
	//账户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	public static final String SELLER = "2088701250874534";//
	//商户（RSA）私钥  
	public static final String RSA_PRIVATE2 = "MIICXAIBAAKBgQCiQQFz8z2gb8dQ6H2dyZO0AMqNVkg5y3zAp5iMJVhfAZVhMF73vegbEsScLLui4WeLVhISe54gF8XoIDUrENCFHAK1kfvjexh2ch2fT8iHpVv3iLpScpYtRhu0IMtbNFHDIb8j0Kx6aAS9fCfJk4YOs5Ixal6bdtcCHF9TszRryQIDAQAB"+
												"AoGBAJm62bOQjZ1LCx//h7VOXiG4djF/xKLtx9+Ja6GtaJNrnqsc933nctjj6WDH"+
												"iWiEBGUiQbE9as6hd6O9kGKjZcyWO61R4Eyk6n9sVNNJIGPmM8kJvBrQ4yeseZlB"+
												"jeCpCxAhx+4jLbBW6rX2bN9B2U6AjDTK/2d6Y+e2QQZ+wpK5AkEAz+S6sSjlqSew"+
												"g5OM6gUk0ZSyds4BRSifDOsouSOLa2wlMchycFRdioQgPPICdBptw17lP//yMIXG"+
												"/YVN2kvHawJBAMfMqgHwn7d5zgaIbKEALSVtYTsH4TcRL7sTpRwc/QyRxW4cvP1h"+
												"tE8a/gdVrmQth6ZVbbptXHGAekOueZU5ipsCQAHK0gU2+gvkSLRJsFxQIQohgwxd"+
												"KEulVwGwgVBfSxNpAkUUmk/3mjrYesKrv3Oqyql1hvpvRXI/pQCIhqxMHPMCQEjd"+
												"pj7Q357NSErvpBZPdyeILYaWyBcKKIskjfmjxNG5s/QFfRM3d8fFw1EveUtFo4wQ"+
												"6dSsn6MxROW8EtFOS/MCQByd6NH5XpGY7lIXxH7YMm9Mn9HUQ3qGYRTpU2cG7ILH"+
												"lsmvXMSehLl4If/Y3Up6F0GC1O6BHluBbpbkSgkqckA=";
	public static   String RSA_PRIVATE="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKJBAXPzPaBvx1Do"+
			"fZ3Jk7QAyo1WSDnLfMCnmIwlWF8BlWEwXve96BsSxJwsu6LhZ4tWEhJ7niAXxegg"+
			"NSsQ0IUcArWR++N7GHZyHZ9PyIelW/eIulJyli1GG7Qgy1s0UcMhvyPQrHpoBL18"+
			"J8mThg6zkjFqXpt21wIcX1OzNGvJAgMBAAECgYEAmbrZs5CNnUsLH/+HtU5eIbh2"+
			"MX/Eou3H34lroa1ok2ueqxz3fedy2OPpYMeJaIQEZSJBsT1qzqF3o72QYqNlzJY7"+
			"rVHgTKTqf2xU00kgY+YzyQm8GtDjJ6x5mUGN4KkLECHH7iMtsFbqtfZs30HZToCM"+
			"NMr/Z3pj57ZBBn7CkrkCQQDP5LqxKOWpJ7CDk4zqBSTRlLJ2zgFFKJ8M6yi5I4tr"+
			"bCUxyHJwVF2KhCA88gJ0Gm3DXuU///Iwhcb9hU3aS8drAkEAx8yqAfCft3nOBohs"+
			"oQAtJW1hOwfhNxEvuxOlHBz9DJHFbhy8/WG0Txr+B1WuZC2HplVtum1ccYB6Q655"+
			"lTmKmwJAAcrSBTb6C+RItEmwXFAhCiGDDF0oS6VXAbCBUF9LE2kCRRSaT/eaOth6"+
			"wqu/c6rKqXWG+m9Fcj+lAIiGrEwc8wJASN2mPtDfns1ISu+kFk93J4gthpbIFwoo"+
			"iySN+aPE0bmz9AV9Ezd3x8XDUS95S0WjjBDp1KyfozFE5bwS0U5L8wJAHJ3o0fle"+
			"kZjuUhfEftgyb0yf0dRDeoZhFOlTZwbsgseWya9cxJ6EuXgh/9jdSnoXQYLU7oEe"+
			"W4FuluRKCSpyQA==";
//	MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKJBAXPzPaBvx1Do
//	fZ3Jk7QAyo1WSDnLfMCnmIwlWF8BlWEwXve96BsSxJwsu6LhZ4tWEhJ7niAXxegg
//	NSsQ0IUcArWR  N7GHZyHZ9PyIelW/eIulJyli1GG7Qgy1s0UcMhvyPQrHpoBL18
//	J8mThg6zkjFqXpt21wIcX1OzNGvJAgMBAAECgYEAmbrZs5CNnUsLH/ HtU5eIbh2
//	MX/Eou3H34lroa1ok2ueqxz3fedy2OPpYMeJaIQEZSJBsT1qzqF3o72QYqNlzJY7
//	rVHgTKTqf2xU00kgY YzyQm8GtDjJ6x5mUGN4KkLECHH7iMtsFbqtfZs30HZToCM
//	NMr/Z3pj57ZBBn7CkrkCQQDP5LqxKOWpJ7CDk4zqBSTRlLJ2zgFFKJ8M6yi5I4tr
//	bCUxyHJwVF2KhCA88gJ0Gm3DXuU///Iwhcb9hU3aS8drAkEAx8yqAfCft3nOBohs
//	oQAtJW1hOwfhNxEvuxOlHBz9DJHFbhy8/WG0Txr B1WuZC2HplVtum1ccYB6Q655
//	lTmKmwJAAcrSBTb6C RItEmwXFAhCiGDDF0oS6VXAbCBUF9LE2kCRRSaT/eaOth6
//	wqu/c6rKqXWG m9Fcj lAIiGrEwc8wJASN2mPtDfns1ISu kFk93J4gthpbIFwoo
//	iySN aPE0bmz9AV9Ezd3x8XDUS95S0WjjBDp1KyfozFE5bwS0U5L8wJAHJ3o0fle
//	kZjuUhfEftgyb0yf0dRDeoZhFOlTZwbsgseWya9cxJ6EuXgh/9jdSnoXQYLU7oEe
//	W4FuluRKCSpyQA==

	  //MIICXAIBAAKBgQCiQQFz8z2gb8dQ6H2dyZO0AMqNVkg5y3zAp5iMJVhfAZVhMF73vegbEsScLLui4WeLVhISe54gF8XoIDUrENCFHAK1kfvjexh2ch2fT8iHpVv3iLpScpYtRhu0IMtbNFHDIb8j0Kx6aAS9fCfJk4YOs5Ixal6bdtcCHF9TszRryQIDAQABAoGBAJm62bOQjZ1LCx//h7VOXiG4djF/xKLtx9 Ja6GtaJNrnqsc933nctjj6WDHiWiEBGUiQbE9as6hd6O9kGKjZcyWO61R4Eyk6n9sVNNJIGPmM8kJvBrQ4yeseZlBjeCpCxAhx 4jLbBW6rX2bN9B2U6AjDTK/2d6Y e2QQZ wpK5AkEAz S6sSjlqSewg5OM6gUk0ZSyds4BRSifDOsouSOLa2wlMchycFRdioQgPPICdBptw17lP//yMIXG/YVN2kvHawJBAMfMqgHwn7d5zgaIbKEALSVtYTsH4TcRL7sTpRwc/QyRxW4cvP1htE8a/gdVrmQth6ZVbbptXHGAekOueZU5ipsCQAHK0gU2 gvkSLRJsFxQIQohgwxdKEulVwGwgVBfSxNpAkUUmk/3mjrYesKrv3Oqyql1hvpvRXI/pQCIhqxMHPMCQEjdpj7Q357NSErvpBZPdyeILYaWyBcKKIskjfmjxNG5s/QFfRM3d8fFw1EveUtFo4wQ6dSsn6MxROW8EtFOS/MCQByd6NH5XpGY7lIXxH7YMm9Mn9HUQ3qGYRTpU2cG7ILHlsmvXMSehLl4If/Y3Up6F0GC1O6BHluBbpbkSgkqckA=

		//支付宝（RSA）公钥  用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
	public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC24s4cnSmRSGv3qHBu9WpG0MiWB9oqHM9SkNCz 6YXUncK2PRilYqELthMmt00ZG0vecBpdZS8wLbhf8DOZ2sv+wwyOEVrhdR/VQxeJhEcW465ttklf aSCowA2ngI/KMY3AcwguST7UgccNiA/qkg54STEFnvkugDHFprOZAvgi4QIDAQAB";

	//xsa675fszuowuca4pva2krel62rtaglz
	public static final String ALIPAY_PLUGIN_NAME = "alipay_plugin223_0309.apk";
	
	 
}
