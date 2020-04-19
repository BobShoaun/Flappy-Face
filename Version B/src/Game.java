
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

public class Game extends Applet implements Runnable, KeyListener, MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private Image doubleI;
	private Graphics doubleG;
	private int score = 0;

	private int gameOverMessageY = -50;
	private int gameOverMessageDy = 5;
	private int gameOverMenuY = 600;
	private int gameOverMenuDy = 0;
	private int scoreY = 950;
	private boolean replayButtonAppear = false;

	private boolean gameStartMenu = true;
	private boolean gameStart = false;
	private boolean gameOver = false;
	private boolean gameOverMenu = false;
	private boolean birdMove = false;
	private boolean mouseInPlayButton = false;
	private boolean mouseInReplayButton = false;
	private boolean mouseInCreditButton = false;

	private Image backgroundImage;
	private Image teamDoxelTradeMarkImage;
	private Image gameTitleImage;
	private Image playButtonImage;
	private Image creditButtonImage;
	private Image gameOnMessageImage;
	private Image spacebarMessageImage;
	private Image gameOverMenuImage;
	private Image gameOverMessageImage;
	private Image replayButtonImage;
	private URL url;
	
	private AudioClip tak;
	private AudioClip foop;
	private AudioClip backgroundMusic;
	
	private Bird b = new Bird();
	private Obstacles obs = new Obstacles();
	private Ground grd = new Ground();
	
	public void gameOverAllow() {
		gameOver = true;
		// bird hits ground then trigger menu
		if (b.getY() - b.getRadius() > grd.getGroundY()) {
			gameOverMenu = true;
			gameOverMessageY += gameOverMessageDy;
			gameOverMenuY += gameOverMenuDy;
			scoreY += gameOverMenuDy;
		}
		// game over menu sequence
		if (gameOverMessageY > 0) {
			gameOverMessageY = 0;
			gameOverMenuDy = -15;
		}
		if (gameOverMenuY < 0 && scoreY < 350) {
			gameOverMenuY = 0;
			scoreY = 350;
			replayButtonAppear = true;
		}
	}

	public void scoreCounter() {
		score++;
		Obstacles.dadeng.play();
	}

	public void init() {
		setSize(400, 600);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		try {
			url = getDocumentBase();
		} catch (Exception e) {

		}
		backgroundImage = getImage(url, "images/Background.png");
		Obstacles.obstacleImageTop = getImage(url, "images/ObstacleTop.png");
		Obstacles.obstacleImageBottom = getImage(url, "images/ObstacleBottom.png");
		Ground.groundImage = getImage(url, "images/Ground.png");
		teamDoxelTradeMarkImage = getImage(url, "images/TeamDoxelTradeMark.png");
		gameTitleImage = getImage(url, "images/GameTitle.png");
		playButtonImage = getImage(url, "images/PlayButton.png");
		creditButtonImage = getImage(url, "images/CreditButton.png");
		gameOnMessageImage = getImage(url, "images/GameOnMessage.png");
		spacebarMessageImage = getImage(url, "images/SpacebarMessage.png");
		gameOverMenuImage = getImage(url, "images/GameOverMenu.png");
		gameOverMessageImage = getImage(url, "images/GameOverMessage.png");
		replayButtonImage = getImage(url, "images/ReplayButton.png");
		
		tak = getAudioClip(url, "audio/Tak.au");
		foop = getAudioClip(url, "audio/Foop.au");
		backgroundMusic = getAudioClip(url, "audio/BackgroundMusic.au");
		Obstacles.boom = getAudioClip(url, "audio/Boom.au");
		Obstacles.dadeng = getAudioClip(url, "audio/DaDeng.au");
		
	
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		while (true) {
			if (birdMove) {
				b.update(this);
			}
			if (gameOver) {
				grd.setGroundDx(0);
			} else {
				if (gameStart) {
					obs.update(this, b);
				}
			}
			grd.update(this, b);
			// setting frame rate
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}

	public void update(Graphics g) {
		// double buffering
		if (doubleI == null) {
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
		String currentScore = Integer.toString(score);
		g.drawImage(backgroundImage, (int) 0, 0, this);
		obs.paint(g, this);
		grd.paint(g, this);
		g.drawImage(teamDoxelTradeMarkImage, 0, 0, this);
		if (gameStartMenu) {
			// game title
			g.drawImage(gameTitleImage, 0, 0, this);
			// credit button
			if (mouseInCreditButton) {
				g.drawImage(creditButtonImage, 3, 3, this);
			} else {
				g.drawImage(creditButtonImage, 0, 0, this);
			}
			// play button
			if (mouseInPlayButton) {
				g.drawImage(playButtonImage, 3, 3, this);
			} else {
				g.drawImage(playButtonImage, 0, 0, this);
			}
		} else {
			b.paint(g);
			if (gameStart) {
				if (!gameOverMenu) {
					// paint score
					Font font1 = new Font("Impact", Font.BOLD, 60);
					g.setFont(font1);
					g.setColor(Color.BLACK);
					g.drawString(currentScore, 193, 153);
					g.setColor(Color.WHITE);
					g.drawString(currentScore, 190, 150);
				}
			} else {
				// game on message
				g.drawImage(gameOnMessageImage, 0, 0, this);
				g.drawImage(spacebarMessageImage, 0, -80, this);
			}
		}
		// game over menu
		if (gameOverMenu) {
			g.drawImage(gameOverMenuImage, 0, gameOverMenuY, this);
			g.drawImage(gameOverMessageImage, 0, gameOverMessageY, this);
			// score
			Font font2 = new Font("Impact", Font.PLAIN, 60);
			g.setFont(font2);
			g.setColor(Color.BLACK);
			g.drawString(currentScore, 78, scoreY + 3);
			g.setColor(Color.WHITE);
			g.drawString(currentScore, 75, scoreY);
		}
		// replay button
		if (replayButtonAppear) {
			if (mouseInReplayButton) {
				g.drawImage(replayButtonImage, 3, 3, this);
			} else {
				g.drawImage(replayButtonImage, 0, 0, this);
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (!gameOver && !gameStartMenu) {
				b.moveUp();
				birdMove = true;
				gameStart = true;
				foop.play();
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (e.getX() > 230 && e.getX() < 370 && e.getY() > 410 && e.getY() < 480) {
			mouseInReplayButton = true;
		} else {
			mouseInReplayButton = false;
		}
		if (e.getX() > 40 && e.getX() < 180 && e.getY() > 363 && e.getY() < 418) {
			mouseInPlayButton = true;
		} else {
			mouseInPlayButton = false;
		}
		if (e.getX() > 200 && e.getX() < 380 && e.getY() > 363 && e.getY() < 418) {
			mouseInCreditButton = true;
		} else {
			mouseInCreditButton = false;
		}
	}

	public void mouseClicked(MouseEvent e) {
		// game reset
		if (gameOverMenu) {
			if (mouseInReplayButton) {
				score = 0;
				birdMove = false;
				gameStart = false;
				gameOver = false;
				gameOverMenu = false;
				b = new Bird();
				obs = new Obstacles();
				grd = new Ground();

				gameOverMessageY = -50;
				gameOverMessageDy = 5;
				gameOverMenuY = 600;
				gameOverMenuDy = 0;
				scoreY = 950;
				replayButtonAppear = false;
				
				tak.play();
			}
		}
		if (mouseInPlayButton) {
			gameStartMenu = false;
			tak.play();
		}
	}

	// unused methods
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