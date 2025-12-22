package cz.koscak.jan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Images {

    public static BufferedImage imgSemafor;

    private final Map<Image, BufferedImage> images = new HashMap<>();

    public Images() {
        /*loadImage(Image.TRAFFIC_LIGHTS_RED);
        loadImage(Image.TRAFFIC_LIGHTS_RED_YELLOW);
        loadImage(Image.TRAFFIC_LIGHTS_YELLOW);
        loadImage(Image.TRAFFIC_LIGHTS_GREEN);
        loadImage(Image.CAR_1);
        loadImage(Image.ROAD_VERTICAL);*/
        for (Image Image : Image.values()) {
            loadImage(Image);
        }
    }

    public void loadImage(Image image) {
        final String imageName = image.getName();
        BufferedImage img = null;
        try {
            img = ImageIO.read(
                    Objects.requireNonNull(Images.class.getResourceAsStream("/" + imageName + ".png"))
            );
            System.out.println("Image loaded: " + imageName + " " + img.getWidth() + "x" + img.getHeight());
        } catch (Exception e) {
            System.err.println("Error loading image: " + imageName);
        }
        images.put(image, img);
    }

    BufferedImage get(Image image) {
        return images.get(image);
    }

    public static BufferedImage rotate(BufferedImage bImg, Double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = bImg.getWidth();
        int h = bImg.getHeight();
        int newW = (int) Math.floor(w*cos + h*sin),
                newH = (int) Math.floor(h*cos + w*sin);
        BufferedImage rotated = new BufferedImage(newW, newH, bImg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((newW-w)/2, (newH-h)/2);
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawRenderedImage(bImg, null);
        graphic.dispose();
        return rotated;
    }

    public static BufferedImage resize(BufferedImage image, int newW, int newH) {
        java.awt.Image temporaryImage = image.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(temporaryImage, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }
}
