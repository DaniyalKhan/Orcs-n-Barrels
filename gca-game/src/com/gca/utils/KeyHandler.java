package com.gca.utils;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class KeyHandler extends InputAdapter {
	
	private final KeyCallback callback;
	
	
	public KeyHandler(KeyCallback callback) {
		super();
		this.callback = callback;
	}

	@Override
	public boolean keyDown(int keycode) {
		super.keyDown(keycode);
		if (keycode == Keys.LEFT) callback.onLeftDown();
		if (keycode == Keys.RIGHT) callback.onRightDown();
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		super.keyUp(keycode);
		if (keycode == Keys.LEFT) callback.onLeftUp();
		if (keycode == Keys.RIGHT) callback.onRightUp();
		return true;
	}


	public interface KeyCallback {
		void onLeftDown();
		void onLeftUp();
		void onRightDown();
		void onRightUp();
	}

}
