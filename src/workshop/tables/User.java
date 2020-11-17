package workshop.tables;

import java.util.InputMismatchException;
import java.util.Scanner;

public class User {
    long id;
    String email;
    String username;
    String password;

    public User() {
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void getDataFromConsol() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter data in the format: email~username~password: ");
        if (scan.hasNextLine()) {
            String line = scan.nextLine();

            String[] tabLine = line.split("~");

            if (tabLine.length != 3 || !tabLine[0].contains("@")) {
                throw new InputMismatchException("Provided data are not correct. Please try again.");
            }
            else {
                setEmail(tabLine[0]);
                setUsername(tabLine[1]);
                setPassword(tabLine[2]);
            }
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                "}\n";
    }
}
