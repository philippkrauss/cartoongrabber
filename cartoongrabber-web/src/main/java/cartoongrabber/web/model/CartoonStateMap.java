package cartoongrabber.web.model;

import cartoongrabber.model.CartoonStrip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Map that compares the imageUrl of a cartoon with an older version of the same cartoon.
 * If the cartoons share the same imageUrl, it is considered "old".
 */
public class CartoonStateMap {

    private Map<CartoonStrip, Boolean> isOldMap = new HashMap<>();

    public CartoonStateMap() {
    }

    public CartoonStateMap(List<CartoonStrip> cartoons, List<CartoonStrip> previousCartoons) {
        Map<String, CartoonStrip> cartoonsByName = previousCartoons.stream()
                .collect(Collectors.toMap(CartoonStrip::getName, Function.identity()));
        for (CartoonStrip cartoon : cartoons) {
            CartoonStrip oldCartoon = cartoonsByName.get(cartoon.getName());
            if (oldCartoon != null) {
                isOldMap.put(cartoon, cartoon.getImageUrl().equals(oldCartoon.getImageUrl()));
            }
        }
    }

    public boolean isOld(CartoonStrip cartoon) {
        return isOldMap.computeIfAbsent(cartoon, k -> Boolean.FALSE);
    }
}
