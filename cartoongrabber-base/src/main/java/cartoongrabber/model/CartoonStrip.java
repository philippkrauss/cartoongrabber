package cartoongrabber.model;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartoonStrip that = (CartoonStrip) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (sourceUrl != null ? !sourceUrl.equals(that.sourceUrl) : that.sourceUrl != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (sourceUrl != null ? sourceUrl.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
