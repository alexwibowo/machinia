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
	    public TrafficLight() {
	        super(Red.class);
	    }
	}
The **@StateMachine** annotation is saying that the TrafficLight state machine  has three known states: Green, Red and Yellow. Below is what the Green state looks like:

	@StateConfiguration({
	        @OnEvent(event =  WarnEvent.class, newState = Yellow.class),
	        @OnEvent(event =  PanicEvent.class, newState = Red.class)
	})
	public class Green implements State{
	
	    @Override
	    public String getName() {
	        return "Green";
	    }
	
	    @Override
	    public void onEnter() {
	        System.out.println("Entering " + this.getClass().getName());
	    }
	
	    @Override
	    public void onExit() {
	        System.out.println("Exiting " + this.getClass().getName());
	    }
	}	

The **@StateConfiguration** annotation is saying that when we are on the Green state:

   1. *WarnEvent* will cause state transition to the Yellow state
   2. *PanicEvent* will cause state transition to the Red state 

