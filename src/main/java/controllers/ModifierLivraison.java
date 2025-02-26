package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.*;
import services.*;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class ModifierLivraison {

    private CrudUser crudUser = new CrudUser();
    private CrudCommande crudCommande = new CrudCommande();
    private CrudFacture crudFacture = new CrudFacture();
    private CrudZone crudZone = new CrudZone();

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnModifier;

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

    private CrudLivraison crudLivraison = new CrudLivraison();

    private Livraison livraisonToUpdate;  // This should be initialized with the livraison to modify

    @FXML
    private void initialize() {
        btnModifier.setOnAction(event -> saveLivraison());
        btnAnnuler.setOnAction(event -> fermerFenetre());
        deleteError();
    }
    void deleteError(){
        textZoneId.setText("");
        factId.setText("");
        creatorId.setText("");
        cmdId.setText("");
    }
    public void setLivraison(Livraison livraison) {
        this.livraisonToUpdate = livraison;
        // Populate the fields with the current livraison data
        commandeIdField.setText(String.valueOf(livraison.getCommandeId()));
        createdByField.setText(String.valueOf(livraison.getCreatedBy()));
        factureIdField.setText(String.valueOf(livraison.getFactureId()));
        zoneIdField.setText(String.valueOf(livraison.getZoneId()));
    }

    @FXML
    void saveLivraison() {
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
        try {
            if(isValid){
            // Get the values from the text fields
            int commandeId = Integer.parseInt(commandeIdField.getText());
            int createdBy = Integer.parseInt(createdByField.getText());
            int factureId = Integer.parseInt(factureIdField.getText());
            int zoneId = Integer.parseInt(zoneIdField.getText());


            // Update the Livraison object
            livraisonToUpdate.setCommandeId(commandeId);
            livraisonToUpdate.setCreatedBy(createdBy);
            livraisonToUpdate.setFactureId(factureId);
            livraisonToUpdate.setZoneId(zoneId);

            // Save the updated Livraison to the database
            crudLivraison.update(livraisonToUpdate);
            goToGestionLivraisons();
            // Handle post-save actions (e.g., close window, refresh list)
            // Optionally, go back to the list view
            // You can add logic here to navigate back to the list or display a success message
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle validation errors or database issues
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
