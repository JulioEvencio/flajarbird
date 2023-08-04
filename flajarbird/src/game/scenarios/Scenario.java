package game.scenarios;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.entities.player.Player;

public class Scenario {

	private double gravity;

	public final Player player;
	
	private int score;

	private final List<Pipe> pipes;
	private final List<Floor> floors;
	private final List<Background> backgrounds;

	public Scenario(Player player) throws IOException {
		this.gravity = 2;

		this.player = player;
		this.player.updatePosition(50, 50);
		
		this.score = 0;

		this.pipes = new ArrayList<>();

		this.pipes.add(new Pipe(500));
		this.pipes.add(new Pipe(650));

		this.floors = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			this.floors.add(new Floor(i * 64));
		}

		this.backgrounds = new ArrayList<>();

		this.backgrounds.add(new Background(0));
		this.backgrounds.add(new Background(256));
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore() {
		score++;
	}

	public double getGravity() {
		return gravity;
	}

	public boolean isGameOver() {
		return player.isDead();
	}

	public void tick() {
		for (Pipe pipe : pipes) {
			pipe.tick(this);

			if (player.getMaskCollision().iscolliding(pipe.getMaskCollision())) {
				player.toKill();
			}
		}

		for (Floor floor : floors) {
			floor.tick(this);

			if (player.getMaskCollision().iscolliding(floor.getMaskCollision())) {
				player.toKill();
			}
		}

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

		for (Pipe pipe : pipes) {
			pipe.render(graphics);
		}

		for (Floor floor : floors) {
			floor.render(graphics);
		}
	}
	
	public void renderString(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 12));
		graphics.drawString("SCORE: " + score, 10, 20);
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
