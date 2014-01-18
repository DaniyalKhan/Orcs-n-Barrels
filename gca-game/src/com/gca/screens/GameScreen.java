package com.gca.screens;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gca.GCAGame;
import com.gca.models.Obstacle;
import com.gca.models.Orc;
import com.gca.models.Wizard;
import com.gca.models.WizardGroup;
import com.gca.models.projectiles.Arrow;
import com.gca.utils.KeyHandler;

public class GameScreen extends AbstractScreen {
	
	private static final float VIEWPORT_WIDTH = 6f;
	public static final float PIX_PER_UNIT = GCAGame.TARGET_RES_PIX/VIEWPORT_WIDTH;
	
	private static final float MAIN_SEG_SIZE = 100f/PIX_PER_UNIT;
	private static final float MID_SEG_SIZE = 25f/PIX_PER_UNIT;
	private static final float RIVER_SIZE = 4 * MAIN_SEG_SIZE + 3 * MID_SEG_SIZE;
	private static final float GRASS_BORDER_SIZE = (VIEWPORT_WIDTH - RIVER_SIZE)/2f;
	
	private static final float GRASS_BORDER_LEFT = GRASS_BORDER_SIZE;
	private static final float GRASS_BORDER_RIGHT = VIEWPORT_WIDTH - GRASS_BORDER_SIZE;
	
	private final OrthographicCamera camera;
	
	private final List<Orc> orcs;
	private final List<Arrow> arrows;
	private final List<Obstacle> obstacles;
	private final WizardGroup wizards;
	
	private float timeSinceOrcSpawn = 0;
	private float orcSpawnTime;
	private final Random random;
	
	private Renderer renderer;
	
	public GameScreen(SpriteBatch batch) {
		super(batch);
		this.camera = new OrthographicCamera();
		this.orcs = new LinkedList<Orc>();
		this.arrows = new LinkedList<Arrow>();
		this.obstacles = new LinkedList<Obstacle>();
		this.wizards = new WizardGroup(VIEWPORT_WIDTH/2f);
		this.random = new Random(); 
		this.orcSpawnTime = 2f; 
	}
	
	@Override
	public void addTime(float delta) {

//		wizards.print();
		
		super.addTime(delta);
		
		wizards.addTime(delta);
		
		if (timeSinceOrcSpawn >= orcSpawnTime) {
			timeSinceOrcSpawn = 0;
			orcSpawnTime = random.nextFloat() * 2 + 1;
			spawnOrc();
		} else timeSinceOrcSpawn += delta;
		
		for (Orc orc: orcs) {
			orc.addTime(delta);
			if (orc.shootArrow()) {
				Wizard target = wizards.getRandomWizard();
//				float distY = orc.position.y - target.position.y;		
//				float distX = orc.position.x - target.position.x;
//				float angle;
//				if (orc.position.y < target.position.y) angle = (float) Math.acos(distX/distance);
//				else  angle = (float) Math.asin(distX/distance);
//				arrows.add(new Arrow(orc.position.x, orc.position.y, 0));
			}
		}
		
		Iterator<Arrow> arrowIt = arrows.iterator();
		while (arrowIt.hasNext()) {
			Arrow arrow = arrowIt.next();
		    if (notInBounds(arrow.position)) arrowIt.remove();
		    else {
		    	arrow.addTime(delta);
		    	if (wizards.detectCollision(arrow)) arrowIt.remove();
		    }
		}
		
		Iterator<Obstacle> obstacleIt = obstacles.iterator();
		while (obstacleIt.hasNext()) {
			Obstacle obstacle = obstacleIt.next();
		    if (notInBounds(obstacle.position)) obstacleIt.remove();
		    else {
		    	obstacle.addTime(delta);
		    	if (wizards.detectCollision(obstacle)) obstacleIt.remove();
		    }
		}
		
	}

	@Override
	public void draw(float delta) {
		super.draw(delta);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		renderer.setSpriteBatch(batch);
		renderer.water();
		renderer.bubble(delta);
		renderer.grass();
		renderer.cliff(delta);
		renderer.wizards(wizards);
		renderer.orcs(orcs);
		renderer.arrows(arrows);
		batch.end();
	}
	
	private void spawnOrc() {
		float x = random.nextDouble() < 0.5 ? 0 : VIEWPORT_WIDTH - 100f/GameScreen.PIX_PER_UNIT;
		float y = -1f;
		Orc newOrc = new Orc(x, y);
		newOrc.setDestination(newOrc.position.x, random.nextFloat() * camera.viewportHeight * 0.8f);
		orcs.add(newOrc);
	}
	
	private boolean notInBounds(Vector2 v) {
		return (v.x > VIEWPORT_WIDTH && v.x < 0 && v.y > camera.viewportHeight && v.y < 0);
	}
	
	@Override
	public void show() {
		float aspectRatio = ((float)Gdx.graphics.getHeight())/Gdx.graphics.getWidth();
		camera.setToOrtho(false, VIEWPORT_WIDTH, aspectRatio * VIEWPORT_WIDTH);
		renderer = new Renderer(VIEWPORT_WIDTH, camera.viewportHeight);
		Gdx.input.setInputProcessor(new KeyHandler(wizards));
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
