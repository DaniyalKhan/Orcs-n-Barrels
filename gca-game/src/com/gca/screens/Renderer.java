package com.gca.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.gca.models.Life;
import com.gca.models.Obstacle;
import com.gca.models.Orc;
import com.gca.models.Wizard;
import com.gca.models.WizardGroup;
import com.gca.models.projectiles.Arrow;
import com.gca.models.projectiles.Spell;

public class Renderer {

	
	public static float SPEED = 500f/GameScreen.PIX_PER_UNIT;
			
	private final float width;
	private final float height;
	
	public final TextureAtlas main;
	public final Texture main2;
	
	private final Texture background;
	
	private  Texture attacks;

	private  TextureRegion ice;
	private  TextureRegion poison;
	private  TextureRegion fire;
	
	private final TextureRegion cliff;
	private final TextureRegion water;
	private final TextureRegion grass;
	
	private final TextureRegion barrel;
	private final TextureRegion wizard;
	
	private final TextureRegion arr;
	private final TextureRegion spell;
	private final TextureRegion spell2;
	private final TextureRegion spell3;
	
	private final TextureRegion shadow;
	
	private final TextureRegion lid;
	
	private final TextureRegion log;
	private final TextureRegion fish;
	private final TextureRegion rock;
	private final TextureRegion shark;
	private final TextureRegion octopus;
	
	private final TextureRegion sideBubble;
	private final TextureRegion mainBubble;
	private final TextureRegion moveBubble;

	private final TextureRegion text1;
	private final TextureRegion text2;
	private final TextureRegion text3;
	
	private final Array<Sprite> orcWalk;
	private static final float ORC_WALK_FPS = 4f;
	
	private final Array<Sprite> orcShoot;
	private static final float ORC_SHOOT_FPS = 4f;
	
	private SpriteBatch batch;
		
	private final float cliffs[];
	private final float bubbles[];

	private TextureRegion gameOver;

	private TextureRegion replay;

	private TextureRegion yesT;

	private TextureRegion noT;
	
	private TextureRegion bar;
	private TextureRegion hpfill;
	private TextureRegion cdfill;
	
	public Renderer(float width, float height) {
		this.width = width;
		this.height = height;
		
		main = new TextureAtlas(Gdx.files.internal("main.txt"));
		main2 = new Texture(Gdx.files.internal("other.png"));
		
		cliff = main.createSprite("cliff tile");
		background = new Texture("background.png");

		attacks = new Texture(Gdx.files.internal("select.png"));
		
		grass = new TextureRegion(background, 1, 1, 100, 100);
		water = new TextureRegion(background, 1, 101, 100, 100);
		barrel = main.createSprite("barrel");
		wizard = main.createSprite("wizard");
		orcWalk = main.createSprites("orcwalk");
		orcShoot = main.createSprites("orcshoot");
		arr = main.createSprite("arrow");
		arr.flip(true, false);
		spell = main.createSprite("fire");
		spell2 = main.createSprite("ice");
		spell3 = main.createSprite("poison");
		
		log = main.createSprite("log");
		fish = main.createSprite("fish");
		rock = main.createSprite("rock");
		shark = main.createSprite("shark");
		octopus = main.createSprite("octopus");
		shadow = main.createSprite("shadow");
		lid = main.createSprite("barreltop");
		
		text1 = main.createSprite("1");
		text2 = main.createSprite("2");
		text3 = main.createSprite("3");
		
		gameOver = new TextureRegion(main2, 594, 66, 388,100);
		replay = new TextureRegion(main2, 2, 694, 236, 100);
		yesT = new TextureRegion(main2, 521, 491, 66, 120);
		noT = new TextureRegion(main2, 325, 918, 96, 75);

		poison = new TextureRegion(attacks, 2, 304, 150, 150);
		fire = new TextureRegion(attacks, 2, 2, 150, 150);
		ice = new TextureRegion(attacks, 2, 152, 160, 150);
		
		bar = new TextureRegion(main2, 886, 1014, 110, 20);
		hpfill = new TextureRegion(main2, 984, 66, 6, 2);
		hpfill.flip(false, true);
		
		cdfill = new TextureRegion(main2, 1016, 70, 6, 2);
		cdfill.flip(false, true);
		
		cliffs = new float[15];
		float cliffWidth = 100f/GameScreen.PIX_PER_UNIT;
		for (int i = 0; i < cliffs.length ; i++) {
			cliffs[i] = cliffWidth * i;
		}
		
		sideBubble = main.createSprite("side bubble");
		bubbles = new float[10];
		float bubbleHeight = sideBubble.getRegionHeight()/GameScreen.PIX_PER_UNIT;
		for (int i = 0; i < bubbles.length ; i++) {
			bubbles[i] = bubbleHeight * i;
		}
		
		mainBubble = main.createSprite("trail fwd");
		moveBubble = main.createSprite("bubbles move side");
		
	}
	
	public void setSpriteBatch(SpriteBatch b) {
		batch = b;
	}
	
	public void gameOver() {
		batch.draw(gameOver, (width - gameOver.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f, height - 2f, 
				gameOver.getRegionWidth()/GameScreen.PIX_PER_UNIT, gameOver.getRegionHeight()/GameScreen.PIX_PER_UNIT);
		batch.draw(replay, (width - replay.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f,height - 5f, 
				replay.getRegionWidth()/GameScreen.PIX_PER_UNIT, replay.getRegionHeight()/GameScreen.PIX_PER_UNIT);
		if (drawYes)
		batch.draw(yesT, (width - yesT.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f,height - 6.5f, 
				yesT.getRegionWidth()/GameScreen.PIX_PER_UNIT/2f, yesT.getRegionHeight()/GameScreen.PIX_PER_UNIT/2f,
				yesT.getRegionWidth()/GameScreen.PIX_PER_UNIT, yesT.getRegionHeight()/GameScreen.PIX_PER_UNIT, 1f, 1f, 270);
		if (drawNo)
		batch.draw(noT, (width - noT.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f, height - 7.5f, 
				noT.getRegionWidth()/GameScreen.PIX_PER_UNIT, noT.getRegionHeight()/GameScreen.PIX_PER_UNIT);
	}
	
	Rectangle yesRect = new Rectangle();
	Rectangle noRect = new Rectangle();
	
	public boolean drawYes = true;
	public boolean drawNo = true;;
	
	public Rectangle getYesRect() {
		yesRect.set((width - yesT.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f,height - 6.5f, 
				yesT.getRegionWidth()/GameScreen.PIX_PER_UNIT, yesT.getRegionHeight()/GameScreen.PIX_PER_UNIT);
		return yesRect;
	}
	
	public Rectangle getNoRect() {
		noRect.set((width - noT.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f, height - 7.5f, 
				noT.getRegionWidth()/GameScreen.PIX_PER_UNIT, noT.getRegionHeight()/GameScreen.PIX_PER_UNIT);
		return noRect;
	}
	
	public void grass() {
		float grassSize = 100f/GameScreen.PIX_PER_UNIT;
		for (int i = 0; i < height/grassSize + 1; i++) {
			batch.draw(grass, 0, i * grassSize, grassSize, grassSize);
			batch.draw(grass, width - grassSize, i * grassSize , grassSize, grassSize);
		}
	}

	public void water() {
		float waterSize = 100f/GameScreen.PIX_PER_UNIT;
		for (int i = 0; i < width + 1; i++) {
			for (int j = 0; j < height + 1; j++) {
				batch.draw(water, i * waterSize, j * waterSize, waterSize, waterSize);
			}
		}
	}

	public void cliff(float delta) {
		float cliffWidth = 100f/GameScreen.PIX_PER_UNIT;
		float cliffHeight = 22f/GameScreen.PIX_PER_UNIT;
		for (int i = 0; i < cliffs.length; i++) {
			cliffs[i] -= delta * SPEED;
			batch.draw(cliff,  100f/GameScreen.PIX_PER_UNIT + cliffHeight, cliffs[i], 0f, 0f, cliffWidth, cliffHeight, 1f, 1f, 90);
			cliff.flip(false, true);
			batch.draw(cliff, width - 100f/GameScreen.PIX_PER_UNIT, cliffs[i], 0f, 0f, cliffWidth, cliffHeight, 1f, 1f, 90);
			cliff.flip(false, true);
			if (cliffs[i] + cliffWidth < 0) cliffs[i] = height;
		}
	}
	
	public void bubble(float delta, WizardGroup wg) {
		float bubbleWidth = sideBubble.getRegionWidth()/GameScreen.PIX_PER_UNIT;
		float bubbleHeight = sideBubble.getRegionHeight()/GameScreen.PIX_PER_UNIT;
		for (int i = 0; i < bubbles.length; i++) {
			
			bubbles[i] -= delta * SPEED;
			sideBubble.flip(true, false);
			batch.draw(sideBubble,  100f/GameScreen.PIX_PER_UNIT, bubbles[i], 0f, 0f, bubbleWidth, bubbleHeight, 1f, 1f, 0);
			sideBubble.flip(true, false);
			batch.draw(sideBubble, width - 100f/GameScreen.PIX_PER_UNIT - bubbleWidth, bubbles[i], 0f, 0f, bubbleWidth, bubbleHeight, 1f, 1f, 0);
			if (bubbles[i] + bubbleHeight < 0) bubbles[i] = height;
			
		}
		for (Wizard w : wg.getWizards()) {
			batch.draw(mainBubble, w.position.x - 0.08f, w.position.y - 1.3f, mainBubble.getRegionWidth()/GameScreen.PIX_PER_UNIT, mainBubble.getRegionHeight()/GameScreen.PIX_PER_UNIT);
		}
	}
	
	public void wizards(WizardGroup wg) {
		for (Wizard w : wg.getWizards()) {
			batch.draw(barrel, w.position.x, w.position.y, Wizard.SIZE, Wizard.SIZE);
			if (!w.hurt) {
				batch.draw(wizard, w.position.x, w.position.y, Wizard.WIZARD_WIDTH/2f + 0.05f, Wizard.WIZARD_HEIGHT/2f, Wizard.WIZARD_WIDTH, Wizard.WIZARD_HEIGHT, 1f, 1f, 0);
			} else {
				batch.setColor(1.0f, 1.0f, 1.0f, 0.5f);
				batch.draw(wizard, w.position.x, w.position.y, Wizard.WIZARD_WIDTH/2f + 0.05f, Wizard.WIZARD_HEIGHT/2f, Wizard.WIZARD_WIDTH, Wizard.WIZARD_HEIGHT, 1f, 1f, 0);
				batch.setColor(1.0f, 1.0f, 1.0f, 1f);
			}
						
			batch.draw(bar, w.position.x+ (Wizard.SIZE - bar.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f , w.position.y + Wizard.SIZE , bar.getRegionWidth()/GameScreen.PIX_PER_UNIT, bar.getRegionHeight()/GameScreen.PIX_PER_UNIT);
			
			int barsPerHealth = 45;
			float begX = 7f/GameScreen.PIX_PER_UNIT + w.position.x+ (Wizard.SIZE - bar.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f;
			for (int i = 0; i < barsPerHealth * w.health/((float)Wizard.HEALTH); i ++) {
				for (int j = 0; j < 4; j++) {
					batch.draw(hpfill, begX, w.position.y + Wizard.SIZE + 0.075f, hpfill.getRegionWidth()/GameScreen.PIX_PER_UNIT/2f, hpfill.getRegionHeight()/GameScreen.PIX_PER_UNIT/2f,  
						hpfill.getRegionWidth()/GameScreen.PIX_PER_UNIT, (hpfill.getRegionHeight() +4f)/GameScreen.PIX_PER_UNIT, 1f, 1f, 270);
					begX += 0.5f/GameScreen.PIX_PER_UNIT;
				}
			}
		}
	}
	
	public void wizards(List<Wizard> wizards) {
		for (Wizard w : wizards) {
			batch.setColor(1.0f, 1.0f, 1.0f, w.deathOpacity);
			batch.draw(mainBubble, w.position.x - 0.08f, w.position.y - 1.3f, mainBubble.getRegionWidth()/GameScreen.PIX_PER_UNIT, mainBubble.getRegionHeight()/GameScreen.PIX_PER_UNIT);
			batch.draw(barrel, w.position.x, w.position.y, Wizard.SIZE, Wizard.SIZE);
			if (!w.hurt) {
				batch.draw(wizard, w.position.x, w.position.y, Wizard.WIZARD_WIDTH/2f + 0.05f, Wizard.WIZARD_HEIGHT/2f, Wizard.WIZARD_WIDTH, Wizard.WIZARD_HEIGHT, 1f, 1f, 0);
			} else {
				batch.draw(wizard, w.position.x, w.position.y, Wizard.WIZARD_WIDTH/2f + 0.05f, Wizard.WIZARD_HEIGHT/2f, Wizard.WIZARD_WIDTH, Wizard.WIZARD_HEIGHT, 1f, 1f, 0);
			}
			batch.setColor(1.0f, 1.0f, 1.0f, 1f);
		}
	}
	
	public void spells(WizardGroup w) {
		
		batch.draw(ice, 0.8f, 0f, ice.getRegionWidth()/GameScreen.PIX_PER_UNIT, ice.getRegionHeight()/GameScreen.PIX_PER_UNIT);
		batch.draw(fire, 2.35f, 0f, fire.getRegionWidth()/GameScreen.PIX_PER_UNIT, fire.getRegionHeight()/GameScreen.PIX_PER_UNIT);
		batch.draw(poison, 3.8f, 0f, poison.getRegionWidth()/GameScreen.PIX_PER_UNIT, poison.getRegionHeight()/GameScreen.PIX_PER_UNIT);
				
		float width = ice.getRegionWidth()/GameScreen.PIX_PER_UNIT;

		batch.draw(bar, 0.8f + (width - bar.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f +0.08f , 0.2f - 0.075f , 
				bar.getRegionWidth()/GameScreen.PIX_PER_UNIT, bar.getRegionHeight()/GameScreen.PIX_PER_UNIT);
		int barsPerHealth = 45;
		float begX = 7f/GameScreen.PIX_PER_UNIT + 0.8f + (width - bar.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f +0.08f;
		for (int i = 0; i < barsPerHealth * w.numSpells[2]/(10f); i ++) {
			for (int j = 0; j < 4; j++) {
				batch.draw(cdfill, begX,  0.2f, cdfill.getRegionWidth()/GameScreen.PIX_PER_UNIT/2f, cdfill.getRegionHeight()/GameScreen.PIX_PER_UNIT/2f,  
						cdfill.getRegionWidth()/GameScreen.PIX_PER_UNIT, (cdfill.getRegionHeight() +4f)/GameScreen.PIX_PER_UNIT, 1f, 1f, 270);
				begX += 0.5f/GameScreen.PIX_PER_UNIT;
			}
		}
		
		width = fire.getRegionWidth()/GameScreen.PIX_PER_UNIT;

		batch.draw(bar, 2.35f + (width - bar.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f +0.03f , 0.2f - 0.075f , 
				bar.getRegionWidth()/GameScreen.PIX_PER_UNIT, bar.getRegionHeight()/GameScreen.PIX_PER_UNIT);
		begX = 7f/GameScreen.PIX_PER_UNIT + 2.35f + (width - bar.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f +0.03f ;
		for (int i = 0; i < barsPerHealth * w.numSpells[1]/(10f); i ++) {
			for (int j = 0; j < 4; j++) {
				batch.draw(cdfill, begX,  0.2f, cdfill.getRegionWidth()/GameScreen.PIX_PER_UNIT/2f, cdfill.getRegionHeight()/GameScreen.PIX_PER_UNIT/2f,  
						cdfill.getRegionWidth()/GameScreen.PIX_PER_UNIT, (cdfill.getRegionHeight() +4f)/GameScreen.PIX_PER_UNIT, 1f, 1f, 270);
				begX += 0.5f/GameScreen.PIX_PER_UNIT;
			}
		}
		
		width = poison.getRegionWidth()/GameScreen.PIX_PER_UNIT;

		batch.draw(bar, 3.8f + (width - bar.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f +0.03f , 0.2f - 0.075f , 
				bar.getRegionWidth()/GameScreen.PIX_PER_UNIT, bar.getRegionHeight()/GameScreen.PIX_PER_UNIT);
		begX = 7f/GameScreen.PIX_PER_UNIT +3.8f + (width - bar.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f +0.03f ;
		for (int i = 0; i < barsPerHealth * w.numSpells[3]/(10f); i ++) {
			for (int j = 0; j < 4; j++) {
				batch.draw(cdfill, begX,  0.2f, cdfill.getRegionWidth()/GameScreen.PIX_PER_UNIT/2f, cdfill.getRegionHeight()/GameScreen.PIX_PER_UNIT/2f,  
						cdfill.getRegionWidth()/GameScreen.PIX_PER_UNIT, (cdfill.getRegionHeight() +4f)/GameScreen.PIX_PER_UNIT, 1f, 1f, 270);
				begX += 0.5f/GameScreen.PIX_PER_UNIT;
			}
		}
		

	}
	
	public void orcs(List<Orc> orcs) {
		for (Orc orc: orcs) {
			TextureRegion frame;
			float angle;
			if (orc.shooting) {
				int frameNumber = (int) ((orc.animTime * ORC_SHOOT_FPS));
				if (frameNumber == orcShoot.size) {
					orc.shooting = false;
					frameNumber -=1;
				}
				frame = orcShoot.get(frameNumber);
				angle = 180 * orc.angle/MathUtils.PI - 20 - 90;
			} else {
				frame = orcWalk.get((int) ((orc.animTime * ORC_WALK_FPS) % orcWalk.size));
				angle = 270;
			}
						
			float width =  shadow.getRegionWidth()/GameScreen.PIX_PER_UNIT;
			float height =  shadow.getRegionHeight()/GameScreen.PIX_PER_UNIT;
			batch.setColor(1.0f, 1f, 1f, orc.deadOpacity);
			batch.draw(shadow, orc.position.x + 0.1f, orc.position.y + 0.2f, width/2f, height/2f, width, height, 1f, 1f, 0);

			width =  frame.getRegionWidth()/GameScreen.PIX_PER_UNIT;
			height =  frame.getRegionHeight()/GameScreen.PIX_PER_UNIT;
			
			if (orc instanceof Orc.EliteOrc) {
				batch.setColor(0.5f, orc.redFilter * 0.5f, orc.redFilter * 0.5f, orc.deadOpacity);
				batch.draw(frame, orc.position.x, orc.position.y, width/2f, height/2f, width, height, 1f, 1f, angle);
				batch.setColor(1.0f, 1.0f, 1.0f, 1f);
			} else {
				batch.setColor(1.0f, orc.redFilter, orc.redFilter, orc.deadOpacity);
				batch.draw(frame, orc.position.x, orc.position.y, width/2f, height/2f, width, height, 1f, 1f, angle);
				batch.setColor(1.0f, 1.0f, 1.0f, 1f);
			}
		}
	}
	
	public void arrows(List<Arrow> arrows) {
		float arrWidth = arr.getRegionWidth()/GameScreen.PIX_PER_UNIT;
		float arrHeight = arr.getRegionHeight()/GameScreen.PIX_PER_UNIT;
		for (Arrow arrow: arrows) {
			if (arrow.delay >= Arrow.DELAY_THRESHOLD) 
				batch.draw(arr, arrow.position.x, arrow.position.y, arrWidth/2f, arrHeight/2f, arrWidth, arrHeight, 
					1f, 1f, arrow.angle / MathUtils.PI * 180f);
		}
	}
	
	public void spells(List<Spell> spells) {
		float sWidth = spell.getRegionWidth()/GameScreen.PIX_PER_UNIT;
		float sHeight = spell.getRegionHeight()/GameScreen.PIX_PER_UNIT;
		for (Spell spell: spells) {
			TextureRegion s;
			if (spell.type == 1) s = this.spell;
			else if (spell.type == 2) s = spell2;
			else s = spell3;
			batch.draw(s, spell.position.x, spell.position.y, sWidth/2f, sHeight/2f, sWidth, sHeight, 
					1f, 1f, spell.angle / MathUtils.PI * 180f - 90);
		}
	}

	public void obstacles(List<Obstacle> obstacles) {
		for (Obstacle ob : obstacles) {
			batch.setColor(1.0f, 1.0f, 1.0f, ob.opacityMult);
			if (ob.type == 1) {
				batch.draw(log, ob.position.x, ob.position.y, Obstacle.LOG_WIDTH/2f, Obstacle.LOG_HEIGHT/2f, 
						Obstacle.LOG_WIDTH, Obstacle.LOG_HEIGHT, 1f, 1f, 90);
			} else if (ob.type == 2) {
				batch.draw(fish, ob.position.x, ob.position.y, Obstacle.FISH_WIDTH/2f, Obstacle.FISH_HEIGHT/2f, 
						Obstacle.FISH_WIDTH, Obstacle.FISH_HEIGHT, 1f, 1f, 0);
			} else if (ob.type == 3) {
				batch.draw(rock, ob.position.x, ob.position.y, Obstacle.ROCK_WIDTH/2f, Obstacle.ROCK_HEIGHT/2f, 
						Obstacle.ROCK_WIDTH, Obstacle.ROCK_HEIGHT, 1f, 1f, 270);
			} else if (ob.type == 4) {
				batch.draw(shark, ob.position.x, ob.position.y, Obstacle.SHARK_WIDTH/2f, Obstacle.SHARK_HEIGHT/2f, 
						Obstacle.SHARK_WIDTH, Obstacle.SHARK_HEIGHT, 1f, 1f, 270);
			} else if (ob.type == 5) {
				batch.draw(octopus, ob.position.x, ob.position.y, Obstacle.OCTOPUS_WIDTH/2f, Obstacle.OCTOPUS_HEIGHT/2f, 
						Obstacle.OCTOPUS_WIDTH, Obstacle.OCTOPUS_HEIGHT, 1f, 1f, 270);
			}  
			batch.setColor(1.0f, 1.0f, 1.0f, 1f);
		}
	}
	
	public void life(Life l) {
		batch.draw(barrel, l.position.x, l.position.y, Wizard.SIZE, Wizard.SIZE);
		batch.setColor(1.0f, 1.0f, 1.0f, l.op);
		batch.draw(lid, l.position.x, l.position.y, Wizard.SIZE, Wizard.SIZE);
		batch.setColor(1.0f, 1.0f, 1.0f, 1f);
	}

	public void timer(float timer) {
		TextureRegion time;
		if (timer <=3 ) time = text3;
		else if (timer <=2 ) time = text2;
		else time = text1;
		
		if (timer <=2) {
			batch.draw(time, (width - time.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f, (height - time.getRegionHeight()/GameScreen.PIX_PER_UNIT)/2f, 
					time.getRegionWidth()/2f, time.getRegionHeight()/2f, 
					time.getRegionWidth()/GameScreen.PIX_PER_UNIT, time.getRegionHeight()/GameScreen.PIX_PER_UNIT, 1f, 1f, 270);
		} else {
			batch.draw(time, (width - time.getRegionWidth()/GameScreen.PIX_PER_UNIT)/2f, (height - time.getRegionHeight()/GameScreen.PIX_PER_UNIT)/2f, 
					time.getRegionWidth()/GameScreen.PIX_PER_UNIT, time.getRegionHeight()/GameScreen.PIX_PER_UNIT);
		}
	}
	
	
}
