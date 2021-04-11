package businesslayer.states;

import businesslayer.production.IProduction;

public class Complete implements StatusState{
    @Override
    public void next(IProduction production) {
//        production.setState(new InProgress());
        System.out.println("It is already completed.");
    }

    @Override
    public String getStatus(IProduction production) {
        return null;
    }
}
