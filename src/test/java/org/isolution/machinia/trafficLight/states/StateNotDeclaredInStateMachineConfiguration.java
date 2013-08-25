package org.isolution.machinia.trafficLight.states;

import org.isolution.machinia.State;

/**
 * User: alexwibowo
 */
public class StateNotDeclaredInStateMachineConfiguration implements State {

    @Override
    public String getName() {
        return "Weird state";
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
