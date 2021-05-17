package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Moderator.findAll", query = "select m from Moderator as m")
})
@Table(name = "MODERATOR")
@Getter @Setter
public class Moderator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @Column(name = "FULLNAME")
    private String fullname;

    @Column(name="CONTRACTNUMBER")
    private Integer contractNumber;

    @ManyToOne
    @JoinColumn(name="EVENT_ID")
    private Event event;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

    public Moderator() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Moderator moderator = (Moderator) o;
        return Objects.equals(id, moderator.id) &&
                Objects.equals(fullname, moderator.fullname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullname);
    }
}
