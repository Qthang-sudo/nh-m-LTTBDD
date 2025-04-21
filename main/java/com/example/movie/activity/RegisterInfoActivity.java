package com.example.movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movie.R;

public class RegisterInfoActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge;
    private Spinner spinnerGender;
    private Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);

        // Lấy email từ LoginActivity
        String email = getIntent().getStringExtra("email");

        // Ánh xạ các View
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnComplete = findViewById(R.id.btnComplete);

        // Tạo dữ liệu cho Spinner (giới tính)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        // Xử lý sự kiện khi nhấn nút "Hoàn tất"
        btnComplete.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String age = editTextAge.getText().toString().trim();
            String gender = spinnerGender.getSelectedItem().toString();

            if (name.isEmpty() || age.isEmpty()) {
                Toast.makeText(RegisterInfoActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu thông tin người dùng vào SharedPreferences
            SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(email + "_name", name);
            editor.putString(email + "_age", age);
            editor.putString(email + "_gender", gender);
            editor.putString("email", email); // Lưu email để đăng nhập
            editor.apply();

            // Gửi dữ liệu sang MainActivity
            Intent intent = new Intent(RegisterInfoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
