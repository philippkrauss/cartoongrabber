package cartoongrabber.backend;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.FileSystemPersistenceService;
import cartoongrabber.tools.RenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * creates a HTML document containing links to the images, instead of storing the images themselves.
 */
public class HtmlCollection implements CartoonCollectionService {

    private final Logger log = LoggerFactory.getLogger(HtmlCollection.class);
    private final FileSystemPersistenceService persistenceService;
    private final RenderService renderService;


    public HtmlCollection(FileSystemPersistenceService persistenceService, RenderService renderService) {
        this.persistenceService = persistenceService;
        this.renderService = renderService;
    }

    @Override
    public void collect(List<CartoonStrip> cartoons) {
        if (cartoons == null || cartoons.isEmpty()) {
            throw new RuntimeException("empty cartoon list, cannot render!");
        }
        log.debug("collecting {} cartoons", cartoons.size());
        LocalDate date = cartoons.get(0).getDate();
        String directoryName = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String text = renderService.render(cartoons);
        persistenceService.storeTextFile("cartoons-" + directoryName + ".html", text);
        persistenceService.storeTextFile("cartoons.html", text);
    }
}
