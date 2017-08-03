package cartoongrabber.web.controller;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.web.service.MockPersistenceService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

import static cartoongrabber.tools.TestTools.createCartoon;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CartoonControllerTest {

    MockPersistenceService mockPersistenceService;

    CartoonController cartoonController;

    @Before
    public void setUp() {
        mockPersistenceService = new MockPersistenceService();
        cartoonController = new CartoonController(mockPersistenceService);
    }

    @Test
    public void testGetCartoons() {
        Model model = new ExtendedModelMap();
        cartoonController.getCartoons("2017-08-01", model);
        assertEquals(LocalDate.of(2017, 8, 1), model.asMap().get("date"));
        assertTrue((getCartoonsFromModel(model)).isEmpty());
    }

    @Test
    public void testGetCartoonsNoDateNoData() {
        Model model = new ExtendedModelMap();
        cartoonController.getCartoons(null, model);
        assertEquals(LocalDate.now(), model.asMap().get("date"));
    }

    @Test
    public void testGetCartoonsNoDate() {
        Model model = new ExtendedModelMap();
        mockPersistenceService.dates.add(LocalDate.of(2017, 8, 1));
        cartoonController.getCartoons(null, model);
        assertEquals(LocalDate.of(2017, 8, 1), model.asMap().get("date"));
    }

    @Test
    public void testGetCartoonsForDate() {
        Model model = new ExtendedModelMap();
        mockPersistenceService.addCartoonForDate(createCartoon(0), LocalDate.of(2017, 8, 1));
        cartoonController.getCartoons("2017-08-01", model);
        assertEquals(1, (getCartoonsFromModel(model)).size());
        assertEquals(createCartoon(0), getCartoonsFromModel(model).get(0));
    }

    @SuppressWarnings("unchecked")
    private List<CartoonStrip> getCartoonsFromModel(Model model) {
        return (List<CartoonStrip>) model.asMap().get("cartoons");
    }
}
