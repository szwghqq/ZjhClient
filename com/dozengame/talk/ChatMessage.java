package com.dozengame.talk;

public class ChatMessage {
	String msg;//说话内容
	String talkNick;//名称
	boolean isSelf = false;//是否自己

	public String getTalkNick() {
		return talkNick;
	}

	public void setTalkNick(String talkNick) {
		this.talkNick = talkNick;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		String result ="";
		 if(isSelf){
			 result ="我："+msg;
		 }else{
			 result =talkNick+"："+msg;
		 }
		return result;
	}
}
