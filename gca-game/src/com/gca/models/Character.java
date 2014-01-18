package com.gca.models;

public class Character extends GameModel {

	protected DeathCallback death;
	protected int health;
	
	public Character(float x, float y, float width, float height, int health) {
		super(x, y, width, height);
		this.health = health;
	}
	
	public final void damage(int damage) {
		health -= damage;
		if (health <= 0 && death != null) death.onDeath(); 
	}

	public final void setDeath(DeathCallback death) {
		this.death = death;
	}
	
	public interface DeathCallback {
		void onDeath();
	}
	
	
}
