package controllers;

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
        String cin = tfCIN.getText();
        String password = tfPassword.getText();

        Authentification authService = new Authentification();

        String token = authService.login(cin, password);

        if (token != null) {
            System.out.println("Connexion réussie. Token : " + token);


            JSONObject userInfo = authService.decodeToken(token);
            if (userInfo != null) {
                String role = userInfo.get("role").toString();  // Get role as string
                boolean isVerified = Boolean.parseBoolean(userInfo.get("verified").toString()); // Get verified status as boolean

                if (!isVerified) {
                    navigateToHomeNotVerified();
                } else {
                    if (role.equals("admin")) {
                        navigateToHomeAdmin();
                    } else if (role.equals("partner")){
                        navigateToHomePartner();
                    }else if (role.equals("delivery_person")){
                        navigateToHomeLivreur();
                    }
                }
            } else {
                System.out.println("Erreur lors de la lecture du token.");
            }

        } else {
            System.out.println("Échec de la connexion.");
        }
    }

    @FXML
    private void navigateToHomeNotVerified() {
        try {
            // Assuming you use FXMLLoader to load the FXML for homeNotVerified
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeNotVerified.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) tfCIN.getScene().getWindow();
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
    @FXML
    private void navigateToHomeAdmin() {
        try {
            // Load the homeAdmin.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeAdmin.fxml"));
            Scene homeAdminScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            stage.setScene(homeAdminScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading homeAdmin.fxml.");
        }
    }
    @FXML
    private void navigateToHomePartner() {
        try {
            // Load the homeAdmin.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homePartner.fxml"));
            Scene homeAdminScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            stage.setScene(homeAdminScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading homeAdmin.fxml.");
        }
    }
    @FXML
    private void navigateToHomeLivreur() {
        try {
            // Load the homeAdmin.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeLivreur.fxml"));
            Scene homeAdminScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            stage.setScene(homeAdminScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading homeAdmin.fxml.");
        }
    }
}
