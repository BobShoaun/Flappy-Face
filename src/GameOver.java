import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class GameOver {
	private int suckX, suckY, suckDy, leadX, leadY, leadDy, restX, restY, restDy, startX, startY, startDy;
	public static Image suckImage, leadImage, restImage, startImage;
	public static URL suckURL, leadURL, restURL, startURL;
	public static boolean appear = false;
	
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
		suckY = 600;
		suckDy = -10;
		
		leadY = 600;
		leadDy = -14;
	}
	
	public void update(){

		if(suckY <= 600){
			suckY += suckDy;
		}
		if(leadY <= 600 && suckY < 0){
			leadY += leadDy;
		}
		if(suckY < 0){
			suckDy = 0;
		}
		
		if(leadY < 0){
			leadDy = 0;
		}
	}
	
	public void paint(Graphics g, Game gm){
		g.drawImage(suckImage, suckX, suckY, gm);
		g.drawImage(leadImage, leadX, leadY, gm);
		if(leadY < 0){
			try {
				TimeUnit.MILLISECONDS.sleep(80);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(startImage, startX, startY, gm);
			g.drawImage(restImage, restX, restY, gm);
			appear = true;
		}
	}
}
