package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Role;
import models.User;
import services.CrudUser;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GestionUtilisateurs implements Initializable {

    private CrudUser su = new CrudUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Afficher tous les utilisateurs au démarrage
        Show(null);

        // Ajouter un écouteur sur le champ de recherche
        anSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // Si le champ de recherche est vide, afficher tous les utilisateurs
                loadUsers();
            } else {
                // Sinon, effectuer une recherche
                searchUsers(newValue);
            }
        });
    }

    @FXML
    void Show(ActionEvent showEvent) {
        hbHedha.getChildren().clear();

        List<User> usersList = su.getAll();

        for (User user : usersList) {
            HBox userRow = new HBox(4);
            userRow.setPrefHeight(32.0);
            userRow.setPrefWidth(765.0);

            Label lblPrenom = new Label(user.getPrenom());
            lblPrenom.setMinWidth(80);
            lblPrenom.setMaxWidth(80);

            Label lblNom = new Label(user.getNom());
            lblNom.setMinWidth(80);
            lblNom.setMaxWidth(80);

            Label lblCin = new Label(user.getCin());
            lblCin.setMinWidth(80);
            lblCin.setMaxWidth(80);

            Label lblAdress = new Label(user.getAdresse());
            lblAdress.setMinWidth(80);
            lblAdress.setMaxWidth(80);

            Label lblEmail = new Label(user.getEmail());
            lblEmail.setMinWidth(130);
            lblEmail.setMaxWidth(130);

            Label lblRole = new Label(user.getRole().name());
            lblRole.setMinWidth(105);
            lblRole.setMaxWidth(105);

            Label lblVerified = new Label(String.valueOf(user.isVerified() ? "Yes" : "No"));
            lblVerified.setMinWidth(80);
            lblVerified.setMaxWidth(80);

            // Vérification si type_vehicule est null et assignation d'une valeur par défaut
            String transport = (user.getType_vehicule() != null) ? user.getType_vehicule().toString() : "Aucun transport";
            Label lblTransport = new Label(transport);
            lblTransport.setMinWidth(80);
            lblTransport.setMaxWidth(80);

            userRow.getChildren().addAll(lblPrenom, lblNom, lblCin, lblAdress, lblEmail, lblRole, lblVerified, lblTransport);

            userRow.setOnMouseClicked(event -> showUserDetailsPopup(user));

            vListUsers.getChildren().add(userRow);
        }
    }


    @FXML
    private void showUserDetailsPopup(User user) {
        System.out.println("Utilisateur sélectionné : " + user.getPrenom() + " " + user.getNom());

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de l'utilisateur");
        alert.setHeaderText("Informations sur " + user.getPrenom() + " " + user.getNom());
        alert.setContentText("CIN : " + user.getCin() + "\n" +
                "Adresse : " + user.getAdresse() + "\n" +
                "Email : " + user.getEmail() + "\n" +
                "Rôle : " + user.getRole().name() + "\n" +
                "Vérifié : " + user.isVerified() + "\n" +
                "Transport : " + user.getType_vehicule());


        ButtonType updateButton = new ButtonType("Mettre à jour");
        ButtonType deleteButton = new ButtonType("Supprimer");

        alert.getButtonTypes().setAll(updateButton, deleteButton, ButtonType.CLOSE);

        alert.showAndWait().ifPresent(response -> {
            if (response == updateButton) {
                showUpdatePopup(user);
                System.out.println("Mettre à jour les informations de l'utilisateur.");
            } else if (response == deleteButton) {
                // Afficher une pop-up de confirmation pour la suppression
                javafx.scene.control.Alert confirmationAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation de la suppression");
                confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
                confirmationAlert.setContentText("Cette action est irréversible.");

                // Ajouter les boutons de confirmation
                ButtonType yesButton = new ButtonType("Oui");
                ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

                confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                confirmationAlert.showAndWait().ifPresent(confirmationResponse -> {
                    if (confirmationResponse == yesButton) {
                        // Effectuer la suppression
                        su.delete(user.getId());
                        System.out.println("Utilisateur supprimé.");
                        loadUsers();
                    } else {
                        System.out.println("Suppression annulée.");
                    }
                });
            }
        });
    }

    @FXML
    private void showUpdatePopup(User user) {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Mettre à jour l'utilisateur");
        dialog.setHeaderText("Modifier les informations de l'utilisateur");

        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField prenomField = new TextField(user.getPrenom());
        TextField nomField = new TextField(user.getNom());
        TextField cinField = new TextField(user.getCin());
        TextField adresseField = new TextField(user.getAdresse());
        TextField emailField = new TextField(user.getEmail());
        TextField roleField = new TextField(user.getRole().name());
        TextField transportField = new TextField(user.getType_vehicule().toString());

        grid.add(new Label("Prénom:"), 0, 0);
        grid.add(prenomField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(nomField, 1, 1);
        grid.add(new Label("CIN:"), 0, 2);
        grid.add(cinField, 1, 2);
        grid.add(new Label("Adresse:"), 0, 3);
        grid.add(adresseField, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(emailField, 1, 4);
        grid.add(new Label("Rôle:"), 0, 5);
        grid.add(roleField, 1, 5);
        grid.add(new Label("Transport:"), 0, 6);
        grid.add(transportField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                // Update the user object with the new values
                user.setPrenom(prenomField.getText());
                user.setNom(nomField.getText());
                user.setCin(cinField.getText());
                user.setAdresse(adresseField.getText());
                user.setEmail(emailField.getText());
                user.setRole(User.role.valueOf(roleField.getText()));
                user.setType_vehicule(User.type_vehicule.valueOf(transportField.getText()));

                // Save the updated user to the database
                su.update(user);

                // Refresh the user list in the UI
                loadUsers(); // Call loadUsers to refresh the list

                return user;
            }
            return null;
        });

        dialog.showAndWait();
    }


    @FXML
    public void loadUsers() {
        System.out.println("Chargement des utilisateurs...");

        // Nettoyer la liste actuelle des utilisateurs
        vListUsers.getChildren().clear();

        HBox headerRow = new HBox(4);
        headerRow.setPrefHeight(32.0);
        headerRow.setPrefWidth(765.0);

        headerRow.setStyle("-fx-background-color: #398c3e; -fx-padding: 10px;");

        Label lblHeaderPrenom = new Label("First Name");
        lblHeaderPrenom.setMinWidth(80);
        lblHeaderPrenom.setMaxWidth(80);
        lblHeaderPrenom.setStyle("-fx-text-fill: black;");

        Label lblHeaderNom = new Label("Last name");
        lblHeaderNom.setMinWidth(80);
        lblHeaderNom.setMaxWidth(80);
        lblHeaderNom.setStyle("-fx-text-fill: black;");

        Label lblHeaderCin = new Label("CIN");
        lblHeaderCin.setMinWidth(80);
        lblHeaderCin.setMaxWidth(80);
        lblHeaderCin.setStyle("-fx-text-fill: black;");

        Label lblHeaderAdress = new Label("Address");
        lblHeaderAdress.setMinWidth(80);
        lblHeaderAdress.setMaxWidth(80);
        lblHeaderAdress.setStyle("-fx-text-fill: black;");

        Label lblHeaderEmail = new Label("Email");
        lblHeaderEmail.setMinWidth(130);
        lblHeaderEmail.setMaxWidth(130);
        lblHeaderEmail.setStyle("-fx-text-fill: black;");

        Label lblHeaderRole = new Label("Role");
        lblHeaderRole.setMinWidth(105);
        lblHeaderRole.setMaxWidth(105);
        lblHeaderRole.setStyle("-fx-text-fill: black;");

        Label lblHeaderVerified = new Label("Verified");
        lblHeaderVerified.setMinWidth(80);
        lblHeaderVerified.setMaxWidth(80);
        lblHeaderVerified.setStyle("-fx-text-fill: black;");

        Label lblHeaderTransport = new Label("Transport");
        lblHeaderTransport.setMinWidth(80);
        lblHeaderTransport.setMaxWidth(80);
        lblHeaderTransport.setStyle("-fx-text-fill: black;");

        headerRow.getChildren().addAll(lblHeaderPrenom, lblHeaderNom, lblHeaderCin, lblHeaderAdress, lblHeaderEmail, lblHeaderRole, lblHeaderVerified, lblHeaderTransport);

        vListUsers.getChildren().add(headerRow);

        List<User> usersList = su.getAll();

        for (User user : usersList) {
            HBox userRow = new HBox(4);
            userRow.setPrefHeight(32.0);
            userRow.setPrefWidth(765.0);

            Label lblPrenom = new Label(user.getPrenom());
            lblPrenom.setMinWidth(80);
            lblPrenom.setMaxWidth(80);

            Label lblNom = new Label(user.getNom());
            lblNom.setMinWidth(80);
            lblNom.setMaxWidth(80);

            Label lblCin = new Label(user.getCin());
            lblCin.setMinWidth(80);
            lblCin.setMaxWidth(80);

            Label lblAdress = new Label(user.getAdresse());
            lblAdress.setMinWidth(80);
            lblAdress.setMaxWidth(80);

            Label lblEmail = new Label(user.getEmail());
            lblEmail.setMinWidth(130);
            lblEmail.setMaxWidth(130);

            Label lblRole = new Label(user.getRole().name());
            lblRole.setMinWidth(105);
            lblRole.setMaxWidth(105);

            Label lblVerified = new Label(String.valueOf(user.isVerified() ? "Yes" : "No"));
            lblVerified.setMinWidth(80);
            lblVerified.setMaxWidth(80);

            // Gestion du transport avec vérification si type_vehicule est null
            String transport = (user.getType_vehicule() != null) ? user.getType_vehicule().toString() : "Aucun transport";
            Label lblTransport = new Label(transport);
            lblTransport.setMinWidth(80);
            lblTransport.setMaxWidth(80);

            userRow.getChildren().addAll(lblPrenom, lblNom, lblCin, lblAdress, lblEmail, lblRole, lblVerified, lblTransport);

            Button deleteButton = new Button("Supprimer");
            deleteButton.setOnAction(event -> {
                javafx.scene.control.Alert confirmationAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation de la suppression");
                confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
                confirmationAlert.setContentText("Cette action est irréversible.");

                ButtonType yesButton = new ButtonType("Oui");
                ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

                confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                confirmationAlert.showAndWait().ifPresent(confirmationResponse -> {
                    if (confirmationResponse == yesButton) {
                        // Effectuer la suppression
                        su.delete(user.getId());
                        System.out.println("Utilisateur supprimé.");
                        loadUsers();
                    } else {
                        System.out.println("Suppression annulée.");
                    }
                });
            });

            userRow.getChildren().add(deleteButton);

            userRow.setOnMouseClicked(event -> showUserDetailsPopup(user));

            vListUsers.getChildren().add(userRow);
        }
    }



    @FXML
    private void searchUsers(String criteria) {
        System.out.println("Recherche des utilisateurs pour le critère : " + criteria);

        vListUsers.getChildren().clear();

        HBox headerRow = new HBox(4);
        headerRow.setPrefHeight(32.0);
        headerRow.setPrefWidth(765.0);
        headerRow.setStyle("-fx-background-color: #398c3e; -fx-padding: 10px;");

        Label lblHeaderPrenom = new Label("First name");
        lblHeaderPrenom.setMinWidth(80);
        lblHeaderPrenom.setMaxWidth(80);
        lblHeaderPrenom.setStyle("-fx-text-fill: black;");

        Label lblHeaderNom = new Label("Last name");
        lblHeaderNom.setMinWidth(80);
        lblHeaderNom.setMaxWidth(80);
        lblHeaderNom.setStyle("-fx-text-fill: black;");

        Label lblHeaderCin = new Label("CIN");
        lblHeaderCin.setMinWidth(80);
        lblHeaderCin.setMaxWidth(80);
        lblHeaderCin.setStyle("-fx-text-fill: black;");

        Label lblHeaderAdress = new Label("Address");
        lblHeaderAdress.setMinWidth(80);
        lblHeaderAdress.setMaxWidth(80);
        lblHeaderAdress.setStyle("-fx-text-fill: black;");

        Label lblHeaderEmail = new Label("Email");
        lblHeaderEmail.setMinWidth(130);
        lblHeaderEmail.setMaxWidth(130);
        lblHeaderEmail.setStyle("-fx-text-fill: black;");

        Label lblHeaderRole = new Label("Role");
        lblHeaderRole.setMinWidth(105);
        lblHeaderRole.setMaxWidth(105);
        lblHeaderRole.setStyle("-fx-text-fill: black;");

        Label lblHeaderVerified = new Label("Verified");
        lblHeaderVerified.setMinWidth(80);
        lblHeaderVerified.setMaxWidth(80);
        lblHeaderVerified.setStyle("-fx-text-fill: black;");

        Label lblHeaderTransport = new Label("Transport");
        lblHeaderTransport.setMinWidth(80);
        lblHeaderTransport.setMaxWidth(80);
        lblHeaderTransport.setStyle("-fx-text-fill: black;");

        headerRow.getChildren().addAll(lblHeaderPrenom, lblHeaderNom, lblHeaderCin, lblHeaderAdress, lblHeaderEmail, lblHeaderRole, lblHeaderVerified, lblHeaderTransport);
        vListUsers.getChildren().add(headerRow);

        List<User> usersList = su.search(criteria);

        for (User user : usersList) {
            HBox userRow = new HBox(4);
            userRow.setPrefHeight(32.0);
            userRow.setPrefWidth(765.0);
            userRow.setStyle("-fx-padding: 10px;");

            Label lblPrenom = new Label(user.getPrenom());
            lblPrenom.setMinWidth(80);
            lblPrenom.setMaxWidth(80);

            Label lblNom = new Label(user.getNom());
            lblNom.setMinWidth(80);
            lblNom.setMaxWidth(80);

            Label lblCin = new Label(user.getCin());
            lblCin.setMinWidth(80);
            lblCin.setMaxWidth(80);

            Label lblAdress = new Label(user.getAdresse());
            lblAdress.setMinWidth(80);
            lblAdress.setMaxWidth(80);

            Label lblEmail = new Label(user.getEmail());
            lblEmail.setMinWidth(130);
            lblEmail.setMaxWidth(130);

            Label lblRole = new Label(user.getRole().toString());
            lblRole.setMinWidth(105);
            lblRole.setMaxWidth(105);

            Label lblVerified = new Label(user.isVerified() ? "Yes" : "No");
            lblVerified.setMinWidth(80);
            lblVerified.setMaxWidth(80);

            Label lblTransport = new Label(user.getType_vehicule().toString());
            lblTransport.setMinWidth(80);
            lblTransport.setMaxWidth(80);

            userRow.getChildren().addAll(lblPrenom, lblNom, lblCin, lblAdress, lblEmail, lblRole, lblVerified, lblTransport);

            vListUsers.getChildren().add(userRow);
        }
    }


    @FXML
    private HBox headerhb;

    @FXML
    private HBox hbActions;

    @FXML
    private AnchorPane anCategories;

    @FXML
    private AnchorPane anCoverageArea;

    @FXML
    private AnchorPane anLogout;

    @FXML
    private AnchorPane anOrder;

    @FXML
    private AnchorPane anPendingUsers;

    @FXML
    private AnchorPane anRiders;

    @FXML
    private TextField anSearch;

    @FXML
    private AnchorPane anUsers;

    @FXML
    private ImageView imLogo;

    @FXML
    private VBox vListUsers;

    @FXML
    private HBox hbHedha;

    @FXML
    private void navigateToHome() {
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeAdmin.fxml"));
            Scene signUpScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) imLogo.getScene().getWindow();
            stage.setScene(signUpScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading SignUp.fxml.");
        }
    }

    @FXML
    public void normalEffect(javafx.scene.input.MouseEvent event) {
        ((AnchorPane) event.getSource()).setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

    }

    @FXML
    public void hoverEffect(javafx.scene.input.MouseEvent event) {
        ((AnchorPane) event.getSource()).setStyle("-fx-background-color: lightgrey; -fx-cursor: hand;");

    }

    @FXML
    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Scene SignInScene = new Scene(loader.load());

            Stage stage = (Stage) anLogout.getScene().getWindow();
            stage.setScene(SignInScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading SignUp.fxml.");
        }
    }
}
