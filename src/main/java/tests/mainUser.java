package tests;

import models.User;
import models.role_user;
import services.Authentification;
import services.CrudUser;

import java.util.Scanner;
import io.github.cdimascio.dotenv.Dotenv;

public class mainUser {
    public static void main(String[] args) {
      /*  User user = new User(
                "Benhassine",
                "Tasnim",
                role_user.delivery_person,
                "tunis",
                "tasnim.benhassine1@esprit.tn",
                "123456",
                "28740885",
                "55455027"
        );*/
        CrudUser crudUser = new CrudUser();
        crudUser.search("11111111");
        /*
       Authentification authentification = new Authentification();
       String token = authentification.login("11111111", "admin123");

        // Test de récupération de mot de passe
        boolean resetSuccess = authentification.forgotPassword("at103185@gmail.com");
        if (resetSuccess) {
            System.out.println("Un email de réinitialisation a été envoyé avec succès !");
        } else {
            System.out.println("Échec de l'envoi de l'email de réinitialisation !");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le code reçu par email : ");
        String enteredCode = scanner.nextLine();

        String token1 = authentification.verifyResetCode("at103185@gmail.com", enteredCode);
        if (token != null) {
            // L'utilisateur est connecté automatiquement avec le token
            System.out.println("Utilisateur connecté avec le token : " + token);
        } else {
            System.out.println("Code de réinitialisation invalide ou utilisateur non trouvé.");
        }*/
        /*Dotenv dotenv = Dotenv.load();
        String dbUrl = dotenv.get("SECRET_KEY_SENDGRID");
        System.out.println("SECRET_KEY_SENDGRID: " + dbUrl);*/


    }

}
