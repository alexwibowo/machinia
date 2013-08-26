package org.isolution.machinia;

/**
 * User: alexwibowo
 */
public interface State {

    /**
     * @return name of the current state
     */
    String getName();

    /**
     * Callback to be executed on entering this state
     */
    void onEnter(StateMachineContext stateMachineContext);

    /**
     * Callback to be executed on exiting this state
     */
    void onExit(StateMachineContext stateMachineContext);
}
