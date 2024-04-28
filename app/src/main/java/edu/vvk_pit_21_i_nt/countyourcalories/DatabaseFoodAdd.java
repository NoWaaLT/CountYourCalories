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
        DecimalFormat dff = new DecimalFormat("#");

        TextView tOne = (TextView) view.findViewById(R.id.editTextNumber5);
        Slider sliderOne = (Slider) view.findViewById(R.id.vienas);
        tOne.setText(Float.toString(sliderOne.getValue())+"g");

        sliderOne.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                float abb = sliderOne.getValue();
                Double abc = (double) abb;
                abc = Double.parseDouble(df.format(abc));
                tOne.setText(Double.toString(abc)+"g");
            }
        });

        TextView tTwo = (TextView) view.findViewById(R.id.editTextNumber);
        Slider sliderTwo = (Slider) view.findViewById(R.id.du);
        tTwo.setText(Float.toString(sliderTwo.getValue())+"g");

        sliderTwo.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                float abb = sliderTwo.getValue();
                Double abc = (double) abb;
                abc = Double.parseDouble(df.format(abc));
                tTwo.setText(Double.toString(abc)+"g");
            }
        });

        TextView tThree = (TextView) view.findViewById(R.id.editTextNumber2);
        Slider sliderThree = (Slider) view.findViewById(R.id.trys);
        tThree.setText(Float.toString(sliderThree.getValue())+"kcal");

        sliderThree.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                float abb = sliderThree.getValue();
                Float abc = (float) abb;
                abc = Float.parseFloat(dff.format(abc));
                tThree.setText(Float.toString(abc)+"kcal");
            }
        });

        TextView tFour = (TextView) view.findViewById(R.id.editTextNumber3);
        Slider sliderFour = (Slider) view.findViewById(R.id.keturi);
        tFour.setText(Float.toString(sliderFour.getValue())+"g");

        sliderFour.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                float abb = sliderFour.getValue();
                Double abc = (double) abb;
                abc = Double.parseDouble(df.format(abc));
                tFour.setText(Double.toString(abc)+"g");
            }
        });


        TextView tFive = (TextView) view.findViewById(R.id.editTextNumber4);
        Slider sliderFive = (Slider) view.findViewById(R.id.penki);
        tFive.setText(Float.toString(sliderFive.getValue())+"g");

        sliderFive.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                float abb = sliderFive.getValue();
                Double abc = (double) abb;
                abc = Double.parseDouble(df.format(abc));
                tFive.setText(Double.toString(abc)+"g");
            }
        });

        return view;
    }
}