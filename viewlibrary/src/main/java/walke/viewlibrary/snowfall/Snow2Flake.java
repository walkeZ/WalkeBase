package walke.viewlibrary.snowfall;

/**
 * Created by heqiang on 16-12-1.
 */
public class Snow2Flake {
    public int startX;
    public int startY;
    public int x;
    public int y;
    public long startTimeVertical;
    public long startTimeHorizontal;
    public boolean isLive;
    public int alpha;
    public int speedVertical;
    public float scale;
    public int index;

    public Snow2Flake(){
        this.startX = 0;
        this.startY = 0;
        this.x = 0;
        this.y = 0;
        this.startTimeVertical = 0;
        this.alpha = 255;
        this.speedVertical = 0;
        this.isLive = false;
        this.scale = 1;
    }
}
