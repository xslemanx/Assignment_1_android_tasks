package birzeit.slemanhghithan.as1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

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

public class editTask extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        setupSharedPrefs();

        boolean editMode = getIntent().getBooleanExtra("editMode", false);

        Task task;
        task = (Task) getIntent().getSerializableExtra("taskDetails");
        TextView edtTitle = findViewById(R.id.edt_task_title);
        EditText edtDescription = findViewById(R.id.edt_task_description);
        EditText edtDate = findViewById(R.id.edt_date);
        Switch aSwitch = findViewById(R.id.switch1);

        edtTitle.setText(task.getTitle());
        edtDescription.setText(task.getDescription());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        edtDate.setText(dateFormat.format(task.getDate()));
        aSwitch.setChecked(task.getStatus().equals("done"));
        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> editTask());
    }
    private void setupSharedPrefs(){
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor=prefs.edit();
    }
    public void editTask() {
        Task existingTask = (Task) getIntent().getSerializableExtra("taskDetails");
        TextView edtTitle = findViewById(R.id.edt_task_title);
        EditText edtDescription = findViewById(R.id.edt_task_description);
        EditText edtDate = findViewById(R.id.edt_date);
        Switch aSwitch = findViewById(R.id.switch1);
        existingTask.setTitle(edtTitle.getText().toString());
        existingTask.setDescription(edtDescription.getText().toString());
        Date newDate = stringToDate(edtDate.getText().toString());
        existingTask.setDate(newDate);
        String newStatus = aSwitch.isChecked() ? "done" : "due";
        existingTask.setStatus(newStatus);
        String tasksJson = prefs.getString("tasksKey", "[]");
        Gson gson = new Gson();
        Type taskListType = new TypeToken<List<Task>>() {}.getType();
        List<Task> tasks = gson.fromJson(tasksJson, taskListType);
        int indexOfTask = tasks.indexOf(existingTask);
        if (indexOfTask != -1) {
            tasks.set(indexOfTask, existingTask);
        }
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

}