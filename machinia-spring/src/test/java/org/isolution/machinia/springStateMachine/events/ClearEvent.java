package org.isolution.machinia.springStateMachine.events;

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
