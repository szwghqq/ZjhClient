package com.dozengame.talk;

import android.graphics.drawable.Drawable;

public class BiaoQingDto {

	int id;
	String name;
	Drawable iconDraw;
	public Drawable getIconDraw() {
		return iconDraw;
	}
	public void setIconDraw(Drawable iconDraw) {
		this.iconDraw = iconDraw;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
