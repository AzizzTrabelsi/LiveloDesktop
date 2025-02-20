package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Livraison;
import services.CrudLivraison;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GestionLivraisons implements Initializable {

    private CrudLivraison livraisonService = new CrudLivraison();

    @FXML
    private VBox vListLivraisons;
    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLivraisons();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                loadLivraisons();
            } else {
                searchLivraisons(newValue);
            }
        });
    }

    @FXML
    void loadLivraisons() {
        vListLivraisons.getChildren().clear();
        List<Livraison> livraisons = livraisonService.getAll();

        for (Livraison livraison : livraisons) {
            HBox livraisonRow = new HBox(10);
            Label lblId = new Label(String.valueOf(livraison.getIdLivraison()));
            Label lblCommandeId = new Label(String.valueOf(livraison.getCommandeId()));
            Label lblCreatedBy = new Label();
            Label lblCreatedAt = new Label(livraison.getCreatedAt().toString());
            Label lblFactureId = new Label(String.valueOf(livraison.getFactureId()));
            Label lblZoneId = new Label(String.valueOf(livraison.getZoneId()));

            livraisonRow.getChildren().addAll(lblId, lblCommandeId, lblCreatedBy, lblCreatedAt, lblFactureId, lblZoneId);
            livraisonRow.setOnMouseClicked(event -> showLivraisonDetailsPopup(livraison));
            vListLivraisons.getChildren().add(livraisonRow);
        }
    }

    @FXML
    private void showLivraisonDetailsPopup(Livraison livraison) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de la Livraison");
        alert.setHeaderText("Informations de la livraison ID: " + livraison.getIdLivraison());
        alert.setContentText("Commande ID: " + livraison.getCommandeId() + "\n" +
                "Créé par: " + livraison.getCreatedBy() + "\n" +
                "Date de création: " + livraison.getCreatedAt() + "\n" +
                "Facture ID: " + livraison.getFactureId() + "\n" +
                "Zone ID: " + livraison.getZoneId());

        ButtonType updateButton = new ButtonType("Mettre à jour");
        ButtonType deleteButton = new ButtonType("Supprimer");

        alert.getButtonTypes().setAll(updateButton, deleteButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == updateButton) {
                showUpdatePopup(livraison);
            } else if (response == deleteButton) {
                livraisonService.delete(livraison.getIdLivraison());
                loadLivraisons();
            }
        });
    }

    @FXML
    private void showUpdatePopup(Livraison livraison) {
        Dialog<Livraison> dialog = new Dialog<>();
        dialog.setTitle("Mettre à jour la livraison");
        dialog.setHeaderText("Modifier les informations de la livraison");

        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField commandeIdField = new TextField(String.valueOf(livraison.getCommandeId()));
        TextField createdByField = new TextField();
        TextField factureIdField = new TextField(String.valueOf(livraison.getFactureId()));
        TextField zoneIdField = new TextField(String.valueOf(livraison.getZoneId()));

        grid.add(new Label("Commande ID:"), 0, 0);
        grid.add(commandeIdField, 1, 0);
        grid.add(new Label("Créé par:"), 0, 1);
        grid.add(createdByField, 1, 1);
        grid.add(new Label("Facture ID:"), 0, 2);
        grid.add(factureIdField, 1, 2);
        grid.add(new Label("Zone ID:"), 0, 3);
        grid.add(zoneIdField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                livraison.setCommandeId(Integer.parseInt(commandeIdField.getText()));
                livraison.setCreatedBy(Integer.parseInt(createdByField.getText()));
                livraison.setFactureId(Integer.parseInt(factureIdField.getText()));
                livraison.setZoneId(Integer.parseInt(zoneIdField.getText()));

                livraisonService.update(livraison);
                loadLivraisons();
                return livraison;
            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    private void searchLivraisons(String criteria) {
        vListLivraisons.getChildren().clear();
        List<Livraison> livraisons = livraisonService.search(criteria);

        for (Livraison livraison : livraisons) {
            HBox livraisonRow = new HBox(10);
            Label lblId = new Label(String.valueOf(livraison.getIdLivraison()));
            Label lblCommandeId = new Label(String.valueOf(livraison.getCommandeId()));
            Label lblCreatedBy = new Label();
            Label lblCreatedAt = new Label(livraison.getCreatedAt().toString());
            Label lblFactureId = new Label(String.valueOf(livraison.getFactureId()));
            Label lblZoneId = new Label(String.valueOf(livraison.getZoneId()));

            livraisonRow.getChildren().addAll(lblId, lblCommandeId, lblCreatedBy, lblCreatedAt, lblFactureId, lblZoneId);
            livraisonRow.setOnMouseClicked(event -> showLivraisonDetailsPopup(livraison));
            vListLivraisons.getChildren().add(livraisonRow);
        }
    }
}