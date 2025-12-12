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
    private final Timer timer;
    private final Images images;
    private java.util.List<Car> listOfCars;
    private java.util.List<Train> listOfTrains;
    private java.util.List<TrafficLight> listOfTrafficLights;
    private java.util.List<TrafficStop> listOfTrafficStops;
    private java.util.List<RailroadCrossing> listOfRailroadCrossings;
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
        loadCars();
        loadTrains();
        loadTrafficLights();
        loadRailroadCrossings();
        listOfSmokes = new ArrayList<>();
    }

    private void loadCars() {
        listOfCars = new ArrayList<>();

        listOfCars.add(new Car(100, 300, 0, 1));
        listOfCars.add(new Car(125, 300, 0, -1));
        listOfCars.add(new Car(100, 600, 0, 1));
        listOfCars.add(new Car(125, 600, 0, -1));
        listOfCars.add(new Car(100, 550, 0, 1));
    }

    private void loadTrains() {
        listOfTrains = new ArrayList<>();

        listOfTrains.add(new Train(200, 600, -2, 0, true));
        listOfTrains.add(new Train(250, 600, -2, 0, false));
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
    }

    private void loadRailroadCrossings() {
        listOfRailroadCrossings = new ArrayList<>();

        final RailroadCrossing railroadCrossing1 = new RailroadCrossing(
                100, 550, 0, 1, RailroadCrossing.State.RED,
                50, 250, 600, 625);
        listOfRailroadCrossings.add(railroadCrossing1);

        final RailroadCrossing railroadCrossing2 = new RailroadCrossing(
                125, 650, 0, -1, RailroadCrossing.State.RED,
                50, 250, 600, 625);
        listOfRailroadCrossings.add(railroadCrossing2);
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
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 250, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 300, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 350, 600, this);
        g.drawImage(getImage(Image.RAILS_HORIZONTAL), 400, 600, this);


        //g.drawImage(getImage(Image.ROAD_HORIZONTAL), 50, 400, this);
        //g.drawImage(getImage(Image.ROAD_HORIZONTAL), 150, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL_NEAR_CROSS_ROAD_UP), 90.0), 50, 400, this);
        g.drawImage(Images.rotate(getImage(Image.ROAD_VERTICAL_NEAR_CROSS_ROAD_UP), 270.0), 150, 400, this);

        //g.drawImage(getImage(Image.CAR_1), 100, 650, this);
        //g.drawImage(getImage(Image.CAR_1), 125, 650, this);

        for (Car car : listOfCars) {
            g.drawImage(getImage(Image.CAR_1), car.getX(), car.getY(), this);
        }

        for (Train train : listOfTrains) {
            BufferedImage image = getImage(Image.TRAIN_CARGO_1);
            if (train.isLocomotive()) {
                image = getImage(Image.TRAIN_LOCO_1);
            }
            g.drawImage(Images.rotate(image, 270.0), train.getX(), train.getY(), this);
        }

        for (Smoke smoke : listOfSmokes) {
            int shift = 4 - (smoke.getTimeToDisappear() / 5);
            g.setColor(smoke.getColor());
            g.drawOval(smoke.getX() - shift, smoke.getY() - shift, 1 + shift * 2, 1 + shift * 2);
        }

        g.drawImage(Images.rotate(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 180.0), 100, 150, this);
        g.drawImage(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 100, 700, this);

        g.drawImage(Images.rotate(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 90.0), 0, 600, this);
        g.drawImage(Images.rotate(getImage(Image.TUNNEL_VERTICAL_UP_ENTRY_2), 270.0), 400, 600, this);

        RailroadCrossing railroadCrossing1 = listOfRailroadCrossings.get(0);
        BufferedImage imageOfRailroadCrossing1 = getImage(railroadCrossing1.getImage());
        g.drawImage(Images.rotate(imageOfRailroadCrossing1, 0.0), 50, 550, this);
        BufferedImage imageOfBar1 = getImage(Image.RAILROAD_CROSSING_BAR);
        BufferedImage imageOfBarResized1 = Images.resize(imageOfBar1,
                imageOfBar1.getWidth() - railroadCrossing1.getBarState(), imageOfBar1.getHeight());
        g.drawImage(Images.rotate(imageOfBarResized1, 0.0), 50 + (railroadCrossing1.getBarState() / 3), 550, this);

        RailroadCrossing railroadCrossing2 = listOfRailroadCrossings.get(1);
        BufferedImage imageOfRailroadCrossing2 = getImage(railroadCrossing2.getImage());
        g.drawImage(Images.rotate(imageOfRailroadCrossing2, 180.0), 150, 650, this);
        BufferedImage imageOfBar2 = getImage(Image.RAILROAD_CROSSING_BAR);
        BufferedImage imageOfBarResized2 = Images.resize(imageOfBar2,
                imageOfBar2.getWidth() - listOfRailroadCrossings.get(1).getBarState(), imageOfBar2.getHeight());
        g.drawImage(Images.rotate(imageOfBarResized2, 180.0), 100 + listOfRailroadCrossings.get(1).getBarState() - listOfRailroadCrossings.get(1).getBarState() / 3, 650, this);

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
            for(RailroadCrossing railroadCrossing : listOfRailroadCrossings) {
                railroadCrossing.time();
                railroadCrossing.checkState(listOfTrains);
            }
            for(Car car : listOfCars) {
                car.move(listOfTrafficStops, listOfRailroadCrossings);
            }
            for (TrafficLight trafficLight : listOfTrafficLights) {
                trafficLight.time();
            }
            for(Train train : listOfTrains) {
                train.move(listOfTrafficStops);
                if (train.isLocomotive()) {
                    listOfSmokes.add(new Smoke(17 + train.getX(), 12 + train.getY(), 20, Color.LIGHT_GRAY));
                }
            }
            for(Smoke smoke : listOfSmokes) {
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
