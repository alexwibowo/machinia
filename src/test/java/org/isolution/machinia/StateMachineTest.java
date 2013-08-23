package org.isolution.machinia;

import org.isolution.machinia.trafficLight.TrafficLight;
import org.isolution.machinia.trafficLight.states.Green;
import org.isolution.machinia.trafficLight.states.Red;
import org.isolution.machinia.trafficLight.states.StateWithInvalidTransition;
import org.isolution.machinia.trafficLight.states.Yellow;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

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

    @StateMachine(states = {Green.class, Red.class} )
    public static class StateMachineConstructedWithInvalidState extends BaseStateMachine {
        public StateMachineConstructedWithInvalidState() {
            super(Yellow.class);
        }
    }

    @StateMachine(states = {StateWithInvalidTransition.class})
    public static class StateMachineWithInvalidStateTransition extends BaseStateMachine {
        public StateMachineWithInvalidStateTransition() {
            super(StateWithInvalidTransition.class);
        }
    }
}
