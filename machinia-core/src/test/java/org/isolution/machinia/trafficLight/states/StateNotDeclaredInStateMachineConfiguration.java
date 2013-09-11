package org.isolution.machinia.trafficLight.states;

import org.isolution.machinia.State;
import org.isolution.machinia.StateMachineContext;

/**
 * User: alexwibowo
 */
public class StateNotDeclaredInStateMachineConfiguration implements State {

    @Override
    public String getName() {
        return "Weird state";
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
