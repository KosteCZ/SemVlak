package cz.koscak.jan;

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

    public void move() {
        x = x + vx;
        y = y + vy;

        if (x < 200) vx = 0;
        if (x > 660) vx = 0;
        if (y < 200) vy = 0;
        if (y > 660) vy = 0;
    }

}
