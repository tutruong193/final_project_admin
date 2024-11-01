package com.example.final_project_admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project_admin.db.DatabaseHelper;

public class ClassDetailsActivity extends AppCompatActivity {

    private EditText etTeacher, etClassType, etClassTime, etDuration, etDayOfWeek, etCapacity, etPrice, etDescription;
    private Button btnUpdate, btnBack;
    private DatabaseHelper DB;
    private int classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        DB = new DatabaseHelper(this);

        etTeacher = findViewById(R.id.etTeacher);
        etClassType = findViewById(R.id.etClassType);
        etClassTime = findViewById(R.id.etClassTime);
        etDuration = findViewById(R.id.etDuration);
        etDayOfWeek = findViewById(R.id.etDayOfWeek);
        etCapacity = findViewById(R.id.etCapacity);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        classId = intent.getIntExtra("CLASS_ID", -1);
        if (classId != -1) {
            loadClassDetails(classId);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateClass();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadClassDetails(int classId) {
        Cursor cursor = DB.getClassDetails(classId);

        if (cursor != null && cursor.moveToFirst()) {
            // Thêm kiểm tra `-1` cho từng cột trước khi lấy giá trị
            int teacherIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TEACHER);
            if (teacherIndex != -1) {
                etTeacher.setText(cursor.getString(teacherIndex));
            }

            int classTypeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TYPE);
            if (classTypeIndex != -1) {
                etClassType.setText(cursor.getString(classTypeIndex));
            }

            int classTimeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TIME);
            if (classTimeIndex != -1) {
                etClassTime.setText(cursor.getString(classTimeIndex));
            }

            int durationIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DURATION);
            if (durationIndex != -1) {
                etDuration.setText(cursor.getString(durationIndex));
            }

            int dayOfWeekIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DAY_OF_WEEK);
            if (dayOfWeekIndex != -1) {
                etDayOfWeek.setText(cursor.getString(dayOfWeekIndex));
            }

            int capacityIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CAPACITY);
            if (capacityIndex != -1) {
                etCapacity.setText(cursor.getString(capacityIndex));
            }

            int priceIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE);
            if (priceIndex != -1) {
                etPrice.setText(cursor.getString(priceIndex));
            }

            int descriptionIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION);
            if (descriptionIndex != -1) {
                etDescription.setText(cursor.getString(descriptionIndex));
            }

            cursor.close();
        } else {
            Toast.makeText(this, "Không thể tải thông tin lớp học", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateClass() {
        String teacher = etTeacher.getText().toString();
        String classType = etClassType.getText().toString();
        String classTime = etClassTime.getText().toString();
        int duration = Integer.parseInt(etDuration.getText().toString());
        String dayOfWeek = etDayOfWeek.getText().toString();
        int capacity = Integer.parseInt(etCapacity.getText().toString());
        double price = Double.parseDouble(etPrice.getText().toString());
        String description = etDescription.getText().toString();

        boolean isUpdated = DB.updateClass(classId, dayOfWeek, classTime, capacity, duration, price, classType, teacher, description);

        if (isUpdated) {
            Toast.makeText(this, "Class updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update class", Toast.LENGTH_SHORT).show();
        }
    }
}