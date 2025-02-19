package models;

import java.util.Date;

public class Facture {
    private int idFacture;
    private float montant;
    private Date datef;
    private type_paiement typePaiement;
    private int userId;
    private int commandeId;

    public Facture(float montant, Date datef, type_paiement typePaiement, int userId, int commandeId) {
        this.montant = montant;
        this.datef = datef;
        this.typePaiement = typePaiement;
        this.userId = userId;
        this.commandeId = commandeId;
    }

    public Facture(int idFacture, float montant, Date datef, type_paiement typePaiement, int userId, int commandeId) {
        this.idFacture = idFacture;
        this.montant = montant;
        this.datef = datef;
        this.typePaiement = typePaiement;
        this.userId = userId;
        this.commandeId = commandeId;
    }

    public Facture() {
    }

    public int getIdFacture() {
        return idFacture;
    }

    public float getMontant() {
        return montant;
    }

    public Date getDatef() {
        return datef;
    }

    public type_paiement getTypePaiement() {
        return typePaiement;
    }

    public int getUserId() {
        return userId;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public void setDatef(Date datef) {
        this.datef = datef;
    }

    public void setTypePaiement(type_paiement typePaiement) {
        this.typePaiement = typePaiement;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
    }
    public String toString() {
        return "Facture{" +
                "id=" + idFacture +
                ", montant=" + montant +
                ", Date facture'" + datef + '\'' +
                ", type paiement='" + typePaiement + '\'' +
                ", userd ID='" + userId + '\'' +
                ", Commande ID='" + commandeId + '\'' +
                "}\n";
    }
}
