package com.gca.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.gca.GCAGame;
import com.gca.models.Orc;
import com.gca.models.Wizard;
import com.gca.models.WizardGroup;
import com.gca.models.projectiles.Arrow;
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
	
	private static final float ORC_RESPAWN_TIMER = 2f;
	
	private final List<Orc> orcs;
	private final List<Arrow> arrows;
	private final WizardGroup wizards;
	
	private float timeSinceOrcSpawn = 0;
	private final Random random;
	
	public GameScreen(SpriteBatch batch) {
		super(batch);
		this.camera = new OrthographicCamera();
		this.orcs = new ArrayList<Orc>();
		this.arrows = new ArrayList<Arrow>();
		this.wizards = new WizardGroup(5f);
		this.random = new Random(); 
	}
	
	@Override
	public void addTime(float delta) {
		super.addTime(delta);
		
		wizards.addTime(delta);
		
		if (timeSinceOrcSpawn >= ORC_RESPAWN_TIMER) {
			timeSinceOrcSpawn = 0;
			spawnOrc();
		} else timeSinceOrcSpawn += delta;
		
		for (Orc orc: orcs) {
			orc.addTime(delta);
			if (orc.shootArrow()) {
				Wizard target = pickTarget();
				float distX = target.position.x - orc.position.x;
				float distY = target.position.y - orc.position.y;
				arrows.add(new Arrow(orc.position.x, orc.position.y, (float) Math.atan(distX/distY)));
			}
		}
		
		for (Arrow arrow: arrows) arrow.addTime(delta);
	}

	@Override
	public void draw(float delta) {
		super.draw(delta);
		batch.setProjectionMatrix(camera.combined);
	}
	
	public Wizard pickTarget() {
		return wizards.getRandomWizard();
	}
	
	public void spawnOrc() {
		float x = random.nextDouble() < 0.5 ? -1f : VIEWPORT_WIDTH + 1;
		float y = random.nextFloat() * camera.viewportHeight;
		orcs.add(new Orc(x, y));
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
