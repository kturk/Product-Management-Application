package businesslayer.states;

import businesslayer.production.IProduction;

public interface StatusState {
    public void next(IProduction production);
}
