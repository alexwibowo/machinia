package org.isolution.machinia;

/**
 * Indicates that the state machine has one or more states which has
 * {@link OnEvent} transition pointing to a state that is unknown to the state machine.
 *
 * User: alexwibowo
 */
public class InvalidTransitionConfigurationException extends StateMachineConfigurationException{
}
