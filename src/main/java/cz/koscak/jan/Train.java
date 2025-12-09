package cz.koscak.jan;

import java.util.List;

public class Train {

    private int x, y, vx, vy;

    private boolean isLocomotive = false;

    public Train(int x, int y, int vx, int vy, boolean locomotive) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.isLocomotive = locomotive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVX() {
        return vx;
    }

    public int getVY() {
        return vy;
    }

    public boolean isLocomotive() {
        return isLocomotive;
    }

    public void move(List<TrafficStop> listOfTrafficStops) {
        boolean stop = false;
        /*for (TrafficStop trafficStop : listOfTrafficStops) {
            if (trafficStop.stop(this)) {
                stop = true;
                //System.out.println("!!! STOP: " + trafficStop);
            }
        }*/
        if (!stop) {
            x = x + vx;
            y = y + vy;
        }

        if (x < 0) x = 400;
        if (x > 400) x = 0;
        //if (y < 200) vy = 0;
        //if (y > 660) vy = 0;
        if (y < 0) y = 700;
        if (y > 700) y = 0;
    }

}
