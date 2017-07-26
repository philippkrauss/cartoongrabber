package cartoongrabber.web;

import cartoongrabber.backend.InMemoryCartoonCollector;
import cartoongrabber.service.GrabberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartoonController {

    @Autowired
    private GrabberService grabberService;

    @Autowired
    private InMemoryCartoonCollector cartoonCollector;

    @RequestMapping("/cartoons")
    public String greeting(Model model) {
        model.addAttribute("name", "value");
        grabberService.grab("all");
        model.addAttribute("cartoons", cartoonCollector.getCollectedCartoons());
        return "cartoons";
    }
}
