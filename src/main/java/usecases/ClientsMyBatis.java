package usecases;

import lombok.Getter;
import lombok.Setter;
import mybatis.dao.ClientMapper;
import mybatis.model.Client;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class ClientsMyBatis {
    @Inject
    private ClientMapper clientMapper;

    @Getter
    private List<Client> allClients;

    @Getter @Setter
    private Client clientToCreate = new Client();

    @PostConstruct
    public void init() {
        this.loadAllClients();
    }

    private void loadAllClients() {
        this.allClients = clientMapper.selectAll();
    }

    @Transactional
    public String createClient() {
        clientMapper.insert(clientToCreate);
        return "/myBatis/clients?faces-redirect=true";
    }
}
