package cartoongrabber.cli;

import cartoongrabber.service.GrabberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * This class is responsible for CLI parsing and invocation of the grabber service.
 * Created by Philipp Krauß on 14.07.2017.
 */
@Service
public class CartoonGrabberCli {

    private final GrabberService grabberService;

    @Autowired
    public CartoonGrabberCli(GrabberService grabberService) {
        this.grabberService = grabberService;
    }

    public void run(String[] args) {
        String argument = "dilbert";
        if (args.length == 1) {
            argument = args[0];
        }
        grabberService.grab(argument);

//        BufferedImage img = null;
//        try {
//            URL url = new URL("http", "assets.amuniversal.com", "/367703903d890135d5f8005056a9545d");
//            img = ImageIO.read(url);
//            ImageIO.write(img, "png", new File("C:/src/myfile.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(img.toString());
    }

}
