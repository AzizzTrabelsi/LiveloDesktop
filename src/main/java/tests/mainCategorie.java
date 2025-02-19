package tests;

import models.Categorie;
import services.CrudCategorie;

public class mainCategorie {
    public static void main(String[] args) {

        Categorie nouvelleCategorie = new Categorie(
                "lemino",
                "description delemino",
                "img1.jpg",
                50
        );

        CrudCategorie crudCategorie = new CrudCategorie();
        crudCategorie.delete(10);
        crudCategorie.add(nouvelleCategorie);
        crudCategorie.getAll();


        nouvelleCategorie.setNom("amine");
        nouvelleCategorie.setDescription("06");
        crudCategorie.update(nouvelleCategorie);
        crudCategorie.getAll();
        crudCategorie.getById(8);



        crudCategorie.search("amine");

    }
}
