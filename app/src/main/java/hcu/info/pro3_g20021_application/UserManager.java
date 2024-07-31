package hcu.info.pro3_g20021_application;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static UserManager instance;
    private List<User> users;

    private UserManager() {
        users = new ArrayList<>();
        // デバッグ用にいくつかのユーザーを追加
        users.add(new User("山田太郎", "2023001", "12345678", 1000));
        users.add(new User("鈴木一郎", "2023002", "87654321", 500));
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
