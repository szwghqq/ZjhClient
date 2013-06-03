package com.dozengame.util;

public class SplitString {
	private String str;

	private int byteNum;

	public SplitString() {
	}

	public SplitString(String str, int byteNum) {
		this.str = str;
		this.byteNum = byteNum;
	}

	public void splitIt() {
		byte bt[] = str.getBytes();

		System.out.println(" Length of this String ===> " + bt.length);

		if (byteNum >= 1) {
			if (bt[byteNum] < 0) {
				String substrx = new String(bt, 0, --byteNum);
				System.out.println(substrx);
			} else {
				String substrex = new String(bt, 0, byteNum);
				System.out.println(substrex);
			}
		} else {
			System.out.println(" 输入错误!!!请输入大于零的整数： ");
		}
	}

}
