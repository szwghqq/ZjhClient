package com.dozengame.util;

import java.nio.ByteBuffer;

public class ConvertUtil {

	/**
	 * 将int转成字节数组
	 * @param i
	 * @return
	 */
	public static byte[] intToByteArray(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}
	/**
	 * 将int转成字节数组
	 * @param i
	 * @return
	 */
	public static byte[] intToByteArray2(int i) {
		 
	    ByteBuffer bt=ByteBuffer.allocate(4);
	    bt.putInt(i);
	    bt.flip();
	    byte by[]=  bt.array();
	    bt.clear();
	    bt=null;
	    return by;
	}

	public static int bytesToInt(byte[] intByte) {
		int fromByte = 0;
		int len=intByte.length;
		for (int i = 0; i < len ; i++) {
			int n = (intByte[i] < 0 ? (int) intByte[i] + 256 : (int) intByte[i]) << (8 * i);
			//System.out.println(n);
			fromByte += n;
		}
		return fromByte;
	}

	public static byte[] shortToByte(short i) {
		byte[] result = new byte[2];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
	
		return result;
	}
	
	
	/**
	 * Byte[]转Int
	 * @param Byte[]
	 * @return
	 */
	@SuppressWarnings("unused")
	public static int byte2int(byte[] res) {
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00)
				| ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}
	
	/**
	 * byte[]转Short
	 * @param byte[]
	 * @return
	 */
	@SuppressWarnings("unused")
	public static short byte2Short(byte[] res) {
		short shortNum = (short) ((res[0] & 0xff) | ((res[1] << 8) & 0xff00));
		return shortNum;
	}
	
	/**
	 * short转byte[]
	 * @param short
	 * @return
	 */
	@SuppressWarnings("unused")
	public static byte[] short2byte(short i) {
		byte[] abyte0 = new byte[2];
		abyte0[0] = (byte) (0xff & i);
		abyte0[1] = (byte) ((0xff00 & i) >> 8);
		return abyte0;
	}
	
	/**
	 * int转byte[]
	 * @param int
	 * @return
	 */
	@SuppressWarnings("unused")
	public static byte[] int2byte(int i){
		byte[] abyte0 = new byte[4];
		abyte0[0] = (byte) (0xff & i);
		abyte0[1] = (byte) ((0xff00 & i) >> 8);
		abyte0[2] = (byte) ((0xff0000 & i) >> 16);
		abyte0[3] = (byte) ((0xff000000 & i) >> 24);
		return abyte0;
	}
	public static void main(String args[]){
		  int k=1893;
    	  k=byte2int(int2byte(k));
          System.out.println("k: "+k);
	}

}
