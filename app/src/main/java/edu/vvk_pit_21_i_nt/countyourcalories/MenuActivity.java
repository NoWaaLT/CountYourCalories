package edu.vvk_pit_21_i_nt.countyourcalories;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import edu.vvk_pit_21_i_nt.countyourcalories.databinding.ActivityMenuBinding;


public class MenuActivity extends AppCompatActivity {

    ActivityMenuBinding binding;
    public DatabaseReference mDatabase;
   HashMap<String, UserHistory> userHistoryHashMap;
    public UserDb userDb;
    public FirebaseUser user;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new MyDiaryFragment());
        user = FirebaseAuth.getInstance().getCurrentUser();
        //firebaseDb = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+ user.getUid());

        userHistoryHashMap = new HashMap<>();
        getUserData();
        //UserHistory uh = new UserHistory(1890, 213, 256, 452, 2300);
        //addUserHistory("2024-04-22", uh);
        binding.bottomNavigationView.setOnItemSelectedListener(Item -> {

            int itemId = Item.getItemId();
            if (itemId == R.id.diary) {
                ;
                replaceFragment(new MyDiaryFragment());
            } else if (itemId == R.id.meal_plans) {
                ;
                replaceFragment(new MealPlansFragment());
            } else if (itemId == R.id.add_food) {
                ;
                replaceFragment(new AddFoodFragment());
            } else if (itemId == R.id.recipes) {
                ;
                replaceFragment(new RecipesFragment());
            } else if (itemId == R.id.profile) {
                ;
                replaceFragment(new ProfileFragment());
            }

            return true;
        });


//
//         EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_menu);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        Toast.makeText(MenuActivity.this,  "Įšsaugota", Toast.LENGTH_LONG).show();
    }


    public  void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }
    protected void getUserData() {
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDb = dataSnapshot.getValue(UserDb.class);
                if (userDb != null) {
                    Log.d("Naudotojo duomenys", userDb.getDisplayName());
                }
                for (DataSnapshot userSnapshot: dataSnapshot.child("History").getChildren()) {
                    String key = userSnapshot.getKey();
                    if(key != null) {
                        userHistoryHashMap.put(key, userSnapshot.getValue(UserHistory.class));
                        //Log.d("Istorijos raktas", key);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Firebase klaida", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(userListener);
    }
    protected void updateUserData(String key, String val) {
        if (Objects.equals(key, "gender")) {
            userDb.setGender(val);
            mDatabase.child(key).setValue(val);
        }
    }
    protected void updateUserData(String key, float val) {
        if (Objects.equals(key, "weight")) {
            userDb.setWeight(val);
        }
        else if (Objects.equals(key, "activityLevel")) {
            userDb.setActivityLevel(val);;
        }
        else if (Objects.equals(key, "bmr")) {
            userDb.setBmr(val);
        }
        mDatabase.child(key).setValue(val);
    }
    protected void updateUserData(String key, int val) {
        if (Objects.equals(key, "height")) {
            userDb.setHeight(val);
        }
        else if (Objects.equals(key, "age")) {
            userDb.setAge(val);
        }
        else if (Objects.equals(key, "goal")) {
            userDb.setGoal(val);
        }
        else if (Objects.equals(key, "target")) {
            userDb.setTarget(val);
        }
        mDatabase.child(key).setValue(val);
    }
    protected void addUserHistory() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        String currentDate = sdf.format(new Date());
        UserHistory uh = new UserHistory();
        mDatabase.child("History").child(currentDate).setValue(uh);
    }

    protected void updateUserHistory(String date, String key, int val) {
        mDatabase.child("History").child(date).child(key).setValue(val);
    }
    protected void addUserHistory(String date, UserHistory uh) {
        mDatabase.child("History").child(date).setValue(uh);
    }

    private int calcTargetProtein(int goal, int targetKcal) {
        int proteins;
        if (goal == 0) {
            proteins = targetKcal / 100 * 20;
        } else if (goal == 1) {
            proteins = targetKcal / 100 * 30;
        } else {
            proteins = targetKcal / 100 * 20;
        }

        return proteins;
    }

    private int calcTargetCarbs(int goal, int targetKcal) {
        int carbs;
        if (goal == 0) {
            carbs = targetKcal / 100 * 55;
        } else if (goal == 1) {
            carbs = targetKcal / 100 * 45;
        } else {
            carbs = targetKcal / 100 * 55;
        }

        return carbs;
    }

    private int calcTargetFat(int goal, int targetKcal) {
        int fat;
        if (goal == 0) {
            fat = targetKcal / 100 * 25;
        } else if (goal == 1) {
            fat = targetKcal / 100 * 25;
        } else {
            fat = targetKcal / 100 * 25;
        }

        return fat;
    }
}