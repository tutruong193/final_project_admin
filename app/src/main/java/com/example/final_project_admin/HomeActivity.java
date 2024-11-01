package com.example.final_project_admin;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project_admin.db.CustomAdapter;
import com.example.final_project_admin.db.DatabaseHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    EditText searchInput;
    TextView searchBtn, addClassBtn;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    DatabaseHelper DB;
    ArrayList<String> teacher, classTime, classType, duration;
    ArrayList<Integer> classIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchInput = findViewById(R.id.searchInput);
        searchBtn = findViewById(R.id.searchBtn);
        addClassBtn = findViewById(R.id.addClassBtn);

        teacher = new ArrayList<>();
        classTime = new ArrayList<>();
        classType = new ArrayList<>();
        duration = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        classIds = new ArrayList<>();

        DB = new DatabaseHelper(HomeActivity.this);
        adapter = new CustomAdapter(HomeActivity.this, this, teacher, classType,classTime, duration, classIds);


        // set up custom adapter
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

        // display class into recyclerView
        displayClass();


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Searching!", Toast.LENGTH_SHORT).show();
            }
        });

        addClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddClassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayClass() {
        Cursor cs = DB.getAllClasses();
        if (cs.getCount() == 0) {
            Toast.makeText(this, "No class available!", Toast.LENGTH_SHORT).show();
        } else {
            while (cs.moveToNext()) {
                classIds.add(cs.getInt(0));  // Add class ID
                teacher.add(cs.getString(7));
                classType.add(cs.getString(6));
                classTime.add(cs.getString(2));
                duration.add(cs.getString(4));
            }
        }
    }
    private void deleteClass(int position) {
        // Xóa lớp từ database
        // Cập nhật danh sách và thông báo adapter để hiển thị lại dữ liệu
        teacher.remove(position);
        classType.remove(position);
        classTime.remove(position);
        duration.remove(position);
        adapter.notifyItemRemoved(position);
        Toast.makeText(this, "Lớp đã được xóa", Toast.LENGTH_SHORT).show();
    }
    public void refreshData() {
        classIds.clear();
        teacher.clear();
        classType.clear();
        classTime.clear();
        duration.clear();
        displayClass();
        adapter.notifyDataSetChanged();
    }
}