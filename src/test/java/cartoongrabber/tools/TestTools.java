package cartoongrabber.tools;

import cartoongrabber.model.CartoonStrip;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TestTools {

    public static void imageEquals(BufferedImage expected, BufferedImage actual) {
        String expectedString = expected.toString().replaceAll("BufferedImage@[^:]+", "");
        String actualString = actual.toString().replaceAll("BufferedImage@[^:]+", "");
        assertEquals(expectedString, actualString);
    }

    public static CartoonStrip createCartoon() {
        try {
            return new CartoonStrip("dilbert",
                    new URL("http", "www.dilbert.com", "/"),
                    new URL("http", "www.dilbert.com", "/myImage"),
                    new BufferedImage(10, 10, BufferedImage.TYPE_BYTE_INDEXED),
                    LocalDate.of(2000, 1, 17));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
