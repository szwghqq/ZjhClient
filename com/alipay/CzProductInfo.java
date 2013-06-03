package com.alipay;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
/**
 * 充�?产品信息
 * @author hewengao
 *
 */
public class CzProductInfo implements Serializable {

	int productId;   //产品标识
	String productName; //产品名称
	String productImgPath;//产品图片路径
	Bitmap productBitmap;//产品图片
	int productPrice;
	
	Drawable drawable=null;
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImgPath() {
		return productImgPath;
	}
	public void setProductImgPath(String productImgPath) {
		this.productImgPath = productImgPath;
	}
	public Bitmap getProductBitmap() {
		return productBitmap;
	}
	public void setProductBitmap(Bitmap productBitmap) {
		this.productBitmap = productBitmap;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public Drawable getDrawable(){
		
		if(drawable ==null){
			if(productBitmap ==null){
				drawable= null;
			}
			else{
				drawable=new BitmapDrawable(productBitmap);
			}
		}
		return drawable;
	}
	 
}