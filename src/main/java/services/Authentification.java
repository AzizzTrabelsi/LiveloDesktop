package services;

import models.User;
import org.mindrot.jbcrypt.BCrypt;
import utils.MyDatabase;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import net.minidev.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;


public class Authentification implements interfaces.IServiceAuth {
    Connection conn = MyDatabase.getInstance().getConnection();

    @Override
    public String login(String cin, String password) {
        String query = "SELECT * FROM `user` WHERE `cin` = ?";  // Change to search by `cin`
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cin);  // Use `cin` instead of `email`
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    // Create User object
                    User user = new User(
                            rs.getInt("idUser"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                           models.role_user.valueOf(rs.getString("role")),
                            rs.getBoolean("verified"), // verified field
                            rs.getString("adresse"),
                            models.type_vehicule.valueOf(rs.getString("type_vehicule")), // type_vehicule field
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("num_tel"),
                            rs.getString("cin")
                    );

                    String token = generateToken(user);
                    System.out.println("Connexion réussie. Token généré : " + token);

                    decodeToken(token);

                    return token;
                } else {
                    System.out.println("Mot de passe incorrect.");
                }
            } else {
                System.out.println("Utilisateur non trouvé avec le CIN : " + cin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateToken(User user) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

            JSONObject payload = new JSONObject();
            payload.put("idUser", user.getId());
            payload.put("nom", user.getNom());
            payload.put("prenom", user.getPrenom());
            payload.put("role", user.getRole().toString());
            payload.put("adresse", user.getAdresse());
            payload.put("email", user.getEmail());
            payload.put("num_tel", user.getNum_tel());
            payload.put("cin", user.getCin());
            payload.put("verified", user.isVerified());
            payload.put("type_vehicule", user.getType_vehicule().toString());

            JWSObject jwsObject = new JWSObject(header, new Payload(payload));

            String secretKey = "livelo_256_bit_long_secret_key_here";  // Ensure this is at least 32 characters long.
            MACSigner signer = new MACSigner(secretKey.getBytes());

            jwsObject.sign(signer);

            return jwsObject.serialize();  // Return the serialized token
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void decodeToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);

            String payload = jwsObject.getPayload().toString();

            System.out.println("Informations utilisateur extraites du token :");
            System.out.println(payload);  // Print the entire payload

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
