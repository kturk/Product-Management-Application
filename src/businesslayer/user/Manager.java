package businesslayer.user;

import businesslayer.production.IProduction;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {

    private IProduction product;

    public Manager(String name, IProduction product) {
        super(name);
        this.product = product;
    }

    public Manager(String name) {
        super(name);
        this.product = null;
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

    //TODO ADDSUBUSER THOR EXCEPTION
}
