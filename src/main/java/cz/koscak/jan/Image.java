package cz.koscak.jan;

public enum Image {

    TRAFFIC_LIGHTS_RED("traffic-lights-red"),
    TRAFFIC_LIGHTS_YELLOW("traffic-lights-red-yellow"),
    TRAFFIC_LIGHTS_RED_YELLOW("traffic-lights-yellow"),
    TRAFFIC_LIGHTS_GREEN("traffic-lights-green"),
    CAR_1("car-1"),
    ROAD_VERTICAL("road-vertical"),
    ROAD_HORIZONTAL("road-horizontal"),
    ROAD_CROSSROAD("road-crossroad");

    private final String name;

    Image(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
