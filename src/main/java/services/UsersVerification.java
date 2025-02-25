package services;

import interfaces.IServiceUsersVerification;
import models.User;
import models.role_user;
import models.type_vehicule;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersVerification implements IServiceUsersVerification {
    Connection conn = MyDatabase.getInstance().getConnection();

    @Override
    public List<User> getUnverifiedUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user WHERE verified = false";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idUser");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String roleString = rs.getString("role");
                String typeVehiculeString = rs.getString("type_vehicule");
                boolean verified = rs.getBoolean("verified");
                String adresse = rs.getString("adresse");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String num_tel = rs.getString("num_tel");
                String cin = rs.getString("cin");

                role_user role = role_user.valueOf(roleString);

                type_vehicule typeVehicule = null;
                if (typeVehiculeString != null && !typeVehiculeString.trim().isEmpty()) {
                    try {
                        typeVehicule = type_vehicule.valueOf(typeVehiculeString);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Valeur inconnue pour type_vehicule: " + typeVehiculeString);
                        typeVehicule = null;
                    }
                }

                User user = new User(id, nom, prenom, role, verified, adresse, typeVehicule, email, password, num_tel, cin);
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
    public void acceptUser(int userId) {
        String query = "UPDATE user SET verified = true WHERE idUser = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refuseUser(int id) {
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
    public List<User> search(String criteria) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM `user` WHERE `cin` LIKE ? AND `verified` = false";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            String searchTerm = "%" + criteria + "%";
            stmt.setString(1, searchTerm);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idUser");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String roleString = rs.getString("role");
                String typeVehiculeString = rs.getString("type_vehicule"); // Peut être NULL
                boolean verified = rs.getBoolean("verified");
                String adresse = rs.getString("adresse");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String num_tel = rs.getString("num_tel");
                String cin = rs.getString("cin");

                // Convertir roleString en enum
                role_user role = role_user.valueOf(roleString);

                // Gérer le cas où typeVehiculeString est NULL ou invalide
                type_vehicule typeVehicule = null;
                if (typeVehiculeString != null && !typeVehiculeString.trim().isEmpty()) {
                    try {
                        typeVehicule = type_vehicule.valueOf(typeVehiculeString);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Valeur inconnue pour type_vehicule: " + typeVehiculeString);
                        typeVehicule = null; // Gérer une valeur invalide si elle est présente dans la BD
                    }
                }

                // Créer l'objet User
                User user = new User(id, nom, prenom, role, verified, adresse,
                        typeVehicule, email, password, num_tel, cin);

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur non vérifié trouvé pour le critère : " + criteria);
        } else {
            System.out.println("Nombre d'utilisateurs non vérifiés trouvés : " + users.size());
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
}