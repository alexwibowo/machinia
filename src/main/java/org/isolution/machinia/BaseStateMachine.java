package org.isolution.machinia;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * User: alexwibowo
 */
public abstract class BaseStateMachine {

    protected State currentState;

    private final List<Class<? extends State>> allKnownStates;

    public BaseStateMachine(Class<? extends State> initialState) {
        this.currentState = createState(initialState);

        StateMachine stateMachineAnnotation = this.getClass().getAnnotation(StateMachine.class);
        allKnownStates = asList(stateMachineAnnotation.states());

        validateInitialState();
        validateStateTransitionConfiguration();
    }

    /**
     * @return current {@link State} of the state machine
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Handle the given event.
     * Unrecognized event will just get ignored.
     *
     * @param event instance of {@link Event}
     */
    public void handle(Event event) {
        Class<? extends State> currentStateClass = currentState.getClass();
        StateConfiguration stateConfiguration = currentStateClass.getAnnotation(StateConfiguration.class);

        for (OnEvent onEvent : stateConfiguration.value()) {
            if (event.getClass().isAssignableFrom(onEvent.event())) {
                currentState = createState(onEvent.newState());
                break;
            }
        }
    }

    private void validateInitialState() throws StateMachineConfigurationException {
        if (!allKnownStates.contains(currentState.getClass())) {
            throw new InvalidInitialStateException();
        }
    }

    private void validateStateTransitionConfiguration() {
        for (Class<? extends State> state : allKnownStates) {
            StateConfiguration stateConfiguration = state.getAnnotation(StateConfiguration.class);
            for (OnEvent onEvent : stateConfiguration.value()) {
                if (!allKnownStates.contains(onEvent.newState())) {
                    throw new InvalidTransitionConfigurationException();
                }
            }
        }
    }

    private State createState(Class<? extends State> stateClass) {
        try {
            return stateClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to construct state " + stateClass);
        }
    }
}
