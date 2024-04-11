package edu.vvk_pit_21_i_nt.countyourcalories;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    Button buttonNext;
    Button buttonBack;
    TextView upperText;
    EditText inputText;
    Button signoutButton;
    FirebaseUser user;
    private DatabaseReference mDatabase;
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
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        

        if (user == null) {
            signIn();
        }
        else {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }


        upperText = findViewById(R.id.textView2);
        spinner = findViewById(R.id.gender_spinner);
        buttonNext = findViewById(R.id.button_next);
        buttonBack = findViewById(R.id.button_back);
        inputText = findViewById(R.id.inputText);
        signoutButton = findViewById(R.id.signout_button);
        signOut();
        setUpGUI(0, genderList);

        buttonNext.setOnClickListener(v -> {
            String inputString;
            switch (preMenuStage) {
                case 0:
                    gender = spinner.getSelectedItem().toString();
                    preMenuStage++;
                    setUpGUI(1, mainGoal);
                    buttonBack.setVisibility(View.VISIBLE);
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
                    inputString = inputText.getText().toString();
                    if(!inputString.isEmpty()) {
                        age = Integer.parseInt(inputString);
                        preMenuStage++;
                        setUpGUI(4, "Svoris");
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Nepalikite tuščio laukelio!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 4:
                    inputString = inputText.getText().toString();
                    if(!inputString.isEmpty()) {
                        weight = Float.parseFloat(inputText.getText().toString());
                        preMenuStage++;
                        setUpGUI(5, "Ūgis");
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Nepalikite tuščio laukelio!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 5:
                    inputString = inputText.getText().toString();
                    if(!inputString.equals("")) {
                        height = Integer.parseInt(inputText.getText().toString());
                        bmr = calcBmr(weight, height, age, gender);
                        targetKcal = calcTargetKcal(bmr, activityLevel, difference);
                        Log.d("TAG", "gender " + gender + ", age " + age + "," +
                                " goal " + goal + ", height " + height + ", activityLevel " +
                                activityLevel + ", weight " + weight + ", bmr " + bmr + ", target " + targetKcal);


                        addUserData();


                        // Moves to the next activity

                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Nepalikite tuščio laukelio!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        });

        buttonBack.setOnClickListener(v -> {
            if(preMenuStage != 0) {
                switch(preMenuStage) {
                    case 1:
                        setUpGUI(0, genderList);
                        preMenuStage--;
                        buttonBack.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        setUpGUI(1, mainGoal);
                        preMenuStage--;
                        break;
                    case 3:
                        setUpGUI(2, activityDesc);
                        spinner.setVisibility(View.VISIBLE);
                        inputText.setVisibility(View.INVISIBLE);
                        preMenuStage--;
                        break;
                    case 4:
                        setUpGUI(3, "Amžius");
                        preMenuStage--;
                        break;
                    case 5:
                        setUpGUI(4, "Ūgis");
                        preMenuStage--;
                        break;
                    case 6:
                        setUpGUI(5, "Svoris");
                        preMenuStage--;
                        break;
                    default:
                        break;
                }
            }
        });



    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> onSignInResult(result)
    );
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            //user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userUid = user.getUid();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild("Users/"+userUid)) {
                            UserDb userDb = new UserDb(user.getEmail(), user.getDisplayName(), (long) weight, height, activityLevel, age, gender, (long) bmr, goal, targetKcal);
                            mDatabase.child("Users").child(userUid).setValue(userDb);
                            Log.v("Prisijungimas", userUid);
                        }
                        else {
                            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("Firebase klaida", databaseError.getMessage());
                    }
                });
            }

        } else {
            if (response == null) {
                signIn();
            }
            else {
                Log.e("Prisijungimo klaida", response.getError().getMessage());
            }
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
    void signIn() {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                //new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                //.setIsSmartLockEnabled(false, true)
                .build();
        signInLauncher.launch(signInIntent);


    }
    void signOut() {
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    AuthUI.getInstance()
                            .signOut(MainActivity.this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                }
                signIn();
            }
        });
    }
   private void addUserData() {
       user = FirebaseAuth.getInstance().getCurrentUser();
       if (user != null) {
           String userUid = user.getUid();
           mDatabase.child("Users").child(userUid).child("gender").setValue(gender);
           mDatabase.child("Users").child(userUid).child("age").setValue(age);
           mDatabase.child("Users").child(userUid).child("goal").setValue(goal);
           mDatabase.child("Users").child(userUid).child("height").setValue(height);
           mDatabase.child("Users").child(userUid).child("activity_level").setValue(activityLevel);
           mDatabase.child("Users").child(userUid).child("weight").setValue(weight);
           mDatabase.child("Users").child(userUid).child("bmr").setValue(bmr);
           mDatabase.child("Users").child(userUid).child("target").setValue(targetKcal);


       }
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

    private int calcTargetProtein(int goal,  int targetKcal) {
        int proteins;
        if (goal == 0) {
            proteins = targetKcal / 100 * 20;
        } else if (goal == 1){
            proteins = targetKcal / 100 * 30;
        } else {
            proteins = targetKcal/ 100 * 20;
        }

        return proteins;
    }

    private int calcTargetCarbs(int goal,  int targetKcal) {
        int carbs;
        if (goal == 0) {
            carbs = targetKcal / 100 * 55;
        } else if (goal == 1){
            carbs = targetKcal / 100 * 45;
        } else {
            carbs = targetKcal/ 100 * 55;
        }

        return carbs;
    }

    private int calcTargetFat(int goal,  int targetKcal) {
        int fat;
        if (goal == 0) {
            fat = targetKcal / 100 * 25;
        } else if (goal == 1){
            fat = targetKcal / 100 * 25;
        } else {
            fat = targetKcal/ 100 * 25;
        }

        return fat;
    }
}