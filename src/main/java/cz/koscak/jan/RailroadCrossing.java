package cz.koscak.jan;

import java.util.List;

public class RailroadCrossing {

    private int stopX, stopY, vx, vy, checkX1, checkX2, checkY1, checkY2;
    private State state;
    private Light light;
    private int barState = 0;
    private int timeToNextLight = 7;

    public RailroadCrossing(int stopX, int stopY, int vx, int vy, State state,
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
        if (state == State.RED) {
            light = Light.RED_LEFT;
        } else {
            light = Light.WHITE;
        }
        timeToNextLight = 7;
    }

    public enum State {
        RED, WHITE
    }

    public enum Light {
        RED_LEFT, RED_RIGHT, WHITE, NONE
    }

    public boolean stop(Car car) {
        if (car.getX() == stopX && car.getY() == stopY) {
            if ((vx > 0 && car.getVX() > 0)
                    || (vx < 0 && car.getVX() < 0)
                    || (vy > 0 && car.getVY() > 0)
                    || (vy < 0 && car.getVY() < 0)) {
                return state.equals(State.RED);
            }
        }
        return false;
    }

    public void checkState(List<Train> listOfTrains) {
        State newState = State.WHITE;
        for (Train train : listOfTrains) {
            if (train.getX() >= checkX1
                    && train.getX() <= checkX2
                    && train.getY() >= checkY1
                    && train.getY() <= checkY2) {
                newState = State.RED;
            }
        }
        if (state != newState) {
            state = newState;
            if (newState == State.RED) {
                light = Light.RED_LEFT;
            } else {
                light = Light.WHITE;
            }
        }
        if (newState == State.RED && barState > 0) {
            barState = barState - 5;
        }
        if (newState == State.WHITE && barState < 60) {
            barState = barState + 5;
        }
    }

    public int getBarState() {
        return barState;
    }

    public void time() {
        if (timeToNextLight <= 0) {
            timeToNextLight = 7;
            Light nextLight = Light.NONE;
            if (light == Light.WHITE) {
                nextLight = Light.NONE;
            } else if (light == Light.NONE) {
                nextLight = Light.WHITE;
            } else if (light == Light.RED_LEFT) {
                nextLight = Light.RED_RIGHT;
            } else if (light == Light.RED_RIGHT) {
                nextLight = Light.RED_LEFT;
            }
            light = nextLight;
        }
        timeToNextLight--;
    }

    public Light getLight() {
        return light;
    }

    public Image getImage() {
        return switch (light) {
            case NONE -> Image.RAILROAD_CROSSING_NO_LIGHT;
            case WHITE -> Image.RAILROAD_CROSSING_WHITE;
            case RED_LEFT -> Image.RAILROAD_CROSSING_RED_LEFT;
            case RED_RIGHT -> Image.RAILROAD_CROSSING_RED_RIGHT;
            default -> Image.RAILROAD_CROSSING_NO_LIGHT;
        };
    }

}
