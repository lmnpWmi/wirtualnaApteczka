package lmnp.wirtualnaapteczka.utils;

import android.util.Log;

/**
 * Utils for logging in App. Disabled in the production.
 *
 * @author Sebastian Nowak
 * @createdAt 10.01.2018
 */
public final class Logger {
    private boolean inDeveloperMode;
    private Class<?> clazz;

    public Logger(Class<?> clazz) {
        this.inDeveloperMode = AppConstants.IN_DEVELOPER_MODE;
        this.clazz = clazz;
    }

    public void logInfo(String message) {
        if (inDeveloperMode) {
            Log.i(clazz.getSimpleName(), message);
        }
    }

    public void logWarn(String message) {
        if (inDeveloperMode) {
            Log.w(clazz.getSimpleName(), message);
        }
    }

    public void logError(String message) {
        if (inDeveloperMode) {
            Log.e(clazz.getSimpleName(), message);
        }
    }
}
