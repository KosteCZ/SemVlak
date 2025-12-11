package cz.koscak.jan;

import java.util.List;

public class RailroadCrossing {

    private int stopX, stopY, vx, vy, checkX1, checkX2, checkY1, checkY2;
    private RailroadCrossing.Light state;
    private int barState = 0;

    public RailroadCrossing(int stopX, int stopY, int vx, int vy, Light state,
                            int checkX1, int checkX2, int checkY1, int checkY2) {
        this.stopX = stopX;
        this.stopY = stopY;
        this.state = state;
        this.vx = vx;
        this.vy = vy;
        this.checkX1 = checkX1;
        this.checkX2 = checkX2;
        this.checkY1 = checkY1;
        this.checkY2 = checkY2;
    }

    public enum Light {
        RED, WHITE
    }

    public boolean stop(Car car) {
        if (car.getX() == stopX && car.getY() == stopY) {
            if ((vx > 0 && car.getVX() > 0)
                    || (vx < 0 && car.getVX() < 0)
                    || (vy > 0 && car.getVY() > 0)
                    || (vy < 0 && car.getVY() < 0)) {
                return state.equals(Light.RED);
            }
        }
        return false;
    }

    public void checkState(List<Train> listOfTrains) {
        Light newLightState = Light.WHITE;
        for (Train train : listOfTrains) {
            if (train.getX() >= checkX1
                    && train.getX() <= checkX2
                    && train.getY() >= checkY1
                    && train.getY() <= checkY2) {
                newLightState = Light.RED;
            }
        }
        state = newLightState;
        if (newLightState == Light.RED && barState > 0) {
            barState = barState - 5;
        }
        if (newLightState == Light.WHITE && barState < 60) {
            barState = barState + 5;
        }
    }

    public int getBarState() {
        return barState;
    }
}
