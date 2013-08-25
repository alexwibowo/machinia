package org.isolution.machinia.trafficLight.states;

import org.isolution.machinia.OnEvent;
import org.isolution.machinia.State;
import org.isolution.machinia.StateConfiguration;
import org.isolution.machinia.trafficLight.events.CalmEvent;

/**
 * User: alexwibowo
 */
@StateConfiguration({
        @OnEvent(event =  CalmEvent.class, newState = StateNotDeclaredInStateMachineConfiguration.class),
})
public class StateWithInvalidTransition implements State {
    @Override
    public String getName() {
        return "Invalid state";
    }


    @Override
    public void onEnter() {
        System.out.println("Entering " + this.getClass().getName());
    }

    @Override
    public void onExit() {
        System.out.println("Exiting " + this.getClass().getName());
    }
}
