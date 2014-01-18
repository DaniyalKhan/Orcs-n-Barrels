package com.gca.utils;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.gca.screens.GameScreen;

public class KeyHandler extends InputAdapter {
	
	private final KeyCallback callback;
	private final float height;
	
	
	public KeyHandler(KeyCallback callback,float height) {
		super();
		this.callback = callback;
		this.height = height;
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
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		float x = screenX/GameScreen.PIX_PER_UNIT;
		float y = height - screenY/GameScreen.PIX_PER_UNIT;
		callback.onTouch(x, y);
		return true;
	}


	public interface KeyCallback {
		void onLeftDown();
		void onLeftUp();
		void onRightDown();
		void onRightUp();
		void onTouch(float x, float y);
	}

}
