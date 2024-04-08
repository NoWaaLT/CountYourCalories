package edu.vvk_pit_21_i_nt.countyourcalories;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
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



public class MenuActivity extends AppCompatActivity {

    ActivityMenuBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new MyDiaryFragment());
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
}