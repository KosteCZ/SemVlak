package cz.koscak.jan;

public class TrafficStop {

    private int x, y, vx, vy;
    private TrafficLight trafficLight;

    public TrafficStop(int x, int y, int vx, int vy, TrafficLight trafficLight) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.trafficLight = trafficLight;
    }

    public boolean stop(Car car) {
        if (car.getX() == x && car.getY() == y) {
            if ((vx > 0 && car.getVX() > 0)
                    || (vx < 0 && car.getVX() < 0)
                    || (vy > 0 && car.getVY() > 0)
                    || (vy < 0 && car.getVY() < 0)) {
                return trafficLight.getState().equals(TrafficLight.Light.RED)
                        || trafficLight.getState().equals(TrafficLight.Light.RED_YELLOW);
            }
        }
        return false;
    }

}
