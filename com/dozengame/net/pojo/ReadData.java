package com.dozengame.net.pojo;

import java.io.UnsupportedEncodingException;

import com.dozengame.util.ConvertUtil;
/**
 * 主要功能：
 * 封装每一个接收的协议指令数据
 * @author hewengao
 */
public class ReadData {

	private byte[] recvBytes; // 收到的数据
	private int readPos = 0; // 读取位置
	private final int HEADER_SIZE = 8; // 读取消息头长度
	private String strCmd=null;
    private boolean isReaded=false;
	public String getStrCmd() {
		 if(isReaded==false){
			 isReaded=true;
		     strCmd=readString();
		 }
		 return strCmd;
	}

	// -------------------------------------------------------------------//
	public ReadData(int dataLen) {
		this.readPos = HEADER_SIZE;
		recvBytes = new byte[dataLen];
	}

	public ReadData(byte[] recvBytes) {
		this.recvBytes = recvBytes;
		this.readPos = HEADER_SIZE;
	}

	// 根据开始位置和结束位置得到一个新的Byte[]
	@SuppressWarnings("unused")
	private byte[] getReadBytes(int beginPos, int endPos) {
		byte[] bytes = new byte[endPos - beginPos];
		int j=0;
		int len =recvBytes.length;
		for (int i = beginPos; i < endPos && i < len; i++) {
			bytes[j++] = recvBytes[i];
		}
		return bytes;
	}

	/**
	 * 读取Int
	 * 
	 * @return
	 */
	public int readInt() {
		int beginPos = readPos;
		readPos = readPos + 4;

		byte[] bytes = getReadBytes(beginPos, readPos);
		return ConvertUtil.byte2int(bytes);

	}

	/**
	 * 读取String
	 * 
	 * @return
	 */
	public String readString() {
		// 先得到字符串长度
		short strLen = readShort();
		int beginPos = readPos;
		readPos = readPos + strLen;

		byte[] bytes = getReadBytes(beginPos, readPos);

		String str = "";

		try {
			str = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 读取Short
	 * 
	 * @return
	 */
	public short readShort() {
		int beginPos = readPos;
		readPos = readPos + 2;
		byte[] bytes = getReadBytes(beginPos, readPos);
		return ConvertUtil.byte2Short(bytes);
	}

	/**
	 * 读取Byte
	 * 
	 * @return
	 * @throws Exception
	 */
	public byte readByte() {
		int beginPos = readPos;
		readPos = beginPos + 1;
		byte[] bytes = getReadBytes(beginPos, readPos);
		return bytes[0];
	}

	public void addByte(int index, byte bt) {
		recvBytes[index] = bt;
	}
	public void clear(){
		recvBytes=null;
		strCmd=null;
	}
}
