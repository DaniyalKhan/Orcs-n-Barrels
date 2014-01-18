package com.gca.models;

import com.badlogic.gdx.math.Vector2;


public class GameModel {

	public final Vector2 position;
	
//	private float width, height;
//	private Rectangle axisBox;
	
	public GameModel(float x, float y) {
		super();
		position = new Vector2(x, y);
//		this.axisBox = new Rectangle(x, y, width, height); 
	}

//	public Rectangle getAxisBox() {
//		axisBox.x = x;
//		axisBox.y = y;
//		axisBox.width = width;
//		axisBox.height = height;
//		return axisBox;
//	}
	
	public void move(float x, float y) {
		position.x += x;
		position.y += y;
	}
	
}
