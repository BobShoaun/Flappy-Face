

import java.awt.Color;
import java.awt.Graphics;

public class Bird {

	private int x = 160;
	private int y = 200;
	private double dy = 0;
	private int radius = 20;
	private int upwardMotion = -7;

	private double gravity = 2;
	private double dt = 0.2;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRadius() {
		return radius;
	}

	public void update(Game gm) {
		// setting gravity
		y += dy;
		dy += gravity * dt;
		y += dy * dt + 0.5 * gravity * dt * dt;
	}

	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	public void moveUp() {
		dy = upwardMotion;
	}
}