package edu.vvk_pit_21_i_nt.countyourcalories;

import android.annotation.SuppressLint;
import android.content.SearchRecentSuggestionsProvider;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import edu.vvk_pit_21_i_nt.countyourcalories.databinding.ActivityMenuBinding;


public class MenuActivity extends AppCompatActivity {

    ActivityMenuBinding binding;
    public DatabaseReference mDatabase;

    public DatabaseReference historyRef;
   HashMap<String, UserHistory> userHistoryHashMap;
    public UserDb userDb;
    public FirebaseUser user;
    MenuViewModel menuModel;
    int fragmentId;
    private MyDiaryFragment myDiaryFragment;
    private MealPlansFragment mealPlansFragment;
    private RecipesFragment recipesFragment;
    public static ProfileFragment profileFragment;
    private DatabaseFoodAdd databaseFoodAdd;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = FirebaseAuth.getInstance().getCurrentUser();
        menuModel = new ViewModelProvider(this).get(MenuViewModel.class);
        userDb = menuModel.getUserDb();
        historyRef = FirebaseDatabase.getInstance().getReference("History/" + user.getUid());
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+ user.getUid());
        //userHistoryHashMap = menuModel.getUserHistoryHashMap();
        createRandomHistory();
        userHistoryHashMap = new HashMap<>();
        getUserData();
        getUserHistory();
        //Random rand = new Random();
        //for(int i = 0; i < 7; i++) {
            //int setProteins= rand.nextInt(100) + 600;
            //int setCarbs = rand.nextInt(100) + 600;
            //int setFat= rand.nextInt(100) + 600;
            //int setCalories = rand.nextInt(1000) + 2000;
            //int setWater = rand.nextInt(1000) + 3000;
            //UserHistory uh = new UserHistory(setCalories, setProteins, setCarbs, setFat, setWater);
            //String day = "2024-05-0" + (i + 1);
            //addUserHistory(day, uh);
        //}

        FragmentManager managerOG = getSupportFragmentManager();
        myDiaryFragment = new MyDiaryFragment();
        mealPlansFragment = new MealPlansFragment();
        recipesFragment = new RecipesFragment();
//        profileFragment = new ProfileFragment();
        databaseFoodAdd = new DatabaseFoodAdd();
        fragmentId = myDiaryFragment.getId();
        // Show MyDiaryFragment by default
        showFragment(myDiaryFragment,managerOG);

        // Bottom navigation view listener
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;
            if (item.getItemId() == R.id.diary) {
                selectedFragment = myDiaryFragment;

            } else if (item.getItemId() == R.id.meal_plans) {
                selectedFragment = mealPlansFragment;

            } else if (item.getItemId() == R.id.add_food) {
                selectedFragment = databaseFoodAdd;

            } else if (item.getItemId() == R.id.recipes) {
                selectedFragment = recipesFragment;

            } else if (item.getItemId() == R.id.profile) {
//                if(ProfileFragment.isEditing==false){
//                    profileFragment = new ProfileFragment();
//                }
                profileFragment = new ProfileFragment();
                selectedFragment = profileFragment;

            }  else if (item.getItemId() == R.id.textView63){
                selectedFragment = databaseFoodAdd;
            }
            else {
                return false;
            }

            showFragment(selectedFragment,managerOG);
            return true;
        });

    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        if (userDb != null) {
            menuModel.setUserDb(userDb);
        }
        menuModel.setUserHistoryHashMap(userHistoryHashMap);
        super.onDestroy();
    }

    public void selectMenuItem(int itemId) {
        binding.bottomNavigationView.setSelectedItemId(itemId);
    }


    public static void showFragment(Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        List<Fragment> allFragments = fragmentManager.getFragments(); // Get all fragments
        for (Fragment frag : allFragments) {
            if (frag != null && frag.isVisible()) {
                transaction.hide(frag); // Hide all other fragments
            }
        }
// Now, show the intended fragment or add it if not already added
        if (!fragment.isAdded()) {
            transaction.add(R.id.frame_layout, fragment);
        } else {
            transaction.show(fragment);
        }
        transaction.commit();
    }

    public void replaceFragmentWithBackStack(Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null); // Adds transaction to the back stack
        transaction.commit();
    }

    public void clearBackStack(FragmentManager fragmentManager) {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
    public void replaceFragment(Fragment fragment,FragmentManager OG) {

        // Hide the current fragment if there's any
        FragmentTransaction transactionLOC = OG.beginTransaction();
        Fragment currentFragment = OG.findFragmentById(R.id.frame_layout);
        transactionLOC.hide(currentFragment);
        transactionLOC.show(fragment);

        // Add the new fragment if it's not added already
        if (!fragment.isAdded()) {
            transactionLOC.add(R.id.frame_layout, fragment);
        }
        transactionLOC.commit();
    }

    public  void replaceFragmentStatic(Fragment fragment,FragmentManager OG){
        FragmentTransaction transactionLOC = OG.beginTransaction();
        transactionLOC.replace(R.id.frame_layout,fragment);
        transactionLOC.commit();

    }

    protected void getUserData() {
       mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               userDb = snapshot.getValue(UserDb.class);
               if (userDb != null) {
                   Log.d("Naudotojo duomenys", userDb.getDisplayName());
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Log.w("Firebase klaida", "loadPost:onCancelled", error.toException());
           }
       });
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
            userDb.setActivityLevel(val);
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
    protected void getUserHistory() {
        if (userHistoryHashMap == null) {
            userHistoryHashMap = new HashMap<>();
        }
        Query userHistoryQuery = historyRef.limitToLast(7);
        userHistoryQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot daySnapshot : snapshot.getChildren()) {
                    String key = daySnapshot.getKey();
                    if(key != null) {
                        userHistoryHashMap.put(key, daySnapshot.getValue(UserHistory.class));
                    }
                }
                Log.d("Naudotojo istorija", "gauta");

                myDiaryFragment.showHistory(myDiaryFragment.Diena7);
                myDiaryFragment.setArrows();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase klaida", error.getMessage());
            }
        });
    }
    protected void addUserHistory() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        String currentDate = sdf.format(new Date());
        UserHistory uh = new UserHistory();
        historyRef.child(currentDate).setValue(uh);
        historyRef.child(currentDate).getKey();
    }
    protected void addUserHistory(UserHistory uh) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        String currentDate = sdf.format(new Date());
        historyRef.child(currentDate).setValue(uh);
    }

    protected void updateUserHistory(String date, String key, int val) {
        historyRef.child(date).child(key).setValue(val);
    }
    protected void addUserHistory(String date, UserHistory uh) {
        historyRef.child(date).setValue(uh);
    }
    protected void createRandomHistory() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ", Locale.UK);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);

        Random rand = new Random();
        for(int i = 0; i < 7; i++) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            int setProteins= rand.nextInt(100) + 300;
            int setCarbs = rand.nextInt(100) + 400;
            int setFat= rand.nextInt(100) + 200;
            int setCalories = rand.nextInt(1000) + 1500;
            int setWater = rand.nextInt(1000) + 1000;
            UserHistory uh = new UserHistory(setCalories, setProteins, setCarbs, setFat, setWater);
            String day = sdf.format(cal.getTime());
            addUserHistory(day.trim(), uh);
        }
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