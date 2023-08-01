package game.scenarios.backgorund;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import game.main.Game;
import game.resources.Spritesheet;
import game.scenarios.Scenario;

public class Pipe {

	private int x;
	private int y;

	private int width;
	private int height;

	private static BufferedImage sprites;

	public Pipe(int x) throws IOException {
		this.x = x;
		this.y = 0;

		this.width = 32;
		this.height = 81;

		if (Pipe.sprites == null) {
			Spritesheet spritesheet = new Spritesheet("/sprites/scenarios/pipe.png");

			Pipe.sprites = spritesheet.getSprite(0, 0, 32, 81);
		}
	}

	public void tick(Scenario scenario) {
		x -= 2;

		if (x == -width) {
			x = Game.WIDTH;
		}
	}

	public void render(Graphics graphics) {
		graphics.drawImage(Pipe.sprites, x, y, width, height, null);
	}

}
