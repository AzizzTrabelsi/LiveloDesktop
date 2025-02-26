package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Trajet;
import models.Zone;
import services.CrudZone;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.Arrays;

public class GestionZoneAdmin implements Initializable{
    private CrudZone su = new CrudZone();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Afficher toutes les zones au démarrage
        Show(null);

        // Ajouter un écouteur sur le champ de recherche
       anSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // Si le champ de recherche est vide, afficher toutes les zones
                loadZones();
            } else {
                // Sinon, effectuer une recherche
                searchZones(newValue);
            }
        });
    }
    @FXML
    void Show(ActionEvent showEvent) {
        hbHedha.getChildren().clear();

        List<Zone> zoneList = su.getAll();

        for (Zone zone : zoneList) {
            HBox zoneRow = new HBox(4);
            zoneRow.setPrefHeight(32.0);
            zoneRow.setPrefWidth(765.0);

            Label lblName = new Label(zone.getNom());
            lblName.setMinWidth(80);
            lblName.setMaxWidth(80);

            Label lbllatitude = new Label(String.valueOf(zone.getLatitudeCentre()));
            lbllatitude.setMinWidth(80);
            lbllatitude.setMaxWidth(80);

            Label lbllongitude = new Label(String.valueOf(zone.getLongitudeCentre()));
            lbllongitude.setMinWidth(80);
            lbllongitude.setMaxWidth(80);

            Label lblrayon = new Label(String.valueOf(zone.getRayon()));
            lblrayon.setMinWidth(80);
            lblrayon.setMaxWidth(80);

            Label lbluser = new Label(String.valueOf(zone.getIdUser()));
            lbluser.setMinWidth(130);
            lbluser.setMaxWidth(130);

            Label lbllivraison = new Label(String.valueOf(zone.getIdLivraison()));
            lbllivraison.setMinWidth(105);
            lbllivraison.setMaxWidth(105);

            Label lblmax = new Label(String.valueOf(zone.getMax()));
            lblmax.setMinWidth(80);
            lblmax.setMaxWidth(80);

            zoneRow.getChildren().addAll(lblName, lbllatitude, lbllongitude, lblrayon, lbluser, lbllivraison, lblmax);

            zoneRow.setOnMouseClicked(event -> showZoneDetailsPopup(zone));

            vListZones.getChildren().add(zoneRow);
        }
    }


    @FXML
    private void showZoneDetailsPopup(Zone zone) {
        System.out.println("Zone sélectionnée : " + zone.getNom());

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de la Zone");
        alert.setHeaderText("Informations sur la zone : " + zone.getNom());
        alert.setContentText("Latitude : " + zone.getLatitudeCentre() + "\n" +
                "Longitude : " + zone.getLongitudeCentre() + "\n" +
                "Rayon : " + zone.getRayon() + " km\n" +
                "Utilisateur associé : " + zone.getIdUser() + "\n" +
                "Livraison associée : " + zone.getIdLivraison() + "\n" +
                "Max : " + zone.getMax());

        // Add new button for "Show Trajets"
        ButtonType showTrajetsButton = new ButtonType("Afficher Trajets");
        ButtonType updateButton = new ButtonType("Mettre à jour");
        ButtonType deleteButton = new ButtonType("Supprimer");
        ButtonType closeButton = new ButtonType("Fermer", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(showTrajetsButton, updateButton, deleteButton, closeButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == showTrajetsButton) {
                // Navigate to AfficherTrajets.fxml and pass the idZone
                navigateToAfficherTrajets(zone.getIdZone());
            } else if (response == updateButton) {
                showUpdateZonePopup(zone);
                System.out.println("Mettre à jour les informations de la zone.");
            } else if (response == deleteButton) {
                javafx.scene.control.Alert confirmationAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation de la suppression");
                confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette zone ?");
                confirmationAlert.setContentText("Cette action est irréversible.");

                ButtonType yesButton = new ButtonType("Oui");
                ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

                confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                confirmationAlert.showAndWait().ifPresent(confirmationResponse -> {
                    if (confirmationResponse == yesButton) {
                        su.delete(zone.getIdZone());
                        System.out.println("Zone supprimée.");
                        loadZones();
                    } else {
                        System.out.println("Suppression annulée.");
                    }
                });
            } else if (response == closeButton) {
                // Fermer la popup
                System.out.println("Popup fermée.");
            }
        });
    }

    private void navigateToAfficherTrajets(int idZone) {
        try {
            // Load the AfficherTrajets.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTrajets.fxml"));
            Parent root = loader.load();

            // Get the controller for AfficherTrajets.fxml
            AfficherTrajets AfficherTrajets = loader.getController();

            // Pass the idZone to the AfficherTrajetsController
            AfficherTrajets.setIdZone(idZone);

            // Create a new stage (window) to display AfficherTrajets.fxml
            Stage stage = new Stage();
            stage.setTitle("Trajets de la Zone");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void showUpdateZonePopup(Zone zone) {
        Dialog<Zone> dialog = new Dialog<>();
        dialog.setTitle("Mettre à jour la zone");
        dialog.setHeaderText("Modifier les informations de la zone");

        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomField = new TextField(zone.getNom());
        TextField latitudeField = new TextField(String.valueOf(zone.getLatitudeCentre()));
        TextField longitudeField = new TextField(String.valueOf(zone.getLongitudeCentre()));
        TextField rayonField = new TextField(String.valueOf(zone.getRayon()));
        TextField idUserField = new TextField(String.valueOf(zone.getIdUser()));
        TextField idLivraisonField = new TextField(String.valueOf(zone.getIdLivraison()));
        TextField maxField = new TextField(String.valueOf(zone.getMax()));

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Latitude:"), 0, 1);
        grid.add(latitudeField, 1, 1);
        grid.add(new Label("Longitude:"), 0, 2);
        grid.add(longitudeField, 1, 2);
        grid.add(new Label("Rayon (km):"), 0, 3);
        grid.add(rayonField, 1, 3);
        grid.add(new Label("ID Utilisateur:"), 0, 4);
        grid.add(idUserField, 1, 4);
        grid.add(new Label("ID Livraison:"), 0, 5);
        grid.add(idLivraisonField, 1, 5);
        grid.add(new Label("Max:"), 0, 6);
        grid.add(maxField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                zone.setNom(nomField.getText());
                zone.setLatitudeCentre(Float.parseFloat(latitudeField.getText()));
                zone.setLongitudeCentre(Float.parseFloat(longitudeField.getText()));
                zone.setRayon(Float.parseFloat(rayonField.getText()));
                zone.setIdUser(Integer.parseInt(idUserField.getText()));
                zone.setIdLivraison(Integer.parseInt(idLivraisonField.getText()));
                zone.setMax(Integer.parseInt(maxField.getText()));

                // Sauvegarde dans la base de données
                su.update(zone);

                // Rafraîchir la liste des zones dans l'interface utilisateur
                loadZones();

                return zone;
            }
            return null;
        });

        dialog.showAndWait();
    }


    @FXML
    public void loadZones() {
        System.out.println("Chargement des zones...");

        // Clear the current list of zones
        vListZones.getChildren().clear();

        // Create header row
        HBox headerRow = new HBox(4);
        headerRow.setPrefHeight(32.0);
        headerRow.setPrefWidth(765.0);

        headerRow.setStyle("-fx-background-color: #398c3e; -fx-padding: 10px;");

        Label lblHeaderNom = new Label("Nom");
        lblHeaderNom.setMinWidth(80);
        lblHeaderNom.setMaxWidth(80);
        lblHeaderNom.setStyle("-fx-text-fill: black;");

        Label lblHeaderLatitude = new Label("Latitude");
        lblHeaderLatitude.setMinWidth(80);
        lblHeaderLatitude.setMaxWidth(80);
        lblHeaderLatitude.setStyle("-fx-text-fill: black;");

        Label lblHeaderLongitude = new Label("Longitude");
        lblHeaderLongitude.setMinWidth(80);
        lblHeaderLongitude.setMaxWidth(80);
        lblHeaderLongitude.setStyle("-fx-text-fill: black;");

        Label lblHeaderRayon = new Label("Rayon");
        lblHeaderRayon.setMinWidth(80);
        lblHeaderRayon.setMaxWidth(80);
        lblHeaderRayon.setStyle("-fx-text-fill: black;");

        Label lblHeaderUser = new Label("ID Utilisateur");
        lblHeaderUser.setMinWidth(130);
        lblHeaderUser.setMaxWidth(130);
        lblHeaderUser.setStyle("-fx-text-fill: black;");

        Label lblHeaderLivraison = new Label("ID Livraison");
        lblHeaderLivraison.setMinWidth(105);
        lblHeaderLivraison.setMaxWidth(105);
        lblHeaderLivraison.setStyle("-fx-text-fill: black;");

        Label lblHeaderMax = new Label("Max");
        lblHeaderMax.setMinWidth(80);
        lblHeaderMax.setMaxWidth(80);
        lblHeaderMax.setStyle("-fx-text-fill: black;");

        headerRow.getChildren().addAll(lblHeaderNom, lblHeaderLatitude, lblHeaderLongitude, lblHeaderRayon, lblHeaderUser, lblHeaderLivraison, lblHeaderMax);

        vListZones.getChildren().add(headerRow);

        // Get all zones from the service
        List<Zone> zoneList = su.getAll();

        for (Zone zone : zoneList) {
            HBox zoneRow = new HBox(4);
            zoneRow.setPrefHeight(32.0);
            zoneRow.setPrefWidth(765.0);

            Label lblName = new Label(zone.getNom());
            lblName.setMinWidth(80);
            lblName.setMaxWidth(80);

            Label lblLatitude = new Label(String.valueOf(zone.getLatitudeCentre()));
            lblLatitude.setMinWidth(80);
            lblLatitude.setMaxWidth(80);

            Label lblLongitude = new Label(String.valueOf(zone.getLongitudeCentre()));
            lblLongitude.setMinWidth(80);
            lblLongitude.setMaxWidth(80);

            Label lblRayon = new Label(String.valueOf(zone.getRayon()));
            lblRayon.setMinWidth(80);
            lblRayon.setMaxWidth(80);

            Label lblUser = new Label(String.valueOf(zone.getIdUser()));
            lblUser.setMinWidth(130);
            lblUser.setMaxWidth(130);

            Label lblLivraison = new Label(String.valueOf(zone.getIdLivraison()));
            lblLivraison.setMinWidth(105);
            lblLivraison.setMaxWidth(105);

            Label lblMax = new Label(String.valueOf(zone.getMax()));
            lblMax.setMinWidth(80);
            lblMax.setMaxWidth(80);

            zoneRow.getChildren().addAll(lblName, lblLatitude, lblLongitude, lblRayon, lblUser, lblLivraison, lblMax);

            // Event listener to show details on click
            zoneRow.setOnMouseClicked(event -> showZoneDetailsPopup(zone));

            vListZones.getChildren().add(zoneRow);
        }
    }


    @FXML
    private void searchZones(String criteria) {
        System.out.println("Recherche des zones pour le critère : " + criteria);

        vListZones.getChildren().clear();

        // Création de la ligne d'en-tête
        HBox headerRow = new HBox(4);
        headerRow.setPrefHeight(32.0);
        headerRow.setPrefWidth(765.0);
        headerRow.setStyle("-fx-background-color: #398c3e; -fx-padding: 10px;");

        Label lblHeaderName = new Label("Nom");
        lblHeaderName.setMinWidth(80);
        lblHeaderName.setMaxWidth(80);
        lblHeaderName.setStyle("-fx-text-fill: black;");

        Label lblHeaderLatitude = new Label("Latitude");
        lblHeaderLatitude.setMinWidth(80);
        lblHeaderLatitude.setMaxWidth(80);
        lblHeaderLatitude.setStyle("-fx-text-fill: black;");

        Label lblHeaderLongitude = new Label("Longitude");
        lblHeaderLongitude.setMinWidth(80);
        lblHeaderLongitude.setMaxWidth(80);
        lblHeaderLongitude.setStyle("-fx-text-fill: black;");

        Label lblHeaderRayon = new Label("Rayon");
        lblHeaderRayon.setMinWidth(80);
        lblHeaderRayon.setMaxWidth(80);
        lblHeaderRayon.setStyle("-fx-text-fill: black;");

        Label lblHeaderUser = new Label("ID Utilisateur");
        lblHeaderUser.setMinWidth(130);
        lblHeaderUser.setMaxWidth(130);
        lblHeaderUser.setStyle("-fx-text-fill: black;");

        Label lblHeaderLivraison = new Label("ID Livraison");
        lblHeaderLivraison.setMinWidth(105);
        lblHeaderLivraison.setMaxWidth(105);
        lblHeaderLivraison.setStyle("-fx-text-fill: black;");

        Label lblHeaderMax = new Label("Max");
        lblHeaderMax.setMinWidth(80);
        lblHeaderMax.setMaxWidth(80);
        lblHeaderMax.setStyle("-fx-text-fill: black;");

        headerRow.getChildren().addAll(lblHeaderName, lblHeaderLatitude, lblHeaderLongitude, lblHeaderRayon, lblHeaderUser, lblHeaderLivraison, lblHeaderMax);
        vListZones.getChildren().add(headerRow);

        // Récupération des zones qui correspondent aux critères de recherche
        List<Zone> zoneList = su.search(criteria);

        for (Zone zone : zoneList) {
            HBox zoneRow = new HBox(4);
            zoneRow.setPrefHeight(32.0);
            zoneRow.setPrefWidth(765.0);
            zoneRow.setStyle("-fx-padding: 10px;");

            Label lblName = new Label(zone.getNom());
            lblName.setMinWidth(80);
            lblName.setMaxWidth(80);

            Label lbllatitude = new Label(String.valueOf(zone.getLatitudeCentre()));
            lbllatitude.setMinWidth(80);
            lbllatitude.setMaxWidth(80);

            Label lbllongitude = new Label(String.valueOf(zone.getLongitudeCentre()));
            lbllongitude.setMinWidth(80);
            lbllongitude.setMaxWidth(80);

            Label lblrayon = new Label(String.valueOf(zone.getRayon()));
            lblrayon.setMinWidth(80);
            lblrayon.setMaxWidth(80);

            Label lbluser = new Label(String.valueOf(zone.getIdUser()));
            lbluser.setMinWidth(130);
            lbluser.setMaxWidth(130);

            Label lbllivraison = new Label(String.valueOf(zone.getIdLivraison()));
            lbllivraison.setMinWidth(105);
            lbllivraison.setMaxWidth(105);

            Label lblmax = new Label(String.valueOf(zone.getMax()));
            lblmax.setMinWidth(80);
            lblmax.setMaxWidth(80);

            zoneRow.getChildren().addAll(lblName, lbllatitude, lbllongitude, lblrayon, lbluser, lbllivraison, lblmax);

            vListZones.getChildren().add(zoneRow);
        }
    }

    @FXML
    void AddZone(ActionEvent event) {
        try {
            // Récupération des valeurs du formulaire
            String nom = anName.getText();
            String latitudeCentreStr = anLatitude.getText();
            String longitudeCentreStr = anLongitude.getText();
            String rayonStr = anRayon.getText();
            String idUserStr = AnUserN.getText();
            String idLivraisonStr = anLivraisonN.getText();
            String maxStr = anMax.getText();

            // Validation des champs obligatoires
            if (nom.isEmpty() || latitudeCentreStr.isEmpty() || longitudeCentreStr.isEmpty() || rayonStr.isEmpty() ||
                    idUserStr.isEmpty() || idLivraisonStr.isEmpty() || maxStr.isEmpty()) {
                showErrorAlert("All fields are required.");
                return;
            }

            // Conversion des valeurs
            float latitudeCentre;
            float longitudeCentre;
            float rayon;
            int idUser;
            int idLivraison;
            int max;
            try {
                latitudeCentre = Float.parseFloat(latitudeCentreStr);
                longitudeCentre = Float.parseFloat(longitudeCentreStr);
                rayon = Float.parseFloat(rayonStr);
                idUser = Integer.parseInt(idUserStr);
                idLivraison = Integer.parseInt(idLivraisonStr);
                max = Integer.parseInt(maxStr);
            } catch (NumberFormatException e) {
                showErrorAlert("Veuillez entrer des nombres valides.");
                return;
            }


            // Création de l'objet Zone
            Zone zone = new Zone();
            zone.setNom(nom);
            zone.setLatitudeCentre(latitudeCentre);
            zone.setLongitudeCentre(longitudeCentre);
            zone.setRayon(rayon);
            zone.setIdUser(idUser);
            zone.setIdLivraison(idLivraison);
            zone.setMax(max);

            // Ajout à la base de données
            su.add(zone);

            showSuccessAlert("Zone has been added successfully.");
            loadZones(); // Recharger les zones dans l'interface

        } catch (NumberFormatException e) {
            showErrorAlert("Invalid number format in one of the fields.");
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Oops, couldn't add the Zone.");
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
    private Button AddButton;

    @FXML
    private TextField AnUserN;

    @FXML
    private AnchorPane anCategories;

    @FXML
    private AnchorPane anCoverageArea;

    @FXML
    private TextField anLatitude;

    @FXML
    private TextField anLivraisonN;

    @FXML
    private AnchorPane anLogout;

    @FXML
    private TextField anLongitude;

    @FXML
    private TextField anMax;

    @FXML
    private TextField anName;

    @FXML
    private AnchorPane anOrder;

    @FXML
    private AnchorPane anPendingUsers;

    @FXML
    private TextField anRayon;

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
    private Label lbllatitude;

    @FXML
    private Label lbllivraison;

    @FXML
    private Label lbllongitude;

    @FXML
    private Label lblmax;

    @FXML
    private Label lblname;

    @FXML
    private Label lblrayon;

    @FXML
    private Label lbluser;

    @FXML
    private VBox vListZones;

    @FXML
    public void hoverEffect(javafx.scene.input.MouseEvent event) {
        ((AnchorPane) event.getSource()).setStyle("-fx-background-color: lightgrey; -fx-cursor: hand;");
    }

    @FXML
    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Scene signInScene = new Scene(loader.load());

            Stage stage = (Stage) anLogout.getScene().getWindow();
            stage.setScene(signInScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de SignIn.fxml.");
        }
    }

    @FXML
    private void navigateToHome() {
        try {
            // Charger le fichier homeZone.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeAdmin.fxml"));
            Scene homeScene = new Scene(loader.load());

            // Obtenir le stage actuel et définir la nouvelle scène
            Stage stage = (Stage) imLogo.getScene().getWindow();
            stage.setScene(homeScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de homeZone.fxml.");
        }
    }

    @FXML
    public void normalEffect(javafx.scene.input.MouseEvent event) {
        ((AnchorPane) event.getSource()).setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
    }

}

