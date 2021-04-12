package businesslayer.user;

import businesslayer.exceptions.UnauthorizedUserOperationException;

import java.util.ArrayList;
import java.util.List;

public abstract class User implements IUser{

    private static int count = 1;
    private int id;
    private String name;
    private List<IUser> userList;

    public User(String name) {
        this.id = count;
        count++;
        this.name = "(" + this.getClass().getSimpleName() + ")" + " " + name;
        this.userList = new ArrayList<IUser>();
    }

    public List<IUser> getUserList() {
        return userList;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addSubUser(IUser user) throws UnauthorizedUserOperationException {
        this.userList.add(user);
    }

}
