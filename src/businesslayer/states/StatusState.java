package businesslayer.states;

import businesslayer.production.Production;

public interface StatusState {
    public void next(Production production);
    public String getStatus(Production production);
}
