package services;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.Random;

@ApplicationScoped
public class EventContractGenerator implements Serializable {

    public Integer generateEventNumber() {
        try {
            Thread.sleep(3); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        Integer generatedContact = new Random().nextInt(100);
        return generatedContact;
    }
}