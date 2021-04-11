package businesslayer.production;

import businesslayer.states.NotStarted;
import businesslayer.states.StatusState;
import businesslayer.user.IUser;

import java.util.*;

public class Product extends Production {

    //TODO CAN BE PROTECTED
    public Product(String name) {
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
    public void addProduction(IProduction production) {
        this.getSubTree().add(production);
    }
}
