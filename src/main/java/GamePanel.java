import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 300;
    static final int SCREEN_HEIGHT = 300;
    static final int PLANE_SIZE = 20;
    static final int DELAY = 10;
    int whiteX;
    int whiteY;
    int blackX;
    int blackY;
    int whiteScore = 0;
    int blackScore = 0;
    char directionW = 'D';
    char directionB = 'D';
    Timer timer;
    boolean running = false;
    JLabel scoreLabel;
    Random random;
    Color background = new Color(3, 72, 97);

    ArrayList<Bullet> bullets = new ArrayList<>();

    GamePanel() {
        long seed = System.currentTimeMillis();
        this.random = new Random(seed);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(background);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        startGame();
    }


    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

        blackX = SCREEN_WIDTH / 2;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.white);
            g.fillRect(whiteX, whiteY, PLANE_SIZE, PLANE_SIZE);

            g.setColor(Color.black);
            g.fillRect(blackX, blackY, PLANE_SIZE, PLANE_SIZE);

            for (Bullet bullet : bullets) bullet.drawBullet(g);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollisions();
        }
        repaint();
    }

    public void move() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.move();
            if (bullet.x > SCREEN_WIDTH || bullet.x < 0 || bullet.y > SCREEN_HEIGHT || bullet.y < 0) {
                iterator.remove();
            }
        }
        switch (directionW) {
            case 'U':
                whiteY = whiteY - 1;
                break;
            case 'D':
                whiteY = whiteY + 1;
                break;
            case 'L':
                whiteX = whiteX - 1;
                break;
            case 'R':
                whiteX = whiteX + 1;
                break;
            default:
                break;
        }
        switch (directionB) {
            case 'U':
                blackY = blackY - 1;
                break;
            case 'D':
                blackY = blackY + 1;
                break;
            case 'L':
                blackX = blackX - 1;
                break;
            case 'R':
                blackX = blackX + 1;
                break;
            default:
                break;
        }
    }

    public void shoot(boolean white) {
        int planeX, planeY;
        char planeDirection;

        if (white) {
            planeX = whiteX;
            planeY = whiteY;
            planeDirection = directionW;
        } else {
            planeX = blackX;
            planeY = blackY;
            planeDirection = directionB;
        }

        // Dodanie nowego pocisku na podstawie aktualnych współrzędnych samolotu
        bullets.add(new Bullet(planeX, planeY, planeDirection, white));
        repaint();
    }


    public void checkCollisions() {
        // Check for collision with left border
        if (whiteX < 0) {
            whiteX = SCREEN_WIDTH;
        }
        // Check for collision with right border
        if (whiteX > SCREEN_WIDTH) {
            whiteX = 0;
        }
        // Check for collision with top border
        if (whiteY < 0) {
            whiteY = SCREEN_HEIGHT;
        }
        // Check for collision with bottom border
        if (whiteY > SCREEN_HEIGHT) {
            whiteY = 0;
        }

        // Check for collision with left border for black jet
        if (blackX <= 0) {
            blackX = SCREEN_WIDTH;
        }
        // Check for collision with right border for black jet
        if (blackX > SCREEN_WIDTH) {
            blackX = 0;
        }
        // Check for collision with top border for black jet
        if (blackY < 0) {
            blackY = SCREEN_HEIGHT;
        }
        // Check for collision with bottom border for black jet
        if (blackY > SCREEN_HEIGHT) {
            blackY = 0;
        }
        //planes collisions
        if ((whiteX + PLANE_SIZE > blackX && whiteX < blackX + PLANE_SIZE) &&
                (whiteY + PLANE_SIZE > blackY && whiteY < blackY + PLANE_SIZE)) {
            running = false;
        }
    }


    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (directionW != 'R') {
                        directionW = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (directionW != 'L') {
                        directionW = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (directionW != 'D') {
                        directionW = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (directionW != 'U') {
                        directionW = 'D';
                    }
                    break;
                case KeyEvent.VK_D:
                    if (directionB != 'L') {
                        directionB = 'R';
                    }
                    break;
                case KeyEvent.VK_A:
                    if (directionB != 'R') {
                        directionB = 'L';
                    }
                    break;
                case KeyEvent.VK_W:
                    if (directionB != 'D') {
                        directionB = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                    if (directionB != 'U') {
                        directionB = 'D';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    shoot(false);
                    break;
                case KeyEvent.VK_ENTER:
                    shoot(true);

                    break;
            }
        }
    }
}
