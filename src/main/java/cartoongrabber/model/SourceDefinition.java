package cartoongrabber.model;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
public class SourceDefinition {
    public final String name;

    public SourceDefinition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SourceDefinition{" +
                "name='" + name + '\'' +
                '}';
    }
}
