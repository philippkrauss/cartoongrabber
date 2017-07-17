package cartoongrabber.transformer;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.model.SourceDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
public class DefinitionToCartoonTransformer {

    private final Logger log = LoggerFactory.getLogger(DefinitionToCartoonTransformer.class);

    public CartoonStrip transform(SourceDefinition source) {
        log.debug("transforming source definition [{}] to cartoon strip", source);
        return new CartoonStrip(source.getName(), null, null);
    }
}
