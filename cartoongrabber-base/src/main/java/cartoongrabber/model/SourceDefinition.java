package cartoongrabber.model;

/**
 * Shared model class that represents the definition of a cartoon source.
 * Created by Philipp Krauß on 17.07.2017.
 */
public class SourceDefinition {
    private final String name;
    private final String baseUrl;
    private final String imagePattern;
    private final String imageUrl;
    private final boolean isDirect;
    private final boolean isPattern;

    private SourceDefinition(String name, String baseUrl, String imagePattern, String imageUrl, boolean isDirect, boolean isPattern) {
        this.name = name;
        this.baseUrl = baseUrl;
        this.imagePattern = imagePattern;
        this.imageUrl = imageUrl;
        this.isDirect = isDirect;
        this.isPattern = isPattern;
    }

    public String getName() {
        return name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isDirect() {
        return isDirect;
    }

    public boolean isPattern() {
        return isPattern;
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

    public static SourceDefinition directSource(String name, String baseUrl, String imageUrl) {
        return new SourceDefinition(name, baseUrl, null, imageUrl, true, false);
    }

    public static SourceDefinition patternSource(String name, String baseUrl, String imagePattern) {
        return new SourceDefinition(name, baseUrl, imagePattern, null, false, true);
    }
}
