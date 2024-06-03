package com.example.soft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.util.ArrayList;

// 모델 클래스
class Exercise implements Serializable {
    private String name;
    private String target;
    private String[] steps;

    public Exercise(String name, String target, String[] steps) {
        this.name = name;
        this.target = target;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public String getTarget() {
        return target;
    }

    public String[] getSteps() {
        return steps;
    }
}

// 커스텀 어댑터
class ExerciseAdapter extends ArrayAdapter<Exercise> {
    private final LayoutInflater inflater;

    public ExerciseAdapter(GoodExerciseActivity context, ArrayList<Exercise> exercises) {
        super(context, 0, exercises);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Exercise exercise = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_exercise, parent, false);
        }
        TextView nameTextView = convertView.findViewById(R.id.exercise_name);
        TextView targetTextView = convertView.findViewById(R.id.exercise_target);
        nameTextView.setText(exercise.getName());
        targetTextView.setText(exercise.getTarget());
        return convertView;
    }
}

// 메인 액티비티
public class GoodExerciseActivity extends AppCompatActivity {
    private ArrayList<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_exercise);

        exerciseList = new ArrayList<>();
        populateExerciseList();

        ListView listView = findViewById(R.id.exercise_list_view);
        ExerciseAdapter adapter = new ExerciseAdapter(this, exerciseList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GoodExerciseActivity.this, ExerciseDetailActivity.class);
                intent.putExtra("exercise", exerciseList.get(position));
                startActivity(intent);
            }
        });
    }

    private void populateExerciseList() {
        exerciseList.add(new Exercise("스쿼트", "허벅지", new String[]{"양 손을 앞으로 뻗는다", "뒤에 투명 의자가 있다는 생각으로 천천히 앉는다"}));
        exerciseList.add(new Exercise("관절 보호 스쿼트", "허벅지", new String[]{"짐볼을 이용해 벽에 허리를 기댄다", "다리는 어깨너비만큼 벌려준다", "손은 팔짱을 낀다"}));
        exerciseList.add(new Exercise("런지", "허벅지", new String[]{"다리를 11자로 만든다", "밑에 의자가 있다고 생각하고 앉는다", "손은 허리에 댄다"}));
        exerciseList.add(new Exercise("윗몸 일으키기", "복근", new String[]{"다리를 바닥에 대고 눕는다", "손으로 머리 뒤를 잡는다", "상체를 반만 일으킨다"}));
        exerciseList.add(new Exercise("짐볼 이용 윗몸 일으키기", "복근", new String[]{"다리를 짐볼 위에 올린다", "손으로 머리 뒤를 잡는다", "몸은 반만 일으킨다"}));
        exerciseList.add(new Exercise("기립근 강화 운동", "허리", new String[]{"배에 짐볼을 대고 엎드린다", "오른쪽 다리를 위로 든다", "중심을 잡은 후 왼쪽 손을 위로 들어준다"}));
        exerciseList.add(new Exercise("브리지 운동", "둔근", new String[]{"다리는 어깨 너비로 벌린다", "손은 바닥에 댄다", "가슴이 아닌 엉덩이를 올려준다"}));
    }

    // 세부 사항 액티비티
    public static class ExerciseDetailActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_exercise_detail);

            Exercise exercise = (Exercise) getIntent().getSerializableExtra("exercise");

            TextView nameTextView = findViewById(R.id.exercise_detail_name);
            TextView targetTextView = findViewById(R.id.exercise_detail_target);
            TextView stepsTextView = findViewById(R.id.exercise_detail_steps);

            nameTextView.setText(exercise.getName());
            targetTextView.setText(exercise.getTarget());
            stepsTextView.setText(String.join("\n", exercise.getSteps()));
        }
    }
}