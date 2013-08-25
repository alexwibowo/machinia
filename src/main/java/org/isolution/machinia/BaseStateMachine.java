package org.isolution.machinia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * User: alexwibowo
 */
public abstract class BaseStateMachine {

    protected State currentState;

    /**
     * List of all State known to the state machine
     */
    private final List<Class<? extends State>> allKnownStates;

    /**
     * Map of all States
     */
    private final Map<Class<? extends State>, ? extends State> allStates;

    public BaseStateMachine(Class<? extends State> initialState) {
        StateMachine stateMachineAnnotation = this.getClass().getAnnotation(StateMachine.class);
        allKnownStates = asList(stateMachineAnnotation.states());
        allStates = createAllStates();

        validateInitialState(initialState);
        validateStateTransitionConfiguration();

        transitionToNewState(initialState);
    }

    private Map<Class<? extends State>, ? extends State> createAllStates() {
        Map<Class<? extends State>, State> allStates = new HashMap<Class<? extends State>, State>();
        for (Class<? extends State> state : allKnownStates) {
            allStates.put(state, createState(state));
        }
        return allStates;
    }

    /**
     * @return current {@link State} of the state machine
     */
    public State getCurrentState() {
        return currentState;
    }


    private void setCurrentState(State currentState) {
        this.currentState = currentState;
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
            if (stateWillChange(onEvent, event)) {
                currentState.onExit();
                transitionToNewState(onEvent.newState());
                break;
            }
        }
    }

    private boolean stateWillChange(OnEvent onEvent, Event event) {
        Class<? extends State> currentStateClass = currentState.getClass();

        boolean sameEvent = event.getClass().isAssignableFrom(onEvent.event());
        boolean isDifferentState = !onEvent.newState().isAssignableFrom(currentStateClass);

        return sameEvent && isDifferentState;
    }


    private void validateInitialState(Class<? extends State> initialState) throws StateMachineConfigurationException {
        if (!allKnownStates.contains(initialState)) {
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

    private void transitionToNewState(Class<? extends State> stateClass) {
        State newState  = getState(stateClass);
        newState.onEnter();
        setCurrentState(newState);
    }

    private State getState(Class<? extends State> stateClass) {
        return allStates.get(stateClass);
    }

    private State createState(Class<? extends State> stateClass) {
        try {
            return stateClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to construct state " + stateClass, e);
        }
    }
}
