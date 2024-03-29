import java.awt.*;

public class Bullet {
    static int BULLET_SIZE = 10;
    int x;
    int y;
    char direction;
    boolean isWhite;
    static final int SPEED = 4;

    public Bullet(int x, int y, char direction, boolean isWhite) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.isWhite = isWhite;
    }

    void move() {
        switch (direction) {
            case 'U':
                y -= SPEED;
                break;
            case 'D':
                y += SPEED;
                break;
            case 'L':
                x -= SPEED;
                break;
            case 'R':
                x += SPEED;
                break;
        }
    }
    void drawBullet(Graphics g){
        if(isWhite){
            g.setColor(Color.WHITE);
            g.fillOval(x, y, BULLET_SIZE,BULLET_SIZE);
        }else{
            g.setColor(Color.BLACK);
            g.fillOval(x, y, BULLET_SIZE,BULLET_SIZE);
        }
    }

    public boolean intersects(int x, int y, int width, int height) {
     return (this.x < x + width &&
                this.x + BULLET_SIZE > x &&
                this.y < y + height &&
                this.y + BULLET_SIZE > y);
    }
}
