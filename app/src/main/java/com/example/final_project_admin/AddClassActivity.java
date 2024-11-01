package com.example.final_project_admin;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.final_project_admin.db.DatabaseHelper;

import java.util.Calendar;
public class AddClassActivity extends AppCompatActivity {
    Spinner dayOfWeek, classType;
    EditText description, price, duration, capacity, teacherName;
    TextView timeCourse;
    Button addClassBtn;
    DatabaseHelper DB;
    private boolean isPickedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addClassBtn = findViewById(R.id._addClassBtn);
        classType = findViewById(R.id.typeOfClass);
        price = findViewById(R.id.inputPrice);
        duration = findViewById(R.id.inputDuration);
        capacity = findViewById(R.id.inputCapacity);
        timeCourse = findViewById(R.id.inputTime);
        dayOfWeek = findViewById(R.id.inputDay);
        description = findViewById(R.id.inputDescription);
        teacherName = findViewById(R.id.inputTeacher);
        isPickedTime = false;
        DB = new DatabaseHelper(AddClassActivity.this);

        // text overflow
        textOverFlow(); // done
        showDropdownList(dayOfWeek); //done
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.class_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classType.setAdapter(adapter);//cho loai lop vao select
//        handle add button
        addClassBtn.setOnClickListener(v -> {
            if (!validateField(description) || !validateField(capacity) || !validateField(duration) || !validateField(price)) {
                return;
            }
            if (!isPickedTime){
                Toast.makeText(this, "Please pick a time!", Toast.LENGTH_SHORT).show();
                return;
            }
            String selectedDay = dayOfWeek.getSelectedItem().toString();
            String timeOfCourse = timeCourse.getText().toString();
            int _capacity = Integer.parseInt(capacity.getText().toString());
            int _duration = Integer.parseInt(duration.getText().toString());
            double _price = Double.parseDouble(price.getText().toString());
            String _classType = classType.getSelectedItem().toString();
            String teacher = teacherName.getText().toString();
            String _description = description.getText().toString();
            boolean checkAddClass = DB.addClass(selectedDay, timeOfCourse, _capacity, _duration, _price, _classType, teacher, _description);
            if (checkAddClass){
                Toast.makeText(this, "Added class successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddClassActivity.this, HomeActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Failed to add class!", Toast.LENGTH_SHORT).show();
            }
        });

        // time picker

        timeCourse.setOnClickListener(v -> showTimePickerDialog(timeCourse));

    }
    private void textOverFlow(){
        description.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                description.setSingleLine();
                description.setHorizontallyScrolling(true);
            }
        });
    }
    private void showTimePickerDialog(TextView timeEditText) {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Initialize the TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (TimePicker view, int selectedHour, int selectedMinute) -> {
                    // Format the selected time and set it to EditText
                    String time = String.format("%02d:%02d", selectedHour, selectedMinute);

                    timeEditText.setText(time);
                    isPickedTime = true;
                }, hour, minute, true); // Set true for 24-hour format, false for AM/PM format

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

    private void showDropdownList(Spinner spinner){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Set a listener to handle selections
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if nothing is selected
            }
        });
    }
    private boolean validateField(EditText field){
        if(field.getText().toString().trim().isEmpty()){
            field.setError("This field is required!");
            return false;
        }
        return true;
    }
}
