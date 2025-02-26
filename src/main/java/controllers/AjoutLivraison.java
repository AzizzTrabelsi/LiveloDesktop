package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import models.Commande;
import models.Facture;
import models.User;
import models.Zone;
import services.CrudCommande;
import services.CrudFacture;
import services.CrudUser;
import services.CrudZone;
import utils.MyDatabase;

public class AjoutLivraison {

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private Text cmdId;

    @FXML
    private TextField commandeIdField;

    @FXML
    private TextField createdByField;

    @FXML
    private Text creatorId;

    @FXML
    private Text factId;

    @FXML
    private TextField factureIdField;

    @FXML
    private Text textZoneId;

    @FXML
    private TextField zoneIdField;

    private Connection connection;


    private CrudUser crudUser = new CrudUser();
    private CrudCommande crudCommande = new CrudCommande();
    private CrudFacture crudFacture = new CrudFacture();
    private CrudZone crudZone = new CrudZone();


    public AjoutLivraison() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @FXML
    private void initialize() {
        btnAjouter.setOnAction(event -> ajouterLivraison());
        btnAnnuler.setOnAction(event -> fermerFenetre());
        deleteError();
    }

    void deleteError() {
        textZoneId.setText("");
        factId.setText("");
        creatorId.setText("");
        cmdId.setText("");
    }

    private void ajouterLivraison() {

        List<User> users = crudUser.getAll();
        List<Zone> zones = crudZone.getAll();
        List<Facture> factures = crudFacture.getAll();
        List<Commande> commandes = crudCommande.getAll();
        deleteError();
        boolean isValid = true;
        try {
            int idZone = Integer.parseInt(zoneIdField.getText());
            List<Integer>  zoneids =zones.stream()
                    .map(cmd -> cmd.getIdZone())  // ou .map(Commande::getId_Commande)
                    .collect(Collectors.toList());
            if (!zoneids.contains(idZone)) {
                isValid = false;
                textZoneId.setText("Zone n'existe pas!");
            }
        } catch (NumberFormatException e) {
            isValid = false;
            textZoneId.setText("Invalid Id");

            if (zoneIdField.getText().isEmpty()) {
                textZoneId.setText("Le champ ne peut pas être vide");//not blank
            }
        }
        try {
            List<Integer>  cmdids =commandes.stream()
                    .map(cmd -> cmd.getId_Commande())  // ou .map(Commande::getId_Commande)
                    .collect(Collectors.toList());
            int idCmd = Integer.parseInt(commandeIdField.getText());
            if (!cmdids.contains(idCmd)) {
                System.out.println(commandes);
                System.out.println("  "+idCmd +commandes.contains(idCmd));
                isValid = false;
                cmdId.setText("Commande n'existe pas!");
            }

        } catch (NumberFormatException e) {
            cmdId.setText("Invalid Id");
            isValid = false;
            if (commandeIdField.getText().isEmpty()) {

                cmdId.setText("Le champ ne peut pas être vide");//not blank
            }
        }

        try {
            int idfact = Integer.parseInt(factureIdField.getText());
            List<Integer>  factids =factures.stream()
                    .map(cmd -> cmd.getIdFacture())  // ou .map(Commande::getId_Commande)
                    .collect(Collectors.toList());
            if (!factids.contains(idfact)) {
                isValid = false;
                factId.setText("Facture n'existe pas!");
            }

        } catch (NumberFormatException e) {
            factId.setText("Invalid Id");
            isValid = false;
            if (factureIdField.getText().isEmpty()) {

                factId.setText("Le champ ne peut pas être vide");//not blank
            }

        }
        try {
            int idUs = Integer.parseInt(createdByField.getText());
            List<Integer>  userids =users.stream()
                    .map(cmd -> cmd.getId())  // ou .map(Commande::getId_Commande)
                    .collect(Collectors.toList());
            if (!userids.contains(idUs)) {
                isValid = false;
                creatorId.setText("User n'existe pas!");
            }

        } catch (NumberFormatException e) {
            creatorId.setText("Invalid Id");
            isValid = false;
            if (createdByField.getText().isEmpty()) {

                creatorId.setText("Le champ ne peut pas être vide");//not blank
            }
        }
        if (isValid) {
            String commandeId = commandeIdField.getText();
            String factureId = factureIdField.getText();
            String zoneId = zoneIdField.getText();
            String createdBy = createdByField.getText();

            if (commandeId.isEmpty() || factureId.isEmpty() || zoneId.isEmpty() || createdBy.isEmpty()) {
                System.out.println("Veuillez remplir tous les champs.");
                return;
            }

            String query = "INSERT INTO livraison (commandeId, factureId, zoneId, created_by) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, commandeId);
                statement.setString(2, factureId);
                statement.setString(3, zoneId);
                statement.setString(4, createdBy);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Livraison ajoutée avec succès !");
                    goToGestionLivraisons();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de l'ajout de la livraison.");
            }
        }
    }

    @FXML
    void goToGestionLivraisons() {
        try {
            Stage stage = (Stage) commandeIdField.getScene().getWindow(); // Get reference to the login window's stage
            stage.setTitle("Gestion Livraisons");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionLivraison.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);

            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle navigation failure
        }

    }

    private void fermerFenetre() {

        goToGestionLivraisons();

    }
}
