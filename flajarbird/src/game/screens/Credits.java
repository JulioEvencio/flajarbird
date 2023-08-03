package game.screens;

import java.awt.Graphics;

import game.main.Game;

public class Credits extends Screen {

	@Override
	protected void renderString(Graphics graphics) {
		graphics.drawString("Programmer:", Game.WIDTH * Game.SCALE / 2 - 200, 150);
		graphics.drawString("    > Júlio Igreja", Game.WIDTH * Game.SCALE / 2 - 200, 180);

		graphics.drawString("Sprites:", Game.WIDTH * Game.SCALE / 2 - 200, 330);
		graphics.drawString("    > MegaCrash (itch.io)", Game.WIDTH * Game.SCALE / 2 - 200, 360);
	}

}
