package businesslayer.production;

import businesslayer.states.StatusState;

public interface Production {
    public void showTree();
    public void nextState();
    public void setState(StatusState state);
    public boolean isCompleted();
}
