package businesslayer.states;

import businesslayer.production.IProduction;

public class InProgress implements StatusState{

    String name = this.getClass().getSimpleName();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void next(IProduction production) {
        production.setState(new Complete());
    }
}
