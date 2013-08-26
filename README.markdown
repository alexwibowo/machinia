Machinia [![Build Status](https://travis-ci.org/alexwibowo/machinia.png?branch=master)](https://travis-ci.org/alexwibowo/machinia)
==============
Machinia is a Java based **finite state machine (FSM)**. A state machine is a mathematical model of computation used to design both computer programs and sequential logic circuits.

A state machine can be in one of a finite number of *states*, and the machine is only on one state at a time, referred to as the *current state*. State *transition* can occur when the machine receive a triggering *event*.


Sample usage
============
We are going to model a traffic light system. So lets start with the traffic light state machine itself.

	package org.isolution.machinia.trafficLight;
	
	import org.isolution.machinia.*;
	import org.isolution.machinia.trafficLight.states.Green;
	import org.isolution.machinia.trafficLight.states.Red;
	import org.isolution.machinia.trafficLight.states.Yellow;

	@StateMachine(
		states = { 
			Green.class, Red.class, Yellow.class 
		}
	)
	public class TrafficLight extends BaseStateMachine {	
	    public TrafficLight(TrafficLightContext context) {
	        super(Red.class, context);
	    }
	}
The **@StateMachine** annotation is saying that the TrafficLight state machine  has three known states: Green, Red and Yellow.
The first argument to the **BaseStateMachine** constructor is the initial state of the state machine. In our example above, we set the initial state to be Red.
The second argument is any implementation of **StateMachineContext**. This same *context* will be passed on to each of the method *hooks* of the states.

Below is what the Red state looks like:

	@StateConfiguration({
	    @OnEvent(event =  CalmEvent.class, newState = Yellow.class),
	    @OnEvent(event =  ClearEvent.class, newState = Green.class)
	})
	public class Red implements State {
	
	    @Override
	    public String getName() {
	        return "Red";
	    }
	
	
	    @Override
	    public void onEnter(StateMachineContext stateMachineContext) {
	        System.out.println("Entering " + this.getClass().getName());
	    }
	
	    @Override
	    public void onExit(StateMachineContext stateMachineContext) {
	        System.out.println("Exiting " + this.getClass().getName());
	    }
	}

The **@StateConfiguration** annotation is saying that when we are on the Red state:

   1. *CalmEvent* will cause state transition to the Yellow state
   2. *ClearEvent* will cause state transition to the Green state 


and with those in place, lets start playing with the traffic light (see sample code for Yellow state)!

    TrafficLight trafficLight = new TrafficLight(new TrafficLightContext());
    trafficLight.handle(new CalmEvent()); // will cause transition to Yellow state
    trafficLight.handle(new ClearEvent()); // will cause transition to Green state
