package cartoongrabber.web;

import cartoongrabber.backend.InMemoryCartoonCollector;
import cartoongrabber.model.CartoonStrip;
import cartoongrabber.service.GrabberService;
import cartoongrabber.tools.RenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CartoonController {

    private final Logger log = LoggerFactory.getLogger(CartoonController.class);

    @Autowired
    private GrabberService grabberService;

    @Autowired
    private InMemoryCartoonCollector cartoonCollector;

    @Autowired
    private RenderService renderService;

    @RequestMapping("/cartoons")
    @ResponseBody
    public String loadCartoons(Model model) {
        log.debug("loading cartoons");
        model.addAttribute("name", "value");
        grabberService.grab("all");
        List<CartoonStrip> cartoons = cartoonCollector.getCollectedCartoons();
        cartoonCollector.clear();
        log.debug("done, [{}] cartoons loaded", cartoons.size());
        return renderService.render(cartoons);
    }
}
