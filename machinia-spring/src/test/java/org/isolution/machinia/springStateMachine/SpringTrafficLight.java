package org.isolution.machinia.springStateMachine;

import org.isolution.machinia.StateMachine;
import org.isolution.machinia.SpringBaseStateMachine;
import org.isolution.machinia.springStateMachine.states.Green;
import org.isolution.machinia.springStateMachine.states.Red;
import org.isolution.machinia.springStateMachine.states.Yellow;

/**
 * User: alexwibowo
 */
@StateMachine(
        states = { Green.class, Red.class, Yellow.class }
)
public class SpringTrafficLight extends SpringBaseStateMachine {

    public SpringTrafficLight(TrafficLightContext stateMachineContext) {
        super(Red.class, stateMachineContext);
    }
}
