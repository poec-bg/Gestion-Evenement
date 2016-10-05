package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Invite implements Serializable {
    @Id
    public String email;
    @Id @ManyToOne
    public Evenement evenement;
}
