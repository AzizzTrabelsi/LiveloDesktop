package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Livraison;
import utils.MyDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GestionLivraisons implements Initializable {

    @FXML
    private VBox vListUsers;

    private Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = MyDatabase.getInstance().getConnection();
        afficherLivraisons();
    }

    private void afficherLivraisons() {
        List<Livraison> livraisons = getLivraisons();

        for (Livraison livraison : livraisons) {
            HBox row = new HBox();
            row.setSpacing(20);

            Label idLabel = new Label(String.valueOf(livraison.getIdLivraison()));
            Label commandeLabel = new Label(String.valueOf(livraison.getCommandeId()));
            Label dateLabel = new Label(livraison.getCreatedAt().toString());
            Label factureLabel = new Label(String.valueOf(livraison.getFactureId()));
            Label zoneLabel = new Label(String.valueOf(livraison.getZoneId()));
            Label createdByLabel = new Label(String.valueOf(livraison.getCreatedBy()));

            row.getChildren().addAll(idLabel, commandeLabel, dateLabel, factureLabel, zoneLabel, createdByLabel);
            vListUsers.getChildren().add(row);
        }
    }

    private List<Livraison> getLivraisons() {
        List<Livraison> livraisons = new ArrayList<>();
        String query = "SELECT * FROM livraison";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Livraison livraison = new Livraison(
                        rs.getInt("idLivraison"),
                        rs.getInt("commandeId"),
                        rs.getInt("created_by"),
                        rs.getDate("created_at"),
                        rs.getInt("factureId"),
                        rs.getInt("zoneId")
                );
                livraisons.add(livraison);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livraisons;
    }
}
