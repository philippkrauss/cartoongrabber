package cartoongrabber.tools;

import cartoongrabber.model.CartoonStrip;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockRenderService implements RenderService {

    public String renderedText = "rendered text";

    @Override
    public String render(List<CartoonStrip> cartoons) {
        return renderedText;
    }
}
