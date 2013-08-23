package org.isolution.machinia.trafficLight.events;

import org.isolution.machinia.Event;

/**
 * User: alexwibowo
 */
public class CalmEvent implements Event{

    @Override
    public String getName() {
        return "Calm Down!";
    }
}
