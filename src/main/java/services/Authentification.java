package services;

import models.User;
import net.minidev.json.JSONValue;
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
        String query = "SELECT * FROM user WHERE cin = ?";  // Recherche par CIN
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cin);  // Utilisation de CIN
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    // Création de l'objet User
                    String typeVehicule = rs.getString("type_vehicule");
                    models.type_vehicule vehicule = null;

                    // Si type_vehicule n'est pas null et est valide, on l'assigne à l'objet user
                    if (typeVehicule != null) {
                        try {
                            vehicule = models.type_vehicule.valueOf(typeVehicule);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Valeur de type_vehicule invalide, valeur par défaut assignée.");
                            // Attribuer une valeur par défaut ou null si nécessaire
                        }
                    }

                    User user = new User(
                            rs.getInt("idUser"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            models.role_user.valueOf(rs.getString("role")),
                            rs.getBoolean("verified"), // Champ verified
                            rs.getString("adresse"),
                            vehicule, // Type de véhicule (peut être null)
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

            // Vérifiez si type_vehicule est null et fournissez une valeur par défaut
            String typeVehicule = (user.getType_vehicule() != null) ? user.getType_vehicule().toString() : "INCONNU";
            payload.put("type_vehicule", typeVehicule);  // Valeur par défaut "INCONNU" si null

            JWSObject jwsObject = new JWSObject(header, new Payload(payload));

            String secretKey = "livelo_256_bit_long_secret_key_here";  // Assurez-vous que cette clé est au moins de 32 caractères
            MACSigner signer = new MACSigner(secretKey.getBytes());

            jwsObject.sign(signer);

            return jwsObject.serialize();  // Retourne le token sérialisé
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }


    public JSONObject decodeToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            String payload = jwsObject.getPayload().toString();

            // Parse the JSON string into a JSONObject
            return (JSONObject) JSONValue.parse(payload);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }}
}
