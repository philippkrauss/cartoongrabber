package cartoongrabber.tools;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

public class TestTools {

    public static void imageEquals(BufferedImage expected, BufferedImage actual) {
        String expectedString = expected.toString().replaceAll("BufferedImage@[^:]+", "");
        String actualString = actual.toString().replaceAll("BufferedImage@[^:]+", "");
        assertEquals(expectedString, actualString);
    }
}
