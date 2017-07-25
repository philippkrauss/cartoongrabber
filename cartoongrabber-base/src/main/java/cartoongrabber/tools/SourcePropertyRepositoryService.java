package cartoongrabber.tools;

import java.util.Collection;
import java.util.Properties;

public interface SourcePropertyRepositoryService {

    Properties get(String source);

    Collection<String> listSources();
}
