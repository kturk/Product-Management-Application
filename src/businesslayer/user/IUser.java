package businesslayer.user;

import businesslayer.exceptions.UnauthorizedUserOperationException;
import businesslayer.production.IProduction;

import java.util.List;

public interface IUser {

    public List<IUser> getUserTree();

    public IUser findUser(IUser user);

    public String getName();

    public int getId();

    public void addSubUser(IUser user) throws UnauthorizedUserOperationException;

    public IProduction getProduction();

    public IProduction getRelatedProduct(IProduction production);

    public void displayTree() throws UnauthorizedUserOperationException;
}
