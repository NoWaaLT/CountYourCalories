package edu.vvk_pit_21_i_nt.countyourcalories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    Button buttonNext;
    TextView upperText;
    EditText inputText;
    final String[] genderList = {"Vyras", "Moteris"};
    final String[] mainGoal = {"Auginti masę", "Numesti svorio", "Išlaikyti svorį"};
    final String[] activityDesc = {"0-1", "2-3", "4-5", "6-7", "2 k./d."};
    final String[] questions = {"Identifikuokite save", "Pasirinkite savo tikslą", "Treniruočių skaičius","Įveskite savo amžių", "Įveskite savo svorį", "Įveskite savo ūgį (CM)"};

    String gender;
    int goal, age, height, targetKcal, difference;
    float activityLevel, weight, bmr;

    int preMenuStage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upperText = findViewById(R.id.textView2);
        spinner = findViewById(R.id.gender_spinner);
        buttonNext = findViewById(R.id.button_next);
        inputText = findViewById(R.id.inputText);

        setUpGUI(0, genderList);

        buttonNext.setOnClickListener(v -> {
            switch (preMenuStage) {
                case 0:
                    gender = spinner.getSelectedItem().toString();
                    preMenuStage++;
                    setUpGUI(1, mainGoal);
                    break;
                case 1:
                    String goalString = spinner.getSelectedItem().toString();
                    switch (goalString) {
                        case "Auginti masę":
                            goal = 0;
                            difference = 300;
                            break;
                        case "Numesti svorio":
                            goal = 1;
                            difference = -300;
                            break;
                        case "Išlaikyti svorį":
                            goal = 2;
                            difference = 0;
                            break;
                        default:
                            break;
                    }
                    preMenuStage++;
                    setUpGUI(2, activityDesc);
                    break;
                case 2:
                    String activityString = spinner.getSelectedItem().toString();
                    switch (activityString) {
                        case "0-1":
                            activityLevel = 1.2f;
                            break;
                        case "2-3":
                            activityLevel = 1.375f;
                            break;
                        case "4-5":
                            activityLevel = 1.55f;
                            break;
                        case "6-7":
                            activityLevel = 1.725f;
                            break;
                        case "2 k./d.":
                            activityLevel = 1.9f;
                            break;
                        default:
                            break;
                    }
                    preMenuStage++;
                    setUpGUI(3, "Amžius");
                    spinner.setVisibility(View.INVISIBLE);
                    inputText.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    age = Integer.parseInt(inputText.getText().toString());
                    preMenuStage++;
                    setUpGUI(4, "Ūgis");
                    break;
                case 4:
                    weight = Float.parseFloat(inputText.getText().toString());
                    preMenuStage++;
                    setUpGUI(5, "Svoris");
                    break;
                case 5:
                    height = Integer.parseInt(inputText.getText().toString());
                    bmr = calcBmr(weight, height, age, gender);
                    targetKcal = calcTargetKcal(bmr, activityLevel, difference);
                    Log.d("TAG", "gender " + gender + ", age " + age + "," +
                            " goal " + goal + ", height " + height + ", activityLevel " +
                            activityLevel + ", weight " + weight + ", bmr " + bmr + ", target " + targetKcal);

                    // Moves to the next activity

                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        });
    }

    private void setUpGUI(int questionNum, String[] valuesArr) {
        upperText.setText(questions[questionNum]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_dropdown_item, valuesArr);
        adapter.setDropDownViewResource(R.layout.custom_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setUpGUI(int questionNum, String name) {
        upperText.setText(questions[questionNum]);
        inputText.setText("");
        inputText.setHint(name);
    }

    private float calcBmr(float weight, int height, int age, String gender) {
        return (gender.equals("Vyras")) ? calcManBMR(weight, height, age) : calcWomanBMR(weight, height, age);
    }

    private float calcManBMR(float weight, int height, int age) {
    return (10f * weight) + (6.25f * height) - (5f * age) + 5f;
    }

    private float calcWomanBMR(float weight, int height, int age) {
       return (10f * weight) + (6.25f * height) - (5f * age) - 16;
    }

    private int calcTargetKcal(float bmr, float activityLevel, int difference) {
        return (int) (bmr * activityLevel) + difference;
    }
}