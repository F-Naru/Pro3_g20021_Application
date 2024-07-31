package hcu.info.pro3_g20021_application;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;

    public UserManager() {
        users = new ArrayList<>();
        // デバッグ用にユーザーを追加
        users.add(new User("西 正博", "0000000", "", 0));
        users.add(new User("新 浩一", "0000000", "", 0));
        users.add(new User("小林 真", "0000000", "", 0));
        users.add(new User("山田 裕太", "0000000", "", 0));
        users.add(new User("立石 李希人", "0000000", "", 0));
        users.add(new User("佐々木 ほのか", "0000000", "", 0));
        users.add(new User("奥田 夕稀", "0000000", "", 0));
        users.add(new User("鵤 篤志", "0000000", "", 0));
        users.add(new User("八津川 航希", "0000000", "", 0));
        users.add(new User("松田 遼", "0000000", "", 0));
        users.add(new User("柳田 雄生", "0000000", "", 0));
        users.add(new User("付 宇航", "0000000", "", 0));
        users.add(new User("西川 潤", "0000000", "", 0));
        users.add(new User("上田 徳彦", "0000000", "", 0));
        users.add(new User("遠藤 夏輝", "0000000", "", 0));
        users.add(new User("藤原 陸", "0000000", "", 0));
        users.add(new User("高橋 朗満", "0000000", "", 0));

    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public User findUserByIdm(String idm) {
        for (User user : users) {
            if (user.getIdm().equals(idm)) {
                return user;
            }
        }
        return null;
    }
}
