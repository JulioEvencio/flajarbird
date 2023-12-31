package game.scenarios;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import game.main.Game;
import game.resources.Spritesheet;
import game.util.Mask;

public class Floor {

	private int x;
	private int y;

	private int width;
	private int height;

	private final Mask maskCollision;

	private static BufferedImage sprites;

	public Floor(int x) throws IOException {
		this.x = x;
		this.y = Game.HEIGHT - 31;

		this.width = 64;
		this.height = 31;

		this.maskCollision = new Mask(x, y, width, height);

		if (Floor.sprites == null) {
			Spritesheet spritesheet = new Spritesheet("/sprites/scenarios/floor.png");

			Floor.sprites = spritesheet.getSprite(0, 0, 64, 31);
		}
	}

	public Mask getMaskCollision() {
		return maskCollision;
	}

	public void tick(Scenario scenario) {
		x -= scenario.player.getSpeed();

		if (x == -width) {
			x = Game.WIDTH;
		}

		maskCollision.update(x, y, width, height);
	}

	public void render(Graphics graphics) {
		graphics.drawImage(Floor.sprites, x, y, width, height, null);
	}

}
