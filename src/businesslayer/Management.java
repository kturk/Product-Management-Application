package businesslayer;

import businesslayer.production.Assembly;
import businesslayer.production.IProduction;
import businesslayer.production.Part;
import businesslayer.production.Product;
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
        Product p = new Product("Computer");

        p.getSubTree().add(new Part("Cip"));
        p.getSubTree().add(new Assembly("Anakart"));

        Manager manager1 = new Manager("Kemal", p);


        Manager manager2 = new Manager("Arda");

        IUser employee1 = new Employee("Can");
        IUser employee2 = new Employee("Hassan");
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
        String adminName = this.view.getStringInput();
        IUser admin = new Admin(adminName);
        return admin;
    }

    public void start(){
        this.view.printWelcome();
        this.login();

    }

    private void login(){
        this.view.printLogin();

        List<IUser> userList = this.admin.getUserTree();

        this.printUserList(userList);

        boolean validation = false;
        while (!validation){
            int userId = this.view.getIntInput();
            IUser loggedUser = getUser(userList, userId);
            if(loggedUser != null){
                validation = true;
                this.roleChooser(loggedUser);
            }
            else
                this.view.invalidId();
        }
    }

    private void logout(){
        this.view.printLogout();
        this.login();
    }


    private void printUserList(List<IUser> userList){
        for(IUser user : userList){
            this.view.printIdAndName(user.getId(), user.getName());
        }
    }

    private void printAssemblyList(List<IProduction> productTree){
        for(IProduction production : productTree){
            if(production.getClass().getSimpleName().equals("Assembly"))
                this.view.printIdAndName(production.getId(), production.getName());
        }
    }

    private IUser getUser(List<IUser> userList, int id){
        for(IUser user : userList){
            if(user.getId() == id)
                return user;
        }
        return null;
    }
    private IProduction getProduction(List<IProduction> productTree, int id){
        for(IProduction production : productTree){
            if(production.getId() == id)
                return production;
        }
        return null;
    }

    private void roleChooser(IUser user){
        String userType = user.getClass().getSimpleName();
        switch (userType) {
            case "Admin":
                this.adminScreen(user); break;
            case "Manager":
                this.managerScreen(user); break;
            case "Employee":
                this.employeeScreen(user); break;
            default:
                //TODO WHAT IS THIS
                break;
        }
    }

    private void adminScreen(IUser admin){
        System.out.println("Admin Screen");
        boolean loop = true;
        while(loop){
            this.view.promptAdminChoices();

            int input = this.view.getIntInput();

            switch (input) {
                case 1:
                    //TODO USERS AND PRODUCT TREES
                    this.printSubUsers(admin); break;
                case 2:
                    this.addManager(admin); break;
//                case 3: // TODO UPDATE
//                    // add product
//                    break;
                case 0:
                    // logout
                    loop = false;
                    this.logout();
                    break;
                default:
                    //TODO ADMIN DEFAULT
                    break;
            }
        }

    }

    private void managerScreen(IUser manager){
        boolean loop = true;
        while(loop){
            this.view.promptManagerChoices();

            int input = this.view.getIntInput();

            switch (input) {
                case 1:
                    //TODO USERS AND PRODUCT TREES
                    this.printSubUsers(manager); break;
                case 2:
                    this.addAssemblyScreen(manager); break;
                case 3:
                    this.addPartScreen(manager);
                    break;
                case 0:
                    // logout
                    loop = false;
                    this.logout();
                    break;
                default:
                    //TODO ADMIN DEFAULT
                    break;
            }
        }

    }

    private void addPartScreen(IUser manager){
        IProduction product = manager.getProduction();
        List<IProduction> productTree = product.getAllTree();
        this.view.getIdForPartAddition();
        this.view.printIdAndName(product.getId(), product.getName());
        this.printAssemblyList(productTree);

        boolean validation = false;
        while (!validation){
            int id = this.view.getIntInput();
            IProduction production = this.getProduction(productTree, id);
            if(production != null && !production.getClass().getSimpleName().equals("Part")){
                validation = true;

                String partName = this.view.getNewPartInput();
                String employeeName = this.view.getNewEmployeeInput();

                IProduction part = new Part(partName);
                IUser employee = new Employee(employeeName, part);

                manager.addSubUser(employee);
                production.addProduction(part);
                this.view.printPartCreationSuccessful();
            }
            else
                this.view.invalidId();
        }

    }

    private void addPartScreen(IUser manager, IProduction production){

        String partName = this.view.getNewPartInput();
        String employeeName = this.view.getNewEmployeeInput();

        IProduction part = new Part(partName);
        IUser employee = new Employee(employeeName, part);

        manager.addSubUser(employee);
        production.addProduction(part);
        this.view.printPartCreationSuccessful();


    }

    private void addAssemblyScreen(IUser manager){

        IProduction product = manager.getProduction();
        List<IProduction> productTree = product.getAllTree();
        this.view.getIdForAssemblyAddition();
        this.view.printIdAndName(product.getId(), product.getName());
        this.printAssemblyList(productTree);

        int input = 1;
        // TODO CAN BE A METHOD
        boolean validation = false;
        while (!validation){
            int id = this.view.getIntInput();
            IProduction production = this.getProduction(productTree, id);
            if(production != null && !production.getClass().getSimpleName().equals("Part")){
                validation = true;
                this.addAssemblyOrPart(manager, production);
            }
            else
                this.view.invalidId();
        }
    }

    private void addAssemblyOrPart(IUser manager, IProduction production){

        boolean validation = false;

        while (!validation){
            String assemblyName = this.view.getNewAssemblyInput();
            IProduction newAssembly = new Assembly(assemblyName);
            production.addProduction(newAssembly);
            this.view.printAssemblyCreationSuccessful();

            this.view.promptAddAssemblyChoices();
            int input = this.view.getIntInput();
            switch (input) {
                case 1:
                    production = newAssembly;
                    break;
                case 2:
                    validation = true;
                    this.addPartScreen(manager, newAssembly);
                    break;
                default:
                    this.view.invalidId();
                    break;
            }
        }
    }

    private void employeeScreen(IUser employee){
        System.out.println("Employee Screen");

    }

    private void printSubUsers(IUser user){
        List<IUser> userList = user.getUserTree();
        for(IUser u : userList){
            System.out.println(u.getName());
        }

    }

    private void addManager(IUser admin){
        String newManagerName = this.view.getNewUserInput();
        String newProductName = this.view.getNewProductInput();
        IProduction product = new Product(newProductName);
        IUser newManager = new Manager(newManagerName, product);
        admin.addSubUser(newManager);
        this.view.printManagerCreationSuccessful();
    }



    //TODO UPDATE
//    private void addProduct(IUser admin){
//        List<IUser> managerList = admin.getUserList();
//    }




}
