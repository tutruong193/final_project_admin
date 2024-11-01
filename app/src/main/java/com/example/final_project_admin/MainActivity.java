package com.example.final_project_admin;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View loadingImage = findViewById(R.id.loadingImage);
        View loginForm = findViewById(R.id.loginForm);
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);

        // Sử dụng Handler để hiển thị form đăng nhập sau 1 giây
        new Handler().postDelayed(() -> {
            loadingImage.setVisibility(View.GONE);
            loginForm.setVisibility(View.VISIBLE);
        }, 1000);

        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Kết thúc MainActivity để không quay lại được
            // Kiểm tra tên đăng nhập và mật khẩu
//            if (username.equals("admin") && password.equals("admin")) {
//                // Chuyển đến HomeActivity nếu đăng nhập thành công
//
//                Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//            } else {
//                // Hiển thị thông báo lỗi nếu đăng nhập thất bại
//                Toast.makeText(MainActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
//            }
        });
    }
}
