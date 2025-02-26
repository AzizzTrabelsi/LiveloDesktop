package tests;

import models.Article;
import models.Categorie;
import models.statut_article;
import services.CrudArticle;
import services.CrudCategorie;
import java.util.Date;

public class mainArticle {
    public static void main(String[] args) {


        CrudCategorie crudCategorie = new CrudCategorie();


        Categorie categorie = crudCategorie.getById(27);

        if (categorie == null) {
            System.out.println("Aucune catégorie trouvée avec cet ID.");
            return;
        }


       Article article = new Article(
               0,
               "https://example.com/image.jpg",
                categorie,
               "CABLE USB ",
               120.99f,
                " 2mt",
                50,
                10,
                statut_article.on_stock,
                new Date(),
                50
        );


        CrudArticle crudArticle = new CrudArticle();




        crudArticle.add(article);
         //crudArticle.delete(8);

        // Mise à jour de l'article
         article.setPrix(99.99f);
         article.setQuantite(15);
        article.setStatut(statut_article.out_of_stock);
         crudArticle.update(article);

        // Récupération de tous les articles
        System.out.println("Récupération de tous les articles :");
        crudArticle.getAll();

        // Récupération d'un article spécifique par ID
        //crudArticle.getById(20);

        // Recherche d'articles par nom
        System.out.println("Recherche d'articles avec le critère 'Casque Bluetooth' :");
        crudArticle.search("Casque Bluetooth");
    }
}
