package models;

import org.joda.time.DateTime;

public class Evenement {
    public long id;
    public String idCreateur;
    public String nom;
    public String description;
    public DateTime dateDebut;
    public DateTime dateFin;
    public Invite[] invites;
    public String lieu;
}
