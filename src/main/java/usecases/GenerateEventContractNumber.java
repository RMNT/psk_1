package usecases;

import interceptors.LoggedInvocation;
import services.EventContractGenerator;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class GenerateEventContractNumber implements Serializable {
    @Inject
    EventContractGenerator eventContractGenerator;

    private CompletableFuture<Integer> eventContractGenerationTask = null;

    @LoggedInvocation
    public String generateNewJerseyNumber() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        eventContractGenerationTask = CompletableFuture.supplyAsync(() -> eventContractGenerator.generateEventNumber());

        return  "/eventDetails.xhtml?faces-redirect=true&eventId=" + requestParameters.get("eventId");
    }

    public boolean isNumberGenerationRunning() {
        return eventContractGenerationTask != null && !eventContractGenerationTask.isDone();
    }

    public String getNumberGenerationStatus() throws ExecutionException, InterruptedException {
        if (eventContractGenerationTask == null) {
            return null;
        } else if (isNumberGenerationRunning()) {
            return "Number generation in progress";
        }
        return "Suggested contract number: " + eventContractGenerationTask.get();
    }
}