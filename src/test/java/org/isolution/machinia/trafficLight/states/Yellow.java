package org.isolution.machinia.trafficLight.states;

import org.isolution.machinia.OnEvent;
import org.isolution.machinia.State;
import org.isolution.machinia.StateConfiguration;
import org.isolution.machinia.trafficLight.events.ClearEvent;
import org.isolution.machinia.trafficLight.events.PanicEvent;

/**
 * User: alexwibowo
 */
@StateConfiguration({
        @OnEvent(event =  PanicEvent.class, newState = Red.class),
        @OnEvent(event =  ClearEvent.class, newState = Green.class)
})
public class Yellow implements State {
    @Override
    public String getName() {
        return "Yellow";
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
