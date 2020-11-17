package workshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    public static final String[] selectionTask = {"create", "createFromConsol", "read", "update", "delete", "findAll", "exit"};
    public static final String DB_URL_PATTERN = "jdbc:mysql://localhost:3306/%s?useSSL=false&characterEncoding=utf8";
    public static final String USER = "adam";
    public static final String PASSWORD = "coderslab";

    public static Connection getConnection(String database) throws SQLException {
        String dbURL = String.format(DB_URL_PATTERN, database);
        return DriverManager.getConnection(dbURL, USER, PASSWORD);
    }

    public static void selectOption(String[] tab, String table) {
        System.out.println(String.format("Please select an option (nummber) what to do with provided %s: ", table));
        for (int i = 0; i < tab.length; i++) {
            System.out.println(String.format("%d: %s", i + 1, tab[i]));
        }
    }

}
