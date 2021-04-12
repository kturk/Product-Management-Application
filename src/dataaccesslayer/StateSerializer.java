package dataaccesslayer;

import businesslayer.states.Complete;
import businesslayer.states.InProgress;
import businesslayer.states.NotStarted;
import businesslayer.states.StatusState;
import com.google.gson.*;

import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Type;

public final class StateSerializer
    implements JsonSerializer<StatusState>, JsonDeserializer<StatusState> {

    @Override
    public JsonElement serialize(final StatusState value, final Type type, final JsonSerializationContext context) {
        final Type targetType = value != null
                ? value.getClass()
                : type;
        return context.serialize(value, targetType);
    }

    @Override
    public StatusState deserialize(final JsonElement jsonElement, final Type typeOfT, final JsonDeserializationContext context) {
        JsonObject jobject = jsonElement.getAsJsonObject();

        StatusState state = null;
        String name = jobject.get("name").getAsString();
        switch (name) {
            case "NotStarted":
                state = new NotStarted();
                break;
            case "InProgress":
                 state = new InProgress();
                 break;
            case "Complete":
                state = new Complete();
                break;
            default:
                state = null;
                break;
        }
        return state;
    }

}