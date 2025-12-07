package cz.koscak.jan;

import javax.imageio.ImageIO;
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
}
