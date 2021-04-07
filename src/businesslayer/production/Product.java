package businesslayer.production;

import businesslayer.states.NotStarted;
import businesslayer.states.StatusState;

import java.util.*;

public class Product implements Production{
    List<Production> subTree;
    StatusState state;

    public Product() {
        this.subTree = new ArrayList<Production>();
        this.state = new NotStarted();
    }

    public Product(List<Production> subTree) {
        this.subTree = subTree;
    }

    @Override
    public void showTree() {

    }

    @Override
    public void nextState() {
        this.state.next(this);
    }

    @Override
    public void setState(StatusState state) {
        this.state = state;
    }

    @Override
    public boolean isCompleted() {
        for (Production p : subTree){
            if (!p.isCompleted())
                return false;
        }
        return true;
    }
}
