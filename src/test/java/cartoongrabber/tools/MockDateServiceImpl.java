package cartoongrabber.tools;

import cartoongrabber.tools.DateService;

import java.time.LocalDate;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
public class MockDateServiceImpl implements DateService {
    private final LocalDate date;

    public MockDateServiceImpl(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }

    @Override
    public LocalDate getDate() {
        return date;
    }
}
