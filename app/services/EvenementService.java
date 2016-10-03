package services;

import models.Evenement;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateTime;

public class EvenementService {
    private static EvenementService monInstance;
    private static Session session = null;

    private EvenementService(){

    }

    public static  EvenementService get(){
        if(monInstance == null){
            monInstance = new EvenementService();
        }
        return monInstance;
    }

    //Créer un nouvelle évènement [nom] par [idCreateur] avec pour péridode [debut] à [fin]
    public void createEvent(DateTime debut, DateTime fin, String nom, String idCreateur){
        //vérifications
        boolean estOk = true;
        estOk = estOk && validateDates(debut, fin);


        Evenement evenement = new Evenement();

    }



    //Règles de validité de la classe Evenement
    private boolean validateDates(DateTime debut, DateTime fin){
        if(debut.isBefore(fin)) {
            return  true;
        }else {
            return false;
        }
    }

    private boolean validateIdCreateur(String id){
        //TODO
        return true;
    }
}
