package edu.vvk_pit_21_i_nt.countyourcalories;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConsumeFoodRecipeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ArrayList<Recipe> recipesList;
    ArrayAdapter<Recipe> adapter;
    AutoCompleteTextView atcvAutoCompleteTextView;
    Button btnConsumeRecipe;
    TextView tvProductTitle1;
    TextView tvProductTitle2;
    TextView tvProductTitle3;
    EditText etProductWeight1;
    EditText etProductWeight2;
    EditText etProductWeight3;

    public ConsumeFoodRecipeFragment() {
        // Required empty public constructor
    }

    public static ConsumeFoodRecipeFragment newInstance(String param1, String param2) {
        ConsumeFoodRecipeFragment fragment = new ConsumeFoodRecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_consume_food_recipe, container, false);

        atcvAutoCompleteTextView = view.findViewById(R.id.ConsumeFoodRecipeFragment_actv_autoCompleteTextView);

        recipesList = new ArrayList<>();

        adapter = new ArrayAdapter<Recipe>(requireContext(), R.layout.recipes_fragment_list_item, recipesList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Recipe recipe = getItem(position);
                if (recipe != null) {
                    TextView textView = view.findViewById(R.id.recipe_title);
                    textView.setText(recipe.getRecipeName());
                }
                return view;
            }
        };

        atcvAutoCompleteTextView.setAdapter(adapter);

        // Fetch recipes from Firebase
        fetchRecipesFromFirebase();

        return view;
    }

    private void fetchRecipesFromFirebase() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("Recipes").child(userId);

        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipesList.clear();
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = new Recipe();
                    recipe.setRecipeName(recipeSnapshot.getKey());
                    ArrayList<product> products = new ArrayList<>();
                    for (DataSnapshot indexSnapshot : recipeSnapshot.getChildren()) {
                        product product = indexSnapshot.getValue(product.class);
                        if (product != null) {
                            products.add(product);
                        }
                    }
                    recipe.setRecipeProductsList(products);
                    recipesList.add(recipe);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error fetching data", databaseError.toException());
            }
        });
    }


}