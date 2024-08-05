package hcu.info.pro3_g20021_application;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static UserManager instance;
    private List<User> users;
    private static final String FILE_NAME = "users.txt";
    private Context context;

    private UserManager(Context context) {
        this.context = context;
        users = new ArrayList<>();
        loadUsersFromFile();
        // デバッグ用にいくつかのユーザーを追加
        if (users.isEmpty()) {
            users.add(new User("山田太郎", "2023001", "12345678", 1000));
            users.add(new User("鈴木一郎", "2023002", "87654321", 500));
        }
    }

    public static UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context);
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
        saveUsersToFile();
    }

    private void loadUsersFromFile() {
        File file = new File(context.getFilesDir(), FILE_NAME);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    String studentId = parts[1];
                    String idm = parts[2];
                    int balance = Integer.parseInt(parts[3]);
                    users.add(new User(name, studentId, idm, balance));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
        }
    }

    private void saveUsersToFile() {
        File file = new File(context.getFilesDir(), FILE_NAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (User user : users) {
                writer.write(String.format("%s,%s,%s,%d\n",
                        user.getName(),
                        user.getStudentId(),
                        user.getIdm(),
                        user.getBalance()));
            }
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }
}
