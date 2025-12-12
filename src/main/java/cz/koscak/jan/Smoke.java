package cz.koscak.jan;

import java.awt.*;

public class Smoke {

    private int x, y, timeToDisappear;

    private Color color;

    public Smoke(int x, int y, int timeToDisappear, Color color) {
        this.x = x;
        this.y = y;
        this.timeToDisappear = timeToDisappear;
        this.color = color;
    }

    public void time() {
        timeToDisappear--;
        int random = (int) Math.round(Math.random() * 2) - 1;
        y = y + random;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTimeToDisappear() {
        return timeToDisappear;
    }

    public Color getColor() {
        return color;
    }

}
