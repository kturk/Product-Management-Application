package businesslayer.user;

import businesslayer.exceptions.UnauthorizedUserOperationException;
import businesslayer.production.IProduction;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User{

    List<Manager> managerList = new ArrayList<>();

    public Admin(String name) {
        super(name);
    }

    public Admin() {
    }

    @Override
    public void addSubUser(IUser user) throws UnauthorizedUserOperationException {
        if (user instanceof Manager)
            this.managerList.add((Manager) user);
    }

    public List<Manager> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<Manager> managerList) {
        this.managerList = managerList;
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
        return null;
    }

    @Override
    public IProduction getRelatedProduct(IProduction production) {
        for (IUser user : this.getUserList()){
            IProduction p = user.getRelatedProduct(production);
            if (p != null){
                return p;
            }
        }
        return null;
    }

    @Override
    public void displayTree() throws UnauthorizedUserOperationException {
        System.out.println("|--" + this.getName());
        for (IUser user : this.getUserList()){
            user.displayTree();
        }
    }

    @Override
    public List<IUser> getUserList() {
        return new ArrayList<>(this.managerList);
    }
}
