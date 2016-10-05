package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Evenement implements Serializable{
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public long idEvenement;
    @ManyToOne(optional = false, cascade= CascadeType.ALL)
    public Utilisateur createur;
    public String nom;
    public String description;
    @Temporal(TemporalType.TIMESTAMP)
    public Date dateDebut;
    @Temporal(TemporalType.TIMESTAMP)
    public Date dateFin;
    public String lieu;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "evenement", cascade= CascadeType.ALL)
    public List<Invite> invites = new ArrayList<>();

}
