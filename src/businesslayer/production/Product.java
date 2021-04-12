package businesslayer.production;

import businesslayer.user.IUser;

import java.util.*;

public class Product extends Production {

    public Product(String name) {
        super(name);
    }

    public Product(int id, String name) {
        super(id, name);
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
        System.out.println(tab +"|--" +  manager.getDisplayName() + " -> " + this.getName() + " | " + this.getStateName());
        for (IProduction p : this.getSubTree())
            p.displayTree(manager, depth+1);
    }


}
