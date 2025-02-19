package tn.esprit.models;


import java.util.Date;

public class Avis {

    private int idAvis, createdBy;
    private Date createdAt;
    private String description;
    private Livraison livraison; // Relation avec Livraison, puisque plusieurs avis peuvent être associés à une livraison

    public Avis() {}

    public Avis(int idAvis, int createdBy, Livraison livraison, Date createdAt, String description) {
        this.idAvis = idAvis;
        this.createdBy = createdBy;
        this.livraison = livraison;
        this.createdAt = createdAt;
        this.description = description;
    }
    public Avis(int createdBy, Livraison livraison, Date createdAt, String description) {
        this.createdBy = createdBy;
        this.livraison = livraison;
        this.createdAt = createdAt;
        this.description = description;
    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Livraison getLivraison() {
        return livraison;
    }

    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
    }

    @Override
    public String toString() {
        return "Avis{idAvis=" + idAvis + ", createdBy=" + createdBy + ", description='" + description + "'}";
    }
}
