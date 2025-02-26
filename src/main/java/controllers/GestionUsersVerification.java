package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;
import services.UsersVerification;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GestionUsersVerification implements Initializable {

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
    private HBox hbHedha;

    @FXML
    private HBox headerhb;

    @FXML
    private ImageView imLogo;

    @FXML
    private Label lblAdress;

    @FXML
    private Label lblCin;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblNom;

    @FXML
    private Label lblPrenom;

    @FXML
    private Label lblRole;

    @FXML
    private Label lblTransport;

    @FXML
    private VBox vListUsers;


    private UsersVerification uv = new UsersVerification();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Show(null);

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
        List<User> usersList = uv.getUnverifiedUsers();

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

            String transport = (user.getType_vehicule() != null) ? user.getType_vehicule().toString() : "No transport";
            Label lblTransport = new Label(transport);
            lblTransport.setMinWidth(80);
            lblTransport.setMaxWidth(80);

            Button acceptButton = new Button("✅");
            acceptButton.setStyle("-fx-background-color: transparent; -fx-border-color: green; -fx-border-radius: 10; -fx-border-width: 2;");
            acceptButton.setOnAction(event -> {
                showConfirmationDialog("Accept", "Are you sure you want to give access to this user: "+user.getNom()+" ?", () -> {
                    uv.acceptUser(user.getId());
                    loadUsers();
                });
            });

            Button refuseButton = new Button("❌");
            refuseButton.setStyle("-fx-background-color: transparent; -fx-border-color: red; -fx-border-radius: 10; -fx-border-width: 2;");
            refuseButton.setOnAction(event -> {
                showConfirmationDialog("Refuse", "Are you sure you want to delete this user:  "+user.getNom()+" ?", () -> {
                    uv.refuseUser(user.getId());
                    loadUsers();
                    });
            });

            userRow.getChildren().addAll(lblPrenom, lblNom, lblCin, lblAdress, lblEmail, lblRole, lblTransport, acceptButton, refuseButton);
            vListUsers.getChildren().add(userRow);
        }
    }


    private void showConfirmationDialog(String title, String message, Runnable onConfirm) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonYes, buttonCancel);
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonYes) {
                onConfirm.run();
            }
        });
    }

    @FXML
    public void loadUsers() {
        System.out.println("Chargement des utilisateurs...");

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

        Label lblHeaderTransport = new Label("Transport");
        lblHeaderTransport.setMinWidth(80);
        lblHeaderTransport.setMaxWidth(80);
        lblHeaderTransport.setStyle("-fx-text-fill: black;");

        headerRow.getChildren().addAll(lblHeaderPrenom, lblHeaderNom, lblHeaderCin, lblHeaderAdress, lblHeaderEmail, lblHeaderRole,  lblHeaderTransport);

        vListUsers.getChildren().add(headerRow);

        List<User> usersList = uv.getUnverifiedUsers();

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

            String transport = (user.getType_vehicule() != null) ? user.getType_vehicule().toString() : "No transport";
            Label lblTransport = new Label(transport);
            lblTransport.setMinWidth(80);
            lblTransport.setMaxWidth(80);

            // Create Accept Button
            Button acceptButton = new Button("V");
            acceptButton.setStyle("-fx-background-color: transparent; -fx-border-color: green; -fx-border-radius: 10; -fx-border-width: 2;");
            acceptButton.setOnAction(event -> {
                showConfirmationDialog("Accepter", "Voulez-vous vraiment accepter cet utilisateur ?", () -> {
                    uv.acceptUser(user.getId());
                    loadUsers();
                });
            });

            // Create Refuse Button
            Button refuseButton = new Button("X");
            refuseButton.setStyle("-fx-background-color: transparent; -fx-border-color: red; -fx-border-radius: 10; -fx-border-width: 2;");
            refuseButton.setOnAction(event -> {
                showConfirmationDialog("Refuser", "Voulez-vous vraiment refuser cet utilisateur ?", () -> {
                    uv.refuseUser(user.getId());
                    loadUsers();
                });
            });

            userRow.getChildren().addAll(lblPrenom, lblNom, lblCin, lblAdress, lblEmail, lblRole, lblTransport, acceptButton, refuseButton);
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

        Label lblHeaderTransport = new Label("Transport");
        lblHeaderTransport.setMinWidth(80);
        lblHeaderTransport.setMaxWidth(80);
        lblHeaderTransport.setStyle("-fx-text-fill: black;");

        headerRow.getChildren().addAll(lblHeaderPrenom, lblHeaderNom, lblHeaderCin, lblHeaderAdress, lblHeaderEmail, lblHeaderRole,  lblHeaderTransport);
        vListUsers.getChildren().add(headerRow);

        List<User> usersList = uv.search(criteria);

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

            String transport = (user.getType_vehicule() != null) ? user.getType_vehicule().toString() : "No transport";
            Label lblTransport = new Label(transport);
            lblTransport.setMinWidth(80);
            lblTransport.setMaxWidth(80);

            // Create Accept Button
            Button acceptButton = new Button("V");
            acceptButton.setStyle("-fx-background-color: transparent; -fx-border-color: green; -fx-border-radius: 10; -fx-border-width: 2;");
            acceptButton.setOnAction(event -> {
                showConfirmationDialog("Accepter", "Voulez-vous vraiment accepter cet utilisateur ?", () -> {
                    uv.acceptUser(user.getId());
                    loadUsers();
                });
            });

            // Create Refuse Button
            Button refuseButton = new Button("X");
            refuseButton.setStyle("-fx-background-color: transparent; -fx-border-color: red; -fx-border-radius: 10; -fx-border-width: 2;");
            refuseButton.setOnAction(event -> {
                showConfirmationDialog("Refuser", "Voulez-vous vraiment refuser cet utilisateur ?", () -> {
                    uv.refuseUser(user.getId());
                    loadUsers();
                });
            });

            userRow.getChildren().addAll(lblPrenom, lblNom, lblCin, lblAdress, lblEmail, lblRole, lblTransport, acceptButton, refuseButton);
            vListUsers.getChildren().add(userRow);
        }
    }

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
    private void NavigateToGestionUsers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionUtilisateurs.fxml"));
            Scene GestionUtilisateursScene = new Scene(loader.load());

            Stage stage = (Stage) anUsers.getScene().getWindow();
            stage.setScene(GestionUtilisateursScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading SignUp.fxml.");
        }
    }
    @FXML
    private void NavigateToGestionCategorie() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/avoir.fxml"));
            Scene GestionCategorieScene = new Scene(loader.load());

            Stage stage = (Stage) anCategories.getScene().getWindow();
            stage.setScene(GestionCategorieScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading gestionategorie.fxml.");
        }
    }
    @FXML
    private void navigateToZones() {
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionZoneAdmin.fxml"));
            Scene signUpScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) anCoverageArea.getScene().getWindow();
            stage.setScene(signUpScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading SignUp.fxml.");
        }
    }
    @FXML
    private void navigateToCommandes() {
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/commandeAdmin.fxml"));
            Scene signUpScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) anOrder.getScene().getWindow();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
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
