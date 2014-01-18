package com.gca;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gca.screens.GameScreen;

public class GCAGame extends Game {
	
	public static final float TARGET_RES_PIX = 720f;
	
	GameScreen gameScreen;
	
	SpriteBatch batch;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(batch);
		setScreen(gameScreen);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
