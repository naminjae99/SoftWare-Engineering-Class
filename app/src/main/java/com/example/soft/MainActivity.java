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
                if(!validateInputs()){
                    return;
                }
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
    // EditText 입력 유효성을 확인하는 메서드
    private boolean validateInputs() {
        boolean isValid = true;

        String name = editTextName.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();
        String heightStr = editTextHeight.getText().toString().trim();
        String weightStr = editTextWeight.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("이름을 입력해주세요");
            isValid = false;
        } else if (!name.matches("[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ ]+")) {
            editTextName.setError("올바른 이름을 입력해주세요\n(특수문자, 숫자 제외)");
            isValid = false;
        }

        if (ageStr.isEmpty()) {
            editTextAge.setError("나이를 입력해주세요");
            isValid = false;
        } else {
            try {
                int age = Integer.parseInt(ageStr);
                if (age <= 0 || age > 150) {
                    editTextAge.setError("올바른 나이를 입력해주세요\n(1~150)");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                editTextAge.setError("숫자로 입력해주세요");
                isValid = false;
            }
        }

        if (heightStr.isEmpty()) {
            editTextHeight.setError("키를 입력해주세요");
            isValid = false;
        } else {
            try {
                float height = Float.parseFloat(heightStr);
                if (height <= 0 || height > 250) {
                    editTextHeight.setError("올바른 키를 입력해주세요\n(1~250)");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                editTextHeight.setError("숫자로 입력해주세요");
                isValid = false;
            }
        }

        if (weightStr.isEmpty()) {
            editTextWeight.setError("몸무게를 입력해주세요");
            isValid = false;
        } else {
            try {
                float weight = Float.parseFloat(weightStr);
                if (weight <= 0 || weight > 250) {
                    editTextWeight.setError("올바른 몸무게를 입력해주세요\n(1~250)");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                editTextWeight.setError("숫자로 입력해주세요");
                isValid = false;
            }
        }

        return isValid;
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
