package services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

@ApplicationScoped
@Specializes
public class ImportantModeratorContractGenerator extends NormalModeratorContractGenerator {
    public Integer generateModeratorNumber() {
        try {
            Thread.sleep(6000); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        Integer generatedModerator = (int)((Math.random() * (1000 - 100)) + 100);
        //Integer generatedContract = new Random().nextInt(100);
        return generatedModerator;
    }
}
