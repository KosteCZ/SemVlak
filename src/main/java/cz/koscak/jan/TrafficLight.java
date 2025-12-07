package cz.koscak.jan;

import java.awt.image.BufferedImage;

public class TrafficLight {

    private int x, y;
    private Light state;
    private int timeToNextStateChange = 100;

    public TrafficLight(int x, int y) {
        this(x, y, Light.RED);
    }

    public TrafficLight(int x, int y, Light state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public enum Light {
        RED, RED_YELLOW, YELLOW, GREEN;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void time() {
        timeToNextStateChange--;
        if (timeToNextStateChange <= 0) {
            if (state == Light.RED) {
                state = Light.RED_YELLOW;
                timeToNextStateChange = 15;
                return;
            }
            if (state == Light.RED_YELLOW) {
                state = Light.GREEN;
                timeToNextStateChange = 100;
                return;
            }
            if (state == Light.GREEN) {
                state = Light.YELLOW;
                timeToNextStateChange = 15;
                return;
            }
            if (state == Light.YELLOW) {
                state = Light.RED;
                timeToNextStateChange = 100;
                return;
            }
        }
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
