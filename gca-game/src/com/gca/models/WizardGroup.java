package com.gca.models;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.gca.screens.GameScreen;
import com.gca.utils.KeyHandler.KeyCallback;
import com.gca.utils.Timeable;

public class WizardGroup implements KeyCallback, Timeable{

	private final int STILL = 0x000;
	private final int LEFT = 0x001;
	private final int RIGHT = 0x002;
	
	private final static int START_LIVES = 4;
	private final float MOVE_SPEED = 125f/GameScreen.PIX_PER_UNIT;
	
	private final Wizard[] wizards;
	private final Vector2 moveSpeed;	
	private int direction;

	private final Random random;
	
	public WizardGroup(float midX) {
		wizards = new Wizard[START_LIVES];
		int y = 0;
		for (Wizard wizard: wizards) {
			wizard = new Wizard(midX - Wizard.SIZE/2f, y, Wizard.SIZE, Wizard.SIZE);
			y+=Wizard.SIZE;
		}
		moveSpeed = new Vector2(MOVE_SPEED, 0);
		direction = STILL;
		random = new Random();
	}
	
	public int size() {
		return wizards.length;
	}
	
	public Wizard getRandomWizard() {
		int index = random.nextInt(size());
		return wizards[index];
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
		
}
