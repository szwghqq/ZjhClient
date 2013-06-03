/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.alipay;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import android.util.Log;

public class Rsa
{

	private static final String ALGORITHM = "RSA";

	/**
	 * @param algorithm
	 * @param ins
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws AlipayException
	 */
	private static PublicKey getPublicKeyFromX509(String algorithm,
			String bysKey) throws NoSuchAlgorithmException, Exception
	{
		byte[] decodedKey = Base64.decode( bysKey );
		X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);

		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);		
		return keyFactory.generatePublic(x509);
	}
	
	public static String encrypt(String content, String key)
	{
		try
		{
			PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, key);

			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubkey);

			byte plaintext[] = content.getBytes("UTF-8");
			byte[] output = cipher.doFinal( plaintext );

			String s = new String(Base64.encode(output));
			
			return s;

		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	public static String sign(String content, String privateKey)
	{
		String charset = "utf-8";
        try 
        {
        	Log.i("test16","content: "+content);
        	 Log.i("test16","SSSSSSSSSSSSSS: "+privateKey);
        	byte bytes[] =Base64.decode(privateKey);
//        	StringBuffer sb = new StringBuffer();
//        	for(int i =0; i< bytes.length;i++){
//        		sb.append(bytes[i]);
//        	}
        	//Log.i("test16", "bytes: "+sb.toString());
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec(bytes); 
        	//Log.i("test16","priPKCS8 "+priPKCS8.getFormat());
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	//Log.i("test16","keyf "+keyf.getAlgorithm());
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);
        	//Log.i("test16","priKey "+priKey.getFormat()+"  "+priKey.getAlgorithm());
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            byte cts [] = content.getBytes(charset);
            StringBuffer sbss = new StringBuffer();
        	for(int i =0; i< cts.length;i++){
        		sbss.append(cts[i]);
        	}
        	System.out.println("\ntest16 cts : "+sbss.toString());
        	signature.update(cts);

            byte[] signed = signature.sign();
            StringBuffer sbs = new StringBuffer();
        	for(int i =0; i< signed.length;i++){
        		sbs.append(signed[i]);
        	}
        	Log.i("test16", "signeds : "+sbs.toString());
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	public static boolean doCheck(String content, String sign, String publicKey)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(publicKey);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes("utf-8") );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
}
