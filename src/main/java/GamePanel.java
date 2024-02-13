import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static  final int SCREEN_HEIGHT = 600;
    static final int PLANE_SIZE = 20;
    static final int DELAY = 10;
    int whiteX;
    int whiteY;
    int blackX;
    int blackY;
    int whiteScore = 0;
    int blackScore = 0;
    char directionW = 'R';
    char directionB = 'D';
    Timer timer;
    boolean running = false;
    JLabel scoreLabel;
Random random;
Color background = new Color(3, 72, 97);
    Jet blackJet;
    Jet whiteJet;


    GamePanel(){
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


    }

public void paintComponent(Graphics g){
    super.paintComponent(g);
    draw(g);

}

    public void setup(){

    }
public void draw(Graphics g){
        if(running) {
            g.setColor(Color.white);
            g.fillRect(whiteX, whiteY, PLANE_SIZE, PLANE_SIZE);

            g.setColor(Color.black);
            g.fillRect(blackX, blackY, PLANE_SIZE, PLANE_SIZE);

        }

}

public void move() {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
               }
        repaint();
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
            }
        }
    }
}
