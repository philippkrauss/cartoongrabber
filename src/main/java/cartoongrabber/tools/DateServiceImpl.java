package cartoongrabber.tools;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Implementation of DateService that provides the current date
 * Created by Philipp Krauß on 17.07.2017.
 */
@Service
public class DateServiceImpl implements DateService {

    @Override
    public LocalDate getDate() {
        return LocalDate.now();
    }
}
