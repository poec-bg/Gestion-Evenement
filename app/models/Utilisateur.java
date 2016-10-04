package models;

import models.types.EUserRole;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Utilisateur {
    @Id
    public String email;
    public String nom;
    public String prenom;
    public String motDePasse;
    @Enumerated(EnumType.STRING)
    public EUserRole role;
    public boolean isSupprime = false;
}
