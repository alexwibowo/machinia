package org.isolution.machinia.springStateMachine;

import org.isolution.machinia.springStateMachine.services.NotificationService;
import org.isolution.machinia.springStateMachine.services.TrackingNotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * User: alexwibowo
 */
@Configuration
public class TrafficLightSpringConfig {


    @Bean
    @Scope("prototype")
    public SpringTrafficLight springTrafficLight() {
        return new SpringTrafficLight(new TrafficLightContext());
    }


    @Bean
    public NotificationService notificationService() {
        return new TrackingNotificationService();
    }
}
