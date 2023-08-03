package game.screens;

import java.awt.Graphics;

import game.main.Game;

public class Tutorial extends Screen {

	@Override
	protected void renderString(Graphics graphics) {
		graphics.drawString("> SPACE -> use to fly", Game.WIDTH * Game.SCALE / 2 - 200, 150);
		graphics.drawString("> ESC or P -> use to pause the game", Game.WIDTH * Game.SCALE / 2 - 200, 180);
		graphics.drawString("> F3 -> to enable/disable FPS", Game.WIDTH * Game.SCALE / 2 - 200, 210);
		graphics.drawString("> F4 -> to enable/disable musics and sounds", Game.WIDTH * Game.SCALE / 2 - 200, 240);
	}

}
