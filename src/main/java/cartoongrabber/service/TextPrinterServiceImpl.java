package cartoongrabber.service;

import org.springframework.stereotype.Service;

/**
 * This service just prints everything to console that is given to it. For testing purposes
 * Created by Philipp Krauß on 14.07.2017.
 */
@Service("textPrinter")
public class TextPrinterServiceImpl implements TextPrinterService {
    @Override
    public void print(Object text) {
        System.out.println(text);
    }
}
