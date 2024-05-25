package edu.vvk_pit_21_i_nt.countyourcalories;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AutoCompleteTextView atcvSelectItem;
    Button btnAddItem;
    Button btnCreateRecipe;
    Button btnClear;
    Button btnConsume;
    TextInputEditText tietRecipeName;
    ListView selectedItemsListView;
    ArrayList<product> productList;
    ArrayAdapter<product> selectedItemsAdapter;
    ArrayList<product> selectedProductList;
    ArrayAdapter<product> adapter;
    FirebaseAuth auth;
    FirebaseUser user;
    public RecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipesFragment newInstance(String param1, String param2) {
        RecipesFragment fragment = new RecipesFragment();
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recipes_fragment, container, false);

        atcvSelectItem = view.findViewById(R.id.RecipesFragment_actv_autoCompleteTextView);
        btnAddItem = view.findViewById(R.id.RecipesFragment_btn_addItem);
        btnCreateRecipe = view.findViewById(R.id.RecipesFragment_btn_createRecipe);
        btnClear = view.findViewById(R.id.RecipesFragment_btn_clear);
        btnConsume = view.findViewById(R.id.RecipesFragment_btn_consume);
        tietRecipeName = view.findViewById(R.id.RecipesFragment_et_recipe_name);


        // Stores the product objects in ArrayList
        productList = new ArrayList<>();

        // Adapter iterates` through the ArrayList and sets the product name to the TextView
        // requireContext() returns the context of the fragment
        // r.layout.recipes_fragment_list_item is the layout of the list item
        // productList is the ArrayList of products
        adapter = new ArrayAdapter<product>(requireContext(), R.layout.recipes_fragment_list_item, productList) {
            @NonNull
            @Override
            // getView method is responsible for creating a view for each item in the list.
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                product product = getItem(position);
                if (product != null) {
                    TextView textView = view.findViewById(R.id.recipe_title);
                    textView.setText(product.getProduktas());
                }
                return view;
            }
        };

        atcvSelectItem.setAdapter(adapter);

        // Reference to the Firebase database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products").child("Sheet1");
        // Adds a listener to the database reference
        reference.addChildEventListener(new ChildEventListener() {
           // onChildAd
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                product product = dataSnapshot.getValue(product.class);
                if (product != null) {
                    productList.add(product);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        selectedProductList = new ArrayList<>();
        selectedItemsListView = view.findViewById(R.id.RecipesFragment_lv_selectedItems);
        selectedItemsAdapter = new ArrayAdapter<>(requireContext(), R.layout.recipes_fragment_listview_item, selectedProductList);
        selectedItemsListView.setAdapter(selectedItemsAdapter);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProductList.size() < 3) {
                    String selectedItem = atcvSelectItem.getText().toString();
                    for (product product : productList) {
                        if (product.getProduktas().equals(selectedItem)) {
                            selectedProductList.add(product);
                            selectedItemsAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Maximum of 3 items can be added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCreateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Recipes");

                // Gets an instance of current user from a database
                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();

                // Validates if the user exists in a database
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChild(user.getUid())) {
                            String recipeName = tietRecipeName.getText().toString();
                            if(TextUtils.isEmpty(recipeName)) {
                                tietRecipeName.setError("Recipe name is required");
                            } else {
                                // Validates if the recipe with the same name already exists
                                DatabaseReference userRecipesRef = FirebaseDatabase.getInstance().getReference("Recipes").child(user.getUid());
                                userRecipesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        boolean recipeExists = false;
                                        for (DataSnapshot recipeSnapshot : snapshot.getChildren()) {
                                            if (recipeSnapshot.getKey().equals(recipeName)) {
                                                recipeExists = true;
                                                break;
                                            }
                                        }
                                        if (recipeExists) {
                                            Toast.makeText(requireContext(), "Recipe with this name already exists", Toast.LENGTH_SHORT).show();
                                        } else {
                                            createRecipe(selectedProductList, reference, user);
                                            clearFields();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        // Handle error
                                    }
                                });
                            }
                        }
                        // If the user record does not exist in the recipes database
                        else {
                            String recipeName = tietRecipeName.getText().toString();
                            if (TextUtils.isEmpty(recipeName)) {
                                tietRecipeName.setError("Recipe name is required");
                            } else {
                              createRecipe(selectedProductList, reference, user);
                              clearFields();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        btnConsume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsumeFoodRecipeFragment consumeFoodRecipeFragment = new ConsumeFoodRecipeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, consumeFoodRecipeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void clearFields() {
        tietRecipeName.setText("");
        selectedProductList.clear();
        adapter.notifyDataSetChanged();
        atcvSelectItem.clearFocus(); // Clear the focus from the AutoCompleteTextView
        selectedItemsAdapter.notifyDataSetChanged();
        // Close the soft keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(atcvSelectItem.getWindowToken(), 0);
        btnCreateRecipe.requestFocus();
    }



    private void createRecipe(ArrayList<product> selectedProductList, DatabaseReference reference, FirebaseUser user) {
        String recipeName = tietRecipeName.getText().toString();
        if (TextUtils.isEmpty(recipeName)) {
            tietRecipeName.setError("Recipe name is required");
        } else {
            ArrayList<HashMap<String, Object>> recipeProductsList = new ArrayList<>();

            for (product product : selectedProductList) {
                HashMap<String, Object> productMap = new HashMap<>();
                productMap.put("Angliavandeniai", product.getAngliavandeniai());
                productMap.put("Baltymai", product.getBaltymai());
                productMap.put("Kilokalorijos", product.getKilokalorijos());
                productMap.put("Produktas", product.getProduktas());
                productMap.put("Riebalai", product.getRiebalai());
                recipeProductsList.add(productMap);
            }
            reference.child(user.getUid()).child(recipeName).setValue(recipeProductsList);

            Log.d("TAG", "RecipeProducts: " + recipeProductsList);

        }
    }

}