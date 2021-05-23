package decorators;

import services.EventContractGenerator;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public class EventContractNumberDecorator implements EventContractGenerator {
    @Inject
    @Delegate
    @Any
    EventContractGenerator eventContractGenerator;

    @Override
    public Integer generateEventNumber() {
        return (-1)*
                eventContractGenerator.generateEventNumber();
    }
}