package controllers.secure;

import exceptions.InvalidArgumentException;
import models.Utilisateur;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import services.UtilisateurService;

public class Security extends Secure.Security {

    public static boolean authenticate(String login, String password) {
        try {
            if (UtilisateurService.get().authenticate(login, password)) {
                Logger.info("Security | authenticate : connexion de l'utilisateur [%s]", login);
                return true;
            }
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        Logger.warn("Security | authenticate : tentative de connexion de l'utilisateur inconnu [%s]", login);
        return false;
    }

    public static boolean check(String profile) {
        Utilisateur connectedClient = connectedUser();
        if (profile.equals(connectedClient.role.name())) {
            return true;
        }
        return false;
    }

    public static String connected() {
        return session.get("username");
    }

    public static boolean isConnected() {
        return session.contains("username");
    }

    public static Utilisateur connectedUser() {
        Utilisateur user = null;
            user = UtilisateurService.get().getUtilisateurByEmail(connected());
        if (user == null) {
            redirect("secure.Secure.logout");
        } else {
            renderArgs.put("connectedUser", user);
        }
        return user;
    }

    public static void onAuthenticated() {
        String url = flash.get("url");
        if (StringUtils.isEmpty(url)) {
            redirect("Application.index");
        }
        redirect(url);
    }

    public static void onDisconnect() {
            Utilisateur user = UtilisateurService.get().getUtilisateurByEmail(Security.connected());
        session.clear();
        response.removeCookie("rememberme");
        redirect("Application.index");
    }
}