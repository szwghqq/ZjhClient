/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.alipay;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.dozengame.R;

public class BaseHelper
{
	public static String convertStreamToString(InputStream is)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static void showDialog(Context context,String strTitle ,String strText,int icon)
	{
		try{
		AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
		tDialog.setIcon(icon);
		tDialog.setTitle(strTitle);
		tDialog.setMessage(strText);
		tDialog.setPositiveButton(R.string.Ensure, null);
		tDialog.show();
		}catch(Exception e){}
	}
	
	public static void log(String tag, String info)
	{
		//Log.d(tag, info);
	}	
	
	public static void chmod(String permission, String path)
	{
		try
			{
				String command 	= "chmod " + permission + " " + path;
				Runtime runtime = Runtime.getRuntime();
				runtime.exec(command);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}
	//
	// show the progress bar.
	public static ProgressDialog showProgress(Context context,
			CharSequence title, CharSequence message, boolean indeterminate,
			boolean cancelable)
	{
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIndeterminate(indeterminate);
		dialog.setCancelable(false);
		//dialog.setDefaultButton(false);
		//dialog.setOnCancelListener( new GameCzDialog.AlixOnCancelListener(context) )
		dialog.show();
		return dialog;
	}
	//
	// show the progress bar.
	public static ProgressDialog showProgress(GameCzDialog context,
			CharSequence title, CharSequence message, boolean indeterminate,
			boolean cancelable)
	{
		ProgressDialog dialog = new ProgressDialog(context.getContext());
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIndeterminate(indeterminate);
		dialog.setCancelable(false);
		//dialog.setDefaultButton(false);
		dialog.setOnCancelListener( new GameCzDialog.AlixOnCancelListener(context) );

		dialog.show();
		return dialog;
	}
	
	public static JSONObject string2JSON(String str, String split)
	{
		JSONObject json = new JSONObject();
		try
		{
			String[] arrStr = str.split(split);
			for (int i = 0; i < arrStr.length; i++)
			{
				String[] arrKeyValue = arrStr[i].split("=");
				json.put(arrKeyValue[0], arrStr[i].substring(arrKeyValue[0]
						.length() + 1));
			}
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

		return json;
	}	
}