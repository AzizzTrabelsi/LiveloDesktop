package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.Authentification;

import java.io.IOException;

public class PopupResetPassword {
    private Stage signInStage;

    public void setSignInStage(Stage signInStage) {
        this.signInStage = signInStage;
    }

    @FXML
    private Button btnSend;

    @FXML
    private TextField txfEmail;

    @FXML
    private Text txtBackLogin;

    private Authentification authService = new Authentification();
    

    @FXML
    private void initialize() {
        btnSend.setOnAction(event -> handleForgotPassword());
    }

    private void handleForgotPassword() {
        String email = txfEmail.getText().trim();
        if (!email.isEmpty()) {
            boolean result = authService.forgotPassword(email);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (result) {
                // Si l’envoi du code a réussi
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Un code de réinitialisation a été envoyé à votre adresse e-mail.");

                // Ouvrir la popup pour saisir le code
                ouvrirPipupSetCode(email);

            } else {
                // Échec de l’envoi
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Échec de l'envoi du code de réinitialisation.");
            }

            alert.showAndWait();

        } else {
            // Email vide => on affiche une erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un email valide.");
            alert.showAndWait();
        }
    }
    @FXML
    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Scene signInScene = new Scene(loader.load());

            Stage stage = (Stage) txtBackLogin.getScene().getWindow();
            stage.setScene(signInScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading login.fxml.");
        }
    }
    @FXML
    private void ouvrirPipupSetCode(String email) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/popupSetCode.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la fenêtre popupSetCode
            popupSetCode controller = loader.getController();
            controller.setUserEmail(email);

            // Si vous souhaitez fermer la fenêtre SignIn plus tard, vous pouvez transmettre le stage
            controller.setSignInStage(signInStage);

            Stage stage = new Stage();
            stage.setTitle("Reset password");
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la popup actuelle (PopupResetPassword) juste après avoir ouvert la nouvelle
            Stage currentStage = (Stage) btnSend.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
