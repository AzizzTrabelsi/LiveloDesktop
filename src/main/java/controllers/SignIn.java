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
        // Get user input from the fields
        String cin = tfCIN.getText();
        String password = tfPassword.getText();

        // Create an instance of the Authentification class
        Authentification authService = new Authentification();

        // Call the login method and get the token
        String token = authService.login(cin, password);

        if (token != null) {
            System.out.println("Connexion réussie. Token : " + token);

            // Decode the token to get the user's role and verified status
            JSONObject userInfo = authService.decodeToken(token);
            if (userInfo != null) {
                String role = userInfo.get("role").toString();  // Get role as string
                boolean isVerified = Boolean.parseBoolean(userInfo.get("verified").toString()); // Get verified status as boolean

                // Check if the user is verified
                if (!isVerified) {
                    // Redirect to the homeNotVerified view
                    navigateToHomeNotVerified();
                } else {
                    // If verified, check the role
                    if (role.equals("admin")) {
                        // Redirect to the homeAdmin view
                        navigateToHomeAdmin();
                    } else {
                        // Redirect to the regular home view (or another view for non-admin users)
                        //navigateToHome();
                    }
                }
            } else {
                System.out.println("Erreur lors de la lecture du token.");
            }
        } else {
            System.out.println("Échec de la connexion.");
        }
    }

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
}
