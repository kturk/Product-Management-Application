package businesslayer.user;

import businesslayer.production.Production;

import java.util.ArrayList;
import java.util.List;

public class Employee extends User {

    private Production part;

    public Employee(String name, Production part) {
        super(name);
        this.part = part;
    }

    public Employee(String name) {
        super(name);
        this.part = null;
    }

    @Override
    public List<IUser> getUsers() {
        List<IUser> users = new ArrayList<IUser>();
        users.add(this);
        return users;
    }

    @Override
    public IUser findUser(IUser user) {
        if(this.equals(user))
            return user;
        else
            return null;
    }

    public void nextStateForPart(){

    }
}
