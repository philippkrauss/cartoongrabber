package cartoongrabber.service;

import cartoongrabber.model.CartoonStrip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
public class FileCollection implements CartoonCollectionService {

    private final Logger log = LoggerFactory.getLogger(FileCollection.class);

    private final Path base;

    public FileCollection(String basePath) {
        this.base = Paths.get(basePath);
    }

    @Override
    public void collect(CartoonStrip cartoon) {
        log.debug("collecting cartoon [{}] in path [{}]", cartoon, base);
    }
}
