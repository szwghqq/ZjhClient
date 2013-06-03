package com.dozengame;

import java.io.IOException;
import java.util.HashMap;

import com.dozengame.util.GameUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 领奖信息窗口
 * 
 * @author hewengao
 * 
 */
public class LinJiangDialog extends Dialog {
	ImageView imgClose;
	TextView linJiangInfo;
	HashMap data;
	Bitmap bg;
	Bitmap bitmap;
 
	public LinJiangDialog(Context context, int dialog, HashMap data,final HwgCallBack callback) {
		super(context, dialog);
		this.data = data;
		this.setContentView(R.layout.linjiangview);
		Window w = this.getWindow();
        
		try {
			bg = BitmapFactory.decodeStream(this.getContext().getAssets().open(
					"linjiang_bg.png"));
			w.setBackgroundDrawable(new BitmapDrawable(bg));

		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
		this.setOnDismissListener(new OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {

				gc();
				callback.CallBack();
			}
		});
	}

	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		if (x >= 0 && x <= 630) {
			if (y >= 0 && y <= 408) {
				return false;
			}
		}
		dismiss();
		return true;
	}

	private void init() {
		imgClose = (ImageView) findViewById(R.id.linJiangImg);
		try {
			bitmap = BitmapFactory.decodeStream(getContext().getAssets().open(
					"game_gamer_close.png"));
			imgClose.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imgClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}

		});
		linJiangInfo = (TextView) findViewById(R.id.linJiangInfo);
		String msg= GameUtil.LOGINLINGJIANG+data.get("gold")+"\r\n"+GameUtil.LOGINLINGJIANG1+data.get("vipadd")+"\r\n"+GameUtil.LOGINLINGJIANG2+data.get("vtask_add");
		linJiangInfo.setText(msg);
	}

	private void gc() {
		if (bg != null) {
			GameUtil.recycle(bg);
		}
		if (bitmap != null) {
			GameUtil.recycle(bitmap);
		}
		if (data != null) {
			data.clear();
		}
		data = null;
		imgClose = null;
		linJiangInfo = null;
	}

	@Override
	public void onBackPressed() {
		dismiss();
	}
}
