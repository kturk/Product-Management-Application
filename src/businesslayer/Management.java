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
        dataHandler = new DataHandler(path);
        view = new ProductManagementView();
        admin = initializeAdmin();

    }

    private IUser initializeAdmin(){
        IUser admin = getAdminFromJson();
        if(admin == null){
            view.printEnterAdmin();
            String adminName = view.getStringInput();
            return new Admin(adminName);
        }
        return admin;

    }

    public void start(){
        view.printWelcome();
        login();

    }

    private IUser getAdminFromJson(){
        IUser admin = dataHandler.readJson();
        return admin;
    }

    private void writeJson(){
        dataHandler.writeJson(admin);
    }


    private void login(){
        view.printLogin();

        List<IUser> userList = admin.getUserTree();

        printUserList(userList);
        view.promptExitChoice();
        boolean validation = false;
        while (!validation){
            int userId = view.getIntInput();
            exitProgram(userId);
            IUser loggedUser = getUser(userList, userId);
            if(loggedUser != null){
                validation = true;
                roleChooser(loggedUser);
            }
            else
                view.invalidIntInput();
        }
    }

    private void exitProgram(int choice) {
        if (choice == 0) {
            view.exitMessage();
            System.exit(0);
        }
    }

    private void logout(){
        writeJson();
        view.printLogout();
        login();
    }


    private void printUserList(List<IUser> userList){
        for(IUser user : userList){
            view.printIdAndName(user.getId(), user.getName());
        }
    }

    private void printAssemblyList(List<IProduction> productTree){
        for(IProduction production : productTree){
            if(production.getClass().getSimpleName().equals("Assembly"))
                view.printIdAndName(production.getId(), production.getName());
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
                adminScreen(user); break;
            case "Manager":
                managerScreen(user); break;
            case "Employee":
                employeeScreen(user); break;
            default:
                break;
        }
    }

    private void adminScreen(IUser admin){
        boolean loop = true;
        while(loop){
            view.promptAdminChoices();

            int input = view.getIntInput();
            switch (input) {
                case 1:
                    printSubUsers(admin); break;
                case 2:
                    addManager(admin); break;
                case 0:
                    loop = false;
                    logout();
                    break;
                default:
                    view.invalidIntInput();
                    break;
            }
        }
    }

    private void managerScreen(IUser manager){
        boolean loop = true;
        while(loop){
            view.promptManagerChoices();

            int input = view.getIntInput();
            switch (input) {
                case 1:
                    printSubUsers(manager); break;
                case 2:
                    addAssemblyScreen(manager); break;
                case 3:
                    addPartScreen(manager);
                    break;
                case 0:
                    loop = false;
                    logout();
                    break;
                default:
                    view.invalidIntInput();
                    break;
            }
        }
    }

    private void addPartScreen(IUser manager){
        IProduction product = manager.getProduction();
        List<IProduction> productTree = product.getAllTree();
        view.getIdForPartAddition();
        view.printIdAndName(product.getId(), product.getName());
        printAssemblyList(productTree);

        boolean validation = false;
        while (!validation){
            int id = view.getIntInput();
            IProduction production = getProduction(productTree, id);
            if(production != null && !production.getClass().getSimpleName().equals("Part")){
                validation = true;

                addPartScreen(manager, production); // TODO
            }
            else
                view.invalidIntInput();
        }
    }

    private void addPartScreen(IUser manager, IProduction production){
        String partName = view.getNewPartInput();
        String employeeName = view.getNewEmployeeInput();

        Part part = new Part(partName);
        IUser employee = new Employee(employeeName, part);

        try{
            manager.addSubUser(employee);
            production.addProduction(part);
            view.printPartCreationSuccessful();
        }
        catch (UnauthorizedUserOperationException e){
            e.printStackTrace();
        }

    }

    private void addAssemblyScreen(IUser manager){
        IProduction product = manager.getProduction();
        List<IProduction> productTree = product.getAllTree();
        view.getIdForAssemblyAddition();
        view.printIdAndName(product.getId(), product.getName());
        printAssemblyList(productTree);

        boolean validation = false;
        while (!validation){
            int id = view.getIntInput();
            IProduction production = getProduction(productTree, id);
            if(production != null && !production.getClass().getSimpleName().equals("Part")){
                validation = true;
                addAssemblyOrPart(manager, production);
            }
            else
                view.invalidIntInput();
        }
    }

    private void addAssemblyOrPart(IUser manager, IProduction production){
        IProduction newAssembly = null;
        boolean loop = false;
        boolean validation = true;
        while (!loop){
            if (validation){
                String assemblyName = view.getNewAssemblyInput();
                newAssembly = new Assembly(assemblyName);
                try {
                    production.addProduction(newAssembly);
                    view.printAssemblyCreationSuccessful();
                }
                catch (UnauthorizedUserOperationException e){
                   e.printStackTrace();
                }
            }
            view.promptAddAssemblyChoices();
            int input = view.getIntInput();
            switch (input) {
                case 1:
                    production = newAssembly;
                    break;
                case 2:
                    loop = true;
                    addPartScreen(manager, newAssembly);
                    break;
                default:
                    view.invalidIntInput();
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
                view.partCompleteMessage();
                loop = false;
                logout();
                break;
            }
            else {
                view.promptEmployeeChoices(part);
                int input = view.getIntInput();
                switch (input) {
                    case 1:
                        part.nextState();
                        IProduction product = admin.getRelatedProduct(part);
                        StatusState state = product.checkAndUpdateTreeStatus();
                        break;
                    case 2:
                        break;
                    case 0:
                        loop = false;
                        logout();
                        break;
                    default:
                        view.invalidIntInput();
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
        String newManagerName = view.getNewUserInput();
        String newProductName = view.getNewProductInput();
        Product product = new Product(newProductName);
        IUser newManager = new Manager(newManagerName, product);

        try{
            admin.addSubUser(newManager);
            view.printManagerCreationSuccessful();
        }
        catch (UnauthorizedUserOperationException e){
            e.printStackTrace();
        }
    }

}
