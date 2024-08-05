package hcu.info.pro3_g20021_application;

import static java.lang.String.valueOf;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddBalanceActivity extends AppCompatActivity {

    private UserManager userManager;
    private EditText editTextIDm;
    private EditText editTextUserName;
    private EditText editTextStudentId;
    private EditText editTextBalance;
    private EditText editTextAddBalance;
    private String IDm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_balance);

        userManager = UserManager.getInstance(this);
        editTextIDm = findViewById(R.id.edit_text_idm);
        editTextUserName = findViewById(R.id.edit_text_user_name);
        editTextStudentId = findViewById(R.id.edit_text_student_id);
        editTextBalance = findViewById(R.id.edit_text_balance);
        editTextAddBalance = findViewById(R.id.edit_text_add_balance);

        IDm = getIntent().getStringExtra("IDm");
        editTextIDm.setText("IDm: " + IDm);
        editTextIDm.setEnabled(false); // 編集不可

        // 情報表示
        User user = userManager.getUser(IDm);
        if (user == null) {
            Toast.makeText(this, "未登録ユーザです", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        editTextUserName.setText("ユーザ名: " + user.getName());
        editTextStudentId.setText("学籍番号(職員番号): " + user.getStudentId());
        editTextBalance.setText(valueOf("残高: " + user.getBalance()));

        editTextUserName.setEnabled(false);
        editTextStudentId.setEnabled(false);
        editTextBalance.setEnabled(false);

        Button addBalanceButton = findViewById(R.id.add_balance_button);
        addBalanceButton.setOnClickListener(v -> {
            String userName = editTextUserName.getText().toString();
            String studentId = editTextStudentId.getText().toString();

            if (!userName.isEmpty() && !studentId.isEmpty() && IDm != null) {
                int newBalance; // 追加後の残高
                try {
                    newBalance = user.getBalance() + Integer.parseInt(editTextAddBalance.getText().toString());
                } catch (NumberFormatException e){
                    Toast.makeText(this, "金額の書式が不正です", Toast.LENGTH_SHORT).show();
                    return;
                }
                User add_balance_user = new User(userName, studentId, IDm, newBalance);
                userManager.addUser(add_balance_user);
                Toast.makeText(this, "残高を追加しました", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "情報が不足しています", Toast.LENGTH_SHORT).show();
            }
        });

    }
}