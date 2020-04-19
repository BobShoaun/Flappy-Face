
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class Obstacles {

	private int obstacleX = 600;
	private int obstacle2X = 837;
	private int obstacleY = -200;
	private int obstacle2Y = -200;
	private double obstacleDx = -3;
	private int obstacleWidth = 74;
	private int obstacleHeight = 400;
	private int obstacleGap = 165;

	private Random r = new Random();

	public static Image obstacleImageTop;
	public static Image obstacleImageBottom;
	public static AudioClip boom;
	public static AudioClip dadeng;

	public void update(Game gm, Bird b) {
		// obstacle movement
		obstacleX += obstacleDx;
		obstacle2X += obstacleDx;
		// randomizing obstacles
		if (obstacleX + obstacleWidth < 0) {
			int randomNum = r.nextInt(270);
			obstacleX = gm.getWidth();
			obstacleY = -370 + randomNum;
		}
		if (obstacle2X + obstacleWidth < 0) {
			int randomNum = r.nextInt(270);
			obstacle2X = gm.getWidth();
			obstacle2Y = -370 + randomNum;
		}
		// check for collision
		if (b.getX() + b.getRadius() > obstacleX && b.getX() - b.getRadius() < obstacleX + obstacleWidth) {
			if (b.getY() - b.getRadius() < obstacleY + obstacleHeight) {
				gm.gameOverAllow();
				boom.play();
			}
			if (b.getY() + b.getRadius() > obstacleY + obstacleHeight + obstacleGap) {
				gm.gameOverAllow();
				boom.play();
			}
		}
		if (b.getX() + b.getRadius() > obstacle2X && b.getX() - b.getRadius() < obstacle2X + obstacleWidth) {
			if (b.getY() - b.getRadius() < obstacle2Y + obstacleHeight) {
				gm.gameOverAllow();
				boom.play();
			}
			if (b.getY() + b.getRadius() > obstacle2Y + obstacleHeight + obstacleGap) {
				gm.gameOverAllow();
				boom.play();
			}
		}
		// score counter
		if (b.getX() + b.getRadius() > obstacleX + obstacleWidth / 2 && b.getX() - b.getRadius() < obstacleX) {
			gm.scoreCounter();
		}
		if (b.getX() + b.getRadius() > obstacle2X + obstacleWidth / 2 && b.getX() - b.getRadius() < obstacle2X) {
			gm.scoreCounter();
		}
	}

	public void paint(Graphics g, Game gm) {
		g.drawImage(obstacleImageTop, obstacleX, obstacleY, gm);
		g.drawImage(obstacleImageBottom, obstacleX, obstacleY + obstacleHeight + obstacleGap, gm);
		g.drawImage(obstacleImageTop, obstacle2X, obstacle2Y, gm);
		g.drawImage(obstacleImageBottom, obstacle2X, obstacle2Y + obstacleHeight + obstacleGap, gm);
	}
}