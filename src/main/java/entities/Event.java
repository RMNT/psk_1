package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Event.findAll", query = "select m from Event as m")
})
@Table(name = "EVENT")
@Getter @Setter
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @Column(name = "title")
    private String title;

    @Column(name="CONTRACTNUMBER")
    private Integer contractNumber;

    @ManyToOne
    @JoinColumn(name="CLIENT_ID")
    private Client client;

    @ManyToMany(mappedBy = "events", fetch = FetchType.EAGER)
    private List<Moderator> moderators = new ArrayList<>();

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

    public Event() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
                Objects.equals(title, event.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
