package cartoongrabber.web.controller;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.RenderService;
import cartoongrabber.web.service.CartoonPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Controller
public class CartoonController {

    @Autowired
    private RenderService renderService;

    @Autowired
    private CartoonPersistenceService persistenceService;

    @RequestMapping("/cartoons")
    public String getCartoons(@RequestParam(value="date", required=false) String date, Model model) {
        LocalDate selectedDate;
        if (date != null) {
            selectedDate = LocalDate.parse(date);
        } else {
            SortedSet<LocalDate> dates = new TreeSet<>(persistenceService.getDates());
            selectedDate = dates.isEmpty()?LocalDate.now():dates.last();
        }
        List<CartoonStrip> cartoons = persistenceService.getCartoonsForDate(selectedDate);
        cartoons.sort(Comparator.comparing(CartoonStrip::getName));
        model.addAttribute("cartoons", cartoons);
        model.addAttribute("date", selectedDate);
        return "cartoons";
    }

    @RequestMapping("/dates")
    public String getList(Model model) {
        Collection<LocalDate> dates = persistenceService.getDates();
        model.addAttribute("dates", dates);
        return "dates";
    }
}
