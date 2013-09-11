package org.isolution.machinia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

/**
 * StateMachine class which supports Spring dependency injection for its {@link State}
 *
 * User: alexwibowo
 */
public abstract class SpringBaseStateMachine extends BaseStateMachine {

    @Autowired
    private ApplicationContext applicationContext;

    public SpringBaseStateMachine(Class<? extends State> initialState, StateMachineContext stateMachineContext) {
        // create new StateMachine, but dont initialize it immediately, as applicationContext wont be ready yet
        super(initialState, stateMachineContext, false);
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void initialize() {
        super.doInitialize(initialState);
    }

    protected void initializeState(State state) {
        // inject the state instance with any dependencies it needs
        applicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(
                state,
                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,
                true
        );
    }

}
