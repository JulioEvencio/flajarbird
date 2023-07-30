package game.scenarios;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.entities.player.Player;
import game.scenarios.backgorund.Background;

public class Scenario {

	private double gravity;

	private final Player player;

	private final List<Background> backgrounds;

	public Scenario(Player player) throws IOException {
		this.gravity = 2;

		this.player = player;
		this.player.updatePosition(50, 50);

		this.backgrounds = new ArrayList<>();

		this.backgrounds.add(new Background(0));
		this.backgrounds.add(new Background(256));
	}

	public double getGravity() {
		return gravity;
	}

	public void tick() {
		for (Background background : backgrounds) {
			background.tick(this);
		}

		player.tick(this);
	}

	public void render(Graphics graphics) {
		for (Background background : backgrounds) {
			background.render(graphics);
		}

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
