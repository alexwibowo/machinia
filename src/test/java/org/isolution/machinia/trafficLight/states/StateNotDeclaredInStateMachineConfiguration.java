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
}
