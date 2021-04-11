package businesslayer.user;

import businesslayer.production.IProduction;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User{

    public Admin(String name) {
        super(name);
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
            if(!currentUser.equals(null)){
                return currentUser;
            }
        }
        return null;
    }

    @Override //TODO THOR EXCEPTION
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
    public void printSubUsers() {
        System.out.println("|--" + this.getName());
        for (IUser user : this.getUserList()){
            user.printSubUsers();
        }
    }
}
