package cartoongrabber.backend;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.FileSystemPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * File based implementation of {@link CartoonCollectionService].
 * Created by Philipp Krauß on 17.07.2017.
 */
@Component
public class FileCollection implements CartoonCollectionService {

    private final Logger log = LoggerFactory.getLogger(FileCollection.class);
    private final FileSystemPersistenceService persistenceService;

    @Autowired
    public FileCollection(FileSystemPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @Override
    public void collect(List<CartoonStrip> cartoons) {
        if (cartoons == null || cartoons.isEmpty()) {
            throw new RuntimeException("empty cartoon list, cannot render!");
        }
        LocalDate date = cartoons.get(0).getDate();
        String directoryName = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        persistenceService.createDirectory(directoryName);

        for (CartoonStrip cartoon : cartoons) {
            log.debug("Storing cartoon [{}] ", cartoon);
            persistenceService.storeImage(directoryName, cartoon.getName(), cartoon.getImage());
            String text = "Cartoon string \"" + cartoon.getName() + "\" downloaded from" + cartoon.getSourceUrl()
                    + System.getProperty("line.separator")
                    + "image URL: " + cartoon.getImageUrl();
            persistenceService.storeTextFile(directoryName, cartoon.getName() + ".txt", text);
        }
    }

}
