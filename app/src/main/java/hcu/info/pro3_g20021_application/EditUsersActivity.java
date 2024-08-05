package hcu.info.pro3_g20021_application;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class EditUsersActivity extends AppCompatActivity {

    private UserManager userManager;
    private TextView textViewUserList;
    private EditText editTextUserName;
    private EditText editTextStudentId;
    private EditText editTextBalance;
    private String IDm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);

        userManager = UserManager.getInstance(this);

        textViewUserList = findViewById(R.id.text_view_user_list);
        editTextUserName = findViewById(R.id.edit_text_user_name);
        editTextStudentId = findViewById(R.id.edit_text_student_id);

        // フェリカから受け取ったIDmを取得
        IDm = getIntent().getStringExtra("IDm");

        updateUserList();

        Button addUserButton = findViewById(R.id.add_user_button);
        addUserButton.setOnClickListener(v -> {
            String userName = editTextUserName.getText().toString();
            String studentId = editTextStudentId.getText().toString();

            if (!userName.isEmpty() && !studentId.isEmpty() && IDm != null) {
                User user = new User(userName, studentId, IDm, 0);
                if(userManager.addUser(user) == -1) {
                    //IDm重複
                    Toast.makeText(this, "ユーザーを編集しました", Toast.LENGTH_SHORT).show();
                };
                updateUserList();
                Toast.makeText(this, "ユーザを登録しました", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "すべてのフィールドを入力してください", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserList() {
        StringBuilder sb = new StringBuilder();
        List<User> users = userManager.getUsers();
        for (User user : users) {
            sb.append("名前: ").append(user.getName()).append("\n");
            sb.append("学籍番号: ").append(user.getStudentId()).append("\n");
            sb.append("IDm: ").append(user.getIDm()).append("\n");
            sb.append("残高: ").append(user.getBalance()).append("円\n");
            sb.append("\n");
        }
        textViewUserList.setText(sb.toString());
    }
}
