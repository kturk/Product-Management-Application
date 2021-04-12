package dataaccesslayer;

import businesslayer.user.Admin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

public class FileIOManager {

    public static void writeJson(Object object, String filePath){
        try{
            Writer writer = new FileWriter(filePath);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(object, writer);
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String readJson(String filePath){
        try{
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(new FileReader(filePath), JsonElement.class);
            String result = gson.toJson(json);
            return result;
        }
        catch (FileNotFoundException e){
//            e.printStackTrace();
            return null;
        }
    }
}
