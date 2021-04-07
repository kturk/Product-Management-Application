package businesslayer.user;

import businesslayer.production.Production;

public class Employee implements User{

    String name;
    Production part;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void getSubUsers() {
        System.out.println(this.name);
    }

    public void nextStateForPart(){

    }
}
