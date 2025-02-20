package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.MyDatabase; // Assure-toi d'avoir une classe pour la connexion DB

public class AjoutLivraison {

    @FXML
    private TextField commandeIdField;

    @FXML
    private TextField factureIdField;

    @FXML
    private TextField zoneIdField;

    @FXML
    private TextField createdByField;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    private Connection connection;

    public AjoutLivraison() {
        // Initialisation de la connexion à la base de données
        connection = MyDatabase.getInstance().getConnection();
    }

    @FXML
    private void initialize() {
        btnAjouter.setOnAction(event -> ajouterLivraison());
        btnAnnuler.setOnAction(event -> fermerFenetre());
    }

    private void ajouterLivraison() {
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
                fermerFenetre();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de la livraison.");
        }
    }

    private void fermerFenetre() {
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        stage.close();
    }
}
