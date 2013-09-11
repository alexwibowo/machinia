package org.isolution.machinia.springStateMachine;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.isolution.machinia.springStateMachine.events.CalmEvent;
import org.isolution.machinia.springStateMachine.services.NotificationService;
import org.isolution.machinia.springStateMachine.states.Yellow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

/**
 * User: alexwibowo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = TrafficLightSpringConfig.class
)
public class SpringTrafficLightTransitionTest {

    @Autowired
    private SpringTrafficLight springTrafficLight;

    @Autowired
    private NotificationService notificationService;

    @Test
    public void testStateMachineWithSpringBeans() throws Exception {
        //when
        springTrafficLight.handle(new CalmEvent());

        //then
        assertThat(
                springTrafficLight.getCurrentState(),
                instanceOf(Yellow.class)
        );
        assertThat(notificationService.getMessages(), hasSize(3));
        assertThat(notificationService.getMessages(), IsIterableContainingInOrder.<String>contains(
                equalTo("Entering org.isolution.machinia.springStateMachine.states.Red"),
                equalTo("Exiting org.isolution.machinia.springStateMachine.states.Red"),
                equalTo("Entering org.isolution.machinia.springStateMachine.states.Yellow")
        ));
    }
}

