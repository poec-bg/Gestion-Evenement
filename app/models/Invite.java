package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Invite implements Serializable {
    @Id
    private long numLign;
    @Column(name = "email", nullable = false)
    public String email;
    @ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name = "idEvenement", nullable = false)
    public Evenement evenement;
}
