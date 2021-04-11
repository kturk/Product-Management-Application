package businesslayer.user;

import businesslayer.production.IProduction;

import java.util.ArrayList;
import java.util.List;

public class Employee extends User {

    private IProduction part;

    public Employee(String name, IProduction part) {
        super(name);
        this.part = part;
    }

    public Employee(String name) {
        super(name);
        this.part = null;
    }

    @Override
    public List<IUser> getUserTree() {
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

    @Override
    public IProduction getProduction() {
        return this.part;
    }

    public void nextStateForPart(){

    }
}
