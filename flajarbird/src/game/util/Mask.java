package game.util;

import java.awt.Rectangle;

public class Mask {

	private Rectangle rectangle;

	public Mask(int x, int y, int width, int height) {
		this.rectangle = new Rectangle(x, y, width, height);
	}

	public void update(int x, int y, int width, int height) {
		rectangle = new Rectangle(x, y, width, height);
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public boolean iscolliding(Mask mask) {
		return this.getRectangle().intersects(mask.getRectangle());
	}

}
