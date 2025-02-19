package tn.esprit.test;

import tn.esprit.models.Avis;
import tn.esprit.models.Livraison;
import tn.esprit.services.CrudAvis;
import tn.esprit.services.CrudLivraison;
import java.util.Date;

public class MainAvis {
    public static void main(String[] args) {

        // Instancier CrudLivraison pour récupérer une livraison spécifique
        CrudLivraison crudLivraison = new CrudLivraison();
        Livraison livraison = crudLivraison.getById(18); // Exemple d'ID de livraison (à ajuster)

        if (livraison == null) {
            System.out.println("Aucune livraison trouvée avec cet ID.");
            return;
        }

        // Création d'un nouvel avis sans spécifier d'ID
        Avis nouvelAvis = new Avis(
                50,                          // ID de l'utilisateur (createdBy)
                livraison,                   // Livraison associée
                new Date(),                  // Date de création de l'avis
                "Très satisfait de la livraison, tout était parfait !"  // Description de l'avis
        );


        // Instancier le service CrudAvis
        CrudAvis crudAvis = new CrudAvis();

        // Ajouter le nouvel avis
        crudAvis.add(nouvelAvis);

        // Supprimer un avis spécifique (exemple d'ID)
        crudAvis.delete(15);

        // Mise à jour de l'avis (par exemple changer la description)
        nouvelAvis.setDescription("Livraison rapide, mais l'emballage pourrait être amélioré.");
        crudAvis.update(nouvelAvis);

        // Récupération de tous les avis
        System.out.println("Récupération de tous les avis :");
        crudAvis.getAll();

        // Récupération d'un avis spécifique par ID
        crudAvis.getById(19);

        // Recherche d'avis par description
        System.out.println("Recherche d'avis avec le critère 'livraison' :");
        crudAvis.search("livraison");
    }
}
