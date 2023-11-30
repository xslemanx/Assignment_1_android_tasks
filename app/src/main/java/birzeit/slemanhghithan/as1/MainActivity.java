package birzeit.slemanhghithan.as1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_add_task = findViewById(R.id.btn_add_task);
        ListView lst_of_tasks = findViewById(R.id.lst_of_tasks);

        btn_add_task.setOnClickListener(v -> {
            Intent intent = new Intent(this, addTask.class);
            startActivity(intent);
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String tasksJson = prefs.getString("tasksKey", "[]");
        Gson gson = new Gson();
        Type taskListType = new TypeToken<List<Task>>() {}.getType();
        List<Task> tasks = gson.fromJson(tasksJson, taskListType);

        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(
                this,
                R.layout.item,
                tasks
        ) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view;
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    view = inflater.inflate(R.layout.item, parent, false);
                } else {
                    view = convertView;
                }
                Task task = getItem(position);
                TextView textViewTitle = view.findViewById(R.id.textViewTitle);
                TextView textViewStatus = view.findViewById(R.id.textViewStatus);

                if (task != null) {
                    textViewTitle.setText(task.getTitle());
                    textViewStatus.setText(task.getStatus());
                }

                return view;
            }
        };

        lst_of_tasks.setAdapter(adapter);
        lst_of_tasks.setOnItemClickListener((parent, view, position, id) -> {
            Task selectedTask = (Task) parent.getItemAtPosition(position);
            viewTaskDetails(selectedTask);
        });
    }

    private void viewTaskDetails(Task task) {
        Intent intent = new Intent(this, TaskDetailes.class);
        intent.putExtra("taskDetails",  task);
        startActivity(intent);

    }
}
