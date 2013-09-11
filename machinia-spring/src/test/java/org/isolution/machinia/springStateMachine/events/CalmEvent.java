package org.isolution.machinia.springStateMachine.events;

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
