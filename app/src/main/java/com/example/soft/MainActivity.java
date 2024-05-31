package com.example.soft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge, editTextHeight, editTextWeight;
    private RadioGroup radioGroupGender, radioGroupHypertension, radioGroupHeartDisease, radioGroupSmoking;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레이아웃에서 위젯들을 찾아와 변수에 할당
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupHypertension = findViewById(R.id.radioGroupHypertension);
        radioGroupHeartDisease = findViewById(R.id.radioGroupHeartDisease);
        radioGroupSmoking = findViewById(R.id.radioGroupSmoking);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Submit 버튼 클릭 시 실행될 리스너 설정
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자 입력 값 가져오기
                String name = editTextName.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());
                float height = Float.parseFloat(editTextHeight.getText().toString());
                float weight = Float.parseFloat(editTextWeight.getText().toString());

                // 라디오 버튼 값 가져오기
                int gender = getSelectedRadioValue(radioGroupGender);
                int hypertension = getSelectedRadioValue(radioGroupHypertension);
                int heartDisease = getSelectedRadioValue(radioGroupHeartDisease);
                int smoking = getSelectedRadioValue(radioGroupSmoking);

                // 다음 화면으로 전달할 데이터를 Intent에 추가
                Intent intent = new Intent(MainActivity.this, MyProbability.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("height", height);
                intent.putExtra("weight", weight);
                intent.putExtra("gender", gender);
                intent.putExtra("hypertension", hypertension);
                intent.putExtra("heartDisease", heartDisease);
                intent.putExtra("smoking", smoking);

                // 다음 화면으로 이동
                startActivity(intent);
            }
        });
    }

    // 라디오 그룹에서 선택된 라디오 버튼의 값 반환하는 메서드
    private int getSelectedRadioValue(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        if (radioButton != null) {
            String selectedValue = radioButton.getText().toString();
            return selectedValue.equals("Yes") || selectedValue.equals("Male") ? 1 : 0;
        }
        return -1;
    }
}
