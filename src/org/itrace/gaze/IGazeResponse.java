package org.itrace.gaze;

import org.itrace.Gaze;
import org.itrace.gaze.handlers.IGazeHandler;

/**
 * Defines a response to a gaze event. Returned by objects implementing
 * IGazeHandler.
 */
public interface IGazeResponse {

    /**
     * Returns the name of the artifact under the gaze.
     */
    public String getName();

    /**
     * Returns the type of artifact.
     */
    public String getGazeType();

    /**
     * Returns the gaze object from which the response originated.
     */
    public Gaze getGaze();

    /**
     * Returns the gaze handler.
     */
    public IGazeHandler getGazeHandler();
}
