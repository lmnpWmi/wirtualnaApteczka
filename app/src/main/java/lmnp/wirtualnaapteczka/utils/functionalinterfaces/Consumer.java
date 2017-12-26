package lmnp.wirtualnaapteczka.utils.functionalinterfaces;

import android.content.Context;

/**
 * Functional interface that invokes action accepting Context as parameter and without returning anything.
 *
 * @author Sebastian Nowak
 * @createdAt 26.12.2017
 */
public interface Consumer {
    void accept(Context context);
}
