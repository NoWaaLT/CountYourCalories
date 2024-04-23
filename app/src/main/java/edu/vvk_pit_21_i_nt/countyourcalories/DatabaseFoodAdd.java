package edu.vvk_pit_21_i_nt.countyourcalories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

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

        TextView laukasOne = (TextView) view.findViewById(R.id.textView68);
        Slider sliderOne = (Slider) view.findViewById(R.id.vienas);
        laukasOne.setText(Float.toString(sliderOne.getValue())+"g");

        sliderOne.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                float abb = sliderOne.getValue();
                Double abc = (double) abb;
                abc = Double.parseDouble(df.format(abc));
                laukasOne.setText(Double.toString(abc)+"g");
            }
        });


        return view;
    }
}