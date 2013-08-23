package org.isolution.machinia;

/**
 * User: alexwibowo
 */
public @interface OnEvent {
    /**
     * Event that is being received
     */
    Class<? extends Event> event();

    /**
     * New state after the event is processed
     */
    Class<? extends State> newState();
}
