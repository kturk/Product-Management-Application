package businesslayer.production;

import businesslayer.states.NotStarted;
import businesslayer.states.StatusState;

import java.util.ArrayList;
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
        this.nextState();
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
