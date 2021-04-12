package businesslayer.production;

import businesslayer.states.Complete;
import businesslayer.states.InProgress;
import businesslayer.states.NotStarted;
import businesslayer.states.StatusState;
import businesslayer.user.IUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assembly extends Production{

    public Assembly(String name) {
        super(name);
    }

    @Override
    public void showTree() {

    }

    @Override
    public boolean isCompleted() {
        for (IProduction p : this.getSubTree()){
            if (!p.isCompleted())
                return false;
        }
        return true;
    }

    @Override
    public List<IProduction> getAllTree() {
        List<IProduction> tempTree = new ArrayList<IProduction>();
        tempTree.add(this);
        for(IProduction production : this.getSubTree()){
            tempTree.addAll(production.getAllTree());
        }
        return tempTree;
    }

    @Override
    public void displayTree(IUser manager, int depth) {
        String tab = String.join("", Collections.nCopies(depth, "\t"));
        System.out.println(tab + "|--Assembly" + " -> " + this.getName() + " | " + this.getStateName());
        for (IProduction p : this.getSubTree())
            p.displayTree(manager, depth+1);
    }

}
