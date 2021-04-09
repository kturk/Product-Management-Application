package businesslayer;

import businesslayer.user.Admin;
import businesslayer.user.Employee;
import businesslayer.user.Manager;
import businesslayer.user.IUser;
import presentationlayer.ProductManagementView;

import java.util.List;

public class Management {

    private IUser admin;
    private ProductManagementView view;

    public Management() {
        this.view = new ProductManagementView();
        this.admin = initializeAdmin();
        Admin adminn = new Admin("Admin");
        Manager manager1 = new Manager("Kemal");
        Manager manager2 = new Manager("Arda");

        IUser employee1 = new Employee("Can");
        IUser employee2 = new Employee("Hasan");
        IUser employee3 = new Employee("Elman");

        adminn.addSubUser(manager1);
        adminn.addSubUser(manager2);

        manager1.addSubUser(employee1);
        manager1.addSubUser(employee2);

        manager2.addSubUser(employee3);
        this.admin = adminn;


    }

    private IUser initializeAdmin(){
        this.view.printEnterAdmin();
        String adminName = this.view.getUserInput();
        IUser admin = new Admin(adminName);
        return admin;
    }

    public void start(){
        this.view.printWelcome();
        this.login();

    }

    private void login(){
        this.view.printLogin();

        List<IUser> userList = this.admin.getUsers();

        this.printUserlist(userList);

        boolean validation = false;
        while (!validation){
            String stringNum = this.view.getUserInput();
            try{
                // TODO Can we do this try catch in view class (NumberFormatException)
                int userId = Integer.parseInt(stringNum);
                IUser loggedUser = getUser(userList, userId);

                if(loggedUser != null){
                    validation = true;
//                    System.out.println(loggedUser.getName());
//                    System.out.println(loggedUser.getClass().getSimpleName());
                    this.roleChooser(loggedUser);

                }
                else
                    this.view.invalidIdLogin();
            }
            catch (NumberFormatException e){
                this.view.invalidInputLogin();
            }
        }

    }

    private void printUserlist(List<IUser> userList){
        for(IUser user : userList){
            this.view.printUser(user.getId(), user.getName());
        }
    }

    private IUser getUser(List<IUser> userList, int id){
        for(IUser user : userList){
            if(user.getId() == id)
                return user;
        }
        return null;
    }

    private void roleChooser(IUser user){
        String userType = user.getClass().getSimpleName();
        switch (userType) {
            case "Admin":
                this.adminScreen(); break;
            case "Manager":
                this.managerScreen(); break;
            case "Employee":
                this.employeeScreen(); break;
            default:
                //TODO WHAT IS THIS
                break;
        }
    }

    private void adminScreen(){
        System.out.println("Admin Screen");
    }

    private void managerScreen(){
        System.out.println("Manager Screen");

    }

    private void employeeScreen(){
        System.out.println("Employee Screen");

    }

}
