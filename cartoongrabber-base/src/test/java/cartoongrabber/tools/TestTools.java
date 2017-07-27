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

    public static CartoonStrip createCartoon(String modifier) {
        try {
            return new CartoonStrip("dilbert" + modifier,
                    new URL("http", "www.dilbert.com", "/" + modifier),
                    new URL("http", "www.dilbert.com", "/myImage" + modifier),
                    new BufferedImage(10, 10, BufferedImage.TYPE_BYTE_INDEXED),
                    LocalDate.of(2000, 1, (modifier.hashCode() % 30) + 1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static CartoonStrip createCartoon() {
         return createCartoon("");
    }
}
