package businesslayer.user;

import java.util.ArrayList;
import java.util.List;

public class Manager implements User{

    List<User> userList;
    String name;

    public Manager(String name) {
        this.userList = new ArrayList<User>();
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addSubUser(User user){
        this.userList.add(user);
    }

    @Override
    public void getSubUsers() {
        System.out.println("Manager: " + this.name);
        for(User aUser : this.userList){
            aUser.getSubUsers();
        }
    }
}
