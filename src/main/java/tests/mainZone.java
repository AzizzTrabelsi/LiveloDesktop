package tests;

import models.Zone;
import services.CrudZone;

public class mainZone {
    public static void main(String[] args) {
        CrudZone crudZone = new CrudZone();


        Zone zone1 = new Zone(
                "Centre Ville saaaaaaahbi",
                36.8065,
                10.1815,
                5.0f,
                17,
                1,
                10
        );
        crudZone.add(zone1);
        crudZone.getAll();


        zone1.setRayon(6.5f);
        zone1.setMax(15);
        crudZone.update(zone1);
        crudZone.getAll();
        crudZone.delete(zone1.getIdZone());
    }
}

