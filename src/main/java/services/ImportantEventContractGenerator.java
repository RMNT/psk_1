package services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.io.Serializable;
import java.util.Random;

@ApplicationScoped
@Alternative
public class ImportantEventContractGenerator implements Serializable, EventContractGenerator {

    public Integer generateEventNumber() {
        try {
            Thread.sleep(6); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        Double randNum = Math.random();
        Integer generatedContract = (int)((Math.random() * (5000 - 1000)) + 1000);
        //Integer generatedContract = new Random().nextInt(100);
        return generatedContract;
    }
}