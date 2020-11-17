package workshop.daoTables;

import workshop.DbUtil;
import workshop.tables.User;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

public class UserDao {
    public static final String DATABASE = "workshop2";
    public static final String CREATE_USER_ROW = "INSERT INTO users2 (email, username, password) VALUES (?, ?, ?);";
    public static final String SHOW_USER_ROW = "SELECT * FROM users2 u WHERE u.id = ?;";
    public static final String UPDATE_USER_ROW = "UPDATE users2 SET email = ?, username = ?, password = ? WHERE id = ?;";
    public static final String DELETE_USER_ROW = "DELETE FROM users2 WHERE id = ?";
    public static final String SELECT_ALL_USER = "SELECT * FROM users2;";

    public static void selectedMethod(UserDao userDao, User user) {
        Scanner scan = new Scanner(System.in);
        DbUtil.selectOption(DbUtil.selectionTask, "user");
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            int selectedOption = 0;
            try {
                selectedOption = Integer.parseInt(input);
                if (selectedOption < 1 || selectedOption > 7) {
                    System.out.println("Please enter a correct number!");
                }
            } catch (NumberFormatException e) {
                System.out.println("It has to be a number!");
            }
            String prompt = "User has been added.";

            switch (selectedOption) {
                case 1: {
                    userDao.create(user);
                    System.out.println(prompt);
                    break;
                }
                case 2: {
                    userDao.createFromConsol(user);
                    System.out.println(prompt);
                    break;
                }
                case 3: {
                    long id = userDao.generateIdFromConsol();
                    System.out.println(userDao.read(id));
                    break;
                }
                case 4: {
                    long id = userDao.generateIdFromConsol();
                    userDao.update(id);
                    break;
                }
                case 5: {
                    long id = userDao.generateIdFromConsol();
                    userDao.delete(id);
                    System.out.println(userDao.read(id));
                    System.out.println("User has been deleted");
                    break;
                }
                case 6: {
                    System.out.println(Arrays.toString(userDao.findAll()));
                    break;
                }
                case 7: {
                    userDao.exit();
                    break;
                }
                default: {
                    System.out.println("This command is not correct. Please try again.");
                }
            }
            System.out.println();
            DbUtil.selectOption(DbUtil.selectionTask, "user");
        }
    }

    private void create(User user) {
        try (Connection conn = DbUtil.getConnection(DATABASE);
             PreparedStatement createRow =
                     conn.prepareStatement(CREATE_USER_ROW, PreparedStatement.RETURN_GENERATED_KEYS);) {
            createRow.setString(1, user.getEmail());
            createRow.setString(2, user.getUsername());
            createRow.setString(3, hashPassword(user.getPassword()));

            setIdGeneratedFromDatabase(user, createRow);
            createRow.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createFromConsol(User user) {
        try (Connection conn = DbUtil.getConnection(DATABASE);
             PreparedStatement createRow =
                     conn.prepareStatement(CREATE_USER_ROW, PreparedStatement.RETURN_GENERATED_KEYS);) {
            user.getDataFromConsol();
            createRow.setString(1, user.getEmail());
            createRow.setString(2, user.getUsername());
            createRow.setString(3, hashPassword(user.getPassword()));

            setIdGeneratedFromDatabase(user, createRow);
            createRow.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User read(long id) {
        try (Connection conn = DbUtil.getConnection(DATABASE);
             PreparedStatement readUserStatement = conn.prepareStatement(SHOW_USER_ROW);) {
            readUserStatement.setLong(1, id);
            ResultSet readUserRs = readUserStatement.executeQuery();
            User user = new User();
            while (readUserRs.next()) {
                user.setId(readUserRs.getLong("id"));
                user.setEmail(readUserRs.getString("email"));
                user.setUsername(readUserRs.getString("username"));
                user.setPassword(readUserRs.getString("password"));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void update(long id) {
        try (Connection conn = DbUtil.getConnection(DATABASE);
             PreparedStatement updateUser = conn.prepareStatement(UPDATE_USER_ROW);) {
            User userToUpdate = this.read(id);
            System.out.println("Before updating:\n" + userToUpdate);
            userToUpdate.getDataFromConsol();
            updateUser.setString(1, userToUpdate.getEmail());
            updateUser.setString(2, userToUpdate.getUsername());
            updateUser.setString(3, this.hashPassword(userToUpdate.getPassword()));
            updateUser.setLong(4, userToUpdate.getId());
            updateUser.executeUpdate();
            System.out.println("After updating:\n" + userToUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void delete(long id) {
        try (Connection conn = DbUtil.getConnection(DATABASE);
             PreparedStatement deleteUser = conn.prepareStatement(DELETE_USER_ROW);) {
            deleteUser.setLong(1, id);
            deleteUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User[] findAll() {
        try (Connection conn = DbUtil.getConnection(DATABASE);
             Statement selectAll = conn.createStatement();) {
            ResultSet selectAllRs = selectAll.executeQuery(SELECT_ALL_USER);
            User[] users = new User[0];
            while (selectAllRs.next()) {
                User user = new User();
                user.setId(selectAllRs.getLong("id"));
                user.setEmail(selectAllRs.getString("email"));
                user.setUsername(selectAllRs.getString("username"));
                user.setPassword(selectAllRs.getString("password"));
                users = addToArray(users, user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User[] addToArray(User[] users, User user) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[tmpUsers.length - 1] = user;
        return tmpUsers;
    }

    private void setIdGeneratedFromDatabase(User user, PreparedStatement prepStat) throws SQLException {
        ResultSet generatedIdRs = prepStat.getGeneratedKeys();
        if (generatedIdRs.next()) {
            long generatedId = generatedIdRs.getLong(1);
            user.setId(generatedId);
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private long generateIdFromConsol() {
        Scanner scan = new Scanner(System.in);
        String message = "Please give an ID: ";
        System.out.println(message);
        while (!scan.hasNextLong()) {
            scan.next();
            System.out.print("It has to be a number. Please try again: ");
        }
        long generatedId = scan.nextInt();
        return generatedId;
    }

    private void exit() {
        System.out.printf("End of the processing.");
        System.exit(0);
    }
}
