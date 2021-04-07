package businesslayer.production;

import businesslayer.states.NotStarted;
import businesslayer.states.StatusState;

import java.util.ArrayList;
import java.util.List;

public class Assembly implements Production{

    List<Production> subTree;
    StatusState state;

    public Assembly() {
        this.subTree = new ArrayList<Production>();
        this.state = new NotStarted();
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
