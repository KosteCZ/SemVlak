package cz.koscak.jan;

public class TrainStation {

    private int stopX, stopY, vx, vy;

    public TrainStation(int stopX, int stopY, int vx, int vy) {
        this.stopX = stopX;
        this.stopY = stopY;
        this.vx = vx;
        this.vy = vy;
    }

    public int getStopX() {
        return stopX;
    }

    public int getStopY() {
        return stopY;
    }
}
