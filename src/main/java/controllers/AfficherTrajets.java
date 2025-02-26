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
import models.Trajet;
import services.CrudTrajet;
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

public class AfficherTrajets implements Initializable{

    private CrudZone su = new CrudZone();
    private CrudTrajet st = new CrudTrajet();
    private int idZone;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Afficher toutes les zones au démarrage
        Show(null);

        // Ajouter un écouteur sur le champ de recherche
        anSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                loadTrajets();
            } else {
                // Sinon, effectuer une recherche
                searchTrajets(newValue);
            }
        });
    }

    public void setIdZone(int idZone) {
        this.idZone = idZone;
        System.out.println("idZone reçu : " + this.idZone);
        loadTrajets();// Debugging: Print the idZone
    }

    @FXML
    void Show(ActionEvent showEvent) {
        // Clear the existing content
        vListTrajets.getChildren().clear();

        // Retrieve the trajets for the selected zone using the idZone
        List<Trajet> trajets = su.getTrajetsByZoneId(idZone); // Call your method to fetch trajets

        // Loop through the trajets and create a row for each one
        for (Trajet trajet : trajets) {
            HBox trajetRow = new HBox(4); // Create a row for each trajet
            trajetRow.setPrefHeight(32.0);
            trajetRow.setPrefWidth(765.0);

            // Add labels for trajet attributes
            Label LblPtDepart = new Label(trajet.getPointDepart());
            lblPtDepart.setMinWidth(120);
            lblPtDepart.setMaxWidth(120);

            Label LblPtArrive = new Label(trajet.getPointArrivee());
            LblPtArrive.setMinWidth(120);
            LblPtArrive.setMaxWidth(120);

            Label LblCommande = new Label(trajet.getPointArrivee());
            LblCommande.setMinWidth(120);
            LblCommande.setMaxWidth(120);

            Label lblLivraison = new Label(trajet.getPointArrivee());
            lblLivraison.setMinWidth(120);
            lblLivraison.setMaxWidth(120);

            Label LblDistance = new Label(String.valueOf(trajet.getDistance()));
            LblDistance.setMinWidth(80);
            LblDistance.setMaxWidth(80);

            Label LblDuree = new Label(String.valueOf(trajet.getDureeEstimee()));
            LblDuree.setMinWidth(80);
            LblDuree.setMaxWidth(80);

            Label LblEtat = new Label(trajet.getEtatTrajet().toString());
            LblEtat.setMinWidth(80);
            LblEtat.setMaxWidth(80);

            // Add all labels to the trajet row
            trajetRow.getChildren().addAll(lblPtDepart, LblPtArrive, LblCommande, lblLivraison, LblDistance, LblDuree, LblEtat);

            // Add the trajet row to the VBox
            vListTrajets.getChildren().add(trajetRow);
        }
    }

    @FXML
    public void loadTrajets() {
        System.out.println("Chargement des trajets...");

        // Clear the current list of zones
        vListTrajets.getChildren().clear();

        // Create header row
        HBox headerRow = new HBox(4);
        headerRow.setPrefHeight(32.0);
        headerRow.setPrefWidth(765.0);

        headerRow.setStyle("-fx-background-color: #398c3e; -fx-padding: 10px;");

        Label lblHeaderPtDepart = new Label("point depart");
        lblHeaderPtDepart.setMinWidth(80);
        lblHeaderPtDepart.setMaxWidth(80);
        lblHeaderPtDepart.setStyle("-fx-text-fill: black;");

        Label LblHeaderPtArrive = new Label("point arrive");
        LblHeaderPtArrive.setMinWidth(80);
        LblHeaderPtArrive.setMaxWidth(80);
        LblHeaderPtArrive.setStyle("-fx-text-fill: black;");

        Label LblHeaderCommande = new Label("commande");
        LblHeaderCommande.setMinWidth(80);
        LblHeaderCommande.setMaxWidth(80);
        LblHeaderCommande.setStyle("-fx-text-fill: black;");

        Label lblHeaderLivraison = new Label("livraison");
        lblHeaderLivraison.setMinWidth(80);
        lblHeaderLivraison.setMaxWidth(80);
        lblHeaderLivraison.setStyle("-fx-text-fill: black;");

        Label LblHeaderDistance = new Label("distance");
        LblHeaderDistance.setMinWidth(130);
        LblHeaderDistance.setMaxWidth(130);
        LblHeaderDistance.setStyle("-fx-text-fill: black;");

        Label LblHeaderDuree = new Label("duree");
        LblHeaderDuree.setMinWidth(105);
        LblHeaderDuree.setMaxWidth(105);
        LblHeaderDuree.setStyle("-fx-text-fill: black;");

        Label LblHeaderEtat = new Label("Etat");
        LblHeaderEtat.setMinWidth(80);
        LblHeaderEtat.setMaxWidth(80);
        LblHeaderEtat.setStyle("-fx-text-fill: black;");

        headerRow.getChildren().addAll(lblHeaderPtDepart, LblHeaderPtArrive, LblHeaderCommande, lblHeaderLivraison, LblHeaderDistance, LblHeaderDuree, LblHeaderEtat);

        vListTrajets.getChildren().add(headerRow);

        // Get all zones from the service
        List<Trajet> trajets = su.getTrajetsByZoneId(idZone);

        for (Trajet trajet : trajets) {
            HBox zoneRow = new HBox(4);
            zoneRow.setPrefHeight(32.0);
            zoneRow.setPrefWidth(765.0);

            Label lblPtDepart = new Label(trajet.getPointDepart());
            lblPtDepart.setMinWidth(80);
            lblPtDepart.setMaxWidth(80);

            Label LblPtArrive = new Label(trajet.getPointArrivee());
            LblPtArrive.setMinWidth(80);
            LblPtArrive.setMaxWidth(80);

            Label LblCommande = new Label(String.valueOf(trajet.getIdCommande()));
            LblCommande.setMinWidth(80);
            LblCommande.setMaxWidth(80);

            Label lblLivraison = new Label(String.valueOf(trajet.getIdLivraison()));
            lblLivraison.setMinWidth(80);
            lblLivraison.setMaxWidth(80);

            Label LblDistance = new Label(String.valueOf(trajet.getDistance()));
            LblDistance.setMinWidth(130);
            LblDistance.setMaxWidth(130);

            Label LblDuree = new Label(String.valueOf(trajet.getDureeEstimee()));
            LblDuree.setMinWidth(105);
            LblDuree.setMaxWidth(105);

            Label LblEtat = new Label(String.valueOf(trajet.getEtatTrajet().toString()));
            LblEtat.setMinWidth(80);
            LblEtat.setMaxWidth(80);

            zoneRow.getChildren().addAll(lblPtDepart, LblPtArrive, LblCommande, lblLivraison, LblDistance, LblDuree, LblEtat);

            vListTrajets.getChildren().add(zoneRow);
        }
    }


    @FXML
    private void searchTrajets(String criteria) {
        System.out.println("Recherche des trajets pour le critère : " + criteria);

        vListTrajets.getChildren().clear();

        // Create header row
        HBox headerRow = new HBox(4);
        headerRow.setPrefHeight(32.0);
        headerRow.setPrefWidth(765.0);

        headerRow.setStyle("-fx-background-color: #398c3e; -fx-padding: 10px;");

        Label lblHeaderPtDepart = new Label("point depart");
        lblHeaderPtDepart.setMinWidth(80);
        lblHeaderPtDepart.setMaxWidth(80);
        lblHeaderPtDepart.setStyle("-fx-text-fill: black;");

        Label LblHeaderPtArrive = new Label("point arrive");
        LblHeaderPtArrive.setMinWidth(80);
        LblHeaderPtArrive.setMaxWidth(80);
        LblHeaderPtArrive.setStyle("-fx-text-fill: black;");

        Label LblHeaderCommande = new Label("commande");
        LblHeaderCommande.setMinWidth(80);
        LblHeaderCommande.setMaxWidth(80);
        LblHeaderCommande.setStyle("-fx-text-fill: black;");

        Label lblHeaderLivraison = new Label("livraison");
        lblHeaderLivraison.setMinWidth(80);
        lblHeaderLivraison.setMaxWidth(80);
        lblHeaderLivraison.setStyle("-fx-text-fill: black;");

        Label LblHeaderDistance = new Label("distance");
        LblHeaderDistance.setMinWidth(130);
        LblHeaderDistance.setMaxWidth(130);
        LblHeaderDistance.setStyle("-fx-text-fill: black;");

        Label LblHeaderDuree = new Label("duree");
        LblHeaderDuree.setMinWidth(105);
        LblHeaderDuree.setMaxWidth(105);
        LblHeaderDuree.setStyle("-fx-text-fill: black;");

        Label LblHeaderEtat = new Label("Etat");
        LblHeaderEtat.setMinWidth(80);
        LblHeaderEtat.setMaxWidth(80);
        LblHeaderEtat.setStyle("-fx-text-fill: black;");

        headerRow.getChildren().addAll(lblHeaderPtDepart, LblHeaderPtArrive, LblHeaderCommande, lblHeaderLivraison, LblHeaderDistance, LblHeaderDuree, LblHeaderEtat);

        vListTrajets.getChildren().add(headerRow);

        // Récupération des zones qui correspondent aux critères de recherche
        List<Trajet> trajetList = st.search(criteria);

        for (Trajet trajet : trajetList) {
            HBox zoneRow = new HBox(4);
            zoneRow.setPrefHeight(32.0);
            zoneRow.setPrefWidth(765.0);

            Label lblPtDepart = new Label(trajet.getPointDepart());
            lblPtDepart.setMinWidth(80);
            lblPtDepart.setMaxWidth(80);

            Label LblPtArrive = new Label(trajet.getPointArrivee());
            LblPtArrive.setMinWidth(80);
            LblPtArrive.setMaxWidth(80);

            Label LblCommande = new Label(String.valueOf(trajet.getIdCommande()));
            LblCommande.setMinWidth(80);
            LblCommande.setMaxWidth(80);

            Label lblLivraison = new Label(String.valueOf(trajet.getIdLivraison()));
            lblLivraison.setMinWidth(80);
            lblLivraison.setMaxWidth(80);

            Label LblDistance = new Label(String.valueOf(trajet.getDistance()));
            LblDistance.setMinWidth(130);
            LblDistance.setMaxWidth(130);

            Label LblDuree = new Label(String.valueOf(trajet.getDureeEstimee()));
            LblDuree.setMinWidth(105);
            LblDuree.setMaxWidth(105);

            Label LblEtat = new Label(String.valueOf(trajet.getEtatTrajet().toString()));
            LblEtat.setMinWidth(80);
            LblEtat.setMaxWidth(80);

            zoneRow.getChildren().addAll(lblPtDepart, LblPtArrive, LblCommande, lblLivraison, LblDistance, LblDuree, LblEtat);

            vListTrajets.getChildren().add(zoneRow);
        }
    }



    @FXML
    private Label LblCommande;

    @FXML
    private Label LblDistance;

    @FXML
    private Label LblDuree;

    @FXML
    private Label LblEtat;

    @FXML
    private Label LblPtArrive;

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
    private Label lblLivraison;

    @FXML
    private Label lblPtDepart;

    @FXML
    private VBox vListTrajets;

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
