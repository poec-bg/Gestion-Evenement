package services;


import exceptions.InvalidArgumentException;
import models.Evenement;
import models.Invite;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class InviteService {
    private static InviteService instance;

    private InviteService() {

    }
    public static InviteService get() {
        if (instance == null) {
            instance = new InviteService();
        }
        return instance;
    }

    public Invite createInvite(String email,Evenement evenement) throws Exception {
        List<String> validationMessages = new ArrayList<>();
        if (email == null) {
            validationMessages.add("L'email ne peut être null");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        Invite invite = new Invite();
        invite.email=email;
        invite.evenement=evenement;

        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(invite);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            throw new Exception("HibernateException:  " + e.getMessage() );
        }finally {
            session.close();
        }
        return invite;
    }



}
