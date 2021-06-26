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
    private int delay = 1;

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

    private void moveRight() {
        Boolean outOfBounds = playerX >= RIGHT_BOUND;
        if (outOfBounds) playerX = RIGHT_BOUND;
        else {
            play = true;
            playerX += PLAYER_SPEED;
        }
    }

    private void moveLeft () {
        Boolean outOfBounds = playerX <= LEFT_BOUND;
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
