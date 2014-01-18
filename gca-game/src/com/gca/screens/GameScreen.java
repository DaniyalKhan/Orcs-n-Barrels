package com.gca.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gca.GCAGame;
import com.gca.models.Orc;
import com.gca.models.projectiles.Projectile;

public class GameScreen extends AbstractScreen {
	
	private static final float VIEWPORT_WIDTH = 10f;
	public static final float PIX_PER_UNIT = GCAGame.TARGET_RES_PIX/VIEWPORT_WIDTH;
	
	private final OrthographicCamera camera;
	
	private static final float MAIN_SEG_SIZE = 100f/PIX_PER_UNIT;
	private static final float MID_SEG_SIZE = 25f/PIX_PER_UNIT;
	private static final float RIVER_SIZE = 4 * MAIN_SEG_SIZE + 3 * MID_SEG_SIZE;
	private static final float GRASS_BORDER_SIZE = (VIEWPORT_WIDTH - RIVER_SIZE)/2f;
	
	private static final float GRASS_BORDER_LEFT = GRASS_BORDER_SIZE;
	private static final float GRASS_BORDER_RIGHT = VIEWPORT_WIDTH - GRASS_BORDER_SIZE;
	
	private final List<Orc> orcs;
	private final List<Projectile> arrows;
	private final List<Projectile> spells;
	
	public GameScreen(SpriteBatch batch) {
		super(batch);
		this.camera = new OrthographicCamera();
		this.orcs = new ArrayList<Orc>();
		this.arrows = new ArrayList<Projectile>();
		this.spells = new ArrayList<Projectile>();
	}
	
	@Override
	public void addTime(float delta) {
		super.addTime(delta);
	}

	@Override
	public void draw(float delta) {
		super.draw(delta);
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void show() {
		float aspectRatio = ((float)Gdx.graphics.getHeight())/Gdx.graphics.getWidth();
		camera.setToOrtho(false, VIEWPORT_WIDTH, aspectRatio * VIEWPORT_WIDTH);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
