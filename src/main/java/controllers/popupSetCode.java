package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.minidev.json.JSONObject;
import services.Authentification;

import java.io.IOException;

public class popupSetCode {

    @FXML
    private Button btnConfirm;

    @FXML
    private TextField tfCode;

    private String userEmail;
    private Authentification authService = new Authentification();

    // Référence vers la fenêtre SignIn (facultatif si vous voulez la fermer ici)
    private Stage signInStage;
    public void setSignInStage(Stage signInStage) {
        this.signInStage = signInStage;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
        System.out.println("Email reçu : " + email);
    }

    @FXML
    public void handleConfirm() {
        String enteredCode = tfCode.getText();

        String token = authService.verifyResetCode(userEmail, enteredCode);

        if (token != null) {
            JSONObject userInfo = authService.decodeToken(token);
            if (userInfo != null) {
                Boolean isVerified = (Boolean) userInfo.get("verified");
                String role = (String) userInfo.get("role");

                if (isVerified == null || !isVerified) {
                    navigateToHomeNotVerified();
                } else {
                    if ("admin".equalsIgnoreCase(role)) {
                        navigateToHomeAdmin();
                    } else if ("partner".equalsIgnoreCase(role)) {
                        navigateToHomePartner();
                    } else if ("delivery_person".equalsIgnoreCase(role)) {
                        navigateToHomeLivreur();
                    } else {
                        System.out.println("Rôle inconnu, redirection par défaut.");
                        // ...
                    }
                }

                // ===> Après la navigation, vous pouvez fermer la fenêtre SignIn
                // si c'est votre besoin, par exemple :
                if (signInStage != null) {
                    signInStage.close();
                }

                // Fermer la popupSetCode elle-même
                Stage currentStage = (Stage) btnConfirm.getScene().getWindow();
                currentStage.close();

            } else {
                System.out.println("Erreur lors du décodage du token.");
            }
        } else {
            System.out.println("Code de réinitialisation invalide ou utilisateur introuvable.");
        }
    }


    @FXML
    private void navigateToHomeNotVerified() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeNotVerified.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = getCurrentStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToHomeAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeAdmin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = getCurrentStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading homeAdmin.fxml.");
        }
    }

    @FXML
    private void navigateToHomePartner() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homePartner.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = getCurrentStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading homePartner.fxml.");
        }
    }

    @FXML
    private void navigateToHomeLivreur() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeLivreur.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = getCurrentStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading homeLivreur.fxml.");
        }
    }

    private Stage getCurrentStage() {
        return (Stage) btnConfirm.getScene().getWindow();
    }


}
