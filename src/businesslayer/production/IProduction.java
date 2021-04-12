package businesslayer.production;

import businesslayer.exceptions.UnauthorizedUserOperationException;
import businesslayer.states.StatusState;
import businesslayer.user.IUser;

import java.util.List;

public interface IProduction {
    public void showTree();
    public void nextState();
    public void setState(StatusState state);
    public boolean isCompleted();
    public StatusState checkAndUpdateTreeStatus();
    public List<IProduction> getSubTree();
    public List<IProduction> getAllTree();
    public String getName();
    public int getId();
    public void addProduction(IProduction production) throws UnauthorizedUserOperationException;
    public String getStateName();
    public void displayTree(IUser manager, int depth);

    }
