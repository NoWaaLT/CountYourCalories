package edu.vvk_pit_21_i_nt.countyourcalories;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
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
    TextView progressText;
    EditText inputText;
    Button signoutButton;
    ProgressBar progressBar;
    FirebaseUser user;
    private DatabaseReference mDatabase;
    String gender, stepProgress;
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
            int userData = checkUserData();
            if (userData == 1) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        }

        upperText = findViewById(R.id.textView2);
        spinner = findViewById(R.id.gender_spinner);
        buttonNext = findViewById(R.id.button_next);
        buttonBack = findViewById(R.id.button_back);
        inputText = findViewById(R.id.inputText);
        signoutButton = findViewById(R.id.signout_button);
        progressText = findViewById(R.id.textViewSteps);
        progressBar = findViewById(R.id.entryProgressBar);

        signOut();
        setUpGUI(0, getResources().getStringArray(R.array.gender_list));

        buttonNext.setOnClickListener(v -> {
            String inputString;
            switch (preMenuStage) {
                case 0:
                    gender = spinner.getSelectedItem().toString();
                    preMenuStage++;
                    setUpGUI(1, getResources().getStringArray(R.array.main_goal));
                    buttonBack.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    String goalString = spinner.getSelectedItem().toString();
                    switch (goalString) {
                        case "Gain weight":
                            goal = 0;
                            difference = 300;
                            break;
                        case "Lose weight":
                            goal = 1;
                            difference = -300;
                            break;
                        case "Maintain weight":
                            goal = 2;
                            difference = 0;
                            break;
                        default:
                            break;
                    }
                    preMenuStage++;
                    setUpGUI(2, getResources().getStringArray(R.array.activity_desc));
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
                    setUpGUI(3, getResources().getString(R.string.age));
                    spinner.setVisibility(View.INVISIBLE);
                    inputText.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    inputString = inputText.getText().toString();
                    if(!inputString.isEmpty()) {
                        age = Integer.parseInt(inputString);
                        preMenuStage++;
                        setUpGUI(4, getResources().getString(R.string.weight));
                    }
                    else {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.emptyField), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 4:
                    inputString = inputText.getText().toString();
                    if(!inputString.isEmpty()) {
                        weight = Float.parseFloat(inputText.getText().toString());
                        preMenuStage++;
                        setUpGUI(5, getResources().getString(R.string.height));
                    }
                    else {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.emptyField), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 5:
                    inputString = inputText.getText().toString();
                    if(!inputString.isEmpty()) {
                        height = Integer.parseInt(inputText.getText().toString());
                        bmr = calcBmr(weight, height, age, gender);
                        targetKcal = calcTargetKcal(bmr, activityLevel, difference);

                        addUserData();


                        // Moves to the next activity

                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.emptyField), Toast.LENGTH_SHORT).show();
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
                        preMenuStage--;
                        setUpGUI(0, getResources().getStringArray(R.array.gender_list));
                        buttonBack.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        preMenuStage--;
                        setUpGUI(1, getResources().getStringArray(R.array.main_goal));
                        break;
                    case 3:
                        preMenuStage--;
                        setUpGUI(2, getResources().getStringArray(R.array.activity_desc));
                        spinner.setVisibility(View.VISIBLE);
                        inputText.setVisibility(View.INVISIBLE);
                        break;
                    case 4:
                        preMenuStage--;
                        setUpGUI(3, getResources().getString(R.string.age));
                        break;
                    case 5:
                        preMenuStage--;
                        setUpGUI(4, getResources().getString(R.string.height));
                        break;
                    case 6:
                        preMenuStage--;
                        setUpGUI(5, getResources().getString(R.string.weight));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userUid = user.getUid();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild("Users/"+userUid)) {
                            mDatabase.child("Users").child(userUid).child("Email").setValue(user.getEmail());
                            //Log.v("Prisijungimas", userUid);

                        }
                        else {
                            if (dataSnapshot.hasChild("Users/"+userUid+"/target")) {
                                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
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
                Log.e("Prisijungimo klaida", Objects.requireNonNull(response.getError().getMessage()));
            }
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
    void signIn() {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false, true)
                .build();
        signInLauncher.launch(signInIntent);

    }
    void signOut() {
        signoutButton.setOnClickListener(v -> {
            if (user != null) {
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(task -> {
                        });
            }
            signIn();
        });
    }
   private void addUserData() {
       if (user != null) {
           String userUid = user.getUid();
           UserDb userDb = new UserDb(user.getEmail(), user.getDisplayName(), weight, height, activityLevel, age, gender, bmr, goal, targetKcal,difference);
           mDatabase.child("Users").child(userUid).setValue(userDb);
           SharedPreferences shaPre = getSharedPreferences("UserInfo", MODE_PRIVATE);
           SharedPreferences.Editor editor = shaPre.edit();
           editor.putInt(userUid, 1);
           editor.apply();
       }
   }

   private int checkUserData() {
        if (user != null) {
            SharedPreferences shaPre = getSharedPreferences("UserInfo", MODE_PRIVATE);
            int userInput = shaPre.getInt(user.getUid(), -1);
            Log.d(user.getUid(), String.valueOf(userInput));
            return  userInput;
        }
        else {
            return 0;
        }

  }


    private void setUpGUI(int questionNum, String[] valuesArr) {
        upperText.setText(getResources().getStringArray(R.array.questions)[questionNum]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_dropdown_item, valuesArr);
        adapter.setDropDownViewResource(R.layout.custom_dropdown_item);
        spinner.setAdapter(adapter);
        progressStepsPrint();
        progressBar.setProgress((int) (100 * ((float) (questionNum + 1) / 6)));
    }

    private void setUpGUI(int questionNum, String name) {
        upperText.setText(getResources().getStringArray(R.array.questions)[questionNum]);
        inputText.setText("");
        inputText.setHint(name);
        progressStepsPrint();
        progressBar.setProgress((int) (100 * ((float) (questionNum + 1) / 6)));
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

    private void progressStepsPrint() {
        stepProgress = (preMenuStage + 1) + getResources().getString(R.string.totalSteps);
        progressText.setText(stepProgress);
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
        } else if (goal == 1) {
            fat = targetKcal / 100 * 25;
        } else {
            fat = targetKcal/ 100 * 25;
        }

        return fat;
    }
}