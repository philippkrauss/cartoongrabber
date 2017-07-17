package cartoongrabber.model;

import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Domain class for modelling a comic strip.
 * Created by Philipp Krauß on 17.07.2017.
 */
public class CartoonStrip {

    private final String name;
    private final URL source;
    private final BufferedImage image;

    public CartoonStrip(String name, URL source, BufferedImage image) {
        this.name = name;
        this.source = source;
        this.image = image;
    }

    @Override
    public String toString() {
        return "CartoonStrip{" +
                "name='" + name + '\'' +
                ", source=" + source +
                ", image=" + image +
                '}';
    }
}
