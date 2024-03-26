package edu.vvk_pit_21_i_nt.countyourcalories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    Button buttonNext;
    TextView upperText;
    EditText inputText;
    final String[] genderList = {"Vyras", "Moteris"};
    final String[] mainGoal = {"Auginti masę", "Numesti svorio", "Išlaikyti svorį"};
    final String[] activityDesc = {"0-1", "1-3", "3-5", "6-7", "2 k./d."};
    final String[] questions = {"Identifikuokite save", "Pasirinkite savo tikslą", "Treniruočių skaičius","Įveskite savo amžių", "Įveskite savo svorį", "Įveskite savo ūgį", "Pasirinkite savo aktyvumo lygį"};

    String gender;
    int goal, age, height, weight;
    float activityLevel;

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

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (preMenuStage) {
                    case 0:
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                gender = parentView.getItemAtPosition(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {

                            }
                        });
                        preMenuStage++;
                        break;
                    case 1:
                        setUpGUI(1, mainGoal);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                                String value = parentView.getItemAtPosition(position).toString();

                                switch (value) {
                                    case "Auginti masę":
                                        goal = 0;
                                        break;
                                    case "Numesti svorio":
                                        goal = 1;
                                        break;
                                    case "Išlaikyti svorį":
                                        goal = 2;
                                        break;
                                    default:
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // Handle the case when nothing is selected (if needed)
                            }
                        });
                        preMenuStage++;
                        break;
                    case 2:
                        setUpGUI(2, activityDesc);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                                String value = parentView.getItemAtPosition(position).toString();

                                switch (value) {
                                    case "0-1":
                                        activityLevel = 1.2f;
                                        break;
                                    case "1-3":
                                        activityLevel = 1.375f;
                                        break;
                                    case "3-5":
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
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // Handle the case when nothing is selected (if needed)
                            }
                        });
                        preMenuStage++;
                        break;
                    case 3:
//                        Log.d("TAG", gender + " " + goal + " " + age);
                        upperText.setText(questions[3]);
                        spinner.setVisibility(View.INVISIBLE);
                        inputText.setVisibility(View.VISIBLE);

                        buttonNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String ageText = inputText.getText().toString();

                                try {
                                    age = Integer.parseInt(ageText);
                                } catch (NumberFormatException e) {
                                    Toast.makeText(MainActivity.this, "Please enter a valid age", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                // Proceed to the next stage or perform other actions as needed
                                preMenuStage++;
                                // Update the UI or perform other actions based on the preMenuStage value
                                switch (preMenuStage) {
                                    // Handle other cases as needed
                                    default:
                                        break;
                                }
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setUpGUI(int questionNum, String[] valuesArr) {
        upperText.setText(questions[questionNum]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_dropdown_item, valuesArr);
        adapter.setDropDownViewResource(R.layout.custom_dropdown_item);
        spinner.setAdapter(adapter);
    }


}
