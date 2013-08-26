package org.isolution.machinia.hookfulStateMachine;

import org.isolution.machinia.BaseStateMachine;
import org.isolution.machinia.StateMachine;
import org.isolution.machinia.hookfulStateMachine.states.FirstState;
import org.isolution.machinia.hookfulStateMachine.states.SecondState;

/**
 * User: alexwibowo
 */
@StateMachine(
        states = {
                FirstState.class,
                SecondState.class
        }
)
public class StateMachineWithHooks extends BaseStateMachine{

    public StateMachineWithHooks(HookfulStateMachineContext stateMachineContext) {
        super(FirstState.class, stateMachineContext);
    }
}
