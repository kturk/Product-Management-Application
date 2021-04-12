package dataaccesslayer;

import businesslayer.production.Assembly;
import businesslayer.production.IProduction;
import businesslayer.production.Part;
import businesslayer.production.Product;
import businesslayer.states.Complete;
import businesslayer.states.InProgress;
import businesslayer.states.NotStarted;
import businesslayer.states.StatusState;
import businesslayer.user.Admin;
import businesslayer.user.Employee;
import businesslayer.user.IUser;
import businesslayer.user.Manager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static dataaccesslayer.InterfaceSerializer.interfaceSerializer;

public class DataHandler {
    private String filePath;

    public DataHandler(String filePath) {
        this.filePath = filePath;
    }

    public IUser readJson(){
        FileIOManager fileIOManager = new FileIOManager();
        String jsonString = fileIOManager.readJson(this.filePath);
        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(IUser.class, interfaceSerializer(Admin.class))
//                .registerTypeAdapter(IUser.class, interfaceSerializer(Manager.class))
//                .registerTypeAdapter(IUser.class, interfaceSerializer(Employee.class))
//
//                .registerTypeAdapter(IProduction.class, interfaceSerializer(Product.class))
//                .registerTypeAdapter(IProduction.class, interfaceSerializer(Assembly.class))
//                .registerTypeAdapter(IProduction.class, interfaceSerializer(Part.class))
//
//                .registerTypeAdapter(StatusState.class, interfaceSerializer(Complete.class))
//                .registerTypeAdapter(StatusState.class, interfaceSerializer(InProgress.class))
//                .registerTypeAdapter(StatusState.class, interfaceSerializer(NotStarted.class))
                .registerTypeAdapter(StatusState.class, new StateSerializer())
                .registerTypeAdapter(IProduction.class, new ProductSerializer())
                .create();
        Admin admin = gson.fromJson(jsonString, Admin.class);
        return admin;
    }

    public void writeJson(Object object){
        FileIOManager fileIOManager = new FileIOManager();
        fileIOManager.writeJson(object, this.filePath);
    }
}
