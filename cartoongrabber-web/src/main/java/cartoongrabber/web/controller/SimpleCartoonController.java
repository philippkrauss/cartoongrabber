package cartoongrabber.web.controller;

import cartoongrabber.backend.InMemoryCartoonCollector;
import cartoongrabber.model.CartoonStrip;
import cartoongrabber.service.GrabberService;
import cartoongrabber.tools.RenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SimpleCartoonController {

    private final Logger log = LoggerFactory.getLogger(SimpleCartoonController.class);

    @Autowired
    private GrabberService grabberService;

    @Autowired
    private InMemoryCartoonCollector cartoonCollector;

    @Autowired
    private RenderService renderService;

    @RequestMapping("/simplecartoons")
    @ResponseBody
    public String loadCartoons() {
        log.debug("loading cartoons");
        grabberService.grab("all");
        List<CartoonStrip> cartoons = cartoonCollector.getCollectedCartoons();
        cartoonCollector.clear();
        log.debug("done, [{}] cartoons loaded", cartoons.size());
        return renderService.render(cartoons);
    }
}
