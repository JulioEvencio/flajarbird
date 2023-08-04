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
import game.resources.Sound;
import game.scenarios.Scenario;
import game.screens.Credits;
import game.screens.GameOver;
import game.screens.MainMenu;
import game.screens.Menu;
import game.screens.Pause;
import game.screens.Screen;
import game.screens.Tutorial;

public class Game extends Canvas implements KeyListener {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 256;
	public static final int HEIGHT = 256;
	public static final int SCALE = 2;

	private int gameState;

	public static final int GAME_MENU = 1;
	public static final int GAME_RUN = 2;
	public static final int GAME_RESTART = 3;
	public static final int GAME_PAUSE = 4;
	public static final int GAME_TUTORIAL = 5;
	public static final int GAME_CREDITS = 6;
	public static final int GAME_GAME_OVER = 7;
	public static final int GAME_FINAL_SCREEN = 8;
	public static final int GAME_EXIT = 9;

	private int fps;
	private boolean showFPS;

	private final BufferedImage renderer;
	
	private final Menu mainMenu;
	private final Menu pause;

	private final Screen tutorial;
	private final Screen credits;
	private final Screen gameOver;

	private Player player;
	private Scenario scenario;
	
	private boolean enableSound;
	
	private Sound musicNow;
	private final Sound soundMenu;
	private final Sound soundGame;

	public Game() throws IOException {
		soundMenu = new Sound("/sounds/sunsai/menu.wav");
		soundMenu.start();
		
		soundGame = new Sound("/sounds/wiphotos/game.wav");
		soundGame.start();
		
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

		this.gameState = Game.GAME_MENU;
		
		this.mainMenu = new MainMenu();
		this.pause = new Pause();

		this.tutorial = new Tutorial();
		this.credits = new Credits();
		this.gameOver = new GameOver();
		
		this.enableSound = true;
		this.musicNow = this.soundMenu;

		this.restart();
	}

	private void restart() {
		try {
			this.player = new Player(0, 0);
		} catch (IOException e) {
			Game.exitWithError("Error loading resources for Player.");
		}

		try {
			this.scenario = new Scenario(this.player);
		} catch (IOException e) {
			Game.exitWithError("Error loading resources for Map.");
		}
		
		this.setMusicNow(soundGame);
	}
	
	public void setMusicNow(Sound sound) {
		soundMenu.soundStop();
		soundGame.soundStop();
		
		musicNow.soundStop();
		musicNow = sound;
		musicNow.soundPlay();
	}

	private void updateGameState(int gameState) {
		if (gameState == Game.GAME_RESTART) {
			this.restart();

			gameState = Game.GAME_RUN;
		}

		this.gameState = gameState;
		
		if (gameState == Game.GAME_RUN && musicNow != soundGame) {
			this.setMusicNow(soundGame);
		} else if (musicNow != soundMenu) {
			this.setMusicNow(soundMenu);
		}
	}

	private void tick() {
		if (enableSound) {
			musicNow.soundPlay();
		} else {
			musicNow.soundStop();
		}
		
		if (gameState == Game.GAME_RUN) {
			scenario.tick();

			if (scenario.isGameOver()) {
				this.updateGameState(Game.GAME_GAME_OVER);
				this.restart();
			}
		} else if (gameState == Game.GAME_MENU) {
			mainMenu.tick();

			this.updateGameState(mainMenu.getOption());
		} else if (gameState == Game.GAME_PAUSE) {
			pause.tick();

			this.updateGameState(pause.getOption());
		} else if (gameState == Game.GAME_EXIT) {
			Game.exit();
		}
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

		if (gameState == Game.GAME_RUN) {
			scenario.render(render);
		}

		render.dispose();

		Graphics graphics = bs.getDrawGraphics();
		graphics.drawImage(renderer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		switch (gameState) {
			case Game.GAME_MENU:
				mainMenu.render(graphics);
				break;
			case Game.GAME_PAUSE:
				pause.render(graphics);
				break;
			case Game.GAME_TUTORIAL:
				tutorial.render(graphics);
				break;
			case Game.GAME_CREDITS:
				credits.render(graphics);
				break;
			case Game.GAME_GAME_OVER:
				gameOver.render(graphics);
				break;
		}

		if (showFPS) {
			graphics.setColor(Color.WHITE);
			graphics.setFont(new Font("arial", Font.BOLD, 20));
			graphics.drawString("FPS: " + fps, (Game.WIDTH * Game.SCALE) - 100, 32);
		}

		bs.show();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameState == Game.GAME_RUN) {
			scenario.keyPressed(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (gameState == Game.GAME_RUN) {
			scenario.keyReleased(e);

			if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				this.updateGameState(Game.GAME_PAUSE);
			}
		} else if (gameState == Game.GAME_PAUSE) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				pause.menuUp();
			}

			if (e.getKeyCode() == KeyEvent.VK_S) {
				pause.menuDown();
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				pause.menuEnter();
			}

			if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				this.updateGameState(Game.GAME_RUN);
			}
		} else if (gameState == Game.GAME_MENU) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				mainMenu.menuUp();
			}

			if (e.getKeyCode() == KeyEvent.VK_S) {
				mainMenu.menuDown();
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				mainMenu.menuEnter();
			}
		} else if (gameState == Game.GAME_TUTORIAL || gameState == Game.GAME_CREDITS || gameState == Game.GAME_GAME_OVER) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.updateGameState(Game.GAME_MENU);
			}
		}

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
