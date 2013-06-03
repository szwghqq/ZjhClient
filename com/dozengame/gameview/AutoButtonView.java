package com.dozengame.gameview;

import android.content.Context;
import android.widget.LinearLayout;

import com.dozengame.R;

public class AutoButtonView extends LinearLayout {

	@SuppressWarnings("static-access")
	public AutoButtonView(Context context) {
		super(context);
	    this.inflate(context, R.layout.autobuttonview, this);
		 
	}

}
