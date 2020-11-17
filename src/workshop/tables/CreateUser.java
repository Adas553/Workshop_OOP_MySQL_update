package workshop.tables;

import workshop.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateUser {
    public static String CREATE_USER_QUERY = "CREATE TABLE users3 (\n" +
            "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "    email VARCHAR(255) UNIQUE,\n" +
            "    username VARCHAR(255),\n" +
            "    password VARCHAR(60)\n" +
            ");";

    public static void main(String[] args) {
        try (Connection conn = DbUtil.getConnection("workshop2");) {
            Statement createUserStat = conn.createStatement();
            createUserStat.executeUpdate(CREATE_USER_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
