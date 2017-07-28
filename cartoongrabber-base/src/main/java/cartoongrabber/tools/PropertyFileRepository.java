package cartoongrabber.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyFileRepository implements SourcePropertyRepositoryService {

    private final Logger log = LoggerFactory.getLogger(PropertyFileRepository.class);

    private final Map<String, Properties> properties = new HashMap<>();

    @Value("${configFile:config/grabber.properties}")
    private String propertyFile;

    public void loadRepoFromFile() {
        File file = new File(propertyFile);
        if (!file.exists() || !file.canRead()) {
            throw new RuntimeException("Cannot access file [" + file.getAbsolutePath() + "]");
        }
        log.debug("loading property file repository from file [{}]", file.getAbsolutePath());
        try (FileReader reader = new FileReader(file)) {
            loadFromReader(reader);
        } catch (Exception e) {
            throw new RuntimeException("Cannot parse configuration properties from file in [" + file + "]", e);
        }
    }

    public void loadRepoFromResource() {
        log.debug("loading property file repository from resource [{}]", propertyFile);
        try (InputStream stream = this.getClass().getResourceAsStream(propertyFile);
             InputStreamReader reader = new InputStreamReader(stream)) {
            loadFromReader(reader);
        } catch (Exception e) {
            throw new RuntimeException("Cannot parse configuration properties from resource in [" + propertyFile + "]", e);
        }


    }

    private void loadFromReader(Reader reader) {
        SourcePropertyParser parser = new SourcePropertyParser();
        Map<String, Properties> parsedProperties = parser.parse(reader);
        properties.putAll(parsedProperties);
    }

    @Override
    public Properties get(String source) {
        if (!properties.containsKey(source)) {
            throw new RuntimeException("unknown source: " + source);
        }
        return properties.get(source);
    }

    @Override
    public Collection<String> listSources() {
        return properties.keySet();
    }

    public void put(String name, Properties p) {
        properties.put(name, p);
    }
}
