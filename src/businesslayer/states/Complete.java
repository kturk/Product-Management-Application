package businesslayer.states;

import businesslayer.production.IProduction;

public class Complete implements StatusState{

    String name = this.getClass().getSimpleName();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void next(IProduction production) {
        System.out.println("It is already completed.");
    }
}
