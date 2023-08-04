package game.scenarios;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import game.main.Game;
import game.resources.Spritesheet;
import game.util.Mask;

public class Pipe {

	private final Random random;

	private int x;
	private int y;

	private int width;
	private int height;

	private final Mask maskCollision;

	private static BufferedImage sprites;

	public Pipe(int x) throws IOException {
		this.random = new Random();

		this.x = x;
		this.updatePosition();

		this.width = 32;
		this.height = 81;

		this.maskCollision = new Mask(x, y, width, height);

		if (Pipe.sprites == null) {
			Spritesheet spritesheet = new Spritesheet("/sprites/scenarios/pipe.png");

			Pipe.sprites = spritesheet.getSprite(0, 0, 32, 81);
		}
	}

	private void updatePosition() {
		this.y = random.nextInt(150);
	}

	public Mask getMaskCollision() {
		return maskCollision;
	}

	public void tick(Scenario scenario) {
		x -= scenario.player.getSpeed();

		if (x == -width) {
			scenario.addScore();
			x = Game.WIDTH;
			this.updatePosition();
		}

		maskCollision.update(x, y, width, height);
	}

	public void render(Graphics graphics) {
		graphics.drawImage(Pipe.sprites, x, y, width, height, null);
	}

}
