package com.itp.pos;


import com.itp.pos.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Example {
    public static void main(String[] args) {
        try {
            Connection c = DBConnection.getInstance()
                    .getConnection();
            c.setAutoCommit(false);
            if (saveStudent()) {
                if (saveRegistration()) {
                    c.commit();
                } else {c.rollback();}
            }else{c.rollback();}

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }finally {
            try{
                DBConnection.getInstance()
                        .getConnection()
                        .setAutoCommit(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private static boolean saveStudent() throws SQLException, ClassNotFoundException {
        PreparedStatement stm =
                DBConnection
                        .getInstance()
                        .getConnection()
                        .prepareStatement(
                                "INSERT INTO student VALUES(?,?,?)"
                        );
        stm.setString(1, "1002");
        stm.setString(2, "Kumara Pradeep");
        stm.setInt(3, 18);
        return stm.executeUpdate() > 0;
    }

    private static boolean saveRegistration() throws SQLException, ClassNotFoundException {
        PreparedStatement stm =
                DBConnection
                        .getInstance()
                        .getConnection()
                        .prepareStatement(
                                "INSERT INTOO registration VALUES(?,?,?,?)"
                        );
        stm.setString(1, "001");
        stm.setObject(2, LocalDate.now());
        stm.setDouble(3, 50000);
        stm.setString(4, "1001");

        return stm.executeUpdate() > 0;
    }

}
