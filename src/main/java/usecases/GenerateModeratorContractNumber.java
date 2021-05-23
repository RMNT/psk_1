package usecases;

import interceptors.LoggedInvocation;
import services.EventContractGenerator;
import services.NormalModeratorContractGenerator;

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
public class GenerateModeratorContractNumber implements Serializable {
    @Inject
    NormalModeratorContractGenerator moderatorContractGenerator;

    private CompletableFuture<Integer> moderatorContractGenerationTask = null;

    @LoggedInvocation
    public String generateNewContractNumber() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        moderatorContractGenerationTask = CompletableFuture.supplyAsync(() -> moderatorContractGenerator.generateModeratorNumber());
        return  "/moderatorDetails.xhtml?faces-redirect=true&moderatorId=" + requestParameters.get("moderatorId");
    }

    public boolean isNumberGenerationRunning() {
        return moderatorContractGenerationTask != null && !moderatorContractGenerationTask.isDone();
    }

    public String getNumberGenerationStatus() throws ExecutionException, InterruptedException {
        if (moderatorContractGenerationTask == null) {
            return null;
        } else if (isNumberGenerationRunning()) {
            return "Number generation in progress";
        }
        return "Suggested contract number: " + moderatorContractGenerationTask.get();
    }
}
