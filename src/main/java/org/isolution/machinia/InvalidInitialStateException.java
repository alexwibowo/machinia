package org.isolution.machinia;

/**
 * Indicates that the state machine was constructed with an unknown {@link State}.
 * All states known to the state machine must be declared at the
 * {@link StateMachine} annotation of the class
 *
 * User: alexwibowo
 */
public class InvalidInitialStateException extends StateMachineConfigurationException{
}
