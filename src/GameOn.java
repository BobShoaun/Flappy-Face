import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

public class GameOn {
	
	private int gameX, gameY, gameDy, spaceX, spaceY, spaceDy;
	public static Image gameImage, spaceImage;
	public static URL gameURL, spaceURL;
	
	public GameOn() {
		gameX = 0;
		gameY = 0;
		gameDy = 0;
		spaceX = 0;
		spaceY = 0;
		spaceDy = 0;
	}
	
	public void paint(Graphics g, Game gm){
		g.drawImage(gameImage, gameX, gameY, gm);
		g.drawImage(spaceImage, spaceX, spaceY, gm);
	}
}
