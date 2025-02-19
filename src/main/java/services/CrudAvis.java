package tn.esprit.services;

import interfaces.IServiceCrud;
import tn.esprit.models.Avis;
import utils.MyDatabase;
import tn.esprit.models.Livraison;
import java.sql.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudAvis implements IServiceCrud<Avis> {
    Connection conn = MyDatabase.getInstance().getConnection();

    @Override
    public void add(Avis avis) {
        String qry = "INSERT INTO `avis` (`created_by`, `created_at`, `description`, `livraisonId`) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, avis.getCreatedBy());
            statement.setDate(2, new java.sql.Date(avis.getCreatedAt().getTime()));
            statement.setString(3, avis.getDescription());

            // Vérification de la présence de la livraison liée
            if (avis.getLivraison() != null) {
                statement.setInt(4, avis.getLivraison().getIdLivraison());
            } else {
                statement.setNull(4, Types.INTEGER); // Si aucune livraison n'est associée, la valeur sera NULL
            }

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        avis.setIdAvis(generatedId);
                        System.out.println("Avis ajouté avec succès avec l'ID : " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Avis> getAll() {
        List<Avis> avisList = new ArrayList<>();
        String query = "SELECT * FROM avis";

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Executing query: " + query);

            while (resultSet.next()) {
                int idAvis = resultSet.getInt("idAvis");
                int createdBy = resultSet.getInt("created_by");
                Date createdAt = resultSet.getDate("created_at");
                String description = resultSet.getString("description");
                int livraisonId = resultSet.getInt("livraisonId");

                // Création de l'objet Avis
                Avis avis = new Avis(idAvis, createdBy, null, createdAt, description);

                // Récupération de la livraison associée si présente
                if (livraisonId > 0) {
                    Livraison livraison = getLivraisonById(livraisonId);
                    avis.setLivraison(livraison);
                }

                avisList.add(avis);
                System.out.println("Avis ID: " + idAvis + " | Created By: " + createdBy + " | Description: " + description);
            }

            System.out.println("Nombre d'avis récupérés: " + avisList.size());
            for (Avis a : avisList) {
                System.out.println(a);
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL: " + e.getMessage());
        }

        return avisList;
    }

    private Livraison getLivraisonById(int idLivraison) {
        Livraison livraison = null;
        String query = "SELECT * FROM livraison WHERE id_livraison = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idLivraison);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Création de l'objet Livraison
                    int id = resultSet.getInt("id_livraison");
                    Date dateLivraison = resultSet.getDate("date_livraison");
                    String adresse = resultSet.getString("adresse");
                    livraison = new Livraison(id, dateLivraison, adresse);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la livraison: " + e.getMessage());
        }

        return livraison;
    }


    public Avis getById(int id) {
        String query = "SELECT * FROM `avis` WHERE `idAvis` = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idAvis = rs.getInt("idAvis");
                String description = rs.getString("description");
                int createdBy = rs.getInt("created_by");
                Date createdAt = rs.getDate("created_at");
                int livraisonId = rs.getInt("livraisonId");
                Livraison livraison = null;

                // Récupérer la livraison associée
                if (livraisonId > 0) {
                    String livraisonQuery = "SELECT * FROM `livraison` WHERE `livraisonId` = ?";
                    try (PreparedStatement livraisonStmt = conn.prepareStatement(livraisonQuery)) {
                        livraisonStmt.setInt(1, livraisonId);
                        ResultSet livraisonRs = livraisonStmt.executeQuery();
                        if (livraisonRs.next()) {
                            int commandeId = livraisonRs.getInt("commandeId");
                            int createdByLivraison = livraisonRs.getInt("createdBy");
                            Date createdAtLivraison = livraisonRs.getDate("createdAt");
                            int factureId = livraisonRs.getInt("factureId");
                            int zoneId = livraisonRs.getInt("zoneId");
                            // Crée l'objet Livraison
                            livraison = new Livraison(livraisonId, commandeId, createdByLivraison, createdAtLivraison, factureId, zoneId);
                        }
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de la récupération de la livraison : " + e.getMessage());
                    }
                }

                // Créer l'objet Avis avec la livraison récupérée
                Avis avis = new Avis(idAvis, createdBy, null, createdAt, description);
                System.out.println("Avis trouvé : " + avis);
                return avis;
            } else {
                System.out.println("Aucun avis trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public void update(Avis avis) {
        // Requête SQL pour mettre à jour un avis
        String qry = "UPDATE `avis` SET `created_by` = ?, `created_at` = ?, `description` = ?, `livraisonId` = ? WHERE `idAvis` = ?";

        try (PreparedStatement statement = conn.prepareStatement(qry)) {
            // Assignation des valeurs dans le PreparedStatement
            statement.setInt(1, avis.getCreatedBy());
            statement.setDate(2, new java.sql.Date(avis.getCreatedAt().getTime()));
            statement.setString(3, avis.getDescription());

            // Vérification si l'ID de la livraison est valide et assignation
            if (avis.getLivraison() != null) {
                statement.setInt(4, avis.getLivraison().getIdLivraison());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            // ID de l'avis à mettre à jour
            statement.setInt(5, avis.getIdAvis());

            // Exécution de la mise à jour
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Avis updated successfully.");
            } else {
                System.out.println("No avis found with ID " + avis.getIdAvis());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void delete(int id) {
        String qry = "DELETE FROM `avis` WHERE `idAvis` = " + id;
        try (PreparedStatement statement = conn.prepareStatement(qry)) {
            statement.executeUpdate();
            System.out.println("Avis deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public List<Avis> search(String criteria) {
        List<Avis> avisList = new ArrayList<>();
        String query = "SELECT * FROM `avis` WHERE `description` LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            String searchTerm = "%" + criteria + "%";
            stmt.setString(1, searchTerm);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idAvis = rs.getInt("idAvis");
                String description = rs.getString("description");
                int createdBy = rs.getInt("created_by");
                Date createdAt = rs.getDate("created_at");
                int livraisonId = rs.getInt("livraisonId");
                Livraison livraison = null;

                // Récupérer la livraison associée
                if (livraisonId > 0) {
                    String livraisonQuery = "SELECT * FROM `livraison` WHERE `livraisonId` = ?";
                    try (PreparedStatement livraisonStmt = conn.prepareStatement(livraisonQuery)) {
                        livraisonStmt.setInt(1, livraisonId);
                        ResultSet livraisonRs = livraisonStmt.executeQuery();
                        if (livraisonRs.next()) {
                            int commandeId = livraisonRs.getInt("commandeId");
                            int createdByLivraison = livraisonRs.getInt("createdBy");
                            Date createdAtLivraison = livraisonRs.getDate("created_at");
                            int factureId = livraisonRs.getInt("factureId");
                            int zoneId = livraisonRs.getInt("zoneId");
                            // Créer l'objet Livraison
                            livraison = new Livraison(livraisonId, commandeId, createdByLivraison, createdAtLivraison, factureId, zoneId);
                        }
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de la récupération de la livraison : " + e.getMessage());
                    }
                }

                // Créer l'objet Avis avec la livraison récupérée
                Avis avis = new Avis(idAvis, createdBy, null, createdAt, description);
                avisList.add(avis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (avisList.isEmpty()) {
            System.out.println("Aucun avis trouvé pour le critère : " + criteria);
        } else {
            System.out.println("Nombre d'avis trouvés : " + avisList.size());
            for (Avis avis : avisList) {
                System.out.println("Avis trouvé : ");
                System.out.println("ID: " + avis.getIdAvis());
                System.out.println("Description: " + avis.getDescription());
                System.out.println("Créé par (ID utilisateur): " + avis.getCreatedBy());
                System.out.println("Date de création: " + avis.getCreatedAt());
                System.out.println("Livraison associée: " + (avis.getLivraison() != null ? "ID: " + avis.getLivraison().getIdLivraison() : "Non définie"));
                System.out.println();
            }
        }

        return avisList;
    }








}