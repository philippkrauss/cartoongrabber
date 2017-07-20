package cartoongrabber.tools;

import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyFileRepository implements SourcePropertyRepositoryService {

    private final Map<String, Properties> properties = new HashMap<>();

    public PropertyFileRepository() {
    }

    public PropertyFileRepository(String propertyFile) {
        loadRepoFromFile(propertyFile);
    }

    private void loadRepoFromFile(String propertyFile) {
        File file = new File(propertyFile);
        if (!file.exists() || !file.canRead()) {
            throw new RuntimeException("Cannot access file [" + file + "]");
        }
        SourcePropertyParser parser = new SourcePropertyParser();
        try (FileReader reader = new FileReader(file)) {
            Map<String, Properties> parsedProperties = parser.parse(reader);
            properties.putAll(parsedProperties);
        } catch (Exception e) {
            throw new RuntimeException("Cannot parse configuration properties in [" + file + "]");
        }
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
