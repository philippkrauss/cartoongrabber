package cartoongrabber.model;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDate;

/**
 * Domain class for modelling a comic strip.
 * Created by Philipp Krauß on 17.07.2017.
 */
public class CartoonStrip {

    private final String name;
    private final URL sourceUrl;
    private final URL imageUrl;
    private final LocalDate date;

    public CartoonStrip(String name, URL sourceUrl, URL imageUrl, LocalDate date) {
        this.name = name;
        this.sourceUrl = sourceUrl;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "CartoonStrip{" +
                "name='" + name + '\'' +
                ", sourceUrl=" + sourceUrl +
                ", imageUrl=" + imageUrl +
                ", date=" + date +
                '}';
    }

    public String getName() {
        return name;
    }

    public URL getImageUrl() {
        return imageUrl;
    }
}
