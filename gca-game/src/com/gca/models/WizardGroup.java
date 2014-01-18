package com.gca.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
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
	private final float MOVE_SPEED = 225f/GameScreen.PIX_PER_UNIT;
	
	private final List<Wizard> wizards;
	private final Vector2 moveSpeed;	

	float lastResponseTimeStamp;
	
	private final Random random;
	
	int direction;
	
	SpellCallback callback;
	
	public WizardGroup(float midX, SpellCallback callback) {
		wizards = new LinkedList<Wizard>();
		float y = (START_LIVES - 1) * Wizard.SIZE;
		for (int i = 0; i < START_LIVES; i++) {
			final Wizard wizard = new Wizard(midX - Wizard.SIZE/2f + (i % 2) * 0.3f -0.15f, y, Wizard.SIZE, Wizard.SIZE, i*0.05f);
			wizards.add(wizard);
			y-=Wizard.SIZE;
		}
		moveSpeed = new Vector2(MOVE_SPEED, 0);
		random = new Random();
		lastResponseTimeStamp = 0;
		this.callback = callback;
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
		int i = -1;
		lastResponseTimeStamp +=delta;
		for (Wizard wizard: wizards) {
			i++;
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

	public void print() {
		
		Gdx.app.log("# remaining ",  Integer.toString(size()));
		
		for (int i = 0; i < wizards.size(); i++) {
			Gdx.app.log("wizard " + i + " has hp: ",  Integer.toString(wizards.get(i).health));
		}
		
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
			if (targetY - wizard.position.y < 0) spellVelocity.scl(1f, -1f);
			if (distX < 0) spellVelocity.scl(-1f, 1f);
			addTo.add(new Spell(wizard.position.x + Wizard.SIZE/2f, wizard.position.y + Wizard.SIZE/2f, Spell.LENGTH, spellVelocity, angle, 1));
		}
	}
	
}
