package models;

import models.types.Categorie;

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
    @Enumerated(EnumType.STRING)
    public Categorie categorie;
    public long idRepetition;

    @Override
    public String toString() {
        String result = "[" + this.idEvenement + " ; " + this.categorie + "] " + this.nom
                + " (" + this.dateDebut + " au " + this.dateFin + " ) "
                + "à " + this.lieu + " \n" + this.description ;
        return result;
    }
}
