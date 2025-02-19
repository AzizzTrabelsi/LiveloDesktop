package services;

import interfaces.IServiceCrud;
import models.Zone;
import models.Trajet;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudZone implements IServiceCrud<Zone> {
    Connection conn = MyDatabase.getInstance().getConnection();

    @Override
    public void add(Zone zone) {
        String qry = "INSERT INTO zone (nom, latitude_centre, longitude_centre, rayon, id_user, id_livraison, max) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, zone.getNom());
            statement.setDouble(2, zone.getLatitudeCentre());
            statement.setDouble(3, zone.getLongitudeCentre());
            statement.setFloat(4, zone.getRayon());
            statement.setInt(5, zone.getIdUser());
            statement.setObject(6, zone.getIdLivraison() != 0 ? zone.getIdLivraison() : null, Types.INTEGER);
            statement.setInt(7, zone.getMax());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        zone.setIdZone(generatedKeys.getInt(1));
                        System.out.println("Zone added successfully with ID: " + zone.getIdZone());
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
        String query = "SELECT * FROM zone";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Zone zone = mapResultSetToZone(rs);
                zone.setTrajets(getTrajetsByZoneId(zone.getIdZone()));
                zones.add(zone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zones;
    }

    @Override
    public void update(Zone zone) {
        String qry = "UPDATE zone SET nom = ?, latitude_centre = ?, longitude_centre = ?, rayon = ?, id_user = ?, id_livraison = ?, max = ? WHERE idZone = ?";

        try (PreparedStatement statement = conn.prepareStatement(qry)) {
            statement.setString(1, zone.getNom());
            statement.setDouble(2, zone.getLatitudeCentre());
            statement.setDouble(3, zone.getLongitudeCentre());
            statement.setFloat(4, zone.getRayon());
            statement.setInt(5, zone.getIdUser());
            statement.setObject(6, zone.getIdLivraison() != 0 ? zone.getIdLivraison() : null, Types.INTEGER);
            statement.setInt(7, zone.getMax());
            statement.setInt(8, zone.getIdZone());

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Zone updated successfully." : "No zone found with ID " + zone.getIdZone());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String deleteTrajetsQry = "DELETE FROM trajet WHERE idZone = ?";
        String deleteZoneQry = "DELETE FROM zone WHERE idZone = ?";

        try (PreparedStatement deleteTrajetsStmt = conn.prepareStatement(deleteTrajetsQry);
             PreparedStatement deleteZoneStmt = conn.prepareStatement(deleteZoneQry)) {

            deleteTrajetsStmt.setInt(1, id);
            deleteTrajetsStmt.executeUpdate();

            deleteZoneStmt.setInt(1, id);
            int rowsAffected = deleteZoneStmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Zone and associated trajets deleted successfully." : "No zone found with ID " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Zone getById(int id) {
        String query = "SELECT * FROM zone WHERE idZone = ?";
        Zone zone = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                zone = mapResultSetToZone(rs);
                zone.setTrajets(getTrajetsByZoneId(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zone;
    }

    @Override
    public List<Zone> search(String criteria) {
        List<Zone> zones = new ArrayList<>();
        String query = "SELECT * FROM zone WHERE nom LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + criteria + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Zone zone = mapResultSetToZone(rs);
                zone.setTrajets(getTrajetsByZoneId(zone.getIdZone()));
                zones.add(zone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zones;
    }

    private Zone mapResultSetToZone(ResultSet rs) throws SQLException {
        Zone zone = new Zone(
                rs.getString("nom"),
                rs.getDouble("latitude_centre"),
                rs.getDouble("longitude_centre"),
                rs.getFloat("rayon"),
                rs.getInt("id_user"),
                rs.getInt("id_livraison"),
                rs.getInt("max")
        );
        zone.setIdZone(rs.getInt("idZone"));
        return zone;
    }

    private List<Trajet> getTrajetsByZoneId(int idZone) {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM trajet WHERE idZone = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idZone);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Trajet trajet = new Trajet(
                        rs.getString("point_depart"),
                        rs.getString("point_arrivee"),
                        null, // Zone object will be set outside
                        rs.getInt("idcommande"),
                        rs.getInt("idlivraison"),
                        rs.getFloat("distance"),
                        rs.getFloat("dureeEstimee"),
                        models.etatTrajet.valueOf(rs.getString("etatTrajet"))
                );
                trajet.setIdTrajet(rs.getInt("idTrajet"));
                trajets.add(trajet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trajets;
    }
}
