package birzeit.slemanhghithan.as1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class addTask extends AppCompatActivity {
    private EditText edt_task_title;

    private EditText edt_task_description;
    private EditText edt_date;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        setupSharedPrefs();
        edt_task_title = findViewById(R.id.edt_task_title);
        edt_task_description = findViewById(R.id.edt_task_description);
        edt_date = findViewById(R.id.edt_date);
        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(v -> saveTask());

    }


    private void saveTask() {
        String title = edt_task_title.getText().toString();
        String description = edt_task_description.getText().toString();

        Date date_until = stringToDate(edt_date.getText().toString());
        Task task = new Task(title, description, date_until, "due");

        String tasksJson = prefs.getString("tasksKey", "[]");
        Gson gson = new Gson();
        Type taskListType = new TypeToken<List<Task>>() {}.getType();
        List<Task> tasks = gson.fromJson(tasksJson, taskListType);
        tasks.add(task);
        String updatedTasksJson = gson.toJson(tasks);
        editor.putString("tasksKey", updatedTasksJson);
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(date));
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void setupSharedPrefs(){
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor=prefs.edit();
    }
}