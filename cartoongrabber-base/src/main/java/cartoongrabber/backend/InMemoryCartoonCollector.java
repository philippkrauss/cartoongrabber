package cartoongrabber.backend;

import cartoongrabber.model.CartoonStrip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryCartoonCollector implements CartoonCollectionService {

    private final Logger log = LoggerFactory.getLogger(InMemoryCartoonCollector.class);

    private final List<CartoonStrip> collectedCartoons = new ArrayList<>();

    @Override
    public void collect(List<CartoonStrip> cartoons) {
        collectedCartoons.addAll(cartoons);
        log.debug("collecting {} cartoons. Total size is {}", cartoons.size(), collectedCartoons.size());
    }

    public List<CartoonStrip> getCollectedCartoons() {
        List<CartoonStrip> ret = new ArrayList<>(collectedCartoons);
        collectedCartoons.clear();
        return ret;
    }

}
