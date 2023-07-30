package game.scenarios.backgorund;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import game.resources.Spritesheet;
import game.scenarios.Scenario;

public class Background {

	private int x;
	private int y;

	private int width;
	private int height;

	private static BufferedImage sprites;

	public Background(int x) throws IOException {
		this.x = x;
		this.y = 0;

		this.width = 256;
		this.height = 256;

		if (Background.sprites == null) {
			Spritesheet spritesheet = new Spritesheet("/sprites/scenarios/background.png");

			Background.sprites = spritesheet.getSprite(0, 0, 256, 256);
		}
	}

	public void tick(Scenario scenario) {
		x--;

		if (x == -256) {
			x = 256;
		}
	}

	public void render(Graphics graphics) {
		graphics.drawImage(Background.sprites, x, y, width, height, null);
	}

}
