package businesslayer.user;

import businesslayer.exceptions.UnauthorizedUserOperationException;

import java.util.ArrayList;
import java.util.List;

public abstract class User implements IUser{

    private static int count = 1;
    private int id;
    private String name;

    public User(String name) {
        this.id = count;
        count++;
        this.name = name;
    }

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
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
    public abstract void addSubUser(IUser user) throws UnauthorizedUserOperationException;

    @Override
    public abstract List<IUser> getUserList();

    public String getDisplayName() {
        return "(" + this.getClass().getSimpleName() + ")" + " " + this.getName();
    }

}
