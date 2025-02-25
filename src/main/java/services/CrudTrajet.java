package services;

import interfaces.IServiceCrud;
import models.Trajet;
import models.Zone;
import models.etatTrajet;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudTrajet implements IServiceCrud<Trajet> {
    Connection conn = MyDatabase.getInstance().getConnection();

    @Override
    public void add(Trajet trajet) {
        String qry = "INSERT INTO `trajet` (`point_depart`, `point_arrivee`, `idZone`, `idcommande`, `idlivraison`, `distance`, `dureeEstimee`, `etatTrajet`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, trajet.getPointDepart());
            statement.setString(2, trajet.getPointArrivee());
            statement.setInt(3, trajet.getZone().getIdZone());
            statement.setInt(4, trajet.getIdCommande());
            statement.setInt(5, trajet.getIdLivraison());
            statement.setFloat(6, trajet.getDistance());
            statement.setFloat(7, trajet.getDureeEstimee());
            statement.setString(8, trajet.getEtatTrajet().name());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        trajet.setIdTrajet(generatedId);
                        System.out.println("Trajet added successfully with ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Trajet> getAll() {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM `trajet`";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Trajet trajet = mapResultSetToTrajet(rs);
                trajets.add(trajet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trajets;
    }

    @Override
    public void update(Trajet trajet) {
        String qry = "UPDATE `trajet` SET `point_depart` = ?, `point_arrivee` = ?, `idZone` = ?, `idcommande` = ?, `idlivraison` = ?, `distance` = ?, `dureeEstimee` = ?, `etatTrajet` = ? WHERE `idTrajet` = ?";

        try (PreparedStatement statement = conn.prepareStatement(qry)) {
            statement.setString(1, trajet.getPointDepart());
            statement.setString(2, trajet.getPointArrivee());
            statement.setInt(3, trajet.getZone().getIdZone());
            statement.setInt(4, trajet.getIdCommande());
            statement.setInt(5, trajet.getIdLivraison());
            statement.setFloat(6, trajet.getDistance());
            statement.setFloat(7, trajet.getDureeEstimee());
            statement.setString(8, trajet.getEtatTrajet().name());
            statement.setInt(9, trajet.getIdTrajet());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Trajet updated successfully.");
            } else {
                System.out.println("No trajet found with ID " + trajet.getIdTrajet());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String qry = "DELETE FROM `trajet` WHERE `idTrajet` = ?";

        try (PreparedStatement statement = conn.prepareStatement(qry)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Trajet deleted successfully.");
            } else {
                System.out.println("No trajet found with ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Trajet getById(int id) {
        String query = "SELECT * FROM `trajet` WHERE `idTrajet` = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToTrajet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Trajet> search(String criteria) {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM `trajet` WHERE `etatTrajet` = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, criteria);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Trajet trajet = mapResultSetToTrajet(rs);
                trajets.add(trajet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trajets;
    }

    private Trajet mapResultSetToTrajet(ResultSet rs) throws SQLException {
        int idTrajet = rs.getInt("idTrajet");
        String pointDepart = rs.getString("point_depart");
        String pointArrivee = rs.getString("point_arrivee");
        int idZone = rs.getInt("idZone");
        int idCommande = rs.getInt("idcommande");
        int idLivraison = rs.getInt("idlivraison");
        float distance = rs.getFloat("distance");
        float dureeEstimee = rs.getFloat("dureeEstimee");
        etatTrajet etat = etatTrajet.valueOf(rs.getString("etatTrajet"));

        Zone zone = new Zone();
        zone.setIdZone(idZone);

        Trajet trajet = new Trajet(pointDepart, pointArrivee, zone, idCommande, idLivraison, distance, dureeEstimee, etat);
        trajet.setIdTrajet(idTrajet);
        return trajet;
    }
}

