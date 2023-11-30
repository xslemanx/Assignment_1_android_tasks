package birzeit.slemanhghithan.as1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskDetailes extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detailes);
        setupSharedPrefs();
        Task task = (Task) getIntent().getSerializableExtra("taskDetails");

        TextView txt_title = findViewById(R.id.txt_title);
        TextView txt_description = findViewById(R.id.txt_description);
        TextView txt_date = findViewById(R.id.txt_date);
        TextView txt_status = findViewById(R.id.txt_status);

        Button btn_delete = findViewById(R.id.btn_delete);

        txt_title.setText(task.getTitle());
        txt_description.setText(task.getDescription());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        txt_date.setText(dateFormat.format(task.getDate()));

        txt_status.setText(task.getStatus());
        Button editButton = findViewById(R.id.btn_edit);
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, editTask.class);
            intent.putExtra("taskDetails", task);
            startActivity(intent);
        });

        btn_delete.setOnClickListener(v -> deleteTask());
    }
    private void deleteTask() {
        String tasksJson = prefs.getString("tasksKey", "[]");
        Gson gson = new Gson();
        Type taskListType = new TypeToken<List<Task>>() {}.getType();
        List<Task> tasks = gson.fromJson(tasksJson, taskListType);
        Task task = (Task) getIntent().getSerializableExtra("taskDetails");
        tasks.remove(task);
        String updatedTasksJson = gson.toJson(tasks);
        editor.putString("tasksKey", updatedTasksJson);
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void setupSharedPrefs(){
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor=prefs.edit();
    }

}