package models;


import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Commande {
    private int id_Commande;
    private String adresse_dep;
    private String adresse_arr;
    private String type_livraison;
    private Timestamp horaire;
    private statutlCommande statut;
    private int created_by;

    private List<Article> articles=new ArrayList<Article>();
    public Commande(int id_Commande, String adresse_dep, String adresse_arr, String type_livraison, Timestamp horaire, statutlCommande statut, int created_by) {
        this.id_Commande = id_Commande;
        this.adresse_dep = adresse_dep;
        this.adresse_arr = adresse_arr;
        this.type_livraison = type_livraison;
        this.horaire = horaire;
        this.statut = statut;
        this.created_by = created_by;
    }

    public Commande(String adresse_dep, String adresse_arr, String type_livraison, Timestamp horaire, statutlCommande statut, int created_by) {
        this.adresse_dep = adresse_dep;
        this.adresse_arr = adresse_arr;
        this.type_livraison = type_livraison;
        this.horaire = horaire;
        this.statut = statut;
        this.created_by = created_by;
    }

    public Commande() {
    }
    @ManyToMany(cascade ={ CascadeType.ALL,CascadeType.MERGE})
    @JoinTable(
            name="articlecommande",
            joinColumns = @JoinColumn(name="idCommande"),
            inverseJoinColumns = @JoinColumn(name="idArticle")
    )
    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getId_Commande() {
        return id_Commande;
    }

    public String getAdresse_dep() {
        return adresse_dep;
    }

    public String getAdresse_arr() {
        return adresse_arr;
    }

    public String getType_livraison() {
        return type_livraison;
    }

    public Timestamp getHoraire() {
        return horaire;
    }

    public statutlCommande getStatut() {
        return statut;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setId_Commande(int id_Commande) {
        this.id_Commande = id_Commande;
    }

    public void setAdresse_dep(String adresse_dep) {
        this.adresse_dep = adresse_dep;
    }

    public void setAdresse_arr(String adresse_arr) {
        this.adresse_arr = adresse_arr;
    }

    public void setType_livraison(String type_livraison) {
        this.type_livraison = type_livraison;
    }

    public void setHoraire(Timestamp horaire) {
        this.horaire = horaire;
    }

    public void setStatut(statutlCommande statut) {
        this.statut = statut;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }
    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id_Commande +
                ", adresse de depart=" + adresse_dep +
                ", adresse d arrive='" + adresse_arr + '\'' +
                ", Type de livraison='" + type_livraison + '\'' +
                ", Horaire='" + horaire + '\'' +
                ", Statut='" + statut + '\'' +
                ", Created by='" + created_by + '\'' +
                "}\n";
    }
}
