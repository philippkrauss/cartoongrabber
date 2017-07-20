package cartoongrabber.aggregator;

import cartoongrabber.model.CartoonStrip;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartoonAggregator {

    public List<CartoonStrip> aggregate(List<CartoonStrip> cartoons) {
        return cartoons;
    }
}
