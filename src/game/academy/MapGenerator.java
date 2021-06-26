package game.academy;

import java.awt.*;

public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    private final static int VISIBLE_BRICK = 1;

    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = VISIBLE_BRICK;
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                boolean brickIsVisible = map[i][j] > 0;
                if (brickIsVisible)  {
                    displayBrick(g, j, i);
                }
            }
        }
    }

    public void displayBrick(Graphics2D g, int j, int i) {
        g.setColor(Color.white);
        g.fillRect(j * brickWidth + 80, i * brickHeight, brickWidth, brickHeight);
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.black);
        g.drawRect(j * brickWidth + 80, i * brickHeight, brickWidth, brickHeight);
    }

}
