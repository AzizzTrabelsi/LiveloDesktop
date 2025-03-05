package controllers;

import com.stripe.exception.StripeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import models.Facture;
import models.type_paiement;
import services.CrudFacture;
import services.StripeService;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.sql.Date;

public class FactureController {
    @FXML
    private Label montantLabel, labelTotal;
    @FXML
    private CheckBox cashCheckBox, onlineCheckBox;
    private Image btn;

    private int userId;
    private int commandeId;
    private final CrudFacture crudFacture = new CrudFacture();
    private final StripeService stripeService = new StripeService(); // Utilisation du service Stripe


    public void setFactureDetails(double montant, int userId, int commandeId) {
        this.montantLabel.setText(montant + " TND");
        this.userId = userId;
        this.commandeId = commandeId;
    }
    private void retournerAuMarketClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MarketClient.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) cashCheckBox.getScene().getWindow(); // Utilise un élément existant
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void attendrePaiementEtRediriger() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successful Payment !!", ButtonType.OK);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            retournerAuMarketClient();
        }
    }


    @FXML
    private void confirmerPaiement() {
        System.out.println("Paiement confirmé !");

        if (cashCheckBox.isSelected()) {
            enregistrerFacture("CASH");
            attendrePaiementEtRediriger();
        } else if (onlineCheckBox.isSelected()) {
            try {
                String paymentUrl = stripeService.createCheckoutSession(commandeId, (getMontant() / 3));
                ouvrirPagePaiement(paymentUrl);

                // Ici, on attend la confirmation du paiement AVANT d'enregistrer la facture et de rediriger
                attendrePaiementEtRedirigerEnLigne();
            } catch (StripeException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error in stripe : " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error, try later !");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Choose Payment Method");
        }
    }
    private void attendrePaiementEtRedirigerEnLigne() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Payment Completed Successfully!", ButtonType.OK);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            enregistrerFacture("ONLINE");
            attendrePaiementEtRediriger();
        }
    }
    private void enregistrerFacture(String type) {
        Facture facture = new Facture(
                getMontant(),

                new Date(System.currentTimeMillis()),
                type_paiement.valueOf(type),
                userId, commandeId
        );
        crudFacture.add(facture);
        showAlert(Alert.AlertType.INFORMATION, "Order Valid continue to payement!");
        afficherFacture();
    }

  
   /* private void ouvrirPagePaiement() {
        try {
            String urlPaiement = "https://buy.stripe.com/test_4gw3cP8TtdtSa80cMM"; // 🔥 LIEN TEST STRIPE
            Desktop.getDesktop().browse(new URI(urlPaiement));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }*/
   private void ouvrirPagePaiement() {
       try {
           StripeService stripeService = new StripeService();
           String urlPaiement = stripeService.createCheckoutSession(commandeId, Float.parseFloat(montantLabel.getText().replace(" TND", "")));

           if (urlPaiement != null && !urlPaiement.isEmpty()) {
               Desktop.getDesktop().browse(new URI(urlPaiement));
           } else {
               System.out.println("Erreur : URL de paiement non générée.");
           }
       } catch (IOException | URISyntaxException | StripeException e) {
           e.printStackTrace();
       }
   }



    private void afficherFacture() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Facture.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
