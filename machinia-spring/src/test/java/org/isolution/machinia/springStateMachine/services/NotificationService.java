package org.isolution.machinia.springStateMachine.services;

import java.util.List;

/**
 * User: alexwibowo
 */
public interface NotificationService {

    void notify(String message);

    List<String> getMessages();
}
