package models;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Evenement implements Serializable{
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public long idEvenement;
    @ManyToOne
    public Utilisateur createur;
    public String nom;
    public String description;
    @Temporal(TemporalType.TIMESTAMP)
    public Date dateDebut;
    @Temporal(TemporalType.TIMESTAMP)
    public Date dateFin;
    public String lieu;
    @OneToMany(cascade= CascadeType.ALL)
    public List<Invite> invites;

    public Evenement(){
        invites = new ArrayList<>();
    }
}
