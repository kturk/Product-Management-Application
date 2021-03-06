package businesslayer.production;

import businesslayer.states.Complete;
import businesslayer.states.NotStarted;
import businesslayer.states.StatusState;

public class Part implements Production{

    StatusState state;

    public Part() {
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
        return this.state instanceof Complete;
    }
}
