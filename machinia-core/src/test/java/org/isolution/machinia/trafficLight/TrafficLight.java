package org.isolution.machinia.trafficLight;

import org.isolution.machinia.*;
import org.isolution.machinia.trafficLight.states.Green;
import org.isolution.machinia.trafficLight.states.Red;
import org.isolution.machinia.trafficLight.states.Yellow;

/**
 * User: alexwibowo
 */
@StateMachine(
        states = { Green.class, Red.class, Yellow.class }
)
public class TrafficLight extends BaseStateMachine {

    public TrafficLight() {
        super(Red.class, new TrafficLightContext());
    }
}
