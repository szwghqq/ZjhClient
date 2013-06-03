package com.dozengame.util;

public class Measure {
	/**
	 * 边界检测
	 * @param x  要检测的x坐标
	 * @param y  要检测的y坐标
	 * @param minx 最小的x坐标
	 * @param miny 最小的y坐标
	 * @param maxx 最大的x坐标
	 * @param maxy 最大的y坐标
	 * @return
	 */
	public static boolean isInnerBorder(float x,float y, int minx,int miny,int maxx,int maxy){
		
		if(x<minx || x>maxx || y<miny || y>maxy){
			return false;
		}
		return true;
	}
}
