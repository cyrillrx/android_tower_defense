package org.es.utils;

/**
 * This class should be implemented by every time aware classes.
 *
 * @author Cyril Leroux
 *         Created on 24/02/14.
 */
public interface TimeAware {

    /** @param elapsedTime The elapsed time since the game was paused. */
    void onResume(long elapsedTime);
}
