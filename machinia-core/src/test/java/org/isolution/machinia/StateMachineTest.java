package org.isolution.machinia;

import org.isolution.machinia.hookfulStateMachine.HookfulStateMachineContext;
import org.isolution.machinia.hookfulStateMachine.StateMachineWithHooks;
import org.isolution.machinia.hookfulStateMachine.events.TimerTickEvent;
import org.isolution.machinia.hookfulStateMachine.events.TransitionToFirstEvent;
import org.isolution.machinia.hookfulStateMachine.events.TransitionToSecondEvent;
import org.isolution.machinia.hookfulStateMachine.states.FirstState;
import org.isolution.machinia.hookfulStateMachine.states.SecondState;
import org.isolution.machinia.trafficLight.TrafficLight;
import org.isolution.machinia.trafficLight.states.Green;
import org.isolution.machinia.trafficLight.states.Red;
import org.isolution.machinia.trafficLight.states.StateWithInvalidTransition;
import org.isolution.machinia.trafficLight.states.Yellow;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * User: alexwibowo
 */
public class StateMachineTest {

    @Test
    public void should_have_the_correct_initial_state() {
        assertThat(new TrafficLight().getCurrentState(), instanceOf(Red.class));
    }

    @Test(expected = InvalidInitialStateException.class)
    public void should_not_allow_constructing_stateMachine_with_unrecognized_initial_state() {
        new StateMachineConstructedWithInvalidState();
    }

    @Test(expected = InvalidTransitionConfigurationException.class)
    public void should_not_allow_constructing_stateMachine_with_invalid_state_transitions() {
        new StateMachineWithInvalidStateTransition();
    }

    @Test
    public void should_execute_onEnter_hook_of_initialState_upon_constructing_state_machine() {
        // given
        StateMachineWithHooks sm = new StateMachineWithHooks(new HookfulStateMachineContext());

        // when
        State currentState = sm.getCurrentState();

        // then
        assertThat(currentState, instanceOf(FirstState.class));
        FirstState firstState = (FirstState) currentState;
        assertThat(firstState.getOnEnterCount(), equalTo(1));
    }

    @Test
    public void should_not_execute_onEnter_hook_when_unrecognized_event_is_received() {
        // given
        StateMachineWithHooks sm = new StateMachineWithHooks(new HookfulStateMachineContext());
        State currentState = sm.getCurrentState();
        assertThat(currentState, instanceOf(FirstState.class));

        // when
        sm.handle(new TransitionToFirstEvent());

        // then
        State newState = sm.getCurrentState();
        assertThat(newState, instanceOf(FirstState.class));
        FirstState firstState = (FirstState) currentState;
        assertThat("Should not execute onEnter hook when there is no transition",
                firstState.getOnEnterCount(), equalTo(1));
    }

    @Test
    public void should_not_execute_onEnter_hook_when_there_is_no_transition_from_current_state() {
        // given
        StateMachineWithHooks sm = new StateMachineWithHooks(new HookfulStateMachineContext());
        State currentState = sm.getCurrentState();
        assertThat(currentState, instanceOf(FirstState.class));

        // when
        sm.handle(new TimerTickEvent());

        // then
        State newState = sm.getCurrentState();
        assertThat(newState, instanceOf(FirstState.class));
        FirstState firstState = (FirstState) currentState;
        assertThat("Should not execute onEnter hook when there is no transition",
                firstState.getOnEnterCount(), equalTo(1));
    }

    @Test
    public void should_not_execute_onExit_hook_when_there_is_no_transition_from_current_state() {
        // given
        StateMachineWithHooks sm = new StateMachineWithHooks(new HookfulStateMachineContext());
        State currentState = sm.getCurrentState();
        assertThat(currentState, instanceOf(FirstState.class));

        // when
        sm.handle(new TimerTickEvent());

        // then
        State newState = sm.getCurrentState();
        assertThat(newState, instanceOf(FirstState.class));
        FirstState firstState = (FirstState) currentState;
        assertThat("Should not execute onExit hook when there is no transition",
                firstState.getOnExitCount(), equalTo(0));

    }

    @Test
    public void should_not_execute_onExit_hook_when_unrecognized_event_is_received() {
        // given
        StateMachineWithHooks sm = new StateMachineWithHooks(new HookfulStateMachineContext());
        State currentState = sm.getCurrentState();
        assertThat(currentState, instanceOf(FirstState.class));

        // when
        sm.handle(new TransitionToFirstEvent());

        // then
        State newState = sm.getCurrentState();
        assertThat(newState, instanceOf(FirstState.class));
        FirstState firstState = (FirstState) currentState;
        assertThat("Should not execute onExit hook when there is no transition",
                firstState.getOnExitCount(), equalTo(0));

    }


    @Test
    public void should_execute_onExit_hook_upon_exiting_current_state() {
        // given
        StateMachineWithHooks sm = new StateMachineWithHooks(new HookfulStateMachineContext());
        State currentState = sm.getCurrentState();
        assertThat(currentState, instanceOf(FirstState.class));
        FirstState firstState = (FirstState) currentState;
        assertThat("Should not have executed onExit hook when event is not received yet",
                firstState.getOnExitCount(), equalTo(0));

        // when
        sm.handle(new TransitionToSecondEvent());

        // then
        State newState = sm.getCurrentState();
        assertThat(newState, instanceOf(SecondState.class));
        assertThat("Should have executed onExit hook when exiting the current state",
                firstState.getOnExitCount(), equalTo(1));
    }

    @Test
    public void should_execute_onEnter_hook_upon_entering_new_state() {
        // given
        StateMachineWithHooks sm = new StateMachineWithHooks(new HookfulStateMachineContext());

        // when
        sm.handle(new TransitionToSecondEvent());

        // then
        State currentState = sm.getCurrentState();
        assertThat(currentState, instanceOf(SecondState.class));
        SecondState secondState = (SecondState) currentState;
        assertThat("Should have executed onEnter hook when entering new state",
                secondState.getOnEnterCount(), equalTo(1));
    }

    @Test
    public void should_only_have_one_instances_of_each_States() {
        StateMachineWithHooks sm = new StateMachineWithHooks(new HookfulStateMachineContext());
        assertThat(sm.getCurrentState(), instanceOf(FirstState.class));
        FirstState firstState1 = (FirstState) sm.getCurrentState();


        sm.handle(new TransitionToSecondEvent());
        assertThat(sm.getCurrentState(), instanceOf(SecondState.class));
        SecondState secondState1 = (SecondState) sm.getCurrentState();

        sm.handle(new TransitionToFirstEvent());
        assertThat(sm.getCurrentState(), instanceOf(FirstState.class));
        FirstState firstState2 = (FirstState) sm.getCurrentState();

        assertThat("Should only construct states once", firstState1, is(firstState2));

        sm.handle(new TransitionToSecondEvent());
        assertThat(sm.getCurrentState(), instanceOf(SecondState.class));
        SecondState secondState2 = (SecondState) sm.getCurrentState();

        assertThat("Should only construct states once", secondState1, is(secondState2));
    }

    @Test
    public void should_execute_onEnter_hook_on_reentering_an_old_state() {
        StateMachineWithHooks sm = new StateMachineWithHooks(new HookfulStateMachineContext());

        // Initial transition - to second state
        sm.handle(new TransitionToSecondEvent());
        assertThat(sm.getCurrentState(), instanceOf(SecondState.class));

        // Second transition - back to first state
        sm.handle(new TransitionToFirstEvent());
        assertThat(sm.getCurrentState(), instanceOf(FirstState.class));
        FirstState firstState = (FirstState) sm.getCurrentState();
        assertThat("First state should have enter count of 2, from initial and second transition", firstState.getOnEnterCount(), equalTo(2));
        assertThat("First state should have exit count of 1 from initial transition", firstState.getOnExitCount(), equalTo(1));

        // Third transition - back to second state
        sm.handle(new TransitionToSecondEvent());
        assertThat(sm.getCurrentState(), instanceOf(SecondState.class));
        SecondState secondState = (SecondState) sm.getCurrentState();
        assertThat("Second state should have enter count of 2, from second and third transition",  secondState.getOnEnterCount(), equalTo(2));
        assertThat("Second state should have exit count of 1 from second transition", secondState.getOnExitCount(), equalTo(1));
    }


    @Test
    public void should_pass_stateMachineContext_upon_transition_to_new_state() throws Exception {
        HookfulStateMachineContext stateMachineContext = new HookfulStateMachineContext();
        StateMachineWithHooks sm = new StateMachineWithHooks(stateMachineContext);

        Field field = StateMachineWithHooks.class.getSuperclass().getDeclaredField("allStates");
        field.setAccessible(true);
        Map<Class, State> allStates = (Map<Class, State>) field.get(sm);
        State spiedFirstState = spy(allStates.get(FirstState.class));
        allStates.put(FirstState.class, spiedFirstState);

        State spiedSecondState = spy(allStates.get(SecondState.class));
        allStates.put(SecondState.class, spiedSecondState);


        // Initial transition - to second state
        sm.handle(new TransitionToSecondEvent());

        ArgumentCaptor<StateMachineContext> stateMachineContextArgumentCaptor = ArgumentCaptor.forClass(StateMachineContext.class);
        verify(spiedSecondState).onEnter(stateMachineContextArgumentCaptor.capture());

        StateMachineContext actualContext = stateMachineContextArgumentCaptor.getValue();
        assertThat(actualContext, instanceOf(HookfulStateMachineContext.class));
        assertThat((HookfulStateMachineContext) actualContext, is(stateMachineContext));


        // second transition - to first state
        sm.handle(new TransitionToFirstEvent());
        verify(spiedFirstState).onEnter(stateMachineContextArgumentCaptor.capture());
        actualContext = stateMachineContextArgumentCaptor.getValue();
        assertThat(actualContext, instanceOf(HookfulStateMachineContext.class));
        assertThat((HookfulStateMachineContext) actualContext, is(stateMachineContext));
    }


    public static class SampleContext implements StateMachineContext{
    }


    @StateMachine(states = {Green.class, Red.class} )
    public static class StateMachineConstructedWithInvalidState extends BaseStateMachine {
        public StateMachineConstructedWithInvalidState() {
            super(Yellow.class, new SampleContext());
        }
    }


    @StateMachine(states = {StateWithInvalidTransition.class})
    public static class StateMachineWithInvalidStateTransition extends BaseStateMachine {
        public StateMachineWithInvalidStateTransition() {
            super(StateWithInvalidTransition.class, new SampleContext());
        }
    }
}
