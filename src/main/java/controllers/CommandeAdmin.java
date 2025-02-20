package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Commande;
import services.CrudCommande;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class CommandeAdmin implements Initializable {
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
    private Label lblArrival;

    @FXML
    private Label lblCreatedby;

    @FXML
    private Label lblCreation;

    @FXML
    private Label lblDeparture;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblTransport;

    @FXML
    private Label lblType;

    @FXML
    private VBox vListCommande;





    @FXML
    void logout(MouseEvent event) {

    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }


    private CrudCommande su = new CrudCommande();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Afficher tous les utilisateurs au démarrage
        Show(null);

        // Ajouter un écouteur sur le champ de recherche
        anSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // Si le champ de recherche est vide, afficher tous les utilisateurs
                loadCommandes();
            } else {
                // Sinon, effectuer une recherche
                searchCommandes(newValue);
            }
        });
    }

    @FXML
    void Show(ActionEvent showEvent) {
        hbHedha.getChildren().clear();

        List<Commande> commandeList = su.getAll();
        System.out.println(commandeList.size());
        for (Commande commande : commandeList) {
            HBox commandeRow = new HBox(4);
            commandeRow.setPrefHeight(32.0);
            commandeRow.setPrefWidth(765.0);

            Label lblDeparture = new Label(commande.getAdresse_dep());
            lblDeparture.setMinWidth(80);
            lblDeparture.setMaxWidth(80);

            Label lblArrival = new Label(commande.getAdresse_arr());
            lblArrival.setMinWidth(80);
            lblArrival.setMaxWidth(80);

            Label lblType = new Label(commande.getType_livraison());
            lblType.setMinWidth(80);
            lblType.setMaxWidth(80);

            Label lblCreation = new Label(commande.getHoraire().toString());
            lblCreation.setMinWidth(125);
            lblCreation.setMaxWidth(0);

            Label lblStatus = new Label(commande.getStatut().toString());
            lblStatus.setMinWidth(130);
            lblStatus.setMaxWidth(130);

            Label lblCreatedBy = new Label(String.valueOf(commande.getCreated_by()));
            lblCreatedBy.setMinWidth(105);
            lblCreatedBy.setMaxWidth(105);

            commandeRow.getChildren().addAll(lblDeparture,lblArrival,lblType,lblCreation,lblStatus,lblCreatedBy);

            commandeRow.setOnMouseClicked(event -> showCommandeDetailsPopup(commande));

            vListCommande.getChildren().add(commandeRow);
        }
    }

    @FXML
    private void showCommandeDetailsPopup(Commande commande) {
        System.out.println("Commande selectionée: " + commande.getId_Commande());

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de la commande");
        alert.setHeaderText("Informations sur commande " + commande.getId_Commande());
        alert.setContentText("Departure address : " + commande.getAdresse_dep() + "\n" +
                "Arrival address : " + commande.getAdresse_arr() + "\n" +
                "Type : " + commande.getType_livraison() + "\n" +
                "Created at : " +commande.getHoraire() + "\n" +
                "Status" + commande.getStatut()+ "\n" +
                "Created By : " + commande.getCreated_by());

        ButtonType updateButton = new ButtonType("Update");
        ButtonType deleteButton = new ButtonType("Delete");

        alert.getButtonTypes().setAll(updateButton, deleteButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == updateButton) {
                showUpdatePopup(commande);
                System.out.println("Update informations of command.");
            } else if (response == deleteButton) {
                // Afficher une pop-up de confirmation pour la suppression
                javafx.scene.control.Alert confirmationAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Delete Confirmation");
                confirmationAlert.setHeaderText("Do you really want to delete this command ?");
                confirmationAlert.setContentText("There's no going back .");

                // Ajouter les boutons de confirmation
                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                confirmationAlert.showAndWait().ifPresent(confirmationResponse -> {
                    if (confirmationResponse == yesButton) {
                        // Effectuer la suppression
                        System.out.println("aaaaa"+commande.getId_Commande());
                        su.delete(commande.getId_Commande());
                        System.out.println("Command deleted.");
                        loadCommandes();
                    } else {
                        System.out.println("Suppression canceled.");
                    }
                });
            }
        });
    }

    @FXML
    private void showUpdatePopup(Commande commande) {
        Dialog<Commande> dialog = new Dialog<>();
        dialog.setTitle("Command Update");
        dialog.setHeaderText("Update the informations of this command");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField DepartureField = new TextField(commande.getAdresse_dep());
        TextField ArrivalField = new TextField(commande.getAdresse_arr());
        TextField TypeField = new TextField(commande.getType_livraison());
        TextField CreationField = new TextField(commande.getHoraire().toString());
        TextField StatusField = new TextField(commande.getStatut().toString());
        TextField CreatedByField = new TextField(String.valueOf(commande.getCreated_by()));
        //TextField transportField = new TextField(user.getType_vehicule().toString());

        grid.add(new Label("Departure"), 0, 0);
        grid.add(DepartureField, 1, 0);
        grid.add(new Label("Arrival"), 0, 1);
        grid.add(ArrivalField, 1, 1);
        grid.add(new Label("Type"), 0, 2);
        grid.add(TypeField, 1, 2);
        grid.add(new Label("CreatedAt"), 0, 3);
        grid.add(CreationField, 1, 3);
        grid.add(new Label("Status"), 0, 4);
        grid.add(StatusField, 1, 4);
        grid.add(new Label("CreatedBy:"), 0, 5);
        grid.add(CreatedByField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                System.out.println("aaaaaaaa"+commande.getId_Commande());
                // Update the user object with the new values
                commande.setAdresse_dep(DepartureField.getText());
                commande.setAdresse_arr(ArrivalField.getText());;
                commande.setType_livraison(TypeField.getText());
                //commande.setHoraire((Timestamp).CreationField.getText());
                commande.setStatut(models.statutlCommande.valueOf(StatusField.getText()));
                commande.setCreated_by(Integer.parseInt((CreatedByField.getText())));

                // Save the updated user to the database
                su.update(commande);

                // Refresh the user list in the UI
                loadCommandes(); // Call loadCommandes to refresh the list

                return commande;
            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    public void loadCommandes() {
        System.out.println("Chargement des commandes...");

        // Nettoyer la liste actuelle des commandes
        vListCommande.getChildren().clear();

        HBox headerRow = new HBox(4);
        headerRow.setPrefHeight(32.0);
        headerRow.setPrefWidth(765.0);

        headerRow.setStyle("-fx-background-color: #398c3e; -fx-padding: 10px;");

        Label lblHeaderPrenom = new Label("Departure");
        lblHeaderPrenom.setMinWidth(80);
        lblHeaderPrenom.setMaxWidth(80);
        lblHeaderPrenom.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderNom = new Label("Arrival");
        lblHeaderNom.setMinWidth(80);
        lblHeaderNom.setMaxWidth(80);
        lblHeaderNom.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderCin = new Label("Type");
        lblHeaderCin.setMinWidth(80);
        lblHeaderCin.setMaxWidth(80);
        lblHeaderCin.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderAdress = new Label("Creation time");
        lblHeaderAdress.setMinWidth(80);
        lblHeaderAdress.setMaxWidth(80);
        lblHeaderAdress.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderEmail = new Label("Status");
        lblHeaderEmail.setMinWidth(130);
        lblHeaderEmail.setMaxWidth(130);
        lblHeaderEmail.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderRole = new Label("Created by");
        lblHeaderRole.setMinWidth(105);
        lblHeaderRole.setMaxWidth(105);
        lblHeaderRole.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        headerRow.getChildren().addAll(lblHeaderPrenom, lblHeaderNom, lblHeaderCin, lblHeaderAdress, lblHeaderEmail, lblHeaderRole);

        vListCommande.getChildren().add(headerRow);

        List<Commande> commandeList = su.getAll();


        for (Commande commande : commandeList) {
            HBox commandeRow = new HBox(4);
            commandeRow.setPrefHeight(32.0);
            commandeRow.setPrefWidth(765.0);

            Label lblDeparture = new Label(commande.getAdresse_dep());
            lblDeparture.setMinWidth(80);
            lblDeparture.setMaxWidth(80);

            Label lblArrival = new Label(commande.getAdresse_arr());
            lblArrival.setMinWidth(80);
            lblArrival.setMaxWidth(80);

            Label lblType = new Label(commande.getType_livraison());
            lblType.setMinWidth(80);
            lblType.setMaxWidth(80);

            Label lblCreation = new Label(commande.getHoraire().toString());
            lblCreation.setMinWidth(130);
            lblCreation.setMaxWidth(130);

            Label lblStatus = new Label(commande.getStatut().toString());
            lblStatus.setMinWidth(105);
            lblStatus.setMaxWidth(105);

            Label lblCreatedBy = new Label(String.valueOf(commande.getCreated_by()));
            lblCreatedBy.setMinWidth(80);
            lblCreatedBy.setMaxWidth(80);



            commandeRow.getChildren().addAll(lblDeparture,lblArrival,lblType,lblCreation,lblStatus,lblCreatedBy);

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> {
                javafx.scene.control.Alert confirmationAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Delete Confirmation");
                confirmationAlert.setHeaderText("Do you want to delete this command ?");
                confirmationAlert.setContentText("There's no going back.");

                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                confirmationAlert.showAndWait().ifPresent(confirmationResponse -> {
                    if (confirmationResponse == yesButton) {
                        // Effectuer la suppression
                        su.delete(commande.getId_Commande());
                        System.out.println("Commande supprimé.");
                        loadCommandes();
                    } else {
                        System.out.println("Suppression annulée.");
                    }
                });
            });

            commandeRow.getChildren().add(deleteButton);

            commandeRow.setOnMouseClicked(event -> showCommandeDetailsPopup(commande));

            vListCommande.getChildren().add(commandeRow);
        }
    }

    @FXML
    private void searchCommandes(String criteria) {
        System.out.println("Recherche des commande pour le critère : " + criteria);

        vListCommande.getChildren().clear();

        HBox headerRow = new HBox(4);
        headerRow.setPrefHeight(32.0);
        headerRow.setPrefWidth(765.0);
        headerRow.setStyle("-fx-background-color: #398c3e; -fx-padding: 10px;");

        Label lblHeaderPrenom = new Label("Departure");
        lblHeaderPrenom.setMinWidth(80);
        lblHeaderPrenom.setMaxWidth(80);
        lblHeaderPrenom.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderNom = new Label("Arrival");
        lblHeaderNom.setMinWidth(80);
        lblHeaderNom.setMaxWidth(80);
        lblHeaderNom.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderCin = new Label("Type");
        lblHeaderCin.setMinWidth(80);
        lblHeaderCin.setMaxWidth(80);
        lblHeaderCin.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderAdress = new Label("Creation time");
        lblHeaderAdress.setMinWidth(125);
        lblHeaderAdress.setMaxWidth(125);
        lblHeaderAdress.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderEmail = new Label("Status");
        lblHeaderEmail.setMinWidth(130);
        lblHeaderEmail.setMaxWidth(130);
        lblHeaderEmail.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderRole = new Label("Created by");
        lblHeaderRole.setMinWidth(105);
        lblHeaderRole.setMaxWidth(105);
        lblHeaderRole.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        headerRow.getChildren().addAll(lblHeaderPrenom, lblHeaderNom, lblHeaderCin, lblHeaderAdress, lblHeaderEmail, lblHeaderRole);
        vListCommande.getChildren().add(headerRow);

        List<Commande> commandeList = su.search(criteria);
        //System.out.println(commandeList);
        for (Commande commande : commandeList) {
            HBox commandeRow = new HBox(4);
            commandeRow.setPrefHeight(32.0);
            commandeRow.setPrefWidth(765.0);
            commandeRow.setStyle("-fx-padding: 10px;");

            Label lblDeparture = new Label(commande.getAdresse_dep());
            lblDeparture.setMinWidth(80);
            lblDeparture.setMaxWidth(80);

            Label lblArrival = new Label(commande.getAdresse_arr());
            lblArrival.setMinWidth(80);
            lblArrival.setMaxWidth(80);

            Label lblType = new Label(commande.getType_livraison());
            lblType.setMinWidth(80);
            lblType.setMaxWidth(80);

            Label lblCreation = new Label(commande.getHoraire().toString());
            lblCreation.setMinWidth(125);
            lblCreation.setMaxWidth(125);

            Label lblStatus = new Label(String.valueOf(commande.getStatut()));
            lblStatus.setMinWidth(130);
            lblStatus.setMaxWidth(130);

            Label lblCreatedBy = new Label(String.valueOf(commande.getCreated_by()));
            lblCreatedBy.setMinWidth(105);
            lblCreatedBy.setMaxWidth(105);


            commandeRow.getChildren().addAll(lblDeparture,lblArrival,lblType,lblCreation,lblStatus,lblCreatedBy);

            vListCommande.getChildren().add(commandeRow);
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
