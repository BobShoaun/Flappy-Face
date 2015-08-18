
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.xml.datatype.Duration;

public class Game extends Applet implements Runnable, KeyListener, MouseMotionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	
	private Image doubleI;
	private Graphics doubleG;
	private int score = 0;
	
	private boolean startMenu = true;
	private boolean mouseInPlay = false;
	private boolean birdMove = false;
	private boolean obstacleAppear = false;
	private boolean gameOver = false;
	private boolean gameOverMenu = false;
	private boolean mouseInTryAgain = false;
	private boolean startCredit = false;
	private boolean mouseInStart = false;
	
	private double backgroundX = 0;
	private URL backgroundURL;
	private Image backgroundImage;

	private Bird b = new Bird();
	private Obstacles obs = new Obstacles();
	private Obstacles2 obs2 = new Obstacles2();
	private Ground grd = new Ground();
	private Menu menu = new Menu();
	private Play play = new Play();
	private Credit credit = new Credit();
	private Doxel dox = new Doxel();
	private GameOn go = new GameOn();
	private GameOver boo = new GameOver();
	
	public void gameOverAllow() {
		gameOver = true;
		//bird hits ground then trigger menu
		if(b.getY() - b.getRadius() > grd.getGroundY()){
			gameOverMenu = true;
		}		
	}

	public void scoreCounter() {
		score++;
	}
	
	
	public void init() {
		setSize(400,600);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		try{
			GameOver.suckURL = GameOver.leadURL = GameOver.restURL = GameOver.startURL = getDocumentBase();
			GameOn.gameURL = GameOn.spaceURL = getDocumentBase();
			Doxel.url = getDocumentBase();
			Credit.url = getDocumentBase();
			Play.url = getDocumentBase();
			backgroundURL = getDocumentBase();
			Ground.groundURL = getDocumentBase();
			Menu.menuURL = getDocumentBase();
		}catch (Exception e){
			
		}
		GameOver.restImage = getImage(GameOver.restURL, "images/restart.png");
		GameOver.startImage = getImage(GameOver.startURL, "images/start.png");
		GameOver.leadImage = getImage(GameOver.leadURL, "images/leaderboard.png");
		GameOver.suckImage = getImage(GameOver.suckURL, "images/u suck.png");
		GameOn.gameImage = getImage(GameOn.gameURL, "images/game on!.png");
		GameOn.spaceImage = getImage(GameOn.spaceURL, "images/spacebar.png");
		Doxel.image = getImage(Doxel.url, "images/teamdoxel.png");
		Credit.image = getImage(Play.url, "images/credit.png");
		Play.image = getImage(Play.url, "images/play.png");
		Menu.menuImage = getImage(Menu.menuURL, "images/menu.png");
		backgroundImage = getImage(backgroundURL, "images/Background.png");
		Ground.groundImage = getImage(Ground.groundURL, "images/Ground.png");
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		while(true){
			if(!startMenu){
				if(birdMove){
					b.update(this);
				}
				if(obstacleAppear){
					if(!gameOver){
						obs.update(this, b);
						obs2.update(this, b);
						if(backgroundX + this.getWidth() < 0 ){
							backgroundX = 0;
						}
					}else{
						boo.update();
						grd.setGroundDx(0);
					}	
				}
			}
			grd.update(this, b);
			//setting frame rate
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	public void update(Graphics g) {
		//double buffering
		if(doubleI == null){
			doubleI = createImage(this.getWidth(), this.getHeight());
			doubleG = doubleI.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		doubleG.setColor(getForeground());
		paint(doubleG);
		
		g.drawImage(doubleI, 0, 0, this);
	}
	
	public void paint(Graphics g) {
		g.drawImage(backgroundImage, (int)backgroundX, 0, this);
		g.drawImage(backgroundImage, (int)backgroundX + this.getWidth(), 0, this);
		if(startMenu){
			//play button
			menu.paint(g, this);
			play.paint(g, this);
			credit.paint(g, this);
			if(mouseInPlay){
				//move button when hovered
				play.setY(5);	
			}else if(startCredit){
				credit.setY(5);
			}else{
				//restore button position when not hovered
				play.setY(0);
				credit.setY(0);
			}
		}else{
			if(obstacleAppear){
				String currentScore = Integer.toString(score);
				obs.paint(g);
				obs2.paint(g);
				//paint score
				if(!gameOverMenu){
					Font font1 = new Font("04b_19", Font.BOLD, 60);
					g.setFont(font1);
					g.setColor(Color.BLACK);
					g.drawString(currentScore, 193, 153);
					g.setColor(Color.WHITE);
					g.drawString(currentScore, 190, 150);
				}
			}else{
				//paint instructions
				go.paint(g, this);
			}		
		}
		//bird and ground are always painted
		grd.paint(g, this);
		b.paint(g);
		if(startMenu){
			// if it's not start menu, 'team doxel' word will not appear
			dox.paint(g, this);
		}else if(gameOverMenu){
			//initize game over menu
			boo.paint(g, this);
			if(boo.appear){
				String currentScore = Integer.toString(score);
				Font font3 = new Font("04b_19", Font.PLAIN, 60);
				Font font4 = new Font("04b_19", Font.BOLD, 63);
				g.setFont(font4);
				g.setColor(Color.BLACK);			
				g.drawString(currentScore , 75 + 3, 350 + 4);
				g.setFont(font3);
				g.setColor(new Color(250, 246, 168));
				g.drawString(currentScore, 75 , 350);
			}
			//Try again button
			if(mouseInTryAgain){
				boo.setRestY(5);
			}else if(mouseInStart){
				boo.setStartY(5);
			}else{
				boo.setRestY(0);
				boo.setStartY(0);
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(!gameOver){
				b.moveUp();
				birdMove = true;
				obstacleAppear = true;
			}			
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(e.getX() > 230 && e.getX() < 370 && e.getY() > 410 && e.getY() < 480){
				mouseInTryAgain = true;
			}else{
				mouseInTryAgain = false;
			}
		
		if(e.getX() > 40 && e.getX() < 180 && e.getY() > 363 && e.getY() < 418){
				mouseInPlay = true;
			}else{
				mouseInPlay = false;
			}
		
		if(e.getX() > 200 && e.getX() < 380 && e.getY() > 363 && e.getY() < 418){
			startCredit = true;
		}else{
			startCredit = false;
		}
		
		if(e.getX() > 40 && e.getX() < 180 && e.getY() > 410 && e.getY() < 480){
			mouseInStart = true;
		}else{
			mouseInStart = false;
		}
	}

	public void mouseClicked(MouseEvent e) {
		//game reset
		if(gameOverMenu){
			if(mouseInTryAgain){
				score = 0;
				boo.appear = false;
				birdMove = false;
				obstacleAppear = false;
				gameOver = false;
				gameOverMenu = false;			
				mouseInTryAgain = false;
				b = new Bird();
				obs = new Obstacles();
				obs2 = new Obstacles2();
				grd = new Ground();
				boo = new GameOver();
			}else if(mouseInStart){
				startMenu = true;
				boo.appear = false;
				score = 0;
				birdMove = false;
				obstacleAppear = false;
				gameOver = false;
				gameOverMenu = false;			
				mouseInTryAgain = false;
				b = new Bird();
				obs = new Obstacles();
				obs2 = new Obstacles2();
				grd = new Ground();
				boo = new GameOver();
			}	
		}
		if(mouseInPlay){
			startMenu = false;
		}
	}
	//unused methods
	public void stop() {

	}
	
	public void destroy() {

	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {

	}
}