package businesslayer.user;

import java.util.ArrayList;
import java.util.List;

public class Admin implements User{

    List<User> userList;
    String name;
    public Admin(String name) {
        this.userList = new ArrayList<User>();
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSubUser(User user){
        this.userList.add(user);
    }

    @Override
    public void getSubUsers() {
        System.out.println("Admin: " + this.name);
        for(User aUser : this.userList){
            aUser.getSubUsers();
        }
    }
}
