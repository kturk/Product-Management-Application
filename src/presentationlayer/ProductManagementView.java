package presentationlayer;

import businesslayer.production.IProduction;

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

    public void printLogout() {
        System.out.println("You have logged out successfully.");
    }

    public void invalidInputLogin() {
        System.out.println("Your input is invalid. Please enter a number.");
    }

    public void invalidId() {
        System.out.println("Please enter a valid id from the list.");
    }

    public void printIdAndName(int id, String name) {
        System.out.println(id + " - " + name);
    }

    public void getIdForPartAddition(){
        System.out.println("Select an id to add the part under it.");
    }

    public void getIdForAssemblyAddition(){
        System.out.println("Select an id to add the assembly under it.");
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
                + 								  "1 -> See the all users and the product trees  \n"
                +                                 "2 -> Add a manager and assign a product \n"
//                + 								  "3 -> Add a product to a manager \n"
                +  								  "0 -> Logout \n"
                +                                 "Please enter a number between 0-2: ");
    }

    public void promptManagerChoices(){
        System.out.println("What would you like to do? \n"
                + 								  "1 -> See the employees and the product tree  \n"
                +                                 "2 -> Add an assembly  \n"
                +                                 "3 -> Add a part \n"
//                + 								  "3 -> Add a product to a manager \n"
                +  								  "0 -> Logout \n"
                +                                 "Please enter a number between 0-3: ");
    }

    public void promptEmployeeChoices(IProduction part){
        System.out.println("Would you like to move your part's (" + part.getName() + ") " + "status to next stage? \n"
                +                                 "Current status is: " + part.getStateName() + "\n"
                + 								  "1 -> Yes  \n"
                +                                 "2 -> No  \n"
                +  								  "0 -> Logout \n"
                +                                 "Please enter a number between 0-2: ");
    }

    public void partCompleteMessage(){
        System.out.println("The part assigned to you is already completed.");
    }

    public void promptAddPartChoices(){
        System.out.println("What would you like to do? \n"
                + 								  "1 -> Add a part and its employee directly to the product  \n"
                +                                 "2 -> Add a part and its employee to an assembly  \n"
                +                                 "3 -> Back  \n"
                +  								  "0 -> Logout \n"
                +                                 "Please enter a number between 0-3: ");
    }

    public void promptAddAssemblyChoices(){
        System.out.println("Each assembly must have at least one part in it. \n" +
                "Thus, you will be see this menu until you select the add a part option.");
        System.out.println("What would you like to do? \n"
                + 								  "1 -> Add an assembly  \n"
                +                                 "2 -> Add a part  \n"
                +                                 "Please enter a number between 1-2: ");
    }



    public String getNewUserInput(){
        System.out.println("Enter the new user's name.");
        String name = this.getStringInput();
        return name;
    }

    public String getNewProductInput(){
        System.out.println("Enter the product's name.");
        String name = this.getStringInput();
        return name;
    }

    public String getNewAssemblyInput(){
        System.out.println("Enter the new assembly's name.");
        String name = this.getStringInput();
        return name;
    }

    public String getNewPartInput(){
        System.out.println("Enter the new part's name.");
        String name = this.getStringInput();
        return name;
    }

    public String getNewEmployeeInput(){
        System.out.println("Enter the new employee's name.");
        String name = this.getStringInput();
        return name;
    }


    public void printManagerCreationSuccessful(){
        System.out.println("Manager and related product are created successfully.");
    }

    public void printPartCreationSuccessful(){
        System.out.println("Part successfully added to the desired location.");
    }

    public void printAssemblyCreationSuccessful(){
        System.out.println("Assembly successfully added to the desired location.");
    }

    public String getStringInput(){
        return keyboard.nextLine();
    }


    public int getIntInput(){
        try{
            String input = this.getStringInput();
            int intInput = Integer.parseInt(input);
            return intInput;
        }
        catch (NumberFormatException e){
            return -1;
        }
    }

    public void exitMessage() {
        System.out.println("Thank you for using Product Management Application.");
    }
}
