package cz.koscak.jan;

import java.awt.image.BufferedImage;

public class TrafficLight {

    private int x, y;
    private Light state;

    public TrafficLight(int x, int y) {
        this.x = x;
        this.y = y;
        state = Light.RED;
    }

    enum Light {
        RED, RED_YELLOW, YELLOW, GREEN;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return switch (state) {
            case RED -> Image.TRAFFIC_LIGHTS_RED;
            case RED_YELLOW -> Image.TRAFFIC_LIGHTS_RED_YELLOW;
            case YELLOW -> Image.TRAFFIC_LIGHTS_YELLOW;
            case GREEN -> Image.TRAFFIC_LIGHTS_GREEN;
            default -> Image.TRAFFIC_LIGHTS_RED;
        };
    }

}
