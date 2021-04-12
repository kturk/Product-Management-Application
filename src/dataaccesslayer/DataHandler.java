package dataaccesslayer;

import businesslayer.user.Admin;
import businesslayer.user.IUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataaccesslayer.Serializers.AdminSerializer;

public class DataHandler {
    private String filePath;

    public DataHandler(String filePath) {
        this.filePath = filePath;
    }

    public IUser readJson(){
        FileIOManager fileIOManager = new FileIOManager();
        String jsonString = fileIOManager.readJson(this.filePath);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Admin.class, new AdminSerializer())
                .setPrettyPrinting()
                .create();
        Admin admin = gson.fromJson(jsonString, Admin.class);
        return admin;
    }

    public void writeJson(Object object){
        FileIOManager fileIOManager = new FileIOManager();
        fileIOManager.writeJson(object, this.filePath);
    }
}
