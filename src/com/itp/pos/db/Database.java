package com.itp.pos.db;

import com.itp.pos.model.Customer;
import com.itp.pos.model.User;

import java.util.ArrayList;

public class Database {
    public static ArrayList<User> userTable
            = new ArrayList<>();
    public static User user = new User();
    public static ArrayList<Customer>
            customerTable = new ArrayList<>();
}
