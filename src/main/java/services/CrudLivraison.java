package services;

import interfaces.IServiceCrud;
import models.Livraison;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudLivraison implements IServiceCrud<Livraison> {
    private Connection cnx;

    public CrudLivraison(){
        cnx = MyDatabase.getInstance().getConnection();}

    @Override
    public void add(Livraison livraison) {
        String qry = "INSERT INTO livraison (commandeId, created_by, created_at, factureId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, livraison.getCommandeId());
            pstm.setInt(2, livraison.getCreatedBy());
            pstm.setTimestamp(3, new Timestamp(livraison.getCreatedDate().getTime()));
            pstm.setInt(4, livraison.getFactureId());
            pstm.executeUpdate();
            System.out.println("Livraison ajoutée avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }
    @Override
    public  void delete(int idLivraison) {
        String qry = "DELETE FROM `livraison` WHERE `idLivraison`=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(qry)) {
            preparedStatement.setInt(1, idLivraison);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("livraison deleted successfully!");
            } else {
                System.out.println("No livraison found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting livraison: " + e.getMessage());
        }
    }


    @Override
    public List<Livraison> getAll() {
        List<Livraison> livraisons = new ArrayList<>();
        String qry = "SELECT * FROM livraison";
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(qry)) {
            while (rs.next()) {
                Livraison l = new Livraison(
                        rs.getInt("idLivraison"),
                        rs.getInt("commandeId"),
                        rs.getInt("created_by"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("factureId")
                );
                livraisons.add(l);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return livraisons;
    }

    @Override
    public void update(Livraison livraison) {
        String qry = "UPDATE livraison SET commandeId = ?, created_by = ?, created_at = ?, factureId = ? WHERE idLivraison = ?";
        try (PreparedStatement statement = cnx.prepareStatement(qry)) {
            statement.setInt(1, livraison.getCommandeId());
            statement.setInt(2, livraison.getCreatedBy());
            statement.setTimestamp(3, new Timestamp(livraison.getCreatedDate().getTime()));
            statement.setInt(4, livraison.getFactureId());
            statement.setInt(5, livraison.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livraison mise à jour avec succès.");
            } else {
                System.out.println("Aucune livraison trouvée avec ID " + livraison.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }


    @Override
    public Livraison getById(int id) {
        String qry = "SELECT * FROM livraison WHERE idLivraison = ?";
        try (PreparedStatement statement = cnx.prepareStatement(qry)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Livraison(
                            rs.getInt("idLivraison"),
                            rs.getInt("commandeId"),
                            rs.getInt("created_by"),
                            rs.getTimestamp("created_at"),
                            rs.getInt("factureId")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }


    public List<Livraison> search(String criteria) {
        List<Livraison> results = new ArrayList<>();
        String qry = "SELECT * FROM livraison WHERE id_commande LIKE ? OR created_by LIKE ?";
        try (PreparedStatement statement = cnx.prepareStatement(qry)) {
            statement.setString(1, "%" + criteria + "%");
            statement.setString(2, "%" + criteria + "%");
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    results.add(new Livraison(
                            rs.getInt("id"),
                            rs.getInt("commande_Id"),
                            rs.getInt("created_by"),
                            rs.getTimestamp("created_at"),
                            rs.getInt("facture_Id")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche : " + e.getMessage());
        }
        return results;
    }
}



