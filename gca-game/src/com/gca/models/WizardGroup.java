package com.gca.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gca.models.projectiles.Arrow;
import com.gca.screens.GameScreen;
import com.gca.utils.CollisionDetector;
import com.gca.utils.KeyHandler.KeyCallback;
import com.gca.utils.Timeable;

public class WizardGroup implements KeyCallback, Timeable{

	private final int STILL = 0x000;
	private final int LEFT = 0x001;
	private final int RIGHT = 0x002;
	
	private final static int START_LIVES = 4;
	private final float MOVE_SPEED = 125f/GameScreen.PIX_PER_UNIT;
	
	private final List<Wizard> wizards;
	private final Vector2 moveSpeed;	
	private int direction;

	private final Random random;
	
	public WizardGroup(float midX) {
		wizards = new LinkedList<Wizard>();
		float y = (START_LIVES - 1) * Wizard.SIZE;
		for (int i = 0; i < START_LIVES; i++) {
			final Wizard wizard = new Wizard(midX - Wizard.SIZE/2f, y, Wizard.SIZE, Wizard.SIZE);
			wizards.add(wizard);
			y-=Wizard.SIZE;
		}
		moveSpeed = new Vector2(MOVE_SPEED, 0);
		direction = STILL;
		random = new Random();
	}
	
	public void detectCollision(Arrow arrow) {
		Iterator<Wizard> wizardIt = wizards.iterator();
		while (wizardIt.hasNext()) {
			Wizard wizard = wizardIt.next();
		    if (CollisionDetector.wizardHit(wizard, arrow)) {
		    	if (wizard.onHit(arrow)) wizardIt.remove();
		    }
		}
	}
	
	public void detectCollision(Obstacle obstacle) {
		Iterator<Wizard> wizardIt = wizards.iterator();
		while (wizardIt.hasNext()) {
			Wizard wizard = wizardIt.next();
		    if (CollisionDetector.wizardHit(wizard, obstacle)) {
		    	if (wizard.onHit(obstacle)) wizardIt.remove();
		    }
		}
	}
	
	public int size() {
		return wizards.size();
	}
	
	public Wizard getRandomWizard() {
		
		Gdx.app.log("# remaining ",  Integer.toString(size()));
		
		for (int i = 0; i < wizards.size(); i++) {
			Gdx.app.log("wizard " + i + " has hp: ",  Integer.toString(wizards.get(i).health));
		}
		
//		Gdx.app.log("# remaining ",  Integer.toString(size()));
		int index = random.nextInt(size());
		Gdx.app.log("selecting wizard ",  Integer.toString(index));
		return wizards.get(index);
	}
	
	@Override
	public void addTime(float delta) {
		int x = 0;
		if (direction == LEFT) x = -1;
		if (direction == RIGHT) x = 1;
		moveWizards(x, 0, delta);
	}
	
	private final void moveWizards(int x, int y, float delta) {
		float dx = x != 0 ? x * moveSpeed.x * delta : 0;
		float dy = y != 0 ? y * moveSpeed.y * delta : 0;
		for (Wizard wizard: wizards) {
			wizard.move(dx, dy);
			wizard.addTime(delta);
		}
	}
	
	@Override
	public void onLeftDown() {
		direction = LEFT;
	}

	@Override
	public void onLeftUp() {
		direction = STILL;
	}

	@Override
	public void onRightDown() {
		direction = RIGHT;
	}

	@Override
	public void onRightUp() {
		direction = STILL;
	}

	public void print() {
		
		Gdx.app.log("# remaining ",  Integer.toString(size()));
		
		for (int i = 0; i < wizards.size(); i++) {
			Gdx.app.log("wizard " + i + " has hp: ",  Integer.toString(wizards.get(i).health));
		}
		
		
	}
		
}
