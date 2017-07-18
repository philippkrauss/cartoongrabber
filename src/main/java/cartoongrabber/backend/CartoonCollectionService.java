package cartoongrabber.backend;

import cartoongrabber.model.CartoonStrip;

/**
 * Service interface for cartoongrabber backend. All backends implement this interface.
 * Created by Philipp Krauß on 17.07.2017.
 */
public interface CartoonCollectionService {

    void collect(CartoonStrip cartoon);
}
