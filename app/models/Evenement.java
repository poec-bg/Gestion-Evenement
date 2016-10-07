package models;

import models.types.Categorie;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Evenement implements Serializable{
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public long idEvenement;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    public Utilisateur createur;
    @Column(nullable = false)
    public String nom;
    public String description;
    @Temporal(TemporalType.TIMESTAMP) @Column(nullable = false)
    public Date dateDebut;
    @Temporal(TemporalType.TIMESTAMP) @Column(nullable = false)
    public Date dateFin;
    public String lieu;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "evenement")
    public List<Invite> invites = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    public Categorie categorie;
    @Column(nullable = true)
    public Long idRepetition = null;

    @Override
    public String toString() {
        String result = "[" + this.idEvenement + " ; " + this.categorie + "] " + this.nom
                + " (" + this.dateDebut + " au " + this.dateFin + " ) "
                + "à " + this.lieu + " \n" + this.description ;
        return result;
    }

    public Evenement(){};

    public Evenement(Evenement model){
        if(model == null){
            return;
        }
        this.createur = model.createur;
        this.nom = model.nom;
        this.description = model.description;
        this.dateDebut = model.dateDebut;
        this.dateFin = model.dateFin;
        this.lieu = model.lieu;
        this.categorie = model.categorie;
        this.idRepetition = model.idRepetition;
        if(model.invites.size() > 0){
            for (Invite inviteI : model.invites){
                Invite inviteNew = new Invite();
                inviteNew.email = inviteI.email;
                this.invites.add(inviteNew);
            }
        }
    }
}
