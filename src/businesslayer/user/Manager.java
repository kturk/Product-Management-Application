package businesslayer.user;

import businesslayer.exceptions.UnauthorizedUserOperationException;
import businesslayer.production.IProduction;
import businesslayer.production.Product;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User implements IUser{

    private List<Employee> employeeList = new ArrayList<>();
    private Product product;

    public Manager(String name, Product product) {
        super(name);
        this.product = product;
    }

    public Manager(String name) {
        super(name);
        this.product = null;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public IProduction getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Manager() {
    }

    public Manager(int id, String name) {
        super(id, name);
    }

    @Override
    public void addSubUser(IUser user) throws UnauthorizedUserOperationException {
        if (user instanceof Employee)
            this.employeeList.add((Employee) user);
    }

    @Override
    public List<IUser> getUserTree() {
        List<IUser> tempUserList = new ArrayList<IUser>();
        tempUserList.add(this);
        for(IUser user : this.getUserList()){
            tempUserList.addAll(user.getUserTree());
        }
        return tempUserList;
    }

    @Override
    public IUser findUser(IUser user) {
        if(user.equals(this))
            return this;
        for(IUser eachUser : this.getUserList()){
            IUser currentUser = eachUser.findUser(user);
            if(currentUser != null){
                return currentUser;
            }
        }
        return null;
    }

    @Override
    public IProduction getProduction() {
        return this.product;
    }

    @Override
    public IProduction getRelatedProduct(IProduction production) {
        for (IUser user : this.getUserList()){
            IProduction p = user.getRelatedProduct(production);
            if (p != null){
                return this.product;
            }
        }
        return null;
    }

    @Override
    public void displayTree() throws UnauthorizedUserOperationException {
        this.product.displayTree(this, 1);
    }

    @Override
    public List<IUser> getUserList() {
        return new ArrayList<>(this.employeeList);
    }

}
