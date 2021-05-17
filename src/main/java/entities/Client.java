package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Client.findAll", query = "select e from Client as e")
})
@Table(name = "CLIENT")
@Getter @Setter
public class Client {
    public Client() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @OneToMany(mappedBy = "client")
    private List<Event> events = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(title, client.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title);
    }

}
