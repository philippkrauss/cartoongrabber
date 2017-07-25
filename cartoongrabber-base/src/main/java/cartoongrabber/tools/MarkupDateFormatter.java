package cartoongrabber.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Formats a String that contains occurrences of time patterns. Time patterns have to be marked using '&lt;' and '&gt;'
 * brackets. Uses a {@link DateTimeFormatter} to format the subsections of the string and a {@link DateService} to get
 * the date.
 * Created by Philipp Krauß on 17.07.2017.
 */
@Component
public class MarkupDateFormatter {

    private final Logger log = LoggerFactory.getLogger(MarkupDateFormatter.class);

    public String format(String base, LocalDate date) {
        String ret = base;
        String formatted = formatFirst(base, date);
        while (!ret.equals(formatted)) {
            ret = formatted;
            formatted = formatFirst(ret, date);
        }
        return ret;
    }

    private String formatFirst(String base, LocalDate date) {
        String pattern = "<([^>]*)>";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(base);
        String result = base;
        if (m.find()) {
            String match = m.group(1);
            log.debug("extracted group [{}]", m.group());
            String formatted = date.format(DateTimeFormatter.ofPattern(match).withLocale(Locale.US));
            String replaced = m.replaceFirst(formatted);
            result = replaced;
            log.debug("replaced string: [{}]", replaced);
        }
        return result;
    }
}
