package cartoongrabber.aggregator;

import cartoongrabber.model.CartoonStrip;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertSame;

public class CartoonAggregatorTest {

    @Test
    public void testAggregate() {
        CartoonAggregator cartoonAggregator = new CartoonAggregator();
        List<CartoonStrip> list = new ArrayList<>();
        List<CartoonStrip> aggregated = cartoonAggregator.aggregate(list);
        assertSame(list, aggregated);
    }
}
