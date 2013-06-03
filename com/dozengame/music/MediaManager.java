package com.dozengame.music;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.dozengame.GameApplication;
import com.dozengame.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class MediaManager {
	 /** 
     * 声音开关 背景音乐 
     */  
    private boolean blnOpenBgSound =false;   
      
    /** 
     * 声音开关 特效音乐 
     */  
    private boolean blnOpenEffectSound = true;   
      
    /** 
     * media 背景音乐 
     */  
    public static final int STATIC_MEDIA_TYPE_BGSOUND = 0;  
      
    public static final int STATIC_MEDIA_TYPE_COUNT = STATIC_MEDIA_TYPE_BGSOUND + 1;  
      
    private int[] mediaListID = {
    		//R.raw.music,
           // R.raw.mame2 
    };  
      
    /** 
     * sound 
     */  
    public static final int STATIC_SOUND_TYPE_DINGDONG = 0;  
      
    public static final int STATIC_SOUND_TYPE_COUNT = STATIC_SOUND_TYPE_DINGDONG + 1;  
    public static final int booted  =0;
    public static final int call2  =1;
    public static final int check2  =2;
    public static final int deal2  =3;
    public static final int flip_a  =4;
    public static final int flip_b  =5;
    public static final int flip_c  =6;
    public static final int fold4  =7;
    public static final int raise  =8;
    public static final int turnstart  =9;
    public static final int turnreminder  =10;
    public static final int winchips  =11;
      
    public static final int mame2  =0;
    private int[] soundListID = {  
             R.raw.booted, //自己没钱后，弹出买筹码窗
             R.raw.call2, //跟注声音
             R.raw.check2,//过牌
             R.raw.deal2,//发牌声音
             R.raw.flip_a,//发前三张公共牌的音效
             R.raw.flip_b,//发第四张公共牌的音效
             R.raw.flip_c,//发第五张公共牌的音效
             R.raw.fold4,//弃牌的声音
             R.raw.raise,//加注的声音
             R.raw.turnstart,//轮到自己的声音
             R.raw.turnreminder,//自己时间过半的声音
             R.raw.winchips  //从底池拿到筹码的声音
    };  
      
      
    private final int maxStreams = 10; //streamType音频池的最大音频流数目为10    
    private final int srcQuality = 100;  
    private final int soundPriority = 1;  
    private final float soundSpeed = 1f;//播放速度 0.5 -2 之间   
      
    /** 
     * 游戏音效 
     */  
   private SoundPool soundPool;  
   private HashMap<Integer ,MediaPlayer> mediaMap;
   private HashMap<Integer ,Integer>  soundPoolMap;
      
    private static MediaManager mediaManager;  
      
    private MediaManager(){  
        initMediaPlayer();  
        initSoundPool();  
    }  
      
    /*** 
     * 实例MediaManager 
     * @return 
     */  
    public static MediaManager getInstance(){  
          
          
          
        if(mediaManager == null){  
            mediaManager = new MediaManager();  
        }  
        return mediaManager;  
    }  
      
    /*** 
     * 是否开启背景音乐 
     */  
    public void setOpenBgState(boolean bgSound){  
        blnOpenBgSound = bgSound;  
        if(!bgSound && mediaMap != null){  
        	int size= mediaMap.size();
            for (int i = 0; i < size; i++) {  
                mediaMap.get(i).pause();  
            }  
        }  
    }  
    /*** 
     * 是否开启特效音乐 
     */  
    public void setOpenEffectState(boolean effectSound){  
        blnOpenEffectSound = effectSound;  
        if(!effectSound && soundPoolMap != null){  
        	int size = soundPoolMap.size();
            for (int i = 0; i < size; i++) {  
                soundPool.pause(soundPoolMap.get(i));  
            }  
        }  
    }  
      
      
    private void initMediaPlayer(){  
        mediaMap = new HashMap<Integer,MediaPlayer>();
        int len =mediaListID.length;
        for (int i = 0; i < len; i++) {  
            MediaPlayer mediaPlayer = MediaPlayer.create(GameApplication.currentActivity, mediaListID[i]);  
            mediaMap.put(i, mediaPlayer);  
        }  
    }  
      
    private void initSoundPool(){  
        soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, srcQuality);  
        soundPoolMap = new HashMap<Integer,Integer>();
        int len=soundListID.length;
        for (int i = 0; i < len; i++) {  
            soundPoolMap.put(i, soundPool.load(GameApplication.currentActivity, soundListID[i], soundPriority));  
        }  
    }  
      
    /** 
     * 播放MediaPlayer音乐 
     */  
    public void playMedia(int mediaType){  
        if(!blnOpenBgSound){  
            return;  
        }  
        MediaPlayer mediaPlayer = mediaMap.get(mediaType);  
        if(!mediaPlayer.isPlaying()){
        	mediaPlayer.setLooping(true); 
            mediaPlayer.start();  
        }  
    }  
      
    /** 
     * 暂停MediaPlayer音乐 
     */  
    public void pauseMedia(int mediaType){  
        MediaPlayer mediaPlayer = mediaMap.get(mediaType); 
        if(mediaPlayer == null)return;
        if(mediaPlayer.isPlaying()){  
            mediaPlayer.pause();  
        }  
    }  
      
    /** 
     * 播放soundPlayer音乐 
     */  
    public void playSound(int soundID, int loop){  
        if(!blnOpenEffectSound){  
            return;  
        }  
        AudioManager audioManager = (AudioManager)GameApplication.currentActivity.getSystemService(Context.AUDIO_SERVICE);  
        float streamVolumeCurrent = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);  
        float streamVolumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
        float volume = streamVolumeCurrent / streamVolumeMax;  
        soundPool.play(soundPoolMap.get(soundID), volume, volume, soundPriority, loop, soundSpeed);
//        soundID= soundPool.load(GameApplication.currentActivity, soundID, soundPriority);
//        soundPool.play(soundID, volume, volume, soundPriority, loop, soundSpeed);
      
    }  
    /** 
     * 播放soundPlayer音乐 ,默认播放一次
     */  
    public void playSound(int soundID){ 
    	playSound(soundID,0);
    }
    /** 
     * 播放soundPlayer音乐 
     */  
    public void playSound(String path, int loop){  
        if(!blnOpenEffectSound){  
            return;  
        }  
        AudioManager audioManager = (AudioManager)GameApplication.currentActivity.getSystemService(Context.AUDIO_SERVICE);  
        float streamVolumeCurrent = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);  
        float streamVolumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
        float volume = streamVolumeCurrent / streamVolumeMax;  
         
        //soundPool.play(soundPoolMap.get(soundID), volume, volume, soundPriority, loop, soundSpeed);
        int soundID= soundPool.load(path, soundPriority);
        soundPool.play(soundID, volume, volume, soundPriority, loop, soundSpeed);
    }  
    /** 
     * 播放soundPlayer音乐 
     */  
    public void playSound(String path){  
        if(!blnOpenEffectSound){  
            return;  
        }  
        AudioManager audioManager = (AudioManager)GameApplication.currentActivity.getSystemService(Context.AUDIO_SERVICE);  
        float streamVolumeCurrent = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);  
        float streamVolumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
        final float volume = streamVolumeCurrent / streamVolumeMax;  
        //soundPool.play(soundPoolMap.get(soundID), volume, volume, soundPriority, loop, soundSpeed);
        final int soundID= soundPool.load(path, soundPriority);
        soundPool.play(soundID, volume, volume, soundPriority, 0, soundSpeed);
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				soundPool.play(soundID, volume, volume, soundPriority, 0, soundSpeed);
			}
		});
    }  
      
    /** 
     * 暂停soundPlayer音乐 
     */  
    public void pauseSound(int soundID){  
        soundPool.pause(soundPoolMap.get(soundID));  
    }

	public void pauseSoundAll() {
		// TODO Auto-generated method stub
		if(soundPoolMap == null)return;
		 Set<Integer> set= soundPoolMap.keySet();
		 soundPool.autoPause();
		 if(set != null){
			 Iterator<Integer> iterator= set.iterator();
			 while(iterator.hasNext()){
				 soundPool.stop(iterator.next());
			 }
		 }
		
	}  
}  
//在使用过程中给出用法

//初始化游戏声音   
//MediaManager.getInstance();  //可以做为初始化   
  
//播放MediaPlayer音频   
//(MediaManager.getInstance().playMedia(MediaManager.STATIC_MEDIA_TYPE_BGSOUND);   
//播放soundPool音频   
 
//MediaManager.getInstance().playSound(MediaManager.STATIC_SOUND_TYPE_DINGDONG);  
//}
