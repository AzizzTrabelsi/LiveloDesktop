package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Livraison;
import services.CrudLivraison;

import java.awt.event.ActionEvent;

public class ModifierLivraison {

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnModifier;

    @FXML
    private TextField commandeIdField;

    @FXML
    private TextField createdByField;

    @FXML
    private TextField factureIdField;

    @FXML
    private TextField zoneIdField;

    private CrudLivraison crudLivraison = new CrudLivraison();

    private Livraison livraisonToUpdate;  // This should be initialized with the livraison to modify

    @FXML
    private void initialize() {
        btnModifier.setOnAction(event -> saveLivraison());
        btnAnnuler.setOnAction(event -> fermerFenetre());
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
        try {
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
