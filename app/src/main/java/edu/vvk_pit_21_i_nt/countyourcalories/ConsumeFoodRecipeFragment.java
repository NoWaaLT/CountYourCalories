package edu.vvk_pit_21_i_nt.countyourcalories;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    private double totalProteins = 0;
    private double totalFats = 0;
    private double totalCarbs = 0;
    private double totalKcal = 0;
    private ArrayList<product> products;

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

        tvProductTitle1 = view.findViewById(R.id.ConsumeFoodRecipeFragment_tv_firstProduct);
        etProductWeight1 = view.findViewById(R.id.ConsumeFoodRecipeFragment_ed_firstProduct);
        tvProductTitle2 = view.findViewById(R.id.ConsumeFoodRecipeFragment_tv_secondProduct);
        etProductWeight2 = view.findViewById(R.id.ConsumeFoodRecipeFragment_ed_secondProduct);
        tvProductTitle3 = view.findViewById(R.id.ConsumeFoodRecipeFragment_tv_thirdProduct);
        etProductWeight3 = view.findViewById(R.id.ConsumeFoodRecipeFragment_ed_thirdProduct);

        btnConsumeRecipe = view.findViewById(R.id.ConsumeFoodRecipeFragment_btn_consume);
        btnConsumeRecipe.setEnabled(false);

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

        atcvAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected recipe
                Recipe selectedRecipe = recipesList.get(position);

                // Get the products from the selected recipe
                products = selectedRecipe.getRecipeProductsList();

                // Set the TextViews and EditTexts to invisible initially
                tvProductTitle1.setVisibility(View.INVISIBLE);
                etProductWeight1.setVisibility(View.INVISIBLE);
                tvProductTitle2.setVisibility(View.INVISIBLE);
                etProductWeight2.setVisibility(View.INVISIBLE);
                tvProductTitle3.setVisibility(View.INVISIBLE);
                etProductWeight3.setVisibility(View.INVISIBLE);

                // Set the TextViews and EditTexts based on the number of products in the recipe
                if (!products.isEmpty()) {
                    tvProductTitle1.setText(products.get(0).getProduktas());
                    tvProductTitle1.setVisibility(View.VISIBLE);
                    etProductWeight1.setVisibility(View.VISIBLE);
                }
                if (products.size() > 1) {
                    tvProductTitle2.setText(products.get(1).getProduktas());
                    tvProductTitle2.setVisibility(View.VISIBLE);
                    etProductWeight2.setVisibility(View.VISIBLE);
                }
                if (products.size() > 2) {
                    tvProductTitle3.setText(products.get(2).getProduktas());
                    tvProductTitle3.setVisibility(View.VISIBLE);
                    etProductWeight3.setVisibility(View.VISIBLE);
                }

                // Enable btnConsumeRecipe when a recipe is selected
                btnConsumeRecipe.setEnabled(true);
            }
        });

        // Get a reference to the UserHistory node in the Firebase database
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference("History").child(userId);

        btnConsumeRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate the total nutritional values from the consumed food
                calculateNutritionValues(products);

                // Get the current date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
                String currentDate = sdf.format(new Date());

                // Get a reference to the current date's node in the Firebase database
                DatabaseReference currentDateRef = historyRef.child(currentDate);

                // Retrieve the existing UserHistory object for the current date
                currentDateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserHistory existingHistory = dataSnapshot.getValue(UserHistory.class);
                        if (existingHistory == null) {
                            // If there is no existing entry for the current date, create a new UserHistory object
                            existingHistory = new UserHistory();
                            existingHistory.setCarbo((int) totalCarbs);
                            existingHistory.setFat((int) totalFats);
                            existingHistory.setKcal((int) totalKcal);
                            existingHistory.setProtein((int) totalProteins);
//                            existingHistory.setWater(0); // Set water to 0 or replace with actual consumed water value //
                        } else {
                            // If there is an existing entry for the current date, add the consumed values to the existing values
                            existingHistory.setCarbo(existingHistory.getCarbo() + (int) totalCarbs);
                            existingHistory.setFat(existingHistory.getFat() + (int) totalFats);
                            existingHistory.setKcal(existingHistory.getKcal() + (int) totalKcal);
                            existingHistory.setProtein(existingHistory.getProtein() + (int) totalProteins);
                        }

                        // Write the UserHistory object back to the Firebase database
                        currentDateRef.setValue(existingHistory);

                        // Reset the total nutritional values for the next calculation
                        resetNutritionValues();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Firebase", "Error fetching data", databaseError.toException());
                    }
                });
            }
        });

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

    private void calculateNutritionValues(ArrayList<product> products) {
        EditText[] productWeights = new EditText[]{etProductWeight1, etProductWeight2, etProductWeight3};
        for (int i = 0; i < productWeights.length; i++) {
            productWeights[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    for (EditText productWeight : productWeights) {
                        if (productWeight.getText().toString().isEmpty()) {
                            btnConsumeRecipe.setEnabled(false);
                            return;
                        }
                    }
                    btnConsumeRecipe.setEnabled(true);
                }
            });
        }
        for (int i = 0; i < products.size(); i++) {
            product product = products.get(i);
            double consumedAmount = productWeights[i].getText().toString().isEmpty() ? 0 : Double.parseDouble(productWeights[i].getText().toString());
            totalProteins += product.getBaltymai() * consumedAmount / 100;
            totalFats += product.getRiebalai() * consumedAmount / 100;
            totalCarbs += product.getAngliavandeniai() * consumedAmount / 100;
            totalKcal += product.getKilokalorijos() * consumedAmount / 100;
        }
    }

    private void resetNutritionValues() {
        totalProteins = 0;
        totalFats = 0;
        totalCarbs = 0;
        totalKcal = 0;
    }




}