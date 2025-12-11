package cz.koscak.jan;

import java.util.List;

public class Car {

    private int x, y, vx, vy;

    public Car(int x, int y, int vx, int vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
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

    public void move(List<TrafficStop> listOfTrafficStops, List<RailroadCrossing> listOfRailroadCrossings) {
        boolean stop = false;
        for (TrafficStop trafficStop : listOfTrafficStops) {
            if (trafficStop.stop(this)) {
                stop = true;
                //System.out.println("!!! STOP: " + trafficStop);
            }
        }
        for (RailroadCrossing railroadCrossing : listOfRailroadCrossings) {
            if (railroadCrossing.stop(this)) {
                stop = true;
                //System.out.println("!!! STOP: " + trafficStop);
            }
        }
        if (!stop) {
            x = x + vx;
            y = y + vy;
        }

        if (x < 200) vx = 0;
        if (x > 660) vx = 0;
        //if (y < 200) vy = 0;
        //if (y > 660) vy = 0;
        if (y < 150) y = 710;
        if (y > 710) y = 150;
    }

}
