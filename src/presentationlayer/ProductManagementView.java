package presentationlayer;

import java.util.Scanner;

public class ProductManagementView {
    private Scanner keyboard;

    public ProductManagementView() {
        keyboard = new Scanner(System.in);
    }

    public void printWelcome(){
        System.out.println("Welcome to the Product Management Application.");
    }

    public void printEnterAdmin() {
        System.out.println("Please enter the name of the admin: ");
    }

    public void printLogin() {
        System.out.println("Which user do you want to login with? Enter the number.");
    }

    public void invalidInputLogin() {
        System.out.println("Your input is invalid. Please enter a number.");
    }

    public void invalidIdLogin() {
        System.out.println("Please enter a valid id from the list.");
    }

    public void printUser(int id, String name) {
        System.out.println(id + " - " + name);
    }

//    public void promptMainChoices(){
//        System.out.println("What would you like to do? \n"
//                +                                 "1 -> Add a team \n"
//                + 								  "2 -> Remove a team \n"
//                + 								  "3 -> Update a team \n"
//                +  								  "0 -> Exit from the application \n"
//                +                                 "Please enter a number between 0-3: ");
//    }

    public void promptAdminChoices(){
        System.out.println("What would you like to do? \n"
                + 								  "1 -> See the users and the product trees  \n"
                +                                 "2 -> Add a manager \n"
                + 								  "3 -> Add a product to a manager \n"
                +  								  "0 -> Logout \n"
                +                                 "Please enter a number between 0-3: ");
    }

    public String getUserInput(){
        return keyboard.nextLine();
    }

    public void exitMessage() {
        System.out.println("Thank you for using Product Management Application.");
    }
}
