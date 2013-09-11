package org.isolution.machinia.springStateMachine.services;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * User: alexwibowo
 */
@Component
public class TrackingNotificationService implements NotificationService{

    private List<String>  messages = new ArrayList<String>();

    @Override
    public void notify(String message) {
        messages.add(message);
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }
}
