package models;

import java.util.ArrayList;
import java.util.List;

public class Zone {
    private int idZone;
    private String nom;
    private double latitudeCentre;
    private double longitudeCentre;
    private float rayon;
    private int idUser;
    private int idLivraison;
    private int max;
    private List<Trajet> trajets = new ArrayList<>();
    public Zone() {}

    public Zone(String nom, double latitude, double longitude, float rayon, int idUser, int idLivraison, int max) {
        this.nom = nom;
        this.latitudeCentre = latitude;
        this.longitudeCentre = longitude;
        this.rayon = rayon;
        this.idUser = idUser;
        this.idLivraison = idLivraison;
        this.max = max;
    }


    public int getIdZone() {
        return idZone;
    }

    public void setIdZone(int idZone) {
        this.idZone = idZone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLatitudeCentre() {
        return latitudeCentre;
    }

    public void setLatitudeCentre(double latitudeCentre) {
        this.latitudeCentre = latitudeCentre;
    }

    public double getLongitudeCentre() {
        return longitudeCentre;
    }

    public void setLongitudeCentre(double longitudeCentre) {
        this.longitudeCentre = longitudeCentre;
    }

    public float getRayon() {
        return rayon;
    }

    public void setRayon(float rayon) {
        this.rayon = rayon;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdLivraison() {
        return idLivraison;
    }

    public void setIdLivraison(int idLivraison) {
        this.idLivraison = idLivraison;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    public List<Trajet> getTrajets() {
        return trajets;
    }

    public void setTrajets(List<Trajet> trajets) {
        this.trajets = trajets;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "idZone=" + idZone +
                ", nom='" + nom + '\'' +
                ", latitudeCentre=" + latitudeCentre +
                ", longitudeCentre=" + longitudeCentre +
                ", rayon=" + rayon +
                ", idUser=" + idUser +
                ", idLivraison=" + idLivraison +
                ", max=" + max +
                ", trajets=" + trajets + // 🔹
                '}';
    }
}