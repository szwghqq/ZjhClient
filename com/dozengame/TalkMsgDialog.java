package com.dozengame;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
/**
 * ¡ƒÃÏΩÁ√Ê
 * @author Administrator
 *
 */
public class TalkMsgDialog extends Dialog {

	public TalkMsgDialog(Context context, int theme) {
		super(context, theme);
		setContentView(R.layout.talkmsg);
		this.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				openInputMethod();
			}
		});
	}
	private void openInputMethod(){
		 InputMethodManager m = (InputMethodManager) this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);   
    	 m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
    	 
	}

}
