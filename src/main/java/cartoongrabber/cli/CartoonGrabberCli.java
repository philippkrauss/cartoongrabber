package cartoongrabber.cli;

import cartoongrabber.service.GrabberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String argument = "http://dilbert.com/strip/2017-07-14";
        if (args.length == 1) {
            argument = args[0];
        }
        grabberService.grab(argument);
    }

}
