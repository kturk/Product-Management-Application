package businesslayer.states;

import businesslayer.production.Production;

public class NotStarted implements StatusState{

    @Override
    public void next(Production production) {
        production.setState(new InProgress());
    }

    @Override
    public String getStatus(Production production) {
        return null;
    }

}
