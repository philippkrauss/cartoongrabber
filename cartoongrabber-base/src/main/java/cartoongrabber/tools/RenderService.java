package cartoongrabber.tools;

import cartoongrabber.model.CartoonStrip;

import java.util.List;

public interface RenderService {

    String render(List<CartoonStrip> cartoons);
}
