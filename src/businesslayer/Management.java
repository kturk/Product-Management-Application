package businesslayer;

import businesslayer.exceptions.UnauthorizedUserOperationException;
import businesslayer.production.Assembly;
import businesslayer.production.IProduction;
import businesslayer.production.Part;
import businesslayer.production.Product;
import businesslayer.states.StatusState;
import businesslayer.user.Admin;
import businesslayer.user.Employee;
import businesslayer.user.Manager;
import businesslayer.user.IUser;
import dataaccesslayer.DataHandler;
import presentationlayer.ProductManagementView;

import java.util.List;

public class Management {

    private static final String path = "admin.json";

    private IUser admin;
    private ProductManagementView view;
    private DataHandler dataHandler;

    public Management() {
        this.dataHandler = new DataHandler(path);
        this.view = new ProductManagementView();
        this.admin = initializeAdmin();

    }

    private IUser initializeAdmin(){
        IUser admin = this.getAdminFromJson();
        if(admin == null){
            this.view.printEnterAdmin();
            String adminName = this.view.getStringInput();
            return new Admin(adminName);
        }
        return admin;

    }

    public void start(){
        this.view.printWelcome();
        this.login();

    }

    private IUser getAdminFromJson(){
        IUser admin = this.dataHandler.readJson();
        return admin;
    }

    private void writeJson(){
        this.dataHandler.writeJson(this.admin);
    }


    private void login(){
        this.view.printLogin();

        List<IUser> userList = this.admin.getUserTree();

        this.printUserList(userList);
        this.view.promptExitChoice();
        boolean validation = false;
        while (!validation){
            int userId = this.view.getIntInput();
            this.exitProgram(userId);
            IUser loggedUser = getUser(userList, userId);
            if(loggedUser != null){
                validation = true;
                this.roleChooser(loggedUser);
            }
            else
                this.view.invalidIntInput();
        }
    }

    private void exitProgram(int choice) {
        if (choice == 0) {
            this.view.exitMessage();
            System.exit(0);
        }
    }

    private void logout(){
        this.writeJson();
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
                break;
        }
    }

    private void adminScreen(IUser admin){
        boolean loop = true;
        while(loop){
            this.view.promptAdminChoices();

            int input = this.view.getIntInput();
            switch (input) {
                case 1:
                    this.printSubUsers(admin); break;
                case 2:
                    this.addManager(admin); break;
                case 0:
                    loop = false;
                    this.logout();
                    break;
                default:
                    this.view.invalidIntInput();
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
                    this.printSubUsers(manager); break;
                case 2:
                    this.addAssemblyScreen(manager); break;
                case 3:
                    this.addPartScreen(manager);
                    break;
                case 0:
                    loop = false;
                    this.logout();
                    break;
                default:
                    this.view.invalidIntInput();
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

                addPartScreen(manager, production); // TODO
            }
            else
                this.view.invalidIntInput();
        }
    }

    private void addPartScreen(IUser manager, IProduction production){
        String partName = this.view.getNewPartInput();
        String employeeName = this.view.getNewEmployeeInput();

        Part part = new Part(partName);
        IUser employee = new Employee(employeeName, part);

        try{
            manager.addSubUser(employee);
            production.addProduction(part);
            this.view.printPartCreationSuccessful();
        }
        catch (UnauthorizedUserOperationException e){
            e.printStackTrace();
        }

    }

    private void addAssemblyScreen(IUser manager){
        IProduction product = manager.getProduction();
        List<IProduction> productTree = product.getAllTree();
        this.view.getIdForAssemblyAddition();
        this.view.printIdAndName(product.getId(), product.getName());
        this.printAssemblyList(productTree);

        boolean validation = false;
        while (!validation){
            int id = this.view.getIntInput();
            IProduction production = this.getProduction(productTree, id);
            if(production != null && !production.getClass().getSimpleName().equals("Part")){
                validation = true;
                this.addAssemblyOrPart(manager, production);
            }
            else
                this.view.invalidIntInput();
        }
    }

    private void addAssemblyOrPart(IUser manager, IProduction production){
        IProduction newAssembly = null;
        boolean loop = false;
        boolean validation = true;
        while (!loop){
            if (validation){
                String assemblyName = this.view.getNewAssemblyInput();
                newAssembly = new Assembly(assemblyName);
                try {
                    production.addProduction(newAssembly);
                    this.view.printAssemblyCreationSuccessful();
                }
                catch (UnauthorizedUserOperationException e){
                   e.printStackTrace();
                }
            }
            this.view.promptAddAssemblyChoices();
            int input = this.view.getIntInput();
            switch (input) {
                case 1:
                    production = newAssembly;
                    break;
                case 2:
                    loop = true;
                    this.addPartScreen(manager, newAssembly);
                    break;
                default:
                    this.view.invalidIntInput();
                    validation = false;
                    break;
            }
        }
    }

    private void employeeScreen(IUser employee){
        boolean loop = true;
        while(loop) {
            IProduction part = employee.getProduction();
            if (part.isCompleted()) {
                this.view.partCompleteMessage();
                loop = false;
                this.logout();
                break;
            }
            else {
                this.view.promptEmployeeChoices(part);
                int input = this.view.getIntInput();
                switch (input) {
                    case 1:
                        part.nextState();
                        IProduction product = this.admin.getRelatedProduct(part);
                        StatusState state = product.checkAndUpdateTreeStatus();
                        break;
                    case 2:
                        break;
                    case 0:
                        loop = false;
                        this.logout();
                        break;
                    default:
                        this.view.invalidIntInput();
                        break;
                }
            }
        }
    }

    private void printSubUsers(IUser user) {
        try {
            user.displayTree();
        }
        catch (UnauthorizedUserOperationException e){
            e.printStackTrace();
        }
    }

    private void addManager(IUser admin){
        String newManagerName = this.view.getNewUserInput();
        String newProductName = this.view.getNewProductInput();
        Product product = new Product(newProductName);
        IUser newManager = new Manager(newManagerName, product);

        try{
            admin.addSubUser(newManager);
            this.view.printManagerCreationSuccessful();
        }
        catch (UnauthorizedUserOperationException e){
            e.printStackTrace();
        }
    }

}
