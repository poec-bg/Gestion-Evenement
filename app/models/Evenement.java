package models;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class Evenement {
    @Id
    public long id;
    public String idCreateur;
    public String nom;
    public String description;
    @Temporal(TemporalType.TIMESTAMP)
    public DateTime dateDebut;
    @Temporal(TemporalType.TIMESTAMP)
    public DateTime dateFin;
    public String lieu;
    @OneToMany(cascade={CascadeType.ALL})
    public Invite[] invites;
}
