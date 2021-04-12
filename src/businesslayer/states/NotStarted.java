package businesslayer.states;

import businesslayer.production.IProduction;

public class NotStarted implements StatusState{

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name = this.getClass().getSimpleName();

    @Override
    public void next(IProduction production) {
        production.setState(new InProgress());
    }
}
