package cz.koscak.jan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Images {

    public static BufferedImage imgSemafor;

    private Map<Image, BufferedImage> images = new HashMap<>();

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

    public static BufferedImage rotate(BufferedImage bimg, Double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        int neww = (int) Math.floor(w*cos + h*sin),
                newh = (int) Math.floor(h*cos + w*sin);
        BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((neww-w)/2, (newh-h)/2);
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawRenderedImage(bimg, null);
        graphic.dispose();
        return rotated;
    }
}
