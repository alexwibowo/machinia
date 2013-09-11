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
    private Map<Class<? extends State>, ? extends State> allStates;

    /**
     * Context for this state machine
     */
    private StateMachineContext stateMachineContext;

    protected final Class<? extends State> initialState;

    /**
     * Create new state machine and auto initialize it.
     *
     * @see BaseStateMachine#BaseStateMachine(Class, StateMachineContext, boolean)
     */
    public BaseStateMachine(Class<? extends State> initialState, StateMachineContext stateMachineContext) {
        this(initialState, stateMachineContext, true);
    }

    /**
     *
     * @param initialState initial state of the StateMachine
     * @param stateMachineContext state machine context. This context instance will be passed around during state transition
     * @param autoInitialize whether or not the state machine should be initialized. Sometimes you want to delay the state machine
     *                       initialization. In such situation, pass in false
     */
    protected BaseStateMachine(Class<? extends State> initialState, StateMachineContext stateMachineContext, boolean autoInitialize) {
        StateMachine stateMachineAnnotation = this.getClass().getAnnotation(StateMachine.class);
        this.allKnownStates = asList(stateMachineAnnotation.states());
        this.stateMachineContext = stateMachineContext;
        this.initialState = initialState;
        validateInitialState(initialState);
        validateStateTransitionConfiguration();

        if (autoInitialize) {
            doInitialize(initialState);
        }
    }

    final void doInitialize(Class<? extends State> initialState) {
        this.allStates = createAllStates();
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
                currentState.onExit(this.stateMachineContext);
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
        newState.onEnter(this.stateMachineContext);
        setCurrentState(newState);
    }

    private State getState(Class<? extends State> stateClass) {
        return allStates.get(stateClass);
    }

    private State createState(Class<? extends State> stateClass) {
        try {
            State state = stateClass.newInstance();
            initializeState(state);
            return state;
        } catch (Exception e) {
            throw new RuntimeException("Unable to construct state " + stateClass, e);
        }
    }

    protected void initializeState(State state) {
        // default implementation, do nothing
    }

}
