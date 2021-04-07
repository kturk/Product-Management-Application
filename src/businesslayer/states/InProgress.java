package businesslayer.states;

import businesslayer.production.Production;

public class InProgress implements StatusState{
    @Override
    public void next(Production production) {
        production.setState(new Complete());
    }

    @Override
    public String getStatus(Production production) {
        return null;
    }
}
