package services;

import interfaces.IServiceCrud;
import models.Avis;
import utils.MyDatabase;

import java.sql.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudAvis implements IServiceCrud<Avis> {
    private Connection cnx ;

    public CrudAvis(){
        cnx = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Avis avis) {
        //create Qry SQL
        //execute Qry
        String qry ="INSERT INTO `avis`(`created_By`, `livraisonId`,`created_at`, `description`) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(qry);
            preparedStatement.setInt(1, avis.getCreatedBy());
            preparedStatement.setInt(2, avis.getDeliveryId());
            preparedStatement.setDate(3, new Date(avis.getCreatedDate().getTime()));
            preparedStatement.setString(4, avis.getComment());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public List<Avis> getAll() {

        List<Avis> listAvis = new ArrayList<>();
        String qry ="SELECT * FROM `Avis`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet resultSet = stm.executeQuery(qry);

            while (resultSet.next()){
                Avis avis = new Avis(resultSet.getInt("idAvis")
                        ,resultSet.getInt("created_By")
                        ,resultSet.getInt("livraisonId")
                        ,resultSet.getDate("created_at")
                        ,resultSet.getString("description"));

                listAvis.add(avis);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listAvis;
    }

    @Override
    public Avis getById(int id) {
        Avis avis = null;
        String qry = "SELECT * FROM `Avis` WHERE `idAvis` = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(qry)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    avis = new Avis(
                            resultSet.getInt("idAvis"),
                            resultSet.getInt("created_By"),
                            resultSet.getInt("livraisonId"),
                            resultSet.getDate("created_at"),
                            resultSet.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'avis : " + e.getMessage());
        }

        return avis;
    }


    @Override
    public void update(Avis avis) {
        String qry = "UPDATE `Avis` SET `created_by`=?, `LivraisonId`=?, `created_at`=?, `description`=? WHERE `idAvis`=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(qry)) {
            preparedStatement.setInt(1, avis.getCreatedBy());
            preparedStatement.setInt(2, avis.getDeliveryId());
            if(avis.getCreatedDate() != null) {
                preparedStatement.setDate(3, new Date(avis.getCreatedDate().getTime()));
            }
            preparedStatement.setString(4, avis.getComment());
            preparedStatement.setInt(5, avis.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Avis updated successfully!");
            } else {
                System.out.println("No Avis found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating Avis: " + e.getMessage());
        }
    }


    @Override
    public void delete(int idReview) {
        String qry = "DELETE FROM `Avis` WHERE `idAvis`=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(qry)) {
            preparedStatement.setInt(1, idReview);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Avis deleted successfully!");
            } else {
                System.out.println("No Avis found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Avis: " + e.getMessage());
        }
    }




    public List<Avis> search(String criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }







}