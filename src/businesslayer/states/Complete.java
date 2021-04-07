package businesslayer.states;

import businesslayer.production.Production;

public class Complete implements StatusState{
    @Override
    public void next(Production production) {
//        production.setState(new InProgress());
        System.out.println("It is already completed.");
    }

    @Override
    public String getStatus(Production production) {
        return null;
    }
}
