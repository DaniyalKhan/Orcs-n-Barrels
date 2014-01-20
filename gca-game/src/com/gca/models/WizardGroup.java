package com.gca.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gca.models.projectiles.Arrow;
import com.gca.models.projectiles.Spell;
import com.gca.screens.GameScreen;
import com.gca.utils.CollisionDetector;
import com.gca.utils.KeyHandler.KeyCallback;
import com.gca.utils.SoundPlayer;
import com.gca.utils.Timeable;

public class WizardGroup implements KeyCallback, Timeable{

	public static final Rectangle ICE = new Rectangle(0.8f - 0.1f, -1f, 160/GameScreen.PIX_PER_UNIT, 150/GameScreen.PIX_PER_UNIT);
	public static final Rectangle FIRE = new Rectangle(2.35f- 0.1f, -1f, 150/GameScreen.PIX_PER_UNIT, 150/GameScreen.PIX_PER_UNIT);
	public static final Rectangle POISON = new Rectangle(3.8f- 0.1f, -1f, 150/GameScreen.PIX_PER_UNIT, 150/GameScreen.PIX_PER_UNIT);
	
	private final static int START_LIVES = 4;
	private final float MOVE_SPEED = 400f/GameScreen.PIX_PER_UNIT;
	
	private final List<Wizard> wizards;
	private final Vector2 moveSpeed;	

	float lastResponseTimeStamp;
	float lastResponseTimeStampY;
	
	private final Random random;
	
	public int numSpells[];
	
	public float regenTimer = 0;
	
	int direction;
	int direction2;
	float height;
	SpellCallback callback;
	
	int currentSpell;
	
	public WizardGroup(float midX, SpellCallback callback, float height) {
		wizards = new LinkedList<Wizard>();
		float y = (START_LIVES - 1) * Wizard.SIZE + 1.3f;
		for (int i = 0; i < START_LIVES; i++) {
			final Wizard wizard = new Wizard(midX - Wizard.SIZE/2f + (i % 2) * 0.3f -0.15f, y, Wizard.SIZE, Wizard.SIZE, i*0.035f, y, height - i*Wizard.SIZE);
			wizards.add(wizard);
			y-=Wizard.SIZE;
		}
		moveSpeed = new Vector2(MOVE_SPEED, 200f/GameScreen.PIX_PER_UNIT);
		random = new Random();
		lastResponseTimeStamp = 0;
		this.callback = callback;
		currentSpell = 3;
		numSpells = new int[4];
		numSpells[1] = 10;
		numSpells[2] = 10;
		numSpells[3] = 10;
	}
	
	public List<Wizard> getWizards() {
		return wizards;
	}
	
	public boolean detectCollision(Arrow arrow, List<Wizard> toAdd) {
		Iterator<Wizard> wizardIt = wizards.iterator();
		while (wizardIt.hasNext()) {
			Wizard wizard = wizardIt.next();
			boolean hurt = wizard.hurt;
		    if (CollisionDetector.wizardHit(wizard, arrow)) {
		    	if (wizard.onHit(arrow)) {
		    		toAdd.add(wizard);
		    		wizardIt.remove();
		    	}
		    	return (!hurt && wizard.hurt);
		    }
		}
		return false;
	}
	
	public boolean detectCollision(Obstacle obstacle, List<Wizard> toAdd) {
		Iterator<Wizard> wizardIt = wizards.iterator();
		while (wizardIt.hasNext()) {
			Wizard wizard = wizardIt.next();
			boolean hurt = wizard.hurt;
		    if (CollisionDetector.wizardHit(wizard, obstacle)) {
		    	if (wizard.onHit(obstacle)) {
		    		toAdd.add(wizard);
		    		wizardIt.remove();
		    	}
		    	return (!hurt && wizard.hurt);
		    }
		}
		return false;
	}
	
	public void add(Wizard e) {
		wizards.add(e);
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
		lastResponseTimeStampY += delta;
		regenTimer += delta;
		if (regenTimer >= 1.5f) {
			if (numSpells[1] < 10) numSpells[1]++;
			if (numSpells[2] < 10) numSpells[2]++;
			if (numSpells[3] < 10) numSpells[3]++;
			regenTimer = 0;
		}
		float accX = 0;
		float accY = 0;
		if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer) && lastResponseTimeStamp > 0.05f) {
			accX = Gdx.input.getAccelerometerX();			
			if (accX > 0.3f) {
				if (direction != Wizard.LEFT) {
					direction = Wizard.LEFT;
					lastResponseTimeStamp = 0;
				}
			} else if (accX < - 0.3f) {
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
		}
		if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer) && lastResponseTimeStampY > 0.05f) {		
			accY = Gdx.input.getAccelerometerY();
			if (accY > 8.5f) {
				if (direction2 != Wizard.UP) {
					direction2 = Wizard.UP;
					lastResponseTimeStampY = 0;
				}
			} else if (accY < 7.5f) {
				if (direction2 != Wizard.DOWN) {
					direction2 = Wizard.DOWN;
					lastResponseTimeStampY = 0;
				}
			} else {
				if (direction2 != Wizard.STILL) {
					direction2 = Wizard.STILL;
					lastResponseTimeStampY = 0;
				}
			}
			
		}
		for (Wizard wizard: wizards) {
			if (wizard.responseThreshold <= lastResponseTimeStamp) {
				wizard.direction = direction;
			}
			wizard.direction2 = direction2;
			int x = 0;
			int y = 0;
			if (wizard.direction == Wizard.LEFT) x = -1;
			if (wizard.direction == Wizard.RIGHT) x = 1;
			if (wizard.direction2 == Wizard.DOWN) y = -3;
			if (wizard.direction2 == Wizard.UP) y = 1;
			wizard.move(x * moveSpeed.x * delta * Math.abs(accX/2f), y * moveSpeed.y * delta * Math.abs(accY/9.8f));
			wizard.addTime(delta);
			if (wizard.position.x < GameScreen.GRASS_BORDER_LEFT) wizard.position.x = GameScreen.GRASS_BORDER_LEFT;
			else if (wizard.position.x + Wizard.SIZE > GameScreen.GRASS_BORDER_RIGHT) wizard.position.x = GameScreen.GRASS_BORDER_RIGHT - Wizard.SIZE;
			if (wizard.position.y < wizard.bottomY) wizard.position.y = wizard.bottomY;
			else if (wizard.position.y + Wizard.SIZE > wizard.topY) wizard.position.y = wizard.topY - Wizard.SIZE;
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
		if (FIRE.contains(x, y)) currentSpell = 1;
		else if (ICE.contains(x, y)) currentSpell = 2;
		else if (POISON.contains(x, y)) currentSpell = 3;
		else if (numSpells[currentSpell] > 0) {
			numSpells[currentSpell]--;
			 callback.useSpell(x, y);
		}
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
			addTo.add(new Spell(wizard.position.x + Wizard.SIZE/2f - Spell.FIRE_WIDTH/2f + 0.6f, wizard.position.y + Wizard.SIZE/2f - Spell.FIRE_HEIGHT/2f - 0.3f, Spell.LENGTH, spellVelocity, angle, 1, currentSpell));
			if (currentSpell == 1) SoundPlayer.fire();
			if (currentSpell == 2) SoundPlayer.ice();
			if (currentSpell == 3) SoundPlayer.poison();
		}
	}
	
}
