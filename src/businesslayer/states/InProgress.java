package businesslayer.states;

import businesslayer.production.IProduction;

public class InProgress implements StatusState{

    @Override
    public void next(IProduction production) {
        production.setState(new Complete());
    }
}
