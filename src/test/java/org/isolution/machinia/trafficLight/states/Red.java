package org.isolution.machinia.trafficLight.states;

import org.isolution.machinia.OnEvent;
import org.isolution.machinia.State;
import org.isolution.machinia.StateConfiguration;
import org.isolution.machinia.trafficLight.events.CalmEvent;
import org.isolution.machinia.trafficLight.events.ClearEvent;

/**
 * User: alexwibowo
 */
@StateConfiguration({
    @OnEvent(event =  CalmEvent.class, newState = Yellow.class),
    @OnEvent(event =  ClearEvent.class, newState = Green.class)
})
public class Red implements State {

    @Override
    public String getName() {
        return "Red";
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
