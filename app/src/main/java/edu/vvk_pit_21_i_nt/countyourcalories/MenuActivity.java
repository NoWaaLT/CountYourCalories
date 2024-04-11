package edu.vvk_pit_21_i_nt.countyourcalories;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.vvk_pit_21_i_nt.countyourcalories.databinding.ActivityMainBinding;
import edu.vvk_pit_21_i_nt.countyourcalories.databinding.ActivityMenuBinding;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MenuActivity extends AppCompatActivity {

    ActivityMenuBinding binding;
    public DatabaseReference mDatabase;
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
        getUserData();
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


    private  void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }
    void getUserData() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+ user.getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                userDb = dataSnapshot.getValue(UserDb.class);
                assert userDb != null;
                Log.d("Naudotojo duomenys", userDb.displayName);
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Firebase klaida", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(postListener);
    }
}