package game.entities;

import java.awt.Graphics;

import game.scenarios.Scenario;
import game.util.Mask;
import game.util.Sprite;

public abstract class Entity {

	protected double x;
	protected double y;

	protected int width;
	protected int height;

	protected double speed;

	protected final Mask maskCollision;

	protected final Sprite sprites;

	public Entity(int x, int y, int width, int height, double speed, Mask maskCollision, Sprite sprite) {
		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;

		this.speed = speed;

		this.maskCollision = maskCollision;

		this.sprites = sprite;
	}

	public void updatePosition(int x, int y) {
		this.x = x;
		this.y = y;

		sprites.updatePosition(x, y);
		maskCollision.update(x, y, width, height);
	}

	public Mask getMaskCollision() {
		return maskCollision;
	}

	public abstract void tick(Scenario scenario);

	public abstract void render(Graphics graphics);

	public double getSpeed() {
		return speed;
	}

}
