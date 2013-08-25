package org.isolution.machinia.hookfulStateMachine.events;

import org.isolution.machinia.Event;

/**
 * User: alexwibowo
 */
public class TransitionToSecondEvent implements Event {
    @Override
    public String getName() {
        return "TransitionToSecondEvent";
    }
}
