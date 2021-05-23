package services;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Random;

@ApplicationScoped
public class NormalEventContractGenerator implements Serializable, EventContractGenerator {

    public Integer generateEventNumber() {
        try {
            Thread.sleep(3); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        Integer generatedContact = new Random().nextInt(100);
        return generatedContact;
    }
}