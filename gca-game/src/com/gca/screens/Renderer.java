package com.gca.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.gca.models.Orc;
import com.gca.models.Wizard;
import com.gca.models.WizardGroup;
import com.gca.models.projectiles.Arrow;
import com.gca.models.projectiles.Spell;

public class Renderer {

	private static final float SPEED = 300f/GameScreen.PIX_PER_UNIT;
	
	private static final ShapeRenderer s = new ShapeRenderer();
	
	private final float width;
	private final float height;
	
	private final TextureAtlas main;
	
	private final Texture background;
	
	private final TextureRegion cliff;
	private final TextureRegion water;
	private final TextureRegion grass;
	
	private final TextureRegion barrel;
	private final TextureRegion wizard;
	
	private final TextureRegion arr;
	private final TextureRegion spell;
	
	private final TextureRegion sideBubble;
	private final TextureRegion mainBubble;
	private final TextureRegion moveBubble;
	
	private final Array<Sprite> orcWalk;
	private static final float ORC_WALK_FPS = 4f;
	
	private final Array<Sprite> orcShoot;
	private static final float ORC_SHOOT_FPS = 4f;
	
	private SpriteBatch batch;
		
	private final float cliffs[];
	private final float bubbles[];
	
	public Renderer(float width, float height) {
		this.width = width;
		this.height = height;
		main = new TextureAtlas(Gdx.files.internal("main.txt"));
		cliff = main.createSprite("cliff tile");
		background = new Texture("background.png");
		grass = new TextureRegion(background, 1, 1, 100, 100);
		water = new TextureRegion(background, 1, 101, 100, 100);
		barrel = main.createSprite("barrel");
		wizard = main.createSprite("wizard");
		orcWalk = main.createSprites("orcwalk");
		orcShoot = main.createSprites("orcshoot");
		arr = main.createSprite("arrow");
		spell = new TextureRegion(arr);
		arr.flip(true, false);
		
		cliffs = new float[10];
		float cliffWidth = 100f/GameScreen.PIX_PER_UNIT;
		for (int i = 0; i < cliffs.length ; i++) {
			cliffs[i] = cliffWidth * i;
		}
		
		bubbles = new float[5];
		float bubbleHeight = 200f/GameScreen.PIX_PER_UNIT;
		for (int i = 0; i < bubbles.length ; i++) {
			bubbles[i] = bubbleHeight * i;
		}
		
		sideBubble = main.createSprite("bubbles side");
		mainBubble = main.createSprite("trail fwd");
		moveBubble = main.createSprite("bubbles move side");
		
	}
	
	public void setSpriteBatch(SpriteBatch b) {
		batch = b;
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
		float bubbleWidth = 62f/GameScreen.PIX_PER_UNIT;
		float bubbleHeight = 200f/GameScreen.PIX_PER_UNIT;
		for (int i = 0; i < bubbles.length; i++) {
			
			bubbles[i] -= delta * SPEED;
			sideBubble.flip(true, false);
			batch.draw(sideBubble,  100f/GameScreen.PIX_PER_UNIT, bubbles[i], 0f, 0f, bubbleWidth, bubbleHeight, 1f, 1f, 0);
			sideBubble.flip(true, false);
			batch.draw(sideBubble, width - 100f/GameScreen.PIX_PER_UNIT - bubbleWidth, bubbles[i], 0f, 0f, bubbleWidth, bubbleHeight, 1f, 1f, 0);
			if (bubbles[i] + bubbleHeight < 0) bubbles[i] = height;
			
		}
		for (Wizard w : wg.getWizards()) {
			if (w.direction == Wizard.STILL) {
				batch.draw(mainBubble, w.position.x - 0.08f, w.position.y - 1.3f, mainBubble.getRegionWidth()/GameScreen.PIX_PER_UNIT, mainBubble.getRegionHeight()/GameScreen.PIX_PER_UNIT);
			} else if (w.direction == Wizard.RIGHT) {
				batch.draw(moveBubble, w.position.x - 1.2f, w.position.y - 1.5f, moveBubble.getRegionWidth()/GameScreen.PIX_PER_UNIT, moveBubble.getRegionHeight()/GameScreen.PIX_PER_UNIT);
			} else if (w.direction == Wizard.LEFT) {
				moveBubble.flip(true, false);
				batch.draw(moveBubble, w.position.x + 0.5f, w.position.y - 1.5f, moveBubble.getRegionWidth()/GameScreen.PIX_PER_UNIT, moveBubble.getRegionHeight()/GameScreen.PIX_PER_UNIT);
				moveBubble.flip(true, false);
			}
		}
	}
	
	public void wizards(WizardGroup wg) {
		for (Wizard w : wg.getWizards()) {
			batch.draw(barrel, w.position.x, w.position.y, Wizard.SIZE, Wizard.SIZE);
			if (!w.hurt) {
				batch.draw(wizard, w.position.x, w.position.y, Wizard.WIZARD_WIDTH/2f + 0.05f, Wizard.WIZARD_HEIGHT/2f, Wizard.WIZARD_WIDTH, Wizard.WIZARD_HEIGHT, 1f, 1f, 270);
			} else {
				batch.setColor(1.0f, 1.0f, 1.0f, 0.5f);
				batch.draw(wizard, w.position.x, w.position.y, Wizard.WIZARD_WIDTH/2f + 0.05f, Wizard.WIZARD_HEIGHT/2f, Wizard.WIZARD_WIDTH, Wizard.WIZARD_HEIGHT, 1f, 1f, 270);
				batch.setColor(1.0f, 1.0f, 1.0f, 1f);
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
			float width =  frame.getRegionWidth()/GameScreen.PIX_PER_UNIT;
			float height =  frame.getRegionHeight()/GameScreen.PIX_PER_UNIT;
						
			if (orc instanceof Orc.EliteOrc) {
				batch.setColor(0.5f, orc.redFilter * 0.5f, orc.redFilter * 0.5f, 1f);
				batch.draw(frame, orc.position.x, orc.position.y, width/2f, height/2f, width, height, 1f, 1f, angle);
				batch.setColor(1.0f, 1.0f, 1.0f, 1f);
			} else {
				batch.setColor(1.0f, orc.redFilter, orc.redFilter, 1f);
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
	
	public void spells(List<Spell> arrows) {
		float arrWidth = arr.getRegionWidth()/GameScreen.PIX_PER_UNIT;
		float arrHeight = arr.getRegionHeight()/GameScreen.PIX_PER_UNIT;
		for (Spell arrow: arrows) {
			batch.draw(arr, arrow.position.x, arrow.position.y, arrWidth/2f, arrHeight/2f, arrWidth, arrHeight, 
					1f, 1f, arrow.angle / MathUtils.PI * 180f);
		}
	}
	
	
}
