package cartoongrabber.tools;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Parses a properties file and splits it into a map of smaller Properties. The first part of the property name
 * determines the name of the smaller Properties.
 * Example:
 * {a.prop1=val1, a.prop2=val2} are merged into {"a" -> (prop1=val1, prop2=val2)}
 * {a.prop1=val1, b.prop2=val2} are merged into {"a" -> (prop1=val1), "b" -> (prop2=val2)}
 */
public class SourcePropertyParser {

    public Map<String, Properties> parse(Reader reader) {
        Map<String, Properties> ret = new HashMap<>();
        Properties p = new Properties();
        try {
            p.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String property : p.stringPropertyNames()) {
            String baseName = property.replaceFirst("\\..*", "");
            Properties propertiesForBase = getPropertiesForBase(ret, baseName);
            String propertyName = property.replaceFirst("[^.]*\\.", "");
            ret.put(baseName, propertiesForBase);
            propertiesForBase.setProperty(propertyName, p.getProperty(property));
        }
        return ret;
    }

    private Properties getPropertiesForBase(Map<String, Properties> propertiesMap, String baseName) {
        Properties ret = propertiesMap.get(baseName);
        if (ret == null) {
            ret = new Properties();
        }
        return ret;
    }
}
