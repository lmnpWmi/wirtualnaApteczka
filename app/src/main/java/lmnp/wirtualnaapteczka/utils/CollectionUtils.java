package lmnp.wirtualnaapteczka.utils;

import java.util.Collection;

public class CollectionUtils {
    public static boolean isEmpty(Collection<?> collection) {
        boolean result = collection == null || collection.size() == 0;

        return result;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
