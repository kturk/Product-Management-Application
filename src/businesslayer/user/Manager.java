package businesslayer.user;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {

    public Manager(String name) {
        super(name);
    }

    @Override
    public List<IUser> getUsers() {
        List<IUser> tempUserList = new ArrayList<IUser>();
        tempUserList.add(this);
        for(IUser user : this.getUserList()){
            tempUserList.addAll(user.getUsers());
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
}
