package cz.koscak.jan;

import java.util.List;

public class Train {

    public static final int WAITING_IN_TRAIN_STATION = 50;
    private int x, y, vx, vy;

    private Train locomotive;

    private int waitingInTrainStation;

    private boolean stop = false;

    public Train(int x, int y, int vx, int vy, Train locomotive) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.locomotive = locomotive;
        waitingInTrainStation = 0;
        stop = false;
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
        return locomotive == null;
    }

    public Train getLocomotive() {
        return locomotive;
    }

    public boolean isStop() {
        return stop;
    }

    public void move(List<TrainStation> listOfTrainStations) {
        if (isLocomotive()) {
            if (waitingInTrainStation == 0) {
                for (TrainStation trainStation : listOfTrainStations) {
                    if (trainStation.getStopX() == x && trainStation.getStopY() == y) {
                        waitingInTrainStation = WAITING_IN_TRAIN_STATION;
                        stop = true;
                        break;
                    }
                }
            } else {
                waitingInTrainStation--;
            }
            if (waitingInTrainStation == 0) {
                stop = false;
                x = x + vx;
                y = y + vy;
            }
        } else {
            if(!getLocomotive().isStop()) {
                x = x + vx;
                y = y + vy;
            }
        }

        if (x < 0) x = 800;
        if (x > 800) x = 0;
        if (y < 0) y = 700;
        if (y > 700) y = 0;
    }

}
