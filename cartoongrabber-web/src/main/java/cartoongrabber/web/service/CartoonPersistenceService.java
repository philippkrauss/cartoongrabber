package cartoongrabber.web.service;

import cartoongrabber.model.CartoonStrip;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface CartoonPersistenceService {

    Collection<LocalDate> getDates();

    List<CartoonStrip> getCartoonsForDate(LocalDate date);

}
