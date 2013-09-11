package org.isolution.machinia.hookfulStateMachine.events;

import org.isolution.machinia.Event;

/**
 * User: alexwibowo
 */
public class TimerTickEvent implements Event {
    @Override
    public String getName() {
        return "TimerTickEvent";
    }
}
