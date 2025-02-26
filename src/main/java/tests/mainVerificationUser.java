package tests;

import services.UsersVerification;

public class mainVerificationUser {
    public static void main(String[] args) {
        UsersVerification user = new UsersVerification();
        user.getUnverifiedUsers();
        //user.acceptUser(84);
        //user.refuseUser(85);
        user.search("12345678");
    }
}
