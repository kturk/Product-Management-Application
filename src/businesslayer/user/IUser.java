package businesslayer.user;

import java.util.List;

public interface IUser {

    public List<IUser> getUsers();

    public IUser findUser(IUser user);

    public String getName();

    public int getId();

    public void addSubUser(IUser user);
}
