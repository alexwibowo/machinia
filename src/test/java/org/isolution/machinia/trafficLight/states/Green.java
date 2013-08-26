package org.isolution.machinia.trafficLight.states;

import org.isolution.machinia.OnEvent;
import org.isolution.machinia.State;
import org.isolution.machinia.StateConfiguration;
import org.isolution.machinia.StateMachineContext;
import org.isolution.machinia.trafficLight.events.PanicEvent;
import org.isolution.machinia.trafficLight.events.WarnEvent;

/**
 * User: alexwibowo
 */
@StateConfiguration({
        @OnEvent(event =  WarnEvent.class, newState = Yellow.class),
        @OnEvent(event =  PanicEvent.class, newState = Red.class)
})
public class Green implements State{

    @Override
    public String getName() {
        return "Green";
    }

    @Override
    public void onEnter(StateMachineContext stateMachineContext) {
        System.out.println("Entering " + this.getClass().getName());
    }

    @Override
    public void onExit(StateMachineContext stateMachineContext) {
        System.out.println("Exiting " + this.getClass().getName());
    }
}
