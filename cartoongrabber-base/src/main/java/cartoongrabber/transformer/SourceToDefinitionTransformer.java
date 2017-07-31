package cartoongrabber.transformer;

import cartoongrabber.model.SourceDefinition;
import cartoongrabber.tools.SourcePropertyRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Transformer component that transforms a cartoon name into its {@link SourceDefinition}
 * Created by Philipp Krauß on 17.07.2017.
 */
@Component
public class SourceToDefinitionTransformer {

    public static final String NAME_PROPERTY = "name";
    public static final String BASE_URL_PROPERTY = "baseUrl";
    public static final String IMAGE_PATTERN_PROPERTY = "imagePattern";
    public static final String IMAGE_URL_PROPERTY = "imageUrl";
    private final Logger log = LoggerFactory.getLogger(SourceToDefinitionTransformer.class);

    private final SourcePropertyRepositoryService repository;

    @Autowired
    public SourceToDefinitionTransformer(SourcePropertyRepositoryService repository) {
        this.repository = repository;
    }


    public SourceDefinition transform(String source) {
        if (source == null) {
            throw new RuntimeException("Cannot transform null source to source definition");
        }
        Properties properties = repository.get(source);
        String name = properties.getProperty(NAME_PROPERTY);
        String baseUrl = properties.getProperty(BASE_URL_PROPERTY);
        String imgPattern = properties.getProperty(IMAGE_PATTERN_PROPERTY);
        String imgUrl = properties.getProperty(IMAGE_URL_PROPERTY);
        if (imgPattern != null) {
            log.debug("transforming source [{}] to pattern source definition", source);
            return SourceDefinition.patternSource(name, baseUrl, imgPattern);
        } else {
            log.debug("transforming source [{}] to direct source definition", source);
            return SourceDefinition.directSource(name, baseUrl, imgUrl);
        }
    }
}
