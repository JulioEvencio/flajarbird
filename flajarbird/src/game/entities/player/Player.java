package game.entities.player;

import java.awt.Graphics;
import java.io.IOException;

import game.entities.Entity;
import game.util.Mask;

public class Player extends Entity {

	public Player(int x, int y) throws IOException {
		super(x, y, 16, 16, 1, new Mask(x, y, 16, 16), new PlayerSprites(x, y, 16, 16));
	}

	private void updateMaskCollision() {
		maskCollision.update((int) x + 3, (int) y + 3, width - 5, height - 5);
	}

	@Override
	public void tick() {
		this.updateMaskCollision();

		sprites.updatePosition((int) x, (int) y);
		sprites.tick();
	}

	@Override
	public void render(Graphics graphics) {
		sprites.render(graphics);
	}

}
