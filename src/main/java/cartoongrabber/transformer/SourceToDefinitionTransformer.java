package cartoongrabber.transformer;

import cartoongrabber.model.SourceDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
public class SourceToDefinitionTransformer {

    private final Logger log = LoggerFactory.getLogger(SourceToDefinitionTransformer.class);

    public SourceDefinition transform(String source) {
        log.debug("transforming source [{}] to source definition", source);
        return new SourceDefinition(source, "http://dilbert.com/strip/<yyyy>-<MM>-<dd>", "<meta property=\"og:image\" content=\"([^\"]*)\"\\/>");
    }
}
