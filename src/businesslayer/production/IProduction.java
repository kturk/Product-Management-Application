package businesslayer.production;

import businesslayer.states.StatusState;

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
    public void addProduction(IProduction production);
    public String getStateName();

    }
