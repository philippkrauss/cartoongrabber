package cartoongrabber.splitter;

import cartoongrabber.tools.SourcePropertyRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SourceSplitter {

    private final SourcePropertyRepositoryService repositoryService;

    private final Logger log = LoggerFactory.getLogger(SourceSplitter.class);

    @Autowired
    public SourceSplitter(SourcePropertyRepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public List<String> split(String input) {
        log.debug("splitting input [{}]", input);
        List<String> ret = new ArrayList<>();
        ret.addAll(Arrays.asList(input.split(",")));
        ret.remove("");
        if (ret.contains("all")) {
            ret.remove("all");
            Collection<String> allSources = repositoryService.listSources();
            ret.addAll(allSources);
            log.debug("expanding 'all' value into [{}]", allSources);
        }
        return ret;
    }
}
