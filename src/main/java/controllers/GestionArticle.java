package controllers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Article;


import services.CrudArticle;
import services.CrudUser;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GestionArticle implements Initializable {

    private CrudArticle su = new CrudArticle();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Afficher tous les utilisateurs au démarrage
        Show(null);

        // Ajouter un écouteur sur le champ de recherche
        anSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // Si le champ de recherche est vide, afficher tous les utilisateurs
                loadArticle();
            } else {
                // Sinon, effectuer une recherche
                searchArticle(newValue);
            }
        });
    }

    @FXML
    void Show(ActionEvent showEvent) {
        hbHedha.getChildren().clear();  // Clear existing content

        // Récupérer tous les articles
        List<Article> articleList = su.getAll();

        // Parcours de la liste des articles
        for (Article article : articleList) {
            HBox artRow = new HBox(4);
            artRow.setPrefHeight(32.0);
            artRow.setPrefWidth(765.0);

            // Label pour l'ID de la catégorie (associée à l'article)
            Label lblIdCategorie = new Label(String.valueOf(article.getCategorie().getId_categorie()));
            lblIdCategorie.setMinWidth(80);
            lblIdCategorie.setMaxWidth(80);

            // Label pour l'URL de l'image de l'article
            Label lblurlimg = new Label(article.getUrlImage());
            lblurlimg.setMinWidth(80);
            lblurlimg.setMaxWidth(80);

            // Label pour le nom de l'article
            Label lblNom = new Label(article.getNom());
            lblNom.setMinWidth(80);
            lblNom.setMaxWidth(80);

            // Label pour le prix de l'article
            Label lblPrix = new Label(String.valueOf(article.getPrix()));  // Correction
            lblPrix.setMinWidth(80);
            lblPrix.setMaxWidth(80);

            // Label pour la description de l'article
            Label lblDesc = new Label(article.getDescription());
            lblDesc.setMinWidth(130);
            lblDesc.setMaxWidth(130);

            // Label pour le statut de l'article
            Label lblStatu = new Label(article.getStatut().name());
            lblStatu.setMinWidth(105);
            lblStatu.setMaxWidth(105);

            // Label pour la quantité de l'article
            Label lblQuant = new Label(String.valueOf(article.getQuantite()));  // Correction
            lblQuant.setMinWidth(80);
            lblQuant.setMaxWidth(80);

            // Label pour la date de création de l'article
            Label lblcreatedat = new Label(article.getCreatedAt().toString());
            lblcreatedat.setMinWidth(80);
            lblcreatedat.setMaxWidth(80);

            // Ajouter tous les labels dans le HBox (ligne)
            artRow.getChildren().addAll(lblIdCategorie, lblurlimg, lblNom, lblPrix, lblDesc, lblStatu, lblQuant, lblcreatedat);

            // Ajouter un événement au clic sur la ligne pour afficher les détails de l'article
            artRow.setOnMouseClicked(event -> showArticleDetailsPopup(article));

            // Ajouter cette ligne à la vue principale (vListUsers)
            vListUsers.getChildren().add(artRow);
        }
    }


    @FXML
    private void showArticleDetailsPopup(Article article) {
        System.out.println("article sélectionné : " + article.getIdArticle() + " " + article.getNom());

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de l'utilisateur");
        alert.setHeaderText("Informations sur " + article.getIdArticle() + " " + article.getNom());
        alert.setContentText("nom de larticle : " + article.getNom() + "\n" +
                "description : " + article.getDescription() + "\n" +
                "prix : " + article.getPrix() + "\n" +
                "statut : " + article.getStatut().name() + "\n" +
                "quantité : " + article.getQuantite() + "\n" +
                "date de creation : " + article.getCreatedAt());

        ButtonType updateButton = new ButtonType("Mettre à jour");
        ButtonType deleteButton = new ButtonType("Supprimer");

        alert.getButtonTypes().setAll(updateButton, deleteButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == updateButton) {
                showUpdatePopup(article);
                System.out.println("Mettre à jour les informations de l'article.");
            } else if (response == deleteButton) {
                // Afficher une pop-up de confirmation pour la suppression
                javafx.scene.control.Alert confirmationAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation de la suppression");
                confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet article ?");
                confirmationAlert.setContentText("Cette action est irréversible.");

                // Ajouter les boutons de confirmation
                ButtonType yesButton = new ButtonType("Oui");
                ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

                confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                confirmationAlert.showAndWait().ifPresent(confirmationResponse -> {
                    if (confirmationResponse == yesButton) {
                        // Effectuer la suppression
                        su.delete(article.getIdArticle());
                        System.out.println("article supprimé.");
                        loadArticle();
                    } else {
                        System.out.println("Suppression annulée.");
                    }
                });
            }
        });
    }

    @FXML
    private void showUpdatePopup(Article article) {
        Dialog<Article> dialog = new Dialog<>();
        dialog.setTitle("Mettre à jour l'larticle");
        dialog.setHeaderText("Modifier les informations de l'article");

        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomField = new TextField(article.getNom());
        TextField urlimgField = new TextField(article.getUrlImage());
        TextField descField = new TextField(article.getDescription());
        TextField prixField =new TextField(Float.toString(article.getPrix()));

        TextField statutField = new TextField(article.getStatut().name());
        TextField createdatField = new TextField(article.getCreatedAt().toString());
        TextField quantField = new TextField(String.valueOf(article.getQuantite()));
        grid.add(new Label("nom article:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("url image:"), 0, 1);
        grid.add(urlimgField, 1, 1);
        grid.add(new Label("description:"), 0, 2);
        grid.add(descField, 1, 2);
        grid.add(new Label("prix:"), 0, 3);
        grid.add(prixField, 1, 3);
        grid.add(new Label("statut:"), 0, 4);
        grid.add(statutField, 1, 4);
        grid.add(new Label("created at:"), 0, 5);
        grid.add(createdatField, 1, 5);
        grid.add(new Label(" quantite:"), 0, 6);
        grid.add(quantField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                // Update the user object with the new values
                article.setNom(nomField.getText());
                article.setUrlImage(urlimgField.getText());
                article.setDescription(descField.getText());
                article.setPrix(Float.parseFloat(prixField.getText()));

                article.setStatut(article.statut.valueOf(statutField.getText()));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date createdAt = dateFormat.parse(createdatField.getText()); // Convertir String en Date
                    article.setCreatedAt(createdAt); // Affecter la date à l'article
                } catch (ParseException e) {
                    System.out.println("Erreur : Format de date invalide. Veuillez entrer la date sous la forme YYYY-MM-DD.");
                }





                // Save the updated user to the database
                su.update(article);

                // Refresh the user list in the UI
                loadArticle(); // Call loadUsers to refresh the list

                return article;
            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    public void loadArticle() {
        System.out.println("Chargement des articles...");

        // Nettoyer la liste actuelle des utilisateurs
        vListUsers.getChildren().clear();

        HBox headerRow = new HBox(4);
        headerRow.setPrefHeight(32.0);
        headerRow.setPrefWidth(765.0);

        headerRow.setStyle("-fx-background-color: #398c3e; -fx-padding: 10px;");

        Label lblHeaderIdcat = new Label("id category");
        lblHeaderIdcat.setMinWidth(80);
        lblHeaderIdcat.setMaxWidth(80);
        lblHeaderIdcat.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderurlimg = new Label("Url image");
        lblHeaderurlimg.setMinWidth(80);
        lblHeaderurlimg.setMaxWidth(80);
        lblHeaderurlimg.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeadernom = new Label("nom");
        lblHeadernom.setMinWidth(80);
        lblHeadernom.setMaxWidth(80);
        lblHeadernom.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderPrix = new Label("prix");
        lblHeaderPrix.setMinWidth(80);
        lblHeaderPrix.setMaxWidth(80);
        lblHeaderPrix.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderDesc = new Label("description");
        lblHeaderDesc.setMinWidth(130);
        lblHeaderDesc.setMaxWidth(130);
        lblHeaderDesc.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderquant = new Label("quantite");
        lblHeaderquant.setMinWidth(105);
        lblHeaderquant.setMaxWidth(105);
        lblHeaderquant.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeaderStat = new Label("statut");
        lblHeaderStat.setMinWidth(80);
        lblHeaderStat.setMaxWidth(80);
        lblHeaderStat.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        Label lblHeadercreated = new Label("created at");
        lblHeadercreated.setMinWidth(80);
        lblHeadercreated.setMaxWidth(80);
        lblHeadercreated.setStyle("-fx-text-fill: black;"); // Changer la couleur du texte en blanc

        headerRow.getChildren().addAll(lblHeaderIdcat, lblHeaderurlimg, lblHeadernom, lblHeaderPrix, lblHeaderDesc, lblHeaderquant, lblHeaderStat, lblHeadercreated);

        vListUsers.getChildren().add(headerRow);

        List<Article> articleList = su.getAll();


        for (Article article : articleList) {
            HBox artRow = new HBox(4);
            artRow.setPrefHeight(32.0);
            artRow.setPrefWidth(765.0);

            Label lblidcat = new Label(String.valueOf(article.getCategorie().getId_categorie()));
            lblidcat.setMinWidth(80);
            lblidcat.setMaxWidth(80);


            Label lblUrlimg = new Label(article.getUrlImage());
            lblUrlimg.setMinWidth(80);
            lblUrlimg.setMaxWidth(80);

            Label lblNom = new Label(article.getNom());
            lblNom.setMinWidth(80);
            lblNom.setMaxWidth(80);

            Label lblPrix = new Label(String.valueOf(article.getPrix()));
            lblPrix.setMinWidth(80);
            lblPrix.setMaxWidth(80);


            Label lblDesc = new Label(article.getDescription());
            lblDesc.setMinWidth(130);
            lblDesc.setMaxWidth(130);
            Label lblquant = new Label(String.valueOf(article.getQuantite()));
            lblquant.setMinWidth(130);
            lblquant.setMaxWidth(130);

            Label lblstat = new Label(article.getStatut().name());
            lblstat.setMinWidth(105);
            lblstat.setMaxWidth(105);



            Label lblcreatedat = new Label(article.getCreatedAt().toString());
            lblcreatedat.setMinWidth(80);
            lblcreatedat.setMaxWidth(80);

            artRow.getChildren().addAll(lblidcat, lblUrlimg, lblNom, lblPrix, lblDesc, lblquant, lblstat, lblcreatedat);

            Button deleteButton = new Button("Supprimer");
            deleteButton.setOnAction(event -> {
                javafx.scene.control.Alert confirmationAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation de la suppression");
                confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet article ?");
                confirmationAlert.setContentText("Cette action est irréversible.");

                ButtonType yesButton = new ButtonType("Oui");
                ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

                confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                confirmationAlert.showAndWait().ifPresent(confirmationResponse -> {
                    if (confirmationResponse == yesButton) {
                        // Effectuer la suppression
                        su.delete(article.getIdArticle());
                        System.out.println("artilce supprimé.");
                        loadArticle();
                    } else {
                        System.out.println("Suppression annulée.");
                    }
                });
            });

            artRow.getChildren().add(deleteButton);

            artRow.setOnMouseClicked(event -> showArticleDetailsPopup(article));

            vListUsers.getChildren().add(artRow);
        }
    }

    @FXML
    private void searchArticle(String criteria) {
        System.out.println("Recherche des utilisateurs pour le critère : " + criteria);

        vListUsers.getChildren().clear();

        HBox headerRow = new HBox(4);
        headerRow.setPrefHeight(32.0);
        headerRow.setPrefWidth(765.0);
        headerRow.setStyle("-fx-background-color: #398c3e; -fx-padding: 10px;");

        Label lblHeaderPrenom = new Label("First name");
        lblHeaderPrenom.setMinWidth(80);
        lblHeaderPrenom.setMaxWidth(80);
        lblHeaderPrenom.setStyle("-fx-text-fill: black;");

        Label lblHeaderNom = new Label("Last name");
        lblHeaderNom.setMinWidth(80);
        lblHeaderNom.setMaxWidth(80);
        lblHeaderNom.setStyle("-fx-text-fill: black;");

        Label lblHeaderCin = new Label("CIN");
        lblHeaderCin.setMinWidth(80);
        lblHeaderCin.setMaxWidth(80);
        lblHeaderCin.setStyle("-fx-text-fill: black;");

        Label lblHeaderAdress = new Label("Address");
        lblHeaderAdress.setMinWidth(80);
        lblHeaderAdress.setMaxWidth(80);
        lblHeaderAdress.setStyle("-fx-text-fill: black;");

        Label lblHeaderEmail = new Label("Email");
        lblHeaderEmail.setMinWidth(130);
        lblHeaderEmail.setMaxWidth(130);
        lblHeaderEmail.setStyle("-fx-text-fill: black;");

        Label lblHeaderRole = new Label("Role");
        lblHeaderRole.setMinWidth(105);
        lblHeaderRole.setMaxWidth(105);
        lblHeaderRole.setStyle("-fx-text-fill: black;");

        Label lblHeaderVerified = new Label("Verified");
        lblHeaderVerified.setMinWidth(80);
        lblHeaderVerified.setMaxWidth(80);
        lblHeaderVerified.setStyle("-fx-text-fill: black;");

        Label lblHeaderTransport = new Label("Transport");
        lblHeaderTransport.setMinWidth(80);
        lblHeaderTransport.setMaxWidth(80);
        lblHeaderTransport.setStyle("-fx-text-fill: black;");

        headerRow.getChildren().addAll(lblHeaderPrenom, lblHeaderNom, lblHeaderCin, lblHeaderAdress, lblHeaderEmail, lblHeaderRole, lblHeaderVerified, lblHeaderTransport);
        vListUsers.getChildren().add(headerRow);

        List<Article> articleList = su.search(criteria);

        for (Article article : articleList) {
            HBox artRow = new HBox(4);
            artRow.setPrefHeight(32.0);
            artRow.setPrefWidth(765.0);
            artRow.setStyle("-fx-padding: 10px;");

            Label lblidcat = new Label(String.valueOf(article.getCategorie().getId_categorie()));
            lblidcat.setMinWidth(80);
            lblidcat.setMaxWidth(80);;

            Label lblurlimg = new Label(article.getUrlImage());
            lblurlimg.setMinWidth(80);
            lblurlimg.setMaxWidth(80);

            Label lblnom = new Label(article.getNom());
            lblnom.setMinWidth(80);
            lblnom.setMaxWidth(80);

            Label lblPrix = new Label(String.valueOf(article.getPrix()));
            lblPrix.setMinWidth(80);
            lblPrix.setMaxWidth(80);


            Label lbldesc = new Label(article.getDescription());
            lbldesc.setMinWidth(130);
            lbldesc.setMaxWidth(130);
            Label lblquant = new Label(String.valueOf(article.getQuantite()));
            lblquant.setMinWidth(130);
            lblquant.setMaxWidth(130);


            Label lblstat = new Label(article.getStatut().toString());
            lblstat.setMinWidth(105);
            lblstat.setMaxWidth(105);


            Label lblcreatedat = new Label(article.getCreatedAt().toString());
            lblcreatedat.setMinWidth(80);
            lblcreatedat.setMaxWidth(80);

            artRow.getChildren().addAll(lblidcat, lblurlimg, lblnom, lblPrix, lbldesc, lblquant ,lblstat, lblcreatedat);

            vListUsers.getChildren().add(artRow);
        }
    }
    public void showArticlesForCategory(int idCategorie) {
        try {
            List<Article> articles = su.getArticlesByCategoryId(idCategorie); // Assurez-vous que cette méthode existe

            Stage popupStage = new Stage();
            popupStage.setTitle("Articles de la catégorie");

            VBox popupLayout = new VBox(10);
            popupLayout.setPadding(new Insets(10));

            Label titleLabel = new Label("Articles de la catégorie : " + idCategorie);
            popupLayout.getChildren().add(titleLabel);

            ListView<String> articleListView = new ListView<>();
            for (Article article : articles) {
                articleListView.getItems().add(article.getNom());
            }
            popupLayout.getChildren().add(articleListView);

            Button closeButton = new Button("Fermer");
            closeButton.setOnAction(e -> popupStage.close());
            popupLayout.getChildren().add(closeButton);

            Scene popupScene = new Scene(popupLayout, 300, 250);
            popupStage.setScene(popupScene);
            popupStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Une erreur est survenue.");
            errorAlert.showAndWait();
        }
    }



    @FXML
    private HBox headerhb;

    @FXML
    private HBox hbActions;

    @FXML
    private AnchorPane anCategories;

    @FXML
    private AnchorPane anCoverageArea;

    @FXML
    private AnchorPane anLogout;

    @FXML
    private AnchorPane anOrder;

    @FXML
    private AnchorPane anPendingUsers;

    @FXML
    private AnchorPane anRiders;

    @FXML
    private TextField anSearch;

    @FXML
    private AnchorPane anUsers;

    @FXML
    private ImageView imLogo;

    @FXML
    private VBox vListUsers;

    @FXML
    private HBox hbHedha;

    @FXML
    private void navigateToHome() {
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeAdmin.fxml"));
            Scene signUpScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) imLogo.getScene().getWindow();
            stage.setScene(signUpScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading SignUp.fxml.");
        }
    }

    @FXML
    public void normalEffect(javafx.scene.input.MouseEvent event) {
        ((AnchorPane) event.getSource()).setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

    }

    @FXML
    public void hoverEffect(javafx.scene.input.MouseEvent event) {
        ((AnchorPane) event.getSource()).setStyle("-fx-background-color: lightgrey; -fx-cursor: hand;");

    }

    @FXML
    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Scene SignInScene = new Scene(loader.load());

            Stage stage = (Stage) anLogout.getScene().getWindow();
            stage.setScene(SignInScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading SignUp.fxml.");
        }
    }
}
