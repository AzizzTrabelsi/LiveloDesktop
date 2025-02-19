package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import services.CrudUser;
import models.role_user;
import models.type_vehicule;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUp implements Initializable {

    @FXML
    private Button btnSignIn;

    @FXML
    private TextField tfAdresse, tfCin, tfEmail, tfNom, tfNumTel, tfPrenom;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private RadioButton rbDelivery, rbPartner, rbClient;

    @FXML
    private ComboBox<String> cbTypeVehicule; // Make sure the fx:id matches the one in FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbTypeVehicule.getItems().addAll("e_bike", "Bike", "e_scooter");
    }
    private CrudUser userService = new CrudUser(); // Assure-toi d'avoir cette classe pour gérer les requêtes SQL

    @FXML
    void handleSignUp(ActionEvent event) {
        try {
            // Récupérer les valeurs du formulaire
            String nom = tfNom.getText();
            String prenom = tfPrenom.getText();
            String cin = tfCin.getText();
            String adresse = tfAdresse.getText();
            String email = tfEmail.getText();
            String num_tel = tfNumTel.getText();
            String password = tfPassword.getText();
            boolean verified = false; // Par défaut, non vérifié

            // Validation des champs
            if (password.length() < 6) {
                showErrorAlert("Password should contain more than 6 characters.");
                return;
            }

            if (!email.contains("@")) {
                showErrorAlert("Email should contain '@'.");
                return;
            }

            if (num_tel.length() != 8 || !num_tel.matches("\\d+")) {
                showErrorAlert("Phone number should contain 8 digits.");
                return;
            }

            if (cin.length() != 8 || !cin.matches("\\d+")) {
                showErrorAlert("CIN should contain 8 digits.");
                return;
            }

            // Vérification du CIN unique
            if (userService.existsCin(cin)) {
                showErrorAlert("CIN already exists.");
                return;
            }

            // Déterminer le rôle sélectionné
            role_user role;
            if (rbDelivery.isSelected()) {
                role = role_user.delivery_person;
            } else if (rbPartner.isSelected()) {
                role = role_user.partner;
            } else {
                role = role_user.client;
            }

            // Si le type de véhicule est sélectionné, on le récupère
            String typeVehicule = cbTypeVehicule.getValue();
            type_vehicule vehicule = null;

            // Création d'un véhicule spécifique en fonction du type
            switch (typeVehicule) {
                case "e_bike":
                    vehicule = type_vehicule.e_bike; // Assurez-vous que ce soit défini dans votre enum type_vehicule
                    break;
                case "Bike":
                    vehicule = type_vehicule.Bike; // Pareil ici
                    break;
                case "e_scooter":
                    vehicule = type_vehicule.e_scooter; // Pareil ici
                    break;
                default:
                    showErrorAlert("Invalid vehicle type.");
                    return;
            }

            // Créer un objet User sans validation obligatoire sur le type de véhicule
            User user = new User(nom, prenom, adresse, email, num_tel, cin, password, role, vehicule);

            // Ajouter l'utilisateur à la base de données
            userService.add(user);

            // Afficher une confirmation
            showSuccessAlert("Your account is created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Oops couldn't create your account.");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void navigateToSignIn() {
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Scene signUpScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) btnSignIn.getScene().getWindow();
            stage.setScene(signUpScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading SignUp.fxml.");
        }
    }

}
