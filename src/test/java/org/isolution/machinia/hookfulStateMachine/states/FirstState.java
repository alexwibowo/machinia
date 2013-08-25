package org.isolution.machinia.hookfulStateMachine.states;

import org.isolution.machinia.OnEvent;
import org.isolution.machinia.State;
import org.isolution.machinia.StateConfiguration;
import org.isolution.machinia.hookfulStateMachine.events.TimerTickEvent;
import org.isolution.machinia.hookfulStateMachine.events.TransitionToSecondEvent;

/**
 * User: alexwibowo
 */
@StateConfiguration({
        @OnEvent(event = TransitionToSecondEvent.class, newState = SecondState.class),
        @OnEvent(event = TimerTickEvent.class, newState = FirstState.class)
})
public class FirstState implements State {

    private int onEnterCount;

    private int onExitCount;

    public FirstState() {
        onEnterCount = 0;
        onExitCount = 0;
    }

    @Override
    public String getName() {
        return "FirstState";
    }

    @Override
    public void onEnter() {
        onEnterCount++;
    }

    @Override
    public void onExit() {
        onExitCount++;
    }

    public int getOnEnterCount() {
        return onEnterCount;
    }

    public int getOnExitCount() {
        return onExitCount;
    }
}
