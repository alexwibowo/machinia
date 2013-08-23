package org.isolution.machinia;

import java.lang.annotation.*;

/**
 * User: alexwibowo
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface StateConfiguration {

    /**
     * All event transition
     */
    OnEvent[] value();
}
