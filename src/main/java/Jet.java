import java.awt.*;
import java.util.Random;

public class Jet {
    int x;
    int y;
    Graphics imege;
    Jet(Graphics image){
        Random random = new Random();
        this.x = random.nextInt(GamePanel.SCREEN_WIDTH);
        this.y = random.nextInt(GamePanel.SCREEN_HEIGHT);
        this.imege = image;
    }
}
