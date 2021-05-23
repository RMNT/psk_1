package services;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.Random;

@ApplicationScoped
public class NormalModeratorContractGenerator implements Serializable {

    public Integer generateModeratorNumber() {
        try {
            Thread.sleep(300); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        Integer generatedModerator = new Random().nextInt(100);
        return generatedModerator;
    }
}