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
    private ComboBox<String> cbTypeVehicule; // ComboBox pour le type de véhicule

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Ajout des types de véhicules disponibles
        cbTypeVehicule.getItems().addAll("e_bike", "Bike", "e_scooter");
    }

    private CrudUser userService = new CrudUser(); // Service pour la gestion des utilisateurs

    @FXML
    void handleSignUp(ActionEvent event) {
        try {
            // Récupération des valeurs du formulaire
            String nom = tfNom.getText();
            String prenom = tfPrenom.getText();
            String cin = tfCin.getText();
            String adresse = tfAdresse.getText();
            String email = tfEmail.getText();
            String num_tel = tfNumTel.getText();
            String password = tfPassword.getText();
            boolean verified = false; // Par défaut, non vérifié

            // Validation des champs
            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || num_tel.isEmpty() || cin.isEmpty()) {
                showErrorAlert("All fields except vehicle type are required.");
                return;
            }

            if (password.length() < 6) {
                showErrorAlert("Password should contain at least 6 characters.");
                return;
            }

            if (!email.contains("@")) {
                showErrorAlert("Email should contain '@'.");
                return;
            }

            if (num_tel.length() != 8 || !num_tel.matches("\\d+")) {
                showErrorAlert("Phone number should contain exactly 8 digits.");
                return;
            }

            if (cin.length() != 8 || !cin.matches("\\d+")) {
                showErrorAlert("CIN should contain exactly 8 digits.");
                return;
            }

            // Vérification du CIN unique
            if (userService.existsCin(cin)) {
                showErrorAlert("CIN already exists.");
                return;
            }

            // Détermination du rôle sélectionné
            role_user role;
            if (rbDelivery.isSelected()) {
                role = role_user.delivery_person;
            } else if (rbPartner.isSelected()) {
                role = role_user.partner;
            } else {
                role = role_user.client;
            }

            // Récupération du type de véhicule (peut être null)
            String typeVehicule = cbTypeVehicule.getValue();
            type_vehicule vehicule = null;

            if (typeVehicule != null) {
                switch (typeVehicule) {
                    case "e_bike":
                        vehicule = type_vehicule.e_bike;
                        break;
                    case "Bike":
                        vehicule = type_vehicule.Bike;
                        break;
                    case "e_scooter":
                        vehicule = type_vehicule.e_scooter;
                        break;
                }
            }

            // Création d'un objet User avec un type de véhicule facultatif
            User user = new User(nom, prenom, adresse, email, num_tel, cin, password, role, vehicule);

            // Ajout de l'utilisateur à la base de données
            userService.add(user);

            // Affichage d'un message de succès
            showSuccessAlert("Your account has been created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Oops, couldn't create your account.");
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
            // Chargement de la scène de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Scene signInScene = new Scene(loader.load());

            // Récupération de la fenêtre actuelle et changement de scène
            Stage stage = (Stage) btnSignIn.getScene().getWindow();
            stage.setScene(signInScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading login.fxml.");
        }
    }
}
