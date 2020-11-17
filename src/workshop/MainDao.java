package workshop;

import workshop.daoTables.UserDao;
import workshop.tables.User;

import java.util.Scanner;

public class MainDao {
    public static void main(String[] args) {
//        User user1 = new User("wolodyjowski52@wp.pl", "Pan Wołodyjowski", "Wolodyjowskki45");
//        UserDao userDao1 = new UserDao();
//        userDao1.create(user1);
//        User user2 = new User("mickiewicz@op.pl", "Adam Mickiewicz", "mickiewicz23");
//        UserDao userDao2 = new UserDao();
//        UserDao.selectedMethod(userDao2, user2);
        User user3 = new User();
        UserDao userDao = new UserDao();
        UserDao.selectedMethod(userDao, user3);



    }
}
