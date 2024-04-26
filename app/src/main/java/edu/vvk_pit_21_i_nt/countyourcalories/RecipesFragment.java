package edu.vvk_pit_21_i_nt.countyourcalories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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

    AutoCompleteTextView autoCompleteTextView;
    Button recipes_add_item_button;
    Button recipes_create_recipe_button;
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
        autoCompleteTextView = view.findViewById(R.id.auto_compelete_txt);
        recipes_add_item_button = view.findViewById(R.id.recipes_add_item_button);
        recipes_create_recipe_button = view.findViewById(R.id.recipes_create_recipe_button);

        // Stores the product objects in ArrayList
        final ArrayList<product> productList = new ArrayList<>();

        // Adapter iterates` through the ArrayList and sets the product name to the TextView
        // requireContext() returns the context of the fragment
        // r.layout.recipes_fragment_list_item is the layout of the list item
        // productList is the ArrayList of products
        final ArrayAdapter<product> adapter = new ArrayAdapter<product>(requireContext(), R.layout.recipes_fragment_list_item, productList) {
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
        autoCompleteTextView.setAdapter(adapter);

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

        final ArrayList<product> selectedProductList = new ArrayList<>();
        recipes_add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = autoCompleteTextView.getText().toString();
                for (product product : productList) {
                    if (product.getProduktas().equals(selectedItem)) {
                        selectedProductList.add(product);
                        break;
                    }
                }
            }
        });

        final ListView selectedItemsListView = view.findViewById(R.id.selected_items_list_view);
        final ArrayAdapter<product> selectedItemsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, selectedProductList);
        selectedItemsListView.setAdapter(selectedItemsAdapter);

        recipes_add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProductList.size() < 3) {
                    String selectedItem = autoCompleteTextView.getText().toString();
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

        return view;
    }




}