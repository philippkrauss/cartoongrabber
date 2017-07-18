package cartoongrabber.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
@Service
public class DateServiceImpl implements DateService{

    @Override
    public LocalDate getDate() {
        return LocalDate.now();
    }
}
