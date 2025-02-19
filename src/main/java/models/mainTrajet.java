package test;

import models.Trajet;
import models.Zone;
import services.CrudTrajet;
import services.CrudZone;
import models.etatTrajet;

public class mainTrajet {
    public static void main(String[] args) {
        CrudTrajet crudTrajet = new CrudTrajet();
        CrudZone crudZone = new CrudZone();

        // Récupérer une zone existante (remplace l'ancien idZone)
        Zone zone1 = crudZone.getById(8); // Assurez-vous que cette zone existe dans la DB
        Zone zone2 = crudZone.getById(3);
        Zone zone3 = crudZone.getById(5);

        if (zone1 == null || zone2 == null || zone3 == null) {
            System.out.println("Erreur : Une ou plusieurs zones n'existent pas.");
            return;
        }

        // Création de trois trajets avec la zone associée
        Trajet trajet1 = new Trajet(
                "Avenue Habib Bourguiba",
                "Rue de Marseille",
                zone1,  // Utilisation de l'objet Zone
                5,
                0,
                3.2f,
                10.5f,
                etatTrajet.en_attente
        );

        Trajet trajet2 = new Trajet(
                "Place Barcelone",
                "Cité Ennasr",
                zone2,
                6,
                0,
                7.8f,
                20.0f,
                etatTrajet.en_cours
        );

        Trajet trajet3 = new Trajet(
                "Lac 2",
                "Centre Urbain Nord",
                zone3,
                4,
                0,
                5.4f,
                15.2f,
                etatTrajet.termine
        );

        crudTrajet.add(trajet1);
        crudTrajet.add(trajet2);
        crudTrajet.add(trajet3);

        crudTrajet.getAll();
    }
}
