package com.itp.pos.db;

import com.itp.pos.model.Customer;
import com.itp.pos.model.User;
import com.itp.pos.util.PasswordEncoder;

import java.util.ArrayList;

public class Database {
    public static ArrayList<User> userTable
            = new ArrayList<>();
    public static User user = new User();
    public static ArrayList<Customer>
            customerTable = new ArrayList<>();
    static {
        userTable.add(
                new User("a",
                        PasswordEncoder.encode("a"),"Saman Kumara")
        );
        //=========
        customerTable.add(
                new Customer("C001","Saman","Colombo",25000)
        );
        customerTable.add(
                new Customer("C002","Jagath","Kalutara",80000)
        );
        customerTable.add(
                new Customer("C003","Nihal","Kandy",75000)
        );
        customerTable.add(
                new Customer("C004","Wasantha","Gampaha",76550)
        );
        customerTable.add(
                new Customer("C005","Samantha","Panadura",28000)
        );
    }
}
