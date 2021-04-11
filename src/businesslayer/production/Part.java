package businesslayer.production;

import businesslayer.states.Complete;
import businesslayer.states.NotStarted;
import businesslayer.states.StatusState;
import businesslayer.user.IUser;

import java.util.ArrayList;
import java.util.List;

public class Part extends Production {


    public Part(String name) {
        super(name);
    }

    @Override
    public void showTree() {

    }

    @Override
    public boolean isCompleted() {
        return this.getState() instanceof Complete;
    }

    @Override
    public List<IProduction> getAllTree() {
        List<IProduction> tempTree = new ArrayList<IProduction>();
        tempTree.add(this);
        return tempTree;
    }

    @Override //TODO THOR EXCEPTION
    public void addProduction(IProduction production) {

    }
}
