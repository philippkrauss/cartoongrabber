package cartoongrabber.backend;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.FileSystemPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public void collect(CartoonStrip cartoon) {
        LocalDate date = cartoon.getDate();
        String directoryName = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        persistenceService.createDirectory(directoryName);
        //store image in subdirectory
        persistenceService.storeImage(directoryName, cartoon.getName(), cartoon.getImage());
        //create text file with "downloaded from" content
        log.debug("collecting cartoon [{}] ", cartoon);
    }

}
