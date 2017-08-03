package cartoongrabber.web.service;

import cartoongrabber.model.CartoonStrip;

import java.time.LocalDate;
import java.util.*;

public class MockPersistenceService implements CartoonPersistenceService {

    public Collection<LocalDate> dates = new ArrayList<>();
    public Map<LocalDate, List<CartoonStrip>> cartoonsForDates = new HashMap<>();

    @Override
    public Collection<LocalDate> getDates() {
        return dates;
    }

    @Override
    public List<CartoonStrip> getCartoonsForDate(LocalDate date) {
        return cartoonsForDates.computeIfAbsent(date, k -> new ArrayList<>());
    }

    public void addCartoonForDate(CartoonStrip cartoon, LocalDate date) {
        List<CartoonStrip> cartoons = cartoonsForDates.computeIfAbsent(date, k -> new ArrayList<>());
        cartoons.add(cartoon);
    }
}
