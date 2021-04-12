package businesslayer.production;

import businesslayer.exceptions.UnauthorizedUserOperationException;
import businesslayer.states.Complete;
import businesslayer.states.StatusState;
import businesslayer.user.IUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Part extends Production {


    public Part(String name) {
        super(name);
    }

    public Part(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isCompleted() {
        return this.getState() instanceof Complete;
    }

    @Override
    public StatusState checkAndUpdateTreeStatus() {
        return this.getState();
    }

    @Override
    public List<IProduction> getAllTree() {
        List<IProduction> tempTree = new ArrayList<IProduction>();
        tempTree.add(this);
        return tempTree;
    }

    @Override
    public void addProduction(IProduction production) throws UnauthorizedUserOperationException {
        throw new UnauthorizedUserOperationException();
    }

    @Override
    public void displayTree(IUser manager, int depth) {
        String tab = String.join("", Collections.nCopies(depth, "\t"));
        List<IUser> employees = manager.getUserTree();
        for (IUser user : employees){
            if  (Objects.equals(user.getProduction(), this)) {
                System.out.println(tab + "|--" + user.getDisplayName() + " -> " + this.getName() + " | " + this.getStateName());
                break;
            }
        }

    }
}
