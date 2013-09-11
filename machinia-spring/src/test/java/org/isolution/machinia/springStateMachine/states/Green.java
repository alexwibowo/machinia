package org.isolution.machinia.springStateMachine.states;

import org.isolution.machinia.OnEvent;
import org.isolution.machinia.State;
import org.isolution.machinia.StateConfiguration;
import org.isolution.machinia.StateMachineContext;
import org.isolution.machinia.springStateMachine.events.PanicEvent;
import org.isolution.machinia.springStateMachine.events.WarnEvent;
import org.isolution.machinia.springStateMachine.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: alexwibowo
 */
@StateConfiguration({
        @OnEvent(event =  WarnEvent.class, newState = Yellow.class),
        @OnEvent(event =  PanicEvent.class, newState = Red.class)
})
public class Green implements State{

    @Autowired
    private NotificationService notificationService;

    @Override
    public String getName() {
        return "Green";
    }

    @Override
    public void onEnter(StateMachineContext stateMachineContext) {
        notificationService.notify("Entering " + this.getClass().getName());
    }

    @Override
    public void onExit(StateMachineContext stateMachineContext) {
        notificationService.notify("Exiting " + this.getClass().getName());
    }
}
