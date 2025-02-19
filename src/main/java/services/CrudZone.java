package services;

import interfaces.IServiceCrud;
import models.Zone;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudZone implements IServiceCrud<Zone> {
    Connection conn = MyDatabase.getInstance().getConnection();

    @Override
    public void add(Zone zone) {
        String qry = "INSERT INTO `zone` (`nom`, `latitude_centre`, `longitude_centre`, `rayon`, `id_user`, `id_livraison`, `max`) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, zone.getNom());
            statement.setDouble(2, zone.getLatitudeCentre());
            statement.setDouble(3, zone.getLongitudeCentre());
            statement.setFloat(4, zone.getRayon());
            statement.setInt(5, zone.getIdUser());
            statement.setInt(6, zone.getIdLivraison());
            statement.setInt(7, zone.getMax());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        zone.setIdZone(generatedId);
                        System.out.println("Zone added successfully with ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Zone> getAll() {
        List<Zone> zones = new ArrayList<>();
        String query = "SELECT * FROM `zone`";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idZone = rs.getInt("idZone");
                String nom = rs.getString("nom");
                double latitudeCentre = rs.getDouble("latitude_centre");
                double longitudeCentre = rs.getDouble("longitude_centre");
                float rayon = rs.getFloat("rayon");
                int idUser = rs.getInt("id_user");
                int idLivraison = rs.getInt("id_livraison");
                int max = rs.getInt("max");

                Zone zone = new Zone(nom, latitudeCentre, longitudeCentre, rayon, idUser, idLivraison, max);
                zones.add(zone);
            }

            System.out.println("Nombre de zones récupérées : " + zones.size());
            for (Zone z : zones) {
                System.out.println(z);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zones;
    }

    @Override
    public void update(Zone zone) {
        String qry = "UPDATE `zone` SET `nom` = ?, `latitude_centre` = ?, `longitude_centre` = ?, `rayon` = ?, " +
                "`id_user` = ?, `id_livraison` = ?, `max` = ? WHERE `idZone` = ?";

        try (PreparedStatement statement = conn.prepareStatement(qry)) {
            statement.setString(1, zone.getNom());
            statement.setDouble(2, zone.getLatitudeCentre());
            statement.setDouble(3, zone.getLongitudeCentre());
            statement.setFloat(4, zone.getRayon());
            statement.setInt(5, zone.getIdUser());
            statement.setInt(6, zone.getIdLivraison());
            statement.setInt(7, zone.getMax());
            statement.setInt(8, zone.getIdZone());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Zone updated successfully.");
            } else {
                System.out.println("No zone found with ID " + zone.getIdZone());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String qry = "DELETE FROM `zone` WHERE `idZone` = ?";

        try (PreparedStatement statement = conn.prepareStatement(qry)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Zone deleted successfully.");
            } else {
                System.out.println("No zone found with ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Zone getById(int id) {
        return null;
    }

    @Override
    public List<Zone> search(String criteria) {
        return List.of();
    }
}
