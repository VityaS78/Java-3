package Java3.Lesson_3.Server;

import java.sql.*;

public class SQLAuth {
    private static Connection con;
    private static String username = "postgres";
    private static String password = "admin";
    private static String URL = "jdbc:postgresql://localhost:5432/auth";


    public static void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            try {
                con = DriverManager.getConnection(URL, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getNick(String[] auth) {
        try {
            connect();
            Statement st = con.createStatement();

            String query = "select * from auth ";
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                if (resultSet.getString("login").equals(auth[0]) &&
                        resultSet.getString("password").equals(auth[1]))
                    return resultSet.getString("nickname");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
