package businesslayer.states;

import businesslayer.production.IProduction;

public class Complete implements StatusState{

    @Override
    public void next(IProduction production) {
        System.out.println("It is already completed.");
    }
}
