import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 400;
    static final int SCREEN_HEIGHT = 500;
    static final int TOP_PANEL_HEIGHT = 100;
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
    JLabel scoreLabelW;
    JLabel scoreLabelB;
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


        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, TOP_PANEL_HEIGHT));
        topPanel.setBackground(Color.DARK_GRAY);

        scoreLabelW = new JLabel("White jet score: " + whiteScore);
        scoreLabelW.setFont(new Font("Arial Narrow", Font.BOLD, 20));
        scoreLabelW.setForeground(Color.black);

        scoreLabelB = new JLabel("Black jet score: " + blackScore);
        scoreLabelB.setFont(new Font("Arial Narrow", Font.BOLD, 20));
        scoreLabelB.setForeground(Color.black);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(10, 25, 10, 10);

        topPanel.add(scoreLabelW, gbc);

        gbc.gridx = 1;
        topPanel.add(scoreLabelB, gbc);

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);



        startGame();
    }
    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

        blackX = SCREEN_WIDTH / 2;
        blackY = TOP_PANEL_HEIGHT;
        whiteY = TOP_PANEL_HEIGHT;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            Graphics2D g2d = (Graphics2D) g;

            // Draw and rotate white plane
            AffineTransform oldTransform = g2d.getTransform();
            g2d.rotate(getAngle(directionW), whiteX + PLANE_SIZE / 2, whiteY + PLANE_SIZE / 2);
            int[] xPointsWhite = {whiteX + PLANE_SIZE / 2, whiteX, whiteX + PLANE_SIZE};
            int[] yPointsWhite = {whiteY, whiteY + PLANE_SIZE, whiteY + PLANE_SIZE};
            int nPointsWhite = 3;
            Polygon whitePlane = new Polygon(xPointsWhite, yPointsWhite, nPointsWhite);
            g2d.setColor(Color.white);
            g2d.fill(whitePlane);
            g2d.setTransform(oldTransform);

            // Draw and rotate black plane
            g2d.rotate(getAngle(directionB), blackX + PLANE_SIZE / 2, blackY + PLANE_SIZE / 2);
            int[] xPointsBlack = {blackX + PLANE_SIZE / 2, blackX, blackX + PLANE_SIZE};
            int[] yPointsBlack = {blackY, blackY + PLANE_SIZE, blackY + PLANE_SIZE};
            int nPointsBlack = 3;
            Polygon blackPlane = new Polygon(xPointsBlack, yPointsBlack, nPointsBlack);
            g2d.setColor(Color.black);
            g2d.fill(blackPlane);
            g2d.setTransform(oldTransform);

            for (Bullet bullet : bullets) bullet.drawBullet(g);
        }
    }

    private double getAngle(char direction) {
        switch (direction) {
            case 'U':
                return Math.toRadians(0); // Up
            case 'D':
                return Math.toRadians(180); // Down
            case 'L':
                return Math.toRadians(270); // Left
            case 'R':
                return Math.toRadians(90);// Right
            default:
                return 0;
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
            if (bullet.x > SCREEN_WIDTH || bullet.x < 0 || bullet.y > SCREEN_HEIGHT || bullet.y < TOP_PANEL_HEIGHT) {
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
         bullets.add(new Bullet(planeX, planeY, planeDirection, white));
        repaint();
    }


    public void checkCollisions() {
        //planes collisions
     /*   if ((whiteX + PLANE_SIZE > blackX && whiteX < blackX + PLANE_SIZE) &&
                (whiteY + PLANE_SIZE > blackY && whiteY < blackY + PLANE_SIZE)) {
            running = false;
        }*/

        // bullet collisions
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.move();

            // Check collision of bullet with white plane
            if (!bullet.isWhite && bullet.intersects(whiteX, whiteY, PLANE_SIZE, PLANE_SIZE)) {
                whiteScore++;
                scoreLabelW.setText("White jet score: " + whiteScore);
                bulletIterator.remove();
            }

            // Check collision of bullet with black plane
            if (bullet.isWhite && bullet.intersects(blackX, blackY, PLANE_SIZE, PLANE_SIZE)) {
                blackScore++;
                scoreLabelB.setText("Black jet score: " + blackScore);
                bulletIterator.remove();
            }
        }


        // Check for collision with left border
        if (whiteX < 0) {
            whiteX = SCREEN_WIDTH;
        }
        // Check for collision with right border
        if (whiteX > SCREEN_WIDTH) {
            whiteX = 0;
        }
        // Check for collision with top border
        if (whiteY < TOP_PANEL_HEIGHT) {
            whiteY = SCREEN_HEIGHT;
        }
        // Check for collision with bottom border
        if (whiteY > SCREEN_HEIGHT) {
            whiteY = TOP_PANEL_HEIGHT;
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
        if (blackY < TOP_PANEL_HEIGHT) {
            blackY = SCREEN_HEIGHT ;
        }
        // Check for collision with bottom border for black jet
        if (blackY > SCREEN_HEIGHT) {
            blackY = TOP_PANEL_HEIGHT;
        }

    }


    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                        directionW = 'L';

                    break;
                case KeyEvent.VK_RIGHT:
                        directionW = 'R';
                    break;
                case KeyEvent.VK_UP:
                        directionW = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                        directionW = 'D';
                    break;
                case KeyEvent.VK_D:
                        directionB = 'R';
                    break;
                case KeyEvent.VK_A:
                        directionB = 'L';
                    break;
                case KeyEvent.VK_W:
                        directionB = 'U';

                    break;
                case KeyEvent.VK_S:
                        directionB = 'D';

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
