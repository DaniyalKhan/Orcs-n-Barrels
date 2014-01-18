package com.gca.models;

import com.gca.models.projectiles.Projectile;


public class Character extends GameModel {

	protected DeathCallback death;
	protected int health;
	
	public Character(float x, float y, int health) {
		super(x, y);
		this.health = health;
	}
	
	public void onHit(Projectile projectile) {
		damage(projectile.getDamage());
	}
	
	private final void damage(int damage) {
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
