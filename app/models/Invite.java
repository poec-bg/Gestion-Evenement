package models;

import javax.persistence.*;

@Entity
public class Invite {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public long id;
    public String email;
    @OneToOne(cascade= CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public Evenement evenement;
}
