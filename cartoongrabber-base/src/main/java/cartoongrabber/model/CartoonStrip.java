package cartoongrabber.model;

import org.apache.commons.lang.StringEscapeUtils;

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
    private final String error;

    public CartoonStrip(String name, URL sourceUrl, URL imageUrl, LocalDate date) {
        this.name = name;
        this.sourceUrl = sourceUrl;
        this.imageUrl = imageUrl;
        this.date = date;
        this.error = null;
    }

    public CartoonStrip(String name, LocalDate date, Exception e) {
        this(name, date, e.getMessage());
    }

    public CartoonStrip(String name, LocalDate date, String error) {
        this.name = name;
        this.sourceUrl = null;
        this.imageUrl = null;
        this.date = date;
        this.error = error;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getError() {
        return error;
    }

    public String getErrorHtml() {
        return StringEscapeUtils.escapeHtml(error);
    }

    @Override
    public String toString() {
        return "CartoonStrip{" +
                "name='" + name + '\'' +
                ", sourceUrl=" + sourceUrl +
                ", imageUrl=" + imageUrl +
                ", date=" + date +
                ", error=" + error +
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
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return error != null ? error.equals(that.error) : that.error == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (sourceUrl != null ? sourceUrl.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        return result;
    }

    public boolean hasError() {
        return error != null;
    }
}
