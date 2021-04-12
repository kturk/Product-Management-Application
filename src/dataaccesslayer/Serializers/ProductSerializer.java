package dataaccesslayer.Serializers;

import businesslayer.production.Assembly;
import businesslayer.production.IProduction;
import businesslayer.production.Part;
import businesslayer.production.Product;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public final class ProductSerializer
    implements JsonSerializer<IProduction>, JsonDeserializer<IProduction> {

    @Override
    public JsonElement serialize(final IProduction value, final Type type, final JsonSerializationContext context) {
        final Type targetType = value != null
                ? value.getClass()
                : type;
        return context.serialize(value, targetType);
    }

    @Override
    public IProduction deserialize(final JsonElement jsonElement, final Type typeOfT, final JsonDeserializationContext context) {
        JsonObject jobject = jsonElement.getAsJsonObject();
        StateSerializer stateSerializer = new StateSerializer();

        IProduction production = null;
        String className = jobject.get("className").getAsString();
        switch (className) {
            case "Part":
                production = new Part(jobject.get("id").getAsInt(),
                        jobject.get("name").getAsString());

                break;
            case "Product":
                production = new Product(jobject.get("id").getAsInt(),
                        jobject.get("name").getAsString());
                 break;
            case "Assembly":
                production = new Assembly(jobject.get("id").getAsInt(),
                        jobject.get("name").getAsString());
                break;
            default:
                throw new JsonParseException("Invalid classname for IProduction");
        }

        List<IProduction> subTree = production.getSubTree();
        jobject.get("subTree").getAsJsonArray();
        production.setState(stateSerializer.deserialize(jobject.get("state"), null, null));
        for (JsonElement element :
                jobject.get("subTree").getAsJsonArray()) {

            subTree.add(deserialize(element, null, null));
        }

        return production;
    }

}