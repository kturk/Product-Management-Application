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

//    public void promptMainChoices(){
//        System.out.println("What would you like to do? \n"
//                +                                 "1 -> Add a team \n"
//                + 								  "2 -> Remove a team \n"
//                + 								  "3 -> Update a team \n"
//                +  								  "0 -> Exit from the application \n"
//                +                                 "Please enter a number between 0-3: ");
//    }

    public String getUserInput(){
        return keyboard.nextLine();
    }

    public void exitMessage() {
        System.out.println("Thank you for using Product Management Application.");
    }
}
