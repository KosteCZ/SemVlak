package cz.koscak.jan;

import java.util.List;

public class Car {

    private int x, y, vx, vy;

    private final Image image;

    public Car(int x, int y, int vx, int vy, Image image) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.image = image;
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

    public void move(List<TrafficStop> listOfTrafficStops, List<RailroadCrossing> listOfRailroadCrossings,
            List<Car> listOfCars) {
        boolean stop = false;
        for (TrafficStop trafficStop : listOfTrafficStops) {
            if (trafficStop.stop(this)) {
                stop = true;
                //System.out.println("!!! STOP: " + trafficStop);
                return;
            }
        }
        for (RailroadCrossing railroadCrossing : listOfRailroadCrossings) {
            if (railroadCrossing.stop(this)) {
                stop = true;
                //System.out.println("!!! STOP: " + railroadCrossing);
                return;
            }
        }
        for (Car car : listOfCars) {
            int nextX = x + 50 * vx;
            int nextY = y + 50 * vy;
            if (car.getX() == nextX && car.getY() == nextY) {
                stop = true;
                //System.out.println("!!! STOP - WAIT FOR ANOTHER CAR: " + x +  "->" + nextX +  ", " + y + "->" + nextY);
                return;
            }
        }
        if (!stop) {
            x = x + vx;
            y = y + vy;
        }

        if (x < 0) x = 810;
        if (x > 810) x = 0;

        if (y < 150) y = 710;
        if (y > 710) y = 150;
    }

    public Image getImage() {
        return image;
    }
}
