package hcu.info.pro3_g20021_application;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditUsersActivity extends AppCompatActivity {

    private UserManager userManager;
    private TextView textViewUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);

        userManager = UserManager.getInstance(this);

        textViewUserList = findViewById(R.id.text_view_user_list);
        updateUserList();

        Button addUserButton = findViewById(R.id.add_user_button);
        addUserButton.setOnClickListener(v -> {
            User user = new User("山田太郎", "2023001", "12345678", 1000);
            userManager.addUser(user);
            updateUserList();
        });
    }

    private void updateUserList() {
        StringBuilder sb = new StringBuilder();
        for (User user : userManager.getUsers()) {
            sb.append("名前: ").append(user.getName()).append("\n");
            sb.append("学籍番号: ").append(user.getStudentId()).append("\n");
            sb.append("IDm: ").append(user.getIdm()).append("\n");
            sb.append("プリペイド残高: ").append(user.getBalance()).append("円\n");
            sb.append("\n");
        }
        textViewUserList.setText(sb.toString());
    }
}
