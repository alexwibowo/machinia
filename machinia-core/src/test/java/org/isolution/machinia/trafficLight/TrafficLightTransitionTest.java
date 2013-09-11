package org.isolution.machinia.trafficLight;

import org.isolution.machinia.Event;
import org.isolution.machinia.State;
import org.isolution.machinia.trafficLight.events.CalmEvent;
import org.isolution.machinia.trafficLight.events.ClearEvent;
import org.isolution.machinia.trafficLight.events.PanicEvent;
import org.isolution.machinia.trafficLight.events.WarnEvent;
import org.isolution.machinia.trafficLight.states.Green;
import org.isolution.machinia.trafficLight.states.Red;
import org.isolution.machinia.trafficLight.states.Yellow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * User: alexwibowo
 */
@RunWith(Parameterized.class)
public class TrafficLightTransitionTest {

    private State currentState;

    private Event event;

    private Class<? extends State> expectedState;

    public TrafficLightTransitionTest(State currentState, Event event, Class<? extends State> expectedState) {
        this.currentState = currentState;
        this.event = event;
        this.expectedState = expectedState;
    }

    @Parameterized.Parameters
    public static Collection data() {
        Object[][] data = new Object[][]{
                {new Red(), new ClearEvent(), Green.class},
                {new Red(), new CalmEvent(), Yellow.class},
                {new Red(), new PanicEvent(), Red.class},

                {new Green(), new PanicEvent(), Red.class},
                {new Green(), new WarnEvent(), Yellow.class},
                {new Green(), new CalmEvent(), Green.class},

                {new Yellow(), new PanicEvent(), Red.class},
                {new Yellow(), new ClearEvent(), Green.class},
                {new Yellow(), new CalmEvent(), Yellow.class}

        };
        return asList(data);
    }

    @Test
    public void test() throws Exception {
        //given
        TrafficLight trafficLight = new TrafficLight();
        setCurrentStateOfTrafficLight(trafficLight, currentState);

        //when
        trafficLight.handle(event);

        //then
        assertThat(
                "When state is [" + currentState.getClass() + "] then receiving [" + event.getClass() + "] should make it transition to [" + expectedState +"]",
                trafficLight.getCurrentState(),
                instanceOf(expectedState)
        );
    }

    private void setCurrentStateOfTrafficLight(TrafficLight trafficLight, State currentState) throws Exception {
        Field field = TrafficLight.class.getSuperclass().getDeclaredField("currentState");
        field.setAccessible(true);
        field.set(trafficLight, currentState);
    }
}

