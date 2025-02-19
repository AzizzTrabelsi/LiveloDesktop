package tests;

import models.Livraison;
import services.CrudLivraison;

import java.util.Date;

public class MainLivraison {

    public static void main(String[] args) {
        CrudLivraison serviceLivraison = new CrudLivraison();

        Livraison livraison1= new Livraison(
                2,
                22,
                new Date(),
                2
        );

        Livraison livraison2= new Livraison(
                2,
                17,
                new Date(),
                2
        );
        serviceLivraison.add(livraison1);
        serviceLivraison.add(livraison2);
        serviceLivraison.getAll();

        livraison1.setCreatedBy(17);
        serviceLivraison.update(livraison1);
        System.out.println(serviceLivraison.getAll());


        serviceLivraison.delete(1);
        System.out.println(serviceLivraison.getAll());

        //serviceLivraison.update(new Livraison(1, 17, new Date(), 2));


       Livraison livraison = serviceLivraison.getById(2);
        if (livraison != null) {
            System.out.println("Test get by ID : " + livraison);
        } else {
            System.out.println("Aucune livraison trouvée avec cet ID.");
        }

       // System.out.println(serviceLivraison.getAll());
    }

}
