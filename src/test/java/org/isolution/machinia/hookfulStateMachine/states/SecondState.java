package org.isolution.machinia.hookfulStateMachine.states;

import org.isolution.machinia.OnEvent;
import org.isolution.machinia.State;
import org.isolution.machinia.StateConfiguration;
import org.isolution.machinia.hookfulStateMachine.events.TimerTickEvent;
import org.isolution.machinia.hookfulStateMachine.events.TransitionToFirstEvent;

/**
 * User: alexwibowo
 */
@StateConfiguration({
        @OnEvent(event = TransitionToFirstEvent.class, newState = FirstState.class),
        @OnEvent(event = TimerTickEvent.class, newState = SecondState.class)
})
public class SecondState implements State {

    private int onEnterCount;

    private int onExitCount;

    public SecondState() {
        onEnterCount = 0;
        onExitCount = 0;
    }

    @Override
    public String getName() {
        return "SecondState";
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
