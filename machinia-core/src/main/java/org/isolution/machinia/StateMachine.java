package org.isolution.machinia;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: alexwibowo
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StateMachine {

    /**
     * All states known to the state machine
     */
    Class<? extends State>[] states();
}
