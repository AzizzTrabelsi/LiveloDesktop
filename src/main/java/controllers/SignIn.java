package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
            // Proceed with the token (e.g., storing it or navigating to another scene)
            System.out.println("Connexion réussie. Token : " + token);
        } else {
            // Handle failed login (e.g., show an error message)
            System.out.println("Échec de la connexion.");
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
