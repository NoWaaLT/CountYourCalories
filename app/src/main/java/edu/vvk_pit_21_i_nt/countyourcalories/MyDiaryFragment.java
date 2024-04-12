package edu.vvk_pit_21_i_nt.countyourcalories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyDiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDiaryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyDiaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyDiaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyDiaryFragment newInstance(String param1, String param2) {
        MyDiaryFragment fragment = new MyDiaryFragment();
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
        return inflater.inflate(R.layout.fragment_my_diary, container, false);
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