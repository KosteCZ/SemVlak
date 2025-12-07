package cz.koscak.jan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Main extends JPanel implements ActionListener, KeyListener {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 900;
    private int rocketSpeed = 0;
    private boolean gamePaused = true;  // Game starts in a paused state
    private boolean gameWon = false;  // Track if the rocket lands on the Moon
    private boolean gameOver = false;  // Track if the game is over (exploded or landed on Moon)
    private Timer timer;
    private Images images;
    private java.util.List<Car> listOfCars;

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
    }

    private void loadCars() {
        listOfCars = new ArrayList<>();
        listOfCars.add(new Car(100, 300, 0, 1));
        listOfCars.add(new Car(125, 300, 0, -1));
        listOfCars.add(new Car(100, 600, 0, 1));
        listOfCars.add(new Car(125, 600, 0, -1));
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
        g.setColor(Color.GRAY);
        g.fillOval(moonX, moonY, 100, 100);  // Simple moon at the top

        /*g.setColor(Color.GRAY);
        g.drawLine(100, 200, 100, HEIGHT - 200);
        g.drawLine(150, 200, 150, HEIGHT - 200);*/

        g.drawImage(getImage(Image.TRAFFIC_LIGHTS_RED), 60, 200, this);
        g.drawImage(getImage(Image.TRAFFIC_LIGHTS_RED_YELLOW), 160, 200, this);

        g.drawImage(getImage(Image.TRAFFIC_LIGHTS_YELLOW), 60, 650, this);
        g.drawImage(getImage(Image.TRAFFIC_LIGHTS_GREEN), 160, 650, this);

        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 200, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 250, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 300, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 350, this);
        g.drawImage(getImage(Image.ROAD_CROSSROAD), 100, 400, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 450, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 500, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 550, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 600, this);
        g.drawImage(getImage(Image.ROAD_VERTICAL), 100, 650, this);

        g.drawImage(getImage(Image.ROAD_HORIZONTAL), 50, 400, this);
        g.drawImage(getImage(Image.ROAD_HORIZONTAL), 150, 400, this);

        //g.drawImage(getImage(Image.CAR_1), 100, 650, this);
        //g.drawImage(getImage(Image.CAR_1), 125, 650, this);

        for (Car car : listOfCars) {
            g.drawImage(getImage(Image.CAR_1), car.getX(), car.getY(), this);
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

    private void drawRocket(Graphics g, int x, int y) {
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gamePaused && !gameWon && !gameOver) {  // Only update game logic when not paused or won/over
            //ToDo
            for(Car car : listOfCars) {
                car.move();
            }
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
