package tests;

    import models.User;
    import models.role_user;
    import models.type_vehicule;
    import services.Authentification;
    import services.CrudUser;


public class mainUser {
    public static void main(String[] args) {
        User user = new User(
                "Benhassine",
                "Tasnim",
                role_user.delivery_person,
                "tunis",
                "tasnim.benhassine1@esprit.tn",
                "123456",
                "28740885",
                "55455027"
        );
        CrudUser crudUser = new CrudUser();
        //crudUser.delete(19);
        /*crudUser.add(user);*/
        crudUser.getAll();

        /*user.setAdresse("Sousse");
        user.setEmail("tasnim.sousse@esprit.tn");
        crudUser.update(user);
        crudUser.getAll();*/
        //crudUser.getById(1);

       /*Authentification authentification = new Authentification();
        String token = authentification.login("48455027", "123456");

        crudUser.search("48455027");*/
    }
}
