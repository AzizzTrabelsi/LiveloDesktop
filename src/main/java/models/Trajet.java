package models;
import models.Zone;
public class Trajet {
    private int idTrajet;
    private String pointDepart;
    private String pointArrivee;
    private int idCommande;
    private int idLivraison;
    private float distance;
    private float dureeEstimee;
    private etatTrajet etatTrajet; // Utilisation de l'énumération
    private Zone zone;

    public Trajet() {}

    public Trajet(String pointDepart, String pointArrivee, Zone zone, int idCommande, int idLivraison, float distance, float dureeEstimee, etatTrajet etatTrajet) {
        this.pointDepart = pointDepart;
        this.pointArrivee = pointArrivee;
        this.zone = zone;
        this.idCommande = idCommande;
        this.idLivraison = idLivraison;
        this.distance = distance;
        this.dureeEstimee = dureeEstimee;
        this.etatTrajet = etatTrajet;
    }

    public int getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(int idTrajet) {
        this.idTrajet = idTrajet;
    }


    public String getPointDepart() {
        return pointDepart;
    }

    public void setPointDepart(String pointDepart) {
        this.pointDepart = pointDepart;
    }

    public String getPointArrivee() {
        return pointArrivee;
    }

    public void setPointArrivee(String pointArrivee) {
        this.pointArrivee = pointArrivee;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdLivraison() {
        return idLivraison;
    }

    public void setIdLivraison(int idLivraison) {
        this.idLivraison = idLivraison;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDureeEstimee() {
        return dureeEstimee;
    }

    public void setDureeEstimee(float dureeEstimee) {
        this.dureeEstimee = dureeEstimee;
    }

    public etatTrajet getEtatTrajet() {
        return etatTrajet;
    }

    public void setEtatTrajet(etatTrajet etatTrajet) {
        this.etatTrajet = etatTrajet;
    }


    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }



    @Override
    public String toString() {
        return "Trajet{" +
                "idTrajet=" + idTrajet +
                ", pointDepart='" + pointDepart + '\'' +
                ", pointArrivee='" + pointArrivee + '\'' +
                ", idCommande=" + idCommande +
                ", idLivraison=" + idLivraison +
                ", distance=" + distance +
                ", dureeEstimee=" + dureeEstimee +
                ", etatTrajet=" + etatTrajet +
                ", zone=" + (zone != null ? zone.getNom() : "NULL") +
                '}';
    }
}

