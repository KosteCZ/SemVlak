package cz.koscak.jan;

public enum Image {

    TRAFFIC_LIGHTS_RED("traffic-lights-red"),
    TRAFFIC_LIGHTS_YELLOW("traffic-lights-yellow"),
    TRAFFIC_LIGHTS_RED_YELLOW("traffic-lights-red-yellow"),
    TRAFFIC_LIGHTS_GREEN("traffic-lights-green"),
    CAR_1("car-1"),
    CAR_2("car-2"),
    CAR_3("car-3"),
    TRAIN_LOCO_1("train-loco-1"),
    TRAIN_CARGO_1("train-cargo-1"),
    ROAD_VERTICAL("road-vertical"),
    ROAD_HORIZONTAL("road-horizontal"),
    ROAD_VERTICAL_NEAR_CROSS_ROAD_UP("road-vertical-near-cross-road-up"),
    ROAD_CROSSROAD("road-crossroad"),
    RAILS_HORIZONTAL("rails-horizontal"),
    ROAD_VERTICAL_RAILS_HORIZONTAL("road-vertical-rails-horizontal"),
    RAILROAD_CROSSING("railroad-crossing"),
    RAILROAD_CROSSING_BAR("railroad-crossing-bar-2"),
    TUNNEL_VERTICAL("tunnel-vertical"),
    TUNNEL_VERTICAL_UP_ENTRY("tunnel-vertical-up-entry"),
    TUNNEL_VERTICAL_UP_ENTRY_2("tunnel-vertical-up-entry-2");

    private final String name;

    Image(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
