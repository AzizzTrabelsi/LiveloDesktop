package services;

import interfaces.IServiceCrud;
import models.User;
import models.role_user;
import models.type_vehicule;
import utils.MyDatabase;
import org.mindrot.jbcrypt.BCrypt;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudUser implements IServiceCrud<User> {
    Connection conn= MyDatabase.getInstance().getConnection();

    @Override
    public void add(User user) {
        String qry = "INSERT INTO `user` (`nom`, `prenom`, `role`, `verified`, `adresse`, `type_vehicule`, `email`, `password`, `num_tel`, `cin`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            System.out.println("Mot de passe original : " + user.getPassword());
            System.out.println("Mot de passe hashé : " + hashedPassword);

            statement.setString(1, user.getNom());
            statement.setString(2, user.getPrenom());
            statement.setString(3, user.getRole().toString());
            statement.setInt(4, user.isVerified() ? 1 : 0);
            statement.setString(5, user.getAdresse());
            statement.setString(6, user.getType_vehicule().toString());
            statement.setString(7, user.getEmail());
            statement.setString(8, hashedPassword);
            statement.setString(9, user.getNum_tel());
            statement.setString(10, user.getCin());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        user.setId(generatedId);
                        System.out.println("User ajouté avec ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM `user`";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idUser");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String roleString = rs.getString("role");
                String typeVehiculeString = rs.getString("type_vehicule");
                type_vehicule typeVehicule = type_vehicule.valueOf(typeVehiculeString);
                role_user role = role_user.valueOf(roleString);

                boolean verified = rs.getBoolean("verified");
                String adresse = rs.getString("adresse");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String num_tel = rs.getString("num_tel");
                String cin = rs.getString("cin");

                User user = new User(id,nom, prenom, role, verified, adresse,
                        typeVehicule, email, password, num_tel, cin);

                users.add(user);
            }

            System.out.println("Nombre d'utilisateurs récupérés : " + users.size());
            for (User u : users) {
                System.out.println(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void update(User user) {
        String qry = "UPDATE `user` SET `nom` = ?, `prenom` = ?, `role` = ?, `verified` = ?, " +
                "`adresse` = ?, `type_vehicule` = ?, `email` = ?, `password` = ?, " +
                "`num_tel` = ?, `cin` = ? WHERE `idUser` = ?";

        try (PreparedStatement statement = conn.prepareStatement(qry)) {
            String hashedPassword;
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            } else {

                System.out.println("Mot de passe vide, mise à jour de l'utilisateur sans changer le mot de passe.");
                hashedPassword = user.getPassword();
            }

            // Remplacer les valeurs dans la requête
            statement.setString(1, user.getNom());
            statement.setString(2, user.getPrenom());
            statement.setString(3, user.getRole().toString());
            statement.setInt(4, user.isVerified() ? 1 : 0);
            statement.setString(5, user.getAdresse());
            statement.setString(6, user.getType_vehicule().toString());
            statement.setString(7, user.getEmail());
            statement.setString(8, hashedPassword);
            statement.setString(9, user.getNum_tel());
            statement.setString(10, user.getCin());
            statement.setInt(11, user.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("No user found with ID " + user.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String qry = "DELETE FROM `user` WHERE `idUser` = " + id;
        try (PreparedStatement statement = conn.prepareStatement(qry)) {
            // Exécuter la requête
            statement.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getById(int id) {
        String query = "SELECT * FROM `user` WHERE `idUser` = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("idUser"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        role_user.valueOf(rs.getString("role")),
                        rs.getBoolean("verified"),
                        rs.getString("adresse"),
                        type_vehicule.valueOf(rs.getString("type_vehicule")),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("num_tel"),
                        rs.getString("cin")
                );
                System.out.println("Utilisateur trouvé : " + user);
                return user;
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> search(String criteria) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM `user` WHERE `cin` LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            String searchTerm = "%" + criteria + "%";
            stmt.setString(1, searchTerm);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("idUser"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                       models.role_user.valueOf(rs.getString("role")),  // Fetch the role
                        rs.getBoolean("verified"),  // Fetch the verified status
                        rs.getString("adresse"),
                        models.type_vehicule.valueOf(rs.getString("type_vehicule")),  // Fetch the vehicle type
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("num_tel"),
                        rs.getString("cin")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé pour le critère : " + criteria);
        } else {
            System.out.println("Nombre d'utilisateurs trouvés : " + users.size());
            for (User user : users) {
                System.out.println("Utilisateur trouvé : ");
                System.out.println("Nom: " + user.getNom());
                System.out.println("Prénom: " + user.getPrenom());
                System.out.println("Role: " + user.getRole());
                System.out.println("Vérifié: " + user.isVerified());
                System.out.println("Adresse: " + user.getAdresse());
                System.out.println("Type de véhicule: " + user.getType_vehicule());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Numéro de téléphone: " + user.getNum_tel());
                System.out.println("CIN: " + user.getCin());
                System.out.println();
            }
        }

        return users;
    }

    public boolean existsCin(String cin) {
        // Ici, tu effectues une requête pour vérifier si le CIN existe déjà
        String query = "SELECT COUNT(*) FROM `user` WHERE `cin` = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {  // Utilisation de la connexion existante 'conn'
            stmt.setString(1, cin); // On remplace le ? par le CIN

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Si le résultat est supérieur à 0, cela signifie que le CIN existe
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Si une exception se produit ou aucun résultat n'est trouvé
    }


}
