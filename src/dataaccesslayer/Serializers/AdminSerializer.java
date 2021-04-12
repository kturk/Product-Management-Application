package dataaccesslayer.Serializers;

import businesslayer.exceptions.UnauthorizedUserOperationException;
import businesslayer.production.*;
import businesslayer.user.Admin;
import businesslayer.user.Employee;
import businesslayer.user.Manager;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AdminSerializer
    implements JsonSerializer<Admin>, JsonDeserializer<Admin> {

    @Override
    public JsonElement serialize(final Admin value, final Type type, final JsonSerializationContext context) {
        final Type targetType = value != null
                ? value.getClass()
                : type;
        return context.serialize(value, targetType);
    }

    @Override
    public Admin deserialize(final JsonElement jsonElement, final Type typeOfT, final JsonDeserializationContext context) {
        JsonObject jobject = jsonElement.getAsJsonObject();
        int id = jobject.get("id").getAsInt();
        String name = jobject.get("name").getAsString();
        Admin admin = new Admin(id, name);
        JsonArray managerList = jobject.get("managerList").getAsJsonArray();

        Map<Integer, IProduction> productions = new HashMap<>();
        for (JsonElement manager :
                managerList) {

            try {
                admin.addSubUser(managerDeserialize(manager, productions));
            } catch (UnauthorizedUserOperationException e) {
                throw new JsonParseException("Admin has a user other than Manager directly under him\\her");
            }
        }

        return admin;
    }

    private Manager managerDeserialize(final JsonElement jsonElement, Map<Integer, IProduction> productions) {
        JsonObject jobject = jsonElement.getAsJsonObject();
        int id = jobject.get("id").getAsInt();
        String name = jobject.get("name").getAsString();
        Manager manager = new Manager(id, name);

        JsonElement productElement = jobject.get("product");
        Product product = productDeserialize(productElement, productions);
        manager.setProduct(product);

        JsonArray employeeElements = jobject.get("employeeList").getAsJsonArray();
        for (JsonElement employeeElement :
                employeeElements) {

            try {
                manager.addSubUser(employeeDeserialize(employeeElement, productions));
            } catch (UnauthorizedUserOperationException e) {
                throw new JsonParseException("Admin has a user other than Manager directly under him\\her");
            }

        }

        return manager;
    }

    private Employee employeeDeserialize(JsonElement employeeElement, Map<Integer, IProduction> productions) {
        JsonObject jobject = employeeElement.getAsJsonObject();
        int id = jobject.get("id").getAsInt();
        String name = jobject.get("name").getAsString();

        JsonElement productElement = jobject.get("part");
        Part part = partDeserialize(productElement, productions);

        return new Employee(id, name, part);
    }


    private Product productDeserialize(final JsonElement jsonElement, Map<Integer, IProduction> productions) {

        int productId = jsonElement.getAsJsonObject().get("id").getAsInt();
        IProduction production;
        if (productions.containsKey(productId))
            production = productions.get(productId);
        else
            production = iProductionDeserialize(jsonElement, productions);

        if (production instanceof Product)
            return (Product) production;
        else
            throw new JsonParseException("Manager has something other than Product in it.");
    }

    private Part partDeserialize(final JsonElement jsonElement, Map<Integer, IProduction> productions) {

        int productId = jsonElement.getAsJsonObject().get("id").getAsInt();
        IProduction production;
        if (productions.containsKey(productId))
            production = productions.get(productId);
        else
            production = iProductionDeserialize(jsonElement, productions);

        if (production instanceof Part)
            return (Part) production;
        else
            throw new JsonParseException("Employee has something other than Part in it.");
    }

    private IProduction iProductionDeserialize(final JsonElement jsonElement, Map<Integer, IProduction> productions) {
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

            IProduction subProduct;
            int id = element.getAsJsonObject().get("id").getAsInt();
            if (productions.containsKey(id))
                subProduct = productions.get(id);
            else {
                subProduct = iProductionDeserialize(element, productions);
            }

            subTree.add(subProduct);
        }

        productions.put(production.getId(), production);

        return production;
    }

}