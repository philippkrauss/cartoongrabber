package cartoongrabber.web.controller;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.web.model.CartoonStateMap;
import cartoongrabber.web.service.MockPersistenceService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cartoongrabber.tools.TestTools.createCartoon;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("unchecked")
public class CartoonControllerTest {

    private MockPersistenceService mockPersistenceService;

    private CartoonController cartoonController;

    private Model model;

    @Before
    public void setUp() {
        mockPersistenceService = new MockPersistenceService();
        cartoonController = new CartoonController(mockPersistenceService);
        model = new ExtendedModelMap();
    }

    @Test
    public void testGetCartoons() {
        cartoonController.getCartoons("2017-08-01", model);
        assertEquals(LocalDate.of(2017, 8, 1), model.asMap().get("date"));
        assertTrue((getCartoonsFromModel(model)).isEmpty());
    }

    @Test
    public void testGetCartoonsNoDateNoData() {
        cartoonController.getCartoons(null, model);
        assertEquals(LocalDate.now(), model.asMap().get("date"));
    }

    @Test
    public void testGetCartoonsNoDate() {
        mockPersistenceService.dates.add(LocalDate.of(2017, 8, 1));
        cartoonController.getCartoons(null, model);
        assertEquals(LocalDate.of(2017, 8, 1), model.asMap().get("date"));
    }

    @Test
    public void testGetCartoonsForDate() {
        mockPersistenceService.addCartoonForDate(createCartoon(0), LocalDate.of(2017, 8, 1));
        cartoonController.getCartoons("2017-08-01", model);
        assertEquals(1, (getCartoonsFromModel(model)).size());
        assertEquals(createCartoon(0), getCartoonsFromModel(model).get(0));
    }

    @Test
    public void testIsNotOld() throws Exception {
        CartoonStrip cartoon1 = new CartoonStrip("dilbert",
                new URL("http", "www.dilbert.com", "/"),
                new URL("http", "www.dilbert.com", "/myImage"),
                LocalDate.of(2017, 8, 2));

        CartoonStrip cartoon2 = new CartoonStrip("dilbert",
                new URL("http", "www.dilbert.com", "/"),
                new URL("http", "www.dilbert.com", "/myImage2"),
                LocalDate.of(2017, 8, 3));

        mockPersistenceService.addCartoonForDate(cartoon1, cartoon1.getDate());
        mockPersistenceService.addCartoonForDate(cartoon2, cartoon2.getDate());
        mockPersistenceService.dates.addAll(Arrays.asList(cartoon1.getDate(), cartoon2.getDate()));
        cartoonController.getCartoons("2017-08-03", model);
        assertNotNull(model.asMap().get("isOldMap"));
        assertFalse(((CartoonStateMap) model.asMap().get("isOldMap")).isOld(cartoon1));
        assertFalse(((CartoonStateMap) model.asMap().get("isOldMap")).isOld(cartoon2));
    }

    @Test
    public void testIsOld() throws Exception {
        CartoonStrip cartoon1 = new CartoonStrip("dilbert",
                new URL("http", "www.dilbert.com", "/"),
                new URL("http", "www.dilbert.com", "/myImage"),
                LocalDate.of(2017, 8, 2));

        CartoonStrip cartoon2 = new CartoonStrip("dilbert",
                new URL("http", "www.dilbert.com", "/"),
                new URL("http", "www.dilbert.com", "/myImage"),
                LocalDate.of(2017, 8, 3));

        mockPersistenceService.addCartoonForDate(cartoon1, cartoon1.getDate());
        mockPersistenceService.addCartoonForDate(cartoon2, cartoon2.getDate());
        mockPersistenceService.dates.addAll(Arrays.asList(cartoon1.getDate(), cartoon2.getDate()));
        cartoonController.getCartoons("2017-08-03", model);
        assertFalse(((CartoonStateMap) model.asMap().get("isOldMap")).isOld(cartoon1));
        assertTrue(((CartoonStateMap) model.asMap().get("isOldMap")).isOld(cartoon2));
    }

    private List<CartoonStrip> getCartoonsFromModel(Model model) {
        return (List<CartoonStrip>) model.asMap().get("cartoons");
    }

    @Test
    public void testGetDates() {
        mockPersistenceService.dates.add(LocalDate.of(2017, 8, 1));
        cartoonController.getDates(model);
        assertEquals(Collections.singletonList(LocalDate.of(2017, 8, 1)), model.asMap().get("dates"));
    }
}
