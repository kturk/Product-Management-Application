package businesslayer.states;

import businesslayer.production.IProduction;

public class NotStarted implements StatusState{

    @Override
    public void next(IProduction production) {
        production.setState(new InProgress());
    }

    @Override
    public String getStatus(IProduction production) {
        return null;
    }

}
