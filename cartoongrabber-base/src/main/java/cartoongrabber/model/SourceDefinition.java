package cartoongrabber.model;

/**
 * Shared model class that represents the definition of a cartoon source.
 * Created by Philipp Krauß on 17.07.2017.
 */
public class SourceDefinition {
    private final String name;
    private final String baseUrl;
    private final String imagePattern;

    public SourceDefinition(String name, String baseUrl, String imagePattern) {
        this.name = name;
        this.baseUrl = baseUrl;
        this.imagePattern = imagePattern;
    }

    public String getName() {
        return name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public String toString() {
        return "SourceDefinition{" +
                "name='" + name + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", imagePattern='" + imagePattern + '\'' +
                '}';
    }

    public String getImagePattern() {
        return imagePattern;
    }
}
