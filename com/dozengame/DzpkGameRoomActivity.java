package com.dozengame;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.dozengame.music.MediaManager;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameUtil;
/**
 * ��Ϸ����
 * @author hewengao
 *
 */
public class DzpkGameRoomActivity extends BaseActivity{
	
 
 
	TextView roomType;//��������
	ImageView prev;
	ImageView next;
	ImageView displaySetBg;
	ImageView backImageBg;
	ImageView pageLeft;
	ImageView pageRight;
	ImageView quickStart;
	ViewFlipper flipper;
	Animation leftIn,leftOut;
	Animation rightIn,rightOut;
	
	TextView roomId;//����id
	TextView roomName;//��������
	TextView renNum;//����
	TextView rebet;//��Сäע
	TextView xiedai;//��СЯ��
	TextView pangguan;//�Թ�
	short []state= {0,0,0,0,0,0};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	 	getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  
	 	Log.i("test1","GameRoom onCreate");
	 	setContentView(R.layout.gameroom);
        initBitmap();
        initData();
      
       
    	flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper01);//���ViewFlipperʵ��
        
        flipper.addView(new RoomView(this,1));
        flipper.addView(new RoomView(this,2));
        flipper.addView(new RoomView(this,3));
        flipper.addView(new RoomView(this,4));
//        flipper.addView(new RoomView(this,5));
        roomType=(TextView)findViewById(R.id.roomType);
        prev.setOnClickListener(listener);
        next.setOnClickListener(listener);
        initAnimation();
        //ѡ��Ĭ�ϳ�
        loadData();
      
        
    }
	Bitmap hall_button1;
	Bitmap hall_button2;
	Bitmap hall_back;
	Bitmap hall_pageLeft1;
	Bitmap hall_pageLeft2;
	Bitmap hall_pageRight1;
	Bitmap hall_pageRight2;
	Bitmap hall_btnPrev1;
	Bitmap hall_btnPrev2;
	Bitmap hall_btnNext1;
	Bitmap hall_btnNext2;
	Bitmap hall_btnBegin1;
	Bitmap hall_btnBegin2;
	
    Bitmap hall_listtitle;
    Bitmap hall_bottombg;
    Bitmap hall_titlebg;
    Bitmap hall_listbg;
	 private void initBitmap(){
    	try {
    		hall_button1 = BitmapFactory.decodeStream(getAssets().open("hall_button.png"));
    		hall_button2 = BitmapFactory.decodeStream(getAssets().open("hall_buttons.png"));
    		hall_back = BitmapFactory.decodeStream(getAssets().open("hall_back.png"));
    		hall_pageRight1 = BitmapFactory.decodeStream(getAssets().open("hall_pageright.png"));
    		hall_pageRight2 = BitmapFactory.decodeStream(getAssets().open("hall_pagerights.png"));
    		hall_pageLeft1 = BitmapFactory.decodeStream(getAssets().open("hall_pageleft.png"));
    		hall_pageLeft2 = BitmapFactory.decodeStream(getAssets().open("hall_pagelefts.png"));
    		
    		hall_btnPrev1 = BitmapFactory.decodeStream(getAssets().open("hall_btnprev.png"));
    		hall_btnPrev2 = BitmapFactory.decodeStream(getAssets().open("hall_btnprevs.png"));
    		hall_btnNext1 = BitmapFactory.decodeStream(getAssets().open("hall_btnnext.png"));
    		hall_btnNext2 = BitmapFactory.decodeStream(getAssets().open("hall_btnnexts.png"));
    		
    		hall_btnBegin1 = BitmapFactory.decodeStream(getAssets().open("hall_btnbegin.png"));
    		hall_btnBegin2 = BitmapFactory.decodeStream(getAssets().open("hall_btnbegins.png"));
    		
    		hall_listtitle = BitmapFactory.decodeStream(getAssets().open("hall_listtitle.png"));
    		hall_bottombg = BitmapFactory.decodeStream(getAssets().open("hall_bottombg.png"));
    		hall_titlebg = BitmapFactory.decodeStream(getAssets().open("hall_titlebg.png"));
    		hall_listbg = BitmapFactory.decodeStream(getAssets().open("hall_listbg.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	 
	private void initData(){
		
		findViewById(R.id.room).setBackgroundDrawable(new BitmapDrawable(hall_listbg));
		findViewById(R.id.top).setBackgroundDrawable(new BitmapDrawable(hall_titlebg));
		findViewById(R.id.bottom).setBackgroundDrawable(new BitmapDrawable(hall_bottombg));
	     ImageView backImage=(ImageView)findViewById(R.id.back);
	     backImage.setImageBitmap(hall_back);
		
	     backImageBg=(ImageView)findViewById(R.id.backbg);
		 backImageBg.setImageBitmap(hall_button1);
		 
		 pageLeft=(ImageView)findViewById(R.id.pageLeft);
		 pageLeft.setImageBitmap(hall_pageLeft2);
		 
		 

		 
		 prev=(ImageView)findViewById(R.id.prev);
		 prev.setImageBitmap(hall_btnPrev1);
		 
	     next=(ImageView)findViewById(R.id.next);
	     next.setImageBitmap(hall_btnNext1);
	     
	     quickStart=(ImageView)findViewById(R.id.quickStart);
	     quickStart.setImageBitmap(hall_btnBegin1);
	     
	     TextView quickStartText=(TextView)findViewById(R.id.quickStartText);
	     quickStartText.setTextColor(Color.BLACK);
	     addListener();
	}
	/**
	 * ����
	 */
	private void back(){
		if(rv != null){
		  rv.setScrollEnable(true);
		}
		Intent it = new Intent();
		it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		it.setClass(DzpkGameRoomActivity.this,DzpkGameMenuActivity.class);
    	startActivity(it);
    	finish();
    	overridePendingTransition();
	}
	  /**
     * ��Ӱ�ť�¼�
     */
    private void addListener(){

    	pageLeft.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				pageLeft.setImageBitmap(hall_pageLeft2);
				//pageRight.setImageBitmap(hall_pageRight1);
			}
		 });
		
		
		prev.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	prev.setImageBitmap(hall_btnPrev2);
			    	return true;
			    }else if(action ==1){
			    	prev.setImageBitmap(hall_btnPrev1);
			    	prevClick();
			    	return true;
			    }
				return false;
			}
    		
    	});
		next.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	next.setImageBitmap(hall_btnNext2);
			    	return true;
			    }else if(action ==1){
			    	next.setImageBitmap(hall_btnNext1);
			    	nextClick();
			    	return true;
			    }
				return false;
			}
    		
    	});
		
		quickStart.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int action =event.getAction();
			    if(action == 0){
			    	quickStart.setImageBitmap(hall_btnBegin2);
			    	return true;
			    }else if(action ==1){
			    	
			    	quickStart.setImageBitmap(hall_btnBegin1);
			    	if(DzpkGameActivity.isDestroy){
				    	Intent it =new Intent();
				    	it.putExtra("type", 1);
						it.setClass(DzpkGameRoomActivity.this, DzpkGameActivity.class);
						 if(rv !=null){
							rv.setScrollEnable(true);
						 }
					    startActivity(it);
					    finish();
			    	}else{
			    		if(rv !=null){
			    			rv.loadStart();
			    		}
			    	}
			    	return true;
			    }
				return false;
			}
    		
    	});
 
    }
	/**
	 * ��ʼ������
	 */
	private void initAnimation(){
		leftIn=AnimationUtils.loadAnimation(DzpkGameRoomActivity.this, R.anim.left_in);
		leftOut=AnimationUtils.loadAnimation(DzpkGameRoomActivity.this, R.anim.left_out);
		rightIn=AnimationUtils.loadAnimation(DzpkGameRoomActivity.this, R.anim.right_in);
		rightOut=AnimationUtils.loadAnimation(DzpkGameRoomActivity.this, R.anim.right_out);
		AnimationListener listener =new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				 setCurrentRoomName();
				 setFilpperBtnEnabled(true);
				 if(rv !=null){
					 rv.setScrollEnable(false);
					 //��������
					 rv.loadData();
				 }
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			   
		   };
		rightOut.setAnimationListener(listener);
		rightIn.setAnimationListener(listener);
	}
	private OnClickListener listener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			   if(v.equals(prev)){
				   prevClick();
			   }else if(v.equals(next)){
				   nextClick();
			   }
		}
		
	};
	private void prevClick(){
	   flipper.setInAnimation(leftIn);
	   flipper.setOutAnimation(leftOut);
	   leftOut.setAnimationListener(new AnimationListener(){

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			  
			setCurrentRoomName();
			setFilpperBtnEnabled(true);
			 if(rv !=null){
				 rv.setScrollEnable(false);
				 //��������
				 rv.loadData();
			 }
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		   
	   });
	   flipper.showPrevious();
	   setCurrentRoomNameAndData();
	   setFilpperBtnEnabled(false);
	}
	private void nextClick(){
	   flipper.setInAnimation(rightIn);
	   flipper.setOutAnimation(rightOut);
	   flipper.showNext();
	   setCurrentRoomNameAndData();
	   setFilpperBtnEnabled(false);
	}
	/**
	 * ���ð�ť����״̬
	 * @param enabled
	 */
	private void setFilpperBtnEnabled(boolean enabled){
		prev.setEnabled(enabled);
		next.setEnabled(enabled);
	}
	  
	/**
	 * �����û���Ϣѡ��Ĭ�ϵĳ���
	 */
	private void chooseDefaultRoom(){
		 if(GameApplication.userInfo ==null){
			 return;
		 }
		 //��Ҫ���ƴ���Ϸ�з��ش���������ʾ֮ǰѡ��ķ���,���ǰ���˵��з�������Ҫ����ѡ����ѷ���
		 if( GameApplication.tab  == -1){
			 int gold=(Integer)GameApplication.userInfo.get("gold");
		      
				if(gold<1500){
					GameApplication.tab =1;
				}else if(gold <10000){
					GameApplication.tab=2;
				}else if(gold <500000){
					GameApplication.tab=3;
				}else{
					GameApplication.tab=4;
				}
		 }
	    flipper.setDisplayedChild(GameApplication.tab-1);
	    setCurrentRoomNameAndData();
	}
	 RoomView rv=null;
	 /**
	  * ���÷�����������
	  */
	 private void setCurrentRoomName(){
		 RoomView temp=getCurrentRoomView();
		 setCurrentRoomTypeName(temp);
	 }
	 /**
	  * ���÷����������Ƽ�����
	  */
	 private void setCurrentRoomNameAndData(){
		 RoomView temp=getCurrentRoomView();
		    setCurrentRoomTypeName(temp);
		    setCurrentRoomData(temp);
	 }
	/**
	 * ���õ�ǰ������������
	 */
	private RoomView getCurrentRoomView(){
		 int index =flipper.getDisplayedChild();
		 RoomView temp=(RoomView) flipper.getChildAt(index);
		 return temp;
//		 setCurrentRoomTypeName(temp);
//		 setCurrentRoomData(temp);
		
	}
	/**
	 * ���ط�������
	 * @param rv
	 */
	private void setCurrentRoomData(RoomView temp){
		if(rv !=null){
			 rv.setScrollEnable(true);
		 }else{
			 //��һ����ʾ������
			 temp.setScrollEnable(false);
			 temp.loadData();
		 }
		 rv = temp;
		 //��������
		 //rv.loadData();
	}
	/**
	 * ���÷�����������
	 * @param rv
	 */
	private void setCurrentRoomTypeName(RoomView rv){
		 if(rv == null)return;
		 switch(rv.getRoomTypeId()){
		 case 1:
			 roomType.setText("���ֳ� ");
			 GameApplication.tab=1;
			 break;
		 case 2:
			 roomType.setText("��ͨ��  ");
			 GameApplication.tab=2;
			 break;
		 case 3:
			 roomType.setText("���ֳ�  ");
			 GameApplication.tab=3;
			 break;
		 case 4:
			 roomType.setText("ר�ҳ�  ");
			 GameApplication.tab=4;
			 break;
//		 case 5:
//			 roomType.setText("���ٳ�  ");
//			 break;
//		 case 6:
//			 roomType.setText("������ "); 
//			 break;
		 }
	}
	
	private void displaySetDialog(){
		DisplayDialog display =new DisplayDialog(this,R.style.dialog);
		display.show();
	}
	/**
	 * ������ʾ����
	 */
	public void executeDispalySetEvent(){
		 int index =flipper.getDisplayedChild();
		 RoomView rv=(RoomView) flipper.getChildAt(index);
		 rv.executeDisplayDeskInfo();
	}
	/**
	 * ���������¼�
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	   //GameUtil.backExit(DzpkGameRoomActivity.this);
	    	   back();
	    	   return true;
	       }
	  return super.onKeyDown(keyCode, event);
	} 
	@Override
	protected void onDestroy() {
		 
		super.onDestroy();
		
		 Log.i("test1","GameRoom onDestroy");
		 GameUtil.recycle(hall_button1);
		 GameUtil.recycle(hall_button2);
		 GameUtil.recycle(hall_back);
		 GameUtil.recycle(hall_pageLeft1);
		 GameUtil.recycle(hall_pageLeft2);
		 GameUtil.recycle(hall_pageRight1);
		 GameUtil.recycle(hall_pageRight2);
		 GameUtil.recycle(hall_btnPrev1);
		 GameUtil.recycle(hall_btnPrev2);
		 GameUtil.recycle(hall_btnNext1);
		 GameUtil.recycle(hall_btnNext2);
		 GameUtil.recycle(hall_btnBegin1);
		 GameUtil.recycle(hall_btnBegin2);
			 
		 GameUtil.recycle(hall_listtitle);
		 GameUtil.recycle(hall_bottombg);
		 GameUtil.recycle(hall_titlebg);
		 GameUtil.recycle(hall_listbg);
		 int index =flipper.getDisplayedChild();
		 RoomView rv=(RoomView) flipper.getChildAt(index);
		 rv.dismiss();
		 System.gc();
		   
	}
	Handler handler  = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			   //ѡ��Ĭ�ϳ�
	        chooseDefaultRoom();
		}
	};
	 /**
	  * �˴���������̵߳Ļ���������⡣
	  * ��������:
	  * �赱ǰ����ΪB.
	  * �����A��������B���� A.finish();A�����onDestroy()����������ͣ���á�ֱ����B������б�Ķ����Ż����
	  * 
	  */
	private void loadData(){
		Thread t=new Thread(){
			public void run() {
				super.run();
				handler.sendEmptyMessage(0);
			}
		};
		 
		t.start();
	}
	boolean restart =false;
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		restart =true;
		Log.i("test5", "onRestart 1");
	}
	@Override
	protected void onResume() {
		super.onResume();
		MediaManager.getInstance().playMedia(MediaManager.mame2);
		if(restart){
			restart =false;
			//����ˢ��
			TaskManager.getInstance().execute(new TaskExecutorAdapter(){
 
				public int executeTask() throws Exception {
					GameApplication.getSocketService().sendRefresh();
					return 0;
				}
			});
			return;
		}
		Intent it =this.getIntent();
		int jiuji =it.getIntExtra("jiuji", -1);
		if(jiuji ==1){
			it.putExtra("jiuji",-1);
			int cangive=it.getIntExtra("cangive",-1);
			int lqcishu=it.getIntExtra("lqcishu",-1);
			HwgCallBack callback = new HwgCallBack() {
				public void CallBack(Object... obj) {
					if(obj == null || obj.length ==0){
						 //ȥ��ֵ
						backToChongZhi();
					}
				}
			};
			if(cangive == -1){
				
				 GameUtil.openMessage1Dialog(DzpkGameRoomActivity.this,GameUtil.msg4,callback,GameUtil.chongZhi);
			}else{
				GameUtil.openMessage1Dialog(DzpkGameRoomActivity.this,GameUtil.msgJiuJi+"\r\n(����"+lqcishu+"��,��3��)",callback,GameUtil.chongZhi,GameUtil.sure);
			}
		}
		 
	}
	 /**
	  * ����ȥ��ֵ
	  */
	private void backToChongZhi(){
		 Intent it =new Intent();
		 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 it.putExtra("chongzhi", true);
		 it.setClass(this, DzpkGameMenuActivity.class);
		 startActivity(it);	
		 finish();
		 overridePendingTransition();
	}
	
	private void overridePendingTransition(){
		 //������
		 overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

	}
}
