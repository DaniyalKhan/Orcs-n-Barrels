package com.gca.models;

import com.badlogic.gdx.math.Rectangle;

public class GameModel {

	public float x, y;
	
	private float width, height;
	private Rectangle axisBox;
	
	public GameModel(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.axisBox = new Rectangle(x, y, width, height); 
	}

	public Rectangle getAxisBox() {
		axisBox.x = x;
		axisBox.y = y;
		axisBox.width = width;
		axisBox.height = height;
		return axisBox;
	}
	
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
}
