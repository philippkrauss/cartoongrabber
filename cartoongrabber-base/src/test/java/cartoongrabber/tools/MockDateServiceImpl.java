package cartoongrabber.tools;

import cartoongrabber.tools.DateService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
@Component
public class MockDateServiceImpl implements DateService {
    private LocalDate date = null;

    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }

    @Override
    public LocalDate getDate() {
        return date;
    }
}
