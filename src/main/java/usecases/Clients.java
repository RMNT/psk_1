package usecases;

import entities.Client;
import lombok.Getter;
import lombok.Setter;
import persistence.ClientsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Clients {

    @Inject
    private ClientsDAO clientsDAO;

    @Getter @Setter
    private Client clientToCreate = new Client();

    @Getter
    private List<Client> allClients;

    @PostConstruct
    public void init(){
        loadAllClients();
    }

    @Transactional
    public String createClient(){
        this.clientsDAO.persist(clientToCreate);
        return "index?faces-redirect=true";
    }

    private void loadAllClients(){
        this.allClients = clientsDAO.loadAll();
    }
}
