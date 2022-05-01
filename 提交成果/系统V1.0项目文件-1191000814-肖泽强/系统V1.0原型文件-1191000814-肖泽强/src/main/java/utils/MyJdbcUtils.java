package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class MyJdbcUtils {

    public static ResultSet executeQuery(String sql, Connection aConnection) {
        try {
            PreparedStatement aStatement = aConnection.prepareStatement(sql);
            ResultSet aResultSet = aStatement.executeQuery(sql);
            return aResultSet;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static void executeUpdate(String sql, Connection aConnection) {
        PreparedStatement aStatement;
        try {
            aStatement = aConnection.prepareStatement(sql);
            try {
                aStatement.executeUpdate(sql);
                if(aStatement!=null){
                    aStatement.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
