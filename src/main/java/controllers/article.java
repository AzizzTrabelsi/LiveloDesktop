package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Article;

public class article {

    @FXML
    private ImageView articleImage;

    @FXML
    private Label articleNom;

    @FXML
    private Label articlePrix;

    /*public void setData(Article article) {
        Image image=new Image(getClass().getResourceAsStream(article.getUrlImage()));
        articleImage.setImage(image);
        articleNom.setText(article.getNom());
        articlePrix.setText(String.valueOf(article.getPrix()));

    }*/
    public void setData(Article article) {

        System.out.println("🟢 setData() appelé pour : " + article.getNom());
        if (article == null) {
            System.out.println("⚠️ Article NULL reçu !");
            return;
        }

        articleNom.setText(article.getNom());
        articlePrix.setText(article.getPrix() + " TND");
        System.out.println("🖼️ Chemin de l'image : " + "/" + article.getUrlImage());

        // Charger l'image (si URL correcte)
        String imagePath = "/" + article.getUrlImage()+ ".jpg" ;
        Image img = new Image(getClass().getResourceAsStream(imagePath));
        System.out.println("🖼️ Chemin de l'image : " + "/" + article.getUrlImage());
        articleImage.setImage(img);

        System.out.println("🎉 Chargement de l'article : " + article.getNom());
    }

}
