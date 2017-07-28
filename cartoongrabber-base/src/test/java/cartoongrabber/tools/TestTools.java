package cartoongrabber.tools;

import cartoongrabber.model.CartoonStrip;

import java.net.URL;
import java.time.LocalDate;

public class TestTools {

    public static CartoonStrip createCartoon(int modifier) {
        try {
            return new CartoonStrip("dilbert" + modifier,
                    new URL("http", "www.dilbert.com", "/" + modifier),
                    new URL("http", "www.dilbert.com", "/myImage" + modifier),
                    LocalDate.ofEpochDay(10000 + modifier));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static CartoonStrip createCartoon() {
         return createCartoon(0);
    }
}
