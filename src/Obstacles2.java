
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Obstacles2 {
	
	private int obstacleX = 637;
	private int obstacleY = -200;
	private double obstacleDx = -3;
	private int obstacleWidth = 74;
	private int obstacleHeight = 400;
	private int obstacleGap = 165;
	
	public void update(Game gm, Bird b){
		//obstacle movement
		obstacleX += obstacleDx;
		//randomizing obstacles
		if(obstacleX + obstacleWidth < 0){
			Random r = new Random();
			int randomNum = r.nextInt(260);
			obstacleX = gm.getWidth();
			obstacleY = -350 + randomNum;
			}
		//check for collision
		if(b.getX() + b.getRadius() > obstacleX && b.getX() - b.getRadius() < obstacleX + obstacleWidth){
			if(b.getY() - b.getRadius() < obstacleY + obstacleHeight){
				gm.gameOverAllow();
			}
			if(b.getY() + b.getRadius() > obstacleY + obstacleHeight + obstacleGap){
				gm.gameOverAllow();
			}
		}
		//score counter
		if(b.getX() + b.getRadius() > obstacleX + obstacleWidth/2 && b.getX() - b.getRadius() < obstacleX){
			gm.scoreCounter();
		}
	}
	
	public void paint(Graphics g){
		g.setColor(new Color(18, 230, 3));
		g.fillRect(obstacleX, obstacleY, obstacleWidth, obstacleHeight);
		g.fillRect(obstacleX, obstacleY + obstacleHeight + obstacleGap, obstacleWidth, obstacleHeight);
	}
}

