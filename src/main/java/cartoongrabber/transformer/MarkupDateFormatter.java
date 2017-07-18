package cartoongrabber.transformer;

import cartoongrabber.service.DateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
@Component
public class MarkupDateFormatter {

    private Logger log = LoggerFactory.getLogger(MarkupDateFormatter.class);

    private final DateService dateService;

    @Autowired
    public MarkupDateFormatter(DateService dateService) {
        this.dateService = dateService;
    }


    public String format(String base) {
        String ret = base;
        String formatted = formatFirst(base);
        while (!ret.equals(formatted)) {
            ret = formatted;
            formatted = formatFirst(ret);
        }
        return ret;
    }

    private String formatFirst(String base) {
        String pattern = "<([^>]*)>";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(base);
        String result = base;
        if (m.find()) {
            String match = m.group(1);
            log.debug("extracted group [{}]", m.group());
            String formatted = dateService.getDate().format(DateTimeFormatter.ofPattern(match));
            String replaced = m.replaceFirst(formatted);
            result = replaced;
            log.debug("replaced string: [{}]", replaced);
        }
        return result;
    }
}
