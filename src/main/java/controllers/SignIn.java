package controllers;

import com.nimbusds.jose.JWSObject;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.minidev.json.JSONObject;
import services.Authentification;

import java.io.IOException;
import java.text.ParseException;

public class SignIn {

    @FXML
    private Button BtnSignUp;

    @FXML
    private Button LoginButton;

    @FXML
    private TextField tfCIN;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private void handleLogin() {
        // Get user input from the fields
        String cin = tfCIN.getText();
        String password = tfPassword.getText();

        // Create an instance of the Authentification class
        Authentification authService = new Authentification();

        // Call the login method and get the token
        String token = authService.login(cin, password);

        if (token != null) {
            System.out.println("Connexion réussie. Token : " + token);

            // Décoder le token pour récupérer les informations de l'utilisateur
            try {
                JWSObject jwsObject = JWSObject.parse(token);
                JSONObject payload = (JSONObject) jwsObject.getPayload().toJSONObject();
                String role = (String) payload.get("role"); // Récupérer le rôle de l'utilisateur

                // Vérifier si l'utilisateur est admin
                if ("ADMIN".equalsIgnoreCase(role)) {
                    // Charger la scène homeadmin.fxml
                    loadAdminHome();
                } else {
                    System.out.println("Utilisateur non admin, redirection standard.");
                    // Ajoute ici la logique de redirection pour d'autres rôles
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Échec de la connexion.");
        }
    }

    // Méthode pour charger homeadmin.fxml
    private void loadAdminHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionUtilisateurs"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et la remplacer
            Stage stage = (Stage) tfCIN.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void initialize() {
        // Bind the LoginButton to the handleLogin method
        LoginButton.setOnAction(event -> handleLogin());

        // Bind the SignUp button to the navigateToSignUp method
        BtnSignUp.setOnAction(event -> navigateToSignUp());
    }

    @FXML
    private void navigateToSignUp() {
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
            Scene signUpScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) BtnSignUp.getScene().getWindow();
            stage.setScene(signUpScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading SignUp.fxml.");
        }
    }
}
