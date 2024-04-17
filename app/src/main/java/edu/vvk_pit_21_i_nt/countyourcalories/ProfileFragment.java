package edu.vvk_pit_21_i_nt.countyourcalories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String myEmail;
    private String myDisplayName;
    private float myWeight;
    private int myHeight;
    private float myActivityLevel;
    private int myAge;
    private String myGender;
    private float myBmr;
    private int myGoal;
    private int myTarget;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        userDataRead();
//        userDataPut();


        //Naudotojo duomenu keitimas
        //((MenuActivity) requireActivity()).updateUserData("age", 56);
        //Duomenu paemimas is MenuActivity naudotojo objekto
        //String email = ((MenuActivity) requireActivity()).userDb.getEmail();
        //.d("Email", email);
        //Log.d("Age", String.valueOf(age));
    }

    private void userDataPut(String key, String value) {
        ((MenuActivity) requireActivity()).updateUserData(key, value);
    }

    private void userDataRead() {
        myEmail = ((MenuActivity) requireActivity()).userDb.getEmail();
        myDisplayName = ((MenuActivity) requireActivity()).userDb.getDisplayName();
        myWeight = (int) ((MenuActivity) requireActivity()).userDb.getWeight();
        myHeight = ((MenuActivity) requireActivity()).userDb.getHeight();
        myActivityLevel = ((MenuActivity) requireActivity()).userDb.getActivityLevel();
        myAge = ((MenuActivity) requireActivity()).userDb.getAge();
        myGender = ((MenuActivity) requireActivity()).userDb.getGender();
        myBmr = ((MenuActivity) requireActivity()).userDb.getBmr();
        myGoal = ((MenuActivity) requireActivity()).userDb.getGoal();
        myTarget = ((MenuActivity) requireActivity()).userDb.getTarget();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment


        TextView profile_Title = (TextView) view.findViewById(R.id.profile_title);
        TextView age = (TextView) view.findViewById(R.id.profile_age);
        TextView profile_Description = (TextView) view.findViewById(R.id.profile_description);


//        TextView email =(TextView) view.findViewById(R.id.email);
//        TextView displayName =(TextView) view.findViewById(R.id.displayName);
//        TextView weight =(TextView) view.findViewById(R.id.weight);
//        TextView height =(TextView) view.findViewById(R.id.height);
//        TextView activityLevel =(TextView) view.findViewById(R.id.activityLevel);
//        TextView gender = (TextView) view.findViewById(R.id.gender);
//        TextView bmr =(TextView) view.findViewById(R.id.bmr);
//        TextView goal =(TextView) view.findViewById(R.id.goal);
//        TextView target =(TextView) view.findViewById(R.id.target);
        profile_Title.setText(profile_Title());
        profile_Description.setText(profile_description());
        age.setText(myAge + " years old");

        ImageView genderIconMale = (ImageView) view.findViewById(R.id.genderIconMale);
        ImageView genderIconFemale = (ImageView) view.findViewById(R.id.genderIconFemale);


        if (Objects.equals(myGender, "A Man")) {
            genderIconMale.setVisibility(View.VISIBLE);
            genderIconFemale.setVisibility(View.INVISIBLE);
        } else {
            genderIconMale.setVisibility(View.INVISIBLE);
            genderIconFemale.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private String profile_description() {
        return "Hello, " + myDisplayName + "!\n" +
                "You are " + myAge + " years old " + myGender + ".\n" +
                "Your weight is " + myWeight + " kg "+ ".\n" +"and height is " + myHeight + " cm.\n" +
                "Your activity level is " + myActivityLevel + ".\n" +
                "Your BMR is " + myBmr + ".\n" +
                "Your goal is " + myGoal + " and target is " + myTarget + ".";
    }
    private String profile_Title() {
        return "My Profile";
    }
}
