package edu.vvk_pit_21_i_nt.countyourcalories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatabaseFoodAdd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatabaseFoodAdd extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView databaseFoodAddFragmentBtnAdd;

    public DatabaseFoodAdd() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatabaseFoodAdd.
     */
    // TODO: Rename and change types and number of parameters
    public static DatabaseFoodAdd newInstance(String param1, String param2) {
        DatabaseFoodAdd fragment = new DatabaseFoodAdd();
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
        View view = inflater.inflate(R.layout.fragment_database_food_add, container, false);

        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormat dff = new DecimalFormat("#");

        TextView tOne = (TextView) view.findViewById(R.id.editTextNumber5);
        Slider sliderOne = (Slider) view.findViewById(R.id.vienas);
        tOne.setText(Float.toString(sliderOne.getValue()));

        // Add new food to database button
        databaseFoodAddFragmentBtnAdd = view.findViewById(R.id.textView75);

        // Singing the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        databaseFoodAddFragmentBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) view.findViewById(R.id.editTextText2)).getText().toString();
                float carbs = ((Slider) view.findViewById(R.id.keturi)).getValue();
                float fats = ((Slider) view.findViewById(R.id.vienas)).getValue();
                float proteins = ((Slider) view.findViewById(R.id.penki)).getValue();
                float kcal = ((Slider) view.findViewById(R.id.trys)).getValue();

                // Check if all fields are set
                if (name.isEmpty() || carbs == 0 || fats == 0 || proteins == 0 || kcal == 0) {
                    // Show an error message
                    Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new Product object
                ProductForDbInsertion product = new ProductForDbInsertion();
                product.setProduktas(name);
                product.setAngliavandeniai(carbs);
                product.setRiebalai(fats);
                product.setBaltymai(proteins);
                product.setKilokalorijos(kcal);

                // Get a reference to the products in the database
                DatabaseReference productsRef = database.getReference("Products/Sheet1");

                // Get a reference to the ProductsCount in the database
                DatabaseReference productsCountRef = database.getReference("Products/ProductsCount");

                // Retrieve the last used index
                productsCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long index;
                        if (dataSnapshot.exists()) {
                            index = (long) dataSnapshot.getValue();
                        } else {
                            index = 0; // Default value if "ProductsCount" node does not exist
                        }

                        // Check if there is a product with the same name in the database
                        productsRef.orderByChild("Produktas").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // Show an error message
                                    Toast.makeText(getActivity(), "A product with this name already exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Save the product to the database
                                    productsRef.child(String.valueOf(index + 1)).setValue(product);

                                    // Update the ProductsCount in the database
                                    productsCountRef.setValue(index + 1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle possible errors.
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors.
                    }
                });
            }
        });
        sliderOne.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                float abb = sliderOne.getValue();
                Double abc = (double) abb;
                abc = Double.parseDouble(df.format(abc));
                tOne.setText(Double.toString(abc));
            }
        });


        TextView tThree = (TextView) view.findViewById(R.id.editTextNumber2);
        Slider sliderThree = (Slider) view.findViewById(R.id.trys);
        tThree.setText(Float.toString(sliderThree.getValue()));

        sliderThree.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                float abb = sliderThree.getValue();
                Float abc = (float) abb;
                abc = Float.parseFloat(dff.format(abc));
                tThree.setText(Float.toString(abc));
            }
        });

        TextView tFour = (TextView) view.findViewById(R.id.editTextNumber3);
        Slider sliderFour = (Slider) view.findViewById(R.id.keturi);
        tFour.setText(Float.toString(sliderFour.getValue()));

        sliderFour.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                float abb = sliderFour.getValue();
                Double abc = (double) abb;
                abc = Double.parseDouble(df.format(abc));
                tFour.setText(Double.toString(abc));
            }
        });


        TextView tFive = (TextView) view.findViewById(R.id.editTextNumber4);
        Slider sliderFive = (Slider) view.findViewById(R.id.penki);
        tFive.setText(Float.toString(sliderFive.getValue()));

        sliderFive.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                float abb = sliderFive.getValue();
                Double abc = (double) abb;
                abc = Double.parseDouble(df.format(abc));
                tFive.setText(Double.toString(abc));
            }
        });

        return view;
    }
}