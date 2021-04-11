package businesslayer.states;

import businesslayer.production.IProduction;

public class InProgress implements StatusState{
    @Override
    public void next(IProduction production) {
        production.setState(new Complete());
    }

    @Override
    public String getStatus(IProduction production) {
        return null;
    }
}
