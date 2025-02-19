package tests;

import models.Avis;
import services.CrudAvis;

import java.util.Date;


public class MainAvis {

    public static void main(String[] args) {
        CrudAvis serviceAvis = new CrudAvis();
        Avis avis = new Avis(
                17,
                5,
                new Date(),
                "Test 1"
        );

        serviceAvis.add(avis);
        System.out.println("Avis added successfully!");

        System.out.println("All Avis:");
        serviceAvis.getAll().forEach(System.out::println);

        System.out.println("Test get by ID : " + serviceAvis.getById(7));

        avis.setComment("tasnim");
        serviceAvis.update(avis);
        

        serviceAvis.delete(7);

        System.out.println("All Avis after Delete:");
        serviceAvis.getAll().forEach(System.out::println);
    }
}
