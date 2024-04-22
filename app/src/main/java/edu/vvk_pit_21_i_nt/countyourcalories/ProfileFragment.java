package edu.vvk_pit_21_i_nt.countyourcalories;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
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
    EditText edit_profile_age;
    private boolean isEditing = false;
    private final String[] genders = {"A Man", "A Woman"};
    private final String[] genderName = {"Man", "Woman"};
    private final float[] myActivityLevels = {1.2f, 1.375f, 1.55f, 1.725f, 1.9f};
    private final String[] myActivityLevelDescription = {"Sedentary, 0-1 per week", "Lightly Active, 2-3 per week", "Moderately Active, 4-5 per week", "Very Active, 6-7 per week", "Super Active, 2 per day"};
    private final int[] myDifference = {300, -300, 0};
    private final String[] myGoalDescription = {"Gain weight", "Lose weight", "Maintain weight"};
    private String key;
    private String value;


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
        myWeight = (float) (Math.round(((MenuActivity) requireActivity()).userDb.getWeight() * 100.0) / 100.0);
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
        profile_Title.setText(profile_Title());
        ScrollView scroll_Profile = (ScrollView) view.findViewById(R.id.scroll_view_profile);
        scroll_Profile.setVisibility(View.VISIBLE);
        ScrollView scroll_Edit_Profile = (ScrollView) view.findViewById(R.id.scroll_view_profile_edit);
        scroll_Edit_Profile.setVisibility(View.INVISIBLE);
        EditText edit_profile_age = (EditText) view.findViewById(R.id.edit_profile_age);
        EditText edit_profile_height = (EditText) view.findViewById(R.id.edit_profile_height);
        EditText edit_profile_weight = (EditText) view.findViewById(R.id.edit_profile_weight);
        TextView profile_Description = (TextView) view.findViewById(R.id.profile_description);
        TextView profile_Desc_Title = (TextView) view.findViewById(R.id.profile_desc_title);
        TextView edit_age_text = (TextView) view.findViewById(R.id.edit_age_text);
        TextView edit_height_text = (TextView) view.findViewById(R.id.edit_height_text);
        TextView edit_weight_text = (TextView) view.findViewById(R.id.edit_weight_text);
        TextView edit_profile_gender = (TextView) view.findViewById(R.id.edit_profile_gender);
        TextView edit_gender_text = (TextView) view.findViewById(R.id.edit_gender_text);
        TextView edit_activity_level_text = (TextView) view.findViewById(R.id.edit_activity_level_text);
        TextView edit_profile_activity_level = (TextView) view.findViewById(R.id.edit_profile_activity_level);
        TextView edit_profile_goal = (TextView) view.findViewById(R.id.edit_profile_goal);
        TextView edit_goal_text = (TextView) view.findViewById(R.id.edit_goal_text);
        TextView edit_profile_target = (TextView) view.findViewById(R.id.edit_profile_target);
        TextView edit_target_text = (TextView) view.findViewById(R.id.edit_target_text);
        TextView edit_bmr_text = (TextView) view.findViewById(R.id.edit_bmr_text);
        TextView edit_profile_bmr = (TextView) view.findViewById(R.id.edit_profile_bmr);
        Button editProfile = (Button) view.findViewById(R.id.edit_profile);


        editProfile.setOnClickListener(v -> {
            if (isEditing) {
                isEditing = false;
                editProfile.setText("Edit");
                editProfile.setBackgroundTintList(getResources().getColorStateList(R.color.purple_200));
                scroll_Edit_Profile.setVisibility(View.INVISIBLE);
                scroll_Profile.setVisibility(View.VISIBLE);

            } else {
                isEditing = true;
                editProfile.setText("Back");
                editProfile.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
                scroll_Profile.setVisibility(View.INVISIBLE);
                scroll_Edit_Profile.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.INVISIBLE);
            }
        });

        profile_Description.setText(profile_description());
        profile_Desc_Title.setText(profile_desc_title());

        edit_age_text.setText("Edit Age");
        edit_profile_age.setText("" + myAge);

        edit_profile_age.setOnClickListener(v -> {
            edit_age_text.setText("Edit Age");
            String content = edit_profile_age.getText().toString(); //gets you the contents of edit text
            edit_profile_age.setText(content);
            if (Integer.parseInt(content) > 0 && Integer.parseInt(content) < 120) {
                ((MenuActivity) requireActivity()).updateUserData("age", Integer.parseInt(content));
                userDataRead();
            }
            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(edit_profile_age.getWindowToken(), 0);
        });

        edit_height_text.setText("Edit Height (cm)");
        edit_profile_height.setText("" + myHeight);

        edit_profile_height.setOnClickListener(v -> {
            edit_height_text.setText("Edit Height (cm)");
            String content = edit_profile_height.getText().toString(); //gets you the contents of edit text
            edit_profile_height.setText(content);
            if (Integer.parseInt(content) > 0 && Integer.parseInt(content) < 300) {
                ((MenuActivity) requireActivity()).updateUserData("height", Integer.parseInt(content));
                userDataRead();
            }
            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(edit_profile_height.getWindowToken(), 0);
        });

        edit_weight_text.setText("Edit Weight (kg)");
        edit_profile_weight.setText("" + myWeight);

        edit_profile_weight.setOnClickListener(v -> {
            edit_weight_text.setText("Edit Weight (kg)");
            String content = edit_profile_weight.getText().toString(); //gets you the contents of edit text
            edit_profile_weight.setText(content);
            if (Float.parseFloat(content) > 0 && Float.parseFloat(content) < 300) {
                content = String.valueOf((float) (Math.round(Float.parseFloat(content) * 100.0) / 100.0));
                edit_profile_weight.setText(content);
                ((MenuActivity) requireActivity()).updateUserData("weight", Float.parseFloat(content));
                userDataRead();
            }
            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(edit_profile_weight.getWindowToken(), 0);
        });

        edit_gender_text.setText("Edit Gender");
        if (Objects.equals(myGender, genders[0])) {
            edit_profile_gender.setText(genderName[0]);
        } else {
            edit_profile_gender.setText(genderName[1]);
        }
        ImageView genderIconMale = (ImageView) view.findViewById(R.id.genderIconMale);
        ImageView genderIconFemale = (ImageView) view.findViewById(R.id.genderIconFemale);
        if (Objects.equals(myGender, "A Man")) {
            genderIconMale.setVisibility(View.VISIBLE);
            genderIconFemale.setVisibility(View.INVISIBLE);
        } else {
            genderIconMale.setVisibility(View.INVISIBLE);
            genderIconFemale.setVisibility(View.VISIBLE);
        }
        edit_profile_gender.setOnClickListener(v -> {
                    if (Objects.equals(myGender, "A Man")) {
                        genderIconMale.setVisibility(View.INVISIBLE);
                        genderIconFemale.setVisibility(View.VISIBLE);
                    } else {
                        genderIconMale.setVisibility(View.VISIBLE);
                        genderIconFemale.setVisibility(View.INVISIBLE);
                    }
                    if (Objects.equals(myGender, genders[0])) {
                        edit_profile_gender.setText(genderName[1]);
                        ((MenuActivity) requireActivity()).updateUserData("gender", genders[1]);

                    } else {
                        edit_profile_gender.setText(genderName[0]);
                        ((MenuActivity) requireActivity()).updateUserData("gender", genders[0]);
                    }
                    userDataRead();
                }
        );
        edit_activity_level_text.setText("Edit Activity Level");
        edit_profile_activity_level.setText("" + myActivityLevel);

        for (int i = 0; i < myActivityLevels.length; i++) {
            if (myActivityLevel == myActivityLevels[i]) {
                edit_profile_activity_level.setText(myActivityLevelDescription[i]);
            }
        }
        edit_profile_activity_level.setOnClickListener(v -> {
            Log.d("Gender: ", myActivityLevel + "");
            for (int i = 0; i < myActivityLevels.length; i++) {
                if (myActivityLevel == myActivityLevels[i]) {
                    if (i == myActivityLevels.length - 1) {
                        edit_profile_activity_level.setText(myActivityLevelDescription[0]);
                        ((MenuActivity) requireActivity()).updateUserData("activityLevel", myActivityLevels[0]);
                        userDataRead();
                        break;
                    } else {
                        edit_profile_activity_level.setText(myActivityLevelDescription[i + 1]);
                        ((MenuActivity) requireActivity()).updateUserData("activityLevel", myActivityLevels[i + 1]);
                        userDataRead();
                        break;
                    }
                }
            }
        });

        edit_goal_text.setText("Edit Goal");
        edit_profile_goal.setText(gal_Description());
        edit_profile_goal.setOnClickListener(v -> {
            for (int i = 0; i < myGoalDescription.length; i++) {
                if (Objects.equals(gal_Description(), myGoalDescription[i])) {
                    if (i == myGoalDescription.length - 1) {
                        edit_profile_goal.setText(myGoalDescription[0]);
                        ((MenuActivity) requireActivity()).updateUserData("goal", 0);
                        userDataRead();
                        break;
                    } else {
                        edit_profile_goal.setText(myGoalDescription[i + 1]);
                        ((MenuActivity) requireActivity()).updateUserData("goal", i + 1);
                        userDataRead();
                        break;
                    }
                }
            }
        });


        edit_bmr_text.setText("BMR (Basal Metabolic Rate)");
        edit_profile_bmr.setText("" + myBmr);
        float newBmr = calcBmr(myWeight, myHeight, myAge, myGender);
        if (newBmr != myBmr) {
            ((MenuActivity) requireActivity()).updateUserData("bmr", newBmr);
            userDataRead();
        }

        edit_target_text.setText("Target Calories");
        edit_profile_target.setText("" + myTarget);

            for (int i = 0; i < myDifference.length; i++) {
                if (myTarget == myBmr + myDifference[i]) {
                    if (i == myDifference.length - 1) {
                        edit_profile_target.setText("" + (myBmr + myDifference[0]));
                        ((MenuActivity) requireActivity()).updateUserData("target", myBmr + myDifference[0]);
                        userDataRead();
                        break;
                    } else {
                        edit_profile_target.setText("" + (myBmr + myDifference[i + 1]));
                        ((MenuActivity) requireActivity()).updateUserData("target", myBmr + myDifference[i + 1]);
                        userDataRead();
                        break;
                    }
                }
            }




        return view;
    }


    private void click() {
    }

    private String profile_desc_title() {
        return "Hi " + myDisplayName + "! We've compiled some information about your health profile:\n\n";
    }

    private CharSequence gal_Description() {
        return myGoalDescription[myGoal];
    }

    private String profile_description() {

        return "Age: " + myAge + " years old. Age is an important factor in determining health and nutritional needs. " +
                "Gender: " + myGender + ". Gender can influence metabolism and nutrient requirements. " +
                "Weight: " + myWeight + " kg. This is crucial for assessing overall health and setting weight-related goals. " +
                "Height: " + myHeight + " cm. Height is necessary for calculating certain health indicators like body mass index (BMI). " +
                "Activity Level: " + myActivityLevel + ".  This indicates how much physical activity he engages in daily, which impacts calorie needs. " +
                "BMR (Basal Metabolic Rate): " + myBmr + ".  BMR represents the number of calories the body needs at rest to maintain vital functions such as breathing and circulation." +
                "Goal: goal is to " + gal_Description() + ". This information is crucial for tailoring dietary recommendations and setting calorie targets. " +
                " (Target Calories: To achieve the goal of" + gal_Description() + "You should consume " + myTarget + " calories per day. This figure considers his BMR, activity level, and the desired outcome of " + gal_Description() + ")\n\n" +
                " In summary, this message provides a snapshot of " + myDisplayName + " health profile, including his basic information, activity level, and dietary goal, along with a recommended calorie intake to support his objective of " + gal_Description() + ".";
    }

    private String profile_Title() {
        return "My Profile";
    }

    private float calcBmr(float weight, int height, int age, String gender) {
        String[] genderArray = getResources().getStringArray(R.array.gender_list);
        return (gender.equals(genderArray[0]) ? calcManBMR(weight, height, age) : calcWomanBMR(weight, height, age));
    }

    private float calcManBMR(float weight, int height, int age) {
        return (10f * weight) + (6.25f * height) - (5f * age) + 5f;
    }

    private float calcWomanBMR(float weight, int height, int age) {
        return (10f * weight) + (6.25f * height) - (5f * age) - 16;
    }


}
