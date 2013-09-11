package org.isolution.machinia.springStateMachine.events;

import org.isolution.machinia.Event;

/**
 * User: alexwibowo
 */
public class PanicEvent implements Event {

    @Override
    public String getName() {
        return "Panic!!";
    }
}
