package com.gca.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gca.models.projectiles.Arrow;
import com.gca.models.projectiles.Spell;
import com.gca.screens.GameScreen;
import com.gca.utils.CollisionDetector;
import com.gca.utils.KeyHandler.KeyCallback;
import com.gca.utils.Timeable;

public class WizardGroup implements KeyCallback, Timeable{

	
	private final static int START_LIVES = 4;
	private final float MOVE_SPEED = 300f/GameScreen.PIX_PER_UNIT;
	
	private final List<Wizard> wizards;
	private final Vector2 moveSpeed;	

	float lastResponseTimeStamp;
	
	private final Random random;
	
	int direction;
	float height;
	SpellCallback callback;
	
	private final float accelX;
	private final float accelY;
	
	public WizardGroup(float midX, SpellCallback callback, float accelX, float accelY) {
		wizards = new LinkedList<Wizard>();
		float y = (START_LIVES - 1) * Wizard.SIZE + 1f;
		for (int i = 0; i < START_LIVES; i++) {
			final Wizard wizard = new Wizard(midX - Wizard.SIZE/2f + (i % 2) * 0.3f -0.15f, y, Wizard.SIZE, Wizard.SIZE, i*0.035f, y, height - i*Wizard.SIZE);
			wizards.add(wizard);
			y-=Wizard.SIZE;
		}
		moveSpeed = new Vector2(MOVE_SPEED, 150f/GameScreen.PIX_PER_UNIT);
		random = new Random();
		lastResponseTimeStamp = 0;
		this.callback = callback;
		this.accelX =accelX;
		this.accelY =accelY;
	}
	
	public List<Wizard> getWizards() {
		return wizards;
	}
	
	public boolean detectCollision(Arrow arrow) {
		Iterator<Wizard> wizardIt = wizards.iterator();
		while (wizardIt.hasNext()) {
			Wizard wizard = wizardIt.next();
			boolean hurt = wizard.hurt;
		    if (CollisionDetector.wizardHit(wizard, arrow)) {
		    	if (wizard.onHit(arrow)) wizardIt.remove();
		    	return (!hurt && wizard.hurt);
		    }
		}
		return false;
	}
	
	public boolean detectCollision(Obstacle obstacle) {
		Iterator<Wizard> wizardIt = wizards.iterator();
		while (wizardIt.hasNext()) {
			Wizard wizard = wizardIt.next();
			boolean hurt = wizard.hurt;
		    if (CollisionDetector.wizardHit(wizard, obstacle)) {
		    	if (wizard.onHit(obstacle)) wizardIt.remove();
		    	return (!hurt && wizard.hurt);
		    }
		}
		return false;
	}
	
	public int size() {
		return wizards.size();
	}
	
	public Wizard getRandomWizard() {
		int index = random.nextInt(size());
		return wizards.get(index);
	}
	
	@Override
	public void addTime(float delta) {
		lastResponseTimeStamp +=delta;
		float accX = 0;
		if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer) && lastResponseTimeStamp > 0.1f) {
			accX = Gdx.input.getAccelerometerX();
			
			if (accX > 1.1f) {
				if (direction != Wizard.LEFT) {
					direction = Wizard.LEFT;
					lastResponseTimeStamp = 0;
				}
			} else if (accX < - 1.1f) {
				if (direction != Wizard.RIGHT) {
					direction = Wizard.RIGHT;
					lastResponseTimeStamp = 0;
				}
			} else {
				if (direction != Wizard.STILL) {
					direction = Wizard.STILL;
					lastResponseTimeStamp = 0;
				}
			}
//			
//			Gdx.app.log("Y: " ,Float.toString(Gdx.input.getAccelerometerY()));
//			
//			if (Gdx.input.getAccelerometerY() > 9f ) {
//				y = 1;
//			} else if (Gdx.input.getAccelerometerY() < 7.5f) {
//				y = -1;
//			} else {
//				y = 0;
//			}
//			
		}
		for (Wizard wizard: wizards) {
			if (wizard.responseThreshold <= lastResponseTimeStamp) {
				wizard.direction = direction;
			}
			int x = 0;
			if (wizard.direction == Wizard.LEFT) x = -1;
			if (wizard.direction == Wizard.RIGHT) x = 1;
			wizard.move(x * moveSpeed.x * delta, 0);
			wizard.addTime(delta);
			if (wizard.position.x < GameScreen.GRASS_BORDER_LEFT) wizard.position.x = GameScreen.GRASS_BORDER_LEFT;
			else if (wizard.position.x + Wizard.SIZE > GameScreen.GRASS_BORDER_RIGHT) wizard.position.x = GameScreen.GRASS_BORDER_RIGHT - Wizard.SIZE;
		}
	}
	
	@Override
	public void onLeftDown() {
		direction = Wizard.LEFT;
		lastResponseTimeStamp = 0;
	}

	@Override
	public void onLeftUp() {
		direction = Wizard.STILL;
		lastResponseTimeStamp = 0;
	}

	@Override
	public void onRightDown() {
		direction = Wizard.RIGHT;
		lastResponseTimeStamp = 0;
	}

	@Override
	public void onRightUp() {
		direction = Wizard.STILL;
		lastResponseTimeStamp = 0;
	}
	
	@Override
	public void onTouch(float x, float y) {
		callback.useSpell(x, y);
	}
		
	public interface SpellCallback {
		void useSpell(float x, float y);
	}
	
	public void cast(float targetX, float targetY, List<Spell> addTo) {
		for (Wizard wizard: wizards) {
			float distance = wizard.position.dst(targetX, targetY);		
			float distX = targetX - wizard.position.x;
			float angle = (float) Math.acos(distX/distance);
			Vector2 spellVelocity = new Vector2(MathUtils.cos(angle) * Spell.VELOCITY, MathUtils.sin(angle) * Spell.VELOCITY);
			if (targetY - wizard.position.y < 0) {
				spellVelocity.y *= -1;
				if (distX < 0) angle = MathUtils.PI2 - angle;
				else angle = - angle;
			}
			addTo.add(new Spell(wizard.position.x + Wizard.SIZE/2f - Spell.FIRE_WIDTH/2f - 0.25f, wizard.position.y + Wizard.SIZE/2f - Spell.FIRE_HEIGHT/2f - 0.25f, Spell.LENGTH, spellVelocity, angle, 1, 1));
		}
	}
	
}
