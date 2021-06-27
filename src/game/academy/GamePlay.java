package game.academy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;

    private static final int PLAYER_SPEED = 20;

    private int playerX = 310; // starting position of slider
    private int ballX = 120;
    private int ballY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private static final int RIGHT_BOUND = 600;
    private static final int LEFT_BOUND = 10;

    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        // the ball
        g.setColor(Color.yellow);
        g.fillOval(ballX, ballY, 20, 20);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        ballMovement();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight();
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft();
        }
    }

    private void ballMovement() {
        if (play) {
            if(platformBallCollision()) ballBouncesOffPlatform();
            handleBallBricksCollision();
            ballX += ballXdir;
            ballY += ballYdir;
            if (ballX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballX > 670) {
                ballXdir = -ballXdir;
            }
        }
    }

    private void handleBallBricksCollision() {
        A: for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[0].length; j++) {
                boolean brickIsVisible = map.map[i][j] > 0;
                if (brickIsVisible) {
                    // create rectangle masks for ball and bricks in order to calculate collision
                    int brickX = j * map.brickWidth + 80;
                    int brickY = i * map.brickHeight + 50;
                    int brickWidth = map.brickWidth;
                    int brickHeight = map.brickHeight;
                    Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);
                    if (ballRect.intersects(brickRect)) {
                        map.setBrickValue(0, i, j);
                        totalBricks--;
                        score += 5;
                        if (ballX + 19 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width) {
                            ballXdir = -ballXdir;
                        } else ballYdir = -ballYdir;
                        break A;
                    }
                }
            }
        }
    }

    private void moveRight() {
        Boolean outOfBounds = playerX >= RIGHT_BOUND;
        if (outOfBounds) playerX = RIGHT_BOUND;
        else {
            play = true;
            playerX += PLAYER_SPEED;
        }
    }

    private void moveLeft () {
        Boolean outOfBounds = playerX < LEFT_BOUND;
        if (outOfBounds) playerX = LEFT_BOUND;
        else {
            play = true;
            playerX -= PLAYER_SPEED;
        }
    }

    private boolean platformBallCollision() {
        return (new Rectangle(ballX, ballY, 20, 20))
                .intersects(
                (new Rectangle(playerX, 550, 100, 8)));
    }

    private void ballBouncesOffPlatform() {
        ballYdir = -ballYdir;
    }
}
