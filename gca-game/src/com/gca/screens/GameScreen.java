package com.gca.screens;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gca.GCAGame;
import com.gca.MenuScreen;
import com.gca.models.Obstacle;
import com.gca.models.Orc;
import com.gca.models.Wizard;
import com.gca.models.WizardGroup;
import com.gca.models.Orc.EliteOrc;
import com.gca.models.projectiles.Arrow;
import com.gca.models.projectiles.Spell;
import com.gca.utils.CollisionDetector;
import com.gca.utils.KeyHandler;

public class GameScreen extends AbstractScreen {
	
	public static final float VIEWPORT_WIDTH = 6f;
	public static final float PIX_PER_UNIT = GCAGame.TARGET_RES_PIX/VIEWPORT_WIDTH;
	
	private static final float MAIN_SEG_SIZE = 100f/PIX_PER_UNIT;
	private static final float MID_SEG_SIZE = 25f/PIX_PER_UNIT;
	private static final float RIVER_SIZE = 4 * MAIN_SEG_SIZE + 3 * MID_SEG_SIZE;
	private static final float GRASS_BORDER_SIZE = (VIEWPORT_WIDTH - RIVER_SIZE)/2f;
	
	public static final float GRASS_BORDER_LEFT = GRASS_BORDER_SIZE - 11f/PIX_PER_UNIT;
	public static final float GRASS_BORDER_RIGHT = VIEWPORT_WIDTH - GRASS_BORDER_SIZE + 11f/PIX_PER_UNIT;;
	
	public final OrthographicCamera camera;
	
	private final List<Orc> orcs;
	private final List<Orc> deadOrcs;
	private final List<Wizard> deadWizards;
	private final List<Obstacle> deadObs;
	private final List<Arrow> arrows;
	private final List<Spell> spells;
	private final List<Obstacle> obstacles;
	private WizardGroup wizards;
	
	private float timeSinceOrcSpawn = 0;
	private float orcSpawnTime;
	private float timeSinceObSpawn = 0;
	private float obSpawnTime;
	private final Random random;
	
	protected Renderer renderer;
	
	public GameScreen(SpriteBatch batch) {
		super(batch);
		this.camera = new OrthographicCamera();
		this.orcs = new LinkedList<Orc>();
		this.arrows = new LinkedList<Arrow>();
		this.spells = new LinkedList<Spell>();
		this.obstacles = new LinkedList<Obstacle>();
		this.random = new Random(); 
		this.deadOrcs = new LinkedList<Orc>();
		this.orcSpawnTime = 2f; 
		this.deadWizards = new LinkedList<Wizard>();
		this.deadObs = new LinkedList<Obstacle>();
		float x = 0;
		float y = 0;
		
		if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) {
			x = Gdx.input.getAccelerometerX();
			y = Gdx.input.getAccelerometerY();
		}
		this.wizards = new WizardGroup(VIEWPORT_WIDTH/2f, new WizardGroup.SpellCallback() {
			@Override
			public void useSpell(float x, float y) {
				wizards.cast(x, y, spells);
			}
		}, x, y);
		
	}
	
	@Override
	public void addTime(float delta) {
		
		super.addTime(delta);
		
		wizards.addTime(delta);
		
		Iterator<Spell> spellIt = spells.iterator();
		while (spellIt.hasNext()) {
			Spell spell = spellIt.next();
		    if (notInBounds(spell.position)) spellIt.remove();
		    else {
		    	spell.addTime(delta);
		    	if (detectCollision(spell)) spellIt.remove();
		    }
		}
		
		if (timeSinceOrcSpawn >= orcSpawnTime) {
			timeSinceOrcSpawn = 0;
			orcSpawnTime = random.nextFloat() + 1;
			spawnOrc();
		} else timeSinceOrcSpawn += delta;
		
		if (timeSinceObSpawn >= obSpawnTime) {
			timeSinceObSpawn = 0;
			obSpawnTime = random.nextInt(5) + 2;
			spawnObstacle();
		} else timeSinceObSpawn += delta;
		
		for (Orc orc: orcs) {
			orc.addTime(delta);
			if (orc.shootArrow()) {
				float preciseProb = 0.25f;
				if (orc instanceof EliteOrc) preciseProb = 0.75f;
				if (random.nextDouble() <= preciseProb) {
					preciseArrow(orc);
				} else {
					float angle =  (float) Math.atan(random.nextDouble());
					if (orc.position.x > VIEWPORT_WIDTH/2f) 
						angle += MathUtils.PI;
					orc.angle = angle;
					Vector2 arrowVelocity = new Vector2(Arrow.VELOCITY * MathUtils.cos(angle), Arrow.VELOCITY * MathUtils.sin(angle));
					float posX =  orc.position.x + Orc.ORC_WIDTH/2f - Arrow.ARROW_WIDTH/2f;
					arrows.add(new Arrow(posX, orc.position.y + Orc.ORC_HEIGHT/2f - Arrow.ARROW_HEIGHT/2f + 0.15f, arrowVelocity, angle, orc.getDamage()));
				}
			}
		}
		
		Iterator<Arrow> arrowIt = arrows.iterator();
		while (arrowIt.hasNext()) {
			Arrow arrow = arrowIt.next();
		    if (notInBounds(arrow.position)) arrowIt.remove();
		    else {
		    	arrow.addTime(delta);
		    	if (wizards.detectCollision(arrow, deadWizards)) arrowIt.remove();
		    }
		}
		
		Iterator<Obstacle> obstacleIt = obstacles.iterator();
		while (obstacleIt.hasNext()) {
			Obstacle obstacle = obstacleIt.next();
		    if (notInBounds(obstacle.position)) {
		    	obstacleIt.remove();
		    }
		    else {
		    	obstacle.addTime(delta);
		    	if (wizards.detectCollision(obstacle, deadWizards)) {
		    		deadObs.add(obstacle);
		    		obstacleIt.remove();
		    	}
		    }
		}
		
		Iterator<Orc> orcIt = deadOrcs.iterator();
		while (orcIt.hasNext()) {
			Orc orc = orcIt.next();
			orc.move(0, -delta * Renderer.SPEED);
			orc.deadOpacity -= delta * 0.5f;
			if (orc.deadOpacity < 0) orcIt.remove();
		}
		
		Iterator<Obstacle> obIt = deadObs.iterator();
		while (obIt.hasNext()) {
			Obstacle ob = obIt.next();
			if (ob.type == 1 || ob.type == 3) ob.position.y -= Renderer.SPEED * delta;
			else ob.position.y -= Obstacle.MOVE_SPEED * delta;
			ob.opacityMult -= delta * 1.5f;
			if (ob.opacityMult < 0) obIt.remove();
		}
		
		Iterator<Wizard> wizardIt = deadWizards.iterator();
		while (wizardIt.hasNext()) {
			Wizard w = wizardIt.next();
			w.deathOpacity -= delta * 0.5f;
			if (w.deathOpacity < 0f) w.deathOpacity = 0f;
		}
		
	}
	
	public void spawnObstacle() {
		float x = random.nextInt((int) (RIVER_SIZE - GRASS_BORDER_SIZE)) + 1.4f * GRASS_BORDER_SIZE ;
		float y = camera.viewportHeight + 1f;
		Obstacle ob;
		if (random.nextDouble() < 0.3f) ob = new Obstacle(x, y, 1);
		else if (random.nextDouble() < 0.5f) ob = new Obstacle(x, y, 2);
		else if (random.nextDouble() < 0.7f) ob = new Obstacle(x, y, 3);
		else if (random.nextDouble() < 0.9f) ob = new Obstacle(x, y, 4);
		else  ob = new Obstacle(x, y, 5);
		obstacles.add(ob);
	}

	@Override
	public void draw(float delta) {
		super.draw(delta);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		renderer.setSpriteBatch(batch);
		renderer.water();
		renderer.bubble(delta, wizards);
		renderer.grass();
		renderer.cliff(delta);
		renderer.obstacles(deadObs);
		renderer.obstacles(obstacles);
		renderer.orcs(deadOrcs);
		renderer.orcs(orcs);
		renderer.wizards(deadWizards);
		renderer.wizards(wizards);
		renderer.arrows(arrows);
		renderer.spells(spells);
		if (!(this instanceof MenuScreen)) {
			renderer.spells();
		}
		batch.end();
	}
	
	public void spawnOrc() {
		float x = random.nextDouble() < 0.5 ? 0 : VIEWPORT_WIDTH - 100f/GameScreen.PIX_PER_UNIT;
		float y = -1f;
		Orc newOrc;
		if (random.nextDouble() < 0.1f) newOrc = new Orc.EliteOrc(x, y);
		else newOrc = new Orc(x, y);
		newOrc.setDestination(newOrc.position.x, random.nextFloat() * camera.viewportHeight * 0.9f);
		orcs.add(newOrc);
	}
	
	public void preciseArrow(Orc orc) {
		Wizard target = wizards.getRandomWizard();
		float distance = orc.position.dst(target.position);		
		float distX = Math.abs(orc.position.x - target.position.x);
		float angle = (float) Math.acos(distX/distance);
		if (orc.position.x < target.position.x && orc.position.y > target.position.y) {
			angle = MathUtils.PI2 - angle;
		} else if (orc.position.x > target.position.x && orc.position.y < target.position.y) {
			angle = MathUtils.PI - angle;
		} else if (orc.position.x > target.position.x && orc.position.y > target.position.y) {
			angle = MathUtils.PI + angle;
		}
		orc.angle = angle;
		Vector2 arrowVelocity = new Vector2(Arrow.VELOCITY * MathUtils.cos(angle), Arrow.VELOCITY * MathUtils.sin(angle));
		float posX =  orc.position.x + Orc.ORC_WIDTH/2f - Arrow.ARROW_WIDTH/2f;
		if (posX > VIEWPORT_WIDTH) posX = VIEWPORT_WIDTH;
		if (posX < 0) posX = 0;
		Arrow a = new Arrow(posX, orc.position.y + Orc.ORC_HEIGHT/2f - Arrow.ARROW_HEIGHT/2f + 0.15f, arrowVelocity, angle, orc.getDamage());
		arrows.add(a);
	}
	
	public boolean detectCollision(Spell spell) {
		Iterator<Orc> orcIt = orcs.iterator();
		while (orcIt.hasNext()) {
			Orc orc = orcIt.next();
		    if (CollisionDetector.orcHit(orc, spell)) {
		    	orc.redFilter = 0.25f;
		    	if (orc.onHit(spell)) {
		    		orcIt.remove();
		    		deadOrcs.add(orc);
		    	}
		    	return true;
		    }
		}
		return false;
	}
	
	
	private boolean notInBounds(Vector2 v) {
		return (v.x > VIEWPORT_WIDTH && v.x < 0 && v.y > camera.viewportHeight && v.y < 0);
	}
	
	@Override
	public void show() {
		float aspectRatio = ((float)Gdx.graphics.getHeight())/Gdx.graphics.getWidth();
		camera.setToOrtho(false, VIEWPORT_WIDTH, aspectRatio * VIEWPORT_WIDTH);
		renderer = new Renderer(VIEWPORT_WIDTH, camera.viewportHeight);
		Gdx.input.setInputProcessor(new KeyHandler(wizards, camera.viewportHeight));
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
