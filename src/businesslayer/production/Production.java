package businesslayer.production;

import businesslayer.states.Complete;
import businesslayer.states.InProgress;
import businesslayer.states.NotStarted;
import businesslayer.states.StatusState;

import java.util.ArrayList;
import java.util.List;

public abstract class Production implements IProduction{

    private static int count = 1;
    private int id;
    private List<IProduction> subTree;
    private StatusState state;
    private String name;

    public Production(String name) {
        this.id = count;
        count++;
        this.subTree = new ArrayList<IProduction>();
        this.state = new NotStarted();
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<IProduction> getSubTree() {
        return subTree;
    }


    public StatusState getState() {
        return state;
    }

    @Override
    public String getStateName() {
        return state.getClass().getSimpleName();
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
    public StatusState checkAndUpdateTreeStatus() {
        boolean isComplete = true;
        boolean isInProgress = false;
        for(IProduction production : this.getSubTree()){
            StatusState state = production.checkAndUpdateTreeStatus();
            if(state instanceof InProgress){
                isInProgress = true;
                isComplete = false;
                break;
            }
            else if(state instanceof Complete == false)
                isComplete = false;
        }
        if(isComplete){
            this.setState(new Complete());
        }
        else if(isInProgress){
            this.setState(new InProgress());
        }
        return this.getState();
    }

}
