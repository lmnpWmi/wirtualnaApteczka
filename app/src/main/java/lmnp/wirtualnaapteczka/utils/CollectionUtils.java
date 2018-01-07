package lmnp.wirtualnaapteczka.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Utils for Java Collections.
 *
 * @author Sebastian Nowak
 * createdAt 02.01.2018
 */
public class CollectionUtils {
    private CollectionUtils() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        boolean result = collection == null || collection.size() == 0;
        return result;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        boolean result = map == null || map.size() == 0;
        return result;
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}
