package game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.entities.player.Player;

public class Game extends Canvas implements KeyListener {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;

	private int fps;
	private boolean showFPS;

	private final BufferedImage renderer;

	private Player player;

	public Game() throws IOException {
		this.addKeyListener(this);

		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame();

		frame.setTitle("Flajarbird");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		this.renderer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		player = new Player(50, 50);
	}

	private void tick() {
		player.tick();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics render = renderer.getGraphics();

		render.setColor(Color.BLACK);
		render.fillRect(0, 0, WIDTH, HEIGHT);

		player.render(render);

		render.dispose();

		Graphics graphics = bs.getDrawGraphics();
		graphics.drawImage(renderer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		// Code

		if (showFPS) {
			graphics.setColor(Color.WHITE);
			graphics.setFont(new Font("arial", Font.BOLD, 20));
			graphics.drawString("FPS: " + fps, (Game.WIDTH * Game.SCALE) - 100, 32);
		}

		bs.show();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Code
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F3) {
			showFPS = !showFPS;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Code
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0.0;

		int frames = 0;
		double timer = System.currentTimeMillis();

		this.requestFocus();

		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				this.tick();
				this.render();
				delta--;

				frames++;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				fps = frames;
				frames = 0;
				timer = System.currentTimeMillis();
			}
		}
	}

	public static void main(String[] args) {
		try {
			new Game().run();
		} catch (Exception e) {
			Game.exitWithError("An error has occurred. The program will be terminated.");
		}
	}

	public static void exit() {
		System.exit(0);
	}

	public static void exitWithError(String error) {
		JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		Game.exit();
	}

}
