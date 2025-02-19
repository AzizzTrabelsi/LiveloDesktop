package models;

import java.util.Date;

public class Livraison {
    private int id;
    private int commandeId;
    private int createdBy;
    private Date createdDate;
    private int factureId;

    public Livraison() {}

    public Livraison(int id, int commandeId, int createdBy, Date createdDate, int factureId) {
        this.id = id;
        this.commandeId = commandeId;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.factureId = factureId;
    }

    public Livraison(int commandeId, int createdBy, Date createdDate, int factureId) {
        this.commandeId = commandeId;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.factureId = factureId;
    }

    public int getId() {
        return id;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public int getFactureId() {
        return factureId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setFactureId(int factureId) {
        this.factureId = factureId;
    }




    @Override
    public String toString() {
        return "Livraison{" +
                "id=" + id +
                ", commandeId=" + commandeId +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", factureId=" + factureId +
                '}';
    }
}