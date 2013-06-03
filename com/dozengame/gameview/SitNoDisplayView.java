package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.dozengame.GameApplication;
import com.dozengame.util.GameUtil;

public class SitNoDisplayView extends View {
	
	static final int[][] point ={{432,440},{210,440},{35,370},{35,155},{270,50},{620,50},{845,150},{845,370},{670,440}};
	 

		Bitmap bg,cacheBitmap;
		int pos;
		Canvas canvas=null;
		Paint pt =null;
		float y =0;
		public SitNoDisplayView(Context context, int pos,Bitmap bg) {
			super(context);
			this.pos = pos;
			this.bg = bg;
			canvas = new Canvas();
			pt = new Paint();
			pt.setAntiAlias(true);
			pt.setTextSize(30);
			pt.setColor(Color.WHITE);
			y = pt.descent() - pt.ascent();
			y =(80-y)/2+y;
			setVisibility(View.INVISIBLE);
		 
		}
		public void draw() {
		 
			 GameUtil.recycle(cacheBitmap);
			 cacheBitmap = Bitmap.createBitmap(80, 80, Config.ARGB_8888);
			 canvas.setBitmap(cacheBitmap);
			  
			 int siteNo = GameApplication.getDzpkGame().playerViewManager.getSiteNo(pos);
			 siteNo = siteNo-5;
			 if(siteNo < 1){
				 siteNo= siteNo+9;
			 } 
			 String no = siteNo+"ºÅ";
			 float w = pt.measureText(no);
			 canvas.drawBitmap(bg, 0,0,null);
	         canvas.drawText(no,(80-w)/2,y-5,pt);
	         postInvalidate();
	     }
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			if(this.getVisibility() == View.VISIBLE){
				 if(cacheBitmap != null && !cacheBitmap.isRecycled()){
			         canvas.drawBitmap(cacheBitmap, point[pos][0],point[pos][1],null);
				 }
			}
		}
		public void setVisibility(int visibility) {
			super.setVisibility(visibility);
			if(View.VISIBLE == visibility){
				draw();
			}
		};
		   /**
		 * ÊÍ·Å×ÊÔ´
		 */
		public void destroy(){
			destroyBitmap();
			this.destroyDrawingCache();
			 
		
		}
		private void destroyBitmap(){
		
			 GameUtil.recycle(cacheBitmap);
			 cacheBitmap =null;
		}
}
