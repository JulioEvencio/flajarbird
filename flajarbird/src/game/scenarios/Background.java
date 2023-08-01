package game.scenarios;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import game.main.Game;
import game.resources.Spritesheet;

public class Background {

	private int x;
	private int y;

	private int width;
	private int height;

	private static BufferedImage sprites;

	public Background(int x) throws IOException {
		this.x = x;
		this.y = 0;

		this.width = Game.WIDTH;
		this.height = Game.HEIGHT;

		if (Background.sprites == null) {
			Spritesheet spritesheet = new Spritesheet("/sprites/scenarios/background.png");

			Background.sprites = spritesheet.getSprite(0, 0, 256, 256);
		}
	}

	public void tick(Scenario scenario) {
		x--;

		if (x == -width) {
			x = Game.WIDTH;
		}
	}

	public void render(Graphics graphics) {
		graphics.drawImage(Background.sprites, x, y, width, height, null);
	}

}
