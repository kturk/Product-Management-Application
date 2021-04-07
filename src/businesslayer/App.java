package businesslayer;

import businesslayer.user.Admin;
import businesslayer.user.Employee;
import businesslayer.user.Manager;
import businesslayer.user.User;

public class App {
    public static void main(String[] args) {
        System.out.println("test234");

        User admin1 = new Admin("Caner");
        User manager1 = new Manager("Kemal");
        User manager2 = new Manager("Arda");

        User employee1 = new Employee("Can");
        User employee2 = new Employee("Hasan");
        User employee3 = new Employee("Elman");

        ((Admin) admin1).addSubUser(manager1);
        ((Admin) admin1).addSubUser(manager2);

        ((Manager) manager1).addSubUser(employee1);
        ((Manager) manager1).addSubUser(employee2);

        ((Manager) manager2).addSubUser(employee3);

        admin1.getSubUsers();

        System.out.println("s");

        manager1.getSubUsers();
    }
}
