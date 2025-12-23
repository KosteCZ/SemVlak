package cz.koscak.jan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Main extends JPanel implements ActionListener, KeyListener {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 900;
    private boolean gamePaused = true;  // Game starts in a paused state
    private boolean gameWon = false;  // Track if the rocket lands on the Moon
    private boolean gameOver = false;  // Track if the game is over (exploded or landed on Moon)
    private boolean debugMode = false;  // Just for debugging purposes
    private final Timer timer;
    private final Images images;
    private java.util.List<Human> listOfHumans;
    private java.util.List<Car> listOfCars;
    private java.util.List<Train> listOfTrains;
    private java.util.List<TrafficLight> listOfTrafficLights;
    private java.util.List<TrafficStop> listOfTrafficStops;
    private java.util.List<RailroadCrossing> listOfRailroadCrossings;
    private java.util.List<TrainStation> listOfTrainStations;
    private java.util.List<Smoke> listOfSmokes;

    // Moon position
    private int moonX = WIDTH / 2 - 50;
    private int moonY = 50;

    // Restart button
    private JButton restartButton;

    public Main() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //setBackground(Color.BLACK);
        setBackground(new Color(69, 177, 69));
        addKeyListener(this);
        setFocusable(true);

        // Set up the timer for periodic updates
        timer = new Timer(50, this); // updates every 15 milliseconds
        timer.start();

        // Initialize the restart button (but keep it invisible until the game ends)
        restartButton = new JButton("Restart");
        restartButton.setBounds(WIDTH / 2 - 50, 350, 100, 50);
        restartButton.setVisible(false);  // Initially hidden
        restartButton.addActionListener(e -> restartGame());
        add(restartButton);

        images = new Images();
        loadHumans();
        loadCars();
        loadTrains();
        loadTrafficLights();
        loadRailroadCrossings();
        loadTrainStations();
        listOfSmokes = new ArrayList<>();
    }

    private void loadHumans() {
        listOfHumans = new ArrayList<>();

        listOfHumans.add(new Human(275, 500, 1, 0, Image.HUMAN_WALKING));
        listOfHumans.add(new Human(300, 488, -1, 0, Image.HUMAN_WALKING));
    }

    private void loadCars() {
        listOfCars = new ArrayList<>();

        listOfCars.add(new Car(100, 300, 0, 1, Image.CAR_1));
        listOfCars.add(new Car(125, 300, 0, -1, Image.CAR_1));
        listOfCars.add(new Car(100, 600, 0, 1, Image.CAR_1));
        listOfCars.add(new Car(125, 600, 0, -1, Image.CAR_1));
        listOfCars.add(new Car(100, 550, 0, 1, Image.CAR_1));

        listOfCars.add(new Car(200, 425, 1, 0, Image.CAR_2));
        listOfCars.add(new Car(200, 400, -1, 0, Image.CAR_2));
        listOfCars.add(new Car(400, 425,  1, 0, Image.CAR_2));
        listOfCars.add(new Car(400, 400,  -1, 0, Image.CAR_2));
        listOfCars.add(new Car(600, 425,  1, 0, Image.CAR_2));

        listOfCars.add(new Car(700, 300, 0, 1, Image.CAR_3));
        listOfCars.add(new Car(725, 300, 0, -1, Image.CAR_3));
        listOfCars.add(new Car(700, 600, 0, 1, Image.CAR_3));
        listOfCars.add(new Car(725, 600, 0, -1, Image.CAR_3));
        listOfCars.add(new Car(700, 550, 0, 1, Image.CAR_3));
    }

    private void loadTrains() {
        listOfTrains = new ArrayList<>();

        final Train locomotive1 = new Train(200, 600, -2, 0, null);
        listOfTrains.add(locomotive1);
        final Train cargoTrain1 = new Train(250, 600, -2, 0, locomotive1);
        listOfTrains.add(cargoTrain1);
        locomotive1.setCargoTrain(cargoTrain1);

        final Train locomotive2 = new Train(400, 625, 2, 0, null);
        listOfTrains.add(locomotive2);
        final Train cargoTrain2 = new Train(350, 625, 2, 0, locomotive2);
        listOfTrains.add(cargoTrain2);
        locomotive2.setCargoTrain(cargoTrain2);
    }

    private void loadTrafficLights() {
        listOfTrafficLights = new ArrayList<>();
        listOfTrafficStops = new ArrayList<>();

        // TOP LEFT
        final TrafficLight trafficLight1 = new TrafficLight(60, 352, TrafficLight.Light.GREEN);
        listOfTrafficLights.add(trafficLight1);
        listOfTrafficStops.add(new TrafficStop(100, 350, 0, 1, trafficLight1));
        // TOP RIGHT
        final TrafficLight trafficLight2 = new TrafficLight(160, 352, TrafficLight.Light.RED);
        listOfTrafficLights.add(trafficLight2);
        listOfTrafficStops.add(new TrafficStop(150, 400, -1, 0, trafficLight2));
        // BOTTOM LEFT
        final TrafficLight trafficLight3 = new TrafficLight(60, 452, TrafficLight.Light.RED);
        listOfTrafficLights.add(trafficLight3);
        listOfTrafficStops.add(new TrafficStop(50, 425, 1, 0, trafficLight3));
        // BOTTOM RIGHT
        final TrafficLight trafficLight4 = new TrafficLight(160, 452, TrafficLight.Light.GREEN);
        listOfTrafficLights.add(trafficLight4);
        listOfTrafficStops.add(new TrafficStop(125, 450,  0,-1, trafficLight4));

        // TOP LEFT
        final TrafficLight trafficLight5 = new TrafficLight(660, 352, TrafficLight.Light.GREEN);
        listOfTrafficLights.add(trafficLight5);
        listOfTrafficStops.add(new TrafficStop(700, 350, 0, 1, trafficLight5));
        // TOP RIGHT
        final TrafficLight trafficLight6 = new TrafficLight(760, 352, TrafficLight.Light.RED);
        listOfTrafficLights.add(trafficLight6);
        listOfTrafficStops.add(new TrafficStop(750, 400, -1, 0, trafficLight6));
        // BOTTOM LEFT
        final TrafficLight trafficLight7 = new TrafficLight(660, 452, TrafficLight.Light.RED);
        listOfTrafficLights.add(trafficLight7);
        listOfTrafficStops.add(new TrafficStop(650, 425, 1, 0, trafficLight7));
        // BOTTOM RIGHT
        final TrafficLight trafficLight8 = new TrafficLight(760, 452, TrafficLight.Light.GREEN);
        listOfTrafficLights.add(trafficLight8);
        listOfTrafficStops.add(new TrafficStop(725, 450,  0,-1, trafficLight8));
    }

    private void loadRailroadCrossings() {
        listOfRailroadCrossings = new ArrayList<>();

        final RailroadCrossing railroadCrossing1 = new RailroadCrossing(
                100, 550, 0, 1, RailroadCrossing.State.RED,
                0, 250, 600, 625);
        listOfRailroadCrossings.add(railroadCrossing1);

        final RailroadCrossing railroadCrossing2 = new RailroadCrossing(
                125, 650, 0, -1, RailroadCrossing.State.RED,
                0, 250, 600, 625);
        listOfRailroadCrossings.add(railroadCrossing2);


        final RailroadCrossing railroadCrossing3 = new RailroadCrossing(
                700, 550, 0, 1, RailroadCrossing.State.RED,
                600, 850, 600, 625);
        listOfRailroadCrossings.add(railroadCrossing3);

        final RailroadCrossing railroadCrossing4 = new RailroadCrossing(
                725, 650, 0, -1, RailroadCrossing.State.RED,
                600, 850, 600, 625);
        listOfRailroadCrossings.add(railroadCrossing4);
    }

    private void loadTrainStations() {
        listOfTrainStations = new ArrayList<>();
        listOfTrainStations.add(new TrainStation(250, 600, -1, 0));
        listOfTrainStations.add(new TrainStation(300, 625, 1, 0));
        listOfTrainStations.add(new TrainStation(550, 600, -1, 0));
        listOfTrainStations.add(new TrainStation(600, 625, 1, 0));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Semafory a vlaky 1");
        Main game = new Main();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Moon
        //g.setColor(Color.GRAY);
        //g.fillOval(moonX, moonY, 100, 100);  // Simple moon at the top

        /*g.setColor(Color.GRAY);
        g.drawLine(100, 200, 100, HEIGHT - 200);
        g.drawLine(150, 200, 150, HEIGHT - 200);*/

        /*g.setColor(Color.DARK_GRAY);
        for (int i = 1; i < 12; i++) {
            g.drawLine(50 * i, 0, 50 * i, HEIGHT );
        }
        for (int i = 1; i < 18; i++) {
            //g.drawLine(0,50 * i, WIDTH, 50 * i );
            g.drawLine(0,50 * i, 550, 50 * i );
        }*/


        /*g.drawImage(getImage(Image.TRAFFIC_LIGHTS_RED), 60, 352, this);
        g.drawImage(getImage(Image.TRAFFIC_LIGHTS_RED_YELLOW), 160, 352, this);

        g.drawImage(getImage(Image.TRAFFIC_LIGHTS_YELLOW), 60, 452, this);
        g.drawImage(getImage(Image.TRAFFIC_LIGHTS_GREEN), 160, 452, this);*/

        for (TrafficLight trafficLight : listOfTrafficLights) {
            g.drawImage(getImage(trafficLight.getImage()), trafficLight.getX(), trafficLight.getY(), this);
        }


        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 150, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 200, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 250, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 300, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL_NEAR_CROSS_ROAD_UP), 180.0), 100, 350, this);
        g.drawImage(getImage(Image.ROAD_CROSSROAD), 100, 400, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL_NEAR_CROSS_ROAD_UP), 100, 450, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 500, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 550, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL_RAILS_HORIZONTAL), 100, 600, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 650, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 700, this);

        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 0, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 50, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 150, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 200, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL_PLATFORM), 250, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL_PLATFORM), 300, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 350, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 400, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 450, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 500, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL_PLATFORM), 550, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL_PLATFORM), 600, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 650, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 700, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 750, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 800, 600, this);

        //g.drawImage(getImage(Image.ROAD_HORIZONTAL), 50, 400, this);
        //g.drawImage(getImage(Image.ROAD_HORIZONTAL), 150, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL_NEAR_CROSS_ROAD_UP), 90.0), 50, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL_NEAR_CROSS_ROAD_UP), 270.0), 150, 400, this);

        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0),   0, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0), 200, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0), 250, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0), 300, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0), 350, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0), 400, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0), 450, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0), 500, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0), 550, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0), 600, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL_NEAR_CROSS_ROAD_UP),  90.0), 650, 400, this);
        //g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0), 700, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL_NEAR_CROSS_ROAD_UP), 270.0), 750, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL), 90.0), 800, 400, this);

        g.drawImage(getImage(Image.ROAD_VERTICAL), 700, 150, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 700, 200, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 700, 250, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 700, 300, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL_NEAR_CROSS_ROAD_UP), 180.0), 700, 350, this);
        g.drawImage(getImage(Image.ROAD_CROSSROAD), 700, 400, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL_NEAR_CROSS_ROAD_UP), 700, 450, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 700, 500, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 700, 550, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL_RAILS_HORIZONTAL), 700, 600, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 700, 650, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 700, 700, this);

        //g.drawImage(getImage(Image.CAR_1), 100, 650, this);
        //g.drawImage(getImage(Image.CAR_1), 125, 650, this);

        g.drawImage(getImage(Image.TRAIN_STATION_1), 250, 550, this);
        g.drawImage(getImage(Image.TRAIN_STATION_1), 550, 550, this);

        g.drawImage(getImage(Image.HOUSE_1), 250, 350, this);
        g.drawImage(getImage(Image.OFFICE_1), 300, 350, this);
        g.drawImage(getImage(Image.HOUSE_1), 250, 450, this);
        g.drawImage(getImage(Image.OFFICE_1), 300, 450, this);

        g.drawImage(getImage(Image.HOUSE_1), 450, 350, this);
        g.drawImage(getImage(Image.OFFICE_1), 500, 350, this);
        g.drawImage(getImage(Image.HOUSE_1), 450, 450, this);
        g.drawImage(getImage(Image.OFFICE_1), 500, 450, this);

        g.drawImage(getImage(Image.OFFICE_1), 550, 350, this);
        g.drawImage(getImage(Image.HOUSE_1), 600, 350, this);
        g.drawImage(getImage(Image.OFFICE_1), 550, 450, this);
        g.drawImage(getImage(Image.HOUSE_1), 600, 450, this);

        g.drawImage(getImage(Image.TREES),   0,   0, this);
        g.drawImage(getImage(Image.TREES),   0,  50, this);
        g.drawImage(getImage(Image.TREES),   0, 100, this);
        g.drawImage(getImage(Image.TREES),   0, 150, this);
        g.drawImage(getImage(Image.TREES),   0, 200, this);
        g.drawImage(getImage(Image.TREES_CORNER), 0,   250, this);
        g.drawImage(getImage(Image.TREES),  50,   0, this);
        g.drawImage(getImage(Image.TREES),  50,  50, this);
        g.drawImage(getImage(Image.TREES),  50, 100, this);
        g.drawImage(getImage(Image.TREES_CORNER), 100,   100, this);
        g.drawImage(getImage(Image.TREES), 100,   0, this);
        g.drawImage(getImage(Image.TREES), 100,  50, this);
        g.drawImage(getImage(Image.TREES_CORNER), 50,   150, this);
        g.drawImage(getImage(Image.TREES), 150,   0, this);
        g.drawImage(getImage(Image.TREES), 150,  50, this);
        g.drawImage(getImage(Image.TREES), 200,   0, this);
        g.drawImage(getImage(Image.TREES), 200,   50, this);
        g.drawImage(getImage(Image.TREES_CORNER), 250,   50, this);
        g.drawImage(getImage(Image.TREES), 250,   0, this);
        g.drawImage(getImage(Image.TREES), 300,   0, this);
        g.drawImage(getImage(Image.TREES), 350,   0, this);
        g.drawImage(getImage(Image.TREES_CORNER), 400,   0, this);

        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 250,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 275,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 300,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 325,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 350,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 375,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 400,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 425,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 450,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 475,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 500,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 525,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 550,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 575,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 600,   500, this);
        g.drawImage(getImage(Image.PAVEMENT_HORIZONTAL), 625,   500, this);


        for (Human human : listOfHumans) {
            BufferedImage image = getImage(human.getImage());

            if (human.getImage().equals(Image.HUMAN_WALKING)) {
                image = image.getSubimage((human.getImageStep() % 3) * 25, (human.getImageStep() / 3) * 25, 25, 25);
            }

            if (human.getVX() < 0) image = Images.mirrorHorizontally(image);
            g.drawImage(image, human.getX(), human.getY(), this);
        }

        for (Car car : listOfCars) {
            final BufferedImage image = getImage(car.getImage());
            double angle = 0.0;
            if (car.getVY() > 0) angle = 180.0;
            if (car.getVX() > 0) angle =  90.0;
            if (car.getVX() < 0) angle = 270.0;
            BufferedImage rotatedImage = Images.rotate(image, angle);
            g.drawImage(rotatedImage, car.getX(), car.getY(), this);
        }

        for (Train train : listOfTrains) {
            BufferedImage image = getImage(Image.TRAIN_LOCO_1);
            if (!train.isLocomotive()) {
                image = getImage(Image.TRAIN_CARGO_1);
                if (train.getCargo() == TrainCargo.STONE) {
                    image = getImage(Image.TRAIN_CARGO_STONE);
                }
            }
            if (train.getVX() < 0) {
                g.drawImage(Images.rotate(image, 270.0), train.getX(), train.getY(), this);
            } else {
                g.drawImage(Images.rotate(image, 90.0), train.getX(), train.getY(), this);
            }
        }

        for (Smoke smoke : listOfSmokes) {
            int shift = 4 - (smoke.getTimeToDisappear() / 5);
            g.setColor(smoke.getColor());
            g.drawOval(smoke.getX() - shift, smoke.getY() - shift, 1 + shift * 2, 1 + shift * 2);
        }

        g.drawImage(Images.rotate(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 180.0), 100, 150, this);
        g.drawImage(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 100, 700, this);

        g.drawImage(Images.rotate(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 90.0), 0, 600, this);
        g.drawImage(Images.rotate(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 270.0), 800, 600, this);

        g.drawImage(Images.rotate(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 180.0), 700, 150, this);
        g.drawImage(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 700, 700, this);

        g.drawImage(Images.rotate(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 90.0), 0, 400, this);
        g.drawImage(Images.rotate(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 270.0), 800, 400, this);

        final BufferedImage imageOfBar = getImage(Image.RAILROAD_CROSSING_BAR);


        final RailroadCrossing railroadCrossing1 = listOfRailroadCrossings.get(0);
        final BufferedImage imageOfRailroadCrossing1 = getImage(railroadCrossing1.getImage());
        g.drawImage(Images.rotate(imageOfRailroadCrossing1, 0.0), 50, 550, this);
        final BufferedImage imageOfBarResized1 = Images.resize(imageOfBar,
                imageOfBar.getWidth() - railroadCrossing1.getBarState(), imageOfBar.getHeight());
        g.drawImage(Images.rotate(imageOfBarResized1, 0.0), 50 + (railroadCrossing1.getBarState() / 3), 550, this);

        final RailroadCrossing railroadCrossing2 = listOfRailroadCrossings.get(1);
        final BufferedImage imageOfRailroadCrossing2 = getImage(railroadCrossing2.getImage());
        g.drawImage(Images.rotate(imageOfRailroadCrossing2, 180.0), 150, 650, this);
        final BufferedImage imageOfBarResized2 = Images.resize(imageOfBar,
                imageOfBar.getWidth() - railroadCrossing2.getBarState(), imageOfBar.getHeight());
        g.drawImage(Images.rotate(imageOfBarResized2, 180.0), 100 + railroadCrossing2.getBarState() - railroadCrossing2.getBarState() / 3, 650, this);


        final RailroadCrossing railroadCrossing3 = listOfRailroadCrossings.get(2);
        final BufferedImage imageOfRailroadCrossing3 = getImage(railroadCrossing3.getImage());
        g.drawImage(Images.rotate(imageOfRailroadCrossing3, 0.0), 650, 550, this);
        final BufferedImage imageOfBarResized3 = Images.resize(imageOfBar,
                imageOfBar.getWidth() - railroadCrossing3.getBarState(), imageOfBar.getHeight());
        g.drawImage(Images.rotate(imageOfBarResized3, 0.0), 650 + (railroadCrossing3.getBarState() / 3), 550, this);

        final RailroadCrossing railroadCrossing4 = listOfRailroadCrossings.get(3);
        final BufferedImage imageOfRailroadCrossing4 = getImage(railroadCrossing4.getImage());
        g.drawImage(Images.rotate(imageOfRailroadCrossing4, 180.0), 750, 650, this);
        final BufferedImage imageOfBarResized4 = Images.resize(imageOfBar,
                imageOfBar.getWidth() - railroadCrossing4.getBarState(), imageOfBar.getHeight());
        g.drawImage(Images.rotate(imageOfBarResized4, 180.0), 700 + railroadCrossing4.getBarState() - railroadCrossing4.getBarState() / 3, 650, this);


        if (debugMode) {

            g.setColor(Color.DARK_GRAY);
            for (int x = 0; x <= 1200; x = x + 25) {
                g.drawLine(x, 0, x, 900);
            }
            for (int y = 0; y <= 900; y = y + 25) {
                g.drawLine(0, y, 1200, y);
            }

            g.setColor(Color.BLACK);
            for (int x = 0; x <= 1200; x = x + 50) {
                g.drawLine(x, 0, x, 900);
            }
            for (int y = 0; y <= 900; y = y + 50) {
                g.drawLine(0, y, 1200, y);
            }

            g.setColor(Color.BLACK);
            for (int x = 0; x <= 1200; x = x + 100) {
                g.drawLine(x-1, 0, x-1, 900);
                g.drawLine(x, 0, x, 900);

                g.drawString(String.valueOf(x), x + 2, 13);
                if (x != 0) g.drawString(String.valueOf(x), x + 2, 895);
            }
            for (int y = 0; y <= 900; y = y + 100) {
                g.drawLine(0, y-1, 1200, y-1);
                g.drawLine(0, y, 1200, y);

                g.drawString(String.valueOf(y), 2, y + 13);
                if (y != 0) g.drawString(String.valueOf(y), 1177, y + 13);
            }

        }


        // Draw rocket (more realistic rocket design)
        //drawRocket(g, ROCKET_X, rocketY);

        // Draw fuel level
        //g.setColor(Color.WHITE);
        //g.drawString("Fuel: " + fuel, 10, 20);

        // Draw background (stars)
        /*for (int i = 0; i < 100; i++) {
            g.setColor(Color.WHITE);
            int x = (int)(Math.random() * getWidth());
            int y = (int)(Math.random() * getHeight());
            g.fillOval(x, y, 2, 2);
        }*/

        // Show pause message
        if (gamePaused) {
            g.setColor(Color.WHITE);
            g.drawString("Press Enter to Start", WIDTH / 2 - 50, 250);
        }

        // Show win message when landed on Moon
        if (gameWon) {
            g.setColor(Color.YELLOW);
            g.drawString("You Landed on the Moon!", WIDTH / 2 - 80, 300);
            restartButton.setVisible(true);  // Show restart button
        }

        // Show game over message when exploded
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("You Exploded! Game Over.", WIDTH / 2 - 80, 300);
            restartButton.setVisible(true);  // Show restart button
        }
    }

    private BufferedImage getImage(Image image) {
        return images.get(image);
    }

    /*private void drawRocket(Graphics g, int x, int y) {
        // Rocket body (cylinder)
        g.setColor(Color.RED);
        g.fillRect(x + 10, y, 30, 80);  // Main body

        // Rocket fins (triangle shapes)
        g.setColor(Color.GRAY);
        int[] xPoints = {x, x + 50, x + 25};
        int[] yPoints = {y + 80, y + 80, y + 110};
        g.fillPolygon(xPoints, yPoints, 3);  // Fins at the bottom

        // Rocket nose cone (triangle)
        g.setColor(Color.WHITE);
        g.fillPolygon(new int[] {x + 10, x + 40, x + 25}, new int[] {y, y, y - 20}, 3);  // Pointed nose

        // Rocket exhaust (fire)
        g.setColor(Color.ORANGE);
        g.fillOval(x + 15, y + 80, 20, 20);  // Exhaust flame
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gamePaused && !gameWon && !gameOver) {  // Only update game logic when not paused or won/over
            //ToDo
            for (RailroadCrossing railroadCrossing : listOfRailroadCrossings) {
                railroadCrossing.time();
                railroadCrossing.checkState(listOfTrains);
            }

            for (Human human : listOfHumans) {
                human.move(listOfTrafficStops, listOfRailroadCrossings, listOfHumans);
            }

            for (Car car : listOfCars) {
                car.move(listOfTrafficStops, listOfRailroadCrossings, listOfCars);
            }
            for (TrafficLight trafficLight : listOfTrafficLights) {
                trafficLight.time();
            }
            for (Train train : listOfTrains) {
                train.move(listOfTrainStations);
                if (train.isLocomotive()) {
                    if (train.getVX() < 0) {
                        listOfSmokes.add(new Smoke(17 + train.getX(), 12 + train.getY(), 20, Color.LIGHT_GRAY));
                    } else {
                        listOfSmokes.add(new Smoke(28 + train.getX(), 12 + train.getY(), 20, Color.LIGHT_GRAY));
                    }
                }
            }
            for (Smoke smoke : listOfSmokes) {
                smoke.time();
            }
            listOfSmokes.removeIf(smoke -> smoke.getTimeToDisappear() <= 0);
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER /*&& gamePaused*/) {
            //gamePaused = false;  // Unpause the game when Enter is pressed
            gamePaused = !gamePaused;  // Unpause the game when Enter is pressed and vice versa
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE /*&& fuel > 0*/ && !gamePaused && !gameWon && !gameOver) {
            //rocketSpeed = 10; // Boost the rocket upwards when space is pressed
            //fuel += 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_D /*&& gamePaused*/) {
            //gamePaused = false;  // Unpause the game when Enter is pressed
            debugMode = !debugMode;  // Unpause the game when Enter is pressed and vice versa
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
    // Restart the game

    private void restartGame() {
        /*rocketY = HEIGHT - 100;  // Reset rocket position
        rocketSpeed = 0;  // Reset speed
        fuel = FUEL_AT_START;  // Reset fuel
        gamePaused = true;  // Pause the game
        gameWon = false;  // Reset win state
        gameOver = false;  // Reset game over state
        restartButton.setVisible(false);  // Hide the restart button*/
        repaint();
    }
}
