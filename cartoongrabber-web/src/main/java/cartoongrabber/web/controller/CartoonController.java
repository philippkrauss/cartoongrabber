package cartoongrabber.web.controller;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.web.model.CartoonStateMap;
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

    private final CartoonPersistenceService persistenceService;

    @Autowired
    public CartoonController(CartoonPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @RequestMapping("/cartoons")
    public String getCartoons(@RequestParam(value = "date", required = false) String date, Model model) {
        SortedSet<LocalDate> availableDates = new TreeSet<>(persistenceService.getDates());
        LocalDate selectedDate = getSelectedDate(availableDates, date);
        List<CartoonStrip> cartoons = persistenceService.getCartoonsForDate(selectedDate);
        CartoonStateMap isOldMap = computeOldMap(availableDates, selectedDate, cartoons);
        cartoons.sort(Comparator.comparing(CartoonStrip::getName));
        model.addAttribute("cartoons", cartoons);
        model.addAttribute("date", selectedDate);
        model.addAttribute("isOldMap", isOldMap);
        return "cartoons";
    }

    private CartoonStateMap computeOldMap(SortedSet<LocalDate> availableDates, LocalDate selectedDate, List<CartoonStrip> cartoons) {
        if (availableDates.headSet(selectedDate).isEmpty()) {
            return new CartoonStateMap();
        }
        LocalDate previousDate = availableDates.headSet(selectedDate).last();
        List<CartoonStrip> previousCartoons = persistenceService.getCartoonsForDate(previousDate);
        return new CartoonStateMap(cartoons, previousCartoons);
    }

    private LocalDate getSelectedDate(SortedSet<LocalDate> availableDates, String date) {
        LocalDate selectedDate;
        if (date != null) {
            selectedDate = LocalDate.parse(date);
        } else {
            selectedDate = availableDates.isEmpty() ? LocalDate.now() : availableDates.last();
        }
        return selectedDate;
    }

    @RequestMapping("/dates")
    public String getList(Model model) {
        Collection<LocalDate> dates = persistenceService.getDates();
        model.addAttribute("dates", dates);
        return "dates";
    }
}
