
import java.awt.Graphics;
import java.awt.Image;

public class Ground {

	private int groundX = 0;
	private int groundY = 500;
	private int groundDx = -3;
	private int groundWidth = 400;

	public static Image groundImage;

	public int getGroundY() {
		return groundY;
	}

	public void setGroundDx(int groundDx) {
		this.groundDx = groundDx;
	}

	public void update(Game gm, Bird b) {
		// ground movement
		groundX += groundDx;
		if (groundX + groundWidth < 0) {
			groundX = 0;
		}
		// check for collision
		if (b.getY() + b.getRadius() > groundY) {
			gm.gameOverAllow();
			b.setY(groundY - b.getRadius());
		}
	}

	public void paint(Graphics g, Game gm) {
		g.drawImage(groundImage, groundX, groundY, gm);
		g.drawImage(groundImage, groundX + groundWidth, groundY, gm);
	}

}