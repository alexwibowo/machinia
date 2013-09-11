package org.isolution.machinia.trafficLight.events;

import org.isolution.machinia.Event;

/**
 * User: alexwibowo
 */
public class ClearEvent implements Event {

    @Override
    public String getName() {
        return "All clear!";
    }
}
