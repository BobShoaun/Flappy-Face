import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

public class GameOver {
	private int suckX, suckY, suckDy, leadX, leadY, leadDy, restX, restY, restDy, startX, startY, startDy;
	public static Image suckImage, leadImage, restImage, startImage;
	public static URL suckURL, leadURL, restURL, startURL;
	
	
	
	public void setSuckY(int suckY) {
		this.suckY = suckY;
	}

	public void setLeadY(int leadY) {
		this.leadY = leadY;
	}

	public void setRestY(int restY) {
		this.restY = restY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public GameOver() {
		// TODO Auto-generated constructor stub
	}
	
	public void update(){
		
	}
	
	public void paint(Graphics g, Game gm){
		g.drawImage(suckImage, suckX, suckY, gm);
		g.drawImage(leadImage, leadX, leadY, gm);
		g.drawImage(startImage, startX, startY, gm);
		g.drawImage(restImage, restX, restY, gm);
	}
}
