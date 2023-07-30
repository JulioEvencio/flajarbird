package game.scenarios;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import game.entities.player.Player;

public class Scenario {

	private double gravity;

	private final Player player;

	public Scenario(Player player) {
		this.gravity = 2;
		this.player = player;
		this.player.updatePosition(50, 50);
	}

	public double getGravity() {
		return gravity;
	}

	public void tick() {
		player.tick(this);
	}

	public void render(Graphics graphics) {
		player.render(graphics);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			gravity = -2;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			gravity = 2;
		}
	}

}
