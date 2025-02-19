package test;

import models.Zone;
import models.Trajet;
import models.etatTrajet;
import services.CrudZone;
import services.CrudTrajet;

import java.util.List;



public class mainZone {
    public static void main(String[] args) {
        CrudZone crudZone = new CrudZone();
        CrudTrajet crudTrajet = new CrudTrajet();

        // 1. Ajouter une nouvelle zone
        Zone zone = new Zone("Zone Test", 36.8065, 10.1815, 5.0f, 50, 0, 10);
        crudZone.add(zone);

        // Récupérer l'ID de la zone nouvellement créée
        List<Zone> zones = crudZone.getAll();
        Zone addedZone = zones.get(zones.size() - 1); // Prendre la dernière zone ajoutée

        // Ajouter des trajets dans la base en leur attribuant l'ID de la zone
        Trajet trajet1 = new Trajet("Point A", "Point B", addedZone, 1, 1, 2.5f, 15.0f, etatTrajet.en_cours);
        Trajet trajet2 = new Trajet("Point C", "Point D", addedZone, 2, 2, 4.0f, 20.0f, etatTrajet.termine);

        crudTrajet.add(trajet1);
        crudTrajet.add(trajet2);

        // 2. Afficher toutes les zones avec leurs trajets
        System.out.println("Liste des zones avec leurs trajets :");
        zones = crudZone.getAll(); // Recharger les zones avec les trajets
        for (Zone z : zones) {
            System.out.println(z);
            for (Trajet t : z.getTrajets()) {
                System.out.println("  -> " + t);
            }
        }

        // 3. Mettre à jour la zone
        /*if (!zones.isEmpty()) {
            Zone firstZone = zones.get(0);
            firstZone.setNom("Zone Modifiée");
            crudZone.update(firstZone);
        }*/

        // 4. Rechercher une zone par ID
        if (!zones.isEmpty()) {
            int idZone = zones.get(0).getIdZone();
            Zone foundZone = crudZone.getById(idZone);
            System.out.println("Zone trouvée: " + foundZone);
        }

        // 5. Rechercher une zone par critère (nom)
        System.out.println("Recherche de zones contenant 'Test' :");
        List<Zone> searchedZones = crudZone.search("Test");
        for (Zone z : searchedZones) {
            System.out.println(z);
        }

        // 6. Supprimer une zone (et ses trajets)
        /*if (!zones.isEmpty()) {
            int idToDelete = zones.get(0).getIdZone();
            crudZone.delete(idToDelete);
            System.out.println("Zone supprimée avec ID : " + idToDelete);
        }*/
    }
}



