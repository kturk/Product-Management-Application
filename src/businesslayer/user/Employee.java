package businesslayer.user;

import businesslayer.exceptions.UnauthorizedUserOperationException;
import businesslayer.production.IProduction;
import businesslayer.production.Part;

import java.util.ArrayList;
import java.util.List;

public class Employee extends User implements IUser{

    private Part part;

    public Employee(String name, Part part) {
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

    @Override
    public IProduction getRelatedProduct(IProduction production) {
        if(production.equals(this.part))
            return this.part;
        return null;
    }

    @Override
    public void displayTree() throws UnauthorizedUserOperationException {
        throw new UnauthorizedUserOperationException();
    }

    @Override
    public List<IUser> getUserList() {
        return null;
    }

    @Override
    public void addSubUser(IUser user) throws UnauthorizedUserOperationException {
        throw new UnauthorizedUserOperationException();
    }
}
